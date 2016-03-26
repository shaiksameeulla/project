package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestDoxDetailsTO;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
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
import com.ff.web.manifest.converter.OutManifestDoxConverter;

/**
 * The Class OutManifestDoxServiceImpl.
 */
public class OutManifestDoxServiceImpl implements OutManifestDoxService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestDoxServiceImpl.class);

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/** The outManifestCommonService. */
	private OutManifestCommonService outManifestCommonService;

	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;

	/**
	 * @param manifestCommonService
	 *            the manifestCommonService to set
	 */
	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

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

	/**
	 * get the Consignment details
	 * <p>
	 * <ul>
	 * <li>if consignment details are exist in booking data need to get from
	 * Booking
	 * <li>if consignment details are exist in in-manifest data need to get from
	 * in-manifest
	 * 
	 * will return the OutManifestDoxDetailsTO which is specific to carry grid
	 * level data.
	 * </ul>
	 * <p>
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return OutManifestDoxDetailsTO will be returned with all the required
	 *         data filled.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */
	@Override
	public OutManifestDoxDetailsTO getConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO,
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxServiceImpl :: getConsignmentDtls() :: START------------>:::::::");
		OutManifestDoxDetailsTO outManifestDoxDtlTO = null;
		// Get the booking details
		outManifestDoxDtlTO = getBookingDtlsByConsignmentNo(manifestFactoryTO,
				cnValidateTO);
		LOGGER.trace("OutManifestDoxServiceImpl :: getConsignmentDtls() :: END------------>:::::::");
		return outManifestDoxDtlTO;
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
	private OutManifestDoxDetailsTO getBookingDtlsByConsignmentNo(
			ManifestFactoryTO manifestFactoryTO,
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxServiceImpl :: getBookingDtlsByConsignmentNo() :: START------------>:::::::");
		OutManifestDoxDetailsTO outManifestDoxDtlTO = null;

		if (!StringUtil.isNull(cnValidateTO.getConsignmentModificationTO())) {
			outManifestDoxDtlTO = OutManifestDoxConverter
					.setBookingDtlsToOutManifestDoxDtl(cnValidateTO
							.getConsignmentModificationTO());
		}
		LOGGER.trace("OutManifestDoxServiceImpl :: getBookingDtlsByConsignmentNo() :: END------------>:::::::");
		return outManifestDoxDtlTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestDoxService#searchManifestDtls(
	 * com.ff.manifest.ManifestInputs)
	 */
	@Override
	public OutManifestDoxTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("OutManifestDoxServiceImpl :: searchManifestDtls() :: START------------>:::::::");
		ManifestDO manifestDO = null;
		OutManifestDoxTO outManifestDoxTO = null;
		Long startTime = System.currentTimeMillis();
		// manifestDO = manifestUniversalDAO.searchManifestDtls(manifestTO);
		manifestDO = OutManifestBaseConverter.prepateManifestDO(manifestTO);
		manifestDO = manifestCommonService.getOutDoxManifest(manifestDO);

		if (!StringUtil.isNull(manifestDO)) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOXDOs(manifestDO
					.getDoxConsignments());
			gridItemOrderDO.setComailDOs(manifestDO.getComails());
			gridItemOrderDO.setIsComailOnly(manifestDO.getContainsOnlyCoMail());
			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);
			manifestDO.setDoxConsignments(gridItemOrderDO
					.getConsignmentDOXDOs());
			manifestDO.setComails(gridItemOrderDO.getComailDOs());
			outManifestDoxTO = OutManifestDoxConverter
					.outManifestDoxDomainConverter(manifestDO);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		Long endTime = System.currentTimeMillis();
		LOGGER.debug("TIMER:" + (endTime - startTime));
		LOGGER.trace("OutManifestDoxServiceImpl :: searchManifestDtls() :: END------------>:::::::");
		return outManifestDoxTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestDoxService#saveOrUpdateOutManifestDox
	 * (com.ff.manifest.OutManifestDoxTO)
	 */
	@Override
	public String saveOrUpdateOutManifestDox(OutManifestDoxTO outmanifestDoxTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestDoxServiceImpl :: saveOrUpdateOutManifestDox() :: START------------>::::::: Manifest No :: "
				+ outmanifestDoxTO.getManifestNo());
		/** Define variables */
		Boolean searchedManifest = Boolean.FALSE;
		ManifestDO manifestDO = null;
		ManifestDO manifest = null;
		List<BookingDO> allBooking = new ArrayList<BookingDO>();
		List<ConsignmentDO> bookingConsignment = new ArrayList<ConsignmentDO>();
		Set<ConsignmentDO> allConsignments = new LinkedHashSet<ConsignmentDO>();
		// List<ConsignmentBillingRateDO> consgBillingRateDOs = new
		// ArrayList<ConsignmentBillingRateDO>();

		manifest = prepareTransferBeforeSave(outmanifestDoxTO, manifest,
				allBooking, bookingConsignment, allConsignments);

		/* Searcg manifest Process */
		/*
		 * ManifestInputs manifestTO =
		 * prepareForManifestProcess(outmanifestDoxTO);
		 */

		/* prepare Manifest Process */
		/*
		 * if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) { manifestProcess
		 * = manifestProcessDOs.get(0); } if (StringUtil.isNull(manifestProcess)
		 * || StringUtils.isEmpty(manifestProcess.getManifestProcessNo())) { //
		 * Setting process id & process code ProcessTO processTO = new
		 * ProcessTO(); processTO
		 * .setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		 * processTO = outManifestUniversalService.getProcess(processTO);
		 * outmanifestDoxTO.setProcessId(processTO.getProcessId());
		 * outmanifestDoxTO.setProcessCode(processTO.getProcessCode());
		 * 
		 * OfficeTO officeTO = new OfficeTO();
		 * officeTO.setOfficeId(outmanifestDoxTO.getLoginOfficeId()); if
		 * (!StringUtil.isStringEmpty(outmanifestDoxTO.getOfficeCode())) {
		 * officeTO.setOfficeCode(outmanifestDoxTO.getOfficeCode()); }
		 * 
		 * String processNumber = outManifestCommonService
		 * .createProcessNumber(processTO, officeTO);
		 * outmanifestDoxTO.setProcessNo(processNumber); }
		 */
		/*
		 * manifestProcessDOs = OutManifestDoxConverter
		 * .prepareManifestProcessDOList(outmanifestDoxTO); manifestProcessDO =
		 * manifestProcessDOs.get(0); if (!StringUtil.isNull(manifestProcess)) {
		 * manifestProcessDO.setManifestProcessId(manifestProcess
		 * .getManifestProcessId());
		 * manifestProcessDO.setManifestProcessNo(manifestProcess
		 * .getManifestProcessNo()); }
		 */
		/**
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(manifest.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}

		/** If manifest is not already searched i.e. the ID is not set */
		manifestDO = manifestCommonService.getManifestForCreation(manifest);
		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				/**
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
			manifestDO.setComails(manifest.getComails());
		}
		if (StringUtil.isNull(manifestDO)) {
			manifestDO = manifest;
		}

		/**
		 * Call the method to update booking and create Consignments for PICKUP
		 * - BULK
		 */

		// Setting process id
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		processTO = outManifestUniversalService.getProcess(processTO);
		ProcessDO updatedProcessDO = new ProcessDO();
		if (!StringUtil.isEmptyInteger(processTO.getProcessId())) {
			updatedProcessDO.setProcessId(processTO.getProcessId());
			updatedProcessDO.setProcessCode(processTO.getProcessCode());
		}
		Set<ConsignmentDO> newConsignment = new LinkedHashSet<ConsignmentDO>();

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
			if (StringUtil.equals(outmanifestDoxTO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				consignmentDO.setUpdatedProcess(updatedProcessDO);
				consignmentDO.setDtToCentral(CommonConstants.NO);
			}

			if (consignmentDO.isPickedup()
					&& CGCollectionUtils.isEmpty(consignmentDO
							.getConsgRateDtls())) {
				LOGGER.error("OutManifestDoxServiceImpl :: saveOrUpdateOutManifestDox() :: Consignment rate is not exist in consignment before save (throwing Business Exception) ------------>:::::::"
						+ consignmentDO.getConsgNo());
				throw new CGBusinessException(
						ManifestErrorCodesConstants.RATE_NOT_CALC_FOR_CN_2
								+ "#" + consignmentDO.getConsgNo());
			} else {
				LOGGER.info("OutManifestDoxServiceImpl :: saveOrUpdateOutManifestDox() :: Consignment rate details is exist in consignment before save ------------>:::::::"
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
			if (StringUtil.equals(outmanifestDoxTO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				bookingDO.setUpdatedProcess(updatedProcessDO);
				bookingDO.setDtToCentral(CommonConstants.NO);
			} else {
				bookingDO.setDtToCentral(CommonConstants.CHARACTER_R);
			}
			newBooking.add(bookingDO);
		}

		manifestDO.setConsignments(newConsignment);
		manifestDO.setManifestStatus(outmanifestDoxTO.getManifestStatus());
		manifestDO.setManifestWeight(outmanifestDoxTO.getFinalWeight());
		manifestDO.setNoOfElements(outmanifestDoxTO.getNoOfElements());

		// Set Grid Position
		GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
		if (!StringUtil.isEmpty(outmanifestDoxTO.getConsgNos())) {
			gridItemOrderDO.setConsignments(outmanifestDoxTO.getConsgNos());
		}
		if (!StringUtil.isEmpty(outmanifestDoxTO.getComailNos())) {
			gridItemOrderDO.setComails(outmanifestDoxTO.getComailNos());
		}
		gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
				ManifestConstants.ACTION_SAVE);
		manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition()
				.toString());

		/* Set Created By, Updated By User Id */
		setUserIdValues(manifestDO, outmanifestDoxTO);

		/** Save Booking CNs */
		LOGGER.trace("OutManifestDoxServiceImpl.saveOrUpdateOutManifestDox() :: Saving following booking consignments..");
		/*
		 * for (BookingDO bookingDO2 : newBooking) { LOGGER.debug(
		 * "OutManifestDoxServiceImpl.saveOrUpdateOutManifestDox() :: Saving booking.. Consg No : "
		 * + bookingDO2.getConsgNumber()); }
		 */
		boolean isBookingSave = manifestCommonService
				.saveOrUpdateBooking(newBooking);
		/*
		 * if (isBookingSave) LOGGER.debug(
		 * "OutManifestDoxServiceImpl.saveOrUpdateOutManifestDox() :: Saved booking consignments."
		 * ); else { LOGGER.debug(
		 * "OutManifestDoxServiceImpl.saveOrUpdateOutManifestDox() :: ERROR in Saving booking consignments."
		 * ); }
		 */

		/** Update Pickup runsheet header details */
		boolean isPickupUpdated = Boolean.FALSE;
		if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
				OutManifestConstants.CLOSE)) {
			isPickupUpdated = OutManifestBaseConverter
					.saveOrUpdatePickupRunsheetHeaderDetails(
							manifestCommonService, newBooking, outmanifestDoxTO);
			if (isPickupUpdated) {
				LOGGER.debug("OutManifestDoxServiceImpl.saveOrUpdateOutManifestDox() :: ERROR in updating Pickup header");
			}
		}
		if (!isBookingSave && !isPickupUpdated) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_SAVED);
		}
		/* SAVING MANIFST and MANIFEST PROCESS */
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
		outmanifestDoxTO.setTwoWayManifestId(manifestDO.getManifestId());
		/*
		 * outmanifestDoxTO.setManifestProcessId(manifestProcessDO
		 * .getManifestProcessId());
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

		LOGGER.debug("OutManifestDoxServiceImpl :: saveOrUpdateOutManifestDox() :: END------------>::::::: Manifest No :: "
				+ outmanifestDoxTO.getManifestNo());
		return manifestDO.getManifestStatus();
	}

	/**
	 * Set User ids i.e. created by or updated by
	 * 
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @param outmanifestDoxTO
	 */
	private void setUserIdValues(ManifestDO manifestDO,
			OutManifestDoxTO outmanifestDoxTO) {
		LOGGER.trace("OutManifestDoxServiceImpl :: setUserIdValues() :: START");
		/* Created By */
		if (StringUtil.isEmptyInteger(manifestDO.getCreatedBy())) {
			manifestDO.setCreatedBy(outmanifestDoxTO.getCreatedBy());
		}
		/* Updated By */
		manifestDO.setUpdatedBy(outmanifestDoxTO.getUpdatedBy());
		LOGGER.trace("OutManifestDoxServiceImpl :: setUserIdValues() :: END");
	}

	/**
	 * @param outmanifestDoxTO
	 * @return ManifestInputs
	 */
	private ManifestInputs prepareForManifestProcess(
			OutManifestDoxTO outmanifestDoxTO) {
		ManifestInputs manifestInputs = new ManifestInputs();
		manifestInputs.setManifestNumber(outmanifestDoxTO.getManifestNo());
		manifestInputs.setLoginOfficeId(outmanifestDoxTO.getLoginOfficeId());
		manifestInputs.setManifestType("O");
		/*
		 * manifestInputs.setManifestProcessCode(outmanifestDoxTO.
		 * getManifestProcessTo());
		 */
		manifestInputs.setManifestDirection(outmanifestDoxTO
				.getManifestDirection());
		return manifestInputs;
	}

	/**
	 * To read all consignments by consgNos.
	 * 
	 * @param outmanifestDoxTO
	 * @return list of consignments
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public List<BookingConsignmentDO> readAllConsignments(
			OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		List<BookingConsignmentDO> bookingConsignmentDOs = new ArrayList<BookingConsignmentDO>();
		List<String> cnNumbers = new ArrayList<String>(
				Arrays.asList(outmanifestDoxTO.getConsgNos()));
		cnNumbers.removeAll(Arrays.asList("", null));

		List<ConsignmentDO> consignments = manifestCommonService
				.getConsignmentsAndEvictFromSession(cnNumbers);
		List<BookingDO> bookings = manifestCommonService.getBookings(cnNumbers);

		for (int i = 0; i < outmanifestDoxTO.getConsgNos().length; i++) {
			LOGGER.debug("No of Consignment DOs :: "
					+ outmanifestDoxTO.getConsgNos().length);
			if (!StringUtil.isStringEmpty(outmanifestDoxTO.getConsgNos()[i])) {
				BookingConsignmentDO bookingConsignmentDO = new BookingConsignmentDO();
				ConsignmentDO consgnDO = null;
				for (ConsignmentDO consignmentDO : consignments) {
					if (consignmentDO.getConsgNo().equalsIgnoreCase(
							outmanifestDoxTO.getConsgNos()[i])) {
						consgnDO = consignmentDO;
						break;
					}
				}
				if (StringUtil.isNull(consgnDO)
						|| (!StringUtil.isNull(consgnDO.getUpdatedProcess()) && (consgnDO
								.getUpdatedProcess().getProcessCode())
								.equals(CommonConstants.PROCESS_PICKUP))) {
					LOGGER.debug("No of Pickup Booking DOs :: "
							+ bookings.size());
					for (BookingDO bookingDO : bookings) {
						if (bookingDO.getConsgNumber().equalsIgnoreCase(
								outmanifestDoxTO.getConsgNos()[i])) {
							bookingConsignmentDO.setConsignmentDO(consgnDO);
							bookingConsignmentDO.setBookingDO(bookingDO);
							break;
						}
					}
				} else {
					bookingConsignmentDO.setConsignmentDO(consgnDO);
				}
				bookingConsignmentDOs.add(bookingConsignmentDO);
			}
		}
		return bookingConsignmentDOs;
	}

	public OutManifestDoxDetailsTO getInManifestdConsignmentDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxServiceImpl :: getInManifestdConsignmentDtls() :: START------------>:::::::");
		OutManifestDoxDetailsTO outManifstDoxDetailsTO = null;

		List<ConsignmentTO> consignmtTOs = outManifestUniversalService
				.getConsgDtls(manifestFactoryTO);

		outManifstDoxDetailsTO = convertConsgDtlsTOListToOutManifestDoxDetailsTO(
				consignmtTOs, manifestFactoryTO);
		LOGGER.trace("OutManifestDoxServiceImpl :: getInManifestdConsignmentDtls() :: END------------>:::::::");
		return outManifstDoxDetailsTO;
	}

	private OutManifestDoxDetailsTO convertConsgDtlsTOListToOutManifestDoxDetailsTO(
			List<ConsignmentTO> consignmentTOs,
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestDoxServiceImpl :: convertConsgDtlsTOListToOutManifestDoxDetailsTO() :: START------------>:::::::");
		OutManifestDoxDetailsTO outManifestDoxDetailsTO = null;

		if (!StringUtil.isEmptyList(consignmentTOs)) {
			outManifestDoxDetailsTO = (OutManifestDoxDetailsTO) OutManifestDoxConverter
					.outManifestDoxGridDetailsForInManifConsg(consignmentTOs
							.get(0));
		}
		LOGGER.trace("OutManifestDoxServiceImpl :: convertConsgDtlsTOListToOutManifestDoxDetailsTO() :: END------------>:::::::");
		return outManifestDoxDetailsTO;
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
			OutManifestDoxTO outmanifestDoxTO, ManifestDO manifestDO,
			List<BookingDO> allBooking, List<ConsignmentDO> bookingConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		/* Populating the out manifest dox detail TO */
		LOGGER.debug("OutManifestDoxServiceImpl ::prepareTransferBeforeSave :: START");
		OutManifestDoxConverter.prepareOutManifestDtlDoxList(outmanifestDoxTO);
		manifestDO = prepareManifestForOutDox(manifestDO, outmanifestDoxTO,
				allBooking, bookingConsignment, allConsignments);
		manifestDO.setConsignments(allConsignments);
		LOGGER.debug("OutManifestDoxServiceImpl ::prepareTransferBeforeSave :: END");
		return manifestDO;
	}

	/**
	 * @param manifestDO
	 * @param manifestProcessDO
	 * @param outmanifestDoxTO
	 * @param allBooking
	 * @param bookingConsignment
	 * @param allConsignments
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */

	private ManifestDO prepareManifestForOutDox(ManifestDO manifestDO,
			OutManifestDoxTO outmanifestDoxTO, List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment,
			Set<ConsignmentDO> allConsignments) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestDoxServiceImpl :: prepareManifestForOutDox() :: START :: Manifest NO ::"
				+ outmanifestDoxTO.getManifestNo());
		List<BookingConsignmentDO> bookingConsignmentDOs = null;
		Set<ComailDO> comailSet = new LinkedHashSet<>();
		BookingDO bookingDO = null;
		/* prepare Manifest for search */
		manifestDO = OutManifestBaseConverter
				.prepateManifestDO(outmanifestDoxTO);

		/** Read All the bookings and consignments */
		bookingConsignmentDOs = readAllConsignments(outmanifestDoxTO);

		for (int i = 0; i < outmanifestDoxTO.getConsgNos().length; i++) {
			for (BookingConsignmentDO bookingConsignmentDO : bookingConsignmentDOs) {
				if (!StringUtil.isNull(bookingConsignmentDO.getBookingDO())
						&& bookingConsignmentDO.getBookingDO()
								.getUpdatedProcess().getProcessCode()
								.equalsIgnoreCase("UPPU")
						&& StringUtil.equals(outmanifestDoxTO.getConsgNos()[i],
								bookingConsignmentDO.getBookingDO()
										.getConsgNumber())) {// Pickup
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: START : BOOKING : Preparing Partial Booking.. Consg No. ----> "
							+ outmanifestDoxTO.getConsgNos()[i]);
					bookingDO = bookingConsignmentDO.getBookingDO();

					/* prepare booking */
					bookingDO
							.setBookingDate(DateUtil
									.parseStringDateToDDMMYYYYHHMMFormat(outmanifestDoxTO
											.getManifestDate()));

					ConsignmentTypeDO consgType = new ConsignmentTypeDO();
					consgType.setConsignmentId(outmanifestDoxTO
							.getConsignmentTypeTO().getConsignmentId());
					consgType
							.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);

					bookingDO.setConsgTypeId(consgType);
					bookingDO.setConsgNumber(outmanifestDoxTO.getConsgNos()[i]
							.toUpperCase());
					bookingDO
							.setCnStatus(BookingConstants.BOOKING_CN_STATUS_ACTIVE);
					bookingDO
							.setStatus(BookingConstants.BOOKING_NORMAL_PROCESS);
					bookingDO.setBookingOfficeId(outmanifestDoxTO
							.getLoginOfficeId());

					/* PINCODE */
					PincodeDO pincode = new PincodeDO();
					pincode.setPincodeId(outmanifestDoxTO.getPincodeIds()[i]);
					bookingDO.setPincodeId(pincode);

					/* WEIGHT */
					bookingDO.setFianlWeight(outmanifestDoxTO.getWeights()[i]);
					bookingDO.setActualWeight(outmanifestDoxTO.getWeights()[i]);

					/* Process code */
					if (StringUtil.equals(outmanifestDoxTO.getManifestStatus(),
							OutManifestConstants.CLOSE)) {
						if (!StringUtil.isEmptyInteger(outmanifestDoxTO
								.getProcessId())) {
							ProcessDO updatedProcessDO = new ProcessDO();
							updatedProcessDO.setProcessId(outmanifestDoxTO
									.getProcessId());
							updatedProcessDO.setProcessCode(outmanifestDoxTO
									.getProcessCode());
							bookingDO.setUpdatedProcess(updatedProcessDO);
						}
					}
					bookingDO.setDtFromOpsman(CommonConstants.DT_FROM_OPSMAN_R);
					bookingDO.setDtToBranch(CommonConstants.NO);
					bookingDO.setDtToCentral(CommonConstants.DT_FROM_OPSMAN_R);
					bookingDO.setDtUpdateToCentral(CommonConstants.NO);
					bookingDO.setDtToOpsman(CommonConstants.NO);

					/* Setting update date in booking */
					bookingDO.setUpdatedDate(DateUtil.getCurrentDate());
					LOGGER.trace("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: END : BOOKING : Prepared Partial Booking : Consg No. ----> "
							+ bookingDO.getConsgNumber());
					allBooking.add(bookingDO);

					/** prepare consignment */
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: START : CONSIGNMENT : Preparing Pickup Consignment..Consignment No :: "
							+ outmanifestDoxTO.getConsgNos()[i]);
					ConsignmentDO consignmentDO = bookingConsignmentDO
							.getConsignmentDO();
					if (StringUtil.isNull(consignmentDO)) {
						consignmentDO = new ConsignmentDO();
					}
					/* Setting Billing Flags */
					LOGGER.debug("Setting BILLING Flags for PICKUP consignment"
							+ outmanifestDoxTO.getConsgNos()[i]);
					if ((!StringUtil.isNull(consignmentDO.getDestPincodeId()) && (!outmanifestDoxTO
							.getPincodeIds()[i].equals(consignmentDO
							.getDestPincodeId().getPincodeId())))
							|| (!StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight()) && (consignmentDO
									.getFinalWeight() < outmanifestDoxTO
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
					/* Setting consignment number */
					consignmentDO.setConsgNo(outmanifestDoxTO.getConsgNos()[i]);

					// Set pickedup flag
					consignmentDO.setPickedup(true);

					/* consignment type */
					consignmentDO.setConsgType(consgType);

					/* Setting Pincode */
					PincodeDO pincodeDO = new PincodeDO();
					pincodeDO.setPincodeId(outmanifestDoxTO.getPincodeIds()[i]);
					consignmentDO.setDestPincodeId(pincodeDO);

					/* Setting Final and Actual Weight */
					consignmentDO
							.setActualWeight(outmanifestDoxTO.getWeights()[i]);
					consignmentDO
							.setFinalWeight(outmanifestDoxTO.getWeights()[i]);

					/* Setting Process */
					if (StringUtil.equals(outmanifestDoxTO.getManifestStatus(),
							OutManifestConstants.CLOSE)) {
						if (!StringUtil.isEmptyInteger(outmanifestDoxTO
								.getProcessId())) {
							ProcessDO updatedProcessDO = new ProcessDO();
							updatedProcessDO.setProcessId(outmanifestDoxTO
									.getProcessId());
							updatedProcessDO.setProcessCode(outmanifestDoxTO
									.getProcessCode());
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
					if (!StringUtil.isStringEmpty(outmanifestDoxTO
							.getMobileNos()[i])) {
						ConsigneeConsignorDO consignee = bookingDO
								.getConsigneeId();
						consignee.setMobile(outmanifestDoxTO.getMobileNos()[i]);
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
					consignmentDO.setConsgStatus("B");

					/* Origin office */
					consignmentDO.setOrgOffId(outmanifestDoxTO
							.getLoginOfficeId());

					/* Operating Level */
					ConsignmentTO consigmentTO = new ConsignmentTO();
					consigmentTO.setOrgOffId(outmanifestDoxTO
							.getLoginOfficeId());
					consigmentTO.setDestOffice(null);
					if (outmanifestDoxTO.getDestinationOfficeId() != null) {
						OfficeTO destoffTO = new OfficeTO();
						destoffTO.setOfficeId(outmanifestDoxTO
								.getDestinationOfficeId());
						consigmentTO.setDestOffice(destoffTO);
					}
					CityTO destCityTO = new CityTO();
					destCityTO.setCityId(outmanifestDoxTO.getDestCityIds()[i]);
					consigmentTO.setDestCity(destCityTO);
					OfficeTO offTO = new OfficeTO();
					offTO.setOfficeId(outmanifestDoxTO.getLoginOfficeId());
					/*
					 * Integer operatingLevel = outManifestCommonService
					 * .getConsgOperatingLevel(consigmentTO, offTO);
					 * consignmentDO.setOperatingLevel(operatingLevel);
					 */
					/*
					 * operating level needs to be set in case of
					 * weight/destination changes
					 */
					consignmentDO.setOperatingOffice(outmanifestDoxTO
							.getLoginOfficeId());

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

					// Rate Details i.e. LC Amount and Bank Name for D-Series
					// Consignment
					setCnPricingDtls(outmanifestDoxTO, consignmentDO, i);

					// Set Event Date
					if (StringUtil.isNull(consignmentDO.getEventDate())) {
						consignmentDO.setEventDate(DateUtil.getCurrentDate());
					} else if (!StringUtil.isNull(bookingDO.getBookingDate())) {
						consignmentDO.setEventDate(bookingDO.getBookingDate());
					}

					bookingConsignment.add(consignmentDO);
					allConsignments.add(consignmentDO);

					/** Rate Integration with out manifest parcel. */
					ConsignmentBillingRateDO consgBillingRateDO = OutManifestBaseConverter
							.calcRateForOutManifest(outmanifestDoxTO, i);
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() ::rate component received from session for consignment["
							+ consignmentDO.getConsgNo()
							+ "] is: "
							+ consgBillingRateDO);
					if (!StringUtil.isNull(consgBillingRateDO)) {
						// Set Topay amount for T-Series
						if (StringUtil.endsWithIgnoreCase(consignmentDO
								.getConsgNo().substring(4, 5),
								OutManifestConstants.CONSG_SERIES_T)) {
							consignmentDO.setTopayAmt(consgBillingRateDO
									.getGrandTotalIncludingTax());
						}
						/** Set Created by And Created Date In Rate */
						if (!StringUtil.isEmptyInteger(outmanifestDoxTO
								.getCreatedBy())) {
							consgBillingRateDO.setCreatedBy(outmanifestDoxTO
									.getCreatedBy());
						}
						if (!StringUtil.isEmptyInteger(outmanifestDoxTO
								.getUpdatedBy())) {
							consgBillingRateDO.setUpdatedBy(outmanifestDoxTO
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
							LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: rate already exist in DB for this CN: "
									+ consignmentDO.getConsgNo());
							for (ConsignmentBillingRateDO consignmentBillingRateDO : consgRateDtls) {
								LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: existing CN rate id: "
										+ consignmentBillingRateDO
												.getConsignmentRateId());
								if (consignmentBillingRateDO
										.getRateCalculatedFor()
										.equalsIgnoreCase("B")) {
									LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: setting CN rate id: "
											+ consignmentBillingRateDO
													.getConsignmentRateId()
											+ " in rate component received from session");
									consgBillingRateDO
											.setConsignmentRateId(consignmentBillingRateDO
													.getConsignmentRateId());
									newConsgRateDtls.add(consgBillingRateDO);
								} else {
									LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: ignoring existinf CN rate id: "
											+ consignmentBillingRateDO
													.getConsignmentRateId()
											+ " in rate component received from session");
									newConsgRateDtls
											.add(consignmentBillingRateDO);
								}
							}
						} else {
							LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: rate does not exist in DB for this CN: "
									+ consignmentDO.getConsgNo());
							LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: added rate component in rate details list: "
									+ consgBillingRateDO);
							newConsgRateDtls.add(consgBillingRateDO);
						}
						LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: total rate component in rate details list[ "
								+ newConsgRateDtls.size()
								+ "] for CN: "
								+ consignmentDO.getConsgNo());
						consignmentDO.setConsgRateDtls(newConsgRateDtls);
					}

					// Setting created and updated user and date
					if (!StringUtil.isEmptyInteger(outmanifestDoxTO
							.getCreatedBy())) {
						consignmentDO.setCreatedBy(outmanifestDoxTO
								.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger(outmanifestDoxTO
							.getUpdatedBy())) {
						consignmentDO.setUpdatedBy(outmanifestDoxTO
								.getUpdatedBy());
						bookingDO.setUpdatedBy(outmanifestDoxTO.getUpdatedBy());
					}
					consignmentDO.setCreatedDate(DateUtil.getCurrentDate());
					consignmentDO.setUpdatedDate(DateUtil.getCurrentDate());
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: END : CONSIGNMENT : Prepared Pickup Consignment : Consg No. ----> "
							+ consignmentDO.getConsgNo());
				} else if (!StringUtil.isNull(bookingConsignmentDO
						.getConsignmentDO())
						&& StringUtil.equals(outmanifestDoxTO.getConsgNos()[i],
								bookingConsignmentDO.getConsignmentDO()
										.getConsgNo())
						&& (!bookingConsignmentDO.getConsignmentDO()
								.getUpdatedProcess().getProcessCode()
								.equalsIgnoreCase("UPPU"))) {// Booking
					/* Modiifed Consignment */
					ConsignmentDO consignmentDO = bookingConsignmentDO
							.getConsignmentDO();
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: START : CONSIGNMENT : Prepared BOOKING Consignment : Consg No. ----> "
							+ consignmentDO.getConsgNo());

					/* Setting Billing Flags */
					LOGGER.debug("Setting BILLING Flags for BOOKING consignment"
							+ consignmentDO.getConsgNo());
					if ((!StringUtil.isNull(consignmentDO.getDestPincodeId()) && (!outmanifestDoxTO
							.getPincodeIds()[i].equals(consignmentDO
							.getDestPincodeId().getPincodeId())))
							|| (!StringUtil.isEmptyDouble(consignmentDO
									.getFinalWeight()) && (consignmentDO
									.getFinalWeight() < outmanifestDoxTO
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
					PincodeDO pincodeDO = new PincodeDO();
					pincodeDO.setPincodeId(outmanifestDoxTO.getPincodeIds()[i]);
					consignmentDO.setDestPincodeId(pincodeDO);

					/* Setting Final and Actual Weight */
					consignmentDO
							.setActualWeight(outmanifestDoxTO.getWeights()[i]);
					consignmentDO
							.setFinalWeight(outmanifestDoxTO.getWeights()[i]);

					/* Setting Process */
					if (!StringUtil.isEmptyInteger(outmanifestDoxTO
							.getProcessId())) {
						ProcessDO updatedProcessDO = new ProcessDO();
						updatedProcessDO.setProcessId(outmanifestDoxTO
								.getProcessId());
						consignmentDO.setUpdatedProcess(updatedProcessDO);
					}

					/* Setting consignee */
					if (!StringUtil.isStringEmpty(outmanifestDoxTO
							.getMobileNos()[i])) {
						ConsigneeConsignorDO consignee = consignmentDO
								.getConsignee();
						consignee.setMobile(outmanifestDoxTO.getMobileNos()[i]);
						consignmentDO.setConsignee(consignee);
					}

					// Rate Details i.e. LC Amount and Bank Name for D-Series
					// Consignment
					// setCnPricingDtls(outmanifestDoxTO, consignmentDO, i);

					allConsignments.add(consignmentDO);

					// calculate rate for bulk booking CN
					/*
					 * ConsignmentBillingRateDO consgBillingRateDO =
					 * OutManifestBaseConverter
					 * .calcRateForOutManifest(outmanifestDoxTO, i); if
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

					// Setting created and updated user and date
					if (!StringUtil.isEmptyInteger(outmanifestDoxTO
							.getCreatedBy())) {
						consignmentDO.setCreatedBy(outmanifestDoxTO
								.getCreatedBy());
					}
					if (!StringUtil.isEmptyInteger(outmanifestDoxTO
							.getUpdatedBy())) {
						consignmentDO.setUpdatedBy(outmanifestDoxTO
								.getUpdatedBy());
					}
					consignmentDO.setCreatedDate(DateUtil.getCurrentDate());
					consignmentDO.setUpdatedDate(DateUtil.getCurrentDate());
					LOGGER.debug("OutManifestDoxServiceImpl.prepareManifestForOutDox() :: END : CONSIGNMENT : Prepared BOOKING Consignment : Consg No. ----> "
							+ consignmentDO.getConsgNo());
				}/* END of ELSE-IF BLOCK */

			}/* END of FOR Loop - Inner */

			/** Set ComailDO if entered in consignment place */
			if (outmanifestDoxTO.getIsCoMailOnly().equalsIgnoreCase("Y")
					&& !StringUtils.isEmpty(outmanifestDoxTO.getConsgNos()[i])) {
				String comail = outmanifestDoxTO.getConsgNos()[i];
				if (!StringUtils.isEmpty(comail)) {
					ComailDO comailDO = prepairComailDO(comail,
							outmanifestDoxTO);
					comailSet.add(comailDO);
				}
			}

		}/* END of FOR Loop - Outer */

		manifestDO.setConsignments(allConsignments);
		/** Set ComailDO */
		if (!StringUtil.isEmpty(outmanifestDoxTO.getComailNos())) {
			for (int i = 0; i < outmanifestDoxTO.getComailNos().length; i++) {
				String comail = outmanifestDoxTO.getComailNos()[i];
				if (!StringUtils.isEmpty(comail)) {
					ComailDO comailDO = prepairComailDO(comail,
							outmanifestDoxTO);
					comailSet.add(comailDO);
				}
			}
		}
		manifestDO.setComails(comailSet);
		/* prepare Manifest */
		manifestDO = OutManifestDoxConverter.prepareManifestDO(
				outmanifestDoxTO, manifestDO);
		LOGGER.debug("OutManifestDoxServiceImpl :: prepareManifestForOutDox() :: END :: Manifest NO ::"
				+ outmanifestDoxTO.getManifestNo());
		return manifestDO;
	}

	/**
	 * Prepair Comail DO
	 * 
	 * @param comail
	 * @param outmanifestDoxTO
	 * @return ComailDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private ComailDO prepairComailDO(String comail,
			OutManifestDoxTO outmanifestDoxTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestDoxServiceImpl :: prepairComailDO:: START :: Comail No :: "
				+ comail);
		Integer comailId = outManifestCommonService
				.getComailIdByComailNo(comail);
		ComailDO comailDO = new ComailDO();
		if (!StringUtil.isEmptyInteger(comailId)) {
			comailDO.setCoMailId(comailId);
		} else {
			comailDO.setCoMailId(null);
		}
		comailDO.setCoMailNo(comail);
		comailDO.setOriginOffice(outmanifestDoxTO.getLoginOfficeId());
		if (!StringUtil.isEmptyInteger(outmanifestDoxTO
				.getDestinationOfficeId())) {
			comailDO.setDestinationOffice(outmanifestDoxTO
					.getDestinationOfficeId());
		}
		LOGGER.debug("OutManifestDoxServiceImpl :: prepairComailDO:: END :: Comail No :: "
				+ comail);
		return comailDO;
	}

	/**
	 * @param outmanifestDoxTO
	 * @param consignmentDO
	 * @param rowId
	 */
	private void setCnPricingDtls(OutManifestDoxTO outmanifestDoxTO,
			ConsignmentDO consignmentDO, int rowId) {
		/*
		 * CNPricingDetailsDO cnPricingDetailsDO = null; if
		 * (!StringUtil.isNull(consignmentDO.getConsgPricingDtls())) {
		 * cnPricingDetailsDO = consignmentDO.getConsgPricingDtls(); } else {
		 * cnPricingDetailsDO = new CNPricingDetailsDO(); }
		 */

		// Find out the appropriate detail of the header
		LOGGER.debug("OutManifestDoxServiceImpl :: setCnPricingDtls ::START :: Consignment No :: "
				+ outmanifestDoxTO.getConsgNos()[rowId]);
		String cnNumberSeries = outmanifestDoxTO.getConsgNos()[rowId]
				.substring(4, 5);
		if ((StringUtils.equalsIgnoreCase(cnNumberSeries,
				OutManifestConstants.CONSG_SERIES_D)
				|| StringUtils.equalsIgnoreCase(cnNumberSeries,
						OutManifestConstants.CONSG_SERIES_L) || StringUtils
					.equalsIgnoreCase(cnNumberSeries,
							OutManifestConstants.CONSG_SERIES_T))) {
			// Splitting the LC Details to derive the Bank Name & LC or COD
			// Amount
			if (!StringUtil
					.isStringEmpty(outmanifestDoxTO.getLcDetails()[rowId])) {
				String[] lcDtls = outmanifestDoxTO.getLcDetails()[rowId]
						.split(CommonConstants.HASH);
				Double lcAmount = 0.0;
				String bankName = "";
				if (!StringUtil.isEmpty(lcDtls)) {
					if (StringUtils.isNotEmpty(lcDtls[0]))
						lcAmount = Double.parseDouble(lcDtls[0]);
					if (StringUtils.isNotEmpty(lcDtls[1]))
						bankName = lcDtls[1];
				}

				// set LC amount details
				if (StringUtils.equalsIgnoreCase(cnNumberSeries,
						OutManifestConstants.CONSG_SERIES_D)) {
					// LC Amount
					if (!StringUtil.isEmptyDouble(lcAmount)) {
						consignmentDO.setLcAmount(lcAmount);
					}

					// Bank Name
					if (!StringUtil.isStringEmpty(bankName)) {
						consignmentDO.setLcBankName(bankName);
					}
				}// set COD amount details
				else if (StringUtils.equalsIgnoreCase(cnNumberSeries,
						OutManifestConstants.CONSG_SERIES_L)
						|| StringUtils.equalsIgnoreCase(cnNumberSeries,
								OutManifestConstants.CONSG_SERIES_T)) {
					// COD Amount
					if (!StringUtil.isEmptyDouble(lcAmount)) {
						consignmentDO.setCodAmt(lcAmount);
					} else {
						consignmentDO.setCodAmt(null);// reset amount
					}
				}
			}// END OF INNER IF
			else if (StringUtils.equalsIgnoreCase(cnNumberSeries,
					OutManifestConstants.CONSG_SERIES_T)) {
				consignmentDO.setCodAmt(null);// reset amount
			}// END OF INNER ELSE-IF
		}// END OF IF

		LOGGER.debug("OutManifestDoxServiceImpl :: setCnPricingDtls :: END :: Consignment No :: "
				+ outmanifestDoxTO.getConsgNos()[rowId]);
	}

}
