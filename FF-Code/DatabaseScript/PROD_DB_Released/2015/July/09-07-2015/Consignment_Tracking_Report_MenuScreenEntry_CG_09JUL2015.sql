/*
Consignment Tracking Report

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : CG Developed
AUTHOR  : SHASHANK SAXENA
DATE    : 09 JUL 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(357, 357, 'Consignment Tracking Report', 'Consignment Tracking Report', 'Report', 'Report', null, 'consignmentDetailReportForFFCL.do?submitName=getConsignmentDetailReportForFFCL', null, 'udaan-report', 'E', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(560,279,7, 'A', 'Consignment Tracking Report', 357, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(358, 358,'Consignment Tracking Report', 'Consignment Tracking Report', 'Report', 'Report', null, 'login.do?submitName=silentLoginToApp&screenCode=357&moduleName=Booking&appName=udaan-report', null, 'centralized', 'E', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(561,247,7,'A','Consignment Tracking Report',358,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(562,414,7,'A','Consignment Tracking Report',358,'N','udaan-web','A');


select * from ff_d_app_menu where MENU_ID = 247;

select * from ff_d_app_menu where EMBEDDED_IN_MENU = 247;