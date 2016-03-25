/*
Scripts for MENU and SCREEN data correction

AUTHOR : SHASHANK SAXENA
CREATION DATE : 
UPDATION DATE : 18 March 2015

*/

/* 
Final Scripts - Module Wise
0. Miscellaneous Scripts
1. UMC
2. Route Service By
3. Stock Management
4. MEC
5. Tracking
6. Rate Management [Rate + Quotation + Contract]
7. Held Up
8. Complaint
9. Coloader
10. Master
11. Report
12. Billing
13. Leads
14. Stop Delivery
15. Report
16. Rate Calculator
*/

/*
Free and Replacable Screen Ids
-33, 34, 183, 184

Replaced Screen Ids
33 CONSUMED for Rate Calculator

Free Menu Ids
-172, -381, -382

Replaced Menu Ids
172 CONSUMED FOR Rate Revision Analysis
381 Consumed for Lead Validation
382 CONSUMED FOR BRR Datewise Status Report

*/

-- SELECT * FROM ff_d_app_scrn;

/*
Miscellaneous Scripts
*/

-- Mark All Screen's SRC_ACCESSIBLE_TO = 'E' except Screen Change Password SRC_ACCESSIBLE_TO = 'B'

UPDATE ff_d_app_scrn SET SRC_ACCESSIBLE_TO = 'E', DT_TO_BRANCH = 'N' WHERE SCREEN_ID <> 96;
UPDATE ff_d_app_scrn SET SRC_ACCESSIBLE_TO = 'B', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 96;

/*
UMC
1. Change Password is centralized with default URL
2. Rest are on only udaan-config-admin

Changes Needed In : ff_d_app_scrn, ff_d_app_menu
*/

-- Changes in ff_d_app_menu
update ff_d_app_menu set MENU_LABEL = 'Create Employee User', APP_SCREEN = 97 WHERE MENU_ID = 142;
update ff_d_app_menu set MENU_LABEL = 'Create Customer User', APP_SCREEN = 98 WHERE MENU_ID = 143;
update ff_d_app_menu set MENU_LABEL = 'Manage User Roles', APP_SCREEN = 99 WHERE MENU_ID = 144;
update ff_d_app_menu set MENU_LABEL = 'Assign User Roles', APP_SCREEN = 100 WHERE MENU_ID = 145;
update ff_d_app_menu set MENU_LABEL = 'Assign Approver', APP_SCREEN = 101 WHERE MENU_ID = 146;

-- Changes in ff_d_app_scrn
update ff_d_app_scrn set URL_NAME = 'createEmployeeLogin.do?submitName=employeeLogin', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() where SCREEN_ID = 97;
update ff_d_app_scrn set URL_NAME = 'createCustomerLogin.do?submitName=customerLogin', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() where SCREEN_ID = 98;
update ff_d_app_scrn set URL_NAME = 'userManagementRoles.do?submitName=addUserRoles', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() where SCREEN_ID = 99;
update ff_d_app_scrn set URL_NAME = 'roleAssignment.do?submitName=assignUserRoles', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() where SCREEN_ID = 100;
update ff_d_app_scrn set URL_NAME = 'assignApprover.do?submitName=viewAssignApprover', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() where SCREEN_ID = 101;

/*
Route Service By
*/

update ff_d_app_scrn set APP_NAME = 'udaan-config-admin', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' where SCREEN_ID = 79;
update ff_d_app_scrn set APP_NAME = 'udaan-config-admin', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' where SCREEN_ID = 80;
update ff_d_app_scrn set APP_NAME = 'udaan-config-admin', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' where SCREEN_ID = 81;

/*
Stock Management
*/
-- Stock Approval
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=300&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 82;
INSERT INTO ff_d_app_scrn VALUES(300,300,'Stock Approval','Stock Approval', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','approveRequisition.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Approval', APP_SCREEN = 300, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 82 AND APP_NAME = 'udaan-config-admin';

-- Stock Issue from RHO / Admin Office
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=301&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 83;
INSERT INTO ff_d_app_scrn VALUES(301,301,'Stock Issue from RHO / Admin Office','Stock Issue from RHO / Admin Office', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockIssue.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Issue from RHO / Admin Office', APP_SCREEN = 301, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 83 AND APP_NAME = 'udaan-config-admin';

-- Stock Issue  to Customer
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=302&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 84;
INSERT INTO ff_d_app_scrn VALUES(302,302,'Stock Issue  to Customer','Stock Issue  to Customer', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockIssueCustomer.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Issue  to Customer', APP_SCREEN = 302, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 84 AND APP_NAME = 'udaan-config-admin';

-- Stock Issue  to Employee
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=303&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 85;
INSERT INTO ff_d_app_scrn VALUES(303,303,'Stock Issue  to Employee','Stock Issue  to Employee', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockIssueEmployee.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Issue  to Employee', APP_SCREEN = 303, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 85 AND APP_NAME = 'udaan-config-admin';

-- Create Stock Requisition
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=304&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 86;
INSERT INTO ff_d_app_scrn VALUES(304,304,'Create Stock Requisition','Create Stock Requisition', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','createRequisition.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create Stock Requisition', APP_SCREEN = 304, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 86 AND APP_NAME = 'udaan-config-admin';

-- Stock Receipt At Branch
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=305&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 87;
INSERT INTO ff_d_app_scrn VALUES(305,305,'Stock Receipt At Branch','Stock Receipt At Branch', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockReceipt.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Receipt At Branch', APP_SCREEN = 305, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 87 AND APP_NAME = 'udaan-config-admin';

-- Stock Return
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=306&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 88;
INSERT INTO ff_d_app_scrn VALUES(306,306,'Stock Return','Stock Return', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockReturnRho.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Return', APP_SCREEN = 306, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 88 AND APP_NAME = 'udaan-config-admin';

-- Stock Transfer
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=307&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 89;
INSERT INTO ff_d_app_scrn VALUES(307,307,'Stock Transfer','Stock Transfer', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockTransfer.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Transfer', APP_SCREEN = 307, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 89 AND APP_NAME = 'udaan-config-admin';

-- Stock Cancellation
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=308&moduleName=Stock Management&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 90;
INSERT INTO ff_d_app_scrn VALUES(308,308,'Stock Cancellation','Stock Cancellation', 'Stock Management','Stock Management','/udaan-config-admin//images//PickupManager.png','stockCancel.do?submitName=viewFormDetails', 'submitName=viewFormDetails','udaan-config-admin','B',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Cancellation', APP_SCREEN = 308, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 90 AND APP_NAME = 'udaan-config-admin';

/*
MEC
*/
-- Expense Entry
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=309&moduleName=MEC&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 59;
INSERT INTO ff_d_app_scrn VALUES(309,309,'Expense Entry','Expense Entry', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','expenseEntry.do?submitName=viewExpenseOffice', 'submitName=viewExpenseOffice','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Expense Entry', APP_SCREEN = 309, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 59 AND APP_NAME = 'udaan-config-admin';

-- Bill Collection
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=310&moduleName=MEC&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 62;
INSERT INTO ff_d_app_scrn VALUES(310,310,'Bill Collection','Bill Collection', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','billCollection.do?submitName=prepareBillCollectionPage', 'submitName=prepareBillCollectionPage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Bill Collection', APP_SCREEN = 310, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 62 AND APP_NAME = 'udaan-config-admin';

-- CN Collection
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=311&moduleName=MEC&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 63;
INSERT INTO ff_d_app_scrn VALUES(311,311,'CN Collection','CN Collection', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','cnCollection.do?submitName=viewCNCollection', 'submitName=viewCNCollection','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'CN Collection', APP_SCREEN = 311, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 63 AND APP_NAME = 'udaan-config-admin';

-- Validate Expense Entry
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now(), CREATION_DATE = now() WHERE SCREEN_ID = 60;

-- Validate Collection Entry

UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now(), CREATION_DATE = now() WHERE SCREEN_ID = 64;

-- Petty Cash Report
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=320&moduleName=MEC&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 130;
INSERT INTO ff_d_app_scrn VALUES(320,320,'Petty Cash Report','Petty Cash Report', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','pettyCashReport.do?submitName=viewPettyCashReport', 'submitName=viewPettyCashReport','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Petty Cash Report', APP_SCREEN = 320, DT_TO_BRANCH = 'N' WHERE MENU_ID = 238;

-- Branch Serviceability - 222
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=326&moduleName=MISC&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 222;
INSERT INTO ff_d_app_scrn VALUES(326,326,'Branch Serviceability','Branch Serviceability', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','searchByBranch.do?submitName=searchByBranch', 'submitName=searchByBranch','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Branch Serviceability', APP_SCREEN = 326, DT_TO_BRANCH = 'N' WHERE MENU_ID = 363;
INSERT INTO ff_d_app_menu VALUES (533,11,6,'L','Pincode Search', null, 'N', 'udaan-web','A');
INSERT INTO ff_d_app_menu VALUES(534,533,1,'A','Branch Serviceability',222,'N','udaan-web','A');

-- Pincode Serviceability - 221
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=327&moduleName=MISC&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 221;
INSERT INTO ff_d_app_scrn VALUES(327,327,'Pincode Serviceability','Pincode Serviceability', 'MEC','Pincode Search','/udaan-config-admin//images//PickupManager.png','searchByPincode.do?submitName=searchByPincode', 'submitName=searchByPincode','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Pincode Serviceability', APP_SCREEN = 327, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 221 AND APP_NAME = 'udaan-config-admin';
INSERT INTO ff_d_app_menu VALUES(535,533,2,'A','Pincode Serviceability',221,'N','udaan-web','A');

-- Liability Payment
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=328&moduleName=MEC&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 61;
INSERT INTO ff_d_app_scrn VALUES(328,328,'Liability Payment','Liability Payment', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','payLiability.do?submitName=viewLiabilityPaymentPage', 'submitName=viewLiabilityPaymentPage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
-- DELETE FROM ff_d_app_menu WHERE MENU_ID = 172;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Liability Payment', APP_SCREEN = 328, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 61 AND APP_NAME = 'udaan-config-admin';

-- Customer Outstanding Report
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=329&moduleName=MEC&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 119;
INSERT INTO ff_d_app_scrn VALUES(329,329,'Customer Outstanding Report','Customer Outstanding Report', 'MEC','MEC','/udaan-config-admin//images//PickupManager.png','outstandingReport.do?submitName=displayOutstandingReportPage', 'submitName=displayOutstandingReportPage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Customer Outstanding Report', APP_SCREEN = 329, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 119 AND APP_NAME = 'udaan-config-admin';
UPDATE ff_d_app_menu SET MENU_LABEL = 'Customer Outstanding Report', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 119 AND APP_NAME = 'udaan-web';

/*
Tracking
*/

-- Consignment Tracking - 91

UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=266&moduleName=Tracking&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 91;
INSERT INTO ff_d_app_scrn VALUES(266,266,'Consignment Tracking','Consignment Tracking', 'Tracking','Tracking','/udaan-config-admin//images//PickupManager.png','consignmentTrackingHeader.do?submitName=viewConsignmentTracking', 'submitName=viewConsignmentTracking','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Consignment Tracking', APP_SCREEN = 266, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 91 AND APP_NAME = 'udaan-config-admin';

-- Manifest Tracking - 92

UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=267&moduleName=Tracking&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 92;
INSERT INTO ff_d_app_scrn VALUES(267,267,'Manifest Tracking','Manifest Tracking', 'Tracking','Tracking','/udaan-config-admin//images//PickupManager.png','manifestTrackingHeader.do?submitName=viewManifestTracking', 'submitName=viewManifestTracking','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Manifest Tracking', APP_SCREEN = 267, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 92 AND APP_NAME = 'udaan-config-admin';

-- CD/RR/AWB/ Gate Pass Tracking - 93

UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=268&moduleName=Tracking&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 93;
INSERT INTO ff_d_app_scrn VALUES(268,268,'CD/RR/AWB/ Gate Pass Tracking','CD/RR/AWB/ Gate Pass Tracking', 'Tracking','Tracking','/udaan-config-admin//images//PickupManager.png','gatePassTracking.do?submitName=viewGatepassTracking', 'submitName=viewGatepassTracking','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'CD/RR/AWB/ Gate Pass Tracking', APP_SCREEN = 268, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 93 AND APP_NAME = 'udaan-config-admin';

-- Bulk Import Tracking - 94

UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=269&moduleName=Tracking&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 94;
INSERT INTO ff_d_app_scrn VALUES(269,269,'Bulk Import Tracking','Bulk Import Tracking', 'Tracking','Tracking','/udaan-config-admin//images//PickupManager.png','bulkImportTracking.do?submitName=viewBulkImportTracking', 'submitName=viewBulkImportTracking','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Bulk Import Tracking', APP_SCREEN = 269, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 94 AND APP_NAME = 'udaan-config-admin';

-- Multiple Tracking - 95

UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=270&moduleName=Tracking&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 95;
INSERT INTO ff_d_app_scrn VALUES(270,270,'Multiple Tracking','Multiple Tracking', 'Tracking','Tracking','/udaan-config-admin//images//PickupManager.png','multipleTracking.do?submitName=viewMultipleTracking', 'submitName=viewMultipleTracking','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Multiple Tracking', APP_SCREEN = 270, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 95 AND APP_NAME = 'udaan-config-admin';

/*

Rate Management

*/

-- Set Rate Benchmark
UPDATE ff_d_app_scrn SET URL_NAME = 'rateBenchMark.do?submitName=viewRateBenchMark', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 70;

-- Set Rate Discount
UPDATE ff_d_app_scrn set URL_NAME = 'rateBenchMarkDiscount.do?submitName=viewBenchMarkDiscount', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 71;

-- Create Normal Quotation-SE
insert into ff_d_app_scrn values(273,273,'Create Normal Quotation-SE','Create Normal Quotation-SE', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=viewRateQuotation&sales=E', 'submitName=viewRateQuotation','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
update ff_d_app_scrn set URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=273&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 72;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create Normal Quotation-SE', APP_SCREEN = 273, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 72 AND APP_NAME = 'udaan-config-admin';

-- Create Normal Quotation-SC
insert into ff_d_app_scrn values(274,274,'Create Normal Quotation-SC','Create Normal Quotation-SC', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=viewRateQuotation&sales=C', 'submitName=viewRateQuotation','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
update ff_d_app_scrn set URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=274&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 73;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create Normal Quotation-SC', APP_SCREEN = 274, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 73 AND APP_NAME = 'udaan-config-admin';

-- Create Ecommerce Quotation-SE
insert into ff_d_app_scrn values(275,275,'Create Ecommerce Quotation-SE','Create Ecommerce Quotation-SE', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=viewEcommerceRateQuotation&sales=E', 'submitName=viewEcommerceRateQuotation','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
update ff_d_app_scrn set URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=275&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 74;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create Ecommerce Quotation-SE', APP_SCREEN = 275, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 74 AND APP_NAME = 'udaan-config-admin';

-- Create Ecommerce Quotation-SC
INSERT INTO ff_d_app_scrn VALUES(276,276,'Create Ecommerce Quotation-SC','Create Ecommerce Quotation-SC', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=viewEcommerceRateQuotation&sales=C', 'submitName=viewEcommerceRateQuotation','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=276&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 75;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create Ecommerce Quotation-SE', APP_SCREEN = 276, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 75 AND APP_NAME = 'udaan-config-admin';

-- List View of Quotation - SE
insert into ff_d_app_scrn values(277,277,'List View of Quotation - SE','List View of Quotation - SE', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=listViewQuotation&emp=E', 'submitName=listViewQuotation&emp=E','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
update ff_d_app_scrn set URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=277&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 76;
UPDATE ff_d_app_menu SET MENU_LABEL = 'List View of Quotation - SE', APP_SCREEN = 277, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 76 AND APP_NAME = 'udaan-config-admin';

-- List View of Quotation - SM
insert into ff_d_app_scrn values(278,278,'List View of Quotation - SM','List View of Quotation - SM', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateQuotation.do?submitName=listViewQuotation&emp=C', 'submitName=listViewQuotation&emp=C','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=278&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 77;
UPDATE ff_d_app_menu SET MENU_LABEL = 'List View of Quotation - SM', APP_SCREEN = 278, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 77 AND APP_NAME = 'udaan-config-admin';

-- BA Rate Configuration [Only udaan-config-admin]
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 108;
UPDATE ff_d_app_menu SET MENU_LABEL = 'BA Rate Configuration', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 108 AND APP_NAME = 'udaan-config-admin';

-- Cash/ACC Rate Configuration [Only udaan-config-admin]
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 109;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Cash/ACC Rate Configuration', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 109 AND APP_NAME = 'udaan-config-admin';

-- EB Rate Configuration [Only udaan-config-admin]
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 110;
UPDATE ff_d_app_menu SET MENU_LABEL = 'EB Rate Configuration', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 110 AND APP_NAME = 'udaan-config-admin';

-- BA Material Rate Configuration [Only udaan-config-admin]
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 111;
UPDATE ff_d_app_menu SET MENU_LABEL = 'BA Material Rate Configuration', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 111 AND APP_NAME = 'udaan-config-admin';

-- Normal Contract-Super User Edit
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 117;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Normal Contract-Super User Edit', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 117 AND APP_NAME = 'udaan-config-admin';

-- Ecommerce Contract-Super User Edit
UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 118;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Ecommerce Contract-Super User Edit', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 118 AND APP_NAME = 'udaan-config-admin';

-- Approve Quotation

UPDATE ff_d_app_scrn SET APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 78;

-- Normal Contract

INSERT INTO ff_d_app_scrn VALUES(315,315,'Normal Contract','Normal Contract', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateContract.do?submitName=viewRateContractNormal', 'submitName=viewRateContractNormal','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=315&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 103;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Normal Contract', APP_SCREEN = 315, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 103 AND APP_NAME = 'udaan-config-admin';

-- Ecommerce Contract

INSERT INTO ff_d_app_scrn VALUES(316,316,'Ecommerce Contract','Ecommerce Contract', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateContract.do?submitName=viewRateContractECommerce', 'submitName=viewRateContractECommerce','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=316&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 104;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Ecommerce Contract', APP_SCREEN = 316, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 104 AND APP_NAME = 'udaan-config-admin';

-- List View of Contract - EE

INSERT INTO ff_d_app_scrn VALUES(317,317,'List View of Contract - EE','List View of Contract - EE', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateContract.do?submitName=listViewContract&emp=E', 'submitName=listViewContract&emp=E','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=317&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 105;
UPDATE ff_d_app_menu SET MENU_LABEL = 'List View of Contract - EE', APP_SCREEN = 317, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 105 AND APP_NAME = 'udaan-config-admin';


-- List View of Contract - EM

INSERT INTO ff_d_app_scrn VALUES(318,318,'List View of Contract - EM','List View of Contract - EM', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','rateContract.do?submitName=listViewContract&emp=C', 'submitName=listViewContract&emp=C','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=318&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 106;
UPDATE ff_d_app_menu SET MENU_LABEL = 'List View of Contract - EM', APP_SCREEN = 318, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 106 AND APP_NAME = 'udaan-config-admin';

-- Customer Search

INSERT INTO ff_d_app_scrn VALUES(319,319,'Customer Search','Customer Search', 'Rate Management','Rate Management','/udaan-config-admin//images//PickupManager.png','viewCustomerSearchInfo.do', null,'udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=319&moduleName=CRM&appName=udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 107;
UPDATE ff_d_app_menu SET MENU_LABEL = 'List View of Contract - EM', APP_SCREEN = 319, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 107 AND APP_NAME = 'udaan-config-admin';

-- Approve Quotation - Ecommerce - 240

UPDATE ff_d_app_scrn SET SCREEN_CODE = 'ECQA', URL_NAME = null, URL_PARAMS = null, APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' where SCREEN_ID = 240;

-- View CASH/ACC Rate Configuration
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 409, MENU_LABEL = 'View Cash/ACC Rate Configuration', DT_TO_BRANCH = 'N' WHERE MENU_ID = 219;

-- View BA Rate Configuration
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 409, MENU_LABEL = 'View BA Rate Configuration', DT_TO_BRANCH = 'N' WHERE MENU_ID = 220;


/*

Held Up

*/

-- Held Up - 30

INSERT INTO ff_d_app_scrn VALUES(279,279,'Held Up','Held Up', 'Held Up','Held Up','/udaan-config-admin//images//PickupManager.png','heldUp.do?submitName=viewHeldUp', 'submitName=viewHeldUp','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=279&moduleName=Held Up&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 30;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Held Up', APP_SCREEN = 279, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 30 AND APP_NAME = 'udaan-config-admin';


/*
Complaint
*/

-- Create service request
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=312&moduleName=Complaints&appName=udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 186;
INSERT INTO ff_d_app_scrn VALUES(312,312,'Create service request','Create service request', 'Complaint','Compslaint','/udaan-config-admin//images//PickupManager.png','serviceRequestForService.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Create service request', APP_SCREEN = 312, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 186 AND APP_NAME = 'udaan-config-admin';

-- Service request summary
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=313&moduleName=Complaints&appName=udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 187;
INSERT INTO ff_d_app_scrn VALUES(313,313,'Service request summary','Service request summary', 'Complaint','Complaint','/udaan-config-admin//images//PickupManager.png','backlikeSummary.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Service request summary', APP_SCREEN = 313, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 187 AND APP_NAME = 'udaan-config-admin';

-- Search Service Request
UPDATE ff_d_app_scrn SET SCREEN_CODE = 193,URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=314&moduleName=Complaints&appName=udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 193;
INSERT INTO ff_d_app_scrn VALUES(314,314,'Search Service Request','Search Service Request', 'Complaint','Complaint','/udaan-config-admin//images//PickupManager.png','searchServiceRequest.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Search Service Request', APP_SCREEN = 314, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 193 AND APP_NAME = 'udaan-config-admin';

/*
Coloader
*/

-- Air Coloading - 122

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 122;

-- Train Coloading - 123

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 123;

-- Vehicle Coloading - 124

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 124;

-- Fuel Rate - 125

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 125;

-- Vehicle Service Entry - 126

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 126;

-- Surface Rate Entry - 127

UPDATE ff_d_app_scrn SET MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 127;

-- CD/AWB Modification - 128

UPDATE ff_d_app_scrn SET SCREEN_NAME = 'CD/AWB Modification', SCREEN_DESCRIPTION = 'CD/AWB Modification', MODULE_NAME = 'Coloader', MODULE_DESCRIPTION = 'Coloader', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', CREATION_DATE = now(), UPDATE_DATE = now() WHERE SCREEN_ID = 128;


/*
Master
*/

-- EB Preference Masters
INSERT INTO ff_d_app_menu VALUES (521, 328, 3, 'A', 'EB Preference Masters', 58, 'N', 'udaan-config-admin','A');


-- Holiday Master
UPDATE ff_d_app_menu SET POSITION = 2, DT_TO_BRANCH = 'N' WHERE MENU_ID = 331;
UPDATE ff_d_app_scrn SET MODULE_NAME = 'Masters', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 194;

/*
Billing
*/

-- Invoice Runsheet Printing
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=321&moduleName=Billing&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 1;
INSERT INTO ff_d_app_scrn VALUES(321,321,'Invoice RunSheet Printing','Invoice RunSheet Printing', 'Billing','Billing','/udaan-config-admin//images//PickupManager.png','invoiceRunSheetPrinting.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Invoice RunSheet Printing', APP_SCREEN = 321, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 1 AND APP_NAME = 'udaan-config-admin';

-- Bill Printing
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=322&moduleName=Billing&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 2;
INSERT INTO ff_d_app_scrn VALUES(322,322,'Bill Printing','Bill Printing', 'Billing','Billing','/udaan-config-admin//images//PickupManager.png','billPrinting.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Bill Printing', APP_SCREEN = 322, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 2 AND APP_NAME = 'udaan-config-admin';

-- Invoice Runsheet Update
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=323&moduleName=Billing&appName=udaan-config-admin', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 3;
INSERT INTO ff_d_app_scrn VALUES(323,323,'Invoice Runsheet Update','Invoice Runsheet Update', 'Billing','Billing','/udaan-config-admin//images//PickupManager.png','invoiceRunSheetUpdate.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Invoice Runsheet Update', APP_SCREEN = 323, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 3 AND APP_NAME = 'udaan-config-admin';
UPDATE ff_d_app_menu SET MENU_LABEL = 'Invoice Runsheet Update', DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 3;

-- Re-Billing
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=324&moduleName=Billing&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 212;
INSERT INTO ff_d_app_scrn VALUES(324,324,'Re-Billing','Re-Billing', 'Billing','Billing','/udaan-config-admin//images//PickupManager.png','reBilling.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Re-Billing', APP_SCREEN = 324, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 212 AND APP_NAME = 'udaan-config-admin';
INSERT INTO ff_d_app_menu VALUES(522,376,6,'A','Re-Billing',212,'N','udaan-web','A');

-- ReBill GDR Report 
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=325&moduleName=Billing&appName=udaan-config-admin', APP_NAME = 'centralized', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 213;
INSERT INTO ff_d_app_scrn VALUES(325,325,'ReBill GDR Report','ReBill GDR Report', 'Billing','Billing','/udaan-config-admin//images//PickupManager.png','reBillingGDR.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'ReBill GDR Report', APP_SCREEN = 325, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 213 AND APP_NAME = 'udaan-config-admin';
INSERT INTO ff_d_app_menu VALUES(523,376,5,'A','ReBill GDR Report',213,'N','udaan-web','A');

/*
Leads
*/

-- Create Lead - 31
UPDATE ff_d_app_scrn SET SCREEN_DESCRIPTION = 'Create Lead', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 31;

-- Leads View - 32
UPDATE ff_d_app_scrn SET SCREEN_DESCRIPTION = 'Leads View', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 32;

-- Lead Validation - 185
UPDATE ff_d_app_scrn SET SCREEN_CODE = '185', SCREEN_NAME = 'Lead Validation', SCREEN_DESCRIPTION = 'Lead Validation', APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 185;
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 310, POSITION = 3, MENU_TYPE = 'A', MENU_LABEL = 'Lead Validation', APP_SCREEN = 185, DT_TO_BRANCH = 'N', APP_NAME = 'udaan-config-admin' WHERE MENU_ID = 381;


/*
Stop Delivery
*/

-- Stop Delivery - 188
UPDATE ff_d_app_scrn SET URL_NAME = 'login.do?submitName=silentLoginToApp&screenCode=330&moduleName=MISC&appName=udaan-config-admin', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 188;
INSERT INTO ff_d_app_scrn VALUES(330,330,'Stop Delivery','Stop Delivery', 'Stop Delivery','Stop Delivery','/udaan-config-admin//images//PickupManager.png','stopDelivery.do?submitName=preparePage', 'submitName=preparePage','udaan-config-admin','E',null,now(),null,now(),'N','N',null,null,null,null);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stop Delivery', APP_SCREEN = 330, DT_TO_BRANCH = 'N' WHERE APP_SCREEN = 188 AND APP_NAME = 'udaan-config-admin';
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stop Delivery', DT_TO_BRANCH = 'N' WHERE MENU_ID = 388;

/*
Report
*/

-- Sales Reports – Direct & BA
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Sales Report - Direct & BA', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID in (202,204);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Sales Report - Direct & BA', DT_TO_BRANCH = 'N' WHERE APP_SCREEN in (202,204);

-- Branch Projection
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Branch Projection', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID in (215,216);
UPDATE ff_d_app_menu SET MENU_LABEL = 'Branch Projection', DT_TO_BRANCH = 'N' WHERE APP_SCREEN in (215,216);

-- Customer Slab Wise Revenue % Report 
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Customer Slab Wise Revenue % Report', SCREEN_DESCRIPTION = 'Customer Slab Wise Revenue % Report', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 207; 
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Customer Slab Wise Revenue % Report', SCREEN_DESCRIPTION = 'Customer Slab Wise Revenue % Report', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 209;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Customer Slab Wise Revenue % Report', DT_TO_BRANCH = 'N' WHERE MENU_ID = 344;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Customer Slab Wise Revenue % Report', APP_SCREEN = 209, DT_TO_BRANCH = 'N' WHERE MENU_ID = 455;

-- Customer Wise Quality Analysis Report

UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Customer Wise Quality Analysis Report', SCREEN_DESCRIPTION = 'Customer Wise Quality Analysis Report', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 203; 
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Customer Wise Quality Analysis Report', SCREEN_DESCRIPTION = 'Customer Wise Quality Analysis Report', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 205;

-- Region Wise Sales Traffic
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Region Wise Sales Traffic', SCREEN_DESCRIPTION = 'Region Wise Sales Traffic', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 206; 
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Region Wise Sales Traffic', SCREEN_DESCRIPTION = 'Region Wise Sales Traffic', UPDATE_DATE = now(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 208;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Region Wise Sales Traffic', APP_SCREEN = 208, DT_TO_BRANCH = 'N' WHERE MENU_ID = 454;

-- Rate Revision Analysis
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 448, POSITION = 8, MENU_TYPE = 'A', MENU_LABEL = 'Rate Revision Analysis', APP_SCREEN = '242', DT_TO_BRANCH = 'N', APP_NAME = 'udaan-web' WHERE MENU_ID = 172;

-- Stock Request & Approval
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Request & Approval', SCREEN_DESCRIPTION = 'Stock Request & Approval', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 144;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Request & Approval', SCREEN_DESCRIPTION = 'Stock Request & Approval', DT_TO_BRANCH = 'N', UPDATE_DATE = now() WHERE SCREEN_ID = 164;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Request & Approval', DT_TO_BRANCH = 'N' WHERE MENU_ID = 242;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Request & Approval', DT_TO_BRANCH = 'N' WHERE MENU_ID = 433;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Stock Request & Approval', DT_TO_BRANCH = 'N' WHERE MENU_ID = 274;

-- BRR Datewise Status Report
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 446, POSITION = 3, MENU_TYPE = 'A', MENU_LABEL = 'BRR Datewise Status Report', APP_SCREEN = '253', DT_TO_BRANCH = 'N', APP_NAME = 'udaan-web' WHERE MENU_ID = 382 ;

-- BRR Summary Report
INSERT INTO ff_d_app_menu VALUES (525,446,10,'A','BRR Summary Report',251, 'N','udaan-web','A');
INSERT INTO ff_d_app_menu VALUES (526,324,10,'A','BRR Summary Report',251, 'N','udaan-config-admin','A');

-- BRR Detail Report
UPDATE ff_d_app_menu set POSITION = 11, EMBEDDED_IN_MENU = 322, APP_SCREEN = 229, APP_NAME = 'udaan-report', DT_TO_BRANCH = 'N' where MENU_ID = 484;
UPDATE ff_d_app_menu set POSITION = 11, EMBEDDED_IN_MENU = 324, APP_SCREEN = 231, APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' where MENU_ID = 519;
INSERT INTO ff_d_app_menu VALUES (528,446,11,'A','BRR Detail',231, 'N','udaan-web','A');

-- BRR All India
UPDATE ff_d_app_menu set POSITION = 12, EMBEDDED_IN_MENU = 446, APP_SCREEN = 232, APP_NAME = 'udaan-web', DT_TO_BRANCH = 'N' where MENU_ID = 485;
UPDATE ff_d_app_menu set POSITION = 12, EMBEDDED_IN_MENU = 324, APP_SCREEN = 232, APP_NAME = 'udaan-config-admin', DT_TO_BRANCH = 'N' where MENU_ID = 486;
INSERT INTO ff_d_app_menu VALUES (536,322,12,'A','BRR Detail',230, 'N','udaan-report','A');

-- Hit Ratio Datewise
INSERT INTO ff_d_app_menu VALUES (529,446,4,'A','Hit Ratio Datewise',257, 'N','udaan-web','A');

-- Hit Ratio Branchwise
INSERT INTO ff_d_app_menu VALUES (530,446,5,'A','Hit Ratio Branchwise',255, 'N','udaan-web','A');

-- Origin Hit Ratio
INSERT INTO ff_d_app_menu VALUES (531,446,6,'A','Origin Hit Ratio',259, 'N','udaan-web','A');

-- In Summary All
UPDATE ff_d_app_menu SET APP_SCREEN = 235, DT_TO_BRANCH = 'N' WHERE MENU_ID = 476;

-- LC/TOPAY/COD Consignment Details
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'LC/TOPAY/COD Consignment Details', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 137;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'LC/TOPAY/COD Consignment Details', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 157;
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 472, POSITION = 7, DT_TO_BRANCH = 'N' WHERE MENU_ID = 422;

-- Pre Alert In All
UPDATE ff_d_app_menu SET APP_SCREEN = 236, DT_TO_BRANCH = 'N' WHERE MENU_ID = 477;

-- Party Wise Report-All
UPDATE ff_d_app_menu SET APP_SCREEN = 237, DT_TO_BRANCH = 'N' WHERE MENU_ID = 478;

-- Summary Report All
UPDATE ff_d_app_menu SET APP_SCREEN = 238, DT_TO_BRANCH = 'N' WHERE MENU_ID = 479;

-- Payment Details to Customer
INSERT INTO ff_d_app_menu VALUES (532,472,6,'A','Payment Details To Customer',249, 'N','udaan-web','A');
UPDATE ff_d_app_menu SET MENU_LABEL = 'Payment Details To Customer', DT_TO_BRANCH = 'N' WHERE MENU_ID = 511;

-- DSR Report
UPDATE ff_d_app_menu SET APP_SCREEN = 234, DT_TO_BRANCH = 'N' WHERE MENU_ID = 474;

-- Sales Report
UPDATE ff_d_app_menu SET APP_SCREEN = 233, DT_TO_BRANCH = 'N' WHERE MENU_ID = 475;

-- Yield Report
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Yield Report', SCREEN_DESCRIPTION = 'Yield Report', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 210;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Yield Report', SCREEN_DESCRIPTION = 'Yield Report', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 211;

-- Held Up Report
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Held Up Report', SCREEN_DESCRIPTION = 'Held Up Report', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 140;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Held Up Report', SCREEN_DESCRIPTION = 'Held Up Report', DT_TO_BRANCH = 'N', UPDATE_DATE = NOW() WHERE SCREEN_ID = 160;
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 418, DT_TO_BRANCH = 'N', POSITION = 8 WHERE MENU_ID = 427;

-- RTH Validation Report
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'RTH Validations', SCREEN_DESCRIPTION = 'RTH Validation Report', DT_TO_BRANCH = 'N' WHERE SCREEN_ID in (150,170);
INSERT INTO ff_d_app_menu VALUES(537,10,14,'L','RTH/RTO Reports',null,'N','udaan-web','A');
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 537, POSITION = 1, DT_TO_BRANCH = 'N' WHERE MENU_ID = 429;

-- All Product RTO Report
UPDATE ff_d_app_menu SET EMBEDDED_IN_MENU = 537, DT_TO_BRANCH = 'N', POSITION = 2 WHERE MENU_ID = 441;

-- Stock Transfer
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Transfer Report', SCREEN_DESCRIPTION = 'Stock Transfer Report', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 146;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Transfer Report', SCREEN_DESCRIPTION = 'Stock Transfer Report', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 166;

-- Stock Cancellation
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Cancellation Report', SCREEN_DESCRIPTION = 'Stock Cancellation Report', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 147;
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Stock Cancellation Report', SCREEN_DESCRIPTION = 'Stock Cancellation Report', DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 167;

-- DRS Tally [Client-Made Report]
UPDATE ff_d_app_menu SET MENU_LABEL = 'DRS Tally', DT_TO_BRANCH = 'N' WHERE MENU_ID = 516;
INSERT INTO ff_d_app_menu VALUES(616,611,1,'A','DRS Tally',265,'N','udaan-web','A');



/*
Rate Calculator
*/

-- Rate Calculator
UPDATE ff_d_app_scrn SET SCREEN_NAME = 'Rate Calculator', SCREEN_DESCRIPTION = 'Rate Calculator Screen', MODULE_NAME = 'Rate Calculator', MODULE_DESCRIPTION = 'Rate Calculator', URL_NAME = 'rateCalculator.do?submitName=viewRateCalculator', URL_PARAMS='submitName=viewRateCalculator', APP_NAME='udaan-web', SRC_ACCESSIBLE_TO = 'E', CREATION_DATE = now(), UPDATE_DATE = now(), DT_TO_BRANCH = 'N'  WHERE SCREEN_ID = 33;
UPDATE ff_d_app_menu SET MENU_LABEL = 'Rate Calculator', APP_SCREEN = '33', DT_TO_BRANCH = 'N' WHERE MENU_ID = 124;
