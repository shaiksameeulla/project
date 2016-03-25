/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ba;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author abarudwa
 *
 */
public class BcunBaRateWeightSlabDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer baWeightSlabId;
	private Integer startWeight;
	private Integer endWeight;
	private Integer baRateConfigProductHeaderId;
	private Integer position;
	private Integer slabOrder;

	private Set<BcunBaRateConfigSlabRateDO> baSlabRateDO;
	private Set<BcunBARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO;
	public Integer getBaWeightSlabId() {
		return baWeightSlabId;
	}
	public void setBaWeightSlabId(Integer baWeightSlabId) {
		this.baWeightSlabId = baWeightSlabId;
	}
	public Integer getStartWeight() {
		return startWeight;
	}
	public void setStartWeight(Integer startWeight) {
		this.startWeight = startWeight;
	}
	public Integer getEndWeight() {
		return endWeight;
	}
	public void setEndWeight(Integer endWeight) {
		this.endWeight = endWeight;
	}
	public Integer getBaRateConfigProductHeaderId() {
		return baRateConfigProductHeaderId;
	}
	public void setBaRateConfigProductHeaderId(Integer baRateConfigProductHeaderId) {
		this.baRateConfigProductHeaderId = baRateConfigProductHeaderId;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Set<BcunBaRateConfigSlabRateDO> getBaSlabRateDO() {
		return baSlabRateDO;
	}
	public void setBaSlabRateDO(Set<BcunBaRateConfigSlabRateDO> baSlabRateDO) {
		this.baSlabRateDO = baSlabRateDO;
	}
	public Set<BcunBARateConfigSpecialDestinationRateDO> getBaSpecialDestinationRateDO() {
		return baSpecialDestinationRateDO;
	}
	public void setBaSpecialDestinationRateDO(
			Set<BcunBARateConfigSpecialDestinationRateDO> baSpecialDestinationRateDO) {
		this.baSpecialDestinationRateDO = baSpecialDestinationRateDO;
	}
	public Integer getSlabOrder() {
		return slabOrder;
	}
	public void setSlabOrder(Integer slabOrder) {
		this.slabOrder = slabOrder;
	}
	
	

}
