/**
 * 
 */
package com.ff.admin.complaints.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.complaints.ServiceRequestDO;

/**
 * @author abarudwa
 * 
 */
public interface ComplaintsBacklineSummaryDAO {

	public List<ServiceRequestDO> getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGSystemException;

	public List<ServiceRequestDO> getComplaintDetailsByServiceRequestStatus(
			String statusName) throws CGSystemException;

	public List<ServiceRequestDO> getComplaintDetailsByUser(Integer employeeId)
			throws CGSystemException;

	List<ServiceRequestDO> getComplaintDetailsByServiceRequestStatus(
			String statusName, Integer employeeId) throws CGSystemException;
}
