package com.ff.admin.complaints.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ComplaintsBacklineSummaryTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.EmployeeUserTO;

public interface ComplaintsBacklineSummaryService {
	public List<StockStandardTypeTO> getComplaintsStatus(String typeName) throws CGSystemException, CGBusinessException;
			
	public List<ServiceRequestTO> getServiceRequestDetails(ComplaintsBacklineSummaryTO summaryTO) throws CGBusinessException,
			CGSystemException;
	
	public List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException ;
	
	public EmployeeUserTO getEmployeeUser(Integer userId)
			throws CGBusinessException, CGSystemException ;
	
	public List<ServiceRequestTO> getComplaintDetailsByServiceRequestNo(
			String serviceRequestNo) throws CGBusinessException,CGSystemException;
	
	public List<ServiceRequestTO> getComplaintDetailsByServiceRequestStatus(
			String statusName) throws CGSystemException,CGBusinessException;
	
	public List<ServiceRequestTO> getComplaintDetailsByUser(
			Integer employeeId) throws CGSystemException,CGBusinessException;

	List<ServiceRequestTO> getComplaintDetailsByServiceRequestStatus(
			String statusName, Integer employeeId) throws CGSystemException,
			CGBusinessException;
}
