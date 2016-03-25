DROP PROCEDURE IF EXISTS sp_scd_ff_dim_geography_pc;
CREATE PROCEDURE `sp_scd_ff_dim_geography_pc`()
BEGIN

  UPDATE
    ff_dim_geography_pc dg_t
   ,(SELECT
       pc.PINCODE_ID
      ,pc.PINCODE
      ,c.CITY_ID
      ,c.CITY_CODE
      ,c.CITY_NAME
      ,s.STATE_ID
      ,s.STATE_CODE
      ,s.STATE_NAME
      ,r.REGION_ID
      ,r.REGION_CODE
      ,r.REGION_NAME
      ,z.ZONE_ID
      ,z.ZONE_CODE
      ,z.ZONE_NAME
     FROM
       ff_d_pincode pc
       JOIN ff_d_city c ON c.city_id = pc.city_id
       JOIN ff_d_state s ON c.state = s.state_id
       JOIN ff_d_region r ON c.region = r.region_id
       JOIN ff_d_zone z ON r.zone = z.zone_id) dg_s
  SET
    dg_t.PINCODE        = dg_s.PINCODE
   ,dg_t.CITY_ID        = dg_s.CITY_ID
   ,dg_t.CITY_CODE      = dg_s.CITY_CODE
   ,dg_t.CITY_NAME      = dg_s.CITY_NAME
   ,dg_t.STATE_ID       = dg_s.STATE_ID
   ,dg_t.STATE_CODE     = dg_s.STATE_CODE
   ,dg_t.STATE_NAME     = dg_s.STATE_NAME
   ,dg_t.REGION_ID      = dg_s.REGION_ID
   ,dg_t.REGION_CODE    = dg_s.REGION_CODE
   ,dg_t.REGION_NAME    = dg_s.REGION_NAME
   ,dg_t.ZONE_ID        = dg_s.ZONE_ID
   ,dg_t.ZONE_CODE      = dg_s.ZONE_CODE
   ,dg_t.ZONE_NAME      = dg_s.ZONE_NAME
   ,dg_t.LAST_UPDT_DATE = NOW()
  WHERE
    dg_t.PINCODE_ID = dg_s.PINCODE_ID;

  COMMIT;


  INSERT INTO
    ff_dim_geography_pc
    SELECT
      X.*
    FROM
      (SELECT
         fn_seq_gen('n_geo_skey_pc') AS N_GEO_SKEY_PC
        ,pc.PINCODE_ID
        ,pc.PINCODE
        ,c.CITY_ID
        ,c.CITY_CODE
        ,c.CITY_NAME
        ,s.STATE_ID
        ,s.STATE_CODE
        ,s.STATE_NAME
        ,r.REGION_ID
        ,r.REGION_CODE
        ,r.REGION_NAME
        ,z.ZONE_ID
        ,z.ZONE_CODE
        ,z.ZONE_NAME
        ,NOW() AS LAST_UPDT_DATE
       FROM
         ff_d_pincode pc
         JOIN ff_d_city c ON c.city_id = pc.city_id
         JOIN ff_d_state s ON c.state = s.state_id
         JOIN ff_d_region r ON c.region = r.region_id
         JOIN ff_d_zone z ON r.zone = z.zone_id) X
     ,(SELECT
         a.PINCODE_ID
       FROM
         ff_d_pincode a
         LEFT OUTER JOIN ff_dim_geography_pc b ON a.PINCODE = b.PINCODE
       WHERE
         b.PINCODE IS NULL AND
         a.PINCODE IS NOT NULL) Y
    WHERE
      X.PINCODE_ID = Y.PINCODE_ID;

  COMMIT;

END;
