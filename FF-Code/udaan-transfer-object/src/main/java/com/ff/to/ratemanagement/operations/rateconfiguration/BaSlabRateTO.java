package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class BaSlabRateTO extends CGBaseTO {

	private static final long serialVersionUID = -4624267172698691551L;
	private Integer baSlabRateId;
	private Integer baProductHeaderId;
	private Integer originSector;
	private Integer destinationSector;
	private Double rate;
	private Integer weightSlabId;
	private String servicedOn;
	private Integer baWeightSlabId;

	
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
	 * @return the weightSlabId
	 */
	public Integer getWeightSlabId() {
		return weightSlabId;
	}

	/**
	 * @param weightSlabId
	 *            the weightSlabId to set
	 */
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
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

}
