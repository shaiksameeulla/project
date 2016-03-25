package com.ff.admin.stockmanagement.stockissue.dao;

import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;

/**
 * The Interface StockIssueDAO.
 */
public interface StockIssueDAO {
	
	/**
	 * Save issue dtls.
	 *
	 * @param domainEntity the domain entity
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveIssueDtls(StockIssueDO domainEntity) throws CGSystemException;
	
	/**
	 * Find details by requisition number.
	 *
	 * @param to the to
	 * @return the stock requisition do
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionDO findDetailsByRequisitionNumber(StockIssueTO to) throws CGSystemException;
	
	/**
	 * Find details by issue number.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueDO findDetailsByIssueNumber(StockIssueTO to) throws CGSystemException;
	
	/**
	 * Update stock issue branch.
	 *
	 * @param entityDO the entity do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateStockIssueBranch(StockIssueDO entityDO)
			throws CGSystemException;
	
	/**
	 * Save issue employee dtls.
	 *
	 * @param domainEntity the domain entity
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveIssueEmployeeDtls(StockIssueDO domainEntity) throws CGSystemException;
	
	/**
	 * Find issue employee dtls.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueDO findIssueEmployeeDtls(StockIssueEmployeeTO to) throws CGSystemException;
}
