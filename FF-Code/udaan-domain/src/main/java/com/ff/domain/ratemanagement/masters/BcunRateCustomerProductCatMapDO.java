/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author rmaladi
 *
 */
public class BcunRateCustomerProductCatMapDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateCustomerProductCatMapId;
	private Integer rateCustomerCategoryId;
	private Integer rateProductCategoryId;
	
	public Integer getRateCustomerProductCatMapId() {
		return rateCustomerProductCatMapId;
	}
	public void setRateCustomerProductCatMapId(Integer rateCustomerProductCatMapId) {
		this.rateCustomerProductCatMapId = rateCustomerProductCatMapId;
	}
	/**
	 * @return the rateCustomerCategoryId
	 */
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	/**
	 * @param rateCustomerCategoryId the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
	/**
	 * @return the rateProductCategoryId
	 */
	public Integer getRateProductCategoryId() {
		return rateProductCategoryId;
	}
	/**
	 * @param rateProductCategoryId the rateProductCategoryId to set
	 */
	public void setRateProductCategoryId(Integer rateProductCategoryId) {
		this.rateProductCategoryId = rateProductCategoryId;
	}
	
	
	
}
