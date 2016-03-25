package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

/**
 * @author hkansagr
 */

public class CashRateSpecialDestinationDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The specialDestId. */
	private Integer specialDestId;
	
	/** The productMapId. */
	//private Integer productMapId;
	/** The rateHeaderProductDO. */
	private CashRateConfigHeaderProductDO rateHeaderProductDO;
	
	/** The weightSlab */
	private WeightSlabDO weightSlab;
	
	/** The cityDO. */
	private CityDO cityDO;
	
	/** The pincode. */
	private String pincode;
	
	/** The slabRate. */
	private Double slabRate;
	
	/** The servicedOn. */
	private String servicedOn;
	
	/** The origin sector. */
	private Integer originSector;

	private Integer stateId;
	
	
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
	 * @return the rateHeaderProductDO
	 */
	public CashRateConfigHeaderProductDO getRateHeaderProductDO() {
		return rateHeaderProductDO;
	}
	/**
	 * @param rateHeaderProductDO the rateHeaderProductDO to set
	 */
	public void setRateHeaderProductDO(
			CashRateConfigHeaderProductDO rateHeaderProductDO) {
		this.rateHeaderProductDO = rateHeaderProductDO;
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
	 * @return the cityDO
	 */
	public CityDO getCityDO() {
		return cityDO;
	}
	/**
	 * @param cityDO the cityDO to set
	 */
	public void setCityDO(CityDO cityDO) {
		this.cityDO = cityDO;
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
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
}
