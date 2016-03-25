/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class BARateConfigSlabRateTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -90278834225174311L;

	private Integer rateCustomerCategoryId;
	private Integer prioritySplDestcoloumCount;
	private Integer courierSplDestcoloumCount;
	private Integer airCargoSplDestcoloumCount;
	private Integer trainSplDestcoloumCount;
	
	private String servicedOn;

	int rowCount;
	private Double[] rate = new Double[rowCount];
	private Integer[] baWeightSlabId = new Integer[rowCount];
	private Integer[] destinationSectorId = new Integer[rowCount];
	private String[] pincode = new String[rowCount];
	private Integer[] pincodeId = new Integer[rowCount];
	private String[] cityName = new String[rowCount];
	private Integer[] cityIds = new Integer[rowCount];
	private Double[] specialDestinationRate = new Double[rowCount];
	private Integer[] baSlabRateid = new Integer[rowCount];
	private Integer[] specialDestinationId = new Integer[rowCount];
	private Integer[] stateId = new Integer[rowCount];
	private List<BaSlabRateTO> baSlabTOList;
	private List<BaSpecialDestinationRateTO> baSplDestTOList;

	private Integer[] startWeightSlabId = new Integer[rowCount];
	private Integer[] endWeightSlabId = new Integer[rowCount];
	private Integer[] position = new Integer[rowCount];

	
	public Integer getAirCargoSplDestcoloumCount() {
		return airCargoSplDestcoloumCount;
	}
	public void setAirCargoSplDestcoloumCount(Integer airCargoSplDestcoloumCount) {
		this.airCargoSplDestcoloumCount = airCargoSplDestcoloumCount;
	}
	public Integer getTrainSplDestcoloumCount() {
		return trainSplDestcoloumCount;
	}
	public void setTrainSplDestcoloumCount(Integer trainSplDestcoloumCount) {
		this.trainSplDestcoloumCount = trainSplDestcoloumCount;
	}
	/**
	 * @return the position
	 */
	public Integer[] getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer[] position) {
		this.position = position;
	}

	/**
	 * @return the startWeightSlabId
	 */
	public Integer[] getStartWeightSlabId() {
		return startWeightSlabId;
	}

	/**
	 * @param startWeightSlabId
	 *            the startWeightSlabId to set
	 */
	public void setStartWeightSlabId(Integer[] startWeightSlabId) {
		this.startWeightSlabId = startWeightSlabId;
	}

	/**
	 * @return the endWeightSlabId
	 */
	public Integer[] getEndWeightSlabId() {
		return endWeightSlabId;
	}

	/**
	 * @param endWeightSlabId
	 *            the endWeightSlabId to set
	 */
	public void setEndWeightSlabId(Integer[] endWeightSlabId) {
		this.endWeightSlabId = endWeightSlabId;
	}

	/**
	 * @return the courierSplDestcoloumCount
	 */
	public Integer getCourierSplDestcoloumCount() {
		return courierSplDestcoloumCount;
	}

	/**
	 * @param courierSplDestcoloumCount
	 *            the courierSplDestcoloumCount to set
	 */
	public void setCourierSplDestcoloumCount(Integer courierSplDestcoloumCount) {
		this.courierSplDestcoloumCount = courierSplDestcoloumCount;
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
	 * @return the rateCustomerCategoryId
	 */
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}

	/**
	 * @param rateCustomerCategoryId
	 *            the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}

	/**
	 * @return the prioritySplDestcoloumCount
	 */
	public Integer getPrioritySplDestcoloumCount() {
		return prioritySplDestcoloumCount;
	}

	/**
	 * @param prioritySplDestcoloumCount
	 *            the prioritySplDestcoloumCount to set
	 */
	public void setPrioritySplDestcoloumCount(Integer prioritySplDestcoloumCount) {
		this.prioritySplDestcoloumCount = prioritySplDestcoloumCount;
	}

	/**
	 * @return the rate
	 */
	public Double[] getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Double[] rate) {
		this.rate = rate;
	}

	/**
	 * @return the baWeightSlabId
	 */
	public Integer[] getBaWeightSlabId() {
		return baWeightSlabId;
	}

	/**
	 * @param baWeightSlabId
	 *            the baWeightSlabId to set
	 */
	public void setBaWeightSlabId(Integer[] baWeightSlabId) {
		this.baWeightSlabId = baWeightSlabId;
	}

	/**
	 * @return the destinationSectorId
	 */
	public Integer[] getDestinationSectorId() {
		return destinationSectorId;
	}

	/**
	 * @param destinationSectorId
	 *            the destinationSectorId to set
	 */
	public void setDestinationSectorId(Integer[] destinationSectorId) {
		this.destinationSectorId = destinationSectorId;
	}

	/**
	 * @return the pincode
	 */
	public String[] getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String[] pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the pincodeId
	 */
	public Integer[] getPincodeId() {
		return pincodeId;
	}

	/**
	 * @param pincodeId
	 *            the pincodeId to set
	 */
	public void setPincodeId(Integer[] pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * @return the cityName
	 */
	public String[] getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String[] cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the cityIds
	 */
	public Integer[] getCityIds() {
		return cityIds;
	}

	/**
	 * @param cityIds
	 *            the cityIds to set
	 */
	public void setCityIds(Integer[] cityIds) {
		this.cityIds = cityIds;
	}

	/**
	 * @return the specialDestinationRate
	 */
	public Double[] getSpecialDestinationRate() {
		return specialDestinationRate;
	}

	/**
	 * @param specialDestinationRate
	 *            the specialDestinationRate to set
	 */
	public void setSpecialDestinationRate(Double[] specialDestinationRate) {
		this.specialDestinationRate = specialDestinationRate;
	}

	/**
	 * @return the baSlabRateid
	 */
	public Integer[] getBaSlabRateid() {
		return baSlabRateid;
	}

	/**
	 * @param baSlabRateid
	 *            the baSlabRateid to set
	 */
	public void setBaSlabRateid(Integer[] baSlabRateid) {
		this.baSlabRateid = baSlabRateid;
	}

	/**
	 * @return the specialDestinationId
	 */
	public Integer[] getSpecialDestinationId() {
		return specialDestinationId;
	}

	/**
	 * @param specialDestinationId
	 *            the specialDestinationId to set
	 */
	public void setSpecialDestinationId(Integer[] specialDestinationId) {
		this.specialDestinationId = specialDestinationId;
	}

	/**
	 * @return the baSlabTOList
	 */
	public List<BaSlabRateTO> getBaSlabTOList() {
		return baSlabTOList;
	}

	/**
	 * @param baSlabTOList
	 *            the baSlabTOList to set
	 */
	public void setBaSlabTOList(List<BaSlabRateTO> baSlabTOList) {
		this.baSlabTOList = baSlabTOList;
	}

	/**
	 * @return the baSplDestTOList
	 */
	public List<BaSpecialDestinationRateTO> getBaSplDestTOList() {
		return baSplDestTOList;
	}

	/**
	 * @param baSplDestTOList
	 *            the baSplDestTOList to set
	 */
	public void setBaSplDestTOList(
			List<BaSpecialDestinationRateTO> baSplDestTOList) {
		this.baSplDestTOList = baSplDestTOList;
	}

	public Integer[] getStateId() {
		return stateId;
	}

	public void setStateId(Integer[] stateId) {
		this.stateId = stateId;
	}

}
