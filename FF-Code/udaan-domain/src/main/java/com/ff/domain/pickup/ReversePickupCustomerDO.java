package com.ff.domain.pickup;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;

//
//  @ Project : FirstFlight
//  @ File Name : ReversePickupCustomerDO.java
//  @ Date : 10/4/2012
//  @ Author : 
//
//

public class ReversePickupCustomerDO extends CGFactDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5804910164038240212L;
	private Integer assignmentTypeId;
	private String assignmentTypeCode;
	private String assignmentTypeDescription;
	
	public Integer getAssignmentTypeId() {
		return assignmentTypeId;
	}
	public void setAssignmentTypeId(Integer assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}
	public String getAssignmentTypeCode() {
		return assignmentTypeCode;
	}
	public void setAssignmentTypeCode(String assignmentTypeCode) {
		this.assignmentTypeCode = assignmentTypeCode;
	}
	public String getAssignmentTypeDescription() {
		return assignmentTypeDescription;
	}
	public void setAssignmentTypeDescription(String assignmentTypeDescription) {
		this.assignmentTypeDescription = assignmentTypeDescription;
	}
	
}
