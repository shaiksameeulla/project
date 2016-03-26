package com.ff.web.booking.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.CustomerTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.converter.CreditCustomerBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;

/**
 * The Class CreditCustomerBookingServiceImpl.
 */
public class CreditCustomerBookingServiceImpl implements
		CreditCustomerBookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreditCustomerBookingServiceImpl.class);

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;



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

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.CreditCustomerBookingService#
	 * saveOrUpdateCreditCustBookingDox(java.util.List)
	 */
	@Override
	public String saveOrUpdateBookingDox(
			List<CreditCustomerBookingDoxTO> bookingTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingServiceImpl::saveOrUpdateCreditCustBookingDox::START------------>:::::::");
		List<BookingDO> bookings = null;
		String transStatus = "";
		try {
			bookings = CreditCustomerBookingConverter
					.creaditCustBookingDoxDomainConverter(bookingTOs,
							bookingCommonService);
			if (!StringUtil.isEmptyList(bookings)) {
				List<Integer> successBookingIds = bookingCommonDAO.saveOrUpdateBooking(bookings);
				if (successBookingIds != null && !successBookingIds.isEmpty()) {
					// Setting CNPricing details
					List<Integer> successCnIds = bookingCommonService.saveCNPricingDtlsDox(bookingTOs);
					transStatus = CommonConstants.SUCCESS;
					bookingCommonService.process2WayWrite(successBookingIds, successCnIds);
				} else
					transStatus = CommonConstants.FAILURE;
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: saveOrUpdateCreditCustBookingDox() :"
					+ e.getMessage());
			transStatus = CommonConstants.FAILURE;
			throw new CGSystemException(e);
		} catch (Exception e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: saveOrUpdateCreditCustBookingDox() :"
					+ e.getMessage());
			transStatus = CommonConstants.FAILURE;
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl::saveOrUpdateCreditCustBookingDox::END------------>:::::::");
		return transStatus;
	}

	/*private void processd2WayWrite(List<Integer> successBookingsIds, List<Integer> successCnsIds) {
		try {
			bookingCommonService.process2WayWrite(successBookingsIds, successCnsIds);
		} catch (Exception ex) {
			LOGGER.error("CreditCustomerBookingServiceImpl::saveOrUpdateBookingDox:: error in 2way write", ex);
		}
	}*/
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.CreditCustomerBookingService#
	 * saveOrUpdateCreditCustBookingParcel(java.util.List)
	 */
	@Override
	public String saveOrUpdateBookingParcel(
			List<CreditCustomerBookingParcelTO> bookingTOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingServiceImpl::saveOrUpdateCreditCustBookingParcel::START------------>:::::::");
		List<BookingDO> bookings = null;
		String transStatus = "";
		try {
			bookings = CreditCustomerBookingConverter
					.creaditCustBookingParcelDomainConverter(bookingTOs,
							bookingCommonService);
			if (!StringUtil.isEmptyList(bookings)) {
				List<Integer> successBookingIds = bookingCommonDAO.saveOrUpdateBooking(bookings);
				if (successBookingIds != null && !successBookingIds.isEmpty()) {
					// Setting CNPricing details
					List<Integer> successCNsIds = bookingCommonService.saveCNPricingDtlsParcel(bookingTOs);
					transStatus = CommonConstants.SUCCESS;
					bookingCommonService.process2WayWrite(successBookingIds, successCNsIds);
				} else
					transStatus = CommonConstants.FAILURE;
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: saveOrUpdateBABookingParcel() :"
					+ e.getMessage());
			throw new CGSystemException(e);
		} catch (Exception e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: saveOrUpdateBABookingParcel() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl::saveOrUpdateCreditCustBookingParcel::END------------>:::::::");
		return transStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.CreditCustomerBookingService#
	 * saveOrUpdateCreditCustBookingDox(java.util.List)
	 */
	@Override
	public BookingResultTO createBookingAndConsigmentsDox(BookingWrapperTO bookingWrapper)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsDox::START------------>:::::::");
		String transStatus = "";
		BookingWrapperDO bookingWrapperDO = null;
		Set<String> failureConsingments = null;
		BookingResultTO result = new BookingResultTO();
		try {
			LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsDox::DO preparation started------------>:::::::");
			bookingWrapperDO = CreditCustomerBookingConverter
					.creaditCustBookingDoxDomainConverter(bookingWrapper,
							bookingCommonService);
			LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsDox::DO preparation completed------------>:::::::");
			if (!StringUtil.isNull(bookingWrapperDO)) {
				// Saving Bulk Bookings
				try {
					
					bookingWrapperDO = bookingCommonDAO
							.batchBookingUpdate(bookingWrapperDO);
					LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsDox::batch update completed------------>:::::::");
					bookingCommonService.prepareBookingCNIds(bookingWrapperDO);
				} catch (CGSystemException e) {
					// Individual Save
					LOGGER.error("Exception Occurred in :: CreditCustomerBookingServiceImpl::createBookingAndConsigmentsDox():: after batchBookingUpdate fails--->looking for individual save:::::::");
					failureConsingments = saveBookingAndConsignment(
							bookingWrapperDO, failureConsingments);
					
				}
				if (bookingWrapperDO.isBulkSave()) {
					transStatus = CommonConstants.SUCCESS + "#"
							+ BookingConstants.IS_BULK_SAVE;
					failureConsingments = bookingWrapper
							.getFailureConsignments();
					if (!StringUtil.isEmptyColletion(failureConsingments)
							&& failureConsingments.size() > 0) {
						transStatus = "";
						transStatus = prepareFailureConsignments(transStatus,
								failureConsingments);
					}
				} else {
					transStatus = prepareFailureConsignments(transStatus,
							failureConsingments);
				}
				result.setSuccessBookingsIds(bookingWrapperDO.getSuccessBookingsIds());
				result.setSuccessCNsIds(bookingWrapperDO.getSuccessCNsIds());
				result.setTransMessage(transStatus);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: createBookingAndConsigmentsDox() :"
					+ e.getMessage());
			throw e;
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: createBookingAndConsigmentsDox() :"
					+ e.getMessage());
			throw e;
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl::saveOrUpdateCreditCustBookingDox::END------------>:::::::");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.booking.service.CreditCustomerBookingService#
	 * saveOrUpdateCreditCustBookingParcel(java.util.List)
	 */
	@Override
	public BookingResultTO createBookingAndConsigmentsParcel(
			BookingWrapperTO bookingWrapper) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsParcel::START------------>:::::::");
		String transStatus = "";
		BookingWrapperDO bookingWrapperDO = null;
		Set<String> failureConsingments = null;
		BookingResultTO result = new BookingResultTO();
		try {
			bookingWrapperDO = CreditCustomerBookingConverter
					.creaditCustBookingParcelDomainConverter(bookingWrapper,
							bookingCommonService);
			if (!StringUtil.isNull(bookingWrapperDO)) {
				// Saving Bulk Bookings
				try {
					bookingWrapperDO = bookingCommonDAO
							.batchBookingUpdate(bookingWrapperDO);
					bookingCommonService.prepareBookingCNIds(bookingWrapperDO);
					
					
				} catch (CGSystemException e) {
					// Individual Save
					LOGGER.error("Exception Occurred in :: CreditCustomerBookingServiceImpl::createBookingAndConsigmentsParcel:: batchBookingUpdate------------>:::::::");
					failureConsingments = saveBookingAndConsignment(
							bookingWrapperDO, failureConsingments);
				}
					
				if (!StringUtil.isEmptyColletion(failureConsingments)
						&& failureConsingments.size() > 0) {
					transStatus = "";
					transStatus = prepareFailureConsignments(transStatus,
							failureConsingments);
				}else{
					transStatus = CommonConstants.SUCCESS + "#"
							+ BookingConstants.IS_BULK_SAVE;
				}
				result.setSuccessBookingsIds(bookingWrapperDO.getSuccessBookingsIds());
				result.setSuccessCNsIds(bookingWrapperDO.getSuccessCNsIds());
				result.setTransMessage(transStatus);
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: createBookingAndConsigmentsParcel() :"
					,e);
			transStatus = CommonConstants.FAILURE;
			throw e;
		} catch (CGBusinessException e) {
			LOGGER.error("Exception in CreditCustomerBookingServiceImpl :: createBookingAndConsigmentsParcel() :"
					,e);
			transStatus = CommonConstants.FAILURE;
			throw e;
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl::createBookingAndConsigmentsParcel::END------------>:::::::");
		return result;
	}
	
	
	
	/*private void call2WayWriteProcess(BookingWrapperDO bookingWrapperDO) {
		try {
			List<Integer> bookingIds = null;
			List<Integer> cnIds = null;
			if(bookingWrapperDO.isBulkSave()) {
				List<BookingDO> bookings = bookingWrapperDO.getSucessConsignments();
				List<ConsignmentDO> consignments = bookingWrapperDO.getConsingments();
				if(bookings == null || bookings.isEmpty() || consignments == null || consignments.isEmpty())
					return;
				
				bookingIds = new ArrayList(bookings.size());
				cnIds = new ArrayList(bookings.size());
				for(int i =0; i< bookings.size(); i++) {
					bookingIds.add(bookings.get(i).getBookingId());
					cnIds.add(consignments.get(i).getConsgId());
				}
			} else {
				bookingIds = bookingWrapperDO.getSuccessBookingsIds();
				cnIds = bookingWrapperDO.getSuccessCNsIds();
			}
			processd2WayWrite(bookingIds, cnIds);
		} catch (Exception ex) {
			LOGGER.error("Exception Occurred in ::saveBookingAndConsignment:: saveBookingAndConsignment :2way write------------>", ex);
		}
	}*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.CreditCustomerBookingService#getBookingByProcess
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public CreditCustomerBookingDoxTO getBookingByProcess(String consgNumber,
			String processCode) throws CGBusinessException, CGSystemException {
		LOGGER.debug("CreditCustomerBookingServiceImpl::getBookingByProcess::START------------>:::::::");
		CreditCustomerBookingDoxTO bookingTO = null;
		BookingDO booking = bookingCommonDAO.getBookingByProcess(consgNumber,
				processCode);
		if (!StringUtil.isNull(booking)) {
			bookingTO = new CreditCustomerBookingDoxTO();
			bookingTO.setBookingId(booking.getBookingId());
			bookingTO.setPickupRunsheetNo(booking.getPickRunsheetNo());
			if (!StringUtil.isNull(booking.getCustomerId())) {
				CustomerTO customer = new CustomerTO();
				customer.setCustomerId(booking.getCustomerId().getCustomerId());
				
				customer.setCustomerCode(booking.getShippedToCode());
				customer.setBusinessName(booking.getCustomerId()
						.getBusinessName());
				bookingTO.setCustomer(customer);
			}
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl::getBookingByProcess::END------------>:::::::");
		return bookingTO;
	}

	private Set<String> saveBookingAndConsignment(
			BookingWrapperDO bookingWrapperDO, Set<String> failureConsingments) {
		LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment------------>individual save START:::::::");
		String failureCN = "";
		bookingWrapperDO.setBulkSave(Boolean.TRUE);
		List<Integer> bookingIdList = new ArrayList<Integer>(bookingWrapperDO.getSucessConsignments().size());
		List<Integer> cnIdList = new ArrayList<Integer>(bookingWrapperDO.getSucessConsignments().size());
		for (int i = 0; i < bookingWrapperDO.getSucessConsignments().size(); i++) {
			try {
				BookingDO booking = (BookingDO) bookingWrapperDO
						.getSucessConsignments().get(i);
				ConsignmentDO consignment = (ConsignmentDO) bookingWrapperDO
						.getConsingments().get(i);
				failureCN = booking.getConsgNumber();
				LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment::individual save bookingDO[ " + booking.getConsgNumber()+ " ] consignmentDO[ " + consignment.getConsgNo() + " ]");
				Integer[] bookingAndCnId = bookingCommonDAO.createBookingAndConsignment(booking,
						consignment);
				bookingIdList.add(bookingAndCnId[0]);
				cnIdList.add(bookingAndCnId[1]);
			} catch (CGSystemException ex) {
				LOGGER.error("Exception Occurred in ::CreditCustomerBookingServiceImpl:: saveBookingAndConsignment::CGSystemException :Individual Save fails------------>");
				if (StringUtil.isEmptyColletion(failureConsingments))
					failureConsingments = new HashSet();
				failureConsingments.add(failureCN);
				bookingWrapperDO.setBulkSave(Boolean.FALSE);
			}
		}
		LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment->success booking ids in individual save are:::::::" + bookingIdList);
		LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment->success consignment ids in individual save are:::::::" + cnIdList);
		LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment->failed CNs in individual save are:::::::" + failureConsingments);
		
		bookingWrapperDO.setSuccessBookingsIds(bookingIdList);
		bookingWrapperDO.setSuccessCNsIds(cnIdList);
		LOGGER.debug("CreditCustomerBookingServiceImpl:: saveBookingAndConsignment------------>individual save END:::::::");
		return failureConsingments;
	}

	private String prepareFailureConsignments(String transStatus,
			Set<String> failureConsingments) {
		Set<String> failureCns = failureConsingments;
		StringBuilder failureCNs = new StringBuilder();
		if (!StringUtil.isEmptyColletion(failureCns) && failureCns.size() > 0) {
			for (String consingment : failureCns) {
				failureCNs.append(consingment);
				failureCNs.append(CommonConstants.COMMA);
			}
			transStatus = CommonConstants.SUCCESS + "#" + failureCNs.toString();
		}
		return transStatus;
	}
}
