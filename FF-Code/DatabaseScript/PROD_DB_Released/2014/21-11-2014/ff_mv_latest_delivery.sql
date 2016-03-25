DROP TABLE IF EXISTS ff_mv_latest_delivery;
CREATE TABLE `ff_mv_latest_delivery` (
  `CONSG_ID` int(11) NOT NULL DEFAULT '0',
  `DELIVERY_DETAIL_ID` int(11) DEFAULT NULL,
  `DELIVERY_TIME` datetime DEFAULT NULL,
  `DELIVERY_STATUS` char(1) DEFAULT NULL,
  `RECORD_STATUS` char(1) DEFAULT NULL,
  `REASON_ID` int(11) DEFAULT NULL,
  `DELIVERY_ID` int(11) DEFAULT NULL,
  `LOAD_NO` int(2) DEFAULT NULL,
  `TRANS_MODIFIED_DATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`CONSG_ID`),
  KEY `fk_latest_delivery_delivery_dtls` (`DELIVERY_DETAIL_ID`),
  KEY `fk_latest_delivery_reason` (`REASON_ID`),
  KEY `fk_latest_delivery_delivery` (`DELIVERY_ID`),
  CONSTRAINT `fk_latest_delivery_consignment` FOREIGN KEY (`CONSG_ID`) REFERENCES `ff_f_consignment` (`CONSG_ID`),
  CONSTRAINT `fk_latest_delivery_delivery` FOREIGN KEY (`DELIVERY_ID`) REFERENCES `ff_f_delivery` (`DELIVERY_ID`),
  CONSTRAINT `fk_latest_delivery_delivery_dtls` FOREIGN KEY (`DELIVERY_DETAIL_ID`) REFERENCES `ff_f_delivery_dtls` (`DELIVERY_DETAIL_ID`),
  CONSTRAINT `fk_latest_delivery_reason` FOREIGN KEY (`REASON_ID`) REFERENCES `ff_d_reason` (`REASON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
