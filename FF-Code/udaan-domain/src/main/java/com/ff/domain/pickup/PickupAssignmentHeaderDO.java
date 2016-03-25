package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class PickupAssignmentHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067252569900872982L;
	private Integer pickupAssignmentHeaderId;
	private String pickupAssignmentCreatedAt;
	private String pickupAssignmentCreatedFor;
	private Integer officeCreatedAt;
	private Integer officeCreatedFor;
	private Integer employeeFieldStaff;
	private PickupAssignmentTypeDO pickupAssignmentTypeDO;
	
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
	public String getPickupAssignmentCreatedAt() {
	    return pickupAssignmentCreatedAt;
	}
	public void setPickupAssignmentCreatedAt(String pickupAssignmentCreatedAt) {
	    this.pickupAssignmentCreatedAt = pickupAssignmentCreatedAt;
	}
	public String getPickupAssignmentCreatedFor() {
	    return pickupAssignmentCreatedFor;
	}
	public void setPickupAssignmentCreatedFor(String pickupAssignmentCreatedFor) {
	    this.pickupAssignmentCreatedFor = pickupAssignmentCreatedFor;
	}
	public PickupAssignmentTypeDO getPickupAssignmentTypeDO() {
	    return pickupAssignmentTypeDO;
	}
	public void setPickupAssignmentTypeDO(
		PickupAssignmentTypeDO pickupAssignmentTypeDO) {
	    this.pickupAssignmentTypeDO = pickupAssignmentTypeDO;
	}

	
	
}
