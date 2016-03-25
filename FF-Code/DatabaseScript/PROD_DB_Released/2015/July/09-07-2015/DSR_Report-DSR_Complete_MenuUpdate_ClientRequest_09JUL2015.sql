/*
DSR Report  >>  DSR - Complete

SCRIPT TYPE : Menu-Screen Update Script
TYPE    : Client Request
AUTHOR  : SHASHANK SAXENA
DATE    : 09 JUL 2015

*/


UPDATE ff_d_app_scrn
   SET SCREEN_NAME = 'DSR – Complete', SCREEN_DESCRIPTION = 'DSR – Complete', DT_TO_BRANCH = 'N'
 WHERE SCREEN_NAME = 'DSR Report' AND SCREEN_ID = 197;

UPDATE ff_d_app_scrn
   SET SCREEN_NAME = 'DSR – Complete', SCREEN_DESCRIPTION = 'DSR – Complete', DT_TO_BRANCH = 'N'
 WHERE SCREEN_NAME = 'DSR Report' AND SCREEN_ID = 234;

UPDATE ff_d_app_menu
   SET MENU_LABEL = 'DSR – Complete', DT_TO_BRANCH = 'N'
 WHERE MENU_LABEL = 'DSR Report' AND MENU_ID = 353;

UPDATE ff_d_app_menu
   SET MENU_LABEL = 'DSR – Complete', DT_TO_BRANCH = 'N'
 WHERE MENU_LABEL = 'DSR Report' AND MENU_ID = 465;

UPDATE ff_d_app_menu
   SET MENU_LABEL = 'DSR – Complete', DT_TO_BRANCH = 'N'
 WHERE MENU_LABEL = 'DSR Report' AND MENU_ID = 474;