package com.ff.domain.ratemanagement.operations.ratequotation;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRateQuotationWeightSlabDO extends CGFactDO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5322823439885549207L;
	

	private Integer rateQuotationWeightSlabId;
	private Integer rateQuotProductCategoryHeaderId;
	private Integer startWeight;
	private Integer endWeight;
	private String additional;
	private Set<BcunRateQuotationSlabRateDO> rateQuotationSlabRateDO;
	private Set<BcunRateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO;
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
	 * @return the rateQuotProductCategoryHeaderId
	 */
	public Integer getRateQuotProductCategoryHeaderId() {
		return rateQuotProductCategoryHeaderId;
	}
	/**
	 * @param rateQuotProductCategoryHeaderId the rateQuotProductCategoryHeaderId to set
	 */
	public void setRateQuotProductCategoryHeaderId(
			Integer rateQuotProductCategoryHeaderId) {
		this.rateQuotProductCategoryHeaderId = rateQuotProductCategoryHeaderId;
	}
	/**
	 * @return the startWeight
	 */
	public Integer getStartWeight() {
		return startWeight;
	}
	/**
	 * @param startWeight the startWeight to set
	 */
	public void setStartWeight(Integer startWeight) {
		this.startWeight = startWeight;
	}
	/**
	 * @return the endWeight
	 */
	public Integer getEndWeight() {
		return endWeight;
	}
	/**
	 * @param endWeight the endWeight to set
	 */
	public void setEndWeight(Integer endWeight) {
		this.endWeight = endWeight;
	}
	/**
	 * @return the rateQuotationSlabRateDO
	 */
	public Set<BcunRateQuotationSlabRateDO> getRateQuotationSlabRateDO() {
		return rateQuotationSlabRateDO;
	}
	/**
	 * @param rateQuotationSlabRateDO the rateQuotationSlabRateDO to set
	 */
	public void setRateQuotationSlabRateDO(
			Set<BcunRateQuotationSlabRateDO> rateQuotationSlabRateDO) {
		this.rateQuotationSlabRateDO = rateQuotationSlabRateDO;
	}
	/**
	 * @return the rateQuotationSpecialDestinationDO
	 */
	public Set<BcunRateQuotationSpecialDestinationDO> getRateQuotationSpecialDestinationDO() {
		return rateQuotationSpecialDestinationDO;
	}
	/**
	 * @param rateQuotationSpecialDestinationDO the rateQuotationSpecialDestinationDO to set
	 */
	public void setRateQuotationSpecialDestinationDO(
			Set<BcunRateQuotationSpecialDestinationDO> rateQuotationSpecialDestinationDO) {
		this.rateQuotationSpecialDestinationDO = rateQuotationSpecialDestinationDO;
	}
	public String getRateConfiguredType() {
		return rateConfiguredType;
	}
	public void setRateConfiguredType(String rateConfiguredType) {
		this.rateConfiguredType = rateConfiguredType;
	}
	
}
