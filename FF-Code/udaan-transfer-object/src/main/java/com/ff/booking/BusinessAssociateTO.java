package com.ff.booking;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 *	@author Added by Narasimha
 * 
 */

public class BusinessAssociateTO extends CGBaseTO 
{
	private static final long serialVersionUID = 1L;

	private Integer baId;
	private String baCode;
	private String firstName;
	private String lastName;
	private	String phone;
	private String mobile;
	private String fax;
	private String businessName;
	private String email;
	private OfficeTO officeMappedTo;
	private String created_By;
	private Date creationDate;
	private String updateBy;
	private Date updateDate;
	
	
	
	
	
	public String getBaCode() {
		return baCode;
	}
	public void setBaCode(String baCode) {
		this.baCode = baCode;
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
	public OfficeTO getOfficeMappedTo() {
		return officeMappedTo;
	}
	public void setOfficeMappedTo(OfficeTO officeMappedTo) {
		this.officeMappedTo = officeMappedTo;
	}
	public String getCreated_By() {
		return created_By;
	}
	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public Integer getBaId() {
		return baId;
	}
	public void setBaId(Integer baId) {
		this.baId = baId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
