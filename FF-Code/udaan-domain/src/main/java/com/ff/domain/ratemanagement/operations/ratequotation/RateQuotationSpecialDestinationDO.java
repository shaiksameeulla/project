package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;

public class RateQuotationSpecialDestinationDO extends CGFactDO {
	private static final long serialVersionUID = -5179618936471881487L;

	private Integer rateQuotationSpecialDestinationId;
	private CityDO specialDestinationCityDO;
	private RateQuotationWeightSlabDO rateQuotationWeightSlabDO;
	private RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeaderDO;
	private Double rate;
	private Integer pinCode;
	private Integer order;
	private Integer originSector;
	private Integer stateId;
	private String rateConfiguredType;

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
	public RateQuotationWeightSlabDO getRateQuotationWeightSlabDO() {
		return rateQuotationWeightSlabDO;
	}

	/**
	 * @param rateQuotationWeightSlabDO
	 *            the rateQuotationWeightSlabDO to set
	 */
	public void setRateQuotationWeightSlabDO(
			RateQuotationWeightSlabDO rateQuotationWeightSlabDO) {
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

	/**
	 * @return the specialDestinationCity
	 */
	public CityDO getSpecialDestinationCityDO() {
		return specialDestinationCityDO;
	}

	/**
	 * @param specialDestinationCity
	 *            the specialDestinationCity to set
	 */
	public void setSpecialDestinationCityDO(CityDO specialDestinationCityDO) {
		this.specialDestinationCityDO = specialDestinationCityDO;
	}

	/**
	 * @return the rateQuotationProductCategoryHeader
	 */
	public RateQuotationProductCategoryHeaderDO getRateQuotationProductCategoryHeaderDO() {
		return rateQuotationProductCategoryHeaderDO;
	}

	/**
	 * @param rateQuotationProductCategoryHeader
	 *            the rateQuotationProductCategoryHeader to set
	 */
	public void setRateQuotationProductCategoryHeaderDO(
			RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeaderDO) {
		this.rateQuotationProductCategoryHeaderDO = rateQuotationProductCategoryHeaderDO;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getRateConfiguredType() {
		return rateConfiguredType;
	}

	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	
	
}