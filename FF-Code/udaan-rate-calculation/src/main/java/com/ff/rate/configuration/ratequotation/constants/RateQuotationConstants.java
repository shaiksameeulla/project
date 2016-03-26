package com.ff.rate.configuration.ratequotation.constants;

public interface RateQuotationConstants {

	String SUCCESS_FORWARD = "success";
	
	String NORMAL_QUOTATION = "normal";
	
	String ECOMMERCE_QUOTATION = "ecommerce";
	
	String VIEW_NORMAL_QUOTATION = "viewNormalQuotation";
	
	String VIEW_ECOMMERCE_QUOTATION = "viewEcommerceQuotation";
	
	String QRY_GET_RATE_QUOT_WT_SLABS_BY_PROD_CAT = "getRateQuotationWeightSlabByProductCat";
	
	String QRY_GET_RATE_QUOT_SLAB_RATES = "getRateQuotationSlabRates";
	
	String QRY_GET_RATE_QUOT_PROD_CAT_HEADER = "getRateQuotationProductCatHeaderDetails";
	
	String QRY_GET_RATE_QUOT_SLAB_RATES_PROD_CAT = "getQuotationProposedRatesByQuotationAndProductCat";
	
	String PARAM_RATE_PROD_CAT_ID = "rateProductCategoryId";
	
	String PARAM_RATE_QUOT_ID = "rateQuotationId";
	
	String QRY_DELETE_SLAB_RATE = "deleteRateQuotationSlabRateRecords";
	
	String PARAM_RATE_QUOT_PROD_CAT_HEADER_ID = "rateQuotationProductCategoryHeaderId";
	
	String QRY_DELETE_ORG_REG_SLAB_RATE = "deleteRateQuotationOriginSectorSlabRateRecords";
	
	String PARAM_RATE_QUOT_ORG_REG = "originSector";
	
	String QRY_DELETE_WEIGHT_SLABS = "deleteRateQuotationWeighSlabs";
	
	String QRY_DELETE_SPECIAL_DEST_SLAB_RATE = "deleteSpecialDestinationSlabRates";
	
	String QRY_GET_RATE_QUOT_SLAB_RATE_BY_PROD_CAT_HEADER = "getRateQuotSlabRatesByProdCatHeader";
	
	String SUCCESS_FORWARD_ECOMMERCE = "successEcommerce";
	String RATE_INDUSTRY_CAT_ID = "rateIndustryCategoryId";
	String RATE_INDUSTRY_CAT_ID_LIST = "rateIndustryCategoryIdList";
	String RATE_UNIVERSAL_SERVICE="rateUniversalService";
	String RATE_QUOTATION_SERVICE="rateQuotationService";
	String USER_INFO = "user";
	String QRY_GET_EMP_USERDO_BY_USERID = "getEmpUserDObyUserId";
	String USER_ID = "userId";
	String EMP_ID = "empId";
	String ONE = "1";
	String QUOTATION_NO = "QUOTATION_NO";
	String CHAR_Q = "Q";
	String CHAR_C = "C";
	String NEW = "N";
	String NORMAL = "N";
	String ECOMMERCE = "E";
	String GOVERNMENT = "GOV";
	String COPY = "C";
	String LIST_VIEW = "listView";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION = "rateQuotationListView";
	
	String PARAM_USER_ID = "userId";
	
	String PARAM_FROM_DATE = "fromDate";
	
	String PARAM_TO_DATE =  "toDate";
	
	String PARAM_QUOTATION_NO = "rateQuotationNo";
	
	String PARAM_REGION = "region";
	
	String PARAM_CITY = "city";
	
	String PARAM_STATUS = "status";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO = "rateQuotationListViewForALLRHO";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_HO = "rateQuotationListViewForHO";
	
	String QRY_GET_RATE_QUOTATION = "getRateQuotation";
	
	String QRY_GET_RATE_QUOTATION_FOR_USER = "getRateQuotationForUser";
	
	String QRY_GET_RATE_QUOTATION_FOR_ALL_RO = "rateQuotationForALLRHO";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_RO_WITH_CITY = "rateQuotationListViewForALLRHOWithCity";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_ALL_HO_WITH_CITY = "rateQuotationListViewForHOWithCity";
	
	String QRY_GET_RATE_QUOTATION_FOR_ALL_HO = "rateQuotationForHO";
	
	String QRY_GET_RATE_QUOTATION_FOR_ALL_HO_WITH_CITY = "rateQuotationForHOWithCity";
	
	String FIXED_PERCENT = "f";
	String COD_PERCENT = "c";
	String QUOTATION = "Q";
	String CONTRACT = "C";
	
	String UPDATE_RATE_QUOTATION_STATUS = "updateRateQuotationStatus";
	String ECOMMERCE_CUST_CAT_ID = "ecommerceCustCatId";
	String BFSI = "BFSI";
	String GNRL = "GNRL";
	
	String QRY_DELETE_ORG_REG_SPL_DEST_SLAB_RATE = "deleteRateQuotationOriginSectorSplDestSlabRateRecords";
	
	String QRY_GET_USERDO_BY_OFFICEID = "getUserDObyOfficId";
	String QRY_GET_USERDO_LIST_BY_OFFICEID = "getUserDOListbyOfficId";
	String QRY_GET_RHO_BY_CREATEDBY = "getRHONameByCreatedById";
	String QRY_GET_STATION_BY_CREATEDBY = "getStationNameByCreatedById";
	String QRY_GET_SALES_OFFC_NAME_BY_CUSTOMER = "getSalesOffcNameByCustomer";
	String QRY_GET_SALES_PERSON_NAME_BY_CUSTOMER = "getSalesPersonNameByCustomer";
	String QRY_GET_QUOTATION_DTLS_BY_ID_DATE_STATUS = "getQuotatnDtlsByUserIdDateStatus";
	String QRY_GET_QUOTATION_DTLS_BY_ID_QUOTATION_NO_STATUS = "getQuotatnDtlsByUserIdQuotatnNoStatus";
	String QRY_APPROVE_REJECT_DOMESTIC_QUOTATION = "approveRejectDomesticQuotation";
	String QRY_IS_EMP_ID_REGIONAL_APPROVR ="isEmpIdRegionalApprover";
	String QRY_IS_EMP_ID_CORP_APPROVR = "isEmpIdCorpApprover";
	String QRY_SEARCH_QUOTATION_FOR_REGION ="getRateQuotationDtlsForRegion";
	String CUST_ID = "custId";
	String OFFIC_ID = "officeId";

	String FROM_DATE = "fromDate";
	String TO_DATE = "toDate";
	String STATUS = "status";

	String SUBMITTED = "S";

	String APPROVE_RATE = "approve";
	String REJECT_RATE = "reject";
	String REJECTED = "R";
	String APPROVED = "A";
	String REGIONAL_OPERATOR = "RO";
	String REGIONAL_CORP = "RC";
	String APPROVAL_REQUIRD_PARAM = "approveRequird";
	String APPROVED_AT_PARAM = "approveAt";
	

	String QUOTATION_USED_FOR = "quotationUsdFor";

	String QRY_UPDATE_CONTRACT_NO = "updateContractNo";

	String QRY_SEARCH_QUOTATION_FOR_CORP_BY_QUOT_NO = "getQuotatnDtlsforCorpByQuotationNo";
	String QRY_SEARCH_QUOTATION_FOR_REGION_BY_QUOT_NO = "getQuotatnDtlsforRegionByQuotationNo";
	String QRY_SEARCH_QUOTATION_FOR_CORP="searchQuotatnForCorp";
	
	String RATE_QUOTATION_TYPE = "rateQuotationType";
	
	String COD_CHARGE_TO = "codChargeTO";
	
	String RATE_QUOTAION_SALES = "sales";
	String RATE_QUOT_SALES_TYPE = "salesType";
	String CREATED_DATE = "createdDate";
	String CREATED_BY = "createdBy";
	String UPDATED_BY = "updatedBy";
	String OFFICE = "office";
	String LOGGED_IN_OFFICE_ID = "loggedInOfficeId";
	String LOGIN_REGION_NAME = "loginRegionName";
	String LOGIN_OFFICE_CODE = "loginOfficeCode";
	String LOGIN_CITY_ID = "loginCityId";
	String STATE_ID = "stateId";
	String CITY_NAME = "cityName";
	String CITY_TOS = "cityTOs";
	String BUSINESS_TYPE = "BUSINESS_TYPE";
	String STOCK_STANDRD_TYPE_TO_LIST = "stockStandardTypeTOList";
	String CUSTOMER_DEPARTMENT = "CUSTOMER_DEPARTMENT";
	String CUSTOMER_DEPARTMENT_LIST = "CUSTOMER_DEPARTMENT_LIST";
	String CONSIGNOR_CONSIGNEE = "CONSIGNOR_CONSIGNEE";
	String OCTROI_BOURNE_BY_LIST = "OctroiBourneByList";
	String RATE_INDUSTRY_TYPE_LIST = "rateIndustryTypeTOList";
	String IND_CAT_LIST = "industryCategoryToList";
	String IND_CAT_GNRL = "indCatGeneral";
	String CUSTOMER_GROUP_LIST = "customerGroupTOList";
	String INSURED_BY_TOS = "insuredByTOs";
	String PINCODE = "pincode";
	String INVALID = "INVALID";
	String STATION = "Station";
	String SALES_OFFICE = "salesOffice";
	String QUOTATION_NUMBER = "QuotationNo";
	String RATE_QUOTATION_ID = "rateQuotationId";
	String QUOTATION_ID = "quotationId";
	String RISK_SURCHARGES = "riskSurcharges";
	String FIXED_CHRGS_TYPE = "type";
	String QUOT_TYPE = "quotType";
	String PRODUCT_CATEGOTY_ID = "productCategoryId";
	String QUOT_NUMBER = "quotationNo";
	String TYPE = "type";
	String CITY = "city";
	String REGION = "region";
	String EMP = "emp";
	String EMP_TYPE = "empType";
	String EMP_TYPE_E = "E";
	String EMP_TYPE_C = "C";
	String REGION_ALL_LIST = "regionalAllList";
	String REGION_LIST = "regionalList";
	String  STATION_REG_LIST = "stationRegList";
	String STATION_ALL_LIST = "stationAllList";
	String STATION_LIST = "stationList";
	String STATUS_LIST = "quotStatusList";
	String QUOTATION_LIST_V_STATUS = "QUOTATION_LIST_V_STATUS";
	String PAGE = "page";
	String LIST_VIEW_PAGE = "listViewPage";
	String RATE_QUOTATION_NO = "rateQuotationNo";
	String RATE_APPROVER = "approver";
	String APPROVER_REGION_DSC_LIST = "approverRegionDiscountTOList";
	String APPROVER_BENCH_MARK_IND_CAT_LIST = "approverBenchMarkIndCatList";
	String TYPE_R = "R";
	String TYPE_C = "C";
	String MESSAGE = "message";
	String QUOTATION_STATUS = "QUOTATION_STATUS";
	String QUOT_STATUS = "quotationStatus";
	String SELECTED_QUOT_NOS = "selectdQuotationNos";
	String SELECTED_APPROVAL_REQUIRED = "selectdApprovalRequirds";
	String OPERATION_NAME = "opName";
	String RATE_COMMA = ",";
	String APPROVAL_RO = "RO";
	String APPROVAL_RC = "RC";
	String SUCCESS = "SUCCESS";
	String FAILURE = "FAILURE";
	String USER_EMP_ID = "userEmpId";
	String SALES_USER_EMP_ID = "salesUserEmpId";
	String SALES_TYPE_E = "E";
	String SALES_TYPE_C = "C";
	String VIEW = "view";
	String QUOT_MODULE = "quotModule";
	String QUOT_USER_ID = "quotUserId";
	String SALES_OFFICE_NAME = "salesOfcName";
	String REGIONAL_NAME = "regionalName";
	String EMPLOYEE_NAME = "employeeName";
	String EMPLOYEE_ID = "employeeId";
	String RATE_GOVERNMENT = "government";
	String ON = "on";

	String APPROVER = "approver";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_SE = "rateQuotationListViewForSE";
	
	String QRY_GET_RATE_QUOTATION_SE = "getRateQuotationForSE";
	
	String QRY_GET_LIST_VIEW_RATE_QUOTATION_FOR_CO = "rateQuotationListViewForCO";
	
	String QRY_GET_RATE_QUOTATION_FOR_CO = "rateQuotationForCO";
	
	String QRY_SRH_APRV_OR_REJ_QUOTATION_FOR_REGION ="getApprovedOrRejctedRateQuotationDtlsForRegion";

	String RATE_QUOTATION_TITLE = "RATE_QUOTATION_TITLE";
	
	String QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_REGION = "getEcommerceRateQuotationDtlsForRegion";
	
	String QRY_SRH_APRV_OR_REJ_ECOM_QUOTATION_FOR_REGION ="getApprvdOrRjctdEcommerceRateQuotationDtlsForRegion";
	
	String QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_REGION_BY_QUOT_NO = "getEcommQuotatnDtlsforRegionByQuotationNo";
	
	String QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_CORP="searchEcommerceQuotatnForCorp";

	String QRY_SEARCH_ECOMMERCE_QUOTATION_FOR_CORP_BY_QUOT_NO = "getEcommerceQuotatnDtlsforCorpByQuotationNo";
	
	String INACTIVE = "I";

	String LC_CODE = "LC";
	
	String IND_CAT_CUST_CAT_ID = "indCatCustCatIds";
	
	String STATUS_INACTIVE = "I";

	String RATE_CONTRACT_NO = "Contract number";
	String RATE_QUOT_NO = "Quotation number";
	String RATE_BENCH_MARK = "Rate benchmark";
	String RATE_REGION_BENCH_MARK = "Regional Rate benchmark";
	String RATE_REGION_BENCH_MARK_DISCOUNT = "Regional Rate benchmark discount";
	String QUOTATION_APPROVE_NOT_AUTHORIZED = "You are not authorised to approve the quotation";

	String QRY_GET_RATE_COD_CHARGES = "getRateCodCharges";
	String USER_TYPE = "userType";

	String SUPER_USER = "S";

	String CHAR_E = "E";
	String QUOT_REGION_LIST = "quotRHOList";

	String LEAD_CUSTOMER_NAME = "customerName";
	String LEAD_NUMBER = "leadNumber";
	String LEAD_CONTACT_PERSON = "contactPerson";
	String LEAD_CONTACT_NO = "contactNo";
	String LEAD_MOBILE_NO = "mobileNo";
	String LEAD_ADDRESS_ONE = "address1";
	String LEAD_ADDRESS_TWO = "address2";
	String LEAD_ADDRESS_THREE = "address3";
	String LEAD_PINCODE = "pincode";
	String LEAD_DESIGNATION = "designation";
	String LEAD_EMAIL = "email";
	String LEAD_IND_TYPE_CODE = "indTypeCode";
	String IND_TYPE_CODE = "industryTypeCode";
	String CUST_GROUP = "CUSTGROUP";
	
	String GET_RATE_QUOTATION_BY_ID = "getRateQuotationByQuotationId";
	
	String QRY_IS_RATE_QUOTATION_EXIST = "isRateQuotationExist";
	String QUOT_USED_FOR = "quotationUsedFor";
	
	String ECOMMERCE_QUOTATION_APPROVE_SCREEN_CODE = "ECQA";
	String IS_ECOMMERCE__APPROVER = "isEQApprover";
}
