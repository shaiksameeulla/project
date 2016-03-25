DROP PROCEDURE IF EXISTS sp_insert_dimension_data;
CREATE PROCEDURE `sp_insert_dimension_data`()
BEGIN

CALL sp_scd_ff_dim_product ();

CALL sp_scd_ff_dim_geography ();

call sp_scd_ff_dim_geography_pc ();

CALL sp_scd_ff_dim_customer ();

END;