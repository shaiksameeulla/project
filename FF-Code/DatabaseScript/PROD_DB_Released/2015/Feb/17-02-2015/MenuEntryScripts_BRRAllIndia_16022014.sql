-- Menu Insertion for UDAAN-WEB [Silent Login]

UPDATE ff_d_app_menu
   SET EMBEDDED_IN_MENU = 446, APP_SCREEN = 232, DT_TO_BRANCH = 'N'
 WHERE MENU_ID = 485;

-- Menu Insertion for UDAAN-CONFIG-ADMIN [Silent Login]

INSERT INTO ff_d_app_menu
VALUES (519,
        324,
        9,
        'A',
        'BRR All India',
        232,
        'N',
        'udaan-config-admin');

-- Menu Insertion for UDAAN-REPORT [Self Login]

INSERT INTO ff_d_app_menu
VALUES (520,
        322,
        9,
        'A',
        'BRR All India',
        230,
        'N',
        'udaan-report');