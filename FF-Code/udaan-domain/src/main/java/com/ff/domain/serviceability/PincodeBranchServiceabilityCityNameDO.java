package com.ff.domain.serviceability;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;

public class PincodeBranchServiceabilityCityNameDO extends CGFactDO{
	private static final long serialVersionUID = -2753299598766361116L;
	private Integer pincodeBranchServiceId;
	private OfficeDO office;
	private String pincode;
	private CityDO city;
	private Integer createdBy;
	private Integer updateBy;
	private Date updateDate;
	private Date creationDate;
	private String dtToBranch;
	private String status = "A";
	private String servStatus;
	private String cityName;
	private String stateName;
	private String regionName;
	private String officeName;
	private String address1;
	private String address2;
	private String phone;
	private String email;
	
	public Integer getPincodeBranchServiceId() {
		return pincodeBranchServiceId;
	}
	public void setPincodeBranchServiceId(Integer pincodeBranchServiceId) {
		this.pincodeBranchServiceId = pincodeBranchServiceId;
	}
	public OfficeDO getOffice() {
		return office;
	}
	public void setOffice(OfficeDO office) {
		this.office = office;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public CityDO getCity() {
		return city;
	}
	public void setCity(CityDO city) {
		this.city = city;
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
}
