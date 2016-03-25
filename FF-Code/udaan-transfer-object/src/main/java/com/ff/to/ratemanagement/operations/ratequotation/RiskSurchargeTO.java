package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;

/**
 * @author preegupt
 *
 */
public class RiskSurchargeTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2117190269821714334L;
	private Integer riskSurchargeId;
	private RateComponentTO rateComponent;
	private RateCustomerCategoryTO customerCategory;
	/**
	 * @return the riskSurchargeId
	 */
	public Integer getRiskSurchargeId() {
		return riskSurchargeId;
	}
	/**
	 * @param riskSurchargeId the riskSurchargeId to set
	 */
	public void setRiskSurchargeId(Integer riskSurchargeId) {
		this.riskSurchargeId = riskSurchargeId;
	}
	/**
	 * @return the rateComponent
	 */
	public RateComponentTO getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(RateComponentTO rateComponent) {
		this.rateComponent = rateComponent;
	}
	/**
	 * @return the customerCategory
	 */
	public RateCustomerCategoryTO getCustomerCategory() {
		return customerCategory;
	}
	/**
	 * @param customerCategory the customerCategory to set
	 */
	public void setCustomerCategory(RateCustomerCategoryTO customerCategory) {
		this.customerCategory = customerCategory;
	}
	
}
