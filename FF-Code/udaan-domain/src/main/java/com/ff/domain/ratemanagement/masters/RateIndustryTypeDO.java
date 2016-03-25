package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class RateIndustryTypeDO extends CGMasterDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3304192345978509632L;
	

	private Integer rateIndustryTypeId;;
	private String rateIndustryTypeCode;
	private String rateIndustryTypeName;
	/**
	 * @return the rateIndustryTypeId
	 */
	public Integer getRateIndustryTypeId() {
		return rateIndustryTypeId;
	}
	/**
	 * @param rateIndustryTypeId the rateIndustryTypeId to set
	 */
	public void setRateIndustryTypeId(Integer rateIndustryTypeId) {
		this.rateIndustryTypeId = rateIndustryTypeId;
	}
	/**
	 * @return the rateIndustryTypeCode
	 */
	public String getRateIndustryTypeCode() {
		return rateIndustryTypeCode;
	}
	/**
	 * @param rateIndustryTypeCode the rateIndustryTypeCode to set
	 */
	public void setRateIndustryTypeCode(String rateIndustryTypeCode) {
		this.rateIndustryTypeCode = rateIndustryTypeCode;
	}
	/**
	 * @return the rateIndustryTypeName
	 */
	public String getRateIndustryTypeName() {
		return rateIndustryTypeName;
	}
	/**
	 * @param rateIndustryTypeName the rateIndustryTypeName to set
	 */
	public void setRateIndustryTypeName(String rateIndustryTypeName) {
		this.rateIndustryTypeName = rateIndustryTypeName;
	}
	

}
