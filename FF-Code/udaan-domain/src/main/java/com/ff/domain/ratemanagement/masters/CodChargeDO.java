package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class CodChargeDO extends CGMasterDO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 762694995654063628L;
	private Integer codChargeId;
	private Double minimumDeclaredValue;
	private Double maximumDeclaredValue;
	private String codChargeType;
	private RateComponentDO rateComponent;
	private Double percentileValue;
	private Double fixedValue;
	private String configuredFor;
	private String minimumDeclaredValLabel;
	private String maximumDeclaredValLabel;
	private RateCustomerCategoryDO rateCustomerCategory;
	
	/**
	 * @return the percentileValue
	 */
	public Double getPercentileValue() {
		return percentileValue;
	}
	/**
	 * @param percentileValue the percentileValue to set
	 */
	public void setPercentileValue(Double percentileValue) {
		this.percentileValue = percentileValue;
	}
	/**
	 * @return the fixedValue
	 */
	public Double getFixedValue() {
		return fixedValue;
	}
	/**
	 * @param fixedValue the fixedValue to set
	 */
	public void setFixedValue(Double fixedValue) {
		this.fixedValue = fixedValue;
	}
	/**
	 * @return the codChargeId
	 */
	public Integer getCodChargeId() {
		return codChargeId;
	}
	/**
	 * @param codChargeId the codChargeId to set
	 */
	public void setCodChargeId(Integer codChargeId) {
		this.codChargeId = codChargeId;
	}

	/**
	 * @return the minimumDeclaredValue
	 */
	public Double getMinimumDeclaredValue() {
		return minimumDeclaredValue;
	}
	/**
	 * @param minimumDeclaredValue the minimumDeclaredValue to set
	 */
	public void setMinimumDeclaredValue(Double minimumDeclaredValue) {
		this.minimumDeclaredValue = minimumDeclaredValue;
	}
	/**
	 * @return the maximumDeclaredValue
	 */
	public Double getMaximumDeclaredValue() {
		return maximumDeclaredValue;
	}
	/**
	 * @param maximumDeclaredValue the maximumDeclaredValue to set
	 */
	public void setMaximumDeclaredValue(Double maximumDeclaredValue) {
		this.maximumDeclaredValue = maximumDeclaredValue;
	}
	/**
	 * @return the codChargeType
	 */
	public String getCodChargeType() {
		return codChargeType;
	}
	/**
	 * @param codChargeType the codChargeType to set
	 */
	public void setCodChargeType(String codChargeType) {
		this.codChargeType = codChargeType;
	}
	/**
	 * @return the rateComponent
	 */
	public RateComponentDO getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(RateComponentDO rateComponent) {
		this.rateComponent = rateComponent;
	}
	public String getConfiguredFor() {
		return configuredFor;
	}
	public void setConfiguredFor(String configuredFor) {
		this.configuredFor = configuredFor;
	}
	public String getMinimumDeclaredValLabel() {
		return minimumDeclaredValLabel;
	}
	public void setMinimumDeclaredValLabel(String minimumDeclaredValLabel) {
		this.minimumDeclaredValLabel = minimumDeclaredValLabel;
	}
	public String getMaximumDeclaredValLabel() {
		return maximumDeclaredValLabel;
	}
	public void setMaximumDeclaredValLabel(String maximumDeclaredValLabel) {
		this.maximumDeclaredValLabel = maximumDeclaredValLabel;
	}
	public RateCustomerCategoryDO getRateCustomerCategory() {
		return rateCustomerCategory;
	}
	public void setRateCustomerCategory(RateCustomerCategoryDO rateCustomerCategory) {
		this.rateCustomerCategory = rateCustomerCategory;
	}
	

}
