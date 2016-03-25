package com.ff.domain.pickup;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BcunRunsheetAssignmentHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067252569900872982L;
	private Integer pickupAssignmentHeaderId;
	private Integer pickupAssignmentCreatedAt;
	private Integer pickupAssignmentCreatedFor;
	private Integer officeCreatedAt;
	private Integer officeCreatedFor;
	private Integer employeeFieldStaff;
	private Integer pickupAssignmentType;
	private String runsheetAssignmentStatus;
	private String dataTransferStatus;
	@JsonManagedReference
	private Set<BcunRunsheetAssignmentDetailDO> assignmentDetails;
	
	public Integer getPickupAssignmentHeaderId() {
		return pickupAssignmentHeaderId;
	}
	public void setPickupAssignmentHeaderId(Integer pickupAssignmentHeaderId) {
		this.pickupAssignmentHeaderId = pickupAssignmentHeaderId;
	}
	public Integer getOfficeCreatedAt() {
		return officeCreatedAt;
	}
	public void setOfficeCreatedAt(Integer officeCreatedAt) {
		this.officeCreatedAt = officeCreatedAt;
	}
	
	public Integer getOfficeCreatedFor() {
		return officeCreatedFor;
	}
	public void setOfficeCreatedFor(Integer officeCreatedFor) {
		this.officeCreatedFor = officeCreatedFor;
	}
	public Integer getEmployeeFieldStaff() {
		return employeeFieldStaff;
	}
	public void setEmployeeFieldStaff(Integer employeeFieldStaff) {
		this.employeeFieldStaff = employeeFieldStaff;
	}
	public Integer getPickupAssignmentCreatedAt() {
		return pickupAssignmentCreatedAt;
	}
	public void setPickupAssignmentCreatedAt(Integer pickupAssignmentCreatedAt) {
		this.pickupAssignmentCreatedAt = pickupAssignmentCreatedAt;
	}
	public Integer getPickupAssignmentCreatedFor() {
		return pickupAssignmentCreatedFor;
	}
	public void setPickupAssignmentCreatedFor(Integer pickupAssignmentCreatedFor) {
		this.pickupAssignmentCreatedFor = pickupAssignmentCreatedFor;
	}
	public Integer getPickupAssignmentType() {
		return pickupAssignmentType;
	}
	public void setPickupAssignmentType(Integer pickupAssignmentType) {
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
	public Set<BcunRunsheetAssignmentDetailDO> getAssignmentDetails() {
		return assignmentDetails;
	}
	public void setAssignmentDetails(
			Set<BcunRunsheetAssignmentDetailDO> assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}
	
}
