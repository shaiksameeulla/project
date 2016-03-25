package com.ff.domain.ratemanagement.operations.ratebenchmark;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.ratemanagement.masters.RateIndustryCategoryDO;

public class RateBenchMarkHeaderDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateBenchMarkHeaderId;
	private Date rateBenchMarkEffectiveFrom;
	private Date rateBenchMarkEffectiveTo;
	private RateIndustryCategoryDO rateIndustryCategoryDO;
	private RateCustomerCategoryDO rateCustomerCategoryDO;
	private EmployeeDO approver;
	private Set<RateBenchMarkProductDO> rateBenchMarkProductDO;
	private String isApproved;
	
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public RateCustomerCategoryDO getRateCustomerCategoryDO() {
		return rateCustomerCategoryDO;
	}
	public void setRateCustomerCategoryDO(
			RateCustomerCategoryDO rateCustomerCategoryDO) {
		this.rateCustomerCategoryDO = rateCustomerCategoryDO;
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
	public RateIndustryCategoryDO getRateIndustryCategoryDO() {
		return rateIndustryCategoryDO;
	}
	public void setRateIndustryCategoryDO(
			RateIndustryCategoryDO rateIndustryCategoryDO) {
		this.rateIndustryCategoryDO = rateIndustryCategoryDO;
	}
	public EmployeeDO getApprover() {
		return approver;
	}
	public void setApprover(EmployeeDO approver) {
		this.approver = approver;
	}
	public Set<RateBenchMarkProductDO> getRateBenchMarkProductDO() {
		return rateBenchMarkProductDO;
	}
	public void setRateBenchMarkProductDO(
			Set<RateBenchMarkProductDO> rateBenchMarkProductDO) {
		this.rateBenchMarkProductDO = rateBenchMarkProductDO;
	}
	
	
}
