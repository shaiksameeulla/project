package com.ff.admin.leads.constants;

/**
 * @author sdalli
 *
 */
public interface LeadCommonConstants {
	String SUCCESS = "Success";
	
	String LEAD_NUMBER_START_CODE="L";
	
	String LEAD_NUMBER = "LEAD_NUMBER";
	
	String LEAD_TYPE = "LEAD_TYPE";
	
	String LEAD_STATUS_CODE="leadStatusCode";
	
	String LEAD_ID="leadId";
	
	String LEAD_LEAD_SOURCE="LEAD_LEAD_SOURCE";
	String LEAD_INDUSTRY_CATEGORY="LEAD_INDUSTRY_CATEGORY";
	String LEAD_COMPETITOR_PRODUCT ="LEAD_COMPETITOR_PRODUCT";
	String LEAD_LEAD_FEEDBACK="LEAD_LEAD_FEEDBACK";
	
	
	String DEPARTMENT_NAME_TYPE="Sales";
	String DESIGNATION_TYPE="SE";
	
	
	String DEPARTMENT_NAME ="departmentName";
	String DESIGNATION="designation";
	String OFFICEID="officeId";
	String OFFICE_TYPE_ID="officeTypeId";
	String REGION_ID="regionId";
	String LEADNUMBER="leadNumber";
	
	
	String QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE="getEmployeesDtlsByDepartmentType";
	
	String QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_EMP_DESIGN="getEmployeesDtlsByDepartmentTypeAndEmpDesign";
	
	String QRY_GET_REGIONAL_BRANCHES_BY_REGIONID_AND_OFFICE_TYPE_ID ="getRegionalBranchesByRegionIdAndOfficeTypeId";

	String USER_INFO = "user";
	

	String COMPETITOR_TO ="competitorTOs";
	
	String LEAD_MANAGEMENT = "LEAD_MANAGEMENT";
	
	String Stock_Standard_Type_TO = "stockStandardTypeTOs";
	
	String LEAD_SOURCE_LIST = "leadSourceList";
	
	String INDUSTRY_CATEGORY_LIST = "industryCategoryList";
	
	String OFFICETO_LIST = "officeTOList";
	
	String REGIONAL_SALES_PERSON_LIST = "salesPersonList";
	
	String SALES_PERSON_TITLE_LIST = "salesPersonTitleList";
	
	String COMPETITOR_PRODUCT_LIST = "competitorProductList";
	
	String LEADS_VIEW_SERVICE = "leadsViewService";
	
	String SALES_PERSON_DESIGNATION_LIST = "salesPersonDesignationList";
	
	String SALES_PERSON_DESIGNATION_SET = "salesPersonDesignationSet";
	
	String LEAD_LEAD_STATUS="LEAD_STATUS";
	
	String LEAD_STATUS_LIST = "leadStatusList";
	
	String PRODUCT_ROW_LIST = "productRowList";

	String SALES_EXECUTIVE_LIST = "salesExecutiveList";
	
	String QRY_GET_LEADS_BY_STATUS="getLeadsByStatus";
	
	String LEAD_STATUS = "New";
	
	String LEAD_APPROVED = "Approved";
	
	String LEAD_ACTIVE="Active";
	
	String LEAD_REJECTED = "Rejected";
	
	String LEAD_ON_HOLD = "On Hold";
	
	String LEAD_TO ="leadTOs";
	
	String QRY_GET_LEADS_BY_USER = "getLeadsByUser";
	
	String QRY_GET_LEADS_BY_USER_FOR_ALL_STATUS = "getLeadsByUserForAllStatus";
	
	String SALES_EXECUTIVE_ID = "salesExecutiveId";
	
	String USER_ID = "userId";
	
	String USER_ROLE = "userRole";
	
	String ROLE_ID = "roleId";
	
	String SALES_EXECUTIVE = "SALESEXECUTIVE";
	
	String CONTROL_TEAM_MEMBER = "CONTROLTEAM";
	
	String SAVING_LEADS_PLANNING = "LM001";
	String ERROR_IN_SAVING_LEADS_PLANNING = "LM002";
	String MESSAGE_DATE_MORE_THAN_1_MONTH="LM003";
	String MESSAGE_BACK_DATE="LM004";
	String ERROR_IN_GENERATING_LEAD_NUMBER = "LM005";
	String ERROR_IN_GETTING_COMPETITOR_LIST="LM006";
	String ERROR_IN_GETTING_INDUSTRY_CATEGORY_LIST="LM007";
	String ERROR_IN_GETTING_LEAD_SOURCE_LIST="LM008";
	String ERROR_IN_GETTING_REGIONAL_BRANCH_LIST="LM009";
	String ERROR_IN_GETTING_REGIONAL_SALES_PERSON_LIST="LM010";
	String ERROR_IN_GETTING_SALES_PERSON_TITLE_LIST="LM011";
	String ERROR_IN_GETTING_COMPETITOR_PRODUCT_LIST="LM012";
	String ERROR_IN_SAVING_LEAD="LM013";
	String ERROR_IN_GETTING_SALES_EXECUTIVE_LIST="LM014";
	String ERROR_IN_GETTING_LEADS_DETAILS="LM015";
	String ERROR_IN_GETTING_LEADS_FEEDBACK_DETAILS="LM016";
	String SYSTEM_EXCEPTION="LM017";
	String LEAD_NUMBER_IS_NOT_VALIED="LM018";
	String LEAD_APPROVED_SUCCESSFULLY="LM019";
	String LEAD_NOT_APPROVED="LM020";
	String LEAD_REJECTED_SUCCESSFULLY="LM021";
	String LEAD_COULD_NOT_BE_REJECTED="LM022";
	String LEAD_IS_PUT_ON_HOLD_SUCCESSFULLY="LM023";
	String LEAD_COULD_NOT_BE_PUT_ON_HOLD="LM024";
	String NO_LEADS_AVAILABLE_FOR_PROCESSING="LM025";
	String ERROR_IN_GETTING_LEADS_STAUS_LIST="LM026";
	String ERROR_IN_GETTING_USER_ROLES="LM027";
	String ERROR_IN_GETTING_FEEDBACK_LIST="LM028";
	String ERROR_IN_GETTING_PLAN_DETAILS="LM029";
	String NO_LEADS_AVAILABLE="LM030";
	String NO_SALES_EXECUTIVE_PRESENT="LM031";
	String CREATE_PLAN_BEFORE_FEEDBACK="LM032";
	String NO_SALES_EXECUTIVE_FOR_SELECTD_BRANCH="LM033";
	String ERROR_IN_SAVING_FEEDBACK_DETAILS="LM034";
	String ERROR_IN_SENDING_EMAIL="Email could not be sent";
	String ERROR_IN_SENDING_SMS="SMS could not be sent successfully";
	
	String FAILURE = "Failure";
	
	String QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_OFFICE_ID="getEmployeesDtlsByDepartmentTypeAndOfficeId";
	
	String QRY_GET_LEADS_BY_REGION="getLeadsByRegion";
	
	String QRY_GET_LEADS_BY_REGION_AND_STATUS = "getLeadsByRegionAndStatus";
	
	String QRY_GET_LEADS_PLANNING_FEEDBACK_DTLS_BY_LEADNUMBER_IN_TIME_DESC_ORDER="getLeadsPlanningFeedbackDtlsbyLeadNumberInTimeDescOrder";
	
	String OFFICE_ID = "officeId";
	
	String QRY_GET_EMPLOYEES_DTLS_BY_DEPARTMENT_TYPE_AND_REGION_ID="getEmployeesDtlsByDepartmentTypeAndRegionId";
	
	String QRY_UPDATE_LEADS_STATUS_BY_LEAD_ID ="updateLeadsStatusByLeadId";
	
	String EMAIL = "Email";
	
	String SMS = "SMS";
	
	String SMS_SALES_EXECUTIVE = "salesExecutive";
	
	String SMS_CUSTOMER = "customer";
	
	String DESIGNATION_LIST = "designationList";
	
	String PLAN = "PLAN";
	
	String FEEDBACK = "FEEDBACK";
	
	String LEAD_FEEDCODE_SAVE = "FeedBackSave";
	
	String QRY_GET_ALL_BRANCHES_UNDER_REPORTING_RHO ="getAllBranchesUnderRepotingRHO";
	
	String QRY_GET_ALL_BRANCHES_UNDER_REPORTING_HUB ="getAllBranchesUnderRepotingHub";
	
	String QRY_GET_ALL_BRANCHES_UNDER_CORPORATE_OFFICE ="getOfficeNameByOfficeId";
	
	String QRY_GET_USER_RIGHT_BY_USER_ID="getUserRoles";
	
	String VIEW_RATE_QUOTATION = "viewRateQuotation";
	
	String VIEW_ECOMMERCE_QUOTATION = "viewEcommerceRateQuotation";
	
	String QRY_GET_OFFICES_MAPPED_TO_USER ="getOfficesMappedToUser";
	
}


