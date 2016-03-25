package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateVobSlabsDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateVobSlabsId;
	private Integer vobSlabId;
	private Integer position;
	private Integer rateCustomerProductCatMapId; 
	
	public Integer getRateVobSlabsId() {
		return rateVobSlabsId;
	}
	public void setRateVobSlabsId(Integer rateVobSlabsId) {
		this.rateVobSlabsId = rateVobSlabsId;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * @return the vobSlabId
	 */
	public Integer getVobSlabId() {
		return vobSlabId;
	}
	/**
	 * @param vobSlabId the vobSlabId to set
	 */
	public void setVobSlabId(Integer vobSlabId) {
		this.vobSlabId = vobSlabId;
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
