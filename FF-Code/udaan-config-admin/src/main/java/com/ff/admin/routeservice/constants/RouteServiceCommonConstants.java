package com.ff.admin.routeservice.constants;
/**
 * @author rmaladi
 *
 */
public interface RouteServiceCommonConstants {
	String SUCCESS_FORWARD = "success";
	
	String PARAM_ORIGIN_CITY_ID = "originStationId";
	String PARAM_DEST_CITY_ID = "destinationStationId";
	String QRY_TRANSPORT_MODE = "transportMode";
	String PARAM_REGION_ID = "regionId";
	String PARAM_TRANSSHIPMENT_CITY_ID = "transshipmentCityId";
	String PARAM_TRANSPORT_MODE_ID = "transportModeId";
	String PARAM_SERVICE_BY_TYPE_ID = "serviceByTypeId";
	String PARAM_SERVICE_BY_TYPE_CODE = "serviceByTypeCode";
	String PARAM_EFFECTIVE_FROM = "effectiveFrom";
	String PARAM_EFFECTIVE_TO = "effectiveTo";
	String PARAM_VENDOR = "vendor";
	String QRY_ORIGIN_CITY = "originCity";
	String QRY_DEST_CITY = "destCity";
	String PARAM_TRANSPORT_MODE = "transportMode";
	String PARAM_TRANSPORT_NUMBER = "transportNo";
	String SCREEN_NAME = "screenName";
	
	
	
	String CODE_SERVICE_BY_TYPE_DIRECT = "D";
	String CODE_SERVICE_BY_TYPE_EMPLOYEE = "E";
	String CODE_SERVICE_BY_TYPE_VENDOR = "V";
	String QRY_GET_TRIP_DETAILS_BY_EMP_TRIP_SERVICED_BY = "getTripDetailsByEmployeeTripServicedBy";
	String QRY_GET_TRIP_DETAILS_BY_VENDOR_TRIP_SERVICED_BY = "getTripDetailsByVendorTripServicedBy";
	String QRY_TRIP_SERVICED_BY_EFFECTIVE_FROM = "effectiveFrom";
	String QRY_TRIP_SERVICED_BY_EFFECTIVE_TO = "effectiveTo";
	String QRY_VENDOR = "vendorId";
	String QRY_EMPLOYEE = "employeeId";
	String QRY_SERVICE_BY_TYPE = "serviceByTypeId";
	String TRANSPORT_MODE = "transportModeList";
	String REGION = "regionList";
	String QRY_TRIP_IN_ACTIVE = "inActiveTripDetails";
	String PARAM_ACTIVE = "active";
	String FLAG_N = "N";
	String FLAG_Y = "Y";
	String PARAM_TRIP_ID = "tripId";
	String QRY_TRANSSHIPMENT_ROUTE_IN_ACTIVE = "inActiveTransshipmentRouteDetails";
	String PARAM_TRANSSHIPMENT_ROUTE_ID = "transshipmentRouteId";	
	String QRY_TRIP_SERVICED_BY_IN_ACTIVE = "inActiveTripServicedByDetails";
	String PARAM_TRIP_SERVICED_BY_ID = "tripServicedById";
	String ROUTE_PURE = "Pure Route";
	String TRANSPORT_LIST = "transportList";
	String REGION_LIST = "regList";
	String ERROR_FLAG = "ERROR";
	String SUCCESS_FLAG = "SUCCESS";
	String ROUTE_SERVICE ="Route Service";
	String STATION = " Station ";
	String ORIGIN_CITY = "Origin"+STATION;
	String DEST_CITY = "Destination"+STATION;
	String TRNSPRT_MODE = "Transport Mode";
	String FLIGHT = "Flight";
	String TRAIN = "Train";
	String VEHICLE = "Vehicle";
	String DEPT_TIME = "Departure Time";
	String ARVL_TIME = "Arrival Time";
	String REGION_DATA ="Region";
	String SAVE = "save";
	String UPDATE = "update";
	String TRANSPMENT_ROUTE = "Transshipment Route";
	String TRANSPMENT_STATION = "Transshipment City";
	String SERVICE_BY_TYPE = "ServiceBy Type";
	String TRIP_SERVICED = "Route Serviced";
	String TRIP = "Route Serviced";
	String DATE_VAL = "Date";
	String VENDOR = "Vendor";
	String TRIP_DATA = "Trip";
	String QRY_GET_TRIP_IDS_BY_ROUTE_AND_TRANSPORT_MODE = "getTripIdsByRouteAndTransportMode";
	String QRY_GET_TRIP_IDS_FOR_VEHICLE_BY_REGION = "getTripIdsForVehicleByRegion";
	String PARAM_OFC_TYPE_CODE = "officeTypeCode";
	String QRY_GET_TRIP_DETAILS_BY_EMP_TRIP_SERVICED_BY_FOR_VEHICLE = "getTripDetailsByEmployeeTripServicedByForVehicle";
	String QRY_GET_TRIP_DETAILS_BY_VENDOR_TRIP_SERVICED_BY_FOR_VEHICLE = "getTripDetailsByVendorTripServicedByForVehicle";
	String AIRLINE_SESSION = "AIRLINE_SESSION";
	
	String AIR_CO_LOADER_RATE_ENTRY = "Air Co-loader Rate Entry - AWB/CD";
	String ROUTE_SERVICED_BY = "Route ServicedBy";
}

