package com.ff.universe.booking.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingPreferenceDetailsTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;

/**
 * @author shashsax
 *
 */
public interface BookingUniversalService {
	/**
	 * @param consgNumbers
	 * @param consgType
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<ConsignmentModificationTO> getBookings(
			List<String> consgNumbers, String consgType)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param bookingValidationInputs
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public BookingValidationTO isConsignmentsBooked(
			BookingValidationTO bookingValidationInputs)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param consgList
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public Boolean isAtleastOneConsignmentBooked(List<String> consgList)
			throws CGSystemException, CGBusinessException;

	/**
	 * @param consgNumber
	 * @param manifestDirection
	 * @param manifestStatus
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public boolean isConsignmentClosed(String consgNumber,
			String manifestDirection, String manifestStatus)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param consignmentTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * @param bookingType
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public BookingTypeTO getBookingType(String bookingType)
			throws CGSystemException, CGBusinessException;

	/**
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<BookingPreferenceDetailsTO> getBookingPrefDetails()
			throws CGSystemException, CGBusinessException;

	/**
	 * @param customerTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getContractCustomerList(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * 
	 * @return
	 * @throws CGBusinessException 
	 * @throws CGSystemException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws HttpException 
	 */
	public void sendEmailForFocBooking() throws CGSystemException, CGBusinessException, HttpException, ClassNotFoundException, IOException;
}
