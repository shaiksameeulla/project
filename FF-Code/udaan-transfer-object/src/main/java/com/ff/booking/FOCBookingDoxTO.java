package com.ff.booking;

/**
 * The Class FOCBookingDoxTO.
 */
public class FOCBookingDoxTO extends BookingTO  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4520956327229628582L;
	private Integer approverId;

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

}
