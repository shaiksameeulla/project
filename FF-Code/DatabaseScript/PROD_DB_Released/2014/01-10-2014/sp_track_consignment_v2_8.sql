DROP PROCEDURE IF EXISTS sp_track_consignment;
CREATE PROCEDURE `sp_track_consignment`(IN CONSG_NO VARCHAR(255))
BEGIN

-- Procedure name - sp_track_consignment
-- Purpose - To track the consignment details
-- Created by and Date - UPASANA
-- Version - 2.8
-- Modified by - SHAHNAZ
-- Modified Date - 30 Sep 2014

drop temporary table if exists manifestsMappedToConsignment;

drop temporary table if exists manifestsMappedToPacketOrBag;

drop temporary table if exists manifestsMappedToMasterBag;

drop temporary table if exists manifestsForMasterBagDispatch;

drop temporary table if exists manifestsForBagDispatch;

drop temporary table if exists manifestsForMasterBagRecieve;

drop temporary table if exists manifestsForBagRecieve;

drop temporary table if exists ff_f_cn_track;

create temporary table manifestsMappedToConsignment engine=memory
SELECT DISTINCT ff_f_consignment.CONSG_NO,
       ff_f_consignment.CONSG_STATUS,
       ff_f_consignment.CUSTOMER,
       ff_f_manifest.MANIFEST_NO,
       ff_f_manifest.MANIFEST_ID
  FROM (   (   ff_f_manifest ff_f_manifest
            INNER JOIN
               ff_f_consignment_manifested ff_f_consignment_manifested
            ON (ff_f_consignment_manifested.MANIFEST_ID =
                   ff_f_manifest.MANIFEST_ID))
        RIGHT OUTER JOIN
           ff_f_consignment ff_f_consignment
        ON (ff_f_consignment_manifested.CONSIGNMENT_ID =
               ff_f_consignment.CONSG_ID))
 WHERE (ff_f_consignment.CONSG_NO = CONSG_NO);

create temporary table manifestsMappedToPacketOrBag engine=memory
select parentm.MANIFEST_ID from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
-- where childm.MANIFEST_ID IN (select cnManifested.MANIFEST_ID from manifestsMappedToConsignment cnManifested)
where childm.MANIFEST_NO IN (select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)
and parentm.manifest_no is not null;

create temporary table manifestsMappedToMasterBag engine=memory
select parentm.MANIFEST_ID from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.MANIFEST_ID IN (select * from manifestsMappedToPacketOrBag)
and parentm.manifest_no is not null;

create temporary table manifestsForMasterBagDispatch engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where MANIFEST_NO IN
(select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)));
--  (select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
--  where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));

create temporary table manifestsForBagDispatch engine=memory
select manifest_no from ff_f_manifest where manifest_id IN (
(select a.MANIFEST_EMBEDDED_IN from ff_f_manifest a where a.MANIFEST_NO in
(select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)));
--  (select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
--  where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));

create temporary table manifestsForMasterBagRecieve engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where MANIFEST_NO IN(
--  select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
--  where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));
select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)));

create temporary table manifestsForBagRecieve engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
(select a.MANIFEST_EMBEDDED_IN from ff_f_manifest a where a.MANIFEST_NO in
--  (select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
--  where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));
(select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)));

CREATE TEMPORARY TABLE ff_f_cn_track( ROW_ID int,
       PROCESS_ID int,
       ARTIFACT_TYPE varchar(10),
	     OPERATING_OFFICE int, 
       CONSG_NO varchar(20),
       MANIFEST_NO varchar(20),
       PROCESS_NO varchar(20),
       OPERATING_LEVEL int,
	     PROCESS_ORDER int,
       PROCESS_DATE datetime,
       DIRECTION varchar(10),
       KEY_1 varchar(20),
       VALUE_1 varchar(50),
       KEY_2 varchar(20),
       VALUE_2 varchar(50),
       KEY_3 varchar(20),
       VALUE_3 varchar(100),
       KEY_4 varchar(20),
       VALUE_4 varchar(50),
       KEY_5 varchar(20),
       VALUE_5 varchar(50),
       KEY_6 varchar(20),
       VALUE_6 varchar(50),
       KEY_7 varchar(20),
       VALUE_7 varchar(50),
       KEY_8 varchar(20),
       VALUE_8 varchar(50),
       KEY_9 varchar(20),
       VALUE_9 varchar(50),
       KEY_10 varchar(20),
       VALUE_10 varchar(50),
	     KEY_11 varchar(20),
       VALUE_11 varchar(50),
       KEY_12 varchar(20),
       VALUE_12 varchar(50),
       KEY_13 varchar(20),
       VALUE_13 varchar(50),
       KEY_14 varchar(20),
       VALUE_14 varchar(50));


insert into ff_f_cn_track(
select @rownum:=@rownum+1 as ROW_ID,
       cn_track.PROCESS_ID,
       cn_track.ARTIFACT_TYPE,
	     cn_track.OPERATING_OFFICE, 
       cn_track.CONSG_NO,
       cn_track.MANIFEST_NO,
       cn_track.PROCESS_NO,
       cn_track.OPERATING_LEVEL,
	     cn_track.PROCESS_ORDER,
       cn_track.PROCESS_DATE,
       cn_track.DIRECTION,
       cn_track.KEY_1,
       cn_track.VALUE_1,
       cn_track.KEY_2,
       cn_track.VALUE_2,
       cn_track.KEY_3,
       cn_track.VALUE_3,
       cn_track.KEY_4,
       cn_track.VALUE_4,
       cn_track.KEY_5,
       cn_track.VALUE_5,
       cn_track.KEY_6,
       cn_track.VALUE_6,
       cn_track.KEY_7,
       cn_track.VALUE_7,
       cn_track.KEY_8,
       cn_track.VALUE_8,
       cn_track.KEY_9,
       cn_track.VALUE_9,
       cn_track.KEY_10,
       cn_track.VALUE_10,
	     cn_track.KEY_11,
       cn_track.VALUE_11,
       cn_track.KEY_12,
       cn_track.VALUE_12,
       cn_track.KEY_13,
       cn_track.VALUE_13,
       cn_track.KEY_14,
       cn_track.VALUE_14
from (
-- Packet data
SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
	     ff_f_manifest.OPERATING_OFFICE as OPERATING_OFFICE, 
       CONSG_NO AS CONSG_NO, 
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.CREATED_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       
       
       IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
       'weight' as     KEY_3,
       convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%m-%d-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'orgCity' AS KEY_7,
      (select city_id from ff_d_office where office_id = convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)) as VALUE_7,
       'destCity' AS KEY_8,
      convert(ff_f_manifest.DESTINATION_CITY, CHARACTER) as VALUE_8,
        'consgNo' AS KEY_9,
        CONSG_NO as VALUE_9,
        'custName' AS KEY_10, 
        cnManifested.CUSTOMER as VALUE_10,
				NULL AS KEY_11,
        NULL as VALUE_11,
        NULL AS KEY_12,
        NULL as VALUE_12,
        NULL AS KEY_13,
        NULL as VALUE_13,
        'cnStatus' AS KEY_14, 
        cnManifested.CONSG_STATUS as VALUE_14
FROM ff_f_manifest ff_f_manifest
INNER JOIN ff_d_process ff_d_process
ON ff_f_manifest.MANIFEST_PROCESS_CODE = ff_d_process.PROCESS_CODE
INNER JOIN manifestsMappedToConsignment cnManifested
ON ff_f_manifest.MANIFEST_ID = cnManifested.MANIFEST_ID

-- ROW FOR BAG MANIFEST
UNION


SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
	   ff_f_manifest.OPERATING_OFFICE as OPERATING_OFFICE,		
       CONSG_NO AS CONSG_NO,
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.CREATED_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       
       
       IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
       'weight' as     KEY_3,
       convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'orgCity' AS KEY_7,
      (select city_id from ff_d_office where office_id = convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)) as VALUE_7,
       'destCity' AS KEY_8,
      convert(ff_f_manifest.DESTINATION_CITY, CHARACTER) as VALUE_8,
       'consgNo' AS KEY_9,
        CONSG_NO as VALUE_9,
        NULL AS KEY_10,
        NULL as VALUE_10,
				NULL AS KEY_11,
        NULL as VALUE_11,
        NULL AS KEY_12,
        NULL as VALUE_12,
        NULL AS KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_manifest ff_f_manifest
INNER JOIN ff_d_process ff_d_process
ON ff_f_manifest.MANIFEST_PROCESS_CODE = ff_d_process.PROCESS_CODE
WHERE  ff_f_manifest.MANIFEST_ID in (select * from manifestsMappedToPacketOrBag)

UNION

-- ROW FOR MASTER BAG
SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
	   ff_f_manifest.OPERATING_OFFICE as OPERATING_OFFICE, 
       CONSG_NO AS CONSG_NO,
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       
       
       IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.CREATED_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       
       IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
       'weight' as     KEY_3,
       convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'orgCity' AS KEY_7,
      (select city_id from ff_d_office where office_id = convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)) as VALUE_7,
       'destCity' AS KEY_8,
      convert(ff_f_manifest.DESTINATION_CITY, CHARACTER) as VALUE_8,
        'consgNo' AS KEY_9,
        CONSG_NO as VALUE_9,
        NULL AS KEY_10,
        NULL as VALUE_10,
				NULL AS KEY_11,
        NULL as VALUE_11,
        NULL AS KEY_12,
        NULL as VALUE_12,
        NULL AS KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_manifest ff_f_manifest
INNER JOIN ff_d_process ff_d_process
ON ff_f_manifest.MANIFEST_PROCESS_CODE = ff_d_process.PROCESS_CODE
WHERE  ff_f_manifest.MANIFEST_ID in (select * from manifestsMappedToMasterBag)

UNION

--
-- Sequel to get booked consignments records
-- Replaced ff_f_booking with ff_f_consignment
-- In case of consignment created from OPKT/OBPC or updated with RTOH , OPKT/OBPC/RTOH records are coming twice. 
-- So added PROCESS_NO as 'BOOK'
-- ROW FOR BOOK

SELECT  consignment.CONSG_ID AS PROCESS_ID,
       'C' as ARTIFACT_TYPE,
		    consignment.ORG_OFF as OPERATING_OFFICE,  
        consignment.CONSG_NO AS CONSG_NO,
        NULL AS MANIFEST_NO,
        
		    process.PROCESS_CODE as PROCESS_NO, 
        NULL AS OPERATING_LEVEL,
        process.PROCESS_ORDER AS PROCESS_ORDER,
        consignment.CREATED_DATE as PROCESS_DATE,
        NULL as DIRECTION,
				'originOff' AS KEY_1,
        convert(consignment.ORG_OFF, CHARACTER) as VALUE_1,
				'orgCity' AS KEY_2,
        (select city_id from ff_d_office where office_id = convert(consignment.ORG_OFF, CHARACTER)) as VALUE_2,
        'weight' as  KEY_3,
        convert(FORMAT(consignment.FINAL_WEIGHT,3), CHARACTER)  as VALUE_3,
        'noOfPieces' as    KEY_4,
        convert(consignment.NO_OF_PCS, CHARACTER) as VALUE_4,
        'other' as     KEY_5,
        CONCAT(coalesce(consignment.REF_NO,''),'#',consignment.ORG_OFF,'#',consignment.DEST_PINCODE) as VALUE_5,
        'date' as       KEY_6,
        convert(date_format(consignment.CREATED_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) AS VALUE_6,
        'destCity' AS KEY_7,
        (select city_id from ff_d_pincode where pincode_id = convert(consignment.DEST_PINCODE, CHARACTER)) as VALUE_7,
        'custName' AS KEY_8,
      	consignment.CUSTOMER AS VALUE_8,
        NULL AS KEY_9,
        NULL as VALUE_9,
        NULL AS KEY_10,
        NULL as VALUE_10,
		    NULL AS KEY_11,
        NULL as VALUE_11,
        NULL AS KEY_12,
        NULL as VALUE_12,
        NULL AS KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_consignment consignment
left join ff_d_process process on
process.PROCESS_CODE = 'BOOK' 
WHERE consignment.CONSG_NO = CONSG_NO
AND consignment.IS_EXCESS_CONSG = 'N'

UNION

-- ROW FOR HELDUP
SELECT  heldup.HELDUP_ID AS PROCESS_ID,
	      'C' as ARTIFACT_TYPE,
		heldup.OFFICE as OPERATING_OFFICE , 
        CONSG_NO AS CONSG_NO,
		NULL AS MANIFEST_NO,
	      process.PROCESS_CODE AS PROCESS_NO,
	      NULL AS OPERATING_LEVEL,
        process.PROCESS_ORDER as PROCESS_ORDER ,
        heldup.CREATED_DATE as PROCESS_DATE,
        NULL as DIRECTION,
	      'originOff'AS  KEY_1,
         OFFICE AS VALUE_1,
	      'hubOff' AS    KEY_2,
         office.REPORTING_HUB AS VALUE_2,
	      'date' AS      KEY_3,
        CREATED_DATE AS VALUE_3,
        'TRAN_NO' AS   KEY_4,
        TRANSACTION_NUMBER as VALUE_4,
	      NULL AS 	KEY_5,
        NULL as VALUE_5,
	      NULL AS 	KEY_6,
        NULL as VALUE_6,
	      NULL AS 	KEY_7,
        NULL as VALUE_7,
	      NULL AS 	KEY_8,
        NULL as VALUE_8,
	      NULL AS 	KEY_9,
        NULL as VALUE_9,
	      NULL AS 	KEY_10,
        NULL as VALUE_10,
        NULL AS 	KEY_11,
        NULL as VALUE_11,
        NULL AS 	KEY_12,
        NULL as VALUE_12,
        NULL AS 	KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_heldup heldup left join ff_d_office office
ON   heldup.OFFICE = office.OFFICE_ID
left join ff_d_process process on
heldup.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE	TRANSACTION_TYPE IN ('CONG')
AND TRANSACTION_NUMBER = CONSG_NO

UNION

-- ROW FOR DISPATCH
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE, 
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	        'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='D'
AND manifest.manifest_no IN(select * from manifestsForMasterBagDispatch)

UNION

-- ROW FOR DISPATCH
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE,  
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	       'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='D'
AND manifest.MANIFEST_ID IN
 (select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
 where cmanifest.consignment_id = consg.consg_id and consg.consg_no= CONSG_NO)

UNION

-- ROW FOR DISPATCH
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE, 
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	        'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='D'
AND manifest.manifest_no IN(select * from manifestsForBagDispatch)

UNION

-- ROW FOR RECIEVE
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE,  
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	        'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        'receiveType' AS KEY_14,
        loadMovement.RECEIVE_TYPE as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='R'
AND manifest.manifest_no IN(select * from manifestsForMasterBagRecieve)

UNION

-- ROW FOR RECIEVE
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE, 
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	        'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        'receiveType' AS KEY_14,
        loadMovement.RECEIVE_TYPE as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='R'
AND manifest.MANIFEST_ID IN
 (select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
 where cmanifest.consignment_id = consg.consg_id and consg.consg_no= CONSG_NO)

UNION

-- ROW FOR RECIEVE
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
		      loadMovement.OPERATING_OFFICE as OPERATING_OFFICE,  
          CONSG_NO AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.CREATED_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
	        'date' AS       		KEY_4,
          LOADING_DATE AS VALUE_4,
          'other' AS       KEY_5,
          CONCAT(TOKEN_NUMBER,'#',vehicle1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) as VALUE_5,
	        'manifestNo' AS 			KEY_6,
           manifest.MANIFEST_NO AS VALUE_6,
	        'departureTime' AS 		KEY_7,
          trip.DEPARTURE_TIME as VALUE_7,
	        'arrivalTime' AS 		KEY_8,
          trip.ARRIVAL_TIME as VALUE_8,
	        'flightNo' AS 			KEY_9,
          flight.FLIGHT_NUMBER as VALUE_9,
	        'trainNo' AS 			KEY_10,
          train.TRAIN_NUMBER as VALUE_10,
	        'vehicleNo' AS 			KEY_11,
          vehicle.REG_NUMBER as VALUE_11,
	        'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_13,
          vehicle1.REG_NUMBER as VALUE_13,
        'receiveType' AS KEY_14,
        loadMovement.RECEIVE_TYPE as VALUE_14
FROM   ff_f_load_connected loadConnected
left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest on
loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process on
loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
left join ff_f_trip_serviced_by tripServicedBy on loadMovement.TRIP_SERVICED_BY=tripServicedBy.TRIP_SERVICED_BY_ID
left join ff_f_trip trip on tripServicedBy.TRIP=trip.TRIP_ID
left join ff_f_transport transport on trip.TRANSPORT=transport.TRANSPORT_ID
left join ff_d_flight flight on transport.FLIGHT=flight.FLIGHT_ID
left join ff_d_train train on transport.TRAIN=train.TRAIN_ID
left join ff_d_vehicle vehicle on transport.VEHICLE=vehicle.VEHICLE_ID
left join ff_d_vehicle vehicle1 on loadMovement.VEHICLE=vehicle1.VEHICLE_ID
WHERE MOVEMENT_DIRECTION='R'
AND manifest.manifest_no IN(select * from manifestsForBagRecieve)

UNION

-- ROW FOR DELIVERY
(SELECT  delivery.DELIVERY_ID AS PROCESS_ID,
        'C' as ARTIFACT_TYPE,
		    delivery.CREATED_OFFICE_ID as OPERATING_OFFICE,  
        details.CONSIGNMENT_NUMBER AS CONSG_NO,
        NULL AS MANIFEST_NO,
	      p.PROCESS_CODE AS PROCESS_NO,
	      NULL AS OPERATING_LEVEL,
        p.PROCESS_ORDER as PROCESS_ORDER,
        IFNULL(details.DELIVERY_TIME, DRS_DATE) as PROCESS_DATE,       
        NULL as DIRECTION,
	      'originOff'AS  	KEY_1,
        delivery.CREATED_OFFICE_ID AS VALUE_1,
        'date' AS      	KEY_2,
        convert(date_format(DRS_DATE,'%m-%d-%Y %h:%i:%s'), CHARACTER) AS VALUE_2,
	      'other' AS    	KEY_3,
        CONCAT(delivery.DRS_NUMBER,'#',details.DELIVERY_STATUS,'#',coalesce(reason.REASON_NAME,'')) AS VALUE_3,
	      'DRSNo' AS 	KEY_4,
        delivery.DRS_NUMBER as VALUE_4,
        'ReceiverName' AS   KEY_5,
        IF(DELIVERY_TYPE = 'O', '( Office Seal & Sign )', details.RECEIVER_NAME) as VALUE_5,
	      NULL AS 	KEY_6,
        NULL as VALUE_6,
	      NULL AS 	KEY_7,
        NULL as VALUE_7,
	      NULL AS 	KEY_8,
        NULL as VALUE_8,
        NULL AS 	KEY_9,
        NULL as VALUE_9,
	      NULL AS 	KEY_10,
        NULL as VALUE_10,
        NULL AS 	KEY_11,
        NULL as VALUE_11,
        NULL AS 	KEY_12,
        NULL as VALUE_12,
        NULL AS 	KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_delivery delivery left join ff_f_delivery_dtls details
ON   delivery.DELIVERY_ID = details.DELIVERY_ID
left join ff_d_process p on p.PROCESS_CODE = 'DLRS'
left join ff_d_reason reason on reason.REASON_ID = details.REASON_ID
WHERE details.CONSIGNMENT_NUMBER = CONSG_NO
AND details.RECORD_STATUS = 'A' 
ORDER BY delivery.DRS_DATE LIMIT 1)

UNION

-- ROW fOR PICKUP
SELECT booking.BOOKING_ID AS PROCESS_ID,
	     'C' as ARTIFACT_TYPE,
	   booking.BOOKING_OFF as OPERATING_OFFICE,  
       booking.consg_number AS CONSG_NO,
       NULL AS MANIFEST_NO,
      ff_d_process.PROCESS_CODE AS PROCESS_NO,
      NULL AS OPERATING_LEVEL,
	    ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
      runsheetHeader.RUNSHEET_DATE as PROCESS_DATE,
      NULL as DIRECTION,
	    'originOff'AS  	KEY_1,
      booking.BOOKING_OFF AS VALUE_1,
	    'orgCity' AS KEY_2,
      city.CITY_ID AS VALUE_2,
	    'date' AS KEY_3,
      runsheetHeader.RUNSHEET_DATE AS VALUE_3,
      'custName' AS KEY_4,
      booking.CUSTOMER AS VALUE_4,
	    NULL AS 	KEY_5,
      NULL as VALUE_5,
	    NULL AS 	KEY_6,
      NULL as VALUE_6,
	    NULL AS 	KEY_7,
      NULL as VALUE_7,
	    NULL AS 	KEY_8,
      NULL as VALUE_8,
	    NULL AS 	KEY_9,
      NULL as VALUE_9,
	    NULL AS 	KEY_10,
      NULL as VALUE_10,
      NULL AS 	KEY_11,
      NULL as VALUE_11,
      NULL AS 	KEY_12,
      NULL as VALUE_12,
      NULL AS 	KEY_13,
      NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM 	ff_f_booking booking
INNER JOIN ff_f_pickup_runsheet_detail runsheetDetail on booking.consg_number between runsheetDetail.START_CN_NO and runsheetDetail.END_CN_NO
left join ff_f_pickup_runsheet_header runsheetHeader
on runsheetHeader.RUNSHEET_HEADER_ID = runsheetDetail.RUNSHEET_HEADER_ID
left join ff_d_city city on city.CITY_ID in (select city_id from ff_d_office where office_id = booking.BOOKING_OFF)
INNER JOIN ff_d_process ff_d_process ON (ff_d_process.PROCESS_CODE = 'UPPU')
WHERE booking.consg_number = CONSG_NO

UNION

-- ROW FOR STOP DELIVERY 
SELECT  consignment.CONSG_ID AS PROCESS_ID, 
       'C' as ARTIFACT_TYPE,       
		    NULL as OPERATING_OFFICE,  
        consignment.CONSG_NO AS CONSG_NO,
        NULL AS MANIFEST_NO,
        'SDLV' as PROCESS_NO, 
        NULL AS OPERATING_LEVEL,
        NULL AS PROCESS_ORDER,        
        consignment.STOP_DELV_DATE as PROCESS_DATE,
        NULL as DIRECTION,
				'stopDlvReqOff' AS KEY_1,
        convert(STOP_DELIVERY_REQ_OFFICE, CHARACTER) as VALUE_1,
				NULL AS KEY_2,
        NULL as VALUE_2,
        NULL as KEY_3,
        NULL as VALUE_3,
        NULL as KEY_4,
        NULL as VALUE_4,
        NULL as KEY_5,
        NULL as VALUE_5,
        NULL as KEY_6,
        NULL AS VALUE_6,
        NULL AS KEY_7,
        NULL as VALUE_7,
        NULL AS KEY_8,
      	NULL AS VALUE_8,
        NULL AS KEY_9,
        NULL as VALUE_9,
        NULL AS KEY_10,
        NULL as VALUE_10,
		    NULL AS KEY_11,
        NULL as VALUE_11,
        NULL AS KEY_12,
        NULL as VALUE_12,
        NULL AS KEY_13,
        NULL as VALUE_13,
        NULL AS KEY_14,
        NULL as VALUE_14
FROM ff_f_consignment consignment 
WHERE consignment.CONSG_NO = CONSG_NO
and CONSG_STATUS = 'X') cn_track, (SELECT @rownum:=0) r);

select * from ff_f_cn_track cn_track order by cn_track.PROCESS_DATE,cn_track.PROCESS_ORDER;

drop temporary table if exists manifestsMappedToConsignment;

drop temporary table if exists manifestsMappedToPacketOrBag;

drop temporary table if exists manifestsMappedToMasterBag;

drop temporary table if exists manifestsForMasterBagDispatchRecieve;

drop temporary table if exists manifestsForBagDispatchRecieve;

drop temporary table if exists ff_f_cn_track;

END;
