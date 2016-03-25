package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RateVobSlabsTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer rateVobSlabsId;
	private VobSlabTO vobSlabTO;
	private Integer position;
	private RateCustomerProductCatMapTO rateCustomerProductCatMapTO;
	
	public Integer getRateVobSlabsId() {
		return rateVobSlabsId;
	}
	public void setRateVobSlabsId(Integer rateVobSlabsId) {
		this.rateVobSlabsId = rateVobSlabsId;
	}
	public VobSlabTO getVobSlabTO() {
		return vobSlabTO;
	}
	public void setVobSlabTO(VobSlabTO vobSlabTO) {
		this.vobSlabTO = vobSlabTO;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public RateCustomerProductCatMapTO getRateCustomerProductCatMapTO() {
		return rateCustomerProductCatMapTO;
	}
	public void setRateCustomerProductCatMapTO(
			RateCustomerProductCatMapTO rateCustomerProductCatMapTO) {
		this.rateCustomerProductCatMapTO = rateCustomerProductCatMapTO;
	}
		
}
