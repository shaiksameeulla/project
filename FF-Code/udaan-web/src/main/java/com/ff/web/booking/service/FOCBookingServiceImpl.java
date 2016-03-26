package com.ff.web.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingResultTO;
import com.ff.booking.FOCBookingDoxTO;
import com.ff.booking.FOCBookingParcelTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.tracking.ProcessTO;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.converter.FOCBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;

/**
 * The Class FOCBookingServiceImpl.
 */
public class FOCBookingServiceImpl implements FOCBookingService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(FOCBookingServiceImpl.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService;

	/** The booking common dao. */
	private BookingCommonDAO bookingCommonDAO;

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
	 * Sets the booking common dao.
	 * 
	 * @param bookingCommonDAO
	 *            the new booking common dao
	 */
	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.FOCBookingService#saveOrUpdateFOCBookingDox
	 * (com.ff.booking.FOCBookingDoxTO)
	 */
	@Override
	public BookingResultTO saveOrUpdateBookingDox(FOCBookingDoxTO focBookingDoxTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("FOCBookingServiceImpl::saveOrUpdateFOCBookingDox::START------------>:::::::");
		BookingDO booking = null;
		String transStatus = "";
		BookingResultTO result = new BookingResultTO();
		try {
			BookingTypeDO focBooking = bookingCommonDAO
					.getBookingType(BookingConstants.FOC_BOOKING);// FC.
			ProcessTO process = getProcess();
			focBookingDoxTO.setProcessTO(process);
			if (focBooking != null)
				focBookingDoxTO.setBookingTypeId(focBooking.getBookingTypeId());
			booking = FOCBookingConverter
					.focBookingDoxDomainConverter(focBookingDoxTO);
			if (booking != null) {
				List<BookingDO> bookings = new ArrayList<BookingDO>(1);
				bookings.add(booking);
				List<Integer> successBookedIds = bookingCommonDAO.saveOrUpdateBooking(bookings);
				
				if (!StringUtil.isEmptyColletion(successBookedIds)) {
					List<Integer> successCNsIds = bookingCommonService.saveConsignmentAndRateDox(
							focBookingDoxTO, consgRateDtls);
					result.setSuccessCNsIds(successCNsIds);
					result.setSuccessBookingsIds(successBookedIds);
					transStatus = CommonConstants.SUCCESS;
					bookingCommonService.process2WayWrite(successBookedIds, successCNsIds);
				} else {
					transStatus = CommonConstants.FAILURE;
				}
				result.setTransMessage(transStatus);
			}
		} catch (CGBusinessException e) {
			LOGGER.debug("Exception in saveOrUpdateFOCBookingDox() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.debug("Exception in saveOrUpdateFOCBookingDox() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("FOCBookingServiceImpl::saveOrUpdateFOCBookingDox::END------------>:::::::");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.booking.service.FOCBookingService#saveOrUpdateFOCBookingParcel
	 * (com.ff.booking.FOCBookingParcelTO)
	 */
	@Override
	public BookingResultTO saveOrUpdateBookingParcel(
			FOCBookingParcelTO focBookingParcelTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("FOCBookingServiceImpl::saveOrUpdateFOCBookingParcel::START------------>:::::::");
		BookingDO booking = null;
		String transStatus = "";
		BookingResultTO result = new BookingResultTO();
		try {
			BookingTypeDO cashBooking = bookingCommonDAO
					.getBookingType(BookingConstants.FOC_BOOKING);
			ProcessTO process = getProcess();
			focBookingParcelTO.setProcessTO(process);
			focBookingParcelTO.setBookingTypeId(cashBooking.getBookingTypeId());
			booking = FOCBookingConverter
					.focBookingParcelDomainConverter(focBookingParcelTO);
			
			if (booking != null) {
				List<BookingDO> bookings = new ArrayList<BookingDO>(1);
				bookings.add(booking);
				List<Integer> successBookedIds = bookingCommonDAO.saveOrUpdateBooking(bookings);
				if (!StringUtil.isEmptyColletion(successBookedIds)) {
					List<Integer> successCNsIds = bookingCommonService.saveConsignmentAndRateParcel(
							focBookingParcelTO, consgRateDtls);
					result.setSuccessCNsIds(successCNsIds);
					result.setSuccessBookingsIds(successBookedIds);
					transStatus = "SUCCESS";
					//bookingCommonService.process2WayWrite(successBookedIds, successCNsIds);
				} else
					transStatus = "FAILURE";
			}
			result.setTransMessage(transStatus);
		} catch (CGBusinessException e) {
			LOGGER.debug("Exception in saveOrUpdateFOCBookingParcel() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.debug("Exception in saveOrUpdateFOCBookingParcel() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.debug("FOCBookingServiceImpl::saveOrUpdateFOCBookingParcel::START------------>:::::::");
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

}
