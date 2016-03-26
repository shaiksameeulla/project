package com.ff.sap.integration.employee.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPEmployeeTO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

public class EmployeeSAPIntegrationServiceImpl implements EmployeeSAPIntegrationService {

	Logger logger = Logger.getLogger(EmployeeSAPIntegrationServiceImpl.class);
	private SAPIntegrationDAO integrationDAO; 
	private OrganizationCommonDAO organizationCommonDAO;
	private GeographyServiceDAO geographyServiceDAO;
	private EmployeeMasterSAPTransactionService employeeSAPTransactionService;
	
	
	
	/**
	 * @param employeeSAPTransactionService the employeeSAPTransactionService to set
	 */
	public void setEmployeeSAPTransactionService(
			EmployeeMasterSAPTransactionService employeeSAPTransactionService) {
		this.employeeSAPTransactionService = employeeSAPTransactionService;
	}

	@Override
	public boolean saveEmployeesDetails(List<SAPEmployeeTO> employees,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		logger.debug("EMPLOYEE :: EmployeeSAPIntegrationServiceImpl :: saveEmployeesDetails :: Start");
		boolean isSaved = false;
		boolean isUpdate = false;
		List<CSDSAPEmployeeDO> employeeDOs;
		List<CSDSAPEmployeeDO> updateEmployeeDOs;
		//List<CSDSAPEmployeeDO> sapEmployeeDOs;
		try {
			//fisrt chk data if present - Update
			updateEmployeeDOs = employeeSAPTransactionService.getUpdateEmployeeDOFromTO(employees);
			if(!StringUtil.isNull(updateEmployeeDOs)
					&&(!StringUtil.isEmptyColletion(updateEmployeeDOs)) && !iterateOneByOne){
				logger.debug("EMPLOYEE ::  Inside Update Method !!!!0000");
				isUpdate = employeeSAPTransactionService.updateDetails(updateEmployeeDOs);
				if(isUpdate){
					logger.debug("EMPLOYEE ::  Deactivate User Status start------>");
					//isUpdate = false;
					updateEmpUserStatus(employees,updateEmployeeDOs);
					//integrationDAO.saveDetails(empDOs);
				}
			}/*else if(!isUpdate && !StringUtil.isEmptyColletion(updateEmployeeDOs)){
				//not complete
				isSaved = employeeSAPTransactionService.saveDetailsOneByOneForEmployees(updateEmployeeDOs);
			}*/
			//If Batch Insert/Update fails then try to insert/update records one by one 
			// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
			//for error records
			//Emp code New - Save
			employeeDOs = employeeSAPTransactionService.getDOFromTO(employees);
			if(!StringUtil.isNull(employeeDOs)
					&&(!StringUtil.isEmptyColletion(employeeDOs)) && !iterateOneByOne){
				isSaved = employeeSAPTransactionService.saveDetailsForEmployee(employeeDOs);
			}/*else if(!isSaved && !StringUtil.isEmptyColletion(employeeDOs)){
				isSaved = employeeSAPTransactionService.saveDetailsOneByOneForEmployees(employeeDOs);;
			}*/
		} catch (CGSystemException e) {
			logger.debug("EMPLOYEE :: EXCEPTION IN :: EmployeeSAPIntegrationServiceImpl :: saveEmployeesDetails ",e);
		}
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: saveEmployeesDetails :: End");
		return isSaved;
	}

	private void updateEmpUserStatus(List<SAPEmployeeTO> employees,List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException {
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: updateEmpUserStatus :: Start");
		/*List<CGBaseDO> baseList = null;	
		if(employees != null && !employees.isEmpty()) {			
			baseList = new ArrayList<CGBaseDO>(employees.size());
			CSDSAPEmployeeDO empDO = new CSDSAPEmployeeDO();*/
			if(!StringUtil.isNull(updateEmployeeDOs) && (!StringUtil.isEmptyColletion(updateEmployeeDOs))){ 
				CSDSAPEmployeeDO emplDO = null;
				boolean isUpdate = false;
				logger.debug("EMPLOYEE ::  updateEmployeeDOs size---->"+updateEmployeeDOs.size());
				//changes
					for(CSDSAPEmployeeDO employeeDO : updateEmployeeDOs){
						logger.debug("EMPLOYEE ::  Itearate updateEmployeeDOs inside for loop");
						logger.debug("EMPLOYEE ::  employeeDO"+employeeDO);
						emplDO = (CSDSAPEmployeeDO)employeeDO;
						logger.debug("EMPLOYEE ::  Updated Emp DO Code----------->"+emplDO.getEmpCode());
						if(!StringUtil.isStringEmpty(emplDO.getEmpStatus())
								&& emplDO.getEmpStatus().equalsIgnoreCase("I")){
							logger.debug("EMPLOYEE ::  Update Emp Status------------------------------->"+emplDO.getEmpStatus());
							// Get  User ID from ff_d_emp_user Table on the basis on EMP_ID
							List<EmployeeUserDO> empUserDO = integrationDAO.getEmployeeUserDtlsByEmpID(emplDO.getEmployeeId());
							if(!StringUtil.isEmptyColletion(empUserDO)){
								logger.debug("EMPLOYEE ::  EmployeeUserDO Size --->"+empUserDO.size());
								for(EmployeeUserDO emplUserDO : empUserDO){
									logger.debug("EMPLOYEE ::  EmployeeUserDO user ID is --->"+emplUserDO.getUserId());
									if(!StringUtil.isEmptyInteger(emplUserDO.getUserId())){
										logger.debug("EMPLOYEE ::  User Status updation process Starts--->");
										isUpdate = integrationDAO.deactiveUaserDtls(emplUserDO.getUserId());
										if(isUpdate){
											logger.debug("EMPLOYEE ::  User Table Updated Successfully");
										}
									}
								}
							}
						}
					}
			}
			//logger.debug("EMPLOYEE ::  baseList########## Employee DO Size----------------->"+baseList.size());
		//}
		logger.debug("EMPLOYEE ::  EmployeeSAPIntegrationServiceImpl :: updateEmpUserStatus :: End");
	}

	private List<CGBaseDO> getUpdateEmployeeDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getUpdateEmployeeDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		if(employees != null && !employees.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(employees.size());
			CSDSAPEmployeeDO employeeDO = null;//new EmployeeDO();
			for(SAPEmployeeTO employeeTO : employees) {
				if(!StringUtil.isStringEmpty(employeeTO.getEmpCode())){
					String empCode = employeeTO.getEmpCode();
					employeeDO = organizationCommonDAO.getEmployeeDetailsByEmpCode(empCode);
					if(!StringUtil.isNull(employeeDO)){
						//EmployeeDO empDO = null;
						//empDO = employeeDO.get(0);
						logger.debug("Emp Updated Code ------->"+employeeDO.getEmpCode());
						CSDSAPEmployeeDO employeDO = null;
						boolean isUpdate = true;
						employeDO = convertEmployeeTOtoDO(employeeTO,isUpdate,employeeDO);
						baseList.add(employeDO);
					}
				}
			}
		}
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getUpdateEmployeeDOFromTO :: End");
		return baseList;
	}
	
	private List<CGBaseDO> getDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		if(employees != null && !employees.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(employees.size());
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
	
	/*private List<CGBaseDO> getDOFromTO(List<SAPEmployeeTO> employees) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		if(employees != null && !employees.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(employees.size());
			EmployeeDO employeeDO = null;//new EmployeeDO();
			for(SAPEmployeeTO employeeTO : employees) {
				employeeDO = convertEmployeeTOtoDO(employeeTO);
				baseList.add(employeeDO);
			}
			logger.debug("baseList##########----------------->"+baseList.get(0));
		}
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getDOFromTO :: End");
		return baseList;
	}*/
	
	/*private EmployeeDO convertEmployeeTOtoDO(SAPEmployeeTO employeeTO) throws CGBusinessException, CGSystemException {
		
		logger.debug("EmployeeSAPIntegrationServiceImpl :: convertEmployeeTOtoDO :: Start");
		EmployeeDO employeeDO = new EmployeeDO();

		//employeeDO.setVendorId(!StringUtil.isEmptyInteger(vendorTO.getVendorId())?vendorTO.getVendorId() :null);
		if(!StringUtil.isStringEmpty(employeeTO.getCity())){
			employeeDO.setCity(employeeTO.getCity());
		}
		logger.debug("City----------------->"+employeeDO.getCity());
		
			employeeDO.setEmployeeId(!StringUtil.isEmptyInteger(employeeTO.getEmployeeId())?employeeTO.getEmployeeId() :null);
			logger.debug("Emp ID ----------------->"+employeeDO.getEmployeeId());
			
			if(!StringUtil.isStringEmpty(employeeTO.getDesignation())){
				employeeDO.setDesignation(employeeTO.getDesignation());
			}
			logger.debug("Designation----------------->"+employeeDO.getDesignation());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmailId())){
				employeeDO.setEmailId(employeeTO.getEmailId());
			}
			logger.debug("EmailID----------------->"+employeeDO.getEmailId());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpCode())){
				employeeDO.setEmpCode(employeeTO.getEmpCode());
			}
			logger.debug("Emp Code----------------->"+employeeDO.getEmpCode());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpFax())){
				employeeDO.setEmpFax(employeeTO.getEmpFax());
			}
			logger.debug("Emp Fax----------------->"+employeeDO.getEmpFax());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpPhone())){
				employeeDO.setEmpPhone(employeeTO.getEmpPhone());
			}
			logger.debug("phone----------------->"+employeeDO.getEmpPhone());
			
			if(!StringUtil.isStringEmpty(employeeTO.getEmpType())){
				employeeDO.setEmpType(employeeTO.getEmpType());
			}
			logger.debug("type----------------->"+employeeDO.getEmpType());
			
			if(!StringUtil.isStringEmpty(employeeTO.getFirstName())){
				employeeDO.setFirstName(employeeTO.getFirstName());
			}
			logger.debug("F Name----------------->"+employeeDO.getFirstName());
			
			if(!StringUtil.isStringEmpty(employeeTO.getLastName())){
				employeeDO.setLastName(employeeTO.getLastName());
			}
			logger.debug("L Name----------------->"+employeeDO.getLastName());
			
			if(!StringUtil.isNull(employeeTO.getDateOfJoin())){
				employeeDO.setDateOfJoin(employeeTO.getDateOfJoin());
			}
			logger.debug("DOJ----------------->"+employeeDO.getDateOfJoin());
			
			List<OfficeDO> officeCodeDO = null;
			if(!StringUtil.isStringEmpty(employeeTO.getOfficeCode())){
				logger.debug("Ofice Code ----------------->"+employeeTO.getOfficeCode());
				officeCodeDO = new ArrayList<OfficeDO>();
				String ofcCode = employeeTO.getOfficeCode();
				officeCodeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(ofcCode);
				for(OfficeDO offDO : officeCodeDO){
					employeeDO.setOfficeId(offDO.getOfficeId());
				}
				logger.debug("Ofice ID ----------------->"+employeeDO.getOfficeId());
			}
			CityDO cityDO = null;
			
			if(!StringUtil.isStringEmpty(employeeTO.getCity())){
				cityDO = new CityDO();
				logger.debug("City Name ----------------->"+employeeTO.getCity());
				String cityName = employeeTO.getCity();
				cityDO = geographyServiceDAO.getCityByName(cityName);
				if(!StringUtil.isNull(cityDO)){
					if(!StringUtil.isEmptyInteger(cityDO.getCityId())){
						employeeDO.setCity(String.valueOf(cityDO.getCityId()));
						logger.debug("City ID ----------------->"+employeeDO.getCity());
					}
				}
			}
			
			DepartmentDO deptDO = null;
			if(!StringUtil.isStringEmpty(employeeTO.getDeptName())){
				deptDO = new DepartmentDO();
				logger.debug("Dept Name ----------------->"+employeeTO.getDeptName());	
				String departmentName = employeeTO.getDeptName();
				deptDO = geographyServiceDAO.getDepartmentByName(departmentName);
				if(!StringUtil.isNull(deptDO)){
					if(!StringUtil.isEmptyInteger(deptDO.getDepartmentId())){
						employeeDO.setDepartmentId(deptDO.getDepartmentId());
					}
				}
				logger.debug("Dept ID ----------------->"+employeeDO.getDepartmentId());
			}
			employeeDO.setEmpVirtual("N");
		logger.debug("EmployeeSAPIntegrationServiceImpl :: convertEmployeeTOtoDO :: End");
		return employeeDO;
	}*/
	
	
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
			logger.debug("EMPLOYEE ::  DOJ----------------->"+employeeDO.getDateOfJoin());*/
		
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
				logger.debug("City Name ----------------->"+employeeTO.getCity());
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
				logger.debug("EMPLOYEE ::  Dept ID ----------------->"+employeeDO.getDepartmentId());
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

	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

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
}
