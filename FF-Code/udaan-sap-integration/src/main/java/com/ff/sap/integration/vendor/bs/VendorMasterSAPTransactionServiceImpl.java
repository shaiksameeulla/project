package com.ff.sap.integration.vendor.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.CSDSAPVendorRegionMapDO;
import com.ff.domain.business.SAPVendorDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.constant.SAPIntegrationConstants;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPRegionCodeTO;
import com.ff.sap.integration.to.SAPVendorTO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

/**
 * @author cbhure
 *
 */
public class VendorMasterSAPTransactionServiceImpl implements VendorMasterSAPTransactionService {

	
Logger logger = Logger.getLogger(VendorMasterSAPTransactionServiceImpl.class);
	
	private SAPIntegrationDAO integrationDAO; 
	private OrganizationCommonDAO organizationCommonDAO;
	private EmailSenderUtil emailSenderUtil;
	
	@SuppressWarnings("null")
	/*public boolean saveVendorDetails(List<SAPVendorTO> vendors, boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: saveVendorDetails :: Start");
		boolean isSaved = false;
		boolean isUpdate = false;
		List<CSDSAPLoadMovementVendorDO> updateVendorDOs;
		List<CSDSAPLoadMovementVendorDO> vendorDOs;
		List<CSDSAPLoadMovementVendorDO> vendorErrorRecords;
		List<SAPVendorDO> stagingVendorDOs = new ArrayList<SAPVendorDO>();
		SAPVendorDO stagingVendorDO = null;
		try{
			//fisrt chk data if present - Update
			updateVendorDOs = getUpdateVendorDOFromTO(vendors);
			if(!StringUtil.isNull(updateVendorDOs)
					&&(!StringUtil.isEmptyColletion(updateVendorDOs))
					&& (!iterateOneByOne)){
				isUpdate = integrationDAO.saveOrUpdateVendorDetails(updateVendorDOs);
			}else{
				vendorErrorRecords = integrationDAO.saveDetailsOneByOne(updateVendorDOs);
				if(!StringUtil.isEmptyColletion(vendorErrorRecords)){
					for(CSDSAPLoadMovementVendorDO errorRecord : vendorErrorRecords){
						stagingVendorDO = new SAPVendorDO();
						stagingVendorDO =  convertErrorRecordsToStagingDO(errorRecord);
						stagingVendorDOs.add(stagingVendorDO);
					}
					if(!StringUtil.isEmptyColletion(stagingVendorDOs)){
						isSaved = integrationDAO.saveVendorErrorRecords(stagingVendorDOs);
					}
				}
			}
			//Vendor code New - Save
			vendorDOs = getVendorDOFromTO(vendors);
			if(!StringUtil.isNull(vendorDOs)
					&&(!StringUtil.isEmptyColletion(vendorDOs))
					&& (!iterateOneByOne)){
				isSaved = integrationDAO.saveOrUpdateVendorDetails(vendorDOs);
			}else{
				vendorErrorRecords = integrationDAO.saveDetailsOneByOne(updateVendorDOs);
				if(!StringUtil.isEmptyColletion(vendorErrorRecords)){
					for(CSDSAPLoadMovementVendorDO errorRecord : vendorErrorRecords){
						stagingVendorDO = new SAPVendorDO();
						stagingVendorDO =  convertErrorRecordsToStagingDO(errorRecord);
						stagingVendorDOs.add(stagingVendorDO);
					}
					if(!StringUtil.isEmptyColletion(stagingVendorDOs)){
						isSaved = integrationDAO.saveVendorErrorRecords(stagingVendorDOs);
					}
				}
			}
		}catch(CGSystemException e){
			throw new CGBusinessException("Exception IN :: saveVendorDetails :: ",e);
		}
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: saveVendorDetails :: End");
		return isSaved;
	}*/
	
	
	private SAPVendorDO convertErrorRecordsToStagingDO(CSDSAPLoadMovementVendorDO errorRecord) throws CGSystemException {
		
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDO :: Start");
		
		SAPVendorDO stagingVendorDO = new SAPVendorDO();
		if((!StringUtil.isNull(errorRecord)
				&& (!StringUtil.isEmptyInteger(errorRecord.getVendorId())))){ 
			stagingVendorDO.setId(errorRecord.getVendorId());
		}/*else{
		stagingVendorDO.setId(!StringUtil.isEmptyInteger(errorRecord.getVendorId())?errorRecord.getVendorId() :null);
		}*/
		logger.debug("VENDOR :: Staging Vendor ID ----------------->"+stagingVendorDO.getId());
		
		if(!StringUtil.isStringEmpty(errorRecord.getVendorCode())){
			stagingVendorDO.setVendorCode(errorRecord.getVendorCode());
		}
		logger.debug("VENDOR :: Staging vendor Code----------------->"+stagingVendorDO.getVendorCode());
		
		if(!StringUtil.isStringEmpty(errorRecord.getBusinessName())){
			stagingVendorDO.setBusinessName(errorRecord.getBusinessName());
		}
		logger.debug("VENDOR :: Business Name ----------------->"+stagingVendorDO.getBusinessName());
		
		if(!StringUtil.isStringEmpty(errorRecord.getAddress())){
			stagingVendorDO.setAddress(errorRecord.getAddress());
		}
		logger.debug("VENDOR :: Address ----------------->"+stagingVendorDO.getAddress());
		
		if(!StringUtil.isStringEmpty(errorRecord.getCompanyType())){
			stagingVendorDO.setCompanyType(errorRecord.getCompanyType());
		}
		logger.debug("VENDOR :: Comp Type ----------------->"+stagingVendorDO.getCompanyType());
		
		if(!StringUtil.isStringEmpty(errorRecord.getEmail())){
			stagingVendorDO.setAddress(errorRecord.getEmail());
		}
		logger.debug("VENDOR :: Email ----------------->"+stagingVendorDO.getEmail());
		
		if(!StringUtil.isStringEmpty(errorRecord.getFax())){
			stagingVendorDO.setFax(errorRecord.getFax());
		}
		logger.debug("VENDOR :: Fax ----------------->"+stagingVendorDO.getFax());
		
		if(!StringUtil.isStringEmpty(errorRecord.getFirstname())){
			stagingVendorDO.setFirstname(errorRecord.getFirstname());
		}
		logger.debug("VENDOR :: First Name ----------------->"+stagingVendorDO.getFirstname());
		
		if(!StringUtil.isStringEmpty(errorRecord.getLastName())){
			stagingVendorDO.setLastName(errorRecord.getLastName());
		}
		logger.debug("VENDOR :: Last Name ----------------->"+stagingVendorDO.getLastName());
		
		if(!StringUtil.isStringEmpty(errorRecord.getMobile())){
			stagingVendorDO.setMobile(errorRecord.getMobile());
		}
		logger.debug("VENDOR :: Mobile No ----------------->"+stagingVendorDO.getMobile());
		
		if(!StringUtil.isStringEmpty(errorRecord.getPhone())){
			stagingVendorDO.setPhone(errorRecord.getPhone());
		}
		logger.debug("VENDOR :: VENDOR :: Phone ----------------->"+stagingVendorDO.getPhone());
		
		if(!StringUtil.isStringEmpty(errorRecord.getPincode())){
			stagingVendorDO.setPincode(errorRecord.getPincode());
		}
		logger.debug("Pincode ----------------->"+stagingVendorDO.getPincode());
		
		if(!StringUtil.isStringEmpty(errorRecord.getService())){
			stagingVendorDO.setService(errorRecord.getService());
		}
		logger.debug("VENDOR :: Service ----------------->"+stagingVendorDO.getService());
		
		if(!StringUtil.isNull(errorRecord.getVendorTypeDO())
				&& StringUtil.isStringEmpty(errorRecord.getVendorTypeDO().getVendorTypeCode())){
				stagingVendorDO.setVendorType(errorRecord.getVendorTypeDO().getVendorTypeCode());
		}
		logger.debug("VENDOR :: Vendor Type ----------------->"+stagingVendorDO.getVendorType());
		
		if(!StringUtil.isStringEmpty(errorRecord.getService())){
			stagingVendorDO.setService(errorRecord.getService());
		}
		logger.debug("VENDOR :: RHO Code  ----------------->"+stagingVendorDO.getService());
		
		if(!StringUtil.isStringEmpty(errorRecord.getException())){
			stagingVendorDO.setException(errorRecord.getException());
		}
		logger.debug("VENDOR :: Exception in vendoe Record   ----------------->"+stagingVendorDO.getException());
		
		//Add RHO CODE
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: convertErrorRecordsToStagingDO :: End");
		return stagingVendorDO;
	}

	public List<CSDSAPLoadMovementVendorDO> getVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		/*logger.debug("EmployeeSAPIntegrationServiceImpl :: getVendorDOFromTO :: Start");
		List<CSDSAPLoadMovementVendorDO> baseList = null;
		if(vendors != null && !vendors.isEmpty()) {
			baseList = new ArrayList<CSDSAPLoadMovementVendorDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null;//new EmployeeDO();
			for(SAPVendorTO vendorTO : vendors) {
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					vendorDO = organizationCommonDAO.getVendorByCode(vendorCode);
					if(StringUtil.isNull(vendorDO)){
						CSDSAPLoadMovementVendorDO venDO = null;
						boolean isUpdate = false;
						venDO = convertVendorHeaderTOtoDO(vendorTO,isUpdate,vendorDO);
						Set<CSDSAPVendorRegionMapDO> venRegionMapSet = null;
						venRegionMapSet = convertVendorRegionDtlsTOtoDO(vendorTO,isUpdate,venDO);
						venDO.setVendorRegionMappingDO(venRegionMapSet);
						baseList.add(venDO);
					}
				}
			}
		}
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getVendorDOFromTO :: End");
		return baseList;*/
		
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: getVendorDOFromTO :: Start");
		String userName = "SAP_USER";
		List<CSDSAPLoadMovementVendorDO> baseList = null;
		if(vendors != null && !vendors.isEmpty()) {
			baseList = new ArrayList<CSDSAPLoadMovementVendorDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null;//new EmployeeDO();
			for(SAPVendorTO vendorTO : vendors) {
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					vendorDO = organizationCommonDAO.getVendorByCode(vendorCode);
					if(StringUtil.isNull(vendorDO)){
						CSDSAPLoadMovementVendorDO venDO = null;
						boolean isUpdate = false;
						venDO = convertVendorHeaderTOtoDO(vendorTO,isUpdate,vendorDO);
						UserDO userDO = integrationDAO.getSAPUserDtls(userName);
						if(!StringUtil.isNull(userDO)
								&& !StringUtil.isEmptyInteger(userDO.getUserId())){
							venDO.setCreatedBy(userDO.getUserId());
							Date today = Calendar.getInstance().getTime();
							venDO.setCreatedDate(today);
						}
						Set<CSDSAPVendorRegionMapDO> venRegionMapSet = null;
						venRegionMapSet = convertVendorRegionDtlsTOtoDO(vendorTO,isUpdate,venDO);
						venDO.setVendorRegionMappingDO(venRegionMapSet);
						baseList.add(venDO);
					}
				}
			}
		}
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: getVendorDOFromTO :: End");
		return baseList;
	}

	public List<CSDSAPLoadMovementVendorDO> getUpdateVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		/*
		 * Old code without created by and sap user stamping
		 * 
		 * logger.debug("VendorMasterSAPIntegrationServiceImpl :: getUpdateVendorDOFromTO :: Start");
		List<CSDSAPLoadMovementVendorDO> baseList = null;	
		if(vendors != null && !vendors.isEmpty()) {			
			baseList = new ArrayList<CSDSAPLoadMovementVendorDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null; //new LoadMovementVendorDO();
			for(SAPVendorTO vendorTO : vendors) {	
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					vendorDO =	organizationCommonDAO.getVendorByCode(vendorCode);
					if(!StringUtil.isNull(vendorDO)){
						logger.debug("Vendor Updated Code ------->"+vendorDO.getVendorCode());
						CSDSAPLoadMovementVendorDO venDO = null;
						boolean isUpdate = true;
						venDO = convertVendorHeaderTOtoDO(vendorTO,isUpdate,vendorDO);
						Set<CSDSAPVendorRegionMapDO> venRegionMapSet = null;
						venRegionMapSet = convertVendorRegionDtlsTOtoDO(vendorTO,isUpdate,vendorDO);
						venDO.setVendorRegionMappingDO(venRegionMapSet);
						baseList.add(venDO);
					}
				}
			}
			logger.debug("baseList##########----------------->"+baseList.size());
		}
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: getUpdateVendorDOFromTO :: End");
		return baseList;*/
		
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: getUpdateVendorDOFromTO :: Start");
		List<CSDSAPLoadMovementVendorDO> baseList = null;	
		String userName = "SAP_USER";
		if(vendors != null && !vendors.isEmpty()) {			
			baseList = new ArrayList<CSDSAPLoadMovementVendorDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null; //new LoadMovementVendorDO();
			for(SAPVendorTO vendorTO : vendors) {	
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					vendorDO =	organizationCommonDAO.getVendorByCode(vendorCode);
					logger.debug("VENDOR :: organizationCommonDAO.getVendorByCode(vendorCode); END");
					if(!StringUtil.isNull(vendorDO)){
						logger.debug("VENDOR :: Vendor Updated Code ------->"+vendorDO.getVendorCode());
						CSDSAPLoadMovementVendorDO venDO = null;
						boolean isUpdate = true;
						venDO = convertVendorHeaderTOtoDO(vendorTO,isUpdate,vendorDO);
						UserDO userDO = integrationDAO.getSAPUserDtls(userName);
						if(!StringUtil.isNull(userDO)
								&& !StringUtil.isEmptyInteger(userDO.getUserId())){
							venDO.setUpdatedBy(userDO.getUserId());
							Date today = Calendar.getInstance().getTime();
							venDO.setUpdatedDate(today);
						}
						Set<CSDSAPVendorRegionMapDO> venRegionMapSet = null;
						venRegionMapSet = convertVendorRegionDtlsTOtoDO(vendorTO,isUpdate,vendorDO);
						venDO.setVendorRegionMappingDO(venRegionMapSet);
						baseList.add(venDO);
					}
				}
			}
			logger.debug("VENDOR :: baseList##########----------------->"+baseList.size());
		}
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: getUpdateVendorDOFromTO :: End");
		return baseList;
	}

	private Set<CSDSAPVendorRegionMapDO> convertVendorRegionDtlsTOtoDO(SAPVendorTO vendorTO, boolean isUpdate, CSDSAPLoadMovementVendorDO venDO) throws CGSystemException {
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: convertVendorRegionDtlsTOtoDO :: Start");
		Set<CSDSAPVendorRegionMapDO> venRegionMappingSet = new HashSet<>();
		logger.debug("VENDOR :: Is update Boolean Status  --------->"+isUpdate);
		if(!isUpdate){
			logger.debug("VENDOR :: Vendor Region Dtls While Save");
			if(!StringUtil.isEmptyColletion(vendorTO.getRegionCodeTOList())){
				for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
					if(!StringUtil.isStringEmpty(regionTO.getRhoCode())){
						CSDSAPVendorRegionMapDO venRegionMapDO = new CSDSAPVendorRegionMapDO();
						logger.debug("VENDOR :: RHO Code From TO ----------------->"+regionTO.getRhoCode());
						String reportingRHOCode  = regionTO.getRhoCode();
						List<OfficeDO> officeDO = new ArrayList<OfficeDO>();
						officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
						OfficeDO ofcDO = null;
						if(!StringUtil.isNull(officeDO)
								&&(!StringUtil.isEmptyColletion(officeDO))){
							ofcDO = officeDO.get(0);
							if(!StringUtil.isNull(ofcDO)
									&& (!StringUtil.isNull(ofcDO.getMappedRegionDO()))
									&& (!StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getRegionId()))){
								logger.debug("VENDOR ::  Region adding::----->");
								venRegionMapDO.setStatus("A");
								venRegionMapDO.setRegionId(ofcDO.getMappedRegionDO().getRegionId());
								logger.debug("VENDOR :: Region ID ----------------->"+venRegionMapDO.getRegionId());
							}
						}
						venRegionMapDO.setVendorDO(venDO);
						venRegionMappingSet.add(venRegionMapDO);
					}
				}
			}
		}else{
			logger.debug("VENDOR :: Vendor Region Dtls While Update-------------------->Started");
			if(!StringUtil.isEmptyColletion(vendorTO.getRegionCodeTOList())){
				for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
					if(!StringUtil.isStringEmpty(regionTO.getRhoCode())){
						logger.debug("VENDOR :: RHO Code From TO ----------------->"+regionTO.getRhoCode());
						String reportingRHOCode  = regionTO.getRhoCode();
						List<OfficeDO> officeDO = new ArrayList<OfficeDO>();
						officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
						OfficeDO ofcDO = null;
						if(!StringUtil.isNull(officeDO)
								&&(!StringUtil.isEmptyColletion(officeDO))){
							ofcDO = officeDO.get(0);
							if(!StringUtil.isNull(ofcDO)
									&& (!StringUtil.isNull(ofcDO.getMappedRegionDO()))
									&& (!StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getRegionId()))){
								
								Integer vendorId = venDO.getVendorId();
								Integer regionId = ofcDO.getMappedRegionDO().getRegionId();
								logger.debug("VENDOR :: vendorId From TO ----------->"+vendorId);
								logger.debug("VENDOR :: Region ID From TO ----------->"+regionId);
								
								CSDSAPVendorRegionMapDO vRegionMapDO = new CSDSAPVendorRegionMapDO();
								vRegionMapDO = organizationCommonDAO.getVendorRegionMapping(vendorId,regionId);
								logger.debug("VENDOR :: Afetr Search in DB vRegionMapDO Region ---------------> "+vRegionMapDO.getRegionId());
								logger.debug("VENDOR :: Afer Search in DB vRegionMapDO ---------------> "+vRegionMapDO.getVendorRegionMappingId());
								if(StringUtil.isEmptyInteger(vRegionMapDO.getRegionId())){
									//logger.debug("vendorId From Vendor DO ----------->"+vRegionMapDO.getVendorDO().getVendorId());
									logger.debug("VENDOR :: Region ID From Vendor DO ----------->"+vRegionMapDO.getRegionId());
									vRegionMapDO.setRegionId(ofcDO.getMappedRegionDO().getRegionId());
									vRegionMapDO.setStatus(regionTO.getStatus());
									logger.debug("VENDOR :: Region Status -------> "+regionTO.getStatus());
									vRegionMapDO.setVendorDO(venDO);
									venRegionMappingSet.add(vRegionMapDO);
									logger.debug("VENDOR :: While Update Inserting Map Size =======> "+venRegionMappingSet.size());
								}
					 			logger.debug("VENDOR :: Region ID ----------------->"+vRegionMapDO.getRegionId());
							}
						}
					}
				}
			}
		}
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: convertVendorRegionDtlsTOtoDO :: End");
		return venRegionMappingSet;
	}

	private CSDSAPLoadMovementVendorDO convertVendorHeaderTOtoDO(SAPVendorTO vendorTO,boolean isUpdate, CSDSAPLoadMovementVendorDO venDO) throws CGSystemException {
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertVendorHeaderTOtoDO :: Start");
		CSDSAPLoadMovementVendorDO vendorDO = null;
		if(!StringUtil.isNull(venDO)){
			vendorDO = venDO;
		} else{
			vendorDO = new CSDSAPLoadMovementVendorDO();
		}
		
		if(isUpdate && (!StringUtil.isNull(venDO)
				&& (!StringUtil.isEmptyInteger(venDO.getVendorId())))){ 
			// vendorDO.setVendorId(venDO.getVendorId());
		}else{
			vendorDO.setVendorId(!StringUtil.isEmptyInteger(vendorTO.getVendorId())?vendorTO.getVendorId() :null);
		}
		
		logger.debug("VENDOR :: Vendor ID ----------------->"+vendorDO.getVendorId());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
			vendorDO.setVendorCode(vendorTO.getVendorCode());
		}
		logger.debug("VENDOR :: vendor Code----------------->"+vendorDO.getVendorCode());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorName())){
			vendorDO.setBusinessName(vendorTO.getVendorName());
		}
		logger.debug("VENDOR :: Business Name ----------------->"+vendorDO.getBusinessName());
		
		if(!StringUtil.isStringEmpty(vendorTO.getAddress())){
			vendorDO.setAddress(vendorTO.getAddress());
		}
		logger.debug("VENDOR :: Address ----------------->"+vendorDO.getAddress());
		
		if(!StringUtil.isStringEmpty(vendorTO.getCompanyType())){
			vendorDO.setCompanyType(vendorTO.getCompanyType());
		}
		logger.debug("VENDOR :: Comp Type ----------------->"+vendorDO.getCompanyType());
		
		if(!StringUtil.isStringEmpty(vendorTO.getEmail())){
			vendorDO.setAddress(vendorTO.getEmail());
		}
		logger.debug("VENDOR :: Email ----------------->"+vendorDO.getEmail());
		
		if(!StringUtil.isStringEmpty(vendorTO.getFax())){
			vendorDO.setFax(vendorTO.getFax());
		}
		logger.debug("VENDOR :: Fax ----------------->"+vendorDO.getFax());
		
		if(!StringUtil.isStringEmpty(vendorTO.getFirstName())){
			vendorDO.setFirstname(vendorTO.getFirstName());
		}
		logger.debug("VENDOR :: First Name ----------------->"+vendorDO.getFirstname());
		
		if(!StringUtil.isStringEmpty(vendorTO.getLastName())){
			vendorDO.setLastName(vendorTO.getLastName());
		}
		logger.debug("VENDOR :: Last Name ----------------->"+vendorDO.getLastName());
		
		if(!StringUtil.isStringEmpty(vendorTO.getMobileNo())){
			vendorDO.setMobile(vendorTO.getMobileNo());
		}
		logger.debug("VENDOR :: Mobile No ----------------->"+vendorDO.getMobile());
		
		if(!StringUtil.isStringEmpty(vendorTO.getPhoneNo())){
			vendorDO.setPhone(vendorTO.getPhoneNo());
		}
		logger.debug("VENDOR :: Phone ----------------->"+vendorDO.getPhone());
		
		if(!StringUtil.isStringEmpty(vendorTO.getPincode())){
			vendorDO.setPincode(vendorTO.getPincode());
		}
		logger.debug("VENDOR :: Pincode ----------------->"+vendorDO.getPincode());
		
		if(!StringUtil.isStringEmpty(vendorTO.getService())){
			vendorDO.setService(vendorTO.getService());
		}
		logger.debug("VENDOR :: Service ----------------->"+vendorDO.getService());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorType())){
			String accGroupSAP = vendorTO.getVendorType();
			VendorTypeDO vendorTypeDO = null;
			vendorTypeDO = organizationCommonDAO.getVendorTypeByAccGroup(accGroupSAP);
			if(!StringUtil.isNull(vendorTypeDO)){
				vendorDO.setVendorTypeDO(vendorTypeDO);
				logger.debug("VENDOR :: Vendor Type ----------------->"+vendorDO.getVendorTypeDO().getVendorTypeId());
			}
		}
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: convertVendorHeaderTOtoDO :: End");
		return vendorDO;
	}

	@Override
	public boolean saveOrUpdateVendorDetails(List<CSDSAPLoadMovementVendorDO> updateVendorDOs) throws CGSystemException {
		boolean isUpdate = false;
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveOrUpdateVendorDetails :: Start");
		try{
			isUpdate = integrationDAO.saveOrUpdateVendorDetails(updateVendorDOs);
		}catch(Exception e){
			logger.error("VENDOR :: Exception IN :: VendorMasterSAPTransactionServiceImpl :: saveOrUpdateVendorDetails :: ",e);
			 throw new CGSystemException(e);
		}
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveOrUpdateVendorDetails :: End");
		return isUpdate;
	}



	@SuppressWarnings("null")
	@Override
	public boolean saveDetailsOneByOne(List<CSDSAPLoadMovementVendorDO> updateVendorDOs) {
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveOrUpdateVendorDetails :: Start");
		List<CSDSAPLoadMovementVendorDO> errorVendorDOs = null;
		List<SAPVendorDO> stagingVendorDOs = null;
		SAPVendorDO stagingVendorDO = null;
		boolean  isSaved = false;
		try{
			errorVendorDOs = integrationDAO.saveDetailsOneByOne(updateVendorDOs);
			if(!StringUtil.isEmptyColletion(errorVendorDOs)){
				for(CSDSAPLoadMovementVendorDO errorRecord : errorVendorDOs){
					stagingVendorDO = new SAPVendorDO();
					stagingVendorDO =  convertErrorRecordsToStagingDO(errorRecord);
					stagingVendorDOs.add(stagingVendorDO);
				}
				if(!StringUtil.isEmptyColletion(stagingVendorDOs)){
					isSaved = integrationDAO.saveVendorErrorRecords(stagingVendorDOs);
					
					if(isSaved){
						//If Saved Successfully into staging table trigger mail to SAP
						sendEmail(stagingVendorDOs);
					}
				}
			}
		}catch(Exception e){
			logger.error("VENDOR :: Exception IN :: VendorMasterSAPTransactionServiceImpl :: saveOrUpdateVendorDetails :: ",e);
		}
		return isSaved;
	}
	
	private void sendEmail(List<SAPVendorDO> stagingVendorDOs) throws CGBusinessException, CGSystemException {
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: sendEmail :: Start");
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
			logger.error("VENDOR :: ServiceRequestForServiceReqServiceImpl::saveOrUpdateServiceReqDtls ::sendMail",e);
		}
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: sendEmail :: End");
	}
	
	private StringBuilder prepareMailBody(List<SAPVendorDO> stagingVendorDOs){
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: prepareMailBody :: Start");
		StringBuilder plainMailBody = new StringBuilder();
		plainMailBody.append("<html><body> Dear Sir/madam");
		plainMailBody.append("<br/><br/>Error came while processing Vendor Records to CSD Database");
		if(!StringUtil.isEmptyColletion(stagingVendorDOs)){
			for(SAPVendorDO stagingVendorDO : stagingVendorDOs){
				if(!StringUtil.isStringEmpty(stagingVendorDO.getVendorCode())){
					plainMailBody.append("<br/><br/>Vendor Code : "+stagingVendorDO.getVendorCode());
				}
				if(!StringUtil.isStringEmpty(stagingVendorDO.getException())){
					plainMailBody.append("<br/><br/>Exception : "+stagingVendorDO.getException());
				}
			}
		}
		plainMailBody.append("<BR><BR> Regarads,<BR> FFCL IT support");
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: prepareMailBody :: End");
		return plainMailBody;
	}
	
	private void prepareCallerMailAddress(List<MailSenderTO> mailerList,StringBuilder plainMailBody) {
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: Start");
		MailSenderTO callerSenderTO=new MailSenderTO();
		callerSenderTO.setTo(new String[]{SAPIntegrationConstants.EMAILD_ID});
		//callerSenderTO.setMailSubject(subject);
		callerSenderTO.setPlainMailBody(plainMailBody.toString());
		mailerList.add(callerSenderTO);
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: prepareCallerMailAddress :: End");
	}

	/*private void prepareExecutiveMail(StringBuilder plainMailBody,
			 List<MailSenderTO> mailerList) {
				MailSenderTO exucutive = new MailSenderTO();
				exucutive.setTo(new String[]{SAPIntegrationConstants.EMAILD_ID});
				//exucutive.setMailSubject("Complaint No:"+serviceRequestFollowupDO.getServiceRequestDO().getServiceRequestNo()+" assigned to you");
				exucutive.setPlainMailBody(plainMailBody.toString());
				mailerList.add(exucutive);
	}*/
	
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}

	@Override
	public boolean updateVendorOfficeMapped(List<SAPVendorTO> vendors)throws CGBusinessException, IllegalAccessException,InvocationTargetException, NoSuchMethodException, CGSystemException {
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: updateVendorOfficeMapped :: Start");
		boolean isUpdate = false;
		if(!StringUtil.isEmptyColletion(vendors)){
			for(SAPVendorTO vendorTO : vendors){
				if(!StringUtil.isNull(vendorTO)
						&& !StringUtil.isEmptyColletion(vendorTO.getRegionCodeTOList())){
						for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
							if(!StringUtil.isNull(regionTO)){
								logger.debug("VENDOR :: RHO Code From TO ----------------->"+regionTO.getRhoCode());
								String reportingRHOCode  = regionTO.getRhoCode();
								List<OfficeDO> officeDO = new ArrayList<OfficeDO>();
								officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
								OfficeDO ofcDO = null;
								if(!StringUtil.isNull(officeDO)
										&&(!StringUtil.isEmptyColletion(officeDO))){
									ofcDO = officeDO.get(0);
									if(!StringUtil.isNull(ofcDO)
											&& (!StringUtil.isNull(ofcDO.getMappedRegionDO()))
											&& (!StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getRegionId()))){ 
										Integer regionId = ofcDO.getMappedRegionDO().getRegionId();
										List<Integer> offcIds = organizationCommonDAO.getAllOfficesByRegionID(regionId);
										if(StringUtil.isEmptyColletion(offcIds)){
											isUpdate = integrationDAO.updateVendorOfficeMappedDO(offcIds,regionTO);
										}
									}
								}
							}
						}
					}
				}
		}
		logger.debug("VENDOR :: VendorMasterSAPTransactionServiceImpl :: updateVendorOfficeMapped :: End");
		return isUpdate;
	}

	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	@Override
	public boolean updateVendorDetails(List<CSDSAPLoadMovementVendorDO> updateVendorDOs) throws CGSystemException {
		logger.error("VENDOR :: VendorMasterSAPTransactionServiceImpl :: updateVendorDetails :: START");
		boolean isUpdate = false;
		try{
			isUpdate = integrationDAO.updateVendorDetails(updateVendorDOs);
		}catch(Exception e){
			logger.error("VENDOR :: Exception IN :: VendorMasterSAPTransactionServiceImpl :: updateVendorDetails :: ",e);
			 throw new CGSystemException(e);
		}
		return isUpdate;
	}
	
	
}
