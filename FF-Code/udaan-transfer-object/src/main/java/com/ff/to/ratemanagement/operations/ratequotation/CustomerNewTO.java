package com.ff.to.ratemanagement.operations.ratequotation;


import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.to.ratemanagement.masters.RateCustomerCategoryTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
/**
 * @author preegupt
 *
 */
public class CustomerNewTO  extends CGBaseTO{
	private Integer customerId;
	private Integer contractNo;
	private String customerCode;
	private OfficeTO salesOffice;
	private EmployeeTO salesPerson;
	private String industryCategory;
	private String industryType;
	private String customerCategory;
	private String businessName;
	private PickupDeliveryAddressTO address;
	private ContactTO primaryContact;
	private ContactTO secondaryContact;
	private CustomerGroupTO groupKey;
	private String businessType;
	private Integer stateId;
	
	
	private StockStandardTypeTO billingCycle;
	private StockStandardTypeTO paymentTerm;
	private OfficeTO officeMappedTO;
	private String tanNo;
	private String panNo;
	private String currentStatus;
	private String distributionChannels;
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
	 * @return the contractNo
	 */
	public Integer getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(Integer contractNo) {
		this.contractNo = contractNo;
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
	public OfficeTO getSalesOffice() {
		if(salesOffice==null)
			salesOffice=new OfficeTO();
		return salesOffice;
	}
	/**
	 * @param salesOffice the salesOffice to set
	 */
	public void setSalesOffice(OfficeTO salesOffice) {
		this.salesOffice = salesOffice;
	}
	/**
	 * @return the salesPerson
	 */
	public EmployeeTO getSalesPerson() {
		if(salesPerson==null)
			salesPerson=new EmployeeTO();
		return salesPerson;
	}
	/**
	 * @param salesPerson the salesPerson to set
	 */
	public void setSalesPerson(EmployeeTO salesPerson) {
		this.salesPerson = salesPerson;
	}
	/**
	 * @return the industryCategory
	 */
	public String getIndustryCategory() {
		
		return industryCategory;
	}
	/**
	 * @param industryCategory the industryCategory to set
	 */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
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
	public String getCustomerCategory() {
		
		return customerCategory;
	}
	/**
	 * @param customerCategory the customerCategory to set
	 */
	public void setCustomerCategory(String customerCategory) {
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
	 * @return the primaryContact
	 */
	public ContactTO getPrimaryContact() {
		if(primaryContact==null)
			primaryContact=new ContactTO();
		return primaryContact;
	}
	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(ContactTO primaryContact) {
		if(primaryContact==null)
			primaryContact=new ContactTO();
		this.primaryContact = primaryContact;
	}
	/**
	 * @return the secondaryContact
	 */
	public ContactTO getSecondaryContact() {
		if(secondaryContact==null)
			secondaryContact=new ContactTO();
		return secondaryContact;
	}
	/**
	 * @param secondaryContact the secondaryContact to set
	 */
	public void setSecondaryContact(ContactTO secondaryContact) {
		if(secondaryContact==null)
			secondaryContact=new ContactTO();
		this.secondaryContact = secondaryContact;
	}
	/**
	 * @return the billingCycle
	 */
	public StockStandardTypeTO getBillingCycle() {
		return billingCycle;
	}
	/**
	 * @param billingCycle the billingCycle to set
	 */
	public void setBillingCycle(StockStandardTypeTO billingCycle) {
		this.billingCycle = billingCycle;
	}
	/**
	 * @return the paymentTerm
	 */
	public StockStandardTypeTO getPaymentTerm() {
		return paymentTerm;
	}
	/**
	 * @param paymentTerm the paymentTerm to set
	 */
	public void setPaymentTerm(StockStandardTypeTO paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	/**
	 * @return the officeMappedTO
	 */
	public OfficeTO getOfficeMappedTO() {
		return officeMappedTO;
	}
	/**
	 * @param officeMappedTO the officeMappedTO to set
	 */
	public void setOfficeMappedTO(OfficeTO officeMappedTO) {
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
	public String getIndustryType() {
	
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public CustomerGroupTO getGroupKey() {
		if(groupKey==null)
			groupKey=new CustomerGroupTO();
		return groupKey;
	}
	public void setGroupKey(CustomerGroupTO groupKey) {
		this.groupKey = groupKey;
	}
	public PickupDeliveryAddressTO getAddress() {
		if(address==null)
			address=new PickupDeliveryAddressTO();
		return address;
	}
	public void setAddress(PickupDeliveryAddressTO address) {
		this.address = address;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	
}
