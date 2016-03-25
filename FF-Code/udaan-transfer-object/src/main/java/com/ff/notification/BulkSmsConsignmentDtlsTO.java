package com.ff.notification;

import com.ff.to.serviceofferings.ReasonTO;

public class BulkSmsConsignmentDtlsTO {
	private Integer consgId;
	private String consgNo;
	private String bookingDate;
	private ReasonTO reasonTO;
	private String mobileNo;
	
	public Integer getConsgId() {
		return consgId;
	}
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public ReasonTO getReasonTO() {
		return reasonTO;
	}
	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
}
