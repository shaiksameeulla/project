package com.ff.umc.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.ff.umc.UserRolesTO;

public interface RoleAssignmentCommonService {
	public List<UserRolesTO> getActiveUserRoles(String roleType)
			throws CGBusinessException, CGBaseException;
}
