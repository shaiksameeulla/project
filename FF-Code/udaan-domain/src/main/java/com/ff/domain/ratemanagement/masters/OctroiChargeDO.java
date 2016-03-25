package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class OctroiChargeDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6113155878484171599L;
	
	private Integer octroiChargeId;
	private Double serviceCharge;
	private String octroiBourneBy;
	private RateCustomerCategoryDO rateCustomerCategory;
	private RateComponentDO rateComponent;
	/**
	 * @return the octroiChargeId
	 */
	public Integer getOctroiChargeId() {
		return octroiChargeId;
	}
	/**
	 * @param octroiChargeId the octroiChargeId to set
	 */
	public void setOctroiChargeId(Integer octroiChargeId) {
		this.octroiChargeId = octroiChargeId;
	}
	/**
	 * @return the serviceCharge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}
	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	/**
	 * @return the rateCustomerCategory
	 */
	public RateCustomerCategoryDO getRateCustomerCategory() {
		return rateCustomerCategory;
	}
	/**
	 * @param rateCustomerCategory the rateCustomerCategory to set
	 */
	public void setRateCustomerCategory(RateCustomerCategoryDO rateCustomerCategory) {
		this.rateCustomerCategory = rateCustomerCategory;
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
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	



}
