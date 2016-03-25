DROP TABLE IF EXISTS `ff_f_brr_summay_all_india_datewise_report_data`;
CREATE TABLE `ff_f_brr_summay_all_india_datewise_report_data` (
  `DATA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONSG_ID` int(11) DEFAULT NULL,
  `CONSG_NO` varchar(25) DEFAULT NULL,
  `PRODUCT` int(11) DEFAULT NULL,
  `CONSG_STATUS_ID` int(11) DEFAULT NULL,
  `CONSG_REC_DATE` datetime DEFAULT NULL,
  `DELIVERY_TIME` datetime DEFAULT NULL,
  `CONSIGNMENT_TYPE_ID` int(11) DEFAULT NULL,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `LOAD_NO` int(11) DEFAULT NULL,
  `DELIVERY_OFFICE` int(11) DEFAULT NULL,
  `DESTINATION_CITY` int(11) DEFAULT NULL,
  `DEL_REGION` int(11) DEFAULT NULL,
  PRIMARY KEY (`DATA_ID`),
  UNIQUE KEY `idx_unique` (`CONSG_REC_DATE`,`DELIVERY_OFFICE`,`CONSG_ID`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CN` (`CONSG_ID`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_PRODUCT` (`PRODUCT`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CS` (`CONSG_STATUS_ID`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CT` (`CONSIGNMENT_TYPE_ID`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CR` (`CATEGORY_ID`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_LOAD` (`LOAD_NO`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEL_OFF` (`DELIVERY_OFFICE`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEST_CITY` (`DESTINATION_CITY`),
  KEY `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEL_REGION` (`DEL_REGION`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CN` FOREIGN KEY (`CONSG_ID`) REFERENCES `ff_f_consignment` (`CONSG_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CR` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `ff_d_category_for_reports` (`CATEGORY_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CS` FOREIGN KEY (`CONSG_STATUS_ID`) REFERENCES `ff_d_consignment_status_for_reports` (`CONSG_STATUS_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_CT` FOREIGN KEY (`CONSIGNMENT_TYPE_ID`) REFERENCES `ff_d_consignment_type` (`CONSIGNMENT_TYPE_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEL_OFF` FOREIGN KEY (`DELIVERY_OFFICE`) REFERENCES `ff_d_office` (`OFFICE_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEL_REGION` FOREIGN KEY (`DEL_REGION`) REFERENCES `ff_d_region` (`REGION_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_DEST_CITY` FOREIGN KEY (`DESTINATION_CITY`) REFERENCES `ff_d_city` (`CITY_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_LOAD` FOREIGN KEY (`LOAD_NO`) REFERENCES `ff_d_load_lot` (`LOAD_LOT_ID`),
  CONSTRAINT `FK_BRR_SUMMAY_ALL_INDIA_DATEWISE_REPORT_DATA_PRODUCT` FOREIGN KEY (`PRODUCT`) REFERENCES `ff_d_product` (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



