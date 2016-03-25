DROP PROCEDURE IF EXISTS CorpUDAAN.sp_mv_branch_pincode_servicability;
CREATE PROCEDURE CorpUDAAN.`sp_mv_branch_pincode_servicability`()
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

  
  
  
  
  
  
  
  SELECT
    CURTIME()
  INTO
    START_TIME;
  
  SET _step = "STEP 1 - POPULATE BRANCH PINCODE SERVICABILITY";
  TRUNCATE TABLE ff_mv_branch_pincode_servicability;
  INSERT INTO
  ff_mv_branch_pincode_servicability(
    OFFICE_ID
   ,OFFICE_CODE
   ,OFFICE_TYPE_ID
   ,OFFICE_TYPE_CODE
   ,PINCODE_ID
   ,PINCODE
   ,CITY_ID
   ,CITY_CODE
   ,CITY_NAME
   ,STATE_ID
   ,STATE_CODE
   ,STATE_NAME
   ,REGION_ID
   ,REGION_CODE
   ,REGION_NAME
   ,ZONE_ID
   ,ZONE_CODE
   ,ZONE_NAME)
  SELECT
    fdo.OFFICE_ID
   ,fdo.OFFICE_CODE
   ,fdo.OFFICE_TYPE_ID
   ,fdot.OFFICE_TYPE_CODE
   ,fdp.PINCODE_ID
   ,fdp.PINCODE
   ,fdg.CITY_ID
   ,fdg.CITY_CODE
   ,fdg.CITY_NAME
   ,fdg.STATE_ID
   ,fdg.STATE_CODE
   ,fdg.STATE_NAME
   ,fdg.REGION_ID
   ,fdg.REGION_CODE
   ,fdg.REGION_NAME
   ,fdg.ZONE_ID
   ,fdg.ZONE_CODE
   ,fdg.ZONE_NAME
  FROM
    ff_d_office fdo
    JOIN ff_d_branch_pincode_serviceablity fdbps
      ON fdbps.OFFICE_ID = fdo.OFFICE_ID
    JOIN ff_d_pincode fdp ON fdp.PINCODE_ID = fdbps.PINCODE_ID
    JOIN ff_d_office_type fdot ON fdot.OFFICE_TYPE_ID = fdo.OFFICE_TYPE_ID
    JOIN ff_dim_geography fdg ON fdg.OFFICE_ID = fdo.OFFICE_ID
  WHERE
    fdot.OFFICE_TYPE_CODE = 'BO'
  UNION
  SELECT
    fdo_hub.OFFICE_ID
   ,fdo_hub.OFFICE_CODE
   ,fdo_hub.OFFICE_TYPE_ID
   ,fdot_hub.OFFICE_TYPE_CODE
   ,fdp_hub.PINCODE_ID
   ,fdp_hub.PINCODE
   ,fdg.CITY_ID
   ,fdg.CITY_CODE
   ,fdg.CITY_NAME
   ,fdg.STATE_ID
   ,fdg.STATE_CODE
   ,fdg.STATE_NAME
   ,fdg.REGION_ID
   ,fdg.REGION_CODE
   ,fdg.REGION_NAME
   ,fdg.ZONE_ID
   ,fdg.ZONE_CODE
   ,fdg.ZONE_NAME
  FROM
    ff_d_office fdo_hub
    JOIN ff_d_office_type fdot_hub
      ON fdot_hub.OFFICE_TYPE_ID = fdo_hub.OFFICE_TYPE_ID
    JOIN ff_d_office fdo_rb ON fdo_rb.REPORTING_HUB = fdo_hub.OFFICE_ID
    JOIN ff_d_branch_pincode_serviceablity fdbps_hub
      ON fdbps_hub.OFFICE_ID = fdo_rb.OFFICE_ID
    JOIN ff_d_pincode fdp_hub ON fdp_hub.PINCODE_ID = fdbps_hub.PINCODE_ID
    JOIN ff_dim_geography fdg ON fdg.OFFICE_ID = fdo_hub.OFFICE_ID
  WHERE
    fdot_hub.OFFICE_TYPE_CODE = 'HO'
  UNION
  SELECT
    fdo.OFFICE_ID
   ,fdo.OFFICE_CODE
   ,fdo.OFFICE_TYPE_ID
   ,fdot.OFFICE_TYPE_CODE
   ,fdp.PINCODE_ID
   ,fdp.PINCODE
   ,fdg.CITY_ID
   ,fdg.CITY_CODE
   ,fdg.CITY_NAME
   ,fdg.STATE_ID
   ,fdg.STATE_CODE
   ,fdg.STATE_NAME
   ,fdg.REGION_ID
   ,fdg.REGION_CODE
   ,fdg.REGION_NAME
   ,fdg.ZONE_ID
   ,fdg.ZONE_CODE
   ,fdg.ZONE_NAME
  FROM
    ff_d_office fdo
    JOIN ff_dim_geography fdg ON fdg.OFFICE_ID = fdo.OFFICE_ID
    JOIN ff_d_pincode fdp ON fdp.CITY_ID = fdg.CITY_ID
    JOIN ff_d_office_type fdot ON fdot.OFFICE_TYPE_ID = fdo.OFFICE_TYPE_ID
  WHERE
    fdot.OFFICE_TYPE_CODE = 'HO'
  UNION
  SELECT
    fdo_tpt.OFFICE_ID
   ,fdo_tpt.OFFICE_CODE
   ,fdo_tpt.OFFICE_TYPE_ID
   ,fdot_tpt.OFFICE_TYPE_CODE
   ,fdp_tpt.PINCODE_ID
   ,fdp_tpt.PINCODE
   ,fdg.CITY_ID
   ,fdg.CITY_CODE
   ,fdg.CITY_NAME
   ,fdg.STATE_ID
   ,fdg.STATE_CODE
   ,fdg.STATE_NAME
   ,fdg.REGION_ID
   ,fdg.REGION_CODE
   ,fdg.REGION_NAME
   ,fdg.ZONE_ID
   ,fdg.ZONE_CODE
   ,fdg.ZONE_NAME
  FROM
    ff_d_transshipment_route fdtr
    JOIN ff_d_office fdo_tpt ON fdo_tpt.CITY_ID = fdtr.TRANSSHIPMENT_CITY
    JOIN ff_d_office_type fdot_tpt
      ON fdot_tpt.OFFICE_TYPE_ID = fdo_tpt.OFFICE_TYPE_ID
    JOIN ff_d_pincode fdp_tpt ON fdp_tpt.CITY_ID = fdtr.SERVICED_CITY
    LEFT JOIN ff_d_office fdo_serviced
      ON fdo_serviced.CITY_ID = fdtr.SERVICED_CITY
    JOIN ff_dim_geography fdg ON fdg.OFFICE_ID = fdo_tpt.OFFICE_ID
  WHERE
    fdot_tpt.OFFICE_TYPE_CODE = 'HO' AND
    fdo_serviced.OFFICE_ID IS NULL;
  
  SELECT
    row_count()
  INTO
    ROWCOUNT;
  
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
      'sp_mv_branch_pincode_servicability'
     ,'STEP 1 - POPULATE BRANCH PINCODE SERVICABILITY'
     ,CURDATE()
     ,START_TIME
     ,CURTIME()
     ,ROWCOUNT);
  

  COMMIT;

END;
