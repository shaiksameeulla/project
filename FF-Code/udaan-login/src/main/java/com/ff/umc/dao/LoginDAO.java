package com.ff.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.common.ConfigurableParamsDO;
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

public interface LoginDAO {
	/**
	 * @desc authenticates User
	 * @param to
	 * @return UserDO
	 * @throws CGSystemException
	 */
	public UserDO authenticateUser(UserTO to) throws CGSystemException;

	/**
	 * @desc get EmpUser Info
	 * @param userId
	 * @return EmployeeUserDO
	 * @throws CGSystemException
	 */
	public EmployeeUserDO getEmpUserInfo(Integer userId)
			throws CGSystemException;

	/**
	 * @desc get CustUser Info
	 * @param userId
	 * @return CustomerUserDO
	 * @throws CGSystemException
	 */
	public CustomerUserDO getCustUserInfo(Integer userId)
			throws CGSystemException;

	/**
	 * @desc get LastLogin time 
	 * @param userdo
	 * @return LogInOutDetlDO
	 * @throws CGSystemException
	 */
	public LogInOutDetlDO getLastLogin(UserDO userdo) throws CGSystemException;

	/**
	 * @desc gets Password 
	 * @param userId
	 * @return PasswordDO
	 * @throws CGSystemException
	 */
	public PasswordDO getPassword(Integer userId) throws CGSystemException;

	/**
	 * @desc getUserId By UserName
	 * @param username
	 * @return UserDO
	 * @throws CGSystemException
	 */
	public UserDO getUserIdByUserName(String username) throws CGSystemException;

	/**
	 * @desc getConfParam By ParamName
	 * @param paramName
	 * @return ConfigurableParamsDO
	 * @throws CGSystemException
	 */
	public ConfigurableParamsDO getConfParamByParamName(String paramName)
			throws CGSystemException;

	/**
	 * @desc update LoginAttempt
	 * @param userDO
	 * @throws CGSystemException
	 */
	public void updateLoginAttempt(UserDO userDO) throws CGSystemException;

	/**
	 * @desc lock User
	 * @param userdo
	 * @throws CGSystemException
	 */
	public void lockUser(UserDO userdo) throws CGSystemException;

	/**
	 * @desc get AccessScreens By UserRole
	 * @param userRoles
	 * @param appsName
	 * @return List<ApplRightsDO>
	 * @throws CGSystemException
	 */
	public List<ApplRightsDO> getAccessScreensByUserRole(List<Integer> userRoles, List<String> appsNames)
			throws CGSystemException;

	/**
	 * @desc insert LoginLogout Time
	 * @param logdo
	 * @throws CGSystemException
	 */
	public void insertLoginLogoutTime(LogInOutDetlDO logdo)
			throws CGSystemException;

	/**
	 * @desc get UserRoles
	 * @param userid
	 * @return List<UserRightsDO>
	 * @throws CGSystemException
	 */
	public List<UserRightsDO> getUserRoles(Integer userid)
			throws CGSystemException;

	/**
	 * @desc get AllowedScreens For User
	 * @param userid
	 * @return List<ApplScreenDO>
	 * @throws CGSystemException
	 */
	public List<ApplScreenDO> getAllowedScreensForUser(Integer userid)
			throws CGSystemException;

	/**
	 * @desc validate Password
	 * @param loginID
	 * @param newPassword
	 * @return List<PasswordDO>
	 * @throws CGSystemException
	 */
	public List<PasswordDO> validatePassword(int loginID, String newPassword)
			throws CGSystemException;

	/**
	 * @desc update Password
	 * @param passDO
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public Boolean updatePassword(PasswordDO passDO,String contextPath) throws CGSystemException;

	/**
	 * @desc update ChangeRequired Flag
	 * @param userId
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public Boolean updateChangeRequiredFlag(Integer userId)
			throws CGSystemException;

	/**
	 * @desc get Office By EmpId
	 * @param EmpId
	 * @return OfficeDO
	 * @throws CGSystemException
	 */
	public OfficeDO getOfficeByEmpId(Integer EmpId) throws CGSystemException;
	
	/**
	 * Gets Office details of Customer by sales Office
	 * 
	 * @param EmpId
	 * @return
	 * @throws CGSystemException
	 */
	public OfficeDO getOfficeByCustId(Integer EmpId) throws CGSystemException;

	/**
	 * @desc save Password
	 * @param passDO
	 * @return boolean value
	 */
	public Boolean savePassword(PasswordDO passDO,String contextPath)throws CGSystemException;

	/**
	 * @desc get CustEmailID
	 * @param passDO
	 * @return List<CustomerUserDO
	 */
	public List<CustomerUserDO> getCustEmailID(PasswordDO passDO)throws CGSystemException;

	/**
	 * @desc get EmpEmailID
	 * @param passDO
	 * @return   List<EmployeeUserDO>
	 */
	public List<EmployeeUserDO> getEmpEmailID(PasswordDO passDO)throws CGSystemException;

	/**
	 * @desc validates CurrentPaswd
	 * @param changePaswdTO
	 * @return List<PasswordDO>
	 */
	public List<PasswordDO> validateCurrentPaswd(PasswordTO changePaswdTO)throws CGSystemException;

	/**
	 * @desc get UserDetails By UserId
	 * @param userId
	 * @return UserDO
	 */
	public UserDO getUserDetailsByUserId(Integer userId)throws CGSystemException;
	

	/**
	 * @desc get AllOffices By Type
	 * @param offType
	 * @return  List<OfficeDO>
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getAllOfficesByType(String offType)
			throws CGSystemException;

	/**
	 * @desc get AllOffices By City
	 * @param cityId
	 * @return List<OfficeDO>
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getAllOfficesByCity(Integer cityId)
			throws CGSystemException;

	/**
	 * @desc get UserId By UserNameType
	 * @param username
	 * @param userType
	 * @return UserDO
	 * @throws CGSystemException
	 */
	public UserDO getUserIdByUserNameType(String username, String userType)
			throws CGSystemException;

	/**
	 * @desc get User By Id
	 * @param userId
	 * @return UserDO
	 * @throws CGSystemException
	 */
	public UserDO getUserById(Integer userId) throws CGSystemException;

	/**
	 * @desc get AllUsers By EmpId
	 * @param empId
	 * @return List<UserDO>
	 * @throws CGSystemException
	 */
	public List<UserDO> getAllUsersByEmpId(Integer empId) throws CGSystemException;
	
	/**
	 * @desc update Logout Details
	 * @param logdo
	 * @return returns boolean value
	 * @throws CGSystemException
	 */
	public Boolean updateLogoutDetails(LogInOutDetlDO logdo) throws CGSystemException;

	//public List<UserDO> getActiveUserByUser(UserTO userTO) throws CGSystemException;
	
	/**
	 * @desc get User By User
	 * @param userTO
	 * @return List<UserDO>
	 * @throws CGSystemException
	 */
	public List<UserDO> getUserByUser(UserTO userTO) throws CGSystemException;
	
	public List<MenuNodeDO> getMenuNodesByScreenIds(List<Integer> screenIds,String appName)
			throws CGSystemException;
	
	public UserJoinBean getUserJoinBean(String username,String paswd)throws CGSystemException;
	
	/**
	 * 
	 * @param userRoles
	 * @param appsNames
	 * @return
	 * @throws CGSystemException
	 */
	public List<Integer> getAccessScreensIdsByUserRole(
			List<Integer> userRoles, String appsNames)
			throws CGSystemException;

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws CGSystemException 
	 */
	public UserJoinBean getCustomerUserJoinBean(String username,
			String password) throws CGSystemException;

}
