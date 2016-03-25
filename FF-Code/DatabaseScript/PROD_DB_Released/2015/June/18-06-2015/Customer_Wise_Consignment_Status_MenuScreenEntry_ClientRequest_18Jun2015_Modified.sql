/*
Customer Wise Consignment Status

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : Client Request
AUTHOR  : SHASHANK SAXENA
DATE    : 18 JUN 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(418, 418, 'Customer Wise Consignment Status', 'Customer Wise Consignment Status', 'Report', 'Report', null, 'CustomerWiseConsignmentStatusDetails.htm', null, 'udaan-report', 'B', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(632,279,6, 'A', 'Customer Wise Consignment Status', 418, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(419, 419,'Customer Wise Consignment Status', 'Customer Wise Consignment Status', 'Report', 'Report', null, 'login.do?submitName=silentLoginToApp&screenCode=418&moduleName=Reports&appName=udaan-report', null, 'centralized', 'B', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(633,247,6,'A','Customer Wise Consignment Status',419,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(634,414,6,'A','Customer Wise Consignment Status',419,'N','udaan-web','A');