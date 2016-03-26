package com.ff.web.booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.geography.CityTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.converter.BABookingConverter;
import com.ff.web.booking.dao.BABookingDAO;
import com.ff.web.booking.dao.BookingCommonDAO;

/**
 * The Class BABookingServiceImpl.
 */
public class BABookingServiceImpl implements BABookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BABookingServiceImpl.class);

	/** The ba booking dao. */
	private BABookingDAO baBookingDAO = null;

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;
	

	/**
	 * Gets the ba booking dao.
	 * 
	 * @return the ba booking dao
	 */
	public BABookingDAO getBaBookingDAO() {
		return baBookingDAO;
	}

	/**
	 * Sets the ba booking dao.
	 * 
	 * @param baBookingDAO
	 *            the new ba booking dao
	 */
	public void setBaBookingDAO(BABookingDAO baBookingDAO) {
		this.baBookingDAO = baBookingDAO;
	}

	/**
	 * Gets the booking common dao.
	 * 
	 * @return the booking common dao
	 */
	public BookingCommonDAO getBookingCommonDAO() {
		return bookingCommonDAO;
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
	 * Gets the booking common service.
	 * 
	 * @return the booking common service
	 */
	public BookingCommonService getBookingCommonService() {
		return bookingCommonService;
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
	 * Sets the consignment common service.
	 * 
	 * @param consignmentCommonService
	 *            the new consignment common service
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BABookingService#saveOrUpdateBABookingDox(
	 * java.util.List)
	 */
	@Override
	public BookingResultTO createBookingAndConsigmentsDox(
			BookingWrapperTO bookingWrapperTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingDox::START------------>:::::::");
		BookingWrapperDO bookingWrapperDO = null;
		String transStatus = "";
		Set<String> failureConsingments = null;
		BookingResultTO result =  new BookingResultTO();
		try {
			// Preparing Bookings & Consingments
			bookingWrapperDO = BABookingConverter.baBookingDoxDomainConverter(
					bookingWrapperTO, bookingCommonService);
			if (!StringUtil.isNull(bookingWrapperDO)) {
				// Saving Bulk Bookings
				try {
					LOGGER.debug("BABookingServiceImpl::createBookingAndConsigmentsDox::saving booking and consignment in batch....");
					bookingWrapperDO = bookingCommonDAO
							.batchBookingUpdate(bookingWrapperDO);
					bookingCommonService.prepareBookingCNIds(bookingWrapperDO);
					LOGGER.debug("BABookingServiceImpl::createBookingAndConsigmentsDox::batch saving for booking and consignment is successfull....");
				} catch (CGSystemException e) {
					LOGGER.error("BABookingServiceImpl::createBookingAndConsigmentsDox::batch saving for booking and consignment fails.");
					LOGGER.error("Exception Occurred in :: BABookingServiceImpl::saveOrUpdateBABookingDox:: batchBookingUpdate------------>:::::::");
					failureConsingments = saveBookingAndConsignment(
							bookingWrapperDO, failureConsingments);
				}
				if (bookingWrapperDO.isBulkSave()) {
					transStatus = CommonConstants.SUCCESS + "#"
							+ BookingConstants.IS_BULK_SAVE;
				} else {
					transStatus = prepareFailureConsignments(transStatus,
							failureConsingments);
				}
				result.setSuccessBookingsIds(bookingWrapperDO.getSuccessBookingsIds());
				result.setSuccessCNsIds(bookingWrapperDO.getSuccessCNsIds());
				result.setTransMessage(transStatus);
				result.setTransStatus(bookingWrapperDO.isBulkSave());
				//bookingCommonService.process2WayWrite(bookingWrapperDO);
				
			}
		} catch (CGBusinessException e) {
			// transStatus = CommonConstants.SUCCESS + "#" + CommonConstants.NO;
			LOGGER.error("Exception in BABookingServiceImpl:: saveOrUpdateBABookingDox() :"
					, e);
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception in BABookingServiceImpl:: saveOrUpdateBABookingDox() :"
					,e);
			throw new CGSystemException(e);
		}
		LOGGER.info("BABookingServiceImpl::saveOrUpdateBABookingDox::transaction status is : " + transStatus);
		LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingDox::END------------>:::::::");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BABookingService#saveOrUpdateBABookingParcel
	 * (java.util.List)
	 */
	@Override
	public BookingResultTO createBookingAndConsigmentsParcel(
			BookingWrapperTO bookingWrapperTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingParcel::START------------>:::::::");
		String transStatus = "";
		BookingWrapperDO bookingWrapperDO = null;
		Set<String> failureConsingments = null;
		BookingResultTO result =  new BookingResultTO();
		try {
			// Preparing Bookings & Consignments
			
			bookingWrapperDO = BABookingConverter
					.baBookingParcelDomainConverter(bookingWrapperTO,
							bookingCommonService);
			failureConsingments = bookingWrapperDO.getFailureConsignments();
			
			if (!StringUtil.isNull(bookingWrapperDO)) {
				// Saving Bulk Save Bookings
				try {
					LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingParcel::saving booking and consignment in batch....");
					bookingWrapperDO = bookingCommonDAO.batchBookingUpdate(bookingWrapperDO);
					bookingCommonService.prepareBookingCNIds(bookingWrapperDO);
					LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingParcel::batch saving for booking and consignment is successfull....");
				} catch (CGSystemException e) {
					LOGGER.error("BABookingServiceImpl::saveOrUpdateBABookingParcel::batch saving for booking and consignment fails.");
					failureConsingments = saveBookingAndConsignment(
							bookingWrapperDO, failureConsingments);
				}
				if (bookingWrapperDO.isBulkSave()) {
					transStatus = CommonConstants.SUCCESS + "#"
							+ BookingConstants.IS_BULK_SAVE;
				} else {
					transStatus = prepareFailureConsignments(transStatus,
							failureConsingments);
				}
				result.setSuccessBookingsIds(bookingWrapperDO.getSuccessBookingsIds());
				result.setSuccessCNsIds(bookingWrapperDO.getSuccessCNsIds());
				result.setTransMessage(transStatus);
				result.setTransStatus(bookingWrapperDO.isBulkSave());
			}

		} catch (CGBusinessException e) {
			// transStatus = CommonConstants.SUCCESS + "#" + CommonConstants.NO;
			LOGGER.error("Exception in BABookingServiceImpl:: saveOrUpdateBABookingParcel() :"
					,e);
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception in BABookingServiceImpl:: saveOrUpdateBABookingParcel() :"
					, e);
			throw new CGSystemException(e);
		}
		LOGGER.info("BABookingServiceImpl::saveOrUpdateBABookingParcel::transaction status is : " + transStatus);
		LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingParcel::END------------>:::::::");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.BABookingService#getBABookings(java.lang.String
	 * , java.lang.Integer)
	 */
	@Override
	public List<BABookingParcelTO> getBABookings(String bookingDate,
			Integer businessAssociateId) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("BABookingServiceImpl::getBABookings::START------------>:::::::");
		List<BABookingParcelTO> baBookings = null;
		List<BookingDO> baBookingDOs = null;
		CNPricingDetailsTO cnPricingDtls = null;
		Date bkDate = DateUtil.stringToDDMMYYYYFormat(bookingDate);
		baBookingDOs = baBookingDAO.getBABookings(bkDate, businessAssociateId);
		if (!StringUtil.isEmptyList(baBookingDOs)) {
			baBookings = new ArrayList<>(baBookingDOs.size());
			for (BookingDO booking : baBookingDOs) {
				BABookingParcelTO bookingTO = new BABookingParcelTO();
				// Setting Consingmenr Pricing details
				ConsignmentTO consignment = consignmentCommonService
						.getConsingmentDtls(booking.getConsgNumber());
				bookingTO.setConsigmentTO(consignment);
				
			
				bookingTO.setBookingId(booking.getBookingId());
				bookingTO.setBookingDate(DateUtil
						.getDDMMYYYYDateToString(booking.getBookingDate()));
				bookingTO.setConsgNumber(booking.getConsgNumber());
				bookingTO.setRefNo(booking.getRefNo());
				bookingTO.setBookingOfficeId(booking.getBookingOfficeId());
				bookingTO.setBookingType(booking.getBookingType()
						.getBookingType());
				bookingTO.setBookingTime(DateUtil.getTimeFromDate(booking.getBookingDate()));
				if (!StringUtil.isEmptyInteger(booking.getNoOfPieces()))
					bookingTO.setNoOfPieces(booking.getNoOfPieces());
				if (booking.getActualWeight() != null
						&& booking.getActualWeight() > 0)
					bookingTO.setActualWeight(booking.getActualWeight());
				if (booking.getFianlWeight() != null
						&& booking.getFianlWeight() > 0)
					bookingTO.setFinalWeight(booking.getFianlWeight());
				if (booking.getVolWeight() != null
						&& booking.getVolWeight() > 0)
					bookingTO.setVolWeight(booking.getVolWeight());
				bookingTO.setConsgTypeId(booking.getConsgTypeId()
						.getConsignmentId());
				bookingTO.setConsgTypeName(booking.getConsgTypeId()
						.getConsignmentCode());
				bookingTO.setPincodeId(booking.getPincodeId().getPincodeId());
				bookingTO.setPincode(booking.getPincodeId().getPincode());
				// Setting City
				CityTO city = geographyCommonService.getCity(booking
						.getPincodeId().getPincode());
				if (city != null) {
					bookingTO.setCityId(city.getCityId());
					bookingTO.setCityName(city.getCityName());
				}

				bookingTO.setPrice(booking.getPrice());
				if (booking.getHeight() != null && booking.getHeight() > 0)
					bookingTO.setHeight(booking.getHeight());
				if (booking.getLength() != null && booking.getLength() > 0)
					bookingTO.setLength(booking.getLength());
				if (booking.getBreath() != null && booking.getBreath() > 0)
					bookingTO.setBreath(booking.getBreath());
				bookingTO.setCnStatus(booking.getCnStatus());

				bookingTO
						.setWeightCapturedMode(booking.getWeightCapturedMode());
				bookingTO.setOtherCNContent(booking.getOtherCNContent());
				bookingTO.setPaperWorkRefNo(booking.getPaperWorkRefNo());
				// Setting child entities
				if (!StringUtil.isNull(booking.getCnContentId())) {
					CNContentTO cnContentTO = new CNContentTO();
					CGObjectConverter.createToFromDomain(
							booking.getCnContentId(), cnContentTO);
					bookingTO.setCnContents(cnContentTO);
				}
				if (!StringUtil.isNull(booking.getCnPaperWorkId())) {
					CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
					CGObjectConverter.createToFromDomain(
							booking.getCnPaperWorkId(), cnPaperworkTO);
					bookingTO.setCnPaperWorks(cnPaperworkTO);
				}
				if (!StringUtil.isNull(consignment.getConsigneeTO())) {
					bookingTO.setConsignee(consignment.getConsigneeTO());
				}
				if (!StringUtil.isNull(consignment.getConsignorTO())) {
					bookingTO.setConsignor(consignment.getConsignorTO());
				}

				if (!StringUtil.isNull(consignment.getConsgPriceDtls())) {
					bookingTO.setCnPricingDtls(consignment.getConsgPriceDtls());
				}
				// Insurence Dtls
				bookingTO.setPolicyNo(booking.getInsurencePolicyNo());
				if (!StringUtil.isNull(booking.getInsuredBy())) {
					bookingTO.setInsuredBy(booking.getInsuredBy()
							.getInsuredByDesc());
					bookingTO.setInsuredById(booking.getInsuredBy()
							.getInsuredById());
				}
				// Start..Customer enhanchments
				// Setting Business Asoociate
				// Integer baId = booking.getBusinessAssociateId();
				/*
				 * BusinessAssociateTO ba = bookingCommonService
				 * .getBusinessAssociateByIdOrCode(baId,
				 * CommonConstants.EMPTY_STRING);
				 */
				bookingTO.setBizAssociateCode(booking.getCustomerId()
						.getCustomerCode()
						+ " - "
						+ booking.getCustomerId().getBusinessName());
				// End..Customer enhanchments
				baBookings.add(bookingTO);
			}
		}
		LOGGER.debug("BABookingServiceImpl::getBABookings::END------------>:::::::");
		return baBookings;
	}

	private Set<String> saveBookingAndConsignment(
			BookingWrapperDO bookingWrapperDO, Set<String> failureConsingments) {
		LOGGER.debug("BABookingServiceImpl::saveBookingAndConsignment::trying individual save/update for booking and consignment....");
		String failureCN = "";
		List<Integer> bookingIdList = new ArrayList<Integer>(bookingWrapperDO.getSucessConsignments().size());
		List<Integer> cnIdList = new ArrayList<Integer>(bookingWrapperDO.getSucessConsignments().size());
		for (int i = 0; i < bookingWrapperDO.getSucessConsignments().size(); i++) {
			try {
				BookingDO booking = (BookingDO) bookingWrapperDO
						.getSucessConsignments().get(i);
				
				ConsignmentDO consignment = (ConsignmentDO) bookingWrapperDO
						.getConsingments().get(i);
				failureCN = booking.getConsgNumber();
				LOGGER.trace("BABookingServiceImpl::saveBookingAndConsignment::individual save/update for booking and consignment [ " + failureCN + " ] initiated.");
				Integer[] bookingAndCnId = bookingCommonDAO.createBookingAndConsignment(booking, consignment);
				bookingIdList.add(bookingAndCnId[0]);
				cnIdList.add(bookingAndCnId[1]);
			} catch (CGSystemException ex) {
				LOGGER.error("BABookingServiceImpl::saveBookingAndConsignment::individual save/update FAILS for booking and consignment[ " + failureCN + " ]");
				if (StringUtil.isEmptyColletion(failureConsingments))
					failureConsingments = new HashSet<String>();
				failureConsingments.add(failureCN);
				LOGGER.error("BABookingServiceImpl::saveBookingAndConsignment::Error", ex);
			}
		}
		int failCount = failureConsingments.size();
		int successCount = bookingWrapperDO.getSucessConsignments().size()-failCount;
		LOGGER.debug("BABookingServiceImpl::saveBookingAndConsignment::success count for save is => " + successCount);
		LOGGER.debug("BABookingServiceImpl::saveBookingAndConsignment::falure count for save is =>" + failCount);
		bookingWrapperDO.setSuccessBookingsIds(bookingIdList);
		bookingWrapperDO.setSuccessCNsIds(cnIdList);
		LOGGER.debug("BABookingServiceImpl::saveOrUpdateBABookingDox:: saveBookingAndConsignment------------>END:::::::");
		return failureConsingments;
	}

	private String prepareFailureConsignments(String transStatus,
			Set<String> failureConsingments) {
		LOGGER.debug("BABookingServiceImpl::prepareFailureConsignments:: prepareFailureConsignments------------>START:::::::");
		Set<String> failureCns = failureConsingments;
		StringBuilder failureCNs = new StringBuilder();
		if (!StringUtil.isEmptyColletion(failureCns) && failureCns.size() > 0) {
			for (String consingment : failureCns) {
				failureCNs.append(consingment);
				failureCNs.append(CommonConstants.COMMA);
			}
			transStatus = CommonConstants.SUCCESS + "#" + failureCNs.toString();
		}
		LOGGER.debug("BABookingServiceImpl::prepareFailureConsignments:: prepareFailureConsignments------------>END:::::::");
		return transStatus;
	}

	/*
	 * public void createBookingAndConsignment(BookingDO booking, ConsignmentDO
	 * consignment) throws CGSystemException {
	 * bookingCommonDAO.createBooking(booking);
	 * consignmentDAO.createConsignment(consignment); }
	 */

}
