/*Niharika*/
/*SCRIPT FOR ff_d_app_menu on 4th august 2014 for udaan-report according to prod DB
for SALES REPORTS MENU*/
USE CorpUDAAN;

update ff_d_app_menu set POSITION=2 where MENU_ID=355;

update ff_d_app_menu set POSITION=3 where MENU_ID=344;

update ff_d_app_menu set POSITION=4 where MENU_ID=335;

update ff_d_app_menu set POSITION=5 where MENU_ID=369;

update ff_d_app_menu set POSITION=6 where MENU_ID=340;

update ff_d_app_menu set POSITION=7 where MENU_ID=359;

update ff_d_app_menu set POSITION=8 where MENU_ID=343;

update ff_d_app_menu set POSITION=9 where MENU_ID=488;

update ff_d_app_menu set POSITION=11 where MENU_ID=347;

/*for Booking*/

update ff_d_app_menu set POSITION=1 where MENU_ID=282;
update ff_d_app_menu set POSITION=2 where MENU_ID=280;

/*for dispatch*/


update ff_d_app_menu set POSITION=1 where MENU_ID=289;

update ff_d_app_menu set POSITION=2,EMBEDDED_IN_MENU=283 where MENU_ID=291;

update ff_d_app_menu set POSITION=3 where MENU_ID=288;

update ff_d_app_menu set POSITION=4 where MENU_ID=284;

update ff_d_app_menu set POSITION=5 where MENU_ID=286;

update ff_d_app_menu set POSITION=6 where MENU_ID=333;

update ff_d_app_menu set APP_SCREEN=null,EMBEDDED_IN_MENU=null where MENU_ID=287;


/*for RTH*/

update ff_d_app_menu set POSITION=1 where MENU_ID=289;
/*select * from ff_d_app_menu where MENU_LABEL='RTH Validation'*/

update ff_d_app_menu set EMBEDDED_IN_MENU=302,POSITION=2 where MENU_ID=299;


/*for performance reports*/

update ff_d_app_menu set POSITION=1 where MENU_ID=498;

update ff_d_app_menu set POSITION=2 where MENU_ID=494;

update ff_d_app_menu set POSITION=3 where MENU_ID=493;

update ff_d_app_menu set POSITION=4 where MENU_ID=496;

update ff_d_app_menu set POSITION=5 where MENU_ID=495;

update ff_d_app_menu set POSITION=7 where MENU_ID=499;

update ff_d_app_menu set POSITION=8 where MENU_ID=323;

/*L-series*/

update ff_d_app_menu set POSITION=2,EMBEDDED_IN_MENU=364 where MENU_ID=287;
update ff_d_app_menu set POSITION=3 where MENU_ID=366;
update ff_d_app_menu set POSITION=4 where MENU_ID=367;
update ff_d_app_menu set POSITION=5 where MENU_ID=492;
update ff_d_app_menu set POSITION=6 where MENU_ID=491;
update ff_d_app_menu set POSITION=7 where MENU_ID=368;

/*for Billing*/
update ff_d_app_menu set POSITION=1 where MENU_ID=490;
update ff_d_app_menu set POSITION=2 where MENU_ID=489;
update ff_d_app_menu set POSITION=3 where MENU_ID=353;
update ff_d_app_menu set POSITION=4 where MENU_ID=354;

/*for ff_d_app_scrn*/
update ff_d_app_scrn set SCREEN_NAME='All Product RTO Report' where SCREEN_ID in(142,162);

update ff_d_app_scrn set SCREEN_NAME='Load Confirmation' where SCREEN_ID in(138);

/*for positions of parent menu*/

update ff_d_app_menu set POSITION=1 where MENU_ID=334;

update ff_d_app_menu set POSITION=2 where MENU_ID=273;

update ff_d_app_menu set POSITION=3 where MENU_ID=279;

update ff_d_app_menu set POSITION=4 where MENU_ID=283;

update ff_d_app_menu set POSITION=5 where MENU_ID=302;

update ff_d_app_menu set POSITION=6 where MENU_ID=322;

update ff_d_app_menu set POSITION=7 where MENU_ID=364;

update ff_d_app_menu set POSITION=8 where MENU_ID=352;

update ff_d_app_menu set EMBEDDED_IN_MENU=null where MENU_ID=290;

update ff_d_app_menu set MENU_LABEL='RTH/RTO Reports' where MENU_ID=302;

