package com.ff.manifest;


/**
 * The Class ManifestFactoryTO.
 */
public class ManifestFactoryTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6301090844635025433L;
	
	/** The consg number. */
	private String consgNumber;
	
	/** The manifest number. */
	private String manifestNumber;
	
	/** The manifest type. */
	private String manifestType;
	
	/** The consg type. */
	private String consgType;
	
	/** The login office id. */
	private Integer loginOfficeId;
	
	/** The manifest direction. */
	private String manifestDirection;

	private boolean isPickupCN=Boolean.FALSE;
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
	 * @param manifestDirection the new manifest direction
	 */
	public void setManifestDirection(String manifestDirection) {
		this.manifestDirection = manifestDirection;
	}

	/**
	 * Gets the consg number.
	 *
	 * @return the consg number
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * Sets the consg number.
	 *
	 * @param consgNumber the new consg number
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	/**
	 * Gets the manifest number.
	 *
	 * @return the manifest number
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}

	/**
	 * Sets the manifest number.
	 *
	 * @param manifestNumber the new manifest number
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}

	/**
	 * Gets the consg type.
	 *
	 * @return the consg type
	 */
	public String getConsgType() {
		return consgType;
	}

	/**
	 * Sets the consg type.
	 *
	 * @param consgType the new consg type
	 */
	public void setConsgType(String consgType) {
		this.consgType = consgType;
	}

	/**
	 * Gets the manifest type.
	 *
	 * @return the manifest type
	 */
	public String getManifestType() {
		return manifestType;
	}

	/**
	 * Sets the manifest type.
	 *
	 * @param manifestType the new manifest type
	 */
	public void setManifestType(String manifestType) {
		this.manifestType = manifestType;
	}
	
	/**
	 * Gets the login office id.
	 *
	 * @return the login office id
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}
	
	/**
	 * Sets the login office id.
	 *
	 * @param loginOfficeId the new login office id
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	public boolean isPickupCN() {
		return isPickupCN;
	}

	public void setPickupCN(boolean isPickupCN) {
		this.isPickupCN = isPickupCN;
	}

	
}
