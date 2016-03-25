/*New table introduced on 28/11/2013 */
DROP TABLE IF EXISTS `ff_d_product_customer_type_map`;

CREATE TABLE `ff_d_product_customer_type_map` (
  `PRODUCT_CUSTOMER_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL,
  `CUSTOMER_TYPE_ID` int(11) NOT NULL,
  `DT_TO_BRANCH` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`PRODUCT_CUSTOMER_TYPE_ID`),
  KEY `FK_product_customer_type_map_1` (`PRODUCT_ID`),
  KEY `FK_product_customer_type_map_2` (`CUSTOMER_TYPE_ID`),
  CONSTRAINT `FK_product_customer_type_map_1` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `ff_d_product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_product_customer_type_map_2` FOREIGN KEY (`CUSTOMER_TYPE_ID`) REFERENCES `ff_d_customer_type` (`CUSTOMER_TYPE_ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

/*Data for the table `ff_d_product_customer_type_map` */

insert into `ff_d_product_customer_type_map` values 
(1,11,1,'N'),
(2,6,1,'N'),
(3,3,1,'N'),
(4,1,1,'N'),
(5,5,2,'N'),
(6,3,3,'N'),
(7,3,4,'N'),
(8,6,4,'N'),
(9,8,4,'N'),
(10,13,4,'N'),
(11,12,4,'N'),
(12,14,4,'N'),
(13,7,5,'N'),
(14,3,6,'N'),
(15,6,6,'N'),
(16,8,6,'N'),
(17,13,6,'N'),
(18,12,6,'N'),
(19,14,6,'N'),
(20,2,7,'N'),
(21,9,8,'N'),
(22,4,9,'N'),
(23,5,9,'N'),
(24,4,10,'N'),
(25,5,10,'N'),
(26,13,11,'N'),
(27,5,12,'N'),
(28,6,12,'N'),
(29,8,12,'N'),
(30,13,12,'N'),
(31,8,13,'N'),
(32,13,13,'N'),
(33,3,15,'N'),
(34,6,15,'N'),
(35,8,15,'N'),
(36,13,15,'N'),
(37,12,15,'N'),
(38,14,15,'N');
