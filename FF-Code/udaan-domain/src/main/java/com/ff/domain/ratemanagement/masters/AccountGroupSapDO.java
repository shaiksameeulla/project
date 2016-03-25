package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class AccountGroupSapDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5342644139734696956L;
	private Integer accountGroupSapId;
	private RateCustomerCategoryDO rateCustomerCategoryId;
	private String customerGroup;
	private String accountAssignGroup;
	private String accountGroup;
	/**
	 * @return the accountGroupSapId
	 */
	public Integer getAccountGroupSapId() {
		return accountGroupSapId;
	}
	/**
	 * @param accountGroupSapId the accountGroupSapId to set
	 */
	public void setAccountGroupSapId(Integer accountGroupSapId) {
		this.accountGroupSapId = accountGroupSapId;
	}
	/**
	 * @return the rateCustomerCategoryId
	 */
	public RateCustomerCategoryDO getRateCustomerCategoryId() {
		return rateCustomerCategoryId;
	}
	/**
	 * @param rateCustomerCategoryId the rateCustomerCategoryId to set
	 */
	public void setRateCustomerCategoryId(
			RateCustomerCategoryDO rateCustomerCategoryId) {
		this.rateCustomerCategoryId = rateCustomerCategoryId;
	}
	/**
	 * @return the customerGroup
	 */
	public String getCustomerGroup() {
		return customerGroup;
	}
	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	/**
	 * @return the accountAssignGroup
	 */
	public String getAccountAssignGroup() {
		return accountAssignGroup;
	}
	/**
	 * @param accountAssignGroup the accountAssignGroup to set
	 */
	public void setAccountAssignGroup(String accountAssignGroup) {
		this.accountAssignGroup = accountAssignGroup;
	}
	/**
	 * @return the accountGroup
	 */
	public String getAccountGroup() {
		return accountGroup;
	}
	/**
	 * @param accountGroup the accountGroup to set
	 */
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	
	
}
