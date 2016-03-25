/*
Consignment Detail Report

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : CG Developed
AUTHOR  : SHASHANK SAXENA
DATE    : 17 JUN 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(353, 353, 'Consignment Detail Report', 'Consignment Detail Report', 'Report', 'Report', null, 'consignmentDetailReport.do?submitName=getConsignmentDetailReport', null, 'udaan-report', 'C', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(555,279,5, 'A', 'Consignment Detail Report', 353, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(354, 354,'Consignment Detail Report', 'Consignment Detail Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=353&moduleName=Reports&appName=udaan-report', null, 'centralized', 'C', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(556,247,5,'A','Consignment Detail Report',354,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(557,414,5,'A','Consignment Detail Report',354,'N','udaan-web','A');