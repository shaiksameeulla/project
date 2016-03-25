package com.ff.booking;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class BookingPaymentTO.
 */
public class BookingPaymentTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -489912461937413841L;
	
	/** The booking payment id. */
	private Integer bookingPaymentId;
	
	/** The payment id. */
	private Integer paymentId;
	
	/** The booking id. */
	private Integer bookingId;
	
	/** The payment mode id. */
	private Integer paymentModeId;
	
	/** The payment mode. */
	private String paymentMode;
	
	/** The chq date str. */
	private String chqDateStr;
	
	/** The chq no. */
	private String chqNo;
	
	/** The bank name. */
	private String bankName;
	
	/** The bank branch name. */
	private String bankBranchName;
	
	/** The privilege card no. */
	private String privilegeCardNo;
	
	/** The privilege card amt. */
	private Double privilegeCardAmt;
	
	/** The privilege card id. */
	private Integer privilegeCardId;
	
	private Integer privilegeCardTransId;
	
	private Integer createdBy;
	
	

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the privilegeCardTransId
	 */
	public Integer getPrivilegeCardTransId() {
		return privilegeCardTransId;
	}

	/**
	 * @param privilegeCardTransId the privilegeCardTransId to set
	 */
	public void setPrivilegeCardTransId(Integer privilegeCardTransId) {
		this.privilegeCardTransId = privilegeCardTransId;
	}

	/**
	 * Gets the payment id.
	 *
	 * @return the payment id
	 */
	public Integer getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the payment id.
	 *
	 * @param paymentId the new payment id
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the booking id.
	 *
	 * @return the booking id
	 */
	public Integer getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking id.
	 *
	 * @param bookingId the new booking id
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the chq date str.
	 *
	 * @return the chq date str
	 */
	public String getChqDateStr() {
		return chqDateStr;
	}

	/**
	 * Sets the chq date str.
	 *
	 * @param chqDateStr the new chq date str
	 */
	public void setChqDateStr(String chqDateStr) {
		this.chqDateStr = chqDateStr;
	}

	/**
	 * Gets the chq no.
	 *
	 * @return the chq no
	 */
	public String getChqNo() {
		return chqNo;
	}

	/**
	 * Sets the chq no.
	 *
	 * @param chqNo the new chq no
	 */
	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}

	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Gets the bank branch name.
	 *
	 * @return the bank branch name
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * Sets the bank branch name.
	 *
	 * @param bankBranchName the new bank branch name
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * Gets the booking payment id.
	 *
	 * @return the booking payment id
	 */
	public Integer getBookingPaymentId() {
		return bookingPaymentId;
	}

	/**
	 * Sets the booking payment id.
	 *
	 * @param bookingPaymentId the new booking payment id
	 */
	public void setBookingPaymentId(Integer bookingPaymentId) {
		this.bookingPaymentId = bookingPaymentId;
	}

	/**
	 * Gets the payment mode id.
	 *
	 * @return the payment mode id
	 */
	public Integer getPaymentModeId() {
		return paymentModeId;
	}

	/**
	 * Sets the payment mode id.
	 *
	 * @param paymentModeId the new payment mode id
	 */
	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	/**
	 * Sets the payment mode.
	 *
	 * @param paymentMode the new payment mode
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * Gets the payment mode.
	 *
	 * @return the payment mode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Gets the privilege card no.
	 *
	 * @return the privilege card no
	 */
	public String getPrivilegeCardNo() {
		return privilegeCardNo;
	}

	/**
	 * Sets the privilege card no.
	 *
	 * @param privilegeCardNo the new privilege card no
	 */
	public void setPrivilegeCardNo(String privilegeCardNo) {
		this.privilegeCardNo = privilegeCardNo;
	}

	/**
	 * Gets the privilege card amt.
	 *
	 * @return the privilege card amt
	 */
	public Double getPrivilegeCardAmt() {
		return privilegeCardAmt;
	}

	/**
	 * Sets the privilege card amt.
	 *
	 * @param privilegeCardAmt the new privilege card amt
	 */
	public void setPrivilegeCardAmt(Double privilegeCardAmt) {
		this.privilegeCardAmt = privilegeCardAmt;
	}

	/**
	 * Gets the privilege card id.
	 *
	 * @return the privilege card id
	 */
	public Integer getPrivilegeCardId() {
		return privilegeCardId;
	}

	/**
	 * Sets the privilege card id.
	 *
	 * @param privilegeCardId the new privilege card id
	 */
	public void setPrivilegeCardId(Integer privilegeCardId) {
		this.privilegeCardId = privilegeCardId;
	}

}
