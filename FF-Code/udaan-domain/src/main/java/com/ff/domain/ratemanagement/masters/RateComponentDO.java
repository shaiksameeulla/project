package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateComponentDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5330487715755065480L;
	

	private Integer rateComponentId;
	private String rateComponentCode;
	private String rateComponentDesc;
	private String rateComponentConfig;
	private String rateAmountDeviationType;
	private String rateComponentType;
	private Double rateGlobalConfigValue;
	private String componentExpessedIn;
	private String canOverrideGlobalValue;
	private Integer calculationSequence;
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
	 * @return the rateComponentConfig
	 */
	public String getRateComponentConfig() {
		return rateComponentConfig;
	}
	/**
	 * @param rateComponentConfig the rateComponentConfig to set
	 */
	public void setRateComponentConfig(String rateComponentConfig) {
		this.rateComponentConfig = rateComponentConfig;
	}
	/**
	 * @return the rateAmountDeviationType
	 */
	public String getRateAmountDeviationType() {
		return rateAmountDeviationType;
	}
	/**
	 * @param rateAmountDeviationType the rateAmountDeviationType to set
	 */
	public void setRateAmountDeviationType(String rateAmountDeviationType) {
		this.rateAmountDeviationType = rateAmountDeviationType;
	}
	/**
	 * @return the rateComponentType
	 */
	public String getRateComponentType() {
		return rateComponentType;
	}
	/**
	 * @param rateComponentType the rateComponentType to set
	 */
	public void setRateComponentType(String rateComponentType) {
		this.rateComponentType = rateComponentType;
	}
	
	/**
	 * @return the rateGlobalConfigValue
	 */
	public Double getRateGlobalConfigValue() {
		return rateGlobalConfigValue;
	}
	/**
	 * @param rateGlobalConfigValue the rateGlobalConfigValue to set
	 */
	public void setRateGlobalConfigValue(Double rateGlobalConfigValue) {
		this.rateGlobalConfigValue = rateGlobalConfigValue;
	}
	/**
	 * @return the componentExpessedIn
	 */
	public String getComponentExpessedIn() {
		return componentExpessedIn;
	}
	/**
	 * @param componentExpessedIn the componentExpessedIn to set
	 */
	public void setComponentExpessedIn(String componentExpessedIn) {
		this.componentExpessedIn = componentExpessedIn;
	}
	/**
	 * @return the canOverrideGlobalValue
	 */
	public String getCanOverrideGlobalValue() {
		return canOverrideGlobalValue;
	}
	/**
	 * @param canOverrideGlobalValue the canOverrideGlobalValue to set
	 */
	public void setCanOverrideGlobalValue(String canOverrideGlobalValue) {
		this.canOverrideGlobalValue = canOverrideGlobalValue;
	}
	public Integer getCalculationSequence() {
		return calculationSequence;
	}
	public void setCalculationSequence(Integer calculationSequence) {
		this.calculationSequence = calculationSequence;
	}
	
	public boolean equals(Object other){
	    boolean result;
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } // end if
	    else{
	    	RateComponentDO rateComponent = (RateComponentDO)other;
	        result = rateComponent.getRateComponentCode().equalsIgnoreCase(this.getRateComponentCode());
	    } // end else

	    return result;
	} // end equals
}
