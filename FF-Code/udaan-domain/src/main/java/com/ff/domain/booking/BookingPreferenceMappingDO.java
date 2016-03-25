package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class BookingPreferenceMappingDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6155798099266188229L;
	private Integer bookingPreId;

	@JsonBackReference
	private BookingDO bookingId;
	private BookingPreferenceDetailsDO referenceId;
	private String splInstructions;
	private String otherPref;

	public Integer getBookingPreId() {
		return bookingPreId;
	}

	public void setBookingPreId(Integer bookingPreId) {
		this.bookingPreId = bookingPreId;
	}

	public BookingDO getBookingId() {
		return bookingId;
	}

	public void setBookingId(BookingDO bookingId) {
		this.bookingId = bookingId;
	}

	public BookingPreferenceDetailsDO getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(BookingPreferenceDetailsDO referenceId) {
		this.referenceId = referenceId;
	}

	public String getSplInstructions() {
		return splInstructions;
	}

	public void setSplInstructions(String splInstructions) {
		this.splInstructions = splInstructions;
	}

	/**
	 * @return the otherPref
	 */
	public String getOtherPref() {
		return otherPref;
	}

	/**
	 * @param otherPref
	 *            the otherPref to set
	 */
	public void setOtherPref(String otherPref) {
		this.otherPref = otherPref;
	}

}
