/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author prmeher
 * 
 */
public class BaSpecialDestinationRateTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2321340734617032074L;
	private Integer specialDestinationId;
	private Integer rateProductHeaderId;
	private CityTO cityTO;
	private String pincode;
	private Double rate;
	private Integer weightSlabId;
	private String servicedOn;
	private Integer stateId;
	private List<CityTO> cityList;
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
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}

	/**
	 * @param cityTO
	 *            the cityTO to set
	 */
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
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

	public List<CityTO> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityTO> cityList) {
		this.cityList = cityList;
	}

}
