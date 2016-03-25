package com.ff.admin.master.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.master.dao.VendorMappingDAO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.business.VendorRegionMapTO;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.business.VendorOfficeMapDO;
import com.ff.domain.business.VendorRegionMapDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;

public class VendorMappingServiceImpl implements VendorMappingService {
	
	private VendorMappingDAO  vendorMappingDAO;
	/** The geography common service. */
	private transient GeographyCommonService geographyCommonService;
	/** The organization common service. */
	private transient OrganizationCommonService organizationCommonService;
	
	
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public void setVendorMappingDAO(VendorMappingDAO vendorMappingDAO) {
		this.vendorMappingDAO = vendorMappingDAO;
	} 
	
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(VendorMappingServiceImpl.class);
    

	public LoadMovementVendorTO getVendorDetails(String vendorCode)throws CGBusinessException,
	CGSystemException {
		LOGGER.debug("VendorMappingServiceImpl::getVendorDetails::START----->");
		LoadMovementVendorDO vendorDO =vendorMappingDAO.getVendorDetails(vendorCode);
		LoadMovementVendorTO vendorTO= new LoadMovementVendorTO();
		if(!StringUtil.isNull(vendorDO)) {
			convertVendorDO2TO(vendorDO,vendorTO);
		}
		else{
			throw new CGBusinessException(AdminErrorConstants.NO_VENDORDETAILS_FOUND);
		}
		LOGGER.debug("VendorMappingServiceImpl::getVendorDetails::END----->");
		return vendorTO;
	}
	
	@Override
	public List<CityTO> getCitiesByRegionId(final Integer regionId)
			throws CGBusinessException, CGSystemException {
		final CityTO cityTO = new CityTO();
		cityTO.setRegion(regionId);
		return geographyCommonService.getCitiesByCity(cityTO);
	}
	
	public void  convertVendorDO2TO(LoadMovementVendorDO vendorDO,LoadMovementVendorTO vendorTO) throws CGBusinessException{
		LOGGER.debug("VendorMappingServiceImpl::convertVendorDO2TO::START----->");
		CGObjectConverter.createToFromDomain(vendorDO, vendorTO);
		List <VendorRegionMapTO> vendorRegionMapTOs=new ArrayList<VendorRegionMapTO>();
		try {
		if(!StringUtil.isNull(vendorDO.getVendorRegionMappingDO())){
			Set<VendorRegionMapDO> regionMappedList = vendorDO.getVendorRegionMappingDO();
			for(VendorRegionMapDO regionDo :regionMappedList){
				VendorRegionMapTO vendorRegionMapTO=new VendorRegionMapTO();
				CGObjectConverter.createToFromDomain(regionDo, vendorRegionMapTO);
				
				RegionTO regionTO= geographyCommonService.getRegionByIdOrName(null, regionDo.getRegionId());
				vendorRegionMapTO.setRegionTO(regionTO);
				vendorRegionMapTOs.add(vendorRegionMapTO);
			}
		
			vendorTO.setVendorRegionMappingTO(vendorRegionMapTOs);
		  }	
		
		 if(!StringUtil.isNull(vendorDO.getVendorTypeDO())){
			 if(!StringUtil.isNull(vendorDO.getVendorTypeDO().getVendorTypeId())){
				 vendorTO.setVendorTypeId(vendorDO.getVendorTypeDO().getVendorTypeId());
			 }
			 
			 if(!StringUtil.isNull(vendorDO.getVendorTypeDO().getVendorTypeDescription())){
				 vendorTO.setVendorType(vendorDO.getVendorTypeDO().getVendorTypeDescription());
			 }
		 }
		 
		 if(!StringUtil.isNull(vendorDO.getOfficeDO())){
			 OfficeTO officeTO=new OfficeTO();
			 CGObjectConverter.createToFromDomain(vendorDO.getOfficeDO(), officeTO);
			 vendorTO.setOfficeTO(officeTO);
		 }
			
		} catch (CGSystemException e) {
			LOGGER.error("Exception in VendorMappingServiceImpl::convertVendorDO2TO",e);
		}
		LOGGER.debug("VendorMappingServiceImpl::convertVendorDO2TO::END----->");
	}	
	
	
	@Override
	public List<OfficeTO> getOfficesByCityId(final Integer cityId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllOfficesByCity(cityId);
	}
		
	
	public Boolean saveOrUpdateVendor(LoadMovementVendorTO vendorTO)throws CGBusinessException,
	CGSystemException{
		LOGGER.debug("VendorMappingServiceImpl::saveOrUpdateVendor::START----->");
		boolean flag=false;
		 if(!StringUtil.isNull(vendorTO)){
			 LoadMovementVendorDO vendorDO=new LoadMovementVendorDO(); 
			 prepareVendorTO2DO(vendorDO,vendorTO);
			 flag= vendorMappingDAO.saveOrUpdateVendor(vendorDO);
			
		 }
		 LOGGER.debug("VendorMappingServiceImpl::saveOrUpdateVendor::END----->");
		 return flag;
	}
	
	
	public void  prepareVendorTO2DO(LoadMovementVendorDO vendorDO,LoadMovementVendorTO vendorTO) throws CGBusinessException{
		LOGGER.debug("VendorMappingServiceImpl::prepareVendorTO2DO::START----->");
		CGObjectConverter.createDomainFromTo(vendorTO,vendorDO);
		vendorDO.setDtToBranch("N");
		if(!StringUtil.isNull(vendorTO.getVendorTypeId())){
			VendorTypeDO vendorTypeDO=new VendorTypeDO();
			vendorTypeDO.setVendorTypeId(vendorTO.getVendorTypeId());
			vendorDO.setVendorTypeDO(vendorTypeDO);
		}
		if(!StringUtil.isNull(vendorTO.getOfficeTO())){
			OfficeDO officeDO=new OfficeDO();
			CGObjectConverter.createDomainFromTo(vendorTO.getOfficeTO(),officeDO);
			vendorDO.setOfficeDO(officeDO);
		}
		LOGGER.debug("VendorMappingServiceImpl::prepareVendorTO2DO::END----->");
	}
	
	public boolean saveOrUpdateVendorOffice(LoadMovementVendorTO vendorTO,Integer regionId,List<Integer> officeList,List<OfficeTO> officeTOBeRemovedList)throws CGBusinessException,
	CGSystemException{
		LOGGER.debug("VendorMappingServiceImpl::saveOrUpdateVendorOffice::START----->");
		Integer regionMappedId=null;
		boolean flag=false;
		List<VendorRegionMapTO> vendorRegionMapTOs=vendorTO.getVendorRegionMappingTO();
		    for(VendorRegionMapTO vendorRegionMapTO:vendorRegionMapTOs ){
		    	if(vendorRegionMapTO.getRegionTO().getRegionId()!=null){
		    	 if(vendorRegionMapTO.getRegionTO().getRegionId().equals(regionId)){
		    		regionMappedId=vendorRegionMapTO.getVendorRegionMappingId();
		    	 }
		    	}
		    }
		
		List<VendorOfficeMapDO> vendorOfficeDOList =prepareVendorOfficeMapDO(regionMappedId,officeList);
		
		List<Integer> branchIds=new ArrayList<>();
		for (OfficeTO officeTO : officeTOBeRemovedList) {
			branchIds.add(officeTO.getOfficeId());
		}
		
		List<VendorOfficeMapDO> existsVendorOfficeDOList=vendorMappingDAO.alreadtExistVedorOffice(vendorOfficeDOList);
		if(existsVendorOfficeDOList.size()>0){
		   flag=vendorMappingDAO.saveOrUpdateVendorOffice(existsVendorOfficeDOList);
		}
		vendorMappingDAO.deleteVendorOfficeMap(regionMappedId, branchIds);
		LOGGER.debug("VendorMappingServiceImpl::saveOrUpdateVendorOffice::END----->");
	    return flag;
	}
	
	public List<VendorOfficeMapDO>  prepareVendorOfficeMapDO(Integer regionMappedId,List<Integer> officeList) throws CGBusinessException{
		LOGGER.debug("VendorMappingServiceImpl::prepareVendorOfficeMapDO::START----->");
		List<VendorOfficeMapDO> vendorOfficeDOList=new ArrayList<VendorOfficeMapDO>();
		for(Integer office:officeList){
			VendorOfficeMapDO vendorOfficeMapDO=new VendorOfficeMapDO();
			vendorOfficeMapDO.setVendorOfficeRegionId(regionMappedId);
			OfficeDO officeDO=new OfficeDO(); 
			officeDO.setOfficeId(office);
			vendorOfficeMapDO.setOfficeDO(officeDO);
			vendorOfficeMapDO.setStatus("A");
			vendorOfficeMapDO.setUpdatedDate(DateUtil.getCurrentDate());
			vendorOfficeDOList.add(vendorOfficeMapDO);
		}
		LOGGER.debug("VendorMappingServiceImpl::prepareVendorOfficeMapDO::END----->");
		return vendorOfficeDOList;
	}
	
	public List<String> getAllVendorsList()throws CGBusinessException, CGSystemException{
		
		return vendorMappingDAO.getAllVendorsList();
	}

	@Override
	public List<OfficeTO> getSelectedOffices(Integer regionId, Integer cityId,
			Integer vendorId) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList=null;
		LOGGER.debug("VendorMappingServiceImpl::getSelectedOffices::START----->");
		List<OfficeDO> officeDOList=vendorMappingDAO.getSelectedOffices(regionId, cityId, vendorId);
		
		if (!StringUtil.isEmptyList(officeDOList)) {
			officeTOList = new ArrayList<>(officeDOList.size());
			try {
				for (OfficeDO frDO : officeDOList) {
					OfficeTO to = new OfficeTO();
					PropertyUtils.copyProperties(to, frDO);
					officeTOList.add(to);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("VendorMappingServiceImpl::getSelectedOffices::END----->",e);
			}
		}
		LOGGER.debug("VendorMappingServiceImpl::getSelectedOffices::END----->");
		return officeTOList;
	}
}
