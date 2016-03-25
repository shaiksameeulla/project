package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;


public class BcunProductDO extends CGMasterDO{


	private static final long serialVersionUID = 4483101489903070802L;
	private Integer productId;
	private String productCode;
	private String productName;
	private String ProductDesc;
	private String consgSeries;
	private String status = "A";
	private Integer productGroup;
    private Integer consolidationWindow;
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productDesc
	 */
	public String getProductDesc() {
		return ProductDesc;
	}
	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		ProductDesc = productDesc;
	}
	/**
	 * @return the consgSeries
	 */
	public String getConsgSeries() {
		return consgSeries;
	}
	/**
	 * @param consgSeries the consgSeries to set
	 */
	public void setConsgSeries(String consgSeries) {
		this.consgSeries = consgSeries;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the productGroup
	 */
	public Integer getProductGroup() {
		return productGroup;
	}
	/**
	 * @param productGroup the productGroup to set
	 */
	public void setProductGroup(Integer productGroup) {
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
