/*
Active Inactive Menus

AUTHOR          : SHASHANK SAXENA
CREATION DATE   : 18 MAR 2015
UPDATION DATE   : 27 MAR 2015
*/

/**/
ALTER TABLE ff_d_app_menu ADD COLUMN STATUS enum('A','I');
UPDATE ff_d_app_menu SET STATUS = 'A', DT_TO_BRANCH = 'N';

/**/
-- UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 380; Consumed for RATE WEB
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 483;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 125;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 132;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 133;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 134;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 440;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 126;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 129;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 513;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 499;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 507;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 473;
UPDATE ff_d_app_menu SET STATUS = 'I', DT_TO_BRANCH = 'N' WHERE MENU_ID = 524;
