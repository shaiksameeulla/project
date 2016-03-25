-- Insert App Screen in FF_D_APP_SCRN

insert into `ff_d_app_scrn`(`SCREEN_ID`,`SCREEN_CODE`,`SCREEN_NAME`,`SCREEN_DESCRIPTION`,`MODULE_NAME`,`MODULE_DESCRIPTION`,`IMAGE_PATH_NAME`,`URL_NAME`,`URL_PARAMS`,`APP_NAME`,`SRC_ACCESSIBLE_TO`,`CREATED_BY`,`CREATION_DATE`,`UPDATE_BY`,`UPDATE_DATE`,`IS_SRC_ASSIGN`,`DT_TO_BRANCH`,`PARENT`,`PARENT_NAME`,`LEVEL_ID`,`DISPLAY_SEQUENCE`) values (264,'264','DRS Tally','DRS Report','Report','Report','/udaan-config-admin//images//PickupManager.png','deliveryRunsheetReport.htm','   ','udaan-report','B',1,now(),1,now(),'N','N',null,null,null,null);
   
-- Insert Menu Entry in FF_D_APP_MENU
        
insert into `ff_d_app_menu`(`MENU_ID`,`EMBEDDED_IN_MENU`,`POSITION`,`MENU_TYPE`,`MENU_LABEL`,`APP_SCREEN`,`DT_TO_BRANCH`,`APP_NAME`) values (515,272,9,'L','Branch Operations',null,'N','udaan-report');
insert into `ff_d_app_menu`(`MENU_ID`,`EMBEDDED_IN_MENU`,`POSITION`,`MENU_TYPE`,`MENU_LABEL`,`APP_SCREEN`,`DT_TO_BRANCH`,`APP_NAME`) values (516,515,1,'A','Branch Operations',264,'N','udaan-report');