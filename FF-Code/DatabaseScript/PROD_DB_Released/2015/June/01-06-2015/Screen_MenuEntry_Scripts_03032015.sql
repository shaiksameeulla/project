/*
Scripts for Screen and Menu Entry 

CR    : Location Search
DATED : 03 March 2015
AUTHER: SHASHANK SAXENA
*/

-- Script for Entry of screen in 'ff_d_app_scrn' table
INSERT INTO ff_d_app_scrn VALUES(350,'PCLS','Location Search','Location Search Functionality', 'MISC','MISC',null,'locationSearch.do?submitName=prepareSearchPage', 'submitName=prepareSearchPage','udaan-web','E',null,now(),null,now(),'N','N',null,null,null,null,'A');

-- Script for Entry of Screen in Menu for udaan-web  [Utilities >> MISC >> Location Search]
INSERT INTO ff_d_app_menu VALUES(550,387,2,'A','Location Search',350,'N','udaan-web','A');