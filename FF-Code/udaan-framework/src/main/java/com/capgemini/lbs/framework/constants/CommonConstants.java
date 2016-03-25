package com.capgemini.lbs.framework.constants;

/**
 * @author anwar
 * 
 */
public interface CommonConstants {

	String EMOTIONAL_BOND_BOOKING_NO = "EMOTIONAL_BOND_BOOKING_NO";
	String GEN_MISC_EXP_TXN_NO = "GEN_MISC_EXP_TXN_NO";
	String GEN_MISC_COLL_TXN_NO = "GEN_MISC_COLL_TXN_NO";
	String GEN_MISC_CONSG_COLL_TXN_NO = "GEN_MISC_CONSG_COLL_TXN_NO";
	String GEN_LIABILITY_TXN_NO = "GEN_LIABILITY_TXN_NO";

	String CONFIGURABLE_PARAM_QUERRY = "getConfigurableParmByName";
	String PARAM_NAME = "paramName";
	String HYPHEN = "-";
	String SLASH_CONST = "/";
	String UNDERSCORE = "_";
	/** The character new line. */
	String CHARACTER_NEW_LINE = "\n";
	String EMPTY_STRING = "";
	String SPACE = " ";
	Integer ZERO = 0;
	Integer EMPTY_INTEGER = -1;
	Integer ONE_INTEGER = 1;
	String YES = "Y";
	String NO = "N";
	String COMMA = ",";
	String HASH = "#";
	String QRY_GET_SEQUENCE_BY_PROCESS = "getSequenceByProcess";
	String QRY_UPDATE_SEQ_BY_GENERATOR_ID = "updateSeqByGeneratorId";
	String QRY_PARAM_PROCESS_TYPE = "processType";
	String QRY_PARAM_LAST_GENERATED_NUMBER = "lastGeneratedNumber";
	String QRY_PARAM_SEQUENCE_GENERATOR_ID = "sequenceGeneratorId";
	String QRY_PARAM_LAST_GENERATED_DATE = "lastGeneratedDate";

	String PICKUP_PROCESS_TYPE = "PICKUP";
	String GENERATE_PICKUP_RUN_SHEET_NO = "GEN_PICKUP_RUN_SHEET_NO";
	String GENERATE_PICKUP_ORDER_NUMBER = "GEN_PICKUP_ORDER_NO";
	/** The TILD. */
	String TILD = "~";
	/** The OPENIN g_ curl y_ brace. */
	String OPENING_CURLY_BRACE = "{";

	/** The CLOSIN g_ curl y_ brace. */
	String CLOSING_CURLY_BRACE = "}";

	/** The OPENIN g_ round_ brace. */
	String OPENING_ROUND_BRACE = "(";

	/** The CLOSIN g_ round__ brace. */
	String CLOSING_ROUND_BRACE = ")";

	/** The OPENIN g_ inne r_ qoutes. */
	String OPENING_INNER_QOUTES = "\"";
	/** The CLOSIN g_ inne r_ qoutes. */
	String CLOSING_INNER_QOUTES = "\"";
	
	String OPENING_SQUARE_BRACE = "[";
	String CLOSING_SQUARE_BRACE = "]";
	
	/** The CHARACTE r_ colon. */
	String CHARACTER_R = "R";

	String CHARACTER_COLON = ":";
	String CHARACTER_SEMI_COLON = ";";
	int PLACE_HOLDER_MAX_SIZE = 4;

	String ERROR_MESSAGE = FrameworkConstants.ERROR_MESSAGE;
	String WARNING_MESSAGE = FrameworkConstants.WARNING_MESSAGE;
	String INFO_MESSAGE = FrameworkConstants.INFO_MESSAGE;

	String SUCCESS = "SUCCESS";
	String FAILURE = "FAILURE";
	String CLOSE = "CLOSE";
	// Global Office Type
	String OFF_TYPE_REGION_HEAD_OFFICE = "RO";
	String OFF_TYPE_HUB_OFFICE = "HO";
	String OFF_TYPE_BRANCH_OFFICE = "BO";
	String OFF_TYPE = "OFFICE";

	String OFF_TYPE_CORP_OFFICE = "CO";

	// Tracking type

	String ORIGIN = "O";
	String DESTINATION = "D";
	String TRANSHIPMENT = "T";

	String DIRECTION_RECEIVE = "R";
	String DIRECTION_OUT = "O";
	String DIRECTION_IN = "I";

	// PROCESS CODES
	String PROCESS_BOOKING = "BOOK";
	String PROCESS_PICKUP = "UPPU";
	String PROCESS_OUT_MANIFEST_PKT_DOX = "OPKT";
	String PROCESS_OUT_MANIFEST_BAG_DOX = "OBDX";
	String PROCESS_OUT_MANIFEST_BAG_PARCEL = "OBPC";
	String PROCESS_OUT_MANIFEST_MATER_BAG = "OMBG";
	String PROCESS_DISPATCH = "DSPT";
	String PROCESS_HELD_UP = "HLDP";
	String PROCESS_RECEIVE = "RCVE";
	String PROCESS_IN_MANIFEST_MASTER_BAG = "IMBG";
	String PROCESS_IN_MANIFEST_BAG_PARCEL = "IBPC";
	String PROCESS_IN_MANIFEST_DOX = "IBDX";
	String PROCESS_IN_MANIFEST_PKT_DOX = "IPKT";
	String PROCESS_RTO_RTH = "RTOH";
	String PROCESS_POD = "PRDL";
	String PROCESS_DELIVERY_RUN_SHEET = "DLRS";
	String PROCESS_MIS_ROUTE = "MSRT";
	String PROCESS_BRANCH_IN_MANIFEST = "BRIN";
	String PROCESS_BRANCH_OUT_MANIFEST = "BOUT";
	String CONSIGNMENT_TYPE_DOCUMENT = "DOX";
	String CONSIGNMENT_TYPE_PARCEL = "PPX";
	String CONSIGNMENT_TYPE_DOCUMENT_CODE = "DOX";
	String CONSIGNMENT_TYPE_PARCEL_CODE = "PPX";
	String PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX = "TPDX";
	String PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL = "TPBP";
	String PROCESS_MEC = "MEC";
	String PROCESS_STOP_DELIVERY = "SDLV";

	String CO_MAIL_DEFAULT_VALUE = "N";

	// PRODUCTS CODES
	String NORMAL_CREDIT = "PC000013";
	String NO_RESULTS = "NO_RESULTS";
	String ALL = "ALL";

	// PRODUCTS - CONSIGNMENT SERIES
	String PRODUCT_SERIES_CASH_COD = "L";
	String PRODUCT_SERIES_TO_PAY_PARTY_COD = "T";
	String PRODUCT_SERIES_CASH = "M";
	String PRODUCT_SERIES_BA = "B";
	String PRODUCT_SERIES_PRIORITY = "P";
	String PRODUCT_SERIES_AIR_CARGO = "A";
	String PRODUCT_SERIES_CREDIT_CARD = "C";
	String PRODUCT_SERIES_EB = "E";
	String PRODUCT_SERIES_TRAIN_SURFACE = "S";
	String PRODUCT_SERIES_LETTER_OF_CREDIT = "D";
	String PRODUCT_SERIES_QUALITY = "Q";
	String PRODUCT_SERIES_Z = "Z";
	String PRODUCT_SERIES_NORMALCREDIT = "N";

	String MANIFEST_TYPE_OUT = "O"; // OUT MANIFEST
	String MANIFEST_TYPE_IN = "I"; // IN MANIFEST
	String MANIFEST_TYPE_RTO = "R"; // RTO MANIFEST
	// RTH-'T' ENUM has been changed from 'H'. Since H is already in use for Hub
	// misroute as per discussion with Misroute team.
	String MANIFEST_TYPE_RTH = "T"; // RTH MANIFEST
	String MANIFEST_TYPE_HUB_MISROUTE = "H";// hub Misroute/Origin Misroute
	String MANIFEST_TYPE_BRANCH_MISROUTE = "B"; // Branch Misroute
	String MANIFEST_TYPE_POD = "P"; // POD MANIFEST

	String MANIFEST_STATUS_CODE = "C"; // Closed Manifest Status Code
	String MANIFEST_STATUS_CLOSED = "C"; // Closed Manifest Status Code

	String PARTY_TYPE_CONSIGNER = "CO";
	String PARTY_TYPE_CONSIGNEE = "CE";

	/** The logged in office id. */
	String LOGGED_IN_OFFICE_ID = "loggedInOfficeId";
	String REGION_ID = "regionId";

	String CONSIGNMENT = "C";
	String MANIFEST = "M";
	String OFFICE_POPUP = "officepopup";

	// Transport Mode Codes
	String TRANSPORT_MODE_AIR_CODE = "Air";
	String TRANSPORT_MODE_ROAD_CODE = "Road";
	String TRANSPORT_MODE_Train_CODE = "Rail";

	// Default No Of Pieces

	String DEFAULT_NO_PIECES = "1";
	String PROCESS_PICKUP_DELIVERY_LOCATION_CODE = "CONTRACT_CUST_CODE";
	// Consignment Operating Levels
	Integer CONSIGNMENT_OPERATING_AT_ORIGIN_BRANCH = 10;
	Integer CONSIGNMENT_OPERATING_AT_ORIGIN_HUB = 20;
	Integer CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_ORIGIN = 30;
	Integer CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_DEST = 70;
	Integer CONSIGNMENT_OPERATING_AT_DESTINATION_HUB = 80;
	Integer CONSIGNMENT_OPERATING_AT_DESTINATION_BRANCH = 100;
	Integer CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB = 30;
	// Customer Codes
	String CUSTOMER_CODE_WALK_IN_CASH = "WI";
	String CUSTOMER_CODE_PRIORITY_CASH = "PR";
	String CUSTOMER_CODE_TO_PAY_CASH = "TP";
	String CUSTOMER_CODE_CREDIT = "CR";
	String CUSTOMER_CODE_CREDIT_CARD = "CC";
	String CUSTOMER_CODE_REVERSE_LOGISTICS = "RL";
	String CUSTOMER_CODE_COD = "CD";
	String CUSTOMER_CODE_LC = "LC";
	String CUSTOMER_CODE_BA_PICKUP = "BA";
	String CUSTOMER_CODE_BA_DELIVERY = "BV";
	String CUSTOMER_CODE_FRANCHISEE = "FR";
	String CUSTOMER_CODE_ACC = "AC";
	String CUSTOMER_CODE_INTERNAL = "IC";
	String CUSTOMER_CODE_OVERSEAS = "OC";
	String CUSTOMER_CODE_GOVT_ENTITY = "GV";
	String CONSIGNMENT_TYPE_PARCEL_NAME = "Parcel";
	String CONSIGNMENT_TYPE_DOCUMENT_NAME = "Document";

	// BILLING_STATUS
	String BILLING_STATUS_TBB = "TBB";
	String BILLING_STATUS_RTB = "RTB";
	String BILLING_STATUS_BLD = "BLD";
	String BILLING_STATUS_TRB = "TRB";
	String BILLING_STATUS_PFB = "PFB";
	String DT_FROM_OPSMAN_R = "R";
	String DT_TO_CENTRAL_R = "R";

	// For Pick up
	String PICKUP_TYPE_PICK_UP = "P";
	String PICKUP_TYPE_DELIVERY = "D";

	// Payment Mode codes
	String PAYMENT_MODE_CODE_CASH = "CA";
	String PAYMENT_MODE_CODE_DD = "DD";
	String PAYMENT_MODE_CODE_CHEQUE = "CHQ";
	String PAYMENT_MODE_CODE_PRIVILIGE_CARD = "PVC";

	// Insured By Code
	String INSURED_BY_CODE_FFCL = "F";
	String INSURED_BY_CODE_CONSIGNOR = "C";
	String INSURED_BY_DESC_CONSIGNOR = "CONSIGNOR";

	// CONSIGNMENT STATUS
	String CONSIGNMENT_STATUS_RTOH = "R";
	String CONSIGNMENT_STATUS_RTH = "H";
	String CONSIGNMENT_STATUS_RTO_DRS = "S";
	String CONSIGNMENT_STATUS_BOOK = "B";
	String CONSIGNMENT_STATUS_STOPDELV = "X";
	String CONSIGNMENT_STATUS_DELV = "D";
	String CONSIGNMENT_STATUS_PENDING = "P";

	String GROUP1 = "GROUP 1";
	String GROUP2 = "GROUP 2";
	String GROUP3 = "GROUP 3";
	String GROUP4 = "GROUP 4";
	String GROUP5 = "GROUP 5";

	String PARAM_MANIFEST_REMARK_TYPE = "manifest";
	Object REMARKS_STATUS_ACTIVE = "A";

	// Config params

	String CONFIG_PARAM_STOCK_ISSUE_BA_EXPIRY_DAYS = "STOCK_ISSUE_BA_EXPIRY_DAYS";
	String CONFIG_PARAM_STOCK_ISSUE_FR_EXPIRY_DAYS = "STOCK_ISSUE_FR_EXPIRY_DAYS";

	// Billing Flags while updating CN and CN rate
	String UPDATE_BILLING_FLAGS_CREATE_CN = "C";
	String UPDATE_BILLING_FOR_MEC = "M";
	String UPDATE_BILLING_FLAGS_FOR_RTO = "R";
	String UPDATE_BILLING_FLAGS_UPDATE_CN = "U";
	String RATE_CALCULATED_FOR_BOOKING = "B";
	String RATE_CALCULATED_FOR_RTO = "R";
	String UPDATE_BILLING_FLAGS_ERROR_CONSIGNMENTDO_NULL = "CONSG0001";
	String UPDATE_BILLING_FLAGS_ERROR_UPDATEDIN = "CONSG0002";
	String UPDATE_BILLING_FLAGS_ERROR_CONSIGNMENTBILLINGRATEDO_NULL = "CONSG0003";
	String STATUS_INACTIVE = "I";

	// Ocrtoi Bourne By
	String OCTROI_BOURNE_BY_CONSIGNEE = "CE";
	String OCTROI_BOURNE_BY_CONSIGNOR = "CO";

	// Rate Customer Category
	String RATE_CUSTOMER_CAT_CRDT = "CRDT";
	String RATE_CUSTOMER_CAT_CASH = "CASH";
	String RATE_CUSTOMER_CAT_BA = "BA";
	String RATE_CUSTOMER_CAT_FR = "FR";
	String RATE_CUSTOMER_CAT_ACC = "ACC";
	String UNCHECKED = "unchecked";

	/** The cash booking. */
	String CASH_BOOKING = "CS";

	/** The emotional bond booking. */
	String EMOTIONAL_BOND_BOOKING = "EB";

	/** The foc booking. */
	String FOC_BOOKING = "FC";

	/** Rate Pickup/Delivery Location(s) */
	String LOCATION_TYPE_BILLING = "B";
	String LOCATION_TYPE_PAYMENT = "P";
	String LOCATION_TYPE_BILLING_PAYMENT = "BP";

	String CONSIGNMENT_STATUS_DELIVERED = "D";

	String REASON_REDIRECT_CODE = "RDT";
	String REASON_REDISPATCH_CODE = "RDP";

	String ENUM_DEFAULT_NULL = "Z";
	String SERVICE_REQUEST_FOLLOWUP_SERVICE = "serviceRequestFollowupService";

	/** The config param role sales coordinator. */
	String CONFIG_PARAM_ROLE_SALES_COORDINATOR = "EMP_ROLE_SALES_COORDINATOR";

	/** The config param role backline executive. */
	String CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE = "EMP_ROLE_BACKLINE_EXECUTIVE";
	
	/** The config param role backline executive. */
	String CONFIG_PARAM_COMPLAINT_PRODUCT = "COMPLAINT_PRODUCT_SERIES";
	
	String CONFIG_PARAM_ROLE_CSM_ROLE = "CSM_ROLE_NAME";
	String CONFIG_PARAM_ROLE_CORP_CSM_ROLE = "CORP_CSM_ROLE_NAME";
	

	/** Complaints Common Constants START */

	// The Booking No Type
	String BOOKING_NO_TYPE_CONSG_NO = "CN";
	String BOOKING_NO_TYPE_REF_NO = "RF";

	String PARAM_LOCAL = "LOCAL";

	String PARAM_CORP = "CORP";

	/** Complaints Common Constants END */

	String RECORD_STATUS_ACTIVE = "A";

	String RECORD_STATUS_INACTIVE = "I";

	// Two Way Write
	String TWO_WAY_WRITE_PROCESS_REVERSE_PICKUP_REQ = "REVERSE_PICKUP_REQ";
	String TWO_WAY_WRITE_PROCESS_RUNSHEET_ASSIGNMENT = "RUNSHEET_ASSIGNMENT";
	String TWO_WAY_WRITE_PROCESS_GENERATE_RUNSHEET = "GENERATE_RUNSHEET";
	String TWO_WAY_WRITE_PROCESS_BOOKING = "BOOKING";
	String TWO_WAY_WRITE_PROCESS_CONSIGNMENT = "CONSIGNMENT";
	String TWO_WAY_WRITE_PROCESS_MANIFEST = "MANIFEST";
	String TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS = "MANIFEST_PROCESS";
	String TWO_WAY_WRITE_PROCESS_DISPATCH_RECEIVE = "DISPATCH_RECEIVE";
	String TWO_WAY_WRITE_PROCESS_CONSIGNMENT_RETURN = "CONSIGNMENT_RETURN";
	String TWO_WAY_WRITE_PROCESS_DRS = "DRS";
	String TWO_WAY_WRITE_PROCESS_DRS_NAVIGATOR = "DRS_NAVIGATOR";
	String TWO_WAY_WRITE_PROCESS_LOGIN_PASSWORD = "LOGIN_PASSWORD";
	String TWO_WAY_WRITE_PROCESS_LOGIN_HISTORY = "LOGIN_HISTORY";

	/** The drs status open. */
	String DRS_STATUS_OPEN = "OPEN";

	/** The drs status updated. */
	String DRS_STATUS_UPDATED = "UPDATED";

	/** The drs status closed. */
	String DRS_STATUS_CLOSED = "CLOSED";

	String DELIVERY_STATUS_DELIVERED = "D";

	/** The DELIVERY status out delivery. */
	String DELIVERY_STATUS_OUT_DELIVERY = "O";

	/** The DELIVERY status PENDING/NON DELIVERED. */
	String DELIVERY_STATUS_PENDING = "P";

	String COMPAINTS_MODULE = "COMPLAINTS";
	String MODULE_NAME_DISPATCH = "DISPATCH";

	/** FFCL UDAAN support email id. */
	String FFCL_UDAAN_SUPPORT_EMAIL_ID = "FFCL_UDAAN_SUPPORT_EMAIL_ID";
	String FFCL_FROM_EMAIL_ID = "FROM_EMAIL_ID";

	/** Print Job Constant. */
	String PRINT_STR = "printStr";
	
	String queryName = "getPincodeServicingBranch";
	
	/** The rto cod drs config params series. */
	String RTO_COD_DRS_CONFIG_PARAMS_SERIES = "RTO_COD_DRS";
	String TX_CODE_BC = "BC";
	String TX_CODE_CC = "CC";
	String TX_CODE_LP = "LP";
	String TX_CODE_EX = "EX";
	String BULK_DRS_PENIDNG_NUMBER = "BLK";
	
	/** The qry param prefix. */
	String QRY_PARAM_PREFIX="prefix";
	/** The qry param number length. */
	String QRY_PARAM_NUMBER_LENGTH = "numberLength";
	
	int COLLECTION_TRANSACTION_NUMBER_LENGTH = 12;
	int COLLECTION_RUNNING_NUMBER_LENGTH = 6;
	
	String CONSIGNMENT_STATUS_UPDATED_YES=YES;
	
	String CONSIGNMENT_STATUS_UPDATED_NO=NO;
	String CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED = "CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED";
	
	String USER_TYPE_CUSTOMER = "C";
}
