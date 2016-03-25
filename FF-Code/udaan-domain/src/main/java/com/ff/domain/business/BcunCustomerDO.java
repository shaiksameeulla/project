package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.pickup.BcunAddressDO;
import com.ff.domain.ratemanagement.masters.BcunContactDO;


public class BcunCustomerDO extends CGFactDO {

	private static final long serialVersionUID = 3947419811702177546L;
	private Integer customerId;
    private String customerCode;
    private String businessName;
    private String firstName;
    private String lastName;
    private String phone;
    private String mobile;
    private String fax;
    private String status;
    private String email;
    private String description;
    private String city;
    
    /*Added fo customer new changes*/
    private Integer salesOfficeDO;
	private Integer salesPersonDO;
	private Integer industryCategoryDO;
	private Integer industryTypeDO;
	private String distributionChannels;
	private Integer customerCategoryDO;
	//private Integer addressDO;
	private BcunAddressDO addressDO;
	//private Integer primaryContactDO;
	//private Integer secondaryContactDO;
	private BcunContactDO primaryContactDO;
	private BcunContactDO secondaryContactDO;
	private Integer officeMappedDO;
	private String tanNo;
	private String panNo;
	private Integer groupKeyDO;
	private String businessType;
	private Integer customerType;
	private String billingCycle;
	private String paymentTerm;
	private String contractNo;
    
    /*Ends here*/


	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getSalesOfficeDO() {
		return salesOfficeDO;
	}

	public void setSalesOfficeDO(Integer salesOfficeDO) {
		this.salesOfficeDO = salesOfficeDO;
	}

	public Integer getSalesPersonDO() {
		return salesPersonDO;
	}

	public void setSalesPersonDO(Integer salesPersonDO) {
		this.salesPersonDO = salesPersonDO;
	}

	public Integer getIndustryCategoryDO() {
		return industryCategoryDO;
	}

	public void setIndustryCategoryDO(Integer industryCategoryDO) {
		this.industryCategoryDO = industryCategoryDO;
	}

	public Integer getIndustryTypeDO() {
		return industryTypeDO;
	}

	public void setIndustryTypeDO(Integer industryTypeDO) {
		this.industryTypeDO = industryTypeDO;
	}

	public String getDistributionChannels() {
		return distributionChannels;
	}

	public void setDistributionChannels(String distributionChannels) {
		this.distributionChannels = distributionChannels;
	}

	public Integer getCustomerCategoryDO() {
		return customerCategoryDO;
	}

	public void setCustomerCategoryDO(Integer customerCategoryDO) {
		this.customerCategoryDO = customerCategoryDO;
	}

	public BcunAddressDO getAddressDO() {
		return addressDO;
	}

	public void setAddressDO(BcunAddressDO addressDO) {
		this.addressDO = addressDO;
	}

	public BcunContactDO getPrimaryContactDO() {
		return primaryContactDO;
	}

	public void setPrimaryContactDO(BcunContactDO primaryContactDO) {
		this.primaryContactDO = primaryContactDO;
	}

	public BcunContactDO getSecondaryContactDO() {
		return secondaryContactDO;
	}

	public void setSecondaryContactDO(BcunContactDO secondaryContactDO) {
		this.secondaryContactDO = secondaryContactDO;
	}

	public Integer getOfficeMappedDO() {
		return officeMappedDO;
	}

	public void setOfficeMappedDO(Integer officeMappedDO) {
		this.officeMappedDO = officeMappedDO;
	}

	public String getTanNo() {
		return tanNo;
	}

	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Integer getGroupKeyDO() {
		return groupKeyDO;
	}

	public void setGroupKeyDO(Integer groupKeyDO) {
		this.groupKeyDO = groupKeyDO;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
    
}
