/*1.Changes requested by Anwar */

/*Table structure for table ff_d_service_request_complaint_type */

CREATE TABLE ff_d_service_request_complaint_type (
  SERVICE_REQUEST_COMPLAINT_TYPE_ID int(11) NOT NULL AUTO_INCREMENT,
  COMPLAINT_TYPE_CODE varchar(10) DEFAULT NULL,
  COMPLAINT_TYPE_NAME varchar(30) DEFAULT NULL,
  COMPLAINT_TYPE_DESCRIPTION varchar(10) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_COMPLAINT_TYPE_ID)
) 

/*Table structure for table ff_d_service_request_cust_type */


CREATE TABLE ff_d_service_request_cust_type (
  SERVICE_REQUEST_CUSTOMER_TYPE_ID int(11) NOT NULL AUTO_INCREMENT,
  CUSTOMER_TYPE_CODE varchar(10) DEFAULT NULL,
  CUSTOMER_TYPE_NAME varchar(30) DEFAULT NULL,
  CUSTOMER_TYPE_DESCRIPTION varchar(10) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_CUSTOMER_TYPE_ID)
) 

/*Table structure for table ff_d_service_request_query_type */


CREATE TABLE ff_d_service_request_query_type (
  SERVICE_REQUEST_QUERY_TYPE_ID int(11) NOT NULL AUTO_INCREMENT,
  QUERY_TYPE_CODE varchar(10) DEFAULT NULL,
  QUERY_TYPE_NAME varchar(30) DEFAULT NULL,
  QUERY_TYPE_DESCRIPTION varchar(10) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_QUERY_TYPE_ID)
) 

/*Table structure for table ff_d_service_request_status */


CREATE TABLE ff_d_service_request_status (
  SERVICE_REQUEST_STATUS_ID int(11) NOT NULL AUTO_INCREMENT,
  STATUS_CODE varchar(10) DEFAULT NULL,
  STATUS_NAME varchar(30) DEFAULT NULL,
  STATUS_DESCRIPTION varchar(10) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_STATUS_ID)
) 

/*Table structure for table ff_f_service_request */

CREATE TABLE ff_f_service_request (
  SERVICE_REQUEST_ID int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_NO varchar(12) DEFAULT NULL,
  CALLER_NAME varchar(30) DEFAULT NULL,
  CALLER_PHONE varchar(11) DEFAULT NULL,
  CALLER_EMAIL varchar(50) DEFAULT NULL,
  SERVICE_CUSTOMER_TYPE int(11) DEFAULT NULL,
  QUERY_TYPE int(11) DEFAULT NULL,
  COMPLAINT_CATEGORY int(11) DEFAULT NULL,
  SERVICE_REQUEST_DETAILS_ID int(11) DEFAULT NULL,
  REMARK varchar(255) DEFAULT NULL,
  STATUS int(11) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  CREATED_BY int(11) DEFAULT NULL,
  UPDATE_DATE datetime DEFAULT NULL,
  UPDATE_BY int(11) DEFAULT NULL,
  BOOKING_NO varchar(12) DEFAULT NULL COMMENT 'CONSIGNMENT NO or BOOKING REFRENCE NO',
  NBOOKING_NO_TYPE enum('CN','RF') DEFAULT NULL,
  ORIGIN_CITY int(11) DEFAULT NULL,
  PRODUCT_TYPE int(11) DEFAULT NULL,
  DEST_PINCODE int(11) DEFAULT NULL,
  WEIGHT double(11,3) DEFAULT NULL,
  DOX_TYPE enum('DOX','PAR') DEFAULT NULL,
  CCMPLAINT_PAPER_TYPE int(11) DEFAULT NULL,
  ASSIGNED_TO int(11) DEFAULT NULL,
  SMS_TO_CONSIGNOR enum('Y','N') DEFAULT NULL,
  SMS_TO_CONSIGNEE enum('Y','N') DEFAULT NULL,
  EMAIL_TO_CALLER enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_ID),
  KEY FK_ff_f_service_request1 (SERVICE_CUSTOMER_TYPE),
  KEY FK_ff_f_service_request2 (QUERY_TYPE),
  KEY FK_ff_f_service_request3 (COMPLAINT_CATEGORY),
  KEY FK_ff_f_service_request4 (STATUS),
  KEY FK_ff_f_service_request5 (CREATED_BY),
  KEY FK_ff_f_service_request6 (UPDATE_BY),
  KEY FK_ff_f_service_request7 (ORIGIN_CITY),
  KEY FK_ff_f_service_request8 (PRODUCT_TYPE),
  KEY FK_ff_f_service_request9 (DEST_PINCODE),
  KEY FK_ff_f_service_request10 (ASSIGNED_TO),
  CONSTRAINT FK_ff_f_service_request3 FOREIGN KEY (COMPLAINT_CATEGORY) REFERENCES ff_d_service_request_complaint_type (SERVICE_REQUEST_COMPLAINT_TYPE_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request1 FOREIGN KEY (SERVICE_CUSTOMER_TYPE) REFERENCES ff_d_service_request_cust_type (SERVICE_REQUEST_CUSTOMER_TYPE_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request10 FOREIGN KEY (ASSIGNED_TO) REFERENCES ff_d_employee (EMPLOYEE_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request2 FOREIGN KEY (QUERY_TYPE) REFERENCES ff_d_service_request_query_type (SERVICE_REQUEST_QUERY_TYPE_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request4 FOREIGN KEY (STATUS) REFERENCES ff_d_service_request_status (SERVICE_REQUEST_STATUS_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request5 FOREIGN KEY (CREATED_BY) REFERENCES ff_d_user (USER_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request6 FOREIGN KEY (UPDATE_BY) REFERENCES ff_d_user (USER_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request7 FOREIGN KEY (ORIGIN_CITY) REFERENCES ff_d_city (CITY_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request8 FOREIGN KEY (PRODUCT_TYPE) REFERENCES ff_d_product (PRODUCT_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request9 FOREIGN KEY (DEST_PINCODE) REFERENCES ff_d_pincode (PINCODE_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*Table structure for table ff_f_service_request_complaint */

CREATE TABLE ff_f_service_request_complaint (
  SERVICE_REQUEST_COMPLAINT_ID int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_ID int(11) DEFAULT NULL,
  CONSIGNMENT_NUMBER varchar(12) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  BRANCH varchar(50) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  DECLARED_VALUE varchar(10) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  CONSIGNER_NAME varchar(50) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  CUSTOMER_ADDRESS varchar(50) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  CUSTOMER_PHONE varchar(12) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  CUSTOMER_EMAIL varchar(50) DEFAULT NULL COMMENT 'RESTORE CONSIGNMENT',
  INFORMATION_GIVEN_TO enum('SALES','CUSTOMER') DEFAULT NULL,
  MAILER_PAPER int(11) DEFAULT NULL,
  STATUS int(11) DEFAULT NULL,
  FIR_COPY enum('Y','N') DEFAULT NULL,
  FIR_DATE datetime DEFAULT NULL,
  FOC_NUMBER varchar(30) DEFAULT NULL,
  CUSTOMER_TYPE enum('Y','N') DEFAULT NULL,
  TYPE_DATE datetime DEFAULT NULL,
  REMARK varchar(150) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_COMPLAINT_ID),
  KEY FK_ff_f_service_request_complaint1 (SERVICE_REQUEST_ID),
  KEY FK_ff_f_service_request_complaint2 (MAILER_PAPER),
  KEY FK_ff_f_service_request_complaint3 (STATUS),
  CONSTRAINT FK_ff_f_service_request_complaint1 FOREIGN KEY (SERVICE_REQUEST_ID) REFERENCES ff_f_service_request (SERVICE_REQUEST_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request_complaint2 FOREIGN KEY (MAILER_PAPER) REFERENCES ff_f_service_request_papers (SERVICE_REQUEST_PAPER_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request_complaint3 FOREIGN KEY (STATUS) REFERENCES ff_d_service_request_status (SERVICE_REQUEST_STATUS_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*Table structure for table ff_f_service_request_complaint_claim */

DROP TABLE IF EXISTS ff_f_service_request_complaint_claim;

CREATE TABLE ff_f_service_request_complaint_claim (
  SERVICE_REQUEST_CLAIM_ID int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_COMPLAINT_ID int(11) DEFAULT NULL,
  ACTUAL_CLAIM enum('Y','N') DEFAULT NULL,
  ACTUAL_CLAIM_AMOUNT double(10,3) DEFAULT NULL,
  NEGOCIABLE_CLAIM enum('Y','N') DEFAULT NULL,
  NEGOCIABLE_CLAIM_AMOUNT double(10,3) DEFAULT NULL,
  SETTLEMENT enum('LOCAL','CORP') DEFAULT NULL,
  PAPER_WORK varchar(30) DEFAULT NULL,
  ACCOUNTABILITY varchar(50) DEFAULT NULL,
  CLIENT_POLICY varchar(50) DEFAULT NULL,
  MISSING_CERTIFICATE varchar(50) DEFAULT NULL,
  REMARKS varchar(150) DEFAULT NULL,
  SALES_MANAGER_FEEDBACK varchar(150) DEFAULT NULL,
  SALES_MANAGER_FEEDBACK_DATE datetime DEFAULT NULL,
  CS_MANAGER_FEEDBACK varchar(150) DEFAULT NULL,
  CS_MANAGER_FEEDBACK_DATE datetime DEFAULT NULL,
  AGM_FEEDBACK varchar(150) DEFAULT NULL,
  AGM_FEEDBACK_DATE datetime DEFAULT NULL,
  VP_FEEDBACK varchar(150) DEFAULT NULL,
  VP_FEEDBACK_DATE datetime DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_CLAIM_ID),
  KEY FK_ff_f_service_request_complaint1 (SERVICE_REQUEST_COMPLAINT_ID),
  CONSTRAINT FK_service_request_complaint_claim1 FOREIGN KEY (SERVICE_REQUEST_COMPLAINT_ID) REFERENCES ff_f_service_request_complaint (SERVICE_REQUEST_COMPLAINT_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*Table structure for table ff_f_service_request_complaint_legal */

CREATE TABLE ff_f_service_request_complaint_legal (
  ff_f_service_request_complaint_legal int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_COMPLAINT_ID int(11) DEFAULT NULL,
  INVESTIGATION_FEEDBACK varchar(150) DEFAULT NULL,
  FORWARDED_TO_FFCL_LAWYER enum('Y','N') DEFAULT NULL,
  FORWARDED_TO_FFCL_LAWYER_DATE datetime DEFAULT NULL,
  LAWYER_FEES double(10,3) DEFAULT NULL,
  REMARK varchar(150) DEFAULT NULL,
  HEARING1 varchar(150) DEFAULT NULL,
  HEARING2 varchar(150) DEFAULT NULL,
  HEARING3 varchar(150) DEFAULT NULL,
  HEARING4 varchar(150) DEFAULT NULL,
  HEARING5 varchar(150) DEFAULT NULL,
  PRIMARY KEY (ff_f_service_request_complaint_legal),
  KEY FK_service_request_complaint_legal1 (SERVICE_REQUEST_COMPLAINT_ID),
  CONSTRAINT FK_service_request_complaint_legal1 FOREIGN KEY (SERVICE_REQUEST_COMPLAINT_ID) REFERENCES ff_f_service_request_complaint (SERVICE_REQUEST_COMPLAINT_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*Table structure for table ff_f_service_request_followup */

CREATE TABLE ff_f_service_request_followup (
  FOLLOWUP_ID int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_ID int(11) DEFAULT NULL,
  FOLLOWUP_DATE datetime DEFAULT NULL,
  CALL_FROM enum('EMAI','PHONE','LATTER') DEFAULT NULL,
  CALLER enum('CUSTOMER','ORIGIN','BRANCH') DEFAULT NULL,
  CUSTOMER_NAME varchar(30) DEFAULT NULL,
  EMAIL varchar(50) DEFAULT NULL,
  FOLLOWUP_NOTE varchar(50) DEFAULT NULL,
  PRIMARY KEY (FOLLOWUP_ID),
  KEY FK_ff_f_service_request_followup1 (SERVICE_REQUEST_ID),
  CONSTRAINT FK_ff_f_service_request_followup1 FOREIGN KEY (SERVICE_REQUEST_ID) REFERENCES ff_f_service_request (SERVICE_REQUEST_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*Table structure for table ff_f_service_request_papers */

CREATE TABLE ff_f_service_request_papers (
  SERVICE_REQUEST_PAPER_ID int(11) NOT NULL AUTO_INCREMENT,
  SERVICE_REQUEST_ID int(11) DEFAULT NULL,
  FILE_NAME varchar(20) DEFAULT NULL,
  FEEDBACK varchar(150) DEFAULT NULL,
  CLIENT_MEET varchar(150) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  CREATED_BY int(11) DEFAULT NULL,
  TRANSFER_ICC int(11) DEFAULT NULL,
  PRIMARY KEY (SERVICE_REQUEST_PAPER_ID),
  KEY FK_ff_f_service_request_papers1 (SERVICE_REQUEST_ID),
  KEY FK_ff_f_service_request_papers2 (CREATED_BY),
  CONSTRAINT FK_ff_f_service_request_papers1 FOREIGN KEY (SERVICE_REQUEST_ID) REFERENCES ff_f_service_request (SERVICE_REQUEST_ID) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT FK_ff_f_service_request_papers2 FOREIGN KEY (CREATED_BY) REFERENCES ff_d_user (USER_ID) ON DELETE NO ACTION ON UPDATE CASCADE
) 

/*2.Changes requested by Chandrakant */

create table ff_f_sap_coloader_rates(
ID	int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
TRANSACTION_NUMBER 	int(11),
DISPATCH_DATE	datetime,
VENDOR_NAME	varchar(100),
SUB_RATE_TYPE	varchar(11),
AWB_CD_RR_Number	varchar(50),
TRANSPORT_REF_NO	varchar(50),
DESTINATION_CITY	varchar(15),
BASIC	double(10,3),
GROSS_TOTAL	double(10,3),
SERVICE_TYPE	varchar(50),
UOM	int(11),
QUANTITY	double(10,3),
OFFICE_CODE	varchar(20),
TRIP_SHEET_NUMBER	varchar(50),
OTHER_CHARGES	double(10,3),
TOTAL	double(10,3),
DT_SAP_OUTBOUND	enum ('N','C'),
SAP_TIMESTAMP	DATETIME,
EXCEPTION	VARCHAR(500));
