package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateWeightSlabsTO extends CGBaseTO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateWeightSlabsId;
	private WeightSlabTO weightSlabTO;
	private RateCustomerProductCatMapTO rateCustomerProductCatMapTO;
	
	public Integer getRateWeightSlabsId() {
		return rateWeightSlabsId;
	}
	public void setRateWeightSlabsId(Integer rateWeightSlabsId) {
		this.rateWeightSlabsId = rateWeightSlabsId;
	}
	public WeightSlabTO getWeightSlabTO() {
		return weightSlabTO;
	}
	public void setWeightSlabTO(WeightSlabTO weightSlabTO) {
		this.weightSlabTO = weightSlabTO;
	}
	public RateCustomerProductCatMapTO getRateCustomerProductCatMapTO() {
		return rateCustomerProductCatMapTO;
	}
	public void setRateCustomerProductCatMapTO(
			RateCustomerProductCatMapTO rateCustomerProductCatMapTO) {
		this.rateCustomerProductCatMapTO = rateCustomerProductCatMapTO;
	}
	
}
