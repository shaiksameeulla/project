package com.ff.web.loadmanagement.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.domain.loadmanagement.LoadMovementDO;
import com.ff.loadmanagement.LoadManagementTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.tracking.ProcessMapTO;
import com.ff.tracking.ProcessTO;
import com.ff.tracking.TrackingParameterTO;
import com.ff.universe.loadmanagement.service.LoadManagementCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.universe.util.UdaanContextService;

/**
 * The Class LoadManagementUtils.
 * 
 * @author narmdr
 */
public class LoadManagementUtils {

	/** The load management common service. */
	private static LoadManagementCommonService loadManagementCommonService;
	
	/** The sequence generator service. */
	private static SequenceGeneratorService sequenceGeneratorService;
	
	//private static ServiceOfferingCommonService serviceOfferingCommonService;

	/** The organization common service. */
	private static OrganizationCommonService organizationCommonService;

	/** The tracking universal service. */
	private static TrackingUniversalService trackingUniversalService;
	
	private static UdaanContextService udaanContextService;

	/**
	 * Sets the load management common service.
	 *
	 * @param loadManagementCommonService the loadManagementCommonService to set
	 */
	public static void setLoadManagementCommonService(
			LoadManagementCommonService loadManagementCommonService) {
		LoadManagementUtils.loadManagementCommonService = loadManagementCommonService;
	}

	/**
	 * Sets the sequence generator service.
	 *
	 * @param sequenceGeneratorService the sequenceGeneratorService to set
	 */
	public static void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		LoadManagementUtils.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * Sets the organization common service.
	 *
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public static void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		LoadManagementUtils.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the tracking universal service.
	 *
	 * @param trackingUniversalService the trackingUniversalService to set
	 */
	public static void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		LoadManagementUtils.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * @param udaanContextService the udaanContextService to set
	 */
	public static void setUdaanContextService(
			UdaanContextService udaanContextService) {
		LoadManagementUtils.udaanContextService = udaanContextService;
	}

	/**
	 * Sets the update flag4 db sync.
	 *
	 * @param cgBaseDO the new update flag4 db sync
	 */
	public static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	/**
	 * Sets the save flag4 db sync.
	 *
	 * @param cgBaseDO the new save flag4 db sync
	 */
	public static void setSaveFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		validateAndSetTwoWayWriteFlag(cgBaseDO);
	}
	
	public static void validateAndSetTwoWayWriteFlag(CGBaseDO cgBaseDO) {
		if(TwoWayWriteProcessCall.isTwoWayWriteEnabled()){
			cgBaseDO.setDtToCentral(CommonConstants.YES);
		}
	}

	/**
	 * Generate running load number.
	 *
	 * @param startCode the start code
	 * @param officeCode the office code
	 * @param processRequestingCode the process requesting code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static String generateRunningLoadNumber(String startCode, String officeCode, String processRequestingCode) 
			throws CGBusinessException, CGSystemException {		
		String generatedNumber = null;
		String runningNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO.setProcessRequesting(processRequestingCode);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(1);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceGeneratorConfigTO.getGeneratedSequences();
		
		if(sequenceGeneratorConfigTO.getGeneratedSequences()!=null && 
				sequenceGeneratorConfigTO.getGeneratedSequences().size()>0){
			runningNumber = sequenceGeneratorConfigTO.getGeneratedSequences().get(0);
		}
		
		generatedNumber = startCode + officeCode + runningNumber;
		return generatedNumber;
	}
	

	/**
	 * Prepare and save process map.
	 *
	 * @param loadManagementTO the load management to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	/*public static void prepareAndSaveProcessMap(LoadManagementTO loadManagementTO)
			throws CGBusinessException, CGSystemException {
		
		OfficeTO hubOfficeTO = null;
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(loadManagementTO.getProcessCode());
		processTO = trackingUniversalService.getProcess(processTO);
		
		if (!StringUtil
				.isEmptyInteger(loadManagementTO.getLoggedInOfficeTO().getReportingHUB())) {
			hubOfficeTO = organizationCommonService
					.getOfficeDetails(loadManagementTO.getLoggedInOfficeTO().getReportingHUB());
		}
		//TODO need to check is dest office, hub officice for every case.
		if (!StringUtil.isEmptyInteger(loadManagementTO.getDestOfficeId())) {
			hubOfficeTO = organizationCommonService
					.getOfficeDetails(loadManagementTO.getDestOfficeId());
		}
		
		ManifestBaseTO manifestTO = new ManifestBaseTO();
		OfficeTO originOfficeTO = new OfficeTO();
		OfficeTO destinationOfficeTO = new OfficeTO();
		if(!StringUtil.isEmptyInteger(loadManagementTO.getOriginOfficeId())){
			originOfficeTO.setOfficeId(loadManagementTO.getOriginOfficeId());
		}
		if(!StringUtil.isEmptyInteger(loadManagementTO.getDestOfficeId())){
			destinationOfficeTO.setOfficeId(loadManagementTO.getDestOfficeId());
		}
		manifestTO.setOriginOfficeTO(originOfficeTO);
		manifestTO.setDestinationOfficeTO(destinationOfficeTO);
		
		final int length = loadManagementTO.getLoadNumber().length;
		for(int i=0; i<length;i++){
			if(StringUtils.isBlank(loadManagementTO.getLoadNumber()[i]) ||
					!StringUtil.isEmptyInteger(loadManagementTO.getLoadConnectedId()[i])){
				continue;
			}

			ProcessMapTO processMapTO = new ProcessMapTO();
			List<TrackingParameterTO> trackingParameterTOs = new ArrayList<>();
			
			manifestTO.setManifestNumber(loadManagementTO.getLoadNumber()[i]);
			processMapTO.setProcessNumber(loadManagementTO.getProcessNumber());
			processMapTO.setArtifactType(CommonConstants.MANIFEST);
			processMapTO.setManifestNo(loadManagementTO.getLoadNumber()[i]);

			createAndSetTrackingParameterTO(trackingParameterTOs,
					UniversalTrackingConstants.BRANCH_OFFICE, loadManagementTO
							.getLoggedInOfficeTO().getOfficeId()
							+ CommonConstants.HASH
							+ loadManagementTO.getLoggedInOfficeTO()
									.getOfficeName()
							+ " "
							+ loadManagementTO.getLoggedInOfficeTO()
									.getOfficeTypeTO().getOffcTypeDesc());

			if (hubOfficeTO!=null) {
				createAndSetTrackingParameterTO(trackingParameterTOs,
						UniversalTrackingConstants.HUB_OFFICE,
						hubOfficeTO.getOfficeId() + 
						CommonConstants.HASH + 
						hubOfficeTO.getOfficeName() + " " +
						hubOfficeTO.getOfficeTypeTO().getOffcTypeDesc());
			}
			createAndSetTrackingParameterTO(trackingParameterTOs,
					UniversalTrackingConstants.WEIGHT,
					loadManagementTO.getWeight()[i].toString());
			
			createAndSetTrackingParameterTO(trackingParameterTOs,					
					UniversalTrackingConstants.MANIFEST_NUMBER,
					loadManagementTO.getLoadNumber()[i]);

			processMapTO.setParameterTOs(trackingParameterTOs);
			processMapTO.setProcessOrder(processTO.getProcessOrder());

			trackingUniversalService.saveProcessMap(processMapTO,
					loadManagementTO.getLoggedInOfficeTO(), null, manifestTO);
			
		}
	}
*/
	/**
	 * Creates the and set tracking parameter to.
	 *
	 * @param trackingParameterTOs the tracking parameter t os
	 * @param paramKey the param key
	 * @param paramValue the param value
	 */
	private static void createAndSetTrackingParameterTO(
			final List<TrackingParameterTO> trackingParameterTOs,
			final String paramKey, final String paramValue) {
		TrackingParameterTO trackingParameterTO = new TrackingParameterTO();
		trackingParameterTO.setParamKey(paramKey);
		trackingParameterTO.setParamValue(paramValue);
		trackingParameterTOs.add(trackingParameterTO);
	}
	
	/**
	 * Generate and set process number.
	 *
	 * @param loadManagementTO the load management to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static void generateAndSetProcessNumber(final LoadManagementTO loadManagementTO)
			throws CGBusinessException, CGSystemException {
		final ProcessTO processTO = new ProcessTO();
		final OfficeTO officeTO = new OfficeTO();
		if (loadManagementTO.getLoggedInOfficeTO() != null
				&& !StringUtil.isEmptyInteger(loadManagementTO.getLoggedInOfficeTO()
						.getOfficeId())) {
			officeTO.setOfficeId(loadManagementTO.getLoggedInOfficeTO().getOfficeId());
		}
		processTO.setProcessCode(loadManagementTO.getProcessCode());
		if(StringUtils.isBlank(loadManagementTO.getProcessNumber())){
			String processNumber = loadManagementCommonService.createProcessNumber(processTO, officeTO);
			loadManagementTO.setProcessNumber(processNumber);
		}
	}

	/**
	 * Sets the load movement id.
	 *
	 * @param loadManagementTO the load management to
	 * @param loadMovementDO the load movement do
	 */
	public static void setLoadMovementId(
			LoadManagementTO loadManagementTO,
			LoadMovementDO loadMovementDO) {
		if(loadMovementDO!=null){
			loadManagementTO.setLoadMovementId(loadMovementDO.getLoadMovementId());
		}
	}

	/**
	 * Sets the operating office to manifest.
	 *
	 * @param manifestTO the new operating office to manifest
	 */
	public static void setOperatingOfficeToManifest(ManifestTO manifestTO) {
		if (udaanContextService.getUserInfoTO() != null
				&& udaanContextService.getUserInfoTO().getOfficeTo() != null
				&& !StringUtil.isEmptyInteger(udaanContextService
						.getUserInfoTO().getOfficeTo().getOfficeId())) {
			manifestTO.setOperatingOffice(udaanContextService.getUserInfoTO()
					.getOfficeTo().getOfficeId());
		}
	}

}
