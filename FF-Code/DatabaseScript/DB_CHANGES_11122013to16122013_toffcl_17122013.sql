/*DB CHANGES 11/12/2013 */

/*1.Nilesh  */
create table ff_f_stop_delivery_cn(
STOP_DELIVERY_CN_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
CONSIGNMENT_NO	varchar(25) NOT NULL,
STATUS_UPDATED_IN_ACTUAL_CN enum('N','Y') Default 'N',
CREATED_BY int(11) DEFAULT NULL,
CREATED_DATE datetime DEFAULT NULL,
UPDATE_BY int(11) DEFAULT NULL,
UPDATE_DATE datetime DEFAULT NULL,  
UNIQUE KEY ff_f_stop_delivery_cn_unq_indx1 (CONSIGNMENT_NO));


/*2 Himal */

CREATE TABLE ff_f_petty_cash (
`PETTY_CASH_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'AI',
`CLOSING_DATE` date NOT NULL COMMENT 'dd/MM/yyyy',
`OFFICE_ID` int(11) NOT NULL,
`CLOSING_BALANCE` double(20,2) DEFAULT NULL COMMENT 'Closing balance [ Note: it may contain negative value(s) ]',`TRANS_CREATED_DATE_TIME` datetime DEFAULT NULL COMMENT 'Record created date and time',                   
PRIMARY KEY (`PETTY_CASH_ID`),
KEY `FK_ff_f_petty_cash` (`OFFICE_ID`),
CONSTRAINT `ff_f_petty_cash_ibfk_1` FOREIGN KEY (`OFFICE_ID`) REFERENCES `ff_d_office` (`OFFICE_ID`) ON DELETE NO ACTION ON UPDATE CASCADE ); 


/*DB CHANGES 12/12/2013 */
/*1. Narmdeshwar */
alter table ff_f_load_movement 
add OPERATING_OFFICE  int(11)  COMMENT 'Logged In Office',
add KEY FK_ff_d_load_move_8 (OPERATING_OFFICE),
add CONSTRAINT FK_ff_d_load_move_8 FOREIGN KEY (OPERATING_OFFICE) REFERENCES ff_d_office(OFFICE_ID) ON DELETE NO ACTION ON UPDATE CASCADE ;


/*DB CHANGES 13/12/2013 */

/*1.Chandrakant  */

alter table ff_f_sap_bill_sales_order add EXCEPTION varchar(500);
alter table ff_f_sap_billing_consignment_summary add EXCEPTION varchar(500);
alter table ff_f_sap_collection add EXCEPTION varchar(500);
alter table ff_f_sap_contract add EXCEPTION varchar(500);
alter table ff_f_sap_customer add EXCEPTION varchar(500);
alter table ff_f_sap_employee_pickup add EXCEPTION varchar(500);
alter table ff_f_sap_expense add EXCEPTION varchar(500);
alter table ff_f_sap_item add EXCEPTION varchar(500);
alter table ff_f_sap_liability_entries add EXCEPTION varchar(500);
alter table ff_f_sap_liability_payment add EXCEPTION varchar(500);
alter table ff_f_sap_office add EXCEPTION varchar(500);
alter table ff_f_sap_outstanding_payment add EXCEPTION varchar(500);
alter table ff_f_sap_report add EXCEPTION varchar(500);
alter table ff_f_sap_stock_cancellation add EXCEPTION varchar(500);
alter table ff_f_sap_stock_issue add EXCEPTION varchar(500);
alter table ff_f_sap_stock_receipt add EXCEPTION varchar(500);
/*alter table ff_f_sap_stock_requisition add EXCEPTION varchar(500); change already done*/
alter table ff_f_sap_stock_return add EXCEPTION varchar(500);
alter table ff_f_sap_stock_transfer add EXCEPTION varchar(500);
/*alter table ff_f_sap_vendor add EXCEPTION varchar(500);change already done*/


/*DB CHANGES 16/12/2013 */

/*1. Changes requested by Chandrakant bhure */

alter table ff_f_sap_bill_sales_order add GRAND_TOTAL double(10,2);

/*2. Changes requested by Preeti Gupta */

alter table ff_f_booking
modify PRICE double(12,2) DEFAULT '0.00',
modify VOL_WEIGHT double(20,4) DEFAULT NULL;

alter table ff_f_consignment
modify PRICE double(12,2) DEFAULT '0.00',
modify VOL_WEIGHT double(20,4) DEFAULT NULL;


/*DB CHANGES 17/12/2013 */
/*1.Niharika */

alter table ff_f_delivery  drop FOREIGN KEY FK_ff_f_delivery_2;
alter table ff_f_delivery  drop FOREIGN KEY FK_ff_f_delivery_4;

alter table ff_f_delivery  
add CONSTRAINT FK_ff_f_delivery_2 FOREIGN KEY (BA_ID) REFERENCES ff_d_vendor(VENDOR_ID) ON DELETE NO ACTION ON UPDATE CASCADE;
alter table ff_f_delivery  
add CONSTRAINT FK_ff_f_delivery_4 FOREIGN KEY (FRANCHISEE_ID) REFERENCES ff_d_vendor(VENDOR_ID) ON DELETE NO ACTION ON UPDATE CASCADE;

