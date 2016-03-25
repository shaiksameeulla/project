package com.ff.domain.ratemanagement.operations.ratecalculation;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigHeaderDO;

public class BARateConfigRTOChargesDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9090999703745389480L;
	
	private Integer rateBARTOChargesId;
	private String rtoChargeApplicable;
	private String sameAsSlabRate;
	private String rateComponentCode;
	/*private BaRateConfigProductHeaderDO baProductHeaderDO;*/
	private Double discountOnSlab;
	/*private Integer baRateConfigHeaderId;*/
	private BaRateConfigHeaderDO baRateConfigHeaderDO;
	private String priorityIndicator;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;
	/**
	 * @return the rateBARTOChargesId
	 */
	public Integer getRateBARTOChargesId() {
		return rateBARTOChargesId;
	}
	/**
	 * @param rateBARTOChargesId the rateBARTOChargesId to set
	 */
	public void setRateBARTOChargesId(Integer rateBARTOChargesId) {
		this.rateBARTOChargesId = rateBARTOChargesId;
	}
	/**
	 * @return the rtoChargeApplicable
	 */
	public String getRtoChargeApplicable() {
		return rtoChargeApplicable;
	}
	/**
	 * @param rtoChargeApplicable the rtoChargeApplicable to set
	 */
	public void setRtoChargeApplicable(String rtoChargeApplicable) {
		this.rtoChargeApplicable = rtoChargeApplicable;
	}
	/**
	 * @return the sameAsSlabRate
	 */
	public String getSameAsSlabRate() {
		return sameAsSlabRate;
	}
	/**
	 * @param sameAsSlabRate the sameAsSlabRate to set
	 */
	public void setSameAsSlabRate(String sameAsSlabRate) {
		this.sameAsSlabRate = sameAsSlabRate;
	}
	/**
	 * @return the rateComponentCode
	 */
	public String getRateComponentCode() {
		return rateComponentCode;
	}
	/**
	 * @param rateComponentCode the rateComponentCode to set
	 */
	public void setRateComponentCode(String rateComponentCode) {
		this.rateComponentCode = rateComponentCode;
	}
	/**
	 * @return the discountOnSlab
	 */
	public Double getDiscountOnSlab() {
		return discountOnSlab;
	}
	/**
	 * @param discountOnSlab the discountOnSlab to set
	 */
	public void setDiscountOnSlab(Double discountOnSlab) {
		this.discountOnSlab = discountOnSlab;
	}
	
	public BaRateConfigHeaderDO getBaRateConfigHeaderDO() {
		return baRateConfigHeaderDO;
	}
	public void setBaRateConfigHeaderDO(BaRateConfigHeaderDO baRateConfigHeaderDO) {
		this.baRateConfigHeaderDO = baRateConfigHeaderDO;
	}
	public String getPriorityIndicator() {
		return priorityIndicator;
	}
	public void setPriorityIndicator(String priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
