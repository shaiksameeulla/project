DROP PROCEDURE IF EXISTS udaan_gold.sp_track_manifest;
CREATE PROCEDURE udaan_gold.`sp_track_manifest`(IN MANIFEST_NO VARCHAR(25))
BEGIN
drop temporary table if exists manifestsMappedToMasterBag;
drop temporary table if exists manifestsMappedToPacketOrBag;
drop temporary table if exists manifestsForDispatchBag;
drop temporary table if exists manifestsForRecieveBag;
create temporary table manifestsMappedToPacketOrBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no = MANIFEST_NO
and parentm.manifest_no is not null;
create temporary table manifestsMappedToMasterBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no IN  (select * from manifestsMappedToPacketOrBag)
and parentm.manifest_no is not null;
create temporary table manifestsForDispatchBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no = MANIFEST_NO
and parentm.manifest_no is not null;
create temporary table manifestsForRecieveBag engine=memory
select parentm.manifest_no from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.manifest_no  = MANIFEST_NO
and parentm.manifest_no is not null;

select @rownum:=@rownum+1 as ROW_ID,
       mnfst_track.PROCESS_ID,
       mnfst_track.ARTIFACT_TYPE,
       mnfst_track.CONSG_NO,
       mnfst_track.MANIFEST_NO,
       mnfst_track.PROCESS_NO,
       mnfst_track.OPERATING_LEVEL,
       mnfst_track.PROCESS_ORDER,
       mnfst_track.PROCESS_DATE,
       mnfst_track.DIRECTION,
       mnfst_track.KEY_1,
       mnfst_track.VALUE_1,
       mnfst_track.KEY_2,
       mnfst_track.VALUE_2,
       mnfst_track.KEY_3,
       mnfst_track.VALUE_3,
       mnfst_track.KEY_4,
       mnfst_track.VALUE_4,
       mnfst_track.KEY_5,
       mnfst_track.VALUE_5,
       mnfst_track.KEY_6,
       mnfst_track.VALUE_6,
       mnfst_track.KEY_7,
       mnfst_track.VALUE_7,
       mnfst_track.KEY_8,
       mnfst_track.VALUE_8,
       mnfst_track.KEY_9,
       mnfst_track.VALUE_9,
       mnfst_track.KEY_10,
       mnfst_track.VALUE_10,
		 	mnfst_track.KEY_11,
       mnfst_track.VALUE_11,
       mnfst_track.KEY_12,
       mnfst_track.VALUE_12,
       mnfst_track.KEY_13,
       mnfst_track.VALUE_13,
       mnfst_track.KEY_14,
       mnfst_track.VALUE_14
from (
SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       NULL AS CONSG_NO,
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
FROM ( ff_f_manifest ff_f_manifest
		INNER JOIN
              ff_d_process ff_d_process
           ON (ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID))
WHERE ff_f_manifest.MANIFEST_NO = MANIFEST_NO
UNION
SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       NULL AS CONSG_NO,
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
FROM ( ff_f_manifest ff_f_manifest
		INNER JOIN
              ff_d_process ff_d_process
           ON (ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID))
WHERE  ff_f_manifest.MANIFEST_NO in (select * from manifestsMappedToPacketOrBag)
UNION
SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
       'M' as ARTIFACT_TYPE,
       NULL AS CONSG_NO,
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
FROM ( ff_f_manifest ff_f_manifest
		INNER JOIN
              ff_d_process ff_d_process
           ON (ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID))
WHERE  ff_f_manifest.MANIFEST_NO in (select * from manifestsMappedToMasterBag)
UNION
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'M' as ARTIFACT_TYPE,
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
	        'CD_AWB_RR' AS 			KEY_5,
          TOKEN_NUMBER AS VALUE_5,
	        'departureTime' AS 		KEY_6,
          trip.DEPARTURE_TIME as VALUE_6,
	        'arrivalTime' AS 		KEY_7,
          trip.ARRIVAL_TIME as VALUE_7,
	        'flightNo' AS 			KEY_8,
          flight.FLIGHT_NUMBER as VALUE_8,
	        'trainNo' AS 			KEY_9,
          train.TRAIN_NUMBER as VALUE_9,
	        'vehicleNo' AS 			KEY_10,
          vehicle1.REG_NUMBER as VALUE_10,
	         'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_12,
          vehicle1.REG_NUMBER as VALUE_12,
	        'regionOtherVehicleNo1' AS 	KEY_13,
          loadMovement.VEHICLE_REG_NUMBER  as VALUE_13,
		  'manifestNo' AS 	KEY_14,
          manifest.MANIFEST_NO  as VALUE_14
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
AND manifest.MANIFEST_NO = MANIFEST_NO
UNION
SELECT    loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	       'M' as ARTIFACT_TYPE,
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
	        'CD_AWB_RR' AS 			KEY_5,
          TOKEN_NUMBER AS VALUE_5,
	        'departureTime' AS 		KEY_6,
          trip.DEPARTURE_TIME as VALUE_6,
	        'arrivalTime' AS 		KEY_7,
          trip.ARRIVAL_TIME as VALUE_7,
	        'flightNo' AS 			KEY_8,
          flight.FLIGHT_NUMBER as VALUE_8,
	        'trainNo' AS 			KEY_9,
          train.TRAIN_NUMBER as VALUE_9,
	        'vehicleNo' AS 			KEY_10,
          vehicle1.REG_NUMBER as VALUE_10,
	         'gatePassNo' AS 		KEY_12,
          loadMovement.GATE_PASS_NUMBER as VALUE_12,
	        'regionVehicleNo' AS 		KEY_12,
          vehicle1.REG_NUMBER as VALUE_12,
	        'regionOtherVehicleNo1' AS 	KEY_13,
          loadMovement.VEHICLE_REG_NUMBER  as VALUE_13,
		  'manifestNo' AS 	KEY_14,
          manifest.MANIFEST_NO  as VALUE_14		  
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
AND manifest.MANIFEST_NO IN (select * from manifestsForDispatchBag)
UNION
########Recive Local - L########
SELECT
  loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	'M' as ARTIFACT_TYPE,
   NULL AS CONSG_NO,
   manifest.MANIFEST_NO AS MANIFEST_NO,
	process.PROCESS_CODE AS PROCESS_NO,
	NULL AS OPERATING_LEVEL,
  process.PROCESS_ORDER AS PROCESS_ORDER,
  loadMovement.LOADING_DATE as PROCESS_DATE,
  loadMovement.MOVEMENT_DIRECTION as DIRECTION,
	'originOff'AS  	KEY_1,
  loadMovement.ORIGIN_OFFICE AS VALUE_1,
	'destOff' AS    KEY_2,
  DEST_OFFICE AS VALUE_2,
	'weight' AS     KEY_3,
  DISPATCH_WEIGHT AS VALUE_3,
	'date' AS       KEY_4,
  LOADING_DATE AS VALUE_4,
	 'gatePassNo' AS 		KEY_5,
   loadMovement.GATE_PASS_NUMBER as VALUE_5,
	'manifestNo' AS 	KEY_6,
    manifest.MANIFEST_NO  as VALUE_6,	  
	NULL AS 	KEY_7,
  NULL as VALUE_7,
	NULL AS 	KEY_8,
  NULL as VALUE_8,
	NULL AS 	KEY_9,
  NULL as VALUE_9,
  NULL AS   KEY_10,
  NULL as VALUE_10,
  NULL AS 	KEY_11,
  NULL as VALUE_11,
  NULL AS 	KEY_12,
  NULL as VALUE_12,
  NULL AS 	KEY_13,
  NULL as VALUE_13,
  NULL AS 	KEY_14,
  NULL as VALUE_14
FROM 	ff_f_load_connected loadConnected left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest
on loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process
on loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE MOVEMENT_DIRECTION='R'
AND RECEIVE_TYPE='L'
AND manifest.MANIFEST_NO = MANIFEST_NO
UNION
########Recive out station - O########
SELECT
  loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	'M' as ARTIFACT_TYPE,
   NULL AS CONSG_NO,
   manifest.MANIFEST_NO AS MANIFEST_NO,
	process.PROCESS_CODE AS PROCESS_NO,
	NULL AS OPERATING_LEVEL,
  process.PROCESS_ORDER AS PROCESS_ORDER,
  loadMovement.LOADING_DATE as PROCESS_DATE,
  loadMovement.MOVEMENT_DIRECTION as DIRECTION,
	'originOff'AS  	KEY_1,
  loadMovement.ORIGIN_OFFICE AS VALUE_1,
	'destOff' AS    KEY_2,
  DEST_OFFICE AS VALUE_2,
	'weight' AS     KEY_3,
  DISPATCH_WEIGHT AS VALUE_3,
	'date' AS       KEY_4,
  LOADING_DATE AS VALUE_4,
	 'gatePassNo' AS 		KEY_5,
   loadMovement.RECEIVE_NUMBER as VALUE_5,
	'manifestNo' AS 	KEY_6,
    manifest.MANIFEST_NO  as VALUE_6,	  
	NULL AS 	KEY_7,
  NULL as VALUE_7,
	NULL AS 	KEY_8,
  NULL as VALUE_8,
	NULL AS 	KEY_9,
  NULL as VALUE_9,
  NULL AS   KEY_10,
  NULL as VALUE_10,
  NULL AS 	KEY_11,
  NULL as VALUE_11,
  NULL AS 	KEY_12,
  NULL as VALUE_12,
  NULL AS 	KEY_13,
  NULL as VALUE_13,
  NULL AS 	KEY_14,
  NULL as VALUE_14
FROM 	ff_f_load_connected loadConnected left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest
on loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process
on loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE MOVEMENT_DIRECTION='R'
AND RECEIVE_TYPE='O'
AND manifest.MANIFEST_NO = MANIFEST_NO
UNION
SELECT
  loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
	'M' as ARTIFACT_TYPE,
   NULL AS CONSG_NO,
   manifest.MANIFEST_NO AS MANIFEST_NO,
	process.PROCESS_CODE AS PROCESS_NO,
	NULL AS OPERATING_LEVEL,
  process.PROCESS_ORDER AS PROCESS_ORDER,
  loadMovement.LOADING_DATE as PROCESS_DATE,
  loadMovement.MOVEMENT_DIRECTION as DIRECTION,
	'originOff'AS  	KEY_1,
  loadMovement.ORIGIN_OFFICE AS VALUE_1,
	'destOff' AS    KEY_2,
  DEST_OFFICE AS VALUE_2,
	'weight' AS     KEY_3,
  DISPATCH_WEIGHT AS VALUE_3,
	'date' AS       KEY_4,
  LOADING_DATE AS VALUE_4,
	 'gatePassNo' AS 		KEY_5,
          loadMovement.GATE_PASS_NUMBER as VALUE_5,
	'manifestNo' AS 	KEY_6,
    manifest.MANIFEST_NO  as VALUE_6,	 
	NULL AS 	KEY_7,
  NULL as VALUE_7,
	NULL AS 	KEY_8,
  NULL as VALUE_8,
	NULL AS 	KEY_9,
  NULL as VALUE_9,
  NULL AS   KEY_10,
  NULL as VALUE_10,
  NULL AS 	KEY_11,
  NULL as VALUE_11,
  NULL AS 	KEY_12,
  NULL as VALUE_12,
  NULL AS 	KEY_13,
  NULL as VALUE_13,
  NULL AS 	KEY_14,
  NULL as VALUE_14
FROM 	ff_f_load_connected loadConnected left join ff_f_load_movement loadMovement
on loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
left join ff_f_manifest manifest
on loadConnected.MANIFEST = manifest.MANIFEST_ID
left join ff_d_process process
on loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE MOVEMENT_DIRECTION='R'
AND manifest.MANIFEST_NO IN (select * from manifestsForRecieveBag)
UNION
SELECT
   heldup.HELDUP_ID AS PROCESS_ID,
	'M' as ARTIFACT_TYPE,
   NULL AS CONSG_NO,
  TRANSACTION_NUMBER AS MANIFEST_NO,
  process.PROCESS_CODE AS PROCESS_NO,
	NULL AS OPERATING_LEVEL,
  process.PROCESS_ORDER AS PROCESS_ORDER,
  heldup.CREATED_DATE AS PROCESS_DATE,
  NULL as DIRECTION,
	'originOff'AS  	KEY_1,
  OFFICE AS VALUE_1,
	'hubOff' AS    	KEY_2,
  office.REPORTING_HUB AS VALUE_2,
	'date' AS      	KEY_3,
  CREATED_DATE AS VALUE_3,
	NULL AS 	KEY_4,
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
  NULL AS 	KEY_14,
  NULL as VALUE_14
FROM 	ff_f_heldup heldup left join ff_d_office office
on heldup.OFFICE = office.OFFICE_ID
left join ff_d_process process
on heldup.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE	TRANSACTION_TYPE IN ('OPMF','OGMF','BPLD','BPLP','MBPL')
AND TRANSACTION_NUMBER = MANIFEST_NO) mnfst_track, (SELECT @rownum:=0) r
order by mnfst_track.PROCESS_DATE,mnfst_track.PROCESS_ORDER;

drop temporary table if exists manifestsMappedToMasterBag;
drop temporary table if exists manifestsMappedToPacketOrBag;
drop temporary table if exists manifestsForDispatchBag;
drop temporary table if exists manifestsForRecieveBag;
END;
