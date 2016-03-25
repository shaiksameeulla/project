/*Niharika*/
/*menu scripts for report in config-udaan for production*/
/*for sales reports menu*/
USE CorpUDAAN;

update ff_d_app_menu set POSITION=2 where MENU_ID=356;

update ff_d_app_menu set POSITION=3 where MENU_ID=462;

update ff_d_app_menu set POSITION=4 where MENU_ID=338;

update ff_d_app_menu set POSITION=5 where MENU_ID=370;

update ff_d_app_menu set POSITION=6 where MENU_ID=460;

update ff_d_app_menu set POSITION=7 where MENU_ID=360;

update ff_d_app_menu set POSITION=8 where MENU_ID=461;

update ff_d_app_menu set POSITION=9 where MENU_ID=500;

update ff_d_app_menu set POSITION=11 where MENU_ID=348;

/*for booking*/

update ff_d_app_menu set POSITION=1 where MENU_ID=250;
update ff_d_app_menu set POSITION=2 where MENU_ID=248;

/*for dispatch*/

update ff_d_app_menu set POSITION=1 where MENU_ID=257;
update ff_d_app_menu set POSITION=2,EMBEDDED_IN_MENU=251 where MENU_ID=259;

update ff_d_app_menu set POSITION=3 where MENU_ID=256;

update ff_d_app_menu set POSITION=4 where MENU_ID=252;

update ff_d_app_menu set POSITION=5 where MENU_ID=254;

update ff_d_app_menu set POSITION=6 where MENU_ID=336;

/*for RTO*/
update ff_d_app_menu set EMBEDDED_IN_MENU=266,POSITION=2 where MENU_ID=263;


/*for performance reports*/

update ff_d_app_menu set POSITION=1 where MENU_ID=506;

update ff_d_app_menu set POSITION=2 where MENU_ID=502;

update ff_d_app_menu set POSITION=3 where MENU_ID=501;

update ff_d_app_menu set POSITION=4 where MENU_ID=504;

update ff_d_app_menu set POSITION=5 where MENU_ID=503;

update ff_d_app_menu set POSITION=6 where MENU_ID=505;

update ff_d_app_menu set POSITION=7 where MENU_ID=507;
update ff_d_app_menu set POSITION=8 where MENU_ID=325;


update ff_d_app_menu set EMBEDDED_IN_MENU=null,APP_SCREEN=null where MENU_ID in(486,501);

/*for L-series*/

update ff_d_app_menu set EMBEDDED_IN_MENU=464 where MENU_ID=255;

update ff_d_app_menu set POSITION=3 where MENU_ID=468;
update ff_d_app_menu set POSITION=4 where MENU_ID=469;

insert into ff_d_app_menu values(513,464,5,'A','Payment Details To Customer',null,'N','udaan-config-admin');
insert into ff_d_app_menu values(514,464,6,'A','Party Reconcilation',null,'N','udaan-config-admin');

update ff_d_app_menu set POSITION=7 where MENU_ID=470;

/*for Billing*/
update ff_d_app_menu set POSITION=1 where MENU_ID=509;
update ff_d_app_menu set POSITION=2 where MENU_ID=508;
update ff_d_app_menu set POSITION=3 where MENU_ID=465;
update ff_d_app_menu set POSITION=4 where MENU_ID=466;


/*sequence of parent menu*/

update ff_d_app_menu set POSITION=1 where MENU_ID=337;

update ff_d_app_menu set POSITION=2 where MENU_ID=241;

update ff_d_app_menu set POSITION=3 where MENU_ID=247;

update ff_d_app_menu set POSITION=4 where MENU_ID=251;

update ff_d_app_menu set POSITION=5 where MENU_ID=266;

update ff_d_app_menu set POSITION=6 where MENU_ID=324;

update ff_d_app_menu set POSITION=7 where MENU_ID=464;

update ff_d_app_menu set POSITION=8 where MENU_ID=463;

update ff_d_app_menu set EMBEDDED_IN_MENU=null where MENU_ID=258;

update ff_d_app_menu set MENU_LABEL='RTH/RTO Reports' where MENU_ID=266;

