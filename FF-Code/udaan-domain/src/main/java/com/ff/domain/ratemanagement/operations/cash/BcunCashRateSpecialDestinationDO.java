package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author pravin meher
 */

public class BcunCashRateSpecialDestinationDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The specialDestId. */
	private Integer specialDestId;
	
	/** The productMapId. */
	//private Integer productMapId;
	/** The rateHeaderProductDO. */
	private Integer rateHeaderProduct;
	
	/** The weightSlab */
	private Integer weightSlab;
	
	/** The cityDO. */
	private Integer cityId;
	
	/** The pincode. */
	private String pincode;
	
	/** The slabRate. */
	private Double slabRate;
	
	/** The servicedOn. */
	private String servicedOn;
	
	/** The origin sector. */
	private Integer originSector;
	/** The state . */
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
	 * @return the specialDestId
	 */
	public Integer getSpecialDestId() {
		return specialDestId;
	}

	/**
	 * @param specialDestId the specialDestId to set
	 */
	public void setSpecialDestId(Integer specialDestId) {
		this.specialDestId = specialDestId;
	}

	/**
	 * @return the rateHeaderProduct
	 */
	public Integer getRateHeaderProduct() {
		return rateHeaderProduct;
	}

	/**
	 * @param rateHeaderProduct the rateHeaderProduct to set
	 */
	public void setRateHeaderProduct(Integer rateHeaderProduct) {
		this.rateHeaderProduct = rateHeaderProduct;
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
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
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
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	
}
