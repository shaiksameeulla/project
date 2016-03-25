DROP TABLE IF EXISTS ff_mv_rto_manifest_origin_branch;
CREATE TABLE `ff_mv_rto_manifest_origin_branch` (
  `CONSG_ID` int(11) NOT NULL DEFAULT '0',
  `CONSIGNMENT_MANIFESTED_ID` int(11) DEFAULT NULL,
  `MANIFEST_ID` int(11) DEFAULT NULL,
  `MANIFEST_NO` varchar(12) DEFAULT NULL,
  `MANIFEST_DATE` datetime DEFAULT NULL,
  `MANIFEST_TYPE` char(1) DEFAULT NULL,
  `OPERATING_OFFICE` int(11) DEFAULT NULL,
  `MANIFEST_LOAD_CONTENT` int(11) DEFAULT NULL,
  `MANIFEST_PROCESS_CODE` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`CONSG_ID`),
  KEY `fk_rto_manifest_consignment_manifested` (`CONSIGNMENT_MANIFESTED_ID`),
  KEY `fk_rto_manifest_manifest` (`MANIFEST_ID`),
  KEY `fk_rto_manifest_office` (`OPERATING_OFFICE`),
  KEY `fk_rto_manifest_consignment_type` (`MANIFEST_LOAD_CONTENT`),
  CONSTRAINT `fk_rto_manifest_consignment` FOREIGN KEY (`CONSG_ID`) REFERENCES `ff_f_consignment` (`CONSG_ID`),
  CONSTRAINT `fk_rto_manifest_consignment_manifested` FOREIGN KEY (`CONSIGNMENT_MANIFESTED_ID`) REFERENCES `ff_f_consignment_manifested` (`CONSIGNMENT_MANIFESTED_ID`),
  CONSTRAINT `fk_rto_manifest_consignment_type` FOREIGN KEY (`MANIFEST_LOAD_CONTENT`) REFERENCES `ff_d_consignment_type` (`CONSIGNMENT_TYPE_ID`),
  CONSTRAINT `fk_rto_manifest_manifest` FOREIGN KEY (`MANIFEST_ID`) REFERENCES `ff_f_manifest` (`MANIFEST_ID`),
  CONSTRAINT `fk_rto_manifest_office` FOREIGN KEY (`OPERATING_OFFICE`) REFERENCES `ff_d_office` (`OFFICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
