DROP TABLE IF EXISTS ff_aud_report_data_refresh_audit;
CREATE TABLE `ff_aud_report_data_refresh_audit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROC_NAME` varchar(50) DEFAULT NULL,
  `OPERATION` varchar(100) DEFAULT NULL,
  `EXEC_DATE` datetime DEFAULT NULL,
  `START_TIME` time DEFAULT NULL,
  `END_TIME` time DEFAULT NULL,
  `ROWS_AFFECTED` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

