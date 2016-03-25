package com.ff.admin.complaints.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.umc.EmployeeUserDO;

/**
 * @author sdalli
 *
 */
public interface ServiceRequestForConsignmentDAO {
		 
		
		List<EmployeeUserDO> getBackLineEmpList(final Integer officeId,final String designation )throws CGSystemException;
		
}
