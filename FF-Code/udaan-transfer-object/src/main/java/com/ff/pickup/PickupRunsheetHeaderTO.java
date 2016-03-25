package com.ff.pickup;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class PickupRunsheetHeaderDO.
 */
public class PickupRunsheetHeaderTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3290348099021782004L;
	private Integer runsheetHeaderId;
	private String runsheetNo;
	private Integer assignmentHeaderId;
	private Integer createdAtOfficeId;
	private Integer createdForOfficeId;
	private Integer employeeFieldStaffId;
	private String employeeFieldStaffName;
	private String pickupAssignmentType; 
	private String runsheetAssignmentStatus;
	private Date runsheetDate;
	private String runsheetStatus;
	private List<PickupRunsheetDetailTO> runsheetDetails;
	private String assignmentDtlsIds;
	
	public Integer getRunsheetHeaderId() {
		return runsheetHeaderId;
	}
	public void setRunsheetHeaderId(Integer runsheetHeaderId) {
		this.runsheetHeaderId = runsheetHeaderId;
	}
	public String getRunsheetNo() {
		return runsheetNo;
	}
	public void setRunsheetNo(String runsheetNo) {
		this.runsheetNo = runsheetNo;
	}
	public Integer getAssignmentHeaderId() {
		return assignmentHeaderId;
	}
	public void setAssignmentHeaderId(Integer assignmentHeaderId) {
		this.assignmentHeaderId = assignmentHeaderId;
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
	public String getRunsheetAssignmentStatus() {
		return runsheetAssignmentStatus;
	}
	public void setRunsheetAssignmentStatus(String runsheetAssignmentStatus) {
		this.runsheetAssignmentStatus = runsheetAssignmentStatus;
	}
	public Date getRunsheetDate() {
		return runsheetDate;
	}
	public void setRunsheetDate(Date runsheetDate) {
		this.runsheetDate = runsheetDate;
	}
	public String getRunsheetStatus() {
		return runsheetStatus;
	}
	public void setRunsheetStatus(String runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}
	public List<PickupRunsheetDetailTO> getRunsheetDetails() {
		return runsheetDetails;
	}
	public void setRunsheetDetails(List<PickupRunsheetDetailTO> runsheetDetails) {
		this.runsheetDetails = runsheetDetails;
	}
	public String getAssignmentDtlsIds() {
		return assignmentDtlsIds;
	}
	public void setAssignmentDtlsIds(String assignmentDtlsIds) {
		this.assignmentDtlsIds = assignmentDtlsIds;
	}
	public String getPickupAssignmentType() {
		return pickupAssignmentType;
	}
	public void setPickupAssignmentType(String pickupAssignmentType) {
		this.pickupAssignmentType = pickupAssignmentType;
	}
	public String getEmployeeFieldStaffName() {
		return employeeFieldStaffName;
	}
	public void setEmployeeFieldStaffName(String employeeFieldStaffName) {
		this.employeeFieldStaffName = employeeFieldStaffName;
	}

}
