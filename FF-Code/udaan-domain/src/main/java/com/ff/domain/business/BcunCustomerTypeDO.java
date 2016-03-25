/**
 * 
 */
package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author Pravin Meher
 *
 */
public class BcunCustomerTypeDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer customerTypeId;
	private String customerTypeCode;
	private String customerTypeDesc;
	/**
	 * @return the customerTypeId
	 */
	public Integer getCustomerTypeId() {
		return customerTypeId;
	}
	/**
	 * @param customerTypeId the customerTypeId to set
	 */
	public void setCustomerTypeId(Integer customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	/**
	 * @return the customerTypeCode
	 */
	public String getCustomerTypeCode() {
		return customerTypeCode;
	}
	/**
	 * @param customerTypeCode the customerTypeCode to set
	 */
	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}
	/**
	 * @return the customerTypeDesc
	 */
	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}
	/**
	 * @param customerTypeDesc the customerTypeDesc to set
	 */
	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}
	
	

}
