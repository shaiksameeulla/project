/*
Screen Name : DRS Closure Summary
Request Type: Client
Author      : Shaheedul Islam. A
Created date: 26-Aug-2015

*/

-- Udaan Report--

insert into ff_d_app_scrn values (428,428,'DRS Closure Summary','DRS Closure Summary','Report','Report',null,'stationWiseDRSReport.htm',null,'udaan-report','E',null,curdate(),null,curdate(),'N','N',null,null,null,null,'A');

insert into ff_d_app_menu values (646,515,9,'A','DRS Closure Summary',428,'N','udaan-report','A');

-- Udaan Config --

insert into ff_d_app_scrn values (429,429,'DRS Closure Summary','DRS Closure Summary','Report','Report',null,'login.do?submitName=silentLoginToApp&screenCode=426&moduleName=Reports&appName=udaan-report',null,'centralized','E',null,curdate(),null,curdate(),'N','N',null,null,null,null,'A');

insert into ff_d_app_menu values (647,517,9,'A','DRS Closure Summary',429,'N','udaan-config-admin','A');

-- Udaan Web---

insert into ff_d_app_menu values (648,611,9,'A','DRS Closure Summary',427,'N','udaan-web','A');