package com.ff.admin.stockmanagement.stockcancel.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;

/**
 * The Interface StockCancellationService.
 */
public interface StockCancellationService {


	/**
	 * Save cancellation.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveCancellation(StockCancellationTO to)throws CGBusinessException,CGSystemException;

	/**
	 * Find details by cancellation number.
	 *
	 * @param to the to
	 * @return the stock cancellation to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockCancellationTO findDetailsByCancellationNumber(StockCancellationTO to)throws CGBusinessException,CGSystemException;  

}
