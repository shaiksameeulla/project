/**
 * 
 */
package com.ff.web.booking.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BABookingParcelTO;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.CashBookingDoxTO;
import com.ff.booking.EmotionalBondBookingTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;

/**
 * The Interface EmotionalBondBookingService.
 * 
 * @author uchauhan
 */
public interface EmotionalBondBookingService {

	/**
	 * Generate the consignment number for emotional bond booking.
	 * 
	 * @param OffcCode
	 *            the offc code
	 * @param seqNums
	 *            the seq nums
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String generateEBNum(String OffcCode, Integer seqNums)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update emotional bond booking.
	 * 
	 * @param emotionalBondBookingTO
	 *            the emotional bond booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO saveOrUpdateBookingEmotionalBond(
			EmotionalBondBookingTO emotionalBondBookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGBusinessException, CGSystemException;

	/**
	 * Retrieves the booking details for given date
	 * 
	 * @param bookingDate
	 *            the booking date
	 * @param bookingType
	 *            the booking type
	 * @return the eB bookings
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<EmotionalBondBookingTO> getEBBookings(String bookingDate,
			String bookingType, Integer loginUserId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booking pref details for options
	 * @param stateId 
	 * 
	 * @return the booking pref details
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails(Integer stateId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Updates the emotional bond bookings details.
	 * 
	 * @param emotionalBondBookingTO
	 *            the emotional bond booking to
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public Boolean updateEBBookingsDtls(
			EmotionalBondBookingTO emotionalBondBookingTO)
			throws CGSystemException, CGBusinessException;

	public List<String> getBookingPrefCodes(List<Integer> preferenceIds)
			throws CGSystemException;

}
