package com.ff.serviceOfferring;
/**
 * Author : Narasimha Rao kattunga
 */

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ProductTO extends CGBaseTO {

	
	private static final long serialVersionUID = 1139577016907046341L;
	private Integer productId; 
	private String productCode;
	private String productName;
	private String ProductDesc;
	private String consgSeries;
	private String status;
	private ProductGroupServiceabilityTO productGroupTO;
	   

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
	 * @return the productGroupTO
	 */
	public ProductGroupServiceabilityTO getProductGroupTO() {
		return productGroupTO;
	}

	/**
	 * @param productGroupTO the productGroupTO to set
	 */
	public void setProductGroupTO(ProductGroupServiceabilityTO productGroupTO) {
		this.productGroupTO = productGroupTO;
	}

	
	
}
