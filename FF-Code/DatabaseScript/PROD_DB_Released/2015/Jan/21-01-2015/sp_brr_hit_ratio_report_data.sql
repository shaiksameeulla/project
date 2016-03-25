DROP PROCEDURE IF EXISTS sp_brr_hit_ratio_report_data;
CREATE PROCEDURE sp_brr_hit_ratio_report_data ()
BEGIN
  DECLARE _step varchar(150);
  DECLARE base_query_rowcount int(11) DEFAULT 0;
  DECLARE hit_ratio_data_rowcount int(11) DEFAULT 0;
  DECLARE hit_ratio_dest_report_table_rowcount int(11) DEFAULT 0;
  DECLARE hit_ratio_ori_report_table_rowcount int(11) DEFAULT 0;
  DECLARE brr_summary_report_table_rowcount int(11) DEFAULT 0;
  DECLARE consignment_table_update_rowcount int(11) DEFAULT 0;
  DECLARE brr_table_update_rowcount int(11) DEFAULT 0;

  DECLARE base_query_start_time TIME;
  DECLARE hit_ratio_data_start_time TIME;
  DECLARE hit_ratio_dest_report_table_start_time TIME;
  DECLARE hit_ratio_ori_report_table_start_time TIME;
  DECLARE brr_summary_report_table_start_time TIME;
  DECLARE consignment_table_update_start_time TIME;
  DECLARE brr_table_update_start_time TIME;

--   DECLARE no_such_table CONDITION FOR 1051;
--   DECLARE no_data_found CONDITION FOR SQLSTATE '45000';
-- --
--   DECLARE EXIT HANDLER FOR SQLEXCEPTION
--   BEGIN
--     ROLLBACK;
--     RESIGNAL;
--   END;
-- -- --
--   DECLARE EXIT HANDLER FOR no_such_table
--   BEGIN
--     ROLLBACK;
--     RESIGNAL;
--   END;

--   START TRANSACTION;

  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 0 - STARTING'
     ,curdate()
     ,curtime()
     ,NULL
     ,0);
  -- ------------------------------------------------------------------
  -- STEP 1 - READ CONSIGNMENTS WHICH ARE NOT PROCESSED FOR HIT RATIO
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    base_query_start_time;
  -- ----------------------------------------------------------------
  SET _step = "STEP 1 - READ CONSIGNMENTS WHICH ARE NOT PROCESSED FOR HIT RATIO";
  DROP TEMPORARY TABLE IF EXISTS BASEQUERY;
  CREATE TEMPORARY TABLE BASEQUERY AS
  SELECT
    ffbtb.CONSG_ID AS 'CONSG_ID'
   ,ffbtb.CONSG_NO AS 'CONSG_NO'
   ,CASE
      WHEN bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE THEN ffm_in1.MANIFEST_DATE
      WHEN bpcs1.OFFICE_ID = ffbtb.ORG_OFF THEN ffbtb.BOOKING_DATE
    END
      AS 'CONSG_REC_DATE'
   ,fdimgeo_del_org.CITY_ID AS 'ORIGIN_CITY'
   ,if(
      bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
     ,fdimgeo_del_in1.CITY_ID
     ,fdimgeo_del_org.CITY_ID)
      AS 'DESTINATION_CITY'
   ,CASE
      WHEN bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
      THEN
        ffm_in1.OPERATING_OFFICE
      WHEN bpcs1.OFFICE_ID = ffbtb.ORG_OFF
      THEN
        ffbtb.ORG_OFF
      ELSE
        ffm_in1.OPERATING_OFFICE
    END
      AS 'DELIVERY_OFFICE'
   ,ifnull(
      ffm_in1.LOAD_LOT_ID
     ,1)
      AS 'LOAD_NO'
   ,ffbtb.CONSG_TYPE AS 'CONSG_TYPE'
   ,ffbtb.PRODUCT AS 'PRODUCT'
   ,CASE WHEN bpcs1.CITY_ID = fdimgeo_del_org.CITY_ID THEN 1 ELSE 2 END
      AS 'CATEGORY_ID'
   ,ffdd1.DELIVERY_TIME AS 'ORIGINWISE_DELIVERY_TIME'
   ,getNthWorkingDay(
      ffdd1.DELIVERY_TIME
     ,1
     ,NULL
     ,NULL
     ,NULL
     ,if(
        bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
       ,fdimgeo_del_in1.REGION_ID
       ,fdimgeo_del_org.REGION_ID))
      AS 'DESTINATIONWISE_DELIVERY_TIME'
   ,NULL AS 'PENDING_DATE'
   ,ffdd1.REASON_ID
   ,ffm_in1.OPERATING_OFFICE AS 'IN_MANIFEST_OFF'
   ,ffbtb.BOOKING_DATE AS 'BOOKING_DATE'
   ,ffbtb.ORG_OFF AS 'BOOKING_OFF'
   ,getNthWorkingDay(
      ffm_in1.MANIFEST_DATE
     ,1
     ,NULL
     ,NULL
     ,NULL
     ,if(
        bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
       ,fdimgeo_del_in1.REGION_ID
       ,fdimgeo_del_org.REGION_ID))
      AS 'MANIFEST_DATE'
   ,CASE
      WHEN ffbtb.CONSG_STATUS = 'S'
      THEN
        8
      WHEN ffbtb.CONSG_STATUS = 'H'
      THEN
        5
      WHEN ffbtb.CONSG_STATUS = 'R'
      THEN
        CASE WHEN bpcs1.OFFICE_ID = ffbtb.ORG_OFF THEN 8 ELSE 6 END
      WHEN ffdd1.DELIVERY_STATUS = 'D' OR
           ffm_out1.MANIFEST_PROCESS_CODE IN ('TPDX'
                                             ,'TPBP')
      THEN
        1
      WHEN (ffdd1.RECORD_STATUS = 'A' AND
            ffdd1.DELIVERY_STATUS = 'P' AND
            ffdd1.REASON_ID IS NOT NULL AND
            fdr.REASON_CODE != 'MIT')
      THEN
        2
      WHEN (ffdd1.RECORD_STATUS = 'A' AND
            ffdd1.DELIVERY_STATUS = 'P' AND
            ffdd1.REASON_ID IS NOT NULL AND
            fdr.REASON_CODE = 'MIT')
      THEN
        7
      WHEN ffbtb.MIS_ROUTED = 'Y'
      THEN
        9
      ELSE
        3
    END
      AS CONSG_STATUS_ID
   ,if(
      bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
     ,fdimgeo_del_in1.REGION_ID
     ,fdimgeo_del_org.REGION_ID)
      AS 'DEL_REGION'
   ,if(
      bpcs1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
     ,fdh_in1.HOLIDAY_ID
     ,fdh_org.HOLIDAY_ID)
      AS 'HOLIDAY_ID'
   ,ffbtb.DEST_PINCODE
   ,ffm_out1.OPERATING_OFFICE AS 'OUT_MANIFEST_OFF'
   ,ffdd1.DELIVERY_TIME
   ,ffm_in1.MANIFEST_ID AS 'IN_MANIFEST_ID'
  FROM
    ff_fct_book_to_bill ffbtb
    JOIN ff_d_consignment_type fdct
      ON fdct.CONSIGNMENT_TYPE_ID = ffbtb.CONSG_TYPE
    /* The following block is written to get the servicable pincode for all BO and HO */
    JOIN ff_mv_branch_pincode_servicability bpcs1
      ON ffbtb.DEST_PINCODE = bpcs1.PINCODE_ID
    /* The following block is written to get the latest in-manifest record for every CONSIGNMENT_ID from manifest tables */
    LEFT JOIN ff_mv_latest_in_manifest ffm_in1
      ON (ffm_in1.CONSG_ID = ffbtb.CONSG_ID)
    LEFT JOIN ff_dim_geography fdg_in
      ON fdg_in.office_id = ffm_in1.OPERATING_OFFICE
    /* The following block is written to get the latest out-manifest record for every CONSIGNMENT_ID from manifest tables */
    LEFT JOIN ff_mv_latest_out_manifest ffm_out1
      ON (ffm_out1.CONSG_ID = ffbtb.CONSG_ID AND
          ffm_out1.OPERATING_OFFICE = ffm_in1.OPERATING_OFFICE AND
          (ffm_out1.MANIFEST_DATE > ffm_in1.MANIFEST_DATE OR
           ffm_out1.MANIFEST_DATE > ffbtb.BOOKING_DATE))
    /* The following block is written to get the latest record for every CONSIGNMENT_ID from ff_f_delivery_dtls */
    LEFT JOIN ff_mv_latest_delivery ffdd1 ON ffdd1.CONSG_ID = ffbtb.CONSG_ID
    /* Destination geography information */
    LEFT JOIN ff_dim_geography fdimgeo_del_in1
      ON fdimgeo_del_in1.OFFICE_ID = ffm_in1.OPERATING_OFFICE
    /* Origin geography information */
    LEFT JOIN ff_dim_geography fdimgeo_del_org
      ON fdimgeo_del_org.OFFICE_ID = ffbtb.ORG_OFF
    /* Destination geography, in manifest region holiday (for outstation consignments) */
    LEFT JOIN ff_d_holiday fdh_in1
      ON (fdh_in1.REGION_ID = fdimgeo_del_in1.REGION_ID AND
          DATEDIFF(
            ffdd1.DELIVERY_TIME
           ,fdh_in1.HOLIDAY_DATE) = 0)
    /* Destination geography, origin region holiday (for local consignments) */
    LEFT JOIN ff_d_holiday fdh_org
      ON (fdh_org.REGION_ID = fdimgeo_del_org.REGION_ID AND
          DATEDIFF(
            ffdd1.DELIVERY_TIME
           ,fdh_org.HOLIDAY_DATE) = 0)
    /* Pending reason information */
    LEFT JOIN ff_d_reason fdr ON fdr.reason_id = ffdd1.reason_id
    /* Processed Consignments */
    LEFT JOIN ff_f_consignment_processed_for_brr ffcpfb
      ON ffcpfb.CONSG_ID = ffbtb.CONSG_ID
  WHERE
    ffbtb.RATE_CALCULATED_FOR = 'B' AND
    /* 1. For HO - office serviced by destination pincode should be in manifest office or origin office
	   2. For BO - all offices to be considered	 */
    CASE
      WHEN fdg_in.OFFICE_TYPE_CODE = 'HO'
      THEN
        (ffm_in1.OPERATING_OFFICE = bpcs1.OFFICE_ID OR
         bpcs1.OFFICE_ID = ffbtb.ORG_OFF)
      WHEN fdg_in.OFFICE_TYPE_CODE = 'BO'
      THEN
        TRUE
    END AND
    /* consignment should not be out manifested, or if out manifested then process code should not be BOUT */
    ((ffm_out1.MANIFEST_ID IS NULL) OR
     (ffm_out1.MANIFEST_ID IS NOT NULL AND
      ffm_out1.MANIFEST_PROCESS_CODE != 'BOUT')) AND
    /* consignment should not be processed by HIT Ratio procedure */
    ffcpfb.CONSG_ID IS NULL AND
	/* process consignments which are booked December 2014 onwards */
    ffbtb.BOOKING_DATE >= str_to_date(
                            '01/12/2014'
                           ,'%d/%m/%Y');  
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    base_query_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 1 - READ CONSIGNMENTS WHICH ARE NOT PROCESSED FOR HIT RATIO'
     ,curdate()
     ,base_query_start_time
     ,curtime()
     ,base_query_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF base_query_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for Hit Ratio, rolling back', MYSQL_ERRNO=210;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------

  -- ----------------------------------------------------------------
  -- STEP 2 - CREATE DELIVERY DAY STATISTICS
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    hit_ratio_data_start_time;
  -- ----------------------------------------------------------------
  SET _step = "STEP 2 - CREATE DELIVERY DAY STATISTICS";
  DROP TEMPORARY TABLE IF EXISTS HITRATIODATA;
  CREATE TEMPORARY TABLE HITRATIODATA AS
  -- 2. The following select will tag the consignments into appropriate delivery day bucket.
  SELECT
    BQ.CONSG_ID AS 'CONSG_ID'
   ,BQ.CONSG_NO AS 'CONSG_NO'
   ,date(BQ.CONSG_REC_DATE) AS 'CONSG_REC_DATE'
   ,BQ.ORIGIN_CITY AS 'ORIGIN_CITY'
   ,BQ.DESTINATION_CITY AS 'DESTINATION_CITY'
   ,BQ.DELIVERY_OFFICE AS 'DELIVERY_OFFICE'
   ,BQ.LOAD_NO AS 'LOAD_NO'
   ,BQ.CONSG_TYPE AS 'CONSG_TYPE'
   ,BQ.PRODUCT AS 'PRODUCT'
   ,BQ.CATEGORY_ID AS 'CATEGORY_ID'
   ,date(BQ.DELIVERY_TIME) AS 'DELIVERY_TIME'
   ,date(BQ.ORIGINWISE_DELIVERY_TIME) AS 'ORIGINWISE_DELIVERY_TIME'
   ,date(BQ.DESTINATIONWISE_DELIVERY_TIME) AS 'DESTINATIONWISE_DELIVERY_TIME'
   ,NULL AS 'PENDING_DATE'
   ,BQ.REASON_ID AS 'REASON_ID'
   ,BQ.IN_MANIFEST_OFF AS 'IN_MANIFEST_OFF'
   ,date(BQ.BOOKING_DATE) AS 'BOOKING_DATE'
   ,BQ.BOOKING_OFF AS 'BOOKING_OFF'
   ,date(BQ.MANIFEST_DATE) AS 'MANIFEST_DATE'
   ,BQ.CONSG_STATUS_ID AS 'CONSG_STATUS_ID'
   ,CASE
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) = 1
      THEN
        'D1_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) = 2
      THEN
        'D2_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) = 3
      THEN
        'D3_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) = 4
      THEN
        'D4_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) = 5
      THEN
        'D5_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           datediff(
             date(BQ.ORIGINWISE_DELIVERY_TIME)
            ,date(BQ.BOOKING_DATE)) > 5
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
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 0
      THEN
        'D0_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 1
      THEN
        'D1_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 2
      THEN
        'D2_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 3
      THEN
        'D3_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
            ,NULL
            ,NULL
            ,NULL
            ,BQ.DEL_REGION) = 4
      THEN
        'D4_D'
      WHEN BQ.CONSG_STATUS_ID = 1 AND
           getWorkingDayInterval(
             date(BQ.CONSG_REC_DATE)
            ,date(BQ.DESTINATIONWISE_DELIVERY_TIME)
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
   ,BQ.DEL_REGION
   ,BQ.IN_MANIFEST_ID
  FROM
    BASEQUERY BQ;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_data_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 2 - CREATE DELIVERY DAY STATISTICS'
     ,curdate()
     ,hit_ratio_data_start_time
     ,curtime()
     ,hit_ratio_data_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF hit_ratio_data_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for Hit Ratio, rolling back', MYSQL_ERRNO=211;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------

  -- -------------------------------------------------------------------------------------------------------------------
  -- STEP 3 - POPULATE DESTINATION WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    hit_ratio_dest_report_table_start_time;
  -- -------------------------------------------------------------------------------------------------------------------
  SET _step = "STEP 3 - POPULATE DESTINATION WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE";
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
  ON DUPLICATE KEY UPDATE
    CONSIGNMENTS_RECD=CONSIGNMENTS_RECD+(VALUES(CONSIGNMENTS_RECD)-(CONSIGNMENTS_RECD-(DELIVERED_D0+DELIVERED_D1+DELIVERED_D2+DELIVERED_D3+DELIVERED_D4+DELIVERED_BEYOND_D5)))
   ,CONSIGNMENTS_RET_IN=CONSIGNMENTS_RET_IN+VALUES(CONSIGNMENTS_RET_IN)
   ,CONSIGNMENTS_ACTUAL=(CONSIGNMENTS_RECD+(VALUES(CONSIGNMENTS_RECD)-(CONSIGNMENTS_RECD-(DELIVERED_D0+DELIVERED_D1+DELIVERED_D2+DELIVERED_D3+DELIVERED_D4+DELIVERED_BEYOND_D5))))-(CONSIGNMENTS_RET_IN+VALUES(CONSIGNMENTS_RET_IN))
   ,DELIVERED_D0=DELIVERED_D0+VALUES(DELIVERED_D0)
   ,DELIVERED_D1=DELIVERED_D1+VALUES(DELIVERED_D1)
   ,DELIVERED_D2=DELIVERED_D2+VALUES(DELIVERED_D2)
   ,DELIVERED_D3=DELIVERED_D3+VALUES(DELIVERED_D3)
   ,DELIVERED_D4=DELIVERED_D4+VALUES(DELIVERED_D4)
   ,DELIVERED_BEYOND_D5=DELIVERED_BEYOND_D5+VALUES(DELIVERED_BEYOND_D5)
   ,RTO=RTO+VALUES(RTO)
   ,LOST=LOST+VALUES(LOST);
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_dest_report_table_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 3 - POPULATE DESTINATION WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS'
     ,curtime()
     ,hit_ratio_dest_report_table_start_time
     ,curtime()
     ,hit_ratio_dest_report_table_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF hit_ratio_dest_report_table_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for Destination Hit Ratio, rolling back', MYSQL_ERRNO=212;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------

  -- --------------------------------------------------------------------------------------------------------------
  -- STEP 4 - POPULATE ORIGIN WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    hit_ratio_ori_report_table_start_time;
  -- --------------------------------------------------------------------------------------------------------------
  SET _step = "STEP 4 - POPULATE ORIGIN WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE";
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
    DATA.BOOKING_DATE
   ,DATA.BOOKING_OFF
   ,DATA.DELIVERY_OFFICE
   ,DATA.CONSG_TYPE
   ,DATA.PRODUCT
   ,DATA.CATEGORY_ID
  ON DUPLICATE KEY UPDATE
    CONSIGNMENTS_BOOKED=CONSIGNMENTS_BOOKED+(VALUES(CONSIGNMENTS_BOOKED)-(CONSIGNMENTS_BOOKED-(DELIVERED_D1+DELIVERED_D2+DELIVERED_D3+DELIVERED_D4+DELIVERED_D5+DELIVERED_BEYOND_D5)))
   ,DELIVERED_D1=DELIVERED_D1+VALUES(DELIVERED_D1)
   ,D1_HOLIDAY=VALUES(D1_HOLIDAY)
   ,DELIVERED_D2=DELIVERED_D2+VALUES(DELIVERED_D2)
   ,D2_HOLIDAY=VALUES(D2_HOLIDAY)
   ,DELIVERED_D3=DELIVERED_D3+VALUES(DELIVERED_D3)
   ,D3_HOLIDAY=VALUES(D3_HOLIDAY)
   ,DELIVERED_D4=DELIVERED_D4+VALUES(DELIVERED_D4)
   ,D4_HOLIDAY=VALUES(D4_HOLIDAY)
   ,DELIVERED_D5=DELIVERED_D5+VALUES(DELIVERED_D5)
   ,D5_HOLIDAY=VALUES(D5_HOLIDAY)
   ,DELIVERED_BEYOND_D5=DELIVERED_BEYOND_D5+VALUES(DELIVERED_BEYOND_D5)
   ,D6_HOLIDAY=VALUES(D6_HOLIDAY)
   ,RTO=RTO+VALUES(RTO)
   ,MIS=MIS+VALUES(MIS)
   ,LOST=LOST+VALUES(LOST);
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    hit_ratio_ori_report_table_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 4 - POPULATE ORIGIN WISE HIT RATIO REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS'
     ,curdate()
     ,hit_ratio_ori_report_table_start_time
     ,curtime()
     ,hit_ratio_ori_report_table_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF hit_ratio_ori_report_table_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for Origin Hit Ratio, rolling back', MYSQL_ERRNO=213;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------

  -- ---------------------------------------------------------------------------------------------------
  -- STEP 5 - POPULATE BRR SUMMARY REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE
  -- ---------------------------------------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    brr_summary_report_table_start_time;
  -- ---------------------------------------------------------------------------------------------------
  SET _step = "STEP 5 - POPULATE BRR SUMMARY REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE";
  INSERT INTO
    ff_f_brr_summay_all_india_datewise_report_data(
      CONSG_ID
     ,CONSG_NO
     ,PRODUCT
     ,CONSG_STATUS_ID
     ,CONSG_REC_DATE
     ,DELIVERY_TIME
     ,CONSIGNMENT_TYPE_ID
     ,CATEGORY_ID
     ,LOAD_NO
     ,DELIVERY_OFFICE
     ,DESTINATION_CITY
     ,DEL_REGION)
  SELECT
     DATA.CONSG_ID
    ,DATA.CONSG_NO
    ,DATA.PRODUCT
    ,DATA.CONSG_STATUS_ID
    ,DATA.CONSG_REC_DATE
    ,DATA.DELIVERY_TIME
    ,DATA.CONSG_TYPE
    ,DATA.CATEGORY_ID
    ,ifnull(DATA.LOAD_NO, 1)
    ,DATA.DELIVERY_OFFICE
    ,DATA.DESTINATION_CITY
    ,DATA.DEL_REGION
  FROM
    HITRATIODATA DATA
  ON DUPLICATE KEY UPDATE
    CONSG_STATUS_ID=VALUES(CONSG_STATUS_ID)
   ,DELIVERY_TIME=VALUES(DELIVERY_TIME);
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    brr_summary_report_table_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 5 - POPULATE BRR SUMMARY REPORT TABLE FOR REPORTING PURPOSE OF CONSIGNMENTS IN TEMPORARY TABLE'
     ,curdate()
     ,brr_summary_report_table_start_time
     ,curtime()
     ,brr_summary_report_table_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF brr_summary_report_table_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for BRR Summary Report, rolling back', MYSQL_ERRNO=214;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------

  -- --------------------------------------------------------------------------------------
  -- STEP 6 - UPDATE DELIVERY DETAILS OF DELIVERED CONSIGNMENTS IN BRR TABLE
  -- --------------------------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    brr_table_update_start_time;
  -- --------------------------------------------------------------------------------------
  SET _step = "STEP 6 - UPDATE DELIVERY DETAILS OF DELIVERED CONSIGNMENTS IN BRR TABLE";
  UPDATE
    ff_f_brr_summay_all_india_datewise_report_data report_data
    JOIN HITRATIODATA DATA ON DATA.CONSG_ID = report_data.CONSG_ID
  SET
    report_data.CONSG_STATUS_ID  = DATA.CONSG_STATUS_ID
   ,report_data.DELIVERY_TIME    = DATA.DELIVERY_TIME
  WHERE
    DATA.CONSG_STATUS_ID = 1 AND
    report_data.CONSG_STATUS_ID != 1;
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    brr_table_update_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 6 - UPDATE DELIVERY DETAILS OF DELIVERED CONSIGNMENTS IN BRR TABLE'
     ,curdate()
     ,brr_table_update_start_time
     ,curtime()
     ,brr_table_update_rowcount);
  -- -------------------------------------------------------------------------------------------------

  -- --------------------------------------------------------------------------------------
  -- STEP 7 - INSERT DELIVERED CONSIGNMENTS IN STATUS TABLE
  -- --------------------------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    consignment_table_update_start_time;
  -- --------------------------------------------------------------------------------------
  SET _step = "STEP 7 - INSERT DELIVERED CONSIGNMENTS IN STATUS TABLE";
  INSERT INTO
    ff_f_consignment_processed_for_brr(
      CONSG_ID
  )
  SELECT
    CONSG_ID
  FROM
    HITRATIODATA data
  WHERE
    data.CONSG_STATUS_ID = 1
  ON DUPLICATE KEY UPDATE
    CONSG_ID=VALUES(CONSG_ID);
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    consignment_table_update_rowcount;
  -- -------------------------------------------------------------------------------------------------
  INSERT INTO
  ff_aud_report_data_refresh_audit(
    PROC_NAME
   ,OPERATION
   ,EXEC_DATE
   ,START_TIME
   ,END_TIME
   ,ROWS_AFFECTED)
  VALUES
    (
      'sp_brr_hit_ratio_report_data'
     ,'STEP 7 - INSERT DELIVERED CONSIGNMENTS IN STATUS TABLE'
     ,curdate()
     ,consignment_table_update_start_time
     ,curtime()
     ,consignment_table_update_rowcount);
  -- -------------------------------------------------------------------------------------------------
--   IF consignment_table_update_rowcount <= 0 THEN
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='No CN Data found for Hit Ratio, rolling back', MYSQL_ERRNO=214;
-- --     ROLLBACK;
--   ELSEIF
--     base_query_rowcount > 0 AND
--     hit_ratio_data_rowcount > 0 AND
--     hit_ratio_dest_report_table_rowcount > 0 AND
--     hit_ratio_ori_report_table_rowcount > 0 AND
--     consignment_table_update_rowcount > 0 THEN
--     SELECT 'Hit Ratio Report procedure Execution Completed, committing - 216';
-- --     COMMIT;
--   ELSE
--     SIGNAL no_data_found
--       SET MESSAGE_TEXT='Problem in execution of procedure, rolling back', MYSQL_ERRNO=215;
-- --     ROLLBACK;
--   END IF;
  -- -------------------------------------------------------------------------------------------------




  DROP TEMPORARY TABLE IF EXISTS BASEQUERY;
  DROP TEMPORARY TABLE IF EXISTS HITRATIODATA;

END;