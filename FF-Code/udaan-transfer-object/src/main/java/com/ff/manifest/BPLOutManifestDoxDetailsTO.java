package com.ff.manifest;

/**
 * The Class BPLOutManifestDoxDetailsTO.
 */
public class BPLOutManifestDoxDetailsTO extends OutManifestDetailBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -957423078595352172L;

	// specifically for Bpl Branch Manifest
	/** The no of consignment. */
	private Integer noOfConsignment;

	/** The bpl no. */
	private String bplNo;

	/** The bag lock no. */
	private String bagLockNo;

	/** The is active. */
	private String isActive;
	
	/** The manifest open type. */
	private String manifestOpenType;
	
	
	/** The bpl manifest type. */
	private String bplManifestType;
	
	/**
	 * Gets the bpl no.
	 * 
	 * @return the bpl no
	 */

	/*
	 * For printing purpose
	 */

	private Integer srNo;
	
	

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	public String getBplNo() {
		return bplNo;
	}

	/**
	 * Sets the bpl no.
	 * 
	 * @param bplNo
	 *            the new bpl no
	 */
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	/**
	 * Gets the bag lock no.
	 * 
	 * @return the bag lock no
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * Sets the bag lock no.
	 * 
	 * @param bagLockNo
	 *            the new bag lock no
	 */
	public void setBagLockNo(String bagLockNo) {
		this.bagLockNo = bagLockNo;
	}

	/**
	 * Gets the no of consignment.
	 * 
	 * @return the no of consignment
	 */
	public Integer getNoOfConsignment() {
		return noOfConsignment;
	}

	/**
	 * Sets the no of consignment.
	 * 
	 * @param noOfConsignment
	 *            the new no of consignment
	 */
	public void setNoOfConsignment(Integer noOfConsignment) {
		this.noOfConsignment = noOfConsignment;
	}

	/**
	 * Gets the checks if is active.
	 * 
	 * @return the checks if is active
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * Sets the checks if is active.
	 * 
	 * @param isActive
	 *            the new checks if is active
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the manifestOpenType
	 */
	public String getManifestOpenType() {
		return manifestOpenType;
	}

	/**
	 * @param manifestOpenType the manifestOpenType to set
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	/**
	 * @return the bplManifestType
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * @param bplManifestType the bplManifestType to set
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

}
