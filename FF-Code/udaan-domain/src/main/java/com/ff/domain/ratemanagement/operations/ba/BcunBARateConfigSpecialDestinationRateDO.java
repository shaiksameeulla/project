package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;


public class BcunBARateConfigSpecialDestinationRateDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3106423264729895896L;
	private Integer specialDestinationId;
	private Integer rateProductHeaderId;
	private WeightSlabDO weightSlab;
	private CityDO city;
	private String pincode;
	private Double rate;
	private String servicedOn;
	private Integer stateId;
	private Integer baRateProductHeaderId;
	private Integer baRateWeightSlabId;
	
	
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
	 * @return the specialDestinationId
	 */
	public Integer getSpecialDestinationId() {
		return specialDestinationId;
	}
	/**
	 * @param specialDestinationId the specialDestinationId to set
	 */
	public void setSpecialDestinationId(Integer specialDestinationId) {
		this.specialDestinationId = specialDestinationId;
	}
	/**
	 * @return the weightSlab
	 */
	public WeightSlabDO getWeightSlab() {
		return weightSlab;
	}
	/**
	 * @param weightSlab the weightSlab to set
	 */
	public void setWeightSlab(WeightSlabDO weightSlab) {
		this.weightSlab = weightSlab;
	}
	/**
	 * @return the city
	 */
	public CityDO getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(CityDO city) {
		this.city = city;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
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
	public Integer getRateProductHeaderId() {
		return rateProductHeaderId;
	}
	public void setRateProductHeaderId(Integer rateProductHeaderId) {
		this.rateProductHeaderId = rateProductHeaderId;
	}
	public Integer getBaRateProductHeaderId() {
		return baRateProductHeaderId;
	}
	public void setBaRateProductHeaderId(Integer baRateProductHeaderId) {
		this.baRateProductHeaderId = baRateProductHeaderId;
	}
	public Integer getBaRateWeightSlabId() {
		return baRateWeightSlabId;
	}
	public void setBaRateWeightSlabId(Integer baRateWeightSlabId) {
		this.baRateWeightSlabId = baRateWeightSlabId;
	}
	
}
