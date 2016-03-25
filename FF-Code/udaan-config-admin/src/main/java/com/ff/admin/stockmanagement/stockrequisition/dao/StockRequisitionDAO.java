/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;

/**
 * The Interface StockRequisitionDAO.
 *
 * @author mohammes
 */
public interface StockRequisitionDAO {
	
	/**
	 * Save stock requisition.
	 *
	 * @param domainEntity the domain entity
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveStockRequisition(StockRequisitionDO domainEntity)	throws  CGSystemException;
	
	/**
	 * Find requisition dtls by req number.
	 *
	 * @param requisitionTo the requisition to
	 * @return the stock requisition do
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionDO findRequisitionDtlsByReqNumber(StockRequisitionTO requisitionTo)throws CGSystemException;
	
	/**
	 * Find req dtls by req number for approve.
	 *
	 * @param requisitionTo the requisition to
	 * @return the stock requisition do
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionDO findReqDtlsByReqNumberForApprove(StockRequisitionTO requisitionTo)throws CGSystemException;
	
	/**
	 * Approve requisition.
	 *
	 * @param entityDO the entity do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean approveRequisition(StockRequisitionDO entityDO)throws CGSystemException;

	
	/**
	 * Search req dtls by requisiton office for rho view.
	 *
	 * @param requisitionTo the requisition to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<StockRequisitionDO> searchReqDtlsByRequisitonOfficeForRhoView(
			ListStockRequisitionTO requisitionTo) throws CGSystemException;

	
	/**
	 * Search all requisition details for rho view.
	 *
	 * @param requisitionTo the requisition to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<StockRequisitionDO> searchAllRequisitionDetailsForRhoView(
			ListStockRequisitionTO requisitionTo) throws CGSystemException;
	

}
