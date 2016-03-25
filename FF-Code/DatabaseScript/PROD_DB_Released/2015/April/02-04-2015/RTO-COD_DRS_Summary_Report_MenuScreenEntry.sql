/*
RTO-COD DRS Summary Report

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : Client Request
AUTHOR  : SHASHANK SAXENA
DATE    : 02 MAR 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(410, 410, 'RTO-COD DRS Summary Report', 'RTO-COD DRS Summary Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'vendorRTOReport.htm', null, 'udaan-report', 'E', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(620,515,4, 'A', 'RTO-COD DRS Summary Report', 410, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(411, 411,'RTO-COD DRS Summary Report', 'RTO-COD DRS Summary Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=410&moduleName=Report&appName=udaan-report', null, 'centralized', 'E', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(621,517,4,'A','RTO-COD DRS Summary Report',411,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(622,611,4,'A','RTO-COD DRS Summary Report',411,'N','udaan-web','A');