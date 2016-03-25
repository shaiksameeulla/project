DROP PROCEDURE IF EXISTS sp_track_consignment;
CREATE PROCEDURE `sp_track_consignment`(IN CONSG_NO VARCHAR(255))
BEGIN

-- Procedure name - sp_track_consignment
-- Purpose - Track a consignment details
-- Created by and Date - UPASANA
-- Version - 2.14
-- Modified by - SHAHNAZ
-- Modified Date - 29 Jan 2015
-- V2.14 - In details tracking out for delivery and delivery time can’t be same..artf3751789
-- V2.14 - Third party name not refelcting in consignment tracking..artf3758615 
-- V2.14 - Two different dates are reflecting in Booking date in consignment tracking..artf3781186   

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToConsignment;

DROP TEMPORARY TABLE IF EXISTS packetOrBagManifest;

DROP TEMPORARY TABLE IF EXISTS packetOrBagManifestId;

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;

DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagDispatch;

DROP TEMPORARY TABLE IF EXISTS manifestsForBagDispatch;

DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagRecieve;

DROP TEMPORARY TABLE IF EXISTS manifestsForBagRecieve;

create temporary table manifestsMappedToConsignment engine=memory
SELECT DISTINCT ffc.CONSG_NO,
       ffc.CONSG_STATUS,
       ffc.CUSTOMER,
       ffm.MANIFEST_NO,
       ffm.MANIFEST_ID
  FROM ff_f_consignment ffc
  LEFT JOIN ff_f_consignment_manifested ffcm ON ffcm.CONSIGNMENT_ID = ffc.CONSG_ID
  LEFT JOIN ff_f_manifest ffm ON ffcm.MANIFEST_ID = ffm.MANIFEST_ID
  WHERE ffc.CONSG_NO = CONSG_NO;

create temporary table packetOrBagManifest engine=memory
select cnManifested.MANIFEST_ID from manifestsMappedToConsignment cnManifested;

create temporary table packetOrBagManifestId engine=memory
select cnManifested.MANIFEST_ID from packetOrBagManifest cnManifested;

create temporary table manifestsMappedToPacketOrBag engine=memory
select parentm.MANIFEST_ID from ff_f_manifest childm
join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.MANIFEST_NO IN (select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested)
and parentm.manifest_no is not null;

create temporary table manifestsMappedToMasterBag engine=memory
select parentm.MANIFEST_ID from ff_f_manifest childm
left join ff_f_manifest parentm on childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
where childm.MANIFEST_ID IN (select bagMnfst.* from manifestsMappedToPacketOrBag bagMnfst)
and parentm.manifest_no is not null;

create temporary table manifestsForMasterBagDispatch engine=memory
SELECT ffm_mbag.manifest_no FROM ff_f_manifest ffm_mbag
LEFT JOIN ff_f_manifest ffm_bag ON ffm_bag.MANIFEST_EMBEDDED_IN = ffm_mbag.MANIFEST_ID
LEFT JOIN ff_f_manifest ffm_pkt ON ffm_pkt.MANIFEST_EMBEDDED_IN = ffm_bag.MANIFEST_ID
WHERE ffm_pkt.MANIFEST_NO IN (select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested);

create temporary table manifestsForBagDispatch engine=memory
select ffm1.manifest_no from ff_f_manifest ffm1 
left join ff_f_manifest ffm2 on ffm2.MANIFEST_EMBEDDED_IN = ffm1.manifest_id
where ffm2.MANIFEST_NO in (select cnManifested.MANIFEST_NO from manifestsMappedToConsignment cnManifested);

create temporary table manifestsForMasterBagRecieve engine=memory
select * from manifestsForMasterBagDispatch;

create temporary table manifestsForBagRecieve engine=memory
select * from manifestsForBagDispatch;

select @rownum:=@rownum+1 as ROW_ID,
       cn_track.PROCESS_ID,
       cn_track.ARTIFACT_TYPE,
	     cn_track.OPERATING_OFFICE, 
       cn_track.CONSG_NO,
       cn_track.MANIFEST_NO,
       cn_track.PROCESS_NO,
--        cn_track.OPERATING_LEVEL,
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

-- ROW fOR PICKUP
SELECT ffb.BOOKING_ID AS PROCESS_ID
      ,'C' as ARTIFACT_TYPE
      ,ffb.BOOKING_OFF as OPERATING_OFFICE
      ,ffb.consg_number AS CONSG_NO
      ,NULL AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
--       ,ffprh.RUNSHEET_DATE as PROCESS_DATE
      ,ffb.CREATED_DATE AS PROCESS_DATE
      ,NULL as DIRECTION
      ,'originOff' AS  	KEY_1
      ,ffb.BOOKING_OFF AS VALUE_1
      ,'orgCity' AS KEY_2
      ,fdc.CITY_ID AS VALUE_2
      ,'date' AS KEY_3
--       ,ffprh.RUNSHEET_DATE AS VALUE_3
      ,ffb.CREATED_DATE AS VALUE_3
      ,'custName' AS KEY_4
      ,ffb.CUSTOMER AS VALUE_4
      ,NULL AS 	KEY_5
      ,NULL as VALUE_5
      ,NULL AS KEY_6
      ,NULL AS VALUE_6
      ,NULL AS KEY_7
      ,NULL AS VALUE_7
      ,NULL AS KEY_8
      ,NULL AS VALUE_8
      ,NULL AS KEY_9
      ,NULL AS VALUE_9
      ,NULL AS KEY_10
      ,NULL AS VALUE_10
      ,NULL AS KEY_11
      ,NULL AS VALUE_11
      ,NULL AS KEY_12
      ,NULL AS VALUE_12
      ,NULL AS KEY_13
      ,NULL AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM 	ff_f_booking ffb
-- INNER JOIN ff_f_pickup_runsheet_header ffprh ON ffprh.RUNSHEET_NO = ffb.PICKUP_RUNSHEET_NO
LEFT JOIN ff_d_office fdo ON fdo.office_id = ffb.BOOKING_OFF
LEFT JOIN ff_d_city fdc ON fdc.CITY_ID = fdo.CITY_ID
LEFT JOIN ff_d_process fdp ON fdp.PROCESS_CODE = 'UPPU'
WHERE ffb.PICKUP_RUNSHEET_NO <> ''
AND ffb.PICKUP_RUNSHEET_NO IS NOT NULL
AND ffb.consg_number = CONSG_NO

UNION

--
-- Sequel to get booked consignments records
-- Replaced ff_f_booking with ff_f_consignment
-- In case of consignment created from OPKT/OBPC or updated with RTOH , OPKT/OBPC/RTOH records are coming twice. 
-- So added PROCESS_NO as 'BOOK'
-- ROW FOR BOOK

SELECT  ffc.CONSG_ID AS PROCESS_ID
        ,'C' AS ARTIFACT_TYPE
        ,ffc.ORG_OFF AS OPERATING_OFFICE
        ,ffc.CONSG_NO AS CONSG_NO
        ,NULL AS MANIFEST_NO
        ,fdp.PROCESS_CODE AS PROCESS_NO
--         ,NULL AS OPERATING_LEVEL
        ,fdp.PROCESS_ORDER AS PROCESS_ORDER
--         ,ffc.CREATED_DATE AS PROCESS_DATE
        ,IFNULL(ffb.BOOKING_DATE,ffc.CREATED_DATE) AS PROCESS_DATE
        ,NULL AS DIRECTION
        ,'originOff' AS KEY_1
        ,convert(ffc.ORG_OFF, CHARACTER) AS VALUE_1
        ,'orgCity' AS KEY_2
        ,(select city_id from ff_d_office where office_id = convert(ffc.ORG_OFF, CHARACTER)) AS VALUE_2
        ,'weight' AS KEY_3
--         ,convert(FORMAT(ffc.FINAL_WEIGHT,3), CHARACTER) AS VALUE_3
        ,convert(FORMAT(IFNULL(ffb.FINAL_WEIGHT,ffc.FINAL_WEIGHT),3), CHARACTER) AS VALUE_3
        ,'noOfPieces' AS KEY_4
        ,convert(ffc.NO_OF_PCS, CHARACTER) AS VALUE_4
        ,'other' AS KEY_5
        ,CONCAT(coalesce(ffc.REF_NO,''),'#',ffc.ORG_OFF,'#',ffc.DEST_PINCODE) AS VALUE_5
        ,'date' AS KEY_6
        ,convert(date_format(ffc.CREATED_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) AS VALUE_6
        ,'destCity' AS KEY_7
        ,fdpin.city_id AS VALUE_7
        ,'custName' AS KEY_8
        ,ffc.CUSTOMER AS VALUE_8
        ,NULL AS KEY_9
        ,NULL as VALUE_9
        ,NULL AS KEY_10
        ,NULL AS VALUE_10
        ,NULL AS KEY_11
        ,NULL AS VALUE_11
        ,NULL AS KEY_12
        ,NULL AS VALUE_12
        ,NULL AS KEY_13
        ,NULL AS VALUE_13
        ,NULL AS KEY_14
        ,NULL AS VALUE_14
FROM ff_f_consignment ffc
LEFT JOIN ff_f_booking ffb ON ffb.CONSG_NUMBER = ffc.CONSG_NO
LEFT JOIN ff_d_pincode fdpin ON fdpin.pincode_id = ffc.DEST_PINCODE
LEFT JOIN ff_d_process fdp ON fdp.PROCESS_CODE = 'BOOK' 
WHERE ffc.CONSG_NO = CONSG_NO
AND ffc.IS_EXCESS_CONSG = 'N'

UNION

-- PACKET MANIFEST
SELECT ffm.MANIFEST_ID AS PROCESS_ID
       ,'M' AS ARTIFACT_TYPE
	     ,ffm.OPERATING_OFFICE AS OPERATING_OFFICE 
       ,tmp_cnManifested.CONSG_NO AS CONSG_NO
       ,ffm.MANIFEST_NO AS MANIFEST_NO
       ,IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
            IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
            IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
            ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO
--        ,NULL AS OPERATING_LEVEL
       ,fdp.PROCESS_ORDER AS PROCESS_ORDER
       ,ffm.CREATED_DATE AS PROCESS_DATE
       ,ffm.MANIFEST_DIRECTION AS DIRECTION
       ,'originOff' AS KEY_1
       ,convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1
       ,'destOff' AS KEY_2
       ,IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),
            convert(ffm.DESTINATION_OFFICE, CHARACTER),
            convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2
       ,'weight' AS KEY_3
       ,convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) AS VALUE_3
       ,'date' AS KEY_4
       ,convert(date_format(ffm.MANIFEST_DATE,'%m-%d-%Y %h:%i:%s'), CHARACTER) AS VALUE_4
       ,'direction' AS KEY_5
       ,convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5
       ,'manifestNo' AS KEY_6
       ,convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6
       ,'orgCity' AS KEY_7
       ,fdo.city_id AS VALUE_7
       ,'destCity' AS KEY_8
       ,convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8
       ,'consgNo' AS KEY_9
       ,tmp_cnManifested.CONSG_NO AS VALUE_9
       ,'custName' AS KEY_10
--        ,tmp_cnManifested.CUSTOMER AS VALUE_10 -- artf3758615 
       ,fdv.BUSINESS_NAME AS VALUE_10
       ,'cnStatus' AS KEY_11
       ,tmp_cnManifested.CONSG_STATUS AS VALUE_11
			 ,NULL AS KEY_12
       ,NULL AS VALUE_12
       ,NULL AS KEY_13
       ,NULL AS VALUE_13
       ,NULL AS KEY_14
       ,NULL AS VALUE_14
FROM ff_f_manifest ffm
LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
LEFT JOIN ff_d_process fdp ON fdp.PROCESS_CODE = ffm.MANIFEST_PROCESS_CODE
LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ffm.VENDOR_ID
INNER JOIN manifestsMappedToConsignment tmp_cnManifested
ON ffm.MANIFEST_ID = tmp_cnManifested.MANIFEST_ID

UNION

-- BAG MANIFEST
SELECT ffm.MANIFEST_ID AS PROCESS_ID
      ,'M' AS ARTIFACT_TYPE
      ,ffm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
                     ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,ffm.CREATED_DATE AS PROCESS_DATE
      ,ffm.MANIFEST_DIRECTION AS DIRECTION
      ,'originOff' AS KEY_1
      ,convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1
      ,'destOff' AS KEY_2
      ,IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),
          convert(ffm.DESTINATION_OFFICE, CHARACTER),
          convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2
      ,'weight' AS KEY_3
      ,convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) as VALUE_3
      ,'date' AS KEY_4
      ,convert(date_format(ffm.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) AS VALUE_4
      ,'direction' AS KEY_5
      ,convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6
      ,'orgCity' AS KEY_7
      ,fdo.city_id AS VALUE_7
      ,'destCity' AS KEY_8
      ,convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8
      ,'consgNo' AS KEY_9
      ,CONSG_NO AS VALUE_9
      ,'custName' AS KEY_10
      ,fdv.BUSINESS_NAME AS VALUE_10
      ,NULL AS KEY_11
      ,NULL AS VALUE_11
      ,NULL AS KEY_12
      ,NULL AS VALUE_12
      ,NULL AS KEY_13
      ,NULL AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_manifest ffm
INNER JOIN ff_d_process fdp ON ffm.MANIFEST_PROCESS_CODE = fdp.PROCESS_CODE
LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
LEFT JOIN ff_d_vendor fdv ON fdv.VENDOR_ID = ffm.VENDOR_ID
LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
WHERE  ffm.MANIFEST_ID in (select bagMnfst.* from manifestsMappedToPacketOrBag bagMnfst)

UNION

-- MASTER BAG
SELECT ffm.MANIFEST_ID AS PROCESS_ID
      ,'M' AS ARTIFACT_TYPE
      ,ffm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,IF((STRCMP(ffm.MANIFEST_PROCESS_CODE,'DSPT')=0),
                     IF((STRCMP(SUBSTRING(ffm.MANIFEST_NO,4,1),'B') = 0) ,
                     IF((STRCMP(fdct.CONSIGNMENT_CODE,'DOX')=0),'OBDX','OBPC'),'OMBG'),
                     ffm.MANIFEST_PROCESS_CODE) AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,ffm.CREATED_DATE AS PROCESS_DATE
      ,ffm.MANIFEST_DIRECTION AS DIRECTION
      ,'originOff' AS KEY_1
      ,convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1
      ,'destOff' AS KEY_2
      ,IF(STRCMP(ffm.MANIFEST_DIRECTION,'I'),
          convert(ffm.DESTINATION_OFFICE, CHARACTER),
          convert(ffm.OPERATING_OFFICE, CHARACTER)) AS VALUE_2
      ,'weight' AS KEY_3
      ,convert(FORMAT(ffm.MANIFEST_WEIGHT,3), CHARACTER) as VALUE_3
      ,'date' AS KEY_4
      ,convert(date_format(ffm.MANIFEST_DATE,'%d-%m-%Y %h:%i:%s'), CHARACTER) AS VALUE_4
      ,'direction' AS KEY_5
      ,convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6
      ,'orgCity' AS KEY_7
      ,fdo.city_id AS VALUE_7
      ,'destCity' AS KEY_8
      ,convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8
      ,'consgNo' AS KEY_9
      ,CONSG_NO AS VALUE_9
      ,NULL AS KEY_10
      ,NULL AS VALUE_10
      ,NULL AS KEY_11
      ,NULL AS VALUE_11
      ,NULL AS KEY_12
      ,NULL AS VALUE_12
      ,NULL AS KEY_13
      ,NULL AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_manifest ffm
INNER JOIN ff_d_process fdp ON ffm.MANIFEST_PROCESS_CODE = fdp.PROCESS_CODE
LEFT JOIN ff_d_consignment_type fdct ON fdct.consignment_type_id = ffm.MANIFEST_LOAD_CONTENT
LEFT JOIN ff_d_office fdo ON fdo.office_id = ffm.ORIGIN_OFFICE
WHERE  ffm.MANIFEST_ID in (select masterBagMnfst.* from manifestsMappedToMasterBag masterBagMnfst)

UNION

-- DISPATCH
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,fflm.CREATED_DATE AS PROCESS_DATE
      ,fflm.MOVEMENT_DIRECTION AS DIRECTION
      ,'originOff' AS KEY_1
      ,fflm.ORIGIN_OFFICE AS VALUE_1
      ,'destOff' AS KEY_2
      ,DEST_OFFICE AS VALUE_2
      ,'weight' AS KEY_3
      ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
      ,'date' AS KEY_4
      ,LOADING_DATE AS VALUE_4
      ,'other' AS KEY_5
      ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',fft.DEPARTURE_TIME,'#',fft.ARRIVAL_TIME) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,ffm.MANIFEST_NO AS VALUE_6
      ,'departureTime' AS KEY_7
      ,fft.DEPARTURE_TIME AS VALUE_7
      ,'arrivalTime' AS KEY_8
      ,fft.ARRIVAL_TIME AS VALUE_8
      ,'flightNo' AS KEY_9
      ,fdf.FLIGHT_NUMBER as VALUE_9
      ,'trainNo' AS KEY_10
      ,fdtrn.TRAIN_NUMBER AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,fdv.REG_NUMBER AS VALUE_11
      ,'gatePassNo' AS KEY_12
      ,fflm.GATE_PASS_NUMBER AS VALUE_12
      ,'regionVehicleNo' AS KEY_13
      ,fdv1.REG_NUMBER AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM   ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
LEFT JOIN ff_f_trip fft ON fftsb.TRIP = fft.TRIP_ID
LEFT JOIN ff_f_transport transport ON fft.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight fdf ON transport.FLIGHT = fdf.FLIGHT_ID
LEFT JOIN ff_d_train fdtrn ON transport.TRAIN = fdtrn.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.manifest_no IN (select dispManifest.* from manifestsForMasterBagDispatch dispManifest)
AND fflm.MOVEMENT_DIRECTION='D'

UNION

-- ROW FOR DISPATCH
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
        ,'C' AS ARTIFACT_TYPE
        ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
        ,CONSG_NO AS CONSG_NO
        ,ffm.MANIFEST_NO AS MANIFEST_NO
        ,fdp.PROCESS_CODE AS PROCESS_NO
--         ,NULL AS OPERATING_LEVEL
        ,fdp.PROCESS_ORDER AS PROCESS_ORDER
        ,fflm.CREATED_DATE AS PROCESS_DATE
        ,fflm.MOVEMENT_DIRECTION AS DIRECTION
        ,'originOff'AS KEY_1
        ,fflm.ORIGIN_OFFICE AS VALUE_1
        ,'destOff' AS KEY_2
        ,DEST_OFFICE AS VALUE_2
        ,'weight' AS KEY_3
        ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
        ,'date' AS KEY_4
        ,LOADING_DATE AS VALUE_4
        ,'other' AS KEY_5
        ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',fftrp.DEPARTURE_TIME,'#',fftrp.ARRIVAL_TIME) AS VALUE_5
        ,'manifestNo' AS KEY_6
        ,ffm.MANIFEST_NO AS VALUE_6
        ,'departureTime' AS KEY_7
        ,fftrp.DEPARTURE_TIME AS VALUE_7
        ,'arrivalTime' AS KEY_8
        ,fftrp.ARRIVAL_TIME AS VALUE_8
        ,'flightNo' AS KEY_9
        ,fdf.FLIGHT_NUMBER AS VALUE_9
        ,'trainNo' AS KEY_10
        ,train.TRAIN_NUMBER AS VALUE_10
        ,'vehicleNo' AS KEY_11
        ,fdv.REG_NUMBER AS VALUE_11
        ,'gatePassNo' AS KEY_12
        ,fflm.GATE_PASS_NUMBER AS VALUE_12
        ,'regionVehicleNo' AS KEY_13
        ,fdv1.REG_NUMBER AS VALUE_13
        ,NULL AS KEY_14
        ,NULL AS VALUE_14
FROM ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
LEFT JOIN ff_f_trip fftrp ON fftsb.TRIP = fftrp.TRIP_ID
LEFT JOIN ff_f_transport transport ON fftrp.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight fdf ON transport.FLIGHT = fdf.FLIGHT_ID
LEFT JOIN ff_d_train train ON transport.TRAIN = train.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.MANIFEST_ID IN (select pktOrBagMnfst.* from packetOrBagManifest pktOrBagMnfst)
AND fflm.MOVEMENT_DIRECTION = 'D'

UNION

-- ROW FOR DISPATCH
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,fflm.CREATED_DATE AS PROCESS_DATE
      ,fflm.MOVEMENT_DIRECTION AS DIRECTION
      ,'originOff'AS KEY_1
      ,fflm.ORIGIN_OFFICE AS VALUE_1
      ,'destOff' AS KEY_2
      ,DEST_OFFICE AS VALUE_2
      ,'weight' AS KEY_3
      ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
      ,'date' AS KEY_4
      ,LOADING_DATE AS VALUE_4
      ,'other' AS KEY_5
      ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,ffm.MANIFEST_NO AS VALUE_6
      ,'departureTime' AS KEY_7
      ,trip.DEPARTURE_TIME AS VALUE_7
      ,'arrivalTime' AS KEY_8
      ,trip.ARRIVAL_TIME AS VALUE_8
      ,'flightNo' AS KEY_9
      ,fdf.FLIGHT_NUMBER AS VALUE_9
      ,'trainNo' AS KEY_10
      ,train.TRAIN_NUMBER AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,fdv.REG_NUMBER AS VALUE_11
      ,'gatePassNo' AS KEY_12
      ,fflm.GATE_PASS_NUMBER AS VALUE_12
      ,'regionVehicleNo' AS KEY_13
      ,fdv1.REG_NUMBER AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
LEFT JOIN ff_f_trip trip ON fftsb.TRIP = trip.TRIP_ID
LEFT JOIN ff_f_transport transport ON trip.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight fdf ON transport.FLIGHT = fdf.FLIGHT_ID
LEFT JOIN ff_d_train train ON transport.TRAIN = train.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.manifest_no IN (select dspBagMnfst.* from manifestsForBagDispatch dspBagMnfst)
AND fflm.MOVEMENT_DIRECTION = 'D'

UNION

-- ROW FOR RECIEVE
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,fflm.CREATED_DATE AS PROCESS_DATE
      ,fflm.MOVEMENT_DIRECTION AS DIRECTION
      ,'originOff' AS KEY_1
      ,fflm.ORIGIN_OFFICE AS VALUE_1
      ,'destOff' AS KEY_2
      ,DEST_OFFICE AS VALUE_2
      ,'weight' AS KEY_3
      ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
      ,'date' AS KEY_4
      ,LOADING_DATE AS VALUE_4
      ,'other' AS KEY_5
      ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,ffm.MANIFEST_NO AS VALUE_6
      ,'departureTime' AS KEY_7
      ,trip.DEPARTURE_TIME AS VALUE_7
      ,'arrivalTime' AS KEY_8
      ,trip.ARRIVAL_TIME AS VALUE_8
      ,'flightNo' AS KEY_9
      ,flight.FLIGHT_NUMBER as VALUE_9
      ,'trainNo' AS KEY_10
      ,train.TRAIN_NUMBER AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,fdv.REG_NUMBER AS VALUE_11
      ,'gatePassNo' AS KEY_12
      ,fflm.GATE_PASS_NUMBER as VALUE_12
      ,'regionVehicleNo' AS KEY_13
      ,fdv1.REG_NUMBER AS VALUE_13
      ,'receiveType' AS KEY_14
      ,fflm.RECEIVE_TYPE AS VALUE_14
FROM ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
LEFT JOIN ff_f_trip trip ON fftsb.TRIP = trip.TRIP_ID
LEFT JOIN ff_f_transport transport ON trip.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight flight ON transport.FLIGHT = flight.FLIGHT_ID
LEFT JOIN ff_d_train train ON transport.TRAIN = train.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.manifest_no IN (select rcvManifest.* from manifestsForMasterBagRecieve rcvManifest)
AND fflm.MOVEMENT_DIRECTION = 'R'

UNION

-- ROW FOR RECIEVE
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,fflm.CREATED_DATE AS PROCESS_DATE
      ,fflm.MOVEMENT_DIRECTION AS DIRECTION
      ,'originOff' AS KEY_1
      ,fflm.ORIGIN_OFFICE AS VALUE_1
      ,'destOff' AS KEY_2
      ,DEST_OFFICE AS VALUE_2
      ,'weight' AS KEY_3
      ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
      ,'date' AS KEY_4
      ,LOADING_DATE AS VALUE_4
      ,'other' AS KEY_5
      ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,ffm.MANIFEST_NO AS VALUE_6
      ,'departureTime' AS KEY_7
      ,trip.DEPARTURE_TIME AS VALUE_7
      ,'arrivalTime' AS KEY_8
      ,trip.ARRIVAL_TIME AS VALUE_8
      ,'flightNo' AS KEY_9
      ,flight.FLIGHT_NUMBER AS VALUE_9
      ,'trainNo' AS KEY_10
      ,train.TRAIN_NUMBER AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,fdv.REG_NUMBER AS VALUE_11
      ,'gatePassNo' AS KEY_12
      ,fflm.GATE_PASS_NUMBER AS VALUE_12
      ,'regionVehicleNo' AS KEY_13
      ,fdv1.REG_NUMBER AS VALUE_13
      ,'receiveType' AS KEY_14
      ,fflm.RECEIVE_TYPE AS VALUE_14
FROM   ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID 
LEFT JOIN ff_f_trip trip ON fftsb.TRIP = trip.TRIP_ID
LEFT JOIN ff_f_transport transport ON trip.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight flight ON transport.FLIGHT = flight.FLIGHT_ID
LEFT JOIN ff_d_train train ON transport.TRAIN = train.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.MANIFEST_ID IN (select pktBagMnfst.* from packetOrBagManifestId pktBagMnfst)
AND fflm.MOVEMENT_DIRECTION = 'R'

UNION

-- ROW FOR RECIEVE
SELECT fflm.LOAD_MOVEMENT_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,fflm.OPERATING_OFFICE AS OPERATING_OFFICE
      ,CONSG_NO AS CONSG_NO
      ,ffm.MANIFEST_NO AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,fflm.CREATED_DATE AS PROCESS_DATE
      ,fflm.MOVEMENT_DIRECTION AS DIRECTION
      ,'originOff'AS KEY_1
      ,fflm.ORIGIN_OFFICE AS VALUE_1
      ,'destOff' AS KEY_2
      ,DEST_OFFICE AS VALUE_2
      ,'weight' AS KEY_3
      ,FORMAT(DISPATCH_WEIGHT,3) AS VALUE_3
      ,'date' AS KEY_4
      ,LOADING_DATE AS VALUE_4
      ,'other' AS KEY_5
      ,CONCAT(TOKEN_NUMBER,'#',fdv1.REG_NUMBER,'#',trip.DEPARTURE_TIME,'#',trip.ARRIVAL_TIME) AS VALUE_5
      ,'manifestNo' AS KEY_6
      ,ffm.MANIFEST_NO AS VALUE_6
      ,'departureTime' AS KEY_7
      ,trip.DEPARTURE_TIME AS VALUE_7
      ,'arrivalTime' AS KEY_8
      ,trip.ARRIVAL_TIME AS VALUE_8
      ,'flightNo' AS KEY_9
      ,flight.FLIGHT_NUMBER AS VALUE_9
      ,'trainNo' AS KEY_10
      ,train.TRAIN_NUMBER AS VALUE_10
      ,'vehicleNo' AS KEY_11
      ,fdv.REG_NUMBER AS VALUE_11
      ,'gatePassNo' AS KEY_12
      ,fflm.GATE_PASS_NUMBER AS VALUE_12
      ,'regionVehicleNo' AS KEY_13
      ,fdv1.REG_NUMBER AS VALUE_13
      ,'receiveType' AS KEY_14
      ,fflm.RECEIVE_TYPE AS VALUE_14
FROM ff_f_load_connected fflc
LEFT JOIN ff_f_load_movement fflm ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
LEFT JOIN ff_f_manifest ffm ON fflc.MANIFEST = ffm.MANIFEST_ID
LEFT JOIN ff_d_process fdp ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
LEFT JOIN ff_f_trip_serviced_by fftsb ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
LEFT JOIN ff_f_trip trip ON fftsb.TRIP = trip.TRIP_ID
LEFT JOIN ff_f_transport transport ON trip.TRANSPORT = transport.TRANSPORT_ID
LEFT JOIN ff_d_flight flight ON transport.FLIGHT = flight.FLIGHT_ID
LEFT JOIN ff_d_train train ON transport.TRAIN = train.TRAIN_ID
LEFT JOIN ff_d_vehicle fdv ON transport.VEHICLE = fdv.VEHICLE_ID
LEFT JOIN ff_d_vehicle fdv1 ON fflm.VEHICLE = fdv1.VEHICLE_ID
WHERE ffm.manifest_no IN (select rcvBagMnfst.* from manifestsForBagRecieve rcvBagMnfst)
AND fflm.MOVEMENT_DIRECTION='R'

UNION

-- HELDUP
SELECT  heldup.HELDUP_ID AS PROCESS_ID
        ,'C' AS ARTIFACT_TYPE
        ,heldup.OFFICE AS OPERATING_OFFICE 
        ,TRANSACTION_NUMBER AS CONSG_NO
        ,NULL AS MANIFEST_NO
        ,process.PROCESS_CODE AS PROCESS_NO
--         ,NULL AS OPERATING_LEVEL
        ,process.PROCESS_ORDER AS PROCESS_ORDER 
        ,heldup.CREATED_DATE AS PROCESS_DATE
        ,NULL AS DIRECTION
        ,'originOff' AS KEY_1
        ,OFFICE AS VALUE_1
        ,'hubOff' AS KEY_2
        ,office.REPORTING_HUB AS VALUE_2
        ,'date' AS KEY_3
        ,CREATED_DATE AS VALUE_3
        ,'TRAN_NO' AS KEY_4
        ,TRANSACTION_NUMBER AS VALUE_4
        ,NULL AS 	KEY_5
        ,NULL AS VALUE_5
        ,NULL AS 	KEY_6
        ,NULL AS VALUE_6
        ,NULL AS 	KEY_7
        ,NULL AS VALUE_7
        ,NULL AS 	KEY_8
        ,NULL AS VALUE_8
	      ,NULL AS KEY_9
        ,NULL AS VALUE_9
        ,NULL AS KEY_10
        ,NULL AS VALUE_10
        ,NULL AS KEY_11
        ,NULL AS VALUE_11
        ,NULL AS KEY_12
        ,NULL AS VALUE_12
        ,NULL AS KEY_13
        ,NULL AS VALUE_13
        ,NULL AS KEY_14
        ,NULL AS VALUE_14
FROM ff_f_heldup heldup 
left join ff_d_office office ON heldup.OFFICE = office.OFFICE_ID
left join ff_d_process process on heldup.UPDATED_PROCESS_FROM = process.PROCESS_ID
WHERE	heldup.TRANSACTION_TYPE IN ('CONG')
AND heldup.TRANSACTION_NUMBER = CONSG_NO

UNION

-- ROW FOR DELIVERY
(SELECT ffd.DELIVERY_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,ffd.CREATED_OFFICE_ID AS OPERATING_OFFICE
      ,ffdd.CONSIGNMENT_NUMBER AS CONSG_NO
      ,NULL AS MANIFEST_NO
      ,fdp.PROCESS_CODE AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,fdp.PROCESS_ORDER AS PROCESS_ORDER
      ,DRS_DATE AS PROCESS_DATE
--       ,ffd.FS_OUT_TIME AS PROCESS_DATE
      ,NULL AS DIRECTION
      ,'originOff' AS KEY_1
      ,ffd.CREATED_OFFICE_ID AS VALUE_1
      ,'date' AS KEY_2
      ,convert(date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.FS_IN_TIME),'%d/%m/%Y %H:%i'), CHARACTER) AS VALUE_2
      ,'DRSNo' AS KEY_3
      ,ffd.DRS_NUMBER AS VALUE_3
      ,'status' AS KEY_4
      ,ffdd.DELIVERY_STATUS AS VALUE_4
      ,'reason' AS KEY_5
      ,coalesce(fdr.REASON_NAME,'') AS VALUE_5
      ,'ReceiverName' AS KEY_6
      ,IF(DELIVERY_TYPE = 'O', '( Office Seal & Sign )', ffdd.RECEIVER_NAME) AS VALUE_6      
      ,'DeliveryDate' AS KEY_7
      ,date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.DRS_DATE),'%d-%m-%Y') AS VALUE_7
      ,'DeliveryTime' AS KEY_8
      ,date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.DRS_DATE),'%H:%i:%s') AS VALUE_8 -- [%H/k - Hour (0-23), %h	- Hour (01-12)]
      ,'deliveryOutTime' AS KEY_9
      ,date_format(ffd.FS_OUT_TIME,'%d/%m/%Y %H:%i') AS VALUE_9
      ,NULL AS KEY_10
      ,NULL AS VALUE_10
      ,NULL AS KEY_11
      ,NULL AS VALUE_11
      ,NULL AS KEY_12
      ,NULL AS VALUE_12
      ,NULL AS KEY_13
      ,NULL AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_delivery ffd 
LEFT JOIN ff_f_delivery_dtls ffdd ON ffd.DELIVERY_ID = ffdd.DELIVERY_ID
LEFT JOIN ff_d_process fdp ON fdp.PROCESS_CODE = 'DLRS'
LEFT JOIN ff_d_reason fdr ON ffdd.REASON_ID = fdr.REASON_ID
WHERE ffdd.CONSIGNMENT_NUMBER = CONSG_NO
AND ffdd.RECORD_STATUS = 'A' 
ORDER BY ffd.DRS_DATE DESC LIMIT 1)

UNION

-- ROW FOR STOP DELIVERY 
SELECT ffc.CONSG_ID AS PROCESS_ID
      ,'C' AS ARTIFACT_TYPE
      ,NULL AS OPERATING_OFFICE
      ,ffc.CONSG_NO AS CONSG_NO
      ,NULL AS MANIFEST_NO
      ,'SDLV' AS PROCESS_NO
--       ,NULL AS OPERATING_LEVEL
      ,NULL AS PROCESS_ORDER
      ,ffc.STOP_DELV_DATE AS PROCESS_DATE
      ,NULL AS DIRECTION
      ,'stopDlvReqOff' AS KEY_1
      ,convert(ffc.STOP_DELIVERY_REQ_OFFICE, CHARACTER) AS VALUE_1
      ,NULL AS KEY_2
      ,NULL AS VALUE_2
      ,NULL AS KEY_3
      ,NULL AS VALUE_3
      ,NULL AS KEY_4
      ,NULL AS VALUE_4
      ,NULL AS KEY_5
      ,NULL AS VALUE_5
      ,NULL AS KEY_6
      ,NULL AS VALUE_6
      ,NULL AS KEY_7
      ,NULL AS VALUE_7
      ,NULL AS KEY_8
      ,NULL AS VALUE_8
      ,NULL AS KEY_9
      ,NULL AS VALUE_9
      ,NULL AS KEY_10
      ,NULL AS VALUE_10
      ,NULL AS KEY_11
      ,NULL AS VALUE_11
      ,NULL AS KEY_12
      ,NULL AS VALUE_12
      ,NULL AS KEY_13
      ,NULL AS VALUE_13
      ,NULL AS KEY_14
      ,NULL AS VALUE_14
FROM ff_f_consignment ffc 
WHERE ffc.CONSG_NO = CONSG_NO
AND ffc.CONSG_STATUS = 'X') cn_track, 
(SELECT @rownum:=0) r
ORDER BY cn_track.PROCESS_DATE,cn_track.PROCESS_ORDER;

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToConsignment;

DROP TEMPORARY TABLE IF EXISTS packetOrBagManifest;

DROP TEMPORARY TABLE IF EXISTS packetOrBagManifestId;

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;

DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;

DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagDispatch;

DROP TEMPORARY TABLE IF EXISTS manifestsForBagDispatch;

DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagRecieve;

DROP TEMPORARY TABLE IF EXISTS manifestsForBagRecieve;

END;
