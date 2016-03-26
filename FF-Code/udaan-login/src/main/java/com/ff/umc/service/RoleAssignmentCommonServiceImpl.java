package com.ff.umc.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.umc.UserRolesTO;
import com.ff.umc.dao.RoleAssignmentDAO;

public class RoleAssignmentCommonServiceImpl implements
		RoleAssignmentCommonService {
	private RoleManagementCommonService userRolesCommonService;
	private RoleAssignmentDAO userRoleAssignmentDAO;

	public RoleManagementCommonService getUserRolesCommonService() {
		return userRolesCommonService;
	}

	public void setUserRolesCommonService(
			RoleManagementCommonService userRolesCommonService) {
		this.userRolesCommonService = userRolesCommonService;
	}
	

	public RoleAssignmentDAO getUserRoleAssignmentDAO() {
		return userRoleAssignmentDAO;
	}

	public void setUserRoleAssignmentDAO(RoleAssignmentDAO userRoleAssignmentDAO) {
		this.userRoleAssignmentDAO = userRoleAssignmentDAO;
	}

	/**
	 * @Method : getUserRoles
	 * @param : roleType
	 * @Desc : For getting all active user roles
	 * @return : List<UserRolesTO>
	 */
	@Override
	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGBaseException {
		return userRolesCommonService.getActiveUserRoles(roleType);
	}

}
