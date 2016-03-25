package com.ff.universe.umc.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
//import com.ff.universe.util.EmailSenderServiceImpl;
import com.ff.domain.umc.UserDO;

public class UserManagementCommonDAOImpl extends CGBaseDAO implements
		UserManagementCommonDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserManagementCommonDAOImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUsersByType(String userType)
			throws CGSystemException {
		List<Object[]> users = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			users = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"getUsersAutocomplete", "userType", userType);
		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementCommonDAOImpl :: getUsersByType()..:"
					+ e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		return users;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UserDO getUserByUserName(String userName) throws CGSystemException {
		
		List<UserDO> usersList = null;
		usersList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getUserbyUserName", "userName", userName);
		if(!CGCollectionUtils.isEmpty(usersList)){
			return usersList.get(0);
		}
		return null;
	}
}
