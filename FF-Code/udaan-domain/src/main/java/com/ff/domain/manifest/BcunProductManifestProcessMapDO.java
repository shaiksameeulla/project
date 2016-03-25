package com.ff.domain.manifest;

import com.capgemini.lbs.framework.domain.CGMasterDO;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductManifestProcessMapDO.
 */
public class BcunProductManifestProcessMapDO extends CGMasterDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4693542401557779415L;

	
	/** The product manifest process map id. */
	private Integer productManifestProcessMapId;
	
	/** The scanned product. */
	private Integer scannedProduct;
	
	/** The manifest process. */
	private String manifestProcess;
	
	/** The consignment type. */
	private Integer consignmentType;
	
	/** The allowed product. */
	private Integer allowedProduct;
	
	/** The manifest open type. */
	private String manifestOpenType;
	
	/** The allow manifest. */
	private String allowManifest;
	
	/** The logged in office type. */
	private Integer loggedInOfficeType;
	
	
	/**
	 * Gets the product manifest process map id.
	 *
	 * @return the product manifest process map id
	 */
	public Integer getProductManifestProcessMapId() {
		return productManifestProcessMapId;
	}
	
	/**
	 * Sets the product manifest process map id.
	 *
	 * @param productManifestProcessMapId the new product manifest process map id
	 */
	public void setProductManifestProcessMapId(Integer productManifestProcessMapId) {
		this.productManifestProcessMapId = productManifestProcessMapId;
	}
	
	/**
	 * Gets the scanned product.
	 *
	 * @return the scanned product
	 */
	public Integer getScannedProduct() {
		return scannedProduct;
	}
	
	/**
	 * Sets the scanned product.
	 *
	 * @param scannedProduct the new scanned product
	 */
	public void setScannedProduct(Integer scannedProduct) {
		this.scannedProduct = scannedProduct;
	}
	
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
	 * Gets the consignment type.
	 *
	 * @return the consignment type
	 */
	public Integer getConsignmentType() {
		return consignmentType;
	}
	
	/**
	 * Sets the consignment type.
	 *
	 * @param consignmentType the new consignment type
	 */
	public void setConsignmentType(Integer consignmentType) {
		this.consignmentType = consignmentType;
	}
	
	/**
	 * Gets the allowed product.
	 *
	 * @return the allowed product
	 */
	public Integer getAllowedProduct() {
		return allowedProduct;
	}
	
	/**
	 * Sets the allowed product.
	 *
	 * @param allowedProduct the new allowed product
	 */
	public void setAllowedProduct(Integer allowedProduct) {
		this.allowedProduct = allowedProduct;
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
	 * Gets the allow manifest.
	 *
	 * @return the allow manifest
	 */
	public String getAllowManifest() {
		return allowManifest;
	}
	
	/**
	 * Sets the allow manifest.
	 *
	 * @param allowManifest the new allow manifest
	 */
	public void setAllowManifest(String allowManifest) {
		this.allowManifest = allowManifest;
	}
	
	/**
	 * Gets the logged in office type.
	 *
	 * @return the logged in office type
	 */
	public Integer getLoggedInOfficeType() {
		return loggedInOfficeType;
	}
	
	/**
	 * Sets the logged in office type.
	 *
	 * @param loggedInOfficeType the new logged in office type
	 */
	public void setLoggedInOfficeType(Integer loggedInOfficeType) {
		this.loggedInOfficeType = loggedInOfficeType;
	}
	
	
}
