package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RateCalculationQuotationSlabRateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer slabRateId;
	private RateCalculationWeightSlabDO rateCalculationWeightSlabDO;
	private Integer rateQuotationProductCategoryHeader;
	private Double rate;
	private Integer originSector;
	private Integer destinationSector;
	private String valueFromROI;
	private String rateCalculatedFor;
	/**
	 * @return the slabRateId
	 */
	public Integer getSlabRateId() {
		return slabRateId;
	}
	/**
	 * @param slabRateId the slabRateId to set
	 */
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
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
	
	
}
