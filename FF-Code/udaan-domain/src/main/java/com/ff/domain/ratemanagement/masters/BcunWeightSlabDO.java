package com.ff.domain.ratemanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunWeightSlabDO extends CGMasterDO{
	
	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer weightSlabId;
	private Double startWeight;
	private Double endWeight;
	private String additional;
	private Date effectiveFrom;
	private Date effectiveTo;
	private String startWeightLabel;
	private String endWeightLabel;
	private Integer sequenceOrder;
	private String weightSlabCategory;
	
	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
	}
	public Double getStartWeight() {
		return startWeight;
	}
	public void setStartWeight(Double startWeight) {
		this.startWeight = startWeight;
	}
	public Double getEndWeight() {
		return endWeight;
	}
	public void setEndWeight(Double endWeight) {
		this.endWeight = endWeight;
	}
	public String getAdditional() {
		return additional;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
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
	public String getStartWeightLabel() {
		return startWeightLabel;
	}
	public void setStartWeightLabel(String startWeightLabel) {
		this.startWeightLabel = startWeightLabel;
	}
	public String getEndWeightLabel() {
		return endWeightLabel;
	}
	public void setEndWeightLabel(String endWeightLabel) {
		this.endWeightLabel = endWeightLabel;
	}
	/**
	 * @return the sequenceOrder
	 */
	public Integer getSequenceOrder() {
		return sequenceOrder;
	}
	/**
	 * @param sequenceOrder the sequenceOrder to set
	 */
	public void setSequenceOrder(Integer sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}
	public String getWeightSlabCategory() {
		return weightSlabCategory;
	}
	public void setWeightSlabCategory(String weightSlabCategory) {
		this.weightSlabCategory = weightSlabCategory;
	}
}
