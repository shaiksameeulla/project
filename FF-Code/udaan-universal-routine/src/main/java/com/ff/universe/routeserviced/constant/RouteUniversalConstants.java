/**
 * 
 */
package com.ff.universe.routeserviced.constant;

/**
 * @author rmaladi
 *
 */
public interface RouteUniversalConstants {

	String AIR_CODE = "Air";
	String ROAD_CODE = "Road";
	String TRAIN_CODE = "Rail";
	
	String QRY_GET_TRIP_DETAILS = "getTripDetailsByRouteAndTransportMode";
	String QRY_ORIGIN_CITY = "originCity";
	String QRY_DEST_CITY = "destCity";
	String QRY_TRANSPORT_MODE = "transportMode";

	String QRY_GET_TRANSPORT_DETAILS_BY_FLIGHT = "getTransportDetailsByFlight";
	String QRY_FLIGHT_NUMBER = "flightNumber";	
	String QRY_GET_TRANSPORT_DETAILS_BY_TRAIN = "getTransportDetailsByTrain";
	String QRY_TRAIN_NUMBER = "trainNumber";
	String QRY_GET_TRANSPORT_DETAILS_BY_ROAD = "getTransportDetailsByRoad";
	String QRY_GET_TRANSPORT_DETAILS_BY_ROAD_RHO_OFC_AND_REGION = "getTransportDetailsByRoadRHOOfcAndRegion";
	String QRY_GET_TRANSPORT_DETAILS_BY_ROAD_AND_RHOOFC = "getTransportDetailsByRoadAndRHOOfc";
	String QRY_VEHICLE_REG_NUMBER = "regNumber";
	String FLAG_Y = "Y";
	String OFC_TYPE_CODE = "officeTypeCode";
	String PARAM_REGION_ID = "regionId";
	String PARAM_OFC_ID = "officeId";
	String QRY_GET_TRIP_DETAILS_OF_VEHICLE__BY_REGION = "getTripDetailsByRegionForVehicle";
	String QRY_GET_TRIP_SERVICED_BY_LIST_BY_TRIP_SERVICED_BY_FOR_VEHICLE = "getTripServicedByDOListByTripServicedByForVehicle";
	String QRY_GET_TRIP_SERVICED_BY_LIST_BY_TRIP_SERVICED_BY_FOR_AIR_COLOADING = "getTripServicedByDOListByTripServicedByForAirColoading";
}
