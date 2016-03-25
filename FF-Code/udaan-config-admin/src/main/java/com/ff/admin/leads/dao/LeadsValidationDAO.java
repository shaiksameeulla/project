package com.ff.admin.leads.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;

public interface LeadsValidationDAO {

	 boolean approveLead(LeadDO leadDO) throws CGBusinessException,
	CGSystemException;
	
	 List<EmployeeUserDO> getSalesExecutive(final Integer officeId)
			throws CGSystemException;
	
	 LeadDO getLeadDetails(final String leadNumber) throws CGSystemException ;
}
