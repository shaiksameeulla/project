package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 */

public class BcunCashRateSlabRateDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The slabRateId. */
	private Integer slabRateId;
	
	/** The rateProductMapId. */
	//private Integer rateProductMapId;
	/** The rateHeaderProductDO. */
	private Integer rateHeaderProductDO; 
	
	/** The weightSlabDO. */
	private Integer weightSlabDO;
	
	/** The originSectorId. */
	private Integer originSectorId;
	
	/** The destinationSectorId. */
	private Integer destinationSectorId;
	
	/** The slabRate. */
	private Double slabRate;
	
	/** The servicedOn. */
	private String servicedOn;

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
	 * @return the rateHeaderProductDO
	 */
	public Integer getRateHeaderProductDO() {
		return rateHeaderProductDO;
	}

	/**
	 * @param rateHeaderProductDO the rateHeaderProductDO to set
	 */
	public void setRateHeaderProductDO(Integer rateHeaderProductDO) {
		this.rateHeaderProductDO = rateHeaderProductDO;
	}

	/**
	 * @return the weightSlabDO
	 */
	public Integer getWeightSlabDO() {
		return weightSlabDO;
	}

	/**
	 * @param weightSlabDO the weightSlabDO to set
	 */
	public void setWeightSlabDO(Integer weightSlabDO) {
		this.weightSlabDO = weightSlabDO;
	}

	/**
	 * @return the originSectorId
	 */
	public Integer getOriginSectorId() {
		return originSectorId;
	}

	/**
	 * @param originSectorId the originSectorId to set
	 */
	public void setOriginSectorId(Integer originSectorId) {
		this.originSectorId = originSectorId;
	}

	/**
	 * @return the destinationSectorId
	 */
	public Integer getDestinationSectorId() {
		return destinationSectorId;
	}

	/**
	 * @param destinationSectorId the destinationSectorId to set
	 */
	public void setDestinationSectorId(Integer destinationSectorId) {
		this.destinationSectorId = destinationSectorId;
	}

	/**
	 * @return the slabRate
	 */
	public Double getSlabRate() {
		return slabRate;
	}

	/**
	 * @param slabRate the slabRate to set
	 */
	public void setSlabRate(Double slabRate) {
		this.slabRate = slabRate;
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
	
	
	
}
