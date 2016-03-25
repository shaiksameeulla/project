package com.ff.domain.organization;

import java.io.Serializable;
import java.util.Date;

public class SAPEmployeeDO implements Serializable{
	//menction correct version UID
	//private static final long serialVersionUID = -5394680564484073080L;
	
	//private Integer employeeUserId;
	private Integer employeeId;
	private String empCode;
	private String firstName;
	private String lastName;
	private String empPhone;
	private String designation;
	private String empType;
	private Integer department;
	private Integer city;
	private Integer office;
	private String emailId;
	private Integer createdBy;
	private Date creationDate;
	private Integer updateBy;
	private Date updateDate;
	private String dtToBranch;
	private String employeeVirtual;
	private String empStatus;
	private String exception;
	
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getOffice() {
		return office;
	}
	public void setOffice(Integer office) {
		this.office = office;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public String getDtToBranch() {
		return dtToBranch;
	}
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	public String getEmployeeVirtual() {
		return employeeVirtual;
	}
	public void setEmployeeVirtual(String employeeVirtual) {
		this.employeeVirtual = employeeVirtual;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	
	
	
}