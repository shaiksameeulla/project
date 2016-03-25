package com.ff.to.ratemanagement.operations.ratequotation;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;

public class RateQuotationSpecialDestinationTO extends CGBaseTO implements Comparable<RateQuotationSpecialDestinationTO>{

	
private static final long serialVersionUID = 1L;
	
	
	private Integer rateQuotationSpecialDestinationId;
	private CityTO specialDestinationCityTO;
	private Double rate;
	private Integer pinCode;
	private Integer order;
	private Integer originSector;
	private Integer stateId;
	private String rateConfiguredType;
	private List<CityTO> cityList;
	/**
	 * @return the rateQuotationSpecialDestinationId
	 */
	public Integer getRateQuotationSpecialDestinationId() {
		return rateQuotationSpecialDestinationId;
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
	/**
	 * @param rateQuotationSpecialDestinationId the rateQuotationSpecialDestinationId to set
	 */
	public void setRateQuotationSpecialDestinationId(
			Integer rateQuotationSpecialDestinationId) {
		this.rateQuotationSpecialDestinationId = rateQuotationSpecialDestinationId;
	}
	/**
	 * @return the specialDestinationCityTO
	 */
	public CityTO getSpecialDestinationCityTO() {
		return specialDestinationCityTO;
	}
	/**
	 * @param specialDestinationCityTO the specialDestinationCityTO to set
	 */
	public void setSpecialDestinationCityTO(CityTO specialDestinationCityTO) {
		this.specialDestinationCityTO = specialDestinationCityTO;
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
	 * @return the pinCode
	 */
	public Integer getPinCode() {
		return pinCode;
	}
	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public int compareTo(RateQuotationSpecialDestinationTO rqsdTO) {
		
		return this.order - rqsdTO.order;
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
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	

}
