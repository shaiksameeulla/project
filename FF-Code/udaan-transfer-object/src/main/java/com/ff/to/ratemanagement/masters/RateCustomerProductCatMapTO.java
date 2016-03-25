/**
 * 
 */
package com.ff.to.ratemanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author rmaladi
 *
 */
public class RateCustomerProductCatMapTO extends CGBaseTO{
	
	private static final long serialVersionUID = 1L;
	
	private Integer rateCustomerProductCatMapId;
	private RateCustomerCategoryTO rateCustomerCategoryTO;
	private RateProductCategoryTO rateProductCategoryTO;
	
	
	public Integer getRateCustomerProductCatMapId() {
		return rateCustomerProductCatMapId;
	}
	public void setRateCustomerProductCatMapId(Integer rateCustomerProductCatMapId) {
		this.rateCustomerProductCatMapId = rateCustomerProductCatMapId;
	}
	public RateCustomerCategoryTO getRateCustomerCategoryTO() {
		return rateCustomerCategoryTO;
	}
	public void setRateCustomerCategoryTO(
			RateCustomerCategoryTO rateCustomerCategoryTO) {
		this.rateCustomerCategoryTO = rateCustomerCategoryTO;
	}
	public RateProductCategoryTO getRateProductCategoryTO() {
		return rateProductCategoryTO;
	}
	public void setRateProductCategoryTO(RateProductCategoryTO rateProductCategoryTO) {
		this.rateProductCategoryTO = rateProductCategoryTO;
	}
}
