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
   ,BQ.ORG_CITY AS 'ORIGIN_CITY'
   ,BQ.DEL_CITY AS 'DESTINATION_CITY'
   ,BQ.DEL_OFF AS 'DELIVERY_OFFICE'
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
       DATA.CONSG_NO
      ,DATA.CONSG_ID
      ,DATA.LOAD_NO
      ,DATA.ORG_OFF
      ,DATA.ORG_CITY
      ,DATA.ORG_STATE
      ,DATA.ORG_REGION
      ,DATA.DEL_OFF
      ,fdc_del.CITY_ID AS 'DEL_CITY'
      ,fdc_del.STATE AS 'DEL_STATE'
      ,fdc_del.REGION AS 'DEL_REGION'
      ,DATA.DEST_PINCODE
      ,DATA.CONSG_TYPE
      ,DATA.PRODUCT
      ,DATA.IN_MANIFEST_OFF
      ,DATA.CATEGORY_ID
      ,DATA.CONSG_REC_DATE
      ,DATA.BOOKING_DATE
      ,getNthWorkingDay(
         DATA.MANIFEST_DATE
        ,1
        ,NULL
        ,NULL
        ,NULL
        ,fdc_del.REGION)
         AS 'MANIFEST_DATE'
      ,DATA.DELIVERY_TIME AS 'ORIGINWISE_DELIVERY_TIME'
      ,getNthWorkingDay(
         DATA.DELIVERY_TIME
        ,1
        ,NULL
        ,NULL
        ,NULL
        ,fdc_del.REGION)
         AS 'DESTINATIONWISE_DELIVERY_TIME'
      ,DATA.TRANS_MODIFIED_DATE_TIME
      ,DATA.DELIVERY_STATUS
      ,DATA.CONSG_STATUS_ID
      ,DATA.REASON_ID
      ,fdh.HOLIDAY_ID
     FROM
       ( -- 3. Base Query to get status of various consignments received in a delivery office.
        SELECT
          IFNULL(
            DELIVERY.CONSIGNMENT_NUMBER
           ,ff_f_consignment.CONSG_NO)
            AS CONSG_NO
         ,ff_f_consignment.CONSG_ID
         ,ff_f_delivery.LOAD_NO
         ,ff_f_consignment.ORG_OFF
         ,fdo_org.CITY_ID AS 'ORG_CITY'
         ,fdc_org.STATE AS 'ORG_STATE'
         ,fdc_org.REGION AS 'ORG_REGION'
         ,ff_f_consignment.DEST_PINCODE
         ,ff_f_consignment.CONSG_TYPE
         ,ff_f_consignment.PRODUCT
         ,IN_MANIFEST.OPERATING_OFFICE AS IN_MANIFEST_OFF
         ,CASE
            WHEN bpcs1.OFFICE_ID = IN_MANIFEST.OPERATING_OFFICE
            THEN
              IN_MANIFEST.OPERATING_OFFICE
            WHEN bpcs1.OFFICE_ID = ff_f_consignment.ORG_OFF
            THEN
              ff_f_consignment.ORG_OFF
          END
            AS DEL_OFF
         ,CASE WHEN fdo_bpcs.CITY_ID = fdo_org.CITY_ID THEN 1 ELSE 2 END
            AS CATEGORY_ID
         ,CASE
            WHEN bpcs1.OFFICE_ID = IN_MANIFEST.OPERATING_OFFICE THEN date( IN_MANIFEST.MANIFEST_DATE)
            WHEN bpcs1.OFFICE_ID = ff_f_consignment.ORG_OFF THEN date( ff_f_booking.BOOKING_DATE)
          END
            AS CONSG_REC_DATE
         ,date( ff_f_booking.BOOKING_DATE) AS BOOKING_DATE
         ,date( IN_MANIFEST.MANIFEST_DATE) AS MANIFEST_DATE
         ,date( DELIVERY.DELIVERY_TIME) AS DELIVERY_TIME
         ,DELIVERY.TRANS_MODIFIED_DATE_TIME
         ,DELIVERY.DELIVERY_STATUS
         ,CASE
            WHEN ff_f_consignment.CONSG_STATUS = 'S'
            THEN
              8 -- Return to Consignor
            WHEN ff_f_consignment.CONSG_STATUS = 'H'
            THEN
              5 -- RTH
            -- If the consignment status = "R" and Delivery Office = Origin office of the CN
            --    Then it should be RECO
            -- else
            --    RTO
            -- end if
            WHEN ff_f_consignment.CONSG_STATUS = 'R'
            THEN
              CASE
                WHEN bpcs1.OFFICE_ID = ff_f_consignment.ORG_OFF THEN 8 -- Return to Consignor
                ELSE 6 -- RTO
              END
            WHEN DELIVERY.DELIVERY_STATUS = 'D' OR
                 OUT_MANIFEST.MANIFEST_PROCESS_CODE IN ('TPDX'
                                                       ,'TPBP')
            THEN
              1 -- Delivered
            WHEN (DELIVERY.RECORD_STATUS = 'A' AND
                  DELIVERY.DELIVERY_STATUS = 'P' AND
                  DELIVERY.REASON_ID IS NOT NULL)
            THEN
              2 -- Pending with Reason
            WHEN (DELIVERY.reason_id IN (SELECT
                                           reason_id
                                         FROM
                                           ff_d_reason
                                         WHERE
                                           reason_code = 'MIT') OR
                  ff_f_consignment.CONSG_NO IN (SELECT
                                                  TRANSACTION_NUMBER
                                                FROM
                                                  ff_f_heldup
                                                WHERE
                                                  TRANSACTION_TYPE = 'CONG'))
            THEN
              7 -- Lost
            WHEN ff_f_consignment.MIS_ROUTED = 'Y'
            THEN
              9 -- Misrouted
            ELSE
              3 -- Pending without Reason
          END
            AS CONSG_STATUS_ID
         ,DELIVERY.REASON_ID
        FROM
          ff_f_booking -- 1
          JOIN ff_f_consignment -- 2
            ON ff_f_booking.CONSG_NUMBER = ff_f_consignment.CONSG_NO
          -- 3.0. The following block is written to get the servicable pincode for all BO and HO
          JOIN
          (SELECT
             fdbps.PINCODE_ID
            ,fdbps.OFFICE_ID
           FROM
             ff_d_office fdo
             JOIN ff_d_branch_pincode_serviceablity fdbps
               ON fdbps.OFFICE_ID = fdo.OFFICE_ID
             JOIN ff_d_pincode fdp ON fdp.PINCODE_ID = fdbps.PINCODE_ID
             JOIN ff_d_office_type fdot
               ON fdot.OFFICE_TYPE_ID = fdo.OFFICE_TYPE_ID
           UNION
           SELECT
             DISTINCT (fdbps_hub.PINCODE_ID) AS PINCODE_ID
                     ,fdbps_hub.OFFICE_ID
           FROM
             ff_d_office fdo_hub
             JOIN ff_d_office_type fdot_hub
               ON fdot_hub.OFFICE_TYPE_ID = fdo_hub.OFFICE_TYPE_ID
             JOIN ff_d_office fdo_rb ON fdo_rb.REPORTING_HUB = fdo_hub.OFFICE_ID
             JOIN ff_d_branch_pincode_serviceablity fdbps_hub
               ON fdbps_hub.OFFICE_ID = fdo_rb.OFFICE_ID
             JOIN ff_d_pincode fdp_hub
               ON fdp_hub.PINCODE_ID = fdbps_hub.PINCODE_ID
           WHERE
             fdot_hub.OFFICE_TYPE_CODE = 'HO') bpcs1 -- 3
            ON ff_f_consignment.DEST_PINCODE = bpcs1.PINCODE_ID
          -- 3.1. The following block is written to get the latest in-manifest record for every CONSIGNMENT_ID from manifest tables
          LEFT JOIN
          (SELECT
             MAX_MANIFEST_ID.MANIFEST_ID
            ,MAX_MANIFEST_ID.CONSIGNMENT_ID
            ,ff_f_manifest.MANIFEST_DATE
            ,ff_f_manifest.manifest_type
            ,ff_f_manifest.OPERATING_OFFICE
           FROM
             (SELECT
                max( m.MANIFEST_DATE) AS MANIFEST_DATE
               ,m.MANIFEST_ID
               ,cm.consignment_id
              FROM
                ff_f_consignment_manifested cm
                JOIN ff_f_manifest m ON cm.MANIFEST_ID = m.MANIFEST_ID
              WHERE
                m.manifest_type = 'I'
              GROUP BY
                cm.consignment_id) MAX_MANIFEST_ID
             JOIN ff_f_manifest
               ON MAX_MANIFEST_ID.MANIFEST_ID = ff_f_manifest.MANIFEST_ID)
          AS IN_MANIFEST -- 4
            ON IN_MANIFEST.CONSIGNMENT_ID = ff_f_consignment.CONSG_ID
          -- 3.1.1. The following block is written to get the latest out-manifest record for every CONSIGNMENT_ID from manifest tables
          LEFT JOIN
          (SELECT
             MAX_MANIFEST_ID.MANIFEST_ID
            ,MAX_MANIFEST_ID.CONSIGNMENT_ID
            ,ff_f_manifest.MANIFEST_DATE
            ,ff_f_manifest.manifest_type
            ,ff_f_manifest.OPERATING_OFFICE
            ,ff_f_manifest.MANIFEST_PROCESS_CODE
           FROM
             (SELECT
                max( m.MANIFEST_DATE) AS MANIFEST_DATE
               ,m.MANIFEST_ID
               ,cm.consignment_id
              FROM
                ff_f_consignment_manifested cm
                JOIN ff_f_manifest m ON cm.MANIFEST_ID = m.MANIFEST_ID
              WHERE
                m.manifest_type = 'O'
              GROUP BY
                cm.consignment_id) MAX_MANIFEST_ID
             JOIN ff_f_manifest
               ON MAX_MANIFEST_ID.MANIFEST_ID = ff_f_manifest.MANIFEST_ID)
          AS OUT_MANIFEST -- 5
            ON (OUT_MANIFEST.CONSIGNMENT_ID = ff_f_consignment.CONSG_ID AND
                OUT_MANIFEST.OPERATING_OFFICE = IN_MANIFEST.OPERATING_OFFICE AND
                (OUT_MANIFEST.MANIFEST_DATE > IN_MANIFEST.MANIFEST_DATE OR
                 OUT_MANIFEST.MANIFEST_DATE > ff_f_booking.BOOKING_DATE))
          -- 3.2. The following block is written to get the latest record for every CONSIGNMENT_ID from ff_f_delivery_dtls
          LEFT JOIN
          (SELECT
             dd.DELIVERY_ID
            ,dd.CONSIGNMENT_NUMBER
            ,dd.CONSIGNMENT_ID
            ,dd.DELIVERY_TIME
            ,dd.DELIVERY_STATUS
            ,dd.RECORD_STATUS
            ,dd.REASON_ID
            ,dd.TRANS_MODIFIED_DATE_TIME
            ,dd.RECEIVER_NAME
           FROM
             (SELECT
                CONSIGNMENT_ID
               ,CONSIGNMENT_NUMBER
               ,max( TRANS_MODIFIED_DATE_TIME) AS TRANS_MODIFIED_DATE_TIME
              FROM
                ff_f_delivery_dtls
              WHERE
                RECORD_STATUS = 'A'
              GROUP BY
                CONSIGNMENT_ID
               ,CONSIGNMENT_NUMBER) AS DD_MAX
             INNER JOIN ff_f_delivery_dtls AS dd
               ON DD_MAX.CONSIGNMENT_ID = dd.CONSIGNMENT_ID AND
                  DD_MAX.CONSIGNMENT_NUMBER = dd.CONSIGNMENT_NUMBER AND
                  DD_MAX.TRANS_MODIFIED_DATE_TIME = dd.TRANS_MODIFIED_DATE_TIME)
          AS DELIVERY -- 6
            ON ff_f_consignment.CONSG_ID = DELIVERY.CONSIGNMENT_ID
          JOIN ff_f_delivery -- 6
                            ON DELIVERY.DELIVERY_ID = ff_f_delivery.DELIVERY_ID
          JOIN ff_d_office fdo_org -- 7
            ON fdo_org.OFFICE_ID = ff_f_consignment.ORG_OFF
          JOIN ff_d_city fdc_org ON fdc_org.CITY_ID = fdo_org.CITY_ID
          JOIN ff_d_office fdo_bpcs -- 8
                                   ON fdo_bpcs.OFFICE_ID = bpcs1.OFFICE_ID
        WHERE
          (bpcs1.OFFICE_ID = IN_MANIFEST.OPERATING_OFFICE OR
           bpcs1.OFFICE_ID = ff_f_consignment.ORG_OFF) AND
          ((OUT_MANIFEST.MANIFEST_ID IS NULL) OR
           (OUT_MANIFEST.MANIFEST_ID IS NOT NULL AND
            OUT_MANIFEST.MANIFEST_PROCESS_CODE != 'BOUT')) AND
          ff_f_consignment.PROCESSED_FOR_HIT_RATIO = 'N'
        ORDER BY
          IFNULL(
            DELIVERY.CONSIGNMENT_NUMBER
           ,ff_f_consignment.CONSG_NO)
         ,DELIVERY.TRANS_MODIFIED_DATE_TIME) DATA
       LEFT JOIN ff_d_office fdo_del ON fdo_del.OFFICE_ID = DATA.DEL_OFF
       LEFT JOIN ff_d_city fdc_del ON fdc_del.CITY_ID = fdo_del.CITY_ID
       LEFT JOIN ff_d_holiday fdh
         ON (fdh.REGION_ID = fdc_del.REGION AND
             DATEDIFF(
               DATA.DELIVERY_TIME
              ,fdh.HOLIDAY_DATE) = 0)) BQ
  ;
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