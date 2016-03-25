package com.ff.manifest;


/**
 * The Class ThirdPartyOutManifestDoxDetailsTO.
 */
public class ThirdPartyOutManifestDoxDetailsTO extends OutManifestDetailBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -841294157000260585L;
	private String orgOfficeName;
    
	/** The to pay amts. */
	private Double toPayAmts ;
	
	/** The cod amts. */
	private Double codAmts ;
	
	/** The lcamts. */
	private Double lcAmts ;
	
	/** The lcamts. */
	private Double baAmounts ;
	
	private String consgOrgOffice;
	
	
	/**
	 * @return the orgOfficeName
	 */
	public String getOrgOfficeName() {
		return orgOfficeName;
	}

	/**
	 * @param orgOfficeName the orgOfficeName to set
	 */
	public void setOrgOfficeName(String orgOfficeName) {
		this.orgOfficeName = orgOfficeName;
	}

	/**
	 * @return the toPayAmts
	 */
	public Double getToPayAmts() {
		return toPayAmts;
	}

	/**
	 * @param toPayAmts the toPayAmts to set
	 */
	public void setToPayAmts(Double toPayAmts) {
		this.toPayAmts = toPayAmts;
	}

	/**
	 * @return the codAmts
	 */
	public Double getCodAmts() {
		return codAmts;
	}

	/**
	 * @param codAmts the codAmts to set
	 */
	public void setCodAmts(Double codAmts) {
		this.codAmts = codAmts;
	}

	/**
	 * @return the lcAmts
	 */
	public Double getLcAmts() {
		return lcAmts;
	}

	/**
	 * @param lcAmts the lcAmts to set
	 */
	public void setLcAmts(Double lcAmts) {
		this.lcAmts = lcAmts;
	}

	public String getConsgOrgOffice() {
		return consgOrgOffice;
	}

	public void setConsgOrgOffice(String consgOrgOffice) {
		this.consgOrgOffice = consgOrgOffice;
	}

	/**
	 * @return the baAmounts
	 */
	public Double getBaAmounts() {
		return baAmounts;
	}

	/**
	 * @param baAmounts the baAmounts to set
	 */
	public void setBaAmounts(Double baAmounts) {
		this.baAmounts = baAmounts;
	}
	
	
}
