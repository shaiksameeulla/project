DROP PROCEDURE IF EXISTS sp_mv_latest_delivery;
CREATE PROCEDURE sp_mv_latest_delivery ()
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

  -- ------------------------------------------------------------------
  -- STEP 1 - POPULATE LATEST CONSIGNMENT DELIVERY DATA
  -- ------------------------------------------------------------------
  SELECT
    CURTIME()
  INTO
    START_TIME;
  -- ------------------------------------------------------------------
  SET _step = "STEP 1 - POPULATE LATEST CONSIGNMENT DELIVERY DATA";
  TRUNCATE TABLE ff_mv_latest_delivery;
  INSERT INTO
  ff_mv_latest_delivery(
    CONSG_ID
   ,DELIVERY_DETAIL_ID
   ,DELIVERY_TIME
   ,DELIVERY_STATUS
   ,RECORD_STATUS
   ,REASON_ID
   ,DELIVERY_ID
   ,LOAD_NO
   ,TRANS_MODIFIED_DATE_TIME)
  SELECT
    ffdd1.CONSIGNMENT_ID
   ,ffdd1.DELIVERY_DETAIL_ID
   ,ffdd1.DELIVERY_TIME
   ,ffdd1.DELIVERY_STATUS
   ,ffdd1.RECORD_STATUS
   ,ffdd1.REASON_ID
   ,ffd.DELIVERY_ID
   ,ffd.LOAD_NO
   ,ffdd1.TRANS_MODIFIED_DATE_TIME
  FROM
    ff_f_consignment ffc
    JOIN ff_f_delivery_dtls ffdd1 ON ffdd1.CONSIGNMENT_ID = ffc.CONSG_ID
    LEFT OUTER JOIN ff_f_delivery_dtls ffdd2
      ON (ffdd1.CONSIGNMENT_ID = ffdd2.CONSIGNMENT_ID AND
          (ffdd1.TRANS_MODIFIED_DATE_TIME < ffdd2.TRANS_MODIFIED_DATE_TIME OR
           (ffdd1.TRANS_MODIFIED_DATE_TIME = ffdd2.TRANS_MODIFIED_DATE_TIME AND
            ffdd1.DELIVERY_DETAIL_ID > ffdd2.DELIVERY_DETAIL_ID)))
    JOIN ff_f_delivery ffd ON ffd.DELIVERY_ID = ffdd1.DELIVERY_ID
  WHERE
    ffdd2.TRANS_MODIFIED_DATE_TIME IS NULL;
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
      'sp_mv_latest_delivery'
     ,'STEP 1 - POPULATE LATEST CONSIGNMENT DELIVERY DATA'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  -- -------------------------------------------------------------------------------------------------

  COMMIT;

END;
