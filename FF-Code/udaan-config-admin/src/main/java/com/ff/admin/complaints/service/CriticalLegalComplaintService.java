package com.ff.admin.complaints.service;

import java.io.IOException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.complaints.LegalComplaintTO;

public interface CriticalLegalComplaintService {
	
	boolean saveOrUpdateLegalComplaint(LegalComplaintTO legalComplainTO) throws CGBusinessException, CGSystemException, IOException ;
	
	public LegalComplaintTO searchLegalComplaint(LegalComplaintTO legalcomplntTO)
			throws CGBusinessException, CGSystemException ;
}
