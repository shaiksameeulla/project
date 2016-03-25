package com.ff.pickup;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;

/**
 * The Class PickupBookingTO.
 */
public class PickupBookingTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8484473264027501723L;
	// Inputs
	/** The consg numbers. */
	private List<String> consgNumbers = null;
	
	/** The inactive consg numbers. */
	private List<String> inactiveConsgNumbers = null;
	
	/** The pickup runsheet no. */
	private String pickupRunsheetNo;
	
	/** The customer id. */
	private Integer customerId;
	
	/** The process id. */
	private Integer processId;
	
	/** The office id. */
	private Integer officeId;
	
	/** The booking date. */
	private String bookingDate;
	
	/** The consignor address. */
	private ConsignorConsigneeTO consignorAddress;

	// Results
	/** The trans status. */
	private String transStatus;
	
	/** The consg number. */
	private String consgNumber;
	
	/** The booking id. */
	private Integer bookingId;
	private String shippedToCode;
	private Integer loggedInUserId;
	/**
	 * Gets the consg numbers.
	 *
	 * @return the consg numbers
	 */
	public List<String> getConsgNumbers() {
		return consgNumbers;
	}

	/**
	 * Sets the consg numbers.
	 *
	 * @param consgNumbers the new consg numbers
	 */
	public void setConsgNumbers(List<String> consgNumbers) {
		this.consgNumbers = consgNumbers;
	}

	/**
	 * Gets the pickup runsheet no.
	 *
	 * @return the pickup runsheet no
	 */
	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}

	/**
	 * Sets the pickup runsheet no.
	 *
	 * @param pickupRunsheetNo the new pickup runsheet no
	 */
	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * Gets the process id.
	 *
	 * @return the process id
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * Sets the process id.
	 *
	 * @param processId the new process id
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * Gets the office id.
	 *
	 * @return the office id
	 */
	public Integer getOfficeId() {
		return officeId;
	}

	/**
	 * Sets the office id.
	 *
	 * @param officeId the new office id
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	/**
	 * Gets the booking date.
	 *
	 * @return the booking date
	 */
	public String getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 *
	 * @param bookingDate the new booking date
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the trans status.
	 *
	 * @return the trans status
	 */
	public String getTransStatus() {
		return transStatus;
	}

	/**
	 * Sets the trans status.
	 *
	 * @param transStatus the new trans status
	 */
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
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
	 * Gets the consg number.
	 *
	 * @return the consg number
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * Sets the consg number.
	 *
	 * @param consgNumber the new consg number
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	/**
	 * Gets the inactive consg numbers.
	 *
	 * @return the inactive consg numbers
	 */
	public List<String> getInactiveConsgNumbers() {
		return inactiveConsgNumbers;
	}

	/**
	 * Sets the inactive consg numbers.
	 *
	 * @param inactiveConsgNumbers the new inactive consg numbers
	 */
	public void setInactiveConsgNumbers(List<String> inactiveConsgNumbers) {
		this.inactiveConsgNumbers = inactiveConsgNumbers;
	}

	/**
	 * @return the consignorAddress
	 */
	public ConsignorConsigneeTO getConsignorAddress() {
		return consignorAddress;
	}

	/**
	 * @param consignorAddress the consignorAddress to set
	 */
	public void setConsignorAddress(ConsignorConsigneeTO consignorAddress) {
		this.consignorAddress = consignorAddress;
	}

	public String getShippedToCode() {
		return shippedToCode;
	}

	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
}
