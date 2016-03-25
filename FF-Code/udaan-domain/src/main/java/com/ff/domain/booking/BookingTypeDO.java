package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BookingTypeDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4792421530099936463L;
	private Integer bookingTypeId;
	private String bookingType;
	private String bookingTypeDesc;
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public String getBookingTypeDesc() {
		return bookingTypeDesc;
	}
	public void setBookingTypeDesc(String bookingTypeDesc) {
		this.bookingTypeDesc = bookingTypeDesc;
	}
	
}
