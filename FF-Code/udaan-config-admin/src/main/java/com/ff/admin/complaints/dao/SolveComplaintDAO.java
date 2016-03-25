/**
 * 
 */
package com.ff.admin.complaints.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author abarudwa
 *
 */
public interface SolveComplaintDAO {
	
	public ServiceRequestDO getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException;
	
	public ServiceRequestDO saveServiceRequestDetails(ServiceRequestDO serviceRequestDO) throws CGSystemException;
	
	public ServiceRequestDO getComplaintDetailsByServiceRequestId(
			Integer serviceRequestId) throws CGSystemException;

}
