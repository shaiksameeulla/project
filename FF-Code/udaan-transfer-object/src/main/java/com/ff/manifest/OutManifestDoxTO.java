package com.ff.manifest;

import java.util.ArrayList;
import java.util.List;

import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.consignment.ConsignmentTO;

/**
 * The Class OutManifestDoxTO.
 */
public class OutManifestDoxTO extends OutManifestBaseTO {

	// Specific to out manifest document fields
	private static final long serialVersionUID = 7762180382542476526L;

	/** The is co mail only. */
	private String isCoMailOnly = "N";// possible values : 'Y'/'N'

	/** The manifest open type. */
	private String manifestOpenType;// possible values : 'O'-OGM/'P'- Open
									// Manifest

	/** The is manifested. */
	private String isManifested;

	/** The row count. */
	private int rowCount;

	/** The manifested product series. */
	private String manifestedProductSeries;

	/** The is data mismatched. */
	private String[] isDataMismatched = new String[rowCount];

	/** The booking weights. */
	private Double[] bookingWeights = new Double[rowCount];
	/** The mobile nos. */
	private String[] mobileNos = new String[rowCount];

	/** The consignee ids. */
	private Integer[] consigneeIds = new Integer[rowCount];

	/** lcDetails=LC amount#Bank Name. */
	private String[] lcDetails = new String[rowCount];
	// List of Child details of out manifest document
	/** The out manifest dox detail t os. */
	private List<OutManifestDoxDetailsTO> outManifestDoxDetailTOs = new ArrayList<OutManifestDoxDetailsTO>();
	private String errorMsg;
	private String transactionMsg;

	// print
	private int totalConsg;
	private double consigTotalWt;
	private double totalLcAmount;
	private int totalNoPcs;;

	// List added to store Consignments, Comails, Bookings to be created/Updated

	// List of Credit Customer Bookings
	private List<CreditCustomerBookingDoxTO> creditCustomerBookingDoxTOList = new ArrayList<CreditCustomerBookingDoxTO>();

	// List of Consignment already existing - only to be updated
	private List<ConsignmentTO> consignmentstoBeUpdatedList = new ArrayList<ConsignmentTO>();

	// All list of comails.
	private List<ComailTO> comails = new ArrayList<ComailTO>();

	/** The ogm manifest type either pure or transshipment. */
	private String ogmManifestType;

	/**
	 * @return the ogmManifestType
	 */
	public String getOgmManifestType() {
		return ogmManifestType;
	}

	/**
	 * @param ogmManifestType
	 *            the ogmManifestType to set
	 */
	public void setOgmManifestType(String ogmManifestType) {
		this.ogmManifestType = ogmManifestType;
	}

	public List<CreditCustomerBookingDoxTO> getCreditCustomerBookingDoxTOList() {
		return creditCustomerBookingDoxTOList;
	}

	public void setCreditCustomerBookingDoxTOList(
			List<CreditCustomerBookingDoxTO> creditCustomerBookingDoxTOList) {
		this.creditCustomerBookingDoxTOList = creditCustomerBookingDoxTOList;
	}

	public List<ConsignmentTO> getConsignmentstoBeUpdatedList() {
		return consignmentstoBeUpdatedList;
	}

	public void setConsignmentstoBeUpdatedList(
			List<ConsignmentTO> consignmentstoBeUpdatedList) {
		this.consignmentstoBeUpdatedList = consignmentstoBeUpdatedList;
	}

	public List<ComailTO> getComails() {
		return comails;
	}

	public void setComails(List<ComailTO> comails) {
		this.comails = comails;
	}

	/**
	 * Gets the out manifest dox detail t os.
	 * 
	 * @return the out manifest dox detail t os
	 */
	public List<OutManifestDoxDetailsTO> getOutManifestDoxDetailTOs() {
		return outManifestDoxDetailTOs;
	}

	/**
	 * Sets the out manifest dox detail t os.
	 * 
	 * @param outManifestDoxDetailTOs
	 *            the new out manifest dox detail t os
	 */
	public void setOutManifestDoxDetailTOs(
			List<OutManifestDoxDetailsTO> outManifestDoxDetailTOs) {
		this.outManifestDoxDetailTOs = outManifestDoxDetailTOs;
	}

	/**
	 * Gets the checks if is data mismatched.
	 * 
	 * @return the isDataMismatched
	 */
	public String[] getIsDataMismatched() {
		return isDataMismatched;
	}

	/**
	 * Sets the checks if is data mismatched.
	 * 
	 * @param isDataMismatched
	 *            the isDataMismatched to set
	 */
	public void setIsDataMismatched(String[] isDataMismatched) {
		this.isDataMismatched = isDataMismatched;
	}

	/**
	 * Gets the booking weights.
	 * 
	 * @return the bookingWeights
	 */
	public Double[] getBookingWeights() {
		return bookingWeights;
	}

	/**
	 * Sets the booking weights.
	 * 
	 * @param bookingWeights
	 *            the bookingWeights to set
	 */
	public void setBookingWeights(Double[] bookingWeights) {
		this.bookingWeights = bookingWeights;
	}

	/**
	 * Gets the mobile nos.
	 * 
	 * @return the mobile nos
	 */
	public String[] getMobileNos() {
		return mobileNos;
	}

	/**
	 * Sets the mobile nos.
	 * 
	 * @param mobileNos
	 *            the new mobile nos
	 */
	public void setMobileNos(String[] mobileNos) {
		this.mobileNos = mobileNos;
	}

	/**
	 * Gets the consignee ids.
	 * 
	 * @return the consignee ids
	 */
	public Integer[] getConsigneeIds() {
		return consigneeIds;
	}

	/**
	 * Sets the consignee ids.
	 * 
	 * @param consigneeIds
	 *            the new consignee ids
	 */
	public void setConsigneeIds(Integer[] consigneeIds) {
		this.consigneeIds = consigneeIds;
	}

	/**
	 * Gets the checks if is co mail only.
	 * 
	 * @return the checks if is co mail only
	 */
	public String getIsCoMailOnly() {
		return isCoMailOnly;
	}

	/**
	 * Sets the checks if is co mail only.
	 * 
	 * @param isCoMailOnly
	 *            the new checks if is co mail only
	 */
	public void setIsCoMailOnly(String isCoMailOnly) {
		this.isCoMailOnly = isCoMailOnly;
	}

	/**
	 * Gets the manifest open type.
	 * 
	 * @return the manifest open type
	 */
	public String getManifestOpenType() {
		return manifestOpenType;
	}

	/**
	 * Sets the manifest open type.
	 * 
	 * @param manifestOpenType
	 *            the new manifest open type
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	/**
	 * Gets the checks if is manifested.
	 * 
	 * @return the checks if is manifested
	 */
	public String getIsManifested() {
		return isManifested;
	}

	/**
	 * Sets the checks if is manifested.
	 * 
	 * @param isManifested
	 *            the new checks if is manifested
	 */
	public void setIsManifested(String isManifested) {
		this.isManifested = isManifested;
	}

	/**
	 * Gets the lc details.
	 * 
	 * @return the lcDetails
	 */
	public String[] getLcDetails() {
		return lcDetails;
	}

	/**
	 * Sets the lc details.
	 * 
	 * @param lcDetails
	 *            the lcDetails to set
	 */
	public void setLcDetails(String[] lcDetails) {
		this.lcDetails = lcDetails;
	}

	/**
	 * @return the manifestedProductSeries
	 */
	public String getManifestedProductSeries() {
		return manifestedProductSeries;
	}

	/**
	 * @param manifestedProductSeries
	 *            the manifestedProductSeries to set
	 */
	public void setManifestedProductSeries(String manifestedProductSeries) {
		this.manifestedProductSeries = manifestedProductSeries;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTransactionMsg() {
		return transactionMsg;
	}

	public void setTransactionMsg(String transactionMsg) {
		this.transactionMsg = transactionMsg;
	}

	public int getTotalConsg() {
		return totalConsg;
	}

	public void setTotalConsg(int totalConsg) {
		this.totalConsg = totalConsg;
	}

	public double getConsigTotalWt() {
		return consigTotalWt;
	}

	public void setConsigTotalWt(double consigTotalWt) {
		this.consigTotalWt = consigTotalWt;
	}

	public double getTotalLcAmount() {
		return totalLcAmount;
	}

	public void setTotalLcAmount(double totalLcAmount) {
		this.totalLcAmount = totalLcAmount;
	}

	public int getTotalNoPcs() {
		return totalNoPcs;
	}

	public void setTotalNoPcs(int totalNoPcs) {
		this.totalNoPcs = totalNoPcs;
	}

}
