package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunBookingPreferenceMappingDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6155798099266188229L;
	private Integer bookingPreId;
	private Integer bookingId;
	private Integer referenceId;
	private String splInstructions;
	public Integer getBookingPreId() {
		return bookingPreId;
	}
	public void setBookingPreId(Integer bookingPreId) {
		this.bookingPreId = bookingPreId;
	}
	public String getSplInstructions() {
		return splInstructions;
	}
	public void setSplInstructions(String splInstructions) {
		this.splInstructions = splInstructions;
	}
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public Integer getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}
	
}
