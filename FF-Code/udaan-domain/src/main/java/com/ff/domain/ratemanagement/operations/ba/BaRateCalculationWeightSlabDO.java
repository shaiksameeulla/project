package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class BaRateCalculationWeightSlabDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1792646402210838830L;
	private Integer baWeightSlabId;
	private WeightSlabDO startWeight;
	private WeightSlabDO endWeight;
	private Integer baRateHeaderId;
	private Integer slabOrder;
	/**
	 * @return the baWeightSlabId
	 */
	public Integer getBaWeightSlabId() {
		return baWeightSlabId;
	}
	/**
	 * @param baWeightSlabId the baWeightSlabId to set
	 */
	public void setBaWeightSlabId(Integer baWeightSlabId) {
		this.baWeightSlabId = baWeightSlabId;
	}
	/**
	 * @return the startWeight
	 */
	public WeightSlabDO getStartWeight() {
		return startWeight;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeight(WeightSlabDO startWeight) {
		this.startWeight = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public WeightSlabDO getEndWeight() {
		return endWeight;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeight(WeightSlabDO endWeight) {
		this.endWeight = endWeight;
	}
	/**
	 * @return the baRateHeaderId
	 */
	public Integer getBaRateHeaderId() {
		return baRateHeaderId;
	}
	/**
	 * @param baRateHeaderId the baRateHeaderId to set
	 */
	public void setBaRateHeaderId(Integer baRateHeaderId) {
		this.baRateHeaderId = baRateHeaderId;
	}
	/**
	 * @return the slabOrder
	 */
	public Integer getSlabOrder() {
		return slabOrder;
	}
	/**
	 * @param slabOrder the slabOrder to set
	 */
	public void setSlabOrder(Integer slabOrder) {
		this.slabOrder = slabOrder;
	}
	
	
}
