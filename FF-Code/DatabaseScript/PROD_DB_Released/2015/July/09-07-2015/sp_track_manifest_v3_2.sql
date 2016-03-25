################################################
-- Procedure name - sp_track_manifest
-- Purpose - To track manifest details
-- Created by and Date - UPASANA
-- Version - 3.2
-- Modified by - SHAHNAZ
-- Modified Date - 1 Jul 2015
-- v2.2 -  artf3758615 : Third party name not reflecting in consignment tracking 
-- V2.2.1 - Get latest manifest results by manifest_id
-- v2.3 is a optimized SP
-- Enhancement in Tracking requested by FFCL
-- In the dispatch row along with the Gatepass number below additional details should also reflect 
-- Mode – Air / Rail / Road
-- •	Mode Air should consist of following detail (Flight no & Scheduled time of Arrival) 
-- •	Mode Rail should consist of following details (Train Name & Scheduled time of Arrival) 
-- •	Mode Road should consist of following details (Vehicle number & Scheduled time of Arrival)
-- V3 - Resolved issue from 2.2.1 and added manifest_direction for Dispatch/Recieve

-- V3.1 is tracked by latest manifest No..... 
-- As discussed with somesh, we need to show as many details available in DB with the tracked manifest no and its 
-- respective embedded in manifest and corresponding Dispatch/Receive info
-- RTH/RTO manifest should be identified with its specific names (3.2)

################################################
DROP PROCEDURE IF EXISTS sp_track_manifest;
CREATE PROCEDURE `sp_track_manifest`(IN MANIFEST_NO VARCHAR(25))
BEGIN

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;
DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForDispatchBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveOutBag;

-- set @manifestId = (SELECT ffm.MANIFEST_ID FROM ff_f_manifest ffm 
-- WHERE ffm.MANIFEST_NO = MANIFEST_NO
-- ORDER BY ffm.CREATED_DATE DESC LIMIT 1);

CREATE TEMPORARY TABLE manifestsMappedToPacketOrBag ENGINE=memory
SELECT parentm.manifest_id FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.MANIFEST_NO = MANIFEST_NO
AND parentm.manifest_no IS NOT NULL ;

CREATE TEMPORARY TABLE manifestsMappedToMasterBag ENGINE=memory
SELECT parentm.manifest_id FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_id IN  (SELECT * FROM manifestsMappedToPacketOrBag)
AND parentm.manifest_no IS NOT NULL ;

CREATE TEMPORARY TABLE manifestsForDispatchBag ENGINE=memory
SELECT * from manifestsMappedToPacketOrBag ;

CREATE TEMPORARY TABLE manifestsForRecieveBag ENGINE=memory
SELECT * from manifestsMappedToPacketOrBag ;

CREATE TEMPORARY TABLE manifestsForRecieveOutBag ENGINE=memory
SELECT * from manifestsMappedToPacketOrBag ;

SELECT @rownum := @rownum + 1 AS ROW_ID,
      mnfst_track.ARTIFACT_TYPE,
      mnfst_track.CONSG_NO,
      mnfst_track.MANIFEST_NO,
      mnfst_track.PROCESS_NO,
      mnfst_track.PROCESS_ORDER,
      mnfst_track.PROCESS_DATE,
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
FROM (
-- PACKET MANIFEST
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      ffm.MANIFEST_NO AS MANIFEST_NO,
      IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
      IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
      IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
      ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO, -- OUT MANIFEST CREATED BY DISPATCH PROCESS
      fdp.PROCESS_ORDER AS PROCESS_ORDER,
      ffm.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
      'destOff' AS KEY_2,
      -- convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
      -- changed for in-manifest destination office should be shown as operating office in tracking.
      IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),convert(ffm.DESTINATION_OFFICE, CHARACTER),convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2,
      'weight' AS KEY_3,
      convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) AS VALUE_3,
      'date' AS KEY_4,
      convert(date_format(ffm.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'),CHARACTER) AS VALUE_4,
      'direction' AS KEY_5,
      convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
      'manifestNo' AS KEY_6,
      convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
      'city' AS KEY_7,
      fdo.CITY_ID AS VALUE_7,
      'destCity' AS KEY_8,
      convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
      'custName' AS KEY_9, -- Added in Version 2.0
      fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      'manifestType' AS KEY_12,
	  ffm.MANIFEST_TYPE AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_manifest ffm
      LEFT JOIN ff_d_process fdp ON ffm.UPDATING_PROCESS = fdp.PROCESS_ID
      LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
      LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
      LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ffm.VENDOR_ID
WHERE ffm.MANIFEST_NO = MANIFEST_NO
              
UNION

-- BAG MANIFEST
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      ffm.MANIFEST_NO AS MANIFEST_NO,
      IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
      IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
      IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
      ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
      fdp.PROCESS_ORDER AS PROCESS_ORDER,
      ffm.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
      'destOff' AS KEY_2,
      -- convert(ffm.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
      -- changed for in-manifest destination office should be shown as operating office in tracking.
      IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),convert(ffm.DESTINATION_OFFICE, CHARACTER),convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2,
      'weight' AS KEY_3,
      convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) AS VALUE_3,
      'date' AS KEY_4,
      convert(date_format(ffm.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'),CHARACTER) AS VALUE_4,
      'direction' AS KEY_5,
      convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
      'manifestNo' AS KEY_6,
      convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
      'city' AS KEY_7,
      fdo.CITY_ID AS VALUE_7,
      'destCity' AS KEY_8,
      convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
      'custName' AS KEY_9, -- Added in Version 2.0
      fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      'manifestType' AS KEY_12,
	  ffm.MANIFEST_TYPE AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_manifest ffm
      LEFT JOIN ff_d_process fdp ON (ffm.UPDATING_PROCESS = fdp.PROCESS_ID)
      LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
      LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
      LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ffm.VENDOR_ID
WHERE ffm.MANIFEST_ID IN(SELECT * FROM manifestsMappedToPacketOrBag)

UNION

-- MASTER BAG MANIFEST
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      ffm.MANIFEST_NO AS MANIFEST_NO,
      IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
      IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
      IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
      ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
      ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
      ffm.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
      'destOff' AS KEY_2,
      -- convert(ffm.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
      -- changed for in-manifest destination office should be shown as operating office in tracking.
      IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),convert(ffm.DESTINATION_OFFICE, CHARACTER),convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2,
      'weight' AS KEY_3,
      convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) AS VALUE_3,
      'date' AS KEY_4,
      convert( date_format(ffm.MANIFEST_DATE, '%d-%m-%Y %h:%i:%s'),CHARACTER) AS VALUE_4,
      'direction' AS KEY_5,
      convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
      'manifestNo' AS KEY_6,
      convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
      'city' AS KEY_7,
      fdo.CITY_ID AS VALUE_7,
      'destCity' AS KEY_8,
      convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
      'custName' AS KEY_9, -- Added in Version 2.0
      fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      'manifestType' AS KEY_12,
	  ffm.MANIFEST_TYPE AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_manifest ffm
      LEFT JOIN ff_d_process ff_d_process ON ffm.UPDATING_PROCESS = ff_d_process.PROCESS_ID
      LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
      LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
      LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ffm.VENDOR_ID
WHERE ffm.MANIFEST_ID IN (SELECT * FROM manifestsMappedToMasterBag)

UNION

-- DISPATCH
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      fflm.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      fflm.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'other' AS KEY_5,
      CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',fft.DEPARTURE_TIME,'#',fft.ARRIVAL_TIME) AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      'gatePassNo' AS KEY_7,
      fflm.GATE_PASS_NUMBER AS VALUE_7,
      'transportModeAndNo' AS KEY_8
      ,CASE
          WHEN fflm.VEHICLE_TYPE = 'M'
            THEN CONCAT('Road','#',fdv1.REG_NUMBER)
          WHEN fflm.VEHICLE_TYPE = 'O'
            THEN CONCAT('Road','#',fflm.VEHICLE_REG_NUMBER)
          WHEN fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'O'
            THEN CONCAT(ffdtm.TRANSPORT_MODE_DESCRIPTION,'#',fflm.ROUTE_SERVICED_TRANSPORT_NUMBER)
      END AS VALUE_8
      ,'flightNo' AS KEY_9
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',CONCAT(IFNULL(fdf.FLIGHT_NUMBER,''),'-',IFNULL(fdf.AIRLINE_NAME,'')),NULL) AS VALUE_9
      ,'trainNo' AS KEY_10
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',CONCAT(IFNULL(fdtrn.TRAIN_NUMBER,''),'-',IFNULL(fdtrn.TRAIN_NAME,'')),NULL) AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',fdv.REG_NUMBER,NULL) AS VALUE_11
      ,'departureTime' AS KEY_12
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',fft.DEPARTURE_TIME,NULL) AS VALUE_12
      ,'arrivalTime' AS KEY_13
      ,IFNULL(trip1.ARRIVAL_TIME ,fft.ARRIVAL_TIME) AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement fflm ON loadConnected.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON fflm.UPDATED_PROCESS_FROM = process.PROCESS_ID
      LEFT JOIN ff_f_trip_serviced_by tripServicedBy ON fflm.TRIP_SERVICED_BY = tripServicedBy.TRIP_SERVICED_BY_ID
      LEFT JOIN ff_f_trip fft ON tripServicedBy.TRIP = fft.TRIP_ID
      LEFT JOIN ff_f_transport transport ON fft.TRANSPORT = transport.TRANSPORT_ID
      LEFT JOIN ff_d_flight fdf ON transport.FLIGHT = fdf.FLIGHT_ID
      LEFT JOIN ff_d_train fdtrn ON transport.TRAIN = fdtrn.TRAIN_ID
      LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
      LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
      LEFT JOIN ff_d_transport_mode ffdtm ON ffdtm.TRANSPORT_MODE_ID = fflm.TRANSPORT_MODE
      
      LEFT JOIN ff_f_transport transport1 ON transport1.VEHICLE = fdv1.VEHICLE_ID
      LEFT JOIN ff_f_trip trip1 ON trip1.TRANSPORT = transport1.TRANSPORT_ID
WHERE  fflm.MOVEMENT_DIRECTION = 'D'
AND manifest.MANIFEST_DIRECTION='O'
AND manifest.MANIFEST_NO = MANIFEST_NO

UNION

-- DISPATCH
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      fflm.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      fflm.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'other' AS KEY_5,
      CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',fft.DEPARTURE_TIME,'#',fft.ARRIVAL_TIME) AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      'gatePassNo' AS KEY_7,
      fflm.GATE_PASS_NUMBER AS VALUE_7,
      'transportModeAndNo' AS KEY_8
      ,CASE
        WHEN fflm.VEHICLE_TYPE = 'M'
          THEN CONCAT('Road','#',fdv1.REG_NUMBER)
        WHEN fflm.VEHICLE_TYPE = 'O'
          THEN CONCAT('Road','#',fflm.VEHICLE_REG_NUMBER)
        WHEN fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'O'
          THEN CONCAT(ffdtm.TRANSPORT_MODE_DESCRIPTION,'#',fflm.ROUTE_SERVICED_TRANSPORT_NUMBER)
      END AS VALUE_8
      ,'flightNo' AS KEY_9
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',CONCAT(IFNULL(fdf.FLIGHT_NUMBER,''),'-',IFNULL(fdf.AIRLINE_NAME,'')),NULL) AS VALUE_9
      ,'trainNo' AS KEY_10
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',CONCAT(IFNULL(fdtrn.TRAIN_NUMBER,''),'-',IFNULL(fdtrn.TRAIN_NAME,'')),NULL) AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',fdv.REG_NUMBER,NULL) AS VALUE_11
      ,'departureTime' AS KEY_12
      ,IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',fft.DEPARTURE_TIME,NULL) AS VALUE_12
      ,'arrivalTime' AS KEY_13
      ,IFNULL(trip1.ARRIVAL_TIME ,fft.ARRIVAL_TIME) AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement fflm ON loadConnected.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON fflm.UPDATED_PROCESS_FROM = process.PROCESS_ID
      LEFT JOIN ff_f_trip_serviced_by tripServicedBy ON fflm.TRIP_SERVICED_BY = tripServicedBy.TRIP_SERVICED_BY_ID
      LEFT JOIN ff_f_trip fft ON tripServicedBy.TRIP = fft.TRIP_ID
      LEFT JOIN ff_f_transport transport ON fft.TRANSPORT = transport.TRANSPORT_ID
      LEFT JOIN ff_d_flight fdf ON transport.FLIGHT = fdf.FLIGHT_ID
      LEFT JOIN ff_d_train fdtrn ON transport.TRAIN = fdtrn.TRAIN_ID
      LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
      LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
      LEFT JOIN ff_d_transport_mode ffdtm ON ffdtm.TRANSPORT_MODE_ID = fflm.TRANSPORT_MODE
      
      LEFT JOIN ff_f_transport transport1 ON transport1.VEHICLE = fdv1.VEHICLE_ID
      LEFT JOIN ff_f_trip trip1 ON trip1.TRANSPORT = transport1.TRANSPORT_ID
WHERE  fflm.MOVEMENT_DIRECTION = 'D'
AND manifest.MANIFEST_DIRECTION='O'
AND manifest.MANIFEST_ID IN (SELECT * FROM manifestsForDispatchBag)

UNION

-- RECEIVE
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      loadMovement.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      loadMovement.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'gatePassNo' AS KEY_5,
      loadMovement.GATE_PASS_NUMBER AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      NULL AS KEY_7,
      NULL AS VALUE_7,
      NULL AS KEY_8,
      NULL AS VALUE_8,
      NULL AS KEY_9,
      NULL AS VALUE_9,
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      NULL AS KEY_12,
      NULL AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement loadMovement ON loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE loadMovement.MOVEMENT_DIRECTION = 'R'
AND loadMovement.RECEIVE_TYPE = 'L'
AND manifest.MANIFEST_DIRECTION='I'
AND manifest.MANIFEST_NO = MANIFEST_NO

UNION

-- RECEIVE
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      loadMovement.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      loadMovement.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'gatePassNo' AS KEY_5,
      loadMovement.RECEIVE_NUMBER AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      'receiveType' AS KEY_7,
      RECEIVE_TYPE AS VALUE_7,
      NULL AS KEY_8,
      NULL AS VALUE_8,
      NULL AS KEY_9,
      NULL AS VALUE_9,
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      NULL AS KEY_12,
      NULL AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement loadMovement ON loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE loadMovement.MOVEMENT_DIRECTION = 'R'
AND loadMovement.RECEIVE_TYPE = 'O'
AND manifest.MANIFEST_DIRECTION='I'
AND manifest.MANIFEST_NO = MANIFEST_NO

UNION

-- LOCAL RECEIVE
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      loadMovement.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      loadMovement.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'gatePassNo' AS KEY_5,
      loadMovement.GATE_PASS_NUMBER AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      NULL AS KEY_7,
      NULL AS VALUE_7,
      NULL AS KEY_8,
      NULL AS VALUE_8,
      NULL AS KEY_9,
      NULL AS VALUE_9,
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      NULL AS KEY_12,
      NULL AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement loadMovement ON loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE loadMovement.MOVEMENT_DIRECTION = 'R'
AND loadMovement.RECEIVE_TYPE = 'L'
AND manifest.MANIFEST_DIRECTION='I'
AND manifest.MANIFEST_ID IN (SELECT * FROM manifestsForRecieveBag)

UNION

-- OUT STATION RECEIVE
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      manifest.MANIFEST_NO AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      loadMovement.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      loadMovement.ORIGIN_OFFICE AS VALUE_1,
      'destOff' AS KEY_2,
      DEST_OFFICE AS VALUE_2,
      'weight' AS KEY_3,
      FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
      'date' AS KEY_4,
      LOADING_DATE AS VALUE_4,
      'gatePassNo' AS KEY_5,
      loadMovement.RECEIVE_NUMBER AS VALUE_5,
      'manifestNo' AS KEY_6,
      manifest.MANIFEST_NO AS VALUE_6,
      'receiveType' AS KEY_7,
      RECEIVE_TYPE AS VALUE_7,
      NULL AS KEY_8,
      NULL AS VALUE_8,
      NULL AS KEY_9,
      NULL AS VALUE_9,
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      NULL AS KEY_12,
      NULL AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_load_connected loadConnected
      LEFT JOIN ff_f_load_movement loadMovement ON loadConnected.LOAD_MOVEMENT = loadMovement.LOAD_MOVEMENT_ID
      LEFT JOIN ff_f_manifest manifest ON loadConnected.MANIFEST = manifest.MANIFEST_ID
      LEFT JOIN ff_d_process process ON loadMovement.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE loadMovement.MOVEMENT_DIRECTION = 'R'
AND loadMovement.RECEIVE_TYPE = 'O'
AND manifest.MANIFEST_DIRECTION='I'
AND manifest.MANIFEST_ID IN (SELECT * FROM manifestsForRecieveOutBag)
              
UNION

-- HELDUP
SELECT 'M' AS ARTIFACT_TYPE,
      NULL AS CONSG_NO,
      TRANSACTION_NUMBER AS MANIFEST_NO,
      process.PROCESS_CODE AS PROCESS_NO,
      process.PROCESS_ORDER AS PROCESS_ORDER,
      heldup.CREATED_DATE AS PROCESS_DATE,
      'originOff' AS KEY_1,
      OFFICE AS VALUE_1,
      'hubOff' AS KEY_2,
      office.REPORTING_HUB AS VALUE_2,
      'date' AS KEY_3,
      CREATED_DATE AS VALUE_3,
      NULL AS KEY_4,
      NULL AS VALUE_4,
      NULL AS KEY_5,
      NULL AS VALUE_5,
      NULL AS KEY_6,
      NULL AS VALUE_6,
      NULL AS KEY_7,
      NULL AS VALUE_7,
      NULL AS KEY_8,
      NULL AS VALUE_8,
      NULL AS KEY_9,
      NULL AS VALUE_9,
      NULL AS KEY_10,
      NULL AS VALUE_10,
      NULL AS KEY_11,
      NULL AS VALUE_11,
      NULL AS KEY_12,
      NULL AS VALUE_12,
      NULL AS KEY_13,
      NULL AS VALUE_13,
      NULL AS KEY_14,
      NULL AS VALUE_14
FROM ff_f_heldup heldup
      LEFT JOIN ff_d_office office ON heldup.OFFICE = office.OFFICE_ID
      LEFT JOIN ff_d_process process ON heldup.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE TRANSACTION_TYPE IN ('OPMF', 'OGMF', 'BPLD', 'BPLP', 'MBPL')
AND TRANSACTION_NUMBER = MANIFEST_NO) mnfst_track,
             (SELECT @rownum := 0) r
      ORDER BY mnfst_track.PROCESS_DATE, mnfst_track.PROCESS_ORDER;

      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForDispatchBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveOutBag;
   END;
