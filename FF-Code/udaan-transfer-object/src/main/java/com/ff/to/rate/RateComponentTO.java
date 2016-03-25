package com.ff.to.rate;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.operations.ratequotation.RateTaxComponentTO;

public class RateComponentTO extends CGBaseTO {
	
	private static final long serialVersionUID = 3485431123891823143L;
	
	private Integer rateComponentId;
	private String rateComponentCode;
	private String rateComponentDesc;
	private String rateComponentConfig;
	private String rateAmountDeviationType;
	private String rateComponentType;
	private Double rateGlobalConfigValue;
	private String componentExpessedIn;
	private String canOverrideGlobalValue;
	private Double actualValue;
	private List<RateTaxComponentTO> rateTaxComponentList;
	//Non persistent Field
	private Double calculatedValue;

	
	
	
	
	public Double getActualValue() {
		return actualValue;
	}
	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
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
	public Double getRateGlobalConfigValue() {
		return rateGlobalConfigValue;
	}
	public void setRateGlobalConfigValue(Double rateGlobalConfigValue) {
		this.rateGlobalConfigValue = rateGlobalConfigValue;
	}
	public List<RateTaxComponentTO> getRateTaxComponentList() {
		return rateTaxComponentList;
	}
	public void setRateTaxComponentList(List<RateTaxComponentTO> rateTaxComponentList) {
		this.rateTaxComponentList = rateTaxComponentList;
	}
	
}
