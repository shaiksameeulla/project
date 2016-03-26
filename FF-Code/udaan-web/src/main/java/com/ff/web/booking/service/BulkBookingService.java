/**
 * 
 */
package com.ff.web.booking.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.organization.OfficeTO;
import com.ff.web.booking.validator.BulkReturnObject;

/**
 * The Interface BulkBookingService.
 * 
 * @author uchauhan
 */
public interface BulkBookingService {

	/**
	 * Save or update bookings.
	 * 
	 * @param list
	 *            the bulk booking t os
	 * @param cnValidation
	 * @return the credit customer booking parcel to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BulkReturnObject handleBulkBooking(List<Object> list,
			List<String> cnNoList, BookingValidationTO cnValidation,
			String jobNumber) throws CGBusinessException, CGSystemException;

	public void proceedBulkBooking(CreditCustomerBookingParcelTO bulkBookingTO,
			OfficeTO loggedInOffice, final String filePath, String jobService)
			throws CGBusinessException, CGSystemException;

	public BookingValidationTO validateConsignmentForBulkPrinted(
			BookingValidationTO cnValidation) throws CGBusinessException,
			CGSystemException;

}
