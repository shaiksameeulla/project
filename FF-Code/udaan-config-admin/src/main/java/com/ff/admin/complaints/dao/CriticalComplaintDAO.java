package com.ff.admin.complaints.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.CriticalComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author hkansagr
 */
public interface CriticalComplaintDAO {

	/**
	 * To save or update critical complaint details
	 * 
	 * @param domain
	 * @throws CGSystemException
	 */
	void saveOrUpdateCriticalComplaint(ServiceRequestComplaintDO domain)
			throws CGSystemException;

	/**
	 * To get service request details by complaint no.
	 * 
	 * @param to
	 * @return serviceRequestDO
	 * @throws CGSystemException
	 */
	ServiceRequestDO getServiceRequestDtls(CriticalComplaintTO to)
			throws CGSystemException;

	/**
	 * To search critical complaint details
	 * 
	 * @param to
	 * @return serviceRequestComplaintDO
	 * @throws CGSystemException
	 */
	ServiceRequestComplaintDO searchCriticalComplaint(CriticalComplaintTO to)
			throws CGSystemException;

	/**
	 * To check whether complaint no already saved as critical complaint or not.
	 * 
	 * @param complaintNo
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean isCriticalComplaintExist(String complaintNo)
			throws CGSystemException;

}
