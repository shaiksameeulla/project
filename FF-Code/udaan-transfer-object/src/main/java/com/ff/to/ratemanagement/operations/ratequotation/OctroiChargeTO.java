package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.rate.RateComponentTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
/**
 * @author preegupt
 *
 */
public class OctroiChargeTO  extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	    
	private Integer octroiChargeId;
	private Double serviceCharge;
	private String octroiBourneBy;
	private RateCustomerCategoryTO rateCustomerCategory;
	private RateComponentTO rateComponent;
	/**
	 * @return the octroiChargeId
	 */
	public Integer getOctroiChargeId() {
		return octroiChargeId;
	}
	/**
	 * @param octroiChargeId the octroiChargeId to set
	 */
	public void setOctroiChargeId(Integer octroiChargeId) {
		this.octroiChargeId = octroiChargeId;
	}
	/**
	 * @return the serviceCharge
	 */
	public Double getServiceCharge() {
		return serviceCharge;
	}
	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	/**
	 * @return the rateCustomerCategory
	 */
	public RateCustomerCategoryTO getRateCustomerCategory() {
		return rateCustomerCategory;
	}
	/**
	 * @param rateCustomerCategory the rateCustomerCategory to set
	 */
	public void setRateCustomerCategory(RateCustomerCategoryTO rateCustomerCategory) {
		this.rateCustomerCategory = rateCustomerCategory;
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
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
	}
	
	
}
