/**
 * 
 */
package com.ff.admin.complaints.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.CriticalComplaintTO;

/**
 * @author hkansagr
 */
public interface CriticalComplaintService {

	/**
	 * To save or update critical complaint details
	 * 
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void saveOrUpdateCriticalComplaint(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get service request details
	 * 
	 * @param to
	 * @return criticalComplaintTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CriticalComplaintTO getServiceRequestDtls(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException;

	/**
	 * To search critical complaint details
	 * 
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	CriticalComplaintTO searchCriticalComplaint(CriticalComplaintTO to)
			throws CGBusinessException, CGSystemException;

	/**
	 * To check whether complaint no already saved as critical complaint or not.
	 * 
	 * @param complaintNo
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean isCriticalComplaintExist(String complaintNo)
			throws CGBusinessException, CGSystemException;

}
