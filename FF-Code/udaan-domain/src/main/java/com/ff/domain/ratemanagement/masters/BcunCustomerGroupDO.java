package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunCustomerGroupDO extends CGMasterDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer customerGroupId;;
	private String customerGroupCode;
	private String customerGroupName;
	/**
	 * @return the customerGroupId
	 */
	public Integer getCustomerGroupId() {
		return customerGroupId;
	}
	/**
	 * @param customerGroupId the customerGroupId to set
	 */
	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	/**
	 * @return the customerGroupCode
	 */
	public String getCustomerGroupCode() {
		return customerGroupCode;
	}
	/**
	 * @param customerGroupCode the customerGroupCode to set
	 */
	public void setCustomerGroupCode(String customerGroupCode) {
		this.customerGroupCode = customerGroupCode;
	}
	/**
	 * @return the customerGroupName
	 */
	public String getCustomerGroupName() {
		return customerGroupName;
	}
	/**
	 * @param customerGroupName the customerGroupName to set
	 */
	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}



}
