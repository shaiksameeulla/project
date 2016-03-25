/**
 * 
 */
package com.ff.admin.complaints.service;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.CriticalClaimComplaintTO;

/**
 * @author cbhure
 * 
 */
public interface CriticalClaimComplaintService {


	Boolean saveCriticalClaimComplaint(CriticalClaimComplaintTO criticalComplaintTO)
			throws CGBusinessException, CGSystemException;

	CriticalClaimComplaintTO getCriticalClaimComplaintDtls(CriticalClaimComplaintTO criticalComplaintTO) throws CGBusinessException, CGSystemException;

	CriticalClaimComplaintTO getCriticalComplaintDtls(String complaintNo)throws CGBusinessException, CGSystemException; 

}
