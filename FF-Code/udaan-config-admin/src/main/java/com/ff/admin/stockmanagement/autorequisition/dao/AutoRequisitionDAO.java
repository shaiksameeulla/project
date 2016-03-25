/**
 * 
 */
package com.ff.admin.stockmanagement.autorequisition.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.StockOfficeMappingDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;

/**
 * The Interface AutoRequisitionDAO.
 *
 * @author mohammes
 */
public interface AutoRequisitionDAO {
	 
 	/**
 	 * Gets the office dtls for auto req.
 	 *
 	 * @return the office dtls for auto req
 	 * @throws CGSystemException the cG system exception
 	 */
 	List<Integer> getOfficeDtlsForAutoReq()throws CGSystemException;
	 
 	/**
 	 * Gets the stock dtls for auto req by office.
 	 *
 	 * @param officeId the office id
 	 * @return the stock dtls for auto req by office
 	 * @throws CGSystemException the cG system exception
 	 */
 	List<StockOfficeMappingDO> getStockDtlsForAutoReqByOffice(Integer officeId)throws CGSystemException;
	
	/**
	 * Update status for auto req.
	 *
	 * @param stockIdList the stock id list
	 * @return the integer
	 * @throws CGSystemException the cG system exception
	 */
	Integer updateStatusForAutoReq(List<Integer> stockIdList)throws CGSystemException;
	
	/**
	 * Save auto req.
	 *
	 * @param requisitionDO the requisition do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveAutoReq(StockRequisitionDO requisitionDO)throws CGSystemException;

	/**
	 * Gets the all rho codes.
	 *
	 * @return the all rho codes
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getAllRHOCodes() throws CGSystemException;

	/**
	 * Gets the requisition dtls for consolidation.
	 *
	 * @param rhoCode the rho code
	 * @return the requisition dtls for consolidation
	 * @throws CGSystemException the cG system exception
	 */
	List<StockRequisitionItemDtlsDO> getRequisitionDtlsForConsolidation(
			String rhoCode) throws CGSystemException;

	/**
	 * Update requisition consolidated flag.
	 *
	 * @param reqItemDtls the req item dtls
	 * @throws CGSystemException the cG system exception
	 */
	public void updateRequisitionConsolidatedFlag(
			List<StockRequisitionItemDtlsDO> reqItemDtls)
			throws CGSystemException;

	/**
	 * Gets the office by id.
	 *
	 * @param officeId the office id
	 * @return the office by id
	 * @throws CGSystemException the cG system exception
	 */
	OfficeDO getOfficeById(Integer officeId) throws CGSystemException;
}
