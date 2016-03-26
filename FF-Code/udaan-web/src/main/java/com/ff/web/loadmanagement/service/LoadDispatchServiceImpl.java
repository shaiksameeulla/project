package com.ff.web.loadmanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.email.EmailSenderUtil;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.loadmanagement.LoadConnectedDO;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.routeserviced.TripServicedByDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.domain.transport.TransportModeDO;
import com.ff.domain.transport.VehicleDO;
import com.ff.geography.CityTO;
import com.ff.loadmanagement.LoadDispatchDetailsTO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.routeserviced.TripServicedByTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.dao.LoadManagementDAO;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.service.OutManifestCommonService;


/**
 * The Class LoadDispatchServiceImpl.
 *
 * @author narmdr
 */
public class LoadDispatchServiceImpl implements LoadDispatchService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LoadDispatchServiceImpl.class);
	
	/** The load management common service. */
	private LoadManagementCommonService loadManagementCommonService;
	
	/** The load management dao. */
	private LoadManagementDAO loadManagementDAO;
	
	/** The sequence generator service. */
	private SequenceGeneratorService sequenceGeneratorService;
	
	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;
		
	/** The email sender util. */
	private transient EmailSenderUtil emailSenderUtil;
	
	private OrganizationCommonService organizationCommonService;
	
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
	 * Sets the sequence generator service.
	 *
	 * @param sequenceGeneratorService the new sequence generator service
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}	
	
	/**
	 * Sets the out manifest common service.
	 *
	 * @param outManifestCommonService the new out manifest common service
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}
	
	/**
	 * @param emailSenderUtil the emailSenderUtil to set
	 */
	public void setEmailSenderUtil(EmailSenderUtil emailSenderUtil) {
		this.emailSenderUtil = emailSenderUtil;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getAllTransportModeList()
	 */
	@Override
	public List<LabelValueBean> getAllTransportModeList()
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getAllTransportModeList();
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getOfficeTypeListForDispatch()
	 */
	@Override
	public List<LabelValueBean> getOfficeTypeListForDispatch()
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getOfficeTypeListForDispatch();
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getOfficeByOfficeId(java.lang.Integer)
	 */
	@Override
	public OfficeTO getOfficeByOfficeId(final Integer officeId)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getOfficeByOfficeId(officeId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getVehicleNoListByOfficeId(java.lang.Integer)
	 */
	@Override
	public List<LabelValueBean> getVehicleNoListByOfficeId(final Integer officeId)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getVehicleNoListByOfficeId(officeId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getRouteIdByOriginCityIdAndDestCityId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer getRouteIdByOriginCityIdAndDestCityId(final Integer originCityId,
			final Integer destCityId) throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getRouteIdByOriginCityIdAndDestCityId(
				originCityId, destCityId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getServiceByTypeListByTransportModeId(java.lang.Integer)
	 */
	@Override
	public List<LabelValueBean> getServiceByTypeListByTransportModeId(
			final Integer transportModeId) throws CGBusinessException,
			CGSystemException {
		return loadManagementCommonService.getServiceByTypeListByTransportModeId(transportModeId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TripServicedByTO> getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
			final Integer routeId, final Integer transportModeId, final Integer serviceByTypeId)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.
				getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId(
				routeId, transportModeId, serviceByTypeId);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#saveOrUpdateLoadDispatch(com.ff.loadmanagement.LoadMovementTO)
	 */
	@Override
	public LoadMovementTO saveOrUpdateLoadDispatch(final LoadMovementTO loadMovementTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::saveOrUpdateLoadDispatch::START------------>:::::::");
		LoadMovementTO loadMovementTO2 = null;
		LoadMovementDO loadMovementDO = new LoadMovementDO();
		setProcessToLoadMovement(loadMovementTO, loadMovementDO);
		loadMovementDomainConverter(loadMovementTO, loadMovementDO);
		
		//Manifest Weight update is not required after discussion even greater than 10% tolerance
		//loadManagementCommonService.validateWeightTolerance(loadMovementTO);
		
		loadMovementDO = loadManagementDAO.saveOrUpdateLoadMovement(loadMovementDO);

		/*TODO Commented delete operation for offload instead of this Hibernate will take care
		(Hibernate will put NULL ref instead of delete row)*/
		/*if(StringUtils.isNotBlank(loadMovementTO.getOffLoadIds())){
			deleteLoadConnectedDOsByLoadConnectedIdList(loadMovementTO.getOffLoadIds());
		}*/
		
		validateSendMailAndSMS(loadMovementTO);
		loadMovementTO2 = new LoadMovementTO();
		loadMovementTO2.setGatePassNumber(loadMovementDO.getGatePassNumber());

		//Commented Because ProcessMap is no where using
		//LoadManagementUtils.prepareAndSaveProcessMap(loadMovementTO);
		
		//setting id for TwoWayWrite
		LoadManagementUtils.setLoadMovementId(loadMovementTO2, loadMovementDO);
		
		LOGGER.trace("LoadDispatchServiceImpl::saveOrUpdateLoadDispatch::END------------>:::::::");
		return loadMovementTO2;
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
		LOGGER.trace("LoadDispatchServiceImpl::setProcessToLoadMovement::START------------>:::::::");
		Integer processId = null;
		if(!StringUtil.isEmptyInteger(loadManagementTO.getProcessId())){
			processId = loadManagementTO.getProcessId();
		}else{
			ProcessTO processTO = new ProcessTO();
			processTO.setProcessCode(CommonConstants.PROCESS_DISPATCH);
			processTO = loadManagementCommonService.getProcess(processTO);
			processId = processTO.getProcessId();
			loadManagementTO.setProcessId(processId);
		}
		
		if(!StringUtil.isEmptyInteger(processId)){
			ProcessDO processDO = new ProcessDO(); 
			processDO.setProcessId(processId);
			loadMovementDO.setProcessDO(processDO);
		}
		LOGGER.trace("LoadDispatchServiceImpl::setProcessToLoadMovement::END------------>:::::::");
	}

	/**
	 * Validate send mail and sms.
	 *
	 * @param loadMovementTO the load movement to
	 */
	private void validateSendMailAndSMS(final LoadMovementTO loadMovementTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::validateSendMailAndSMS::START------------>:::::::");

		Map<Object, Object> mailTemplateVariables = new HashMap<Object, Object>();

		final int length = loadMovementTO.getLoadNumber().length;
		String fromEmail = FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID;// "test_user1@testfirstflight.com";
		String toEmail = "test_user2@testfirstflight.com";
		List<LoadDispatchDetailsTO> loadDispatchDtlsTos = new ArrayList<>();

		if (!StringUtil.isEmptyInteger(loadMovementTO.getDestOfficeId())) {
			Integer officeId = loadMovementTO.getDestOfficeId();
			OfficeTO officeTO = organizationCommonService
					.getOfficeDetails(officeId);
			loadMovementTO.setDestOfficeTO(officeTO);
			if (StringUtils.isNotBlank(officeTO.getEmail())) {
				toEmail = officeTO.getEmail();
			}
		}

		if (loadMovementTO.getLoggedInOfficeTO() != null
				&& StringUtils.isNotBlank(loadMovementTO.getLoggedInOfficeTO()
						.getEmail())) {
			// fromEmail = loadMovementTO.getLoggedInOfficeTO().getEmail();
		}

		boolean isMailSend = Boolean.FALSE;
		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(loadMovementTO.getLoadNumber()[i])) {
				continue;
			}
			if (loadMovementTO.getSendMail()[i].equals(CommonConstants.YES)) {
				// send Mail
				// send SMS
				isMailSend = Boolean.TRUE;
				LoadDispatchDetailsTO loadDispatchDetailsTO = getLoadDispatchdetailsTO(
						loadMovementTO, i);
				loadDispatchDtlsTos.add(loadDispatchDetailsTO);

			}
		}
		if (isMailSend) {
			loadMovementTO.setLoadDispatchDetailsTOs(loadDispatchDtlsTos);
			mailTemplateVariables.put("loadMovementTO", loadMovementTO);

			MailSenderTO mailSenderTO = new MailSenderTO();
			mailSenderTO.setFrom(fromEmail);
			mailSenderTO.setTo(new String[] { toEmail,
					"test_user2@testfirstflight.com" });
			mailSenderTO.setMailSubject("Dispatch GatePass Number #"
					+ loadMovementTO.getGatePassNumber());
			mailSenderTO.setTemplateName("loadDispatchMail.vm");
			mailSenderTO.setTemplateVariables(mailTemplateVariables);

			emailSenderUtil.sendEmail(mailSenderTO);
			loadManagementCommonService.sendSMS(loadMovementTO);

			/*emailSenderService.sendEmailByTemplate(
					fromEmail,
					toEmail,
					"Dispatch GatePass Number #"
							+ loadMovementTO.getGatePassNumber(), mailTemplateVariables,
					"loadDispatchMail.vm");*/
		}
		
		LOGGER.trace("LoadDispatchServiceImpl::validateSendMailAndSMS::END------------>:::::::");
	}

	/**
	 * Gets the load dispatchdetails to.
	 *
	 * @param loadMovementTO the load movement to
	 * @param rowNo the row no
	 * @return the load dispatchdetails to
	 */
	private LoadDispatchDetailsTO getLoadDispatchdetailsTO(
			final LoadMovementTO loadMovementTO, int rowNo) {
		LoadDispatchDetailsTO loadDispatchDetailsTO = new LoadDispatchDetailsTO();
		loadDispatchDetailsTO.setLoadNumber(loadMovementTO
				.getLoadNumber()[rowNo]);
		String destCity = "";
		if (!StringUtils.isEmpty(loadMovementTO
				.getManifestDestCityDetails()[rowNo])) {
			destCity = loadMovementTO.getManifestDestCityDetails()[rowNo]
					.split("~")[2];
			loadDispatchDetailsTO.setManifestDestCity(destCity);
		}

		String docType = "";
		if (!StringUtils.isEmpty(loadMovementTO.getDocType()[rowNo])) {
			docType = loadMovementTO.getDocType()[rowNo].split("~")[2];
		}
		loadDispatchDetailsTO.setDocType(docType);
		loadDispatchDetailsTO.setWeight(loadMovementTO.getWeight()[rowNo]);
		loadDispatchDetailsTO
				.setCdWeight(loadMovementTO.getCdWeight()[rowNo]);
		loadDispatchDetailsTO.setLockNumber(loadMovementTO
				.getLockNumber()[rowNo]);
		loadDispatchDetailsTO.setTokenNumber(loadMovementTO
				.getTokenNumber()[rowNo]);
		loadDispatchDetailsTO
				.setRemarks(loadMovementTO.getRemarks()[rowNo]);
		return loadDispatchDetailsTO;
	}

	/**
	 * Delete load connected DOs by load connected id list.
	 *
	 * @param offLoadIds the off load ids
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void deleteLoadConnectedDOsByLoadConnectedIdList(final String offLoadIds)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::deleteLoadConnectedDOsByLoadConnectedIdList::START------------>:::::::");
		List<Integer> loadConnectedIdList = new ArrayList<Integer>();
		String loadConnectedIds[] = offLoadIds.split(CommonConstants.TILD);
		for (String loadConnectedId : loadConnectedIds) {
			loadConnectedIdList.add(Integer.valueOf(loadConnectedId));
		}
		if(loadConnectedIdList.size()>0){
			loadManagementDAO.deleteLoadConnectedDOsByLoadConnectedIdList(loadConnectedIdList);			
		}
		LOGGER.trace("LoadDispatchServiceImpl::deleteLoadConnectedDOsByLoadConnectedIdList::END------------>:::::::");
	}	
	
	/**
	 * Load movement domain converter.
	 *
	 * @param loadMovementTO the load movement to
	 * @param loadMovementDO the load movement do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void loadMovementDomainConverter(final LoadMovementTO loadMovementTO,
			LoadMovementDO loadMovementDO) throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::loadMovementDomainConverter::START------------>:::::::");
		
		if(!StringUtil.isEmptyInteger(loadMovementTO.getLoadMovementId())){
			loadMovementDO.setGatePassNumber(loadMovementTO.getGatePassNumber());
			loadMovementDO.setLoadMovementId(loadMovementTO.getLoadMovementId());
			LoadManagementUtils.setUpdateFlag4DBSync(loadMovementDO);//setting dbsync flag
		}else{
			String officeCode = "";
			if(StringUtils.isNotBlank(loadMovementTO.getOriginOffice())){
				officeCode = loadMovementTO.getOriginOffice().split(CommonConstants.HYPHEN)[0];
			}
			loadMovementDO.setGatePassNumber(generateGatePassNumber(officeCode));
			LoadManagementUtils.setSaveFlag4DBSync(loadMovementDO);//setting dbsync flag
			//LoadManagementUtils.generateAndSetProcessNumber(loadMovementTO);
		}
		
		if(loadMovementTO.getTripServicedById()!=null && loadMovementTO.getTripServicedById()!=0){
			TripServicedByDO tripServicedByDO = new TripServicedByDO();
			tripServicedByDO.setTripServicedById(loadMovementTO.getTripServicedById());
			loadMovementDO.setTripServicedByDO(tripServicedByDO);
		}
		//loadMovementDO.setProcessNumber(loadMovementTO.getProcessNumber());
		
		OfficeDO originOfficeDO = new OfficeDO();
		originOfficeDO.setOfficeId(loadMovementTO.getOriginOfficeId());		
		loadMovementDO.setOriginOfficeDO(originOfficeDO);
		
		loadMovementDO.setOperatingOffice(loadMovementTO.getOriginOfficeId());
		
		OfficeDO destOfficeDO = new OfficeDO();
		destOfficeDO.setOfficeId(Integer.valueOf(
				loadMovementTO.getDestOffice().split(CommonConstants.TILD)[0]));
		loadMovementTO.setDestOfficeId(Integer.valueOf(
				loadMovementTO.getDestOffice().split(CommonConstants.TILD)[0]));
		loadMovementDO.setDestOfficeDO(destOfficeDO);
		
		if(loadMovementTO.getTransportMode().split(CommonConstants.TILD)[1].equals
				(LoadManagementConstants.ROAD_CODE)){
			loadMovementDO.setDriverName(loadMovementTO.getDriverName());			
		}
		
		if(StringUtils.isNotBlank(loadMovementTO.getTransportNumber())){
			if(loadMovementTO.getTransportNumber().equals(LoadManagementConstants.OTHERS_CODE)){
				loadMovementDO.setRouteServicedTransportType(LoadManagementConstants.OTHERS_CODE);
				loadMovementDO.setRouteServicedTransportNumber(loadMovementTO.getOtherTransportNumber());
			}else{
				loadMovementDO.setRouteServicedTransportType(LoadManagementConstants.MASTER_CODE);
			}
		}else if(loadMovementTO.getTransportMode().split(CommonConstants.TILD)[1].equals(LoadManagementConstants.ROAD_CODE)){//Road Type Only
			if(StringUtils.isNotBlank(loadMovementTO.getVehicleNumber()) 
					&& loadMovementTO.getVehicleNumber().equals(
					LoadManagementConstants.OTHERS_VEHICLE_CODE)){
				loadMovementDO.setVehicleRegNumber(loadMovementTO.getOtherVehicleNumber());
				loadMovementDO.setVehicleType(LoadManagementConstants.OTHERS_VEHICLE_CODE);
			}else{
				VehicleDO vehicleDO = new VehicleDO();
				vehicleDO.setVehicleId(Integer.valueOf(loadMovementTO.getVehicleNumber().split(CommonConstants.TILD)[0]));
				loadMovementDO.setVehicleDO(vehicleDO);
				loadMovementDO.setVehicleType(LoadManagementConstants.MASTER_VEHICLE_CODE);
			}			
		}
		 
		loadMovementDO.setMovementDirection(LoadManagementConstants.DISPATCH_DIRECTION);
		loadMovementDO.setLoadingDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(
				loadMovementTO.getDispatchDateTime()));
		loadMovementDO.setLoadingTime(DateUtil.getCurrentTime());		

		TransportModeDO transportModeDO = new TransportModeDO();
		transportModeDO.setTransportModeId(Integer.valueOf
				(loadMovementTO.getTransportMode().split(CommonConstants.TILD)[0]));
		loadMovementDO.setTransportModeDO(transportModeDO);
		
		Set<LoadConnectedDO> loadConnectedDOList = new LinkedHashSet<LoadConnectedDO>();
		final int length = loadMovementTO.getLoadNumber().length;
		for(int i=0; i<length;i++){
			if(StringUtils.isBlank(loadMovementTO.getLoadNumber()[i])){
				continue;
			}
			LoadConnectedDO loadConnectedDO = new LoadConnectedDO();
			
			//BPL/MBPL No. Start
			ManifestDO manifestDO = new ManifestDO();//TODO Check Manifest instance
			if(loadMovementTO.getManifestId()[i]!=null && loadMovementTO.getManifestId()[i]!=0){
				manifestDO.setManifestId(loadMovementTO.getManifestId()[i]);	
			}else{
				//save new manifest & set manifestId in manifestDO
				List<ManifestTO> manifestTOList = new ArrayList<ManifestTO>();
				ManifestTO manifestTO = new ManifestTO();
				CityTO destinationCityTO = null;
				OfficeTO originOfficeTO = new OfficeTO();
				ConsignmentTypeTO consignmentTypeTO = null;
				
				if(StringUtils.isNotBlank(loadMovementTO.getManifestDestCityDetails()[i])){
					destinationCityTO = new CityTO();
					destinationCityTO.setCityId(Integer.valueOf(
							loadMovementTO.getManifestDestCityDetails()[i].split
							(CommonConstants.TILD)[0]));					
				}
				
				if(StringUtils.isNotBlank(loadMovementTO.getDocType()[i])){
					consignmentTypeTO = new ConsignmentTypeTO();
					consignmentTypeTO.setConsignmentId(Integer.valueOf
							(loadMovementTO.getDocType()[i].split(CommonConstants.TILD)[0]));
					manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				}

				manifestTO.setManifestNumber(loadMovementTO.getLoadNumber()[i]);
				manifestTO.setBagLockNo(loadMovementTO.getLockNumber()[i]);
				manifestTO.setManifestWeight(loadMovementTO.getWeight()[i]);
				manifestTO.setDestinationCityTO(destinationCityTO);
				manifestTO.setOriginOfficeTO(originOfficeTO);
				manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				manifestTO.setManifestType(LoadManagementConstants.MANIFEST_TYPE_OUT);
				//updating process dispatch
				manifestTO.setProcessId(loadMovementTO.getProcessId());
				manifestTO.setUserId(loadConnectedDO.getUpdatedBy());
				
				//added operating level & office
				/*Integer operatingLevel = loadManagementCommonService.calcAndGetOperatingLevel(loadMovementTO, manifestTO);
				loadManagementCommonService.setOperatingLevelAndOfficeToManifest(operatingLevel, manifestTO);*/
				LoadManagementUtils.setOperatingOfficeToManifest(manifestTO);
				
				if(!StringUtil.isEmptyInteger(loadMovementTO.getManifestDestOffId()[i])){
					OfficeTO destinationOfficeTO = new OfficeTO();
					destinationOfficeTO.setOfficeId(loadMovementTO.getManifestDestOffId()[i]);
					manifestTO.setDestinationOfficeTO(destinationOfficeTO);
				}

				if(!StringUtil.isEmptyInteger(loadMovementTO.getManifestOriginOffId()[i])){
					originOfficeTO.setOfficeId(loadMovementTO.getManifestOriginOffId()[i]);
				} else {
					originOfficeTO.setOfficeId(originOfficeDO.getOfficeId());
				}

				manifestTO.setLoginOfficeId(originOfficeDO.getOfficeId());
				manifestTOList.add(manifestTO);
				
				manifestTOList = outManifestCommonService.saveOrUpdateDispatchDtls(manifestTOList);
				if(!StringUtil.isEmptyList(manifestTOList)){
					manifestDO.setManifestId(manifestTOList.get(0).getManifestId());
				}
			}//BPL/MBPL No. End
					
			loadConnectedDO.setManifestDO(manifestDO);
			loadConnectedDO.setDispatchWeight(loadMovementTO.getWeight()[i]);
			loadConnectedDO.setConnectWeight(loadMovementTO.getCdWeight()[i]);
			//loadConnectedDO.setLockNumber(loadMovementTO.getLockNumber()[i]);
			loadConnectedDO.setRemarks(loadMovementTO.getRemarks()[i]);
			loadConnectedDO.setTokenNumber(loadMovementTO.getTokenNumber()[i]);
			
			if(loadMovementTO.getLoadConnectedId()[i]!=null && loadMovementTO.getLoadConnectedId()[i]!=0){
				loadConnectedDO.setLoadConnectedId(loadMovementTO.getLoadConnectedId()[i]);
			}
			//InManifestUtils.setCreatedByUpdatedBy(loadConnectedDO);
			loadConnectedDOList.add(loadConnectedDO);
		}
		InManifestUtils.setCreatedByUpdatedBy(loadMovementDO);
		loadMovementDO.setLoadConnectedDOs(loadConnectedDOList);

		LOGGER.trace("LoadDispatchServiceImpl::loadMovementDomainConverter::END------------>:::::::");
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getConsignmentTypeTOList()
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypeTOList()
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getConsignmentTypeTOList();
	}
	
	/**
	 * Generate gate pass number.
	 *
	 * @param officeCode the office code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private String generateGatePassNumber(final String officeCode) 
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::generateGatePassNumber::START------------>:::::::");
		String gatePassNumber = null;
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(LoadManagementConstants.GENERATE_LOAD_DISPATCH_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();
		
		if(sequenceGeneratorConfigTO.getGeneratedSequences()!=null && 
				sequenceGeneratorConfigTO.getGeneratedSequences().size()>0){
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		}
		
		gatePassNumber = LoadManagementConstants.GATE_PASS_NUMBER_START_CODE + officeCode + runningNumber;
		
		LOGGER.trace("LoadDispatchServiceImpl::generateGatePassNumber::END------------>:::::::");
		return gatePassNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getLoadMovementTOByGatePassNumber(java.lang.String)
	 */
	@Override
	public LoadMovementTO getLoadMovementTOByGatePassNumber(
			final String gatePassNumber) throws CGBusinessException,
			CGSystemException {
		return loadManagementCommonService.getLoadMovementTOByGatePassNumber(gatePassNumber);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getDestinationOffices(com.ff.organization.OfficeTO)
	 */
	@Override
	public List<OfficeTO> getDestinationOffices(final OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.getDestinationOffices(officeTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getCity(com.ff.geography.CityTO)
	 */
	@Override
	public CityTO getCity(final CityTO cityTO) throws CGBusinessException,
			CGSystemException {
		return loadManagementCommonService.getCity(cityTO);
	}
	
	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#validateTransshipmentRoute(com.ff.routeserviced.TransshipmentRouteTO)
	 */
	@Override
	public boolean validateTransshipmentRoute(
			final TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LoadDispatchServiceImpl::validateTransshipmentRoute::START------------>:::::::");
		TransshipmentRouteTO transshipmentRouteTO1 = loadManagementCommonService.
				getTransshipmentRoute(transshipmentRouteTO);
		if(transshipmentRouteTO1==null){
			return false;
		}
		LOGGER.trace("LoadDispatchServiceImpl::validateTransshipmentRoute::END------------>:::::::");
		return true;
	}

    /**
     * validate Manifest Number or BPL/MBPL Number
     * <p>
     * <ul>
     * <li>Check Manifest dispatched or not.
     * <li>If not Check manifestNumber exist in Manifest.
     * <li>If manifestNumber not exist then return ManifestTO with isNewManifest(true) field.
     * <li>If manifestNumber exist then check issued to branch(loggedInOffice) or not.
     * <li>If yes Check pure route first by Manifest destination city & dispatch destination city.
     * <li>If not check Transshipment Route by dispatch destination city and Manifest destination city.
     * <li>
     * </ul>
     * <p>
     *
     * @param manifestTO the manifest to
     * @return ManifestTO :: If all the validations are passed then return ManifestTO will get filled with the followings:
     * <ul>
     * <li>manifestId
     * <li>manifestNumber
     * <li>bagLockNo
     * <li>manifestWeight
     * <li>consignmentTypeTO
     * <li>originOfficeTO
     * <li>destinationOfficeTO
     * <li>destinationCityTO
     * </ul>
     * else return CGBusinessException.
     * @throws CGBusinessException the cG business exception
     * @throws CGSystemException the cG system exception
     * @author R Narmdeshwar
     */
	@Override
	public ManifestTO validateManifestNumber(final ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException {
		return loadManagementCommonService.validateManifestNumber4Dispatch(manifestTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.loadmanagement.service.LoadDispatchService#getLoadMovementTOByGatePassNumber4Print(java.lang.String)
	 * 
	 * @author Shankar Reddy
	 */
	@Override
	public LoadMovementTO getLoadMovementTOByGatePassNumber4Print(
			final String gatePassNumber) throws CGBusinessException,
			CGSystemException {		
		return loadManagementCommonService.getLoadMovementTOByGatePassNumber4Print(gatePassNumber);
	}

	@Override
	public List<TripServicedByTO> getTripServicedByTOsForTransport(
			final TripServicedByTO tripServicedByTO) throws CGBusinessException,
			CGSystemException {
		return loadManagementCommonService.getTripServicedByTOsForTransport(tripServicedByTO);
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

}
