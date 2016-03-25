To rename existing report

UPDATE ff_d_app_scrn
   SET SCREEN_NAME = 'Gatepass Report',
       SCREEN_DESCRIPTION = 'Gatepass Report',
       UPDATE_DATE = now(),
       DT_TO_BRANCH = 'N'
WHERE SCREEN_ID = 134;

UPDATE ff_d_app_scrn
   SET SCREEN_NAME = 'Gatepass Report',
       SCREEN_DESCRIPTION = 'Gatepass Report',
       UPDATE_DATE = now(),
       DT_TO_BRANCH = 'N'
WHERE SCREEN_ID = 154;





To Rename existing report

update ff_d_app_menu SET
  MENU_LABEL = 'Gatepass Report'
  ,DT_TO_BRANCH = 'N'
WHERE MENU_ID = 252 and APP_NAME = 'udaan-config-admin';

update ff_d_app_menu SET
  MENU_LABEL = 'Gatepass Report'
  ,DT_TO_BRANCH = 'N'
WHERE MENU_ID = 284 and APP_NAME = 'udaan-report';

update ff_d_app_menu SET
  MENU_LABEL = 'Gatepass Report'
  ,DT_TO_BRANCH = 'N'
WHERE MENU_ID = 419 and APP_NAME = 'udaan-web';



UPDATE ff_d_app_scrn
   SET URL_NAME = 'brrDetailReport.do?submitName=getBrrDetailReport',
       URL_PARAMS = 'submitName=getBrrDetailReport'
WHERE SCREEN_NAME = 'BRR Detail' AND SCREEN_ID = 229;

-- For Silent Login

UPDATE ff_d_app_scrn
   SET URL_PARAMS = 'submitName=getBrrDetailReport'
WHERE SCREEN_NAME = 'BRR Detail' AND SCREEN_ID = 231;

-- For Menu Entry

-- For Udaan Report
-- Rest of the entries are already available


INSERT INTO ff_d_app_menu(MENU_ID,
                          EMBEDDED_IN_MENU,
                          POSITION,
                          MENU_TYPE,
                          MENU_LABEL,
                          APP_SCREEN,
                          DT_TO_BRANCH,
                          APP_NAME)
VALUES (519,
        322,
        9,
        'A',
        'BRR Detail',
        231,
        'N',
        'udaan-config-admin');
