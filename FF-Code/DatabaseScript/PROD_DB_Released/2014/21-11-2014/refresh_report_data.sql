DELIMITER |

CREATE EVENT refresh_report_data
    ON SCHEDULE
      EVERY 24 HOUR
      STARTS CURRENT_TIMESTAMP + INTERVAL 10 HOUR
	DISABLE
    COMMENT 'Refreshes Report Data Structure !'
    DO
      BEGIN
        CALL sp_insert_dimension_data();
        CALL sp_insert_fact_data();
        CALL sp_mv_branch_pincode_servicability();
        CALL sp_mv_latest_delivery();
        CALL sp_mv_latest_in_out_manifest();
        CALL sp_brr_hit_ratio_report_data();
      END|

DELIMITER ;