package com.ff.manifest;


/**
 * The Class OutManifestDoxDetailsTO.
 */
public class OutManifestDoxDetailsTO extends OutManifestDetailBaseTO {

	/** The Constant serialVersionUID. */

	private static final long serialVersionUID = 326424389859931595L;

	/** The mobile no. */
	private String mobileNo;
	
	/** The consignee id. */
	private Integer consigneeId;
	private String lcDtls;
	private Integer manifestLoggedInOffId;
	
	private Double lcAmount;
	private Integer priceId;
	private Double amount;
	
	//for printing purpose
	private Integer srNo;
	
	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the consignee id.
	 *
	 * @return the consignee id
	 */
	public Integer getConsigneeId() {
		return consigneeId;
	}

	/**
	 * Sets the consignee id.
	 *
	 * @param consigneeId the new consignee id
	 */
	public void setConsigneeId(Integer consigneeId) {
		this.consigneeId = consigneeId;
	}

	/**
	 * @return the lcDtls
	 */
	public String getLcDtls() {
		return lcDtls;
	}

	/**
	 * @param lcDtls the lcDtls to set
	 */
	public void setLcDtls(String lcDtls) {
		this.lcDtls = lcDtls;
	}

	public Integer getManifestLoggedInOffId() {
		return manifestLoggedInOffId;
	}

	public void setManifestLoggedInOffId(Integer manifestLoggedInOffId) {
		this.manifestLoggedInOffId = manifestLoggedInOffId;
	}

	public Double getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	public Integer getPriceId() {
		return priceId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	


}
