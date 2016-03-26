package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestParcelDetailsTO;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.converter.OutManifestParcelConverter;

/**
 * The Class OutManifestParcelServiceImpl.
 */
public class OutManifestParcelServiceImpl implements OutManifestParcelService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestParcelServiceImpl.class);

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/** The out manifest common service. */
	private OutManifestCommonService outManifestCommonService;

	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the new out manifest universal service
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * Sets the manifest universal dao.
	 * 
	 * @param manifestUniversalDAO
	 *            the new manifest universal dao
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

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestParcelService#getConsignmentDtls
	 * (com.ff.manifest.ManifestFactoryTO)
	 */
	@Override
	public OutManifestParcelDetailsTO getConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO,
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		OutManifestParcelDetailsTO outManifestParcelDtlTO = null;
		// Get the booking details
		outManifestParcelDtlTO = getBookingDtlsByConsignmentNo(
				manifestFactoryTO, cnValidateTO);
		/*
		 * if (StringUtil.isNull(outManifestParcelDtlTO)) { // To get the
		 * incoming details if booking details are not found }
		 */
		return outManifestParcelDtlTO;
	}

	/**
	 * Gets the booking dtls by consignment no.
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return the booking dtls by consignment no
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private OutManifestParcelDetailsTO getBookingDtlsByConsignmentNo(
			ManifestFactoryTO manifestFactoryTO,
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		OutManifestParcelDetailsTO outManifestParcelDtlTO = null;
		// Ami Added on 2009 starts

		if (!StringUtil.isNull(cnValidateTO.getConsignmentModificationTO())) {
			outManifestParcelDtlTO = OutManifestParcelConverter
					.setBookingDtlsToOutManifestParcelDtl(cnValidateTO
							.getConsignmentModificationTO());
			outManifestParcelDtlTO.setChildCn(cnValidateTO.getChildCNDetails());
			if (!StringUtil.isNull(cnValidateTO.getConsgTO())) {
				ConsignmentTO cnTO = cnValidateTO.getConsgTO();
				if (!StringUtil.isEmptyDouble(cnTO.getCodAmt())) {
					outManifestParcelDtlTO.setCodAmt(cnTO.getCodAmt());
				}
				if (!StringUtil.isEmptyDouble(cnTO.getTopayAmt())) {
					outManifestParcelDtlTO.setToPayAmt(cnTO.getTopayAmt());
				}
			}

		}
		// Ami Added on 2009 ends

		return outManifestParcelDtlTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestParcelService#getInsuredByDtls()
	 */
	@Override
	public List<InsuredByTO> getInsuredByDtls() throws CGBusinessException,
			CGSystemException {
		return outManifestCommonService.getInsuarnceBy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestParcelService#getPaperWorks(com
	 * .ff.serviceOfferring.CNPaperWorksTO)
	 */
	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException {
		// Getting consignment type config
		List<CNPaperWorksTO> paperWorks = null;
		ConsignmentTypeConfigTO consgTypeConfigTO = new ConsignmentTypeConfigTO();
		consgTypeConfigTO.setDeclaredValue(paperWorkValidationTO
				.getDeclatedValue());
		consgTypeConfigTO.setDocType(paperWorkValidationTO.getDocType());
		ConsignmentTypeConfigTO consgTypeConfig = outManifestCommonService
				.getConsgTypeConfigDtls(consgTypeConfigTO);
		if (!StringUtil.isNull(consgTypeConfig)) {
			if (paperWorkValidationTO.getDeclatedValue().doubleValue() >= consgTypeConfig
					.getDeclaredValue().doubleValue()) {
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						consgTypeConfig.getIsPaperworkMandatory())) {
					paperWorks = outManifestCommonService
							.getPaperWorks(paperWorkValidationTO);
				}
			}
		}
		return paperWorks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestParcelService#searchManifestDtls
	 * (com.ff.manifest.ManifestInputs)
	 */
	@Override
	public OutManifestParcelTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = null;
		OutManifestParcelTO parcelTO = null;
		LOGGER.trace("OutManifestParcelServiceImpl :: searchManifestDtls() :: start------------>:::::::");
		Long startTime = System.currentTimeMillis();
		// manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO);
		manifestDO = outManifestCommonService.prepareManifestDO(manifestTO);
		manifestDO = manifestCommonService.getParcelManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			/* Arrange in order */
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());
			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);
			manifestDO.setConsignments(gridItemOrderDO.getConsignmentDOs());

			parcelTO = OutManifestParcelConverter
					.outManifestParcelDomainConverter(manifestDO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}

		// Manifest Process
		/*
		 * if (!StringUtils.equalsIgnoreCase(CommonConstants.PROCESS_DISPATCH,
		 * manifestTO.getManifestProcessCode())) { ManifestProcessDO
		 * manifestProcessDO = manifestUniversalDAO
		 * .getManifestProcess(manifestTO); List<ManifestProcessTO>
		 * manifestProcessTos = null; ManifestProcessTO manifestProcessTo =
		 * null; if (!StringUtil.isNull(manifestProcessDO)) { manifestProcessTos
		 * = OutManifestBaseConverter
		 * .manifestProcessTransferObjConverter(manifestProcessDO); if
		 * (!StringUtil.isEmptyList(manifestProcessTos)) { manifestProcessTo =
		 * manifestProcessTos.get(0);
		 * parcelTO.setManifestProcessTo(manifestProcessTo); }
		 * 
		 * } }
		 */
		/*
		 * String rfNo = outManifestUniversalService
		 * .getBagRfNoByRfId(parcelTO.getBagRFID()); parcelTO.setRfidNo(rfNo);
		 */
		Long endTime = System.currentTimeMillis();
		LOGGER.debug("TIMER:" + (endTime - startTime));
		LOGGER.trace("OutManifestParcelServiceImpl :: searchManifestDtls() :: END------------>:::::::");
		return parcelTO;
	}

	public OutManifestParcelDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {

		OutManifestParcelDetailsTO outManifstPpxDetailsTO = null;

		List<ConsignmentTO> consignmtTOs = outManifestUniversalService
				.getConsgDtls(manifestFactoryTO);

		outManifstPpxDetailsTO = convertConsgDtlsTOListToOutManifestPpxDetailsTO(
				consignmtTOs, manifestFactoryTO);

		return outManifstPpxDetailsTO;
	}

	private OutManifestParcelDetailsTO convertConsgDtlsTOListToOutManifestPpxDetailsTO(
			List<ConsignmentTO> consignmentTOs,
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		OutManifestParcelDetailsTO outManifestPpxDetailsTO = null;

		if (!StringUtil.isEmptyList(consignmentTOs)) {
			outManifestPpxDetailsTO = (OutManifestParcelDetailsTO) OutManifestParcelConverter
					.outManifestPpxGridDetailsForInManifConsg(consignmentTOs
							.get(0));
		}
		return outManifestPpxDetailsTO;
	}

	@Override
	public String saveOrUpdateOutManifestParcel(
			OutManifestParcelTO outmanifestParcelTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX()  :: START------------>:::::::");

		/** Define variables */
		Boolean searchedManifest = Boolean.FALSE;
		ManifestDO manifestDO = null;
		ManifestDO manifest = null;
		/*
		 * ManifestProcessDO manifestProcessDO = new ManifestProcessDO();
		 * ManifestProcessDO manifestProcess = null;
		 */
		List<BookingDO> allBooking = new ArrayList<BookingDO>();
		List<ConsignmentDO> bookingConsignment = new ArrayList<ConsignmentDO>();
		// List<ConsignmentBillingRateDO> consgBillingRateDOs = new
		// ArrayList<ConsignmentBillingRateDO>();
		Set<ConsignmentDO> allConsignments = new LinkedHashSet<ConsignmentDO>();

		manifest = prepareTransferBeforeSave(outmanifestParcelTO, manifest,
				allBooking, bookingConsignment, allConsignments);

		/* Search manifest Process */
		/*
		 * ManifestInputs manifestTO =
		 * prepareForManifestProcess(outmanifestParcelTO);
		 */
		/*
		 * List<ManifestProcessDO> manifestProcessDOs = manifestUniversalDAO
		 * .getManifestProcessDtls(manifestTO);
		 */

		/* prepare Manifest Process */
		/*
		 * if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) { manifestProcess
		 * = manifestProcessDOs.get(0); } if (StringUtil.isNull(manifestProcess)
		 * || StringUtils.isEmpty(manifestProcess.getManifestProcessNo())) { //
		 * Setting process id & process code ProcessTO processTO = new
		 * ProcessTO(); processTO
		 * .setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
		 * processTO = outManifestUniversalService.getProcess(processTO);
		 * outmanifestParcelTO.setProcessId(processTO.getProcessId());
		 * outmanifestParcelTO.setProcessCode(processTO.getProcessCode());
		 * 
		 * OfficeTO officeTO = new OfficeTO();
		 * officeTO.setOfficeId(outmanifestParcelTO.getLoginOfficeId()); if
		 * (!StringUtil.isStringEmpty(outmanifestParcelTO.getOfficeCode())) {
		 * officeTO.setOfficeCode(outmanifestParcelTO.getOfficeCode()); }
		 * 
		 * String processNumber = outManifestCommonService
		 * .createProcessNumber(processTO, officeTO);
		 * outmanifestParcelTO.setProcessNo(processNumber); }
		 */
		/*
		 * manifestProcessDOs = OutManifestParcelConverter
		 * .prepareManifestProcessDOList(outmanifestParcelTO); manifestProcessDO
		 * = manifestProcessDOs.get(0); if (!StringUtil.isNull(manifestProcess))
		 * { manifestProcessDO.setManifestProcessId(manifestProcess
		 * .getManifestProcessId());
		 * manifestProcessDO.setManifestProcessNo(manifestProcess
		 * .getManifestProcessNo()); } manifestProcessDO
		 * .setNoOfElements(outmanifestParcelTO.getNoOfElements());
		 */

		/**
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(manifest.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}

		/** If manifest is not already searched i.e. the ID is not set */

		manifest.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
		// manifestDO = manifestCommonService.getParcelManifest(manifest);
		manifestDO = manifestCommonService.getManifestForCreation(manifest);

		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				LOGGER.error("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: Manifest already Closed (throwing Business Exception) ------------>:::::::");
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				/**
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				LOGGER.error("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: Manifest already exist (throwing Business Exception) ------------>:::::::");
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
			// Manually setting UI status
			/*
			 * manifestDO.setManifestStatus(outmanifestParcelTO
			 * .getManifestStatus());
			 */
			manifestDO.setManifestWeight(outmanifestParcelTO.getFinalWeight());

		}
		if (StringUtil.isNull(manifestDO)) {
			manifestDO = manifest;
		}

		/**
		 * Call the method to update booking and create Consignments for PICKUP
		 * - BULK
		 */
		Set<ConsignmentDO> newConsignment = new LinkedHashSet<ConsignmentDO>();

		// Setting process id
		ProcessTO processTO = new ProcessTO();
		processTO
				.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
		processTO = outManifestUniversalService.getProcess(processTO);
		ProcessDO updatedProcessDO = new ProcessDO();
		if (!StringUtil.isEmptyInteger(processTO.getProcessId())) {
			updatedProcessDO.setProcessId(processTO.getProcessId());
			updatedProcessDO.setProcessCode(processTO.getProcessCode());
		}

		for (ConsignmentDO consignmentDO : allConsignments) {
			if (StringUtil.isEmptyInteger(consignmentDO.getConsgId())) {
				for (ConsignmentDO pickConsg : bookingConsignment) {
					if (pickConsg.getConsgNo().equals(
							consignmentDO.getConsgNo())) {
						consignmentDO.setConsgId(pickConsg.getConsgId());
						break;
					}
				}/* END of INNER FOR LOOP */
			}/* END of IF BLOCK */
			consignmentDO.setDtFromOpsman(CommonConstants.DT_FROM_OPSMAN_R);
			consignmentDO.setDtToBranch(CommonConstants.NO);
			consignmentDO.setDtToCentral(CommonConstants.CHARACTER_R);
			consignmentDO.setDtUpdateToCentral(CommonConstants.NO);
			consignmentDO.setDtToOpsman(CommonConstants.NO);
			/* Process code */
			if (StringUtil.equals(outmanifestParcelTO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				consignmentDO.setUpdatedProcess(updatedProcessDO);
				consignmentDO.setDtToCentral(CommonConstants.NO);
			}

			if (consignmentDO.isPickedup()
					&& CGCollectionUtils.isEmpty(consignmentDO
							.getConsgRateDtls())) {
				LOGGER.error("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: Consignment rate is not exist in consignment before save (throwing Business Exception) ------------>:::::::"
						+ consignmentDO.getConsgNo());
				throw new CGBusinessException(
						ManifestErrorCodesConstants.RATE_NOT_CALC_FOR_CN_2
								+ "#" + consignmentDO.getConsgNo());
			} else {
				LOGGER.info("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: Consignment rate details is exist in consignment before save ------------>:::::::"
						+ consignmentDO.getConsgNo()
						+ " is Picked up consg:["
						+ consignmentDO.isPickedup()
						+ "] and rate Size "
						+ (CGCollectionUtils.isEmpty(consignmentDO
								.getConsgRateDtls()) ? "0" : consignmentDO
								.getConsgRateDtls().size()));
			}
			newConsignment.add(consignmentDO);
		}

		/** updating booking status */
		List<BookingDO> newBooking = new ArrayList<BookingDO>();
		for (BookingDO bookingDO : allBooking) {
			/* Process code */
			if (StringUtil.equals(outmanifestParcelTO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				bookingDO.setUpdatedProcess(updatedProcessDO);
				bookingDO.setDtToCentral(CommonConstants.NO);
			} else {
				bookingDO.setDtToCentral(CommonConstants.CHARACTER_R);
			}
			newBooking.add(bookingDO);
		}

		manifestDO.setConsignments(newConsignment);
		manifestDO.setNoOfElements(outmanifestParcelTO.getNoOfElements());
		manifestDO.setManifestStatus(outmanifestParcelTO.getManifestStatus());
		/* Set Grid Position */
		GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
		if (!StringUtil.isEmpty(outmanifestParcelTO.getConsgNos())) {
			gridItemOrderDO.setConsignments(outmanifestParcelTO.getConsgNos());
		}
		gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
				ManifestConstants.ACTION_SAVE);
		manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition()
				.toString());

		/** Save Booking CNs */
		boolean isBookingSave = manifestCommonService
				.saveOrUpdateBooking(newBooking);
		/** Update Pickup runsheet header details */
		boolean isPickupUpdated = Boolean.FALSE;
		if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
				OutManifestConstants.CLOSE)) {
			isPickupUpdated = OutManifestBaseConverter
					.saveOrUpdatePickupRunsheetHeaderDetails(
							manifestCommonService, newBooking,
							outmanifestParcelTO);
		}

		if (!isBookingSave && !isPickupUpdated) {
			LOGGER.error("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: Manifest not saved (throwing Business Exception) ------------>:::::::");
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}
		/** SAVING MANIFST and MANIFEST PROCESS */

		/* Setting Created and updated date in manifest and manifestProcess */
		manifestDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
		/*
		 * manifestProcessDO.setCreatedDate(DateUtil.getCurrentDate());
		 * manifestProcessDO.setUpdatedDate(DateUtil.getCurrentDate());
		 */
		// to set DT_TO_CENTRAL Y while saving
		ManifestUtil.validateAndSetTwoWayWriteFlag(manifestDO);
		/* ManifestUtil.validateAndSetTwoWayWriteFlag(manifestProcessDO); */

		boolean result = manifestCommonService.saveManifest(manifestDO);

		// for two way write
		outmanifestParcelTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*
		 * outmanifestParcelTO.setManifestProcessId(manifestProcessDO.
		 * getManifestProcessId());
		 */

		if (!result) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}

		/** SAVE OR UPDATE CONSIGNMENT RATE */
		/*
		 * manifestCommonService.saveOrUpdateConsgBillingRateDtls(
		 * consgBillingRateDOs, manifestDO.getConsignments());
		 */

		LOGGER.trace("OutManifestParcelServiceImpl :: saveOrUpdateOutManifestPPX() :: END------------>:::::::");
		return manifestDO.getManifestStatus();
	}

	/**
	 * @param outmanifestDoxTO
	 * @return ManifestInputs
	 */
	private ManifestInputs prepareForManifestProcess(
			OutManifestParcelTO outmanifestParcelTO) {
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(outmanifestParcelTO.getManifestNo());
		manifestInputs.setLoginOfficeId(outmanifestParcelTO.getLoginOfficeId());
		manifestInputs.setManifestType("O");
		manifestInputs.setManifestDirection(outmanifestParcelTO
				.getManifestDirection());
		return manifestInputs;
	}

	/**
	 * @param outmanifestDoxTO
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @param allBooking
	 * @param bookingConsignment
	 * @param allConsignments
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ManifestDO prepareTransferBeforeSave(
			OutManifestParcelTO outmanifestParcelTO, ManifestDO manifestDO,
			List<BookingDO> allBooking, List<ConsignmentDO> bookingConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		/* Populating the out manifest dox detail TO */
		OutManifestParcelConverter
				.prepareOutManifestBplDtls(outmanifestParcelTO);
		manifestDO = prepareManifestForOutPPX(manifestDO, outmanifestParcelTO,
				allBooking, bookingConsignment, allConsignments);
		manifestDO.setConsignments(allConsignments);
		return manifestDO;
	}

	/**
	 * To prepare manifest for out manifest parcel
	 * 
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @param outmanifestParcelTO
	 * @param allBooking
	 * @param bookingConsignment
	 * @param allConsignments
	 * @param consgBillingRateDOs
	 * @return manifestDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ManifestDO prepareManifestForOutPPX(ManifestDO manifestDO,
			OutManifestParcelTO outmanifestParcelTO,
			List<BookingDO> allBooking, List<ConsignmentDO> bookingConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		List<BookingConsignmentDO> bookingConsignmentDOs = null;
		BookingDO bookingDO = null;
		/* prepare Manifest for search */
		manifestDO = OutManifestBaseConverter
				.prepateManifestDO(outmanifestParcelTO);

		/** Read All the bookings and consignments */
		bookingConsignmentDOs = outManifestCommonService
				.readAllConsignments(outmanifestParcelTO);

		for (int i = 0; i < outmanifestParcelTO.getConsgNos().length; i++) {
			for (BookingConsignmentDO bookingConsignmentDO : bookingConsignmentDOs) {

				if (!StringUtil.isNull(bookingConsignmentDO.getBookingDO())
						&& bookingConsignmentDO
								.getBookingDO()
								.getUpdatedProcess()
								.getProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_PICKUP)
						&& StringUtil.equals(
								outmanifestParcelTO.getConsgNos()[i],
								bookingConsignmentDO.getBookingDO()
										.getConsgNumber())) { // Pickup
					bookingDO = bookingConsignmentDO.getBookingDO();

					/* Consignment Type */
					ConsignmentTypeDO consgType = new ConsignmentTypeDO();
					consgType.setConsignmentId(outmanifestParcelTO
							.getConsignmentTypeTO().getConsignmentId());
					consgType
							.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
					bookingDO.setConsgTypeId(consgType);

					/* prepare booking */
					prepareBookingDO(bookingDO, outmanifestParcelTO, i);
					bookingDO.setDtFromOpsman(CommonConstants.DT_FROM_OPSMAN_R);
					bookingDO.setDtToBranch(CommonConstants.NO);
					bookingDO.setDtToCentral(CommonConstants.CHARACTER_R);
					bookingDO.setDtUpdateToCentral(CommonConstants.NO);
					bookingDO.setDtToOpsman(CommonConstants.NO);

					/* Setting update date in booking */
					bookingDO.setUpdatedDate(DateUtil.getCurrentDate());

					allBooking.add(bookingDO);

					/** prepare consignment */
					ConsignmentDO consignmentDO = bookingConsignmentDO
							.getConsignmentDO();
					if (StringUtil.isNull(consignmentDO)) {
						consignmentDO = new ConsignmentDO();
					}
					/* Setting consignment number */
					consignmentDO
							.setConsgNo(outmanifestParcelTO.getConsgNos()[i]);
					// Set pickedup flag
					consignmentDO.setPickedup(true);
					/* Setting consignment type */
					consignmentDO.setConsgType(consgType);

					/* Setting Billing Flags */
					if ((!StringUtil.isNull(consignmentDO.getDestPincodeId()) && (!outmanifestParcelTO
							.getPincodeIds()[i].equals(consignmentDO
							.getDestPincodeId().getPincodeId())))
							|| (!StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight()) && (consignmentDO
									.getFinalWeight() < outmanifestParcelTO
									.getWeights()[i]))) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN);
					} else if (StringUtil.isNull(consignmentDO
							.getDestPincodeId())
							|| StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight())) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
					}

					/* Setting Pincode */
					if (!StringUtil.isEmptyInteger(outmanifestParcelTO
							.getPincodeIds()[i])) {
						PincodeDO pincodeDO = new PincodeDO();
						pincodeDO.setPincodeId(outmanifestParcelTO
								.getPincodeIds()[i]);
						consignmentDO.setDestPincodeId(pincodeDO);
					}

					/* Setting Final and Actual Weight */
					consignmentDO.setActualWeight(outmanifestParcelTO
							.getWeights()[i]);
					consignmentDO.setFinalWeight(outmanifestParcelTO
							.getWeights()[i]);

					/* Process code */
					if (StringUtil.equals(
							outmanifestParcelTO.getManifestStatus(),
							OutManifestConstants.CLOSE)) {
						if (!StringUtil.isEmptyInteger(outmanifestParcelTO
								.getProcessId())) {
							ProcessDO updatedProcessDO = new ProcessDO();
							updatedProcessDO.setProcessId(outmanifestParcelTO
									.getProcessId());
							updatedProcessDO.setProcessCode(outmanifestParcelTO
									.getProcessCode());
							bookingDO.setUpdatedProcess(updatedProcessDO);
							consignmentDO.setUpdatedProcess(updatedProcessDO);
						}
					} else {
						ProcessDO updatedProcessDO = bookingDO
								.getUpdatedProcess();
						consignmentDO.setUpdatedProcess(updatedProcessDO);
					}

					/* Setting customer to consignment */
					if (!StringUtil.isNull(bookingDO.getCustomerId())) {
						consignmentDO.setCustomer(bookingDO.getCustomerId()
								.getCustomerId());
						/* Get and Set Rate Type */
						if (!StringUtil.isNull(bookingDO.getCustomerId()
								.getCustomerCategoryDO())) {
							String rateCustCatCode = bookingDO.getCustomerId()
									.getCustomerCategoryDO()
									.getRateCustomerCategoryCode();
							String rateType = OutManifestBaseConverter
									.getRateType(rateCustCatCode);
							consignmentDO.setRateType(rateType);
						}
					}

					/* Setting consignee */
					if (!StringUtil.isStringEmpty(outmanifestParcelTO
							.getMobileNos()[i])) {
						ConsigneeConsignorDO consignee = bookingDO
								.getConsigneeId();
						consignee
								.setMobile(outmanifestParcelTO.getMobileNos()[i]);
						consignmentDO.setConsignee(consignee);
					}
					/* Set Consignor details */
					ConsigneeConsignorDO consignor = null;
					if (StringUtil.isNull(consignmentDO.getConsignor())
							&& !StringUtil.isNull(bookingDO)
							&& !StringUtil.isNull(bookingDO.getCustomerId())) {
						consignor = outManifestCommonService
								.setConsignorDetailsFromCustomer(bookingDO
										.getCustomerId());
						consignmentDO.setConsignor(consignor);
					}

					/* Setting status */
					consignmentDO
							.setConsgStatus(BookingConstants.BOOKING_NORMAL_PROCESS);

					/* Origin office */
					consignmentDO.setOrgOffId(outmanifestParcelTO
							.getLoginOfficeId());

					/* Operation Level */
					ConsignmentTO consigmentTO = new ConsignmentTO();
					consigmentTO.setOrgOffId(outmanifestParcelTO
							.getLoginOfficeId());
					consigmentTO.setDestOffice(null);
					if (outmanifestParcelTO.getDestinationOfficeId() != null) {
						OfficeTO destoffTO = new OfficeTO();
						destoffTO.setOfficeId(outmanifestParcelTO
								.getDestinationOfficeId());
						consigmentTO.setDestOffice(destoffTO);
					}
					OfficeTO offTO = new OfficeTO();
					offTO.setOfficeId(outmanifestParcelTO.getLoginOfficeId());
					/*
					 * Integer operatingLevel = outManifestCommonService
					 * .getConsgOperatingLevel(consigmentTO, offTO);
					 * consignmentDO.setOperatingLevel(operatingLevel);
					 */

					// Set Product added by preeti
					if (!StringUtil.isStringEmpty(consignmentDO.getConsgNo())) {
						String cnSeries = CommonConstants.EMPTY_STRING;
						Character cnSeriesChar = consignmentDO.getConsgNo()
								.substring(4, 5).toCharArray()[0];
						if (Character.isDigit(cnSeriesChar)) {
							cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
						} else {
							cnSeries = cnSeriesChar.toString();
						}
						ProductTO productTO = outManifestCommonService
								.getProductByConsgSeries(cnSeries);
						if (!StringUtil.isNull(productTO)) {
							if (StringUtil.isNull(consignmentDO.getProductId())) {
								consignmentDO.setProductId(productTO
										.getProductId());
							}
						}
					}

					/*
					 * operating level needs to be set in case of
					 * weight/destination changes
					 */
					consignmentDO.setOperatingOffice(outmanifestParcelTO
							.getLoginOfficeId());

					/* Prepare consignment DO */
					prepareConsgDO(consignmentDO, outmanifestParcelTO, i);

					// Set Event Date
					if (StringUtil.isNull(consignmentDO.getEventDate())) {
						consignmentDO.setEventDate(DateUtil.getCurrentDate());
					} else if (!StringUtil.isNull(bookingDO.getBookingDate())) {
						consignmentDO.setEventDate(bookingDO.getBookingDate());
					}
					bookingConsignment.add(consignmentDO);
					allConsignments.add(consignmentDO);

					/** Rate Integration with out manifest parcel. */

					// calculate rate for bulk booking CN
					ConsignmentBillingRateDO consgBillingRateDO = OutManifestBaseConverter
							.calcRateForOutManifest(outmanifestParcelTO, i);
					if (!StringUtil.isNull(consgBillingRateDO)) {
						// Set Topay amount for T-Series
						if (StringUtil.endsWithIgnoreCase(consignmentDO
								.getConsgNo().substring(4, 5),
								OutManifestConstants.CONSG_SERIES_T)) {
							consignmentDO.setTopayAmt(consgBillingRateDO
									.getGrandTotalIncludingTax());
						}
						/** Set Created by And Created Date In Rate */
						if (!StringUtil.isEmptyInteger(outmanifestParcelTO
								.getCreatedBy())) {
							consgBillingRateDO.setCreatedBy(outmanifestParcelTO
									.getCreatedBy());
						}
						if (!StringUtil.isEmptyInteger(outmanifestParcelTO
								.getUpdatedBy())) {
							consgBillingRateDO.setUpdatedBy(outmanifestParcelTO
									.getUpdatedBy());
						}
						consgBillingRateDO.setCreatedDate(DateUtil
								.getCurrentDate());
						consgBillingRateDO.setUpdatedDate(DateUtil
								.getCurrentDate());

						consgBillingRateDO.setConsignmentDO(consignmentDO);
						// consgBillingRateDOs.add(consgBillingRateDO);

						// Setting Booking Rates.
						Set<ConsignmentBillingRateDO> newConsgRateDtls = new HashSet<ConsignmentBillingRateDO>();
						Set<ConsignmentBillingRateDO> consgRateDtls = consignmentDO
								.getConsgRateDtls();
						if (!CGCollectionUtils.isEmpty(consgRateDtls)) {
							for (ConsignmentBillingRateDO consignmentBillingRateDO : consgRateDtls) {
								if (consignmentBillingRateDO
										.getRateCalculatedFor()
										.equalsIgnoreCase("B")) {
									consgBillingRateDO
											.setConsignmentRateId(consignmentBillingRateDO
													.getConsignmentRateId());
									newConsgRateDtls.add(consgBillingRateDO);
								} else {
									newConsgRateDtls
											.add(consignmentBillingRateDO);
								}
							}
						} else {
							newConsgRateDtls.add(consgBillingRateDO);
						}
						consignmentDO.setConsgRateDtls(newConsgRateDtls);
					}

					// Setting created and updated by user and date
					if (!StringUtil.isEmptyInteger((outmanifestParcelTO
							.getCreatedBy()))) {
						consignmentDO.setCreatedBy(outmanifestParcelTO
								.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger((outmanifestParcelTO
							.getUpdatedBy()))) {
						consignmentDO.setUpdatedBy(outmanifestParcelTO
								.getUpdatedBy());
						bookingDO.setUpdatedBy(outmanifestParcelTO
								.getUpdatedBy());
					}
					consignmentDO.setCreatedDate(DateUtil.getCurrentDate());
					consignmentDO.setUpdatedDate(DateUtil.getCurrentDate());
				} else if (!StringUtil.isNull(bookingConsignmentDO
						.getConsignmentDO())
						&& StringUtil.equals(
								outmanifestParcelTO.getConsgNos()[i],
								bookingConsignmentDO.getConsignmentDO()
										.getConsgNo())
						&& (!bookingConsignmentDO.getConsignmentDO()
								.getUpdatedProcess().getProcessCode()
								.equalsIgnoreCase("UPPU"))) {// Booking
					/* Modified Consignment */
					ConsignmentDO consignmentDO = bookingConsignmentDO
							.getConsignmentDO();

					/* Setting Billing Flags */
					if ((!StringUtil.isNull(consignmentDO.getDestPincodeId()) && (!outmanifestParcelTO
							.getPincodeIds()[i].equals(consignmentDO
							.getDestPincodeId().getPincodeId())))
							|| (!StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight()) && (consignmentDO
									.getFinalWeight() < outmanifestParcelTO
									.getWeights()[i]))) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN);
					} else if (StringUtil.isNull(consignmentDO
							.getDestPincodeId())
							|| StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight())) {
						manifestCommonService.updateBillingFlagsInConsignment(
								consignmentDO,
								CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
					}
					/* Setting Process */
					if (StringUtil.equals(
							outmanifestParcelTO.getManifestStatus(),
							OutManifestConstants.CLOSE)) {
						if (!StringUtil.isEmptyInteger(outmanifestParcelTO
								.getProcessId())) {
							ProcessDO updatedProcessDO = new ProcessDO();
							updatedProcessDO.setProcessId(outmanifestParcelTO
									.getProcessId());
							updatedProcessDO.setProcessCode(outmanifestParcelTO
									.getProcessCode());
							consignmentDO.setUpdatedProcess(updatedProcessDO);
						}
					}

					/* Prepare consignment DO */
					prepareConsgDO(consignmentDO, outmanifestParcelTO, i);

					allConsignments.add(consignmentDO);

					// calculate rate for bulk booking CN
					/*
					 * ConsignmentBillingRateDO consgBillingRateDO =
					 * OutManifestBaseConverter
					 * .calcRateForOutManifest(outmanifestParcelTO, i); if
					 * (!StringUtil.isNull(consgBillingRateDO)) { // Set Topay
					 * amount for T-Series if
					 * (StringUtil.endsWithIgnoreCase(consignmentDO
					 * .getConsgNo().substring(4, 5),
					 * OutManifestConstants.CONSG_SERIES_T)) {
					 * consignmentDO.setTopayAmt(consgBillingRateDO
					 * .getGrandTotalIncludingTax()); }
					 * consgBillingRateDO.setConsignmentDO(consignmentDO);
					 * consgBillingRateDOs.add(consgBillingRateDO); }
					 */

					// Setting created and updated by user and date
					if (!StringUtil.isEmptyInteger((outmanifestParcelTO
							.getCreatedBy()))) {
						consignmentDO.setCreatedBy(outmanifestParcelTO
								.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger((outmanifestParcelTO
							.getUpdatedBy()))) {
						consignmentDO.setUpdatedBy(outmanifestParcelTO
								.getUpdatedBy());
					}
					consignmentDO.setCreatedDate(DateUtil.getCurrentDate());
					consignmentDO.setUpdatedDate(DateUtil.getCurrentDate());
				}/* END of ELSE-IF BLOCK */
			}/* END of FOR Loop - Inner */
		}/* END of FOR Loop - Outer */

		manifestDO.setConsignments(allConsignments);

		/* prepare Manifest */
		manifestDO = OutManifestParcelConverter.prepareManifestDO(
				outmanifestParcelTO, manifestDO);
		return manifestDO;
	}

	/**
	 * To prepare consignment DO
	 * 
	 * @param consignmentDO
	 * @param bookingDO
	 * @param outmanifestParcelTO
	 * @param i
	 */
	private void prepareConsgDO(ConsignmentDO consignmentDO,
			OutManifestParcelTO outmanifestParcelTO, int i) {
		/* Setting noOfPcs. and child consignment */
		if (!StringUtil.isEmptyInteger(outmanifestParcelTO.getNoOfPcs()[i])
				&& outmanifestParcelTO.getNoOfPcs()[i] > 1) {
			prepareChildCnDO(consignmentDO, outmanifestParcelTO, i);
			consignmentDO.setNoOfPcs(outmanifestParcelTO.getNoOfPcs()[i]);
		} else {
			consignmentDO.setNoOfPcs(outmanifestParcelTO.getNoOfPcs()[i]);
		}

		/* Setting Pincode */
		if (!StringUtil.isEmptyInteger(outmanifestParcelTO.getPincodeIds()[i])) {
			PincodeDO pincodeDO = new PincodeDO();
			pincodeDO.setPincodeId(outmanifestParcelTO.getPincodeIds()[i]);
			consignmentDO.setDestPincodeId(pincodeDO);
		}
		/* Setting Final and Actual Weight and Vol. weight */
		consignmentDO.setActualWeight(outmanifestParcelTO.getActWeights()[i]);
		consignmentDO.setFinalWeight(outmanifestParcelTO.getWeights()[i]);
		/* Vol. weight */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getVolWeight()[i])) {
			consignmentDO.setVolWeight(outmanifestParcelTO.getVolWeight()[i]);
		}
		/* Heights */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getHeights()[i])) {
			consignmentDO.setHeight(outmanifestParcelTO.getHeights()[i]);
		}
		/* Breaths */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getBreadths()[i])) {
			consignmentDO.setBreath(outmanifestParcelTO.getBreadths()[i]);
		}
		/* Lengths */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getLengths()[i])) {
			consignmentDO.setLength(outmanifestParcelTO.getLengths()[i]);
		}

		/* Setting consignee */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getMobileNos()[i])) {
			ConsigneeConsignorDO consignee = consignmentDO.getConsignee();
			consignee.setMobile(outmanifestParcelTO.getMobileNos()[i]);
			consignmentDO.setConsignee(consignee);
		}

		/* Content Description */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getCnContentIds()[i])) {
			CNContentDO cnContentId = new CNContentDO();
			cnContentId
					.setCnContentId(outmanifestParcelTO.getCnContentIds()[i]);
			consignmentDO.setCnContentId(cnContentId);
			if (!StringUtils
					.isEmpty(outmanifestParcelTO.getOtherCNContents()[i])) {
				consignmentDO.setOtherCNContent(outmanifestParcelTO
						.getOtherCNContents()[i]);
			}
		}

		/* Paper work */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getPaperWorkIds()[i])) {
			CNPaperWorksDO cnPaperWorksDO = new CNPaperWorksDO();
			cnPaperWorksDO.setCnPaperWorkId(outmanifestParcelTO
					.getPaperWorkIds()[i]);
			consignmentDO.setCnPaperWorkId(cnPaperWorksDO);
		}

		/* Insured By */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getInsuredByIds()[i])) {
			InsuredByDO insuredByDO = new InsuredByDO();
			insuredByDO
					.setInsuredById(outmanifestParcelTO.getInsuredByIds()[i]);
			consignmentDO.setInsuredBy(insuredByDO);
		}

		/* Police No. */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getPolicyNos()[i])) {
			consignmentDO.setInsurencePolicyNo(outmanifestParcelTO
					.getPolicyNos()[i]);
		}

		/* Customer Reference No. */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getCustRefNos()[i])) {
			consignmentDO.setRefNo(outmanifestParcelTO.getCustRefNos()[i]);
		}

		/* Declared Value, ToPay Amt. and COD amount. */
		consignmentDO
				.setDeclaredValue(outmanifestParcelTO.getDeclaredValues()[i]);
		String cnSeries = CommonConstants.EMPTY_STRING;
		Character cnSeriesChar = consignmentDO.getConsgNo().substring(4, 5)
				.toCharArray()[0];
		if (Character.isDigit(cnSeriesChar)) {
			cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
		} else {
			cnSeries = cnSeriesChar.toString();
		}
		if (cnSeries.equalsIgnoreCase("T")) {
			consignmentDO.setTopayAmt(outmanifestParcelTO.getToPayAmts()[i]);
			consignmentDO.setCodAmt(outmanifestParcelTO.getCodAmts()[i]);
		} else if (cnSeries.equalsIgnoreCase("D")) {
			consignmentDO.setLcAmount(outmanifestParcelTO.getCodAmts()[i]);
			consignmentDO.setLcBankName(outmanifestParcelTO.getLcBankName()[i]);
		} else if (cnSeries.equalsIgnoreCase("L")) {
			consignmentDO.setCodAmt(outmanifestParcelTO.getCodAmts()[i]);
		}

	}

	/**
	 * To prepare child CN DO
	 * 
	 * @param consignmentDO
	 * @param outmanifestParcelTO
	 * @param i
	 */
	private void prepareChildCnDO(ConsignmentDO consignmentDO,
			OutManifestParcelTO outmanifestParcelTO, int i) {
		// int noOfPcs = 0;
		Set<ChildConsignmentDO> newChildCNDOs = new LinkedHashSet<ChildConsignmentDO>();
		String childCNs = outmanifestParcelTO.getChildCns()[i];
		String childCNList[] = childCNs.split("#");
		for (int j = 0; j < childCNList.length; j++) {
			ChildConsignmentDO childDO = new ChildConsignmentDO();
			String str[] = childCNList[j].split(",");
			childDO.setChildConsgNumber(str[0]);
			childDO.setChildConsgWeight(Double.parseDouble(str[1]));
			childDO.setConsignment(consignmentDO);
			newChildCNDOs.add(childDO);
		}
		Set<ChildConsignmentDO> childCNDOs = consignmentDO.getChildCNs();
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
				// noOfPcs++;
			}
		}
		// consignmentDO.setNoOfPcs(noOfPcs);
		consignmentDO.setChildCNs(newChildCNDOs);
	}

	/**
	 * To prepare consignment DO
	 * 
	 * @param consignmentDO
	 * @param bookingDO
	 * @param outmanifestParcelTO
	 * @param i
	 */
	private void prepareBookingDO(BookingDO bookingDO,
			OutManifestParcelTO outmanifestParcelTO, int i) {
		/* Booking date */
		bookingDO.setBookingDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(outmanifestParcelTO
						.getManifestDate()));

		/* Consignment Number */
		bookingDO.setConsgNumber(outmanifestParcelTO.getConsgNos()[i]
				.toUpperCase());

		/* Consignment Status */
		bookingDO.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);

		/* Booking status */
		bookingDO.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);

		/* Booking office Id */
		bookingDO.setBookingOfficeId(outmanifestParcelTO.getLoginOfficeId());

		/* Setting noOfPcs. and child consignment */
		bookingDO.setNoOfPieces(outmanifestParcelTO.getNoOfPcs()[i]);

		/* Setting Pincode */
		PincodeDO pincodeDO = new PincodeDO();
		pincodeDO.setPincodeId(outmanifestParcelTO.getPincodeIds()[i]);
		bookingDO.setPincodeId(pincodeDO);

		/* Setting Final and Actual Weight and Vol. weight */
		bookingDO.setActualWeight(outmanifestParcelTO.getActWeights()[i]);

		/* Setting final weigth */
		bookingDO.setFianlWeight(outmanifestParcelTO.getWeights()[i]);
		/* Vol. weight */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getVolWeight()[i])) {
			bookingDO.setVolWeight(outmanifestParcelTO.getVolWeight()[i]);
		}
		/* Heights */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getHeights()[i])) {
			bookingDO.setHeight(outmanifestParcelTO.getHeights()[i]);
		}
		/* Breaths */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getBreadths()[i])) {
			bookingDO.setBreath(outmanifestParcelTO.getBreadths()[i]);
		}
		/* Lengths */
		if (!StringUtil.isEmptyDouble(outmanifestParcelTO.getLengths()[i])) {
			bookingDO.setLength(outmanifestParcelTO.getLengths()[i]);
		}

		/* Setting consignee */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getMobileNos()[i])) {
			ConsigneeConsignorDO consignee = bookingDO.getConsigneeId();
			consignee.setMobile(outmanifestParcelTO.getMobileNos()[i]);
			bookingDO.setConsigneeId(consignee);
		}

		/* Content Description */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getCnContentIds()[i])) {
			CNContentDO cnContentId = new CNContentDO();
			cnContentId
					.setCnContentId(outmanifestParcelTO.getCnContentIds()[i]);
			bookingDO.setCnContentId(cnContentId);
			if (!StringUtils
					.isEmpty(outmanifestParcelTO.getOtherCNContents()[i])) {
				bookingDO.setOtherCNContent(outmanifestParcelTO
						.getOtherCNContents()[i]);
			}
		}

		/* Paper work */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getPaperWorkIds()[i])) {
			CNPaperWorksDO cnPaperWorksDO = new CNPaperWorksDO();
			cnPaperWorksDO.setCnPaperWorkId(outmanifestParcelTO
					.getPaperWorkIds()[i]);
			bookingDO.setCnPaperWorkId(cnPaperWorksDO);
		}

		/* Insured By */
		if (!StringUtil
				.isEmptyInteger(outmanifestParcelTO.getInsuredByIds()[i])) {
			InsuredByDO insuredByDO = new InsuredByDO();
			insuredByDO
					.setInsuredById(outmanifestParcelTO.getInsuredByIds()[i]);
			bookingDO.setInsuredBy(insuredByDO);
		}

		/* Police No. */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getPolicyNos()[i])) {
			bookingDO
					.setInsurencePolicyNo(outmanifestParcelTO.getPolicyNos()[i]);
		}

		/* Customer Reference No. */
		if (!StringUtil.isStringEmpty(outmanifestParcelTO.getCustRefNos()[i])) {
			bookingDO.setRefNo(outmanifestParcelTO.getCustRefNos()[i]);
		}

	}

}