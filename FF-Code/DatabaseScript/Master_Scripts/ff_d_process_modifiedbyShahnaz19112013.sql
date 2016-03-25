

USE `udaan_gold`;

/*Data for the table `ff_d_process` */

set foreign_key_checks=0;

truncate table ff_d_process;

insert into `ff_d_process` values 
(1,'UPPU','Pickup - Update Pickup','Pickup / Update Pickup',NULL,'2013-11-19',NULL,'2013-11-19','Y',10,'Picked up from Customer {custName}, {city}','Picked from Customer {custName}, {city}'),
(2,'BOOK','Booking','Booking',NULL,'2013-11-19',NULL,'2013-11-19','Y',20,'Booked at Office : {originOff} in {city} having Weight : {weight} Kg., No. Of pieces : {noOfPieces}','Booked at {originOff}, {city}, Weight {weight}, {noOfPieces}'),
(3,'OPKT','Out Packet for DOX','Out Packet for DOX',NULL,'2013-11-19',NULL,'2013-11-19','Y',110,'Forwarded (Packet no : {manifestNo}) to {destOff} from {originOff} having Weight {weight} kg.','Forwarded (Document Packet No : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(4,'OBDX','Out Bag for DOX','Out Bag for DOX',NULL,'2013-11-19',NULL,'2013-11-19','Y',120,'Forwarded (Bag no : {manifestNo}) to {destOff} from {originOff} Weight {weight} kg.','Forwarded (Document Bag No : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(5,'OBPC','Out Bag for Parcel','Out Bag for Parcel',NULL,'2013-11-19',NULL,'2013-11-19','Y',130,'Forwarded (Consignment no : {consgNo}) at {destOff} from {originOff} Weight {weight} kg.','Forwarded (Parcel Bag No : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(6,'OMBG','Out Master Bag','Out Master Bag',NULL,'2013-11-19',NULL,'2013-11-19','Y',150,'Forwarded (Master Bag No : {manifestNo}) to {destOff} from {originOff} having Weight {weight} kg.','Forwarded (Master Bag No : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(7,'DSPT','Dispatch','Dispatch',NULL,'2013-11-19',NULL,'2013-11-19','Y',160,'Forwarded (Dispatch No : {gatePassNo}) from {originOff} to {destOff} Weight {weight} kg.','Forwarded (Dispatch No : {gatePassNo}) from {originOff} to {destOff} Weight {weight} kg.'),
(8,'HLDP','Held up','Held up',NULL,'2013-11-19',NULL,'2013-11-19','Y',190,'Held Up at {originOff}, {hubOff} hub','Held Up at {originOff}, {hubOff} hub'),
(9,'RCVE','Receive','Receive',NULL,'2013-11-19',NULL,'2013-11-19','Y',30,'Received (Manifest No : {manifestNo}) at {destOff} from {originOff} having Weight {weight} kg.','Received (Gate Pass No : {gatePassNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(10,'IMBG','In Master Bag','In Master Bag',NULL,'2013-11-19',NULL,'2013-11-19','Y',80,'Received (Master Bag No : {manifestNo}) at {destOff} from {originOff} having Weight {weight} kg.','Received (Master Bag No : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(11,'IBPC','In Bag for Parcel','In Bag for Parcel',NULL,'2013-11-19',NULL,'2013-11-19','Y',50,'Received (Parcel Bag No : {manifestNo}) at {destOff} from {originOff} having Weight {weight} kg.','Received (Parcel Bag no : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(12,'IBDX','In Bag for DOX','In Bag for DOX',NULL,'2013-11-19',NULL,'2013-11-19','Y',60,'Received (Document Bag no : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.','Received (Document Bag no : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(13,'IPKT','In Packet for DOX','In Packet for DOX',NULL,'2013-11-19',NULL,'2013-11-19','Y',40,'Received (Packet No : {manifestNo}) at {destOff} from {originOff} having Weight {weight} kg.','Received (Document Packet no : {manifestNo}) at {destOff} from {originOff} Weight {weight} kg.'),
(14,'BOUT','Branch out','Branch out',NULL,'2013-11-19',NULL,'2013-11-19','Y',140,'Branched Out from {originOff} of {city} to {destOff} of {destCity}','Branch Out from {originOff} of {city} to {destOff} of {destCity}'),
(15,'BRIN','Branch In','Branch In',NULL,'2013-11-19',NULL,'2013-11-19','Y',70,'Branched In from {destOff} of {destCity} to {originOff} of {city}','Branch In from {destOff} of {destCity} to {originOff} of {city}'),
(16,'MSRT','Misroute','Misroute',NULL,'2013-11-19',NULL,'2013-11-19','Y',90,'Misroute no ({manifestNo}) forwarded from {originOff} of {city} to {destOff} of {destCity}','Misroute no({manifestNo}) forwarded from {originOff} of {city} to {destOff} of {destCity}'),
(17,'DLRS','Delivery Run sheet','Delivery Run sheet',NULL,'2013-11-19',NULL,'2013-11-19','Y',170,'Sent for delivery(DRS no: {DRSNo}) from {originOff}','Sent for delivery(DRS no:{manifestNo}) from {originOff}'),
(18,'PRDL','Proof of delivery','Proof of delivery',NULL,'2013-11-19',NULL,'2013-11-19','Y',180,'POD Manifested (POD Manifest no: {manifestNo}) from {originOff} to {destOff}','POD Manifested (POD Manifest no: {manifestNo}) from {originOff} to {hubOff}'),
(19,'RTOH','RTO/RTH','RTO/RTH',NULL,'2013-11-19',NULL,'2013-11-19','Y',95,'Forwarded (RTO/RTH No: {manifestNo}) from {originOff} to {destOff} having weight {weight} kg.','Forwarded (RTO/RTH No: {manifestNo}) from {originOff} to {destOff} having weight {weight} kg.'),
(20,'TPDX','OutManifest Third Party DOX','OutManifest Third Party DOX',NULL,'2013-11-19',NULL,'2013-11-19','Y',115,'Third Party Manifest prepared (Manifest no :{manifestNo}) from {originOff} to {custName} having Weight {weight}  kg.','Third Party Manifest prepared (Manifest no :{manifestNo}) from {originOff} to {custName}, Weight {weight} kg.'),
(21,'TPBP','OutManifest Third Party BPL','OutManifest Third Party BPL',NULL,'2013-11-19',NULL,'2013-11-19','Y',135,'Third Party Manifest prepared (Manifest no :{manifestNo}) from {originOff} to {custName} having Weight {weight} kg.','Third Party Manifest prepared (Manifest no :{manifestNo}) from {originOff} to {custName}, Weight {weight} kg.'),
(22,'MEC','Misc Expenses & Collections','Misc Expenses & Collections',NULL,'2013-11-19',NULL,'2013-11-19','Y',200,NULL,NULL);

set foreign_key_checks=1;