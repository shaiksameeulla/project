package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author preegupt
 *
 */
public class RateQuotationSlabRateTO  extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7780395221639826116L;
	private Integer slabRateId;
	private Integer originSector;
	private Integer destinationSector;
	//private RateQuotationWeightSlabTO WeightSlabTO;
	//private RateQuotationProductCategoryHeaderTO productCategoryHeader;
	private Double rate;
	private String valueFromROI;
	private String rateConfiguredType;
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
	 * @return the slabRateId
	 */
	public Integer getSlabRateId() {
		return slabRateId;
	}
	
	/**
	 * @return the weightSlabTO
	 */
	/*public RateQuotationWeightSlabTO getWeightSlabTO() {
		return WeightSlabTO;
	}

	*//**
	 * @param weightSlabTO the weightSlabTO to set
	 *//*
	public void setWeightSlabTO(RateQuotationWeightSlabTO weightSlabTO) {
		WeightSlabTO = weightSlabTO;
	}*/

	/**
	 * @return the productCategoryHeader
	 */
	/*public RateQuotationProductCategoryHeaderTO getProductCategoryHeader() {
		return productCategoryHeader;
	}

	*//**
	 * @param productCategoryHeader the productCategoryHeader to set
	 *//*
	public void setProductCategoryHeader(
			RateQuotationProductCategoryHeaderTO productCategoryHeader) {
		this.productCategoryHeader = productCategoryHeader;
	}*/

	/**
	 * @param slabRateId the slabRateId to set
	 */
	public void setSlabRateId(Integer slabRateId) {
		this.slabRateId = slabRateId;
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
