package com.ff.domain.pickup;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.OfficeTypeDO;

public class RunsheetAssignmentHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5051639934332429687L;

	/**
	 * 
	 */
	/*
	 * public RunsheetAssignmentHeaderDO() { setPickupAssignmentType(new
	 * PickupAssignmentTypeDO()); setAssignmentCreatedAtOfficeType(new
	 * OfficeTypeDO()); setAssignmentCreatedForOfficeType(new OfficeTypeDO());
	 * setAssignmentDetails(new HashSet<RunsheetAssignmentDetailDO>()); }
	 */
	private Integer assignmentHeaderId;
	/*
	 * private String assignmentCreatedAt; private String assignmentCreatedFor;
	 */
	private OfficeTypeDO assignmentCreatedAtOfficeType;
	private OfficeTypeDO assignmentCreatedForOfficeType;
	private Integer createdAtOfficeId;
	private Integer createdForOfficeId;
	private Integer employeeFieldStaffId;
	private PickupAssignmentTypeDO pickupAssignmentType; // Association to be
															// created 1:1
	private String runsheetAssignmentStatus;
	private String dataTransferStatus;
	private Set<RunsheetAssignmentDetailDO> assignmentDetails;

	// private Set<RunsheetAssignmentDetailDO> assignmentDetails;

	public Integer getAssignmentHeaderId() {
		return assignmentHeaderId;
	}

	public void setAssignmentHeaderId(Integer assignmentHeaderId) {
		this.assignmentHeaderId = assignmentHeaderId;
	}

	public OfficeTypeDO getAssignmentCreatedAtOfficeType() {
		return assignmentCreatedAtOfficeType;
	}

	public void setAssignmentCreatedAtOfficeType(
			OfficeTypeDO assignmentCreatedAtOfficeType) {
		this.assignmentCreatedAtOfficeType = assignmentCreatedAtOfficeType;
	}

	public OfficeTypeDO getAssignmentCreatedForOfficeType() {
		return assignmentCreatedForOfficeType;
	}

	public void setAssignmentCreatedForOfficeType(
			OfficeTypeDO assignmentCreatedForOfficeType) {
		this.assignmentCreatedForOfficeType = assignmentCreatedForOfficeType;
	}

	public Integer getCreatedAtOfficeId() {
		return createdAtOfficeId;
	}

	public void setCreatedAtOfficeId(Integer createdAtOfficeId) {
		this.createdAtOfficeId = createdAtOfficeId;
	}

	public Integer getCreatedForOfficeId() {
		return createdForOfficeId;
	}

	public void setCreatedForOfficeId(Integer createdForOfficeId) {
		this.createdForOfficeId = createdForOfficeId;
	}

	public Integer getEmployeeFieldStaffId() {
		return employeeFieldStaffId;
	}

	public void setEmployeeFieldStaffId(Integer employeeFieldStaffId) {
		this.employeeFieldStaffId = employeeFieldStaffId;
	}

	public PickupAssignmentTypeDO getPickupAssignmentType() {
		return pickupAssignmentType;
	}

	public void setPickupAssignmentType(
			PickupAssignmentTypeDO pickupAssignmentType) {
		this.pickupAssignmentType = pickupAssignmentType;
	}

	public String getRunsheetAssignmentStatus() {
		return runsheetAssignmentStatus;
	}

	public void setRunsheetAssignmentStatus(String runsheetAssignmentStatus) {
		this.runsheetAssignmentStatus = runsheetAssignmentStatus;
	}

	public String getDataTransferStatus() {
		return dataTransferStatus;
	}

	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}

	public Set<RunsheetAssignmentDetailDO> getAssignmentDetails() {
		return assignmentDetails;
	}

	public void setAssignmentDetails(
			Set<RunsheetAssignmentDetailDO> assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

}
