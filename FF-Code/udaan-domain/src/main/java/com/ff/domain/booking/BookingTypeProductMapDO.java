package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.serviceOffering.ProductDO;

public class BookingTypeProductMapDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3478260031269258509L;
	
	
	private Integer BookingTypeProductMapId;
 	private BookingDO bookingId;
	private ProductDO productId;
	private Double MaxDiscountAllowed;
	private String IsRateCalcRequired;
	
	public Integer getBookingTypeProductMapId() {
		return BookingTypeProductMapId;
	}
	public void setBookingTypeProductMapId(Integer bookingTypeProductMapId) {
		BookingTypeProductMapId = bookingTypeProductMapId;
	}
	public BookingDO getBookingId() {
		return bookingId;
	}
	public void setBookingId(BookingDO bookingId) {
		this.bookingId = bookingId;
	}
	public ProductDO getProductId() {
		return productId;
	}
	public void setProductId(ProductDO productId) {
		this.productId = productId;
	}
	public Double getMaxDiscountAllowed() {
		return MaxDiscountAllowed;
	}
	public void setMaxDiscountAllowed(Double maxDiscountAllowed) {
		MaxDiscountAllowed = maxDiscountAllowed;
	}
	public String getIsRateCalcRequired() {
		return IsRateCalcRequired;
	}
	public void setIsRateCalcRequired(String isRateCalcRequired) {
		IsRateCalcRequired = isRateCalcRequired;
	}
}
