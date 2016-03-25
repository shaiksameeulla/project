package com.ff.domain.manifest;

import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;

/**
 * @author hkansagr
 * 
 */
public class BookingConsignmentDO {

	private ConsignmentDO consignmentDO;
	private BookingDO bookingDO;

	/**
	 * BookingConsignmnetDO constructor
	 */
	public BookingConsignmentDO() {

	}

	/**
	 * @param consignmentDO
	 * @param bookingDO
	 */
	public BookingConsignmentDO(ConsignmentDO consignmentDO, BookingDO bookingDO) {
		this.consignmentDO = consignmentDO;
		this.bookingDO = bookingDO;
	}

	/**
	 * @return the consignmentDO
	 */
	public ConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}

	/**
	 * @param consignmentDO
	 *            the consignmentDO to set
	 */
	public void setConsignmentDO(ConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}

	/**
	 * @return the bookingDO
	 */
	public BookingDO getBookingDO() {
		return bookingDO;
	}

	/**
	 * @param bookingDO
	 *            the bookingDO to set
	 */
	public void setBookingDO(BookingDO bookingDO) {
		this.bookingDO = bookingDO;
	}

}
