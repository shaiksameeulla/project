package com.ff.manifest;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class ManifestProcessTO.
 */
public class ManifestProcessTO extends CGBaseTO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6765190573689528399L;
	/** The manifest id. */
	private Integer manifestProcessId;
	/** The manifest direction. */
	private String manifestDirection;

	/** The manifest open type. */
	private String manifestOpenType;

	/** The bpl manifest type. */
	private String bplManifestType;// PURE or TRANSIENT

	/** The third party type. */
	private String thirdPartyType;

	/** The ba id. */
	private Integer baId;

	/** The franchisee id. */
	private Integer franchiseeId;

	/** The vendor id. */
	private Integer vendorId;
	// added by ami for search
	/** The load no. */
	private Integer loadNo;

	/** The bag lock no. */
	private String bagLockNo;

	/** The manifest Process Code. */
	private String manifestProcessCode;

	// added by ami for search ends
	
	// Added By CBhure - while search populate TP Name
		/** The business name. */
		String businessName;

	/**
	 * Gets the bag lock no.
	 * 
	 * @return the bag lock no
	 */
	public String getBagLockNo() {
		return bagLockNo;
	}

	/**
	 * @return the manifestProcessId
	 */
	public Integer getManifestProcessId() {
		return manifestProcessId;
	}

	/**
	 * @param manifestProcessId
	 *            the manifestProcessId to set
	 */
	public void setManifestProcessId(Integer manifestProcessId) {
		this.manifestProcessId = manifestProcessId;
	}

	public String getManifestProcessCode() {
		return manifestProcessCode;
	}

	public void setManifestProcessCode(String manifestProcessCode) {
		this.manifestProcessCode = manifestProcessCode;
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
	 * Gets the manifest direction.
	 * 
	 * @return the manifest direction
	 */
	public String getManifestDirection() {
		return manifestDirection;
	}

	/**
	 * Sets the manifest direction.
	 * 
	 * @param manifestDirection
	 *            the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	/**
	 * Gets the manifest open type.
	 * 
	 * @return the manifest open type
	 */
	public String getManifestOpenType() {
		return manifestOpenType;
	}

	/**
	 * Sets the manifest open type.
	 * 
	 * @param manifestOpenType
	 *            the new manifest open type
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	/**
	 * Gets the bpl manifest type.
	 * 
	 * @return the bpl manifest type
	 */
	public String getBplManifestType() {
		return bplManifestType;
	}

	/**
	 * Sets the bpl manifest type.
	 * 
	 * @param bplManifestType
	 *            the new bpl manifest type
	 */
	public void setBplManifestType(String bplManifestType) {
		this.bplManifestType = bplManifestType;
	}

	/**
	 * Gets the third party type.
	 * 
	 * @return the third party type
	 */
	public String getThirdPartyType() {
		return thirdPartyType;
	}

	/**
	 * Sets the third party type.
	 * 
	 * @param thirdPartyType
	 *            the new third party type
	 */
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}

	/**
	 * Gets the ba id.
	 * 
	 * @return the ba id
	 */
	public Integer getBaId() {
		return baId;
	}

	/**
	 * Sets the ba id.
	 * 
	 * @param baId
	 *            the new ba id
	 */
	public void setBaId(Integer baId) {
		this.baId = baId;
	}

	/**
	 * Gets the franchisee id.
	 * 
	 * @return the franchisee id
	 */
	public Integer getFranchiseeId() {
		return franchiseeId;
	}

	/**
	 * Sets the franchisee id.
	 * 
	 * @param franchiseeId
	 *            the new franchisee id
	 */
	public void setFranchiseeId(Integer franchiseeId) {
		this.franchiseeId = franchiseeId;
	}

	/**
	 * Gets the vendor id.
	 * 
	 * @return the vendor id
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * Sets the vendor id.
	 * 
	 * @param vendorId
	 *            the new vendor id
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * Gets the business name.
	 * 
	 * @return the business name
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * Sets the business name.
	 * 
	 * @param businessName
	 *            the new business name
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
