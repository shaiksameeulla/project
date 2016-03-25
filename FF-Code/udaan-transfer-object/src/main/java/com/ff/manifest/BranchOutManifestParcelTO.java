package com.ff.manifest;

import java.util.List;

/**
 * The Class BranchOutManifestParcelTO.
 */

public class BranchOutManifestParcelTO extends OutManifestBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4430068847803179615L;
	
	/* Specific to Branch out manifest Parcel fields */
	/** The load no. */
	private Integer loadNo;
	
	/** The load list. */
	private List<LoadLotTO> loadList;

	/* Internal use for transferring data from UI to Action */
	/** The rowCount. */
	private int rowCount;
	
	/** The no of pcs. */
	private Integer[] noOfPcs = new Integer[rowCount];
	
	/** The cn content ids. */
	private Integer[] cnContentIds = new Integer[rowCount];
															
	/** The declared values. */
	private Double[] declaredValues = new Double[rowCount];
	
	/** The paper work ids. */
	private Integer[] paperWorkIds = new Integer[rowCount];
	
	/** The to pay amts. */
	private Double[] toPayAmts = new Double[rowCount];
	
	/** The cod amts. */
	private Double[] codAmts = new Double[rowCount];
	
	/** The custom duty amts. */
	private Double[] customDutyAmts = new Double[rowCount];
	
	/** The service charges. */
	private Double[] serviceCharges = new Double[rowCount];
	
	/** The state taxes. */
	private Double[] stateTaxes = new Double[rowCount];

	/** The paper work. */
	private String[] paperWork = new String[rowCount];
	
	/** The cn content. */
	private String[] cnContent = new String[rowCount];
	
	/** The isCnUpdated. */
	private String[] isCnUpdated = new String[rowCount];/* hidden for noOfPcs. */
	
	/** The child cns. */
	private String[] childCns = new String[rowCount];/* hidden for noOfPcs. */
	
	/** The booking types. */
	private String[] bookingTypes = new String[rowCount];
	
	/* List of Child details of Branch out manifest Parcel */
	/** The branch out manifest parcel details list. */
	private List<BranchOutManifestParcelDetailsTO> branchOutManifestParcelDetailsList;

	/** The printTotalWeight. */
	private Double printTotalWeight;
	
	/** The printTotalNoOfPcs. */
	private Integer printTotalNoOfPcs;
	
	
	private Integer consignCount;
	
	private Integer comailCount;
	
	
	/**
	 * Gets the bookingTypes
	 * 
	 * @return the bookingTypes
	 */
	public String[] getBookingTypes() {
		return bookingTypes;
	}

	/**
	 * Sets the bookingTypes
	 * 
	 * @param bookingTypes the bookingTypes to set
	 */
	public void setBookingTypes(String[] bookingTypes) {
		this.bookingTypes = bookingTypes;
	}

	/**
	 * Gets isCnUpdated values.
	 * 
	 * @return the isCnUpdated
	 */
	public String[] getIsCnUpdated() {
		return isCnUpdated;
	}

	/**
	 * if noOfPcs. updated then isCnUpdated set to "Y" else "N" 
	 * 
	 * @param isCnUpdated the isCnUpdated to set
	 */
	public void setIsCnUpdated(String[] isCnUpdated) {
		this.isCnUpdated = isCnUpdated;
	}

	/**
	 * Gets the child cns.
	 *
	 * @return the child cns
	 */
	public String[] getChildCns() {
		return childCns;
	}

	/**
	 * Sets the child cns.
	 *
	 * @param childCns the new child cns
	 */
	public void setChildCns(String[] childCns) {
		this.childCns = childCns;
	}

	/**
	 * Gets the paper work.
	 *
	 * @return the paper work
	 */
	public String[] getPaperWork() {
		return paperWork;
	}

	/**
	 * Sets the paper work.
	 *
	 * @param paperWork the new paper work
	 */
	public void setPaperWork(String[] paperWork) {
		this.paperWork = paperWork;
	}

	/**
	 * Gets the cn content.
	 *
	 * @return the cn content
	 */
	public String[] getCnContent() {
		return cnContent;
	}

	/**
	 * Sets the cn content.
	 *
	 * @param cnContent the new cn content
	 */
	public void setCnContent(String[] cnContent) {
		this.cnContent = cnContent;
	}

	/**
	 * Gets the load no.
	 *
	 * @return the load no
	 */
	public Integer getLoadNo() {
		return loadNo;
	}

	/**
	 * Sets the load no.
	 *
	 * @param loadNo the new load no
	 */
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	/**
	 * Gets the load list.
	 *
	 * @return the load list
	 */
	public List<LoadLotTO> getLoadList() {
		return loadList;
	}

	/**
	 * Sets the load list.
	 *
	 * @param loadList the new load list
	 */
	public void setLoadList(List<LoadLotTO> loadList) {
		this.loadList = loadList;
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
	 * @param noOfPcs the new no of pcs
	 */
	public void setNoOfPcs(Integer[] noOfPcs) {
		this.noOfPcs = noOfPcs;
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
	 * @param declaredValues the new declared values
	 */
	public void setDeclaredValues(Double[] declaredValues) {
		this.declaredValues = declaredValues;
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
	 * @param toPayAmts the new to pay amts
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
	 * @param codAmts the new cod amts
	 */
	public void setCodAmts(Double[] codAmts) {
		this.codAmts = codAmts;
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
	 * @param cnContentIds the new cn content ids
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
	 * @param paperWorkIds the new paper work ids
	 */
	public void setPaperWorkIds(Integer[] paperWorkIds) {
		this.paperWorkIds = paperWorkIds;
	}

	/**
	 * Gets the custom duty amts.
	 *
	 * @return the custom duty amts
	 */
	public Double[] getCustomDutyAmts() {
		return customDutyAmts;
	}

	/**
	 * Sets the custom duty amts.
	 *
	 * @param customDutyAmts the new custom duty amts
	 */
	public void setCustomDutyAmts(Double[] customDutyAmts) {
		this.customDutyAmts = customDutyAmts;
	}

	/**
	 * Gets the service charges.
	 *
	 * @return the service charges
	 */
	public Double[] getServiceCharges() {
		return serviceCharges;
	}

	/**
	 * Sets the service charges.
	 *
	 * @param serviceCharges the new service charges
	 */
	public void setServiceCharges(Double[] serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	/**
	 * Gets the state taxes.
	 *
	 * @return the state taxes
	 */
	public Double[] getStateTaxes() {
		return stateTaxes;
	}

	/**
	 * Sets the state taxes.
	 *
	 * @param stateTaxes the new state taxes
	 */
	public void setStateTaxes(Double[] stateTaxes) {
		this.stateTaxes = stateTaxes;
	}

	/**
	 * Gets the branch out manifest parcel details list.
	 *
	 * @return the branch out manifest parcel details list
	 */
	public List<BranchOutManifestParcelDetailsTO> getBranchOutManifestParcelDetailsList() {
		return branchOutManifestParcelDetailsList;
	}

	/**
	 * Sets the branch out manifest parcel details list.
	 *
	 * @param branchOutManifestParcelDetailsList the new branch out manifest parcel details list
	 */
	public void setBranchOutManifestParcelDetailsList(
			List<BranchOutManifestParcelDetailsTO> branchOutManifestParcelDetailsList) {
		this.branchOutManifestParcelDetailsList = branchOutManifestParcelDetailsList;
	}

	/**
	 * @return printTotalWeight
	 */
	public Double getPrintTotalWeight() {
		return printTotalWeight;
	}

	/**
	 * @param printTotalWeight
	 */
	public void setPrintTotalWeight(Double printTotalWeight) {
		this.printTotalWeight = printTotalWeight;
	}

	/**
	 * @return printTotalNoOfPcs
	 */
	public Integer getPrintTotalNoOfPcs() {
		return printTotalNoOfPcs;
	}

	/**
	 * @param printTotalNoOfPcs
	 */
	public void setPrintTotalNoOfPcs(Integer printTotalNoOfPcs) {
		this.printTotalNoOfPcs = printTotalNoOfPcs;
	}


	/**
	 * @return the consignCount
	 */
	public Integer getConsignCount() {
		return consignCount;
	}

	/**
	 * @param consignCount the consignCount to set
	 */
	public void setConsignCount(Integer consignCount) {
		this.consignCount = consignCount;
	}

	/**
	 * @return the comailCount
	 */
	public Integer getComailCount() {
		return comailCount;
	}

	/**
	 * @param comailCount the comailCount to set
	 */
	public void setComailCount(Integer comailCount) {
		this.comailCount = comailCount;
	}
	

}
