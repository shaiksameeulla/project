/*
UMC - MENU Release - Scripts for Branch
AUTHOR		: SHASHANK SAXENA
CREATED DATE: 03 APR 2105
*/


-- Creates new column  in "ff_d_app_scrn" table and sets 'A' for all
ALTER TABLE ff_d_app_scrn ADD COLUMN STATUS enum('A','I');
UPDATE ff_d_app_scrn SET STATUS = 'A';

-- Creates new column  in "ff_d_app_menu" table and sets 'A' for all
ALTER TABLE ff_d_app_menu ADD COLUMN STATUS enum('A','I');
UPDATE ff_d_app_menu SET STATUS = 'A';