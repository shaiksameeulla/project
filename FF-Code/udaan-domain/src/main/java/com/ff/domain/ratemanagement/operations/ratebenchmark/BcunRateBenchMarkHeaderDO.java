package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateBenchMarkHeaderDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkHeaderId;
	private Date rateBenchMarkEffectiveFrom;
	private Date rateBenchMarkEffectiveTo;
	private Integer rateIndustryCategoryId;
	private Integer rateCustomerCategoryId;
	private Integer approver;
	private Set<BcunRateBenchMarkProductDO> rateBenchMarkProductDO;
	private String isApproved;
	
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public Integer getRateBenchMarkHeaderId() {
		return rateBenchMarkHeaderId;
	}
	public void setRateBenchMarkHeaderId(Integer rateBenchMarkHeaderId) {
		this.rateBenchMarkHeaderId = rateBenchMarkHeaderId;
	}
	public Date getRateBenchMarkEffectiveFrom() {
		return rateBenchMarkEffectiveFrom;
	}
	public void setRateBenchMarkEffectiveFrom(Date rateBenchMarkEffectiveFrom) {
		this.rateBenchMarkEffectiveFrom = rateBenchMarkEffectiveFrom;
	}
	public Date getRateBenchMarkEffectiveTo() {
		return rateBenchMarkEffectiveTo;
	}
	public void setRateBenchMarkEffectiveTo(Date rateBenchMarkEffectiveTo) {
		this.rateBenchMarkEffectiveTo = rateBenchMarkEffectiveTo;
	}
	public Set<BcunRateBenchMarkProductDO> getRateBenchMarkProductDO() {
		return rateBenchMarkProductDO;
	}
	public void setRateBenchMarkProductDO(
			Set<BcunRateBenchMarkProductDO> rateBenchMarkProductDO) {
		this.rateBenchMarkProductDO = rateBenchMarkProductDO;
	}
	/**
	 * @return the rateIndustryCategoryId
	 */
	public Integer getRateIndustryCategoryId() {
		return rateIndustryCategoryId;
	}
	/**
	 * @param rateIndustryCategoryId the rateIndustryCategoryId to set
	 */
	public void setRateIndustryCategoryId(Integer rateIndustryCategoryId) {
		this.rateIndustryCategoryId = rateIndustryCategoryId;
	}
	/**
	 * @return the rateCustomerCategoryId
	 */
	public Integer getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	/**
	 * @param rateCustomerCategoryId the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(Integer rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
	/**
	 * @return the approver
	 */
	public Integer getApprover() {
		return approver;
	}
	/**
	 * @param approver the approver to set
	 */
	public void setApprover(Integer approver) {
		this.approver = approver;
	}
	
	
}
