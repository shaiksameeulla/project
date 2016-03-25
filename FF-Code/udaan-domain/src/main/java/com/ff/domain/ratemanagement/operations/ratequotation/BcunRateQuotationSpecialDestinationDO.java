package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;


public class BcunRateQuotationSpecialDestinationDO extends CGFactDO {
	private static final long serialVersionUID = -5179618936471881487L;
	
	
	private Integer rateQuotationSpecialDestinationId;
	private Integer specialDestinationCityId;
	private Integer rateQuotationWeightSlabId;
	private Integer rateQuotationProductCategoryHeaderId;
	private Double rate;
	private Integer pinCode;
	private Integer order;
	private Integer originSector;
	private Integer stateId;
	private String rateConfiguredType;
	
	
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
	 * @param originSector the originSector to set
	 */
	public void setOriginSector(Integer originSector) {
		this.originSector = originSector;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
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
	 * @param rate the rate to set
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
	 * @param specialDestinationId the specialDestinationId to set
	 */
	public void setRateQuotationSpecialDestinationId(Integer rateQuotationSpecialDestinationId) {
		this.rateQuotationSpecialDestinationId = rateQuotationSpecialDestinationId;
	}
	/**
	 * @return the specialDestinationCityId
	 */
	public Integer getSpecialDestinationCityId() {
		return specialDestinationCityId;
	}
	/**
	 * @param specialDestinationCityId the specialDestinationCityId to set
	 */
	public void setSpecialDestinationCityId(Integer specialDestinationCityId) {
		this.specialDestinationCityId = specialDestinationCityId;
	}
	/**
	 * @return the rateQuotationWeightSlabId
	 */
	public Integer getRateQuotationWeightSlabId() {
		return rateQuotationWeightSlabId;
	}
	/**
	 * @param rateQuotationWeightSlabId the rateQuotationWeightSlabId to set
	 */
	public void setRateQuotationWeightSlabId(Integer rateQuotationWeightSlabId) {
		this.rateQuotationWeightSlabId = rateQuotationWeightSlabId;
	}
	/**
	 * @return the rateQuotationProductCategoryHeaderId
	 */
	public Integer getRateQuotationProductCategoryHeaderId() {
		return rateQuotationProductCategoryHeaderId;
	}
	/**
	 * @param rateQuotationProductCategoryHeaderId the rateQuotationProductCategoryHeaderId to set
	 */
	public void setRateQuotationProductCategoryHeaderId(
			Integer rateQuotationProductCategoryHeaderId) {
		this.rateQuotationProductCategoryHeaderId = rateQuotationProductCategoryHeaderId;
	}
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	
}