package com.ff.booking;

/**
 * The Class FOCBookingParcelTO.
 */
public class FOCBookingParcelTO extends BookingParcelTO  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3367960426874190313L;

	private Integer approverId;

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

}
