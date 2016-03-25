ALTER TABLE `ff_f_stock_cancellation`
ADD INDEX `idx_start_end_srl_no` (`START_SI_NUMBER`, `END_SI_NUMBER`, `CANCELLATION_NUMBER`) USING BTREE;
