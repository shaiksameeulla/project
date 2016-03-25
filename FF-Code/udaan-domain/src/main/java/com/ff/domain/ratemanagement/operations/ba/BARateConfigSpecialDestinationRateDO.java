package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class BARateConfigSpecialDestinationRateDO extends CGFactDO {
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
	private BaRateConfigProductHeaderDO baRateProductDO;
	private String servicedOn;
	private Integer stateId;
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
	 * @return the city
	 */
	public CityDO getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(CityDO city) {
		this.city = city;
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

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

}
