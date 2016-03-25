package com.ff.domain.organization;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class DepartmentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer departmentId;
	private String departmentName;
	private String departmentDescription;
	private String departmentCode;
	
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentDescription() {
		return departmentDescription;
	}
	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	
}
