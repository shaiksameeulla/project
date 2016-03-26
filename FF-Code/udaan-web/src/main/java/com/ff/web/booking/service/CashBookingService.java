package com.ff.web.booking.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

/**
 * The Interface CashBookingService.
 */
public interface CashBookingService {

	/**
	 * Save or update booking dox.
	 * 
	 * @param cashBookingTO
	 *            the cash booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO saveOrUpdateBookingDox(CashBookingDoxTO cashBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update booking parcel.
	 * 
	 * @param cashBookingTO
	 *            the cash booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO saveOrUpdateBookingParcel(CashBookingParcelTO cashBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate cash discount.
	 * 
	 * @param booking
	 *            the booking
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateCashDiscount(BookingValidationTO booking)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate privilege card.
	 * 
	 * @param bookingValidationTO
	 *            the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validatePrivilegeCard(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException;

	public void sendCashDiscountEmail() throws CGSystemException,
			CGBusinessException, HttpException, ClassNotFoundException, IOException;
}
