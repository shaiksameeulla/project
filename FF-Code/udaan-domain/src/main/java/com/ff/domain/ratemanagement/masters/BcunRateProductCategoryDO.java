package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunRateProductCategoryDO extends CGMasterDO{

	private static final long serialVersionUID = 4292575844008524332L;
	
	private Integer rateProductCategoryId;
	private String rateProductCategoryCode;
	private String rateProductCategoryName;
	private String rateProductCategoryType;
	private String isOriginConsidered;
	private String calculationType;
	private String dtToBranch;
	
	
	/**
	 * @return the dtToBranch
	 */
	public String getDtToBranch() {
		return dtToBranch;
	}
	/**
	 * @param dtToBranch the dtToBranch to set
	 */
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	/**
	 * @return the isOriginConsidered
	 */
	public String getIsOriginConsidered() {
		return isOriginConsidered;
	}
	/**
	 * @param isOriginConsidered the isOriginConsidered to set
	 */
	public void setIsOriginConsidered(String isOriginConsidered) {
		this.isOriginConsidered = isOriginConsidered;
	}
	/**
	 * @return the calculationType
	 */
	public String getCalculationType() {
		return calculationType;
	}
	/**
	 * @param calculationType the calculationType to set
	 */
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}
	public String getRateProductCategoryCode() {
		return rateProductCategoryCode;
	}
	public void setRateProductCategoryCode(String rateProductCategoryCode) {
		this.rateProductCategoryCode = rateProductCategoryCode;
	}
	
	public Integer getRateProductCategoryId() {
		return rateProductCategoryId;
	}
	public void setRateProductCategoryId(Integer rateProductCategoryId) {
		this.rateProductCategoryId = rateProductCategoryId;
	}
	public String getRateProductCategoryName() {
		return rateProductCategoryName;
	}
	public void setRateProductCategoryName(String rateProductCategoryName) {
		this.rateProductCategoryName = rateProductCategoryName;
	}
	public String getRateProductCategoryType() {
		return rateProductCategoryType;
	}
	public void setRateProductCategoryType(String rateProductCategoryType) {
		this.rateProductCategoryType = rateProductCategoryType;
	}

}
