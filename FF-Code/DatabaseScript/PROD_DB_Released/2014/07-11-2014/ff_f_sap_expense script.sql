-- SAP STAGING TABLES ALTER SCRIPT
ALTER TABLE `CorpUDAAN`.`ff_f_sap_expense` MODIFY COLUMN `TOTAL_EXPENSE` DECIMAL(13,2);
ALTER TABLE `CorpUDAAN`.`ff_f_sap_expense` MODIFY COLUMN `SERVICE_CHARGE` DOUBLE(13,2);
ALTER TABLE `CorpUDAAN`.`ff_f_sap_expense` MODIFY COLUMN `SERVICE_TAX_BASIC` DOUBLE(13,2);
ALTER TABLE `CorpUDAAN`.`ff_f_sap_expense` MODIFY COLUMN `ED_ON_SERVICE_TAX` DOUBLE(13,2);
ALTER TABLE `CorpUDAAN`.`ff_f_sap_expense` MODIFY COLUMN `HED_ON_SERVICE_TAX` DOUBLE(13,2);
ALTER TABLE `CorpUDAAN`.`ff_f_sap_liability_payment` MODIFY COLUMN `AMOUNT` DOUBLE(13,2);