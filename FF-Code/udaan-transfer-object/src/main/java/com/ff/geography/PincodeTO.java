package com.ff.geography;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;


/**
 * The Class PincodeTO.
 */
public class PincodeTO extends CGBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7478544888641031250L;
	
	/** The pincode id. */
	private Integer pincodeId;
	
	/** The pincode. */
	private String pincode;
	
	/** The city id. */
	private Integer cityId;
	
	private CityTO cityTO;
	
	/** The city ids list. */
	private List<Integer> cityIdsList;
	private String serviceableOfficeNames;
	

	/**
	 * Gets the pincode.
	 *
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * Sets the pincode.
	 *
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * Gets the city id.
	 *
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * Sets the city id.
	 *
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * Gets the pincode id.
	 *
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * Sets the pincode id.
	 *
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the cityIdsList
	 */
	public List<Integer> getCityIdsList() {
		return cityIdsList;
	}

	public CityTO getCityTO() {
		return cityTO;
	}

	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}

	/**
	 * @param cityIdsList the cityIdsList to set
	 */
	public void setCityIdsList(List<Integer> cityIdsList) {
		this.cityIdsList = cityIdsList;
	}

	public String getServiceableOfficeNames() {
		return serviceableOfficeNames;
	}

	public void setServiceableOfficeNames(String serviceableOfficeNames) {
		this.serviceableOfficeNames = serviceableOfficeNames;
	}

}
