package com.ff.manifest;

import java.util.ArrayList;
import java.util.List;

import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.consignment.ConsignmentTO;

/**
 * The Class OutManifestParcelTO.
 */
public class OutManifestParcelTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1237743250559827679L;
	// Specific to out manifest parcel fields
	/** The dest office type. */
	private String destOfficeType;

	/** The bpl manifest type. */
	private String bplManifestType;
	// Internal use for transferring data from UI to Action
	/** The rowcount. */
	private int rowcount;

	/** The no of pcs. */
	private Integer[] noOfPcs = new Integer[rowcount];

	/** The booking types. */
	private String[] bookingTypes = new String[rowcount];
	/** The is data mismatched. */
	private String[] isDataMismatched = new String[rowCount];

	/** The booking weights. */
	private Double[] bookingWeights = new Double[rowCount];
	/* volumetric weight details */
	/** The vol weight. */
	private Double[] volWeight = new Double[rowcount];

	/** The lengths. */
	private Double[] lengths = new Double[rowcount];

	/** The breadths. */
	private Double[] breadths = new Double[rowcount];

	/** The heights. */
	private Double[] heights = new Double[rowcount];

	/** The act weights. */
	private Double[] actWeights = new Double[rowcount];

	/** The mobile nos. */
	private String[] mobileNos = new String[rowcount];

	/** The consignee ids. */
	private Integer[] consigneeIds = new Integer[rowcount];

	/** The declared values. */
	private Double[] declaredValues = new Double[rowcount];

	/** The cn content ids. */
	private Integer[] cnContentIds = new Integer[rowcount];

	/** The cn content codes. */
	private String[] cnContentCodes = new String[rowcount];

	/** The cn content names. */
	private String[] cnContentNames = new String[rowcount];

	/** The other cn contents. */
	private String[] otherCNContents = new String[rowcount];
	/** The paper work ids. */
	private Integer[] paperWorkIds = new Integer[rowcount];

	/** The paper ref nums. */
	private String[] paperRefNums = new String[rowcount];

	/** The cn paper work code. */
	private String[] cnPaperWorkCode = new String[rowcount];
	/** The cn paper work names. */
	private String[] cnPaperWorkNames = new String[rowcount];

	/** The insured by ids. */
	private Integer[] insuredByIds = new Integer[rowcount];

	/** The policy nos. */
	private String[] policyNos = new String[rowcount];

	/** The to pay amts. */
	private Double[] toPayAmts = new Double[rowcount];

	/** The cod amts. */
	private Double[] codAmts = new Double[rowcount];
	
	/** The LC Bank name. */
	private String[] lcBankName =  new String[rowcount];

	/** The cust ref nos. */
	private String[] custRefNos = new String[rowcount];

	/**
	 * The isCnUpdated. Added by Himal
	 */
	private String[] isCnUpdated = new String[rowcount];

	/**
	 * The child cns. Added by Himal
	 */
	private String[] childCns = new String[rowcount];

	/** The allowed prod series. */
	private String[] allowedProdSeries = new String[rowcount];

	// List of Child details of out manifest parcel
	/** The out manifest parcel details list. */
	private List<OutManifestParcelDetailsTO> outManifestParcelDetailsList;

	//for print
	private int totalConsg;
	private double totalWt;
	private int totalNoPcs;
	private String originOfficeName;
	
	//Ami Added on 2009 starts

	private String errorMsg;
	// List of Credit Customer Bookings
	private List<CreditCustomerBookingDoxTO> creditCustomerBookingDoxTOList = new ArrayList<CreditCustomerBookingDoxTO>();

	// List of Consignment already existing - only to be updated
	private List<ConsignmentTO> consignmentstoBeUpdatedList = new ArrayList<ConsignmentTO>();
	
	
	//Ami Added on 2009 starts
	
	/**
	 * Gets isCnUpdated values.
	 * 
	 * @return the isCnUpdated
	 */
	public String[] getIsCnUpdated() {
		return isCnUpdated;
	}

	/**
	 * @return the lcBankName
	 */
	public String[] getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String[] lcBankName) {
		this.lcBankName = lcBankName;
	}

	/**
	 * if noOfPcs. updated then isCnUpdated set to "Y" else "N"
	 * 
	 * @param isCnUpdated
	 *            the isCnUpdated to set
	 */
	public void setIsCnUpdated(String[] isCnUpdated) {
		this.isCnUpdated = isCnUpdated;
	}

	/**
	 * Gets the child cns.
	 * 
	 * @return the childCns
	 */
	public String[] getChildCns() {
		return childCns;
	}

	/**
	 * Sets the child cns.
	 * 
	 * @param childCns
	 *            the childCns to set
	 */
	public void setChildCns(String[] childCns) {
		this.childCns = childCns;
	}

	/**
	 * Gets the allowed prod series.
	 * 
	 * @return the allowed prod series
	 */
	public String[] getAllowedProdSeries() {
		return allowedProdSeries;
	}

	/**
	 * Sets the allowed prod series.
	 * 
	 * @param allowedProdSeries
	 *            the new allowed prod series
	 */
	public void setAllowedProdSeries(String[] allowedProdSeries) {
		this.allowedProdSeries = allowedProdSeries;
	}

	/**
	 * Gets the dest office type.
	 * 
	 * @return the dest office type
	 */
	public String getDestOfficeType() {
		return destOfficeType;
	}

	/**
	 * Sets the dest office type.
	 * 
	 * @param destOfficeType
	 *            the new dest office type
	 */
	public void setDestOfficeType(String destOfficeType) {
		this.destOfficeType = destOfficeType;
	}

	/**
	 * Gets the bpl manifest type.
	 *
	 * @return the bplManifestType
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * Sets the bpl manifest type.
	 *
	 * @param bplManifestType the bplManifestType to set
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	/**
	 * Gets the no of pcs.
	 * 
	 * @return the no of pcs
	 */
	public Integer[] getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * Sets the no of pcs.
	 * 
	 * @param noOfPcs
	 *            the new no of pcs
	 */
	public void setNoOfPcs(Integer[] noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	/**
	 * Gets the lengths.
	 * 
	 * @return the lengths
	 */
	public Double[] getLengths() {
		return lengths;
	}

	/**
	 * Sets the lengths.
	 * 
	 * @param lengths
	 *            the new lengths
	 */
	public void setLengths(Double[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * Gets the breadths.
	 * 
	 * @return the breadths
	 */
	public Double[] getBreadths() {
		return breadths;
	}

	/**
	 * Sets the breadths.
	 * 
	 * @param breadths
	 *            the new breadths
	 */
	public void setBreadths(Double[] breadths) {
		this.breadths = breadths;
	}

	/**
	 * Gets the heights.
	 * 
	 * @return the heights
	 */
	public Double[] getHeights() {
		return heights;
	}

	/**
	 * Sets the heights.
	 * 
	 * @param heights
	 *            the new heights
	 */
	public void setHeights(Double[] heights) {
		this.heights = heights;
	}

	/**
	 * Gets the booking types.
	 * 
	 * @return the booking types
	 */
	public String[] getBookingTypes() {
		return bookingTypes;
	}

	/**
	 * Sets the booking types.
	 * 
	 * @param bookingTypes
	 *            the new booking types
	 */
	public void setBookingTypes(String[] bookingTypes) {
		this.bookingTypes = bookingTypes;
	}

	/**
	 * Gets the vol weight.
	 * 
	 * @return the vol weight
	 */
	public Double[] getVolWeight() {
		return volWeight;
	}

	/**
	 * Sets the vol weight.
	 * 
	 * @param volWeight
	 *            the new vol weight
	 */
	public void setVolWeight(Double[] volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * Gets the act weights.
	 * 
	 * @return the act weights
	 */
	public Double[] getActWeights() {
		return actWeights;
	}

	/**
	 * Sets the act weights.
	 * 
	 * @param actWeights
	 *            the new act weights
	 */
	public void setActWeights(Double[] actWeights) {
		this.actWeights = actWeights;
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
	 * Gets the declared values.
	 * 
	 * @return the declared values
	 */
	public Double[] getDeclaredValues() {
		return declaredValues;
	}

	/**
	 * Sets the declared values.
	 * 
	 * @param declaredValues
	 *            the new declared values
	 */
	public void setDeclaredValues(Double[] declaredValues) {
		this.declaredValues = declaredValues;
	}

	/**
	 * Gets the insured by ids.
	 * 
	 * @return the insured by ids
	 */
	public Integer[] getInsuredByIds() {
		return insuredByIds;
	}

	/**
	 * Sets the insured by ids.
	 * 
	 * @param insuredByIds
	 *            the new insured by ids
	 */
	public void setInsuredByIds(Integer[] insuredByIds) {
		this.insuredByIds = insuredByIds;
	}

	/**
	 * Gets the policy nos.
	 * 
	 * @return the policy nos
	 */
	public String[] getPolicyNos() {
		return policyNos;
	}

	/**
	 * Sets the policy nos.
	 * 
	 * @param policyNos
	 *            the new policy nos
	 */
	public void setPolicyNos(String[] policyNos) {
		this.policyNos = policyNos;
	}

	/**
	 * Gets the to pay amts.
	 * 
	 * @return the to pay amts
	 */
	public Double[] getToPayAmts() {
		return toPayAmts;
	}

	/**
	 * Sets the to pay amts.
	 * 
	 * @param toPayAmts
	 *            the new to pay amts
	 */
	public void setToPayAmts(Double[] toPayAmts) {
		this.toPayAmts = toPayAmts;
	}

	/**
	 * Gets the cod amts.
	 * 
	 * @return the cod amts
	 */
	public Double[] getCodAmts() {
		return codAmts;
	}

	/**
	 * Sets the cod amts.
	 * 
	 * @param codAmts
	 *            the new cod amts
	 */
	public void setCodAmts(Double[] codAmts) {
		this.codAmts = codAmts;
	}

	/**
	 * Gets the cust ref nos.
	 * 
	 * @return the cust ref nos
	 */
	public String[] getCustRefNos() {
		return custRefNos;
	}

	/**
	 * Sets the cust ref nos.
	 * 
	 * @param custRefNos
	 *            the new cust ref nos
	 */
	public void setCustRefNos(String[] custRefNos) {
		this.custRefNos = custRefNos;
	}

	/**
	 * Gets the out manifest parcel details list.
	 * 
	 * @return the out manifest parcel details list
	 */
	public List<OutManifestParcelDetailsTO> getOutManifestParcelDetailsList() {
		return outManifestParcelDetailsList;
	}

	/**
	 * Sets the out manifest parcel details list.
	 * 
	 * @param outManifestParcelDetailsList
	 *            the new out manifest parcel details list
	 */
	public void setOutManifestParcelDetailsList(
			List<OutManifestParcelDetailsTO> outManifestParcelDetailsList) {
		this.outManifestParcelDetailsList = outManifestParcelDetailsList;
	}

	/**
	 * Gets the cn content ids.
	 * 
	 * @return the cn content ids
	 */
	public Integer[] getCnContentIds() {
		return cnContentIds;
	}

	/**
	 * Sets the cn content ids.
	 * 
	 * @param cnContentIds
	 *            the new cn content ids
	 */
	public void setCnContentIds(Integer[] cnContentIds) {
		this.cnContentIds = cnContentIds;
	}

	/**
	 * Gets the paper work ids.
	 * 
	 * @return the paper work ids
	 */
	public Integer[] getPaperWorkIds() {
		return paperWorkIds;
	}

	/**
	 * Sets the paper work ids.
	 * 
	 * @param paperWorkIds
	 *            the new paper work ids
	 */
	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	/**
	 * Gets the cn content codes.
	 * 
	 * @return the cnContentCodes
	 */
	public String[] getCnContentCodes() {
		return cnContentCodes;
	}

	/**
	 * Sets the cn content codes.
	 * 
	 * @param cnContentCodes
	 *            the cnContentCodes to set
	 */
	public void setCnContentCodes(String[] cnContentCodes) {
		this.cnContentCodes = cnContentCodes;
	}

	/**
	 * Gets the other cn contents.
	 * 
	 * @return the otherCNContents
	 */
	public String[] getOtherCNContents() {
		return otherCNContents;
	}

	/**
	 * Sets the other cn contents.
	 * 
	 * @param otherCNContents
	 *            the otherCNContents to set
	 */
	public void setOtherCNContents(String[] otherCNContents) {
		this.otherCNContents = otherCNContents;
	}

	/**
	 * Gets the cn content names.
	 * 
	 * @return the cnContentNames
	 */
	public String[] getCnContentNames() {
		return cnContentNames;
	}

	/**
	 * Sets the cn content names.
	 * 
	 * @param cnContentNames
	 *            the cnContentNames to set
	 */
	public void setCnContentNames(String[] cnContentNames) {
		this.cnContentNames = cnContentNames;
	}

	/**
	 * Gets the paper ref nums.
	 * 
	 * @return the paperRefNums
	 */
	public String[] getPaperRefNums() {
		return paperRefNums;
	}

	/**
	 * Sets the paper ref nums.
	 * 
	 * @param paperRefNums
	 *            the paperRefNums to set
	 */
	public void setPaperRefNums(String[] paperRefNums) {
		this.paperRefNums = paperRefNums;
	}

	/**
	 * Gets the cn paper work names.
	 * 
	 * @return the cnPaperWorkNames
	 */
	public String[] getCnPaperWorkNames() {
		return cnPaperWorkNames;
	}

	/**
	 * Sets the cn paper work names.
	 * 
	 * @param cnPaperWorkNames
	 *            the cnPaperWorkNames to set
	 */
	public void setCnPaperWorkNames(String[] cnPaperWorkNames) {
		this.cnPaperWorkNames = cnPaperWorkNames;
	}

	/**
	 * Gets the cn paper work code.
	 * 
	 * @return the cnPaperWorkCode
	 */
	public String[] getCnPaperWorkCode() {
		return cnPaperWorkCode;
	}

	/**
	 * Sets the cn paper work code.
	 * 
	 * @param cnPaperWorkCode
	 *            the cnPaperWorkCode to set
	 */
	public void setCnPaperWorkCode(String[] cnPaperWorkCode) {
		this.cnPaperWorkCode = cnPaperWorkCode;
	}

	/**
	 * @return the isDataMismatched
	 */
	public String[] getIsDataMismatched() {
		return isDataMismatched;
	}

	/**
	 * @param isDataMismatched the isDataMismatched to set
	 */
	public void setIsDataMismatched(String[] isDataMismatched) {
		this.isDataMismatched = isDataMismatched;
	}

	/**
	 * @return the bookingWeights
	 */
	public Double[] getBookingWeights() {
		return bookingWeights;
	}

	/**
	 * @param bookingWeights the bookingWeights to set
	 */
	public void setBookingWeights(Double[] bookingWeights) {
		this.bookingWeights = bookingWeights;
	}

	/**
	 * @return the totalConsg
	 */
	public int getTotalConsg() {
		return totalConsg;
	}

	/**
	 * @param totalConsg the totalConsg to set
	 */
	public void setTotalConsg(int totalConsg) {
		this.totalConsg = totalConsg;
	}

	/**
	 * @return the totalWt
	 */
	public double getTotalWt() {
		return totalWt;
	}

	/**
	 * @param totalWt the totalWt to set
	 */
	public void setTotalWt(double totalWt) {
		this.totalWt = totalWt;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public int getTotalNoPcs() {
		return totalNoPcs;
	}

	public void setTotalNoPcs(int totalNoPcs) {
		this.totalNoPcs = totalNoPcs;
	}

	/**
	 * @return the originOfficeName
	 */
	public String getOriginOfficeName() {
		return originOfficeName;
	}

	/**
	 * @param originOfficeName the originOfficeName to set
	 */
	public void setOriginOfficeName(String originOfficeName) {
		this.originOfficeName = originOfficeName;
	}
	
	
}
