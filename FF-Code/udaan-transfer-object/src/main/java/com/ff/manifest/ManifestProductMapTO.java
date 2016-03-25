package com.ff.manifest;

/**
 * The Class ManifestProductMapTO.
 */
public class ManifestProductMapTO {
	
	/** The scanned product. */
	private String scannedProduct;
	
	/** The manifest process. */
	private String manifestProcess;
	
	/** The consignment type. */
	private String consignmentType;
	
	/** The manifest open type. */
	private String manifestOpenType;
	
	/** The logged in office type. */
	private String loggedInOfficeType;

	/**
	 * Gets the manifest process.
	 *
	 * @return the manifest process
	 */
	public String getManifestProcess() {
		return manifestProcess;
	}

	/**
	 * Sets the manifest process.
	 *
	 * @param manifestProcess the new manifest process
	 */
	public void setManifestProcess(String manifestProcess) {
		this.manifestProcess = manifestProcess;
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
	 * @param manifestOpenType the new manifest open type
	 */
	public void setManifestOpenType(String manifestOpenType) {
		this.manifestOpenType = manifestOpenType;
	}

	/**
	 * Gets the consignment type.
	 *
	 * @return the consignment type
	 */
	public String getConsignmentType() {
		return consignmentType;
	}

	/**
	 * Sets the consignment type.
	 *
	 * @param consignmentType the new consignment type
	 */
	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	/**
	 * Gets the scanned product.
	 *
	 * @return the scanned product
	 */
	public String getScannedProduct() {
		return scannedProduct;
	}

	/**
	 * Sets the scanned product.
	 *
	 * @param scannedProduct the new scanned product
	 */
	public void setScannedProduct(String scannedProduct) {
		this.scannedProduct = scannedProduct;
	}

	/**
	 * Gets the logged in office type.
	 *
	 * @return the logged in office type
	 */
	public String getLoggedInOfficeType() {
		return loggedInOfficeType;
	}

	/**
	 * Sets the logged in office type.
	 *
	 * @param loggedInOfficeType the new logged in office type
	 */
	public void setLoggedInOfficeType(String loggedInOfficeType) {
		this.loggedInOfficeType = loggedInOfficeType;
	}
}
