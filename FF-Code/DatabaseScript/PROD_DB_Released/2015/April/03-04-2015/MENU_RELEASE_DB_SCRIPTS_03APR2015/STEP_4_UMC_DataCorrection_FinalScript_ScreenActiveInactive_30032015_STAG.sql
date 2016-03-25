/*
Active Inactive Screens

AUTHOR          : SHASHANK SAXENA
CREATION DATE   : 24 MAR 2015
UPDATION DATE   : 30 MAR 2015
*/

/*Adding Column in ff_d_app_scrn 
Name  : STATUS
Type  : enum(I,A)
*/
ALTER TABLE ff_d_app_scrn ADD COLUMN STATUS enum('A','I');
UPDATE ff_d_app_scrn SET STATUS = 'A', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N';

/*
Screen-Ids needs to be In_Active
*/
UPDATE ff_d_app_scrn SET STATUS = 'I', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 34;
UPDATE ff_d_app_scrn SET STATUS = 'I', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 183;
UPDATE ff_d_app_scrn SET STATUS = 'I', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 184;
UPDATE ff_d_app_scrn SET STATUS = 'I', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 141;
UPDATE ff_d_app_scrn SET STATUS = 'I', UPDATE_DATE = CURDATE(), DT_TO_BRANCH = 'N' WHERE SCREEN_ID = 161;