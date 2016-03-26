package com.ff.umc.service;

/**
 * Author : Narasimha Rao Kattunga
 * @Class : RoleManagementCommonService
 * @Desc : Common Services for  UMC - Role Management process
 * Creation Date : Nov - 05 - 2012
 */

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.umc.ApplRightsTO;
import com.ff.umc.ApplScreensTO;
import com.ff.umc.UserRolesTO;

public interface RoleManagementCommonService {
	public List<UserRolesTO> getUserRoles() throws CGBusinessException,
			CGSystemException;

	public List<UserRolesTO> getUserRoles(String roleType)
			throws CGBusinessException, CGSystemException;

	public UserRolesTO getUserRoleById(Integer roleId)
			throws CGBusinessException, CGSystemException;

	public List<ApplRightsTO> getUserRights() throws CGBusinessException,
			CGSystemException;

	public List<ApplScreensTO> getApplScreens(String accessibleTo)
			throws CGBusinessException, CGSystemException;

	public List<ApplRightsTO> getUserRights(Integer roleId)
			throws CGBusinessException, CGSystemException;

	public String isRoleAssigned(Integer roleId) throws CGBusinessException,
			CGSystemException;

	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGSystemException;

}
