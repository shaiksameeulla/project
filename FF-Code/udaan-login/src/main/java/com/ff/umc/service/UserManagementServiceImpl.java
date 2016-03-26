package com.ff.umc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.CustomerUserDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.organization.EmployeeTO;
import com.ff.umc.CustomerUserTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.umc.dao.LoginDAO;
import com.ff.umc.dao.UserManagementDAO;

public class UserManagementServiceImpl implements UserManagementService {

	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoginServiceImpl.class);
	private UserManagementDAO umcDAO;
	private LoginDAO loginDAO;
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setUmcDAO(UserManagementDAO umcDAO) {
		this.umcDAO = umcDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	@Override
	public boolean activateDeActivateCustUser(String username, String status,String contextPath)
			throws CGBusinessException, CGSystemException {
		boolean isUserUpdated = Boolean.FALSE;
		try {

			isUserUpdated = umcDAO.activateDeActivateUser(username, status);
			if (isUserUpdated) {
				if (status.equalsIgnoreCase("active")) {
					//opnName = "Activate";
					resetPassword(username, status,contextPath);
				} 
			}
			/*if (isUserUpdated)
				updateStatus = UmcConstants.SUCCESS;*/

		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementServiceImpl :: activateDeActivateUser()..:"
					+ e.getMessage());
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(
					"UM004", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
		}
		return isUserUpdated;

	}

	@Override
	public CustomerTO getCustTOByCustName(String custCode)
			throws CGBusinessException, CGSystemException {
		CustomerTO custTo =null;
		CustomerDO custDO = umcDAO.getCustDOByCustName(custCode);
		CustomerTypeTO custTypeTO=null;
		if(!StringUtil.isStringEmpty(custDO.getStatus())){
		String status = custDO.getStatus().trim();
		custDO.setStatus(status);
		}
		//setting the city name
		if(!StringUtil.isEmptyInteger(custDO.getSalesOfficeDO().getOfficeId())){
			CityDO cityDO =  umcDAO.getCityDOByOfficId(custDO.getSalesOfficeDO().getOfficeId());
		
			if(!StringUtil.isStringEmpty(cityDO.getCityName())){
				custDO.setCity(cityDO.getCityName());
			}
		}
		/*//setting the custType desc
		if(!StringUtil.isStringEmpty(custDO.getCustomerType().getCustomerTypeCode())){
			 custTypeDO =  umcDAO.getCustTypeDescByCustTypeId(custDO.getCustomerType().getCustomerTypeId());
		
			if(!StringUtil.isStringEmpty(custTypeDO.getCustomerTypeDesc())){
				custDO.setCustomerType(custTypeDO);
			}
		}
		*/
		
		if (custDO != null) {
			custTo = new CustomerTO();
			custTypeTO=new CustomerTypeTO();
			CGObjectConverter.createToFromDomain(custDO.getCustomerType(), custTypeTO);
			CGObjectConverter.createToFromDomain(custDO, custTo);
			custTo.setCustomerTypeTO(custTypeTO);

		}
		return custTo;
	}

	@Override
	public boolean saveCustomerUser(CustomerUserTO custUserTO, Integer userId)
			throws CGBusinessException, CGSystemException {
		boolean isCustAdded = Boolean.FALSE;
		CustomerUserDO custUserDO = new CustomerUserDO();
		CustomerDO custDO = new CustomerDO();
		
			custUserDO.setUserId(userId);
			custDO.setCustomerId(custUserTO.getCustTO().getCustomerId());
			custUserDO.setCustDO(custDO);
			custUserDO.setCreatedDate(Calendar.getInstance().getTime());
			custUserDO.setUpdatedDate(Calendar.getInstance().getTime());
			isCustAdded = umcDAO.saveCustomerUser(custUserDO);
		
		return isCustAdded;
	}

	private boolean saveEmployeeUser(EmployeeUserTO empUserTO, Integer userId,
			Integer loginUserId) throws CGBusinessException, CGSystemException {
		boolean isEmpAdded = Boolean.FALSE;
		EmployeeUserDO empUserDO = new EmployeeUserDO();
		EmployeeDO empDO = new EmployeeDO();
		try {
			// For update mode
			if (empUserTO.getEmpUserId() != null
					&& empUserTO.getEmpUserId() > 0) {
				empUserDO.setEmpUserId(empUserTO.getEmpUserId());
			}
			empDO.setUserId(userId);
			empDO.setEmployeeId(empUserTO.getEmpTO().getEmployeeId());
			empUserDO.setUserId(userId);
			empUserDO.setEmpDO(empDO);
			empUserDO.setCreatedBy(loginUserId);
			empUserDO.setUpdatedBy(loginUserId);
			empUserDO.setDtToBranch(UmcConstants.FLAG_N);
			empUserDO.setDtToCentral(UmcConstants.FLAG_N);
			empUserDO.setDtUpdateToCentral(UmcConstants.FLAG_N);
			empUserDO.setCreatedDate(Calendar.getInstance().getTime());
			empUserDO.setUpdatedDate(Calendar.getInstance().getTime());
			isEmpAdded = umcDAO.saveEmployeeUser(empUserDO);
		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementServiceImpl :: saveEmployeeUser()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return isEmpAdded;
	}

	public boolean updateEmpEmail(Integer empId, String email) throws CGSystemException {
		boolean isEmailUpdated = Boolean.FALSE;
		isEmailUpdated = umcDAO.updateEmpEmail(empId, email);
		return isEmailUpdated;
	}
	
	public boolean updateCustEmail(Integer custId, String email) throws CGSystemException {
		boolean isEmailUpdated = Boolean.FALSE;
		isEmailUpdated = umcDAO.updateCustEmail(custId, email);
		return isEmailUpdated;
	}

	@Override
	public Integer saveUser(String userName, Integer loginUserId,
			String userType) throws CGBusinessException, CGSystemException {
		Integer userId = 0;
		UserDO userDO = new UserDO();
		try {
			// For Update mode
			if (userDO.getUserId() != null && userDO.getUserId() > 0) {
				userDO = loginDAO.getUserById(userDO.getUserId());
				userDO.setUserName(userName);
				userDO.setDtToBranch(UmcConstants.FLAG_N);
				userDO.setDtUpdateToCentral(UmcConstants.FLAG_Y);
				userDO.setUpdatedDate(Calendar.getInstance().getTime());
			} else {
				// For saving for the first time in user table
				userDO.setLocked(UmcConstants.FLAG_N);
				userDO.setActive(UmcConstants.FLAG_N);
				userDO.setUserType(userType);
				userDO.setCreatedDate(Calendar.getInstance().getTime());
				userDO.setUpdatedDate(Calendar.getInstance().getTime());
				userDO.setCreatedBy(loginUserId);
				userDO.setLoginAttempt(0);
				userDO.setUserCode(userName);
				userDO.setUserName(userName);
				userDO.setDtToBranch(UmcConstants.FLAG_N);
				userDO.setDtToCentral(UmcConstants.FLAG_N);
				userDO.setDtUpdateToCentral(UmcConstants.FLAG_N);
			}
			// For update
			umcDAO.saveUser(userDO);
			userId = userDO.getUserId();
		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementServiceImpl :: saveUser()..:"
					+ e.getMessage());
			throw new CGSystemException(e);
		}
		return userId;
	}

	@Override
	public EmployeeTO getEmpTOByEmpName(String fName,String lName)
			throws CGBusinessException, CGSystemException {
		EmployeeTO empTo=new EmployeeTO();
		EmployeeDO empDO=umcDAO.getEmpDOByFName(fName,lName);
		
		if(!StringUtil.isNull(empDO) && !StringUtil.isEmptyInteger(empDO.getOfficeId())){
		OfficeDO officeDO=umcDAO.getOfficeNameByOfficeId(empDO.getOfficeId());
			
				CGObjectConverter.createToFromDomain(empDO, empTo);
				empTo.setOfficeName(officeDO.getOfficeName());
		 
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper("UM001", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
			
		}
		
		return empTo;
	}
	public boolean updateUserStatus(String userName, String status,String contextPath)
			throws CGBusinessException {
		boolean isUserUpdated = Boolean.FALSE;
		String opnName = null;
		try {
			isUserUpdated = umcDAO.activateDeActivateUser(userName, status);
			if (isUserUpdated) {
				if (status.equalsIgnoreCase("active")) {
					opnName = "Activate";
					resetPassword(userName, opnName,contextPath);
				} 
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in UserManagementServiceImpl :: updateUserStatus()..:"
					,e);
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(
					"UM004", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);

		}
		return isUserUpdated;
	}


	/*
	 * @Override public String activateDeActivateEmpUser(EmployeeUserTO
	 * empUser)//FIXME //TODO throws CGBusinessException, CGSystemException {
	 * String updateStatus = ""; boolean isUserUpdated = Boolean.FALSE; UserTO
	 * userto=new UserTO(); try {
	 * 
	 * if (empUser != null) { if (empUser.getUserToList() != null &&
	 * !empUser.getUserToList().isEmpty()) { for (UserTO userTO :
	 * empUser.getUserToList()) { userto.setActive(userTO.getActive());
	 * userto.setUserName(userTO.getUserName()); isUserUpdated
	 * =umcDAO.activateDeActivateUser(userto.getUserName(), userto.getActive());
	 * 
	 * } } }
	 * 
	 * //String userName = empUser.getUserTO().getUserName(); //String status =
	 * empUser.getUserTO().getActive(); //isUserUpdated =
	 * umcDAO.activateDeActivateUser(userName, status); if (isUserUpdated)
	 * updateStatus = UmcConstants.SUCCESS;
	 * 
	 * } catch (Exception e) { updateStatus = UmcConstants.FAILURE;
	 * LOGGER.error(
	 * "Error occured in UserManagementServiceImpl :: activateDeActivateUser()..:"
	 * + e.getMessage()); } return updateStatus;
	 * 
	 * }
	 */

	@Override
	public CustomerUserTO getCustUserTObyCustId(Integer custId)
			throws CGBusinessException, CGSystemException {
		CustomerUserTO custUserTO = new CustomerUserTO();
		CustomerUserDO custUserDO = new CustomerUserDO();

		custUserDO = umcDAO.getCustUserDObyCustId(custId);
		if (custUserDO != null)
			CGObjectConverter.createToFromDomain(custUserDO, custUserTO);
		else
			custUserTO = null;

		return custUserTO;
	}

	@Override
	public EmployeeUserTO getEmpUserTObyEmpId(Integer empId)
			throws CGBusinessException, CGSystemException {
		EmployeeUserTO empUserTO = new EmployeeUserTO();
		EmployeeUserDO empUserDO = null;

		empUserDO = umcDAO.getEmpUserDObyEmpId(empId);

		if (empUserDO != null) {
			List<UserTO> userTOs = loginService.getAllUsersByEmpId(empId);
			empUserTO.setUserToList(userTOs);
			CGObjectConverter.createToFromDomain(empUserDO, empUserTO);
			
		} else
			empUserTO = null;

		return empUserTO;
	}

	public boolean saveUpdateEmp(Integer loginUserId, EmployeeUserTO empUserTO)
			throws CGBusinessException, CGSystemException {
		UserTO userto = new UserTO();
		boolean isEmpDetailsSaved = Boolean.FALSE;
		if (empUserTO != null) {
			if (empUserTO.getUserNames() != null
					&& empUserTO.getUserNames().length > 0) {
				for (String empName : empUserTO.getUserNames()) {
					userto.setUserName(empName);
					Integer userId = saveUser(userto.getUserName(),
							loginUserId, UmcConstants.FLAG_E);
					isEmpDetailsSaved = saveEmployeeUser(empUserTO, userId,
							loginUserId);
				}
			}
		}
		// Integer userId = saveUser(userto, loginUserId);
		// boolean isEmpDetailsSaved = saveEmployeeUser(empUserTO, userId);

		return isEmpDetailsSaved;
	}

	public boolean saveUpdateEmp(Integer loginUserId, String userName,
			Integer empID) throws CGBusinessException, CGSystemException {
		boolean isEmpDetailsSaved = Boolean.FALSE;
		Integer userId = saveUser(userName, loginUserId, UmcConstants.FLAG_E);
		if (userId != 0) {
			EmployeeUserTO empUserTO = new EmployeeUserTO();
			EmployeeTO empTO = new EmployeeTO();
			empTO.setEmployeeId(empID);
			empUserTO.setUserId(userId);
			empUserTO.setEmpTO(empTO);
			isEmpDetailsSaved = saveEmployeeUser(empUserTO, userId, loginUserId);
		}
		return isEmpDetailsSaved;
	}

	public Boolean resetPassword(String userName, String oprationName,String contextPath)
			throws CGBusinessException, CGSystemException {
		String newPaswd = null;
		try{
		 newPaswd = loginService.generatePassword();
		}catch(Exception ex){
			LOGGER.error("Error occured in UserManagementServiceImpl :: resetPassword()..:"
					+ ex.getMessage());
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(
					"UM003", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
		}
		boolean isPwdMailSent = Boolean.FALSE;
		try {
			isPwdMailSent = loginService.sendPasswordMail(newPaswd, userName,
					"Request for Reset Password", oprationName,contextPath);
		} catch (Exception ex) {
			LOGGER.error("Error occured in UserManagementServiceImpl :: resetPassword()..:"
					, ex);
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(
					"UM002", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);


		}
		if (isPwdMailSent)
			return true;
		else
			return false;
	}

	public Boolean generatePasswordAndSendMail() throws CGBusinessException,
			CGSystemException {

		// loginService.sendPasswordMail(newPaswd, userName);
		return true;
	}

	@Override
	public boolean isUserNameExists(String userName)
			throws CGBusinessException, CGSystemException {
		boolean isDuplicate = Boolean.TRUE;
		isDuplicate = umcDAO.isUserNameExists(userName);
		return isDuplicate;
	}

	@Override
	public EmployeeTO getEmpTOByEmpCode(String empCode)
			throws CGBusinessException, CGSystemException {
		EmployeeTO empTo=new EmployeeTO();
		EmployeeDO empDO=umcDAO.getEmpDOByEmpCode(empCode);
		
		if(!StringUtil.isNull(empDO) && !StringUtil.isEmptyInteger(empDO.getOfficeId())){
		OfficeDO officeDO=umcDAO.getOfficeNameByOfficeId(empDO.getOfficeId());
			
				CGObjectConverter.createToFromDomain(empDO, empTo);
				empTo.setOfficeName(officeDO.getOfficeName());
		 
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper("UM001", MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
			
		}
		
		return empTo;
	}
	
	@Override
	public boolean saveUserForCustomer(String userName, Integer loginUserId,
			String userType, CustomerUserTO custUserTO) throws CGBusinessException, CGSystemException {
		Integer userId = 0;
		UserDO userDO = new UserDO();
		Date createddate = new Date();
		boolean isCustomerDetailsSaved = false;
		boolean isCustEmailSaved = false;

		
			// For Update mode
			if (userDO.getUserId() != null && userDO.getUserId() > 0) {
				userDO = loginDAO.getUserById(userDO.getUserId());
				userDO.setUserName(userName);
				userDO.setDtToBranch(UmcConstants.FLAG_N);
				userDO.setDtUpdateToCentral(UmcConstants.FLAG_Y);
				userDO.setUpdatedDate(Calendar.getInstance().getTime());
			} else {
				// For saving for the first time in user table
				userDO.setLocked(UmcConstants.FLAG_N);
				userDO.setActive(UmcConstants.FLAG_N);
				userDO.setUserType(userType);
				userDO.setCreatedDate(createddate);
				userDO.setUpdatedDate(Calendar.getInstance().getTime());
				userDO.setCreatedBy(loginUserId);
				userDO.setLoginAttempt(0);
				userDO.setUserCode(userName);
				userDO.setUserName(userName);
				userDO.setDtToBranch(UmcConstants.FLAG_N);
				userDO.setDtToCentral(UmcConstants.FLAG_N);
				userDO.setDtUpdateToCentral(UmcConstants.FLAG_N);
			}
			// For update
			umcDAO.saveUser(userDO);
			userId = userDO.getUserId();
			
			
			isCustomerDetailsSaved = saveCustomerUser(custUserTO,userId);
			if(isCustomerDetailsSaved){
				isCustEmailSaved=saveCustEmail(custUserTO.getCustTO().getCustomerId(),custUserTO.getCustTO().getEmail());
				}	
		
		return isCustEmailSaved;
	}
	
	public boolean saveCustEmail(Integer custId,String email) throws CGSystemException{
		boolean isCustEmailSaved=Boolean.FALSE;
		isCustEmailSaved = updateCustEmail(custId,email);
		return isCustEmailSaved;
	}
	public boolean saveSelectedUsers(Integer loginUserId,String empNames,Integer empID,String emailId) throws CGBusinessException, CGSystemException {
		boolean isEmpDetailsSaved=Boolean.FALSE;
		boolean isDuplicate=Boolean.TRUE;
		
		if(empNames!=null&&!empNames.isEmpty()){
			
			String[] userNamesArr=empNames.split(",");
			for(String userName:userNamesArr)
			{
				if(!StringUtil.isStringEmpty(userName))	{
					isDuplicate=isUserNameExists(userName);
					if(!isDuplicate){
					isEmpDetailsSaved = saveUpdateEmp(loginUserId, userName,empID);
					}else{
						
						MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper("UM005", MessageType.Warning);
						throw new CGBusinessException(msgWrapper);
					}
				}
			}
			
		}
		
		if(isEmpDetailsSaved && !StringUtil.isNull(emailId)){
			saveEmpEmail(empID,emailId);
		}
		return isEmpDetailsSaved;
	}
	
	public boolean saveEmpEmail(Integer empID,String email) throws CGSystemException{
		boolean isEmpEmailSaved=Boolean.FALSE;
		isEmpEmailSaved = updateEmpEmail(empID,email);
		return isEmpEmailSaved;
	}
	
	public boolean updateOnlyEmpEmail(Integer empId, String email) throws CGSystemException {
		boolean isEmailUpdated = Boolean.FALSE;
		isEmailUpdated = umcDAO.updateOnlyEmpEmail(empId, email);
		return isEmailUpdated;
	}

	@Override
	public List<String> getCustomerTObyCityId(Integer cityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("UserManagementServiceImpl::getCustomerTObyCityId::START------->");
		
		List<String> customerTO =	umcDAO.getCustomerByCityId(cityId);
		
		LOGGER.trace("UserManagementServiceImpl::getCustomerTObyCityId::END------->");
		return customerTO;
	}
	
}
