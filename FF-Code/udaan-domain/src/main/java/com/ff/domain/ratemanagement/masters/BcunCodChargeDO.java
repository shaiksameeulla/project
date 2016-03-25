package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunCodChargeDO extends CGMasterDO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 762694995654063628L;
	private Integer codChargeId;
	private Double minimumDeclaredValue;
	private Double maximumDeclaredValue;
	private String codChargeType;
	private Integer rateComponentId;
	private Double percentileValue;
	private Double fixedValue;
	private String configuredFor;
	private String minimumDeclaredValLabel;
	private String maximumDeclaredValLabel;
	
	/**
	 * @return the configuredFor
	 */
	public String getConfiguredFor() {
		return configuredFor;
	}
	/**
	 * @param configuredFor the configuredFor to set
	 */
	public void setConfiguredFor(String configuredFor) {
		this.configuredFor = configuredFor;
	}
	/**
	 * @return the minimumDeclaredValLabel
	 */
	public String getMinimumDeclaredValLabel() {
		return minimumDeclaredValLabel;
	}
	/**
	 * @param minimumDeclaredValLabel the minimumDeclaredValLabel to set
	 */
	public void setMinimumDeclaredValLabel(String minimumDeclaredValLabel) {
		this.minimumDeclaredValLabel = minimumDeclaredValLabel;
	}
	/**
	 * @return the maximumDeclaredValLabel
	 */
	public String getMaximumDeclaredValLabel() {
		return maximumDeclaredValLabel;
	}
	/**
	 * @param maximumDeclaredValLabel the maximumDeclaredValLabel to set
	 */
	public void setMaximumDeclaredValLabel(String maximumDeclaredValLabel) {
		this.maximumDeclaredValLabel = maximumDeclaredValLabel;
	}
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
}
