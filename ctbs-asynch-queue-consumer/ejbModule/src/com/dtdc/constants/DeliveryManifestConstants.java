/*
 * @author nkattung
 */
package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface BookingConstants.
 */
public interface DeliveryManifestConstants {

	/** The delivery status. */
	String DELIVERY_STATUS = "D";
	
	/** The pod delivery status. */
	String POD_DELIVERY_STATUS = "yes";
	
	/** The default status. */
	public String DEFAULT_STATUS = "OPEN";
	
	/** The prepared status. */
	public String PREPARED_STATUS = "PREPARED";
	
	/** The br delivery type. */
	public String BR_DELIVERY_TYPE = "Out for Delivery";
	
	/** The fr delivery type. */
	public String FR_DELIVERY_TYPE = "Prepared";

	/** The invalid productcode. */
	public String INVALID_PRODUCTCODE = "Only same product type must be allowed.";

	/** The duplicate manifest. */
	public String DUPLICATE_MANIFEST = "Duplicate manifest.";

	/** The invalid weight. */
	public String INVALID_WEIGHT = "Weight does not match with booked weight.";
	
	/** The invalid noofpieces. */
	public String INVALID_NOOFPIECES = "No Of Pieces does not match with booked No Of Pieces.";
	
	/** The consg num. */
	public String CONSG_NUM = "CONSGNUM";
	
	/** The consg flag. */
	public String CONSG_FLAG = "CONSGFlag";
	
	/** The weight. */
	public String WEIGHT = "WEIGHT";
	
	/** The noofpieces. */
	public String NOOFPIECES = "NOOFPIECES";
	
	/** The open status. */
	public String OPEN_STATUS = "A";
	
	/** The delited status. */
	public String DELITED_STATUS = "I";
	
	/** The bdm delivery code. */
	public String BDM_DELIVERY_CODE = "BDM";
	
	/** The fdm delivery code. */
	public String FDM_DELIVERY_CODE = "FDM";
	
	/** The consgn status bdm. */
	public String CONSGN_STATUS_BDM = "O";
	
	/** The consgn status bdm find. */
	public String[] CONSGN_STATUS_BDM_FIND = {"O","R","N"};	
	
	/** The consgn status closed. */
	public String CONSGN_STATUS_CLOSED = "C";
	
	/** The consgn status fdm. */
	public String CONSGN_STATUS_FDM = "P";
	
	/** The consgn status return. */
	public String CONSGN_STATUS_RETURN = "R";
	
	/** The empty string. */
	public String EMPTY_STRING = "";
	
	/** The empty integer. */
	public Integer EMPTY_INTEGER = 0;
	
	/** The fr delivery to. */
	public String FR_DELIVERY_TO = "frDeliveryTO";
	
	/** The fr delivery. */
	public String FR_DELIVERY = "frDelivery";
	
	/** The br delivery to. */
	public String BR_DELIVERY_TO = "brDeliveryTO";
	
	/** The br delivery. */
	public String BR_DELIVERY = "brDelivery";
	
	/** The action type. */
	public String ACTION_TYPE = "actionType";
	
	/** The action method find. */
	public String ACTION_METHOD_FIND = "find";
	
	/** The action method convert. */
	public String ACTION_METHOD_CONVERT = "convert";
	
	/** The action method add. */
	public String ACTION_METHOD_ADD = "add";
	
	/** The fdm number. */
	public String FDM_NUMBER = "fdmNumber";
	
	/** The drs number. */
	public String DRS_NUMBER = "drsNumber";
	
	/** The fr ho number. */
	public String FR_HO_NUMBER = "handOverNum";
	
	/** The delivery service. */
	public String DELIVERY_SERVICE = "deliveryManifestService";
	
	/** The booking service. */
	public String BOOKING_SERVICE = "DTDCbookingService";
	
	/** The fdm main page. */
	public String FDM_MAIN_PAGE = "fdmMainPage";
	
	/** The bdm main page. */
	public String BDM_MAIN_PAGE = "bdmMainPage";
	
	/** The phone numbers. */
	public String PHONE_NUMBERS = "phonesNumbers";
	
	/** The reasons. */
	public String REASONS = "reasonsList";
	
	/** The employees. */
	public String EMPLOYEES = "employeeList";
	
	/** The fdm date. */
	public String FDM_DATE = "fdmDate";
	
	/** The fdm time. */
	public String FDM_TIME = "fdmTime";
	
	/** The view fr delivery. */
	public String VIEW_FR_DELIVERY = "viewFranchiseeManifest";
	
	/** The conversion delivery ids. */
	public String CONVERSION_DELIVERY_IDS = "valuesForConvert";
	
	/** The products. */
	public String PRODUCTS = "products";
	
	/** The office. */
	public String OFFICE = "office";
	
	/** The reg office id. */
	public String REG_OFFICE_ID = "regOffId";
	
	/** The print fdm. */
	public String PRINT_FDM = "printFDM";
	
	/** The redirect main page. */
	public String REDIRECT_MAIN_PAGE = "/ctbs-web/frDeliveryManifest.do?submitName=add";
	
	/** The redirect bdm main page. */
	public String REDIRECT_BDM_MAIN_PAGE = "/ctbs-web/brDeliveryManifest.do?submitName=add";
	
	/** The redirect fdmho main page. */
	public String REDIRECT_FDMHO_MAIN_PAGE = "/ctbs-web/frHandOverDeliveryManifest.do?submitName=add";
	
	/** The date today. */
	public String DATE_TODAY = "todayDate";
	
	/** The current time. */
	public String CURRENT_TIME = "currentTime";
	
	/** The view br delivery. */
	public String VIEW_BR_DELIVERY = "viewBranchManifest";
	
	/** The ptp service. */
	public String PTP_SERVICE = "viewPTPService";
	
	/** The total consgnments. */
	public String TOTAL_CONSGNMENTS = "totalConsignments";
	
	/** The total weight. */
	public String TOTAL_WEIGHT = "totalWeight";
	
	/** The print bdm. */
	public String PRINT_BDM = "priintBranchDeliveryManifet";
	
	/** The weight tolerance. */
	public Double WEIGHT_TOLERANCE = 0.1;
	
	/** The booking status. */
	public String BOOKING_STATUS = "BOOKED";
	
	/** The manifest status. */
	public String MANIFEST_STATUS = "M";
	
	/** The get duplicate consg num. */
	public String GET_DUPLICATE_CONSG_NUM = "getDuplicateConsgnNum";
	
	/** The is already ho. */
	public String IS_ALREADY_HO = "isAlreadyHO";	
	
	/** The get duplicate consg number. */
	public String GET_DUPLICATE_CONSG_NUMBER = "getDuplicateConsgnNumber";
	
	/** The get duplicate consg number by pre date. */
	public String GET_DUPLICATE_CONSG_NUMBER_BY_PRE_DATE = "getDuplicateConsgnNumberByPreDate";
	
	/** The consg number. */
	public String CONSG_NUMBER = "conNum";
	
	/** The get fdm handover. */
	public String GET_FDM_HANDOVER = "getFDMHandOverByManifest";
	
	/** The fdm num. */
	public String FDM_NUM = "fdmNum";
	
	/** The get employee. */
	public String GET_EMPLOYEE = "getEmployee";
	
	/** The get employee by id. */
	public String GET_EMPLOYEE_BY_ID = "getEmployeeById";
	
	/** The emp code. */
	public String EMP_CODE = "empCode";
	
	/** The office code. */
	public String OFFICE_CODE = "officeCode";
	
	/** The office id. */
	public String OFFICE_ID = "officeId";
	
	/** The office type. */
	public String OFFICE_TYPE = "offType";
	
	/** The emp id. */
	public String EMP_ID = "empID";
	
	/** The get franchisee and reporting branch. */
	public String GET_FRANCHISEE_AND_REPORTING_BRANCH = "getFranchiseeAndReportingBranch";
	
	/** The get franchisee and reporting branch by id. */
	public String GET_FRANCHISEE_AND_REPORTING_BRANCH_BY_ID = "getFranchiseeAndReportingBranchByID";
	
	/** The fr code. */
	public String FR_CODE = "frCode";
	
	/** The fr id. */
	public String FR_ID = "frID";
	
	/** The get booking by consg num. */
	public String GET_BOOKING_BY_CONSG_NUM = "getBookingByConsgNum";
	
	/** The get paper wrk booking. */
	public String GET_PAPER_WRK_BOOKING = "isPaperWorkCOnsg";
	
	/** The consgnum. */
	public String CONSGNUM = "consgNum";
	
	/** The consignment num. */
	public String CONSIGNMENT_NUM = "consignmentNumber";
	
	/** The consg status. */
	public String CONSG_STATUS = "consgStatus";
	
	/** The consg delivery status. */
	public String CONSG_DELIVERY_STATUS = "deliveryStatus";
	
	/** The manifest consg status. */
	public String MANIFEST_CONSG_STATUS = "manifestConsgStatus";
	
	/** The get products. */
	public String GET_PRODUCTS = "getProductById";
	
	/** The product id. */
	public String PRODUCT_ID = "prodID";
	
	/** The get employee by branch id. */
	public String GET_EMPLOYEE_BY_BRANCH_ID = "getEmployeeByBranchID";
	
	/** The save bdm. */
	public String SAVE_BDM = "saveBrDeliveryManifest";
	
	/** The delivery manifest service class type. */
	public String DELIVERY_MANIFEST_SERVICE_CLASS_TYPE = "DeliveryManifestService";
	
	/** The delivery run sheet service. */
	public String DELIVERY_RUN_SHEET_SERVICE = "deliveryRunSheetService";
	
	/** The delivery run sheet service class type. */
	public String DELIVERY_RUN_SHEET_SERVICE_CLASS_TYPE = "DeliveryRunSheetService";
	
	/** The not written to central. */
	public String NOT_WRITTEN_TO_CENTRAL = "N";
	
	/** The written to central. */
	public String WRITTEN_TO_CENTRAL = "Y";
	
	/** The delivery run sheet update method. */
	public String DELIVERY_RUN_SHEET_UPDATE_METHOD = "updateDRS";
	
	/** The save fr delivery manifest ho. */
	public String SAVE_FR_DELIVERY_MANIFEST_HO = "saveFrDeliveryManifestHO";
	
	/** The save fr delivery manifest. */
	public String SAVE_FR_DELIVERY_MANIFEST = "saveFrDeliveryManifest";
	
	/** The non delivery run sheet service. */
	public String NON_DELIVERY_RUN_SHEET_SERVICE = "nonDeliveryRunSheetService";
	
	/** The non delivery run sheet service class type. */
	public String NON_DELIVERY_RUN_SHEET_SERVICE_CLASS_TYPE = "NonDeliveryRunSheetService";
	
	/** The update non delivery run sheet method. */
	public String UPDATE_NON_DELIVERY_RUN_SHEET_METHOD = "updateDRS";
	
	/** The duplicate consignment. */
	public String DUPLICATE_CONSIGNMENT = "error.consignmentAlreadyDelivered";
	
	/** The duplicate consignment fdm. */
	public String DUPLICATE_CONSIGNMENT_FDM = "error.duplicateFDM";
	
	/** The duplicate consignment bdm. */
	public String DUPLICATE_CONSIGNMENT_BDM = "error.duplicateBDM";
	
	/** The invalid manifest consignment. */
	public String INVALID_MANIFEST_CONSIGNMENT = "error.consignmentIsBooked";
	
	/** The invalid fdmnumber. */
	public String INVALID_FDMNUMBER = "error.fDMNumberdoesnotexists";
	
	/** The duplicate fdmnumber. */
	public String DUPLICATE_FDMNUMBER = "error.duplivateFDMHONumber";
	
	/** The get yesterday details by fr. */
	public String GET_YESTERDAY_DETAILS_BY_FR = "getYesterdaysDetailsByFr";
	
	/** The get yesterday details by emp. */
	public String GET_YESTERDAY_DETAILS_BY_EMP = "getYesterdaysDetailsEmployee";
	
	/** The franchisee id. */
	public String FRANCHISEE_ID = "franchID";
	
	/** The employe id. */
	public String EMPLOYE_ID = "employeID";
	
	/** The product total. */
	public String PRODUCT_TOTAL = "TOTAL";
	
	/** The hand over date from. */
	public String HAND_OVER_DATE_FROM = "handoverdatefrom";
	
	/** The hand over date to. */
	public String HAND_OVER_DATE_TO = "handoverdateto";
	
	/** The invalid consignee. */
	public String INVALID_CONSIGNEE = "error.invalidConsignee";
	
	/** The record status active. */
	public Integer RECORD_STATUS_ACTIVE=1;
	
	/** The record status inactive. */
	public Integer RECORD_STATUS_INACTIVE=0;
	
	/** The duplicate consignment same day. */
	public String DUPLICATE_CONSIGNMENT_SAME_DAY = "error.duplicateConsignmentSameDay";
	
	/** The get fdm number. */
	public String GET_FDM_NUMBER="getFDMNumber";
	
	/** The get attempt number. */
	public String GET_ATTEMPT_NUMBER="getAttemptNumber";
	
	/** The no short approval. */
	public String NO_SHORT_APPROVAL = "error.noApproval";
	
	/** The bdm cn max limit. */
	String BDM_CN_MAX_LIMIT = "BDM_CN_MAX_LIMIT";
	
	/** The cn weight tolerance. */
	String CN_WEIGHT_TOLERANCE = "CN_WEIGHT_TOLERANCE";
	
	/** The bdm back date limit. */
	String BDM_BACK_DATE_LIMIT = "BDM_BACK_DATE_LIMIT";
	
	/** The bdm max weight limit. */
	String BDM_MAX_WEIGHT_LIMIT = "BDM_MAX_WEIGHT_LIMIT";
	
	/** The total records. */
	String TOTAL_RECORDS = "totalRecords";
	
	/** The weight tolerence. */
	String WEIGHT_TOLERENCE = "weightTolerence";
	
	/** The date config limit. */
	String DATE_CONFIG_LIMIT = "dateCnfgLimit";
	
	/** The drs num. */
	String DRS_NUM = "drsNum";
	
	/** The branch delivery manifest. */
	String BRANCH_DELIVERY_MANIFEST = "BRANCH DELIVERY MANIFEST";
	
	/** The branch code. */
	String BRANCH_CODE = "branchCode";
	
	/** The branch name. */
	String BRANCH_NAME = "branchName";
	
	/** The run sheet number. */
	String RUN_SHEET_NUMBER = "runSheetNum";
	
	/** The phone number. */
	String PHONE_NUMBER = "phoneNum";
	
	/** The emp desc. */
	String EMP_DESC = "empDesc";
	
	/** The bdm date. */
	String BDM_DATE = "bdmDate";
	
	/** The bdm time. */
	String BDM_TIME = "bdmTime";
	
	/** The bdm report. */
	String BDM_REPORT = "attachment; filename=bdmReport.pdf";
	
	/** The consg pincode. */
	String CONSG_PINCODE = "consgpinCode";
	
	/** The branch office id. */
	String BRANCH_OFFICE_ID = "brnchOfficeId";
	
	/** The fdm preparation notification. */
	String FDM_PREPARATION_NOTIFICATION = "FDM preparation Notification..";
	
	/** The success. */
	String SUCCESS = "success";
	
	/** The failure. */
	String FAILURE = "failure";
	
	/** The valid. */
	String VALID = "VALID";
	
	/** The error. */
	String ERROR = "ERROR";
	
	/** The hiphon. */
	String HIPHON = "-";
	
	/** The invalid pincode. */
	String INVALID_PINCODE = "Invalid Pincode";
	
	/** The serviceable. */
	String SERVICEABLE = "SERVICEABLE";
	
	/** The nonserviceable. */
	String NONSERVICEABLE = "NONSERVICEABLE";
	
	/** The pincode info. */
	String PINCODE_INFO = "PINCODE NOT MAPPED TO FRANCHISEE";
	
	/** The invalid. */
	String INVALID = "INVALID";
	
	/** The no. */
	String NO = "N";
	
	/** The pincode id. */
	String PINCODE_ID = "pincode.pincodeId";
	
	/** The franchisee key. */
	String FRANCHISEE_KEY = "franchisee.franchiseeId";
	
	/** The pincode. */
	String PINCODE = "pincode";
	
	/** The update consg status. */
	String UPDATE_CONSG_STATUS = "updateConsgStatus";
	
	/** The status. */
	String STATUS = "status";
	
	/** The from date. */
	String FROM_DATE = "fromDate";
	
	/** The to date. */
	String TO_DATE = "toDate";
	
	/** The Delivery_ ids. */
	String Delivery_IDS = "deliveryIds";
	
	/** The total pieces. */
	String TOTAL_PIECES = "totalPieces";
	
	/** The Delivery_ id. */
	String Delivery_ID = "deliveryId";
	
	/** The delete bdm. */
	String DELETE_BDM = "deleteBrDeliveryManifest";
	
	/** The fdm update. */
	String FDM_UPDATE = "updateFDM";
	
	/** The handover time. */
	String HANDOVER_TIME = "hoTime";
	
	/** The handover date. */
	String HANDOVER_DATE = "hoDate";
	
	/** The delv status. */
	String DELV_STATUS = "delvStatus";
	
	/** The handover number. */
	String HANDOVER_NUMBER = "hoNumber";
	
	/** The consignment number. */
	String CONSIGNMENT_NUMBER = "consgNumber";
	
	/** The manifest type. */
	String MANIFEST_TYPE = "manifestType";
	
	/** The rto manifest catogery. */
	String RTO_MANIFEST_CATOGERY = "rtoMnfstCat";
	
	/** The preparation date. */
	String PREPARATION_DATE = "preparationDate";
	
	/** The update booked weight. */
	String UPDATE_BOOKED_WEIGHT = "updateBookedWeight";
	
	/** The final weight. */
	String FINAL_WEIGHT = "finalWeight";
	
	/** The rate amount. */
	String RATE_AMOUNT = "rateAmount";
	
	/** The update destination into booking. */
	String UPDATE_DESTINATION_INTO_BOOKING = "updateDestinationIntoBooking";
	
	/** The dest pincode. */
	String DEST_PINCODE = "destPinCode";
	
	/** The update billing status. */
	String UPDATE_BILLING_STATUS = "updateBillingStatus";
	
	/** The billing status. */
	String BILLING_STATUS = "billingStatus";
	
	/** The office key. */
	String OFFICE_KEY = "office.officeId";
	
	/** The redirect spl cust main page. */
	public String REDIRECT_SPL_CUST_MAIN_PAGE = "/ctbs-web/splCustomerPacketManifestAction.do?submitName=showSplCustPktManifest";
	
	/** The consg paperwork. */
	public String CONSG_PAPERWORK = "error.consgInAnPaperWork";
	
	/** The invald ta city consgnmnts. */
	public String INVALD_TA_CITY_CONSGNMNTS = "error.invalidTACityConsignments";
	
	/** The invald sa city consgnmnts. */
	public String INVALD_SA_CITY_CONSGNMNTS = "error.invalidSACityConsignments";
	
	/** The invald misroute consgnmnts. */
	public String INVALD_MISROUTE_CONSGNMNTS = "error.invalidMisrouteConsignments";
	
	/** The invalid consg returned. */
	public String INVALID_CONSG_RETURNED = "error.consignmentAlreadyReturned";
	
	/** The invalid pincode delivery. */
	public String INVALID_PINCODE_DELIVERY = "error.invalidPincodeDelivery";
	
	/** The update process. */
	String UPDATE_PROCESS = "updatedProcess";
	
}
