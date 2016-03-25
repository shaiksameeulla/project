DROP PROCEDURE IF EXISTS sp_track_consignment;

CREATE PROCEDURE `sp_track_consignment`(IN CONSG_NO VARCHAR(255))
   BEGIN
      -- Procedure name - sp_track_consignment
      -- Purpose - Track a consignment details
      -- Created by and Date - UPASANA
      -- Version - 3.5
      -- Modified by - SHAHNAZ
      -- Modified Date - 3 Sep 2015

      -- V2.16 - Same Manifest occurs multipletimes in central DB
      -- 1. Tracking SP modified with manifest_id instead of manifest_no
      -- 2. Delivery dtls are order by TRANS_MODIFIED_DATE_TIME. DRS_DATE is DRS Preparation date so not getting exact status.
      -- 3. Delivered consignment Date&Time changed to 24 Hrs Format.
      -- 4. Delivery details, removed LIMIT to show all the delivery attempts of the consignment
      -- 5. Added manifest_direction in Dispatch/Receive
      -- 6. artf4010908 : Issue in Tracking details of RTO/RTH
      -- 7. artf4054191 : Delivery details not reflecting against Parent consignments(V3.3)
      -- 8. Illegal mix of collations (latin1_swedish_ci,IMPLICIT) and (utf8_general_ci,COERCIBLE) for operation 'if'
      -- 9. [Enhancement]Delivered CN Path - Delivered to {ReceiverName} on {DeliveryDate} at {DeliveryTime} runsheet updated on {updatedDate}
      -- 10. Direct packet complete path missing..
      -- 3.5 : Trip defined with ROUTE and TRANSPORT

      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToConsignment;

      DROP TEMPORARY TABLE IF EXISTS packetOrBagManifest;

      DROP TEMPORARY TABLE IF EXISTS packetOrBagManifestId;

      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToPacketOrBag;

      DROP TEMPORARY TABLE IF EXISTS manifestsMappedToMasterBag;

      DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagDispatch;

      DROP TEMPORARY TABLE IF EXISTS manifestsForBagDispatch;

      DROP TEMPORARY TABLE IF EXISTS manifestsForMasterBagRecieve;

      DROP TEMPORARY TABLE IF EXISTS manifestsForBagRecieve;

CREATE TEMPORARY TABLE manifestsMappedToConsignment ENGINE=memory
SELECT DISTINCT ffc.CONSG_NO,
       ffc.CONSG_STATUS,
       ffc.CUSTOMER,
       ffm.MANIFEST_NO,
       ffm.MANIFEST_ID
  FROM ff_f_consignment ffc
  LEFT JOIN ff_f_consignment_manifested ffcm ON ffcm.CONSIGNMENT_ID = ffc.CONSG_ID
  LEFT JOIN ff_f_manifest ffm ON ffcm.MANIFEST_ID = ffm.MANIFEST_ID
  WHERE ffc.CONSG_NO = CONSG_NO;

CREATE TEMPORARY TABLE packetOrBagManifest ENGINE=memory
SELECT cnManifested.MANIFEST_ID, cnManifested.MANIFEST_NO FROM manifestsMappedToConsignment cnManifested;

CREATE TEMPORARY TABLE packetOrBagManifestId ENGINE=memory
SELECT cnManifested.* FROM packetOrBagManifest cnManifested;

CREATE TEMPORARY TABLE manifestsMappedToPacketOrBag ENGINE=memory
SELECT parentm.MANIFEST_ID FROM ff_f_manifest childm
JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
-- where childm.MANIFEST_ID IN (select cnManifested.MANIFEST_ID from manifestsMappedToConsignment cnManifested)
JOIN manifestsMappedToConsignment cnManifested ON
    CASE
      WHEN childm.MANIFEST_OPEN_TYPE = 'O'
        THEN childm.MANIFEST_NO = cnManifested.MANIFEST_NO
      ELSE
        childm.MANIFEST_ID = cnManifested.MANIFEST_ID
    END
WHERE parentm.manifest_no IS NOT NULL;

CREATE TEMPORARY TABLE manifestsMappedToMasterBag ENGINE=memory
SELECT parentm.MANIFEST_ID FROM ff_f_manifest childm
LEFT JOIN ff_f_manifest parentm ON childm.MANIFEST_EMBEDDED_IN = parentm.manifest_id
WHERE childm.MANIFEST_ID IN (SELECT bagMnfst.* FROM manifestsMappedToPacketOrBag bagMnfst)
AND parentm.manifest_no IS NOT NULL;

CREATE TEMPORARY TABLE manifestsForMasterBagDispatch ENGINE=memory
SELECT ffm_mbag.MANIFEST_ID FROM ff_f_manifest ffm_mbag
LEFT JOIN ff_f_manifest ffm_bag ON ffm_bag.MANIFEST_EMBEDDED_IN = ffm_mbag.MANIFEST_ID
LEFT JOIN ff_f_manifest ffm_pkt ON ffm_pkt.MANIFEST_EMBEDDED_IN = ffm_bag.MANIFEST_ID
-- WHERE ffm_pkt.MANIFEST_ID IN (select cnManifested.MANIFEST_ID from manifestsMappedToConsignment cnManifested);
JOIN manifestsMappedToConsignment cnManifested ON
    CASE
      WHEN ffm_pkt.MANIFEST_OPEN_TYPE = 'O'
        THEN ffm_pkt.MANIFEST_NO = cnManifested.MANIFEST_NO
      ELSE
        ffm_pkt.MANIFEST_ID = cnManifested.MANIFEST_ID
    END;

CREATE TEMPORARY TABLE manifestsForBagDispatch ENGINE=memory
SELECT ffm1.MANIFEST_ID FROM ff_f_manifest ffm1
LEFT JOIN ff_f_manifest ffm2 ON ffm2.MANIFEST_EMBEDDED_IN = ffm1.manifest_id
-- where ffm2.MANIFEST_ID in (select cnManifested.MANIFEST_ID from manifestsMappedToConsignment cnManifested);
JOIN manifestsMappedToConsignment cnManifested ON
    CASE
      WHEN ffm2.MANIFEST_OPEN_TYPE = 'O'
        THEN ffm2.MANIFEST_NO = cnManifested.MANIFEST_NO
      ELSE
          ffm2.MANIFEST_ID = cnManifested.MANIFEST_ID
    END;

CREATE TEMPORARY TABLE manifestsForMasterBagRecieve ENGINE=memory
SELECT * FROM manifestsForMasterBagDispatch;

CREATE TEMPORARY TABLE manifestsForBagRecieve ENGINE=memory
SELECT * FROM manifestsForBagDispatch;

      SELECT @rownum := @rownum + 1 AS ROW_ID,
             cn_track.ARTIFACT_TYPE,
             cn_track.CONSG_NO,
             cn_track.MANIFEST_NO,
             cn_track.PROCESS_NO,
             cn_track.PROCESS_ORDER,
             cn_track.PROCESS_DATE,
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
        FROM (-- ROW fOR PICKUP
              SELECT 'C' AS ARTIFACT_TYPE,
                     ffb.consg_number AS CONSG_NO,
                     NULL AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffb.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     ffb.BOOKING_OFF AS VALUE_1,
                     'orgCity' AS KEY_2,
                     fdc.CITY_ID AS VALUE_2,
                     'date' AS KEY_3,
                     ffb.CREATED_DATE AS VALUE_3,
                     'custName' AS KEY_4,
                     ffb.CUSTOMER AS VALUE_4,
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
                FROM ff_f_booking ffb
                     LEFT JOIN ff_d_office fdo
                        ON fdo.office_id = ffb.BOOKING_OFF
                     LEFT JOIN ff_d_city fdc
                        ON fdc.CITY_ID = fdo.CITY_ID
                     LEFT JOIN ff_d_process fdp
                        ON fdp.PROCESS_CODE = 'UPPU'
               WHERE     ffb.PICKUP_RUNSHEET_NO <> ''
                     AND ffb.PICKUP_RUNSHEET_NO IS NOT NULL
                     AND ffb.consg_number = CONSG_NO
              UNION
              --
              -- Sequel to get booked consignments records
              -- Replaced ff_f_booking with ff_f_consignment
              -- In case of consignment created from OPKT/OBPC or updated with RTOH , OPKT/OBPC/RTOH records are coming twice.
              -- So added PROCESS_NO as 'BOOK'
              -- ROW FOR BOOK

              SELECT 'C' AS ARTIFACT_TYPE,
                     ffc.CONSG_NO AS CONSG_NO,
                     NULL AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffc.CREATED_DATE AS PROCESS_DATE--   ,IFNULL(ffb.BOOKING_DATE,ffc.CREATED_DATE) AS PROCESS_DATE
                     ,
                     'originOff' AS KEY_1,
                     convert(ffc.ORG_OFF, CHARACTER) AS VALUE_1,
                     'orgCity' AS KEY_2,
                     fdo.CITY_ID AS VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ffc.FINAL_WEIGHT, 3), CHARACTER)
                        AS VALUE_3--   ,convert(FORMAT(IFNULL(ffb.FINAL_WEIGHT,ffc.FINAL_WEIGHT),3), CHARACTER) AS VALUE_3
                     ,
                     'noOfPieces' AS KEY_4,
                     convert(ffc.NO_OF_PCS, CHARACTER) AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(coalesce(ffc.REF_NO, ''),
                            '#',
                            ffc.ORG_OFF,
                            '#',
                            ffc.DEST_PINCODE)
                        AS VALUE_5,
                     'date' AS KEY_6,
                     convert(
                        date_format(ffc.CREATED_DATE, '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_6,
                     'destCity' AS KEY_7,
                     fdpin.city_id AS VALUE_7,
                     'custName' AS KEY_8,
                     ffc.CUSTOMER AS VALUE_8,
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
                FROM ff_f_consignment ffc
                     --  LEFT JOIN ff_f_booking ffb ON ffb.CONSG_NUMBER = ffc.CONSG_NO
                     LEFT JOIN ff_d_pincode fdpin
                        ON fdpin.pincode_id = ffc.DEST_PINCODE
                     LEFT JOIN ff_d_office fdo
                        ON fdo.OFFICE_ID = ffc.ORG_OFF
                     LEFT JOIN ff_d_process fdp
                        ON fdp.PROCESS_CODE = 'BOOK'
               WHERE ffc.CONSG_NO = CONSG_NO AND ffc.IS_EXCESS_CONSG = 'N'
              UNION
              -- PACKET MANIFEST
              SELECT 'M' AS ARTIFACT_TYPE,
                     tmp_cnManifested.CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     IF(
                        (STRCMP(ffm.MANIFEST_PROCESS_CODE, 'DSPT') = 0),
                        IF(
                           (STRCMP(SUBSTRING(ffm.MANIFEST_NO, 4, 1), 'B') = 0),
                           IF((STRCMP(fdct.CONSIGNMENT_CODE, 'DOX') = 0),
                              'OBDX',
                              'OBPC'),
                           'OMBG'),
                        ffm.MANIFEST_PROCESS_CODE)
                        AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
                     'destOff' AS KEY_2,
                     IF(STRCMP(ffm.MANIFEST_DIRECTION, 'I'),
                        convert(ffm.DESTINATION_OFFICE, CHARACTER),
                        convert(ffm.OPERATING_OFFICE, CHARACTER))
                        AS VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ffm.MANIFEST_WEIGHT, 3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ffm.MANIFEST_DATE, '%m-%d-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'orgCity' AS KEY_7,
                     fdo.city_id AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
                     'consgNo' AS KEY_9,
                     tmp_cnManifested.CONSG_NO AS VALUE_9,
                     'custName' AS KEY_10,
                     fdv.BUSINESS_NAME AS VALUE_10,
                     'cnStatus' AS KEY_11,
                     tmp_cnManifested.CONSG_STATUS AS VALUE_11,
                     'manifestType' AS KEY_12,
                     ffm.MANIFEST_TYPE AS VALUE_12,
                     NULL AS KEY_13,
                     NULL AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_manifest ffm
                     LEFT JOIN ff_d_office fdo
                        ON fdo.office_id = ffm.ORIGIN_OFFICE
                     LEFT JOIN ff_d_consignment_type fdct
                        ON fdct.consignment_type_id =
                              ffm.MANIFEST_LOAD_CONTENT
                     LEFT JOIN ff_d_process fdp
                        ON fdp.PROCESS_CODE = ffm.MANIFEST_PROCESS_CODE
                     LEFT JOIN ff_d_vendor fdv
                        ON fdv.VENDOR_ID = ffm.VENDOR_ID
                     --     INNER JOIN manifestsMappedToConsignment tmp_cnManifested
                     --     ON ffm.MANIFEST_ID = tmp_cnManifested.MANIFEST_ID
                     JOIN manifestsMappedToConsignment tmp_cnManifested
                        ON CASE
                              WHEN ffm.MANIFEST_OPEN_TYPE = 'O'
                              THEN
                                 ffm.MANIFEST_NO =
                                    tmp_cnManifested.MANIFEST_NO
                              ELSE
                                 ffm.MANIFEST_ID =
                                    tmp_cnManifested.MANIFEST_ID
                           END
              UNION
              -- BAG MANIFEST
              SELECT 'M' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     IF(
                        (STRCMP(ffm.MANIFEST_PROCESS_CODE, 'DSPT') = 0),
                        IF(
                           (STRCMP(SUBSTRING(ffm.MANIFEST_NO, 4, 1), 'B') = 0),
                           IF((STRCMP(fdct.CONSIGNMENT_CODE, 'DOX') = 0),
                              'OBDX',
                              'OBPC'),
                           'OMBG'),
                        ffm.MANIFEST_PROCESS_CODE)
                        AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
                     'destOff' AS KEY_2,
                     IF(STRCMP(ffm.MANIFEST_DIRECTION, 'I'),
                        convert(ffm.DESTINATION_OFFICE, CHARACTER),
                        convert(ffm.OPERATING_OFFICE, CHARACTER))
                        AS VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ffm.MANIFEST_WEIGHT, 3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ffm.MANIFEST_DATE, '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'orgCity' AS KEY_7,
                     fdo.city_id AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
                     'consgNo' AS KEY_9,
                     CONSG_NO AS VALUE_9,
                     'custName' AS KEY_10,
                     fdv.BUSINESS_NAME AS VALUE_10,
                     NULL AS KEY_11,
                     NULL AS VALUE_11,
                     'manifestType' AS KEY_12,
                     ffm.MANIFEST_TYPE AS VALUE_12,
                     NULL AS KEY_13,
                     NULL AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_manifest ffm
                     INNER JOIN ff_d_process fdp
                        ON ffm.MANIFEST_PROCESS_CODE = fdp.PROCESS_CODE
                     LEFT JOIN ff_d_consignment_type fdct
                        ON fdct.consignment_type_id =
                              ffm.MANIFEST_LOAD_CONTENT
                     LEFT JOIN ff_d_vendor fdv
                        ON fdv.VENDOR_ID = ffm.VENDOR_ID
                     LEFT JOIN ff_d_office fdo
                        ON fdo.office_id = ffm.ORIGIN_OFFICE
               WHERE ffm.MANIFEST_ID IN
                        (SELECT bagMnfst.*
                           FROM manifestsMappedToPacketOrBag bagMnfst)
              UNION
              -- MASTER BAG
              SELECT 'M' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     IF(
                        (STRCMP(ffm.MANIFEST_PROCESS_CODE, 'DSPT') = 0),
                        IF(
                           (STRCMP(SUBSTRING(ffm.MANIFEST_NO, 4, 1), 'B') = 0),
                           IF((STRCMP(fdct.CONSIGNMENT_CODE, 'DOX') = 0),
                              'OBDX',
                              'OBPC'),
                           'OMBG'),
                        ffm.MANIFEST_PROCESS_CODE)
                        AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     convert(ffm.ORIGIN_OFFICE, CHARACTER) AS VALUE_1,
                     'destOff' AS KEY_2,
                     IF(STRCMP(ffm.MANIFEST_DIRECTION, 'I'),
                        convert(ffm.DESTINATION_OFFICE, CHARACTER),
                        convert(ffm.OPERATING_OFFICE, CHARACTER))
                        AS VALUE_2,
                     'weight' AS KEY_3,
                     convert(FORMAT(ffm.MANIFEST_WEIGHT, 3), CHARACTER)
                        AS VALUE_3,
                     'date' AS KEY_4,
                     convert(
                        date_format(ffm.MANIFEST_DATE, '%d-%m-%Y %h:%i:%s'),
                        CHARACTER)
                        AS VALUE_4,
                     'direction' AS KEY_5,
                     convert(ffm.MANIFEST_DIRECTION, CHARACTER) AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     convert(ffm.MANIFEST_NO, CHARACTER) AS VALUE_6,
                     'orgCity' AS KEY_7,
                     fdo.city_id AS VALUE_7,
                     'destCity' AS KEY_8,
                     convert(ffm.DESTINATION_CITY, CHARACTER) AS VALUE_8,
                     'consgNo' AS KEY_9,
                     CONSG_NO AS VALUE_9,
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
                     INNER JOIN ff_d_process fdp
                        ON ffm.MANIFEST_PROCESS_CODE = fdp.PROCESS_CODE
                     LEFT JOIN ff_d_consignment_type fdct
                        ON fdct.consignment_type_id =
                              ffm.MANIFEST_LOAD_CONTENT
                     LEFT JOIN ff_d_office fdo
                        ON fdo.office_id = ffm.ORIGIN_OFFICE
               WHERE ffm.MANIFEST_ID IN
                        (SELECT masterBagMnfst.*
                           FROM manifestsMappedToMasterBag masterBagMnfst)
              UNION
              -- DISPATCH
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            fft.DEPARTURE_TIME,
                            '#',
                            fft.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'gatePassNo' AS KEY_7,
                     fflm.GATE_PASS_NUMBER AS VALUE_7,
                     'transportModeAndNo' AS KEY_8,
                     CASE
                        WHEN fflm.VEHICLE_TYPE = 'M'
                        THEN
                           CONCAT('Road', '#', fdv1.REG_NUMBER)
                        WHEN fflm.VEHICLE_TYPE = 'O'
                        THEN
                           CONCAT('Road', '#', fflm.VEHICLE_REG_NUMBER)
                        WHEN fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'O'
                        THEN
                           CONCAT(ffdtm.TRANSPORT_MODE_DESCRIPTION,
                                  '#',
                                  fflm.ROUTE_SERVICED_TRANSPORT_NUMBER)
                     END
                        AS VALUE_8,
                     'flightNo' AS KEY_9,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(fdf.FLIGHT_NUMBER, ''),
                               '-',
                               IFNULL(fdf.AIRLINE_NAME, '')),
                        NULL)
                        AS VALUE_9,
                     'trainNo' AS KEY_10,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(fdtrn.TRAIN_NUMBER, ''),
                               '-',
                               IFNULL(fdtrn.TRAIN_NAME, '')),
                        NULL)
                        AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        fdv.REG_NUMBER,
                        NULL)
                        AS VALUE_11,
                     'departureTime' AS KEY_12,
                     IFNULL(trip1.DEPARTURE_TIME, fft.DEPARTURE_TIME)
                        AS VALUE_12,
                     'arrivalTime' AS KEY_13,
                     IFNULL(trip1.ARRIVAL_TIME, fft.ARRIVAL_TIME) AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_d_office lmOrgOfc  ON fflm.ORIGIN_OFFICE = lmOrgOfc.OFFICE_ID
                     LEFT JOIN ff_d_office lmDestOfc  ON fflm.DEST_OFFICE = lmDestOfc.OFFICE_ID
                     LEFT JOIN ff_d_city lmOrgCity  ON lmOrgOfc.CITY_ID = lmOrgCity.CITY_ID
                     LEFT JOIN ff_d_city lmDestCity  ON lmDestOfc.CITY_ID = lmDestCity.CITY_ID
                     LEFT JOIN ff_d_route fflmRoute ON (fflmRoute.ORIGIN_CITY = lmOrgCity.CITY_ID and fflmRoute.DEST_CITY = lmDestCity.CITY_ID)   
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip fft
                        ON (fftsb.TRIP = fft.TRIP_ID and fflmRoute.ROUTE_ID = fft.ROUTE)
                     LEFT JOIN ff_f_transport transport
                        ON fft.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_transport_mode ffdtm
                        ON ffdtm.TRANSPORT_MODE_ID = fflm.TRANSPORT_MODE
                     LEFT JOIN ff_d_flight fdf
                        ON transport.FLIGHT = fdf.FLIGHT_ID
                     LEFT JOIN ff_d_train fdtrn
                        ON transport.TRAIN = fdtrn.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_transport transport1
                        ON transport1.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_trip trip1
                        ON (trip1.TRANSPORT = transport1.TRANSPORT_ID and fflmRoute.ROUTE_ID = trip1.ROUTE)
               WHERE ffm.MANIFEST_ID IN
                        (SELECT dispManifest.*
                           FROM manifestsForMasterBagDispatch dispManifest)
                     AND ffm.MANIFEST_DIRECTION = 'O'
                     AND fflm.MOVEMENT_DIRECTION = 'D'
              UNION
              -- ROW FOR DISPATCH
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            fftrp.DEPARTURE_TIME,
                            '#',
                            fftrp.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'gatePassNo' AS KEY_7,
                     fflm.GATE_PASS_NUMBER AS VALUE_7,
                     'transportModeAndNo' AS KEY_8,
                     CASE
                        WHEN fflm.VEHICLE_TYPE = 'M'
                        THEN
                           CONCAT('Road', '#', fdv1.REG_NUMBER)
                        WHEN fflm.VEHICLE_TYPE = 'O'
                        THEN
                           CONCAT('Road', '#', fflm.VEHICLE_REG_NUMBER)
                        WHEN fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'O'
                        THEN
                           CONCAT(ffdtm.TRANSPORT_MODE_DESCRIPTION,
                                  '#',
                                  fflm.ROUTE_SERVICED_TRANSPORT_NUMBER)
                     END
                        AS VALUE_8,
                     'flightNo' AS KEY_9,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(fdf.FLIGHT_NUMBER, ''),
                               '-',
                               IFNULL(fdf.AIRLINE_NAME, '')),
                        NULL)
                        AS VALUE_9,
                     'trainNo' AS KEY_10,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(train.TRAIN_NUMBER, ''),
                               '-',
                               IFNULL(train.TRAIN_NAME, '')),
                        NULL)
                        AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        fdv.REG_NUMBER,
                        NULL)
                        AS VALUE_11,
                     'departureTime' AS KEY_12,
                     IFNULL(trip1.DEPARTURE_TIME, fftrp.DEPARTURE_TIME)
                        AS VALUE_12,
                     'arrivalTime' AS KEY_13,
                     IFNULL(trip1.ARRIVAL_TIME, fftrp.ARRIVAL_TIME)
                        AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_d_office lmOrgOfc  ON fflm.ORIGIN_OFFICE = lmOrgOfc.OFFICE_ID
                     LEFT JOIN ff_d_office lmDestOfc  ON fflm.DEST_OFFICE = lmDestOfc.OFFICE_ID
                     LEFT JOIN ff_d_city lmOrgCity  ON lmOrgOfc.CITY_ID = lmOrgCity.CITY_ID
                     LEFT JOIN ff_d_city lmDestCity  ON lmDestOfc.CITY_ID = lmDestCity.CITY_ID
                     LEFT JOIN ff_d_route fflmRoute ON (fflmRoute.ORIGIN_CITY = lmOrgCity.CITY_ID and fflmRoute.DEST_CITY = lmDestCity.CITY_ID)   
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip fftrp
                        ON (fftsb.TRIP = fftrp.TRIP_ID and fflmRoute.ROUTE_ID = fftrp.ROUTE)
                     LEFT JOIN ff_f_transport transport
                        ON fftrp.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_transport_mode ffdtm
                        ON ffdtm.TRANSPORT_MODE_ID = fflm.TRANSPORT_MODE
                     LEFT JOIN ff_d_flight fdf
                        ON transport.FLIGHT = fdf.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_transport transport1
                        ON transport1.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_trip trip1
                        ON (trip1.TRANSPORT = transport1.TRANSPORT_ID and fflmRoute.ROUTE_ID = trip1.ROUTE)
                     -- WHERE ffm.MANIFEST_ID IN (select pktOrBagMnfst.* from packetOrBagManifest pktOrBagMnfst)
                     JOIN packetOrBagManifest pktOrBagMnfst
                        ON CASE
                              WHEN ffm.MANIFEST_OPEN_TYPE = 'O'
                              THEN
                                 ffm.MANIFEST_NO = pktOrBagMnfst.MANIFEST_NO
                              ELSE
                                 ffm.MANIFEST_ID = pktOrBagMnfst.MANIFEST_ID
                           END
               WHERE ffm.MANIFEST_DIRECTION = 'O'
                     AND fflm.MOVEMENT_DIRECTION = 'D'
              UNION
              -- ROW FOR DISPATCH
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            trip.DEPARTURE_TIME,
                            '#',
                            trip.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'gatePassNo' AS KEY_7,
                     fflm.GATE_PASS_NUMBER AS VALUE_7,
                     'transportModeAndNo' AS KEY_8,
                     CASE
                        WHEN fflm.VEHICLE_TYPE = 'M'
                        THEN
                           CONCAT('Road', '#', fdv1.REG_NUMBER)
                        WHEN fflm.VEHICLE_TYPE = 'O'
                        THEN
                           CONCAT('Road', '#', fflm.VEHICLE_REG_NUMBER)
                        WHEN fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'O'
                        THEN
                           CONCAT(ffdtm.TRANSPORT_MODE_DESCRIPTION,
                                  '#',
                                  fflm.ROUTE_SERVICED_TRANSPORT_NUMBER)
                     END
                        AS VALUE_8,
                     'flightNo' AS KEY_9,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(fdf.FLIGHT_NUMBER, ''),
                               '-',
                               IFNULL(fdf.AIRLINE_NAME, '')),
                        NULL)
                        AS VALUE_9,
                     'trainNo' AS KEY_10,
                     IF(
                        fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        CONCAT(IFNULL(train.TRAIN_NUMBER, ''),
                               '-',
                               IFNULL(train.TRAIN_NAME, '')),
                        NULL)
                        AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     IF(fflm.ROUTE_SERVICED_TRANSPORT_TYPE = 'M',
                        fdv.REG_NUMBER,
                        NULL)
                        AS VALUE_11,
                     'departureTime' AS KEY_12,
                     IFNULL(trip1.DEPARTURE_TIME, trip.DEPARTURE_TIME)
                        AS VALUE_12,
                     'arrivalTime' AS KEY_13,
                     IFNULL(trip1.ARRIVAL_TIME, trip.ARRIVAL_TIME)
                        AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_d_office lmOrgOfc  ON fflm.ORIGIN_OFFICE = lmOrgOfc.OFFICE_ID
                     LEFT JOIN ff_d_office lmDestOfc  ON fflm.DEST_OFFICE = lmDestOfc.OFFICE_ID
                     LEFT JOIN ff_d_city lmOrgCity  ON lmOrgOfc.CITY_ID = lmOrgCity.CITY_ID
                     LEFT JOIN ff_d_city lmDestCity  ON lmDestOfc.CITY_ID = lmDestCity.CITY_ID
                     LEFT JOIN ff_d_route fflmRoute ON (fflmRoute.ORIGIN_CITY = lmOrgCity.CITY_ID and fflmRoute.DEST_CITY = lmDestCity.CITY_ID)
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON (fftsb.TRIP = trip.TRIP_ID and fflmRoute.ROUTE_ID = trip.ROUTE)
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_transport_mode ffdtm
                        ON ffdtm.TRANSPORT_MODE_ID = fflm.TRANSPORT_MODE
                     LEFT JOIN ff_d_flight fdf
                        ON transport.FLIGHT = fdf.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_transport transport1
                        ON transport1.VEHICLE = fdv1.VEHICLE_ID
                     LEFT JOIN ff_f_trip trip1
                        ON (trip1.TRANSPORT = transport1.TRANSPORT_ID and fflmRoute.ROUTE_ID = trip1.ROUTE)
               WHERE ffm.MANIFEST_ID IN
                        (SELECT dspBagMnfst.*
                           FROM manifestsForBagDispatch dspBagMnfst)
                     AND ffm.MANIFEST_DIRECTION = 'O'
                     AND fflm.MOVEMENT_DIRECTION = 'D'
              UNION
              -- ROW FOR RECIEVE
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            trip.DEPARTURE_TIME,
                            '#',
                            trip.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'departureTime' AS KEY_7,
                     trip.DEPARTURE_TIME AS VALUE_7,
                     'arrivalTime' AS KEY_8,
                     trip.ARRIVAL_TIME AS VALUE_8,
                     'flightNo' AS KEY_9,
                     flight.FLIGHT_NUMBER AS VALUE_9,
                     'trainNo' AS KEY_10,
                     train.TRAIN_NUMBER AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     fdv.REG_NUMBER AS VALUE_11,
                     'gatePassNo' AS KEY_12,
                     fflm.GATE_PASS_NUMBER AS VALUE_12,
                     'regionVehicleNo' AS KEY_13,
                     fdv1.REG_NUMBER AS VALUE_13,
                     'receiveType' AS KEY_14,
                     fflm.RECEIVE_TYPE AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON fftsb.TRIP = trip.TRIP_ID
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_flight flight
                        ON transport.FLIGHT = flight.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
               WHERE ffm.MANIFEST_ID IN
                        (SELECT rcvManifest.*
                           FROM manifestsForMasterBagRecieve rcvManifest)
                     AND ffm.MANIFEST_DIRECTION = 'I'
                     AND fflm.MOVEMENT_DIRECTION = 'R'
              UNION
              -- ROW FOR RECIEVE
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            trip.DEPARTURE_TIME,
                            '#',
                            trip.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'departureTime' AS KEY_7,
                     trip.DEPARTURE_TIME AS VALUE_7,
                     'arrivalTime' AS KEY_8,
                     trip.ARRIVAL_TIME AS VALUE_8,
                     'flightNo' AS KEY_9,
                     flight.FLIGHT_NUMBER AS VALUE_9,
                     'trainNo' AS KEY_10,
                     train.TRAIN_NUMBER AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     fdv.REG_NUMBER AS VALUE_11,
                     'gatePassNo' AS KEY_12,
                     fflm.GATE_PASS_NUMBER AS VALUE_12,
                     'regionVehicleNo' AS KEY_13,
                     fdv1.REG_NUMBER AS VALUE_13,
                     'receiveType' AS KEY_14,
                     fflm.RECEIVE_TYPE AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON fftsb.TRIP = trip.TRIP_ID
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_flight flight
                        ON transport.FLIGHT = flight.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
                     -- WHERE ffm.MANIFEST_ID IN (select pktBagMnfst.* from packetOrBagManifestId pktBagMnfst)
                     JOIN packetOrBagManifestId pktBagMnfst
                        ON CASE
                              WHEN ffm.MANIFEST_OPEN_TYPE = 'O'
                              THEN
                                 ffm.MANIFEST_NO = pktBagMnfst.MANIFEST_NO
                              ELSE
                                 ffm.MANIFEST_ID = pktBagMnfst.MANIFEST_ID
                           END
                           AND ffm.MANIFEST_DIRECTION = 'I'
                           AND fflm.MOVEMENT_DIRECTION = 'R'
              UNION
              -- ROW FOR RECIEVE
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     ffm.MANIFEST_NO AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     fflm.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     fflm.ORIGIN_OFFICE AS VALUE_1,
                     'destOff' AS KEY_2,
                     DEST_OFFICE AS VALUE_2,
                     'weight' AS KEY_3,
                     FORMAT(DISPATCH_WEIGHT, 3) AS VALUE_3,
                     'date' AS KEY_4,
                     LOADING_DATE AS VALUE_4,
                     'other' AS KEY_5,
                     CONCAT(TOKEN_NUMBER,
                            '#',
                            fdv1.REG_NUMBER,
                            '#',
                            trip.DEPARTURE_TIME,
                            '#',
                            trip.ARRIVAL_TIME)
                        AS VALUE_5,
                     'manifestNo' AS KEY_6,
                     ffm.MANIFEST_NO AS VALUE_6,
                     'departureTime' AS KEY_7,
                     trip.DEPARTURE_TIME AS VALUE_7,
                     'arrivalTime' AS KEY_8,
                     trip.ARRIVAL_TIME AS VALUE_8,
                     'flightNo' AS KEY_9,
                     flight.FLIGHT_NUMBER AS VALUE_9,
                     'trainNo' AS KEY_10,
                     train.TRAIN_NUMBER AS VALUE_10,
                     'vehicleNo' AS KEY_11,
                     fdv.REG_NUMBER AS VALUE_11,
                     'gatePassNo' AS KEY_12,
                     fflm.GATE_PASS_NUMBER AS VALUE_12,
                     'regionVehicleNo' AS KEY_13,
                     fdv1.REG_NUMBER AS VALUE_13,
                     'receiveType' AS KEY_14,
                     fflm.RECEIVE_TYPE AS VALUE_14
                FROM ff_f_load_connected fflc
                     LEFT JOIN ff_f_load_movement fflm
                        ON fflc.LOAD_MOVEMENT = fflm.LOAD_MOVEMENT_ID
                     LEFT JOIN ff_f_manifest ffm
                        ON fflc.MANIFEST = ffm.MANIFEST_ID
                     LEFT JOIN ff_d_process fdp
                        ON fflm.UPDATED_PROCESS_FROM = fdp.PROCESS_ID
                     LEFT JOIN ff_f_trip_serviced_by fftsb
                        ON fflm.TRIP_SERVICED_BY = fftsb.TRIP_SERVICED_BY_ID
                     LEFT JOIN ff_f_trip trip
                        ON fftsb.TRIP = trip.TRIP_ID
                     LEFT JOIN ff_f_transport transport
                        ON trip.TRANSPORT = transport.TRANSPORT_ID
                     LEFT JOIN ff_d_flight flight
                        ON transport.FLIGHT = flight.FLIGHT_ID
                     LEFT JOIN ff_d_train train
                        ON transport.TRAIN = train.TRAIN_ID
                     LEFT JOIN ff_d_vehicle fdv
                        ON transport.VEHICLE = fdv.VEHICLE_ID
                     LEFT JOIN ff_d_vehicle fdv1
                        ON fflm.VEHICLE = fdv1.VEHICLE_ID
               WHERE ffm.manifest_id IN
                        (SELECT rcvBagMnfst.*
                           FROM manifestsForBagRecieve rcvBagMnfst)
                     AND ffm.MANIFEST_DIRECTION = 'I'
                     AND fflm.MOVEMENT_DIRECTION = 'R'
              UNION
              -- HELDUP
              SELECT 'C' AS ARTIFACT_TYPE,
                     TRANSACTION_NUMBER AS CONSG_NO,
                     NULL AS MANIFEST_NO,
                     process.PROCESS_CODE AS PROCESS_NO,
                     process.PROCESS_ORDER AS PROCESS_ORDER,
                     heldup.CREATED_DATE AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     OFFICE AS VALUE_1,
                     'hubOff' AS KEY_2,
                     office.REPORTING_HUB AS VALUE_2,
                     'date' AS KEY_3,
                     CREATED_DATE AS VALUE_3,
                     'TRAN_NO' AS KEY_4,
                     TRANSACTION_NUMBER AS VALUE_4,
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
               WHERE heldup.TRANSACTION_TYPE IN ('CONG')
                     AND heldup.TRANSACTION_NUMBER = CONSG_NO
              UNION
              -- ROW FOR DELIVERY
              SELECT 'C' AS ARTIFACT_TYPE,
                     CONSG_NO AS CONSG_NO,
                     NULL AS MANIFEST_NO,
                     fdp.PROCESS_CODE AS PROCESS_NO,
                     fdp.PROCESS_ORDER AS PROCESS_ORDER,
                     ffdd.TRANS_MODIFIED_DATE_TIME AS PROCESS_DATE,
                     'originOff' AS KEY_1,
                     ffd.CREATED_OFFICE_ID AS VALUE_1,
                     'date' AS KEY_2,
                     date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.FS_IN_TIME),
                                 '%d/%m/%Y %H:%i')
                        AS VALUE_2,
                     'DRSNo' AS KEY_3,
                     ffd.DRS_NUMBER AS VALUE_3,
                     'status' AS KEY_4--     ,ffdd.DELIVERY_STATUS AS VALUE_4
                     ,
                     if((SELECT count(dd.CONSIGNMENT_ID)
                           FROM ff_f_delivery_dtls dd
                          WHERE dd.CONSIGNMENT_NUMBER = CONSG_NO) > 0,
                        ffdd.DELIVERY_STATUS,
                        (SELECT fp.consg_status
                           FROM ff_f_consignment fp
                          WHERE fp.consg_no = CONSG_NO))
                        AS VALUE_4,
                     'reason' AS KEY_5,
                     coalesce(fdr.REASON_NAME, '') AS VALUE_5,
                     'ReceiverName' AS KEY_6,
                     IF(DELIVERY_TYPE = 'O',
                        '( Office Seal & Sign )',
                        ffdd.RECEIVER_NAME)
                        AS VALUE_6,
                     'DeliveryDate' AS KEY_7,
                     date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.DRS_DATE),
                                 '%d-%m-%Y')
                        AS VALUE_7,
                     'DeliveryTime' AS KEY_8,
                     date_format(IFNULL(ffdd.DELIVERY_TIME, ffd.DRS_DATE),
                                 '%H:%i:%s')
                        AS VALUE_8  -- [%H/k - Hour (0-23), %h - Hour (01-12)]
                                  ,
                     'deliveryOutTime' AS KEY_9,
                     date_format(ffd.FS_OUT_TIME, '%d/%m/%Y %H:%i')
                        AS VALUE_9,
                     'thirdparty/FS' AS KEY_10,
                     CASE
                        WHEN ffd.DRS_FOR = 'FS'
                        THEN
                           (SELECT CONCAT(fde.FIRST_NAME, ' ', fde.LAST_NAME)
                              FROM ff_d_employee fde
                             WHERE fde.employee_id = ffd.employee_id)
                        WHEN ffd.DRS_FOR = 'BA'
                        THEN
                           (SELECT fdv.BUSINESS_NAME
                              FROM ff_d_vendor fdv
                             WHERE fdv.VENDOR_ID = ffd.BA_ID)
                        WHEN ffd.DRS_FOR = 'FR'
                        THEN
                           (SELECT fdv.BUSINESS_NAME
                              FROM ff_d_vendor fdv
                             WHERE fdv.VENDOR_ID = ffd.FRANCHISEE_ID)
                        WHEN ffd.DRS_FOR = 'CC'
                        THEN
                           (SELECT fdv.BUSINESS_NAME
                              FROM ff_d_vendor fdv
                             WHERE fdv.VENDOR_ID = ffd.VENDOR_ID)
                     END
                        AS VALUE_10,
                     'updatedDate' AS KEY_11,
                     date_format(ffdd.TRANS_MODIFIED_DATE_TIME,
                                 '%d-%m-%Y %H:%i') AS VALUE_11,
                     NULL AS KEY_12,
                     NULL AS VALUE_12,
                     NULL AS KEY_13,
                     NULL AS VALUE_13,
                     NULL AS KEY_14,
                     NULL AS VALUE_14
                FROM ff_f_delivery ffd
                     LEFT JOIN ff_f_delivery_dtls ffdd
                        ON ffd.DELIVERY_ID = ffdd.DELIVERY_ID
                     LEFT JOIN ff_d_process fdp
                        ON fdp.PROCESS_CODE = 'DLRS'
                     LEFT JOIN ff_d_reason fdr
                        ON ffdd.REASON_ID = fdr.REASON_ID
               WHERE ffdd.CONSIGNMENT_NUMBER =
                        if(
                           (SELECT count(1)
                              FROM ff_f_delivery_dtls dd
                             WHERE dd.CONSIGNMENT_NUMBER =
                                      CONSG_NO) > 0,
                           CONSG_NO,
                           (if(
                               (SELECT cn.consg_status
                                  FROM ff_f_consignment cn
                                 WHERE cn.consg_no = CONSG_NO) IN ('D','S'),
                               (SELECT del.consignment_number
                                  FROM ff_f_delivery_dtls del
                                 WHERE del.consignment_number IN
                                          (SELECT fc.child_consg_number
                                             FROM ff_f_child_consignments fc
                                            WHERE fc.consg_id IN
                                                     (SELECT fp.consg_id
                                                        FROM ff_f_consignment fp
                                                       WHERE fp.consg_no =
                                                                CONSG_NO))
                                 LIMIT 1 ),
                               NULL) COLLATE utf8_general_ci))
                     AND ffdd.RECORD_STATUS = 'A'
              UNION
              -- ROW FOR STOP DELIVERY
              SELECT 'C' AS ARTIFACT_TYPE,
                     ffc.CONSG_NO AS CONSG_NO,
                     NULL AS MANIFEST_NO,
                     'SDLV' AS PROCESS_NO,
                     NULL AS PROCESS_ORDER,
                     ffc.STOP_DELV_DATE AS PROCESS_DATE,
                     'stopDlvReqOff' AS KEY_1,
                     convert(ffc.STOP_DELIVERY_REQ_OFFICE, CHARACTER)
                        AS VALUE_1,
                     NULL AS KEY_2,
                     NULL AS VALUE_2,
                     NULL AS KEY_3,
                     NULL AS VALUE_3,
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
                FROM ff_f_consignment ffc
               WHERE ffc.CONSG_NO = CONSG_NO AND ffc.CONSG_STATUS = 'X') cn_track,
             (SELECT @rownum := 0) r
      ORDER BY cn_track.PROCESS_DATE, cn_track.PROCESS_ORDER;

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