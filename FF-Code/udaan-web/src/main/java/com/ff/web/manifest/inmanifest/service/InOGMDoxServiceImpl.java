package com.ff.web.manifest.inmanifest.service;

import java.text.DecimalFormat;
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
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestOGMDetailTO;
import com.ff.manifest.inmanifest.InManifestOGMTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InManifestBaseConverter;
import com.ff.web.manifest.inmanifest.converter.InOGMDoxConverter;
import com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO;
import com.ff.web.manifest.inmanifest.dao.InOGMDoxDAO;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.service.ManifestCommonService;

/**
 * The Class InOGMDoxServiceImpl.
 * 
 * @author uchauhan
 */
public class InOGMDoxServiceImpl implements InOGMDoxService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InOGMDoxServiceImpl.class);

	/** The in ogm dox dao. */
	private InOGMDoxDAO inOgmDoxDAO;

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The in manifest common dao. */
	private InManifestCommonDAO inManifestCommonDAO;

	/** The in manifest common service. */
	private InManifestCommonService inManifestCommonService;

	/** The geography common service. */
	private transient GeographyCommonService geographyCommonService;

	/** The manifest common service. */
	private transient ManifestCommonService manifestCommonService;

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * Sets the in ogm dox dao.
	 * 
	 * @param inOgmDoxDAO
	 *            the inOgmDoxDAO to set
	 */
	public void setInOgmDoxDAO(InOGMDoxDAO inOgmDoxDAO) {
		this.inOgmDoxDAO = inOgmDoxDAO;
	}

	/**
	 * Sets the in manifest common dao.
	 * 
	 * @param inManifestCommonDAO
	 *            the inManifestCommonDAO to set
	 */
	public void setInManifestCommonDAO(InManifestCommonDAO inManifestCommonDAO) {
		this.inManifestCommonDAO = inManifestCommonDAO;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the in manifest common service.
	 * 
	 * @param inManifestCommonService
	 *            the inManifestCommonService to set
	 */
	public void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InOGMDoxService#
	 * saveOrUpdateConsignment(com.ff.domain.consignment.ConsignmentDO)
	 */
	@Override
	public ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException {
		return inOgmDoxDAO.saveOrUpdateConsignment(consignmentDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InOGMDoxService#
	 * getConsgManifestedDetails(com.ff.manifest.ManifestBaseTO)
	 */
	@Override
	public InManifestOGMTO getConsgManifestedDetails(ManifestBaseTO baseTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InOGMDoxServiceImpl::getConsgManifestedDetails::START------------>:::::::");
		ManifestBaseTO manifestBaseTO = null;
		ManifestDO manifestDO = null;
		List<InManifestOGMDetailTO> detailTOs = new ArrayList<>();
		InManifestOGMTO inManifestOGMTO = new InManifestOGMTO();
		boolean isOutManifest = Boolean.FALSE;
		boolean isInBplDoxPacket = Boolean.FALSE;

		baseTO.setIsFetchProfileManifestDox(Boolean.TRUE);
		manifestDO = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(baseTO);
		
		if (manifestDO != null
				&& StringUtil.isEmptyInteger(manifestDO.getNoOfElements())) {
			inManifestCommonService.validateIsManifestOutManifested(manifestDO);

			/*********** Start of Get consg & comail from other Manifest ***********/
			ManifestDO manifestDO4Out = getOtherManifestDO(baseTO);
			if (manifestDO4Out != null) {
				manifestDO
						.setManifestWeight(manifestDO4Out.getManifestWeight());
				manifestDO.setComails(manifestDO4Out.getComails());
				manifestDO.setConsignments(manifestDO4Out.getConsignments());
				manifestDO.setNoOfElements(manifestDO4Out.getNoOfElements());
				manifestDO.setContainsOnlyCoMail(manifestDO4Out
						.getContainsOnlyCoMail());
				manifestDO.setGridItemPosition(manifestDO4Out.getGridItemPosition());
				isOutManifest = Boolean.TRUE;
			} else {
				isInBplDoxPacket = Boolean.TRUE;
			}
			/*********** End of Get consg & comail from out Manifest ***********/
		}

		// get Data from other manifest
		if (manifestDO == null) {

			// InBPL process did not happen.Please do InBPL process before
			// doing InOGM process.
			ExceptionUtil
					.prepareBusinessException(InManifestConstants.IN_BPL_PROCESS_DID_NOT_HAPPEN);
			
			/*manifestDO = getOtherManifestDO(baseTO);
			if (manifestDO != null) {
				isOutManifest = Boolean.TRUE;
				manifestDO.setManifestId(null);
			}*/

			/*ManifestBaseTO manifestBaseTO4Out = new ManifestBaseTO();
			manifestBaseTO4Out.setManifestNumber(baseTO.getManifestNumber());
			manifestBaseTO4Out
					.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
			manifestBaseTO4Out.setIsExcludeManifestType(Boolean.TRUE);
			Integer manifestId = inManifestCommonDAO
					.getManifestIdByManifest(manifestBaseTO4Out);
			if (!StringUtil.isEmptyInteger(manifestId)) {
				// InBPL process did not happen.Please do InBPL process before
				// doing InOGM process.
				ExceptionUtil
						.prepareBusinessException(InManifestConstants.IN_BPL_PROCESS_DID_NOT_HAPPEN);
			}*/

		}

		// //////////////////////////////////////////////////////////////////////////
		/*if (baseTO.getManifestType().equals(CommonConstants.MANIFEST_TYPE_IN)) {
			baseTO.setIsFetchProfileManifestDox(Boolean.TRUE);
			manifestDO = inManifestCommonDAO.getManifestByNoProcessType(baseTO);
			InManifestUtils.resetFetchProfile(baseTO);

			*//*********** Start of Get consg & comail from other Manifest ***********//*
			if (manifestDO != null
					&& StringUtil.isEmptyInteger(manifestDO.getNoOfElements())) {

				ManifestDO manifestDO4Out = getOtherManifestDO(baseTO);
				if (manifestDO4Out != null) {
					manifestDO.setManifestWeight(manifestDO4Out
							.getManifestWeight());
					manifestDO.setComails(manifestDO4Out.getComails());
					manifestDO
							.setConsignments(manifestDO4Out.getConsignments());
					manifestDO
							.setNoOfElements(manifestDO4Out.getNoOfElements());
					manifestDO.setContainsOnlyCoMail(manifestDO4Out
							.getContainsOnlyCoMail());
					isOutManifest = Boolean.TRUE;
				} else {
					isInBplDoxPacket = Boolean.TRUE;
				}
			}
			*//*********** End of Get consg & comail from out Manifest ***********//*
		} else {
			// get Data from other manifest
			manifestDO = getOtherManifestDO(baseTO);
			if (manifestDO != null) {
				isOutManifest = Boolean.TRUE;
			}
		}*/
		// ////////////////////////////////////

		if (manifestDO != null) {
			manifestBaseTO = new ManifestBaseTO();
			manifestBaseTO = InManifestBaseConverter
					.inManifestBaseTransferObjConverter(manifestBaseTO,
							manifestDO);
			if(manifestDO.getManifestDate()!=null){
				inManifestOGMTO.setManifestDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
						(manifestDO.getManifestDate()));
				/*inManifestOGMTO.setManifestDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat());*/
			}
			inManifestOGMTO.setManifestNumber(baseTO.getManifestNumber());
			inManifestOGMTO.setLockNum(manifestBaseTO.getLockNum());
			inManifestOGMTO.setManifestWeight(manifestBaseTO
					.getManifestWeight());
			inManifestOGMTO.setManifestId(manifestBaseTO.getManifestId());
			inManifestOGMTO.setProcessCode(manifestBaseTO.getProcessCode());
			inManifestOGMTO.setOriginOfficeTO(manifestBaseTO
					.getOriginOfficeTO());
			inManifestOGMTO.setOfficeTypeTO(manifestBaseTO.getOfficeTypeTO());
			inManifestOGMTO.setOriginRegionTO(manifestBaseTO
					.getOriginRegionTO());
			inManifestOGMTO.setOriginCityTO(manifestBaseTO.getOriginCityTO());
			inManifestOGMTO.setDestinationOfficeTO(manifestBaseTO
					.getDestinationOfficeTO());
			inManifestOGMTO.setIsCoMail(manifestDO.getContainsOnlyCoMail());
			InManifestUtils.setEmbeddedInRemarksPositionToTO(manifestDO,
					inManifestOGMTO);
			if (manifestBaseTO.getOriginOfficeTO() != null) {
				inManifestOGMTO.setLoginOfficeName(manifestBaseTO
						.getOriginOfficeTO().getOfficeName());
			}
			inManifestOGMTO.setDestinationCityId(manifestBaseTO
					.getDestinationCityId());
			inManifestOGMTO.setDestinationOfficeId(manifestBaseTO
					.getDestinationOfficeId());
			inManifestOGMTO.setManifestReceivedStatus(manifestDO.getReceivedStatus());
			if (manifestDO.getDestOffice() != null
					&& manifestDO.getDestOffice().getMappedRegionDO() != null) {
				inManifestOGMTO.setLoginRegionOffice(manifestDO.getDestOffice()
						.getMappedRegionDO().getRegionDisplayName()
						+ CommonConstants.HYPHEN
						+ manifestDO.getDestOffice().getOfficeName());
			}
			
			// grid converter Starts
			InManifestOGMDetailTO detailTO = null;
			double totalWt = 0.0;
			int rowcount = 0;
			boolean isConsgComail = Boolean.FALSE;

			/***************** Sorting Grid Position Start *****************/
			if (StringUtils.isNotBlank(manifestDO.getGridItemPosition())) {
				GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
				gridItemOrderDO.setGridPosition(manifestDO
						.getGridItemPosition());
				gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());
				gridItemOrderDO.setComailDOs(manifestDO.getComails());
				gridItemOrderDO = manifestCommonService.arrangeOrder(
						gridItemOrderDO, ManifestConstants.ACTION_SEARCH);

				if (gridItemOrderDO != null
						&& !StringUtil.isEmptyColletion(gridItemOrderDO
								.getConsignmentDOs())) {
					manifestDO.setConsignments(gridItemOrderDO
							.getConsignmentDOs());
				}
				if (gridItemOrderDO != null
						&& !StringUtil.isEmptyColletion(gridItemOrderDO
								.getComailDOs())) {
					manifestDO.setComails(gridItemOrderDO.getComailDOs());
				}
			}
			/***************** Sorting Grid Position End *****************/

			Set<ConsignmentDO> consignmentDOs = manifestDO.getConsignments();
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				isConsgComail = Boolean.TRUE;

				for (ConsignmentDO consignmentDO : consignmentDOs) {
					detailTO = InOGMDoxConverter
							.createInOGMDoxDetailTO(consignmentDO);
					/*
					 * detailTO.setConsignmentManifestId(consignmentDO
					 * .getConsignmentManifestId());
					 */
					// detailTO.setPosition(consignmentDO.getPosition());
					// set rcvd status & remarks
					detailTO.setIsCN(CommonConstants.YES);
					if (!StringUtil.isNull(detailTO.getManifestWeight())) {
						totalWt += detailTO.getManifestWeight();
					}
					rowcount++;
					detailTOs.add(detailTO);
				}
			}

			List coMailTOs = null;
			Set<ComailDO> comailDOs = manifestDO.getComails();

			if (!StringUtil.isEmptyColletion(comailDOs)) {
				coMailTOs = new ArrayList<>(comailDOs.size());
				isConsgComail = Boolean.TRUE;

				for (ComailDO comailDO : comailDOs) {
					detailTO = new InManifestOGMDetailTO();
					/*
					 * detailTO.setComailManifestId(comailManifestDO
					 * .getCoMailManifestId());
					 */
					detailTO.setComailId(comailDO.getCoMailId());
					detailTO.setComailNo(comailDO.getCoMailNo());
					detailTO.setIsCN(CommonConstants.NO);

					coMailTOs.add(detailTO);
				}
			}

			if (isConsgComail && !isOutManifest) {
				inManifestOGMTO.setIsManifested(CommonConstants.YES);
			}
			inManifestOGMTO.setIsInBplDoxPacket(isInBplDoxPacket);
			/*
			 * if (manifestDO != null &&
			 * StringUtil.isEmptyInteger(manifestDO.getNoOfElements())) {
			 * inManifestOGMTO.setIsInBplDoxPacket(Boolean.TRUE); }
			 */
			// Collections.sort(detailTOs);
			inManifestOGMTO.setTotalWt(Double.parseDouble(new DecimalFormat(
					"##.###").format(totalWt)));
			inManifestOGMTO.setRowCount(rowcount);
			inManifestOGMTO.setInManifestOGMDetailTOs(detailTOs);
			inManifestOGMTO.setInCoMailTOs(coMailTOs);

			// grid converter End
		}
		LOGGER.trace("InOGMDoxServiceImpl::getConsgManifestedDetails::END------------>:::::::");
		return inManifestOGMTO;
	}

	private ManifestDO getOtherManifestDO(ManifestBaseTO baseTO)
			throws CGSystemException {
		ManifestBaseTO manifestBaseTO4Out = new ManifestBaseTO();

		manifestBaseTO4Out.setManifestNumber(baseTO.getManifestNumber().trim());
		manifestBaseTO4Out.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
		manifestBaseTO4Out.setIsExcludeManifestType(Boolean.TRUE);
		manifestBaseTO4Out.setIsFetchProfileManifestDox(Boolean.TRUE);
		manifestBaseTO4Out.setIsNoOfElementNotNull(Boolean.TRUE);
		
		ManifestDO manifestDO4Out = null;
		
		manifestDO4Out = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(manifestBaseTO4Out);
		if (manifestDO4Out != null
				&& StringUtil.isEmptyColletion(manifestDO4Out
						.getConsignments())
				&& StringUtil.isEmptyColletion(manifestDO4Out.getComails()) && manifestDO4Out.getOriginOffice()!=null) {
			manifestBaseTO4Out.setLoggedInOfficeId(manifestDO4Out.getOriginOffice().getOfficeId());
			manifestDO4Out = inManifestCommonDAO
					.getManifestDetailsWithFetchProfile(manifestBaseTO4Out);
		}
		/*manifestDO4Out = inManifestCommonDAO
				.getManifestDetailsWithFetchProfileAscOrder(manifestBaseTO4Out);*/
		
		

		/*
		 * //problem in criteria not able to load data by below specific
		 * paramater so used named query
		 * manifestBaseTO4Out.setManifestType(CommonConstants
		 * .MANIFEST_TYPE_OUT);
		 * manifestBaseTO4Out.setProcessCode(InManifestConstants
		 * .PROCESS_CODE_IN_OGM);
		 * manifestBaseTO4Out.setUpdateProcessCode(InManifestConstants
		 * .PROCESS_CODE_IN_OGM); ManifestDO manifestDO4Out =
		 * inManifestCommonDAO .getManifestDtls(manifestBaseTO4Out);
		 */

		return manifestDO4Out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InOGMDoxService#
	 * saveOrUpdateInOGMDox(com.ff.manifest.inmanifest.InManifestOGMTO)
	 */
	@Override
	public InManifestOGMTO saveOrUpdateInOGMDox(InManifestOGMTO ogmDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("InOGMDoxServiceImpl::saveOrUpdateInOGMDox::START------------>:::::::");
		// String trasnStatus = CommonConstants.EMPTY_STRING;
		// try {

		// Check is Header Manifest already inManifested
		inManifestCommonService.isManifestHeaderInManifested(ogmDoxTO);

		if (ogmDoxTO.getConsignmentTypeTO() != null) {
			ogmDoxTO.getConsignmentTypeTO().setConsignmentCode(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		}

		List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
				.getConsignmentTypes(ogmDoxTO.getConsignmentTypeTO());
		// Setting Consignment type id
		if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
			ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs.get(0);
			ogmDoxTO.setConsignmentTypeTO(consignmentTypeTO);
		}
		// Setting process id
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		processTO = outManifestUniversalService.getProcess(processTO);
		ogmDoxTO.setProcessId(processTO.getProcessId());
		ogmDoxTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		ogmDoxTO.setProcessTO(processTO);
		ManifestDO manifestDO = new ManifestDO();

		// Prepare ManifestDO
		manifestDO = InOGMDoxConverter.createManifestConsignmentDO(ogmDoxTO,
				manifestDO);
		inManifestCommonService.getMnfstOpenTypeAndBplMnfstType(manifestDO);
		
		inManifestCommonService.setProductInConsg(manifestDO);
		//validate and mark consg as excess.
		inManifestCommonService.validateAndSetExcessConsgFlag(manifestDO);
		
		// Saving Manifest
		if (manifestDO != null) {
			//manifestDO = inManifestCommonDAO.saveOrUpdateManifest(manifestDO);

			//TODO Using merge due to NonUniqueObjectException: a different object with the same identifier value was already associated with the session: 
			manifestDO = inManifestCommonDAO.saveOrMergeManifest(manifestDO);
			
			//setting manifestId for TwoWayWrite
			InManifestUtils.setManifestId(ogmDoxTO, manifestDO);
		}

		/*List<ManifestProcessDO> manifestProcessDOs = new ArrayList<>();
		// Prepare ManifestProcessDO
		ManifestProcessDO manifestProcessDO = InManifestBaseConverter
				.inManifestProcessTransferObjConverter(ogmDoxTO);
		int noOfElements = 0;
		for (int i = 0; i < ogmDoxTO.getInManifestOGMDetailTOs().size(); i++) {
			if (StringUtils.isBlank(ogmDoxTO.getInManifestOGMDetailTOs().get(i)
					.getConsignmentNumber())) {
				continue;
			}
			noOfElements++;
		}
		for (int i = 0; i < ogmDoxTO.getCoMailTOs().length; i++) {
			if (StringUtils.isBlank(ogmDoxTO.getCoMailTOs()[i])) {
				continue;
			}
			noOfElements++;
		}

		if (!StringUtil.isEmptyInteger(ogmDoxTO.getLoggedInOfficeId())) {
			manifestProcessDO.setDestOfficeId(ogmDoxTO.getLoggedInOfficeId());
		}
		
		 * if
		 * (!StringUtil.isEmptyInteger(ogmDoxTO.getDestinationOfficeTO().getOfficeId
		 * ())) {
		 * manifestProcessDO.setDestOfficeId(ogmDoxTO.getDestinationOfficeTO
		 * ().getOfficeId()); }
		 
		manifestProcessDO.setManifestWeight(ogmDoxTO.getTotalManifestWeight());
		manifestProcessDO.setNoOfElements(noOfElements);
		if (StringUtils.isNotBlank(ogmDoxTO.getIsCoMail())
				&& (ogmDoxTO.getIsCoMail().equals(CommonConstants.YES) || ogmDoxTO
						.getIsCoMail().equals(CommonConstants.NO))) {
			manifestProcessDO.setContainsOnlyCoMail(ogmDoxTO.getIsCoMail());
		}

		manifestProcessDOs.add(manifestProcessDO);
		// Saving manifest process
		if (manifestProcessDO != null) {
			InManifestUtils.setDestOfficeAndCityToManifestProcess(manifestDO, manifestProcessDOs);
			inManifestCommonDAO.saveOrUpdateManifestProcess(manifestProcessDOs);
			
			//setting manifestProcessId for TwoWayWrite
			InManifestUtils.setManifestProcessId(ogmDoxTO, manifestProcessDOs.get(0));
		}*/
		ogmDoxTO.setUpdateProcessCode(InManifestConstants.PROCESS_CODE_IN_OGM);
		ogmDoxTO.setProcessCode(InManifestConstants.PROCESS_CODE_IN_OGM);
		inManifestCommonService.getLessExcessConsg(ogmDoxTO);

		/*
		 * if (isSaved) { { trasnStatus = CommonConstants.SUCCESS; } } else {
		 * trasnStatus = CommonConstants.FAILURE; }
		 */

		/*
		 * } catch (Exception e) { LOGGER.error(
		 * "Error occured in OutManifestDoxServiceImpl :: saveOrUpdateOutManifestDox()..:"
		 * + e.getMessage()); throw new CGSystemException(e); }
		 */
		LOGGER.debug("InOGMDoxServiceImpl::saveOrUpdateInOGMDox::END------------>:::::::");
		return ogmDoxTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InOGMDoxService#saveOrUpdateComail
	 * (com.ff.domain.manifest.ComailDO)
	 */
	@Override
	public ComailDO saveOrUpdateComail(ComailDO comailDO)
			throws CGBusinessException, CGSystemException {
		return inOgmDoxDAO.saveOrUpdateComail(comailDO);
	}

	@Override
	public InManifestOGMTO isConsgManifested(InManifestOGMTO ogmTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InOGMDoxServiceImpl::isConsgManifested::START------------>:::::::");
		List<InManifestOGMDetailTO> inManifestOGMDetailTOs = new ArrayList<>();

		ogmTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		ConsignmentManifestDO consgManifestDO = inOgmDoxDAO
				.isConsgManifested(ogmTO);
		InManifestOGMDetailTO detailTO = null;
		if (consgManifestDO.getConsignment() == null) {
			ogmTO.setUpdateProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
			consgManifestDO = inOgmDoxDAO.isConsgManifested(ogmTO);
			if (consgManifestDO != null) {

				ConsignmentDO consgDO = consgManifestDO.getConsignment();
				if (consgDO != null) {
					detailTO = InOGMDoxConverter
							.createInOGMDoxDetailTO(consgDO);
					detailTO.setConsignmentManifestId(consgManifestDO
							.getConsignmentManifestId());
					inManifestOGMDetailTOs.add(detailTO);
					ogmTO.setInManifestOGMDetailTOs(inManifestOGMDetailTOs);
				}
			}
		} else {
			String errorMsg = inManifestCommonService
					.getErrorMessages(InManifestConstants.CONSIGNMENT_ALREADY_EXSITS);
			ogmTO.setTransMsg(errorMsg);
		}

		LOGGER.trace("InOGMDoxServiceImpl::isConsgManifested::END------------>:::::::");
		return ogmTO;
	}

	@Override
	public ConsignmentTO getConsgDetails(InBagManifestTO inBagManifestTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InOGMDoxServiceImpl::getConsgDetails::START------------>:::::::");
		ConsignmentTO consignmentTO1 = null;

		// validate consgNo is InManifested
//		inBagManifestTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
//		inBagManifestTO.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
//		inBagManifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
//		if (inManifestCommonDAO.isConsgNoInManifested(inBagManifestTO)) {
//			ExceptionUtil
//					.prepareBusinessException(InManifestConstants.CONSIGNMENT_NO_ALREADY_IN_MANIFESTED);
//			/*
//			 * throw new CGBusinessException(
//			 * InManifestConstants.CONSIGNMENT_NO_ALREADY_IN_MANIFESTED);
//			 */
//		}

		inManifestCommonService.validateConsgNoInManifested(inBagManifestTO);
		
		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		consignmentTypeTO
				.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);

		ConsignmentTO consignmentTO = new ConsignmentTO();
		consignmentTO.setConsgNo(inBagManifestTO.getConsgNumber());
		consignmentTO.setTypeTO(consignmentTypeTO);

		ConsignmentDO consignmentDO = inManifestCommonDAO
				.getConsignmentDetails(consignmentTO);
		if (consignmentDO != null) {
			consignmentTO1 = convertConsignmentDOToTO(consignmentDO);
		} else {
			// Integer consignmentId =
			// inManifestCommonDAO.getConsignmentIdByConsgNo(inManifestTO);
			Integer consignmentId = inManifestCommonService
					.getConsignmentIdByConsgNo(consignmentTO);
			if (!StringUtil.isEmptyInteger(consignmentId)) {
				ExceptionUtil
						.prepareBusinessException(InManifestConstants.ONLY_DOX_TYPE_CONSIGNMENT_ALLOWED);
				/*
				 * throw new CGBusinessException(
				 * InManifestConstants.ONLY_DOX_TYPE_CONSIGNMENT_ALLOWED);
				 */
			}
			// new consg
			consignmentTO1 = new ConsignmentTO();
			consignmentTO1.setIsNewConsignment(Boolean.TRUE);
		}

		LOGGER.trace("InOGMDoxServiceImpl::getConsgDetails::END------------>:::::::");
		return consignmentTO1;
	}

	private ConsignmentTO convertConsignmentDOToTO(ConsignmentDO consgDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InOGMDoxServiceImpl::convertConsignmentDOToTO::START------------>:::::::");
		ConsignmentTO consgTO = new ConsignmentTO();
		PincodeDO pincodeDO = consgDO.getDestPincodeId();
		PincodeTO pincodeTO = new PincodeTO();
		CityTO cityTO = new CityTO();
		if (pincodeDO != null) {
			CGObjectConverter.createToFromDomain(pincodeDO, pincodeTO);
			cityTO = geographyCommonService.getCity(pincodeDO.getPincode());
		}
		CGObjectConverter.createToFromDomain(consgDO, consgTO);
		consgTO.setDestPincode(pincodeTO);
		consgTO.setDestCity(cityTO);
		LOGGER.trace("InOGMDoxServiceImpl::convertConsignmentDOToTO::END------------>:::::::");
		return consgTO;
	}

	@Override
	public InManifestValidationTO validateCoMailNumber(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InOGMDoxServiceImpl::validateCoMailNumber::START------------>:::::::");
		// validate consgNo is InManifested
		inManifestTO
				.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		inManifestTO
				.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX);
		inManifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		if (inManifestCommonDAO.isCoMailNumInManifested(inManifestTO)) {
			ExceptionUtil
					.prepareBusinessException(InManifestConstants.COMAIL_NO_ALREADY_IN_MANIFESTED);
			/*
			 * throw new CGBusinessException(
			 * InManifestConstants.COMAIL_NO_ALREADY_IN_MANIFESTED);
			 */
		}

		// getComailIdByNo
		Integer coMailId = inManifestCommonDAO.getCoMailIdByNo(inManifestTO
				.getCoMailNo());

		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();
		inManifestValidationTO.setCoMailId(coMailId);

		LOGGER.trace("InOGMDoxServiceImpl::validateCoMailNumber::END------------>:::::::");
		return inManifestValidationTO;
	}
}
