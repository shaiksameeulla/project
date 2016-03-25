package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class BaRateConfigSlabRateDO extends CGFactDO {

	private static final long serialVersionUID = 5817325862392307914L;
	private Integer baSlabRateId;
	private Integer baProductHeaderId;
	private WeightSlabDO weightSlab;
	private Integer originSector;
	private Integer destinationSector;
	private Double rate;
	private BaRateConfigProductHeaderDO baRateProductDO;
	private String servicedOn;
	private BaRateWeightSlabDO baRateWeightSlabDO;

	/**
	 * @return the baRateWeightSlabDO
	 */
	public BaRateWeightSlabDO getBaRateWeightSlabDO() {
		return baRateWeightSlabDO;
	}

	/**
	 * @param baRateWeightSlabDO
	 *            the baRateWeightSlabDO to set
	 */
	public void setBaRateWeightSlabDO(BaRateWeightSlabDO baRateWeightSlabDO) {
		this.baRateWeightSlabDO = baRateWeightSlabDO;
	}

	/**
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}

	/**
	 * @param servicedOn
	 *            the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}

	/**
	 * @return the baSlabRateId
	 */
	public Integer getBaSlabRateId() {
		return baSlabRateId;
	}

	/**
	 * @param baSlabRateId
	 *            the baSlabRateId to set
	 */
	public void setBaSlabRateId(Integer baSlabRateId) {
		this.baSlabRateId = baSlabRateId;
	}

	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}

	/**
	 * @param baProductHeaderId
	 *            the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}

	/**
	 * @return the weightSlab
	 */
	public WeightSlabDO getWeightSlab() {
		return weightSlab;
	}

	/**
	 * @param weightSlab
	 *            the weightSlab to set
	 */
	public void setWeightSlab(WeightSlabDO weightSlab) {
		this.weightSlab = weightSlab;
	}

	/**
	 * @return the originSector
	 */
	public Integer getOriginSector() {
		return originSector;
	}

	/**
	 * @param originSector
	 *            the originSector to set
	 */
	public void setOriginSector(Integer originSector) {
		this.originSector = originSector;
	}

	/**
	 * @return the destinationSector
	 */
	public Integer getDestinationSector() {
		return destinationSector;
	}

	/**
	 * @param destinationSector
	 *            the destinationSector to set
	 */
	public void setDestinationSector(Integer destinationSector) {
		this.destinationSector = destinationSector;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the baRateProductDO
	 */
	public BaRateConfigProductHeaderDO getBaRateProductDO() {
		return baRateProductDO;
	}

	/**
	 * @param baRateProductDO
	 *            the baRateProductDO to set
	 */
	public void setBaRateProductDO(BaRateConfigProductHeaderDO baRateProductDO) {
		this.baRateProductDO = baRateProductDO;
	}

}
