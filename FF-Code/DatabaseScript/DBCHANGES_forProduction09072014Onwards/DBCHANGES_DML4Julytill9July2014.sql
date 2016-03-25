/*DATE:04/07/2014*/
/*Sami*/

insert into ff_d_configurable_params values (103,'COMPLAINT_PRODUCT_SERIES','M,P,T,A,S','N');

update ff_d_configurable_params set PARAM_VALUE = 'M,P,T,A,S,N' where PARAM_ID = 103 and PARAM_NAME = 'COMPLAINT_PRODUCT_SERIES';


/*DATE:08/07/2014*/
/*Shahnaz*/

update ff_d_process
set TRACKING_TXT_TMPL_MNF= 'Branched Out (Manifest No :{manifestNo}) from {originOff} of {orgCity} to {destOff} of {destCity}'
where PROCESS_CODE like 'BOUT';

update ff_d_process
set TRACKING_TXT_TMPL_MNF= 'Branched In (Manifest No :{manifestNo}) from {destOff} of {destCity} to {originOff} of {orgCity}'
where PROCESS_CODE like 'BRIN';

update ff_d_process
set TRACKING_TXT_TMPL_MNF = 'Misroute No ({manifestNo}) forwarded from {originOff} of {orgCity} to {destOff} of {destCity}'
where PROCESS_CODE like 'MSRT';


/*Himal*/

insert into `ff_d_std_type` values 
(284,'SETTLED','SETTLED','COMPLAINTS','CRTLS_CMPLTS_STATUS','A','Y'),
(285,'LEGAL','LEGAL','COMPLAINTS','CRTLS_CMPLTS_STATUS','A','Y'),
(286,'CLAIM','CLAIM','COMPLAINTS','CRTLS_CMPLTS_STATUS','A','Y');
