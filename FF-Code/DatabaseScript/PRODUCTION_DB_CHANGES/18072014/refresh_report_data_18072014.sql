USE information_schema;
SET GLOBAL event_scheduler = ON;
USE CorpUDAAN;

DELIMITER $$ 
CREATE EVENT  IF NOT EXISTS refresh_report_data 
ON SCHEDULE EVERY 1 DAY
STARTS '2014-07-19 00:00:00'
COMMENT 'Refresh Report Data'
DO 
 BEGIN
  CALL sp_insert_dimension_data();
  CALL sp_insert_fact_data();
  /*COMMENT 'Below procedure added in event on 11 July,2014'*/
  CALL sp_brr_hit_ratio_report_data();	
 END$$
DELIMITER ;

