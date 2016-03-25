package com.ff.admin.tracking.manifestTracking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.tracking.manifestTracking.converter.ManifestTrackingConverter;
import com.ff.admin.tracking.manifestTracking.dao.ManifestTrackingDAO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.tracking.ProcessMapDO;
import com.ff.manifest.ComailTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ManifestTrackingTO;
import com.ff.tracking.ProcessMapTO;
import com.ff.tracking.TrackingManifestTO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;
import com.ff.universe.tracking.service.TrackingUniversalService;

public class ManifestTrackingServiceImpl implements ManifestTrackingService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManifestTrackingServiceImpl.class);

	private ManifestTrackingDAO manifestTrackingDAO;
	private TrackingUniversalService trackingUniversalService;
	
	/**
	 * @param manifestTrackingDAO
	 *            the manifestTrackingDAO to set
	 */
	public void setManifestTrackingDAO(ManifestTrackingDAO manifestTrackingDAO) {
		this.manifestTrackingDAO = manifestTrackingDAO;
	}

	/**
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	@Override
	public ManifestTrackingTO viewTrackInformation(String manifestNumber,
			String manifestType) throws CGSystemException, CGBusinessException {
		LOGGER.trace("ManifestTrackingServiceImpl::viewTrackInformation()::START");
		ManifestTrackingTO manifestTrackingTO = new ManifestTrackingTO();

		//First Out Manifest
		TrackingManifestTO outManifestTO = getManifestInfoByDirection(manifestNumber, manifestType,CommonConstants.DIRECTION_OUT);
		manifestTrackingTO.setOutManifestTO(outManifestTO);
		// Latest In Manifest
		TrackingManifestTO inManifestTO = getManifestInfoByDirection(manifestNumber, manifestType,CommonConstants.DIRECTION_IN);
		manifestTrackingTO.setInManifestTO(inManifestTO);
		
		List<ProcessMapDO> processMapDOs = manifestTrackingDAO.getDetailedTrackingInformation(manifestNumber);
		if (!StringUtil.isEmptyList(processMapDOs)) {
			List<ProcessMapTO> processMapTO = setProcessMapTO(processMapDOs);
			manifestTrackingTO.setProcessMapTO(processMapTO);
		}else{
			throw new CGBusinessException(AdminErrorConstants.PROCESS_DETAILS_NOT_FOUND);
		}
		LOGGER.trace("ManifestTrackingServiceImpl::viewTrackInformation()::END");
		return manifestTrackingTO;
	}

	private TrackingManifestTO getManifestInfoByDirection(String manifestNumber,
			String manifestType,String manifestDirection) throws CGSystemException, CGBusinessException {
		TrackingManifestTO trackingManifestTO = new TrackingManifestTO();
		List<ManifestBaseTO> embeddedManifestTO = null;
		List<ConsignmentTO> consignmentTO = null;
		String fetchProfile = "manifest-tracking";
		ManifestDO manifestDO = null;
		
		manifestDO = manifestTrackingDAO.getManifestInformation(manifestNumber, manifestDirection, fetchProfile);

		if (!StringUtil.isNull(manifestDO)) {
			LOGGER.debug("Manifest details found for manifest no :: "+manifestNumber);
			setManifestHeaderInfo(manifestType, trackingManifestTO, manifestDO);
			
			if (manifestType.equalsIgnoreCase("MBPL")) {
				//Getting the embedded manifest details for the Master Bag
				Set<ManifestDO> ManifestDOs = manifestDO.getEmbeddedManifestDOs();
				embeddedManifestTO = ManifestTrackingConverter.convertMbplManifest(ManifestDOs);
				Collections.sort(embeddedManifestTO);
				trackingManifestTO.setManifestTOs(embeddedManifestTO);
			} else if (manifestType.equalsIgnoreCase("BPL")) {
				String consignmentType=null;
				if(!StringUtil.isNull(manifestDO.getManifestLoadContent())){
					consignmentType = manifestDO.getManifestLoadContent().getConsignmentCode();					
				}else{
					LOGGER.debug("BAG TRACKING IS NOT DONE. MANIFEST LOAD CONTENT NOT FOUND...");
				}
				if(StringUtils.equalsIgnoreCase(consignmentType, CommonConstants.CONSIGNMENT_TYPE_DOCUMENT)){
					//bag details
					List<ManifestDO> embeddedManifestDOs = manifestTrackingDAO.getEmbeddedInManifestInfo(manifestDO.getManifestId(), fetchProfile);
					Set<ManifestDO> manifestDOs = new HashSet<ManifestDO>(embeddedManifestDOs);
					embeddedManifestTO = ManifestTrackingConverter.convertMbplManifest(manifestDOs);
					Collections.sort(embeddedManifestTO);
					trackingManifestTO.setManifestTOs(embeddedManifestTO);
				} else if (StringUtils.equalsIgnoreCase(consignmentType, CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
					Set<ConsignmentDO> consignmentDO = manifestDO.getConsignments();
					
					/*if(StringUtil.isEmptyColletion(consignmentDO)){
						manifestDO = manifestTrackingDAO.getDetailsFromOriginManifest(manifestNumber);
						if (!StringUtil.isNull(manifestDO)) {
							Set<ConsignmentDO> orgConsignmentDO = manifestDO.getConsignments();
							if(!StringUtil.isEmptyColletion(orgConsignmentDO)){
								consignmentDO = orgConsignmentDO;
							}
						}
					}*/
					if(!StringUtil.isEmptyColletion(consignmentDO)){
						consignmentTO = ManifestTrackingConverter.convertOgmManifest(consignmentDO);
						Collections.sort(consignmentTO);
						trackingManifestTO.setConsignmentTO(consignmentTO);
					}
				}
			}else if (manifestType.equalsIgnoreCase("OGM")) {
				Set<ConsignmentDO> consignmentDO = manifestDO.getConsignments();
				Set<ComailDO> comailDOs = manifestDO.getComails();
				
				//Get and set consignment details from manifest DO				
				consignmentTO = ManifestTrackingConverter.convertOgmManifest(consignmentDO);
				Collections.sort(consignmentTO);
				trackingManifestTO.setConsignmentTO(consignmentTO);
				
				//Get and set Comail details from manifest DO if exist				
				List<ComailTO> comailTOs = ManifestTrackingConverter.convertComailsManifested(comailDOs);
				Collections.sort(comailTOs);
				trackingManifestTO.setComailTO(comailTOs);				
			} 
		}else{
//			throw new CGBusinessException(AdminErrorConstants.MANIFEST_DETAILS_NOT_FOUND);
			LOGGER.debug("First Out Manifest details does not found for manifest no :: "+manifestNumber);
		}
		return trackingManifestTO;
	}
	private void setManifestHeaderInfo(String manifestType,
			TrackingManifestTO trackingManifestTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		ManifestBaseTO manifestTO = ManifestTrackingConverter.convertManifestDO(manifestDO);
		if(StringUtils.isEmpty(manifestTO.getManifestType())){
			manifestTO.setManifestType(manifestType);
		}
		if(StringUtils.isNotEmpty(manifestDO.getReceivedStatus())){
			switch (manifestDO.getReceivedStatus()) {
			case "R":
				trackingManifestTO.setReceiveStatus("Received");
				break;
			case "E":
				trackingManifestTO.setReceiveStatus("Excess");
				break;
			default:
				trackingManifestTO.setReceiveStatus("Not Excess");
				break;
			}
		}
		if(!StringUtil.isEmptyInteger(manifestDO.getOperatingOffice())){
			OfficeTO officeTO = trackingUniversalService.getOfficeByOfficeId(manifestDO.getOperatingOffice());
			String offcName = officeTO.getOfficeCode() + "-" + officeTO.getOfficeName();
			trackingManifestTO.setOperatingOff(offcName);
		}
		trackingManifestTO.setManifestBaseTO(manifestTO);
	}
	private List<ProcessMapTO> setProcessMapTO(List<ProcessMapDO> processMapDOs)
			throws CGSystemException, NumberFormatException,
			CGBusinessException {
		LOGGER.trace("ManifestTrackingServiceImpl::setProcessMapTO()::START");
		ProcessMapTO processMapTO = null;
		List<ProcessMapTO> processTOs = new ArrayList<>();
		Map<String, ProcessDO> codePathMap = getAllPath();

		for (ProcessMapDO processMapDO : processMapDOs) {

			processMapTO = new ProcessMapTO();
			String pcode = processMapDO.getProcessNumber();
			ProcessDO processDO = codePathMap.get(processMapDO
					.getProcessNumber());
			String path = trackingUniversalService.formatArtifactPath(
					processDO.getTrackingTxtMnf(), processMapDO);
			// RTH/RTO Manifest Type should be specific
			// Key12 - contains MANIFEST_TYPE in SP for manifest queries
			String manifestType = null;
			if (pcode.equalsIgnoreCase(CommonConstants.PROCESS_RTO_RTH) 
					&& StringUtils.equalsIgnoreCase(processMapDO.getKey12(),"manifestType")) {	
				if(StringUtils.equalsIgnoreCase(processMapDO.getValue12(), CommonConstants.MANIFEST_TYPE_RTH)){
					manifestType = "RTH Manifest";		
				}else if(StringUtils.equalsIgnoreCase(processMapDO.getValue12(), CommonConstants.MANIFEST_TYPE_RTO)){
					manifestType = "RTO Manifest";			
				}
			}else{
				manifestType = processDO.getProcessName();
			}
			processMapTO.setManifestType(manifestType);
			processMapTO.setConsignmentPath(path);
//			processMapTO.setOperatingLevel(processMapDO.getOpreratingLevel());
			processMapTO.setProcessOrder(processMapDO.getProcessOrder());
			if (processMapDO.getProcessDate() != null) {
				processMapTO.setDate(DateUtil
						.parseStringDateToDDMMYYYYHHMMFormat(DateUtil
								.getDateInDDMMYYYYHHMMSlashFormat(processMapDO
										.getProcessDate())));
				processMapTO.setDateAndTime(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(processMapDO
								.getProcessDate()));
			}
			if (pcode
					.equalsIgnoreCase(CommonConstants.PROCESS_DELIVERY_RUN_SHEET)) {
				processTOs.add(processMapTO);
				String template = UniversalTrackingConstants.PENDING_CN_PATH;
				processMapTO = addDelivery(template, processMapDO, processDO);
			}
			processTOs.add(processMapTO);
		}
		LOGGER.trace("ManifestTrackingServiceImpl::setProcessMapTO()::END");
		return processTOs;
	}

	private Map<String, ProcessDO> getAllPath() throws CGSystemException {
		LOGGER.trace("ManifestTrackingServiceImpl::getAllPath()::START");
		Map<String, ProcessDO> processMapPath = new HashMap<>();
		List<ProcessDO> processDOs = manifestTrackingDAO.getProcessDetails();
		for (ProcessDO processDO : processDOs) {
			processMapPath.put(processDO.getProcessCode(), processDO);
		}
		LOGGER.trace("ManifestTrackingServiceImpl::getAllPath()::END");
		return processMapPath;
	}

	private ProcessMapTO addDelivery(String template,
			ProcessMapDO processMapDO, ProcessDO processDO)
			throws NumberFormatException, CGBusinessException,
			CGSystemException {
		LOGGER.trace("ManifestTrackingServiceImpl::addDelivery()::START");
		ProcessMapTO processMapTO1 = new ProcessMapTO();
		String path = trackingUniversalService.formatArtifactPath(template,
				processMapDO, "");
		processMapTO1.setManifestType(processDO.getProcessName());
		processMapTO1.setConsignmentPath(path);
//		processMapTO1.setOperatingLevel(processMapDO.getOpreratingLevel());
		processMapTO1.setProcessOrder(processMapDO.getProcessOrder());
		if (processMapDO.getProcessDate() != null) {
			processMapTO1.setDate(processMapDO.getProcessDate());
		}
		LOGGER.trace("ManifestTrackingServiceImpl::addDelivery()::END");
		return processMapTO1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockStandardTypeTO> getTypeName() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ManifestTrackingServiceImpl::getTypeName()::START");
		List<StockStandardTypeTO> stockTpeList = null;
		List<StockStandardTypeDO> stockTpeDOList = manifestTrackingDAO
				.getTypeName();
		if(StringUtil.isEmptyList(stockTpeDOList)){
			throw new CGBusinessException(AdminErrorConstants.NO_STOCK_TYPE);
		}
		stockTpeList = (List<StockStandardTypeTO>) CGObjectConverter
				.createTOListFromDomainList(stockTpeDOList,
						StockStandardTypeTO.class);
		LOGGER.trace("ManifestTrackingServiceImpl::getTypeName()::END");
		return stockTpeList;
	}
	
}
