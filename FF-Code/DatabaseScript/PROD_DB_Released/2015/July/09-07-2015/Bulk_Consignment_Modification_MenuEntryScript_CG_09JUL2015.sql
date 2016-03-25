/*
Bulk Consignment Modification

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : CG Developed
AUTHOR  : SHASHANK SAXENA
DATE    : 09 JUL 2015

*/

-- For UDAAN-CONFIG-ADMIN 
INSERT INTO ff_d_app_scrn VALUES(355, 355, 'Bulk Consignment Modification', 'Bulk Consignment Modification', 'Billing', 'Billing', null, 'bulkCustModification.do?submitName=preparePage', null, 'udaan-config-admin', 'E', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(558,294,7, 'A', 'Bulk Consignment Modification', 355, 'N', 'udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_scrn VALUES(356, 356,'Bulk Consignment Modification', 'Bulk Consignment Modification', 'Billing', 'Billing', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=355&moduleName=Billing&appName=udaan-config-admin', null, 'centralized', 'E', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(559,376,7,'A','Bulk Consignment Modification',356,'N','udaan-web','A');