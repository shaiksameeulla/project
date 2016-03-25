Please check below initial data is present.

/*Initial Data*/

insert into `ff_d_user` values 
(1,'1','EMPADMIN','E','N','Y',0,NULL,NULL,'2013-09-20',NULL,'2013-09-20','Y'),
(2,'OPSMAN','OPSMAN','E','N','Y',0,NULL,1,'2013-11-11',NULL,'2013-11-11','Y'),
(3,'SAP_USER','SAP_USER','E','N','Y',0,NULL,1,'2013-11-11',NULL,'2013-11-11','Y'),
(4,'sysuser','sysuser','E','N','Y',0,NULL,1,'2013-11-11',NULL,'2013-11-11','Y');


insert into `ff_d_office` values 
(1,'1000','Corporate Office',1,'15/16, National House Saki Vihar Road  Chandivili Junction Andheri (East)','Maharashtra','Mumbai','39576666','ffcl@firstflight.net',NULL,NULL,NULL,NULL,517,3,'2014-04-11',3,'2014-04-11',NULL,'Y',NULL,'400072',NULL),
(2,'1001','Virtual Plant',5,NULL,'Maharashtra',NULL,NULL,NULL,NULL,1,NULL,NULL,517,3,'2014-04-11',3,'2014-04-11',NULL,'Y',NULL,NULL,NULL);


insert into `ff_d_employee` values 
(1,'01000','Corporate Office',' ',NULL,NULL,NULL,NULL,517,1,'ffcl@firstflight.net',3,'2014-04-11',3,'2014-04-11','Y','Y','A'),
(2,'01001','Virtual Plant',' ',NULL,NULL,NULL,NULL,517,2,NULL,3,'2014-04-11',3,'2014-04-11','Y','Y','A');


/*opsman - initial data*/

insert into `ff_d_consigner_consignee` values 
(1,'OPSMAN Consignee','OPSMAN Consignee FN','OPSMAN Consignee LN','CE',NULL,NULL,NULL,NULL,NULL,NULL,'2013-12-24',NULL,'2013-12-24',NULL,NULL,'N'),
(2,'OPSMAN Consignor','OPSMAN Consignor FN ','OPSMAN Consignor LN','CO',NULL,NULL,NULL,NULL,NULL,NULL,'2013-12-24',NULL,'2013-12-24',NULL,NULL,'N');

insert into `ff_d_identity_proof_type` values 
(9,'ID009','Others','NULL','Y','Y');

insert into `ff_d_relation` values 
(13,'R013','Others','Y','Y');


insert into `ff_d_vendor` values 
(1,'OPSMAN','OPSMAN-UDAAN Vendor','OPSMAN-UDAAN Vendor',4,'1111111111','1111111111',NULL,'Company',NULL,'OPSMAN-UDAAN Vendor',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y','Corporate Office','400072');

insert into `ff_d_route` values 
(1,1,2,NULL,'Y');

insert into `ff_d_vehicle` (`VEHICLE_ID`, `VEHICLE_TYPE`, `REG_NUMBER`) values('1','Tempo','Udaan_Vehicle');
insert into `ff_d_train` (`TRAIN_ID`,  `TRAIN_NAME`, `TRAIN_NUMBER`) values('1','Udaan_Train','Udaan_Train');
insert into `ff_d_flight` (`FLIGHT_ID`, `FLIGHT_NUMBER`, `AIRLINE_NAME`) values('1','Udaan_Flight','Udaan_Flight');


insert into `ff_f_serviced_by` values 
(1,1,NULL,'V',1,'Y','N','R','N','N',2, '2014-04-01',2, '2014-04-01'),
(2,1,NULL,'V',7,'Y','N','R','N','N', 2,'2014-04-01',2, '2014-04-01'),
(3,1,NULL,'V',4,'Y','N','R','N','N', 2, '2014-04-01',2, '2014-04-01'),
(4,NULL,2,'E',6,'Y','N','R','N','R', 2, '2014-04-01',2, '2014-04-01');

insert into `ff_f_transport` values 
(1,1,1,NULL,NULL,'N','N','R','N','N', 2, '2014-04-01',2, '2014-04-01'),
(2,2,NULL,1,NULL,'N','N','R','N','N', 2,'2014-04-01',2, '2014-04-01'),
(3,3,NULL,NULL,1,'N','N','R','N','N', 2,'2014-04-01',2, '2014-04-01');

insert into `ff_f_trip` values 
(1,1,1, '11:50','08:00','Y','N','N','N','Y','N', 2, '2014-04-01',2, '2014-04-01'),
(2,1,2, '11:50', '08:00','Y','N','N','N','Y','N', 2, '2014-04-01',2, '2014-04-01'),
(3,1,3, '11:50', '08:00','Y','N','N','N','Y','N', 2,'2014-04-01',2, '2014-04-01');

insert into `ff_f_trip_serviced_by` values 
(1,1,1,1,'Y','N','N','Y-Y-Y-Y-Y-Y-Y','Y', '2014-04-01', '2014-04-02','N','Y','N', 2, '2014-04-01',2, '2014-04-01'),
(2,2,2,2,'Y','N','N','Y-Y-Y-Y-Y-Y-Y','Y', '2014-04-01', '2014-04-02','N','Y','N', 2, '2014-04-01',2, '2014-04-01'),
(3,3,3,3,'Y','N','N','Y-Y-Y-Y-Y-Y-Y','Y', '2014-04-01', '2014-04-02','N','Y','N', 2,'2014-04-01',2, '2014-04-01'),
(4,4,3,3,'Y','N','N','Y-Y-Y-Y-Y-Y-Y','Y', '2014-04-01','2020-04-01','N','Y','R', 2, '2014-04-01',2, '2014-04-01');




