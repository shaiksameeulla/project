package com.ff.rate.configuration.ratecontract.constants;

import com.capgemini.lbs.framework.constants.CommonConstants;

public interface RateContractConstants {

	/** Billing Details START */
	String VIEW_RATE_CONTRACT_NORMAL = "viewRateContractNormal";
	String VIEW_RATE_CONTRACT_ECOMMERCE = "viewRateContractECommerce";
	String SEARCH_CUSTOMER_INFO = "searchCustomerInfo";
	String CONTRACT_TYPE_TYPE_NAME = "CONTRACT_TYPE";
	String BILLING_TYPE_TYPE_NAME = "BILLING_TYPE";
	String BILLING_CYCLE_TYPE_NAME = "BILLING_CYCLE";
	String BILLING_MODE_TYPE_NAME = "BILLING_MODE";
	String PAYMENT_TERM_TYPE_NAME = "PAYMENT_TERM";
	String OCTROI_BORN_BY_TYPE_NAME = "OCTROI_BORN_BY";
	String CONTRACT_FOR_TYPE_NAME = "CONTRACT_FOR";
	String CONTRACT_TYPE = "contractType";
	String BILLING_TYPE = "billingType";
	String BILLING_CYCLE = "billingCycle";
	String BILLING_MODE = "billingMode";
	String PAYMENT_TERM = "paymentTerm";
	String OCTROI_BORN_BY = "octroiBornBy";
	String CONTRACT_FOR = "contractFor";
	String NORMAL_CONTRACT = "N";
	String ECCOMERCE_CONTRACT = "E";
	String BILL_TYPE_DBDP = "DBDP";
	String BILL_TYPE_CBCP = "CBCP";
	String BILL_TYPE_DBCP = "DBCP";
	String HARD_COPY = "H";
	String MONTHLY_BILLING = "M";
	String OCTROI_BY_CE = "CE";
	String USER_INFO = "user";
	String PARAM_NORMAL_CONTRACT = "NORMAL_CONTRACT";
	String PARAM_BILL_TYPE_DBDP = "BILL_TYPE_DBDP";
	String PARAM_HARD_COPY = "HARD_COPY";
	String PARAM_MONTHLY_BILLING = "MONTHLY_BILLING";
	String PARAM_OCTROI_BY_CE = "OCTROI_BY_CE";
	String PARAM_TODAY_DATE = "TODAY_DATE";
	String RATE_CONTRACT_ID = "rateContractId";
	String QRY_GET_RATE_CONTRACT_BY_ID = "getRateContractById";
	String PARAM_NORM = "NORM";
	String PARAM_ECOM = "ECOM";
	String PARAM_FROM_DT = "fromDate";
	String PARAM_TO_DT = "toDate";
	String PARAM_BILL_CON_TYPE = "billContractType";
	String PARAM_BILL_TYPE = "billType";
	String PARAM_BILL_MODE = "billMode";
	String PARAM_BILL_CYCLE = "billCycle";
	String PARAM_PAYMENT_TERM = "paymentTerm";
	String PARAM_UPDATED_BY = "updatedBy";
	String PARAM_UPDATED_DT = "updatedDate";
	/** Billing Details END */
	String ONE = "1";
	String CONTRACT_NO = "CONTRACT_NO";
	String CHAR_C = "C";
	String CONTRACT_ID = "contractId";
	String RATE_CONTRACT_NO = "contractNo";
	String CREATED_BY = "createdBy";
	String QRY_SEARCH_CONTRACT = "searchContractDetails";
	String QRY_SEARCH_CUSTOMER_INFO = "rateCustomersByCustomerName";

	/** Pickup/Delivery Detail(s) START */
	String PARAM_PINCODE = "pincode";
	String ACTIVE = "A";
	String INACTIVE = "I";
	String BLOCKED = "B";
	String SUBMITTED = "S";
	String CONTRACT_TYPE_PICKUP = "P";
	String CONTRACT_TYPE_DELIVERY = "D";
	String LOCATION_TYPE_BILLING = CommonConstants.LOCATION_TYPE_BILLING;
	String LOCATION_TYPE_PAYMENT = CommonConstants.LOCATION_TYPE_PAYMENT;
	String LOCATION_TYPE_BILLING_PAYMENT = CommonConstants.LOCATION_TYPE_BILLING_PAYMENT;
	String PARAM_INACTIVE = "INACTIVE";
	String PARAM_ACTIVE = "ACTIVE";
	String PROCESS_CONTRACT_CUST_CODE = "CONTRACT_CUST_CODE";
	Integer ONE_INT = 1;
	String PARAM_PICKUP_CONTRACT_TYPE = "PICKUP_CONTRACT_TYPE";
	String PARAM_DLV_CONTRACT_TYPE = "DLV_CONTRACT_TYPE";
	String PARAM_BILL = "BILL";
	String PARAM_PAY = "PAY";
	// String BILL="B";
	// String PAY="P";
	String PARAM_NORMAL_CON = "NORMAL_CON";
	String PARAM_REVS_LOGI_CON = "REVS_LOGI_CON";
	String NORMAL_CON = "N";
	String REVS_LOGI_CON = "R";
	/** Pickup/Delivery Detail(s) END */

	/** error constants START */
	String CUST_BLOCKED = "RCN001";
	String CUST_UNBLOCKED = "RCN002";
	String CUST_NOT_BLOCK_OR_UNBLOCK = "RCN003";
	String ERR_DTLS_NOT_UPDATED = "Details Not Updated. Please Try Again";
	/** error constants END */
	String CREATED = "C";
	String QRY_UPDATE_STATUS = "updateStatus";

	String EXPIRY_CONTRACT_EMAIL_SUBJECT = "Imp-Contract About to Expire";
	String CONTRACT_SUBMITTED = "S";
	String CONTRACT_INACTIVE = "I";
	String RATE_CONTRACT_TYPE = "rateContractType";
	String QRY_UPDATE_RATE_CON_BILL_DTLS = "updateRateContractBillingDtls";
	String CUSTOMER_CODE = "customerCode";
	String QRY_GET_CONTRACT_BY_CUSTOMER_CODE = "getContractsByCustomerCode";

	String UPDATE_RATE_CONTRACT_RENEWED_STATUS = "updateContractRenewedStatus";
	String IS_RENEWED = "isRenewed";
	String YES = "Y";
	String VALID_TO_DATE = "validToDate";
	String CONTRACT_ACTIVE = "A";
	Integer Days90 = 90;
	Integer Days60 = 60;
	Integer Days30 = 30;
	Integer Days15 = 15;
	String ExpDays90 = "exp90Days";
	String ExpDays60 = "exp60Days";
	String ExpDays30 = "exp30Days";
	String ExpDays15 = "exp15Days";
	String NO_OF_DAYS = "noOfDays";
	String EXP_DAYS = "expDays";
	String CONTRACT_ALERT_SCREEN_CODE = "CNTALERT";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT_RO = "rateContractListViewForRHO";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT_HO = "rateContractListViewForHO";

	String QRY_GET_RATE_CONTRACT = "getRateContract";

	String QRY_GET_RATE_CONTRACT_FOR_USER = "getRateContractForUser";

	String QRY_GET_RATE_CONTRACT_FOR_RO = "rateContractForRHO";

	String QRY_GET_RATE_CONTRACT_FOR_HO = "rateContractForHO";

	// String QRY_GET_RATE_CONTRACT_FOR_ALL_RHO_WITH_CITY =
	// "rateContractForALLRHOWithCity";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT_RO_WITH_CITY = "rateContractListViewForALLRHOWithCity";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT = "rateContractListView";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT_FOR_CO = "rateContractListViewForCO";

	String QRY_GET_RATE_CONTRACT_FOR_CO = "getRateContractForCO";

	String PARAM_USER_ID = "userId";

	String PARAM_FROM_DATE = "fromDate";

	String PARAM_TO_DATE = "toDate";

	String PARAM_CONTRACT_NO = "rateContractNo";

	String PARAM_REGION = "region";

	String PARAM_CITY = "city";

	String PARAM_STATUS = "status";

	String LIST_VIEW = "listView";

	String QRY_GET_CONTRACT_BY_ORIGINATED_CONTRACT = "getContractByOriginatedContractId";
	String CONTRACT_LIST_V_STATUS = "CONTRACT_LIST_V_STATUS";
	String USER = "User";
	String USER_TYPE = "userType";
	String RATE_CONTRACT_NUMBER = "rateContractNo";
	String SUCCESS = "SUCCESS";
	String CUST_NAME = "custName";
	String CUST_TOS = "custTOs";
	String DAYS = "days";
	String NAME = "name";
	String CUSTOMER_NAME = "customerName";
	String END_DATE = "endDate";
	String CONTRACT_EXP_ALERT_EMAIL_VM = "contractExpiryAlertEmail.vm";
	String FROM_MAIL_ID = "test_user1@testfirstflight.com";

	String VIEW_RATE_NORMAL_CONTRACT = "viewNormalContract";
	String VIEW_RATE_ECOMMERCE_CONTRACT = "viewEcommerceContract";

	String QRY_GET_LIST_VIEW_RATE_CONTRACT_SE = "rateContractListViewForSE";

	String QRY_GET_RATE_CONTRACT_SE = "getRateContractForSE";

	String STATUS_LIST = "contrctStatusList";

	String COMPLAINT_LIST = "complaintList";

	String CONTRACT_COMPLAINT = "CONTRACT_COMPLAINT";

	String QRY_GET_CONTRACT_SPOC_DETAILS = "getRateContractSpocDetails";

	String QRY_UPDATE_CUSTOMER_CODE = "updateCustomerCodeBycustomerId";

	String QRY_UPDATE_CUSTOMER_TYPE = "updateCustomerType";

	String CUSTOMER_ID = "customerId";

	String CUSTOMER_TYPE_ID = "customerTypeId";

	String QRY_UPDATE_CUSTOMER_BILL_DTLS = "updateCustomerBillDtls";

	String SAP_STATUS = "sapStatus";

	String DISTRIBUTION_CHANNELS = "distributionChannels";

	String QRY_UPDATE_CONTRACT_NO_AND_DSTRB_CHNL = "updateContractNoAndDistributionChannel";


	String QRY_CONTRACT_LIST_FOR_EMAIL_FOR_AREA = "SELECT con.RATE_CONTRACT_ID,con.RATE_CONTRACT_NUMBER,con.VALID_TO_DATE,con.VALID_FROM_DATE, touser.EMAIL_ID,ccuser.EMAIL_ID,cus.BUSINESS_NAME,cus.CUSTOMER_CODE,touser.FIRST_NAME,touser.LAST_NAME FROM ff_d_rate_contract con,ff_d_customer cus, (SELECT emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.EMAIL_ID as EMAIL_ID, offc.OFFICE_ID as OFFICE_ID, emp.OFFICE as OFFICE FROM ff_d_employee emp, ff_d_employee_user empusr, ff_d_app_scrn scrn, ff_d_app_rights app, ff_d_user_rights rght, ff_d_user_office_rights offc WHERE scrn.SCREEN_CODE =? AND offc.USER_ID = empusr.USER_ID AND scrn.SCREEN_ID = app.SCREEN_ID AND app.ROLE_ID = rght.ROLE_ID AND rght.USER_ID = empusr.USER_ID AND empusr.EMPLOYEE_ID = emp.EMPLOYEE_ID and offc.MAPPED_TO = 'A') ccuser, (SELECT  emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.FIRST_NAME as FIRST_NAME,emp.LAST_NAME as LAST_NAME, emp.EMAIL_ID as EMAIL_ID FROM ff_d_employee emp) touser WHERE con.CUSTOMER = cus.CUSTOMER_ID AND touser.EMPLOYEE_ID=cus.SALES_PERSON AND con.CUSTOMER = cus.CUSTOMER_ID AND con.VALID_FROM_DATE < ? AND con.VALID_TO_DATE=? AND (cus.SALES_OFFICE= ccuser.OFFICE OR cus.SALES_OFFICE IN (SELECT rhoofc.OFFICE_ID FROM ff_d_office rhoofc where REPORTING_RHO in (ccuser.OFFICE_ID)) OR cus.SALES_OFFICE in (SELECT regofc.OFFICE_ID FROM ff_d_office regofc, ff_d_office ofcmap WHERE ofcmap.REPORTING_RHO = regofc.OFFICE_ID AND (regofc.OFFICE_ID = ccuser.OFFICE OR regofc.OFFICE_ID = ccuser.OFFICE_ID) AND regofc.OFFICE_TYPE_ID = (select OFFICE_TYPE_ID  from ff_d_office_type where OFFICE_TYPE_CODE = 'RO'))) ORDER BY VALID_FROM_DATE DESC";
	
	String QRY_CONTRACT_LIST_FOR_EMAIL_FOR_RHO = "SELECT con.RATE_CONTRACT_ID,con.RATE_CONTRACT_NUMBER,con.VALID_TO_DATE,con.VALID_FROM_DATE,touser.EMAIL_ID,ccuser.EMAIL_ID,cus.BUSINESS_NAME,cus.CUSTOMER_CODE, touser.FIRST_NAME,touser.LAST_NAME FROM ff_d_rate_contract con,ff_d_customer cus, (SELECT emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.EMAIL_ID as EMAIL_ID, offc.OFFICE_ID as OFFICE_ID, emp.OFFICE as OFFICE FROM ff_d_employee emp, ff_d_employee_user empusr, ff_d_app_scrn scrn, ff_d_app_rights app, ff_d_user_rights rght, ff_d_user_office_rights offc WHERE scrn.SCREEN_CODE =? AND offc.USER_ID = empusr.USER_ID AND scrn.SCREEN_ID = app.SCREEN_ID AND app.ROLE_ID = rght.ROLE_ID AND rght.USER_ID = empusr.USER_ID AND empusr.EMPLOYEE_ID = emp.EMPLOYEE_ID and offc.MAPPED_TO = 'R') ccuser, (SELECT  emp.EMPLOYEE_ID as EMPLOYEE_ID,emp.FIRST_NAME as FIRST_NAME,emp.LAST_NAME as LAST_NAME, emp.EMAIL_ID as EMAIL_ID FROM ff_d_employee emp) touser WHERE con.CUSTOMER = cus.CUSTOMER_ID AND touser.EMPLOYEE_ID=cus.SALES_PERSON AND con.CUSTOMER = cus.CUSTOMER_ID AND con.VALID_FROM_DATE < ? AND con.VALID_TO_DATE=? AND (cus.SALES_OFFICE= ccuser.OFFICE OR cus.SALES_OFFICE IN (SELECT rhoofc.OFFICE_ID FROM ff_d_office rhoofc where REPORTING_RHO in (ccuser.OFFICE_ID)) OR cus.SALES_OFFICE in (SELECT regofc.OFFICE_ID FROM ff_d_office regofc, ff_d_office ofcmap WHERE ofcmap.REPORTING_RHO = regofc.OFFICE_ID AND (regofc.OFFICE_ID = ccuser.OFFICE OR regofc.OFFICE_ID = ccuser.OFFICE_ID) AND regofc.OFFICE_TYPE_ID = (select OFFICE_TYPE_ID  from ff_d_office_type where OFFICE_TYPE_CODE = 'RO'))) ORDER BY VALID_FROM_DATE DESC";
	
	String DT_TO_BRANCH = "dtToBranch";
	
	String QRY_GET_EMPLOYEES_FOR_CONTRACT_ALERT = "getEmployeesforContractAlert";

	String OFFICE_ID_LIST = "officeIdList";
	
	String SCRN_CODE = "scrnCode";
	
	String RHO_OFFICE_ID = "rhoOfficeId";
	
	String OFFICE_ID = "officeId";
	
	String CORP_OFC_TYPE = "corpOfcType";
	
	String QRY_SEARCH_CONTRACT_ON_SEARCH_CLICK = "searchContractDetailsOnSearchClick";
	String IS_DELETE_PICKUP_OR_DLV_LOCATIONS = "isDeletePickupOrDlvLocations";
	String QRY_CLEAR_PICKUP_DLV_LOCATIONS = "clearPickupOrDlvLocations";
}
