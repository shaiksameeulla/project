package com.ff.web.loadmanagement.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
import com.ff.domain.transport.VehicleDO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.LoadManagementValidationTO;
import com.ff.loadmanagement.LoadReceiveLocalTO;
import com.ff.loadmanagement.LoadReceiveManifestValidationTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.transport.TransportModeTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.dao.LoadManagementDAO;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;

/**
 * The Class LoadReceiveLocalServiceImpl.
 *
 * @author narmdr
 */
public class LoadReceiveLocalServiceImpl implements LoadReceiveLocalService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadReceiveLocalServiceImpl.class);
	
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
	 * @see com.ff.web.loadmanagement.service.LoadReceiveLocalService#getTransportMode(com.ff.transport.TransportModeTO)
	 */
	@Override
	public TransportModeTO getTransportMode(TransportModeTO transportModeTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getTransportMode(transportModeTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveLocalService#getLoadReceiveLocalTO(com.ff.loadmanagement.LoadManagementValidationTO)
	 */
	@Override
	public LoadReceiveLocalTO getLoadReceiveLocalTO(
			LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getLoadReceiveLocalTO(loadManagementValidationTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveLocalService#saveOrUpdateLoadReceiveLocal(com.ff.loadmanagement.LoadReceiveLocalTO)
	 */
	@Override
	public LoadReceiveLocalTO saveOrUpdateLoadReceiveLocal(
			final LoadReceiveLocalTO loadReceiveLocalTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LoadReceiveLocalServiceImpl::saveOrUpdateLoadReceiveLocal::START------------>:::::::");
		LoadReceiveLocalTO loadReceiveLocalTO2 = null;
		LoadMovementDO loadMovementDO = new LoadMovementDO();
		loadMovementDomainConverterForLoadReceiveLocal(loadReceiveLocalTO, loadMovementDO);
		
		//get Not Received LoadConnected by LoadMovementId
		/*TODO Commented delete operation for offload instead of this Hibernate will take care
		(Hibernate will put NULL ref instead of delete row)*/
		//deleteNotReceivedLoadConnceted(loadReceiveLocalTO);
		
		//Check Partial/Complete Receive & not received in grid as well
		validateCompleteOrPartialReceive(loadMovementDO, loadReceiveLocalTO);

		//Manifest Weight update is not required after discussion even greater than 10% tolerance
		//loadManagementCommonService.validateWeightTolerance(loadReceiveLocalTO);
		
		setProcessToLoadMovement(loadReceiveLocalTO, loadMovementDO);
		
		loadMovementDO = loadManagementDAO.saveOrUpdateLoadMovement(loadMovementDO);
		//loadManagementCommonService.validateWeightTolerance(loadReceiveLocalTO);	
		loadReceiveLocalTO2 = new LoadReceiveLocalTO();
		loadReceiveLocalTO2.setGatePassNumber(loadMovementDO.getGatePassNumber());
		loadReceiveLocalTO2.setIsReceiveSaved(true);

		//Commented Because ProcessMap is no where using
		//LoadManagementUtils.prepareAndSaveProcessMap(loadReceiveLocalTO);

		//setting id for TwoWayWrite
		LoadManagementUtils.setLoadMovementId(loadReceiveLocalTO2, loadMovementDO);

		LOGGER.trace("LoadReceiveLocalServiceImpl::saveOrUpdateLoadReceiveLocal::END------------>:::::::");
		return loadReceiveLocalTO2;
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
			final LoadMovementDO loadMovementDO) throws CGBusinessException,
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
	 * Validate complete or partial receive.
	 *
	 * @param loadMovementDO the load movement do
	 * @param loadReceiveLocalTO the load receive local to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void validateCompleteOrPartialReceive(
			final LoadMovementDO loadMovementDO, final LoadReceiveLocalTO loadReceiveLocalTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadReceiveLocalServiceImpl::validateCompleteOrPartialReceive::START------------>:::::::");
		if (StringUtil.isEmptyInteger(loadMovementDO.getReceivedAgainst())) {
			loadMovementDO
					.setReceivedStatus(LoadManagementConstants.PARTIAL_RECEIVE);
			return;
		}

		LoadReceiveValidationTO loadReceiveValidationTO = new LoadReceiveValidationTO();
		List<LoadConnectedDO> loadConnectedDOs = null;
		List<String> manifestNumberList = new ArrayList<String>();

		final int length = loadReceiveLocalTO.getLoadNumber().length;
		for(int i=0; i<length;i++){
			if(StringUtils.isBlank(loadReceiveLocalTO.getLoadNumber()[i])){
				continue;
			}
			manifestNumberList.add(loadReceiveLocalTO.getLoadNumber()[i]);
		}
		
		loadReceiveValidationTO.setReceivedAgainstId(loadMovementDO
				.getReceivedAgainst());
		loadReceiveValidationTO.setManifestNumberList(manifestNumberList);
		loadConnectedDOs = loadManagementDAO
				.getLoadConnectedFromDispatchAsNotReceived(loadReceiveValidationTO);
		
		if(!StringUtil.isEmptyColletion(loadConnectedDOs)){
			for (LoadConnectedDO loadConnectedDO1 : loadConnectedDOs) {
				LoadConnectedDO loadConnectedDO = new LoadConnectedDO();

				if(loadConnectedDO1.getManifestDO()!=null){
					ManifestDO manifestDO = new ManifestDO();
					manifestDO.setManifestId(loadConnectedDO1.getManifestDO().getManifestId());
					loadConnectedDO.setManifestDO(manifestDO);
					loadConnectedDO.setLockNumber(loadConnectedDO1.getManifestDO().getBagLockNo());
				}
				loadConnectedDO.setDispatchWeight(loadConnectedDO1.getDispatchWeight());
				loadConnectedDO.setReceivedStatus(LoadManagementConstants.NOT_RECEIVED_STATUS);
				loadMovementDO.getLoadConnectedDOs().add(loadConnectedDO);
			}
			loadMovementDO.setReceivedStatus(LoadManagementConstants.PARTIAL_RECEIVE);
		}else{
			loadMovementDO.setReceivedStatus(LoadManagementConstants.COMPLETE_RECEIVE);
		}

		LOGGER.trace("LoadReceiveLocalServiceImpl::validateCompleteOrPartialReceive::END------------>:::::::");
	}
	
	/**
	 * Load movement domain converter for load receive local.
	 *
	 * @param loadReceiveLocalTO the load receive local to
	 * @param loadMovementDO the load movement do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void loadMovementDomainConverterForLoadReceiveLocal(
			final LoadReceiveLocalTO loadReceiveLocalTO, final LoadMovementDO loadMovementDO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("LoadReceiveLocalServiceImpl::loadMovementDomainConverterForLoadReceiveLocal::START------------>:::::::");
		
		if(!StringUtil.isEmptyInteger(loadReceiveLocalTO.getLoadMovementId())){
			loadMovementDO.setLoadMovementId(loadReceiveLocalTO.getLoadMovementId());
			LoadManagementUtils.setUpdateFlag4DBSync(loadMovementDO);//setting dbsync flag
		}else{
			LoadManagementUtils.setSaveFlag4DBSync(loadMovementDO);//setting dbsync flag
			//LoadManagementUtils.generateAndSetProcessNumber(loadReceiveLocalTO);			
		}
		
		if(!StringUtil.isEmptyInteger(loadReceiveLocalTO.getReceivedAgainstId())){
			loadMovementDO.setReceivedAgainst(loadReceiveLocalTO.getReceivedAgainstId());
		}

		//loadMovementDO.setProcessNumber(loadReceiveLocalTO.getProcessNumber());
		loadMovementDO.setGatePassNumber(loadReceiveLocalTO.getGatePassNumber());
		loadMovementDO.setReceiveType(LoadManagementConstants.LOCAL_RECEIVE_TYPE);
		loadMovementDO.setMovementDirection(LoadManagementConstants.RECEIVE_DIRECTION);
		loadMovementDO.setLoadingDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				loadReceiveLocalTO.getReceiveDateTime()));
		
		//Actual receiving/arrival time should be captured automatically as per the server time
		if(StringUtils.isNotBlank(loadReceiveLocalTO.getActualArrival())){
			loadMovementDO.setReceivedAtTime(loadReceiveLocalTO.getActualArrival());			
		}else{
			loadMovementDO.setReceivedAtTime(DateUtil.getCurrentTime());
		}
		
		OfficeDO originOfficeDO = new OfficeDO();
		originOfficeDO.setOfficeId(Integer.valueOf(loadReceiveLocalTO
				.getOriginOffice().split(CommonConstants.TILD)[0]));
		loadReceiveLocalTO.setOriginOfficeId(Integer.valueOf(loadReceiveLocalTO
				.getOriginOffice().split(CommonConstants.TILD)[0]));
		loadMovementDO.setOriginOfficeDO(originOfficeDO);

		OfficeDO destOfficeDO = new OfficeDO();
		destOfficeDO.setOfficeId(loadReceiveLocalTO.getDestOfficeId());
		loadMovementDO.setDestOfficeDO(destOfficeDO);

		loadMovementDO.setOperatingOffice(loadReceiveLocalTO.getDestOfficeId());
		
		loadMovementDO.setDriverName(loadReceiveLocalTO.getDriverName());
		
		if(StringUtils.isNotBlank(loadReceiveLocalTO.getVehicleNumber()) 
				&& loadReceiveLocalTO.getVehicleNumber().equals(
				LoadManagementConstants.OTHERS_VEHICLE_CODE)){
			loadMovementDO.setVehicleRegNumber(loadReceiveLocalTO.getOtherVehicleNumber());
			loadMovementDO.setVehicleType(LoadManagementConstants.OTHERS_VEHICLE_CODE);
		}else{
			VehicleDO vehicleDO = new VehicleDO();
			vehicleDO.setVehicleId(Integer.valueOf(loadReceiveLocalTO.getVehicleNumber().split(CommonConstants.TILD)[0]));
			loadMovementDO.setVehicleDO(vehicleDO);
			loadMovementDO.setVehicleType(LoadManagementConstants.MASTER_VEHICLE_CODE);
		}	
		
		TransportModeDO transportModeDO = new TransportModeDO();
		transportModeDO.setTransportModeId(Integer.valueOf
				(loadReceiveLocalTO.getTransportModeDetails().split(CommonConstants.TILD)[0]));
		loadMovementDO.setTransportModeDO(transportModeDO);		

		Set<LoadConnectedDO> loadConnectedDOList = new LinkedHashSet<LoadConnectedDO>();
		final int length = loadReceiveLocalTO.getLoadNumber().length;
		for(int i=0; i<length; i++){
			if(StringUtils.isBlank(loadReceiveLocalTO.getLoadNumber()[i])){
				continue;
			}
			LoadConnectedDO loadConnectedDO = new LoadConnectedDO();
			//InManifestUtils.setCreatedByUpdatedBy(loadConnectedDO);
			
			//BPL/MBPL No. Start
			ManifestDO manifestDO = new ManifestDO();
			if(loadReceiveLocalTO.getManifestId()[i]!=null && loadReceiveLocalTO.getManifestId()[i]!=0){
				manifestDO.setManifestId(loadReceiveLocalTO.getManifestId()[i]);	
			}else{
				//save new manifest & set manifestId in manifestDO
				ManifestTO manifestTO = new ManifestTO();
				CityTO destinationCityTO = null;
				ConsignmentTypeTO consignmentTypeTO = null;
				
				if(StringUtils.isNotBlank(loadReceiveLocalTO.getManifestDestCityDetails()[i])){
					destinationCityTO = new CityTO();
					destinationCityTO.setCityId(Integer.valueOf(
							loadReceiveLocalTO.getManifestDestCityDetails()[i].split
							(CommonConstants.TILD)[0]));					
				}
				
				if(StringUtils.isNotBlank(loadReceiveLocalTO.getDocType()[i])){
					consignmentTypeTO = new ConsignmentTypeTO();
					consignmentTypeTO.setConsignmentId(Integer.valueOf
							(loadReceiveLocalTO.getDocType()[i].split(CommonConstants.TILD)[0]));
					consignmentTypeTO.setConsignmentCode(
							loadReceiveLocalTO.getDocType()[i].split(CommonConstants.TILD)[1]);
					manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				}

				manifestTO.setManifestNumber(loadReceiveLocalTO.getLoadNumber()[i]);
				manifestTO.setBagLockNo(loadReceiveLocalTO.getLockNumber()[i]);
				manifestTO.setManifestWeight(loadReceiveLocalTO.getWeight()[i]);
				manifestTO.setDestinationCityTO(destinationCityTO);
				manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				manifestTO.setManifestType(LoadManagementConstants.MANIFEST_TYPE_IN);

				//added operating level & office
				/*Integer operatingLevel = loadManagementCommonService.calcAndGetOperatingLevel(loadReceiveLocalTO, manifestTO);
				loadManagementCommonService.setOperatingLevelAndOfficeToManifest(operatingLevel, manifestTO);*/
				LoadManagementUtils.setOperatingOfficeToManifest(manifestTO);
				
				//added manifestOriginOffice and manifestDestOffice
				OfficeTO originOfficeTO = new OfficeTO();
				manifestTO.setOriginOfficeTO(originOfficeTO);
				if(!StringUtil.isEmptyInteger(loadReceiveLocalTO.getManifestOriginOffId()[i])){
					originOfficeTO.setOfficeId(loadReceiveLocalTO.getManifestOriginOffId()[i]);
				} else {
					//set receive origin office for offline mode
					originOfficeTO.setOfficeId(originOfficeDO.getOfficeId());
				}
				if(!StringUtil.isEmptyInteger(loadReceiveLocalTO.getManifestDestOffId()[i])){
					OfficeTO destinationOfficeTO = new OfficeTO();
					destinationOfficeTO.setOfficeId(loadReceiveLocalTO.getManifestDestOffId()[i]);
					manifestTO.setDestinationOfficeTO(destinationOfficeTO);
				}
				manifestTO.setLoginOfficeId(destOfficeDO.getOfficeId());
				manifestTO.setReceivedStatus(loadReceiveLocalTO.getReceivedStatus()[i]);
								
				//TODO need to save incoming manifest only
				inManifestCommonService.saveOrUpdateReceiveDtls(manifestTO);
				manifestDO.setManifestId(manifestTO.getManifestId());
			}//BPL/MBPL No. End
					
			loadConnectedDO.setManifestDO(manifestDO);
			loadConnectedDO.setDispatchWeight(loadReceiveLocalTO.getWeight()[i]);
			loadConnectedDO.setRemarks(loadReceiveLocalTO.getRemarks()[i]);
			loadConnectedDO.setLockNumber(loadReceiveLocalTO.getLockNumber()[i]);
			loadConnectedDO.setReceivedStatus(loadReceiveLocalTO.getReceivedStatus()[i]);

			if(!StringUtil.isEmptyInteger(loadReceiveLocalTO.getLoadConnectedId()[i])){
				loadConnectedDO.setLoadConnectedId(loadReceiveLocalTO.getLoadConnectedId()[i]);
			}
			loadConnectedDOList.add(loadConnectedDO);
		}
		InManifestUtils.setCreatedByUpdatedBy(loadMovementDO);
		loadMovementDO.setLoadConnectedDOs(loadConnectedDOList);

		LOGGER.trace("LoadReceiveLocalServiceImpl::loadMovementDomainConverterForLoadReceiveLocal::END------------>:::::::");
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveLocalService#getOriginOffices(com.ff.organization.OfficeTO)
	 */
	@Override
	public List<OfficeTO> getOriginOffices(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getOriginOffices(officeTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadReceiveLocalService#validateManifestNumber4ReceiveLocal(com.ff.loadmanagement.LoadReceiveManifestValidationTO)
	 */
	@Override
	public LoadReceiveManifestValidationTO validateManifestNumber4ReceiveLocal(
			final LoadReceiveManifestValidationTO loadReceiveManifestValidationTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.
				validateManifestNumber4ReceiveLocal(loadReceiveManifestValidationTO);
	}

	@Override
	public LoadReceiveLocalTO printLoadReceiveLocalTO(
			final LoadManagementValidationTO loadManagementValidationTO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		return loadManagementCommonService.printLoadReceiveLocalTO(loadManagementValidationTO);
	}

}
