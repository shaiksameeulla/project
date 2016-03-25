package com.ff.admin.stockmanagement.stockcancel.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;

/**
 * The Interface StockCancellationDAO.
 */
public interface StockCancellationDAO {

	/**
	 * Save cancellation.
	 *
	 * @param cancelDO the cancel do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveCancellation(StockCancellationDO cancelDO) throws CGSystemException;

	/**
	 * Find details by cancellation number.
	 *
	 * @param stockCancelTO the stock cancel to
	 * @return the stock cancellation do
	 * @throws CGSystemException the cG system exception
	 */
	StockCancellationDO findDetailsByCancellationNumber(StockCancellationTO stockCancelTO)throws CGSystemException; 

}
