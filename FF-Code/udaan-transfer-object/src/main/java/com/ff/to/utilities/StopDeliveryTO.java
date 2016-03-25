package com.ff.to.utilities;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.booking.BookingTO;
import com.ff.to.serviceofferings.ReasonTO;

public class StopDeliveryTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1645887034662594717L;
	String consgNo;
	String bookingDate;
	String pincode;
	String weight;
	String customer;
    String codLcTopay;
	Integer stopDeliveryReason;
	String remarks;
	String errorMessage;
	String successMessage;
	String date;
	List<ReasonTO> reasonList;
	//BookingTO bookingTO;
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
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCodLcTopay() {
		return codLcTopay;
	}
	public void setCodLcTopay(String codLcTopay) {
		this.codLcTopay = codLcTopay;
	}
	public Integer getStopDeliveryReason() {
		return stopDeliveryReason;
	}
	public void setStopDeliveryReason(Integer stopDeliveryReason) {
		this.stopDeliveryReason = stopDeliveryReason;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<ReasonTO> getReasonList() {
		return reasonList;
	}
	public void setReasonList(List<ReasonTO> reasonList) {
		this.reasonList = reasonList;
	}
	
	
	
	
	

}
