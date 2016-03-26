package com.ff.web.booking.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;

/**
 * The Interface BABookingDAO.
 */
public interface BABookingDAO {
	
	/**
	 * Gets the bA bookings.
	 *
	 * @param bookingDate the booking date
	 * @param businessAssociateId the business associate id
	 * @return the bA bookings
	 * @throws CGSystemException the cG system exception
	 */
	public List<BookingDO> getBABookings(Date bookingDate,
			Integer businessAssociateId) throws CGSystemException;
}
