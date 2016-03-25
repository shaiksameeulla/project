/*
Hit Ratio Product Wise 

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : CG Developed
AUTHOR  : SHASHANK SAXENA
DATE    : 23 JUL 2015

*/

-- For UDAAN REPORT MENU 
INSERT INTO ff_d_app_scrn VALUES(360, 360, 'Hit Ratio Product Wise ', 'Hit Ratio Product Wise ', 'Report', 'Report', null, 'hitRatioProductwise.do?submitName=getHitRatioProductwiseReport', null, 'udaan-report', 'E', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(538,322,13, 'A', 'Hit Ratio Product Wise ', 360, 'N', 'udaan-report','A');

-- For UDAAN-CONFIG-ADMIN
INSERT INTO ff_d_app_scrn VALUES(361, 361,'Hit Ratio Product Wise ', 'Hit Ratio Product Wise ', 'Report', 'Report', null, 'login.do?submitName=silentLoginToApp&screenCode=360&moduleName=Reports&appName=udaan-report', null, 'centralized', 'E', null, CURDATE(), null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(539,324,13,'A','Hit Ratio Product Wise ',361,'N','udaan-config-admin','A');

-- For UDAAN-WEB
INSERT INTO ff_d_app_menu VALUES(540,446,13,'A','Hit Ratio Product Wise ',361,'N','udaan-web','A');