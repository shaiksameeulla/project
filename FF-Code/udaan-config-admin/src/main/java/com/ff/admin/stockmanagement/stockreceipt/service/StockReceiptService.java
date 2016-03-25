package com.ff.admin.stockmanagement.stockreceipt.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockReceiptService.
 */
public interface StockReceiptService {
	
	/**
	 * Save receipt dtls.
	 *
	 * @param receiptTO the receipt to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveReceiptDtls(StockReceiptTO receiptTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Find details by receipt number.
	 *
	 * @param receiptTO the receipt to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReceiptTO findDetailsByReceiptNumber(StockReceiptTO receiptTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Find details by issue number.
	 *
	 * @param toreceiptTO the toreceipt to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReceiptTO findDetailsByIssueNumber(StockReceiptTO toreceiptTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Find details by requisition number.
	 *
	 * @param receiptTO the receipt to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReceiptTO findDetailsByRequisitionNumber(StockReceiptTO receiptTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Update receipt dtls.
	 *
	 * @param receiptTO the receipt to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateReceiptDtls(StockReceiptTO receiptTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Checks if is valid series for receipt against pr.
	 *
	 * @param validationTO the validation to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String isValidSeriesForReceiptAgainstPR(StockValidationTO validationTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Checks if is valid series for receipt against issue.
	 *
	 * @param validationTO the validation to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String isValidSeriesForReceiptAgainstIssue(StockValidationTO validationTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Checks if is valid series for receipt.
	 *
	 * @param validationTO the validation to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String isValidSeriesForReceipt(StockValidationTO validationTO) throws CGBusinessException, CGSystemException;
}
