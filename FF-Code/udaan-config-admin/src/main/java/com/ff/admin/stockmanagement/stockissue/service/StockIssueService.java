package com.ff.admin.stockmanagement.stockissue.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.to.stockmanagement.StockUserTO;
import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockIssueService.
 */
public interface StockIssueService {
	
	/**
	 * Save issue dtls for branch.
	 *
	 * @param stockIssueTo the stock issue to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveIssueDtlsForBranch(StockIssueTO stockIssueTo) throws CGBusinessException, CGSystemException;
	
	/**
	 * Save issue employee dtls.
	 *
	 * @param issueEmpTo the issue emp to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveIssueEmployeeDtls(StockIssueEmployeeTO issueEmpTo) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Find issue employee dtls.
	 *
	 * @param issueEmpTo the issue emp to
	 * @return the stock issue employee to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueEmployeeTO findIssueEmployeeDtls(StockIssueEmployeeTO issueEmpTo) 
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Find details by requisition number.
	 *
	 * @param stockIssueTo the stock issue to
	 * @return the stock issue to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueTO findDetailsByRequisitionNumber(StockIssueTO stockIssueTo) throws CGBusinessException,CGSystemException;
	
	/**
	 * Find details by issue number.
	 *
	 * @param stockIssueTo the stock issue to
	 * @return the stock issue to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueTO findDetailsByIssueNumber(StockIssueTO stockIssueTo) throws CGBusinessException,CGSystemException;
	
	/**
	 * Gets the receipient details.
	 *
	 * @param validattionTo the validattion to
	 * @return the receipient details
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	StockUserTO getReceipientDetails(StockValidationTO validattionTo) throws CGSystemException,
	CGBusinessException;
	
	/**
	 * Update issue dtls for branch.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateIssueDtlsForBranch(StockIssueTO to)
			throws CGBusinessException, CGSystemException;
}
