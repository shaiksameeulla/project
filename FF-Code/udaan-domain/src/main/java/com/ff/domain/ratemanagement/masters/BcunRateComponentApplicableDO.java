package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateComponentApplicableDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1826725771698657848L;
	
	private Integer rateComponentApplicableId;
	private Integer rateComponent;
	private Integer product;
	private Integer consignmentType;
	/**
	 * @return the rateComponentApplicableId
	 */
	public Integer getRateComponentApplicableId() {
		return rateComponentApplicableId;
	}
	/**
	 * @param rateComponentApplicableId the rateComponentApplicableId to set
	 */
	public void setRateComponentApplicableId(Integer rateComponentApplicableId) {
		this.rateComponentApplicableId = rateComponentApplicableId;
	}
	/**
	 * @return the rateComponent
	 */
	public Integer getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(Integer rateComponent) {
		this.rateComponent = rateComponent;
	}
	/**
	 * @return the product
	 */
	public Integer getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Integer product) {
		this.product = product;
	}
	/**
	 * @return the consignmentType
	 */
	public Integer getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(Integer consignmentType) {
		this.consignmentType = consignmentType;
	}
	
	
}
