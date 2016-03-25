package com.ff.domain.ratemanagement.operations.rateBenchmarkDiscount;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author preegupt
 *
 */
public class BcunRegionRateBenchMarkDiscountDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518579948605053536L;

	private Integer regionRateBenchMarkDiscountId;
	private Integer industryCategory;
	private Integer region;
	private Integer employeeId;
	private Double discountPercentage;
	private String discountApproved;
	
	/**
	 * @return the regionRateBenchMarkDiscountId
	 */
	public Integer getRegionRateBenchMarkDiscountId() {
		return regionRateBenchMarkDiscountId;
	}
	/**
	 * @param regionRateBenchMarkDiscountId the regionRateBenchMarkDiscountId to set
	 */
	public void setRegionRateBenchMarkDiscountId(
			Integer regionRateBenchMarkDiscountId) {
		this.regionRateBenchMarkDiscountId = regionRateBenchMarkDiscountId;
	}
	/**
	 * @return the industryCategory
	 */
	public Integer getIndustryCategory() {
		return industryCategory;
	}
	/**
	 * @param industryCategory the industryCategory to set
	 */
	public void setIndustryCategory(Integer industryCategory) {
		this.industryCategory = industryCategory;
	}
	/**
	 * @return the region
	 */
	public Integer getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(Integer region) {
		this.region = region;
	}
	/**
	 * @return the discountPercentage
	 */
	public Double getDiscountPercentage() {
		return discountPercentage;
	}
	/**
	 * @param discountPercentage the discountPercentage to set
	 */
	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	/**
	 * @return the discountApproved
	 */
	public String getDiscountApproved() {
		return discountApproved;
	}
	/**
	 * @param discountApproved the discountApproved to set
	 */
	public void setDiscountApproved(String discountApproved) {
		this.discountApproved = discountApproved;
	}
	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
