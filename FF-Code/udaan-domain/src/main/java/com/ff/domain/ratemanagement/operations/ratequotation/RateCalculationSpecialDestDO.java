package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RateCalculationSpecialDestDO extends CGFactDO {
	private static final long serialVersionUID = -5179618936471881487L;

	private Integer rateQuotationSpecialDestinationId;
	private RateCalculationWeightSlabDO rateQuotationWeightSlabDO;
	private Integer rateQuotationProductCategoryHeader;
	private Double rate;
	private Integer pinCode;
	private Integer order;
	private Integer originSector;
	private Integer cityId;
	private Integer stateId;
	private String rateCalculatedFor;
	
	
	/**
	 * @return the rateCalculatedFor
	 */
	public String getRateCalculatedFor() {
		return rateCalculatedFor;
	}

	/**
	 * @param rateCalculatedFor the rateCalculatedFor to set
	 */
	public void setRateCalculatedFor(String rateCalculatedFor) {
		this.rateCalculatedFor = rateCalculatedFor;
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
	 * @return the rateQuotationProductCategoryHeader
	 */
	public Integer getRateQuotationProductCategoryHeader() {
		return rateQuotationProductCategoryHeader;
	}

	/**
	 * @param rateQuotationProductCategoryHeader the rateQuotationProductCategoryHeader to set
	 */
	public void setRateQuotationProductCategoryHeader(
			Integer rateQuotationProductCategoryHeader) {
		this.rateQuotationProductCategoryHeader = rateQuotationProductCategoryHeader;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @return the originSector
	 */
	public Integer getOriginSector() {
		return originSector;
	}

	/**
	 * @param originSector
	 *            the originSector to set
	 */
	public void setOriginSector(Integer originSector) {
		this.originSector = originSector;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}


	/**
	 * @return the rateQuotationWeightSlabDO
	 */
	public RateCalculationWeightSlabDO getRateQuotationWeightSlabDO() {
		return rateQuotationWeightSlabDO;
	}

	/**
	 * @param rateQuotationWeightSlabDO the rateQuotationWeightSlabDO to set
	 */
	public void setRateQuotationWeightSlabDO(
			RateCalculationWeightSlabDO rateQuotationWeightSlabDO) {
		this.rateQuotationWeightSlabDO = rateQuotationWeightSlabDO;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
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

	/**
	 * @return the specialDestinationId
	 */
	public Integer getRateQuotationSpecialDestinationId() {
		return rateQuotationSpecialDestinationId;
	}

	/**
	 * @param specialDestinationId
	 *            the specialDestinationId to set
	 */
	public void setRateQuotationSpecialDestinationId(
			Integer rateQuotationSpecialDestinationId) {
		this.rateQuotationSpecialDestinationId = rateQuotationSpecialDestinationId;
	}

}