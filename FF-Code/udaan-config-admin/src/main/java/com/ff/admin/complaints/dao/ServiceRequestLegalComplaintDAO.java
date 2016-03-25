package com.ff.admin.complaints.dao;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.LegalComplaintTO;
import com.ff.domain.complaints.ServiceRequestLegalComplaintDO;
import com.ff.domain.complaints.ServiceRequestPapersDO;

public interface ServiceRequestLegalComplaintDAO {

	public boolean saveOrUpdateLegalComplaint(
			ServiceRequestLegalComplaintDO servicereqstLegalcomplaintDO,ServiceRequestPapersDO serviceRequestPaperDO) throws CGSystemException;
	
	public ServiceRequestLegalComplaintDO searchLegalComplaint(LegalComplaintTO to) throws CGSystemException;
	
	public ServiceRequestPapersDO getPaperWorkDO(LegalComplaintTO legalComplntTO) throws CGSystemException;
}
