package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.booking.BookingTypeDO;

public class BookingTypeProductMapDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 3478260031269258509L;

	private Integer bookingTypeProductMapId;
	private BookingTypeDO bookingType;
	private ProductDO product;
	private String status = "A";

	public Integer getBookingTypeProductMapId() {
		return bookingTypeProductMapId;
	}

	public void setBookingTypeProductMapId(Integer bookingTypeProductMapId) {
		this.bookingTypeProductMapId = bookingTypeProductMapId;
	}

	public BookingTypeDO getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingTypeDO bookingType) {
		this.bookingType = bookingType;
	}

	public ProductDO getProduct() {
		return product;
	}

	public void setProduct(ProductDO product) {
		this.product = product;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}