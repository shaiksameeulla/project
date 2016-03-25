package com.ff.domain.ratemanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunVobSlabDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer vobSlabId;
	private Double lowerLimit;
	private Double upperLimit;
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
	
	
}
