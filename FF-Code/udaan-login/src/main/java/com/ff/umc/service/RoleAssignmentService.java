package com.ff.umc.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.organization.OfficeTO;
import com.ff.umc.RoleAssignmentTO;
import com.ff.umc.UserRightsTO;
import com.ff.umc.UserRolesTO;

public interface RoleAssignmentService {
	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGBaseException;

	public List<UserRightsTO> getUserDetails(String userName, String roleType, RoleAssignmentTO roleAssignmntTO)
			throws CGBusinessException, CGBaseException;

	public String saveOrUpdateRoleAssignments(RoleAssignmentTO roleAssignmentTO)
			throws CGBusinessException, CGBaseException;

	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;
}
