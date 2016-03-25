package com.ff.to.codreceipt;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class CodReceiptFormTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5331347798959936921L;
	String consgNo;
	String bookingDate;
	String currDate;
    String regionId;
    String branchId;
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
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
	
    
}
