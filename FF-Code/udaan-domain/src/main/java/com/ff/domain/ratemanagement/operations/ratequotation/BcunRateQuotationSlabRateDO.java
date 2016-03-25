package com.ff.domain.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRateQuotationSlabRateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300153540090732132L;
	
	
	private Integer slabRateId;
	private Integer rateQuotationWeightSlabId;
	private Integer rateQuotationProductCategoryHeaderId;
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
