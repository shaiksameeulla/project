package com.ff.organization;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class EmployeeTO extends CGBaseTO implements Comparable<EmployeeTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6666435488978125217L;
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private Date dateOfJoin;
	private String designation;
	private String empCode;
	private String empType;
	private String gender;
	private Date dateOfBirth;
	private String street1;
	private String street2;
	private String street3;
	private String buildingName;
	private String label;
	private Integer value;
	private Integer regionId;
	private Integer cityId;
	private String empVirtual = "N";
	private String empStatus;
	

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	private String buildingBlock;
	private String addressType;
	private Integer officeId;
	private String emailId;
	private String empPhone;
	private String officeName;

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public Date getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet3() {
		return street3;
	}

	public void setStreet3(String street3) {
		this.street3 = street3;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingBlock() {
		return buildingBlock;
	}

	public void setBuildingBlock(String buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getEmailId() {
		return emailId;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
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

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	
	@Override
	public String toString() {
		return "EmployeeTO [employeeId=" + employeeId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", dateOfJoin="
				+ dateOfJoin + ", designation=" + designation + ", empCode="
				+ empCode + ", empType=" + empType + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", street1=" + street1
				+ ", street2=" + street2 + ", street3=" + street3
				+ ", buildingName=" + buildingName + ", label=" + label
				+ ", value=" + value + ", buildingBlock=" + buildingBlock
				+ ", addressType=" + addressType + ", officeId=" + officeId
				+ ", emailId=" + emailId + ", empPhone=" + empPhone + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(EmployeeTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.firstName)) {
			returnVal = this.firstName.compareTo(obj1.firstName);
		}
		return returnVal;
	}

	/**
	 * @return the empVirtual
	 */
	public String getEmpVirtual() {
		return empVirtual;
	}

	/**
	 * @param empVirtual the empVirtual to set
	 */
	public void setEmpVirtual(String empVirtual) {
		this.empVirtual = empVirtual;
	}

	/**
	 * @return the empStatus
	 */
	public String getEmpStatus() {
		return empStatus;
	}

	/**
	 * @param empStatus the empStatus to set
	 */
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	
	
}
