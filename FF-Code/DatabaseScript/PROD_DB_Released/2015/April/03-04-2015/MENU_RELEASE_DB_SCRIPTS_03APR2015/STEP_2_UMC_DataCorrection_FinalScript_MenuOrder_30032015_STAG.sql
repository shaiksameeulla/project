/*
Menu Re-Ordering

AUTHOR          : SHASHANK SAXENA
CREATION DATE   : 27 FEB 2015
UPDATION DATE   : 27 FEB 2015
*/

/*
Menu Order Correction
*/

/*
############   HEAD MENU  #################
*/

-- UMC - Position > 1
UPDATE ff_d_app_menu SET POSITION = 1, DT_TO_BRANCH = 'N' WHERE MENU_ID = 135 AND APP_NAME = 'udaan-config-admin';

-- Stock Management - Position > 2
UPDATE ff_d_app_menu SET POSITION = 2, DT_TO_BRANCH = 'N' WHERE MENU_ID = 136 AND APP_NAME = 'udaan-config-admin';

-- MEC - Position > 3
UPDATE ff_d_app_menu SET POSITION = 3, DT_TO_BRANCH = 'N' WHERE MENU_ID = 137 AND APP_NAME = 'udaan-config-admin';

-- Rate Management - Position > 4
UPDATE ff_d_app_menu SET POSITION = 4, DT_TO_BRANCH = 'N' WHERE MENU_ID = 138 AND APP_NAME = 'udaan-config-admin';

-- Route Service By - Position > 5
UPDATE ff_d_app_menu SET POSITION = 5, DT_TO_BRANCH = 'N' WHERE MENU_ID = 139 AND APP_NAME = 'udaan-config-admin';

-- Held Up - Position > 6
UPDATE ff_d_app_menu SET POSITION = 6, DT_TO_BRANCH = 'N' WHERE MENU_ID = 140 AND APP_NAME = 'udaan-config-admin';

-- Tracking - Position > 7
UPDATE ff_d_app_menu SET POSITION = 7, DT_TO_BRANCH = 'N' WHERE MENU_ID = 188 AND APP_NAME = 'udaan-config-admin';

-- CRM - Position > 8
UPDATE ff_d_app_menu SET POSITION = 8, DT_TO_BRANCH = 'N' WHERE MENU_ID = 9 AND APP_NAME = 'udaan-config-admin';

-- Coloader - Position > 10
UPDATE ff_d_app_menu SET POSITION = 10, DT_TO_BRANCH = 'N' WHERE MENU_ID = 224 AND APP_NAME = 'udaan-config-admin';

-- Reports - Position > 11
UPDATE ff_d_app_menu SET POSITION = 11, DT_TO_BRANCH = 'N' WHERE MENU_ID = 240 AND APP_NAME = 'udaan-config-admin';

-- Billing - Position > 12
UPDATE ff_d_app_menu SET POSITION = 12, DT_TO_BRANCH = 'N' WHERE MENU_ID = 294 AND APP_NAME = 'udaan-config-admin';

-- Complaints - Position > 13
UPDATE ff_d_app_menu SET POSITION = 13, DT_TO_BRANCH = 'N' WHERE MENU_ID = 318 AND APP_NAME = 'udaan-config-admin';

-- Leads - Position > 14
UPDATE ff_d_app_menu SET POSITION = 14, DT_TO_BRANCH = 'N' WHERE MENU_ID = 310 AND APP_NAME = 'udaan-config-admin';

-- MISC - Position > 15
UPDATE ff_d_app_menu SET POSITION = 15, DT_TO_BRANCH = 'N' WHERE MENU_ID = 308 AND APP_NAME = 'udaan-config-admin';


/*
############   SUB MENU  #################
*/


-- Ecommerce Contract-Super User Edit [118] EMBEDDED_IN_MENU = CONTRACT [196]
UPDATE ff_d_app_menu SET POSITION = 6, DT_TO_BRANCH = 'N' WHERE MENU_ID = 317 AND APP_NAME = 'udaan-config-admin';