package com.ff.universe.booking.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;
import com.ff.domain.booking.BookingTypeDO;

public interface BookingUniversalDAO {
	List<BookingDO> getBookings(List<String> consgNumbers, String consgType)
			throws CGBusinessException;

	BookingValidationTO updateBookings(List<BookingDO> bookings)
			throws CGBusinessException;

	BookingValidationTO isConsignmentsBooked(
			BookingValidationTO bookingValidationInputs)
			throws CGBusinessException;

	Boolean isAtleastOneConsignmentBooked(List<String> consgList)
			throws CGBusinessException;

	String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException;

	public BookingTypeDO getBookingType(String bookingType)
			throws CGBusinessException;
	
	public List<BookingPreferenceDetailsDO> getBookingPrefDetails()
			throws CGSystemException;

	BookingDO getBookingDtlsByConsgNo(String consgNumber) throws CGSystemException;

	/*CNPricingDetailsDO getCnPriceByConsgID(Integer consgId)throws CGSystemException;  */
	
	public List<BookingDO> getFocBookingDetailsByDate(String currentDate, String previousDate) throws CGBusinessException, CGSystemException;
	
	BookingDO getBookingDtlsByConsgNoWithDateFilter(String consgNumber) throws CGSystemException;

	/**
	 * Gets the booking date by consg no.
	 *
	 * @param consgNumber the consg number
	 * @return the booking date by consg no
	 * @throws CGSystemException the CG system exception
	 */
	Date getBookingDateByConsgNo(String consgNumber) throws CGSystemException;
}
