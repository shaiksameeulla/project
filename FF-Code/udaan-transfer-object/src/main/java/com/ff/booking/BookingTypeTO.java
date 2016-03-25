package com.ff.booking;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BookingTypeTO.
 */
public class BookingTypeTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6111270657832310561L;
	
	/** The booking type id. */
	private Integer bookingTypeId;
	
	/** The booking type. */
	private String bookingType;
	
	/** The booking type desc. */
	private String bookingTypeDesc;

	/**
	 * Gets the booking type id.
	 *
	 * @return the booking type id
	 */
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}

	/**
	 * Sets the booking type id.
	 *
	 * @param bookingTypeId the new booking type id
	 */
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	/**
	 * Gets the booking type.
	 *
	 * @return the booking type
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * Sets the booking type.
	 *
	 * @param bookingType the new booking type
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * Gets the booking type desc.
	 *
	 * @return the booking type desc
	 */
	public String getBookingTypeDesc() {
		return bookingTypeDesc;
	}

	/**
	 * Sets the booking type desc.
	 *
	 * @param bookingTypeDesc the new booking type desc
	 */
	public void setBookingTypeDesc(String bookingTypeDesc) {
		this.bookingTypeDesc = bookingTypeDesc;
	}

}
