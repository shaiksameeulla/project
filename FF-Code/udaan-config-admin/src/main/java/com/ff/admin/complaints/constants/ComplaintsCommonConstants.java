package com.ff.admin.complaints.constants;

public interface ComplaintsCommonConstants {

	String CITY_ID = "cityId";

	/** The city code. */
	String CITY_CODE = "cityCode";

	String PINCODE = "pincode";

	String EMPLOYEE_DESIGNATION_TYPE = "SC";
	String USER_INFO = "user";
	String COMPLAINTS_NUMBER_START_CODE = "C";
	String COMPLAINTS_NUMBER = "COMPLAINTS_NUMBER";
	String COMPLAINT_SERVICE_REQ_NUMBER="serviceRequestNo";

	String SCHK = "SCHK";
	String BPIN = "BPIN";
	String TRENQ = "TRENQ";
	String GINFO = "GINFO";
	String EBOND = "EBOND";
	String SPAPER = "SPAPER";
	String LCALL = "LCALL";
	String PCALL = "PCALL";
	String PWORK = "PWORK";
	String DESIGNATION_CODE = "BL";
	String DEPARTMENT_NAME_TYPE = "BackLine";
	String DEPARTMENT_NAME = "departmentName";
	String DESIGNATION = "designation";
	String QRY_PARAM_ROLE = "roleName";
	String OFFICEID = "officeId";
	String OFFICE_TYPE_ID = "officeTypeId";
	String SERVICE_TYPE = "serviceType";

	String URL_VIEW_SERVICE_REQUEST_FOR_SERVICE = "serviceRequestForService";

	String URL_VIEW_SERVICE_REQUEST_FOR_CONSIGNMENT = "serviceRequestForConsignment";

	String COMPLAINTS_BACKLINE_SUMMARY_SERVICE = "complaintsBacklineSummaryService";

	String URL_VIEW_FOR_TRACKING_POPUP = "viewtrackingPOPup";

	String URL_VIEW_BACKLINE_SUMMARY = "backlineSummary";

	String COMPLAINTS_STATUS = "COMPLAINTS_STATUS";

	String COMPLAINTS_SERVICE_QRY = "COMPLAINTS_SERVICE_QRY";

	String COMPLAINTS_SEARCH = "COMPLAINTS_SEARCH";

	String COMPLAINTS_TRACKING = "TRACKING";

	String COMPLAINTS_STATUS_LIST = "complaintsStatusList";
	String COMPLAINTS_STATUS_MAP = "complaintsStatusMap";
	String SEARCH_CATEGORY_LIST = "searchCategoryList";

	String STD_TYPE_SOURCE_OF_QUERY = "COMPLAINTS_QUERY_SOURCE";
	String SESSION_ATTRIBUTE_SOURCE_OF_QUERY = "sourceOfQuery";
	String SESSION_ATTRIBUTE_INDUSTRY_CATEGORY = "industryCategory";

	String SERVICE_RELATED_LIST = "serviceRelatedList";

	String QUERY_TYPE = "queryType";

	String BACKlINE_EMP_LIST = "empList";

	String SERVICE_REQUEST_FOR_SERVICE_REQ_SERVICE = "serviceRequestForServiceReqService";

	String SERVICE_REQUEST_FOR_CONSIGNMENT = "serviceRequestForConsignment";

	String COMPLAINTS_QUERY_TYPE = "COMPLAINTS_QUERY_TYPE";

	String COMPLAINTS_COMMON_SERVICE = "complaintsCommonService";

	String CUSTOMER_CATEGORY_LIST = "custCategoryList";

	String REQ_PARAM_SERVICE_QUERY_TYPE = "serviceQueryType";
	String REQ_PARAM_SERVICE_QUERY_TYPE_MAP = "serviceQueryTypeMap";

	String PRODUCT_LIST = "productTO";
	String REQ_PARAM_PRODUCT_MAP = "productMap";
	String CONSG_TYPES = "consgTypes";
	String CONSG_TYPE_MAP = "consgTypesMap";
	String REQ_PARAM_CITY_DTLS = "cityDtlsList";
	String REQ_PARAM_BRANCH_DTLS = "branchDtlsList";
	
	String REQ_PARAM_TRANSFER_LINK = "complaintTransfer";
	String SESSION_PARAM_TRANSFER_DTLS = "complaintTransferDtls";
	String SESSION_PARAM_RHO_MAP = "allRegionMap";

	String COMPLAINTS_CUSTOMER_CATEGORY = "COMPLAINTS_CUST_CATOGARY";

	String EMPLOYEE_DTLS_TO_LIST = "EmpDtl";

	String PAPERWORK_DTLS_TO_LIST = "paperwork";

	String QRY_GET_EMPLOYEES_DTLS_BY_DESIGNATION_TYPE = "getEmployeesDtlsByDesignationType";

	String QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_EMP_DESIGN = "getEmployeesDtlsByDepartmentTypeAndEmpDesign";

	String SAVING_COMPLAINTS_DETAILS = "CM001";

	String ERROR_IN_SAVING_COMPLAINTS_DETAILS = "CM002";

	String SUCCESS_FORWARD = "success";

	String SUCCESS_PAPER_WORK = "successPaperWork";

	String ERROR_IN_GETTING_BACKLINE_SUMMARY_STATUS = "CM003";

	String QRY_GET_EMP_USERDO_BY_USERID = "getEmpUserDObyUserId";
	String QRY_GET_ALL_OFFICE_ID_BY_CITY = "getAllOfficeIdByCity";

	String USER_ID = "userId";

	String QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_NO = "getServiceRequestByServiceRequestNo";

	String SERVICE_REQUEST_NO = "serviceRequestNo";

	String QRY_GET_COMPLAINTS_CUSTOMER_TYPE = "getComplaintsCustomerType";
	String QRY_GET_SERVICE_REQ_MAX_NUMBER = "getMaxServiceRequestNumber";

	String REQ_PARAM_COMPLAINT_CUST_TYPE = "complaintCustomerType";
	String REQ_PARAM_COMPLAINT_CUST_TYPE_MAP = "complaintCustomerTypeMap";
	String REQ_PARAM_COMPLAINT_TYPE = "complaintType";
	String REQ_PARAM_COMPLAINT_TYPE_MAP = "complaintTypeMap";
	String REQ_PARAM_EMP_ID = "empId";

	String QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_STATUS = "getServiceRequestByServiceRequestStatus";
	
	String QRY_GET_SERVICEREQUEST_DTLS_BYSTATUS_AND_ASSIGNED_EMP="getServiceRequestDtsStatusAndAssignedEmp";

	String SERVICE_REQUEST_STATUS = "statusName";

	String REQ_PARAM_CITY_WITH_PINCODE_DTLS = "cityIdWithPincodeList";
	String REQ_PARAM_WITH_PINCODE_DTLS = "pincodeList";

	/***** Critical Constants START *****/
	// Error Code
	String DTLS_NOT_FOUND = "CM005";
	String DTLS_NOT_SAVED_SUCCESSFULLY = "CM006";
	String FILE_NOT_UPLOADED_SUCCESSFULLY = "CM007";
	String DTLS_SAVED_SUCCESSFULLY = "CM008";
	String UPLOADED_FILE_NOT_LOAD = "CM013";

	// Constants
	String VIEW_CRITICAL_COMPLAINT = "viewCriticalComplaint";
	String STD_TYPE_INFORMATION_GIVEN_TO = "INFORMATION_GIVEN_TO";
	String STD_TYPE_CLAIM_COMPLAINTS_STATUS = "CLAIM_COMPLAINTS_STATUS";
	String STD_TYPE_LEGAL_COMPLAINTS_STATUS = "LEGAL_COMPLAINTS_STATUS";
	String STD_TYPE_CRITICAL_COMPLAINTS_STATUS = "CRTLS_CMPLTS_STATUS";
	
	// Parameters
	String PARAM_TODAY_DATE = "TODAY_DATE";
	String PARAM_YES = "YES";
	String PARAM_NO = "NO";
	String PARAM_INFO_GIVEN_TO = "infoGivenTo";
	String PARAM_CLAIM_COMPLAINT_STATUS_LIST = "claimComplaintStatusList";
	String PARAM_LEGAL_COMPLAINT_STATUS_LIST = "legalComplaintStatusList";
	String PARAM_CRITICAL_COMPLAINT_STATUS = "criticalCmpltStatus";
	
	String QRY_GET_EMPLOYEES_DTLS_BY_ROLE_OFFICE = "getAllEmployeesByRoleAndOffice";
	String PARAM_COMPLAINT_NO = "complaintNo";
	String PARAM_SERVICE_REQUEST_NO = "serviceRequestNo";
	String PARAM_COMPLAINT_ID = "complaintId";
	String PARAM_SERVICE_REQUEST_COMPLAINT_ID = "serviceRequestComplaintId";
	String PARAM_CRITICAL_COMPLAINT_URL = "criticalComplaintURL";

	String SERVICE_REQUEST_TYPE_FOR_SERVICE = "SREQ";
	String SERVICE_REQUEST_TYPE_FOR_CONSG = "CNO";
	String SERVICE_REQUEST_TYPE_FOR_BOOKING_REF = "BREFNO";
	String SERVICE_REQUEST_TYPE_FOR_CONTACT_NO = "CTNO";
	
	String SERVICE_REQUEST_QUERY_TYPE_CN = "C";
	String SERVICE_REQUEST_QUERY_TYPE_SR = "S";
	String SERVICE_REQUEST_QUERY_TYPE_BOTH_CN_AND_SR = "B";

	int SERVICE_REQUEST_NUMBER_LENGTH = 12;
	int SERVICE_REQUEST_NUMBER_RUNNING_LENGTH = 4;

	// Queries
	String QRY_IS_CRITICAL_COMPLAINT_EXIST = "isCriticalComplaintExist";

	/***** Critical Constants END *****/

	String QRY_GET_SERVICE_REQUEST_BY_USER = "getServiceRequestsByUser";

	String EMPLOYEE_ID = "employeeId";

	// Critical Claim Complaint Starts

	String VIEW_CRITICAL_CLAIM_COMPLAINT = "viewCriticalClaimComplaint";

	String PARAM_LOCAL = "LOCAL";

	String PARAM_CORP = "CORP";

	String COMPLAINT_NO = "serviceRequestNo";

	String ERROR_IN_GETTING_SERVICE_REQUEST_TRANSFER_LIST = "CM007";

	String SERVICE_REQUEST_TRANSFER_LIST = "serviceRequestTransferList";

	String QRY_PARAM_GET_CRITICAL_CLAIM_COMPLAINT_DTLS = "getCriticalClaimComplaintDtls";

	String COMPLAINT_BOOKING_NO_TYPE_CN = "CN";
	String COMPLAINT_BOOKING_NO_TYPE_REF = "RN";

	String SERVICE_REQUEST_TO_LIST = "serviceRequestTOList";

	String FILE_NAME_FOR_LEGAL_COMPLAINT = "Advocate_Notice";

	String PARAM_CRITICAL_CLAIM_COMPLAINT_URL = "criticalClaimComplaintURL";

	String QRY_GET_SERVICE_REQUEST_BY_SERVICE_REQUEST_ID = "getServiceRequestByServiceRequestId";

	String SERVICE_REQUEST_ID = "serviceRequestId";

	/** File Description constants START */
	String COMPLAINT_LETTER = "Complaint Letter";
	String MAIL_COPY = "Mail Copy";
	String CONSIGNOR_COPY = "Consignor Copy";
	String UNDERTAKING_LETTER = "Undertaking Letter";
	String INVOICE_COPY = "Invoice Copy";
	String ADVOCATE_NOTICE_FROM_CLIENT = "Advocate Notice From Client";
	String MAILER_FILE = "Mailer File";
	/** File Description constants END */

	String COMPLAINTS_FILE_LIST = "complaintsFileList";
	String PARAM_PAPER_WORK_ID = "paperWorkId";
	String QRY_GET_PAPER_WORK_BY_PAPER_WORK_ID = "getPaperworkByPaperWorkId";
	String PARAM_CONS_NO = "consignNo";
	String PARAM_COMPLAINT_STATUS = "complaintStatus";

	String REGION_ID = "regionId";

	String EMPLOYEE_BACKLINE_EX_ROLE = "BackLine Exe";

	String ERROR_EMPLOYEE_DETAILS_NOT_AVAILALE = "CM017";
	
	String INDEX = "index";

	String STATUS_CODE_FOLLOWUP = "FLW";
}
