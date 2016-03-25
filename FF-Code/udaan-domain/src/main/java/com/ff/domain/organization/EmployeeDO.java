package com.ff.domain.organization;

import java.io.Serializable;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class EmployeeDO extends CGFactDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2151341813664839991L;
	private Integer employeeUserId;
	private Integer employeeId;
	private String empCode;
	private String firstName;
	private String lastName;
	private String empPhone;
	private String empFax;
	private Date dateOfJoin;
	private String designation;
	private String emailId;
	private String empType;
	private Integer city;
	private Integer officeId;
	private String empVirtual;
	private Integer departmentId;
	private String empStatus;

	// Employee reporting office id
	private Integer empOfficeRHOId;// dummy variables for Criteria purpose
	// Employee Hub office id
	private Integer empOfficeHUBId;// dummy variables for Criteria purpose

	public Integer getEmployeeUserId() {
		return employeeUserId;
	}

	public void setEmployeeUserId(Integer employeeUserId) {
		this.employeeUserId = employeeUserId;
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

	public String getEmpFax() {
		return empFax;
	}

	public void setEmpFax(String empFax) {
		this.empFax = empFax;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	

	/**
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	/**
	 * @return the empOfficeRHOId
	 */
	public Integer getEmpOfficeRHOId() {
		return empOfficeRHOId;
	}

	/**
	 * @return the empOfficeHUBId
	 */
	public Integer getEmpOfficeHUBId() {
		return empOfficeHUBId;
	}

	/**
	 * @param empOfficeRHOId
	 *            the empOfficeRHOId to set
	 */
	public void setEmpOfficeRHOId(Integer empOfficeRHOId) {
		this.empOfficeRHOId = empOfficeRHOId;
	}

	/**
	 * @param empOfficeHUBId
	 *            the empOfficeHUBId to set
	 */
	public void setEmpOfficeHUBId(Integer empOfficeHUBId) {
		this.empOfficeHUBId = empOfficeHUBId;
	}

	/**
	 * @return
	 * @param departmentId
	 *            the
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the empVirtual
	 */
	public String getEmpVirtual() {
		return empVirtual;
	}

	/**
	 * @param empVirtual
	 *            the empVirtual to set
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
