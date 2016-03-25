/**
 * 
 */
package com.ff.admin.complaints.service;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;

/**
 * @author abarudwa
 *
 */
public interface SolveComplaintService {

	public List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException;
	
	public List<ServiceRequestTransfertoTO> getServiceRequestTransferList()
			throws CGBusinessException, CGSystemException;
	
	public ServiceRequestTO getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException,CGBusinessException;
	
	public ServiceRequestTO saveServiceRequestDetails(
			ServiceRequestTO serviceRequestTO) throws CGSystemException,CGBusinessException;

	public Date getConsignmentDeliveryDate(String upperCase)throws CGSystemException,CGBusinessException;
}
