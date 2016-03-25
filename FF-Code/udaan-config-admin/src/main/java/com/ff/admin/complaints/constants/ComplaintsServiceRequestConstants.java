/**
 * 
 */
package com.ff.admin.complaints.constants;

import com.capgemini.lbs.framework.constants.FrameworkConstants;

/**
 * @author mohammes
 *
 */
public interface ComplaintsServiceRequestConstants {


	/*** constants complaint Query type/service related for CN */
	String SERVICE_REQUEST_CONSG_QUERY_TYPE_COMPLAINT = "CMP";
	String SERVICE_REQUEST_CONSG_QUERY_TYPE_POD = "POD";
	String SERVICE_REQUEST_CONSG_QUERY_TYPE_CRITICAL_COMPLAINT = "CCMP";
	String SERVICE_REQUEST_CONSG_QUERY_TYPE_ESCALATION_COMPLAINT = "CRTE";
	String SERVICE_REQUEST_CONSG_QUERY_TYPE_FINANCIAL_COMPLAINT = "CRTF";

	/*** constants complaint Query type/service related for Service */
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_TARIFF_ENQUIRY = "TRF";
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_SERVICE_CHECK = "SRVCHK";
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_GENERAL_INFO = "GINF";
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_EMOTIONAL_BOND = "EMB";

	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_LEAD_CALL = "LC";
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_PICKUP_CALL = "PC";
	String SERVICE_REQUEST_SERVICE_QUERY_TYPE_PAPERWORK = "PPWRK";

	/*** constants complaint status*/
	String COMPLAINT_STATUS_RESOLVED = "RVD";
	String COMPLAINT_STATUS_BACKLINE = "BKL";
	String COMPLAINT_STATUS_FOLLOWUP = "FLW";
	
	String COMPLAINT_SOURCE_OF_QUERY_PHONE = "P";
	String COMPLAINT_SOURCE_OF_QUERY_EMAIL = "E";
	String COMPLAINT_SOURCE_OF_QUERY_SMS = "S";
	String COMPLAINT_SOURCE_OF_QUERY_LETTER = "L";
	
	String REQ_PARAM_WEIGHT ="weight";
	String REQ_PARAM_PRODUCT ="product";
	String REQ_PARAM_CUSTOMER_TYPE ="customerType";
	String REQ_PARAM_CONSIGNMENT_TYPE ="consgType";
	String REQ_PARAM_QUERY_TYPE ="queryType";
	
	String RATE_SERVICE_ON ="A,B";
	
	String COMPLAINT_KEY ="Complaint";
	String COMPLAINT_CATEGORY_ERROR_KEY = COMPLAINT_KEY+FrameworkConstants.EMPTY_STRING_WITH_SPACE+"Category";
	
	String COMPLAINT_STATUS_ERROR_KEY = COMPLAINT_KEY+FrameworkConstants.EMPTY_STRING_WITH_SPACE+"Status";
	
	String COMPLAINT_SERVICE_RELATED_ERROR_KEY = "Service Related";

}
