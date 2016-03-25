/**
 * 
 */
package com.ff.universe.pickup.constant;



/**
 * @author uchauhan
 * 
 */
public interface UniversalPickupConstant {

	// office type
	String BRANCH = "BO";
	String HUB = "HO";
	String REGION = "RO";
	String RESP_ERROR = "ERROR";
	String RESP_SUCCESS = "SUCCESS";
	String RESP_ERROR_MSG = "PU0013";
	String RESP_SUCCESS_MSG = "PU0012";
	String RUNSHEET_ASSIGNMENT_STATUS_UNUSED = "U";
	String OPEN="Open";
	String REVERSE="R";
	String STANDARD="S";

	String QRY_GET_PICKUP_RUNSHEET_DETAILS = "getPickupRunsheetDetails";
	String QRY_GET_RUNSHEET_ASSIGNMENT_HEADER="getRunsheetAssignmentHeader";
	String RUNSHEET_HEADER_ID_LIST = "RunsheetHeaderId";
	String ASSIGNMENT_HEADER_ID = "assignmentHeaderId";

	String QRY_GET_MASTER_RUNSHEET_ASSIGNMENT_DETAILS_BY_EMP_ID = "getMasterRunsheetAssignmentDetailsByEmpId";
	String PARAM_EMPLOYEE_ID = "employeeId";
	
	String QRY_GET_PICKUP_DLV_LOC_FOR_SAP_CUST_CONTRACT="getPickupDlvLocForSAPCustContract";
	
}
