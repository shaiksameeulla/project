package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class ProductDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2900037355705516067L;

	private Integer productId;
	private String productCode;
	private String productName;
	private String ProductDesc;
	private String consgSeries;
	private String status = "A";
	private ProductGroupServiceabilityDO productGroup;
    private Integer consolidationWindow;
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return ProductDesc;
	}

	public void setProductDesc(String productDesc) {
		ProductDesc = productDesc;
	}

	public String getConsgSeries() {
		return consgSeries;
	}

	public void setConsgSeries(String consgSeries) {
		this.consgSeries = consgSeries;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the productGroup
	 */
	public ProductGroupServiceabilityDO getProductGroup() {
		return productGroup;
	}

	/**
	 * @param productGroup
	 *            the productGroup to set
	 */
	public void setProductGroup(ProductGroupServiceabilityDO productGroup) {
		this.productGroup = productGroup;
	}

	/**
	 * @return the consolidationWindow
	 */
	public Integer getConsolidationWindow() {
		return consolidationWindow;
	}

	/**
	 * @param consolidationWindow the consolidationWindow to set
	 */
	public void setConsolidationWindow(Integer consolidationWindow) {
		this.consolidationWindow = consolidationWindow;
	}

}
