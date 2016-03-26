package com.ff.web.loadmanagement.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveOutstationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.dao.LoadManagementDAO;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;

/**
 * The Class LoadReceiveOutstationServiceImpl.
 *
 * @author narmdr
 */
public class LoadReceiveOutstationServiceImpl implements LoadReceiveOutstationService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadReceiveOutstationServiceImpl.class);
	
	/** The load management common service. */
	private LoadManagementCommonService loadManagementCommonService;
	
	/** The load management dao. */
	private LoadManagementDAO loadManagementDAO;

	/** The in manifest common service. */
	private InManifestCommonService inManifestCommonService;
	
	/**
	 * Sets the load management common service.
	 *
	 * @param loadManagementCommonService the new load management common service
	 */
	public void setLoadManagementCommonService(
			LoadManagementCommonService loadManagementCommonService) {
		this.loadManagementCommonService = loadManagementCommonService;
	}
	
	/**
	 * Sets the load management dao.
	 *
	 * @param loadManagementDAO the new load management dao
	 */
	public void setLoadManagementDAO(LoadManagementDAO loadManagementDAO) {
		this.loadManagementDAO = loadManagementDAO;
	}	
	
	/**
	 * @param inManifestCommonService the inManifestCommonService to set
	 */
	public void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveOutstationService#isReceiveNumberExist(com.ff.loadmanagement.LoadReceiveValidationTO)
	 */
	@Override
	public boolean isReceiveNumberExist(
			final LoadReceiveValidationTO loadReceiveValidationTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService
				.isReceiveNumberExist(loadReceiveValidationTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveOutstationService#saveOrUpdateLoadReceiveOutstation(com.ff.loadmanagement.LoadReceiveOutstationTO)
	 */
	@Override
	public boolean saveOrUpdateLoadReceiveOutstation(
			final LoadReceiveOutstationTO loadReceiveOutstationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadReceiveOutstationServiceImpl::saveOrUpdateLoadReceiveOutstation::START------------>:::::::");
		LoadMovementDO loadMovementDO = new LoadMovementDO();
		loadMovementDomainConverterForLoadReceiveOutstation(loadReceiveOutstationTO, loadMovementDO);
		setProcessToLoadMovement(loadReceiveOutstationTO, loadMovementDO);

		loadMovementDO = loadManagementDAO.saveOrUpdateLoadMovement(loadMovementDO);

		//setting id for TwoWayWrite
		LoadManagementUtils.setLoadMovementId(loadReceiveOutstationTO, loadMovementDO);
		//Manifest Weight update is not required after discussion even greater than 10% tolerance
		//loadManagementCommonService.validateWeightTolerance(loadReceiveOutstationTO);

		//Commented Because ProcessMap is no where using
		//LoadManagementUtils.prepareAndSaveProcessMap(loadReceiveOutstationTO);

		//twoWayWrite();
		LOGGER.trace("LoadReceiveOutstationServiceImpl::saveOrUpdateLoadReceiveOutstation::END------------>:::::::");
		return true;
	}
	
	/**
	 * Sets the process to load movement.
	 *
	 * @param loadManagementTO the load management to
	 * @param loadMovementDO the load movement do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void setProcessToLoadMovement(final LoadManagementTO loadManagementTO,
			LoadMovementDO loadMovementDO) throws CGBusinessException,
			CGSystemException {
		Integer processId = null;
		if(!StringUtil.isEmptyInteger(loadManagementTO.getProcessId())){
			processId = loadManagementTO.getProcessId();
		}else{
			ProcessTO processTO = new ProcessTO();
			processTO.setProcessCode(CommonConstants.PROCESS_RECEIVE);
			processTO = loadManagementCommonService.getProcess(processTO);
			processId = processTO.getProcessId();
		}
		
		if(!StringUtil.isEmptyInteger(processId)){
			ProcessDO processDO = new ProcessDO(); 
			processDO.setProcessId(processId);
			loadMovementDO.setProcessDO(processDO);
		}
	}

	/**
	 * Load movement domain converter for load receive outstation.
	 *
	 * @param loadReceiveOutstationTO the load receive outstation to
	 * @param loadMovementDO the load movement do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void loadMovementDomainConverterForLoadReceiveOutstation(
			final LoadReceiveOutstationTO loadReceiveOutstationTO,
			LoadMovementDO loadMovementDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LoadReceiveOutstationServiceImpl::loadMovementDomainConverterForLoadReceiveOutstation::START------------>:::::::");

		
		if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getLoadMovementId())){
			loadMovementDO.setLoadMovementId(loadReceiveOutstationTO.getLoadMovementId());
			LoadManagementUtils.setUpdateFlag4DBSync(loadMovementDO);//setting dbsync flag
		}else{
			LoadManagementUtils.setSaveFlag4DBSync(loadMovementDO);//setting dbsync flag
			//LoadManagementUtils.generateAndSetProcessNumber(loadReceiveOutstationTO);
		}
		
		loadReceiveOutstationTO.setReceiveNumber(LoadManagementUtils
				.generateRunningLoadNumber(
						LoadManagementConstants.RECEIVE_NUMBER_START_CODE,
						loadReceiveOutstationTO.getLoggedInOfficeCode(),
						LoadManagementConstants.RECEIVE_NUMBER));

		//loadMovementDO.setProcessNumber(loadReceiveOutstationTO.getProcessNumber());
		loadMovementDO.setReceiveNumber(loadReceiveOutstationTO.getReceiveNumber());
		loadMovementDO.setReceiveType(LoadManagementConstants.OUTSTATION_RECEIVE_TYPE);
		loadMovementDO.setMovementDirection(LoadManagementConstants.RECEIVE_DIRECTION);
		loadMovementDO.setLoadingDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				loadReceiveOutstationTO.getReceiveDateTime()));				

		OfficeDO destOfficeDO = new OfficeDO();
		destOfficeDO.setOfficeId(loadReceiveOutstationTO.getDestOfficeId());
		loadMovementDO.setDestOfficeDO(destOfficeDO);

		loadMovementDO.setOperatingOffice(loadReceiveOutstationTO.getDestOfficeId());
		
		loadReceiveOutstationTO.setOriginOfficeId(loadReceiveOutstationTO.getDestOfficeId());
		
		Set<LoadConnectedDO> loadConnectedDOList = new HashSet<LoadConnectedDO>();
		final int length = loadReceiveOutstationTO.getLoadNumber().length;
		for(int i=0; i<length;i++){
			if(StringUtils.isBlank(loadReceiveOutstationTO.getLoadNumber()[i])){
				continue;
			}
			LoadConnectedDO loadConnectedDO = new LoadConnectedDO();
			//InManifestUtils.setCreatedByUpdatedBy(loadConnectedDO);
			
			//BPL/MBPL No. Start
			ManifestDO manifestDO = new ManifestDO();//TODO Check Manifest instance
			if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getManifestId()[i])){
				manifestDO.setManifestId(loadReceiveOutstationTO.getManifestId()[i]);	
			}else{
				//save new manifest & set manifestId in manifestDO
				ManifestTO manifestTO = new ManifestTO();
				CityTO destinationCityTO = null;
				
				if(StringUtils.isNotBlank(loadReceiveOutstationTO.getManifestDestCityDetails()[i])){
					destinationCityTO = new CityTO();
					destinationCityTO.setCityId(Integer.valueOf(
							loadReceiveOutstationTO.getManifestDestCityDetails()[i].split
							(CommonConstants.TILD)[0]));					
				}
				
				if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getConsgTypeId()[i])){
					ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
					consignmentTypeTO.setConsignmentId(loadReceiveOutstationTO.getConsgTypeId()[i]);
					consignmentTypeTO.setConsignmentCode(loadReceiveOutstationTO.getDocType()[i]);
					manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				}

				manifestTO.setManifestNumber(loadReceiveOutstationTO.getLoadNumber()[i]);
				manifestTO.setBagLockNo(loadReceiveOutstationTO.getLockNumber()[i]);
				manifestTO.setManifestWeight(loadReceiveOutstationTO.getWeight()[i]);
				manifestTO.setDestinationCityTO(destinationCityTO);
				manifestTO.setManifestType(LoadManagementConstants.MANIFEST_TYPE_IN);

				//added operating level & office
				/*Integer operatingLevel = loadManagementCommonService.calcAndGetOperatingLevel(loadReceiveOutstationTO, manifestTO);
				loadManagementCommonService.setOperatingLevelAndOfficeToManifest(operatingLevel, manifestTO);*/
				LoadManagementUtils.setOperatingOfficeToManifest(manifestTO);
				
				//added manifestOriginOffice and manifestDestOffice
				if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getManifestOriginOffId()[i])){
					OfficeTO originOfficeTO = new OfficeTO();
					originOfficeTO.setOfficeId(loadReceiveOutstationTO.getManifestOriginOffId()[i]);
					manifestTO.setOriginOfficeTO(originOfficeTO);
				}
				if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getManifestDestOffId()[i])){
					OfficeTO destinationOfficeTO = new OfficeTO();
					destinationOfficeTO.setOfficeId(loadReceiveOutstationTO.getManifestDestOffId()[i]);
					manifestTO.setDestinationOfficeTO(destinationOfficeTO);
				}
				manifestTO.setLoginOfficeId(destOfficeDO.getOfficeId());
				
				//TODO need to save incoming manifest only
				inManifestCommonService.saveOrUpdateReceiveDtls(manifestTO);
				manifestDO.setManifestId(manifestTO.getManifestId());
				
			}//BPL/MBPL No. End
					
			loadConnectedDO.setManifestDO(manifestDO);
			loadConnectedDO.setDispatchWeight(loadReceiveOutstationTO.getWeight()[i]);
			loadConnectedDO.setRemarks(loadReceiveOutstationTO.getRemarks()[i]);
			loadConnectedDO.setLockNumber(loadReceiveOutstationTO.getLockNumber()[i]);
			loadConnectedDO.setRecvTransportNumber(loadReceiveOutstationTO.getRecvTransportNumber()[i]);
			loadConnectedDO.setTokenNumber(loadReceiveOutstationTO.getTokenNumber()[i]);
			loadConnectedDO.setRecvVendorName(loadReceiveOutstationTO.getRecvVendor()[i]);
			loadConnectedDO.setRecvGatepassNumber(loadReceiveOutstationTO.getRecvGatePassNumber()[i]);
			if(StringUtils.isNotBlank(loadReceiveOutstationTO.getRecvTransportMode()[i])){
				TransportModeDO transportModeDO = new TransportModeDO();
				transportModeDO.setTransportModeId(Integer.valueOf(
						loadReceiveOutstationTO.getRecvTransportMode()[i]));
				loadConnectedDO.setRecvTransportModeDO(transportModeDO);				
			}
			if(!StringUtil.isEmptyInteger(loadReceiveOutstationTO.getLoadConnectedId()[i])){
				loadConnectedDO.setLoadConnectedId(loadReceiveOutstationTO.getLoadConnectedId()[i]);
			}
			loadConnectedDOList.add(loadConnectedDO);
		}
		InManifestUtils.setCreatedByUpdatedBy(loadMovementDO);
		loadMovementDO.setLoadConnectedDOs(loadConnectedDOList);

		LOGGER.trace("LoadReceiveOutstationServiceImpl::loadMovementDomainConverterForLoadReceiveOutstation::END------------>:::::::");
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveOutstationService#validateManifestNumber4ReceiveOutstation(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@Override
	public LoadReceiveManifestValidationTO validateManifestNumber4ReceiveOutstation(
			final LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService
				.validateManifestNumber4ReceiveOutstation(loadReceiveManifestValidationTO);
	}	
}
