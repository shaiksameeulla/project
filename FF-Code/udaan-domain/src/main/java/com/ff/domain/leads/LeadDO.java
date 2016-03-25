
package com.ff.domain.leads;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.EmployeeUserDO;

public class LeadDO extends CGFactDO
{

	
	private static final long serialVersionUID = 1L;
	
	private Integer leadId;
	
	private String customerName;
	
	private String leadNumber;
	
	private String contactPerson;
	
	private Set<LeadCompetitorDO> competitorListDOs;
	
	private String phoneNoSTD;
	
	private String phoneNo;
	
	private String mobileNo;
	
	private String doorNoBuilding;
	
	private String street;
	
	private String location;
	
	private String city;
	
	private String pincode;
	
	private String designation;
	
	private String emailAddress;
	
	//private RateIndustryCategoryDO rateIndustryCategoryDO;
	private String industryCategoryCode;
	
	private String leadSourceCode;
	
	private String secondaryContact;
	
	private OfficeDO branchOfficeDO;
	
	private EmployeeUserDO assignedToEmployeeDO;
	
	private String leadStatusCode;
	
	private EmployeeDO createdByEmployeeDO;
	
	private Date createdDate;
	
	private EmployeeDO updatedByEmployeeDO;
	
	private Date updatedDate;
	
	private String businessType;

	public Integer getLeadId() {
		return leadId;
	}

	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLeadNumber() {
		return leadNumber;
	}

	public void setLeadNumber(String leadNumber) {
		this.leadNumber = leadNumber;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Set<LeadCompetitorDO> getCompetitorListDOs() {
		return competitorListDOs;
	}

	public void setCompetitorListDOs(Set<LeadCompetitorDO> competitorListDOs) {
		this.competitorListDOs = competitorListDOs;
	}

	public String getPhoneNoSTD() {
		return phoneNoSTD;
	}

	public void setPhoneNoSTD(String phoneNoSTD) {
		this.phoneNoSTD = phoneNoSTD;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDoorNoBuilding() {
		return doorNoBuilding;
	}

	public void setDoorNoBuilding(String doorNoBuilding) {
		this.doorNoBuilding = doorNoBuilding;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getIndustryCategoryCode() {
		return industryCategoryCode;
	}

	public void setIndustryCategoryCode(
			String industryCategoryCode) {
		this.industryCategoryCode = industryCategoryCode;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public OfficeDO getBranchOfficeDO() {
		return branchOfficeDO;
	}

	public void setBranchOfficeDO(OfficeDO branchOfficeDO) {
		this.branchOfficeDO = branchOfficeDO;
	}

	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EmployeeUserDO getAssignedToEmployeeDO() {
		return assignedToEmployeeDO;
	}

	public void setAssignedToEmployeeDO(EmployeeUserDO assignedToEmployeeDO) {
		this.assignedToEmployeeDO = assignedToEmployeeDO;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLeadSourceCode() {
		return leadSourceCode;
	}

	public void setLeadSourceCode(String leadSourceCode) {
		this.leadSourceCode = leadSourceCode;
	}

	public String getLeadStatusCode() {
		return leadStatusCode;
	}

	public void setLeadStatusCode(String leadStatusCode) {
		this.leadStatusCode = leadStatusCode;
	}

	/**
	 * @return the createdByEmployeeDO
	 */
	public EmployeeDO getCreatedByEmployeeDO() {
		return createdByEmployeeDO;
	}

	/**
	 * @param createdByEmployeeDO the createdByEmployeeDO to set
	 */
	public void setCreatedByEmployeeDO(EmployeeDO createdByEmployeeDO) {
		this.createdByEmployeeDO = createdByEmployeeDO;
	}

	/**
	 * @return the updatedByEmployeeDO
	 */
	public EmployeeDO getUpdatedByEmployeeDO() {
		return updatedByEmployeeDO;
	}

	/**
	 * @param updatedByEmployeeDO the updatedByEmployeeDO to set
	 */
	public void setUpdatedByEmployeeDO(EmployeeDO updatedByEmployeeDO) {
		this.updatedByEmployeeDO = updatedByEmployeeDO;
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

	

}
