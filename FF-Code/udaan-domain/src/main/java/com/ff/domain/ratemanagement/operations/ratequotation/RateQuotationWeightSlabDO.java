package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class RateQuotationWeightSlabDO extends CGFactDO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5322823439885549207L;
	

	private Integer rateQuotationWeightSlabId;
	private RateQuotationProductCategoryHeaderDO rateQuotProductCategoryHeaderDO;
	private WeightSlabDO startWeight;
	private WeightSlabDO endWeight;
	private String additional;
	private Set<RateQuotationSlabRateDO> rateQuotationSlabRateDO;
	private Set<RateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO;
	private Integer order;
	private String rateConfiguredType;
	
	/**
	 * 
	 * @return
	 */
	public String getAdditional() {
		return additional;
	}
	/**
	 * 
	 * @param additional
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
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
	/**
	 * @return the rateQuotationSlabRateDO
	 */
	public Set<RateQuotationSlabRateDO> getRateQuotationSlabRateDO() {
		return rateQuotationSlabRateDO;
	}
	/**
	 * @return the rateQuotationSpecialDestinationDO
	 */
	public Set<RateQuotationSpecialDestinationDO> getRateQuotationSpecialDestinationDO() {
		return rateQuotationSpecialDestinationDO;
	}
	/**
	 * @param rateQuotationSpecialDestinationDO the rateQuotationSpecialDestinationDO to set
	 */
	public void setRateQuotationSpecialDestinationDO(
			Set<RateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO) {
		this.rateQuotationSpecialDestinationDO = rateQuotationSpecialDestinationDO;
	}
	/**
	 * @param rateQuotationSlabRateDO the rateQuotationSlabRateDO to set
	 */
	public void setRateQuotationSlabRateDO(
			Set<RateQuotationSlabRateDO> rateQuotationSlabRateDO) {
		this.rateQuotationSlabRateDO = rateQuotationSlabRateDO;
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
	 * @return the startWeight
	 */
	public WeightSlabDO getStartWeight() {
		return startWeight;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeight(WeightSlabDO startWeight) {
		this.startWeight = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public WeightSlabDO getEndWeight() {
		return endWeight;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeight(WeightSlabDO endWeight) {
		this.endWeight = endWeight;
	}
	/**
	 * @return the rateQuotProductCategoryHeaderDO
	 */
	public RateQuotationProductCategoryHeaderDO getRateQuotProductCategoryHeaderDO() {
		return rateQuotProductCategoryHeaderDO;
	}
	/**
	 * @param rateQuotProductCategoryHeaderDO the rateQuotProductCategoryHeaderDO to set
	 */
	public void setRateQuotProductCategoryHeaderDO(
			RateQuotationProductCategoryHeaderDO rateQuotProductCategoryHeaderDO) {
		this.rateQuotProductCategoryHeaderDO = rateQuotProductCategoryHeaderDO;
	}
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	

}
