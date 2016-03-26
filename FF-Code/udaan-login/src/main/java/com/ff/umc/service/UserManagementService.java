package com.ff.umc.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.organization.EmployeeTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.EmployeeUserTO;


public interface UserManagementService {
	
	/**
	 * @desc get CustTO By CustName
	 * @param custName
	 * @return CustomerTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CustomerTO getCustTOByCustName(String custCode) throws CGBusinessException, CGSystemException;

	/**
	 * @desc save CustomerUser
	 * @param custUser
	 * @param userId
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveCustomerUser(CustomerUserTO custUser,Integer userId)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc save User
	 * @param userName
	 * @param loginUserId
	 * @param userType
	 * @return Integer value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Integer saveUser(String userName ,Integer loginUserId, String userType)
			throws CGBusinessException, CGSystemException;

	/**
	 * @desc activateDeActivate CustUser
	 * @param username
	 * @param status
	 * @return String value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean activateDeActivateCustUser(String username,String status,String contextPath )
			throws CGBusinessException, CGSystemException;
	
	/*public String activateDeActivateEmpUser(EmployeeUserTO empUser)
			throws CGBusinessException, CGSystemException;
	*/
	/**
	 * @desc get EmpTO By EmpName 
	 * @param empName
	 * @return EmployeeTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public EmployeeTO getEmpTOByEmpName(String fName,String lName) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc get CustUserTO by CustId
	 * @param custId
	 * @return CustomerUserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CustomerUserTO getCustUserTObyCustId(Integer custId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc get EmpUserTO by EmpId
	 * @param custId
	 * @return EmployeeUserTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public EmployeeUserTO getEmpUserTObyEmpId(Integer custId) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc saveUpdate Emp
	 * @param loginUserId
	 * @param empUserTO
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveUpdateEmp(Integer loginUserId,EmployeeUserTO empUserTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc saveUpdate Emp
	 * @param loginUserId
	 * @param userName
	 * @param empID
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveUpdateEmp(Integer loginUserId,String userName,Integer empID) throws CGBusinessException,CGSystemException; 
			
	/**
	 * @desc reset Password
	 * @param userName
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean resetPassword(String userName,String operationName,String contextPath) throws CGBusinessException, CGSystemException;
	
	/**
	 * @desc generate Password And Sends Mail
	 * @return boolean value
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public Boolean generatePasswordAndSendMail() throws CGBusinessException,CGSystemException ;
	
	/**
	 * @desc update UserStatus
	 * @param userName
	 * @param status
	 * @return boolean value
	 */
	public boolean updateUserStatus(String userName, String status,String contextPath) throws CGBusinessException ;
	
	/**
	 * @desc update EmpEmail
	 * @param empId
	 * @param email
	 * @return boolean value
	 */
	public boolean updateEmpEmail(Integer empId, String email)throws CGSystemException ;
	
	/**
	 * 
	 * @param empId
	 * @param email
	 * @return
	 * @throws CGSystemException
	 */
	public boolean updateCustEmail(Integer empId, String email)throws CGSystemException ;
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean isUserNameExists(String userName)throws CGBusinessException,CGSystemException;
	
	/**
	 * 
	 * @param empCode
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public EmployeeTO getEmpTOByEmpCode(String empCode) throws CGBusinessException, CGSystemException;

	/**
	 * 
	 * @param custId
	 * @param email
	 * @return
	 * @throws CGSystemException
	 */
	boolean saveCustEmail(Integer custId,String email) throws CGSystemException;

	/**
	 * 
	 * @param userName
	 * @param loginUserId
	 * @param userType
	 * @param custUserTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveUserForCustomer(String userName, Integer loginUserId,
			String userType, CustomerUserTO custUserTO) throws CGBusinessException, CGSystemException;

	/**
	 * 
	 * @param empID
	 * @param email
	 * @return
	 * @throws CGSystemException
	 */
	public boolean saveEmpEmail(Integer empID,String email) throws CGSystemException;

	/**
	 * 
	 * @param loginUserId
	 * @param empNames
	 * @param empID
	 * @param emailId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean saveSelectedUsers(Integer loginUserId,String empNames,Integer empID,String emailId) throws CGBusinessException, CGSystemException;

	/**
	 * 
	 * @param empId
	 * @param email
	 * @return
	 * @throws CGSystemException
	 */
	public boolean updateOnlyEmpEmail(Integer empId, String email) throws CGSystemException;
	
	/**
	 * 
	 * @param cityId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<String> getCustomerTObyCityId(Integer cityId) throws CGBusinessException, CGSystemException;

}
