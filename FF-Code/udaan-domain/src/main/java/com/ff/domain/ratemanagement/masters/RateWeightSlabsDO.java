package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateWeightSlabsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateWeightSlabsId;
	private WeightSlabDO weightSlabDO;
	private RateCustomerProductCatMapDO rateCustomerProductCatMapDO; 
	
	public Integer getRateWeightSlabsId() {
		return rateWeightSlabsId;
	}
	public void setRateWeightSlabsId(Integer rateWeightSlabsId) {
		this.rateWeightSlabsId = rateWeightSlabsId;
	}
	public WeightSlabDO getWeightSlabDO() {
		return weightSlabDO;
	}
	public void setWeightSlabDO(WeightSlabDO weightSlabDO) {
		this.weightSlabDO = weightSlabDO;
	}
	public RateCustomerProductCatMapDO getRateCustomerProductCatMapDO() {
		return rateCustomerProductCatMapDO;
	}
	public void setRateCustomerProductCatMapDO(
			RateCustomerProductCatMapDO rateCustomerProductCatMapDO) {
		this.rateCustomerProductCatMapDO = rateCustomerProductCatMapDO;
	}
}
