package com.ff.domain.ratemanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.StateDO;

public class RateTaxComponentDO extends CGMasterDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1760938537697485505L;
	private Integer taxComponentId;
	private Double taxPercentile;
	private String activeStatus;
	private Date effectiveFrom;
	private Date effectiveTo;
	private String rateComponentCode;
	private String taxGroup;
	private StateDO state;
	/**
	 * @return the taxComponentId
	 */
	public Integer getTaxComponentId() {
		return taxComponentId;
	}
	/**
	 * @param taxComponentId the taxComponentId to set
	 */
	public void setTaxComponentId(Integer taxComponentId) {
		this.taxComponentId = taxComponentId;
	}
	
	/**
	 * @return the taxPercentile
	 */
	public Double getTaxPercentile() {
		return taxPercentile;
	}
	/**
	 * @param taxPercentile the taxPercentile to set
	 */
	public void setTaxPercentile(Double taxPercentile) {
		this.taxPercentile = taxPercentile;
	}
	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}


	/**
	 * @return the effectiveFrom
	 */
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
	/**
	 * @param effectiveFrom the effectiveFrom to set
	 */
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	/**
	 * @return the effectiveTo
	 */
	public Date getEffectiveTo() {
		return effectiveTo;
	}
	/**
	 * @param effectiveTo the effectiveTo to set
	 */
	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
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
	 * @return the taxGroup
	 */
	public String getTaxGroup() {
		return taxGroup;
	}
	/**
	 * @param taxGroup the taxGroup to set
	 */
	public void setTaxGroup(String taxGroup) {
		this.taxGroup = taxGroup;
	}
	/**
	 * @return the state
	 */
	public StateDO getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(StateDO state) {
		this.state = state;
	}
}
