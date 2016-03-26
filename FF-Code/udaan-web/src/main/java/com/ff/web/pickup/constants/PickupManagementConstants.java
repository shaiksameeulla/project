/**
 * 
 */
package com.ff.web.pickup.constants;

/**
 * @author uchauhan
 * 
 */
public interface PickupManagementConstants {

	public static final String PICKUP_COMMON_SERVICE = "pickupManagementCommonService";
	public static final String ORGANIZATION_COMMON_SERVICE = "organizationCommonService";
	public static final String CONFIRM_PICKUP_ORDER_SERVICE = "confirmPickupOrderService";
	public static final String CREATE_PICKUP_ORDER_SERVICE = "pickupManagementService";
	public static final String CREATE_RUNSHEET_ASSIGNMENT_SERVICE = "pickupAssignmentService";
	public static final String PICKUP_GATEWAY_SERVICE = "pickupGatewayService";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	String GENERATE_RUNSHEET_SERVICE = "generateRunsheetService";
	String UPDATE_RUNSHEET_SERVICE = "updateRunsheetService";
	String SEQUENCE_GENERATOR_SERVICE = "sequenceGeneratorService";

	// Header List constants

	String HEADER_CONSIGNER_NAME = "Name of the Consignor";
	String HEADER_ADDRESS = "Address";
	String HEADER_PINCODE = "Pincode";
	String HEADER_CITY = "City";
	String HEADER_TELEPHONE = "TelNo";
	String HEADER_MOBILE = "MobileNo";
	String HEADER_EMAIL = "Email_ID";
	String HEADER_PRODUCT_TYPE = "Product_Type";
	String HEADER_REFRENCE_NUMBER = "Ref_No";
	String HEADER_MATERIAL_DESC = "Material_Desp";
	String HEADER_MATERIAL_VALUE = "Material Value";
	String ERROR_DESCRIPTION = "Error Description";

	// Query Constants

	String PARAMS = "officeId,status";
	String UPDATE_PARAMS = "status,officeId,detailId";
	String GET_REQUEST_DETAILS = "getPickupReqList";
	String UPDATE_PICKUP_ORDER_DETAILS = "updatePickupOrderDetails";
	String SEQUENCE_NUMBER_TYPE = "ORDER_NUMBER";
	String UPDATE_BRANCH_ORDER_DETAILS = "updateBranchOrderDetails";
	String UPDATE_DATA_SYNC_DETAILS = "updateDataSyncDetails";

	// Runsheet Status
	String OPEN = "Open";
	String UPDATE = "UPDATE";
	String CLOSE = "CLOSE";
	String GENERATE = "GENERATE";
	String UN_USED = "Unused";
	String MASTER = "Master";
	String TEMPORARY = "Temporary";
	String PICKUP_ASSIGNMENT_TYPE_CODE_MASTER = "P";
	String RUNSHEET_STATUS_OPEN = "O";
	String RUNSHEET_STATUS_UPDATE = "U";
	String RUNSHEET_STATUS_CLOSE = "C";
	String RUNSHEET_ASSIGNMENT_STATUS_GENERATE = "G";
	String RUNSHEET_ASSIGNMENT_STATUS_UNUSED = "U";
	String RUNSHEET_LIST = "runsheetList";
	String UPDATE_PICKUP_RUNSHEET_TO = "updatePkupRunsheetTO";
	String PICKUP_RUNSHEET_DETAILS = "pkupRunsheetDetails";
	String YES = "Y";
	String NO = "N";
	// Page Forward Strings
	String UPDATE_PICKUP_RUNSHEET = "viewUpdatePickupRunSheet";
	String VIEW_GENERATE_PICKUP_RUNSHEET = "viewGeneratePickupRunSheet";
	String URL_PRINT_GENERATE_PICKUP_RUNSHEET = "printGeneratePickupRunSheet";

	// Query Names used for PickupManagementServiceDAO
	String GET_PICKUP_RUNSHEET_DETAILS = "getPickupRunsheetDetails";
	String GET_PICKUP_RUNSHEET_DETAILS_RUNSHEET_NO = "getPickupRunsheetDetailsByRunsheetNo";
	// String GET_BRANCH_EMPLOYEES = "getBranchEmployees";
	// String GET_ALL_BRANCH_EMPLOYEES = "getAllBranchEmployees";
	String GET_BRANCHES_UNDER_HUB = "getBranchesUnderHub";
	String GET_PICKUP_RUNSHEET = "getPickupRunsheet";
	String GET_PICKUP_RUNSHEET_FOR_ALL_BRANCHES = "getPickupRunsheetForAllBranches";
	String GET_ASSIGNMENT_DETAILS = "getAssignmentDetails";
	// String UPDATE_ASSIGNMENT_STATUS = "updateAssignmentStatus";
	String GET_RUNSHEET_NUMBER = "getRunsheetNumber";
	/* String UPDATE_RUNSHEET_DETAILS = "updateRunsheetDetails"; */
	String UPDATE_RUNSHEET_HEADER = "updateRunsheetHeader";
	/*
	 * String UPDATE_REVERSE_PICKUP_REQEST_STATUS =
	 * "updateReversePickupReqestStatus";
	 */
	String GET_ASSIGNMENT_TYPE_BY_ID = "getAssignmentTypeById";
	String GET_RUNSHEET_ASSIGNMENT_HEADER = "getRunsheetAssignmentHeader";
	String GET_MASTER_RUNSHEET_STATUS = "getMasterRunsheetStatus";
	String QRY_GET_CONTRACT_PAY_BILLING_LOCATION_DTLS = "getContractPayBillocationDtlsByLocationId";

	// Query Param names
	String LOGIN_OFFICE_ID = "loginOfficeId";
	String BRANCH_IDS = "branchIds";
	String EMPLOYEE_ID = "employeeId";
	String BRANCH_ID = "branchId";
	String ASSIGNMENT_HEADER_ID = "assignmentHeaderId";
	String ASSIGNMENT_DETAIL_ID = "assignmentDetailId";
	String STATUS = "status";
	String ASSIGNMENT_TYPE_ID = "assignmentTypeId";
	String CURRENT_DATE = "currentDate";
	String REVERSE_DETAIL_ID = "reverseDetailId";
	String PICKUP_DELIVERY_LOCATION = "pickupDlvLocId";
	// Request Order Status

	String PENDING = "P";
	String ACCEPTED = "A";
	String DECLINED = "D";
	String CLOSED = "C";
	String REV_REQUEST_ORDER_STATUS_PICKED_UP = "U";

	// office type
	String BRANCH = "BO";
	String HUB = "HO";
	String REGION = "RO";

	// Forward name

	String ACCEPT = "Accept";
	String DECLINE = "Decline";
	String SUCCESS = "Success";

	String RUNSHEET_HEADER_ID = "RunsheetHeaderId";
	String RUNSHEET_NO = "RunsheetNo";
	String UPDATED = "UPDATED";
	String STATUS_UPDATED = "Updated";
	String STATUS_CLOSED = "Closed";
	// Pickup types
	String REVERSE = "R";
	String STANDARD = "S";

	String TRUE = "true";

	String TIME = "time";
	String STARTCN = "startCN";
	String ENDCN = "endCN";
	String COUNT = "count";
	String RUNSHEET_DETAIL_ID = "runsheetDtlId";
	String ERROR_MSG_SEQUENCE_NO_GENERATION = "Sequence number not generated";
	String RUNSHEET_TYPE_DROP_DOWN = "runsheetTypeMap";
	String RUNSHEET_BRANCH_DROP_DOWN = "runsheetBranchMap";
	String RUNSHEET_BRANCH_DROP_DOWN_HUB = "runsheetBranchMapHub";
	String RUNSHEET_EMP_DROP_DOWN = "runsheetEmpMap";
	String RUNSHEET_DETAIL_FOR_SAVE = "RUNSHEET_DETAILS";
	String RUNSHEET_DETAIL_FOR_CUSTOMER_LIST = "RUNSHEET_DETAILS_FOR_CUSTOMERS";

	int PICKUP_RUNSHEET_TYPE_MASTER = 1;
	int PICKUP_RUNSHEET_TYPE_TEMPORARY = 2;
	String INVALID_PINCODE = "PU001";
	String INVALID_CITY = "PU002";
	String INVALID_PHONE = "PU003";
	String INVALID_MOBILE = "PU004";
	String INVALID_EMIAL = "PU005";
	String INVALID_CONSIGNMENT = "PU006";
	String INVALID_CONSIGNOR_NAME = "PU007";
	String INVALID_ADDRESS = "PU008";
	String INVALID_HEADER = "PU009";

	String NO_SEARCH_RESULTS_FOUND = "PUG001";
	String ERROR_IN_RUNSHEET_GENERATION = "PUG002";
	String NO_ASSIGNED_EMPLOYEES = "PUG003";

	String LOGGEDIN_USER_IS_CUSTOMER_NOT_EMPLOYEE = "W0010";
	String NO_DELIVERY_BRANCHES_FOR_SELECTED_CUSTOMER = "I0005";
	String DATABASE_ERROR_PLEASE_TRY_AGAIN = "E0002";
	String DATA_NOT_UPLOADED_SERVER_ERROR = "E0001";
	String DETAILS_NOT_FOUND = "E0005";
	String NO_PENDING_REQUESTS = "I0001";
	String REQUEST_ACCEPTED_SUCCESSFULLY = "I0003";
	String REQUEST_DECLINED_SUCCESSFULLY = "I0004";
	String PICKUP_RUNSHEET_DETAILS_NOT_EXIST = "P001";
	String RUNSHEET_GENERATED_SUCCESSFULLY = "PUG004";
	String PROBLEM_RUNSHEET_NUMBER_GENERATION = "PUG005";
	String SEQUENCE_NUMBER_GENERATED = "PUG006";

	String QRY_GET_LATEST_SHIP_TO_CODE_BY_CUSTOMER = "SELECT bilLoc.SHIPPED_TO_CODE "
			+ "FROM ff_d_contract_payment_billing_location bilLoc "
			+ "    	JOIN ff_d_pickup_delivery_location dlvLoc ON bilLoc.PICKUP_DELIVERY_LOCATION = dlvLoc.LOCATION_ID "
			+ "  	JOIN ff_d_pickup_delivery_contract dlvCon ON dlvLoc.CONTRACT_ID = dlvCon.CONTRACT_ID "
			+ " 	JOIN ff_d_rate_contract rc ON bilLoc.RATE_CONTRACT = rc.RATE_CONTRACT_ID "
			+ "		JOIN ff_d_customer cust ON cust.customer_id = rc.customer "
			+ "WHERE dlvCon.OFFICE_ID = :officeId "
			+ "     AND bilLoc.RATE_CONTRACT = "
			+ "           (SELECT c.RATE_CONTRACT_ID "
			+ "             FROM ff_d_rate_contract c "
			+ "                 JOIN ff_d_customer cust ON cust.customer_id = c.customer "
			+ "        		WHERE c.CONTRACT_STATUS IN ('A', 'I') "
			+ "             AND cust.customer_id = :customerId "
			+ "            	AND date(curdate()) >= date(c.VALID_FROM_DATE) "
			+ "    ORDER BY c.VALID_FROM_DATE DESC "
			+ "    LIMIT 1) "
			+ "AND bilLoc.STATUS = 'A' ";

	String QRY_GET_CUSTOMERS_IN_CONTRACT_BY_BRANCH = "SELECT DISTINCT fdc.CUSTOMER_ID, "
			+ "fdc.BUSINESS_NAME,fdc.CUSTOMER_CODE, bilLoc.SHIPPED_TO_CODE "
			+ "FROM ff_d_contract_payment_billing_location bilLoc "
			+ "JOIN ff_d_pickup_delivery_location dlvLoc ON bilLoc.PICKUP_DELIVERY_LOCATION = dlvLoc.LOCATION_ID "
			+ "JOIN ff_d_pickup_delivery_contract dlvCon ON dlvLoc.CONTRACT_ID = dlvCon.CONTRACT_ID "
			+ "JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = dlvCon.CUSTOMER_ID "
			+ "JOIN ff_d_rate_contract rc ON rc.CUSTOMER = fdc.CUSTOMER_ID "
			+ "JOIN ff_d_customer_type fdct ON fdct.CUSTOMER_TYPE_ID = fdc.CUSTOMER_TYPE "
			+ "JOIN ff_d_rate_customer_category fdrcc ON fdc.CUSTOMER_CATEGORY = fdrcc.RATE_CUSTOMER_CATEGORY_ID "
			+ "WHERE fdc.CONTRACT_NO IS NOT NULL "
			+ "AND fdc.CUR_STATUS = 'A' "
			+ "AND bilLoc.STATUS = 'A' "
			+ "AND dlvCon.STATUS = 'A' "
			+ "AND fdc.CUSTOMER_CODE IS NOT NULL "
			+ "AND bilLoc.LOCATION_OPERATION_TYPE IS NOT NULL "
			+ "AND ((fdct.CUSTOMER_TYPE_CODE = 'RL' AND dlvCon.CONTRACT_TYPE = 'D' "
			+ "AND dlvCon.OFFICE_ID IN "
			+ "(SELECT fdo.OFFICE_ID "
			+ "FROM ff_d_office fdo "
			+ "WHERE    fdo.OFFICE_ID = :officeId "
			+ "OR fdo.REPORTING_HUB = :officeId "
			+ "OR fdo.REPORTING_RHO = :officeId)) "
			+ "OR (fdrcc.CUSTOMER_CATEGORY_CODE NOT IN ('BA', 'ACC') "
			+ "AND rc.TYPE_OF_BILLING <> 'DBCP' "
			+ "AND dlvCon.OFFICE_ID IN "
			+ "(SELECT fdo.OFFICE_ID "
			+ "FROM ff_d_office fdo "
			+ "WHERE fdo.OFFICE_ID = :officeId OR fdo.REPORTING_HUB = :officeId))) ORDER BY fdc.BUSINESS_NAME";
}
