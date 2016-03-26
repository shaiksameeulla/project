/**
 * 
 */
package com.ff.web.booking.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;

/**
 * The Interface EBBookingDAO.
 *
 * @author uchauhan
 */
public interface EBBookingDAO {
	
	/**
	 * Gets the booking details for selected date 
	 *
	 * @param bookingDate the booking date
	 * @param bookingType the booking type
	 * @return list of Booking
	 * @throws CGSystemException if any database error occurs
	 */
	public List<BookingDO> getEBBookings(Date bookingDate,String bookingType,Integer loginOffceId) throws CGSystemException;

	/**
	 * Gets the booking pref details.
	 * @param stateId 
	 *
	 * @return the booking pref details
	 * @throws CGSystemException if any database error occurs
	 */
	public List<BookingPreferenceDetailsDO> getBookingPrefDetails(Integer stateId) throws CGSystemException;

	/**
	 * Updates the status of Emotional Bond Booking.
	 *
	 * @param bookingDOs list of booking for which the status has to be updated
	 * @return {true} if updated successfully
	 *  @return {false} if not updated successfully
	 * @throws CGSystemException if any database error occurs
	 */
	public Boolean updateEBBookingsDtls(List<BookingDO> bookingDOs) throws CGSystemException;
	
	public List<String> getBookingPrefCodes(List<Integer> preferenceIds)
			throws CGSystemException;

}
