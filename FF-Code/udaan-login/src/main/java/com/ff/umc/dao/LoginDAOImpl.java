package com.ff.umc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.capgemini.lbs.framework.dao.CGBaseDAO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.ApplRightsDO;
import com.ff.domain.umc.ApplScreenDO;
import com.ff.domain.umc.CustomerUserDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.LogInOutDetlDO;
import com.ff.domain.umc.MenuNodeDO;
import com.ff.domain.umc.PasswordDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.umc.PasswordTO;
import com.ff.umc.UserTO;
import com.ff.umc.action.UserJoinBean;
import com.ff.umc.constants.UmcConstants;

public class LoginDAOImpl extends CGBaseDAO implements LoginDAO {

	/** logger. */
	private Logger LOGGER = LoggerFactory.getLogger(LoginDAOImpl.class);

	@SuppressWarnings("unchecked")
	public UserDO authenticateUser(UserTO to) throws CGSystemException {
		UserDO userDO = null;
		LOGGER.debug("LoginDAOImpl : authenticateUser:START"
				+ DateUtil.getCurrentTimeInMilliSeconds());

		List<UserDO> result = null;
		try {

			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_USER_BY_USERNAME,
					UmcConstants.USER_NAME, to.getUserName());
			if (!CGCollectionUtils.isEmpty(result)) {
				userDO = result.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.authenticateUser", e);
			throw new CGSystemException(e);
		}
		LOGGER.debug("LoginDAOImpl : END"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return userDO;
	}

	@SuppressWarnings("unchecked")
	public LogInOutDetlDO getLastLogin(UserDO userdo) throws CGSystemException {
		List<LogInOutDetlDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_LAST_LOGIN_LOGOUT_RECORD,
					UmcConstants.USER_ID, userdo.getUserId());
			if (result.isEmpty())
				return null;
			else if (result.size() > 1)
				return null;
			else
				return result.get(0);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getLastLogin", e);
			throw new CGSystemException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.umc.dao.LoginDAO#getPassword(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public PasswordDO getPassword(Integer userId) throws CGSystemException {

		List<PasswordDO> result = null;
		PasswordDO passDO = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_BY_USERID,
					UmcConstants.USER_ID, userId);
			if (result.isEmpty())
				return null;
			else if (result.size() > 1) {
				// return null;// TODO throw Exceptions

				for (PasswordDO paswdDO : result) {
					if (paswdDO.getIsActivePassword().equalsIgnoreCase(
							UmcConstants.FLAG_Y)) {
						passDO = paswdDO;
						return passDO;
					}
				}

			} else {
				return result.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getPassword", e);
			throw new CGSystemException(e);
		}
		return passDO;
	}

	@SuppressWarnings("unchecked")
	public UserDO getUserIdByUserName(String username) throws CGSystemException {
		List<UserDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_USER_BY_USERNAME,
					UmcConstants.USER_NAME, username);
			if (result.isEmpty())
				return null;
			else if (result.size() > 1)
				return null;// TODO throw Exceptions
			else
				return result.get(0);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getUserIdByUserName", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserDO> getUserByUser(UserTO userTO) throws CGSystemException {
		LOGGER.trace("LoginDAOImpl :: getUserByUser() :: START --------> ::::::");
		Session session = null;
		List<UserDO> userDO = null;
		Criteria criteria = null;
		try {
			session = createSession();
			criteria = session.createCriteria(UserDO.class, "user");

			if (!StringUtil.isEmptyInteger(userTO.getUserId())) {
				criteria.add(Restrictions.eq("user.userId", userTO.getUserId()));
			}
			if (!StringUtil.isStringEmpty(userTO.getUserName())) {
				criteria.add(Restrictions.eq("user.userName",
						userTO.getUserName()));
			}

			userDO = criteria.list();

		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getUserByUser", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("LoginDAOImpl :: getUserByUser() :: End --------> ::::::");
		return userDO;

	}

	@SuppressWarnings("unchecked")
	public ConfigurableParamsDO getConfParamByParamName(String paramName)
			throws CGSystemException {

		ConfigurableParamsDO paramDO = null;
		try {
			List<ConfigurableParamsDO> confparamDOLis = getHibernateTemplate()
					.findByNamedQueryAndNamedParam(
							UmcConstants.CONFIGURABLE_PARAM_QUERRY,
							UmcConstants.PARAM_NAME, paramName);
			if (confparamDOLis != null && !confparamDOLis.isEmpty()) {
				paramDO = confparamDOLis.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::getConfParamByParamName", e);
			throw new CGSystemException(e);

		}
		return paramDO;

	}

	public void updateLoginAttempt(UserDO userDO) throws CGSystemException {
		try {
			getHibernateTemplate().saveOrUpdate(userDO);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::updateLoginAttempt", e);
			throw new CGSystemException(e);
		}
	}

	public void lockUser(UserDO userdo) throws CGSystemException {
		try {
			getHibernateTemplate().saveOrUpdate(userdo);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::lockUser", e);
			throw new CGSystemException(e);

		}// update lock flag

	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeUserDO getEmpUserInfo(Integer userId)
			throws CGSystemException {
		LOGGER.debug("LoginDAOImpl : getEmpUserInfo"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<EmployeeUserDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_EMP_USERDO_BY_USERID,
					UmcConstants.USER_ID, userId);
			LOGGER.debug("LoginDAOImpl : getEmpUserInfo"
					+ DateUtil.getCurrentTimeInMilliSeconds());
			if (result.isEmpty())
				return null;
			else if (result.size() > 1)
				return null;// TODO throw Exceptions
			else {
				EmployeeUserDO domain = result.get(0);
				return domain;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::getEmpUserInfo", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerUserDO getCustUserInfo(Integer userId)
			throws CGSystemException {
		List<CustomerUserDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_CUST_USERDO_BY_USERID,
					UmcConstants.USER_ID, userId);

			if (result.isEmpty())
				return null;
			else {
				CustomerUserDO domain = result.get(0);
				return domain;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::getCustUserInfo", e);
			throw new CGSystemException(e);
		}
	}

	@Override
	public void insertLoginLogoutTime(LogInOutDetlDO logdo)
			throws CGSystemException {
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate();
			hibernateTemplate.save(logdo);

		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::insertLoginLogoutTime", e);
			throw new CGSystemException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRightsDO> getUserRoles(Integer userid)
			throws CGSystemException {

		String[] paramNames = { UmcConstants.USER_ID, UmcConstants.STATUS };
		Object[] values = { userid, "A" };
		List<UserRightsDO> userRoleList = new ArrayList<UserRightsDO>();
		userRoleList = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GET_USER_ROLES, paramNames, values);
		return userRoleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplRightsDO> getAccessScreensByUserRole(
			List<Integer> userRoles, List<String> appsNames)
			throws CGSystemException {
		LOGGER.debug("LoginDAOImpl::getAccessScreensByUserRole:::START"
				+ System.currentTimeMillis());
		List<ApplRightsDO> appRightsDOs = null;//
		Criteria rightsCriteria = null;
		Session session = null;

		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			rightsCriteria = session.createCriteria(ApplRightsDO.class);
			Criteria roleCriteria = rightsCriteria
					.createCriteria("rolesTypeDO");
			roleCriteria.add(Restrictions.in("roleId", userRoles));
			Criteria screencriteria = rightsCriteria
					.createCriteria("appscreenTypeDO");
			screencriteria.add(Restrictions.in("appName", appsNames));
			screencriteria.addOrder(Order.asc("moduleName"));
			screencriteria.addOrder(Order.asc("screenName"));

			appRightsDOs = rightsCriteria.list();

		} catch (HibernateException e) {
			LOGGER.error("ERROR : LoginDAOImpl::getAccessScreensByUserRole", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.debug("LoginDAOImpl::getAccessScreensByUserRole:::END"
				+ System.currentTimeMillis());
		return appRightsDOs;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getAccessScreensIdsByUserRole(List<Integer> userRoles,
			String appsName) throws CGSystemException {
		LOGGER.debug("LoginDAOImpl::getAccessScreensIdsByUserRole:::START"
				+ System.currentTimeMillis());
		List<Integer> screenIds = null;
		String hqlName = null;
		String paramName = "userRoles";
		
		LOGGER.info("LoginDAOImpl::getAccessScreensIdsByUserRole::INFO::APP_NAME=" + appsName + " and USER_ROLES" + userRoles);
		
		switch (appsName) {
		case UmcConstants.APP_NAME_WEB:
			hqlName = UmcConstants.QRY_GET_SCREEN_IDS_FOR_UDAAN_WEB;
			break;
		case UmcConstants.APP_NAME_CONFIG_ADMIN:
			hqlName = UmcConstants.QRY_GET_SCREEN_IDS_FOR_CONFIG_ADMIN;
			break;

		case UmcConstants.APP_NAME_UDAAN_REPORT:
			hqlName = UmcConstants.QRY_GET_SCREEN_IDS_FOR_UDAAN_REPORT;
			break;
		}
		
		screenIds = getHibernateTemplate().findByNamedQueryAndNamedParam(hqlName, paramName , userRoles);
		
		LOGGER.debug("LoginDAOImpl::getAccessScreensIdsByUserRole:::END"
				+ System.currentTimeMillis());
		return screenIds;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplScreenDO> getAllowedScreensForUser(Integer userid)
			throws CGSystemException {
		List<ApplScreenDO> screenList = null;
		try {
			screenList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_USER_SCREEN, UmcConstants.USER_ID,
					userid);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::getAllowedScreensForUser", e);
			throw new CGSystemException(e);
		}
		return screenList;
	}

	@SuppressWarnings("unchecked")
	public List<PasswordDO> validatePassword(int loginID, String newPassword)
			throws CGSystemException {
		List<PasswordDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_RECORDS,
					UmcConstants.USER_ID, loginID);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::validatePassword",
					e.getMessage());
			throw new CGSystemException(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Boolean updatePassword(PasswordDO passDO, String contextPath)
			throws CGSystemException {

		List<PasswordDO> result = null;

		try {

			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_RECORDS,
					UmcConstants.USER_ID, passDO.getUserId());

			// if (result.size() == 3) {

			// getHibernateTemplate().delete(result.get(2)); //commented for
			// bcun soft delete
			// getHibernateTemplate().delete(result.get(1));

			for (PasswordDO paswdDO : result) {
				if (paswdDO.getIsActivePassword().equalsIgnoreCase(
						UmcConstants.FLAG_Y)) {
					getHibernateTemplate().delete(paswdDO);
					paswdDO.setIsActivePassword(UmcConstants.FLAG_N);
					paswdDO.setChangeRequired(UmcConstants.FLAG_N);
					// if config
					if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
						paswdDO.setDtToBranch(UmcConstants.FLAG_N);
					} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
						// if web
						paswdDO.setDtToCentral(UmcConstants.FLAG_N);
					}

					paswdDO.setDtUpdateToCentral(UmcConstants.FLAG_Y);

					getHibernateTemplate().save(paswdDO);
				}
			}

			// result.get(0).setDtUpdateToCentral(UmcConstants.FLAG_Y);

			// result.get(1).setIsActivePassword(UmcConstants.FLAG_N);
			// result.get(1).setChangeRequired(UmcConstants.FLAG_N);

			// getHibernateTemplate().save(result.get(1));

			getHibernateTemplate().save(passDO);
			// } else if (result.size() == 2) {

			// getHibernateTemplate().delete(result.get(0));
			// result.get(0).setIsActivePassword(UmcConstants.FLAG_N);
			// result.get(0).setChangeRequired(UmcConstants.FLAG_N);
			// result.get(0).setDtToBranch(UmcConstants.FLAG_N);
			// result.get(0).setDtUpdateToCentral(UmcConstants.FLAG_Y);
			// getHibernateTemplate().save(result.get(0));
			// getHibernateTemplate().save(passDO);

			// } else {

			// result.get(0).setIsActivePassword(UmcConstants.FLAG_N);
			// result.get(0).setChangeRequired(UmcConstants.FLAG_N);
			// result.get(0).setDtToBranch(UmcConstants.FLAG_N);
			// result.get(0).setDtUpdateToCentral(UmcConstants.FLAG_Y);
			// getHibernateTemplate().delete(result.get(0));
			// getHibernateTemplate().save(result.get(0));
			// getHibernateTemplate().save(passDO);
			// }
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::updatePassword", e.getMessage());
			throw new CGSystemException(e);
		}

		return true;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateChangeRequiredFlag(Integer userId)
			throws CGSystemException {
		List<PasswordDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_BY_USERID,
					UmcConstants.USER_ID, userId);

			if (!result.isEmpty()) {
				PasswordDO domain = result.get(0);
				domain.setChangeRequired(UmcConstants.FLAG_Y);
				getHibernateTemplate().saveOrUpdate(domain);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl::updateChangeRequiredFlag",
					e.getMessage());
			throw new CGSystemException(e);
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfficeDO getOfficeByEmpId(Integer EmpId) throws CGSystemException {

		List<OfficeDO> result = null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(
				UmcConstants.QRY_GET_OFFICE_BY_EMPID, UmcConstants.EMPLOYEE_ID,
				EmpId);

		if (result.isEmpty())
			return null;
		/*
		 * else if (result.size() > 1) return null;
		 */
		else
			return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public OfficeDO getOfficeByCustId(Integer custId) throws CGSystemException {
		
		List<OfficeDO> result = null;
		result = getHibernateTemplate().findByNamedQueryAndNamedParam(UmcConstants.QRY_GET_OFFICE_BY_CUSTID, UmcConstants.CUSTOMER_ID, custId);
		
		if (result.isEmpty())
			return null;
		/*
		 * else if (result.size() > 1) return null;
		 */
		else
			return result.get(0);
	}

	/* saves new encrypted pasword in the database */
	@SuppressWarnings("unchecked")
	public Boolean savePassword(PasswordDO passDO, String contextPath)
			throws CGSystemException {
		List<PasswordDO> result = null;
		Boolean flag = false;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_RECORDS,
					UmcConstants.USER_ID, passDO.getUserId());
			if (result.isEmpty()) {
				getHibernateTemplate().save(passDO);
				flag = true;
			} else if (result.size() > 1 || result.size() == 1) {

				for (PasswordDO paswdDO : result) {
					if (paswdDO.getIsActivePassword().equalsIgnoreCase(
							UmcConstants.FLAG_Y)) {
						getHibernateTemplate().delete(paswdDO);
						paswdDO.setIsActivePassword(UmcConstants.FLAG_N);
						paswdDO.setChangeRequired(UmcConstants.FLAG_N);
						// if config
						if (contextPath.equalsIgnoreCase("/udaan-config-admin")) {
							paswdDO.setDtToBranch(UmcConstants.FLAG_N);
						} else if (contextPath.equalsIgnoreCase("/udaan-web")) {
							// if web
							paswdDO.setDtToCentral(UmcConstants.FLAG_N);
						}

						paswdDO.setDtUpdateToCentral(UmcConstants.FLAG_Y);

						getHibernateTemplate().save(paswdDO);
					}
				}

				getHibernateTemplate().save(passDO);
				flag = true;
				// } else if (result.size() == 2) {

				// getHibernateTemplate().delete(result.get(0));
				// result.get(0).setIsActivePassword(UmcConstants.FLAG_N);
				// result.get(0).setChangeRequired(UmcConstants.FLAG_N);
				// result.get(0).setDtToBranch(UmcConstants.FLAG_N);
				// getHibernateTemplate().save(result.get(0));
				// getHibernateTemplate().save(passDO);
				// flag = true;

				// } else if (result.size() == 3) {

				// getHibernateTemplate().delete(result.get(2));
				// getHibernateTemplate().delete(result.get(1));
				// getHibernateTemplate().delete(result.get(0));
				// result.get(0).setIsActivePassword(UmcConstants.FLAG_N);
				// result.get(0).setChangeRequired(UmcConstants.FLAG_N);
				// result.get(0).setDtToBranch(UmcConstants.FLAG_N);
				// result.get(1).setIsActivePassword(UmcConstants.FLAG_N);
				// result.get(1).setChangeRequired(UmcConstants.FLAG_N);
				//
				// getHibernateTemplate().save(result.get(1));
				// getHibernateTemplate().save(result.get(0));
				// getHibernateTemplate().save(passDO);
				// flag = true;
			}
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl:savePassword", e);
			throw new CGSystemException(e);
		}

		return flag;

	}

	/* gets the customer emailid to send mail for forgot paswd */
	@SuppressWarnings("unchecked")
	public List<CustomerUserDO> getCustEmailID(PasswordDO passDO)
			throws CGSystemException {
		List<CustomerUserDO> custList = null;
		try {
			custList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_CUST_USERDO_BY_USERID,
					UmcConstants.USER_ID, passDO.getUserId());
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl:getCustEmailID", e);
			throw new CGSystemException(e);
		}
		return custList;

	}

	/* gets the Employee emailid to send mail for forgot paswd */
	@SuppressWarnings("unchecked")
	public List<EmployeeUserDO> getEmpEmailID(PasswordDO passDO)
			throws CGSystemException {
		List<EmployeeUserDO> empList = null;
		try {
			empList = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_EMP_USERDO_BY_USERID,
					UmcConstants.USER_ID, passDO.getUserId());
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl:getEmpEmailID", e);
			throw new CGSystemException(e);
		}
		return empList;

	}

	/* for validating the current paswd for change password */
	@SuppressWarnings("unchecked")
	public List<PasswordDO> validateCurrentPaswd(PasswordTO changePaswdTO)
			throws CGSystemException {
		List<PasswordDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_PASSWORD_RECORDS,
					UmcConstants.USER_ID, changePaswdTO.getUserId());
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl:validateCurrentPaswd", e);
			throw new CGSystemException(e);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDO getUserDetailsByUserId(Integer userId)
			throws CGSystemException {
		List<UserDO> result = null;
		try {
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_USER_BY_USERID, UmcConstants.USER_ID,
					userId);
			if (result.isEmpty())
				return null;
			else if (result.size() > 1)
				return null;// TODO throw Exceptions
			else
				return result.get(0);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl:getUserDetailsByUserId", e);
			throw new CGSystemException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByType(String offType)
			throws CGSystemException {
		LOGGER.trace("LoginDAOImpl :: getAllOfficesByType() :: START --------> ::::::");
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_OFFICES_BY_TYPE, "offType", offType);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByType", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("LoginDAOImpl :: getAllOfficesByType() :: END --------> ::::::");
		return offices;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfficeDO> getAllOfficesByCity(Integer cityId)
			throws CGSystemException {
		LOGGER.trace("LoginDAOImpl :: getAllOfficesByCity() :: START --------> ::::::");
		List<OfficeDO> offices = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			offices = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_OFFICES_BY_CITY,
					UmcConstants.PARAM_CITY_ID, cityId);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getAllOfficesByCity", e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("LoginDAOImpl :: getAllOfficesByCity() :: END --------> ::::::");
		return offices;
	}

	@SuppressWarnings("unchecked")
	public UserDO getUserIdByUserNameType(String username, String userType)
			throws CGSystemException {
		List<UserDO> result = null;
		try {
			String[] params = { UmcConstants.USER_NAME,
					UmcConstants.PARAM_USER_TYPE_1 };
			Object[] values = { username, userType };
			result = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GET_USER_BY_USERNAME_TYPE, params, values);
			if (result.isEmpty())
				return null;
			else if (result.size() > 1)
				return null;// TODO throw Exceptions
			else
				return result.get(0);
		} catch (Exception e) {
			LOGGER.error("ERROR : LoginDAOImpl.getUserIdByUserNameType", e);
			throw new CGSystemException(e);
		}
	}

	public UserDO getUserById(Integer userId) throws CGSystemException {
		LOGGER.trace("LoginDAOImpl :: getUserById() :: START --------> ::::::");
		UserDO user = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			user = (UserDO) session.createCriteria(UserDO.class)
					.add(Restrictions.eq(UmcConstants.USER_ID, userId))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error("Error occured in LoginDAOImpl :: getUserById()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		} finally {

			session.close();
		}
		LOGGER.trace("LoginDAOImpl :: getUserById() :: END --------> ::::::");
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDO> getAllUsersByEmpId(Integer employeeId)
			throws CGSystemException {
		List<UserDO> userDO = null;
		try {
			userDO = getHibernateTemplate().findByNamedQueryAndNamedParam(
					UmcConstants.QRY_GETUSER_BYEMPID, UmcConstants.EMPLOYEE_ID,
					employeeId);

			if (userDO.isEmpty())
				return null;
			else if (userDO.size() > 1)
				return userDO;
		} catch (Exception e) {
			LOGGER.error("Error occured in LoginDAOImpl :: getAllUsersByEmpId()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return userDO;
	}

	@Override
	public Boolean updateLogoutDetails(LogInOutDetlDO logdo)
			throws CGSystemException {
		LOGGER.trace("LoginDAOImpl :: updateLogoutDetails() :: START --------> ::::::");
		boolean isLogout = Boolean.FALSE;
		Session session = null;
		try {
			session = createSession();
			Query query = session
					.getNamedQuery(UmcConstants.QRY_UPDATE_LOGOUT_DATE);
			query.setParameter(UmcConstants.QRY_PARAM_LOGOUT_DATE,
					logdo.getLogOutDate());
			query.setParameter(UmcConstants.QRY_PARAM_LOG_IN_OUT_ID,
					logdo.getLogInOutId());
			query.executeUpdate();
			isLogout = Boolean.TRUE;

		} catch (Exception e) {
			LOGGER.error(
					"Error occured in LoginDAOImpl :: updateLogoutDetails()..:",
					e);
			throw new CGSystemException(e);
		} finally {
			closeSession(session);
		}
		LOGGER.trace("LoginDAOImpl :: updateLogoutDetails() :: END --------> ::::::");
		return isLogout;

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<MenuNodeDO> getMenuNodesByScreenIds( List<Integer>
	 * screenIds, String appName) throws CGSystemException { LOGGER.debug(
	 * "LoginDAOImpl :: getMenuNodesByScreenIds() :: Start --------> ::::::"
	 * +System.currentTimeMillis()); List<MenuLeafNodeDO> menuNodeDOs = null;
	 * List<MenuNodeDO> menuNodeDOList = null; Criteria menuCriteria = null;
	 * Session session = null;
	 * 
	 * try { session = getHibernateTemplate().getSessionFactory().openSession();
	 * menuCriteria = session.createCriteria(MenuNodeDO.class,"menu");
	 * 
	 * 
	 * menuCriteria.add(Restrictions.eq("appName", appName));
	 * menuCriteria.addOrder(Order.asc("menu.position"));
	 * menuCriteria.addOrder(Order.asc("menu.menuNodeDOs"));
	 * 
	 * //menuCriteria.createCriteria("menu.menuNodeDOs","subMenuDOs").add(
	 * Restrictions.in("subMenuDOs.applScreenDO.screenId", screenIds));
	 * //menuCriteria.addOrder(Order.desc("menu.position"));
	 * //menuCriteria.addOrder(Order.asc("menu.embeddedInMenu")); Criteria
	 * childCriteria = menuCriteria .createCriteria("menu.applScreenDO");
	 * childCriteria.add(Restrictions.in("menu.applScreenDO.screenId",
	 * screenIds));
	 * 
	 * childCriteria.addOrder(Order.asc("menu.position"));
	 * 
	 * menuNodeDOList = menuCriteria.list();
	 * 
	 * String params[] = { "screenIds", "appName" }; Object values[] = {
	 * screenIds, appName }; menuNodeDOs =
	 * getHibernateTemplate().findByNamedQueryAndNamedParam( "getMenuNodes",
	 * params, values);
	 * 
	 * } catch (Exception e) { LOGGER.error(
	 * "Exception Occured in::LoginDAOImpl::getMenuNodesByScreenIds() :: " + e);
	 * throw new CGSystemException(e); }finally{ session.close(); }
	 * LOGGER.debug(
	 * "LoginDAOImpl :: getMenuNodesByScreenIds() :: End --------> ::::::"
	 * +System.currentTimeMillis()); return menuNodeDOList; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuNodeDO> getMenuNodesByScreenIds(List<Integer> screenIds,
			String appName) throws CGSystemException {
		LOGGER.debug("LoginDAOImpl :: getMenuNodesByScreenIds() :: Start --------> ::::::"
				+ System.currentTimeMillis());

		List<MenuNodeDO> menuNodeDOList = null;

		try {

			String params[] = { "screenIds", "appName" };
			Object values[] = { screenIds, appName };
			menuNodeDOList = getHibernateTemplate()
					.findByNamedQueryAndNamedParam("getMenuNodes", params,
							values);

		} catch (Exception e) {
			LOGGER.error("Exception Occured in::LoginDAOImpl::getMenuNodesByScreenIds() :: "
					+ e);
			throw new CGSystemException(e);
		}
		
		LOGGER.debug("LoginDAOImpl :: getMenuNodesByScreenIds() :: End --------> ::::::"
				+ System.currentTimeMillis());
		return menuNodeDOList;
	}

	@Override
	public UserJoinBean getUserJoinBean(String username, String paswd)
			throws CGSystemException {
		LOGGER.debug("LoginDAOImpl::getUserJoinBean::Checking User Details::START");
		//List<UserJoinBean> userJoinBeanlist = null;
		Session session = null;
		UserJoinBean userJoinBean = null;
		String sqlQuery = "select u.*,e.*,p.*,f.* from ff_d_user u  "
				+ "inner join ff_d_employee_user eu on u.USER_ID = eu.USER_ID  "
				+ "inner join ff_d_employee e on e.EMPLOYEE_ID=eu.EMPLOYEE_ID "
				+ "inner join ff_d_office f on e.OFFICE=f.OFFICE_ID  "
				+ "inner join ff_d_password p on u.USER_ID = p.USER_ID " +

				"where u.USER_NAME='" + username + "' and "
				+ "p.IS_ACTIVE_PASSWORD='Y'";

		try {
			/*
			 * String params[] = {"username","status"}; Object values[] =
			 * {username,"Y"}; userJoinBeanlist =
			 * getHibernateTemplate().findByNamedQueryAndNamedParam
			 * ("getUserJoinBean", params, values);
			 */
			session = createSession();
			SQLQuery sQLQuery = session.createSQLQuery(sqlQuery);
			sQLQuery.addEntity("u", UserDO.class);
			sQLQuery.addEntity("e", EmployeeDO.class);
			sQLQuery.addEntity("p", PasswordDO.class);
			
			// Commented to improve login time performance 
			// sQLQuery.addEntity("h", LogInOutDetlDO.class);
			
			sQLQuery.addEntity("f", OfficeDO.class);

			List<?> list = sQLQuery.list();

			if (!CGCollectionUtils.isEmpty(list)) {
				Object[] userDetail = (Object[]) list.get(0);
				if (!StringUtil.isEmpty(userDetail)) {
					UserDO userDO = (UserDO) userDetail[0];
					EmployeeDO empDO = (EmployeeDO) userDetail[1];
					PasswordDO passDO = (PasswordDO) userDetail[2];
					
					// Commented to improve login time performance 
					// LogInOutDetlDO historyDO = (LogInOutDetlDO)
					// userDetail[3];
					
					OfficeDO offDO = (OfficeDO) userDetail[3];
					userJoinBean = new UserJoinBean(userDO, empDO, null, passDO,
							offDO);
				}

			}
			LOGGER.debug("LoginDAOImpl::getUserJoinBean..fetching login list.size();"
					+ list.size());
			LOGGER.debug("LoginDAOImpl::getUserJoinBean..fetching login list.size();"
					+ list);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::LoginDAOImpl::getUserJoinBean() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("LoginDAOImpl::getUserJoinBean::Checking User Details::START");
		return userJoinBean;
	}

	@Override
	public UserJoinBean getCustomerUserJoinBean(String username,
			String password) throws CGSystemException {
		LOGGER.debug("LoginDAOImpl::getCustomerUserJoinBean..fetching login details:Start");
		Session session = null;
		UserJoinBean userJoinBean = null;
		String sqlQuery = "SELECT fdu.*, "
				+ "fdc.*, "
				+ "fdo.*, "
				+ "fdp.* "
				+ "FROM ff_d_user fdu  "
				+ "INNER JOIN ff_d_customer_user fdcu ON fdcu.USER_ID = fdu.USER_ID "
				+ "INNER JOIN ff_d_customer fdc ON fdc.CUSTOMER_ID = fdcu.CUSTOMER_ID "
				+ "INNER JOIN ff_d_office fdo ON fdc.SALES_OFFICE = fdo.OFFICE_ID "
				+ "INNER JOIN ff_d_password fdp ON fdu.USER_ID = fdp.USER_ID "
				+ "WHERE fdu.USER_NAME = '" + username + "' AND fdp.IS_ACTIVE_PASSWORD = 'Y';";
		try {
			/*
			 * String params[] = {"username","status"}; Object values[] =
			 * {username,"Y"}; userJoinBeanlist =
			 * getHibernateTemplate().findByNamedQueryAndNamedParam
			 * ("getUserJoinBean", params, values);
			 */
			session = createSession();
			SQLQuery sQLQuery = session.createSQLQuery(sqlQuery);
			sQLQuery.addEntity("fdu", UserDO.class);
			sQLQuery.addEntity("fdc", CustomerDO.class);
			sQLQuery.addEntity("fdp", PasswordDO.class);
			sQLQuery.addEntity("fdo", OfficeDO.class);

			List<?> list = sQLQuery.list();

			if (!CGCollectionUtils.isEmpty(list)) {
				Object[] userDetail = (Object[]) list.get(0);
				if (!StringUtil.isEmpty(userDetail)) {
					UserDO userDO = (UserDO) userDetail[0];
					CustomerDO custDO = (CustomerDO) userDetail[1];
					PasswordDO passDO = (PasswordDO) userDetail[2];
					OfficeDO offDO = (OfficeDO) userDetail[3];
					userJoinBean = new UserJoinBean(userDO, null, custDO, passDO,
							offDO);
				}

			}
			LOGGER.debug("LoginDAOImpl::getUserJoinBean..fetching login list.size();"
					+ list.size());
			LOGGER.debug("LoginDAOImpl::getUserJoinBean..fetching login list.size();"
					+ list);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::LoginDAOImpl::getUserJoinBean() :: ",
					e);
			throw new CGSystemException(e);
		} finally {
			session.close();
		}
		LOGGER.debug("LoginDAOImpl::getUserJoinBean..fetching login END");
		return userJoinBean;
	}

}
