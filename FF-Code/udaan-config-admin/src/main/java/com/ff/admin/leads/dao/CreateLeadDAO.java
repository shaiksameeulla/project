package com.ff.admin.leads.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.leads.LeadDO;
import com.ff.domain.umc.EmployeeUserDO;

public interface CreateLeadDAO {

	 boolean saveLead(LeadDO leadDO)throws  CGSystemException ;
	
	 List<EmployeeUserDO> getSalesExecutive(final Integer officeId)
			throws CGSystemException;

}
