package com.ff.domain.organization;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class CSDSAPEmployeeDO extends CGFactDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5394680564484073080L;
	
	private Integer employeeUserId;
	private Integer employeeId;
	private String empCode;
	private String firstName;
	private String lastName;
	private String empPhone;
	/*private String empFax;
	private Date dateOfJoin;*/
	private String designation;
	private String emailId;
	private String empType;
	private Integer officeId;
	private String empVirtual;
	private Integer departmentId;
	private String empStatus;
	
	private Integer cty;

	/**
	 * @return the cty
	 */
	public Integer getCty() {
		return cty;
	}

	/**
	 * @param cty the cty to set
	 */
	public void setCty(Integer cty) {
		this.cty = cty;
	}

	/**
	 * @return the employeeUserId
	 */
	public Integer getEmployeeUserId() {
		return employeeUserId;
	}

	/**
	 * @param employeeUserId the employeeUserId to set
	 */
	public void setEmployeeUserId(Integer employeeUserId) {
		this.employeeUserId = employeeUserId;
	}

	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the empPhone
	 */
	public String getEmpPhone() {
		return empPhone;
	}

	/**
	 * @param empPhone the empPhone to set
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	/**
	 * @return the empFax
	 *//*
	public String getEmpFax() {
		return empFax;
	}

	*//**
	 * @param empFax the empFax to set
	 *//*
	public void setEmpFax(String empFax) {
		this.empFax = empFax;
	}

	*//**
	 * @return the dateOfJoin
	 *//*
	public Date getDateOfJoin() {
		return dateOfJoin;
	}

	*//**
	 * @param dateOfJoin the dateOfJoin to set
	 *//*
	public void setDateOfJoin(Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}*/

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
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the empType
	 */
	public String getEmpType() {
		return empType;
	}

	/**
	 * @param empType the empType to set
	 */
	public void setEmpType(String empType) {
		this.empType = empType;
	}

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
	 * @return the departmentId
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
