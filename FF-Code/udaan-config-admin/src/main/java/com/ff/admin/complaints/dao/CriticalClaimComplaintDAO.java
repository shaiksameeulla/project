/**
 * 
 */
package com.ff.admin.complaints.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.CriticalClaimComplaintTO;
import com.ff.domain.complaints.ServiceRequestComplaintDO;
import com.ff.domain.complaints.ServiceRequestCriticalComplaintClaimDO;


/**
 * @author cbhure
 *
 */
public interface CriticalClaimComplaintDAO {


	Boolean saveCriticalClaimComplaint(
			ServiceRequestCriticalComplaintClaimDO criticalComplaintDO) throws CGSystemException;

	ServiceRequestCriticalComplaintClaimDO getCriticalClaimComplaintDtls(CriticalClaimComplaintTO criticalComplaintTO) throws CGSystemException;

	ServiceRequestComplaintDO getCriticalComplaintDtls(String complaintNo) throws CGSystemException;

}
