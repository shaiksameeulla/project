package com.ff.manifest;


/**
 * The Class BranchOutManifestDoxDetailsTO.
 */
public class BranchOutManifestDoxDetailsTO extends OutManifestDetailBaseTO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5522893371053187993L;
	
	/** The lc amount. */
	private Double lcAmount;
	
	/** The bank name. */
	private String bankName;
	
	/** The To Pay amount. */
	private Double toPayAmount;
	
	/** The cod amount. */
	private Double codAmount;
	
	/** for print */
	private Integer srNo;
	
	
	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	/**
	 * Gets the lc amount.
	 *
	 * @return the lc amount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	
	/**
	 * Sets the lc amount.
	 *
	 * @param lcAmount the new lc amount
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}
	
	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}
	
	/**
	 * Sets the bank name.
	 *
	 * @param bankName the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Double getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public Double getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}	
	
}
