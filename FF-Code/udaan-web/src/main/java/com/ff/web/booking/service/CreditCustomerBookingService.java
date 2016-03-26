package com.ff.web.booking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingWrapperTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;

/**
 * The Interface CreditCustomerBookingService.
 */
public interface CreditCustomerBookingService {
	
	/**
	 * Save or update credit cust booking dox.
	 *
	 * @param bookingTOs the booking t os
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateBookingDox(
			List<CreditCustomerBookingDoxTO> bookingTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update credit cust booking parcel.
	 *
	 * @param bookingTOs the booking t os
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public String saveOrUpdateBookingParcel(
			List<CreditCustomerBookingParcelTO> bookingTOs)
			throws CGBusinessException, CGSystemException;
	
	/**
	 * Save or update credit cust booking dox.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO createBookingAndConsigmentsDox(BookingWrapperTO bookingWrapper)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update credit cust booking parcel.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO createBookingAndConsigmentsParcel(
			BookingWrapperTO bookingWrapper) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the booking by process.
	 *
	 * @param consgNumber the consg number
	 * @param processCode the process code
	 * @return the booking by process
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public CreditCustomerBookingDoxTO getBookingByProcess(String consgNumber,
			String processCode) throws CGBusinessException, CGSystemException;
}
