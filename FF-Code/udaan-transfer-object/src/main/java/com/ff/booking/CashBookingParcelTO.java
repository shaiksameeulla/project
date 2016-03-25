package com.ff.booking;

/**
 * The Class CashBookingParcelTO.
 *
 * @author Narasimha Rao kattunga
 */
public class CashBookingParcelTO extends BookingParcelTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7625335105936183101L;

	/** The reference no. */
	private String referenceNo;
	
	/** The declared value. */
	private Double declaredValue;
	
	/** The insurance. */
	private String insurance;
	
	/** The amount. */
	private Double amount;
	
	/** The pieces. */
	//private Integer pieces;
	
	/** The other. */
	private String other;
	
	/** The paper number. */
	private Integer paperNumber;
	
	/** The booking payment. */
	private BookingPaymentTO bookingPayment;
	
	/** The approver id. */
	private Integer approverId;
	
	/** The approved by. */
	private String approvedBy;
	
	/** The dlv time map id. */
	private Integer dlvTimeMapId;

	/** The content name. */
	private String contentName;
	
	/** The paper work. */
	private String paperWork;

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#getReferenceNo()
	 */
	public String getReferenceNo() {
		return referenceNo;
	}

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#setReferenceNo(java.lang.String)
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#getDeclaredValue()
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#setDeclaredValue(java.lang.Double)
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * Gets the insurance.
	 *
	 * @return the insurance
	 */
	public String getInsurance() {
		return insurance;
	}

	/**
	 * Sets the insurance.
	 *
	 * @param insurance the new insurance
	 */
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the pieces.
	 *
	 * @return the pieces
	 *//*
	public Integer getPieces() {
		return pieces;
	}

	*//**
	 * Sets the pieces.
	 *
	 * @param pieces the pieces to set
	 *//*
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}*/

	/**
	 * Gets the paper number.
	 *
	 * @return the paperNumber
	 */
	public Integer getPaperNumber() {
		return paperNumber;
	}

	/**
	 * Sets the paper number.
	 *
	 * @param paperNumber the paperNumber to set
	 */
	public void setPaperNumber(Integer paperNumber) {
		this.paperNumber = paperNumber;
	}

	/**
	 * Gets the other.
	 *
	 * @return the other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * Sets the other.
	 *
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * Gets the content name.
	 *
	 * @return the contentName
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * Sets the content name.
	 *
	 * @param contentName the contentName to set
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * Gets the paper work.
	 *
	 * @return the paperWork
	 */
	public String getPaperWork() {
		return paperWork;
	}

	/**
	 * Sets the paper work.
	 *
	 * @param paperWork the paperWork to set
	 */
	public void setPaperWork(String paperWork) {
		this.paperWork = paperWork;
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

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#getDlvTimeMapId()
	 */
	public Integer getDlvTimeMapId() {
		return dlvTimeMapId;
	}

	/* (non-Javadoc)
	 * @see com.ff.booking.BookingParcelTO#setDlvTimeMapId(java.lang.Integer)
	 */
	public void setDlvTimeMapId(Integer dlvTimeMapId) {
		this.dlvTimeMapId = dlvTimeMapId;
	}

}
