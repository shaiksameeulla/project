/**
 * 
 */
package com.ff.admin.stockmanagement.common.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;

/**
 * @author mohammes
 *
 */
public interface StockScheduledDAO {

	List<StockIssuePaymentDetailsDO> getStockPaymentDetails()throws CGSystemException;
	
	Integer updateStockPaymentDetails(List<StockIssuePaymentDetailsDO> paymentDtls)throws CGSystemException;

	/**
	 * Save collection details.
	 *
	 * @param collectionDO the collection do
	 * @throws CGSystemException the cG system exception
	 */
	void saveCollectionDetails(CollectionDO collectionDO)
			throws CGSystemException;

	/**
	 * Update stock payment details.
	 *
	 * @param paymentDO the payment do
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	Integer updateStockPaymentDetails(StockIssuePaymentDetailsDO paymentDO)
			throws CGSystemException;
	
}
