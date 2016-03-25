/*Niharika - insert script for Manual Download:*/

Use CorpUDAAN;

INSERT INTO ff_d_app_scrn VALUES(263, '263', 'Manual Download', 'Manual Download', 'DOWNLOAD', 'DOWNLOAD', '/udaan-config-admin//images//PickupManager.png', 'login.do?submitName=silentLoginToApp&screenCode=263&moduleName=DOWNLOAD&appName=udaan-central-server', 'bcunAuthenticServlet.ff', 'udaan-config-admin','E',NULL ,'',NULL ,'', 'N','Y',NULL ,'',NULL ,NULL);

INSERT INTO ff_d_app_menu VALUES(512, 308, 5, 'A', 'BCUN Download', 263, 'Y', 'udaan-config-admin');