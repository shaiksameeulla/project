UPDATE ff_d_app_scrn
   SET APP_NAME = 'udaan-report'
 WHERE SCREEN_ID = 322;

UPDATE ff_d_app_menu
   SET EMBEDDED_IN_MENU = 376,
       POSITION = 1,
       MENU_TYPE = 'A',
       MENU_LABEL = 'Bill Printing',
       APP_SCREEN = '2',
       DT_TO_BRANCH = 'N',
       APP_NAME = 'udaan-web',
       STATUS = 'A'
 WHERE MENU_ID = 377;

UPDATE ff_d_app_menu
   SET EMBEDDED_IN_MENU = 294,
       POSITION = 1,
       MENU_TYPE = 'A',
       MENU_LABEL = 'Bill Printing',
       APP_SCREEN = '2',
       DT_TO_BRANCH = 'N',
       APP_NAME = 'udaan-config-admin',
       STATUS = 'A'
 WHERE MENU_ID = 295;

INSERT INTO ff_d_app_menu(MENU_ID,
                          EMBEDDED_IN_MENU,
                          POSITION,
                          MENU_TYPE,
                          MENU_LABEL,
                          APP_SCREEN,
                          DT_TO_BRANCH,
                          APP_NAME,
                          STATUS)
VALUES (554,
        352,
        5,
        'A',
        'Bill Printing',
        322,
        'N',
        'udaan-report',
        'A');