DROP PROCEDURE IF EXISTS sp_mv_latest_in_out_manifest;
CREATE PROCEDURE sp_mv_latest_in_out_manifest ()
BEGIN
  DECLARE _step varchar(150);
  DECLARE ROWCOUNT INT(11) DEFAULT 0;
  DECLARE START_TIME TIME;

  DECLARE no_such_table CONDITION FOR 1051;
  DECLARE no_data_found CONDITION FOR SQLSTATE '45000';

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    RESIGNAL;
    SELECT _step;
    ROLLBACK;
  END;

  DECLARE EXIT HANDLER FOR no_such_table
  BEGIN
    RESIGNAL;
    SELECT _step;
    ROLLBACK;
  END;

  START TRANSACTION;

  -- ----------------------------------------------------------------
  -- STEP 1 - POPULATE LATEST IN MANIFEST DATA FOR EACH CONSIGNMENT
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ----------------------------------------------------------------
  SET _step = "STEP 1 - POPULATE LATEST IN MANIFEST DATA FOR EACH CONSIGNMENT";
  TRUNCATE TABLE ff_mv_latest_in_manifest;
  INSERT INTO
    ff_mv_latest_in_manifest(
      CONSG_ID
     ,CONSIGNMENT_MANIFESTED_ID
     ,MANIFEST_ID
     ,MANIFEST_NO
     ,MANIFEST_DATE
     ,MANIFEST_TYPE
     ,OPERATING_OFFICE
     ,MANIFEST_LOAD_CONTENT
     ,LOAD_LOT_ID)
    SELECT
      ffc.CONSG_ID
     ,ffcm.CONSIGNMENT_MANIFESTED_ID
     ,ffm1.MANIFEST_ID
     ,ffm1.MANIFEST_NO
     ,ffm1.MANIFEST_DATE
     ,ffm1.MANIFEST_TYPE
     ,ffm1.OPERATING_OFFICE
     ,ffm1.MANIFEST_LOAD_CONTENT
     ,ifnull(ffm1_out.LOAD_LOT_ID,1)
    FROM
      ff_f_consignment ffc
      JOIN ff_f_consignment_manifested ffcm ON ffcm.consignment_id = ffc.consg_id
      JOIN ff_d_consignment_type fdct
        ON fdct.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE
      JOIN ff_f_manifest ffm1
        ON (ffm1.MANIFEST_ID = ffcm.MANIFEST_ID AND
            ffm1.MANIFEST_TYPE = 'I')
      LEFT JOIN ff_f_manifest ffm1_out
        ON (ffm1_out.MANIFEST_NO = ffm1.MANIFEST_NO AND
            ffm1_out.ORIGIN_OFFICE = ffm1_out.OPERATING_OFFICE AND
            ffm1_out.MANIFEST_DIRECTION = 'O' AND
            ffm1_out.UPDATING_PROCESS != 7)
    WHERE
      NOT EXISTS
        (SELECT
           1
         FROM
           ff_f_consignment_manifested ffcm2
           JOIN ff_f_manifest ffm12 ON ffm12.manifest_id = ffcm2.manifest_id
         WHERE
           (ffcm.consignment_id = ffcm2.consignment_id AND
            ffm12.MANIFEST_TYPE = ffm1.MANIFEST_TYPE AND
            ffm1.MANIFEST_ID < ffm12.MANIFEST_ID));
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    ROWCOUNT;
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
      'sp_mv_latest_in_out_manifest'
     ,'STEP 1 - POPULATE LATEST IN MANIFEST DATA FOR EACH CONSIGNMENT'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------

  -- ----------------------------------------------------------------
  -- STEP 2 - POPULATE LATEST OUT MANIFEST DATA FOR EACH CONSIGNMENT
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ----------------------------------------------------------------
  SET _step = "STEP 2 - POPULATE LATEST OUT MANIFEST DATA FOR EACH CONSIGNMENT";
  TRUNCATE TABLE ff_mv_latest_out_manifest;
  INSERT INTO
    ff_mv_latest_out_manifest(
      CONSG_ID
     ,CONSIGNMENT_MANIFESTED_ID
     ,MANIFEST_ID
     ,MANIFEST_NO
     ,MANIFEST_DATE
     ,MANIFEST_TYPE
     ,OPERATING_OFFICE
     ,MANIFEST_LOAD_CONTENT
     ,MANIFEST_PROCESS_CODE
     ,LOAD_LOT_ID)
    SELECT
      ffc.CONSG_ID
     ,ffcm.CONSIGNMENT_MANIFESTED_ID
     ,ffm1.MANIFEST_ID
     ,ffm1.MANIFEST_NO
     ,ffm1.MANIFEST_DATE
     ,ffm1.MANIFEST_TYPE
     ,ffm1.OPERATING_OFFICE
     ,ffm1.MANIFEST_LOAD_CONTENT
     ,ffm1.MANIFEST_PROCESS_CODE
     ,ifnull(ffm1.LOAD_LOT_ID,1)
    FROM
      ff_f_consignment ffc
      JOIN ff_f_consignment_manifested ffcm ON ffcm.consignment_id = ffc.consg_id
      JOIN ff_d_consignment_type fdct
        ON fdct.CONSIGNMENT_TYPE_ID = ffc.CONSG_TYPE
      JOIN ff_f_manifest ffm1
        ON (ffm1.MANIFEST_ID = ffcm.MANIFEST_ID AND
            ffm1.MANIFEST_TYPE = 'O')
    WHERE
      NOT EXISTS
        (SELECT
           1
         FROM
           ff_f_consignment_manifested ffcm2
           JOIN ff_f_manifest ffm12 ON ffm12.manifest_id = ffcm2.manifest_id
         WHERE
           (ffcm.consignment_id = ffcm2.consignment_id AND
            ffm12.MANIFEST_TYPE = ffm1.MANIFEST_TYPE AND
              ffm1.MANIFEST_ID < ffm12.MANIFEST_ID));
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    ROWCOUNT;
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
      'sp_mv_latest_in_out_manifest'
     ,'STEP 2 - POPULATE LATEST OUT MANIFEST DATA FOR EACH CONSIGNMENT'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------

  -- ----------------------------------------------------------------
  -- STEP 3 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN BRANCH
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ----------------------------------------------------------------
  SET _step = "STEP 3 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN BRANCH";
  TRUNCATE TABLE ff_mv_first_out_manifest_origin_branch;
  INSERT INTO
    ff_mv_first_out_manifest_origin_branch(
      CONSG_ID
     ,CONSIGNMENT_MANIFESTED_ID
     ,MANIFEST_ID
     ,MANIFEST_NO
     ,MANIFEST_DATE
     ,MANIFEST_TYPE
     ,OPERATING_OFFICE
     ,MANIFEST_LOAD_CONTENT
     ,MANIFEST_PROCESS_CODE)
    SELECT
      ffc.CONSG_ID
     ,ffcm.CONSIGNMENT_MANIFESTED_ID
     ,ffcm.MANIFEST_ID
     ,ffm.MANIFEST_NO
     ,ffm.MANIFEST_DATE
     ,ffm.MANIFEST_TYPE
     ,ffm.OPERATING_OFFICE
     ,ffm.MANIFEST_LOAD_CONTENT
     ,ffm.MANIFEST_PROCESS_CODE
    FROM
      ff_f_consignment ffc
      LEFT JOIN ff_f_consignment_manifested ffcm
        ON (ffcm.CONSIGNMENT_ID = ffc.CONSG_ID)
      LEFT JOIN ff_f_manifest ffm
        ON (ffm.MANIFEST_TYPE = 'O' AND
            ffm.MANIFEST_PROCESS_CODE IN ('OPKT'
                                         ,'OBPC') AND
            ffm.OPERATING_OFFICE = ffc.ORG_OFF AND
            ffm.MANIFEST_ID = ffcm.MANIFEST_ID)
    WHERE
      ffm.manifest_no IS NOT NULL AND
      NOT EXISTS
        (SELECT
           1
         FROM
           ff_f_consignment_manifested ffcm2
           JOIN ff_f_manifest ffm12 ON ffm12.manifest_id = ffcm2.manifest_id
         WHERE
           (ffcm.consignment_id = ffcm2.consignment_id AND
            ffm12.MANIFEST_TYPE = ffm.MANIFEST_TYPE AND
      		ffm12.OPERATING_OFFICE = ffm.OPERATING_OFFICE AND
      		ffm.MANIFEST_ID < ffm12.MANIFEST_ID));
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    ROWCOUNT;
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
      'sp_mv_latest_in_out_manifest'
     ,'STEP 3 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN BRANCH'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------


  -- ----------------------------------------------------------------
  -- STEP 4 - POPULATE RTO MANIFEST HAPPENED AT DESTINATION HUB
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ----------------------------------------------------------------
  SET _step = "STEP 4 - POPULATE RTO MANIFEST HAPPENED AT DESTINATION HUB";
  TRUNCATE TABLE ff_mv_rto_manifest_origin_branch;
  INSERT INTO
    ff_mv_rto_manifest_origin_branch(
      CONSG_ID
     ,CONSIGNMENT_MANIFESTED_ID
     ,MANIFEST_ID
     ,MANIFEST_NO
     ,MANIFEST_DATE
     ,MANIFEST_TYPE
     ,OPERATING_OFFICE
     ,MANIFEST_LOAD_CONTENT
     ,MANIFEST_PROCESS_CODE)
    SELECT
      ffc.CONSG_ID
     ,ffcm.CONSIGNMENT_MANIFESTED_ID
     ,ffcm.MANIFEST_ID
     ,ffm.MANIFEST_NO
     ,ffm.MANIFEST_DATE
     ,ffm.MANIFEST_TYPE
     ,ffm.OPERATING_OFFICE
     ,ffm.MANIFEST_LOAD_CONTENT
     ,ffm.MANIFEST_PROCESS_CODE
    FROM
      ff_f_consignment ffc
      LEFT JOIN ff_f_consignment_manifested ffcm
        ON (ffcm.CONSIGNMENT_ID = ffc.CONSG_ID)
      LEFT JOIN ff_f_manifest ffm
        ON (ffm.MANIFEST_TYPE = 'R' AND
            ffm.MANIFEST_PROCESS_CODE IN ('RTOH') AND
            ffm.MANIFEST_ID = ffcm.MANIFEST_ID)
    WHERE
      ffm.manifest_no IS NOT NULL AND
      NOT EXISTS
        (SELECT
           1
         FROM
           ff_f_consignment_manifested ffcm2
           JOIN ff_f_manifest ffm12 ON ffm12.manifest_id = ffcm2.manifest_id
         WHERE
           (ffcm.consignment_id = ffcm2.consignment_id AND
            ffm12.MANIFEST_TYPE = ffm.MANIFEST_TYPE AND
            ffm.MANIFEST_ID < ffm12.MANIFEST_ID));
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    ROWCOUNT;
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
      'sp_mv_latest_in_out_manifest'
     ,'STEP 4 - POPULATE RTO MANIFEST HAPPENED AT DESTINATION HUB'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------


  -- ----------------------------------------------------------------
  -- STEP 5 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN HUB
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ----------------------------------------------------------------
  SET _step = "STEP 5 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN HUB";
  TRUNCATE TABLE ff_mv_first_out_manifest_origin_hub;
  INSERT INTO
    ff_mv_first_out_manifest_origin_hub(
      CONSG_ID
     ,CONSIGNMENT_MANIFESTED_ID
     ,MANIFEST_ID
     ,MANIFEST_NO
     ,MANIFEST_DATE
     ,MANIFEST_TYPE
     ,OPERATING_OFFICE
     ,MANIFEST_LOAD_CONTENT
     ,MANIFEST_PROCESS_CODE
  	 ,LOAD_LOT_ID
  	 ,ORIGIN_OFFICE
  	 ,DESTINATION_OFFICE)
    SELECT
      ffc.CONSG_ID
     ,ffcm.CONSIGNMENT_MANIFESTED_ID
     ,ffcm.MANIFEST_ID
     ,ffm.MANIFEST_NO
     ,ffm.MANIFEST_DATE
     ,ffm.MANIFEST_TYPE
     ,ffm.OPERATING_OFFICE
     ,ffm.MANIFEST_LOAD_CONTENT
     ,ffm.MANIFEST_PROCESS_CODE
  	 ,ffm.LOAD_LOT_ID
  	 ,ffm.ORIGIN_OFFICE
  	 ,ffm.DESTINATION_OFFICE
	 FROM
      ff_f_consignment ffc
      JOIN ff_dim_geography fdg
        ON fdg.OFFICE_ID = ffc.ORG_OFF
      LEFT JOIN ff_f_consignment_manifested ffcm
        ON (ffcm.CONSIGNMENT_ID = ffc.CONSG_ID)
      LEFT JOIN ff_f_manifest ffm
        ON (ffm.MANIFEST_TYPE = 'O' AND
            ffm.MANIFEST_PROCESS_CODE IN ('OPKT'
                                         ,'OBPC') AND
            ffm.OPERATING_OFFICE = IF(fdg.OFFICE_TYPE_CODE = 'HO', fdg.OFFICE_ID, fdg.REPORTING_HUB) AND
            ffm.MANIFEST_ID = ffcm.MANIFEST_ID)
    WHERE
      ffm.manifest_no IS NOT NULL AND
      NOT EXISTS
        (SELECT
           1
         FROM
           ff_f_consignment_manifested ffcm2
           JOIN ff_f_manifest ffm12 ON ffm12.manifest_id = ffcm2.manifest_id
         WHERE
           (ffcm.consignment_id = ffcm2.consignment_id AND
            ffm12.MANIFEST_TYPE = ffm.MANIFEST_TYPE AND
            ffm12.OPERATING_OFFICE = ffm.OPERATING_OFFICE AND
      		ffm.MANIFEST_ID < ffm12.MANIFEST_ID));
			
  -- -------------------------------------------------------------------------------------------------
  SELECT
    row_count()
  INTO
    ROWCOUNT;
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
      'sp_mv_latest_in_out_manifest'
     ,'STEP 5 - POPULATE FIRST OUT MANIFEST DATA AT ORIGIN HUB'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------
  
  COMMIT;

END;
