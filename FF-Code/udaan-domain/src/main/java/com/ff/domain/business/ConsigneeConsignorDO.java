package com.ff.domain.business;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author nkattung
 *
 */
public class ConsigneeConsignorDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1553390165697343467L;
	
	private Integer partyId;
	private String partyCode;
	private String firstName;
	private String lastName;
	private String partyType = "NA";
	private String phone;
	private String mobile;
	private String fax;
	private String businessName;
	private String email;
	private String address;
	private String relation;
	
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
	public void setPartyName(String partyType) {
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
	
}
