package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BookingTypeConfigDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8216515383596351184L;
	private Integer bookingTypeConfigId;
	private Double maxDiscountAllowed;
	private String isRateCalcRequired="N";
	private String isDiscountAllowed="N";
	private Integer denominator;
	private BookingTypeDO bookingType;
	private Double maxDeclaredValAllowed;

	public Integer getBookingTypeConfigId() {
		return bookingTypeConfigId;
	}

	public void setBookingTypeConfigId(Integer bookingTypeConfigId) {
		this.bookingTypeConfigId = bookingTypeConfigId;
	}

	public Double getMaxDiscountAllowed() {
		return maxDiscountAllowed;
	}

	public void setMaxDiscountAllowed(Double maxDiscountAllowed) {
		this.maxDiscountAllowed = maxDiscountAllowed;
	}

	public String getIsRateCalcRequired() {
		return isRateCalcRequired;
	}

	public void setIsRateCalcRequired(String isRateCalcRequired) {
		this.isRateCalcRequired = isRateCalcRequired;
	}

	public String getIsDiscountAllowed() {
		return isDiscountAllowed;
	}

	public void setIsDiscountAllowed(String isDiscountAllowed) {
		this.isDiscountAllowed = isDiscountAllowed;
	}

	public Integer getDenominator() {
		return denominator;
	}

	public void setDenominator(Integer denominator) {
		this.denominator = denominator;
	}

	public BookingTypeDO getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingTypeDO bookingType) {
		this.bookingType = bookingType;
	}

	public Double getMaxDeclaredValAllowed() {
		return maxDeclaredValAllowed;
	}

	public void setMaxDeclaredValAllowed(Double maxDeclaredValAllowed) {
		this.maxDeclaredValAllowed = maxDeclaredValAllowed;
	}

}
