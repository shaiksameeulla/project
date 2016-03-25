package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.ProductDO;

public class RateComponentApplicableDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1826725771698657848L;
	
	private Integer rateComponentApplicableId;
	private RateComponentDO rateComponent;
	private ProductDO product;
	private ConsignmentTypeDO consignmentType;
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
	public RateComponentDO getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(RateComponentDO rateComponent) {
		this.rateComponent = rateComponent;
	}
	/**
	 * @return the product
	 */
	public ProductDO getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductDO product) {
		this.product = product;
	}
	/**
	 * @return the consignmentType
	 */
	public ConsignmentTypeDO getConsignmentType() {
		return consignmentType;
	}
	/**
	 * @param consignmentType the consignmentType to set
	 */
	public void setConsignmentType(ConsignmentTypeDO consignmentType) {
		this.consignmentType = consignmentType;
	}
	
	
	
	


}
