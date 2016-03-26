package com.ff.web.booking.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeConfigDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.PrivilegeCardTransactionDO;

/**
 * The Interface BookingCommonDAO.
 */
public interface BookingCommonDAO {

	/**
	 * Save pickup booking.
	 * 
	 * @param bookings
	 *            the bookings
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Integer> savePickupBooking(List<BookingDO> bookings)
			throws CGSystemException;

	/**
	 * Save or update booking.
	 * 
	 * @param bookings
	 *            the bookings
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<Integer> saveOrUpdateBooking(List<BookingDO> bookings)
			throws CGSystemException;

	/**
	 * Update booking.
	 * 
	 * @param bookingDO
	 *            the booking do
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public Integer updateBooking(BookingDO bookingDO) throws CGSystemException;

	/**
	 * Gets the booking type.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking type
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingTypeDO getBookingType(String bookingType)
			throws CGSystemException;

	/**
	 * Checks if is consg booked.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @return true, if is consg booked
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public boolean isConsgBooked(String consgNumber) throws CGSystemException;

	/**
	 * Save or update privilege card trans dtls.
	 * 
	 * @param privgCardTransDtls
	 *            the privg card trans dtls
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public boolean saveOrUpdatePrivilegeCardTransDtls(
			PrivilegeCardTransactionDO privgCardTransDtls)
			throws CGSystemException;

	/**
	 * Gets the booking type config.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking type config
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingTypeConfigDO getBookingTypeConfig(String bookingType)
			throws CGSystemException;

	/**
	 * Gets the booking by process.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param processCode
	 *            the process code
	 * @return the booking by process
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingDO getBookingByProcess(String consgNumber, String processCode)
			throws CGSystemException;

	/**
	 * Update booking cn status.
	 * 
	 * @param cnNumbers
	 *            the cn numbers
	 * @param cnStatus
	 *            the cn status
	 * @return true, if successful
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public boolean updateBookingCNStatus(List<String> cnNumbers, String cnStatus)
			throws CGSystemException;

	/**
	 * Gets the booking dtls.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param processCode
	 *            the process code
	 * @return the booking dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Object[]> getBookingDtls(String consgNumber)
			throws CGSystemException;

	public List<BookingDO> saveOrUpdateBookingList(List<BookingDO> bookings)
			throws CGSystemException;

	public Boolean createBooking(BookingDO booking) throws CGSystemException;

	public BookingWrapperDO batchBookingUpdate(BookingWrapperDO bookingWrapperDO)
			throws CGSystemException;
	
	public void batchBookingSaveUpdate(List<BookingDO> bookings)
			throws CGSystemException;

	public  Integer[] createBookingAndConsignment(BookingDO booking,
			ConsignmentDO consignment) throws CGSystemException;

	public List<Object[]> getBookingDtlsByStatus(String consgNumber) throws CGSystemException;

	public boolean deleteConsignments(List<String> cnNumbers)
			throws CGSystemException;

	public List<BookingTypeDO> getAllBookingType()
			throws CGSystemException;

	public boolean isChildConsgBooked(String consgNumber)
			throws CGSystemException;

	public boolean isConsgBookedForPickup(String consgNumber)	throws CGSystemException;

	public boolean isConsgBookedAsChildCn(String consgNumber) throws CGSystemException;


}
