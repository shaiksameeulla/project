/**
 * 
 */
package com.ff.admin.stockmanagement.common.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;

/**
 * @author mohammes
 *
 */
public interface StockScheduledService {

	List<StockIssuePaymentDetailsDO> getStockPaymentDetails()throws CGBusinessException,CGSystemException;

	/**
	 * Gets the payment mode type for collection.
	 *
	 * @param paymntDtlsListDO the paymnt dtls list do
	 * @return the payment mode type for collection
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<String, Integer> getPaymentModeTypeForCollection()
			throws CGBusinessException, CGSystemException;

	/**
	 * Creates the expense from stock payment.
	 *
	 * @param paymentTypeMap the payment type map
	 * @param paymntDO the paymnt do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void createExpenseFromStockPayment(Map<String, Integer> paymentTypeMap,
			StockIssuePaymentDetailsDO paymntDO) throws CGBusinessException,
			CGSystemException;
	
}
