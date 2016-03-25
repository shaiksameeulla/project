package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateProductProductCategoryMapDO extends CGMasterDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -584247666981821147L;
	private Integer rateProdProdCategoryId;
	private Integer productId;
	private Integer rateProductCategoryId;
	
	public Integer getRateProdProdCategoryId() {
		return rateProdProdCategoryId;
	}
	public void setRateProdProdCategoryId(Integer rateProdProdCategoryId) {
		this.rateProdProdCategoryId = rateProdProdCategoryId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getRateProductCategoryId() {
		return rateProductCategoryId;
	}
	public void setRateProductCategoryId(Integer rateProductCategoryId) {
		this.rateProductCategoryId = rateProductCategoryId;
	}
	
}
