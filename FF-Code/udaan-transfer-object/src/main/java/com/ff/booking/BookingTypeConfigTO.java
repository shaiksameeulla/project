package com.ff.booking;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BookingTypeConfigTO.
 */
public class BookingTypeConfigTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 300595624493657705L;
	
	/** The booking type config id. */
	private Integer bookingTypeConfigId;
	
	/** The max discount allowed. */
	private Double maxDiscountAllowed;
	
	/** The is rate calc required. */
	private String isRateCalcRequired;
	
	/** The is discount allowed. */
	private String isDiscountAllowed;
	
	/** The denominator. */
	private Integer denominator;
	
	/** The booking type to. */
	private BookingTypeTO bookingTypeTO;
	
	/** The max declared val allowed. */
	private Double maxDeclaredValAllowed;

	// output
	/** The error msg. */
	private String errorMsg;
	
	/** The is declared values exceeded. */
	private String isDeclaredValuesExceeded = "N";

	/**
	 * Gets the booking type config id.
	 *
	 * @return the booking type config id
	 */
	public Integer getBookingTypeConfigId() {
		return bookingTypeConfigId;
	}

	/**
	 * Sets the booking type config id.
	 *
	 * @param bookingTypeConfigId the new booking type config id
	 */
	public void setBookingTypeConfigId(Integer bookingTypeConfigId) {
		this.bookingTypeConfigId = bookingTypeConfigId;
	}

	/**
	 * Gets the max discount allowed.
	 *
	 * @return the max discount allowed
	 */
	public Double getMaxDiscountAllowed() {
		return maxDiscountAllowed;
	}

	/**
	 * Sets the max discount allowed.
	 *
	 * @param maxDiscountAllowed the new max discount allowed
	 */
	public void setMaxDiscountAllowed(Double maxDiscountAllowed) {
		this.maxDiscountAllowed = maxDiscountAllowed;
	}

	/**
	 * Gets the checks if is rate calc required.
	 *
	 * @return the checks if is rate calc required
	 */
	public String getIsRateCalcRequired() {
		return isRateCalcRequired;
	}

	/**
	 * Sets the checks if is rate calc required.
	 *
	 * @param isRateCalcRequired the new checks if is rate calc required
	 */
	public void setIsRateCalcRequired(String isRateCalcRequired) {
		this.isRateCalcRequired = isRateCalcRequired;
	}

	/**
	 * Gets the checks if is discount allowed.
	 *
	 * @return the checks if is discount allowed
	 */
	public String getIsDiscountAllowed() {
		return isDiscountAllowed;
	}

	/**
	 * Sets the checks if is discount allowed.
	 *
	 * @param isDiscountAllowed the new checks if is discount allowed
	 */
	public void setIsDiscountAllowed(String isDiscountAllowed) {
		this.isDiscountAllowed = isDiscountAllowed;
	}

	/**
	 * Gets the denominator.
	 *
	 * @return the denominator
	 */
	public Integer getDenominator() {
		return denominator;
	}

	/**
	 * Sets the denominator.
	 *
	 * @param denominator the new denominator
	 */
	public void setDenominator(Integer denominator) {
		this.denominator = denominator;
	}

	/**
	 * Gets the booking type to.
	 *
	 * @return the booking type to
	 */
	public BookingTypeTO getBookingTypeTO() {
		return bookingTypeTO;
	}

	/**
	 * Sets the booking type to.
	 *
	 * @param bookingTypeTO the new booking type to
	 */
	public void setBookingTypeTO(BookingTypeTO bookingTypeTO) {
		this.bookingTypeTO = bookingTypeTO;
	}

	/**
	 * Gets the max declared val allowed.
	 *
	 * @return the max declared val allowed
	 */
	public Double getMaxDeclaredValAllowed() {
		return maxDeclaredValAllowed;
	}

	/**
	 * Sets the max declared val allowed.
	 *
	 * @param maxDeclaredValAllowed the new max declared val allowed
	 */
	public void setMaxDeclaredValAllowed(Double maxDeclaredValAllowed) {
		this.maxDeclaredValAllowed = maxDeclaredValAllowed;
	}

	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Gets the checks if is declared values exceeded.
	 *
	 * @return the checks if is declared values exceeded
	 */
	public String getIsDeclaredValuesExceeded() {
		return isDeclaredValuesExceeded;
	}

	/**
	 * Sets the checks if is declared values exceeded.
	 *
	 * @param isDeclaredValuesExceeded the new checks if is declared values exceeded
	 */
	public void setIsDeclaredValuesExceeded(String isDeclaredValuesExceeded) {
		this.isDeclaredValuesExceeded = isDeclaredValuesExceeded;
	}

}
