package com.ff.loadmanagement;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class LoadManagementDetailsTO.
 *
 * @author narmdr
 */
public abstract class LoadManagementDetailsTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6113606069461685603L;
	
	/** The load connected id. */
	private Integer loadConnectedId;
	
	/** The load number. */
	private String loadNumber;
	
	/** The doc type. */
	private String docType;
	
	/** The weight. */
	private Double weight;
	
	/** The lock number. */
	private String lockNumber;
	
	/** The token number. */
	private String tokenNumber;
	
	/** The remarks. */
	private String remarks;
	
	/** The manifest id. */
	private Integer manifestId;
	
	/** The manifest weight. */
	private Double manifestWeight;
	
	/** The manifest dest city. */
	private String manifestDestCity;//cityCode
	
	/** The manifest dest city details. */
	private String manifestDestCityDetails;//cityId~code~name
	
	/** The weight tolerance. */
	private String weightTolerance; //Y,N

	private Integer manifestOriginOffId;	
	private Integer manifestDestOffId;
	private Integer consgTypeId;
	
	/**
	 * Gets the load connected id.
	 *
	 * @return the load connected id
	 */
	public Integer getLoadConnectedId() {
		return loadConnectedId;
	}
	
	/**
	 * Sets the load connected id.
	 *
	 * @param loadConnectedId the new load connected id
	 */
	public void setLoadConnectedId(Integer loadConnectedId) {
		this.loadConnectedId = loadConnectedId;
	}
	
	/**
	 * Gets the load number.
	 *
	 * @return the load number
	 */
	public String getLoadNumber() {
		return loadNumber;
	}
	
	/**
	 * Sets the load number.
	 *
	 * @param loadNumber the new load number
	 */
	public void setLoadNumber(String loadNumber) {
		this.loadNumber = loadNumber;
	}
	
	/**
	 * Gets the doc type.
	 *
	 * @return the doc type
	 */
	public String getDocType() {
		return docType;
	}
	
	/**
	 * Sets the doc type.
	 *
	 * @param docType the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}
	
	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the lock number.
	 *
	 * @return the lock number
	 */
	public String getLockNumber() {
		return lockNumber;
	}
	
	/**
	 * Sets the lock number.
	 *
	 * @param lockNumber the new lock number
	 */
	public void setLockNumber(String lockNumber) {
		this.lockNumber = lockNumber;
	}
	
	/**
	 * Gets the token number.
	 *
	 * @return the token number
	 */
	public String getTokenNumber() {
		return tokenNumber;
	}
	
	/**
	 * Sets the token number.
	 *
	 * @param tokenNumber the new token number
	 */
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	
	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * Gets the manifest id.
	 *
	 * @return the manifest id
	 */
	public Integer getManifestId() {
		return manifestId;
	}
	
	/**
	 * Sets the manifest id.
	 *
	 * @param manifestId the new manifest id
	 */
	public void setManifestId(Integer manifestId) {
		this.manifestId = manifestId;
	}
	
	/**
	 * Gets the manifest weight.
	 *
	 * @return the manifest weight
	 */
	public Double getManifestWeight() {
		return manifestWeight;
	}
	
	/**
	 * Sets the manifest weight.
	 *
	 * @param manifestWeight the new manifest weight
	 */
	public void setManifestWeight(Double manifestWeight) {
		this.manifestWeight = manifestWeight;
	}
	
	/**
	 * Gets the manifest dest city.
	 *
	 * @return the manifest dest city
	 */
	public String getManifestDestCity() {
		return manifestDestCity;
	}
	
	/**
	 * Sets the manifest dest city.
	 *
	 * @param manifestDestCity the new manifest dest city
	 */
	public void setManifestDestCity(String manifestDestCity) {
		this.manifestDestCity = manifestDestCity;
	}
	
	/**
	 * Gets the manifest dest city details.
	 *
	 * @return the manifest dest city details
	 */
	public String getManifestDestCityDetails() {
		return manifestDestCityDetails;
	}
	
	/**
	 * Sets the manifest dest city details.
	 *
	 * @param manifestDestCityDetails the new manifest dest city details
	 */
	public void setManifestDestCityDetails(String manifestDestCityDetails) {
		this.manifestDestCityDetails = manifestDestCityDetails;
	}
	
	/**
	 * Gets the weight tolerance.
	 *
	 * @return the weight tolerance
	 */
	public String getWeightTolerance() {
		return weightTolerance;
	}
	
	/**
	 * Sets the weight tolerance.
	 *
	 * @param weightTolerance the new weight tolerance
	 */
	public void setWeightTolerance(String weightTolerance) {
		this.weightTolerance = weightTolerance;
	}

	/**
	 * @return the manifestOriginOffId
	 */
	public Integer getManifestOriginOffId() {
		return manifestOriginOffId;
	}

	/**
	 * @param manifestOriginOffId the manifestOriginOffId to set
	 */
	public void setManifestOriginOffId(Integer manifestOriginOffId) {
		this.manifestOriginOffId = manifestOriginOffId;
	}

	/**
	 * @return the manifestDestOffId
	 */
	public Integer getManifestDestOffId() {
		return manifestDestOffId;
	}

	/**
	 * @param manifestDestOffId the manifestDestOffId to set
	 */
	public void setManifestDestOffId(Integer manifestDestOffId) {
		this.manifestDestOffId = manifestDestOffId;
	}

	/**
	 * @return the consgTypeId
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}
}
