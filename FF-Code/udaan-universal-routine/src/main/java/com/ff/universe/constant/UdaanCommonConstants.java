package com.ff.universe.constant;

public interface UdaanCommonConstants {

	String TRUE = "true";
	String FALSE = "false";
	String GET_EMPLOYEE_DETAILS_BY_EMPID = "getEmployeeDetailsByEmpId";
	String GET_CUSTOMER = "getCustomer";
	String GET_EMPLOYEE = "getEmployee";
	String CUSTOMER_ID = "customerId";
	String EMPLOYEE_ID = "employeeId";
	String GET_ALL_CUSTOMERS = "getAllCustomers";
	String GET_ALL_EMPLOYEES = "getAllEmployees";
	String GET_EMPLOYEES_BY_CITY = "getEmpBycity";
	String GET_CUSTOMERS_BY_OFFICE = "getCustomersByOffice";
	String OTHERS_VEHICLE = "Others";
	String OTHERS_VEHICLE_CODE = "O";
	// Runsheet Assignmnet Status
	String MASTER = "Master";
	String RUNSHEET_ASSIGNMENT_STATUS_GENERATE = "G";
	String RUNSHEET_ASSIGNMENT_STATUS_UNUSED = "U";

	// Param constants for DAO
	String AAPLICABLE_FOR_DISPATCH_PARAM = "aaplicableForDispatch";
	String REGIONAL_OFFICE_DO_OFFICE_ID_PARAM = "regionalOfficeDO.officeId";
	String ORIGIN_CITY_DO_CITY_ID_PARAM = "originCityDO.cityId";
	String DEST_CITY_DO_CITY_ID_PARAM = "destCityDO.cityId";
	String TRANSPORT_MODE_DO_TRANSPORT_MODE_ID_PARAM = "transportModeDO.transportModeId";
	String TRANSPORT_MODE_ID_PARAM = "transportModeId";
	String ROUTE_ID_PARAM = "routeId";
	String SERVICE_BY_TYPE_ID_PARAM = "serviceByTypeId";
	String ASSIGNMENT_HEADER_ID = "assignmentHeaderId";
	String CURRENT_DATE = "currentDate";
	String PARAM_VENDOR_ID = "vendorId";
	
	String TRANSPORT_MODE_CODE_PARAM = "transportModeCode";

	String MANIFEST_TYPE = "ManifestType";
	String REGION_ID = "RegionId";
	String OFFICE_ID = "officeId";
	String PINCODE_ID = "pincodeId";
	String CITY_ID = "cityId";
	String REGIONAL_OFFICE_ID_PARAM = "regionalOfficeId";
	String OFFICE_ID_PARAM = "officeId";

	// Query Name
	String GET_TRIP_SERVICED_BY_LIST_BY_ROUTE_ID_TRANSPORT_MODE_ID_SERVICE_BY_TYPE_ID = "getTripServicedByListByRouteIdTransportModeIdServiceByTypeId";
	String GET_TRIP_SERVICED_BY_LIST_BY_TRANSPORT_MODE_ID_SERVICE_BY_TYPE_ID = "getTripServicedByListByTransportModeIdServiceByTypeId";
	String QRY_GET_TRIP_SERVICED_BY_LIST_BY_ROUTE_ID_MODE_ID_SERVICE_BY_TYPE_ID_VENDOR_ID = "getTripServicedByListByRouteIdModeIdServiceByTypeIdVendorId";
	String GET_STATUS_MASTER_PICKUP_ASSIGNMENT = "getStatusOfMasterPickupAssignment";
	String GET_ALL_REGIONS = "getAllRegions";
	String QRY_GET_OFFICES_BY_CITY = "getOfficeByCity";
	String QRY_GET_ALL_BRANCH_OFFICES_BY_CITY = "getAllBranchOfficesByCity";
	String QRY_GET_ALL_BRANCH_AND_STANDALONE_OFFICES_BY_CITY="getAllBranchAndStandaloneOfficesByCity";
	String QRY_GET_OFFICES_BY_REGION = "getOfficeByRegion";
	String GET_OFFICES_BY_REGION_AND_OFFICE_TYPE = "getOfficeByRegionAndOfcType";
	String QRY_GET_OFFICES_BY_REGION_ID = "getOfficeByRegionID";
	String PARAM_CITY_ID = "cityId";
	String PARAM_MAPPED_TO_REGION = "regionId";

	String QRY_GET_CITIES_BY_REGION = "getCitiesByRegion";
	String QRY_GET_TRANSHIPMENT_CITIES_BY_REGION = "getTranshipmentCitiesByRegion";
	String QRY_BRANCH_PINCODE_SERVICEABILITY = "branchPincodeServiceability";
	String QRY_PINCODE_SERVICEABILITY_BY_CITY = "pincodeServiceabilityByCity";
	String QRY_GET_OFFICES_BY_CITY_AND_OFFICETYPE = "getOfficeByCityAndOfficeType";
	String GET_OFFICES_BY_REGIONAL_OFFICE_EXCLUDE_OFFICE_QUERY = "getOfficesByRegionalOfficeExcludeOffice";
	String QRY_GET_EMPLOYEES_BY_OFFICE_ID = "getEmployeesByOfficeId";
	String QRY_GET_ALL_HUBS_BY_CITY = "getAllHubsByCity";
	String QRY_GET_REPORTING_HUB_OF_LOGGED_IN_BRANCH = "getReportingHubOfLoggdInBranchOffice";
	String QRY_GET_OFFICES_BY_TYPE = "getOfficeByType";
	String PARAM_OFF_TYPE = "offType";
	
	// for Stock Magmnt module/universal
	// service***************START******************
	// ####Issued Type START
	String ISSUED_TO_BRANCH = "BR";
	String ISSUED_TO_BA = "BA";
	String ISSUED_TO_EMPLOYEE = "EMP";
	String ISSUED_TO_CUSTOMER = "CUSTOMER";
	String ISSUED_TO_FR = "FR";
	// ####Issued Type END

	// ####Stock Series type START
	String SERIES_TYPE_CNOTES = "ZCON";// ITEM(MATERIAL) CATEGORY //CNOTES ZCON
	String SERIES_TYPE_OGM_STICKERS = "ZOGM";// MATERIAL OGM_NO ZOGM
	String SERIES_TYPE_BPL_STICKERS = "ZBPL";// MATERIAL BPL_NO ZBPL
	String SERIES_TYPE_CO_MAIL_NO = "ZCOM";// MATERIAL CO_MAIL_NO ZCOM
	String SERIES_TYPE_BAG_LOCK_NO = "ZBLK";// MATERIAL BAG_LOCK_NO ZBLK
	String SERIES_TYPE_MBPL_STICKERS = "ZMBP";// MATERIAL MBPL_NO ZMBP

	// series identifier
	String SERIES_TYPE_BPL_STICKERS_PRODUCT = "B";
	String SERIES_TYPE_MBPL_STICKERS_PRODUCT = "M";
	String SERIES_TYPE_CO_MAIL_NO_PRODUCT = "CM";
	String SERIES_TYPE_BAG_LOCK_NO_PRODUCT = "BL";

	// series length
	int CN_LENGTH = 12;
	int STICKER_LENGTH = 10;
	int CO_MAIL_LENGTH = 12;
	int BAG_LOCK_LENGTH = 8;

	// If RTO / RTH / Misroute manifest is for Dox then OGM Number is used.
	// If RTO / RTH / Misroute manifest is for Parcels / Packets then BPL Number
	// is used.
	// OGM and BPL stickers under Stickers Category

	// ####Stock Series type END
	// for Stock Magmnt module/universal
	// service***************END******************

	String QRY_GET_THIRD_PARTY = "getThirdParty";
	String TYPE_NAME = "typeName";
	String THIRD_PARTY_TYPE = "THIRD_PARTY_TYPE";
	String THIRD_PARTY_ID = "partyID";
	String QRY_GET_PARTY_NAME_FOR_BA = "getPartyNamesForBA";
	String IS_REGION_EXISTS = "isRegionExists";

	// for Routing
	String GET_ALL_CITIES = "getAllCities";

	String QRY_GET_ALL_ASSIGN_APPL_SCREENS = "getAllAssignApplScreens";
	String QRY_PARAM_SCR_ASSIGN = "scrAssign";
	String QRY_PARAM_VALUE_SCR_ASSIGN = "Y";

	String QRY_GET_ALL_REGIONAL_OFFICES = "getRegionalOffices";
	String QRY_PARAM_OFFICE_TYPE_CODE = "offcTypeCode";
	String QRY_PARAM_VALUE_OFFICE_TYPE_CODE = "RO"; // changed from RHO
	String QRY_GET_CITIES_BY_OFFICE = "getCitiesByOfficeIds";
	String QRY_PARAM_OFFICE_ID = "officeId";
	String QRY_PARAM_CITY_ID = "cityId";
	String QRY_GET_ALL_REGIONAL_OFFICES_BY_CITI_RHO = "getRegionalOfficesByCityAndRHO";
	String QRY_GET_OFFICE_BY_EMPID = "getOfficeByEmpId";

	String WEIGHT_READER_URL = "weightReaderURL";
	String MAC_ADDRESS_URL = "macReaderURL";
	String PRINT_JOB_URL = "printJobURL";
	String BULK_BILL_PRINT_JOB_URL = "bulkBillPrintJobURL";

	// Reason Type
	/** The reason type for held up. */
	String REASON_TYPE_FOR_HELD_UP = "H";
	
	/** The reason type for held up location. */
	String REASON_TYPE_FOR_HELD_UP_LOCATION = "RHL";	

	/** The reason type for non delivery. */
	String REASON_TYPE_FOR_NON_DELIVERY = "P";
	
	/** The reason type for non delivery for bulk drs. */
	String REASON_TYPE_FOR_NON_DELIVERY_FOR_BULK_DRS = "BP";

	/** The reason type for rto rth validation. */
	String REASON_TYPE_FOR_RTO_RTH_VALIDATION = "RH";
	String REASON_TYPE_FOR_RTO_RTH_DOX = REASON_TYPE_FOR_RTO_RTH_VALIDATION;

	// Vendor Type
	/** The vendor type co courier. */
	String VENDOR_TYPE_CO_COURIER = "R";
	String VENDOR_TYPE_FRANCHISE = "F";
	String VENDOR_TYPE_BA = "S";
	// For SAP Integration read services

	String QRY_GET_OFFICE_TYPE_BY_OFFICE_TYPE_CODE = "getOfficeTypeByOfficeTypeID";
	String OFFICE_TYPE_CODE = "offcTypeCode";

	String QRY_GET_OFFICE_ID_BY_RHO_CODE = "getOfficeIdByReportingRHOCode";
	String REPORTING_RHO_CODE = "officeCode";

	String QRY_GET_VENDOR_BY_CODE = "getVendorByCode";
	String QRY_GET_VENDOR_TYPE_BY_ACC_GRP = "getVendorTypeByAccGrp";
	String ACC_GRP_SAP = "accGroupSAP";
	String VENDOR_CODE = "vendorCode";

	String QRY_GET_EMPLOYEE_BY_CODE = "getEmpByCode";
	String QRY_GET_EMPLOYEE_BY_CODE_SAP = "getEmpByCodeSAP";
	String EMP_CODE = "empCode";

	String QRY_GET_CUSTOMER_BY_CODE = "getCustByCode";
	String QRY_GET_CUSTOMER_BY_CODE_SAP = "getCustByCodeSAP";
	String CUSTOMER_CODE = "customerCode";

	String QRY_GET_CONTRACT_BY_NO = "getContractByNo";
	String QRY_GET_CONTRACT_BY_NO_SAP = "getContractByNoSAP";
	String QRY_GET_CUSTOMER_ID_BY_CONTRACT_NO = "getCustomerIdByContractNo";
	String CONTRACT_NO = "contractNo";

	String QRY_GET_BA_CUSTOMER_BY_CODE = "getBACustByCode";
	String QRY_GET_BA_CUSTOMER_BY_CODE_SAP = "getBACustByCodeSAP";
	String BA_CODE = "baCode";

	String QRY_GET_CONTRACT_DTLS_BY_NO = "getContractDtls";
	String QRY_GET_CONTRACT_ID_BY_CONTRACT_NO = "getContractIdByContractNo";
	String RATE_CONTRACT_NO = "rateContractNo";
	String RATE_CONTRACT_ID = "rateContractId";

	String QRY_GET_CONTRACT_PAY_DTLS = "getContractPayDtls";

	String QRY_GET_RHO_OFFICES_BY_USER_ID = "getRHOOfficesByUserId";

	String QRY_PARAM_USER_ID = "userId";

	String QRY_PARAM_OFF_TYPE_CODE = "officeTypeCode";

	String QRY_GET_CITIES_OF_REPORTED_OFC_RHO = "getCitiesOfReportedOfficesByRHOOffice";

	String PARAM_RHO_OFFICE_ID = "rhoOfficeId";

	String QRY_GET_CITIES_OF_ASSIGNED_OFC_FOR_USER = "getAssignedOfficeCitiesForUser";

	String PARAM_USER_ID = "userId";

	String CORPORATE_OFFICE_TYPE_CODE = "CO";

	String QRY_OFFICE_MAP_UNDER_BRANCH = "getOfficeMapUnderBranch";

	String QRY_PARAM_BRANCHID = "branchId";

	String QRY_GET_CITIES_OF_ALL_REPORTED_OFC_RHO = "getCitiesOfAssignedRHOOffices";

	String PARAM_RHO_OFFICE_LIST = "rhoOfficeList";

	// ERROR CODES
	String INVALID_PINCODE = "PU001";
	String NO_DELIVERY_BRANCH_FOR_PINCODE = "PU0010";
	String NO_DELIVERY_BRANCHES_FOR_SELECTED_CUSTOMER = "I0005";
	String SEQUENCE_NUMBER_GENERATED = "PUG006";

	String QRY_GET_OFC_AND_HUB_OFC_SERVICED_BY_PINCODE = "getOffficesAndHubOfficesServicedByPincode";
	String PARAM_PINCODE = "pincode";
	String QRY_GET_OFC_BY_USER_ID = "getOfficeByUserId";
	String GET_ALL_STATES = "getAllStates";
	String GET_CITY_LIST_BY_STATE = "getCitiesByStateId";
	String STATE_ID = "stateId";
	String QRY_GET_OFC_BY_OFC_CODE = "getOfficeByOfficeCode";
	String PARAM_OFC_CODE = "officeCode";
	String QRY_GET_EMP_BY_CODE = "getEmpDOByCode";
	String PARAM_EMP_CODE = "empCode";
	String PARAM_DEPARTMENT_CODE = "departmentCode";
	String QRY_GET_DEPARTMENT_BY_CODE = "getDepartmentByCode";

	String SERVICED_ON_AFTER_14_00 = "A";
	String SERVICED_ON_BEFORE_14_00 = "B";
	String SERVICED_ON_SUNDAY = "S";

	// PnD Commission Constants
	String SP_GEN_DLV_COMMISSION_CALC = "generateDlvCommissionCalc";
	String QRY_GET_DLV_COMM_CALC_DTLS = "getDlvCommCalcDtls";
	String QRY_GET_OFC_AND_HUB_OFC_OF_CITY_SERVICED_BY_PINCODE = "getOffficesAndAllHubOfficesofCityServicedByPincode";
	String PARAM_HUB_OFC_CODE = "hubOfcCode";
	String GET_ALL_SECTORS = "getAllSectors";
	String getOfficecByCityIDForReport = "getOfficecByCityIDForReport";
	String getAllOfficesAndRHOOfcByCity = "getAllOfficesAndRHOOfcByCity";
	String PARAM_EFFECTIVE_TO = "effectiveTo";
	String PARAM_EFFECTIVE_FROM = "effectiveFrom";
	String STATUS_ACTIVE = "A";
	String STATUS = "status";
	
}

