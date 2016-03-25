package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateMinChargeableWeightDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateMinChargeableWeightId;
	private Double minChargeableWeight;
	private Integer rateCustomerProductCatMapId; 
	
	
	public Integer getRateMinChargeableWeightId() {
		return rateMinChargeableWeightId;
	}
	public void setRateMinChargeableWeightId(Integer rateMinChargeableWeightId) {
		this.rateMinChargeableWeightId = rateMinChargeableWeightId;
	}
	public Double getMinChargeableWeight() {
		return minChargeableWeight;
	}
	public void setMinChargeableWeight(Double minChargeableWeight) {
		this.minChargeableWeight = minChargeableWeight;
	}
	/**
	 * @return the rateCustomerProductCatMapId
	 */
	public Integer getRateCustomerProductCatMapId() {
		return rateCustomerProductCatMapId;
	}
	/**
	 * @param rateCustomerProductCatMapId the rateCustomerProductCatMapId to set
	 */
	public void setRateCustomerProductCatMapId(Integer rateCustomerProductCatMapId) {
		this.rateCustomerProductCatMapId = rateCustomerProductCatMapId;
	}
	

}
