package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class PickupAssignmentTypeDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6583319462764240250L;
	private Integer assignmentTypeId;
	private String assignmentTypeCode;
	private String assignmentTypeDescription;
	
	public PickupAssignmentTypeDO() {
		
	}

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
