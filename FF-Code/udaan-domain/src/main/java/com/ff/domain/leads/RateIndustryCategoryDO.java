/**
 * 
 */
package com.ff.domain.leads;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author abarudwa
 *
 */
public class RateIndustryCategoryDO extends CGFactDO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer rateIndustryCategoryId;
	
	private String industryCategoryCode;
	
	private String industryCategoryName;

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
	 * @return the industryCategoryCode
	 */
	public String getIndustryCategoryCode() {
		return industryCategoryCode;
	}

	/**
	 * @param industryCategoryCode the industryCategoryCode to set
	 */
	public void setIndustryCategoryCode(String industryCategoryCode) {
		this.industryCategoryCode = industryCategoryCode;
	}

	/**
	 * @return the industryCategoryName
	 */
	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	/**
	 * @param industryCategoryName the industryCategoryName to set
	 */
	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
