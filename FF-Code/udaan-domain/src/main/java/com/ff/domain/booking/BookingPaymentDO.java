package com.ff.domain.booking;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BookingPaymentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -743654757275885622L;
	private Integer bookingPaymentId;
	private Integer paymentModeId;
	private Date chqDate;
	private String chqNo;
	private String bankName;
	private String bankBranchName;

	public Integer getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public Date getChqDate() {
		return chqDate;
	}

	public void setChqDate(Date chqDate) {
		this.chqDate = chqDate;
	}

	public String getChqNo() {
		return chqNo;
	}

	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public Integer getBookingPaymentId() {
		return bookingPaymentId;
	}

	public void setBookingPaymentId(Integer bookingPaymentId) {
		this.bookingPaymentId = bookingPaymentId;
	}

}
