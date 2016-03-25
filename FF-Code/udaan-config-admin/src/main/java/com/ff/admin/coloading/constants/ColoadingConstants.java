package com.ff.admin.coloading.constants;



/**
 * @author isawarka
 *
 */
public interface ColoadingConstants {
	
	String AIR_COLOADING_SUCCESS = "airSuccess";
	String TRAIN_COLOADING_SUCCESS = "trainSuccess";
	String VEHICLES_COLOADING_SUCCESS = "vehiclesSuccess";
	String CD_M_SUCCESS = "cdAwbModificationSuccess";
	String SERVICE_ENTRY_SUCCESS = "vehicleServiceEntrySuccess";
	String SURFACE_RATE_SUCCESS = "surfaceRateEntrySuccess";
	String FUEL_SUCCESS = "fuelRateEntrySuccess";
	
	String REGION_LIST ="regionList";
	String VENDOR_LIST ="vendorList";
	String VENDOR_LIST_BY_ROUTE ="vendorListByRoute";
	String ORIGION_CITY_LIST ="origionCityList";
	String DESTINATION_CITY_LIST ="destinationCityList";
	String VEHICLE_LIST ="vehicleList";

	String ORIGIN_CITY_ID ="origionCityId";
	String DESTINATION_CITY_ID ="destCityId";
	String GET_TRAIN_LIST ="getTrainList";
	
	String CD_AWB_TYPE = "cdType";
	String TRANSPORT_MODE= "transportMode";
	String VENDOR_TYPE = "vendorType";
	String VENDOR_TYPE_CODE = "vendorTypeCode";
	String SERVICE_BY_TYPE = "serviceByType";
	
	String GET_FLIGHT_LIST ="getFlightDetails";
	
	String GET_VENDOR_LIST_BY_TRANSPORT_MODE = "getVendorListByTransportMode";
	
	String AWB ="AWB";
	String CD ="CD";
	String RR = "RR";
	String FIRST_FLIGHT_TO_FIRST_FLIGHT ="FF";

	Integer  AWB_LENGTH = 28;
	Integer  CD_LENGTH = 10;
	
	String XLS = "application/vnd.ms-excel";
	String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	//Constants for Drop-Downs
	String CD_TYPE_LIST = "cdTypeList";
	String CD_TYPE = "CD_TYPE";
	String SSP_RATE_LIST = "sspRateList";
	String SSP_RATE_KG = "SSP_RATE_KG";
	String RATE_TYPE_LIST = "rateTypeList";
	String RATE_TYPE = "COLOADER_RATE_TYPE";
	String DUTY_HOURS_LIST = "dutyHourList";
	String DUTY_HOURS = "DUTY_HOURS";
	String FUEL_TYPE_LIST = "fuelTypeList";
	String FUEL_TYPE = "FUEL_TYPE";
	String VEHICLE_TYPE_LIST = "vehicleTypeList";
	String VEHICLE_TYPE = "VEHICLE_TYPE";
	//Constants for Drop-Downs End
	
	
	String STD_TYPE_QUERY = "getStockStdTypeByTypeName";
	String BLANK = "";
	String USER_INFO = "user";
	String AIR = "Air";
	String TRAIN = "Rail";
	String ROAD = "Road";
	String SURFACE = "Surface";
	
	char SAVE_CHAR = 'T';
	char SUBMIT_CHAR = 'P';
	char INACTIVE = 'I';
	char RENEW = 'R';
	String SAVE_STRING_T = "T";
	String RENEW_R = "R";
	String SUBMIT_P = "P";
	
	//Errors Constants
	String CL0001 = "CL0001";
	String CL0002 = "CL0002";
	String CL0003 = "CL0003";
	String CL0004 = "CL0004";
	String CL0005 = "CL0005";
	String CL0006 = "CL0006";
	String CL0007 = "CL0007";
	String CL0008 = "CL0008";
	String CL0009 = "CL0009";
	String CL0012 = "CL0012";
	String CL0017 = "CL0017";
	String CL0018 = "CL0018";
	String CL0019 = "CL0019";
	String CL0020 = "CL0020";
	String CL0021 = "CL0021";
	String CL0022 = "CL0022";
	String CL0023 = "CL0023";
	String CL0024 = "CL0024";
	String CL0025 = "CL0025";
	String CL0026 = "CL0026";
	
	String CD_AWB_DETAILS_NOT_FOUND = "CL0010";
	String CD_AWB_SAVED_SUCCESSFULLY = "CL0011";
	String FROM_DATE_SHOULD_NOT_BE_GREATER_THAN_TO_DATE = "CL0013";
	String TO_DATE_AND_FROM_DATE_SHOULD_NOT_BE_SAME = "CL0014";
	String TO_DATE_SHOULD_NOT_BE_GREATER_THAN_TODAY_DATE = "CL0015";
	String LOGGED_IN_OFFICE_AND_REGION_DETAILS_NOT_FOUND = "CL0016";

	//Errors End
	
	//Rate Calculation Constants
	String M = "M";
	String MOVEMENT_DIRECTION = "D";
	String COLOADER = "C";
	String RATE_TYPE_FIXED = "FT";
	String RATE_TYPE_FIXED_DAY = "FD";
	String RATE_TYPE_FIXED_MONTH = "FM";
	String RATE_TYPE_PER_DAY_FUEL = "PD+F";
	String RATE_TYPE_PER_MONTH_FUEL = "PM+F";
	
	Integer KM =240;
	Integer HRS =241;
	Integer Each_BAG =242;
	Integer Days =243;
	
	//Fuel Types
	String PETROL ="Petrol";
	String DIESEL ="Diesel";
	String CNG ="CNG";
	String LPG ="LPG";
	
	//CD/AWB status
	String CD_AWB_STATUS_NEW = "N";
	String CD_AWB_STATUS_UPDATED = "U";
	
	
	//query
	String QRY_GET_LOAD_CONNECTED_4_CD_AWB_MODIFACTION = "getLoadConnected4CdAwbModifaction";
	String QRY_UPDATE_CD_AWB_DETAILS_BY_LOAD_MOVEMENT_ID = "updateCdAwbDetailsByLoadMovementId";
	String QRY_UPDATE_LOAD_MOVEMENT_DT_TO_CENTRAL = "updateLoadMovementDtToCentral";
	String QRY_UPDATE_RATE_CALCULATED = "updateIfRateCalculated";
	
	String ERROR_DESCRIPTION = "Error Description";
	String ERROR = "Y";
	
	String TRAIN_COLOADING_WELCOME = "welcome";
    String TRAIN_COLOADING_ONLY_ALLOWED_AT_RHO ="CUSTM027";
    
    String VEHICLE_COLOADING_WELCOME = "welcome";
    String VEHICLE_COLOADING_ONLY_ALLOWED_AT_RHO ="CUSTM028";
	
}
