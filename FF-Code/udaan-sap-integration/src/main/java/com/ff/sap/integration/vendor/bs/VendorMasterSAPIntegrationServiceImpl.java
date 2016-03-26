package com.ff.sap.integration.vendor.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.CSDSAPVendorRegionMapDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.sap.integration.material.bs.MaterialMasterSAPIntegrationServiceImpl;
import com.ff.sap.integration.to.SAPRegionCodeTO;
import com.ff.sap.integration.to.SAPVendorTO;

/**
 * @author cbhure
 *
 */
public class VendorMasterSAPIntegrationServiceImpl implements VendorMasterSAPIntegrationService {

	
Logger logger = Logger.getLogger(MaterialMasterSAPIntegrationServiceImpl.class);
	
	//private SAPIntegrationDAO integrationDAO; 
	//private OrganizationCommonDAO organizationCommonDAO;
	private VendorMasterSAPTransactionService vendorSAPTransactionService;
	
	public boolean saveVendorDetails(List<SAPVendorTO> vendors,boolean iterateOneByOne) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveVendorDetails :: START");
		boolean isSaved = false;
		boolean isUpdate = false;
		List<CSDSAPLoadMovementVendorDO> updateVendorDOs;
		List<CSDSAPLoadMovementVendorDO> vendorDOs;
		try{
			
			//If Batch Insert/Update fails then try to insert/update records one by one 
			// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
			//for error records
			//first chk data if present - Update
			updateVendorDOs = vendorSAPTransactionService.getUpdateVendorDOFromTO(vendors);
			if(!StringUtil.isNull(updateVendorDOs)
					&&(!StringUtil.isEmptyColletion(updateVendorDOs)) 
					&& !iterateOneByOne){
				isUpdate = vendorSAPTransactionService.updateVendorDetails(updateVendorDOs);
				if(isUpdate){
					isUpdate = false;
					isUpdate = vendorSAPTransactionService.updateVendorOfficeMapped(vendors);
				}	
			}/*else if(!isUpdate && !StringUtil.isNull(updateVendorDOs) ){
				isSaved = vendorSAPTransactionService.saveDetailsOneByOne(updateVendorDOs);
			}*/
			//If Batch Insert/Update fails then try to insert/update records one by one 
			// and error records will be stored in staging table ff_f_sap_vendor and email will be trigger 
			//for error records
			//Vendor code New - Save
			vendorDOs = vendorSAPTransactionService.getVendorDOFromTO(vendors);
			if(!StringUtil.isNull(vendorDOs)
					&&(!StringUtil.isEmptyColletion(vendorDOs)) 
					&& !iterateOneByOne){
					isSaved = false;
					isSaved = vendorSAPTransactionService.saveOrUpdateVendorDetails(vendorDOs);
			}/*else if(!isSaved && !StringUtil.isNull(vendorDOs)){
				isSaved = vendorSAPTransactionService.saveDetailsOneByOne(vendorDOs);
			}*/
			/*//Vendor Region Map Save
			vendorRegionMapDOs = getDOFromTOVendorRegionMapping(vendors);
			integrationDAO.saveDetails(vendorRegionMapDOs);*/
		}catch(CGSystemException e){
			logger.error("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveVendorDetails :: Exception :: ",e);
		}
		logger.debug("VENDOR :: VendorMasterSAPIntegrationServiceImpl :: saveVendorDetails :: END");
		return isSaved;
	}
	
	private boolean updateVendorOfficeMapped(List<SAPVendorTO> vendors) throws CGSystemException {
		boolean isUpdate = false;
		/*logger.debug("VendorMasterSAPIntegrationServiceImpl :: updateVendorOfficeMapped :: Start");
		if(!StringUtil.isEmptyColletion(vendors)){
			for(SAPVendorTO vendorTO : vendors){
				if(!StringUtil.isNull(vendorTO)){
					for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
						if(!StringUtil.isNull(regionTO)){
							logger.debug("RHO Code From TO ----------------->"+regionTO.getRhoCode());
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
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: updateVendorOfficeMapped :: End");*/
		return isUpdate;
	}

	private List<CGBaseDO> getVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		logger.debug("EmployeeSAPIntegrationServiceImpl :: getVendorDOFromTO :: Start");
		List<CGBaseDO> baseList = null;
		if(vendors != null && !vendors.isEmpty()) {
			baseList = new ArrayList<CGBaseDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null;//new EmployeeDO();
			for(SAPVendorTO vendorTO : vendors) {
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					//vendorDO = organizationCommonDAO.getVendorByCode(vendorCode);
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
		return baseList;
	}

	private List<CGBaseDO> getUpdateVendorDOFromTO(List<SAPVendorTO> vendors) throws CGBusinessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, CGSystemException  {
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: getUpdateVendorDOFromTO :: Start");
		List<CGBaseDO> baseList = null;	
		String userName = "SAP_USER";
		if(vendors != null && !vendors.isEmpty()) {			
			baseList = new ArrayList<CGBaseDO>(vendors.size());
			CSDSAPLoadMovementVendorDO vendorDO = null; //new LoadMovementVendorDO();
			for(SAPVendorTO vendorTO : vendors) {	
				if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
					String vendorCode = vendorTO.getVendorCode();
					//vendorDO =	organizationCommonDAO.getVendorByCode(vendorCode);
					if(!StringUtil.isNull(vendorDO)){
						logger.debug("Vendor Updated Code ------->"+vendorDO.getVendorCode());
						CSDSAPLoadMovementVendorDO venDO = null;
						boolean isUpdate = true;
						venDO = convertVendorHeaderTOtoDO(vendorTO,isUpdate,vendorDO);
						//UserDO userDO = integrationDAO.getSAPUserDtls(userName);
						/*if(!StringUtil.isNull(userDO)
								&& !StringUtil.isEmptyInteger(userDO.getUserId())){
							venDO.setCreatedBy(userDO.getUserId());
							venDO.setUpdatedBy(userDO.getUserId());
							Date today = Calendar.getInstance().getTime();
							venDO.setCreatedDate(today);
							venDO.setUpdatedDate(today);
						}*/
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
		return baseList;
	}

	private Set<CSDSAPVendorRegionMapDO> convertVendorRegionDtlsTOtoDO(SAPVendorTO vendorTO, boolean isUpdate, CSDSAPLoadMovementVendorDO venDO) throws CGSystemException {
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertVendorRegionDtlsTOtoDO :: Start");
		Set<CSDSAPVendorRegionMapDO> venRegionMappingSet = new HashSet<>();
		logger.debug("Is update Boolean Status  --------->"+isUpdate);
		if(!isUpdate){
			logger.debug("Vendor Region Dtls While Save");
			if(!StringUtil.isEmptyColletion(vendorTO.getRegionCodeTOList())){
				for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
					if(!StringUtil.isStringEmpty(regionTO.getRhoCode())){
						CSDSAPVendorRegionMapDO venRegionMapDO = new CSDSAPVendorRegionMapDO();
						logger.debug("RHO Code From TO ----------------->"+regionTO.getRhoCode());
						String reportingRHOCode  = regionTO.getRhoCode();
						List<OfficeDO> officeDO = new ArrayList<OfficeDO>();
					//	officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
						OfficeDO ofcDO = null;
						if(!StringUtil.isNull(officeDO)
								&&(!StringUtil.isEmptyColletion(officeDO))){
							ofcDO = officeDO.get(0);
							if(!StringUtil.isNull(ofcDO)
									&& (!StringUtil.isNull(ofcDO.getMappedRegionDO()))
									&& (!StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getRegionId()))){ 
								venRegionMapDO.setRegionId(ofcDO.getMappedRegionDO().getRegionId());
								venRegionMapDO.setStatus(regionTO.getStatus());
								logger.debug("Region ID ----------------->"+venRegionMapDO.getRegionId());
							}
						}
						venRegionMapDO.setVendorDO(venDO);
						venRegionMappingSet.add(venRegionMapDO);
					}
				}
			}
		}else{
			logger.debug("Vendor Region Dtls While Update-------------------->");
			if(!StringUtil.isEmptyColletion(vendorTO.getRegionCodeTOList())){
				for(SAPRegionCodeTO regionTO : vendorTO.getRegionCodeTOList()){
					if(!StringUtil.isStringEmpty(regionTO.getRhoCode())){
						logger.debug("RHO Code From TO ----------------->"+regionTO.getRhoCode());
						String reportingRHOCode  = regionTO.getRhoCode();
						List<OfficeDO> officeDO = new ArrayList<OfficeDO>();
					//	officeDO = organizationCommonDAO.getOfficeIdByReportingRHOCode(reportingRHOCode);
						OfficeDO ofcDO = null;
						if(!StringUtil.isNull(officeDO)
								&&(!StringUtil.isEmptyColletion(officeDO))){
							ofcDO = officeDO.get(0);
							if(!StringUtil.isNull(ofcDO)
									&& (!StringUtil.isNull(ofcDO.getMappedRegionDO()))
									&& (!StringUtil.isEmptyInteger(ofcDO.getMappedRegionDO().getRegionId()))){
								
								Integer vendorId = venDO.getVendorId();
								Integer regionId = ofcDO.getMappedRegionDO().getRegionId();
								logger.debug("vendorId From TO ----------->"+vendorId);
								logger.debug("Region ID From TO ----------->"+regionId);
								
								CSDSAPVendorRegionMapDO vRegionMapDO = new CSDSAPVendorRegionMapDO();
							//	vRegionMapDO = organizationCommonDAO.getVendorRegionMapping(vendorId,regionId);
								logger.debug("Afetr Search in DB vRegionMapDO Region ---------------> "+vRegionMapDO.getRegionId());
								logger.debug("Afer Search in DB vRegionMapDO ---------------> "+vRegionMapDO.getVendorRegionMappingId());
								if(StringUtil.isEmptyInteger(vRegionMapDO.getRegionId())){
									//logger.debug("vendorId From Vendor DO ----------->"+vRegionMapDO.getVendorDO().getVendorId());
									logger.debug("Region ID From Vendor DO ----------->"+vRegionMapDO.getRegionId());
									vRegionMapDO.setRegionId(ofcDO.getMappedRegionDO().getRegionId());
									vRegionMapDO.setStatus(regionTO.getStatus());
									vRegionMapDO.setVendorDO(venDO);
									venRegionMappingSet.add(vRegionMapDO);
									logger.debug("While Update Inserting Map Size =======> "+venRegionMappingSet.size());
								}
					 			logger.debug("Region ID ----------------->"+vRegionMapDO.getRegionId());
							}
						}
					}
				}
			}
		}
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertVendorRegionDtlsTOtoDO :: End");
		return venRegionMappingSet;
	}

	private CSDSAPLoadMovementVendorDO convertVendorHeaderTOtoDO(SAPVendorTO vendorTO,boolean isUpdate, CSDSAPLoadMovementVendorDO venDO) throws CGSystemException {
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertVendorHeaderTOtoDO :: Start");
		CSDSAPLoadMovementVendorDO vendorDO = new CSDSAPLoadMovementVendorDO();
		if(isUpdate && (!StringUtil.isNull(venDO)
				&& (!StringUtil.isEmptyInteger(venDO.getVendorId())))){ 
			vendorDO.setVendorId(venDO.getVendorId());
		}else{
			vendorDO.setVendorId(!StringUtil.isEmptyInteger(vendorTO.getVendorId())?vendorTO.getVendorId() :null);
		}
		
		logger.debug("Vendor ID ----------------->"+vendorDO.getVendorId());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorCode())){
			vendorDO.setVendorCode(vendorTO.getVendorCode());
		}
		logger.debug("vendor Code----------------->"+vendorDO.getVendorCode());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorName())){
			vendorDO.setBusinessName(vendorTO.getVendorName());
		}
		logger.debug("Business Name ----------------->"+vendorDO.getBusinessName());
		
		if(!StringUtil.isStringEmpty(vendorTO.getAddress())){
			vendorDO.setAddress(vendorTO.getAddress());
		}
		logger.debug("Address ----------------->"+vendorDO.getAddress());
		
		if(!StringUtil.isStringEmpty(vendorTO.getCompanyType())){
			vendorDO.setCompanyType(vendorTO.getCompanyType());
		}
		logger.debug("Comp Type ----------------->"+vendorDO.getCompanyType());
		
		if(!StringUtil.isStringEmpty(vendorTO.getEmail())){
			vendorDO.setAddress(vendorTO.getEmail());
		}
		logger.debug("Email ----------------->"+vendorDO.getEmail());
		
		if(!StringUtil.isStringEmpty(vendorTO.getFax())){
			vendorDO.setFax(vendorTO.getFax());
		}
		logger.debug("Fax ----------------->"+vendorDO.getFax());
		
		if(!StringUtil.isStringEmpty(vendorTO.getFirstName())){
			vendorDO.setFirstname(vendorTO.getFirstName());
		}
		logger.debug("First Name ----------------->"+vendorDO.getFirstname());
		
		if(!StringUtil.isStringEmpty(vendorTO.getLastName())){
			vendorDO.setLastName(vendorTO.getLastName());
		}
		logger.debug("Last Name ----------------->"+vendorDO.getLastName());
		
		if(!StringUtil.isStringEmpty(vendorTO.getMobileNo())){
			vendorDO.setMobile(vendorTO.getMobileNo());
		}
		logger.debug("Mobile No ----------------->"+vendorDO.getMobile());
		
		if(!StringUtil.isStringEmpty(vendorTO.getPhoneNo())){
			vendorDO.setPhone(vendorTO.getPhoneNo());
		}
		logger.debug("Phone ----------------->"+vendorDO.getPhone());
		
		if(!StringUtil.isStringEmpty(vendorTO.getPincode())){
			vendorDO.setPincode(vendorTO.getPincode());
		}
		logger.debug("Pincode ----------------->"+vendorDO.getPincode());
		
		if(!StringUtil.isStringEmpty(vendorTO.getService())){
			vendorDO.setService(vendorTO.getService());
		}
		logger.debug("Service ----------------->"+vendorDO.getService());
		
		if(!StringUtil.isStringEmpty(vendorTO.getVendorType())){
			String accGroupSAP = vendorTO.getVendorType();
			VendorTypeDO vendorTypeDO = null;
			//vendorTypeDO = organizationCommonDAO.getVendorTypeByAccGroup(accGroupSAP);
			if(!StringUtil.isNull(vendorTypeDO)){
				vendorDO.setVendorTypeDO(vendorTypeDO);
				logger.debug("Vendor Type ----------------->"+vendorDO.getVendorTypeDO().getVendorTypeId());
			}
		}
		logger.debug("VendorMasterSAPIntegrationServiceImpl :: convertVendorHeaderTOtoDO :: End");
		return vendorDO;
	}

	/**
	 * @param vendorSAPTransactionService the vendorSAPTransactionService to set
	 */
	public void setVendorSAPTransactionService(
			VendorMasterSAPTransactionService vendorSAPTransactionService) {
		this.vendorSAPTransactionService = vendorSAPTransactionService;
	}

	/*public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}

	*//**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 *//*
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}*/
	
	
}
