package com.ff.booking;

/**
 * The Class CashBookingDoxTO.
 *
 * @author Narasimha Rao kattunga
 */
public class CashBookingDoxTO extends BookingTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4078119337018163159L;

	/** The cheque no. */
	private String chequeNo;
	
	/** The cheque date. */
	private String chequeDate;
	
	/** The bank. */
	private String bank;
	
	/** The branch. */
	private String branch;
	
	/** The approver id. */
	private Integer approverId;
	
	/** The approved by. */
	private String approvedBy;
	
	/** The booking payment. */
	private BookingPaymentTO bookingPayment;
	
	/** The dlv time map id. */
	private Integer dlvTimeMapId;

	/**
	 * Gets the cheque no.
	 *
	 * @return the cheque no
	 */
	public String getChequeNo() {
		return chequeNo;
	}

	/**
	 * Sets the cheque no.
	 *
	 * @param chequeNo the new cheque no
	 */
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	/**
	 * Gets the cheque date.
	 *
	 * @return the cheque date
	 */
	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * Sets the cheque date.
	 *
	 * @param chequeDate the new cheque date
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * Gets the bank.
	 *
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * Sets the bank.
	 *
	 * @param bank the new bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the new branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * Gets the approver id.
	 *
	 * @return the approver id
	 */
	public Integer getApproverId() {
		return approverId;
	}

	/**
	 * Sets the approver id.
	 *
	 * @param approverId the new approver id
	 */
	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	/**
	 * Gets the approved by.
	 *
	 * @return the approved by
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * Sets the approved by.
	 *
	 * @param approvedBy the new approved by
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * Gets the booking payment.
	 *
	 * @return the booking payment
	 */
	public BookingPaymentTO getBookingPayment() {
		return bookingPayment;
	}

	/**
	 * Sets the booking payment.
	 *
	 * @param bookingPayment the new booking payment
	 */
	public void setBookingPayment(BookingPaymentTO bookingPayment) {
		this.bookingPayment = bookingPayment;
	}

	/**
	 * Gets the dlv time map id.
	 *
	 * @return the dlv time map id
	 */
	public Integer getDlvTimeMapId() {
		return dlvTimeMapId;
	}

	/**
	 * Sets the dlv time map id.
	 *
	 * @param dlvTimeMapId the new dlv time map id
	 */
	public void setDlvTimeMapId(Integer dlvTimeMapId) {
		this.dlvTimeMapId = dlvTimeMapId;
	}

	

}
