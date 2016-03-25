/*DML requested by Sami 09/12/2013 */

delete from ff_d_configurable_params where param_name = 'STOCK_ISSUE_EXPIRY_DAYS';

insert into `ff_d_configurable_params` values 
(74,'STOCK_ISSUE_BA_EXPIRY_DAYS','180','N'),
(75,'STOCK_ISSUE_FR_EXPIRY_DAYS','90','N'),
(76,'SAP_INTGRATION_MAX_DATA_CHECK','1000','N'),
(77,'BCUN_FILE_RE_PROCESSING_RECEIVER_EMAIL_ID','UdaanDS@firstflight.net','N');