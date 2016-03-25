use CorpUDAAN;
CREATE TABLE  `ff_d_airline` (
  `AIRLINE_ID` int(11) NOT NULL AUTO_INCREMENT,
`AIRLINE_CODE` varchar(20) NOT NULL  COMMENT ' airline Code which uniquely identifies the airline ,example AI means Air Idia,SJ means Spice jet etc',
  `AIRLINE_NAME` varchar(20) NOT NULL  COMMENT ' airline name such as Air Idia,Spice jet etc',
  `CREATION_DATE` date DEFAULT NULL COMMENT ' creation date of the record',
`RECORD_STATUS` enum('A','I') NOT NULL DEFAULT 'A' COMMENT ' A - Active, I - Inactive',
  `DT_TO_BRANCH` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT ' Y- data transferred to branch, N - data Not transferred to Branch',
  PRIMARY KEY (`AIRLINE_ID`),
  UNIQUE KEY `ff_d_airline_unq_indx1` (`AIRLINE_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 ;
