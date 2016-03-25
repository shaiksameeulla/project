package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.ff.to.ratemanagement.masters.WeightSlabTO;

/**
 * @author hkansagr
 * 
 */

public class BaRateWeightSlabTO {

	private Integer baWeightSlabId;
	private WeightSlabTO startWeightTO;
	private WeightSlabTO endWeightTO;
	private Integer baRateConfigProductHeaderId;
	private Integer slabOrder;

	private List<BaSlabRateTO> baSlabRateList;
	private List<BaSpecialDestinationRateTO> baSpecialDestinationRateTOList;

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
	 * @return the baSlabRateList
	 */
	public List<BaSlabRateTO> getBaSlabRateList() {
		return baSlabRateList;
	}

	/**
	 * @param baSlabRateList
	 *            the baSlabRateList to set
	 */
	public void setBaSlabRateList(List<BaSlabRateTO> baSlabRateList) {
		this.baSlabRateList = baSlabRateList;
	}

	/**
	 * @return the baSpecialDestinationRateTOList
	 */
	public List<BaSpecialDestinationRateTO> getBaSpecialDestinationRateTOList() {
		return baSpecialDestinationRateTOList;
	}

	/**
	 * @param baSpecialDestinationRateTOList
	 *            the baSpecialDestinationRateTOList to set
	 */
	public void setBaSpecialDestinationRateTOList(
			List<BaSpecialDestinationRateTO> baSpecialDestinationRateTOList) {
		this.baSpecialDestinationRateTOList = baSpecialDestinationRateTOList;
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
	 * @return the startWeightTO
	 */
	public WeightSlabTO getStartWeightTO() {
		return startWeightTO;
	}

	/**
	 * @param startWeightTO
	 *            the startWeightTO to set
	 */
	public void setStartWeightTO(WeightSlabTO startWeightTO) {
		this.startWeightTO = startWeightTO;
	}

	/**
	 * @return the endWeightTO
	 */
	public WeightSlabTO getEndWeightTO() {
		return endWeightTO;
	}

	/**
	 * @param endWeightTO
	 *            the endWeightTO to set
	 */
	public void setEndWeightTO(WeightSlabTO endWeightTO) {
		this.endWeightTO = endWeightTO;
	}

	/**
	 * @return the baRateConfigProductHeaderId
	 */
	public Integer getBaRateConfigProductHeaderId() {
		return baRateConfigProductHeaderId;
	}

	/**
	 * @param baRateConfigProductHeaderId
	 *            the baRateConfigProductHeaderId to set
	 */
	public void setBaRateConfigProductHeaderId(
			Integer baRateConfigProductHeaderId) {
		this.baRateConfigProductHeaderId = baRateConfigProductHeaderId;
	}

}
