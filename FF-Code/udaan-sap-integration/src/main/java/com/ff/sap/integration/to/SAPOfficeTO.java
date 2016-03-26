/**
 * 
 */
package com.ff.sap.integration.to;

/**
 * @author cbhure
 *
 */
public class SAPOfficeTO {
	
	private Integer officeId;
	private String officeCode;
	//Office Type will come from SAP and stored in database its ID by finding in Office Type table
	private Integer officeTypeId;
	private String officeType;
	private String officeName;
	private String address1;
	private String address2;
	private String address3;
	private String email;
	//Pin code SAP is sending, In CSD cross check in pin code table and store CityId in office
	private String pincode;
	private String reportingRHO;
	private String reportingHUB;
	private String city;
	private String phoneNo;
	private String mobileNo;
	
	
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
	 * @return the officeTypeId
	 */
	public Integer getOfficeTypeId() {
		return officeTypeId;
	}
	/**
	 * @param officeTypeId the officeTypeId to set
	 */
	public void setOfficeTypeId(Integer officeTypeId) {
		this.officeTypeId = officeTypeId;
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
	 * @return the officeType
	 */
	public String getOfficeType() {
		return officeType;
	}
	/**
	 * @param officeType the officeType to set
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	/**
	 * @return the reportingRHO
	 */
	public String getReportingRHO() {
		return reportingRHO;
	}
	/**
	 * @param reportingRHO the reportingRHO to set
	 */
	public void setReportingRHO(String reportingRHO) {
		this.reportingRHO = reportingRHO;
	}
	/**
	 * @return the reportingHUB
	 */
	public String getReportingHUB() {
		return reportingHUB;
	}
	/**
	 * @param reportingHUB the reportingHUB to set
	 */
	public void setReportingHUB(String reportingHUB) {
		this.reportingHUB = reportingHUB;
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
	
}
