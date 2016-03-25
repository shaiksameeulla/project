package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class CashSpecialDestinationDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9124618964556011511L;
	private Integer specialDestId;
	private Integer productHeaderMap;
	private WeightSlabDO weightSlab;
	private Integer cityId;
	private String pincode;
	private double slabRate;
	private String servicedOn;
	private Integer originSector;
	private Integer stateId;

	
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

	public String getServicedOn() {
		return servicedOn;
	}

	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}

	public Integer getSpecialDestId() {
		return specialDestId;
	}

	public void setSpecialDestId(Integer specialDestId) {
		this.specialDestId = specialDestId;
	}

	public Integer getProductHeaderMap() {
		return productHeaderMap;
	}

	public void setProductHeaderMap(Integer productHeaderMap) {
		this.productHeaderMap = productHeaderMap;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public double getSlabRate() {
		return slabRate;
	}

	public void setSlabRate(double slabRate) {
		this.slabRate = slabRate;
	}
}
