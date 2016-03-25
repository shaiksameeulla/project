/**
 * 
 */
package com.ff.admin.stockmanagement.stockreceipt.constants;

/**
 * The Interface StockReceiptConstants.
 *
 * @author mohammes
 */
public interface StockReceiptConstants {
 
	/** The transaction type. */
	String TRANSACTION_TYPE="transType";
	
	/** The receipt item dtls id. */
	String RECEIPT_ITEM_DTLS_ID="receptItemDtlId";
	
	/** The qry series received with req number. */
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER = "isSeriesAlreadyReceivedWithReqNumber";
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_WITH_RANGE = "isSeriesAlreadyReceivedWithReqNumberWithRange";
	
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEM_TYPE = "isSeriesAlreadyReceivedWithReqNumberUnderItemType";
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEM_TYPE_WITH_RANGE = "isSeriesAlreadyReceivedWithReqNumberUnderItemTypeWithRange";
	
	/** The qry series received with req number exclude id. */
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_EXCLUDE_ID = "isSeriesAlreadyReceivedWithReqNumberExcludeId";
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_EXCLUDE_ID_WITH_RANGE = "isSeriesAlreadyReceivedWithReqNumberExcludeIdWithRange";
	
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID = "isSeriesAlreadyReceivedWithReqNumberUnderItemTypeExcludeId";
	String QRY_SERIES_RECEIVED_WITH_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID_WITH_RANGE = "isSeriesAlreadyReceivedWithReqNumberUnderItemTypeExcludeIdWithRange";
	
	/** The qry series received with other req number. */
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER = "isSeriesAlreadyReceivedWithOtherReqNumber";
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_WITH_RANGE = "isSeriesAlreadyReceivedWithOtherReqNumberWithRange";
	
	/** The qry series received with other req number itemtype. */
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE = "isSeriesAlreadyReceivedWithOtherReqNumberUnderItemType";
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_WITH_RANGE = "isSeriesAlreadyReceivedWithOtherReqNumberUnderItemTypeWithRange";
	
	/** The qry series received with other req number exclude id. */
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_EXCLUDE_ID = "isSeriesAlreadyReceivedWithOtherReqNumberExcludeId";
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_EXCLUDE_ID_WITH_RANGE = "isSeriesAlreadyReceivedWithOtherReqNumberExcludeIdWithRange";
	
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID = "isSeriesAlreadyReceivedWithOtherReqNumberUnderItemTypeExcludeId";
	String QRY_SERIES_RECEIVED_WITH_OTHER_REQ_NUMBER_ITEMTYPE_EXCLUDE_ID_WITH_RANGE = "isSeriesAlreadyReceivedWithOtherReqNumberUnderItemTypeExcludeIdWithRange";
	
	/** The qry param receipt dtls id. */
	String QRY_PARAM_RECEIPT_DTLS_ID="receiptItemDtlsId";
	
	/** The qry series received with issue number. */
	String QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER ="isSeriesAlreadyReceivedWithIssueNumber";
	String QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_WITH_RANGE ="isSeriesAlreadyReceivedWithIssueNumberWithRange";
	
	/** The qry series received with issue number exclude id. */
	String QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_EXCLUDE_ID ="isSeriesAlreadyReceivedWithIssueNumberExcludeId";
	String QRY_SERIES_RECEIVED_WITH_ISSUE_NUMBER_EXCLUDE_ID_WITH_RANGE ="isSeriesAlreadyReceivedWithIssueNumberExcludeIdWithRange";
	
	/** The qry get issue dtl id for receipt. */
	String QRY_GET_ISSUE_DTL_ID_FOR_RECEIPT="getIssueItemDtlIdWithIssueNumberForReceipt";
	
	String QRY_IS_REQUISTION_ISSUED="isRequistionNumberIssued";
	String QRY_CONSOLIDATED_REQ_DTLS_BY_REQNUMBER="getConsolidatedReqDtlsByReqNumber";
}
