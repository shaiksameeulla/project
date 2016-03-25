/*
Error Handling

SCRIPT TYPE : Menu-Screen Entry Script
TYPE    : CG Developed
AUTHOR  : SHASHANK SAXENA
DATE    : 13 JUL 2015

*/

-- For UDAAN CONFIG ADMIN MENU 
INSERT INTO ff_d_app_scrn VALUES(359, 359, 'Error Handling', 'Error Handling', 'MISC', 'MISC', null, 'errorHandling.do?submitName=viewErrorHandlingScreen', null, 'udaan-config-admin', 'E', null, CURDATE() , null, CURDATE(), 'N', 'N',null,null,null,null,'A');
INSERT INTO ff_d_app_menu VALUES(563,308,6, 'A', 'Error Handling', 359, 'N', 'udaan-config-admin','A');



