package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

/**
 * @author hkansagr
 */

public class CashRateSpecialDestinationTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	private Integer specialDestId;//The specialDestId
	private Integer productMapId;//The productMapId
	private Integer weightSlabId;//The weightSlabId
	private CityTO cityTO;//The cityTO
	private String pincode;//The pincode
	private Double slabRate;//The slabRate
	private String servicedOn;//The servicedOn
	private Integer stateId;
	
	int rowCount;
	private String[] pincodes = new String [rowCount];
	private Integer[] pincodeIds = new Integer[rowCount];
	private String[] cityNames = new String [rowCount];
	private Integer[] cityIds =  new Integer[rowCount];
	private Double[] specialDestRates = new Double[rowCount];
	private Integer[] specialDestIds = new Integer[rowCount];
	private Integer[] stateAry = new Integer[rowCount];
	
	private Integer splDestColCount;
	private List<CityTO> cityList;
	
	
	/**
	 * @return the splDestColCount
	 */
	public Integer getSplDestColCount() {
		return splDestColCount;
	}
	/**
	 * @param splDestColCount the splDestColCount to set
	 */
	public void setSplDestColCount(Integer splDestColCount) {
		this.splDestColCount = splDestColCount;
	}
	/**
	 * @return the pincodes
	 */
	public String[] getPincodes() {
		return pincodes;
	}
	/**
	 * @param pincodes the pincodes to set
	 */
	public void setPincodes(String[] pincodes) {
		this.pincodes = pincodes;
	}
	/**
	 * @return the pincodeIds
	 */
	public Integer[] getPincodeIds() {
		return pincodeIds;
	}
	/**
	 * @param pincodeIds the pincodeIds to set
	 */
	public void setPincodeIds(Integer[] pincodeIds) {
		this.pincodeIds = pincodeIds;
	}
	/**
	 * @return the cityNames
	 */
	public String[] getCityNames() {
		return cityNames;
	}
	/**
	 * @param cityNames the cityNames to set
	 */
	public void setCityNames(String[] cityNames) {
		this.cityNames = cityNames;
	}
	/**
	 * @return the cityIds
	 */
	public Integer[] getCityIds() {
		return cityIds;
	}
	/**
	 * @param cityIds the cityIds to set
	 */
	public void setCityIds(Integer[] cityIds) {
		this.cityIds = cityIds;
	}
	/**
	 * @return the specialDestRates
	 */
	public Double[] getSpecialDestRates() {
		return specialDestRates;
	}
	/**
	 * @param specialDestRates the specialDestRates to set
	 */
	public void setSpecialDestRates(Double[] specialDestRates) {
		this.specialDestRates = specialDestRates;
	}
	/**
	 * @return the specialDestIds
	 */
	public Integer[] getSpecialDestIds() {
		return specialDestIds;
	}
	/**
	 * @param specialDestIds the specialDestIds to set
	 */
	public void setSpecialDestIds(Integer[] specialDestIds) {
		this.specialDestIds = specialDestIds;
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
	 * @return the productMapId
	 */
	public Integer getProductMapId() {
		return productMapId;
	}
	/**
	 * @param productMapId the productMapId to set
	 */
	public void setProductMapId(Integer productMapId) {
		this.productMapId = productMapId;
	}
	/**
	 * @return the weightSlabId
	 */
	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	/**
	 * @param weightSlabId the weightSlabId to set
	 */
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
	}
	/**
	 * @return the cityTO
	 */
	public CityTO getCityTO() {
		return cityTO;
	}
	/**
	 * @param cityTO the cityTO to set
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
	public Integer[] getStateAry() {
		return stateAry;
	}
	public void setStateAry(Integer[] stateAry) {
		this.stateAry = stateAry;
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
