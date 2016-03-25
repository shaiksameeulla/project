package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateWeightSlabsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateWeightSlabsId;
	private Integer weightSlabId;
	private Integer rateCustomerProductCatMapId; 
	
	public Integer getRateWeightSlabsId() {
		return rateWeightSlabsId;
	}
	public void setRateWeightSlabsId(Integer rateWeightSlabsId) {
		this.rateWeightSlabsId = rateWeightSlabsId;
	}
	/**
	 * @return the weightSlabId
	 */
	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	/**
	 * @param weightSlabId the weightSlabId to set
	 */
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
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
