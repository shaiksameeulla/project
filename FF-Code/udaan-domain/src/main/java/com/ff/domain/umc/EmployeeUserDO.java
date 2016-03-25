package com.ff.domain.umc;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.EmployeeDO;

/**
 * @author nihsingh
 *
 */
public class EmployeeUserDO extends CGFactDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4545034549818085639L;
	private Integer empUserId;
	private Integer userId;
	private EmployeeDO empDO;
	
	/**
	 * @desc get EmpUserId
	 * @return empUserId
	 */
	public Integer getEmpUserId() {
		return empUserId;
	}
	
	
	/**
	 * @desc set EmpUserId
	 * @param empUserId
	 */
	public void setEmpUserId(Integer empUserId) {
		this.empUserId = empUserId;
	}
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @desc get EmpDO
	 * @return EmployeeDO
	 */
	public EmployeeDO getEmpDO() {
		return empDO;
	}
	
	
	/**
	 * @desc set EmpDO
	 * @param empDO
	 */
	public void setEmpDO(EmployeeDO empDO) {
		this.empDO = empDO;
	}

}
