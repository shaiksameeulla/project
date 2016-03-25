package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;


public class RateCalculationSpecialDestinationDO extends CGFactDO {
	private static final long serialVersionUID = -5179618936471881487L;
	
	
	private Integer rateQuotationSpecialDestinationId;
	private Integer cityId;
	private RateCalculationWeightSlabDO rateCalculationWeightSlabDO;
	private Double rate;
	/**
	 * @return the rateQuotationSpecialDestinationId
	 */
	public Integer getRateQuotationSpecialDestinationId() {
		return rateQuotationSpecialDestinationId;
	}
	/**
	 * @param rateQuotationSpecialDestinationId the rateQuotationSpecialDestinationId to set
	 */
	public void setRateQuotationSpecialDestinationId(
			Integer rateQuotationSpecialDestinationId) {
		this.rateQuotationSpecialDestinationId = rateQuotationSpecialDestinationId;
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
	 * @return the rateCalculationWeightSlabDO
	 */
	public RateCalculationWeightSlabDO getRateCalculationWeightSlabDO() {
		return rateCalculationWeightSlabDO;
	}
	/**
	 * @param rateCalculationWeightSlabDO the rateCalculationWeightSlabDO to set
	 */
	public void setRateCalculationWeightSlabDO(
			RateCalculationWeightSlabDO rateCalculationWeightSlabDO) {
		this.rateCalculationWeightSlabDO = rateCalculationWeightSlabDO;
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
	
	
}