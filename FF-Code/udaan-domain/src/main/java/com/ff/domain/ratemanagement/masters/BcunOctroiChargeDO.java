package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunOctroiChargeDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6113155878484171599L;
	
	private Integer octroiChargeId;
	private Double serviceCharge;
	private String octroiBourneBy;
	private Integer rateCustomerCategoryId;
	private Integer rateComponentId;
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
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	/**
	 * @return the rateCustomerCategoryId
	 */
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	/**
	 * @param rateCustomerCategoryId the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
	/**
	 * @return the rateComponentId
	 */
	public Integer getRateComponentId() {
		return rateComponentId;
	}
	/**
	 * @param rateComponentId the rateComponentId to set
	 */
	public void setRateComponentId(Integer rateComponentId) {
		this.rateComponentId = rateComponentId;
	}
}
