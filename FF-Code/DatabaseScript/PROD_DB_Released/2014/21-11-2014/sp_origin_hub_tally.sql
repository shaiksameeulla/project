DROP PROCEDURE IF EXISTS sp_origin_hub_tally;
CREATE PROCEDURE sp_origin_hub_tally(
  IN loading_date varchar(15)
 ,IN office_id    int)
BEGIN
    SET SESSION group_concat_max_len = 1000000;

    SELECT
      received.RECEIVED_OFFICE_NAME
     ,count(DISTINCT received.MBPL) AS 'MBPL_COUNT'
     ,count(DISTINCT received.BPL) + count(received.MBPL_BAG) AS 'BPL_COUNT'
     ,count(
        DISTINCT CASE
                   WHEN received.MBPL_BAG_PACKET_OPEN_TYPE = 'O'
                   THEN
                     received.MBPL_BAG_PKT
                   ELSE
                     NULL
                 END) +
      count(
        DISTINCT CASE
                   WHEN received.BAG_PACKET_OPEN_TYPE = 'O'
                   THEN
                     received.BPL_PKT
                   ELSE
                     NULL
                 END)
        AS 'OGM_PACKET_COUNT'
     ,count(
        DISTINCT CASE
                   WHEN received.MBPL_BAG_PACKET_OPEN_TYPE = 'P'
                   THEN
                     received.MBPL_BAG_PKT
                   ELSE
                     NULL
                 END) +
      count(
        DISTINCT CASE
                   WHEN received.BAG_PACKET_OPEN_TYPE = 'P'
                   THEN
                     received.BPL_PKT
                   ELSE
                     NULL
                 END)
        AS 'OPEN_PACKET_COUNT'
     ,count(DISTINCT received.CONSG_ID) AS 'RECEIVED_CONSIGNMENT_COUNT'
     ,count(
        DISTINCT CASE
                   WHEN received.CONSG_ID IS NOT NULL
                   THEN
                     CASE
                       WHEN dispatched.CONSG_ID IS NOT NULL OR
                            ffdd.DELIVERY_STATUS = 'D'
                       THEN
                         received.CONSG_ID
                       ELSE
                         NULL
                     END
                 END)
        AS 'DISPATCHED_CONSIGNMENT_COUNT'
     ,(count(DISTINCT received.CONSG_ID) -
       (count(
          DISTINCT CASE
                     WHEN received.CONSG_ID IS NOT NULL
                     THEN
                       CASE
                         WHEN dispatched.CONSG_ID IS NOT NULL OR
                              ffdd.DELIVERY_STATUS = 'D'
                         THEN
                           received.CONSG_ID
                         ELSE
                           NULL
                       END
                   END)))
        AS 'DIFFERENCE'
     ,group_concat(
        DISTINCT CASE
                   WHEN received.CONSG_ID IS NOT NULL
                   THEN
                     CASE
                       WHEN dispatched.CONSG_ID IS NULL AND
                            (ffdd.DELIVERY_STATUS IS NULL OR
                             ffdd.DELIVERY_STATUS != 'D')
                       THEN
                         received.CONSG_ID
                     END
                 END)
        AS 'PENDING_CNS'
    FROM
      /* receive */
      (SELECT
         fdo_hub.OFFICE_ID AS 'RECEIVED_OFFICE_ID'
        ,fdo_hub.OFFICE_NAME AS 'RECEIVED_OFFICE_NAME'
        ,ffm.MANIFEST_NO
        ,CASE
           WHEN ffm.MANIFEST_NO LIKE '___M%' THEN ffm.MANIFEST_NO
           ELSE NULL
         END
           AS 'MBPL'
        ,ffm_bag.MANIFEST_NO AS 'MBPL_BAG'
        ,ffm_pkt.MANIFEST_NO AS 'MBPL_BAG_PKT'
        ,CASE
           WHEN ffm.MANIFEST_NO LIKE '___B%' THEN ffm.MANIFEST_NO
           ELSE NULL
         END
           AS 'BPL'
        ,ffm_pkt1.MANIFEST_NO AS 'BPL_PKT'
        ,ffm_pkt_prev_out.MANIFEST_OPEN_TYPE AS 'MBPL_BAG_PACKET_OPEN_TYPE'
        ,ffm_pkt1_prev_out.MANIFEST_OPEN_TYPE AS 'BAG_PACKET_OPEN_TYPE'
        ,CASE
           WHEN if(ffm_pkt.NO_OF_ELEMENTS is null, ffcm_pkt_prev_out.CONSIGNMENT_ID, ffcm_pkt.CONSIGNMENT_ID) IS NOT NULL
           THEN
             if(ffm_pkt.NO_OF_ELEMENTS is null, ffcm_pkt_prev_out.CONSIGNMENT_ID, ffcm_pkt.CONSIGNMENT_ID)
           WHEN if(ffm_bag.NO_OF_ELEMENTS is null, ffcm_pkt3_prev_out.CONSIGNMENT_ID, ffcm_pkt3.CONSIGNMENT_ID) IS NOT NULL
           THEN
             if(ffm_bag.NO_OF_ELEMENTS is null, ffcm_pkt3_prev_out.CONSIGNMENT_ID, ffcm_pkt3.CONSIGNMENT_ID)
           WHEN if(ffm_pkt1.NO_OF_ELEMENTS is null, ffcm_pkt1_prev_out.CONSIGNMENT_ID, ffcm_pkt1.CONSIGNMENT_ID) IS NOT NULL
           THEN
             if(ffm_pkt1.NO_OF_ELEMENTS is null, ffcm_pkt1_prev_out.CONSIGNMENT_ID, ffcm_pkt1.CONSIGNMENT_ID)
           WHEN if(ffm.NO_OF_ELEMENTS is null, ffcm_pkt2_prev_out.CONSIGNMENT_ID, ffcm_pkt2.CONSIGNMENT_ID) IS NOT NULL
           THEN
             if(ffm.NO_OF_ELEMENTS is null, ffcm_pkt2_prev_out.CONSIGNMENT_ID, ffcm_pkt2.CONSIGNMENT_ID)
           ELSE
             NULL
         END
           AS 'CONSG_ID'
       FROM
         ff_d_office fdo_hub
         JOIN ff_d_office_type fdot
           ON fdot.OFFICE_TYPE_ID = fdo_hub.OFFICE_TYPE_ID
         JOIN ff_f_load_movement fflm
           ON (fflm.DEST_OFFICE = fdo_hub.OFFICE_ID AND
               fflm.RECEIVE_TYPE = 'L' AND
               fflm.MOVEMENT_DIRECTION = 'R')
         JOIN ff_f_load_connected fflc
           ON (fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID AND
               fflc.RECEIVED_STATUS != 'N')
         JOIN ff_f_manifest ffm ON ffm.MANIFEST_ID = fflc.MANIFEST
         LEFT JOIN ff_d_consignment_type fdct_mlc
           ON fdct_mlc.CONSIGNMENT_TYPE_ID = ffm.MANIFEST_LOAD_CONTENT
         /*
         hierarchy under master bags received
         */
         LEFT JOIN ff_f_manifest ffm_bag
           ON (ffm.MANIFEST_NO LIKE '___M%' AND
               ffm_bag.MANIFEST_EMBEDDED_IN = ffm.MANIFEST_ID)
         LEFT JOIN ff_d_consignment_type fdct_bag_mlc
           ON fdct_bag_mlc.CONSIGNMENT_TYPE_ID = ffm_bag.MANIFEST_LOAD_CONTENT
         /* for DOX */
         LEFT JOIN ff_f_manifest ffm_pkt
           ON (ffm_pkt.MANIFEST_EMBEDDED_IN = ffm_bag.MANIFEST_ID)
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt
           ON ffcm_pkt.MANIFEST_ID = ffm_pkt.MANIFEST_ID
         LEFT JOIN ff_f_manifest ffm_pkt_prev_out
           ON (ffm_pkt_prev_out.MANIFEST_NO = ffm_pkt.MANIFEST_NO AND
               ffm_pkt_prev_out.OPERATING_OFFICE = ffm_pkt.ORIGIN_OFFICE AND
               ffm_pkt_prev_out.MANIFEST_TYPE = 'O')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt_prev_out
           ON ffcm_pkt_prev_out.MANIFEST_ID = ffm_pkt_prev_out.MANIFEST_ID           
         /* for PPX */
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt3
           ON (ffcm_pkt3.MANIFEST_ID = ffm_bag.MANIFEST_ID AND
               ffm_bag.MANIFEST_NO LIKE '___B%')
         LEFT JOIN ff_f_manifest ffm_pkt3_prev_out
           ON (ffm_pkt3_prev_out.MANIFEST_NO = ffm_bag.MANIFEST_NO AND
               ffm_pkt3_prev_out.OPERATING_OFFICE = ffm_bag.ORIGIN_OFFICE AND
               ffm_pkt3_prev_out.MANIFEST_TYPE = 'O')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt3_prev_out
           ON ffcm_pkt3_prev_out.MANIFEST_ID = ffm_pkt3_prev_out.MANIFEST_ID
         /*
         hierarchy under the direct bags received for DOX
         */
         LEFT JOIN ff_f_manifest ffm_pkt1
           ON (ffm_pkt1.MANIFEST_EMBEDDED_IN = ffm.MANIFEST_ID AND
               ffm.MANIFEST_NO LIKE '___B%')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt1
           ON ffcm_pkt1.MANIFEST_ID = ffm_pkt1.MANIFEST_ID
         LEFT JOIN ff_f_manifest ffm_pkt1_prev_out
           ON (ffm_pkt1_prev_out.MANIFEST_NO = ffm_pkt1.MANIFEST_NO AND
               ffm_pkt1_prev_out.OPERATING_OFFICE = ffm_pkt1.ORIGIN_OFFICE AND
               ffm_pkt1_prev_out.MANIFEST_TYPE = 'O')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt1_prev_out
           ON ffcm_pkt1_prev_out.MANIFEST_ID = ffm_pkt1_prev_out.MANIFEST_ID
         /*
         hierarchy under the direct bags received for PPX
         */
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt2
           ON (ffcm_pkt2.MANIFEST_ID = ffm.MANIFEST_ID AND
               ffm.MANIFEST_NO LIKE '___B%')
         LEFT JOIN ff_f_manifest ffm_pkt2_prev_out
           ON (ffm_pkt2_prev_out.MANIFEST_NO = ffm.MANIFEST_NO AND
               ffm_pkt2_prev_out.OPERATING_OFFICE = ffm.ORIGIN_OFFICE AND
               ffm_pkt2_prev_out.MANIFEST_TYPE = 'O')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt2_prev_out
           ON ffcm_pkt2_prev_out.MANIFEST_ID = ffm_pkt2_prev_out.MANIFEST_ID
       WHERE
         fdot.OFFICE_TYPE_CODE = 'HO' AND
         date(ffm.CREATED_DATE ) = str_to_date(
                                     loading_date
                                    ,'%d/%m/%Y') AND
         fdo_hub.OFFICE_ID = office_id
       UNION
       SELECT
         fdo_hub.OFFICE_ID AS 'RECEIVED_OFFICE_ID'
        ,fdo_hub.OFFICE_NAME AS 'RECEIVED_OFFICE_NAME'
        ,NULL AS 'MANIFEST_NO'
        ,NULL AS 'MBPL'
        ,NULL AS 'MBPL_BAG'
        ,NULL AS 'MBPL_BAG_PKT'
        ,NULL AS 'BPL'
        ,NULL AS 'BPL_PKT'
        ,NULL AS 'MBPL_BAG_PACKET_OPEN_TYPE'
        ,NULL AS 'BAG_PACKET_OPEN_TYPE'
        ,ffc_consg.CONSG_ID AS 'CONSG_ID'
       FROM
         ff_d_office fdo_hub
         JOIN ff_d_office_type fdot
           ON fdot.OFFICE_TYPE_ID = fdo_hub.OFFICE_TYPE_ID
         JOIN ff_f_booking ffb_booking
           ON ffb_booking.BOOKING_OFF = fdo_hub.OFFICE_ID
         JOIN ff_f_consignment ffc_consg
           ON ffb_booking.CONSG_NUMBER = ffc_consg.CONSG_NO
       WHERE
         fdot.OFFICE_TYPE_CODE = 'HO' AND
         date(ffb_booking.Booking_date) = str_to_date(
                                            loading_date
                                           ,'%d/%m/%Y') AND
         fdo_hub.OFFICE_ID = office_id) received
      LEFT JOIN
      /* dispatch */
      (SELECT
         CASE
           WHEN ffcm_pkt.CONSIGNMENT_ID IS NOT NULL
           THEN
             ffcm_pkt.CONSIGNMENT_ID
           WHEN ffcm_pkt3.CONSIGNMENT_ID IS NOT NULL
           THEN
             ffcm_pkt3.CONSIGNMENT_ID
           WHEN ffcm_pkt1.CONSIGNMENT_ID IS NOT NULL
           THEN
             ffcm_pkt1.CONSIGNMENT_ID
           WHEN ffcm_pkt2.CONSIGNMENT_ID IS NOT NULL
           THEN
             ffcm_pkt2.CONSIGNMENT_ID
           ELSE
             NULL
         END
           AS 'CONSG_ID'
       FROM
         ff_d_office fdo_hub
         JOIN ff_d_office_type fdot
           ON fdot.OFFICE_TYPE_ID = fdo_hub.OFFICE_TYPE_ID
         JOIN ff_f_load_movement fflm
           ON (fflm.DEST_OFFICE = fdo_hub.OFFICE_ID AND
               fflm.MOVEMENT_DIRECTION = 'D')
         JOIN ff_f_load_connected fflc
           ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
         JOIN ff_f_manifest ffm ON ffm.MANIFEST_ID = fflc.MANIFEST
         LEFT JOIN ff_d_consignment_type fdct_mlc
           ON fdct_mlc.CONSIGNMENT_TYPE_ID = ffm.MANIFEST_LOAD_CONTENT
         /*
         hierarchy under master bags dispatched
         */
         LEFT JOIN ff_f_manifest ffm_bag
           ON (ffm.MANIFEST_NO LIKE '___M%' AND
               ffm_bag.MANIFEST_EMBEDDED_IN = ffm.MANIFEST_ID)
         LEFT JOIN ff_d_consignment_type fdct_bag_mlc
           ON fdct_bag_mlc.CONSIGNMENT_TYPE_ID = ffm_bag.MANIFEST_LOAD_CONTENT
         /* for DOX */
         LEFT JOIN ff_f_manifest ffm_pkt
           ON (ffm_pkt.MANIFEST_EMBEDDED_IN = ffm_bag.MANIFEST_ID AND
               fdct_bag_mlc.CONSIGNMENT_CODE = 'DOX')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt
           ON ffcm_pkt.MANIFEST_ID = ffm_pkt.MANIFEST_ID
         /* for PPX */
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt3
           ON (ffcm_pkt3.MANIFEST_ID = ffm_bag.MANIFEST_ID AND
               ffm_bag.MANIFEST_NO LIKE '___B%' AND
               fdct_bag_mlc.CONSIGNMENT_CODE = 'PPX')
         /*
         hierarchy under the direct bags dispatched for DOX
         */
         LEFT JOIN ff_f_manifest ffm_pkt1
           ON (ffm_pkt1.MANIFEST_EMBEDDED_IN = ffm.MANIFEST_ID AND
               ffm.MANIFEST_NO LIKE '___B%' AND
               fdct_mlc.CONSIGNMENT_CODE = 'DOX')
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt1
           ON ffcm_pkt1.MANIFEST_ID = ffm_pkt1.MANIFEST_ID
         /*
         hierarchy under the direct bags dispatched for PPX
         */
         LEFT JOIN ff_f_consignment_manifested ffcm_pkt2
           ON (ffcm_pkt2.MANIFEST_ID = ffm.MANIFEST_ID AND
               fdct_mlc.CONSIGNMENT_CODE = 'PPX' AND
               ffm.MANIFEST_NO LIKE '___B%')
       WHERE
         (fdct_mlc.CONSIGNMENT_CODE = 'DOX' OR
          fdct_mlc.CONSIGNMENT_CODE = 'PPX') AND
         fdot.OFFICE_TYPE_CODE = 'HO' AND
         fdo_hub.OFFICE_ID = office_id) dispatched
        ON received.CONSG_ID = dispatched.CONSG_ID
      LEFT JOIN ff_f_delivery_dtls ffdd
        ON (ffdd.CONSIGNMENT_ID = received.CONSG_ID AND
            ffdd.DELIVERY_STATUS = 'D')
      LEFT JOIN ff_f_delivery ffd
        ON (ffd.DELIVERY_ID = ffdd.DELIVERY_ID AND
            ffd.CREATED_OFFICE_ID = received.RECEIVED_OFFICE_ID)
      LEFT JOIN ff_f_consignment ffc ON ffc.CONSG_ID = received.CONSG_ID
      LEFT JOIN ff_d_consignment_type fdct
        ON fdct.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE;

    SET SESSION group_concat_max_len = 1024;
  END;
