package src.com.dtdc.constants;

// TODO: Auto-generated Javadoc
/**
 * Responsible for holding all constants related to
 * UC 71 & 72 DRS Franchisee and Branch.
 */
public interface DRSConstants {

	//tiles mapping ...
	/** The runsheet. */
	String RUNSHEET = "deliveryRunSheet";
	
	/** The print drs. */
	String PRINT_DRS = "printDeliveryRunSheet";
	
	//set attributes
	/** The display list. */
	String DISPLAY_LIST="displayList";
	
	//error key
	/** The update exception. */
	String UPDATE_EXCEPTION = "pikup.update.exception";
	
	/** The log error in save. */
	String LOG_ERROR_IN_SAVE = "Error occured in save() ..";
	
	/** The log error in show. */
	String LOG_ERROR_IN_SHOW = "Error occured in show()";
	
	/** The log error in print. */
	String LOG_ERROR_IN_PRINT = "Error occured in print()";
	
	//split model settings
	/** The drs update. */
	String DRS_UPDATE = "updateDRS";
	
	//service class name for split model
	/** The drs service name. */
	String DRS_SERVICE_NAME = "DeliveryRunSheetService";
	
	/** The descriminator. */
	String DESCRIMINATOR = "discriminator";
	
	/** The useraction. */
	String USERACTION = "useraction";
	
	/** The branch. */
	String BRANCH = "branch";
	
	/** The franchisee. */
	String FRANCHISEE = "franchisee";
	
	/** The franchisee id. */
	String FRANCHISEE_ID = "franchiseeId";
	
	/** The fr status. */
	String FR_STATUS ="status";
	
	/** The fr status value. */
	String FR_STATUS_VALUE ="A";
	
	/** The drs. */
	String DRS = "DRS";
	
	/** The ndrs. */
	String NDRS = "NDRS";
	
	/** The bdm fdm. */
	String BDM_FDM = "BDM_FDM";
	
	/** The new bdm fdm. */
	String NEW_BDM_FDM = "NEW_BDM_FDM";
	
	/** The drs page. */
	String DRS_PAGE = "deliveryRunSheet";
	//String BRANCH_DRS_PAGE = "branchDrs";
	//String FRANCHISEE_DRS_PAGE = "franchiseeDrs";
	
	/** The Request parameter from form. */
	String   FRANCHISEE_CODE="franchiseeCode";	
	
	/** The Request parameter from form. */
	String   EMPLOYEE_CODE="employeeCode";
	
	/** The drs number. */
	String DRS_NUMBER="drsNo";
	
	/** The drs log error. */
	String DRS_LOG_ERROR="Error in ajaxValidateConsgNumber ...";
	
	/** The max del attempt. */
	String MAX_DEL_ATTEMPT="MAX_DELIVERY_ATTEMPT";
	
	/** The closed consg status. */
	String CLOSED_CONSG_STATUS = "C";
	
	/** The inactive status. */
	String INACTIVE_STATUS="I";
	
	/** The active status. */
	String ACTIVE_STATUS="A";
	
	//map keys
	/** The error. */
	String ERROR = "error";
	
	/** The fdm no. */
	String FDM_NO = "fdmNo";
	
	/** The branch id. */
	String BRANCH_ID ="branchId";
	
	/** The branch code. */
	String BRANCH_CODE="branchCode";
	
	/** The branch name. */
	String BRANCH_NAME="branchName";
	
	/** The franchisee name. */
	String FRANCHISEE_NAME="franchiseeName";
	
	/** The total count. */
	String TOTAL_COUNT="totalCount";
	
	/** The delivery count. */
	String DELIVERY_COUNT="deliveryCount";
	
	/** The non delivery count. */
	String NON_DELIVERY_COUNT="nonDeliveryCount";
	
	/** The pending count. */
	String PENDING_COUNT="pendingCount";
	
	//jason object keys
	/** The emp id. */
	String EMP_ID = "empId";
	
	/** The emp code. */
	String EMP_CODE = "empCode";
	
	/** The emp name. */
	String EMP_NAME = "empName";
	
	/** The product id. */
	String PRODUCT_ID="pId";
	
	/** The max delivery back date. */
	String MAX_DELIVERY_BACK_DATE = "MAX_DRS_DELIVERY_BACKDATE_DAYS";
	
	/** The max cn weight tolerance. */
	final String MAX_CN_WEIGHT_TOLERANCE = "CN_WEIGHT_TOLERANCE";
	
	/** The consg status inactive. */
	String CONSG_STATUS_INACTIVE = "I";
	
	/** The delivery code fdm. */
	String DELIVERY_CODE_FDM = "FDM";
	
	/** The delivery code bdm. */
	String DELIVERY_CODE_BDM = "BDM";
	
	/** The status p. */
	String STATUS_P="P";
	
	/** The status o. */
	String STATUS_O="O";
	
	/** The status d. */
	String STATUS_D="D";
	
	/** The status n. */
	String STATUS_N="N";
	
	/** The status h. */
	String STATUS_H="H";
	
	/** The save. */
	String SAVE = "save";
	
	/** The edit. */
	String EDIT = "edit";
	
	/** The db status y. */
	String DB_STATUS_Y = "Y";
	
	/** The db status n. */
	String DB_STATUS_N = "N";
	
	/** The db status a. */
	String DB_STATUS_A = "A";
	
	/** The db status m. */
	String DB_STATUS_M = "M";
	
	/** The db status p. */
	String DB_STATUS_P = "P";
	
	/** The di flag. */
	String DI_FLAG = "N";
}
