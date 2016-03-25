package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateMinChargeableWeightTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateMinChargeableWeightId;
	private Double minChargeableWeight;
	private RateCustomerProductCatMapTO rateCustomerProductCatMapTO;
	
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
	public RateCustomerProductCatMapTO getRateCustomerProductCatMapTO() {
		return rateCustomerProductCatMapTO;
	}
	public void setRateCustomerProductCatMapTO(
			RateCustomerProductCatMapTO rateCustomerProductCatMapTO) {
		this.rateCustomerProductCatMapTO = rateCustomerProductCatMapTO;
	}
	
	
}
