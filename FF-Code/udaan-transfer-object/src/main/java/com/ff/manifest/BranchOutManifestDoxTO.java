package com.ff.manifest;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

// TODO: Auto-generated Javadoc
/**
 * The Class BranchOutManifestDoxTO.
 */
public class BranchOutManifestDoxTO extends OutManifestBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6951069721010278576L;
	// Specific to Branch out manifest document fields
	/** The load list. */
	private List<LabelValueBean> loadList;
	
	/** The load no. */
	private Integer loadNo;
	
	/** The load l no. */
	private Integer loadLNo;
	
	/** The comail manifest id. */
	private String comailManifestListId;

	

	// Internal use for transferring data from UI to Action
	/** The row count. */
	private int rowCount;
	
	/** The lc amounts. */
	private Double[] lcAmounts = new Double[rowCount];
	
	/** The bank names. */
	private String[] bankNames = new String[rowCount];
	
	/** The row to pay amount. */
	private Double[] rowToPay = new Double[rowCount];
	
	/** The row COD amount. */
	private Double[] rowCOD = new Double[rowCount];

	

	// List of Child details of Branch out manifest document
	/** The branch out manifest dox details to list. */
	private List<BranchOutManifestDoxDetailsTO> branchOutManifestDoxDetailsTOList;
	
	/** The print comail count. */
	private int printComailCount;
	
	/** The print consig count. */
	private int printConsigCount;
	
	// All list of comails.
		private List<ComailTO> comails = new ArrayList<ComailTO>();
	

	/**
	 * Gets the load l no.
	 *
	 * @return the load l no
	 */
	public Integer getLoadLNo() {
		return loadLNo;
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
	public List<LabelValueBean> getLoadList() {
		return loadList;
	}

	/**
	 * Sets the load list.
	 *
	 * @param loadList the new load list
	 */
	public void setLoadList(List<LabelValueBean> loadList) {
		this.loadList = loadList;
	}

	/* (non-Javadoc)
	 * @see com.ff.manifest.OutManifestBaseTO#getRowCount()
	 */
	public int getRowCount() {
		return rowCount;
	}

	/* (non-Javadoc)
	 * @see com.ff.manifest.OutManifestBaseTO#setRowCount(int)
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the lc amounts.
	 *
	 * @return the lc amounts
	 */
	public Double[] getLcAmounts() {
		return lcAmounts;
	}

	/**
	 * Sets the lc amounts.
	 *
	 * @param lcAmounts the new lc amounts
	 */
	public void setLcAmounts(Double[] lcAmounts) {
		this.lcAmounts = lcAmounts;
	}

	/**
	 * Gets the bank names.
	 *
	 * @return the bank names
	 */
	public String[] getBankNames() {
		return bankNames;
	}

	/**
	 * Sets the bank names.
	 *
	 * @param bankNames the new bank names
	 */
	public void setBankNames(String[] bankNames) {
		this.bankNames = bankNames;
	}

	/**
	 * Gets the branch out manifest dox details to list.
	 *
	 * @return the branch out manifest dox details to list
	 */
	public List<BranchOutManifestDoxDetailsTO> getBranchOutManifestDoxDetailsTOList() {
		return branchOutManifestDoxDetailsTOList;
	}

	/**
	 * Sets the branch out manifest dox details to list.
	 *
	 * @param branchOutManifestDoxDetailsTOList the new branch out manifest dox details to list
	 */
	public void setBranchOutManifestDoxDetailsTOList(
			List<BranchOutManifestDoxDetailsTO> branchOutManifestDoxDetailsTOList) {
		this.branchOutManifestDoxDetailsTOList = branchOutManifestDoxDetailsTOList;
	}

	/**
	 * Gets the prints the comail count.
	 *
	 * @return the prints the comail count
	 */
	public int getPrintComailCount() {
		return printComailCount;
	}

	/**
	 * Sets the prints the comail count.
	 *
	 * @param printComailCount the new prints the comail count
	 */
	public void setPrintComailCount(int printComailCount) {
		this.printComailCount = printComailCount;
	}

	/**
	 * Gets the prints the consig count.
	 *
	 * @return the prints the consig count
	 */
	public int getPrintConsigCount() {
		return printConsigCount;
	}

	/**
	 * Sets the prints the consig count.
	 *
	 * @param printConsigCount the new prints the consig count
	 */
	public void setPrintConsigCount(int printConsigCount) {
		this.printConsigCount = printConsigCount;
	}
	
	public Double[] getRowToPay() {
		return rowToPay;
	}

	public void setRowToPay(Double[] rowToPay) {
		this.rowToPay = rowToPay;
	}

	public Double[] getRowCOD() {
		return rowCOD;
	}

	public void setRowCOD(Double[] rowCOD) {
		this.rowCOD = rowCOD;
	}

	/**
	 * @return the comailManifestListId
	 */
	public String getComailManifestListId() {
		return comailManifestListId;
	}

	/**
	 * @param comailManifestListId the comailManifestListId to set
	 */
	public void setComailManifestListId(String comailManifestListId) {
		this.comailManifestListId = comailManifestListId;
	}
	public List<ComailTO> getComails() {
		return comails;
	}

	public void setComails(List<ComailTO> comails) {
		this.comails = comails;
	}
	
	
	
}
