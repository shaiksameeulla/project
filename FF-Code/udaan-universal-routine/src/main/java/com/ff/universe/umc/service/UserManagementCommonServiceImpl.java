package com.ff.universe.umc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.umc.UserDO;
import com.ff.umc.UserTO;
import com.ff.universe.umc.dao.UserManagementCommonDAO;

public class UserManagementCommonServiceImpl implements
		UserManagementCommonService {
	private UserManagementCommonDAO umcCommonDAO = null;

	public UserManagementCommonDAO getUmcCommonDAO() {
		return umcCommonDAO;
	}

	public void setUmcCommonDAO(UserManagementCommonDAO umcCommonDAO) {
		this.umcCommonDAO = umcCommonDAO;
	}

	@Override
	public Map<Integer, String> getUsersByType(String userType)
			throws CGBusinessException, CGSystemException {
		List<Object[]> userList = umcCommonDAO.getUsersByType(userType);
		Map<Integer, String> users = null;

		if (userList != null && userList.size() > 0) {
			users = new HashMap<Integer, String>();
			for (int i = 0; i < userList.size(); i++) {
				Object[] obj = userList.get(i);
				users.put(Integer.parseInt(obj[0].toString()), obj[1]
						.toString().replace(",", "^"));
			}
		}

		return users;
	}

	@Override
	public UserTO getUserByUserName(String userName)
			throws CGBusinessException, CGSystemException {
		
		UserDO userDO = null;
		UserTO userTO = null;
		
		userDO = umcCommonDAO.getUserByUserName(userName);
		
		if(!StringUtil.isNull(userDO)){
			userTO = new UserTO();
			userTO = (UserTO)CGObjectConverter.createToFromDomain(userDO, userTO);
		}
		return userTO;
	}
}
