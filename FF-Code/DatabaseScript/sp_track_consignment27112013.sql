-- Procedure name - sp_track_consignment
-- Purpose - To track the consignment details
-- Created by and Date - UPASANA
-- Version - 1.2 
-- Modified by - SHAHNAZ
-- Modified Date - 26 Nov 2013 

DROP PROCEDURE IF EXISTS udaan_gold_bcun.sp_track_consignment;
CREATE PROCEDURE udaan_gold_bcun.`sp_track_consignment`(IN CONSG_NO VARCHAR(255))
BEGIN


drop temporary table if exists manifestsMappedToConsignment;

drop temporary table if exists manifestsMappedToPacketOrBag;

drop temporary table if exists manifestsMappedToMasterBag;

drop temporary table if exists manifestsForMasterBagDispatch;

drop temporary table if exists manifestsForBagDispatch;

drop temporary table if exists manifestsForMasterBagRecieve;

drop temporary table if exists manifestsForBagRecieve;



create temporary table manifestsMappedToConsignment engine=memory
SELECT distinct MANIFEST_NO
FROM ff_f_manifest m,ff_f_consignment_manifested cnm, ff_f_consignment cn
where m.manifest_id = cnm.manifest_id
and cnm.consignment_id = cn.CONSG_ID
and cn.CONSG_NO = CONSG_NO;



create temporary table manifestsMappedToPacketOrBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no IN (select * from manifestsMappedToConsignment)
and parentm.manifest_no is not null;


create temporary table manifestsMappedToMasterBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no IN (select * from manifestsMappedToPacketOrBag)
and parentm.manifest_no is not null;



create temporary table manifestsForMasterBagDispatch engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));



create temporary table manifestsForBagDispatch engine=memory
select manifest_no from ff_f_manifest where manifest_id IN (
(select a.MANIFEST_EMBEDDED_IN from ff_f_manifest a where a.manifest_id in
(select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));




create temporary table manifestsForMasterBagRecieve engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select MANIFEST_EMBEDDED_IN from ff_f_manifest where manifest_id IN(
select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));




create temporary table manifestsForBagRecieve engine=memory
select manifest_no from ff_f_manifest where manifest_id IN(
(select a.MANIFEST_EMBEDDED_IN from ff_f_manifest a where a.manifest_id in
(select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no=CONSG_NO)));



--
-- Replaced ff_f_manifest_process with ff_f_manifest
--

select @rownum:=@rownum+1 as ROW_ID,
       cn_track.PROCESS_ID,
       cn_track.ARTIFACT_TYPE,
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

SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       CONSG_NO AS CONSG_NO,
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       ff_f_manifest.MANIFEST_PROCESS_CODE as PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.MANIFEST_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) as VALUE_2,
       'weight' as     KEY_3,
       convert(ff_f_manifest.MANIFEST_WEIGHT, CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'city' AS KEY_7,
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
WHERE  ff_f_manifest.MANIFEST_NO IN (select * from manifestsMappedToConsignment)


UNION




SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       CONSG_NO AS CONSG_NO,
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       ff_f_manifest.MANIFEST_PROCESS_CODE as PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.MANIFEST_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) as VALUE_2,
       'weight' as     KEY_3,
       convert(ff_f_manifest.MANIFEST_WEIGHT, CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'city' AS KEY_7,
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
WHERE  ff_f_manifest.MANIFEST_NO in (select * from manifestsMappedToPacketOrBag)

UNION



SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       CONSG_NO AS CONSG_NO,
       ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
       ff_f_manifest.MANIFEST_PROCESS_CODE as PROCESS_NO,
       NULL AS OPERATING_LEVEL,
       ff_d_process.PROCESS_ORDER as PROCESS_ORDER,
       ff_f_manifest.MANIFEST_DATE as PROCESS_DATE,
       ff_f_manifest.MANIFEST_DIRECTION as DIRECTION,
       'originOff' as  KEY_1,
       convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER) as     VALUE_1,
       'destOff' as    KEY_2,
       convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) as VALUE_2,
       'weight' as     KEY_3,
       convert(ff_f_manifest.MANIFEST_WEIGHT, CHARACTER) as   VALUE_3,
       'date' as       KEY_4,
       convert(date_format(ff_f_manifest.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) as     VALUE_4,
       'direction' AS KEY_5,
        convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER) as VALUE_5,
        'manifestNo' AS KEY_6,
       convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
         'city' AS KEY_7,
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
WHERE  ff_f_manifest.MANIFEST_NO in (select * from manifestsMappedToMasterBag)

UNION

--
-- Sequel to get booked consignments records 
-- Replaced ff_f_booking with ff_f_consignment
-- In case of consignment created from OPKT/OBPC, OPKT/OBPC records are coming twice. 
-- So added process.PROCESS_CODE not in ('OPKT','OBPC')
-- 

SELECT  consignment.CONSG_ID AS PROCESS_ID,
       'C' as ARTIFACT_TYPE,
        consignment.CONSG_NO AS CONSG_NO,
        NULL AS MANIFEST_NO,
				process.PROCESS_CODE as PROCESS_NO,
        NULL AS OPERATING_LEVEL,
        process.PROCESS_ORDER AS PROCESS_ORDER,
        consignment.CREATED_DATE as PROCESS_DATE,
        NULL as DIRECTION,
				'originOff' AS KEY_1,
        convert(consignment.ORG_OFF, CHARACTER) as VALUE_1,
				'city' AS KEY_2,
        (select city_id from ff_d_office where office_id = convert(consignment.ORG_OFF, CHARACTER)) as VALUE_2,
        'weight' as  KEY_3,
        convert(consignment.FINAL_WEIGHT, CHARACTER)  as VALUE_3,
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
consignment.UPDTAED_PROCESS_FROM = process.PROCESS_ID
WHERE consignment.CONSG_NO = CONSG_NO
and process.PROCESS_CODE not in ('OPKT','OBPC')

UNION



SELECT  heldup.HELDUP_ID AS PROCESS_ID,
	      'C' as ARTIFACT_TYPE,
        TRANSACTION_NUMBER AS CONSG_NO,
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
        NULL AS   KEY_4,
        NULL as VALUE_4,
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



SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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



SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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
AND manifest.manifest_id IN
(select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no= CONSG_NO)

UNION



SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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



SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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
WHERE MOVEMENT_DIRECTION='R'
AND manifest.manifest_no IN(select * from manifestsForMasterBagRecieve)

UNION


SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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
WHERE MOVEMENT_DIRECTION='R'
AND manifest.manifest_id IN
(select manifest_id from ff_f_consignment_manifested cmanifest, ff_f_consignment consg
where cmanifest.consignment_id = consg.consg_id and consg.consg_no= CONSG_NO)

UNION




SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'C' as ARTIFACT_TYPE,
          NULL AS CONSG_NO,
          manifest.MANIFEST_NO AS MANIFEST_NO,
          process.PROCESS_CODE as PROCESS_NO,
          NULL AS OPERATING_LEVEL,
          process.PROCESS_ORDER AS PROCESS_ORDER,
          loadMovement.LOADING_DATE as PROCESS_DATE,
          loadMovement.MOVEMENT_DIRECTION as DIRECTION,
          'originOff'AS  KEY_1,
          loadMovement.ORIGIN_OFFICE AS VALUE_1,
          'destOff' AS        KEY_2,
          DEST_OFFICE AS VALUE_2,
          'weight' AS         KEY_3,
          DISPATCH_WEIGHT AS VALUE_3,
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
WHERE MOVEMENT_DIRECTION='R'
AND manifest.manifest_no IN(select * from manifestsForBagRecieve)

UNION



SELECT  delivery.DELIVERY_ID AS PROCESS_ID,
        'C' as ARTIFACT_TYPE,
        details.CONSIGNMENT_NUMBER AS CONSG_NO,
        NULL AS MANIFEST_NO,
	      p.PROCESS_CODE AS PROCESS_NO,
	      NULL AS OPERATING_LEVEL,
        p.PROCESS_ORDER as PROCESS_ORDER,
        delivery.DRS_DATE as PROCESS_DATE,
        NULL as DIRECTION,
	      'originOff'AS  	KEY_1,
        delivery.CREATED_OFFICE_ID AS VALUE_1,
        'date' AS      	KEY_2,
        DRS_DATE AS VALUE_2,
	      'other' AS    	KEY_3,
        CONCAT(delivery.DRS_NUMBER,'#',details.DELIVERY_STATUS,'#',coalesce(reason.REASON_NAME,'')) AS VALUE_3,
	      'DRSNo' AS 	KEY_4,
        delivery.DRS_NUMBER as VALUE_4,
        NULL AS   KEY_5,
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
FROM ff_f_delivery delivery left join ff_f_delivery_dtls details
ON   delivery.DELIVERY_ID = details.DELIVERY_ID
left join ff_d_process p on p.PROCESS_CODE = 'DLRS'
left join ff_d_reason reason on reason.REASON_ID = details.REASON_ID
WHERE details.CONSIGNMENT_NUMBER = CONSG_NO

UNION



SELECT booking.BOOKING_ID AS PROCESS_ID,
	     'C' as ARTIFACT_TYPE,
       booking.consg_number AS CONSG_NO,
       NULL AS MANIFEST_NO,
      ff_d_process.PROCESS_CODE AS PROCESS_NO,
      NULL AS OPERATING_LEVEL,
	    ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
      runsheetHeader.RUNSHEET_DATE as PROCESS_DATE,
      NULL as DIRECTION,
	    'originOff'AS  	KEY_1,
      booking.BOOKING_OFF AS VALUE_1,
	    'city' AS KEY_2,
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
WHERE booking.consg_number = CONSG_NO) cn_track, (SELECT @rownum:=0) r

order by cn_track.PROCESS_DATE,cn_track.PROCESS_ORDER;


drop temporary table if exists manifestsMappedToConsignment;

drop temporary table if exists manifestsMappedToPacketOrBag;

drop temporary table if exists manifestsMappedToMasterBag;


drop temporary table if exists manifestsForMasterBagDispatchRecieve;

drop temporary table if exists manifestsForBagDispatchRecieve;

END;
