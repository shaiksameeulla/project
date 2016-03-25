package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;
public class CashSlabRateDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933606779165194225L;

	private Integer slabRateId;
	private Integer rateProductMapId;
	private WeightSlabDO weightSlabDO;
	private Integer originSectorId;
	private Integer destinationSectorId;
	private double slabRate;
	private String servicedOn;
	
	
	public String getServicedOn() {
		return servicedOn;
	}
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	public Integer getSlabRateId() {
		return slabRateId;
	}
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
	}
	public Integer getRateProductMapId() {
		return rateProductMapId;
	}
	public void setRateProductMapId(Integer rateProductMapId) {
		this.rateProductMapId = rateProductMapId;
	}

	public WeightSlabDO getWeightSlabDO() {
		return weightSlabDO;
	}
	public void setWeightSlabDO(WeightSlabDO weightSlabDO) {
		this.weightSlabDO = weightSlabDO;
	}
	public Integer getOriginSectorId() {
		return originSectorId;
	}
	public void setOriginSectorId(Integer originSectorId) {
		this.originSectorId = originSectorId;
	}
	public Integer getDestinationSectorId() {
		return destinationSectorId;
	}
	public void setDestinationSectorId(Integer destinationSectorId) {
		this.destinationSectorId = destinationSectorId;
	}
	public double getSlabRate() {
		return slabRate;
	}
	public void setSlabRate(double slabRate) {
		this.slabRate = slabRate;
	}

	
}
