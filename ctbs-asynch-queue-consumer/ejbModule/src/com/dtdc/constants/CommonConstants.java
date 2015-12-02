/**
 * 
 */
package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommonConstants.
 *
 * @author mohammal
 */
public interface CommonConstants {
	
	/** The international expence type. */
	char INTERNATIONAL_EXPENCE_TYPE = 'I';
	
	/** The domestic expence type. */
	char DOMESTIC_EXPENCE_TYPE = 'D';
	
	/** The international cn number index. */
	char INTERNATIONAL_CN_NUMBER_INDEX = 'N';
	
	/** The place holder max size. */
	int PLACE_HOLDER_MAX_SIZE = 4;
	
	/** The error message. */
	String ERROR_MESSAGE = "error";
	
	/** The warning message. */
	String WARNING_MESSAGE = "warning";
	
	/** The info message. */
	String INFO_MESSAGE = "info";
	
	/** The dd mm yyyy format. */
	String DD_MM_YYYY_FORMAT = "dd-MM-yyyy";
	
	/** The min lavel. */
	int MIN_LAVEL = 1;
	
	/** The max level. */
	int MAX_LEVEL = 7;
	
	/** The consignment number length. */
	int CONSIGNMENT_NUMBER_LENGTH = 8;//FIXME Remove this constant if already defined in any constant file.
	
	/** The rc loc mis match feature enable. */
	String RC_LOC_MIS_MATCH_FEATURE_ENABLE = "Y";
	
	/** The response format. */
	String RESPONSE_FORMAT = "text/xml";
	
	/** The content type. */
	String CONTENT_TYPE = "application/pdf";
	
	/** The content disposition. */
	String CONTENT_DISPOSITION = "Content-Disposition";
	
	/** The report title. */
	String REPORT_TITLE = "reportTitle";
	
	/** The std customer type. */
	String STD_CUSTOMER_TYPE= "Customer";
	
	/** The std product type. */
	String STD_PRODUCT_TYPE= "Product";
	
	/** The std handover type. */
	String STD_HANDOVER_TYPE= "HandOver";
	
	/** The std relationship type. */
	String STD_RELATIONSHIP_TYPE= "Relationship";
	
	/** The user. */
	String USER = "user";
	
	/** The user desc. */
	String USER_DESC = "userDesc";
	/*
	 * These Key will be used to pick a service and have mapped with a bean<service name> id 
	 * inside dtdc-service.xml file of spring application context. These key must be unique.
	 *  
	 */
	/** The franchisee rate calculation type key. */
	String FRANCHISEE_RATE_CALCULATION_TYPE_KEY = "F";
	
	/** The direct party rate calculation type key. */
	String DIRECT_PARTY_RATE_CALCULATION_TYPE_KEY = "D";
	
	/** The cash booking rate calculation type key. */
	String CASH_BOOKING_RATE_CALCULATION_TYPE_KEY = "C";
	
	/** The vas charge calculation query. */
	String VAS_CHARGE_CALCULATION_QUERY = "vasChargeCalculationQuery";
	
	/** The franchisee query by fr code. */
	String FRANCHISEE_QUERY_BY_FR_CODE = "frDetailByFrCode";
	
	/** The booking query by booking number. */
	String BOOKING_QUERY_BY_BOOKING_NUMBER = "bookingDetailByBookingCode";
	
	/** The location mis match query. */
	String LOCATION_MIS_MATCH_QUERY = "locationMisMatchChargeQuery";
	
	/** The location mis match param. */
	String LOCATION_MIS_MATCH_PARAM = "customerId,regionId,locationType,bookingDate";
	
	/** The transhipment type by locations query. */
	String TRANSHIPMENT_TYPE_BY_LOCATIONS_QUERY= "getTranshTypeBySourceDestination";
	
	/** The transhipment type by locations param. */
	String TRANSHIPMENT_TYPE_BY_LOCATIONS_PARAM= "sourecCityId,destCityId";
	
	/** The direct party customer type. */
	String DIRECT_PARTY_CUSTOMER_TYPE = "DR";
	
	/** The franchisee customer type. */
	String FRANCHISEE_CUSTOMER_TYPE = "FR";
	
	/** The get franchisee office query. */
	String GET_FRANCHISEE_OFFICE_QUERY = "getFrOfficeByFrIdOrFrCode";
	
	/** The get franchisee office param. */
	String GET_FRANCHISEE_OFFICE_PARAM = "frId,frCode";
	
	/** The get booking office query. */
	String GET_BOOKING_OFFICE_QUERY = "getBookingOfficeByBookingId";
	
	/** The get booking office param. */
	String GET_BOOKING_OFFICE_PARAM = "bookingId";
	
	
	/** The get rc param. */
	String GET_RC_PARAM = "franchiseeId,serviceId,modeId,franchiseeScope,transshipmentId," +
			"sourceRegionCode,destZoneCode,destStateCode,destCityCode,rateType,bookingDate";
	
	/** The GE t_ r c_ lave l_1_ query. */
	String GET_RC_LAVEL_1_QUERY = "getStanderdRateLavel1Query";
	
	/** The GE t_ r c_ lave l_2_ query. */
	String GET_RC_LAVEL_2_QUERY = "getStanderdRateLavel2Query";
	
	/** The GE t_ r c_ lave l_3_ query. */
	String GET_RC_LAVEL_3_QUERY = "getStanderdRateLavel3Query";
	
	/** The GE t_ r c_ lave l_4_ query. */
	String GET_RC_LAVEL_4_QUERY = "getStanderdRateLavel4Query";
	
	/** The GE t_ r c_ lave l_5_ query. */
	String GET_RC_LAVEL_5_QUERY = "getStanderdRateLavel5Query";
	
	/** The GE t_ r c_ lave l_6_ query. */
	String GET_RC_LAVEL_6_QUERY = "getStanderdRateLavel6Query";
	
	/** The GE t_ r c_ lave l_7_ query. */
	String GET_RC_LAVEL_7_QUERY = "getStanderdRateLavel7Query";
	
	/** The regional office type. */
	String REGIONAL_OFFICE_TYPE = "RO";
	
	/** The branch office type. */
	String BRANCH_OFFICE_TYPE = "BO";
	
	/** The ccc office type. */
	String CCC_OFFICE_TYPE = "CCC";
	
	//Search for direct party rate calculation
	/** The city wise search. */
	int CITY_WISE_SEARCH = 0;
	
	/** The state wise search. */
	int STATE_WISE_SEARCH = 1;
	
	/** The zone wise search. */
	int ZONE_WISE_SEARCH = 2;
	
	/** The all zone wise search. */
	int ALL_ZONE_WISE_SEARCH = 3;
	
	//Inner search for all four <city,state,zone and all zone> options for rate calculation.
	/** The mod transshipment search. */
	int MOD_TRANSSHIPMENT_SEARCH = 0;
	
	/** The doc transshipment search. */
	int DOC_TRANSSHIPMENT_SEARCH = 1;
	
	/** The doc mod search. */
	int DOC_MOD_SEARCH = 2;
	
	/** The none type search. */
	int NONE_TYPE_SEARCH = 3;
	
	/** The only mod search. */
	int ONLY_MOD_SEARCH = 4;
	
	/** The only transshipment search. */
	int ONLY_TRANSSHIPMENT_SEARCH = 5;
	
	/** The only doc search. */
	int ONLY_DOC_SEARCH = 6;
	
	//Rate calculation queries
	/** The direct party rc common query. */
	String DIRECT_PARTY_RC_COMMON_QUERY = 
		"FROM RateDO rate " +
		"WHERE (:bookingDate between rate.fromDate and rate.toDate) " + 
		"and rate.rateType=:rateType " +
       	"and rate.sourceRegionId=:sourceRegionId " +
        "and rate.serviceid=:serviceId " +
        "and rate.customerId=:customerId ";
	
	/** The direct party rc common query param. */
	String DIRECT_PARTY_RC_COMMON_QUERY_PARAM = "bookingDate,rateType,sourceRegionId,serviceId,customerId";
	
	//For city wise search
	/** The direct party rc city and clause. */
	String DIRECT_PARTY_RC_CITY_AND_CLAUSE = 
		"and rate.destCityId=:destCityId " +
		"and rate.destStateId=:destStateId " +
		"and rate.destZoneId=:destZoneId ";
	
	/** The direct party rc city and clause param. */
	String DIRECT_PARTY_RC_CITY_AND_CLAUSE_PARAM = "destCityId,destStateId,destZoneId";
	
	//For state wise search
	/** The direct party rc state and clause. */
	String DIRECT_PARTY_RC_STATE_AND_CLAUSE = 
		"and rate.destStateId=:destStateId " +
		"and rate.destZoneId=:destZoneId ";
	
	/** The direct party rc state and clause param. */
	String DIRECT_PARTY_RC_STATE_AND_CLAUSE_PARAM = "destStateId,destZoneId";
	
	//Zone wise search
	/** The direct party rc zone and clause. */
	String DIRECT_PARTY_RC_ZONE_AND_CLAUSE = 
		"and rate.destZoneId=:destZoneId ";
	
	/** The direct party rc zone and clause param. */
	String DIRECT_PARTY_RC_ZONE_AND_CLAUSE_PARAM = "destZoneId";
	
	//For all types of doc, mod and trasshipment
	/** The direct party rc all type and clause. */
	String DIRECT_PARTY_RC_ALL_TYPE_AND_CLAUSE = 
		"and rate.transshipmentId=:transshipmentId " +
		"and rate.mode.modeId=:modeId " +
		"and rate.docType=:docType ";
	
	/** The direct party rc all type and clause param. */
	String DIRECT_PARTY_RC_ALL_TYPE_AND_CLAUSE_PARAM = "transshipmentId,modeId,docType";
	
	//For mod and trasshipment tyoes
	/** The direct party rc mode trans type and clause. */
	String DIRECT_PARTY_RC_MODE_TRANS_TYPE_AND_CLAUSE = 
		"and rate.transshipmentId=:transshipmentId " +
		"and rate.mode.modeId=:modeId ";
	
	/** The direct party rc mode trans type and clause param. */
	String DIRECT_PARTY_RC_MODE_TRANS_TYPE_AND_CLAUSE_PARAM = "transshipmentId,modeId";
	
	//For doc and trasshipment tyoes
	/** The direct party rc doc trans type and clause. */
	String DIRECT_PARTY_RC_DOC_TRANS_TYPE_AND_CLAUSE = 
		"and rate.transshipmentId=:transshipmentId " 
		+ "and rate.docType=:docType ";
	
	/** The direct party rc doc trans type and clause param. */
	String DIRECT_PARTY_RC_DOC_TRANS_TYPE_AND_CLAUSE_PARAM = "transshipmentId,docType";
	
	//For mod and doc tyoes
	/** The direct party rc mod doc type and clause. */
	String DIRECT_PARTY_RC_MOD_DOC_TYPE_AND_CLAUSE = 
		"and rate.mode.modeId=:modeId " +
		"and rate.docType=:docType ";
	
	/** The direct party rc mod doc type and clause param. */
	String DIRECT_PARTY_RC_MOD_DOC_TYPE_AND_CLAUSE_PARAM = "modeId,docType";
	
	//For mod tyoes
	/** The direct party rc only mod type and clause. */
	String DIRECT_PARTY_RC_ONLY_MOD_TYPE_AND_CLAUSE = 
		"and rate.mode.modeId=:modeId ";
	
	/** The direct party rc only mod type and clause param. */
	String DIRECT_PARTY_RC_ONLY_MOD_TYPE_AND_CLAUSE_PARAM = "modeId";
	
	//For trasshipment tyoes
	/** The direct party rc only trans type and clause. */
	String DIRECT_PARTY_RC_ONLY_TRANS_TYPE_AND_CLAUSE = 
		"and rate.transshipmentId=:transshipmentId "; 
	
	/** The direct party rc only trans type and clause param. */
	String DIRECT_PARTY_RC_ONLY_TRANS_TYPE_AND_CLAUSE_PARAM = "transshipmentId";
	
	//Added for doc type
	/** The direct party rc only doc type and clause. */
	String DIRECT_PARTY_RC_ONLY_DOC_TYPE_AND_CLAUSE = "and rate.docType=:docType ";
	
	/** The direct party rc only doc type and clause param. */
	String DIRECT_PARTY_RC_ONLY_DOC_TYPE_AND_CLAUSE_PARAM = "docType";
	
	/** The Constant MANIEFST_STD_TYPE_NAME. */
	public static final String MANIEFST_STD_TYPE_NAME="PRODUCT_MANIFEST";
	
	/** The get ro by bo query. */
	String GET_RO_BY_BO_QUERY = "getRegionalOfficeByBranchOffice";
	
	/** The get ro by bo param. */
	String GET_RO_BY_BO_PARAM = "officeId";
	
	/** The employee id. */
	String EMPLOYEE_ID="employeeId";
	
	/** The employee code. */
	String EMPLOYEE_CODE="empCode";
	
	/** The empty string. */
	String EMPTY_STRING="";
	
	/** The manifest x series string. */
	String MANIFEST_X_SERIES_STRING="X";
	
	/** The manifest y series string. */
	String MANIFEST_Y_SERIES_STRING="Y";
	
	/** The consignment d series. */
	String CONSIGNMENT_D_SERIES="D";
	
	/** The consignment e series. */
	String CONSIGNMENT_E_SERIES="E";
	
	/** The consignment v series. */
	String CONSIGNMENT_V_SERIES="V";
	
	/** The consignment w series. */
	String CONSIGNMENT_W_SERIES="W";
	
	/** The reason id. */
	String REASON_ID = "reasonId";
	
	/** The reason code. */
	String REASON_CODE = "reasonCode";
	
	/** The reason type. */
	String REASON_TYPE = "reasonType";
	
	/** The customer id. */
	String CUSTOMER_ID="customerId";
	
	/** The customer code. */
	String CUSTOMER_CODE="customerCode";
	
	/** The get franchisee by code. */
	String GET_FRANCHISEE_BY_CODE="getFranchiseeByFrCode";
	
	/** The get franchisee by code param. */
	String GET_FRANCHISEE_BY_CODE_PARAM="frCode";
	
	/** The Constant DELIVERY_STATUS. */
	public static final String DELIVERY_STATUS = "DELIVERY_STATUS";
	
	/** The Constant REGIONAL_OFFICE. */
	public static final String REGIONAL_OFFICE = "Regional Office";
	
	/** The Constant BRANCH_OFFICE. */
	public static final String BRANCH_OFFICE = "Branch Office";
	
	/** The Constant CORPORATE_OFFICE. */
	public static final String CORPORATE_OFFICE = "Corporate Office";
	
	/** The Constant RO. */
	public static final String RO = "RO";
	
	/** The Constant CO. */
	public static final String CO = "CO";
	
	/** The Constant BO. */
	public static final String BO = "BO";
	
	/** The query get types by name. */
	String QUERY_GET_TYPES_BY_NAME="getTypesByTypeName";
	
	/** The param type name. */
	String PARAM_TYPE_NAME="typeName";
	
	/** The Constant DELIVERY_STATUS_OUT_OF_DELIVERY. */
	public static final String DELIVERY_STATUS_OUT_OF_DELIVERY="O";
	
	/** The Constant DELIVERY_STATUSES. */
	public static final String[] DELIVERY_STATUSES={"O","D","P","C"};	
	
	/** The Constant DELIVERY_STATUSES_NONDELIVERY. */
	public static final String[] DELIVERY_STATUSES_NONDELIVERY={"N"};	
	
	/** The Constant DELIVERY_STATUS_DELIVERY. */
	public static final String DELIVERY_STATUS_DELIVERY="D";
	
	/** The Constant DELIVERY_STATUS_NON_DELIVERY. */
	public static final String DELIVERY_STATUS_NON_DELIVERY="N";
	
	/** The Constant COMMA. */
	public static final String COMMA=",";
	
	/** The Constant ERROR_PAGE. */
	public static final String ERROR_PAGE="errorpage";
	
    /** The Constant OFF_TYPE. */
    public static final String OFF_TYPE= "OFFICE_TYPE";
	
	/** The Constant STD_TYPE_DO_LIST. */
	public static final String STD_TYPE_DO_LIST = "standaradTypeToList";
	
	/** The get booking details query. */
	String GET_BOOKING_DETAILS_QUERY = "getExpenceBookingDetails";
	
	/** The get delivery details query. */
	String GET_DELIVERY_DETAILS_QUERY = "getExpenceDeliveryDetails";
	
	/** The get all expences by cn query. */
	String GET_ALL_EXPENCES_BY_CN_QUERY = "getAllExpensesByCn";
	
	/** The get expence details param. */
	String GET_EXPENCE_DETAILS_PARAM = "cnNumber";
	
	
	/** The get all expenditure types query. */
	String GET_ALL_EXPENDITURE_TYPES_QUERY = "getAllExpenseTypes";
	
	/** The get all expenditure types param. */
	String GET_ALL_EXPENDITURE_TYPES_PARAM = "location";
	
	/** The get booking by cn query. */
	String GET_BOOKING_BY_CN_QUERY = "getDetailsForConsignmentNO";
	
	/** The get booking by cn param. */
	String GET_BOOKING_BY_CN_PARAM = "cnNo";
	
	/** The get std types by parent type and type name query. */
	String GET_STD_TYPES_BY_PARENT_TYPE_AND_TYPE_NAME_QUERY = "getStdTypesByParentAndTypeName";
	
	/** The get std types by parent type and type name param. */
	String GET_STD_TYPES_BY_PARENT_TYPE_AND_TYPE_NAME_PARAM = "parentTypeName,typeName";
	
	/** The standard parent type tracking. */
	String STANDARD_PARENT_TYPE_TRACKING = "Tracking";
	
	/** The standard type file tracking. */
	String STANDARD_TYPE_FILE_TRACKING = "File";
	
	/** The standard type process tracking. */
	String STANDARD_TYPE_PROCESS_TRACKING = "Process";
	
	/** The standard type report tracking. */
	String STANDARD_TYPE_REPORT_TRACKING = "Report";
	
	/** The standard type query tracking. */
	String STANDARD_TYPE_QUERY_TRACKING = "Query";
	
	/** The Constant GET_APPROVERS. */
	public static final String GET_APPROVERS = "getApproversByApprovalNameAndOff";	
	
	/** The get manifest type query. */
	String GET_MANIFEST_TYPE_QUERY = "getManifestType";
	
	/** The get manifest type query param. */
	String GET_MANIFEST_TYPE_QUERY_PARAM = "mnfstCode";
	
	/** The get all manifest type query. */
	String GET_ALL_MANIFEST_TYPE_QUERY = "getManifestTypeNames";
	
	/** The auto complete key separator. */
	String AUTO_COMPLETE_KEY_SEPARATOR = "~";
	
	/** The get offices by office type query. */
	String GET_OFFICES_BY_OFFICE_TYPE_QUERY = "getOfficesByOfficeType";
	
	/** The get offices by office type query param. */
	String GET_OFFICES_BY_OFFICE_TYPE_QUERY_PARAM = "officeType";
	
	/** The get ro detail by ro code query. */
	String GET_RO_DETAIL_BY_RO_CODE_QUERY = "getRegionalOfficeByOfficeCode";
	
	/** The get ro detail by ro code query param. */
	String GET_RO_DETAIL_BY_RO_CODE_QUERY_PARAM = "officeCode";
	
	/** The get all bo by ro id query. */
	String GET_ALL_BO_BY_RO_ID_QUERY = "getBranchOfficesByROId";
	
	/** The get all bo by ro id query param. */
	String GET_ALL_BO_BY_RO_ID_QUERY_PARAM = "roId";
	
	/** The consgn status rto. */
	String CONSGN_STATUS_RTO="R";
	
	/** The franchisee agent. */
	String FRANCHISEE_AGENT="DLV";
	
	/** The AUT o_ complet e_ ke y_ separato r1. */
	String AUTO_COMPLETE_KEY_SEPARATOR1 = " - ";
	
	/** The get weight recon param query. */
	String GET_WEIGHT_RECON_PARAM_QUERY = "reconWtCalculationQuery";
	
	/** The get weight recon param params. */
	String GET_WEIGHT_RECON_PARAM_PARAMS = "consgSeries,customerId,officeId,productType,reconWt";
	
	/** The get service query. */
	String GET_SERVICE_QUERY = "getServiceByCode";
	
	/** The get service param. */
	String GET_SERVICE_PARAM = "serviceCode";
	
	/** The get fr customer query. */
	String GET_FR_CUSTOMER_QUERY = "getCustomerDetails";
	
	/** The get fr customer param. */
	String GET_FR_CUSTOMER_PARAM = "customerCode";
	
	/** The get product type series query. */
	String GET_PRODUCT_TYPE_SERIES_QUERY = "getProductTypeSeriese";
	
	/** The get product type series param. */
	String GET_PRODUCT_TYPE_SERIES_PARAM = "productType";
	
	/** The get offices by office type roid query. */
	String GET_OFFICES_BY_OFFICE_TYPE_ROID_QUERY = "getOfficesByOfficeTypeByROId";
	
	/** The get offtype roid. */
	String GET_OFFTYPE_ROID = "officeType,roOffId";
	
	/** The get cash booking rc query. */
	String GET_CASH_BOOKING_RC_QUERY = "getCashBookingRateCalculationQuery";
	
	/** The get cash booking rc params. */
	String GET_CASH_BOOKING_RC_PARAMS = "modeId,transshipmentId,officeCode,rateType,bookingDate";
	
	/** The consgn billing status sap intg. */
	String CONSGN_BILLING_STATUS_SAP_INTG="C";
	
	/** The get cities by country query. */
	String GET_CITIES_BY_COUNTRY_QUERY = "getCitiesByCountry";
	
	/** The get cities by country query param. */
	String GET_CITIES_BY_COUNTRY_QUERY_PARAM = "countryCode";
	
	/** The cod. */
	String COD = "COD";
	
	/** The fod. */
	String FOD = "FOD";
	
	/** The cfd. */
	String CFD = "CFD";
	
	/** The consg active status. */
	String CONSG_ACTIVE_STATUS = "A";
	
	/** The consg in active status. */
	String CONSG_IN_ACTIVE_STATUS = "I";
	
	/** The consg status. */
	String CONSG_STATUS = "status";
	
	/** The remote serviceable. */
	String REMOTE_SERVICEABLE = "REMOTE";
	
	/** The diplomatic serviceable. */
	String DIPLOMATIC_SERVICEABLE = "DIPLOMATIC";
	
	/** The pincode type nonserviceable. */
	String PINCODE_TYPE_NONSERVICEABLE = "NONSERVICEABLE";
	
	/** The pincode type serviceable. */
	String PINCODE_TYPE_SERVICEABLE = "SERVICEABLE";
	
	/** The error. */
	String ERROR = "ERROR";
	
	/** The valid message. */
	String VALID_MESSAGE = "VALID";
	
	/** The pincode serviceable. */
	String PINCODE_SERVICEABLE = "Y";
	
	/** The pincode non serviceable. */
	String PINCODE_NON_SERVICEABLE = "N";
	
	/** The hash. */
	String HASH = "#";
	
	/** The Constant DELIVERY_PROCESS. */
	public static final String[] DELIVERY_PROCESS={"BDM","FDM"};	
	
	/** The Constant PRODUCT_CATEGORY_DOMESTIC. */
	public static final String PRODUCT_CATEGORY_DOMESTIC="DOM";
	
	/** The Constant PRODUCT_CATEGORY. */
	public static final String PRODUCT_CATEGORY="prodCategory";
	
	/** The get regional office by emp id. */
	String GET_REGIONAL_OFFICE_BY_EMP_ID ="getRegionalOfficeByEmployeeId";
	
	/** The get branchcode servicetype. */
	String GET_BRANCHCODE_SERVICETYPE = "branchCode,serviceType";
	
	/** The consignment number. */
	String CONSIGNMENT_NUMBER = "consignmentNumber";
	
	/** The misc expense. */
	String MISC_EXPENSE = "miscExpense";
	
	/** The drs misc expnse map. */
	String DRS_MISC_EXPNSE_MAP="drsMiscExpense";
	
	/** The get all existing vouchers query. */
	String GET_ALL_EXISTING_VOUCHERS_QUERY = "getAllExistingVoucher";
	
	/** The get all existing vouchers param. */
	String GET_ALL_EXISTING_VOUCHERS_PARAM = "voucherNumber";
	
	/** The invalid pincode. */
	String INVALID_PINCODE = "Invalide Pincode";
	
	/** The branch. */
	String BRANCH = "BRANCH";
	
	/** The updated booked weight. */
	String UPDATED_BOOKED_WEIGHT = "Y";
	
	/** The pincode. */
	String PINCODE = "pincode";
	
	/** The Constant CN_DELIVERY_STATUS. */
	public static final String CN_DELIVERY_STATUS = "D";
	
	/** The Constant CN_NOT_DELIVERY_STATUS. */
	public static final String CN_NOT_DELIVERY_STATUS = "N";
	
	/** The Constant CN_RTOED_STATUS. */
	public static final String CN_RTOED_STATUS = "C";
	
	/** The Constant CN_MANIFEST_STATUS. */
	public static final String CN_MANIFEST_STATUS = "P";
	
	/** The Constant CN_OUT_OF_DELIVERY. */
	public static final String CN_OUT_OF_DELIVERY = "O";
	
    /** The Constant GET_DISPATCH_DETAILS. */
    public static final String GET_DISPATCH_DETAILS = "getDispatchDetaislByManifestNumber";
	
	/** The Constant GET_MANIFEST_BY_NO_TYPE. */
	public static final String GET_MANIFEST_BY_NO_TYPE = "getManifestByNoTypeId";
	
	/** The Constant NO_OUTGOING_MANIFEST. */
	public static final String NO_OUTGOING_MANIFEST = "There is no outgoing details for this manifest";
	
	/** The Constant MANIFEST_ALLREADY_EXISTS. */
	public static final String MANIFEST_ALLREADY_EXISTS = "There is a manifest number already exist with this number";
	
	/** The Constant HO. */
	public static final String HO = "HO";
	
	/** The Constant SF. */
	public static final String SF = "SF";
	
	/** The Constant MF. */
	public static final String MF = "MF";
	
	/** The Constant SB. */
	public static final String SB = "SB";
	
	/** The Constant SO. */
	public static final String SO = "SO";
	
	/** The Constant OS. */
	public static final String OS = "OS";
	
	/** The Constant ZO. */
	public static final String ZO = "ZO";
	
	/** The Constant HEAD_OFFICE. */
	public static final String HEAD_OFFICE = "Head Office";
	
	/** The Constant SUPER_FRANCHISEE. */
	public static final String SUPER_FRANCHISEE = "Super Franchisee";
	
	/** The Constant MASTER_FRANCHISEE. */
	public static final String MASTER_FRANCHISEE = "Master Franchisee";
	
	/** The Constant SUB_BRANCH. */
	public static final String SUB_BRANCH = "Sub Branch";
	
	/** The Constant SORTING_OFFICE. */
	public static final String SORTING_OFFICE = "Sorting Office";
	
	/** The Constant OUTSOURCED. */
	public static final String OUTSOURCED = "Outsourced";
	
	/** The Constant ZONAL_OFFICE. */
	public static final String ZONAL_OFFICE = "Zonal Office";
	
	/** The Constant GET_DISTINCT_OFFICE_TYPES. */
	public static final String GET_DISTINCT_OFFICE_TYPES = "getDistinctOfficeTypes";
	
	/** The Constant VIA_OFFICE_TYPE. */
	public static final String VIA_OFFICE_TYPE = "VIA_OFFICE_TYPE";
	
	/** The Constant GET_DISPATCH_DETAILS_BY_MANIFEST_NO_TYPE. */
	public static final String GET_DISPATCH_DETAILS_BY_MANIFEST_NO_TYPE = "getDispatchDetaislByManifestNumberAndType";
	
	/** The Constant MISC_EXPENSE_SERVICE. */
	public static final String MISC_EXPENSE_SERVICE = "MiscExpenseService";
	
	/** The Constant UPDATE_EXPENSES. */
	public static final String UPDATE_EXPENSES = "updateExpenses";
	
	/** The Constant SAVE_MISC_EXPENSES. */
	public static final String SAVE_MISC_EXPENSES = "saveMiscExpenses";
	
	/** The misc expense excel header. */
	String MISC_EXPENSE_EXCEL_HEADER = "Sr. No., Exp. Type, Exp. Date, Exp. Amount (Rs), Vouher No., Voucher Date, Authorized By, Entered By\n";
	
	/** The Constant POD_SERVICE_IMPL. */
	public static final String POD_SERVICE_IMPL = "PODServiceImpl";
	
	/** The Constant SAVE_POD. */
	public static final String SAVE_POD = "savePOD";
	
	/** The Constant UPDATE_POD. */
	public static final String UPDATE_POD = "updatePOD";
	
	/** The success msg. */
	String SUCCESS_MSG = "SUCCESS";
	
	/** The manifest type against incoming. */
	String MANIFEST_TYPE_AGAINST_INCOMING = "I";
	
	/** The manifest type against outgoing. */
	String MANIFEST_TYPE_AGAINST_OUTGOING = "O";
	
	/** The pod delivery status. */
	String POD_DELIVERY_STATUS = "D";
	
	/** The read by local flag y. */
	String READ_BY_LOCAL_FLAG_Y = "Y";
}
