package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateProductCategoryDO;

public class BaRateProductDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2950338089788807117L;
	private Integer baProductHeaderId;
	private BaRateHeaderDO baRateHeaderDO;
	private RateProductCategoryDO rateProductCategory;
	private String servicedOn;
	
	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}
	/**
	 * @param baProductHeaderId the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}
	/**
	 * @return the baRateHeaderDO
	 */
	public BaRateHeaderDO getBaRateHeaderDO() {
		return baRateHeaderDO;
	}
	/**
	 * @param baRateHeaderDO the baRateHeaderDO to set
	 */
	public void setBaRateHeaderDO(BaRateHeaderDO baRateHeaderDO) {
		this.baRateHeaderDO = baRateHeaderDO;
	}
	
	/**
	 * @return the rateProductCategory
	 */
	public RateProductCategoryDO getRateProductCategory() {
		return rateProductCategory;
	}
	/**
	 * @param rateProductCategory the rateProductCategory to set
	 */
	public void setRateProductCategory(RateProductCategoryDO rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}
	/**
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}
	/**
	 * @param servicedOn the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	
	
}
