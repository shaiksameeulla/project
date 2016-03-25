/**
 * 
 */
package com.ff.admin.complaints.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestFollowupDO;

/**
 * @author prmeher
 *
 */
public interface ServiceRequestFollowupDAO {

	/**
	 * @param serviceRequestFollowupDO
	 * @return
	 * @throws CGSystemException
	 */
	Boolean saveOrUpdateFollowup(
			ServiceRequestFollowupDO serviceRequestFollowupDO) throws CGSystemException;

	/**
	 * @param complaintId
	 * @return
	 * @throws CGSystemException
	 */
	List<ServiceRequestFollowupDO> getComplaintFollowupDetails(
			Integer complaintId)throws CGSystemException;

	/**
	 * @param complaintId
	 * @return
	 * @throws CGSystemException
	 */
	ServiceRequestDO getComplaintDtlsByComplaintId(Integer complaintId)throws CGSystemException;

	/**
	 * @param complaint
	 * @return
	 * @throws CGSystemException
	 */
	Boolean updateServiceRequest(ServiceRequestDO complaint)throws CGSystemException;

}
