/*Niharika*/
/*Execute only in staging DBschema*/
USE `CorpUDAAN`;

/*Data for the table `ff_d_app_scrn` */

insert into `ff_d_app_scrn` values 
(241,'241','Rate Revision Analysis','Rate Revision Analysis','Report','Report','/udaan-report//images//PickupManager.png','rateRevisionAnalysisReport.do?submitName=getRateRevisionAnalysisDetailsReport','submitName=getRateRevisionAnalysisDetailsReport','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(242,'242','Rate Revision Analysis','Rate Revision Analysis','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=241&moduleName=Reports&appName=udaan-report','submitName=getRateRevisionAnalysisDetailsReport','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(243,'243','Bill Register After Transfer','Bill Register After Transfer','Report','Report','/udaan-report//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(244,'244','Bill Register After Transfer','Bill Register After Transfer','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(245,'245','Bill Reg','Bill Reg','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(246,'246','Bill Reg','Bill Reg','Report','Report','/udaan-report//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(247,'247','Party Re-conciliation','Party Re-conciliation','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(248,'248','Party Re-conciliation','Party Re-conciliation','Report','Report','/udaan-report//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(249,'249','Payment Details To Customer','Payment Details To Customer','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(250,'250','Payment Details To Customer','Payment Details To Customer','Report','Report','/udaan-report//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=225&moduleName=Reports&appName=udaan-report','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(251,'251','BRR Summary Report','BRR Summary Report','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=252&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(252,'252','BRR Summary Report','BRR Summary Report','Report','Report','/udaan-report//images//PickupManager.png','brrSummary.do?submitName=getBrrSummaryReport','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(253,'253','BRR Datewise Status Report','BRR Datewise Status Report','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=254&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(254,'254','BRR Datewise Status Report','BRR Datewise Status Report','Report','Report','/udaan-report//images//PickupManager.png','brrdatewisestatusReport.do?submitName=viewFormDetails','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(255,'255','Hit Ratio Branchwise','Hit Ratio Branchwise','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=256&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(256,'256','Hit Ratio Branchwise','Hit Ratio Branchwise','Report','Report','/udaan-report//images//PickupManager.png','hitRatioBranchwise.do?submitName=getHitRatioBranchwiseReport&reportName=BranchWise','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(257,'257','Hit Ratio Datewise','Hit Ratio Datewise','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=258&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(258,'258','Hit Ratio Datewise','Hit Ratio Datewise','Report','Report','/udaan-report//images//PickupManager.png','hitRatioBranchwise.do?submitName=getHitRatioBranchwiseReport&reportName=DateWise','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(259,'259','Origin Hit Ratio','Origin Hit Ratio','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=260&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(260,'260','Origin Hit Ratio','Origin Hit Ratio','Report','Report','/udaan-report//images//PickupManager.png','hitRatioOriginwise.do?submitName=getHitRatioOriginwiseReport','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(261,'261','BRR Status Report','BRR Status Report','Report','Report','/udaan-config-admin//images//PickupManager.png','login.do?submitName=silentLoginToApp&screenCode=262&moduleName=Reports&appName=udaan-report','submitName=preparePage','centralized','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL),

(262,'262','BRR Status Report','BRR Status Report','Report','Report','/udaan-report//images//PickupManager.png','brrReport.do?submitName=viewFormDetails&reportName=Summary','submitName=preparePage','udaan-report','E',NULL,NULL,NULL,NULL,'N','Y',NULL,NULL,NULL,NULL);



/*Data for the table `ff_d_app_menu` */

insert into `ff_d_app_menu` values 


(488,334,11,'A','Rate Revision Analysis',241,'Y','udaan-report'),

(489,352,3,'A','Bill Register After Transfer',243,'Y','udaan-report'),

(490,352,4,'A','Bill Reg',246,'Y','udaan-report'),

(491,364,5,'A','Party Re-conciliation',248,'Y','udaan-report'),

(492,364,6,'A','Payment Details To Customer',250,'Y','udaan-report'),

(493,322,1,'A','BRR Summary Report',252,'Y','udaan-report'),

(494,322,3,'A','BRR Datewise Status Report',254,'Y','udaan-report'),

(495,322,4,'A','Hit Ratio Branchwise',256,'Y','udaan-report'),

(496,322,5,'A','Hit Ratio Datewise',258,'Y','udaan-report'),

(497,322,6,'A','Origin Hit Ratio',260,'Y','udaan-report'),

(498,322,7,'A','BRR Status Report',262,'Y','udaan-report'),

(499,322,8,'A','Online Pending Datewise',NULL,'Y','udaan-report'),

(500,337,11,'A','Rate Revision Analysis',242,'Y','udaan-config-admin');
