package com.ff.web.booking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BABookingDoxTO;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingWrapperTO;

/**
 * The Interface BABookingService.
 */
public interface BABookingService {

	/**
	 * Save or update ba booking dox.
	 *
	 * @param baBookingTOs the ba booking t os
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public BookingResultTO createBookingAndConsigmentsDox(BookingWrapperTO bookingWrapperTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update ba booking parcel.
	 *
	 * @param baBookingTOs the ba booking t os
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public BookingResultTO createBookingAndConsigmentsParcel(
			BookingWrapperTO bookingWrapperTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the bA bookings.
	 *
	 * @param bookingDate the booking date
	 * @param businessAssociateId the business associate id
	 * @return the bA bookings
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<BABookingParcelTO> getBABookings(String bookingDate,
			Integer businessAssociateId) throws CGBusinessException,
			CGSystemException;
	
	
	
}
