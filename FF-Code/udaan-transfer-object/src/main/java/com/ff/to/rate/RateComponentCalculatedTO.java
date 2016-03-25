/**
 * 
 */
package com.ff.to.rate;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class RateComponentCalculatedTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531690776500633646L;

	
	private Integer rateComponentId;
	private String rateComponentCode;
	private String rateComponentDesc;
	private Double calculatedValue;
	/**
	 * @return the rateComponentId
	 */
	public Integer getRateComponentId() {
		return rateComponentId;
	}
	/**
	 * @param rateComponentId the rateComponentId to set
	 */
	public void setRateComponentId(Integer rateComponentId) {
		this.rateComponentId = rateComponentId;
	}
	/**
	 * @return the rateComponentCode
	 */
	public String getRateComponentCode() {
		return rateComponentCode;
	}
	/**
	 * @param rateComponentCode the rateComponentCode to set
	 */
	public void setRateComponentCode(String rateComponentCode) {
		this.rateComponentCode = rateComponentCode;
	}
	/**
	 * @return the rateComponentDesc
	 */
	public String getRateComponentDesc() {
		return rateComponentDesc;
	}
	/**
	 * @param rateComponentDesc the rateComponentDesc to set
	 */
	public void setRateComponentDesc(String rateComponentDesc) {
		this.rateComponentDesc = rateComponentDesc;
	}
	/**
	 * @return the calculatedValue
	 */
	public Double getCalculatedValue() {
		return calculatedValue;
	}
	/**
	 * @param calculatedValue the calculatedValue to set
	 */
	public void setCalculatedValue(Double calculatedValue) {
		this.calculatedValue = calculatedValue;
	}
	
	
}
