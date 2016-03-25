################################################
-- Procedure name - sp_track_manifest
-- Purpose - To track manifest details
-- Created by and Date - UPASANA
-- Version - 2.2
-- Modified by - SHAHNAZ
-- Modified Date - 29 Jan 2015
-- v2.2 -  artf3758615 : Third party name not refelcting in consignment tracking 
################################################
DROP PROCEDURE IF EXISTS sp_track_manifest;
CREATE PROCEDURE `sp_track_manifest`(IN MANIFEST_NO VARCHAR(25))
BEGIN

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;
DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForDispatchBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveBag;
DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveOutBag;

CREATE TEMPORARY TABLE manifestsMappedToPacketOrBag ENGINE=memory
SELECT parentm.manifest_no FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_no = MANIFEST_NO
AND parentm.manifest_no IS NOT NULL      ;

CREATE TEMPORARY TABLE manifestsMappedToMasterBag ENGINE=memory
SELECT parentm.manifest_no FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_no IN  (SELECT * FROM manifestsMappedToPacketOrBag)
AND parentm.manifest_no IS NOT NULL      ;

CREATE TEMPORARY TABLE manifestsForDispatchBag ENGINE=memory
SELECT parentm.manifest_no FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_no = MANIFEST_NO
AND parentm.manifest_no IS NOT NULL      ;

CREATE TEMPORARY TABLE manifestsForRecieveBag ENGINE=memory
SELECT parentm.manifest_no FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_no  = MANIFEST_NO
AND parentm.manifest_no IS NOT NULL      ;

CREATE TEMPORARY TABLE manifestsForRecieveOutBag ENGINE=memory
SELECT parentm.manifest_no FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.manifest_no  = MANIFEST_NO
AND parentm.manifest_no IS NOT NULL      ;

      SELECT @rownum := @rownum + 1 AS ROW_ID,
             mnfst_track.PROCESS_ID,
             mnfst_track.ARTIFACT_TYPE,
             mnfst_track.OPERATING_OFFICE,                      -- Newly added
             mnfst_track.CONSG_NO,
             mnfst_track.MANIFEST_NO,
             mnfst_track.PROCESS_NO,
--              mnfst_track.OPERATING_LEVEL,
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
        FROM (SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
                     IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO, -- OUT MANIFEST CREATED BY DISPATCH PROCESS
--                      NULL AS OPERATING_LEVEL,
                     ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
                     ff_f_manifest.CREATED_DATE AS PROCESS_DATE,
                     ff_f_manifest.MANIFEST_DIRECTION AS DIRECTION,
                     'originOff' AS KEY_1,
                     convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)
                        AS VALUE_1,
                     'destOff' AS KEY_2,
                     -- convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
                     -- changed for in-manifest destination office should be shown as operating office in tracking.
                     IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ff_f_manifest.MANIFEST_DATE,
                                    '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'city' AS KEY_7,
                     (SELECT city_id
                        FROM ff_d_office
                       WHERE office_id =
                                convert(ff_f_manifest.ORIGIN_OFFICE,
                                        CHARACTER))
                        AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ff_f_manifest.DESTINATION_CITY, CHARACTER)
                        AS VALUE_8,
                     'custName' AS KEY_9, -- Added in Version 2.0
--                      ff_f_manifest.VENDOR_ID AS VALUE_9,
                     fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
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
                FROM ff_f_manifest ff_f_manifest
                  INNER JOIN ff_d_process ff_d_process
                      ON ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID
                  LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ff_f_manifest.VENDOR_ID
               WHERE ff_f_manifest.MANIFEST_NO = MANIFEST_NO
              UNION
              SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
                    -- ff_f_manifest.MANIFEST_PROCESS_CODE AS PROCESS_NO,
                     IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
                     ff_f_manifest.CREATED_DATE AS PROCESS_DATE,
                     ff_f_manifest.MANIFEST_DIRECTION AS DIRECTION,
                     'originOff' AS KEY_1,
                     convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)
                        AS VALUE_1,
                     'destOff' AS KEY_2,
                     -- convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
                     -- changed for in-manifest destination office should be shown as operating office in tracking.
                      IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ff_f_manifest.MANIFEST_DATE,
                                    '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'city' AS KEY_7,
                     (SELECT city_id
                        FROM ff_d_office
                       WHERE office_id =
                                convert(ff_f_manifest.ORIGIN_OFFICE,
                                        CHARACTER))
                        AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ff_f_manifest.DESTINATION_CITY, CHARACTER)
                        AS VALUE_8,
                     'custName' AS KEY_9, -- Added in Version 2.0
--                      ff_f_manifest.VENDOR_ID AS VALUE_9,
                     fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
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
                FROM ff_f_manifest ff_f_manifest
                      INNER JOIN ff_d_process ff_d_process
                      ON ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID
                      LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ff_f_manifest.VENDOR_ID
               WHERE ff_f_manifest.MANIFEST_NO IN
                        (SELECT * FROM manifestsMappedToPacketOrBag)
              UNION
              SELECT ff_f_manifest.MANIFEST_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     ff_f_manifest.MANIFEST_NO AS MANIFEST_NO,
                     -- ff_f_manifest.MANIFEST_PROCESS_CODE AS PROCESS_NO,
                     IF((STRCMP(ff_f_manifest.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ff_f_manifest.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(ff_f_manifest.MANIFEST_LOAD_CONTENT,'1')=0),'OBDX','OBPC'),'OMBG'),
                     ff_f_manifest.MANIFEST_PROCESS_CODE) AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     ff_d_process.PROCESS_ORDER AS PROCESS_ORDER,
                     ff_f_manifest.CREATED_DATE AS PROCESS_DATE,
                     ff_f_manifest.MANIFEST_DIRECTION AS DIRECTION,
                     'originOff' AS KEY_1,
                     convert(ff_f_manifest.ORIGIN_OFFICE, CHARACTER)
                        AS VALUE_1,
                     'destOff' AS KEY_2,
                     -- convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER) AS VALUE_2,
                     -- changed for in-manifest destination office should be shown as operating office in tracking.
                      IF(STRCMP(ff_f_manifest.MANIFEST_DIRECTION,'I'),convert(ff_f_manifest.DESTINATION_OFFICE, CHARACTER),convert(ff_f_manifest.OPERATING_OFFICE, CHARACTER)) as VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ff_f_manifest.MANIFEST_WEIGHT,3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ff_f_manifest.MANIFEST_DATE,
                                    '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ff_f_manifest.MANIFEST_DIRECTION, CHARACTER)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ff_f_manifest.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'city' AS KEY_7,
                     (SELECT city_id
                        FROM ff_d_office
                       WHERE office_id =
                                convert(ff_f_manifest.ORIGIN_OFFICE,
                                        CHARACTER))
                        AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ff_f_manifest.DESTINATION_CITY, CHARACTER)
                        AS VALUE_8,
                     'custName' AS KEY_9, -- Added in Version 2.0
--                      ff_f_manifest.VENDOR_ID AS VALUE_9,
                     fdv.BUSINESS_NAME AS VALUE_9, -- Added in Version 2.2
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
                FROM ff_f_manifest ff_f_manifest
                      INNER JOIN ff_d_process ff_d_process ON ff_f_manifest.UPDATING_PROCESS = ff_d_process.PROCESS_ID
                      LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ff_f_manifest.VENDOR_ID
               WHERE ff_f_manifest.MANIFEST_NO IN
                        (SELECT * FROM manifestsMappedToMasterBag)
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
                     'originOff' AS KEY_1,
                     loadMovement.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'CD_AWB_RR' AS KEY_5,
                     TOKEN_NUMBER AS VALUE_5,
                     'departureTime' AS KEY_6,
                     trip.DEPARTURE_TIME AS VALUE_6,
                     'arrivalTime' AS KEY_7,
                     trip.ARRIVAL_TIME AS VALUE_7,
                     'flightNo' AS KEY_8,
                     flight.FLIGHT_NUMBER AS VALUE_8,
                     'trainNo' AS KEY_9,
                     train.TRAIN_NUMBER AS VALUE_9,
                     'vehicleNo' AS KEY_10,
                     vehicle1.REG_NUMBER AS VALUE_10,
                     'gatePassNo' AS KEY_12,
                     loadMovement.GATE_PASS_NUMBER AS VALUE_12,
                     'regionVehicleNo' AS KEY_12,
                     vehicle1.REG_NUMBER AS VALUE_12,
                     'regionOtherVehicleNo1' AS KEY_13,
                     loadMovement.VEHICLE_REG_NUMBER AS VALUE_13,
                     'manifestNo' AS KEY_14,
                     manifest.MANIFEST_NO AS VALUE_14
                FROM ff_f_load_connected loadConnected
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by tripServicedBy
                        ON loadMovement.TRIP_SERVICED_BY =
                              tripServicedBy.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON tripServicedBy.TRIP = trip.TRIP_ID
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_flight flight
                        ON transport.FLIGHT = flight.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle vehicle
                        ON transport.VEHICLE = vehicle.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle vehicle1
                        ON loadMovement.VEHICLE = vehicle1.VEHICLE_ID
               WHERE     MOVEMENT_DIRECTION = 'D'
                     AND manifest.MANIFEST_NO = MANIFEST_NO
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
                     'originOff' AS KEY_1,
                     loadMovement.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'CD_AWB_RR' AS KEY_5,
                     TOKEN_NUMBER AS VALUE_5,
                     'departureTime' AS KEY_6,
                     trip.DEPARTURE_TIME AS VALUE_6,
                     'arrivalTime' AS KEY_7,
                     trip.ARRIVAL_TIME AS VALUE_7,
                     'flightNo' AS KEY_8,
                     flight.FLIGHT_NUMBER AS VALUE_8,
                     'trainNo' AS KEY_9,
                     train.TRAIN_NUMBER AS VALUE_9,
                     'vehicleNo' AS KEY_10,
                     vehicle1.REG_NUMBER AS VALUE_10,
                     'gatePassNo' AS KEY_12,
                     loadMovement.GATE_PASS_NUMBER AS VALUE_12,
                     'regionVehicleNo' AS KEY_12,
                     vehicle1.REG_NUMBER AS VALUE_12,
                     'regionOtherVehicleNo1' AS KEY_13,
                     loadMovement.VEHICLE_REG_NUMBER AS VALUE_13,
                     'manifestNo' AS KEY_14,
                     manifest.MANIFEST_NO AS VALUE_14
                FROM ff_f_load_connected loadConnected
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by tripServicedBy
                        ON loadMovement.TRIP_SERVICED_BY =
                              tripServicedBy.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON tripServicedBy.TRIP = trip.TRIP_ID
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_flight flight
                        ON transport.FLIGHT = flight.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle vehicle
                        ON transport.VEHICLE = vehicle.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle vehicle1
                        ON loadMovement.VEHICLE = vehicle1.VEHICLE_ID
               WHERE     MOVEMENT_DIRECTION = 'D'
                     AND manifest.MANIFEST_NO IN
                            (SELECT * FROM manifestsForDispatchBag)
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
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
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
               WHERE     MOVEMENT_DIRECTION = 'R'
                     AND RECEIVE_TYPE = 'L'
                     AND manifest.MANIFEST_NO = MANIFEST_NO
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
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
                     RECEIVE_TYPE as VALUE_7,
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
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
               WHERE     MOVEMENT_DIRECTION = 'R'
                     AND RECEIVE_TYPE = 'O'
                     AND manifest.MANIFEST_NO = MANIFEST_NO
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
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
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
               WHERE     MOVEMENT_DIRECTION = 'R'
                     AND RECEIVE_TYPE = 'L'
                     AND manifest.MANIFEST_NO IN
                            (SELECT * FROM manifestsForRecieveBag)
              UNION
              SELECT loadMovement.LOAD_MOVEMENT_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     manifest.MANIFEST_NO AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     loadMovement.CREATED_DATE AS PROCESS_DATE,
                     loadMovement.MOVEMENT_DIRECTION AS DIRECTION,
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
                     RECEIVE_TYPE as VALUE_7,
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
                     LEFT JOIN ff_f_load_movement loadMovement
                        ON loadConnected.LOAD_MOVEMENT =
                              loadMovement.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest manifest
                        ON loadConnected.MANIFEST = manifest.MANIFEST_ID
                     LEFT JOIN ff_d_process process
                        ON loadMovement.UPDATED_PROCESS_FROM =
                              process.PROCESS_ID
               WHERE     MOVEMENT_DIRECTION = 'R'
                     AND RECEIVE_TYPE = 'O'
                     AND manifest.MANIFEST_NO IN
                            (SELECT * FROM manifestsForRecieveOutBag)
              UNION
              SELECT heldup.HELDUP_ID AS PROCESS_ID,
                     'M' AS ARTIFACT_TYPE,
                     NULL AS OPERATING_OFFICE,
                     NULL AS CONSG_NO,
                     TRANSACTION_NUMBER AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
--                      NULL AS OPERATING_LEVEL,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     heldup.CREATED_DATE AS PROCESS_DATE,
                     NULL AS DIRECTION,
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
                     LEFT JOIN ff_d_office office
                        ON heldup.OFFICE = office.OFFICE_ID
                     LEFT JOIN ff_d_process process
                        ON heldup.UPDATED_PROCESS_FROM = process.PROCESS_ID
               WHERE     TRANSACTION_TYPE IN
                            ('OPMF', 'OGMF', 'BPLD', 'BPLP', 'MBPL')
                     AND TRANSACTION_NUMBER = MANIFEST_NO) mnfst_track,
             (SELECT @rownum := 0) r
      ORDER BY mnfst_track.PROCESS_DATE, mnfst_track.PROCESS_ORDER;

      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForDispatchBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveBag;
      DROP TEMPORARY TABLE IF EXISTS manifestsForRecieveOutBag;
   END;
