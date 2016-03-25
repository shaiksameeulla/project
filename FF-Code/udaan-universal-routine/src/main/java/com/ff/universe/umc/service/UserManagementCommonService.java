package com.ff.universe.umc.service;

import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.umc.UserTO;

/**
 * @author preegupt
 *
 */
public interface UserManagementCommonService {
	
	 /**@Desc:return users based on the userTpye(i.e. E or C)
	 * @param userType
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	Map<Integer, String> getUsersByType(String userType)
			throws CGBusinessException, CGSystemException;
	
	UserTO getUserByUserName(String userName)
			throws CGBusinessException, CGSystemException;
}
