package com.ff.domain.umc;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author nihsingh
 *
 */
public class BcunEmployeeUserDO extends CGFactDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4545034549818085639L;
	private Integer empUserId;
	private Integer userId;
	private EmployeeDO employee;
	
	public Integer getEmpUserId() {
		return empUserId;
	}
	public void setEmpUserId(Integer empUserId) {
		this.empUserId = empUserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public EmployeeDO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDO employee) {
		this.employee = employee;
	}
	
	
}
