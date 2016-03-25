package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGFactDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ComailDO.
 */
public class ComailDO extends CGFactDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1014634590798373929L;

	/** The co mail id. */
	private Integer coMailId;
	
	/** The co mail no. */
	private String  coMailNo;
	
	/** The manifest id. *//*
	private Integer manifestId;
	
	*//** The manifest weight. *//*
	private Double manifestWeight;*/
	
	/** The origin office. */
	private Integer originOffice;
	
	/** The destination office. */
	private Integer destinationOffice;
	private String isStockConsumed="N";
	
	
	/**
	 * Gets the co mail id.
	 *
	 * @return the co mail id
	 */
	public Integer getCoMailId() {
		return coMailId;
	}
	
	/**
	 * Sets the co mail id.
	 *
	 * @param coMailId the new co mail id
	 */
	public void setCoMailId(Integer coMailId) {
		this.coMailId = coMailId;
	}
	
	/**
	 * Gets the co mail no.
	 *
	 * @return the co mail no
	 */
	public String getCoMailNo() {
		return coMailNo;
	}
	
	/**
	 * Sets the co mail no.
	 *
	 * @param coMailNo the new co mail no
	 */
	public void setCoMailNo(String coMailNo) {
		this.coMailNo = coMailNo;
	}
	
	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	/*public Integer getManifestId() {
		return manifestId;
	}
	
	*//**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 *//*
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}
	
	*//**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 *//*
	public Double getManifestWeight() {
		return manifestWeight;
	}
	
	*//**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 *//*
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}*/
	
	/**
	 * Gets the origin office.
	 *
	 * @return the origin office
	 */
	public Integer getOriginOffice() {
		return originOffice;
	}
	
	/**
	 * Sets the origin office.
	 *
	 * @param originOffice the new origin office
	 */
	public void setOriginOffice(Integer originOffice) {
		this.originOffice = originOffice;
	}
	
	/**
	 * Gets the destination office.
	 *
	 * @return the destination office
	 */
	public Integer getDestinationOffice() {
		return destinationOffice;
	}
	
	/**
	 * Sets the destination office.
	 *
	 * @param destinationOffice the new destination office
	 */
	public void setDestinationOffice(Integer destinationOffice) {
		this.destinationOffice = destinationOffice;
	}

	/**
	 * @return the isStockConsumed
	 */
	public String getIsStockConsumed() {
		return isStockConsumed;
	}

	/**
	 * @param isStockConsumed the isStockConsumed to set
	 */
	public void setIsStockConsumed(String isStockConsumed) {
		this.isStockConsumed = isStockConsumed;
	}
}
