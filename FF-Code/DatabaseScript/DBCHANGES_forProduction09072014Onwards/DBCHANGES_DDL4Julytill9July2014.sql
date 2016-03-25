/*DATE:09/07/2014*/
/*Himal*/
alter table ff_f_service_request_complaint
drop FOREIGN KEY FK_ff_f_service_request_complaint3,
drop  KEY FK_ff_f_service_request_complaint3,
modify STATUS enum('CLAIM','LEGAL','SETTLED');
