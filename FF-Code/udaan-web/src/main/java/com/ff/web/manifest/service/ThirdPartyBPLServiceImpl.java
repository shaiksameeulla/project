package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.ThirdPartyBPLDetailsTO;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.universe.drs.dao.DeliveryUniversalDAO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.converter.ThirdPartyBPLConverter;

public class ThirdPartyBPLServiceImpl implements ThirdPartyBPLService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ThirdPartyBPLServiceImpl.class);

	/** The outManifestUniversalService. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The outManifestCommonService. */
	private OutManifestCommonService outManifestCommonService;

	/** The manifestUniversalDAO. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;

	/** The deliveryUniversalDAO. */
	private DeliveryUniversalDAO deliveryUniversalDAO;

	/**
	 * @param deliveryUniversalDAO
	 *            the deliveryUniversalDAO to set
	 */
	public void setDeliveryUniversalDAO(
			DeliveryUniversalDAO deliveryUniversalDAO) {
		this.deliveryUniversalDAO = deliveryUniversalDAO;
	}

	/**
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * @param manifestUniversalDAO
	 *            the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	@Override
	public ThirdPartyBPLDetailsTO getConsignmentDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getConsignmentDtls() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		ConsignmentModificationTO consignmentModificationTO = cnValidateTO
				.getConsignmentModificationTO();

		if (!StringUtil.isNull(consignmentModificationTO)) {
			thirdPartyBPLDetailsTO = convertBookingDtlsTOListToThirdPartyBPLTO(consignmentModificationTO);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getConsignmentDtls() :: END");
		return thirdPartyBPLDetailsTO;
	}

	/**
	 * To convert booking dtls to list to third party BPL details TO
	 * 
	 * @param consignmentModificationTO
	 * @return thirdPartyBPLDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ThirdPartyBPLDetailsTO convertBookingDtlsTOListToThirdPartyBPLTO(
			ConsignmentModificationTO consignmentModificationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: convertBookingDtlsTOListToThirdPartyBPLTO() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		if (!StringUtil.isNull(consignmentModificationTO)) {
			thirdPartyBPLDetailsTO = (ThirdPartyBPLDetailsTO) ThirdPartyBPLConverter
					.thirdPartyOutManifestBPLDtlsConverter(consignmentModificationTO);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: convertBookingDtlsTOListToThirdPartyBPLTO() :: END");
		return thirdPartyBPLDetailsTO;
	}

	@Override
	public ThirdPartyBPLDetailsTO getThirdPartyManifestDtls(
			ManifestInputs manifestInputTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getThirdPartyManifestDtls() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO = null;
		ManifestDO manifestDO = manifestCommonService
				.getManifestDtls(manifestInputTO);
		if (!StringUtil.isNull(manifestDO)) {
			/** To check whether Manifest is Third Party Manifest or not */
			if (!manifestDO.getManifestProcessCode().equalsIgnoreCase(
					OutManifestConstants.PROCESS_CODE_TPDX)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_INVALID);
			}

			/**
			 * To check whether manifest for third party dox is booked for same
			 * third party name and type.
			 */
			if (!(manifestDO.getThirdPartyType().equalsIgnoreCase(
					manifestInputTO.getThirdPartyType()) && manifestDO
					.getVendorId().equals(manifestInputTO.getThirdPartyName()))) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.INVALID_THIRD_PARTY_TYPE_MANIFEST);
			}

			thirdPartyBPLDetailsTO = new ThirdPartyBPLDetailsTO();

			/** Manifest Number */
			thirdPartyBPLDetailsTO.setManifestNo(manifestDO.getManifestNo());

			/** Manifest Status */
			thirdPartyBPLDetailsTO.setManifestStatus(manifestDO
					.getManifestStatus());

			/** Manifest Weight */
			thirdPartyBPLDetailsTO.setWeight(manifestDO.getManifestWeight());

			/** Set Emebedded Type for Consignment */
			thirdPartyBPLDetailsTO
					.setEmbeddedType(OutManifestConstants.SCANNED_TYPE_M);

			manifestInputTO.setManifestId(manifestDO.getManifestId());
			manifestInputTO.setLoginOfficeId(manifestDO.getOriginOffice()
					.getOfficeId());

			/** In-Manifest Integration START */
			if (!manifestDO.getManifestType().equalsIgnoreCase(
					ManifestConstants.MANIFEST_DIRECTION_IN)) {
				if (outManifestCommonService
						.isManifestEmbeddedIn(manifestInputTO)) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_EMBEDDED);
				}
			}
			/** In-Manifest Integration END */
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_INVALID);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getThirdPartyManifestDtls() :: END");
		return thirdPartyBPLDetailsTO;
	}

	@Override
	public String saveOrUpdateOutManifestTPBP(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: saveOrUpdateOutManifestTPBP() :: START");
		ManifestDO manifestDO = null;
		/*ManifestProcessDO manifestProcessDO = null;*/
		String manifestStatus = CommonConstants.EMPTY_STRING;
		Boolean searchedManifest = Boolean.FALSE;
		Set<ConsignmentDO> consignments = null;
		Set<ManifestDO> embeddedManifests = null;

		/*
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}
		ManifestDO manifest = new ManifestDO();
		manifest.setManifestNo(thirdPartyBPLTO.getManifestNo());
		manifest.setOperatingOffice(thirdPartyBPLTO.getLoginOfficeId());
		manifest.setManifestProcessCode(OutManifestConstants.PROCESS_CODE_TPBP);
		manifest.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
		manifest = manifestCommonService.getManifestForCreation(manifest);
		if (!StringUtil.isNull(manifest)) {
			if (StringUtils.equalsIgnoreCase(manifest.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifest.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				/*
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
		}

		/* Create Manifest */
		if (StringUtil.isEmptyInteger(thirdPartyBPLTO.getManifestId())) {
			/* temp object */
			ManifestDO mnfDO = new ManifestDO();

			/* Set Specific values for TPBP */
			setSpecificValuesForTPBP(thirdPartyBPLTO);

			/* prepare cosignment or manifest SET or LIST for third party BPL */
			prepareConsgOrManifestForTPBP(thirdPartyBPLTO, consignments,
					embeddedManifests, mnfDO);

			consignments = mnfDO.getConsignments();
			embeddedManifests = mnfDO.getEmbeddedManifestDOs();

			/* Prepare Manifest */
			manifestDO = ThirdPartyBPLConverter
					.prepareManifestDOList(thirdPartyBPLTO);

			/* Prepare Manifest Process */
			/*manifestProcessDO = ThirdPartyBPLConverter
					.prepareManifestProcessDOList(thirdPartyBPLTO);*/
		}
		/* Update Manifest */
		else {
			/* Set manifest */
			manifestDO = manifest;

			/* Set manifest process */
			ManifestInputs manifestTO = prepareForManifestProcess(thirdPartyBPLTO);
			/*List<ManifestProcessDO> manifestProcessDOs = manifestUniversalDAO
					.getManifestProcessDtls(manifestTO);
			manifestProcessDO = manifestProcessDOs.get(0);*/

			/* prepare cosignment or manifest SET or LIST for third party BPL */
			prepareConsgOrManifestForTPBP(thirdPartyBPLTO, consignments,
					embeddedManifests, manifestDO);

			consignments = manifestDO.getConsignments();
			embeddedManifests = manifestDO.getEmbeddedManifestDOs();

			/* Update manifest */
			manifestDO.setNoOfElements(thirdPartyBPLTO.getNoOfElements());
			manifestDO.setManifestWeight(thirdPartyBPLTO.getFinalWeight());

			/* Update manifest process */
		/*	manifestProcessDO
					.setNoOfElements(thirdPartyBPLTO.getNoOfElements());
			manifestProcessDO.setManifestWeight(thirdPartyBPLTO
					.getFinalWeight());*/
		}

		/* Set Created By, Updated By User Id */
		setUserIdValues(manifestDO, thirdPartyBPLTO);

		/* Set Consignment and/or Manifest SET and/or LIST to manifest */
		if (!CGCollectionUtils.isEmpty(consignments)) {
			manifestDO.setConsignments(consignments);
		}
		if (!CGCollectionUtils.isEmpty(embeddedManifests)) {
			manifestDO.setEmbeddedManifestDOs(embeddedManifests);
		}

		/* Setting grid item position - to maintain order in grid */
		if (!StringUtil.isStringEmpty(thirdPartyBPLTO.getGridPosition())) {
			manifestDO.setGridItemPosition(thirdPartyBPLTO.getGridPosition());
		}

		/* Setting manifest status if it is Auto CLOSE */
		manifestDO.setManifestStatus(thirdPartyBPLTO.getManifestStatus());
		/*manifestProcessDO
				.setManifestStatus(thirdPartyBPLTO.getManifestStatus());*/

		/* Save manifest and manifest process */
		// to set DT_TO_CENTRAL Y while saving
		ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
		/*ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO);*/
		boolean result = manifestCommonService.saveManifest(manifestDO);
		// for two way write
		thirdPartyBPLTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*thirdPartyBPLTO.setManifestProcessId(manifestProcessDO
				.getManifestProcessId());*/
		if (result) {
			manifestStatus = manifestDO.getManifestStatus();
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: saveOrUpdateOutManifestTPBP() :: END");
		return manifestStatus;
	}

	/**
	 * Set User ids i.e. created by or updated by
	 * 
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @param thirdPartyBPLTO
	 */
	private void setUserIdValues(ManifestDO manifestDO,
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: setUserIdValues() :: START");
		/* Created By */
		if (StringUtil.isEmptyInteger(manifestDO.getCreatedBy())) {
			manifestDO.setCreatedBy(thirdPartyBPLTO.getCreatedBy());
		}
		/*if (StringUtil.isEmptyInteger(manifestProcessDO.getCreatedBy())) {
			manifestProcessDO.setCreatedBy(thirdPartyBPLTO.getCreatedBy());
		}*/
		/* Updated By */
		manifestDO.setUpdatedBy(thirdPartyBPLTO.getUpdatedBy());
		/*manifestProcessDO.setUpdatedBy(thirdPartyBPLTO.getUpdatedBy());*/
		// Created and Updated Date
		manifestDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
		/*manifestProcessDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestProcessDO.setUpdatedDate(DateUtil.getCurrentDate());*/
		LOGGER.trace("ThirdPartyBPLServiceImpl :: setUserIdValues() :: END");
	}

	/**
	 * @param thirdPartyBPLTO
	 * @return manifestInputs
	 */
	private ManifestInputs prepareForManifestProcess(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareForManifestProcess() :: START");
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(thirdPartyBPLTO.getManifestNo());
		manifestInputs.setLoginOfficeId(thirdPartyBPLTO.getLoginOfficeId());
		manifestInputs.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifestInputs.setManifestDirection(thirdPartyBPLTO
				.getManifestDirection());
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareForManifestProcess() :: END");
		return manifestInputs;
	}

	/**
	 * To set specific values to ThirdPartyBPLOutManifestTO
	 * 
	 * @param thirdPartyBPLTO
	 */
	private void setSpecificValuesForTPBP(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: setSpecificValuesForTPBP() :: START");
		Integer operatingLevel = 0;
		// Setting Logged In City Id as Destination City Id and
		thirdPartyBPLTO.setDestinationCityId(thirdPartyBPLTO.getLoginCityId());
		// Setting destination office as logged in office
		thirdPartyBPLTO.setDestinationOfficeId(thirdPartyBPLTO
				.getLoginOfficeId());
		// Setting process id and code
		if (!StringUtil.isNull(thirdPartyBPLTO.getProcessTO())) {
			thirdPartyBPLTO.setProcessId(thirdPartyBPLTO.getProcessTO()
					.getProcessId());
			thirdPartyBPLTO.setProcessCode(thirdPartyBPLTO.getProcessTO()
					.getProcessCode());
		}
		// To calc operating level
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(thirdPartyBPLTO.getLoginOfficeId());
		operatingLevel = outManifestUniversalService.calcOperatingLevel(
				thirdPartyBPLTO, loggedInOffice);
		thirdPartyBPLTO.setOperatingLevel(operatingLevel);
		// Origin Hub Off and Destination Hub Off
		List<Integer> originHubList = null;
		List<OfficeTO> orgOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						thirdPartyBPLTO.getLoginCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(orgOfficeTOs)) {
			originHubList = new ArrayList<Integer>();
			for (OfficeTO officeTO2 : orgOfficeTOs) {
				originHubList.add(officeTO2.getOfficeId());
			}
			thirdPartyBPLTO.setOriginHubOffList(originHubList);
			thirdPartyBPLTO.setDestHubOffList(originHubList);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: setSpecificValuesForTPBP() :: END");
	}

	/**
	 * To prepare consg or/and manifest SET or/and LIST
	 * 
	 * @param thirdPartyBPLTO
	 * @param embeddedManifests
	 * @param consignments
	 */
	private void prepareConsgOrManifestForTPBP(
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO,
			Set<ConsignmentDO> consignments, Set<ManifestDO> embeddedManifests,
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareConsgOrManifestForTPBP() :: START");
		int noOfElements = 0;
		double finalWeight = 0.0;
		String manifestStatus = thirdPartyBPLTO.getManifestStatus();
		List<String> consgNos = new ArrayList<String>();
		List<String> manifestNos = new ArrayList<String>();
		List<ConsignmentDO> consignmentDOs = null;
		List<ManifestDO> manifestDOs = null;
		StringBuffer gridPosition = new StringBuffer();

		/* Prepare consignment number SET and manifest number SET to search */
		for (int i = 0; i < thirdPartyBPLTO.getScannedGridItemNo().length; i++) {
			if (!StringUtil.isStringEmpty(thirdPartyBPLTO
					.getScannedGridItemNo()[i])) {
				/* if grid element is CONSIGNMENT NUMBER */
				if (thirdPartyBPLTO.getEmbeddedType()[i]
						.equalsIgnoreCase(OutManifestConstants.SCANNED_TYPE_C)) {
					consgNos.add(thirdPartyBPLTO.getScannedGridItemNo()[i]);
					noOfElements++;
				}
				/* if grid element is MANIFEST NUMBER */
				else if (thirdPartyBPLTO.getEmbeddedType()[i]
						.equalsIgnoreCase(OutManifestConstants.SCANNED_TYPE_M)) {
					manifestNos.add(thirdPartyBPLTO.getScannedGridItemNo()[i]);
					noOfElements++;
				}
				finalWeight += thirdPartyBPLTO.getWeights()[i];
			}
		}

		/* Get all consignments */
		if (!CGCollectionUtils.isEmpty(consgNos) && consgNos.size() > 0) {
			consignmentDOs = manifestCommonService
					.getConsignmentsAndEvictFromSession(consgNos);
			consignments = new LinkedHashSet<ConsignmentDO>(
					consignmentDOs.size());
		}

		/* Get all manifests */
		if (!CGCollectionUtils.isEmpty(manifestNos) && manifestNos.size() > 0) {
			manifestDOs = manifestCommonService.getManifests(manifestNos,
					thirdPartyBPLTO.getLoginOfficeId());
			embeddedManifests = new LinkedHashSet<ManifestDO>(
					manifestDOs.size());
		}

		/* Update consignment in grid if any change */
		for (int i = 0; i < thirdPartyBPLTO.getScannedGridItemNo().length; i++) {
			if (!StringUtil.isStringEmpty(thirdPartyBPLTO
					.getScannedGridItemNo()[i])) {
				/* Update Consignment */
				if (thirdPartyBPLTO.getEmbeddedType()[i]
						.equalsIgnoreCase(OutManifestConstants.SCANNED_TYPE_C)) {
					for (ConsignmentDO consgDO : consignmentDOs) {
						if (consgDO.getConsgNo().equalsIgnoreCase(
								thirdPartyBPLTO.getScannedGridItemNo()[i])) {
							updateConsgDtls(consgDO, thirdPartyBPLTO, i);
							/* Set grid order for consignment */
							gridPosition.append(consgDO.getConsgNo());
							gridPosition
									.append(CommonConstants.CHARACTER_COLON);
							gridPosition
									.append(thirdPartyBPLTO.getPosition()[i]);
							gridPosition.append(CommonConstants.COMMA);
							consignments.add(consgDO);
							break;
						}
					}
				}
				/* Update Manifest */
				else if (thirdPartyBPLTO.getEmbeddedType()[i]
						.equalsIgnoreCase(OutManifestConstants.SCANNED_TYPE_M)) {
					for (ManifestDO manifest : manifestDOs) {
						if (manifest.getManifestNo().equalsIgnoreCase(
								thirdPartyBPLTO.getScannedGridItemNo()[i])) {
							updateManifstDtls(manifest, thirdPartyBPLTO, i);
							embeddedManifests.add(manifest);
							break;
						}
					}
				}
			}
		}

		/* To set position */
		if (!StringUtil.isNull(gridPosition) && gridPosition.length() > 0) {
			thirdPartyBPLTO.setGridPosition(gridPosition.toString());
		}

		/* Setting no of elements */
		thirdPartyBPLTO.setNoOfElements(noOfElements);

		/* Setting manifest final weight */
		thirdPartyBPLTO.setFinalWeight(finalWeight);

		/* Check max CN Allowed for AUTO CLOSE */
		if (!StringUtil.isStringEmpty(thirdPartyBPLTO.getMaxCNsAllowed())) {
			int maxCNsAllowed = Integer.parseInt(thirdPartyBPLTO
					.getMaxCNsAllowed());
			if (noOfElements == maxCNsAllowed) {
				manifestStatus = OutManifestConstants.CLOSE;
			}
		}

		/* Check max weight tolerance */
		double maxWeightAllowed = Double.parseDouble(thirdPartyBPLTO
				.getMaxWeightAllowed());
		double maxTolerenceAllowed = Double.parseDouble(thirdPartyBPLTO
				.getMaxTolerenceAllowed());
		double maxWtTolerence = maxWeightAllowed
				+ (maxWeightAllowed * maxTolerenceAllowed / 100);
		if (thirdPartyBPLTO.getFinalWeight().doubleValue() >= maxWtTolerence) {
			manifestStatus = OutManifestConstants.CLOSE;
		}

		thirdPartyBPLTO.setManifestStatus(manifestStatus);

		// Setting Consignment
		if (!CGCollectionUtils.isEmpty(consignments)) {
			manifestDO.setConsignments(consignments);
		} else {
			manifestDO.setConsignments(null);
		}
		// Setting Manifest
		if (!CGCollectionUtils.isEmpty(embeddedManifests)) {
			manifestDO.setEmbeddedManifestDOs(embeddedManifests);
		} else {
			manifestDO.setEmbeddedManifestDOs(null);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareConsgOrManifestForTPBP() :: END");
	}

	/**
	 * To update manifest details in grid like position, updating process
	 * 
	 * @param manifestDO
	 * @param thirdPartyBPLTO
	 * @param rowId
	 */
	private void updateManifstDtls(ManifestDO manifestDO,
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO, int rowId) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: updateManifstDtls() :: START");
		/* Setting position */
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getPosition()[rowId])) {
			manifestDO.setPosition(thirdPartyBPLTO.getPosition()[rowId]);
		}
		/* Setting updating process code */
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getProcessId())) {
			ProcessDO updatedProcessDO = new ProcessDO();
			updatedProcessDO.setProcessId(thirdPartyBPLTO.getProcessId());
			// manifestDO.setUpdatingProcess(updatedProcessDO);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: updateManifstDtls() :: END");
	}

	/**
	 * To update consignment
	 * 
	 * @param consgDO
	 * @param thirdPartyBPLTO
	 * @param rowId
	 */
	private void updateConsgDtls(ConsignmentDO consgDO,
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO, int rowId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: updateConsgDtls() :: START");

		/** Setting Billing Flags - Added By Himal on 6 Dec. 2013 */
		boolean isBillingFlagReq = Boolean.FALSE;
		if (!StringUtil.isEmptyDouble(consgDO.getFinalWeight())
				&& (consgDO.getFinalWeight().doubleValue() != thirdPartyBPLTO
						.getWeights()[rowId].doubleValue())) {
			isBillingFlagReq = Boolean.TRUE;
		} else if (StringUtil.isEmptyDouble(consgDO.getFinalWeight())) {
			isBillingFlagReq = Boolean.TRUE;
		}
		if (isBillingFlagReq) {
			manifestCommonService.updateBillingFlagsInConsignment(consgDO,
					CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
		}

		/* Set Weight */
		consgDO.setFinalWeight(thirdPartyBPLTO.getWeights()[rowId]);

		/* Setting noOfPcs. and child consignment */
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getNoOfPcs()[rowId])
				&& thirdPartyBPLTO.getNoOfPcs()[rowId] > 1) {
			consgDO.setNoOfPcs(thirdPartyBPLTO.getNoOfPcs()[rowId]);
			prepareChildCnDO(consgDO, thirdPartyBPLTO, rowId);
		} else {
			consgDO.setNoOfPcs(thirdPartyBPLTO.getNoOfPcs()[rowId]);
		}

		/* Setting updated process code */
		if (!StringUtil.isEmptyInteger(thirdPartyBPLTO.getProcessId())) {
			ProcessDO updatedProcessDO = new ProcessDO();
			updatedProcessDO.setProcessId(thirdPartyBPLTO.getProcessId());
			consgDO.setUpdatedProcess(updatedProcessDO);
		}
		/* Created By */
		consgDO.setCreatedBy(thirdPartyBPLTO.getCreatedBy());
		/* Updated By */
		consgDO.setUpdatedBy(thirdPartyBPLTO.getUpdatedBy());
		// Created and Updated Date
		consgDO.setCreatedDate(DateUtil.getCurrentDate());
		consgDO.setUpdatedDate(DateUtil.getCurrentDate());
		LOGGER.trace("ThirdPartyBPLServiceImpl :: updateConsgDtls() :: END");
	}

	/**
	 * To prepare child CN DO
	 * 
	 * @param consgDO
	 * @param thirdPartyBPLTO
	 * @param rowId
	 */
	private void prepareChildCnDO(ConsignmentDO consgDO,
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO, int rowId) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareChildCnDO() :: START");
		// int noOfPcs = 0;
		Set<ChildConsignmentDO> newChildCNDOs = new LinkedHashSet<ChildConsignmentDO>();
		String childCNs = thirdPartyBPLTO.getChildCns()[rowId];
		String childCNList[] = childCNs.split(CommonConstants.HASH);
		for (int j = 0; j < childCNList.length; j++) {
			ChildConsignmentDO childDO = new ChildConsignmentDO();
			String str[] = childCNList[j].split(CommonConstants.COMMA);
			childDO.setChildConsgNumber(str[0]);
			childDO.setChildConsgWeight(Double.parseDouble(str[1]));
			childDO.setConsignment(consgDO);
			newChildCNDOs.add(childDO);
		}
		Set<ChildConsignmentDO> childCNDOs = consgDO.getChildCNs();
		if (!CGCollectionUtils.isEmpty(childCNDOs)) {
			for (ChildConsignmentDO newChildCN : newChildCNDOs) {
				for (ChildConsignmentDO childCN : childCNDOs) {
					if (childCN.getChildConsgNumber().equalsIgnoreCase(
							newChildCN.getChildConsgNumber())) {
						newChildCN.setBookingChildCNId(childCN
								.getBookingChildCNId());
						break;
					}
				}
			}
		}
		consgDO.setChildCNs(newChildCNDOs);
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareChildCnDO() :: END");
	}

	@Override
	public ThirdPartyBPLOutManifestTO searchManifestDtls(
			ManifestInputs manifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: searchManifestDtls() :: START");
		ManifestDO manifestDO = null;
		ThirdPartyBPLOutManifestTO thirdPartyBPLOutManifestTO = null;
		List<ThirdPartyBPLDetailsTO> consgDtlsList = null;
		List<ThirdPartyBPLDetailsTO> manifestDtlsList = null;
		List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsTOs = new ArrayList<ThirdPartyBPLDetailsTO>();
		manifestDO = OutManifestBaseConverter.prepateManifestDO(manifestTO);

		/* Search manifest for third party BPL */
		manifestDO = manifestCommonService
				.getParcelEmbeddedInManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			/* Prepare header */
			thirdPartyBPLOutManifestTO = fillHeaderDetails(manifestDO,
					thirdPartyBPLOutManifestTO);

			/* Prepare consignment SET */
			Set<ConsignmentDO> consigDOs = manifestDO.getConsignments();
			if (!CGCollectionUtils.isEmpty(consigDOs) && consigDOs.size() > 0) {
				consgDtlsList = prepareConsgList(manifestDO, consigDOs);
			}
			/* Prepare manifest SET */
			Set<ManifestDO> manifestDOs = manifestDO.getEmbeddedManifestDOs();
			if (!CGCollectionUtils.isEmpty(manifestDOs)
					&& manifestDOs.size() > 0) {
				manifestDtlsList = prepareManifestList(manifestDO, manifestDOs);
			}

			/* Merge consig and manifest details then sort the details */
			if (!CGCollectionUtils.isEmpty(consgDtlsList)
					&& consgDtlsList.size() > 0) {
				thirdPartyBPLDetailsTOs.addAll(consgDtlsList);
			}
			if (!CGCollectionUtils.isEmpty(manifestDtlsList)
					&& manifestDtlsList.size() > 0) {
				thirdPartyBPLDetailsTOs.addAll(manifestDtlsList);
			}
			Collections.sort(thirdPartyBPLDetailsTOs);
			thirdPartyBPLOutManifestTO
					.setThirdPartyBPLDetailsListTO(thirdPartyBPLDetailsTOs);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: searchManifestDtls() :: END");
		return thirdPartyBPLOutManifestTO;
	}

	/**
	 * To prepare manifest list
	 * 
	 * @param manifestDO
	 * @param manifestDOs
	 * @return ThirdPartyBPLDetailsTO List
	 */
	private List<ThirdPartyBPLDetailsTO> prepareManifestList(
			ManifestDO manifestDO, Set<ManifestDO> manifestDOs) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareManifestList() :: START");
		List<ThirdPartyBPLDetailsTO> embeddedManifestList = new ArrayList<ThirdPartyBPLDetailsTO>(
				manifestDOs.size());
		for (ManifestDO mnftDO : manifestDOs) {
			ThirdPartyBPLDetailsTO detailsTO = new ThirdPartyBPLDetailsTO();

			// need for print
			detailsTO.setManifestId(mnftDO.getManifestId());

			/* Set manifest number */
			detailsTO.setManifestNo(mnftDO.getManifestNo());

			/* Set manifest weight */
			detailsTO.setWeight(mnftDO.getManifestWeight());

			/* Set grid position */
			detailsTO.setPosition(mnftDO.getPosition());

			/* Set Embedded type for manifest */
			detailsTO.setEmbeddedType(OutManifestConstants.SCANNED_TYPE_M);
			if (!StringUtil.isStringEmpty(mnftDO.getOriginOffice()
					.getOfficeName())) {
				detailsTO.setBookingOffName(mnftDO.getOriginOffice()
						.getOfficeName());
			}
			embeddedManifestList.add(detailsTO);
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareManifestList() :: END");
		return embeddedManifestList;
	}

	/**
	 * To prepare consignment list
	 * 
	 * @param manifestDO
	 * @param consigDOs
	 * @return ThirdPartyBPLDetailsTO List
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private List<ThirdPartyBPLDetailsTO> prepareConsgList(
			ManifestDO manifestDO, Set<ConsignmentDO> consigDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareConsgList() :: START");
		List<ThirdPartyBPLDetailsTO> consgList = new ArrayList<ThirdPartyBPLDetailsTO>(
				consigDOs.size());
		String gridItemPosition = manifestDO.getGridItemPosition();
		OfficeTO officeTO = new OfficeTO();
		// int consignCount=0;

		for (ConsignmentDO consgDO : consigDOs) {
			ThirdPartyBPLDetailsTO detailsTO = new ThirdPartyBPLDetailsTO();
			/* Consignment Number */
			detailsTO.setConsgNo(consgDO.getConsgNo());

			/* No of Pcs */
			detailsTO.setWeight(consgDO.getFinalWeight());
			/* Set No of Pcs and child CN. */
			if (!StringUtil.isEmptyInteger(consgDO.getNoOfPcs())) {
				detailsTO.setNoOfPcs(consgDO.getNoOfPcs());
				/* Set Child CN */
				if (!CGCollectionUtils.isEmpty(consgDO.getChildCNs())) {
					StringBuilder chindCNDtls = new StringBuilder();
					for (ChildConsignmentDO childCN : consgDO.getChildCNs()) {
						chindCNDtls.append(childCN.getChildConsgNumber());
						chindCNDtls.append(CommonConstants.COMMA);
						chindCNDtls.append(childCN.getChildConsgWeight());
						chindCNDtls.append(CommonConstants.HASH);
					}
					detailsTO.setChildCn(chindCNDtls.toString());
				}
			}

			/* Set Cn-Content */
			if (!StringUtil.isNull(consgDO.getCnContentId())) {
				detailsTO.setCnContent(consgDO.getCnContentId()
						.getCnContentName());
			}
			// to print other content
			if (!StringUtil.isNull(consgDO.getOtherCNContent())) {
				detailsTO.setOtherContent(consgDO.getOtherCNContent());
			}
			/* Set Cn-Paper Work */
			if (!StringUtil.isNull(consgDO.getCnPaperWorkId())) {
				detailsTO.setPaperWork(consgDO.getCnPaperWorkId()
						.getCnPaperWorkName());
			}
			/* Set Declared value */
			if (!StringUtil.isEmptyDouble(consgDO.getDeclaredValue())) {
				detailsTO.setDeclaredValues(consgDO.getDeclaredValue());
			}
			/* Set Topay Amount */
			if (!StringUtil.isEmptyDouble(consgDO.getTopayAmt())) {
				detailsTO.setToPayAmts(consgDO.getTopayAmt());
			}
			/* Set COD Amount */
			if (!StringUtil.isEmptyDouble(consgDO.getCodAmt())) {
				detailsTO.setCodAmts(consgDO.getCodAmt());
			}

			/* Set BA Amount */
			if (!StringUtil.isEmptyDouble(consgDO.getBaAmt())) {
				detailsTO.setBaAmounts(consgDO.getBaAmt());
			}
			if (!StringUtil.isEmptyInteger(consgDO.getOrgOffId())) {
				officeTO = outManifestCommonService.getOfficeDetails(consgDO
						.getOrgOffId());
			}

			// TODO rate integration
			// detailsTO.setOctroiAmts();
			// detailsTO.setStateTaxes();
			// detailsTO.setServiceCharges();

			/* Set grid position */
			if (!StringUtil.isStringEmpty(gridItemPosition)) {
				Integer position = getConsgPosition(detailsTO.getConsgNo(),
						gridItemPosition);
				detailsTO.setPosition(position);
			}
			detailsTO.setOriginOfficeTO(officeTO);
			detailsTO.setBookingOffName(officeTO.getOfficeName());

			if (StringUtil.equals(manifestDO.getManifestStatus(), "C")) {
				// setting delivery Attempt for print
				DeliveryDetailsDO deliveryDetailsDO = deliveryUniversalDAO
						.getDrsDtlsByConsgNo(detailsTO.getConsgNo());
				if (!StringUtil.isNull(deliveryDetailsDO)) {
					Integer deliveryAttempt = deliveryDetailsDO
							.getAttemptNumber();
					detailsTO.setDeliveryAttempt(deliveryAttempt);
				}

			}

			/* Set Embedded type for consignmnet */
			detailsTO.setEmbeddedType(OutManifestConstants.SCANNED_TYPE_C);
			consgList.add(detailsTO);
			// consignCount++;
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: prepareConsgList() :: END");
		return consgList;
	}

	/**
	 * To get consignment.s position
	 * 
	 * @param consgNo
	 * @param gridItemPosition
	 * @return Integer
	 */
	private Integer getConsgPosition(String consgNo, String gridItemPosition) {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getConsgPosition() :: START");
		Integer position = 0;
		String[] result = gridItemPosition.split(CommonConstants.COMMA);
		for (int i = 0; i < result.length; i++) {
			if (!StringUtil.isStringEmpty(result[i])) {
				String[] pos = result[i].split(CommonConstants.CHARACTER_COLON);
				if (pos[0].equalsIgnoreCase(consgNo)) {
					position = Integer.parseInt(pos[1]);
					break;
				}
			}
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getConsgPosition() :: END");
		return position;
	}

	/**
	 * To convert ManifestDO to Header ThirdPartyBPLOutManifestTO
	 * 
	 * @param manifestDO
	 * @param thirdPartyBPLTO
	 * @return thirdPartyBPLTO
	 * @throws CGBusinessException
	 */
	private ThirdPartyBPLOutManifestTO fillHeaderDetails(ManifestDO manifestDO,
			ThirdPartyBPLOutManifestTO thirdPartyBPLTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: fillHeaderDetails() :: START");
		thirdPartyBPLTO = ThirdPartyBPLConverter
				.thirdPartyBPLDomainConverter(manifestDO);
		/* Set manifest id */
		thirdPartyBPLTO.setManifestId(manifestDO.getManifestId());

		/* Set manifest no. */
		thirdPartyBPLTO.setManifestNo(manifestDO.getManifestNo());

		/* Set load no. */
		thirdPartyBPLTO.setLoadNo(manifestDO.getLoadLotId());

		/* Set third party type */
		thirdPartyBPLTO.setThirdPartyType(manifestDO.getThirdPartyType());

		/* Set vendor id and third party name */
		List<LoadMovementVendorTO> partyListTO = outManifestCommonService
				.getDtlsForTPCC(manifestDO.getVendorId());
		LoadMovementVendorTO vendorTO = partyListTO.get(0);
		if (!StringUtil.isNull(vendorTO)) {
			thirdPartyBPLTO.setThirdPartyName(vendorTO.getBusinessName());
			thirdPartyBPLTO.setVendorId(vendorTO.getVendorId());
			thirdPartyBPLTO.setVendorCode(vendorTO.getVendorCode());
		}

		/* Set bag lock no. */
		thirdPartyBPLTO.setBagLockNo(manifestDO.getBagLockNo());

		/* Set manifest status */
		thirdPartyBPLTO.setManifestStatus(manifestDO.getManifestStatus());

		LOGGER.trace("ThirdPartyBPLServiceImpl :: fillHeaderDetails() :: END");
		return thirdPartyBPLTO;
	}

	@Override
	public ThirdPartyBPLDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getInManifestdConsignmentDtls() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBplDetailsTO = null;

		List<ConsignmentTO> consignmtTOs = outManifestUniversalService
				.getConsgDtls(manifestFactoryTO);

		thirdPartyBplDetailsTO = convertConsgDtlsTOListToThirdPartyBplDetailsTO(consignmtTOs);
		LOGGER.trace("ThirdPartyBPLServiceImpl :: getInManifestdConsignmentDtls() :: END");
		return thirdPartyBplDetailsTO;
	}

	/**
	 * To convert consignmentTO list to ThirdPartyBPLDetailsTO
	 * 
	 * @param consignmentTOs
	 * @return thirdPartyBplDtlsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ThirdPartyBPLDetailsTO convertConsgDtlsTOListToThirdPartyBplDetailsTO(
			List<ConsignmentTO> consignmentTOs) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ThirdPartyBPLServiceImpl :: convertConsgDtlsTOListToThirdPartyBplDetailsTO() :: START");
		ThirdPartyBPLDetailsTO thirdPartyBplDtlsTO = null;

		if (!StringUtil.isEmptyList(consignmentTOs)) {
			thirdPartyBplDtlsTO = (ThirdPartyBPLDetailsTO) ThirdPartyBPLConverter
					.thirdPartyBplGridDetailsForInManifConsg(consignmentTOs
							.get(0));
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl :: convertConsgDtlsTOListToThirdPartyBplDetailsTO() :: END");
		return thirdPartyBplDtlsTO;
	}

	@Override
	public ThirdPartyBPLOutManifestTO getTotalConsignmentCount(
			List<ThirdPartyBPLDetailsTO> thirdPartyBPLDetailsTOs)
			throws CGBusinessException, CGSystemException {
		ThirdPartyBPLOutManifestTO thirdPartyBPLOutManifestTO = new ThirdPartyBPLOutManifestTO();
		Long totalComail = 0L;
		int totalConsg = 0;
		int totalPacket = 0;
		try {
			LOGGER.trace("ThirdPartyBPLServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
			for (ThirdPartyBPLDetailsTO thirdPartyBPLDetailsTO : thirdPartyBPLDetailsTOs) {

				if (!StringUtil.isEmptyInteger(thirdPartyBPLDetailsTO
						.getManifestId())) {
					totalComail += manifestUniversalDAO
							.getComailCountByManifestId(thirdPartyBPLDetailsTO
									.getManifestId());
					totalConsg += manifestUniversalDAO
							.getConsgCountByManifestId(thirdPartyBPLDetailsTO
									.getManifestId());
					totalPacket++;

				} else if (!StringUtil.isStringEmpty(thirdPartyBPLDetailsTO
						.getConsgNo())) {
					totalConsg++;

				}
			}

			thirdPartyBPLOutManifestTO.setTotalConsg(totalConsg);
			thirdPartyBPLOutManifestTO.setTotalComail(totalComail);
			thirdPartyBPLOutManifestTO.setTotalPacket(totalPacket);
			thirdPartyBPLOutManifestTO.setRowCount(thirdPartyBPLDetailsTOs
					.size());
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in ThirdPartyBPLServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in ThirdPartyBPLServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("ThirdPartyBPLServiceImpl:: getTotalConsignmentCount()::END------------>:::::::");

		return thirdPartyBPLOutManifestTO;
	}

	@Override
	public Boolean isConsignmentExistInDRS(String consignment)
			throws CGBusinessException, CGSystemException {
		return outManifestUniversalService.isConsignmentExistInDRS(consignment);
	}

}
