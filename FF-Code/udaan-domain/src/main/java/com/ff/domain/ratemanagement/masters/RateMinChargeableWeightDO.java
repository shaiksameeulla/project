package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateMinChargeableWeightDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateMinChargeableWeightId;
	private Double minChargeableWeight;
	private RateCustomerProductCatMapDO rateCustomerProductCatMapDO; 
	
	
	public RateCustomerProductCatMapDO getRateCustomerProductCatMapDO() {
		return rateCustomerProductCatMapDO;
	}
	public void setRateCustomerProductCatMapDO(
			RateCustomerProductCatMapDO rateCustomerProductCatMapDO) {
		this.rateCustomerProductCatMapDO = rateCustomerProductCatMapDO;
	}
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
	

}
