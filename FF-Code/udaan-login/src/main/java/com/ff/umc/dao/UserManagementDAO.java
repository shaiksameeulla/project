package com.ff.umc.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.CustomerUserDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;


public interface UserManagementDAO {
	/**
	 * @desc save CustomerUser
	 * @param custUserDO
	 * @return booelan value
	 * @throws CGSystemException
	 */
	public boolean saveCustomerUser(CustomerUserDO custUserDO )	throws CGSystemException;

	/**
	 * @desc save EmployeeUser
	 * @param empUserDO
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public boolean saveEmployeeUser(EmployeeUserDO empUserDO )	throws CGSystemException;
	
	/**
	 * @desc save user
	 * @param userDO
	 * @throws CGSystemException
	 */
	public void saveUser(UserDO userDO )	throws CGSystemException;
	
	/**
	 * @desc activateDeActivate User
	 * @param userName
	 * @param statusType
	 * @return boolean value
	 * @throws CGSystemException
	 */
	public boolean activateDeActivateUser(String userName,String statusType) throws CGSystemException;

	/**
	 * @desc get CustDO By CustName
	 * @param custName
	 * @return CustomerDO
	 * @throws CGSystemException
	 */
	public CustomerDO getCustDOByCustName(String custCode) throws CGSystemException;
	
	/**
	 * @desc get EmpDO By FName
	 * @param custName
	 * @return EmployeeDO
	 * @throws CGSystemException
	 */
	public EmployeeDO getEmpDOByFName(String fName,String lName) throws CGSystemException;
	
	/**
	 * @desc get CustUserDO by CustId
	 * @param custId
	 * @return CustomerUserDO
	 * @throws CGSystemException
	 */
	public CustomerUserDO getCustUserDObyCustId(Integer custId) throws CGSystemException;
	
	/**
	 * @desc get EmpUserDO by EmpId
	 * @param empId
	 * @return EmployeeUserDO
	 * @throws CGSystemException
	 */
	public EmployeeUserDO getEmpUserDObyEmpId(Integer empId) throws CGSystemException;
	
	/**
	 * @desc update EmpEmail
	 * @param empId
	 * @param email
	 * @return boolean value
	 */
	public boolean updateEmpEmail(Integer empId,String email)throws CGSystemException;
	
	public boolean updateCustEmail(Integer custId,String email)throws CGSystemException;
	/**
	 * @desc get OfficeName By OfficeId
	 * @param officeId
	 * @return OfficeDO
	 */
	public OfficeDO getOfficeNameByOfficeId(Integer officeId);
	
	public boolean isUserNameExists(String userName) throws CGSystemException;
	
	public String getCustDescByCustTypeId(String custTypeId)
			throws CGSystemException ;
	
	public CityDO getCityDOByOfficId(Integer mappedOfficId)
			throws CGSystemException;
	
	public CustomerTypeDO getCustTypeDescByCustTypeId(Integer custTypeId)
			throws CGSystemException ;
	
	
	public EmployeeDO getEmpDOByEmpCode(String empCode) throws CGSystemException;
	public boolean updateOnlyEmpEmail(Integer empId, String email)throws CGSystemException;

	/**
	 * 
	 * @param cityId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<String> getCustomerByCityId(Integer cityId) throws CGSystemException, CGBusinessException;
}
