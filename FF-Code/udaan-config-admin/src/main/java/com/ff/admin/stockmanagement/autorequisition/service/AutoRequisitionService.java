/**
 * 
 */
package com.ff.admin.stockmanagement.autorequisition.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * The Interface AutoRequisitionService.
 *
 * @author mohammes
 */
public interface AutoRequisitionService {

	/**
	 * Generate auto requisition by office.
	 *
	 * @param officeId the office id
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public Boolean generateAutoRequisitionByOffice(Integer officeId)throws CGBusinessException,CGSystemException;

	public List<String> getAllRHOCodes() throws CGSystemException, CGBusinessException;

	public Boolean consolidateAutoRequisitionByRHO(String rhoCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the office dtls for auto req.
	 *
	 * @return the office dtls for auto req
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public List<Integer> getOfficeDtlsForAutoReq() throws CGSystemException,
			CGBusinessException;
}
