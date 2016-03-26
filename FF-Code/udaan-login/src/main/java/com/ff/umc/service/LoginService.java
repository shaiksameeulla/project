package com.ff.umc.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.LogInOutDetlDO;
import com.ff.domain.umc.MenuNodeDO;
import com.ff.domain.umc.UserDO;
import com.ff.domain.umc.UserRightsDO;
import com.ff.organization.OfficeTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.PasswordTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.action.UserJoinBean;

public interface LoginService {

	
	/**
	 * @Desc authenticates User
	 * @param to
	 * @return UserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	//public UserTO authenticateUser(UserTO to,String contextPath) throws CGBusinessException,CGSystemException;

	/**
	 * @Desc  encrypts the password
	 * @param password
	 * @return encrypted password
	 * @throws CGBusinessException
	 */
	public String getEncryptedPassword(String password)
			throws CGBusinessException;

	/**
	 * @Desc gets EmpUserInfo
	 * @param userId
	 * @return EmployeeUserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public EmployeeUserTO getEmpUserInfo(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @Desc gets CustUserInfo
	 * @param userId
	 * @return CustomerUserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CustomerUserTO getCustUserInfo(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc checks last login 
	 * @param userDO
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public Boolean checkLastLogin(UserJoinBean userJoinBean,Map<String,String> configParam) throws CGSystemException;

	/**
	 * @desc insert login time
	 * @param userTO
	 * @throws CGBusinessException
	 * @throws CGSystemException 
	 */
	public void insertLoginTime(UserTO userTO) throws CGBusinessException, CGSystemException;

	/**
	 * @desc gets last login time
	 * @param userdo
	 * @return LogInOutDetlDO
	 * @throws CGSystemException
	 */
	public LogInOutDetlDO getLastLogin(UserDO userdo) throws CGSystemException;

	/**
	 * @desc gets the password
	 * @param userId
	 * @return PasswordTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public PasswordTO getPassword(UserJoinBean userJoinBean) throws CGBusinessException,
			CGSystemException;

	/**
	 * @desc gets userId by userName
	 * @param username
	 * @return userId
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Integer getUserIdByUsername(String username)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc locks user
	 * @param userdo
	 * @throws CGSystemException
	 */
	public void lockUser(UserDO userdo) throws CGSystemException;

	/**
	 * @desc gets access to screens for user
	 * @param userid
	 * @param userRoles
	 * @param appsName
	 * @return MenuListItems
	 * @throws CGBusinessException
	 */
	public LinkedHashMap<String, MenuNodeDO> getAccessScreensForUser(Integer userid,
			List<Integer> userRoles, String appsName) throws CGBusinessException;

	/**
	 * @desc gets user roles
	 * @param to
	 * @return list of roleIds
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<Integer> getUserRoles(UserTO to) throws CGBusinessException,
			CGSystemException;

	/**
	 * @desc gets allowed screens for user
	 * @param userid
	 * @return list of screenIds
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<Integer> getAllowedScreensForUser(Integer userid)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc upadates password
	 * @param pwdTO
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean updatePassword(PasswordTO pwdTO,String contextPath) throws CGBusinessException,
			CGSystemException;

	/**
	 * @desc validates password
	 * @param loginID
	 * @param newPassword
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean validatePassword(Integer loginID, String newPassword)
			throws CGBusinessException, CGSystemException;

	
	
	/**
	 * @desc checks for if password change required
	 * @param userId
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean updatePwdChangeRequiredFlag(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc getOffice by empId
	 * @param empId
	 * @return OfficeTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException;
	
	
	/**
	 * 
	 * @param custId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeByCustId(Integer custId) throws CGBusinessException,
	CGSystemException;

	/**
	 * @desc generates password
	 * @return generated password
	 * @throws CGBusinessException
	 */
	public String generatePassword() throws CGBusinessException;

	/**
	 * @desc send password mail
	 * @param newPaswd
	 * @param userName
	 * @param mailSubject
	 * @return boolean value
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public Boolean sendPasswordMail(String newPaswd, String userName,String mailSubject, String operatnName,String contextPath)
			throws CGSystemException, CGBusinessException;

	/**@desc validates current password
	 * @param changePaswdTO
	 * @return boolean value
	 * @throws CGBusinessException
	 */
	public Boolean validateCurrentPaswd(PasswordTO changePaswdTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc get user details by userId
	 * @param userId
	 * @return UserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public UserTO getUserDetailsByUserId(Integer userId)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc get all offices by type
	 * @param offType
	 * @return List<OfficeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc get all offices by city
	 * @param cityId
	 * @return List<OfficeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**@ desc get userId by userName
	 * @param username
	 * @param userType
	 * @return userId
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Integer getUserIdByUserNameType(String username, String userType)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc get user by Id
	 * @param userId
	 * @return UserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public UserTO getUserById(Integer userId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @desc get all users bu EmpId
	 * @param empId
	 * @return List<UserTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<UserTO> getAllUsersByEmpId(Integer empId) throws CGBusinessException,
	CGSystemException;
	
	/**
	 * @desc insert logOut time
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public Boolean insertLogoutTime(UserTO userTo ) throws CGSystemException;
	
	/**
	 * @desc validates userName
	 * @param username
	 * @return booelan value
	 * @throws CGSystemException
	 */
	public Boolean validateUsername(String username) throws CGSystemException;
	
	//public UserTO getActiveUserByUser(UserTO userTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc gets User by user
	 * @param userTO
	 * @return UserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public UserTO getUserByUser(UserTO userTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Gets the jdbc offic code.
	 *
	 * @return the jdbc offic code
	 */
	public String getjdbcOfficCode();
	
	public String getjdbcOfficeBuild();

	/**
	 * Gets the user roles list.
	 *
	 * @param to the to
	 * @return the user roles list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<String> getUserRolesList(UserTO to) throws CGBusinessException,
			CGSystemException;
	
	/**
	 * Gets Employee User Join Bean
	 * 
	 * @param username
	 * @param paswd
	 * @return
	 * @throws CGSystemException
	 */
	public UserJoinBean getUserJoinBean(String username,String paswd)throws CGSystemException;
	
	/**
	 * Gets Customer User Join Bean
	 * 
	 * @param username
	 * @param paswd
	 * @return
	 * @throws CGSystemException
	 */
	public UserJoinBean getCustomerUserJoinBean(String username,String paswd)throws CGSystemException;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param contextName
	 * @param configParam
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public UserInfoTO validateUser(String username ,String password,String contextName,Map<String,String> configParam)throws CGBusinessException,
	CGSystemException;
	
	/**
	 * 
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<UserRightsDO> getUserRoleIdsAndNames(UserTO to) throws CGBusinessException,
	CGSystemException;
	
	/**
	 * 
	 * @param userJoinBean
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public EmployeeUserTO getEmpUserInfoFromUserBean (UserJoinBean userJoinBean)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * 
	 * @param officeDo
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeTOByOfficeDO(OfficeDO officeDo) throws CGBusinessException,
	CGSystemException;
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean isPasswordExpired(Integer userId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * 
	 * @param userJoinBean
	 * @param configparam
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean isPasswordExpiredFromUserJavaBean(UserJoinBean userJoinBean,Map<String,String> configparam)
			throws CGBusinessException, CGSystemException;
	
	

}
