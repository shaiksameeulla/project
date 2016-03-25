package com.ff.admin.tracking.manifestTracking.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.geography.CityTO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.geography.service.GeographyCommonServiceImpl;
import com.ff.universe.organization.service.OrganizationCommonServiceImpl;

public class ManifestTrackingConverter {

	public static final NumberFormat formatter = new DecimalFormat("#0.000"); 
	
	private static GeographyCommonServiceImpl geographyCommonService;
	private static OrganizationCommonServiceImpl organizationCommonService;
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GeographyCommonServiceImpl.class);

	
	public static void setOrganizationCommonService(
			OrganizationCommonServiceImpl organizationCommonService) {
		ManifestTrackingConverter.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param geographyCommonService the geographyCommonService to set
	 */
	public static void setGeographyCommonService(
			GeographyCommonServiceImpl geographyCommonService) {
		ManifestTrackingConverter.geographyCommonService = geographyCommonService;
	}

	public static ManifestBaseTO convertManifestDO(ManifestDO manifestDO) throws CGBusinessException{
		LOGGER.trace("ManifestTrackingConverter::convertManifestDO()::START");
		ManifestBaseTO manifestTO=new ManifestBaseTO();
		if(manifestDO!=null){
			String manifestType="";
			if(StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTO, manifestDO.getManifestType())){
				manifestType = "RTO MANIFEST";
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_RTH, manifestDO.getManifestType())){
				manifestType = "RTH MANIFEST";
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_HUB_MISROUTE, manifestDO.getManifestType())){
				manifestType = "HUB MISROUTE";
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_BRANCH_MISROUTE, manifestDO.getManifestType())){
				manifestType = "BRANCH MISROUTE";
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.MANIFEST_TYPE_POD, manifestDO.getManifestType())){
				manifestType = "POD MANIFEST";
			}
			manifestTO.setManifestType(manifestType);
			if(!StringUtil.isEmptyDouble(manifestDO.getManifestWeight())){
				manifestTO.setMnfstWeight(formatter.format(manifestDO.getManifestWeight()));
			}
			manifestTO.setManifestDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(manifestDO.getManifestDate()));
			
			OfficeTO originOffice = new OfficeTO();
			OfficeDO  originOfficeDO = null;
			OfficeTO destOffice = new OfficeTO();
			OfficeDO  destOfficeDO = null;
			CityTO city = new CityTO();
			originOfficeDO = manifestDO.getOriginOffice();
			destOfficeDO = manifestDO.getDestOffice();
			if(!StringUtil.isNull(originOfficeDO)){
				CGObjectConverter.createToFromDomain(originOfficeDO,originOffice);
			}
			if(!StringUtil.isNull(destOfficeDO)){
				CGObjectConverter.createToFromDomain(destOfficeDO,destOffice);
			}else if(!StringUtil.isNull(manifestDO.getDestinationCity())){
				CGObjectConverter.createToFromDomain(manifestDO.getDestinationCity(),city);				
			}
			if(StringUtils.equalsIgnoreCase(CommonConstants.YES, manifestDO.getMultipleDestination())){
				CityTO destCityTO = new CityTO();
				CGObjectConverter.createToFromDomain(manifestDO.getDestinationCity(),destCityTO);
				manifestTO.setDestCityTO(destCityTO);
			}
			manifestTO.setOriginOfficeTO(originOffice);
			manifestTO.setDestinationOfficeTO(destOffice);
			manifestTO.setDestCityTO(city);
			manifestTO.setManifestNumber(manifestDO.getManifestNo());
		}
		LOGGER.trace("ManifestTrackingConverter::convertManifestDO()::END");
		return manifestTO;
		
	}
	
	

	public static List<ManifestBaseTO> convertMbplManifest(Set<ManifestDO> mbplManifestDOs) throws CGBusinessException{
		LOGGER.trace("ManifestTrackingConverter::convertMbplManifest()::START");
		ManifestBaseTO childmanifestTO=null;
		//Out manifest
		List<ManifestBaseTO> detailsTO = new ArrayList<>();
		//In manifest
		List<ManifestBaseTO> inMnfstDetailsTO = new ArrayList<>();
		
		for(ManifestDO mbplDO:mbplManifestDOs){
			OfficeTO office =new OfficeTO();
			CityTO city = null;
			OfficeDO office1 = new OfficeDO();
			office1 = mbplDO.getDestOffice();
			if(office1!=null){
				CGObjectConverter.createToFromDomain(office1,office);
			}else if(!StringUtil.isNull(mbplDO.getDestinationCity())){
				city = new CityTO(); 
				CGObjectConverter.createToFromDomain(mbplDO.getDestinationCity(),city);				
			}
			childmanifestTO=new ManifestBaseTO();
			childmanifestTO.setManifestNumber(mbplDO.getManifestNo());
			childmanifestTO.setManifestType(mbplDO.getManifestDirection());
			if(!StringUtil.isEmptyDouble(mbplDO.getManifestWeight())){
				childmanifestTO.setMnfstWeight(formatter.format(mbplDO.getManifestWeight()));
			}
			childmanifestTO.setDestinationOfficeTO(office);
			childmanifestTO.setDestCityTO(city);
			if(StringUtils.equalsIgnoreCase(CommonConstants.DIRECTION_OUT, mbplDO.getManifestDirection())){
				detailsTO.add(childmanifestTO);
			}else if(StringUtils.equalsIgnoreCase(CommonConstants.DIRECTION_IN, mbplDO.getManifestDirection())){
				inMnfstDetailsTO.add(childmanifestTO);
			}
		}
		LOGGER.trace("ManifestTrackingConverter::convertMbplManifest()::END");
		detailsTO.addAll(inMnfstDetailsTO);
		return detailsTO;
   }
	
	
	public static List<ConsignmentTO> convertOgmManifest(Set<ConsignmentDO> consignmentDOs) throws CGBusinessException, CGSystemException{
		LOGGER.trace("ManifestTrackingConverter::convertOgmManifest()::START");
		ConsignmentTO  consignmentTO=null;
		List<ConsignmentTO> detailsTO = new ArrayList<>(consignmentDOs.size());
		for(ConsignmentDO ogmDO:consignmentDOs){
			consignmentTO = new ConsignmentTO();
			consignmentTO.setConsgNo(ogmDO.getConsgNo());
			if(!StringUtil.isEmptyDouble(ogmDO.getActualWeight())){
				consignmentTO.setStrCnWeight(formatter.format(ogmDO.getActualWeight()));
			}
			PincodeDO pincodeDO  = ogmDO.getDestPincodeId();
			CityTO cityTO = new CityTO();
			if(!StringUtil.isNull(pincodeDO)){
				cityTO = geographyCommonService.getCityByPincodeId(pincodeDO.getPincodeId());
			}
			consignmentTO.setDestCity(cityTO);
			detailsTO.add(consignmentTO);
		}
		LOGGER.trace("ManifestTrackingConverter::convertOgmManifest()::END");
		return detailsTO;
		
	}

	public static List<ComailTO> convertComailsManifested(Set<ComailDO> comailDOs) throws CGBusinessException, CGSystemException{
		LOGGER.trace("ManifestTrackingConverter::convertComailsManifested()::START");
		List<ComailTO> comailTOs = new ArrayList<>();
		ComailTO comailTO = null;
		for (ComailDO comailDO : comailDOs) {
			comailTO = new ComailTO();
			comailTO.setCoMailNo(comailDO.getCoMailNo());
			Integer comailDestOff = comailDO.getDestinationOffice();
			if(!StringUtil.isEmptyInteger(comailDestOff)){
				OfficeTO officeTO = organizationCommonService.getOfficeDetails(comailDestOff);
				comailTO.setDestOffice(officeTO);
			}
			comailTOs.add(comailTO);
		}
		LOGGER.trace("ManifestTrackingConverter::convertComailsManifested()::END");
		return comailTOs;
	}
}
