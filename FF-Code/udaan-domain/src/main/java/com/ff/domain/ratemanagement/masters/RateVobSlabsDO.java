package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateVobSlabsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateVobSlabsId;
	private VobSlabDO vobSlabDO;
	private Integer position;
	private RateCustomerProductCatMapDO rateCustomerProductCatMapDO; 
	
	public RateCustomerProductCatMapDO getRateCustomerProductCatMapDO() {
		return rateCustomerProductCatMapDO;
	}
	public void setRateCustomerProductCatMapDO(
			RateCustomerProductCatMapDO rateCustomerProductCatMapDO) {
		this.rateCustomerProductCatMapDO = rateCustomerProductCatMapDO;
	}
	public Integer getRateVobSlabsId() {
		return rateVobSlabsId;
	}
	public void setRateVobSlabsId(Integer rateVobSlabsId) {
		this.rateVobSlabsId = rateVobSlabsId;
	}
	public VobSlabDO getVobSlabDO() {
		return vobSlabDO;
	}
	public void setVobSlabDO(VobSlabDO vobSlabDO) {
		this.vobSlabDO = vobSlabDO;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
}
