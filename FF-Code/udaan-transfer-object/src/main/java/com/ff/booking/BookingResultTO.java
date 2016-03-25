package com.ff.booking;


import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class BookingResultTO extends CGBaseTO {
	private List<Integer> successBookingsIds;
	private List<Integer> successCNsIds;
	
	private Integer successBookingId;
	private Integer successCNId;
	
	private String transMessage;
	private boolean transStatus;
	
	public List<Integer> getSuccessBookingsIds() {
		return successBookingsIds;
	}
	public void setSuccessBookingsIds(List<Integer> successBookingsIds) {
		this.successBookingsIds = successBookingsIds;
	}
	public List<Integer> getSuccessCNsIds() {
		return successCNsIds;
	}
	public void setSuccessCNsIds(List<Integer> successCNsIds) {
		this.successCNsIds = successCNsIds;
	}
	public Integer getSuccessBookingId() {
		return successBookingId;
	}
	public void setSuccessBookingId(Integer successBookingId) {
		this.successBookingId = successBookingId;
	}
	public Integer getSuccessCNId() {
		return successCNId;
	}
	public void setSuccessCNId(Integer successCNId) {
		this.successCNId = successCNId;
	}
	public String getTransMessage() {
		return transMessage;
	}
	public void setTransMessage(String transMessage) {
		this.transMessage = transMessage;
	}
	public boolean isTransStatus() {
		return transStatus;
	}
	public void setTransStatus(boolean transStatus) {
		this.transStatus = transStatus;
	}
}
