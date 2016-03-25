/*1. Change requested by Chandrakant Bhure */

CREATE TABLE ff_f_sap_vendor (               
ID int(11)  	NOT NULL AUTO_INCREMENT PRIMARY KEY, 
VENDOR_CODE 	varchar(10) DEFAULT NULL,
FIRST_NAME  	varchar(50) DEFAULT NULL,
LAST_NAME	varchar(50) DEFAULT NULL,
VENDOR_TYPE	varchar(50) DEFAULT NULL,
ADDRESS		varchar(250) DEFAULT NULL,
COMPANY_TYPE	varchar(50) DEFAULT NULL,
EMAIL		varchar(50) DEFAULT NULL,
FAX		varchar(25) DEFAULT NULL,
BUSINESS_NAME	varchar(150) DEFAULT NULL,
MOBILE		varchar(25) DEFAULT NULL,
PHONE		varchar(25) DEFAULT NULL,
PINCODE		varchar(6) DEFAULT NULL,
SERVICE		varchar(50) DEFAULT NULL,
RHO_CODE	varchar(50) DEFAULT NULL,
CREATED_BY 	int(11) DEFAULT NULL,
CREATED_DATE 	datetime DEFAULT NULL,
UPDATE_BY 	int(11) DEFAULT NULL,
UPDATE_DATE 	datetime DEFAULT NULL,
DT_SAP_INBOUND  enum('N','C'),
SAP_TIMESTAMP	datetime); 

/*2. Change requested by Kaustubh */
alter table ff_f_billing_consignment_summary add DESTINATION_OFFICE VARCHAR(20);