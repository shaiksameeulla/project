/**
 * 
 */
package com.ff.admin.stockmanagement.stocktransfer.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;

/**
 * The Interface StockTransferService.
 *
 * @author mohammes
 */
public interface StockTransferService {

	/**
	 * Save transfer details.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveTransferDetails(StockTransferTO to)throws CGBusinessException,CGSystemException;

	/**
	 * Find transfer details.
	 *
	 * @param to the to
	 * @return the stock transfer to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockTransferTO findTransferDetails(StockTransferTO to) throws CGBusinessException,
			CGSystemException;
}
