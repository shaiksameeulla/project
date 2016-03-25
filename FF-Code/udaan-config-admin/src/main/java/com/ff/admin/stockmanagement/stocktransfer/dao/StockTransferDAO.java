/**
 * 
 */
package com.ff.admin.stockmanagement.stocktransfer.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;

/**
 * The Interface StockTransferDAO.
 *
 * @author mohammes
 */
public interface StockTransferDAO {

	/**
	 * Find stock transfer dtls.
	 *
	 * @param to the to
	 * @return the stock transfer do
	 * @throws CGSystemException the cG system exception
	 */
	StockTransferDO findStockTransferDtls(StockTransferTO to)throws CGSystemException;
	
	/**
	 * Save stock transfer dtls.
	 *
	 * @param entity the entity
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveStockTransferDtls(StockTransferDO entity)throws CGSystemException;
}
