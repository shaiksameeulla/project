update ff_f_pickup_runsheet_detail set dt_from_opsman ='N';

ALTER TABLE corpudaan_master_data.ff_f_pickup_runsheet_detail
DROP FOREIGN KEY FK_ff_f_pick_run_dtl_1,
DROP FOREIGN KEY FK_ff_f_pick_run_dtl_2,
CHANGE DT_FROM_OPSMAN DUPLICATE_CUST_ROW ENUM('Y', 'N') NOT NULL DEFAULT 'N' COMMENT 'To identify duplicate customer row';
ALTER TABLE corpudaan_master_data.ff_f_pickup_runsheet_detail
ADD CONSTRAINT FK_ff_f_pick_run_dtl_1 FOREIGN KEY (RUNSHEET_HEADER_ID) REFERENCES corpudaan_master_data.ff_f_pickup_runsheet_header (RUNSHEET_HEADER_ID) ON UPDATE CASCADE ON DELETE NO ACTION,
ADD CONSTRAINT FK_ff_f_pick_run_dtl_2 FOREIGN KEY (PICKUP_ASSIGNMENT_DETAIL_ID) REFERENCES corpudaan_master_data.ff_f_pickup_assignment_detail (PICKUP_ASSIGNMENT_DETAIL_ID) ON UPDATE CASCADE ON DELETE NO ACTION;
