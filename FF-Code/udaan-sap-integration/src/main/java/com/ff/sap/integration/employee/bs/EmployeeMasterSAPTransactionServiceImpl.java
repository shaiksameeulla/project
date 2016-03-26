package com.ff.sap.integration.employee.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.SAPEmployeeDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPEmployeeTO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

public class EmployeeMasterSAPTransactionServiceImpl implements EmployeeMasterSAPTransactionService{
	
	Logger logger = Logger.getLogger(EmployeeMasterSAPTransactionServiceImpl.class);
	private OrganizationCommonDAO organizationCommonDAO;
	private GeographyServiceDAO geographyServiceDAO;
	private SAPIntegrationDAO integrationDAO; 
	private EmailSenderUtil emailSenderUtil;
	
	
	
	
	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}
	/**
	 * @param geographyServiceDAO the geographyServiceDAO to set
	 */
	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}
	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}
	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}
	public List<CSDSAPEmployeeDO> getUpdateEmployeeDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: getUpdateEmployeeDOFromTO :: Start");
		List<CSDSAPEmployeeDO> baseList = null;
		if(employees != null && !employees.isEmpty()) {
			baseList = new ArrayList<CSDSAPEmployeeDO>(employees.size());
			CSDSAPEmployeeDO employeeDO = null;//new EmployeeDO();
			for(SAPEmployeeTO employeeTO : employees) {
				if(!StringUtil.isStringEmpty(employeeTO.getEmpCode())){
					String empCode = employeeTO.getEmpCode();
					employeeDO = organizationCommonDAO.getEmployeeDetailsByEmpCode(empCode);
					if(!StringUtil.isNull(employeeDO)){
						//EmployeeDO empDO = null;
						//empDO = employeeDO.get(0);
						logger.debug("EMPLOYEE ::  Emp Updated Code ------->"+employeeDO.getEmpCode());
						CSDSAPEmployeeDO employeDO = null;
						boolean isUpdate = true;
						employeDO = convertEmployeeTOtoDO(employeeTO,isUpdate,employeeDO);
						baseList.add(employeDO);
					}
				}
			}
		}
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: getUpdateEmployeeDOFromTO :: End");
		return baseList;
	}
	private CSDSAPEmployeeDO convertEmployeeTOtoDO(SAPEmployeeTO employeeTO,boolean isUpdate,CSDSAPEmployeeDO empDO) throws CGBusinessException, CGSystemException {
		
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: convertEmployeeTOtoDO :: Start");
		
		CSDSAPEmployeeDO employeeDO = new CSDSAPEmployeeDO();
			if(isUpdate && (!StringUtil.isNull(empDO)
					&& (!StringUtil.isEmptyInteger(empDO.getEmployeeId())))){ 
				employeeDO.setEmployeeId(empDO.getEmployeeId());
			}else{
				employeeDO.setEmployeeId(!StringUtil.isEmptyInteger(employeeTO.getEmployeeId())?employeeTO.getEmployeeId() :null);
				logger.debug("EMPLOYEE ::  Emp ID ----------------->"+employeeDO.getEmployeeId());
			}
				
			if(!StringUtil.isStringEmpty(employeeTO.getDesignation())){
				employeeDO.setDesignation(employeeTO.getDesignation());
			}
			logger.debug("EMPLOYEE ::  Designation----------------->"+employeeDO.getDesignation());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmailId())){
				employeeDO.setEmailId(employeeTO.getEmailId());
			}
			logger.debug("EMPLOYEE ::  EmailID----------------->"+employeeDO.getEmailId());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpCode())){
				employeeDO.setEmpCode(employeeTO.getEmpCode());
			}
			logger.debug("EMPLOYEE ::  Emp Code----------------->"+employeeDO.getEmpCode());
			
			/*if(!StringUtil.isStringEmpty(employeeTO.getEmpFax())){
				employeeDO.setEmpFax(employeeTO.getEmpFax());
			}
			logger.debug("EMPLOYEE ::  Emp Fax----------------->"+employeeDO.getEmpFax());*/
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpPhone())){
				employeeDO.setEmpPhone(employeeTO.getEmpPhone());
			}
			logger.debug("EMPLOYEE ::  phone----------------->"+employeeDO.getEmpPhone());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpType())){
				employeeDO.setEmpType(employeeTO.getEmpType());
			}
			logger.debug("EMPLOYEE ::  type----------------->"+employeeDO.getEmpType());
			
			if(!StringUtil.isStringEmpty(employeeTO.getFirstName())){
				employeeDO.setFirstName(employeeTO.getFirstName());
			}
			logger.debug("EMPLOYEE ::  F Name----------------->"+employeeDO.getFirstName());
			
			if(!StringUtil.isStringEmpty(employeeTO.getLastName())){
				employeeDO.setLastName(employeeTO.getLastName());
			}
			logger.debug("EMPLOYEE ::  L Name----------------->"+employeeDO.getLastName());
			
			/*if(!StringUtil.isNull(employeeTO.getDateOfJoin())){
				employeeDO.setDateOfJoin(employeeTO.getDateOfJoin());
			}
			logger.debug("DOJ----------------->"+employeeDO.getDateOfJoin());*/
		
			List<OfficeDO> officeCodeDO = null;
			if(!StringUtil.isStringEmpty(employeeTO.getOfficeCode())){
				logger.debug("EMPLOYEE ::  Ofice Code ----------------->"+employeeTO.getOfficeCode());
				officeCodeDO = new ArrayList<OfficeDO>();
				String ofcCode = employeeTO.getOfficeCode();
				officeCodeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(ofcCode);
				if(!StringUtil.isEmptyColletion(officeCodeDO)){
					OfficeDO ofcDO = new OfficeDO();
					ofcDO = officeCodeDO.get(0);
					employeeDO.setOfficeId(ofcDO.getOfficeId());
					if(!StringUtil.isEmptyInteger(ofcDO.getCityId())){
						CityTO cityTO = new CityTO();
						cityTO.setCityId(ofcDO.getCityId());
						CityDO cityDO = new CityDO();
						cityDO = geographyServiceDAO.getCity(cityTO);
						if(!StringUtil.isNull(cityDO)){
							employeeDO.setCty(cityDO.getCityId());
							logger.debug("EMPLOYEE ::  City ID ----------------->"+employeeDO.getCty());
						}
					}
				}
				logger.debug("EMPLOYEE ::  Ofice ID ----------------->"+employeeDO.getOfficeId());
			}
			/*CityDO cityDO = null;
			
			if(!StringUtil.isStringEmpty(employeeTO.getCity())){
				cityDO = new CityDO();
				logger.debug("EMPLOYEE ::  City Name ----------------->"+employeeTO.getCity());
				String cityName = employeeTO.getCity();
				cityDO = geographyServiceDAO.getCityByName(cityName);
				if(!StringUtil.isNull(cityDO)){
					if(!StringUtil.isEmptyInteger(cityDO.getCityId())){
						employeeDO.setCity(String.valueOf(cityDO.getCityId()));
						logger.debug("City ID ----------------->"+employeeDO.getCity());
					}
				}
			}*/
		
			DepartmentDO deptDO = null;
			if(!StringUtil.isStringEmpty(employeeTO.getDeptName())){
				deptDO = new DepartmentDO();
				logger.debug("EMPLOYEE ::  Dept Name ----------------->"+employeeTO.getDeptName());	
				String departmentName = employeeTO.getDeptName();
				deptDO = geographyServiceDAO.getDepartmentByName(departmentName);
				if(!StringUtil.isNull(deptDO)){
					if(!StringUtil.isEmptyInteger(deptDO.getDepartmentId())){
						employeeDO.setDepartmentId(deptDO.getDepartmentId());
					}
				}
				logger.debug("Dept ID ----------------->"+employeeDO.getDepartmentId());
			}
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpStatus())){
				employeeDO.setEmpStatus(employeeTO.getEmpStatus());
			}
			logger.debug("EMPLOYEE ::  Emp Status----------------->"+employeeDO.getEmpStatus());
			employeeDO.setEmpVirtual("N");
			employeeDO.setDtToBranch("N");
			
			String userName = "SAP_USER";
			UserDO userDO = integrationDAO.getSAPUserDtls(userName);
			if(!StringUtil.isNull(userDO)
					&& !StringUtil.isEmptyInteger(userDO.getUserId())){
				employeeDO.setCreatedBy(userDO.getUserId());
				employeeDO.setUpdatedBy(userDO.getUserId());
				Date today = Calendar.getInstance().getTime();
				employeeDO.setCreatedDate(today);
				employeeDO.setUpdatedDate(today);
			}
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: convertEmployeeTOtoDO :: End");
		return employeeDO;
	}
@Override
public boolean updateDetails(List<CSDSAPEmployeeDO> updateEmployeeDOs)
		throws CGSystemException {
	logger.debug("EmployeeMasterSAPTransactionServiceImpl :: updateDetails :: START");
	boolean isUpdate = false;
	try{
		isUpdate = integrationDAO.updateEmployeeDetails(updateEmployeeDOs);
	}catch(Exception e){
		logger.error("Exception IN :: EmployeeMasterSAPTransactionServiceImpl :: updateDetails :: ",e);
		 throw new CGSystemException(e);
	}
	return isUpdate;
}
@Override
public List<CSDSAPEmployeeDO> getDOFromTO(List<SAPEmployeeTO> employees)
		throws CGBusinessException, IllegalAccessException,
		InvocationTargetException, NoSuchMethodException, CGSystemException {
	logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: getDOFromTO :: Start");
	List<CSDSAPEmployeeDO> baseList = null;
	if(employees != null && !employees.isEmpty()) {
		baseList = new ArrayList<CSDSAPEmployeeDO>(employees.size());
		CSDSAPEmployeeDO employeeDO = null;//new EmployeeDO();
		for(SAPEmployeeTO employeeTO : employees) {
			/*employeeDO = convertEmployeeTOtoDO(employeeTO);
			baseList.add(employeeDO);*/
			if(!StringUtil.isStringEmpty(employeeTO.getEmpCode())){
				String empCode = employeeTO.getEmpCode();
				employeeDO = organizationCommonDAO.getEmployeeDetailsByEmpCode(empCode);
				if(StringUtil.isNull(employeeDO)){
					CSDSAPEmployeeDO emplDO = null;
					boolean isUpdate = false;
					emplDO = convertEmployeeTOtoDO(employeeTO,isUpdate,employeeDO);
					baseList.add(emplDO);
				}
			}
		}
	}
	logger.debug("EmployeeSAPIntegrationServiceImpl :: getDOFromTO :: End");
	return baseList;
}

@SuppressWarnings("null")
public boolean saveDetailsOneByOneForEmployees(List<CSDSAPEmployeeDO> updateEmployeeDOs) {
	logger.debug("EMPLOYEE :: EmployeeMasterSAPTransactionServiceImpl :: saveDetailsOneByOneForEmployees :: Start");
	List<CSDSAPEmployeeDO> errorEmployeeDOs = null;
	List<SAPEmployeeDO> stagingEmployeeDOs = null;
	SAPEmployeeDO stagingEmployeeDO = null;
	boolean  isSaved = false;
	try{
		//new method created 
		errorEmployeeDOs = integrationDAO.saveDetailsOneByOneForEmployee(updateEmployeeDOs);
		if(!StringUtil.isEmptyColletion(errorEmployeeDOs)){
			for(CSDSAPEmployeeDO errorRecord : errorEmployeeDOs){
				stagingEmployeeDO = new SAPEmployeeDO();
				stagingEmployeeDO = convertErrorRecordsToStagingDOForEmployee(errorRecord);
				stagingEmployeeDOs.add(stagingEmployeeDO);
			}
			if(!StringUtil.isEmptyColletion(stagingEmployeeDOs)){
				isSaved = integrationDAO.saveEmployeeErrorRecords(stagingEmployeeDOs);
				
				if(isSaved){
					//If Saved Successfully into staging table trigger mail to SAP
					sendEmail(stagingEmployeeDOs);
				}
			}
		}
	}catch(Exception e){
		logger.error("EMPLOYEE :: Exception IN :: EmployeeMasterSAPTransactionServiceImpl :: saveDetailsOneByOneForEmployees :: ",e);
	}
	logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: saveDetailsOneByOneForEmployees :: End");
	return isSaved;
}

private SAPEmployeeDO convertErrorRecordsToStagingDOForEmployee(CSDSAPEmployeeDO errorRecord) throws CGSystemException {
	
	logger.debug("EMPLOYEE ::  EmployeeMasterSAPTransactionServiceImpl :: convertErrorRecordsToStagingDO :: Start");
	
	//SAPVendorDO stagingVendorDO = new SAPVendorDO();
	SAPEmployeeDO stagingEmployeeDO = new SAPEmployeeDO();
	if((!StringUtil.isNull(errorRecord)
			&& (!StringUtil.isEmptyInteger(errorRecord.getEmployeeId())))){ 
		stagingEmployeeDO.setEmployeeId(errorRecord.getEmployeeId());
	}/*else{
	stagingVendorDO.setId(!StringUtil.isEmptyInteger(errorRecord.getVendorId())?errorRecord.getVendorId() :null);
	}*/
	logger.debug("EMPLOYEE :: Staging Employee ID ----------------->"+stagingEmployeeDO.getEmployeeId());
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmpCode())){
		stagingEmployeeDO.setEmpCode(errorRecord.getEmpCode());
	}
	logger.debug("EMPLOYEE :: Staging Employee Code----------------->"+stagingEmployeeDO.getEmpCode());
	
	if(!StringUtil.isStringEmpty(errorRecord.getFirstName())){
		stagingEmployeeDO.setFirstName(errorRecord.getFirstName());
	}
	logger.debug("EMPLOYEE :: Employee First Name ----------------->"+stagingEmployeeDO.getFirstName());
	
	
	if(!StringUtil.isStringEmpty(errorRecord.getLastName())){
		stagingEmployeeDO.setLastName(errorRecord.getLastName());
	}
	logger.debug("EMPLOYEE :: Employee Last Name ----------------->"+stagingEmployeeDO.getLastName());
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmpPhone())){
		stagingEmployeeDO.setEmpPhone(errorRecord.getEmpPhone());
	}
	logger.debug("EMPLOYEE :: Emp phone ----------------->"+stagingEmployeeDO.getEmpPhone());
	
	if(!StringUtil.isStringEmpty(errorRecord.getDesignation())){
		stagingEmployeeDO.setDesignation(errorRecord.getDesignation());
	}
	logger.debug("EMPLOYEE :: employee Designation ----------------->"+stagingEmployeeDO.getDesignation());
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmpType())){
		stagingEmployeeDO.setEmpType(errorRecord.getEmpType());
	}
	logger.debug("EMPLOYEE :: Emp type ----------------->"+stagingEmployeeDO.getEmpType());
	
	/*if(!StringUtil.isEmptyInteger(errorRecord.getDepartment())){
		stagingEmployeeDO.setDepartment(errorRecord.getDepartment());
	}
	logger.debug("SeriesLength ----------------->"+stagingEmployeeDO.getDepartment());*/
	
	/*if(!StringUtil.isNull(errorRecord.getPurchaseDate())){
		stagingItemDO.setPurchaseDate(errorRecord.getPurchaseDate());
	}
	logger.debug("PurchaseDate ----------------->"+stagingItemDO.getPurchaseDate());*/
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmailId())){
		stagingEmployeeDO.setEmailId(errorRecord.getEmailId());
	}
	logger.debug("EMPLOYEE :: IsActive ----------------->"+stagingEmployeeDO.getEmailId());
	
	/*if(!StringUtil.isNull(errorRecord.getEmpType())
			&& !StringUtil.isStringEmpty(errorRecord.getItemTypeDO().getItemTypeCode())){
		stagingEmployeeDO.setItemTypeCode(errorRecord.getItemTypeDO().getItemTypeCode());
	}
	logger.debug("ItemTypeDO ----------------->"+stagingEmployeeDO.getItemTypeCode());
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmpType())
			&& !StringUtil.isStringEmpty(errorRecord.getEmpType())){
		stagingEmployeeDO.setEmpType(errorRecord.getEmpType());
	}
	logger.debug("ItemTypeDO ----------------->"+stagingEmployeeDO.getEmpType());*/
	
	if(!StringUtil.isStringEmpty(errorRecord.getEmpStatus())){
		stagingEmployeeDO.setEmpStatus(errorRecord.getEmpStatus());
	}
	logger.debug("EMPLOYEE :: Description ----------------->"+stagingEmployeeDO.getEmpStatus());
	
	/*if(!StringUtil.isStringEmpty(errorRecord.getException())){
		stagingEmployeeDO.setException(errorRecord.getException());
	}*/
	logger.debug("EMPLOYEE :: Exceptioon ----------------->"+stagingEmployeeDO.getException());
	
	/*if(!StringUtil.isNull(errorRecord.getIsSeriesVerifier())){
		stagingItemDO.setIsSeriesVerifier(errorRecord.getIsSeriesVerifier());
	}
	logger.debug("IsSeriesVerifier ----------------->"+stagingItemDO.getIsSeriesVerifier());*/
	
	//Add RHO CODE
	logger.debug("EMPLOYEE :: EmployeeMasterSAPTransactionServiceImpl :: convertErrorRecordsToStagingDO :: End");
	return stagingEmployeeDO;
}

private void sendEmail(List<SAPEmployeeDO> stagingEmployeeDOs) throws CGBusinessException, CGSystemException {
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: sendEmail :: Start");
	try {
		List<MailSenderTO> mailerList = new ArrayList<>();
		//Prepare Subject and add it into Mail Body
		StringBuilder plainMailBody = prepareMailBody(stagingEmployeeDOs);
		//String subject="your complaint with reference number "+serviceRequestFollowupDO.getServiceRequestDO().getServiceRequestNo()+" has been followup";
		//subject=subject+" for consignment/Booking ref no: "+serviceRequestFollowupDO.getServiceRequestDO().getBookingNo();
		//StringBuilder plainMailBody = getMailBody(subject);
		prepareCallerMailAddress(mailerList,plainMailBody);
		//prepareExecutiveMail(serviceRequestFollowupDO, mailerList);
		for(MailSenderTO senderTO:mailerList){
			emailSenderUtil.sendEmail(senderTO);
		}
	} catch (Exception e) {
		logger.error("EMPLOYEE :: ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",e);
	}
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: sendEmail :: End");
}

private StringBuilder prepareMailBody(List<SAPEmployeeDO> stagingEmployeeDOs){
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: Start");
	StringBuilder plainMailBody = new StringBuilder();
	plainMailBody.append("<html><body> Dear Sir/madam");
	plainMailBody.append("<br/><br/>Error came while processing Employee Records to CSD Database");
	if(!StringUtil.isEmptyColletion(stagingEmployeeDOs)){
		for(SAPEmployeeDO stagingEmployeeDO : stagingEmployeeDOs){
			if(!StringUtil.isStringEmpty(stagingEmployeeDO.getEmpCode())){
				plainMailBody.append("<br/><br/>Employee Code : "+stagingEmployeeDO.getEmpCode());
			}
			if(!StringUtil.isStringEmpty(stagingEmployeeDO.getException())){
				plainMailBody.append("<br/><br/>Exception : "+stagingEmployeeDO.getException());
			}
		}
	}
	plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: prepareMailBody :: End");
	return plainMailBody;
}

private void prepareCallerMailAddress(List<MailSenderTO> mailerList,StringBuilder plainMailBody) {
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: Start");
	MailSenderTO callerSenderTO=new MailSenderTO();
	callerSenderTO.setTo(new String[]{SAPIntegrationConstants.EMAILD_ID});
	//callerSenderTO.setMailSubject(subject);
	callerSenderTO.setPlainMailBody(plainMailBody.toString());
	mailerList.add(callerSenderTO);
	logger.debug("EMPLOYEE :: MaterialMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: End");
}
	@Override
	public boolean saveDetailsForEmployee(List<CSDSAPEmployeeDO> employeeDOs)
			throws CGSystemException {
		logger.debug("EmployeeMasterSAPTransactionServiceImpl :: saveDetailsForEmployee :: START");
		boolean isUpdate = false;
		try{
			isUpdate = integrationDAO.saveDetailsForEmployee(employeeDOs);
		}catch(Exception e){
			logger.error("Exception IN :: EmployeeMasterSAPTransactionServiceImpl :: saveDetailsForEmployee :: ",e);
			 throw new CGSystemException(e);
		}
		return isUpdate;
	}

}

	
