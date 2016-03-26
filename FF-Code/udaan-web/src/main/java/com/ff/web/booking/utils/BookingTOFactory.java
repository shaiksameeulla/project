package com.ff.web.booking.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.ff.booking.BABookingDoxTO;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.CashBookingParcelTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.web.booking.constants.BookingConstants;

/**
 * A factory for creating BookingTO objects.
 */
public abstract class BookingTOFactory {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(BookingUtils.class);

		private static final Logger LOGGER = LoggerFactory
				.getLogger(BookingTOFactory.class);
	/**
	 * Gets the booking to.
	 *
	 * @param bookingType the booking type
	 * @param consgType the consg type
	 * @return the booking to
	 */
	public static BookingTO getBookingTO(String bookingType, String consgType) {
		LOGGER.debug("BookingTOFactory::getBookingTO::START------------>:::::::");
		BookingTO booking = null;
		if (StringUtils.equalsIgnoreCase(BookingConstants.CASH_BOOKING,
				bookingType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				booking = new CashBookingDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				booking = new CashBookingParcelTO();
		}
		if (StringUtils.equalsIgnoreCase(BookingConstants.BA_BOOKING,
				bookingType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				booking = new BABookingDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				booking = new BABookingParcelTO();
		}
		if (StringUtils.equalsIgnoreCase(BookingConstants.CCC_BOOKING,
				bookingType)) {
			if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, consgType))
				booking = new CreditCustomerBookingDoxTO();
			else if (StringUtils.equalsIgnoreCase(
					CommonConstants.CONSIGNMENT_TYPE_PARCEL, consgType))
				booking = new CreditCustomerBookingParcelTO();
		}
		LOGGER.debug("BookingTOFactory::getBookingTO::END------------>:::::::");
		return booking;
	}
}
