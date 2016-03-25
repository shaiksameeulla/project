package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunBaRateConfigSlabRateDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5817325862392307914L;
	private Integer baSlabRateId;
	private Integer weightSlab;
	private Integer originSector;
	private Integer destinationSector;
	private Double rate;
	private String servicedOn;
	private Integer baProductHeaderId;
	/**
	 * @return the baSlabRateId
	 */
	public Integer getBaSlabRateId() {
		return baSlabRateId;
	}
	/**
	 * @param baSlabRateId the baSlabRateId to set
	 */
	public void setBaSlabRateId(Integer baSlabRateId) {
		this.baSlabRateId = baSlabRateId;
	}
	/**
	 * @return the weightSlab
	 */
	public Integer getWeightSlab() {
		return weightSlab;
	}
	/**
	 * @param weightSlab the weightSlab to set
	 */
	public void setWeightSlab(Integer weightSlab) {
		this.weightSlab = weightSlab;
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
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}
	/**
	 * @param servicedOn the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}
	/**
	 * @param baProductHeaderId the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}
	
}
