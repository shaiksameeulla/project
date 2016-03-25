package com.ff.to.ratemanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class VobSlabTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer vobSlabId;
	private Double lowerLimit;
	private Double upperLimit;
	private String lowerLimitLabel;
	private String upperLimitLabel;
	private Date effectiveFrom;
	private Date effectiveTo;
	
	public Integer getVobSlabId() {
		return vobSlabId;
	}
	public void setVobSlabId(Integer vobSlabId) {
		this.vobSlabId = vobSlabId;
	}
	public Double getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(Double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public Double getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(Double upperLimit) {
		this.upperLimit = upperLimit;
	}
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public Date getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(Date effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	/**
	 * @return the lowerLimitLabel
	 */
	public String getLowerLimitLabel() {
		return lowerLimitLabel;
	}
	/**
	 * @param lowerLimitLabel the lowerLimitLabel to set
	 */
	public void setLowerLimitLabel(String lowerLimitLabel) {
		this.lowerLimitLabel = lowerLimitLabel;
	}
	/**
	 * @return the upperLimitLabel
	 */
	public String getUpperLimitLabel() {
		return upperLimitLabel;
	}
	/**
	 * @param upperLimitLabel the upperLimitLabel to set
	 */
	public void setUpperLimitLabel(String upperLimitLabel) {
		this.upperLimitLabel = upperLimitLabel;
	}
	
	
}
