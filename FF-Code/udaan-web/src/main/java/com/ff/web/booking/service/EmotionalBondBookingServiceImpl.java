/**
 * 
 */
package com.ff.web.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.EmotionalBondBookingTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.booking.BookingPreferenceMappingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.converter.EmotionalBondBookingConvertor;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.dao.EBBookingDAO;

/**
 * The Class EmotionalBondBookingServiceImpl.
 * 
 * @author uchauhan
 */
public class EmotionalBondBookingServiceImpl implements
		EmotionalBondBookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EmotionalBondBookingServiceImpl.class);

	/** The sequence generator service. */
	private SequenceGeneratorService sequenceGeneratorService;

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/** The eb booking dao. */
	private EBBookingDAO ebBookingDAO;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the eb booking dao.
	 * 
	 * @param ebBookingDAO
	 *            the ebBookingDAO to set
	 */
	public void setEbBookingDAO(EBBookingDAO ebBookingDAO) {
		this.ebBookingDAO = ebBookingDAO;
	}

	/**
	 * Sets the sequence generator service.
	 * 
	 * @param sequenceGeneratorService
	 *            the new sequence generator service
	 */
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * Sets the booking common dao.
	 * 
	 * @param bookingCommonDAO
	 *            the new booking common dao
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/**
	 * Sets the booking common service.
	 * 
	 * @param bookingCommonService
	 *            the new booking common service
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	/**
	 * Generate seq num.
	 * 
	 * @param noOfOrders
	 *            the no of orders
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private List<String> generateSeqNum(Integer noOfOrders)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::generateSeqNum::START------------>:::::::");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO
				.setProcessRequesting(CommonConstants.EMOTIONAL_BOND_BOOKING_NO);
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfOrders);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.debug("EmotionalBondBookingServiceImpl::generateSeqNum::END------------>:::::::");
		return sequenceNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.EmotionalBondBookingService#generateEBNum(
	 * java.lang.String, java.lang.Integer)
	 */
	public String generateEBNum(String OffcCode, Integer seqNums)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::generateEBNum::START------------>:::::::");
		List<String> seqNum = generateSeqNum(seqNums);
		String ebNum = OffcCode
				+ BookingConstants.EMOTIONAL_BOND_BOOKING_SERIES
				+ seqNum.get(0);
		LOGGER.debug("EmotionalBondBookingServiceImpl::generateEBNum::END------------>:::::::");
		return ebNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.EmotionalBondBookingService#
	 * saveOrUpdateEmotionalBondBooking(com.ff.booking.EmotionalBondBookingTO)
	 */
	public BookingResultTO saveOrUpdateBookingEmotionalBond(
			EmotionalBondBookingTO emotionalBondBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::saveOrUpdateEmotionalBondBooking::START------------>:::::::");
		BookingDO booking = null;
		// Set<BookingPreferenceDetailsDO> bokingPrefs = new HashSet<>();
		// BookingPreferenceDetailsDO prefDO = null;
		String transStatus = "";
		BookingResultTO result = new BookingResultTO();
		try {
			BookingTypeDO ebBooking = bookingCommonDAO.getBookingType(BookingConstants.EMOTIONAL_BOND_BOOKING);
			emotionalBondBookingTO.setBookingTypeId(ebBooking.getBookingTypeId());
			emotionalBondBookingTO.setBookingType(ebBooking.getBookingType());
			String date = emotionalBondBookingTO.getDelvDateTime();
			date = date + " " + emotionalBondBookingTO.getDlvTime();
			emotionalBondBookingTO.setDelvDateTime(date);
			ProcessTO process = getProcess();
			emotionalBondBookingTO.setProcessTO(process);
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			List<ConsignmentTypeTO> consgTypes = bookingCommonService.getConsignmentType(consgType);
			emotionalBondBookingTO.setConsgTypeId(consgTypes.get(0).getConsignmentId());
			emotionalBondBookingTO.setConsgTypeName(consgTypes.get(0).getConsignmentId()
					+ "#" + consgTypes.get(0).getConsignmentName());
			booking = EmotionalBondBookingConvertor.emotionalBondBookingDomainConverter(emotionalBondBookingTO);
			booking.setBookingType(ebBooking);
			
			/* Setting actual & final weight as 0.0 and not null so that the "not-null" constraint
			 * on final weight at HBM level does not get failed */
			emotionalBondBookingTO.setActualWeight(0.0);
			emotionalBondBookingTO.setFinalWeight(0.0);
			
			if (booking != null) {
				List<BookingDO> bookings = new ArrayList<BookingDO>(1);
				bookings.add(booking);
				List<Integer> successBookedIds = bookingCommonDAO.saveOrUpdateBooking(bookings);
				if (!StringUtil.isEmptyColletion(successBookedIds)) {
					// Setting CNPricing details
					List<Integer> successCNsIds = bookingCommonService
							.saveConsignmentAndRateDox(emotionalBondBookingTO, consgRateDtls);
					result.setSuccessCNsIds(successCNsIds);
					result.setSuccessBookingsIds(successBookedIds);
					transStatus = CommonConstants.SUCCESS;
					// bookingCommonService.process2WayWrite(successBookedIds,
					// successCNsIds);
				} else
					transStatus = CommonConstants.FAILURE;
			}

			result.setTransMessage(transStatus);
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in EmotionalBondBookingServiceImpl :: saveOrUpdateEmotionalBondBooking() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception in EmotionalBondBookingServiceImpl :: saveOrUpdateEmotionalBondBooking() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("EmotionalBondBookingServiceImpl::saveOrUpdateEmotionalBondBooking::END------------>:::::::");
		return result;
	}

	/**
	 * Gets the process.
	 * 
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private ProcessTO getProcess() throws CGSystemException,
			CGBusinessException {
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_BOOKING);
		process = bookingCommonService.getProcess(process);
		return process;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.EmotionalBondBookingService#getEBBookings(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmotionalBondBookingTO> getEBBookings(String bookingDate,
			String bookingType, Integer loginOffceId)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::getEBBookings::START------------>:::::::");
		List<EmotionalBondBookingTO> ebBookings = null;
		List<BookingDO> bookingDOs = null;
		// CNPricingDetailsTO cnPricingDtls = null;
		List<String> felicitation = null;
		Date bkDate = DateUtil.stringToDDMMYYYYFormat(bookingDate);
		bookingDOs = ebBookingDAO.getEBBookings(bkDate, bookingType,
				loginOffceId);
		if (!StringUtil.isEmptyList(bookingDOs)) {
			ebBookings = new ArrayList<EmotionalBondBookingTO>(
					bookingDOs.size());
			for (BookingDO booking : bookingDOs) {
				EmotionalBondBookingTO bookingTO = new EmotionalBondBookingTO();
				bookingTO.setBookingId(booking.getBookingId());
				bookingTO.setBookingDate(DateUtil
						.getDDMMYYYYDateToString(booking.getBookingDate()));
				String dlvTime = DateUtil.extractTimeFromDate(booking
						.getDeliveryDate());
				String dlvDate = DateUtil.getDDMMYYYYDateToString(booking
						.getDeliveryDate());
				dlvDate = dlvDate + " " + dlvTime;
				bookingTO.setDelvDateTime(dlvDate);
				bookingTO.setConsgNumber(booking.getConsgNumber());
				bookingTO.setBookingOfficeId(booking.getBookingOfficeId());
				OfficeTO bookingOffice = bookingCommonService
						.getOfficeByIdOrCode(booking.getBookingOfficeId(),
								CommonConstants.EMPTY_STRING);
				bookingTO.setBookingBranch(bookingOffice.getOfficeName());
				bookingTO.setBookingType(booking.getBookingType()
						.getBookingType());
				if (booking.getBokingPrefs() != null) {
					felicitation=new ArrayList<>();
					for (BookingPreferenceMappingDO mapDO : booking
							.getBokingPrefs())
						felicitation.add(mapDO.getReferenceId()
								.getDescription());
						bookingTO.setFelicitation(felicitation);
						
					
					bookingTO.setInstruction(booking.getBokingPrefs()
							.iterator().next().getSplInstructions());
					bookingTO.setOtherPref(booking.getBokingPrefs().iterator()
							.next().getOtherPref());
				}

				if (booking.getConsgTypeId() != null) {
					bookingTO.setConsgTypeId(booking.getConsgTypeId()
							.getConsignmentId());
					bookingTO.setConsgTypeName(booking.getConsgTypeId()
							.getConsignmentCode());
				}
				if (booking.getPincodeId() != null) {
					bookingTO.setPincodeId(booking.getPincodeId()
							.getPincodeId());
					bookingTO.setPincode(booking.getPincodeId().getPincode());
					List<PincodeServicabilityTO> servicabilityTOs = geographyCommonService
							.getServicableOfficeIdByPincode(booking
									.getPincodeId().getPincodeId());
					List<OfficeTO> officeTOs = new ArrayList<>();
					for (PincodeServicabilityTO servicabilityTO : servicabilityTOs) {
						OfficeTO officeTO = new OfficeTO();
						officeTO.setOfficeId(servicabilityTO.getOfficeId());
						officeTOs.add(officeTO);
					}
					String delvBranchName = "";
					String delvBranchId = "";
					officeTOs = geographyCommonService
							.getAllOfficeByOfficeIds(officeTOs);
					for (OfficeTO TO : officeTOs) {
						if (delvBranchName.length() > 0) {
							delvBranchName += CommonConstants.COMMA;
						}
						delvBranchName += TO.getOfficeName();

						if (delvBranchId.length() > 0) {
							delvBranchId += CommonConstants.COMMA;
						}
						delvBranchId += TO.getOfficeId();

					}
					bookingTO.setDlvBranch(delvBranchName);
					bookingTO.setDlvBranchId(delvBranchId);
					// bookingTO.setDestBranchId(destOfficeId);
				}
				CityTO city = geographyCommonService.getCity(booking
						.getPincodeId().getPincode());
				if (city != null) {
					bookingTO.setCityId(city.getCityId());
					bookingTO.setCityName(city.getCityName());
				}
				bookingTO.setPrice(booking.getPrice());
				bookingTO.setStatusCode(booking.getStatus());
				bookingTO
						.setWeightCapturedMode(booking.getWeightCapturedMode());
				// Setting child entities
				if (!StringUtil.isNull(booking.getConsigneeId())) {
					ConsignorConsigneeTO consignee = new ConsignorConsigneeTO();
					CGObjectConverter.createToFromDomain(
							booking.getConsigneeId(), consignee);
					bookingTO.setConsignee(consignee);
				}
				if (!StringUtil.isNull(booking.getConsignorId())) {
					ConsignorConsigneeTO consignor = new ConsignorConsigneeTO();
					CGObjectConverter.createToFromDomain(
							booking.getConsignorId(), consignor);
					bookingTO.setConsignor(consignor);
				}
				/*
				 * cnPricingDtls = consignmentCommonService
				 * .getConsgPrincingDtls(booking.getConsgNumber()); if
				 * (!StringUtil.isNull(cnPricingDtls))
				 * bookingTO.setCnPricingDtls(cnPricingDtls);
				 */
				ebBookings.add(bookingTO);
			}
		}
		LOGGER.debug("EmotionalBondBookingServiceImpl::getEBBookings::END------------>:::::::");
		return ebBookings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.EmotionalBondBookingService#getBookingPrefDetails
	 * ()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails(
			Integer stateId) throws CGSystemException, CGBusinessException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::getBookingPrefDetails::START------------>:::::::");
		List<BookingPreferenceDetailsTO> bookingPrefTOs = null;
		try{
		List<BookingPreferenceDetailsDO> bookingPrefDOs = null;
		bookingPrefDOs = ebBookingDAO.getBookingPrefDetails(stateId);
			if (!StringUtil.isEmptyColletion(bookingPrefDOs)) {
				bookingPrefTOs = new ArrayList<>();
				bookingPrefTOs = (List<BookingPreferenceDetailsTO>) CGObjectConverter
						.createTOListFromDomainList(bookingPrefDOs,
								BookingPreferenceDetailsTO.class);
			} else {
				ExceptionUtil
						.prepareBusinessException(BookingErrorCodesConstants.PREF_NOT_CONFIGURED);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("EmotionalBondBookingServiceImpl::getBookingPrefDetails::END------------>:::::::"
					+ e.getMessage());
			throw e;
		}
		LOGGER.debug("EmotionalBondBookingServiceImpl::getBookingPrefDetails::END------------>:::::::");
		return bookingPrefTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.EmotionalBondBookingService#updateEBBookingsDtls
	 * (com.ff.booking.EmotionalBondBookingTO)
	 */
	@Override
	public Boolean updateEBBookingsDtls(
			EmotionalBondBookingTO emotionalBondBookingTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.debug("EmotionalBondBookingServiceImpl::updateEBBookingsDtls::START------------>:::::::");
		// BookingDO booking = null;
		Boolean isUpdated = Boolean.FALSE;
		List<BookingDO> bookingDOs = new ArrayList<>();
		bookingDOs = EmotionalBondBookingConvertor
				.createDomainConverter(emotionalBondBookingTO);
		isUpdated = ebBookingDAO.updateEBBookingsDtls(bookingDOs);
		LOGGER.debug("EmotionalBondBookingServiceImpl::updateEBBookingsDtls::END------------>:::::::");
		return isUpdated;
	}

	public List<String> getBookingPrefCodes(List<Integer> preferenceIds)
			throws CGSystemException {
		return ebBookingDAO.getBookingPrefCodes(preferenceIds);
	}

}
