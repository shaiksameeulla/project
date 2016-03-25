package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BaSpecialDestinationRateDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -59372902101728522L;
	private Integer specialDestinationId;
	private Integer rateProductHeaderId;
	private BaRateCalculationWeightSlabDO weightSlab;
	private Integer cityId;
	private String pincode;
	private Integer stateId;
	private Double rate;
	private String servicedOn;

	
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
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
	 * @return the specialDestinationId
	 */
	public Integer getSpecialDestinationId() {
		return specialDestinationId;
	}

	/**
	 * @param specialDestinationId
	 *            the specialDestinationId to set
	 */
	public void setSpecialDestinationId(Integer specialDestinationId) {
		this.specialDestinationId = specialDestinationId;
	}

	/**
	 * @return the rateProductHeaderId
	 */
	public Integer getRateProductHeaderId() {
		return rateProductHeaderId;
	}

	/**
	 * @param rateProductHeaderId
	 *            the rateProductHeaderId to set
	 */
	public void setRateProductHeaderId(Integer rateProductHeaderId) {
		this.rateProductHeaderId = rateProductHeaderId;
	}

	/**
	 * @return the weightSlab
	 */
	public BaRateCalculationWeightSlabDO getWeightSlab() {
		return weightSlab;
	}

	/**
	 * @param weightSlab the weightSlab to set
	 */
	public void setWeightSlab(BaRateCalculationWeightSlabDO weightSlab) {
		this.weightSlab = weightSlab;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
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
