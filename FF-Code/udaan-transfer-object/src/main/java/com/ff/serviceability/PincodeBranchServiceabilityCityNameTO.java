package com.ff.serviceability;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;

public class PincodeBranchServiceabilityCityNameTO extends CGBaseTO {

	private static final long serialVersionUID = -2753299598766361116L;
	private OfficeTO officeTO;
	private CityTO cityTO;
	private String pincode;
	private Integer createdBy;
	private Integer updateBy;
	private Date updateDate;
	private Date creationDate;
	private String dtToBranch;
	private String status;
	private String cityName;
	private String stateName;
	private String regionName;
	private String officeName;
	private String address1;
	private String address2;
	private String phone;
	private String email;
	private String servStatus;
	
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	public CityTO getCityTO() {
		return cityTO;
	}
	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDtToBranch() {
		return dtToBranch;
	}
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the servStatus
	 */
	public String getServStatus() {
		return servStatus;
	}
	/**
	 * @param servStatus the servStatus to set
	 */
	public void setServStatus(String servStatus) {
		this.servStatus = servStatus;
	}
	
	
	
	
	
	
	
}
