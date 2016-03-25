/*
Scripts for Menu Entry for "Delivery Runsheet Summary Report"
Type    : Client Request
AUTHOR  : Shashank Saxena
DATE    : 10 March 2015
*/

-- For UDAAN REPORT MENU 

INSERT INTO ff_d_app_scrn VALUES(404, 404, 'DRS Summary', 'Delivery Runsheet Summary Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'deliveryRunsheetSummaryReport.htm', null, 'udaan-report', 'E', null, now(), null, now(), 'N', 'N',null,null,null,null);
INSERT INTO ff_d_app_menu VALUES(609, 515, 2, 'A', 'Delivery Runsheet Summary Report', 404, 'N', 'udaan-report');

-- For UDAAN-CONFIG-ADMIN

INSERT INTO ff_d_app_scrn VALUES(405, 405, 'DRS Summary', 'Delivery Runsheet Summary Report', 'Report', 'Report', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=404&moduleName=Reports&appName=udaan-report', null, 'centralized', 'E', null, now(), null, now(), 'N', 'N',null,null,null,null);
INSERT INTO ff_d_app_menu VALUES(610, 517, 2, 'A','Delivery Runsheet Summary Report', 405, 'N', 'udaan-config-admin');

-- For UDAAN-WEB

INSERT INTO ff_d_app_menu VALUES(611,10,15,'L','Branch Operations', null, 'N','udaan-web');
INSERT INTO ff_d_app_menu VALUES(612, 611, 1, 'A','Delivery Runsheet Summary Report', 405, 'N', 'udaan-web');