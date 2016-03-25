/*1. Data change requested by pravin Meher */
set foreign_key_checks=0;
truncate table ff_d_rate_component;
truncate table ff_d_rate_component_calculated;

/*Data for the table ff_d_rate_component */

insert into ff_d_rate_component values 
(1,'FNSLB','Final Slab Rate','G','C','B',0.00,'C','N',3,'Y'),
(2,'FSCHG','Fuel Surcharge','G','C','A',20.00,'P','Y',15,'Y'),
(3,'RSKCG','Risk Surcharge','E','C','A',0.00,'P','Y',6,'Y'),
(4,'TPCHG','To Pay Charge','G','F','A',0.00,'C','Y',8,'Y'),
(5,'CODCG','COD Charges','E','F','A',0.00,'P','Y',9,'Y'),
(6,'PRHCG','Parcel Handling Charge','G','F','A',0.00,'C','Y',10,'Y'),
(7,'ARHCG','Airport Handling Charge','G','C','A',0.00,'P','Y',11,'Y'),
(8,'DCHCG','Document Handling Charge','G','F','A',0.00,'C','Y',12,'Y'),
(9,'RTODN','RTO Discount','E','F','A',0.00,'C','N',4,'Y'),
(10,'OTSCG','Other charges/ Special Charges','E','F','A',0.00,'C','Y',13,'Y'),
(11,'DSCNT','Discount','E','F','A',0.00,'P','N',5,'Y'),
(12,'SRVTX','Service Tax','E','C','T',12.00,'P','Y',17,'Y'),
(13,'EDUCS','Education CESS','E','C','T',2.00,'P','Y',18,'Y'),
(14,'HEDCS','Higher Education CES','E','C','T',1.00,'P','Y',19,'Y'),
(15,'STTAX','State Tax','E','C','T',10.00,'P','Y',20,'Y'),
(16,'SCSTX','Surcharge on State Tax','E','C','T',0.50,'P','Y',21,'Y'),
(17,'OCTRI','Octroi ','E','F','A',0.00,'C','Y',22,'Y'),
(18,'OCSCG','Service charge on Octroi','E','C','A',5.00,'P','Y',23,'Y'),
(19,'OCSTG','Service tax on Octroi service Charge','E','C','T',12.00,'P','Y',24,'Y'),
(20,'EDOSG','Education cess on Octro service Charge','E','C','T',2.00,'P','Y',25,'Y'),
(21,'HEDOG','Higher education CESS on Octro service Charge','E','C','T',1.00,'P','Y',26,'Y'),
(22,'TOTBT','Total Without Tax','G','C','A',0.00,'C','N',16,'Y'),
(23,'GTTAX','Grand Total including Tax','G','C','A',0.00,'C','N',29,'Y'),
(24,'LCCHG','LC Charge','G','F','A',0.00,'C','N',30,'Y'),
(25,'DCDVL','Declared Value','E','F','B',0.00,'C','Y',1,'Y'),
(26,'SLBRT','Slab Rate','E','F','B',0.00,'C','N',2,'Y'),
(27,'FSRSA','Final Slab rate added to Risk Surcharge','G','C','B',0.00,'C','N',7,'Y'),
(28,'LCAMT','LC Amount ','G','F','B',0.00,'C','Y',31,'Y'),
(29,'OCSRT','Octroi Sales Tax on Octroi Service Charge','E','C','T',10.00,'P','Y',27,'Y'),
(30,'OCSST','Octroi Surcharge on State Tax on Octroi Service Charge','E','C','T',2.00,'P','Y',28,'Y'),
(31,'CODA','COD Amount','E','F','B',0.00,'C','Y',32,'Y'),
(32,'FCPO','Final slab rate + COD charges + Parcel handling charges + other charges','G','C','B',0.00,'C','Y',14,'N');

/*Table structure for table ff_d_rate_component_calculated */


/*Data for the table ff_d_rate_component_calculated */

insert into ff_d_rate_component_calculated values 
(1,1,9,'PS',2,'Y'),
(2,1,11,'PS',3,'Y'),
(3,32,1,'A',1,'Y'),
(4,3,25,'P',1,'Y'),
(5,22,1,'A',1,'Y'),
(6,22,24,'A',2,'Y'),
(7,22,2,'A',3,'Y'),
(8,22,3,'A',4,'Y'),
(9,22,4,'A',5,'Y'),
(10,22,5,'A',6,'Y'),
(11,22,6,'A',7,'Y'),
(12,22,7,'A',8,'Y'),
(13,22,8,'A',9,'Y'),
(14,22,10,'A',10,'Y'),
(15,12,22,'P',1,'Y'),
(16,13,12,'P',1,'Y'),
(17,14,12,'P',1,'Y'),
(18,15,22,'P',1,'Y'),
(19,16,15,'P',1,'Y'),
(20,18,17,'P',1,'Y'),
(21,19,18,'P',1,'Y'),
(22,20,18,'P',1,'Y'),
(23,21,18,'P',1,'Y'),
(24,23,22,'A',1,'Y'),
(25,23,12,'A',2,'Y'),
(26,23,13,'A',3,'Y'),
(27,23,14,'A',4,'Y'),
(28,23,15,'A',5,'Y'),
(29,23,16,'A',6,'Y'),
(30,23,17,'A',7,'Y'),
(31,23,18,'A',8,'Y'),
(32,23,19,'A',9,'Y'),
(33,23,20,'A',10,'Y'),
(34,11,26,'PS',1,'Y'),
(35,1,26,'A',1,'Y'),
(36,27,1,'A',1,'Y'),
(37,27,2,'A',2,'Y'),
(38,7,27,'P',1,'Y'),
(39,29,18,'P',1,'Y'),
(40,30,18,'P',1,'Y'),
(41,32,5,'A',2,'N'),
(42,32,6,'A',3,'N'),
(43,32,10,'A',4,'N'),
(45,2,32,'P',1,'N');
set foreign_key_checks=1;