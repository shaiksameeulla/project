package com.ff.sap.integration.plantmaster.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.organization.SAPOfficeDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.sap.integration.to.SAPOfficeTO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

/**
 * @author cbhure
 */
public class PlantMasterSAPIntegrationServiceImpl implements PlantMasterSAPIntegrationService{

	/** The LOGGER. */
	Logger logger = Logger
			.getLogger(PlantMasterSAPIntegrationServiceImpl.class);

	/** The integrationDAO. */
	private SAPIntegrationDAO integrationDAO;

	/** The organizationCommonDAO. */
	private OrganizationCommonDAO organizationCommonDAO;

	/** The geographyServiceDAO. */
	private GeographyServiceDAO geographyServiceDAO;

	/** The emailSenderUtil. */
	private EmailSenderUtil emailSenderUtil;

	/** The plantMasterTransactionService. */
	private PlantMasterTransactionService plantMasterTransactionService;
	
	/**
	 * @param integrationDAO
	 *            the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param organizationCommonDAO
	 *            the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(
			OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	/**
	 * @param geographyServiceDAO
	 *            the geographyServiceDAO to set
	 */
	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}

	/**
	 * @param emailSenderUtil
	 *            the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/**
	 * @param plantMasterTransactionService
	 *            the plantMasterTransactionService to set
	 */
	public void setPlantMasterTransactionService(
			PlantMasterTransactionService plantMasterTransactionService) {
		this.plantMasterTransactionService = plantMasterTransactionService;
	}

	@Override
	public boolean saveOfficeDetails(List<SAPOfficeTO> officeTO, boolean iterateOneByOne)throws CGBusinessException, IllegalAccessException,InvocationTargetException, NoSuchMethodException, CGSystemException { 
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: saveOfficeDetails :: Start");
		boolean isSaved = false;
		boolean isUpdate = false;
		List<OfficeDO> updateOfficeDOs;
		List<OfficeDO> officeDOs;
		List<CGBaseDO> empDOs;
		List<SAPErrorTO> sapErroTOlist = new ArrayList<>();
		SAPErrorTO sapErrorTO = null;
		/*
		 * 
		 * If Batch Insert/Update fails then try to insert/update records one by one 
		 and error records will be stored in staging table ff_f_sap_office and email will be trigger 
		for error records
		fisrt chk data if present - Update
		
		*/
		updateOfficeDOs = getDOUpdateFromTO(officeTO);
		if(!StringUtil.isNull(updateOfficeDOs)
				&&(!StringUtil.isEmptyColletion(updateOfficeDOs)) 
				&& !iterateOneByOne){
			isUpdate = integrationDAO.saveOrUpdateOfficeDetails(updateOfficeDOs);
		}
		
		/*If Batch Insert/Update fails then try to insert/update records one by one 
		 and error records will be stored in staging table ff_f_sap_office and email will be trigger 
		for error records
		Vendor code New - Save*/
		officeDOs = getDOFromTO(officeTO);
		// Code Modified by Himal - START
		if (!CGCollectionUtils.isEmpty(officeDOs) && !iterateOneByOne) {
			isSaved = false;
			for (OfficeDO officeDO : officeDOs) {
				// Added AOP - transaction management 
				sapErrorTO = new SAPErrorTO();
				sapErrorTO = plantMasterTransactionService.saveOfficeAndEmpDtls(officeDO);
				sapErroTOlist.add(sapErrorTO);
			}// END FOR LOOP
			integrationDAO.sendSapIntgErrorMail(sapErroTOlist,SAPIntegrationConstants.STK_CANCELLATION_EMAIL_TEMPLATE_NAME,"Expense Error Records");
		} 
		// Code Modified by Himal - END
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: saveOfficeDetails :: End");
		return isSaved;
		
		} 

	/*private List<CGBaseDO> getsapOfcDOFromSapOfctDO(List<CGBaseDO> sapOfficeDOs, String error) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<CGBaseDO> baseList = null;	
		if(sapOfficeDOs != null && !sapOfficeDOs.isEmpty()) {			
			baseList = new ArrayList<CGBaseDO>(sapOfficeDOs.size());
			SAPOfficeDO sapOfcDO = null;
			for(CGBaseDO sapOfficeDO : sapOfficeDOs) {	
				sapOfcDO = new SAPOfficeDO();
				PropertyUtils.copyProperties(sapOfcDO, sapOfficeDO);
				//sapOfcDO = convertSAPCustomerDOtoSapCustDO(sapOfficeDO,error);
				if(!StringUtil.isStringEmpty(error)){
					sapOfcDO.setIsError("Y");
					sapOfcDO.setErrorDesc(error);
					//sapOfcDO.setSapStatusInBound("N");
				}else{
					sapOfcDO.setIsError("N");
					//sapOfcDO.setSapStatusInBound("C");
					//sapOfcDO.setErrorDesc(null);
				}
				logger.debug("Is Error ----------------->"+sapOfcDO.getIsError());
				logger.debug("Error Descp ----------------->"+sapOfcDO.getErrorDesc());
				baseList.add(sapOfcDO);
			}
			logger.debug("baseList##########----------------->"+baseList.get(0));
		}
		return baseList;
	}*/

	private boolean saveDetailsOneByOneOffice(List<OfficeDO> updateOfficeDOs) {
		logger.debug("OFFCIE :: PlantMasterSAPIntegrationServiceImpl :: saveDetailsOneByOneOffice :: Start");
		List<OfficeDO> errorOfficeRecordDOs = null;
		List<SAPOfficeDO> stagingOfficeDOs = null;
		SAPOfficeDO stagingOfficeDO = null;
		boolean  isSaved = false;
		try{
			errorOfficeRecordDOs = integrationDAO.saveDetailsOneByOneOffice(updateOfficeDOs);
			if(!StringUtil.isEmptyColletion(errorOfficeRecordDOs)){
				for(OfficeDO errorRecord : errorOfficeRecordDOs){
					stagingOfficeDO = new SAPOfficeDO();
					stagingOfficeDO =  convertOfficeErrorRecordsToStagingDO(errorRecord);
					stagingOfficeDOs.add(stagingOfficeDO);
				}
				if(!StringUtil.isEmptyColletion(stagingOfficeDOs)){
					isSaved = integrationDAO.saveOfficeErrorRecords(stagingOfficeDOs);
					
					if(isSaved){
						//If Saved Successfully into staging table trigger mail to SAP
						sendEmail(stagingOfficeDOs);
					}
				}
			}
		}catch(Exception e){
			logger.error("OFFCIE :: Exception IN :: PlantMasterSAPIntegrationServiceImpl :: saveDetailsOneByOneOffice :: ",e);
		}
		logger.debug("OFFCIE :: PlantMasterSAPIntegrationServiceImpl :: saveDetailsOneByOneOffice :: END ");
		return isSaved;
	}
	
	private void sendEmail(List<SAPOfficeDO> stagingVendorDOs) throws CGBusinessException, CGSystemException {
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: sendEmail :: Start");
		try {
			List<MailSenderTO> mailerList = new ArrayList<>();
			//Prepare Subject and add it into Mail Body
			StringBuilder plainMailBody = prepareMailBody(stagingVendorDOs);
			//String subject="your complaint with reference number "+serviceRequestFollowupDO.getServiceRequestDO().getServiceRequestNo()+" has been followup";
			//subject=subject+" for consignment/Booking ref no: "+serviceRequestFollowupDO.getServiceRequestDO().getBookingNo();
			//StringBuilder plainMailBody = getMailBody(subject);
			prepareCallerMailAddress(mailerList,plainMailBody);
			//prepareExecutiveMail(serviceRequestFollowupDO, mailerList);
			for(MailSenderTO senderTO:mailerList){
				emailSenderUtil.sendEmail(senderTO);
			}
		} catch (Exception e) {
			logger.error("OFFCIE :: ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",e);
		}
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: sendEmail :: End");
	}
	
	private void prepareCallerMailAddress(List<MailSenderTO> mailerList,StringBuilder plainMailBody) {
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: Start");
		MailSenderTO callerSenderTO=new MailSenderTO();
		callerSenderTO.setTo(new String[]{SAPIntegrationConstants.EMAILD_ID});
		//callerSenderTO.setMailSubject(subject);
		callerSenderTO.setPlainMailBody(plainMailBody.toString());
		mailerList.add(callerSenderTO);
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: End");
	}

	private StringBuilder prepareMailBody(List<SAPOfficeDO> stagingVendorDOs){
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: prepareMailBody :: Start");
		StringBuilder plainMailBody = new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append("<br/><br/>Error came while processing Vendor Records to CSD Database");
		if(!StringUtil.isEmptyColletion(stagingVendorDOs)){
			for(SAPOfficeDO stagingVendorDO : stagingVendorDOs){
				if(!StringUtil.isStringEmpty(stagingVendorDO.getOfficeCode())){
					plainMailBody.append("<br/><br/>Offcie Code : "+stagingVendorDO.getOfficeCode());
				}
				if(!StringUtil.isStringEmpty(stagingVendorDO.getException())){
					plainMailBody.append("<br/><br/>Exception : "+stagingVendorDO.getException());
				}
			}
		}
		plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
		logger.debug("OFFICE :: VendorMasterSAPTransactionServiceImpl :: prepareMailBody :: End");
		return plainMailBody;
	}
	
	private SAPOfficeDO convertOfficeErrorRecordsToStagingDO(OfficeDO errorRecord) throws CGBusinessException, CGSystemException {
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: Start");
		
		SAPOfficeDO officeDO = new SAPOfficeDO();
		if((!StringUtil.isNull(errorRecord)
				&& (!StringUtil.isEmptyInteger(errorRecord.getOfficeId())))){
			officeDO.setId(errorRecord.getOfficeId());
		}/*else{
			officeDO.setOfficeId(!StringUtil.isEmptyInteger(officeTO.getOfficeId())?officeTO.getOfficeId() :null);
		}*/
		logger.debug("OFFICE :: Office ID ----------------->"+officeDO.getId());
		
		if(!StringUtil.isStringEmpty(errorRecord.getOfficeCode())){
			officeDO.setOfficeCode(errorRecord.getOfficeCode());
		}
		logger.debug("OFFICE :: Office Code ----------------->"+officeDO.getOfficeCode());
		
		if(!StringUtil.isStringEmpty(errorRecord.getOfficeName())){
			officeDO.setOfficeName(errorRecord.getOfficeName());
		}
		logger.debug("OFFICE :: Office Name ----------------->"+officeDO.getOfficeName());
		
		if(!StringUtil.isStringEmpty(errorRecord.getAddress1())){
			officeDO.setAddress1(errorRecord.getAddress1());
		}
		logger.debug("OFFICE :: Address 1 ----------------->"+officeDO.getAddress1());
		
		if(!StringUtil.isStringEmpty(errorRecord.getAddress2())){
			officeDO.setAddress2(errorRecord.getAddress2());
		}
		logger.debug("OFFICE :: Address 2 ----------------->"+officeDO.getAddress2());
		
		if(!StringUtil.isStringEmpty(errorRecord.getAddress3())){
			officeDO.setAddress3(errorRecord.getAddress3());
		}
		logger.debug("OFFICE :: Address 3 ----------------->"+officeDO.getAddress3());
		
		if(!StringUtil.isStringEmpty(errorRecord.getEmail())){
			officeDO.setEmail(errorRecord.getEmail());
		}
		logger.debug("OFFICE :: Email ----------------->"+officeDO.getEmail());
		
		OfficeDO reportingRHOOfficeDO = null;
		if(!StringUtil.isEmptyInteger(errorRecord.getReportingRHO())){
			logger.debug("OFFICE :: Reporting RHO Code ----------------->"+errorRecord.getReportingRHO());
			reportingRHOOfficeDO = new OfficeDO();
			String offCode = null;
			Integer reportingRHOID = errorRecord.getReportingRHO();
			reportingRHOOfficeDO = organizationCommonDAO.getOfficeByIdOrCode(reportingRHOID, offCode);
			if(!StringUtil.isNull(reportingRHOOfficeDO)){
				officeDO.setReportingRHO(reportingRHOOfficeDO.getOfficeCode());
			}
			logger.debug("Reporting RHO ID ----------------->"+officeDO.getReportingRHO());
			
		}
		
		OfficeDO reportingHUBOfficeDO = null;
		if(!StringUtil.isEmptyInteger(errorRecord.getReportingHUB())){
			logger.debug("OFFICE :: Reporting HUB Code ----------------->"+errorRecord.getReportingHUB());
			reportingHUBOfficeDO = new OfficeDO();
			String offCode = null;
			Integer reportingHUBID = errorRecord.getReportingHUB();
			reportingHUBOfficeDO = organizationCommonDAO.getOfficeByIdOrCode(reportingHUBID, offCode);
			if(!StringUtil.isNull(reportingHUBOfficeDO)){
				officeDO.setReportingHUB(reportingHUBOfficeDO.getOfficeCode());
			}
			logger.debug("OFFICE :: Reporting HUB ID ----------------->"+officeDO.getReportingHUB());
		}
		
		/*if(!StringUtil.isStringEmpty(officeTO.getOfficeType())
				&& (officeTO.getOfficeType().equals("BO"))){
			logger.debug("Office Type ----------------->"+officeTO.getOfficeType());
			if(!StringUtil.isStringEmpty(officeTO.getReportingHUB())){
				logger.debug("Reporting HUB Code ----------------->"+officeTO.getReportingHUB());	
				String reportingHUBCode = officeTO.getReportingHUB();
				reportingHUBOfficeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingHUBCode);
				if(!StringUtil.isNull(reportingHUBOfficeDO)){
					for(OfficeDO offDO : reportingHUBOfficeDO){
						officeDO.setReportingRHO(offDO.getReportingRHO());
					}
				}
				logger.debug("Office Type ID ----------------->"+officeDO.getReportingRHO());
			}
		}*/
		
		if(!StringUtil.isNull(errorRecord.getOfficeTypeDO())
			&& !StringUtil.isStringEmpty(errorRecord.getOfficeTypeDO().getOffcTypeCode())){
			officeDO.setOfficeTypeCode(errorRecord.getOfficeTypeDO().getOffcTypeCode());
			logger.debug("OFFICE :: Office Type CODE ----------------->"+officeDO.getOfficeTypeCode());
		}
		
		if(!StringUtil.isStringEmpty(errorRecord.getPincode())){
			officeDO.setPincode(errorRecord.getPincode());
		}
		logger.debug("OFFICE :: Pincode ----------------->"+officeDO.getPincode());
		
		/*CityDO cityDO = null;
		if(!StringUtil.isStringEmpty(errorRecord.getCity())){
			logger.debug("City Code ----------------->"+errorRecord.getCity());
			CityTO cityTO = new CityTO();
			cityTO.setCityCode(errorRecord.getCity());
			cityDO = geographyServiceDAO.getCity(cityTO);
			if(!StringUtil.isNull(cityDO)
					&& (!StringUtil.isEmptyInteger(cityDO.getCityId()))){
				officeDO.setCityId(cityDO.getCityId());
			}
			logger.debug("City ID ----------------->"+officeDO.getCityId());
		}
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: End");*/
		
		//By City Name
		/*CityDO cityDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getCity())){
			logger.debug("City Code ----------------->"+officeTO.getCity());
			String cityName = officeTO.getCity();
			cityDO = geographyServiceDAO.getCityByName(cityName);
			if(!StringUtil.isNull(cityDO)
					&& (!StringUtil.isEmptyInteger(cityDO.getCityId()))){
				officeDO.setCityId(cityDO.getCityId());
			}
			logger.debug("City ID ----------------->"+officeDO.getCityId());
		}
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: End");*/
		
	/*	RegionDO regionDO = null;
		if(!StringUtil.isStringEmpty(errorRecord.getOfficeCode())){
			logger.debug("Ofc Code ----------------->"+errorRecord.getOfficeCode());
			regionDO = new RegionDO();
			Character reportingRHOCode = errorRecord.getOfficeCode().charAt(0);
			String regionCode = reportingRHOCode.toString();
			logger.debug("regionCode-------------------->"+regionCode);
			regionDO = geographyServiceDAO.getRegionByReportingRHOCode(regionCode);
			if(!StringUtil.isNull(regionDO)){
				RegionDO regDO = new RegionDO();
				regDO.setRegionId(regionDO.getRegionCode());
				officeDO.setMappedRegionDO(regDO);
				logger.debug("Mapped To Region ID----------------->"+officeDO.getMappedRegionDO().getRegionId());
			}
		}*/
		
		/*if(!StringUtil.isStringEmpty(errorRecord.getPhoneNo())){
			officeDO.setPhone(errorRecord.getPhoneNo());
		}
		logger.debug("Phone No ----------------->"+officeDO.getPhone());
		
		if(!StringUtil.isStringEmpty(errorRecord.getMobileNo())){
			officeDO.setMobileNo(errorRecord.getMobileNo());
		}
		logger.debug("Mobile No ----------------->"+officeDO.getMobileNo());
		
		officeDO.setDtToBranch("N");
		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if(!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())){
			officeDO.setCreatedBy(userDO.getUserId());
			officeDO.setUpdatedBy(userDO.getUserId());
			Date today = Calendar.getInstance().getTime();
			officeDO.setCreatedDate(today);
			officeDO.setUpdatedDate(today);
		}*/
		return officeDO;
	}

	private List<OfficeDO> getDOUpdateFromTO(List<SAPOfficeTO> officeTO) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: getDOUpdateFromTO :: Start");
		List<OfficeDO> baseList = null;	
		if(officeTO != null && !officeTO.isEmpty()) {			
			baseList = new ArrayList<OfficeDO>(officeTO.size());
			logger.debug("OFFICE :: SAP CUSTOMER LIST SIZE ########## ----------------->"+baseList.size());
			OfficeDO officeDO = null;
			for(SAPOfficeTO officeTo : officeTO) {
				if(!StringUtil.isStringEmpty(officeTo.getOfficeCode())){
					String offCode = officeTo.getOfficeCode();
					Integer officeId = null;
					officeDO = new OfficeDO();
					officeDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
					if(!StringUtil.isNull(officeDO)){
						OfficeDO ofcDO  =  new OfficeDO();
						logger.debug("OFFICE :: Record need to update Office Code---------->"+officeDO.getOfficeCode());
						boolean isUpdate = true;
						ofcDO = convertOfficeTOtoDO(officeTo,isUpdate,officeDO);
						baseList.add(ofcDO);
					}
				}
			}
			logger.debug("OFFICE :: Update base List########## Office DO Size----------------->"+baseList.size());
		}
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: getDOUpdateFromTO :: End");
		return baseList;
	}
	
	private List<OfficeDO> getDOFromTO(List<SAPOfficeTO> officeTO) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		logger.debug("OFFICE :: getDOFromTO---------------------> Start");
		List<OfficeDO> baseList = null;	
		if(officeTO != null && !officeTO.isEmpty()) {			
			baseList = new ArrayList<OfficeDO>(officeTO.size());
			OfficeDO officeDO = new OfficeDO();
			for(SAPOfficeTO officeTo : officeTO) {	
				/*officeDO = convertOfficeTOtoDO(officeTo,isUpdate);
				baseList.add(officeDO);*/
				if(!StringUtil.isStringEmpty(officeTo.getOfficeCode())){
					String offCode = officeTo.getOfficeCode();
					Integer officeId = null;
					officeDO = new OfficeDO();
					officeDO = organizationCommonDAO.getOfficeByIdOrCode(officeId, offCode);
					if(StringUtil.isNull(officeDO)){
						boolean isUpdate = false;
						OfficeDO ofcDO = new OfficeDO();
						ofcDO = convertOfficeTOtoDO(officeTo,isUpdate,officeDO);
						baseList.add(ofcDO);
					}
				}
			}
			logger.debug("OFFICE :: Save base List########## Office DO Size----------------->"+baseList.size());
//			logger.debug("OFFICE :: baseList########## First Record Office ID----------------->"+baseList.get(0).getOriginOfficeId());
		}
		logger.debug("OFFICE :: getDOFromTO---------------------> End");
		return baseList;
	}
	
	/*private List<CGBaseDO> getDOFromTOForStaging(List<SAPOfficeTO> officeTO,String error)throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException{
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: getDOFromTOForStaging :: Start");
		List<CGBaseDO> baseList = null;
		if(officeTO != null && !officeTO.isEmpty()){
			baseList = new ArrayList<CGBaseDO>(officeTO.size());
			SAPOfficeDO sapOffcDO = new SAPOfficeDO();
			for(SAPOfficeTO officeTo : officeTO){
				sapOffcDO = convertSAPOfcTOToDO(officeTo,error);
				baseList.add(sapOffcDO);
			}
		}
		logger.debug("Staging baseList Size-------------->"+baseList.size());
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: getDOFromTOForStaging :: End");
		return baseList;
	}*/
	
	private List<CGBaseDO> getDOFromTOEmployee(List<SAPOfficeTO> officeTO, List<OfficeDO> officeDOs) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: getDOFromTOEmployee :: Start");
		List<CGBaseDO> baseList = null;	
		if(officeTO != null && !officeTO.isEmpty()) {			
			baseList = new ArrayList<CGBaseDO>(officeTO.size());
			CSDSAPEmployeeDO empDO = new CSDSAPEmployeeDO();
			if(!StringUtil.isNull(officeDOs) && (!StringUtil.isEmptyColletion(officeDOs))){ 
				OfficeDO offcDO = null;
					for(OfficeDO officeDO : officeDOs){
						offcDO = (OfficeDO)officeDO;
						empDO = convertEmpTOtoDO(offcDO);
						baseList.add(empDO);
					}
			}
			logger.debug("baseList########## Employee DO Size----------------->"+baseList.size());
		}
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: getDOFromTOEmployee :: End");
		return baseList;
	}
	 
	/*private SAPOfficeDO convertSAPOfcTOToDO(SAPOfficeTO officeTO,String error) throws CGBusinessException, CGSystemException{
		
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: convertSAPOfcTOToDO :: Start");
		
		SAPOfficeDO sapOfcDO = new SAPOfficeDO();
		logger.debug("office ID ---------------------> "+officeTO.getOfficeId());
		if(!StringUtil.isNull(officeTO)){
		
		sapOfcDO.setId(Long.valueOf(!StringUtil.isEmptyInteger(officeTO.getOfficeId())?officeTO.getOfficeId() :null));
		
		logger.debug("SAP OFFICE DO --> ID ---------------------> "+sapOfcDO.getId());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
			sapOfcDO.setOfficeCode(officeTO.getOfficeCode());
		}
		logger.debug("Office Code ----------------->"+officeTO.getOfficeCode());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeName())){
			sapOfcDO.setOfficeName(officeTO.getOfficeName());
		}
		logger.debug("Office Name ----------------->"+sapOfcDO.getOfficeName());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress1())){
			sapOfcDO.setAddress1(officeTO.getAddress1());
		}
		logger.debug("Address 1 ----------------->"+sapOfcDO.getAddress1());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress2())){
			sapOfcDO.setAddress2(officeTO.getAddress2());
		}
		logger.debug("Address 2 ----------------->"+sapOfcDO.getAddress2());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress3())){
			sapOfcDO.setAddress3(officeTO.getAddress3());
		}
		logger.debug("Address 3 ----------------->"+sapOfcDO.getAddress3());
		
		if(!StringUtil.isStringEmpty(officeTO.getEmail())){
			sapOfcDO.setEmail(officeTO.getEmail());
		}
		logger.debug("Email ----------------->"+sapOfcDO.getEmail());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeType())){
			sapOfcDO.setOfficeTypeCode(officeTO.getOfficeType());
		}
		logger.debug("Ofc Type Code ----------------->"+sapOfcDO.getOfficeTypeCode());
		
		if(!StringUtil.isStringEmpty(officeTO.getPincode())){
			sapOfcDO.setPincode(officeTO.getPincode());
		}
		logger.debug("Pincode ----------------->"+sapOfcDO.getPincode());
		
		if(!StringUtil.isStringEmpty(officeTO.getReportingHUB())){
			sapOfcDO.setReportingHUB(officeTO.getReportingHUB());
		}
		logger.debug("Email ----------------->"+sapOfcDO.getReportingHUB());
		
		if(!StringUtil.isStringEmpty(officeTO.getReportingRHO())){
			sapOfcDO.setReportingRHO(officeTO.getReportingRHO());
		}
		logger.debug("Email ----------------->"+sapOfcDO.getReportingRHO());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
			sapOfcDO.setEmpCode(officeTO.getOfficeCode());
		}
		logger.debug("Emp Code ----------------->"+sapOfcDO.getEmpCode());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeName())){
			sapOfcDO.setEmpName(officeTO.getOfficeName());
		}
		logger.debug("Emp Name ----------------->"+sapOfcDO.getOfficeName());
		
		sapOfcDO.setEmpVirtual('Y');
		
		logger.debug("Emp Virtual ----------------->"+sapOfcDO.getEmpVirtual());
		
		if(!StringUtil.isStringEmpty(error)){
			sapOfcDO.setIsError('Y');
			sapOfcDO.setErrorDesc(error);
		}else{
			sapOfcDO.setIsError('N');
		}
		logger.debug("Error Description is ----------------->"+error);
		
		//sapOfcDO.setSapStatusInBound("N");
		Date dateTime = Calendar.getInstance().getTime();
		sapOfcDO.setSapTimestamp(dateTime);
		logger.debug("SAP Time Stamp------------>"+sapOfcDO.getSapTimestamp());
		logger.debug("PlantMasterSAPIntegrationServiceImpl :: convertSAPOfcTOToDO :: End");
		}
		return sapOfcDO;
	}*/
	private CSDSAPEmployeeDO convertEmpTOtoDO(CGBaseDO officeDO) throws CGBusinessException, CGSystemException {
		logger.debug("OFFICE :: convertEmpTOtoDO---------------------> Start");
		CSDSAPEmployeeDO empDO = new CSDSAPEmployeeDO();
		OfficeDO offcDO = null;
		offcDO = (OfficeDO)officeDO;
		
		if(!StringUtil.isStringEmpty(offcDO.getOfficeCode())){
			empDO.setEmpCode(offcDO.getOfficeCode());	
		}
		logger.debug("OFFICE :: Emp Code -------->"+empDO.getEmpCode());
		
		if(!StringUtil.isStringEmpty(offcDO.getOfficeName())){
			empDO.setFirstName(offcDO.getOfficeName());	
		}
		logger.debug("OFFICE :: First Name -------->"+empDO.getFirstName());
		
		
		if(!StringUtil.isEmptyInteger(offcDO.getCityId())){
			empDO.setCty(offcDO.getCityId());	
		}
		logger.debug("OFFICE :: City ID -------->"+empDO.getCty());
		
		if(!StringUtil.isStringEmpty(offcDO.getEmail())){
			empDO.setEmailId(offcDO.getEmail());	
		}
		logger.debug("OFFICE :: Email -------->"+empDO.getEmailId());
		
		empDO.setEmpVirtual("Y");
		empDO.setDtToBranch("N");
		empDO.setEmpStatus("A");
		
		if(!StringUtil.isEmptyInteger(offcDO.getOfficeId())){
			empDO.setOfficeId(offcDO.getOfficeId());	
		}
		logger.debug("OFFICE :: Employee Office ID Punched--------------------------------------->"+empDO.getOfficeId());

		empDO.setLastName(" ");
		
		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if(!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())){
			empDO.setCreatedBy(userDO.getUserId());
			empDO.setUpdatedBy(userDO.getUserId());
			Date today = Calendar.getInstance().getTime();
			empDO.setCreatedDate(today);
			empDO.setUpdatedDate(today);
		}
		
		logger.debug("OFFICE :: Last Name -------->"+empDO.getFirstName());
		
		logger.debug("convertEmpTOtoDO---------------------> End");
		return empDO;
	}
	
	@SuppressWarnings("null")
	private OfficeDO convertOfficeTOtoDO(SAPOfficeTO officeTO,boolean isUpdate,OfficeDO ofcDO) throws CGBusinessException, CGSystemException {

		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: Start");
		
		OfficeDO officeDO = new OfficeDO();
		if(isUpdate && (!StringUtil.isNull(ofcDO)
				&& (!StringUtil.isEmptyInteger(ofcDO.getOfficeId())))){
			officeDO.setOfficeId(ofcDO.getOfficeId());
		}else{
			officeDO.setOfficeId(!StringUtil.isEmptyInteger(officeTO.getOfficeId())?officeTO.getOfficeId() :null);
		}
		logger.debug("OFFICE :: Office ID ----------------->"+officeDO.getOfficeId());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
			officeDO.setOfficeCode(officeTO.getOfficeCode());
		}
		logger.debug("OFFICE :: Office Code ----------------->"+officeDO.getOfficeCode());
		
		if(!StringUtil.isStringEmpty(officeTO.getOfficeName())){
			officeDO.setOfficeName(officeTO.getOfficeName());
		}
		logger.debug("OFFICE :: Office Name ----------------->"+officeDO.getOfficeName());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress1())){
			officeDO.setAddress1(officeTO.getAddress1());
		}
		logger.debug("OFFICE :: Address 1 ----------------->"+officeDO.getAddress1());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress2())){
			officeDO.setAddress2(officeTO.getAddress2());
		}
		logger.debug("OFFICE :: Address 2 ----------------->"+officeDO.getAddress2());
		
		if(!StringUtil.isStringEmpty(officeTO.getAddress3())){
			officeDO.setAddress3(officeTO.getAddress3());
		}
		logger.debug("OFFICE :: Address 3 ----------------->"+officeDO.getAddress3());
		
		if(!StringUtil.isStringEmpty(officeTO.getEmail())){
			officeDO.setEmail(officeTO.getEmail());
		}
		logger.debug("OFFICE :: Email ----------------->"+officeDO.getEmail());
		
		List<OfficeDO> reportingRHOOfficeDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getReportingRHO())){
			logger.debug("OFFICE :: Reporting RHO Code ----------------->"+officeTO.getReportingRHO());
			reportingRHOOfficeDO = new ArrayList<>();
			String reportingRHOCode = officeTO.getReportingRHO();
			reportingRHOOfficeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
			if(!StringUtil.isNull(reportingRHOOfficeDO)){
				for(OfficeDO offDO : reportingRHOOfficeDO){
					officeDO.setReportingRHO(offDO.getOfficeId());
				}
			}
			logger.debug("OFFICE :: Reporting RHO ID ----------------->"+officeDO.getReportingRHO());
			
		}
		
		List<OfficeDO> reportingHUBOfficeDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getReportingHUB())){
			logger.debug("OFFICE :: Reporting HUB Code ----------------->"+officeTO.getReportingHUB());
			reportingHUBOfficeDO = new ArrayList<>();
			String reportingHUBCode = officeTO.getReportingHUB();
			reportingHUBOfficeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingHUBCode);
			if(!StringUtil.isNull(reportingHUBOfficeDO)){
				for(OfficeDO offDO : reportingHUBOfficeDO){
					officeDO.setReportingHUB(offDO.getOfficeId());
				}
			}
			logger.debug("OFFICE :: Reporting HUB ID ----------------->"+officeDO.getReportingHUB());
		}
		
		/*if(!StringUtil.isStringEmpty(officeTO.getOfficeType())
				&& (officeTO.getOfficeType().equals("BO"))){
			logger.debug("OFFICE :: Office Type ----------------->"+officeTO.getOfficeType());
			if(!StringUtil.isStringEmpty(officeTO.getReportingHUB())){
				logger.debug("OFFICE :: Reporting HUB Code ----------------->"+officeTO.getReportingHUB());	
				String reportingHUBCode = officeTO.getReportingHUB();
				reportingHUBOfficeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingHUBCode);
				if(!StringUtil.isNull(reportingHUBOfficeDO)){
					for(OfficeDO offDO : reportingHUBOfficeDO){
						officeDO.setReportingRHO(offDO.getReportingRHO());
					}
				}
				logger.debug("OFFICE :: Office Type ID ----------------->"+officeDO.getReportingRHO());
			}
		}*/
		
		OfficeTypeDO officeTypeDo = null;
		if(!StringUtil.isStringEmpty(officeTO.getOfficeType())){
			logger.debug("OFFICE :: Office Type ----------------->"+officeTO.getOfficeType());
			String officeType = officeTO.getOfficeType();
			officeTypeDo = organizationCommonDAO.getOfficeTypeIdByOfficeTypeCode(officeType);
			if(!StringUtil.isNull(officeTypeDo)){
				officeDO.setOfficeTypeDO(officeTypeDo);
				logger.debug("OFFICE :: Office Type ID ----------------->"+officeDO.getOfficeTypeDO().getOffcTypeId());
			}
		}
		
		if(!StringUtil.isStringEmpty(officeTO.getPincode())){
			officeDO.setPincode(officeTO.getPincode());
		}
		logger.debug("OFFICE :: Pincode ----------------->"+officeDO.getPincode());
		
		CityDO cityDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getCity())){
			logger.debug("OFFICE :: City Code ----------------->"+officeTO.getCity());
			CityTO cityTO = new CityTO();
			cityTO.setCityCode(officeTO.getCity());
			cityDO = geographyServiceDAO.getCity(cityTO);
			if(!StringUtil.isNull(cityDO)
					&& (!StringUtil.isEmptyInteger(cityDO.getCityId()))){
				officeDO.setCityId(cityDO.getCityId());
			}
			logger.debug("OFFICE :: City ID ----------------->"+officeDO.getCityId());
		}
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: End");
		
		//By City Name
		/*CityDO cityDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getCity())){
			logger.debug("OFFICE :: City Code ----------------->"+officeTO.getCity());
			String cityName = officeTO.getCity();
			cityDO = geographyServiceDAO.getCityByName(cityName);
			if(!StringUtil.isNull(cityDO)
					&& (!StringUtil.isEmptyInteger(cityDO.getCityId()))){
				officeDO.setCityId(cityDO.getCityId());
			}
			logger.debug("OFFICE :: City ID ----------------->"+officeDO.getCityId());
		}
		logger.debug("OFFICE :: PlantMasterSAPIntegrationServiceImpl :: convertOfficeTOtoDO :: End");*/
		
		RegionDO regionDO = null;
		if(!StringUtil.isStringEmpty(officeTO.getOfficeCode())){
			logger.debug("OFFICE :: Ofc Code ----------------->"+officeTO.getOfficeCode());
			regionDO = new RegionDO();
			Character reportingRHOCode = officeTO.getOfficeCode().charAt(0);
			String regionCode = reportingRHOCode.toString();
			logger.debug("OFFICE :: regionCode-------------------->"+regionCode);
			regionDO = geographyServiceDAO.getRegionByReportingRHOCode(regionCode);
			if(!StringUtil.isNull(regionDO)){
				RegionDO regDO = new RegionDO();
				regDO.setRegionId(regionDO.getRegionId());
				officeDO.setMappedRegionDO(regDO);
				logger.debug("OFFICE :: Mapped To Region ID----------------->"+officeDO.getMappedRegionDO().getRegionId());
			}
		}
		
		if(!StringUtil.isStringEmpty(officeTO.getPhoneNo())){
			officeDO.setPhone(officeTO.getPhoneNo());
		}
		logger.debug("OFFICE :: Phone No ----------------->"+officeDO.getPhone());
		
		if(!StringUtil.isStringEmpty(officeTO.getMobileNo())){
			officeDO.setMobileNo(officeTO.getMobileNo());
		}
		logger.debug("OFFICE :: Mobile No ----------------->"+officeDO.getMobileNo());
		
		officeDO.setDtToBranch("N");
		String userName = "SAP_USER";
		UserDO userDO = integrationDAO.getSAPUserDtls(userName);
		if(!StringUtil.isNull(userDO)
				&& !StringUtil.isEmptyInteger(userDO.getUserId())){
			officeDO.setCreatedBy(userDO.getUserId());
			officeDO.setUpdatedBy(userDO.getUserId());
			Date today = Calendar.getInstance().getTime();
			officeDO.setCreatedDate(today);
			officeDO.setUpdatedDate(today);
		}
		return officeDO;
	}
	
}
