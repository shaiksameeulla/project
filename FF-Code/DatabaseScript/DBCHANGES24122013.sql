/*1.Chandrakant Bhure */

alter table ff_f_sap_liability_payment
modify CHEQUE_BANK_NAME varchar(100) DEFAULT NULL;



/*2.Himal */
alter table ff_f_expense add REMARKS varchar(100) COMMENT 'Remarks of particular transaction specially expense type office';

/*3. ketan Bhadekar */
set foreign_key_checks=0;
Delete from ff_d_consigner_consignee where party_id in(1,2);
insert into `ff_d_consigner_consignee` values 
(1,'OPSMAN Consignee','OPSMAN Consignee FN','OPSMAN Consignee LN','CE',NULL,NULL,NULL,NULL,NULL,NULL,'2013-08-19',NULL,'2013-08-19',NULL,NULL,'N'),
(2,'OPSMAN Consignor','OPSMAN Consignor FN ','OPSMAN Consignor LN','CO',NULL,NULL,NULL,NULL,NULL,NULL,'2013-08-19',NULL,'2013-08-19',NULL,NULL,'N');
set foreign_key_checks=1;

/*3.Pravin Meher */
insert into ff_d_pincode_product_serviceability values 
(31187,4088,NULL,NULL,NULL,'N',1),
(31188,4088,NULL,NULL,NULL,'N',2),
(31189,4088,'14:00','B',217,'N',3),
(31190,4088,'14:00','A',200,'N',3),
(31191,4088,'14:00','B',200,'N',3),
(31192,4088,'14:00','A',203,'N',3),
(31193,4088,'14:00','B',203,'N',3),
(31194,4088,'14:00','A',51,'N',3),
(31195,4088,'14:00','B',51,'N',3),
(31196,4088,'14:00','A',297,'N',3),
(31197,4088,'14:00','B',297,'N',3),
(31198,4088,NULL,NULL,217,'N',5),
(31199,4088,NULL,NULL,203,'N',5),
(31200,4088,NULL,NULL,129,'N',5),
(31201,4088,NULL,NULL,41,'N',5),
(31202,4088,NULL,NULL,200,'N',5),
(31203,4088,NULL,NULL,297,'N',5),
(31204,5642,NULL,NULL,NULL,'N',1),
(31205,5642,NULL,NULL,NULL,'N',2),
(31206,5642,'14:00','A',217,'N',3),
(31207,5642,'14:00','B',217,'N',3),
(31208,5642,'14:00','A',41,'N',3),
(31209,5642,'14:00','B',41,'N',3),
(31210,5642,'14:00','A',200,'N',3),
(31211,5642,'14:00','B',200,'N',3),
(31212,5642,'14:00','A',129,'N',3),
(31213,5642,'14:00','B',129,'N',3),
(31214,5642,'14:00','A',203,'N',3),
(31215,5642,'14:00','B',203,'N',3),
(31216,5642,'14:00','A',51,'N',3),
(31217,5642,'14:00','B',51,'N',3),
(31218,5642,'14:00','A',297,'N',3),
(31219,5642,'14:00','B',297,'N',3),
(31220,5642,NULL,NULL,217,'N',5),
(31221,5642,NULL,NULL,203,'N',5),
(31222,5642,NULL,NULL,129,'N',5),
(31223,5642,NULL,NULL,41,'N',5),
(31224,5642,NULL,NULL,200,'N',5),
(31225,5642,NULL,NULL,297,'N',5),
(31226,5780,NULL,NULL,NULL,'N',1),
(31227,5780,NULL,NULL,NULL,'N',2),
(31228,5780,'14:00','A',217,'N',3),
(31229,5780,'14:00','B',217,'N',3),
(31230,5780,'14:00','A',41,'N',3),
(31231,5780,'14:00','B',41,'N',3),
(31232,5780,'14:00','A',200,'N',3),
(31233,5780,'14:00','B',200,'N',3),
(31234,5780,'14:00','A',129,'N',3),
(31235,5780,'14:00','B',129,'N',3),
(31236,5780,'14:00','A',203,'N',3),
(31237,5780,'14:00','B',203,'N',3),
(31238,5780,'14:00','A',51,'N',3),
(31239,5780,'14:00','B',51,'N',3),
(31240,5780,'14:00','A',297,'N',3),
(31241,5780,'14:00','B',297,'N',3),
(31242,5780,NULL,NULL,217,'N',5),
(31243,5780,NULL,NULL,203,'N',5),
(31244,5780,NULL,NULL,129,'N',5),
(31245,5780,NULL,NULL,41,'N',5),
(31246,5780,NULL,NULL,200,'N',5),
(31247,5780,NULL,NULL,297,'N',5);





/*DML Change requested by Aasma Executed in client's Central DB udaan_plat today*/
insert into ff_d_app_menu values 
(294,NULL,13,'L','Billing',NULL,'N','udaan-config-admin'),
(295,294,1,'A','Bill Printing',2,'N','udaan-config-admin'),
(296,294,2,'A','InvoiceRunSheetPrinting',1,'N','udaan-config-admin'),
(297,294,3,'A','InvoiceRunSheetUpdate',3,'N','udaan-config-admin');

