package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.RateSectorsDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class RateCalculationSlabRateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer slabRateId;
	private RateCalculationWeightSlabDO rateCalculationWeightSlabDO;
	private Double rate;
	private Integer originSector;
	private Integer destinationSector;
	/**
	 * @return the slabRateId
	 */
	public Integer getSlabRateId() {
		return slabRateId;
	}
	/**
	 * @param slabRateId the slabRateId to set
	 */
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
	}
	/**
	 * @return the rateCalculationWeightSlabDO
	 */
	public RateCalculationWeightSlabDO getRateCalculationWeightSlabDO() {
		return rateCalculationWeightSlabDO;
	}
	/**
	 * @param rateCalculationWeightSlabDO the rateCalculationWeightSlabDO to set
	 */
	public void setRateCalculationWeightSlabDO(
			RateCalculationWeightSlabDO rateCalculationWeightSlabDO) {
		this.rateCalculationWeightSlabDO = rateCalculationWeightSlabDO;
	}
	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the originSector
	 */
	public Integer getOriginSector() {
		return originSector;
	}
	/**
	 * @param originSector the originSector to set
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
	 * @param destinationSector the destinationSector to set
	 */
	public void setDestinationSector(Integer destinationSector) {
		this.destinationSector = destinationSector;
	}
	
	
	
}
