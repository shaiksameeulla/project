package com.ff.universe.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.umc.UserDO;

public interface UserManagementCommonDAO {
	/**return users based on the userTpye(i.e. E or C)
	 * @param userType
	 * @return list of users 
	 * @throws CGSystemException
	 */
	public List<Object[]> getUsersByType(String userType)
			throws CGSystemException;
	
	public UserDO getUserByUserName(String userName)
			throws CGSystemException;
}
