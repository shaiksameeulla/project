package com.ff.booking;

import java.util.List;

import com.ff.business.CustomerTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;

/**
 * The Class ConsignmentModificationTO.
 */
public class ConsignmentModificationTO extends BookingTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4900961636864812288L;

	/** The vol weight. */
	private Double volWeight;

	/** The height. */
	private Double height;

	/** The length. */
	private Double length;

	/** The breath. */
	private Double breath;

	/** The content. */
	private String content;

	/** The if others. */
	private String ifOthers;

	/** The pieces. */
	private Integer pieces;

	/** The cn paper works. */
	private CNPaperWorksTO cnPaperWorks;

	/** The cn contents. */
	private CNContentTO cnContents;

	/** The vol weight dtls. */
	private String volWeightDtls;

	/** The child c ns dtls. */
	private String childCNsDtls;

	/** The other cn content. */
	private String otherCNContent;

	/** The paper work ref no. */
	private String paperWorkRefNo;

	/** The declared value. */
	private Double declaredValue;

	/** The trnaspment chg. */
	private Double trnaspmentChg;

	/** The approved by id. */
	private Integer approvedById;

	/** The approved by. */
	private String approvedBy;

	/** The insurence policy no. */
	private String insurencePolicyNo;

	/** The booking payment. */
	private BookingPaymentTO bookingPayment;

	/** The insured by. */
	private InsuredByTO insuredBy;

	/** The payment mode. */
	private String paymentMode;

	/** The is consg closed. */
	private String isConsgClosed = "N";

	/** The ref no. */
	private String refNo;

	private CustomerTO customer;
	private String runsheetNo;

	private String consgTypeCode;
	private PincodeProductServiceabilityTO pincodeProdServiceability;
	
	private Integer bookedCnId;

//	For printing purpose
	private List dateweight;
	private String bookingCity;
	private String destinationCity;
	
	
	private Integer updatedProcessFrom;
	
	
	
	
	public Integer getUpdatedProcessFrom() {
		return updatedProcessFrom;
	}

	public void setUpdatedProcessFrom(Integer updatedProcessFrom) {
		this.updatedProcessFrom = updatedProcessFrom;
	}

	public String getBookingCity() {
		return bookingCity;
	}

	public void setBookingCity(String bookingCity) {
		this.bookingCity = bookingCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public List getDateweight() {
		return dateweight;
	}

	public void setDateweight(List dateweight) {
		this.dateweight = dateweight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#getVolWeight()
	 */
	public Double getVolWeight() {
		return volWeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#setVolWeight(java.lang.Double)
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            the new height
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 * 
	 * @param length
	 *            the new length
	 */
	public void setLength(Double length) {
		this.length = length;
	}

	/**
	 * Gets the breath.
	 * 
	 * @return the breath
	 */
	public Double getBreath() {
		return breath;
	}

	/**
	 * Sets the breath.
	 * 
	 * @param breath
	 *            the new breath
	 */
	public void setBreath(Double breath) {
		this.breath = breath;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the if others.
	 * 
	 * @return the if others
	 */
	public String getIfOthers() {
		return ifOthers;
	}

	/**
	 * Sets the if others.
	 * 
	 * @param ifOthers
	 *            the new if others
	 */
	public void setIfOthers(String ifOthers) {
		this.ifOthers = ifOthers;
	}

	/**
	 * Gets the pieces.
	 * 
	 * @return the pieces
	 */
	public Integer getPieces() {
		return pieces;
	}

	/**
	 * Sets the pieces.
	 * 
	 * @param pieces
	 *            the new pieces
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	/**
	 * Gets the cn paper works.
	 * 
	 * @return the cn paper works
	 */
	public CNPaperWorksTO getCnPaperWorks() {
		return cnPaperWorks;
	}

	/**
	 * Sets the cn paper works.
	 * 
	 * @param cnPaperWorks
	 *            the new cn paper works
	 */
	public void setCnPaperWorks(CNPaperWorksTO cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}

	/**
	 * Gets the cn contents.
	 * 
	 * @return the cn contents
	 */
	public CNContentTO getCnContents() {
		return cnContents;
	}

	/**
	 * Sets the cn contents.
	 * 
	 * @param cnContents
	 *            the new cn contents
	 */
	public void setCnContents(CNContentTO cnContents) {
		this.cnContents = cnContents;
	}

	/**
	 * Gets the vol weight dtls.
	 * 
	 * @return the vol weight dtls
	 */
	public String getVolWeightDtls() {
		return volWeightDtls;
	}

	/**
	 * Sets the vol weight dtls.
	 * 
	 * @param volWeightDtls
	 *            the new vol weight dtls
	 */
	public void setVolWeightDtls(String volWeightDtls) {
		this.volWeightDtls = volWeightDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#getChildCNsDtls()
	 */
	public String getChildCNsDtls() {
		return childCNsDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#setChildCNsDtls(java.lang.String)
	 */
	public void setChildCNsDtls(String childCNsDtls) {
		this.childCNsDtls = childCNsDtls;
	}

	/**
	 * Gets the other cn content.
	 * 
	 * @return the other cn content
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}

	/**
	 * Sets the other cn content.
	 * 
	 * @param otherCNContent
	 *            the new other cn content
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * Gets the paper work ref no.
	 * 
	 * @return the paper work ref no
	 */
	public String getPaperWorkRefNo() {
		return paperWorkRefNo;
	}

	/**
	 * Sets the paper work ref no.
	 * 
	 * @param paperWorkRefNo
	 *            the new paper work ref no
	 */
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}

	/**
	 * Gets the declared value.
	 * 
	 * @return the declared value
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * Sets the declared value.
	 * 
	 * @param declaredValue
	 *            the new declared value
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * Gets the trnaspment chg.
	 * 
	 * @return the trnaspment chg
	 */
	public Double getTrnaspmentChg() {
		return trnaspmentChg;
	}

	/**
	 * Sets the trnaspment chg.
	 * 
	 * @param trnaspmentChg
	 *            the new trnaspment chg
	 */
	public void setTrnaspmentChg(Double trnaspmentChg) {
		this.trnaspmentChg = trnaspmentChg;
	}

	/**
	 * Gets the approved by id.
	 * 
	 * @return the approved by id
	 */
	public Integer getApprovedById() {
		return approvedById;
	}

	/**
	 * Sets the approved by id.
	 * 
	 * @param approvedById
	 *            the new approved by id
	 */
	public void setApprovedById(Integer approvedById) {
		this.approvedById = approvedById;
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
	 * @param approvedBy
	 *            the new approved by
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * Gets the insurence policy no.
	 * 
	 * @return the insurence policy no
	 */
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}

	/**
	 * Sets the insurence policy no.
	 * 
	 * @param insurencePolicyNo
	 *            the new insurence policy no
	 */
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
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
	 * @param bookingPayment
	 *            the new booking payment
	 */
	public void setBookingPayment(BookingPaymentTO bookingPayment) {
		this.bookingPayment = bookingPayment;
	}

	/**
	 * Gets the insured by.
	 * 
	 * @return the insured by
	 */
	public InsuredByTO getInsuredBy() {
		return insuredBy;
	}

	/**
	 * Sets the insured by.
	 * 
	 * @param insuredBy
	 *            the new insured by
	 */
	public void setInsuredBy(InsuredByTO insuredBy) {
		this.insuredBy = insuredBy;
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
	 * Sets the payment mode.
	 * 
	 * @param paymentMode
	 *            the new payment mode
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * Gets the checks if is consg closed.
	 * 
	 * @return the checks if is consg closed
	 */
	public String getIsConsgClosed() {
		return isConsgClosed;
	}

	/**
	 * Sets the checks if is consg closed.
	 * 
	 * @param isConsgClosed
	 *            the new checks if is consg closed
	 */
	public void setIsConsgClosed(String isConsgClosed) {
		this.isConsgClosed = isConsgClosed;
	}

	/**
	 * Gets the ref no.
	 * 
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 * 
	 * @param refNo
	 *            the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @return the customer
	 */
	public CustomerTO getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(CustomerTO customer) {
		this.customer = customer;
	}

	/**
	 * @return the runsheetNo
	 */
	public String getRunsheetNo() {
		return runsheetNo;
	}

	/**
	 * @param runsheetNo
	 *            the runsheetNo to set
	 */
	public void setRunsheetNo(String runsheetNo) {
		this.runsheetNo = runsheetNo;
	}

	/**
	 * @return the consgTypeCode
	 */
	public String getConsgTypeCode() {
		return consgTypeCode;
	}

	/**
	 * @param consgTypeCode
	 *            the consgTypeCode to set
	 */
	public void setConsgTypeCode(String consgTypeCode) {
		this.consgTypeCode = consgTypeCode;
	}

	/**
	 * @return the pincodeProdServiceability
	 */
	public PincodeProductServiceabilityTO getPincodeProdServiceability() {
		return pincodeProdServiceability;
	}

	/**
	 * @param pincodeProdServiceability
	 *            the pincodeProdServiceability to set
	 */
	public void setPincodeProdServiceability(
			PincodeProductServiceabilityTO pincodeProdServiceability) {
		this.pincodeProdServiceability = pincodeProdServiceability;
	}

	public Integer getBookedCnId() {
		return bookedCnId;
	}

	public void setBookedCnId(Integer bookedCnId) {
		this.bookedCnId = bookedCnId;
	}
}
