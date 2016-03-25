/**
 * 
 */
package com.ff.admin.stockmanagement.stockreturn.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;

/**
 * The Interface StockReturnService.
 *
 * @author cbhure
 */
public interface StockReturnService {


	/**
	 * Save return details.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveReturnDetails(StockReturnTO stockReturnTO)throws CGBusinessException,CGSystemException; 
	
	/**
	 * Find details by return number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock return to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReturnTO findDetailsByReturnNumber(StockReturnTO stockReturnTO)throws CGBusinessException,CGSystemException;

	/**
	 * Find details by issue number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock return to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReturnTO findDetailsByIssueNumber(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Find details by acknowledgement number.
	 *
	 * @param stockReturnTO the stock return to
	 * @return the stock return to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockReturnTO findDetailsByAcknowledgementNumber(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException;
}
