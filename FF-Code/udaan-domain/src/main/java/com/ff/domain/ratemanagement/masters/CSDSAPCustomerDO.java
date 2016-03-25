package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.AddressDO;


public class CSDSAPCustomerDO extends CGFactDO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -115435268274976655L;
	
	private Integer customerId;
	private String contractNo;
	private String customerCode;
	private OfficeDO salesOffice;
	private EmployeeDO salesPerson;
	private RateIndustryCategoryDO industryCategory;
	private RateIndustryTypeDO industryType;
	private String distributionChannels;
	private RateCustomerCategoryDO customerCategory;
	private String businessName;
	private AddressDO address;
	private ContactDO primaryContact;
	private ContactDO secondaryContact;
	private OfficeDO officeMappedTO;
	private String tanNo;
	private String panNo;
	private String currentStatus;
	private CustomerGroupDO groupKey;
	private String businessType;
	private CustomerTypeDO customerType;
	private String billingCycle;
	private String paymentTerm;
	private String legacyCustCode;
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the salesOffice
	 */
	public OfficeDO getSalesOffice() {
		return salesOffice;
	}
	/**
	 * @param salesOffice the salesOffice to set
	 */
	public void setSalesOffice(OfficeDO salesOffice) {
		this.salesOffice = salesOffice;
	}
	/**
	 * @return the salesPerson
	 */
	public EmployeeDO getSalesPerson() {
		return salesPerson;
	}
	/**
	 * @param salesPerson the salesPerson to set
	 */
	public void setSalesPerson(EmployeeDO salesPerson) {
		this.salesPerson = salesPerson;
	}
	/**
	 * @return the industryCategory
	 */
	public RateIndustryCategoryDO getIndustryCategory() {
		return industryCategory;
	}
	/**
	 * @param industryCategory the industryCategory to set
	 */
	public void setIndustryCategory(RateIndustryCategoryDO industryCategory) {
		this.industryCategory = industryCategory;
	}
	/**
	 * @return the industryType
	 */
	public RateIndustryTypeDO getIndustryType() {
		return industryType;
	}
	/**
	 * @param industryType the industryType to set
	 */
	public void setIndustryType(RateIndustryTypeDO industryType) {
		this.industryType = industryType;
	}
	/**
	 * @return the distributionChannels
	 */
	public String getDistributionChannels() {
		return distributionChannels;
	}
	/**
	 * @param distributionChannels the distributionChannels to set
	 */
	public void setDistributionChannels(String distributionChannels) {
		this.distributionChannels = distributionChannels;
	}
	/**
	 * @return the customerCategory
	 */
	public RateCustomerCategoryDO getCustomerCategory() {
		return customerCategory;
	}
	/**
	 * @param customerCategory the customerCategory to set
	 */
	public void setCustomerCategory(RateCustomerCategoryDO customerCategory) {
		this.customerCategory = customerCategory;
	}
	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * @return the address
	 */
	public AddressDO getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(AddressDO address) {
		this.address = address;
	}
	/**
	 * @return the primaryContact
	 */
	public ContactDO getPrimaryContact() {
		return primaryContact;
	}
	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(ContactDO primaryContact) {
		this.primaryContact = primaryContact;
	}
	/**
	 * @return the secondaryContact
	 */
	public ContactDO getSecondaryContact() {
		return secondaryContact;
	}
	/**
	 * @param secondaryContact the secondaryContact to set
	 */
	public void setSecondaryContact(ContactDO secondaryContact) {
		this.secondaryContact = secondaryContact;
	}
	/**
	 * @return the officeMappedTO
	 */
	public OfficeDO getOfficeMappedTO() {
		return officeMappedTO;
	}
	/**
	 * @param officeMappedTO the officeMappedTO to set
	 */
	public void setOfficeMappedTO(OfficeDO officeMappedTO) {
		this.officeMappedTO = officeMappedTO;
	}
	/**
	 * @return the tanNo
	 */
	public String getTanNo() {
		return tanNo;
	}
	/**
	 * @param tanNo the tanNo to set
	 */
	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}
	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	/**
	 * @param currentStatus the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	/**
	 * @return the groupKey
	 */
	public CustomerGroupDO getGroupKey() {
		return groupKey;
	}
	/**
	 * @param groupKey the groupKey to set
	 */
	public void setGroupKey(CustomerGroupDO groupKey) {
		this.groupKey = groupKey;
	}
	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * @return the customerType
	 */
	public CustomerTypeDO getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(CustomerTypeDO customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the billingCycle
	 */
	public String getBillingCycle() {
		return billingCycle;
	}
	/**
	 * @param billingCycle the billingCycle to set
	 */
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	/**
	 * @return the paymentTerm
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}
	/**
	 * @param paymentTerm the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	/**
	 * @return the legacyCustCode
	 */
	public String getLegacyCustCode() {
		return legacyCustCode;
	}
	/**
	 * @param legacyCustCode the legacyCustCode to set
	 */
	public void setLegacyCustCode(String legacyCustCode) {
		this.legacyCustCode = legacyCustCode;
	}
	
}
