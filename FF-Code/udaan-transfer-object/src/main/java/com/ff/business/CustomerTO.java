package com.ff.business;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.to.ratemanagement.operations.ratequotation.ContactTO;
import com.ff.to.ratemanagement.operations.ratequotation.CustomerGroupTO;

public class CustomerTO extends CGBaseTO implements Comparable<CustomerTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4662662042782232128L;
	private Integer customerId;
	private String customerCode;
	private String mobile;
	private String status;
	private String email;
	private String city;
	private Integer mappedOffice;
	private String businessName;
	//private String customerType;
	private String firstName;
	private String lastName;
	private String phone;
	private CustomerTypeTO customerTypeTO;
/*Added from CustomerNew*/
	
	private String contractNo;
	private OfficeTO salesOffice;
	private EmployeeTO salesPerson;
	private String industryCategory;
	private String industryType;
	private String customerCategory;
	private PickupDeliveryAddressTO address;
	private ContactTO primaryContact;
	private ContactTO secondaryContact;
	private CustomerGroupTO groupKey;
	private String businessType;
	private Integer stateId;
	
	
	private String billingCycle;
	private String paymentTerm;
	private OfficeTO officeMappedTO;
	private String tanNo;
	private String panNo;
	private String distributionChannels;
	private String currentStatus;
	private Integer regionId;
	private Integer cityId;
	private PincodeTO pincodeTO;
	
	private String shippedToCode;
	private String legacyCustomerCode;
	
	//private Set<String> shipedToCodeList;
	/**/

	public String getMobile() {
		return mobile;
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

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getMappedOffice() {
		return mappedOffice;
	}

	public void setMappedOffice(Integer mappedOffice) {
		this.mappedOffice = mappedOffice;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CustomerTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.businessName)) {
			if (StringUtils.isNotEmpty(obj1.businessName)) {
				String compStr1 = this.businessName.toUpperCase();
				String compStr2 = obj1.businessName.toUpperCase();
				returnVal = compStr1.compareTo(compStr2);
			}else{
				returnVal = -1;
			}
		}
		return returnVal;
	}

	/**
	 * @return the customerTypeTO
	 */
	public CustomerTypeTO getCustomerTypeTO() {
		return customerTypeTO;
	}

	/**
	 * @param customerTypeTO the customerTypeTO to set
	 */
	public void setCustomerTypeTO(CustomerTypeTO customerTypeTO) {
		if(customerTypeTO==null){
			customerTypeTO = new CustomerTypeTO();
		}
		this.customerTypeTO = customerTypeTO;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
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

	public OfficeTO getOfficeMappedTO() {
		return officeMappedTO;
	}

	public void setOfficeMappedTO(OfficeTO officeMappedTO) {
		this.officeMappedTO = officeMappedTO;
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

	public String getDistributionChannels() {
		return distributionChannels;
	}

	public void setDistributionChannels(String distributionChannels) {
		this.distributionChannels = distributionChannels;
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
	 * @return the address
	 */
	public PickupDeliveryAddressTO getAddress() {
		if(address==null)
			address=new PickupDeliveryAddressTO();
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(PickupDeliveryAddressTO address) {
		this.address = address;
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
	 * @return the groupKey
	 */
	public CustomerGroupTO getGroupKey() {
		if(groupKey==null)
			groupKey=new CustomerGroupTO();
		return groupKey;
	}

	/**
	 * @param groupKey the groupKey to set
	 */
	public void setGroupKey(CustomerGroupTO groupKey) {
		this.groupKey = groupKey;
	}

	/**
	 * @return the billingCycle
	 */
	public String getBillingCycle() {
		return billingCycle;
	}

	/**
	 * @return the paymentTerm
	 */
	public String getPaymentTerm() {
		return paymentTerm;
	}

	/**
	 * @param billingCycle the billingCycle to set
	 */
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	/**
	 * @param paymentTerm the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	/**
	 * @return the pincodeTO
	 */
	public PincodeTO getPincodeTO() {
		if(pincodeTO == null){
			pincodeTO = new PincodeTO();
		}
		return pincodeTO;
	}

	/**
	 * @param pincodeTO the pincodeTO to set
	 */
	public void setPincodeTO(PincodeTO pincodeTO) {
		this.pincodeTO = pincodeTO;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		   if (obj == null) 
		          return false;
		   
		   CustomerTO cust = (CustomerTO)obj;
		      return this.customerId.equals(cust.getCustomerId()) && this.customerCode.equals(cust.getCustomerCode());
		
	}

	/**
	 * @return the legacyCustomerCode
	 */
	public String getLegacyCustomerCode() {
		return legacyCustomerCode;
	}

	/**
	 * @param legacyCustomerCode the legacyCustomerCode to set
	 */
	public void setLegacyCustomerCode(String legacyCustomerCode) {
		this.legacyCustomerCode = legacyCustomerCode;
	}

	
	//@Override
	/*public int hashCode() {
		// TODO Auto-generated method stub
		 final int prime = 31;
	        int result = 1;
	        result = prime * result
	                + ((this.customerId == null) ? 0 : this.customerCode.hashCode());
	        return result;
	}*/
	
}
