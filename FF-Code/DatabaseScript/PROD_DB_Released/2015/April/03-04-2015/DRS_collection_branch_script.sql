use CorpUDAAN;
ALTER TABLE `ff_f_delivery_dtls` MODIFY COLUMN `DELIVERY_STATUS` ENUM('O','D','P','S') NOT NULL;
ALTER TABLE `ff_f_delivery_dtls` ADD COLUMN `OFFICE_CODE` VARCHAR(10) AFTER `SAP_TIMESTAMP`;
ALTER TABLE `ff_f_delivery_dtls` ADD COLUMN `PRODUCT_ID` INT(11) UNSIGNED AFTER `OFFICE_CODE`;

ALTER TABLE `ff_f_delivery_dtls` MODIFY COLUMN `OFFICE_CODE` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'DRS PREPARED OFFICE',
 MODIFY COLUMN `PRODUCT_ID` INT(11) UNSIGNED DEFAULT NULL COMMENT 'CONSIGNMENT PRODUCT';

update ff_f_delivery_dtls d set d.product_id = (select c.PRODUCT from ff_f_consignment c where c.consg_id=d.consignment_id) ;

UPDATE ff_f_delivery_dtls d  SET d.OFFICE_CODE =(select  o.office_code from ff_f_delivery pd inner join ff_d_office o on pd.CREATED_OFFICE_ID=o.office_id where pd.delivery_id=d.delivery_id ) ;
