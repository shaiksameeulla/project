DROP TABLE IF EXISTS ff_mv_branch_pincode_servicability;
CREATE TABLE `ff_mv_branch_pincode_servicability` (
  `SERVICABILITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OFFICE_ID` int(11) DEFAULT NULL,
  `OFFICE_CODE` varchar(20) DEFAULT NULL,
  `OFFICE_TYPE_ID` int(11) DEFAULT NULL,
  `OFFICE_TYPE_CODE` varchar(3) DEFAULT NULL,
  `PINCODE_ID` int(11) DEFAULT NULL,
  `PINCODE` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`SERVICABILITY_ID`),
  KEY `fk_branch_pincode_servicability_office` (`OFFICE_ID`),
  KEY `fk_branch_pincode_servicability_office_type` (`OFFICE_TYPE_ID`),
  KEY `fk_branch_pincode_servicability_pincode` (`PINCODE_ID`),
  CONSTRAINT `fk_branch_pincode_servicability_office` FOREIGN KEY (`OFFICE_ID`) REFERENCES `ff_d_office` (`OFFICE_ID`),
  CONSTRAINT `fk_branch_pincode_servicability_office_type` FOREIGN KEY (`OFFICE_TYPE_ID`) REFERENCES `ff_d_office_type` (`OFFICE_TYPE_ID`),
  CONSTRAINT `fk_branch_pincode_servicability_pincode` FOREIGN KEY (`PINCODE_ID`) REFERENCES `ff_d_pincode` (`PINCODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

