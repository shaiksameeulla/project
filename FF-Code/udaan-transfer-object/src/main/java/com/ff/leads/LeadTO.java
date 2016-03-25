/**
 * 
 */
package com.ff.leads;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.EmployeeUserTO;
import com.ff.umc.UserInfoTO;

/**
 * @author abarudwa
 *
 */
public class LeadTO extends CGBaseTO implements Comparable<LeadTO>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer leadId;
	
	private String customerName;
	
	private String leadNumber;
	
	private Integer competitorId;
	
	private ArrayList<CompetitorListTO> competitorList;
	
	private String contactPerson;
	
	private String phoneNoSTD;
	
	private String phoneNo;
	
	private String mobileNo;
	
	private String doorNoBuilding;
	
	private String street;
	
	private String location;
	
	private String city;
	
	private String pincode;
	
	private String designation;
	
	private String contPersonDesig;
	
	private String emailAddress;
	
	private IndustryCategoryTO industryCategory;
	
	private String industryCategoryCode;
	
	private LeadSourceTO leadSource;
	
	private String leadSourceCode;
	
	private String secondaryContact;
	
	private BranchTO branchTO;
	
	private EmployeeUserTO assignedTo;
	
	private EmployeeTO createdBy;
	
	private EmployeeTO updatedBy;
	
	private LeadStatusTO status;
	
	private Integer loginOfficeId;//hidden
	
	private String loginOfficeCode;//hidden
	
	private String date;//hidden
	
	private String dateOfUpdate;//hidden
	
	private Integer userId;//hidden
	
	private OfficeTO officeTO;
	
	private String loginOfficeTypeCode;
	
	private Integer regionId;
	
	private Integer officeTypeId;
	private UserInfoTO userInfoTO;
	
	private String userRoles;
	private String userSaleExRole;
	private String smsPhoneNo;
	
	private String salesDesignation;
	private String alertMsg;
	private String successMsg;
	private String businessType;
	private String transMag;
	
	private String controlTeamRole;
	private String salesExecutiveRole;
	
	//sendEmail
	int count;
	private String salesExecutive;
	private String customer;
	private String sentTo;
	private String sentCc;
	private String[] to = new String[count];
	private String[] cc = new String[count];
	private String subject;
	private String description;
	
	//For UI 
	int rowCount;
	private Integer[] competitorIds = new Integer[rowCount];
	private String[] productCode = new String[rowCount];
	private String[] potential = new String[rowCount];
	private String[] expectedVolume = new String[rowCount];
	private String[] roles = new String[rowCount];
	private Integer[] leadCompetitorId = new Integer[rowCount];
	
	// Lead Generate Quotation Attribute
	private String quotCustName;
	private String quotLeadNumber;
	private String quotContactPerson;
	private String quotStdCode;
	private String quotPhoneNo;
	private String quotMobileNo;
	private String quotDoorNoBuildAddr;
	private String quotStreetRoadAddr;
	private String quotLocationAddr;
	private String quotCity;
	private String quotPincode;
	private String quotDesignation;
	private String quotEmail;
	private String quotIndustryCategoryCode;
	private String quotSecondaryContact;
	

	/**
	 * @return the leadCompetitorId
	 */
	public Integer[] getLeadCompetitorId() {
		return leadCompetitorId;
	}

	/**
	 * @param leadCompetitorId the leadCompetitorId to set
	 */
	public void setLeadCompetitorId(Integer[] leadCompetitorId) {
		this.leadCompetitorId = leadCompetitorId;
	}

	/**
	 * @return the salesDesignation
	 */
	public String getSalesDesignation() {
		return salesDesignation;
	}

	/**
	 * @param salesDesignation the salesDesignation to set
	 */
	public void setSalesDesignation(String salesDesignation) {
		this.salesDesignation = salesDesignation;
	}

	/**
	 * @return the smsPhoneNo
	 */
	public String getSmsPhoneNo() {
		return smsPhoneNo;
	}

	/**
	 * @param smsPhoneNo the smsPhoneNo to set
	 */
	public void setSmsPhoneNo(String smsPhoneNo) {
		this.smsPhoneNo = smsPhoneNo;
	}

	/**
	 * @return the sentCc
	 */
	public String getSentCc() {
		return sentCc;
	}

	/**
	 * @param sentCc the sentCc to set
	 */
	public void setSentCc(String sentCc) {
		this.sentCc = sentCc;
	}

	/**
	 * @return the sentTo
	 */
	public String getSentTo() {
		return sentTo;
	}

	/**
	 * @param sentTo the sentTo to set
	 */
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	/**
	 * @return the salesExecutive
	 */
	public String getSalesExecutive() {
		return salesExecutive;
	}

	/**
	 * @param salesExecutive the salesExecutive to set
	 */
	public void setSalesExecutive(String salesExecutive) {
		this.salesExecutive = salesExecutive;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}


	/**
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String[] getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roles
	 */
	public String[] getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	/**
	 * @return the userSaleExRole
	 */
	public String getUserSaleExRole() {
		return userSaleExRole;
	}

	/**
	 * @param userSaleExRole the userSaleExRole to set
	 */
	public void setUserSaleExRole(String userSaleExRole) {
		this.userSaleExRole = userSaleExRole;
	}

	/**
	 * @return the competitorIds
	 */
	public Integer[] getCompetitorIds() {
		return competitorIds;
	}

	/**
	 * @param competitorIds the competitorIds to set
	 */
	public void setCompetitorIds(Integer[] competitorIds) {
		this.competitorIds = competitorIds;
	}

	
	/**
	 * @return the productCode
	 */
	public String[] getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String[] productCode) {
		this.productCode = productCode;
	}

	

	/**
	 * @return the potential
	 */
	public String[] getPotential() {
		return potential;
	}

	/**
	 * @param potential the potential to set
	 */
	public void setPotential(String[] potential) {
		this.potential = potential;
	}

	/**
	 * @return the expectedVolume
	 */
	public String[] getExpectedVolume() {
		return expectedVolume;
	}

	/**
	 * @param expectedVolume the expectedVolume to set
	 */
	public void setExpectedVolume(String[] expectedVolume) {
		this.expectedVolume = expectedVolume;
	}

	/**
	 * @return the userRoles
	 */
	public String getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * @return the userInfoTO
	 */
	public UserInfoTO getUserInfoTO() {
		return userInfoTO;
	}

	/**
	 * @param userInfoTO the userInfoTO to set
	 */
	public void setUserInfoTO(UserInfoTO userInfoTO) {
		this.userInfoTO = userInfoTO;
	}

	public EmployeeUserTO getAssignedTo() {
		if(assignedTo == null)
			assignedTo = new EmployeeUserTO();
		return assignedTo;
	}

	public void setAssignedTo(EmployeeUserTO assignedTo) {
		this.assignedTo = assignedTo;
	}


	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Gets the region id.
	 *
	 * @return the region id
	 */
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * Sets the region id.
	 *
	 * @param regionId the new region id
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * Gets the office type id.
	 *
	 * @return the office type id
	 */
	public Integer getOfficeTypeId() {
		return officeTypeId;
	}

	/**
	 * Sets the office type id.
	 *
	 * @param officeTypeId the new office type id
	 */
	public void setOfficeTypeId(Integer officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the loginOfficeId
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}

	/**
	 * @param loginOfficeId the loginOfficeId to set
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}

	/**
	 * @return the loginOfficeCode
	 */
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}

	/**
	 * @param loginOfficeCode the loginOfficeCode to set
	 */
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}

	/**
	 * @return the competitorId
	 */
	public Integer getCompetitorId() {
		return competitorId;
	}

	/**
	 * @param competitorId the competitorId to set
	 */
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the leadNumber
	 */
	public String getLeadNumber() {
		return leadNumber;
	}

	/**
	 * @param leadNumber the leadNumber to set
	 */
	public void setLeadNumber(String leadNumber) {
		this.leadNumber = leadNumber;
	}

	/**
	 * @return the competitorList
	 */
	public ArrayList<CompetitorListTO> getCompetitorList() {
		return competitorList;
	}

	/**
	 * @param competitorList the competitorList to set
	 */
	public void setCompetitorList(ArrayList<CompetitorListTO> competitorList) {
		this.competitorList = competitorList;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the phoneNoSTD
	 */
	public String getPhoneNoSTD() {
		return phoneNoSTD;
	}

	/**
	 * @param phoneNoSTD the phoneNoSTD to set
	 */
	public void setPhoneNoSTD(String phoneNoSTD) {
		this.phoneNoSTD = phoneNoSTD;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the doorNoBuilding
	 */
	public String getDoorNoBuilding() {
		return doorNoBuilding;
	}

	/**
	 * @param doorNoBuilding the doorNoBuilding to set
	 */
	public void setDoorNoBuilding(String doorNoBuilding) {
		this.doorNoBuilding = doorNoBuilding;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the industryCategory
	 */
	public IndustryCategoryTO getIndustryCategory() {
		if(industryCategory == null)
			industryCategory = new IndustryCategoryTO();
		return industryCategory;
	}

	/**
	 * @param industryCategory the industryCategory to set
	 */
	public void setIndustryCategory(IndustryCategoryTO industryCategory) {
		this.industryCategory = industryCategory;
	}

	/**
	 * @return the leadSource
	 */
	public LeadSourceTO getLeadSource() {
		if(leadSource == null)
			leadSource = new LeadSourceTO();
		return leadSource;
	}

	/**
	 * @param leadSource the leadSource to set
	 */
	public void setLeadSource(LeadSourceTO leadSource) {
		this.leadSource = leadSource;
	}

	/**
	 * @return the secondaryContact
	 */
	public String getSecondaryContact() {
		return secondaryContact;
	}

	/**
	 * @param secondaryContact the secondaryContact to set
	 */
	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	/**
	 * @return the branch
	 */
	public BranchTO getBranch() {
		if(branchTO == null)
			branchTO = new BranchTO();
		return branchTO;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(BranchTO branch) {
		this.branchTO = branch;
	}

	/**
	 * @return the status
	 */
	public LeadStatusTO getStatus() {
		if(status == null)
			status = new LeadStatusTO();
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(LeadStatusTO status) {
		this.status = status;
	}

	public String getIndustryCategoryCode() {
		return industryCategoryCode;
	}

	public void setIndustryCategoryCode(String industryCategoryCode) {
		this.industryCategoryCode = industryCategoryCode;
	}

	public String getLeadSourceCode() {
		return leadSourceCode;
	}

	public void setLeadSourceCode(String leadSourceCode) {
		this.leadSourceCode = leadSourceCode;
	}

	public BranchTO getBranchTO() {
		return branchTO;
	}

	public void setBranchTO(BranchTO branchTO) {
		this.branchTO = branchTO;
	}


	/**
	 * @return the createdBy
	 */
	public EmployeeTO getCreatedBy() {
		if(createdBy == null)
			createdBy = new EmployeeTO();
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(EmployeeTO createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public EmployeeTO getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(EmployeeTO updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getLeadId() {
		return leadId;
	}

	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}

	/**
	 * @return the dateOfUpdate
	 */
	public String getDateOfUpdate() {
		return dateOfUpdate;
	}

	/**
	 * @param dateOfUpdate the dateOfUpdate to set
	 */
	public void setDateOfUpdate(String dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}

	public String getContPersonDesig() {
		return contPersonDesig;
	}

	public void setContPersonDesig(String contPersonDesig) {
		this.contPersonDesig = contPersonDesig;
	}

	/**
	 * @return the alertMsg
	 */
	public String getAlertMsg() {
		return alertMsg;
	}

	/**
	 * @param alertMsg the alertMsg to set
	 */
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	/**
	 * @return the successMsg
	 */
	public String getSuccessMsg() {
		return successMsg;
	}

	/**
	 * @param successMsg the successMsg to set
	 */
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getQuotCustName() {
		return quotCustName;
	}

	public void setQuotCustName(String quotCustName) {
		this.quotCustName = quotCustName;
	}

	public String getQuotLeadNumber() {
		return quotLeadNumber;
	}

	public void setQuotLeadNumber(String quotLeadNumber) {
		this.quotLeadNumber = quotLeadNumber;
	}

	public String getQuotContactPerson() {
		return quotContactPerson;
	}

	public void setQuotContactPerson(String quotContactPerson) {
		this.quotContactPerson = quotContactPerson;
	}

	public String getQuotStdCode() {
		return quotStdCode;
	}

	public void setQuotStdCode(String quotStdCode) {
		this.quotStdCode = quotStdCode;
	}

	public String getQuotPhoneNo() {
		return quotPhoneNo;
	}

	public void setQuotPhoneNo(String quotPhoneNo) {
		this.quotPhoneNo = quotPhoneNo;
	}

	public String getQuotMobileNo() {
		return quotMobileNo;
	}

	public void setQuotMobileNo(String quotMobileNo) {
		this.quotMobileNo = quotMobileNo;
	}

	public String getQuotDoorNoBuildAddr() {
		return quotDoorNoBuildAddr;
	}

	public void setQuotDoorNoBuildAddr(String quotDoorNoBuildAddr) {
		this.quotDoorNoBuildAddr = quotDoorNoBuildAddr;
	}

	public String getQuotStreetRoadAddr() {
		return quotStreetRoadAddr;
	}

	public void setQuotStreetRoadAddr(String quotStreetRoadAddr) {
		this.quotStreetRoadAddr = quotStreetRoadAddr;
	}

	public String getQuotLocationAddr() {
		return quotLocationAddr;
	}

	public void setQuotLocationAddr(String quotLocationAddr) {
		this.quotLocationAddr = quotLocationAddr;
	}

	public String getQuotCity() {
		return quotCity;
	}

	public void setQuotCity(String quotCity) {
		this.quotCity = quotCity;
	}

	public String getQuotPincode() {
		return quotPincode;
	}

	public void setQuotPincode(String quotPincode) {
		this.quotPincode = quotPincode;
	}

	public String getQuotDesignation() {
		return quotDesignation;
	}

	public void setQuotDesignation(String quotDesignation) {
		this.quotDesignation = quotDesignation;
	}

	public String getQuotEmail() {
		return quotEmail;
	}

	public void setQuotEmail(String quotEmail) {
		this.quotEmail = quotEmail;
	}

	public String getQuotIndustryCategoryCode() {
		return quotIndustryCategoryCode;
	}

	public void setQuotIndustryCategoryCode(String quotIndustryCategoryCode) {
		this.quotIndustryCategoryCode = quotIndustryCategoryCode;
	}

	public String getQuotSecondaryContact() {
		return quotSecondaryContact;
	}

	public void setQuotSecondaryContact(String quotSecondaryContact) {
		this.quotSecondaryContact = quotSecondaryContact;
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
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		if(officeTO == null)
			officeTO = new OfficeTO();
		return officeTO;
	}

	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}

	/**
	 * @return the loginOfficeTypeCode
	 */
	public String getLoginOfficeTypeCode() {
		return loginOfficeTypeCode;
	}

	/**
	 * @param loginOfficeTypeCode the loginOfficeTypeCode to set
	 */
	public void setLoginOfficeTypeCode(String loginOfficeTypeCode) {
		this.loginOfficeTypeCode = loginOfficeTypeCode;
	}

	/**
	 * @return the transMag
	 */
	public String getTransMag() {
		return transMag;
	}

	/**
	 * @param transMag the transMag to set
	 */
	public void setTransMag(String transMag) {
		this.transMag = transMag;
	}
	
	

	public String getControlTeamRole() {
		return controlTeamRole;
	}

	public void setControlTeamRole(String controlTeamRole) {
		this.controlTeamRole = controlTeamRole;
	}

	public String getSalesExecutiveRole() {
		return salesExecutiveRole;
	}

	public void setSalesExecutiveRole(String salesExecutiveRole) {
		this.salesExecutiveRole = salesExecutiveRole;
	}

	@Override
	public int compareTo(LeadTO obj) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.leadNumber)) {
			returnVal = this.leadNumber.compareTo(obj.leadNumber);
		}
		return returnVal;
	}
	
}
