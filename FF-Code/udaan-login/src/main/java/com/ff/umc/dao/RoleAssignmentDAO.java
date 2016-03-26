package com.ff.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRightsDO;

public interface RoleAssignmentDAO {
	public boolean saveOrUpdateRoleAssignments(UserDO user) throws CGBaseException;

	public List<UserRightsDO> getUserRights(String userCode, String roleType)
			throws CGBaseException;

	public Integer getRightsId(Integer roleId, Integer userId)
			throws CGBaseException;

	public Integer getUserOfficeRightsId(Integer userId, Integer officeId)
			throws CGBaseException;

}
