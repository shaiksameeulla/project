/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;

/**
 * The Interface StockRequisitionService.
 *
 * @author mohammes
 */
public interface StockRequisitionService {

	/**
	 * Save stock requisition.
	 *
	 * @param stockRequisitionTo the stock requisition to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveStockRequisition(StockRequisitionTO stockRequisitionTo)throws CGBusinessException,CGSystemException;
	
	
	/**
	 * Find requisition dtls by req number.
	 *
	 * @param stockRequisitionTo the stock requisition to
	 * @return the stock requisition to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionTO findRequisitionDtlsByReqNumber(StockRequisitionTO stockRequisitionTo)throws CGBusinessException,CGSystemException;
	
	/**
	 * Find req dtls by req number for approve.
	 *
	 * @param stockRequisitionTo the stock requisition to
	 * @return the stock requisition to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionTO findReqDtlsByReqNumberForApprove(StockRequisitionTO stockRequisitionTo)throws CGBusinessException,CGSystemException; 
	
	/**
	 * Approve stock requisition.
	 *
	 * @param stockRequisitionTo the stock requisition to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean approveStockRequisition(StockRequisitionTO stockRequisitionTo)throws CGBusinessException,CGSystemException;


	/**
	 * Search requisition details.
	 *
	 * @param to the to
	 * @return the list stock requisition to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	ListStockRequisitionTO searchRequisitionDetails(ListStockRequisitionTO to)
			throws CGBusinessException, CGSystemException;
}
