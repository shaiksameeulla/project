/*
Reverse Logistics Client Report

SCRIPT TYPE : Menu Entry Script
TYPE    : Client Request
AUTHOR  : SHASHANK SAXENA
DATE    : 18 FEB 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(408, 408, 'Reverse Logistics Client Report', 'Reverse Logistics Client Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'reverseLogisticClientReport.htm', null, 'udaan-report', 'E', null, now(), null, now(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(617,515,3, 'A', 'Reverse Logistics Client Report', 408, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(409, 409,'Reverse Logistics Client Report', 'Reverse Logistics Client Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=408&moduleName=Report&appName=udaan-report', null, 'centralized', 'E', null, now(), null, now(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(618,517,3,'A','Reverse Logistics Client Report',409,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(619,611,3,'A','Reverse Logistics Client Report',409,'N','udaan-web','A');