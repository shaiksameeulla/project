DROP PROCEDURE IF EXISTS sp_brr_hit_ratio_report_data;
CREATE PROCEDURE sp_brr_hit_ratio_report_data ()
BEGIN
  DECLARE _step varchar(150);
  DECLARE hit_ratio_data_rowcount int(11) DEFAULT 0;
  DECLARE hit_ratio_dest_report_table_rowcount int(11) DEFAULT 0;
  DECLARE hit_ratio_ori_report_table_rowcount int(11) DEFAULT 0;
  DECLARE consignment_table_update_rowcount int(11) DEFAULT 0;

  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    SHOW ERRORS;
    SELECT _step;
    ROLLBACK;
  END;

  DECLARE EXIT HANDLER FOR no_such_table
  BEGIN
    SHOW ERRORS;
    SELECT _step;
    ROLLBACK;
  END;

  START TRANSACTION;

  -- ----------------------------------------------------------------
  -- STEP 1 - READ CONSIGNMENTS WHICH ARE NOT PROCESSED FOR HIT RATIO
  -- ----------------------------------------------------------------
  SET _step = "STEP 1 - READ CONSIGNMENTS WHICH ARE NOT PROCESSED FOR HIT RATIO";
  CREATE TEMPORARY TABLE IF NOT EXISTS HITRATIODATA AS
  -- 2. The following select will tag the consignments into appropriate delivery day bucket.
  SELECT
    BQ.CONSG_ID AS 'CONSG_ID'
   ,BQ.CONSG_NO AS 'CONSG_NO'
   ,BQ.CONSG_REC_DATE AS 'CONSG_REC_DATE'
   ,BQ.ORIGIN_CITY AS 'ORIGIN_CITY'
   ,BQ.DESTINATION_CITY AS 'DESTINATION_CITY'
   ,BQ.DELIVERY_OFFICE AS 'DELIVERY_OFFICE'
   ,BQ.LOAD_NO AS 'LOAD_NO'
   ,BQ.CONSG_TYPE AS 'CONSG_TYPE'
   ,BQ.PRODUCT AS 'PRODUCT'
   ,BQ.CATEGORY_ID AS 'CATEGORY_ID'
   ,BQ.ORIGINWISE_DELIVERY_TIME AS 'ORIGINWISE_DELIVERY_TIME'
   ,BQ.DESTINATIONWISE_DELIVERY_TIME AS 'DESTINATIONWISE_DELIVERY_TIME'
   ,NULL AS 'PENDING_DATE'
   ,BQ.REASON_ID AS 'REASON_ID'
   ,BQ.IN_MANIFEST_OFF AS 'IN_MANIFEST_OFF'
   ,BQ.BOOKING_DATE AS 'BOOKING_DATE'
   ,BQ.ORG_OFF AS 'BOOKING_OFF'
   ,BQ.MANIFEST_DATE AS 'MANIFEST_DATE'
   ,BQ.CONSG_STATUS_ID AS 'CONSG_STATUS_ID'
   ,CASE
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) = 1
      THEN
        'D1_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) = 2
      THEN
        'D2_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) = 3
      THEN
        'D3_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) = 4
      THEN
        'D4_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) = 5
      THEN
        'D5_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             BQ.ORIGINWISE_DELIVERY_TIME
            ,BQ.BOOKING_DATE) > 5
      THEN
        'MT_D5'
      WHEN BQ.CONSG_STATUS_ID IN (2
                                 ,3)
      THEN
        'P'
      WHEN BQ.CONSG_STATUS_ID = 6
      THEN
        'R'
      WHEN BQ.CONSG_STATUS_ID = 7
      THEN
        'L'
      WHEN BQ.CONSG_STATUS_ID = 9
      THEN
        'M'
      ELSE
        'NA'
    END
      AS 'ORIGINWISE_CONSG_STATUS'
   ,CASE
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 0
      THEN
        'D0_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 1
      THEN
        'D1_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 2
      THEN
        'D2_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 3
      THEN
        'D3_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 4
      THEN
        'D4_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             BQ.MANIFEST_DATE
            ,BQ.DESTINATIONWISE_DELIVERY_TIME
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) > 4
      THEN
        'MT_D5'
      WHEN BQ.CONSG_STATUS_ID IN (2
                                 ,3)
      THEN
        'P'
      WHEN BQ.CONSG_STATUS_ID = 6
      THEN
        'R'
      WHEN BQ.CONSG_STATUS_ID = 7
      THEN
        'L'
      ELSE
        'NA'
    END
      AS 'DESTINATIONWISE_CONSG_STATUS'
   ,BQ.HOLIDAY_ID
  FROM
    (SELECT
       ffc.CONSG_ID AS 'CONSG_ID'
      ,ffc.CONSG_NO AS 'CONSG_NO'
      ,CASE
         WHEN bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE THEN date( ffm_in1.MANIFEST_DATE)
         WHEN bpcs1.OFFICE_ID = ffc.ORG_OFF THEN date( ffb.BOOKING_DATE)
       END
         AS 'CONSG_REC_DATE'
      ,fdo_org.CITY_ID AS 'ORIGIN_CITY'
      ,fdc_del.CITY_ID AS 'DESTINATION_CITY'
      ,CASE
         WHEN bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
         THEN
           ffm_in1.OPERATING_OFFICE
         WHEN bpcs1.OFFICE_ID = ffc.ORG_OFF
         THEN
           ffc.ORG_OFF
       END
         AS 'DELIVERY_OFFICE'
      ,ffc.ORG_OFF
      ,ffd.LOAD_NO
      ,ffc.CONSG_TYPE AS 'CONSG_TYPE'
      ,ffc.PRODUCT AS 'PRODUCT'
      ,CASE WHEN fdo_bpcs.CITY_ID = fdo_org.CITY_ID THEN 1 ELSE 2 END
         AS 'CATEGORY_ID'
      ,date( ffdd1.DELIVERY_TIME) AS 'ORIGINWISE_DELIVERY_TIME'
      ,getNthWorkingDay(
         ffdd1.DELIVERY_TIME
        ,1
        ,NULL
        ,NULL
        ,NULL
        ,fdc_del.REGION)
         AS 'DESTINATIONWISE_DELIVERY_TIME'
      ,NULL AS 'PENDING_DATE'
      ,ffdd1.REASON_ID
      ,ffm_in1.OPERATING_OFFICE AS 'IN_MANIFEST_OFF'
      ,date( ffb.BOOKING_DATE) AS 'BOOKING_DATE'
      ,ffc.ORG_OFF AS 'BOOKING_OFF'
      ,getNthWorkingDay(
         date( ffm_in1.MANIFEST_DATE)
        ,1
        ,NULL
        ,NULL
        ,NULL
        ,fdc_del.REGION)
         AS 'MANIFEST_DATE'
      ,CASE
         WHEN ffc.CONSG_STATUS = 'S'
         THEN
           8 -- Return to Consignor
         WHEN ffc.CONSG_STATUS = 'H'
         THEN
           5 -- RTH
         WHEN ffc.CONSG_STATUS = 'R'
         THEN
           CASE WHEN bpcs1.OFFICE_ID = ffc.ORG_OFF THEN 8 -- Return to Consignor
                                                         ELSE 6 -- RTO
                                                               END
         WHEN ffdd1.DELIVERY_STATUS = 'D' OR
              ffm_out1.MANIFEST_PROCESS_CODE IN ('TPDX'
                                                ,'TPBP')
         THEN
           1 -- Delivered
         WHEN (ffdd1.RECORD_STATUS = 'A' AND
               ffdd1.DELIVERY_STATUS = 'P' AND
               ffdd1.REASON_ID IS NOT NULL)
         THEN
           2 -- Pending with Reason
         WHEN (ffdd1.reason_id IN (SELECT
                                     reason_id
                                   FROM
                                     ff_d_reason
                                   WHERE
                                     reason_code = 'MIT') OR
               ffc.CONSG_NO IN (SELECT
                                  TRANSACTION_NUMBER
                                FROM
                                  ff_f_heldup
                                WHERE
                                  TRANSACTION_TYPE = 'CONG'))
         THEN
           7 -- Lost
         WHEN ffc.MIS_ROUTED = 'Y'
         THEN
           9 -- Misrouted
         ELSE
           3 -- Pending without Reason
       END
         AS CONSG_STATUS_ID
      ,fdh.HOLIDAY_ID AS 'HOLIDAY_ID'
      ,fdc_del.REGION AS 'DEL_REGION'
      ,ffm_out1.OPERATING_OFFICE AS 'OUT_MANIFEST_OFF'
      ,ffdd1.DELIVERY_TIME
     FROM
       ff_f_consignment ffc
       LEFT JOIN ff_f_booking ffb ON ffb.CONSG_NUMBER = ffc.CONSG_NO
       JOIN ff_d_consignment_type fdct
         ON fdct.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE
       JOIN ff_d_office fdo_org ON fdo_org.OFFICE_ID = ffc.ORG_OFF
       JOIN ff_d_city fdc_org ON fdc_org.CITY_ID = fdo_org.CITY_ID
       -- get the servicable pincode for all BO and HO
       JOIN
       (SELECT
          fdbps.PINCODE_ID
         ,fdbps.OFFICE_ID
        FROM
          ff_d_office fdo
          JOIN ff_d_branch_pincode_serviceablity fdbps
            ON fdbps.OFFICE_ID = fdo.OFFICE_ID
          JOIN ff_d_pincode fdp ON fdp.PINCODE_ID = fdbps.PINCODE_ID
          JOIN ff_d_office_type fdot ON fdot.OFFICE_TYPE_ID = fdo.OFFICE_TYPE_ID)
       bpcs1
         ON ffc.DEST_PINCODE = bpcs1.PINCODE_ID
       JOIN ff_d_office fdo_bpcs ON fdo_bpcs.OFFICE_ID = bpcs1.OFFICE_ID
       -- in manifest
       LEFT JOIN (SELECT
                    ffm_in1.MANIFEST_ID
                   ,ffm_in1.MANIFEST_NO
                   ,ffcm_in.CONSIGNMENT_ID
                   ,ffm_in1.MANIFEST_DATE
                   ,ffm_in1.MANIFEST_TYPE
                   ,ffm_in1.OPERATING_OFFICE
                   ,ffm_in1.MANIFEST_LOAD_CONTENT
                  FROM
                    ff_f_consignment_manifested ffcm_in
                    LEFT JOIN ff_f_manifest ffm_in1
                      ON (ffm_in1.MANIFEST_ID = ffcm_in.MANIFEST_ID AND
                          ffm_in1.MANIFEST_TYPE = 'I')
                    LEFT JOIN
                    (SELECT
                       ffcm2.consignment_id
                      ,ffm2.MANIFEST_DATE
                      ,ffm2.MANIFEST_ID
                      ,ffm2.MANIFEST_NO
                      ,ffm2.MANIFEST_TYPE
                     FROM
                       ff_f_consignment_manifested ffcm2
                       LEFT JOIN ff_f_manifest ffm2
                         ON ffm2.manifest_id = ffcm2.manifest_id) AS ffm_in2
                      ON (ffcm_in.consignment_id = ffm_in2.consignment_id AND
                          ffm_in2.MANIFEST_TYPE = ffm_in1.MANIFEST_TYPE AND
                          (ffm_in1.MANIFEST_DATE < ffm_in2.MANIFEST_DATE OR
                           (ffm_in1.MANIFEST_DATE = ffm_in2.MANIFEST_DATE AND
                            ffm_in1.MANIFEST_ID < ffm_in2.MANIFEST_ID)))
                  WHERE
                    ffm_in2.MANIFEST_DATE IS NULL) ffm_in1
         ON (ffm_in1.CONSIGNMENT_ID = ffc.CONSG_ID AND
             ffm_in1.MANIFEST_LOAD_CONTENT = fdct.CONSIGNMENT_TYPE_ID)
       -- out manifest
       LEFT JOIN (SELECT
                    ffm_out1.MANIFEST_ID
                   ,ffm_out1.MANIFEST_NO
                   ,ffcm_out.CONSIGNMENT_ID
                   ,ffm_out1.MANIFEST_DATE
                   ,ffm_out1.MANIFEST_TYPE
                   ,ffm_out1.OPERATING_OFFICE
                   ,ffm_out1.MANIFEST_LOAD_CONTENT
                   ,ffm_out1.MANIFEST_PROCESS_CODE
                  FROM
                    ff_f_consignment_manifested ffcm_out
                    LEFT JOIN ff_f_manifest ffm_out1
                      ON (ffm_out1.MANIFEST_ID = ffcm_out.MANIFEST_ID AND
                          ffm_out1.MANIFEST_TYPE = 'O')
                    LEFT JOIN
                    (SELECT
                       ffcm2.consignment_id
                      ,ffm2.MANIFEST_DATE
                      ,ffm2.MANIFEST_ID
                      ,ffm2.MANIFEST_NO
                      ,ffm2.MANIFEST_TYPE
                     FROM
                       ff_f_consignment_manifested ffcm2
                       LEFT JOIN ff_f_manifest ffm2
                         ON ffm2.manifest_id = ffcm2.manifest_id) AS ffm_out2
                      ON (ffcm_out.consignment_id = ffm_out2.consignment_id AND
                          ffm_out2.MANIFEST_TYPE = ffm_out1.MANIFEST_TYPE AND
                          (ffm_out1.MANIFEST_DATE < ffm_out2.MANIFEST_DATE OR
                           (ffm_out1.MANIFEST_DATE = ffm_out2.MANIFEST_DATE AND
                            ffm_out1.MANIFEST_ID < ffm_out2.MANIFEST_ID)))
                  WHERE
                    ffm_out2.MANIFEST_DATE IS NULL) ffm_out1
         ON (ffm_out1.CONSIGNMENT_ID = ffc.CONSG_ID AND
             ffm_out1.MANIFEST_LOAD_CONTENT = fdct.CONSIGNMENT_TYPE_ID AND
             ffm_out1.OPERATING_OFFICE = ffm_in1.OPERATING_OFFICE AND
             (ffm_out1.MANIFEST_DATE > ffm_in1.MANIFEST_DATE OR
              ffm_out1.MANIFEST_DATE > ffb.BOOKING_DATE))
       -- delivery
       LEFT JOIN ff_f_delivery_dtls ffdd1 ON ffdd1.CONSIGNMENT_ID = ffc.CONSG_ID
       LEFT OUTER JOIN ff_f_delivery_dtls ffdd2
         ON (ffdd1.CONSIGNMENT_ID = ffdd2.CONSIGNMENT_ID AND
             (ffdd1.TRANS_MODIFIED_DATE_TIME < ffdd2.TRANS_MODIFIED_DATE_TIME OR
              (ffdd1.TRANS_MODIFIED_DATE_TIME = ffdd2.TRANS_MODIFIED_DATE_TIME AND
               ffdd1.DELIVERY_DETAIL_ID > ffdd2.DELIVERY_DETAIL_ID)))
       LEFT JOIN ff_f_delivery ffd ON ffd.DELIVERY_ID = ffdd1.DELIVERY_ID
       LEFT JOIN ff_d_office fdo_del
         ON CASE
              WHEN bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
              THEN
                fdo_del.OFFICE_ID = ffm_in1.OPERATING_OFFICE
              WHEN bpcs1.OFFICE_ID = ffc.ORG_OFF
              THEN
                fdo_del.OFFICE_ID = ffc.ORG_OFF
            END
       LEFT JOIN ff_d_city fdc_del ON fdc_del.CITY_ID = fdo_del.CITY_ID
       LEFT JOIN ff_d_holiday fdh
         ON (fdh.REGION_ID = fdc_del.REGION AND
             DATEDIFF(
               ffdd1.DELIVERY_TIME
              ,fdh.HOLIDAY_DATE) = 0)
     WHERE
       -- delivery
       ffdd2.TRANS_MODIFIED_DATE_TIME IS NULL AND
       (bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE OR
        bpcs1.OFFICE_ID = ffc.ORG_OFF) AND
       ((ffm_out1.MANIFEST_ID IS NULL) OR
        (ffm_out1.MANIFEST_ID IS NOT NULL AND
         ffm_out1.MANIFEST_PROCESS_CODE != 'BOUT')) AND
       ffc.PROCESSED_FOR_HIT_RATIO = 'N'
     ORDER BY
       IFNULL(
         ffdd1.CONSIGNMENT_NUMBER
        ,ffc.CONSG_NO)
      ,ffdd1.TRANS_MODIFIED_DATE_TIME) BQ;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_data_rowcount;
  -- -------------------------------------------------------------------------------------------------
  IF hit_ratio_data_rowcount <= 0 THEN
    SIGNAL no_data_found
      SET MESSAGE_TEXT='No CN Data found for Hit Ratio, rolling back', MYSQL_ERRNO=211;
    ROLLBACK;
  END IF;
  -- -------------------------------------------------------------------------------------------------

  -- -------------------------------------------------------------------------------------------------------------------
  -- STEP 2 - POPULATE DESTINATION WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE
  -- -------------------------------------------------------------------------------------------------------------------
  SET _step = "STEP 2 - POPULATE DESTINATION WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE";
  INSERT INTO ff_f_hit_ratio_report_data_destinationwise (
    DELIVERY_OFFICE,
    CONSIGNMENT_REC_DATE,
    LOAD_NO,
    CONSG_TYPE,
    PRODUCT,
    CATEGORY_ID,
    CONSIGNMENTS_RECD,
    CONSIGNMENTS_RET_IN,
    CONSIGNMENTS_ACTUAL,
    DELIVERED_D0,
    DELIVERED_D1,
    DELIVERED_D2,
    DELIVERED_D3,
    DELIVERED_D4,
    DELIVERED_BEYOND_D5,
    RTO,
    LOST
  )
  SELECT
    DATA.DELIVERY_OFFICE
   ,DATA.CONSG_REC_DATE
   ,DATA.LOAD_NO
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
   , /* Total Received */
    SUM( 1) AS 'CONSIGNMENTS_RECD'
   , /* Return In */
    SUM( CASE WHEN CONSG_STATUS_ID = 8 THEN 1 ELSE 0 END)
      AS 'CONSIGNMENTS_RET_IN'
   , /* Actual CNs which can be delivered */
    SUM( CASE WHEN CONSG_STATUS_ID != 8 THEN 1 ELSE 0 END)
      AS 'CONSIGNMENTS_ACTUAL'
   , /* CNs delivered on same day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'D0_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D0'
   , /* CNs delivered on next day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'D1_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D1'
   , /* CNs delivered on next+1 day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'D2_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D2'
   , /* CNs delivered on next+2 day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'D3_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D3'
   , /* CNs delivered on next+3 day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'D4_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D4'
   , /* CNs delivered after next+3 day of receipt */
    SUM( CASE WHEN DATA.DESTINATIONWISE_CONSG_STATUS = 'MT_D5' THEN 1 ELSE 0 END)
      AS 'DELIVERED_BEYOND_D5'
   , /* Total CNs RTOed */
    SUM( CASE WHEN CONSG_STATUS_ID = 6 THEN 1 ELSE 0 END) AS 'RTO'
   , /* Total CNs Lost */
    SUM( CASE WHEN CONSG_STATUS_ID = 7 THEN 1 ELSE 0 END) AS 'LOST'
  FROM
    HITRATIODATA DATA
    JOIN ff_d_consignment_type fdct ON fdct.CONSIGNMENT_TYPE_ID = DATA.CONSG_TYPE
  GROUP BY
    DATA.DELIVERY_OFFICE
   ,DATA.CONSG_REC_DATE
   ,DATA.LOAD_NO
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
  ORDER BY
    DATA.DELIVERY_OFFICE
   ,DATA.CONSG_REC_DATE
   ,DATA.LOAD_NO
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_dest_report_table_rowcount;
  -- -------------------------------------------------------------------------------------------------
  IF hit_ratio_dest_report_table_rowcount <= 0 THEN
    SIGNAL no_data_found
      SET MESSAGE_TEXT='No CN Data found for Destination Hit Ratio, rolling back', MYSQL_ERRNO=212;
    ROLLBACK;
  END IF;
  -- -------------------------------------------------------------------------------------------------

  -- --------------------------------------------------------------------------------------------------------------
  -- STEP 3 - POPULATE ORIGIN WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE
  -- --------------------------------------------------------------------------------------------------------------
  SET _step = "STEP 3 - POPULATE ORIGIN WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE";
  INSERT INTO ff_f_hit_ratio_report_data_originwise (
    BOOKING_DATE,
    BOOKING_OFFICE,
    DELIVERY_OFFICE,
    CONSG_TYPE,
    PRODUCT,
    CATEGORY_ID,
    CONSIGNMENTS_BOOKED,
    DELIVERED_D1,
    D1_HOLIDAY,
    DELIVERED_D2,
    D2_HOLIDAY,
    DELIVERED_D3,
    D3_HOLIDAY,
    DELIVERED_D4,
    D4_HOLIDAY,
    DELIVERED_D5,
    D5_HOLIDAY,
    DELIVERED_BEYOND_D5,
    D6_HOLIDAY,
    RTO,
    MIS,
    LOST
  )
  SELECT
    DATA.BOOKING_DATE
   ,DATA.BOOKING_OFF
   ,DATA.DELIVERY_OFFICE
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
   , /* Total Received */
    sum( 1) AS 'CONSIGNMENTS_BOOKED'
   , /* CNs delivered on next day of booking */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'D1_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D1'
   , /* Destination Region Holiday on next day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'D1_D' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D1_HOLIDAY'
   , /* CNs delivered on next+1 day of booking */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'D2_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D2'
   , /* Destination Region Holiday on next+1 day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'D2_D' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D2_HOLIDAY'
   , /* CNs delivered on next+2 day of booking */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'D3_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D3'
   , /* Destination Region Holiday on next+2 day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'D3_D' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D3_HOLIDAY'
   , /* CNs delivered on next+3 day of booking */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'D4_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D4'
   , /* Destination Region Holiday on next+3 day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'D4_D' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D4_HOLIDAY'
   , /* CNs delivered on next+4 day of booking */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'D5_D' THEN 1 ELSE 0 END)
      AS 'DELIVERED_D5'
   , /* Destination Region Holiday on next+4 day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'D5_D' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D5_HOLIDAY'
   , /* CNs delivered after next+4 day of receipt */
    sum( CASE WHEN DATA.ORIGINWISE_CONSG_STATUS = 'MT_D5' THEN 1 ELSE 0 END)
      AS 'DELIVERED_BEYOND_D5'
   , /* Destination Region Holiday after next+4 day of booking */
    (CASE
       WHEN (DATA.ORIGINWISE_CONSG_STATUS = 'MT_D5' AND
             DATA.HOLIDAY_ID IS NOT NULL)
       THEN
         fdhn.HOLIDAY_NAME
       ELSE
         0
     END)
      AS 'D6_HOLIDAY'
   , /* Total CNs RTOed */
    sum( CASE WHEN CONSG_STATUS_ID = 6 THEN 1 ELSE 0 END) AS 'RTO'
   , /* Total CNs Misrouted */
    sum( CASE WHEN CONSG_STATUS_ID = 9 THEN 1 ELSE 0 END) AS 'MIS'
   , /* Total CNs Lost */
    sum( CASE WHEN CONSG_STATUS_ID = 7 THEN 1 ELSE 0 END) AS 'LOST'
  FROM
    HITRATIODATA DATA
    JOIN ff_d_consignment_type fdct ON fdct.CONSIGNMENT_TYPE_ID = DATA.CONSG_TYPE
    LEFT JOIN ff_d_holiday fdh ON fdh.HOLIDAY_ID = DATA.HOLIDAY_ID
    LEFT JOIN ff_d_holiday_name fdhn
      ON fdhn.HOLIDAY_NAME_ID = fdh.HOLIDAY_NAME_ID
  GROUP BY
    DATA.DELIVERY_OFFICE
   ,DATA.CONSG_REC_DATE
   ,DATA.LOAD_NO
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
  ORDER BY
    DATA.DELIVERY_OFFICE
   ,DATA.CONSG_REC_DATE
   ,DATA.LOAD_NO
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_ori_report_table_rowcount;
  -- -------------------------------------------------------------------------------------------------
  IF hit_ratio_ori_report_table_rowcount <= 0 THEN
    SIGNAL no_data_found
      SET MESSAGE_TEXT='No CN Data found for Origin Hit Ratio, rolling back', MYSQL_ERRNO=213;
    ROLLBACK;
  END IF;
  -- -------------------------------------------------------------------------------------------------

  -- --------------------------------------------------------------------------------------
  -- STEP 4 - MARK DELIVERED CONSIGNMENTS IN TEMPORARY TABLE AS PROCESSED 'Y' FOR HIT RATIO
  -- --------------------------------------------------------------------------------------
  SET _step = "STEP 4 - MARK DELIVERED CONSIGNMENTS IN TEMPORARY TABLE AS PROCESSED 'Y' FOR HIT RATIO";
  UPDATE ff_f_consignment ffc
  JOIN HITRATIODATA data ON data.CONSG_ID = ffc.CONSG_ID
  SET ffc.PROCESSED_FOR_HIT_RATIO = 'Y'
  WHERE data.CONSG_STATUS_ID = 1;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    consignment_table_update_rowcount;
  -- -------------------------------------------------------------------------------------------------
  IF consignment_table_update_rowcount <= 0 THEN
    SIGNAL no_data_found
      SET MESSAGE_TEXT='No CN Data found for Hit Ratio, rolling back', MYSQL_ERRNO=214;
    ROLLBACK;
  ELSEIF
    hit_ratio_data_rowcount > 0 AND
    hit_ratio_dest_report_table_rowcount > 0 AND
    hit_ratio_ori_report_table_rowcount > 0 AND
    consignment_table_update_rowcount > 0 THEN
    SELECT 'Hit Ratio Report procedure Execution Completed, committing - 216';
    COMMIT;
  ELSE
    SIGNAL no_data_found
      SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=215;
    ROLLBACK;
  END IF;
  -- -------------------------------------------------------------------------------------------------

  DROP TEMPORARY TABLE IF EXISTS HITRATIODATA;

END;