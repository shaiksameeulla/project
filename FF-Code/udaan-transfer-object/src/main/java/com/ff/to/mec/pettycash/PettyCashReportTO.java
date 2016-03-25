package com.ff.to.mec.pettycash;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class PettyCashReportTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	/** The region id. */
	private Integer regionId;

	/** The logged in office id. */
	private Integer loggedInOfficeId;

	/** The payment mode i.e. CASH- CA, CHEQUE- CHQ */
	private String paymentMode;

	/** The current date. i.e. YYYY-MM-DD */
	private String currentDate;

	/** The todays date i.e. DD/MM/YYYY display purpose */
	private String todaysDate;

	/** The closingDate. */
	private String closingDate;

	/** The booking Types. */
	private String[] bookingTypes;

	/** The consg series. */
	private String[] consgSeries;

	/** The collection types. */
	private String[] collectionTypes;

	/** The petty cash URL to redirect */
	private String pettyCashURL;
	
	/** The list of consignment numbers considered by petty cash for calculating the closing balance. This list will be updated in the DB separately */
	private List<String> consgNosConsideredForPettyCash; 

	/**
	 * @return the pettyCashURL
	 */
	public String getPettyCashURL() {
		return pettyCashURL;
	}

	/**
	 * @param pettyCashURL
	 *            the pettyCashURL to set
	 */
	public void setPettyCashURL(String pettyCashURL) {
		this.pettyCashURL = pettyCashURL;
	}

	/**
	 * @return the collectionTypes
	 */
	public String[] getCollectionTypes() {
		return collectionTypes;
	}

	/**
	 * @param collectionTypes
	 *            the collectionTypes to set
	 */
	public void setCollectionTypes(String[] collectionTypes) {
		this.collectionTypes = collectionTypes;
	}

	/**
	 * @return the bookingTypes
	 */
	public String[] getBookingTypes() {
		return bookingTypes;
	}

	/**
	 * @param bookingTypes
	 *            the bookingTypes to set
	 */
	public void setBookingTypes(String[] bookingTypes) {
		this.bookingTypes = bookingTypes;
	}

	/**
	 * @return the consgSeries
	 */
	public String[] getConsgSeries() {
		return consgSeries;
	}

	/**
	 * @param consgSeries
	 *            the consgSeries to set
	 */
	public void setConsgSeries(String[] consgSeries) {
		this.consgSeries = consgSeries;
	}

	/**
	 * @return the closingDate
	 */
	public String getClosingDate() {
		return closingDate;
	}

	/**
	 * @param closingDate
	 *            the closingDate to set
	 */
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}

	/**
	 * @param loggedInOfficeId
	 *            the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the currentDate
	 */
	public String getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate
	 *            the currentDate to set
	 */
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the todaysDate
	 */
	public String getTodaysDate() {
		return todaysDate;
	}

	/**
	 * @param todaysDate
	 *            the todaysDate to set
	 */
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}

	/**
	 * @return List<String>
	 */
	public List<String> getConsgNosConsideredForPettyCash() {
		return consgNosConsideredForPettyCash;
	}

	/**
	 * @param consgNosConsideredForPettyCash
	 */
	public void setConsgNosConsideredForPettyCash(
			List<String> consgNosConsideredForPettyCash) {
		this.consgNosConsideredForPettyCash = consgNosConsideredForPettyCash;
	}
	
}
