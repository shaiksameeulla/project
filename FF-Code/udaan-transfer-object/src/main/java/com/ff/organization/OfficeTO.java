package com.ff.organization;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.RegionTO;

public class OfficeTO extends CGBaseTO implements Comparable<OfficeTO> {
 /**
	 * 
	 */
	private static final long serialVersionUID = 5080221486246772430L;
private Integer officeId;
 private String officeCode;
 private String officeName;
 private OfficeTypeTO officeTypeTO;
 private String address1;
 private String address2;
 private String address3;
 private String buildingName;
 private String buildingBlock;
 private String inChargeName;
 private String phone;
 private String email;
 private String fax;
 private Integer reportingRHO;
 private Integer reportingHUB;
 private String officeOpeningTime;
 private String officeClosingTime;
 private String weeklyHoliday;
 private Integer cityId;
 private Integer areaId;
 private RegionTO regionTO;
 private Integer pincodeId;
 private String cityName;
 private String pincode;
 private String mobileNo;
 
 
 
 
 /**
 * @return the cityName
 */
public String getCityName() {
	return cityName;
}
/**
 * @param cityName the cityName to set
 */
public void setCityName(String cityName) {
	this.cityName = cityName;
}
private Boolean isExcludeOfficeId;
/**
 * @return the officeId
 */
public Integer getOfficeId() {
	return officeId;
}
/**
 * @param officeId the officeId to set
 */
public void setOfficeId(Integer officeId) {
	this.officeId = officeId;
}
/**
 * @return the officeCode
 */
public String getOfficeCode() {
	return officeCode;
}
/**
 * @param officeCode the officeCode to set
 */
public void setOfficeCode(String officeCode) {
	this.officeCode = officeCode;
}
/**
 * @return the officeName
 */
public String getOfficeName() {
	return officeName;
}
/**
 * @param officeName the officeName to set
 */
public void setOfficeName(String officeName) {
	this.officeName = officeName;
}
/**
 * @return the officeTypeTO
 */
public OfficeTypeTO getOfficeTypeTO() {
	return officeTypeTO;
}
/**
 * @param officeTypeTO the officeTypeTO to set
 */
public void setOfficeTypeTO(OfficeTypeTO officeTypeTO) {
	this.officeTypeTO = officeTypeTO;
}
/**
 * @return the address1
 */
public String getAddress1() {
	return address1;
}
/**
 * @param address1 the address1 to set
 */
public void setAddress1(String address1) {
	this.address1 = address1;
}
/**
 * @return the address2
 */
public String getAddress2() {
	return address2;
}
/**
 * @param address2 the address2 to set
 */
public void setAddress2(String address2) {
	this.address2 = address2;
}
/**
 * @return the address3
 */
public String getAddress3() {
	return address3;
}
/**
 * @param address3 the address3 to set
 */
public void setAddress3(String address3) {
	this.address3 = address3;
}
/**
 * @return the buildingName
 */
public String getBuildingName() {
	return buildingName;
}
/**
 * @param buildingName the buildingName to set
 */
public void setBuildingName(String buildingName) {
	this.buildingName = buildingName;
}
/**
 * @return the buildingBlock
 */
public String getBuildingBlock() {
	return buildingBlock;
}
/**
 * @param buildingBlock the buildingBlock to set
 */
public void setBuildingBlock(String buildingBlock) {
	this.buildingBlock = buildingBlock;
}
/**
 * @return the inChargeName
 */
public String getInChargeName() {
	return inChargeName;
}
/**
 * @param inChargeName the inChargeName to set
 */
public void setInChargeName(String inChargeName) {
	this.inChargeName = inChargeName;
}
/**
 * @return the phone
 */
public String getPhone() {
	return phone;
}
/**
 * @param phone the phone to set
 */
public void setPhone(String phone) {
	this.phone = phone;
}
/**
 * @return the email
 */
public String getEmail() {
	return email;
}
/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * @return the fax
 */
public String getFax() {
	return fax;
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
 * @param fax the fax to set
 */
public void setFax(String fax) {
	this.fax = fax;
}
/**
 * @return the reportingRHO
 */
public Integer getReportingRHO() {
	return reportingRHO;
}
/**
 * @param reportingRHO the reportingRHO to set
 */
public void setReportingRHO(Integer reportingRHO) {
	this.reportingRHO = reportingRHO;
}
/**
 * @return the reportingHUB
 */
public Integer getReportingHUB() {
	return reportingHUB;
}
/**
 * @param reportingHUB the reportingHUB to set
 */
public void setReportingHUB(Integer reportingHUB) {
	this.reportingHUB = reportingHUB;
}
/**
 * @return the officeOpeningTime
 */
public String getOfficeOpeningTime() {
	return officeOpeningTime;
}
/**
 * @param officeOpeningTime the officeOpeningTime to set
 */
public void setOfficeOpeningTime(String officeOpeningTime) {
	this.officeOpeningTime = officeOpeningTime;
}
/**
 * @return the officeClosingTime
 */
public String getOfficeClosingTime() {
	return officeClosingTime;
}
/**
 * @param officeClosingTime the officeClosingTime to set
 */
public void setOfficeClosingTime(String officeClosingTime) {
	this.officeClosingTime = officeClosingTime;
}
/**
 * @return the weeklyHoliday
 */
public String getWeeklyHoliday() {
	return weeklyHoliday;
}
/**
 * @param weeklyHoliday the weeklyHoliday to set
 */
public void setWeeklyHoliday(String weeklyHoliday) {
	this.weeklyHoliday = weeklyHoliday;
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
 * @return the areaId
 */
public Integer getAreaId() {
	return areaId;
}
/**
 * @param areaId the areaId to set
 */
public void setAreaId(Integer areaId) {
	this.areaId = areaId;
}
public RegionTO getRegionTO() {
	return regionTO;
}
public void setRegionTO(RegionTO regionTO) {
	this.regionTO = regionTO;
}
public Boolean getIsExcludeOfficeId() {
	return isExcludeOfficeId;
}
public void setIsExcludeOfficeId(Boolean isExcludeOfficeId) {
	this.isExcludeOfficeId = isExcludeOfficeId;
}
/**
 * @see java.lang.Object#toString()
 * Nov 27, 2012
 * @return
 * toString
 * com.ff.organization.OfficeTO
 * kgajare
 */
@Override
public String toString() {
    return "OfficeTO [officeId=" + officeId + ", officeCode=" + officeCode
	    + ", officeName=" + officeName + ", officeTypeTO=" + officeTypeTO
	    + ", address1=" + address1 + ", address2=" + address2
	    + ", address3=" + address3 + ", buildingName=" + buildingName
	    + ", buildingBlock=" + buildingBlock + ", inChargeName="
	    + inChargeName + ", phone=" + phone + ", email=" + email + ", fax="
	    + fax + ", reportingRHO=" + reportingRHO + ", reportingHUB="
	    + reportingHUB + ", officeOpeningTime=" + officeOpeningTime
	    + ", officeClosingTime=" + officeClosingTime + ", weeklyHoliday="
	    + weeklyHoliday + ", cityId=" + cityId + ", areaId=" + areaId + "]";
}
/**
 * @return the pincodeId
 */
public Integer getPincodeId() {
	return pincodeId;
}
/**
 * @param pincodeId the pincodeId to set
 */
public void setPincodeId(Integer pincodeId) {
	this.pincodeId = pincodeId;
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(OfficeTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.officeName)) {
			returnVal = this.officeName.compareTo(obj1.officeName);
		}
		return returnVal;
	}
 
}
