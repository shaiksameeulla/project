package com.ff.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.umc.ApplRightsDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRolesDO;

/**
 * Author : Narasimha Rao Kattunga
 * 
 * @Class : RoleManagementDAO
 * @Desc : DAO Services for UMC - Role Management process
 * @Creation Date : Nov - 05 - 2012
 */

public interface RoleManagementDAO {

	public boolean saveOrUpdateUserRoles(UserRolesDO userRoles)
			throws CGSystemException;

	public boolean activateDeActivateUserRoles(List<Integer> userRoleIds,
			String statusType) throws CGSystemException;

	public List<UserRolesDO> getUserRoles() throws CGSystemException;

	public List<UserRolesDO> getUserRoles(String roleType)
			throws CGSystemException;

	public List<UserRolesDO> getActiveUserRoles(String roleType)
			throws CGSystemException;

	public UserRolesDO getUserRoleById(Integer roleId) throws CGSystemException;

	public List<ApplRightsDO> getUserRights() throws CGSystemException;

	public List<ApplRightsDO> getUserRights(Integer roleId)
			throws CGSystemException;

	public List<ApplScreenDO> getApplScreens(String accessibleTo)
			throws CGSystemException;

	public List<UserDO> isRoleAssigned(Integer roleId) throws CGSystemException;

	public boolean deleteApplRights(Integer userRoleId) throws CGSystemException;

	public boolean isUserRoleExists(String roleName) throws CGSystemException;
	
	public ApplScreenDO getCenralizedScreensForScreen(Integer screenId) throws CGSystemException;

}
