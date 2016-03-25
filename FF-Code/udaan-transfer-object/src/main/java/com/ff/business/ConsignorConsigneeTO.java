package com.ff.business;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nkattung
 * 
 */
public class ConsignorConsigneeTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8964266388036556951L;
	private Integer partyId;
	private String partyCode;
	private String firstName;
	private String lastName;
	private String partyType;
	private String phone;
	private String mobile;
	private String fax;
	private String businessName;
	private String email;
	private String address;
	private String relation;
	
	// FOR TRACKING
	private String destPincode;
	private String destCity;
	private String destState;
	
	
	private String orgPincode;
	private String orgCity;
	private String orgState;
	
	private Integer createdBy ;
	private Integer updatedBy;

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the destCity
	 */
	public String getDestCity() {
		return destCity;
	}

	/**
	 * @param destCity the destCity to set
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	/**
	 * @return the destState
	 */
	public String getDestState() {
		return destState;
	}

	/**
	 * @param destState the destState to set
	 */
	public void setDestState(String destState) {
		this.destState = destState;
	}

	/**
	 * @return the orgPincode
	 */
	public String getOrgPincode() {
		return orgPincode;
	}

	/**
	 * @param orgPincode the orgPincode to set
	 */
	public void setOrgPincode(String orgPincode) {
		this.orgPincode = orgPincode;
	}

	/**
	 * @return the orgCity
	 */
	public String getOrgCity() {
		return orgCity;
	}

	/**
	 * @param orgCity the orgCity to set
	 */
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	/**
	 * @return the orgState
	 */
	public String getOrgState() {
		return orgState;
	}

	/**
	 * @param orgState the orgState to set
	 */
	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	private String priorityDlvTime;

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
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

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
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

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPriorityDlvTime() {
		return priorityDlvTime;
	}

	public void setPriorityDlvTime(String priorityDlvTime) {
		this.priorityDlvTime = priorityDlvTime;
	}

	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * @return the destPincode
	 */
	public String getDestPincode() {
		return destPincode;
	}

	/**
	 * @param destPincode the destPincode to set
	 */
	public void setDestPincode(String destPincode) {
		this.destPincode = destPincode;
	}

	

}
