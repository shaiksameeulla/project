package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunBaRateConfigProductHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3060120459470165376L;
	private Integer baProductHeaderId;
	private Integer rateProductCategory;
	private String isSave;
	private Integer baRateHeaderId;

	private Set<BcunBaRateWeightSlabDO> baRateWeightSlabDO;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;
	
	/**
	 * @return the baProductHeaderId
	 */
	public Integer getBaProductHeaderId() {
		return baProductHeaderId;
	}
	/**
	 * @param baProductHeaderId the baProductHeaderId to set
	 */
	public void setBaProductHeaderId(Integer baProductHeaderId) {
		this.baProductHeaderId = baProductHeaderId;
	}
	
	/**
	 * @return the rateProductCategory
	 */
	public Integer getRateProductCategory() {
		return rateProductCategory;
	}
	/**
	 * @param rateProductCategory the rateProductCategory to set
	 */
	public void setRateProductCategory(Integer rateProductCategory) {
		this.rateProductCategory = rateProductCategory;
	}
	/**
	 * @return the isSave
	 */
	public String getIsSave() {
		return isSave;
	}
	/**
	 * @param isSave the isSave to set
	 */
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	
	public Integer getBaRateHeaderId() {
		return baRateHeaderId;
	}
	public void setBaRateHeaderId(Integer baRateHeaderId) {
		this.baRateHeaderId = baRateHeaderId;
	}
	public Set<BcunBaRateWeightSlabDO> getBaRateWeightSlabDO() {
		return baRateWeightSlabDO;
	}
	public void setBaRateWeightSlabDO(Set<BcunBaRateWeightSlabDO> baRateWeightSlabDO) {
		this.baRateWeightSlabDO = baRateWeightSlabDO;
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
