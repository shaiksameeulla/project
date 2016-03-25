package com.ff.domain.ratemanagement.operations.ba;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

/**
 * @author hkansagr
 * 
 */

public class BaRateWeightSlabDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer baWeightSlabId;
	private WeightSlabDO startWeight;
	private WeightSlabDO endWeight;
	private BaRateConfigProductHeaderDO baRateConfigProductHeaderDO;
	private Integer slabOrder;// Position

	private Set<BaRateConfigSlabRateDO> baSlabRateDO;
	private Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO;

	/**
	 * @return the slabOrder
	 */
	public Integer getSlabOrder() {
		return slabOrder;
	}

	/**
	 * @param slabOrder
	 *            the slabOrder to set
	 */
	public void setSlabOrder(Integer slabOrder) {
		this.slabOrder = slabOrder;
	}

	/**
	 * @return the baSlabRateDO
	 */
	public Set<BaRateConfigSlabRateDO> getBaSlabRateDO() {
		return baSlabRateDO;
	}

	/**
	 * @param baSlabRateDO
	 *            the baSlabRateDO to set
	 */
	public void setBaSlabRateDO(Set<BaRateConfigSlabRateDO> baSlabRateDO) {
		this.baSlabRateDO = baSlabRateDO;
	}

	/**
	 * @return the baSpecialDestinationRateDO
	 */
	public Set<BARateConfigSpecialDestinationRateDO> getBaSpecialDestinationRateDO() {
		return baSpecialDestinationRateDO;
	}

	/**
	 * @param baSpecialDestinationRateDO
	 *            the baSpecialDestinationRateDO to set
	 */
	public void setBaSpecialDestinationRateDO(
			Set<BARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO) {
		this.baSpecialDestinationRateDO = baSpecialDestinationRateDO;
	}

	/**
	 * @return the baWeightSlabId
	 */
	public Integer getBaWeightSlabId() {
		return baWeightSlabId;
	}

	/**
	 * @param baWeightSlabId
	 *            the baWeightSlabId to set
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
	 * @param startWeight
	 *            the startWeight to set
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
	 * @param endWeight
	 *            the endWeight to set
	 */
	public void setEndWeight(WeightSlabDO endWeight) {
		this.endWeight = endWeight;
	}

	/**
	 * @return the baRateConfigProductHeaderDO
	 */
	public BaRateConfigProductHeaderDO getBaRateConfigProductHeaderDO() {
		return baRateConfigProductHeaderDO;
	}

	/**
	 * @param baRateConfigProductHeaderDO
	 *            the baRateConfigProductHeaderDO to set
	 */
	public void setBaRateConfigProductHeaderDO(
			BaRateConfigProductHeaderDO baRateConfigProductHeaderDO) {
		this.baRateConfigProductHeaderDO = baRateConfigProductHeaderDO;
	}

}
