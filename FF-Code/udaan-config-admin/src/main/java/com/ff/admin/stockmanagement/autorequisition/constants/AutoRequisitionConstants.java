/**
 * 
 */
package com.ff.admin.stockmanagement.autorequisition.constants;

/**
 * The Interface AutoRequisitionConstants.
 *
 * @author mohammes
 */
public interface AutoRequisitionConstants {
	
	/** The qry param stock id. */
	String QRY_PARAM_STOCK_ID= "stockId";
	
	/** The qry param auto req status. */
	String QRY_PARAM_AUTO_REQ_STATUS = "autoReqStatus";
	
	/** The qry param is auto req required. */
	String QRY_PARAM_IS_AUTO_REQ_REQUIRED = "isAutoRequisitonRequired";
	
	/** The auto req status yes. */
	String AUTO_REQ_STATUS_YES = "Y";
	
	/** The auto req status no. */
	String AUTO_REQ_STATUS_NO = "N";
	
	/** The qry get stock dtls for auto req. */
	String QRY_GET_STOCK_DTLS_FOR_AUTO_REQ = "getStockDtlsForAutoReq";
	
	/** The qry update status for auto req. */
	String QRY_UPDATE_STATUS_FOR_AUTO_REQ = "updateStatusForAutoReq";
	
	/** The qry get office dtls for auto req. */
	String QRY_GET_OFFICE_DTLS_FOR_AUTO_REQ = "getOfficeDtlsForAutoReq";
	
	String QRY_GET_ALL_RHO_CODES ="getAllRhoCodes";
	
	String QRY_PARAM_OFFICE_TYPE ="officeType";
	
	String QRY_REQUISITION_DTLS_FOR_RHO ="getRequisitionDtlsForRHO";
	
	String QRY_PARAM_FOR_RHO_CODE ="rhoCode";
	
	String QRY_PARAM_IS_CONSOLIDATED  ="isConsolidated";
	
	String CONSOLIDATED_FLAG_YES =  AUTO_REQ_STATUS_YES;
	
	String CONSOLIDATED_FLAG_NO = AUTO_REQ_STATUS_NO;
	
	String STOCK_INTEGRATED_WITH_STOCK_YES=AUTO_REQ_STATUS_YES;
	String STOCK_INTEGRATED_WITH_STOCK_NO=AUTO_REQ_STATUS_NO;
}
