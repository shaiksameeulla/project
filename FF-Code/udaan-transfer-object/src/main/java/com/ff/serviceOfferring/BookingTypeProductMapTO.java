package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.booking.BookingTypeTO;

public class BookingTypeProductMapTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5268115207493605406L;
	private Integer bookingTypeProductMapId;
	private BookingTypeTO bookingTypeTO;
	private ProductTO productTO;
	private String status;

	public Integer getBookingTypeProductMapId() {
		return bookingTypeProductMapId;
	}

	public void setBookingTypeProductMapId(Integer bookingTypeProductMapId) {
		this.bookingTypeProductMapId = bookingTypeProductMapId;
	}

	public BookingTypeTO getBookingTypeTO() {
		return bookingTypeTO;
	}

	public void setBookingTypeTO(BookingTypeTO bookingTypeTO) {
		this.bookingTypeTO = bookingTypeTO;
	}

	public ProductTO getProductTO() {
		return productTO;
	}

	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
