package com.ff.booking;

import com.ff.business.CustomerTO;

/**
 * The Class CreditCustomerBookingDoxTO.
 */
public class CreditCustomerBookingDoxTO extends BookingGridTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3137604608537328617L;

	/** The customer id. */
	// private Integer customerId;

	/** The pickup runsheet no. */
	private String pickupRunsheetNo;

	/** The count. */
	private int count;

	/** The customer code. */
	private String[] customerCode = new String[count];

	/** The pickup runsheet nos. */
	private String[] pickupRunsheetNos = new String[count];

	/** The customer ids. */
	private Integer[] customerIds = new Integer[count];

	/** The ref no. */
	private String refNo;

	/** The customer. */
	private CustomerTO customer = null;

	/** The customer ids. */
	private String[] lcBankNames = new String[count];
	private Double[] codOrLCAmts = new Double[count];

	private String lcBankName;
	private Double codOrLCAmt;

	/**
	 * Gets the customer id.
	 * 
	 * @return the customer id
	 */
	/*
	 * public Integer getCustomerId() { return customerId; }
	 *//**
	 * Sets the customer id.
	 * 
	 * @param customerId
	 *            the new customer id
	 */
	/*
	 * public void setCustomerId(Integer customerId) { this.customerId =
	 * customerId; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#getPickupRunsheetNo()
	 */
	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.booking.BookingTO#setPickupRunsheetNo(java.lang.String)
	 */
	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}

	/**
	 * Gets the customer code.
	 * 
	 * @return the customer code
	 */
	public String[] getCustomerCode() {
		return customerCode;
	}

	/**
	 * Sets the customer code.
	 * 
	 * @param customerCode
	 *            the new customer code
	 */
	public void setCustomerCode(String[] customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Gets the pickup runsheet nos.
	 * 
	 * @return the pickup runsheet nos
	 */
	public String[] getPickupRunsheetNos() {
		return pickupRunsheetNos;
	}

	/**
	 * Sets the pickup runsheet nos.
	 * 
	 * @param pickupRunsheetNos
	 *            the new pickup runsheet nos
	 */
	public void setPickupRunsheetNos(String[] pickupRunsheetNos) {
		this.pickupRunsheetNos = pickupRunsheetNos;
	}

	/**
	 * Gets the customer ids.
	 * 
	 * @return the customer ids
	 */
	public Integer[] getCustomerIds() {
		return customerIds;
	}

	/**
	 * Sets the customer ids.
	 * 
	 * @param customerIds
	 *            the new customer ids
	 */
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
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
	 * Gets the customer.
	 * 
	 * @return the customer
	 */
	public CustomerTO getCustomer() {
		return customer;
	}

	/**
	 * Sets the customer.
	 * 
	 * @param customer
	 *            the new customer
	 */
	public void setCustomer(CustomerTO customer) {
		this.customer = customer;
	}

	/**
	 * @return the lcBankNames
	 */
	public String[] getLcBankNames() {
		return lcBankNames;
	}

	/**
	 * @param lcBankNames
	 *            the lcBankNames to set
	 */
	public void setLcBankNames(String[] lcBankNames) {
		this.lcBankNames = lcBankNames;
	}

	/**
	 * @return the codOrLCAmts
	 */
	public Double[] getCodOrLCAmts() {
		return codOrLCAmts;
	}

	/**
	 * @param codOrLCAmts the codOrLCAmts to set
	 */
	public void setCodOrLCAmts(Double[] codOrLCAmts) {
		this.codOrLCAmts = codOrLCAmts;
	}

	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}

	/**
	 * @return the codOrLCAmt
	 */
	public Double getCodOrLCAmt() {
		return codOrLCAmt;
	}

	/**
	 * @param codOrLCAmt the codOrLCAmt to set
	 */
	public void setCodOrLCAmt(Double codOrLCAmt) {
		this.codOrLCAmt = codOrLCAmt;
	}

	
}
