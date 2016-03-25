/**
 * 
 */
package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author rmaladi
 *
 */
public class RateCustomerProductCatMapDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateCustomerProductCatMapId;
	private RateCustomerCategoryDO rateCustomerCategoryDO;
	private RateProductCategoryDO rateProductCategoryDO;
	
	public Integer getRateCustomerProductCatMapId() {
		return rateCustomerProductCatMapId;
	}
	public void setRateCustomerProductCatMapId(Integer rateCustomerProductCatMapId) {
		this.rateCustomerProductCatMapId = rateCustomerProductCatMapId;
	}
	public RateCustomerCategoryDO getRateCustomerCategoryDO() {
		return rateCustomerCategoryDO;
	}
	public void setRateCustomerCategoryDO(
			RateCustomerCategoryDO rateCustomerCategoryDO) {
		this.rateCustomerCategoryDO = rateCustomerCategoryDO;
	}
	public RateProductCategoryDO getRateProductCategoryDO() {
		return rateProductCategoryDO;
	}
	public void setRateProductCategoryDO(
			RateProductCategoryDO rateProductCategoryDO) {
		this.rateProductCategoryDO = rateProductCategoryDO;
	}
	
	
}
