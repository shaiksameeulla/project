package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RateQuotationSlabRateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer slabRateId;
	private RateQuotationWeightSlabDO rateQuotationWeightSlabDO;
	private RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeader;
	private Double rate;
	private Integer originSector;
	private Integer destinationSector;
	private String valueFromROI;
	private String rateConfiguredType;
	/**
	 * @return the slabRateId
	 */
	public Integer getSlabRateId() {
		return slabRateId;
	}
	/**
	 * @return the valueFromROI
	 */
	public String getValueFromROI() {
		return valueFromROI;
	}
	/**
	 * @param valueFromROI the valueFromROI to set
	 */
	public void setValueFromROI(String valueFromROI) {
		this.valueFromROI = valueFromROI;
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
	 * @return the destinationSector
	 */
	public Integer getDestinationSector() {
		return destinationSector;
	}
	/**
	 * @param destinationSector the destinationSector to set
	 */
	public void setDestinationSector(Integer destinationSector) {
		this.destinationSector = destinationSector;
	}
	/**
	 * @param slabRateId the slabRateId to set
	 */
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
	}
		
	/**
	 * @return the rateQuotationWeightSlabDO
	 */
	public RateQuotationWeightSlabDO getRateQuotationWeightSlabDO() {
		return rateQuotationWeightSlabDO;
	}
	/**
	 * @param rateQuotationWeightSlabDO the rateQuotationWeightSlabDO to set
	 */
	public void setRateQuotationWeightSlabDO(
			RateQuotationWeightSlabDO rateQuotationWeightSlabDO) {
		this.rateQuotationWeightSlabDO = rateQuotationWeightSlabDO;
	}
	/**
	 * @return the rateQuotationProductCategoryHeader
	 */
	public RateQuotationProductCategoryHeaderDO getRateQuotationProductCategoryHeader() {
		return rateQuotationProductCategoryHeader;
	}
	/**
	 * @param rateQuotationProductCategoryHeader the rateQuotationProductCategoryHeader to set
	 */
	public void setRateQuotationProductCategoryHeader(
			RateQuotationProductCategoryHeaderDO rateQuotationProductCategoryHeader) {
		this.rateQuotationProductCategoryHeader = rateQuotationProductCategoryHeader;
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
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	
	
}
