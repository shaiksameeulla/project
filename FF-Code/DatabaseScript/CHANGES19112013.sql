/*1. Himal */

alter table ff_f_collection_entries  modify COLLECTION_TYPE  varchar(100) DEFAULT NULL COMMENT 'EXPENSE DESCRIPTION';

/*2. Chandrakant  */
alter table ff_f_sap_vendor add EXCEPTION varchar(500);