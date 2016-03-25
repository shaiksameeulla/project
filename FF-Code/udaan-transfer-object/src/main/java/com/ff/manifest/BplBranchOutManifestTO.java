package com.ff.manifest;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

/**
 * The Class BplBranchOutManifestTO.
 */
public class BplBranchOutManifestTO extends OutManifestBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7464277655815740398L;
	// Specific to BPL Branch out manifest fields
	/** The load list. */
	private List<LabelValueBean> loadList;

	/** The load no. */
	private Integer loadNo;
	// Internal use for transferring data from UI to Action
	/** The rowcount. */
	private int rowcount;

	/** The no of consignments. */
	private Integer[] noOfConsignments = new Integer[rowcount];

	// List of Child details of BPL Branch out manifest
	/** The bpl branch out manifest details t os list. */
	private List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOsList;
	
	private Long consigTotal;
	private double consigTotalWt;

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
	 * @param loadNo
	 *            the new load no
	 */
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}

	/**
	 * Gets the rowcount.
	 * 
	 * @return the rowcount
	 */
	public int getRowcount() {
		return rowcount;
	}

	/**
	 * Sets the rowcount.
	 * 
	 * @param rowcount
	 *            the new rowcount
	 */
	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	/**
	 * Gets the no of consignments.
	 * 
	 * @return the no of consignments
	 */
	public Integer[] getNoOfConsignments() {
		return noOfConsignments;
	}

	/**
	 * Gets the load list.
	 * 
	 * @return the load list
	 */
	public List<LabelValueBean> getLoadList() {
		return loadList;
	}

	/**
	 * Sets the load list.
	 * 
	 * @param loadList
	 *            the new load list
	 */
	public void setLoadList(List<LabelValueBean> loadList) {
		this.loadList = loadList;
	}

	/**
	 * Sets the no of consignments.
	 * 
	 * @param noOfConsignments
	 *            the new no of consignments
	 */
	public void setNoOfConsignments(Integer[] noOfConsignments) {
		this.noOfConsignments = noOfConsignments;
	}

	/**
	 * Gets the bpl branch out manifest details t os list.
	 * 
	 * @return the bpl branch out manifest details t os list
	 */
	public List<BPLBranchOutManifestDetailsTO> getBplBranchOutManifestDetailsTOsList() {
		return bplBranchOutManifestDetailsTOsList;
	}

	/**
	 * Sets the bpl branch out manifest details t os list.
	 * 
	 * @param bplBranchOutManifestDetailsTOsList
	 *            the new bpl branch out manifest details t os list
	 */
	public void setBplBranchOutManifestDetailsTOsList(
			List<BPLBranchOutManifestDetailsTO> bplBranchOutManifestDetailsTOsList) {
		this.bplBranchOutManifestDetailsTOsList = bplBranchOutManifestDetailsTOsList;
	}

	public Long getConsigTotal() {
		return consigTotal;
	}

	public void setConsigTotal(Long consigTotal) {
		this.consigTotal = consigTotal;
	}

	public double getConsigTotalWt() {
		return consigTotalWt;
	}

	public void setConsigTotalWt(double consigTotalWt) {
		this.consigTotalWt = consigTotalWt;
	}

}
