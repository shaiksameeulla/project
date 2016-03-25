package com.ff.pickup;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;

public class RunsheetAssignmentTO extends CGBaseTO {
	/**
	 * @author hkansagr
	 * @date Friday, November 09, 2012
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer assignmentHeaderId;
	private Integer runsheetTypeId;
	private List<PickupAssignmentTypeTO> pickupAssignmentTypeTOs;
	private String assignmentType; // a hidden field in JSP, flag for type of
									// runsheet
	private String runsheetStatus;
	private String createdAt;
	private String createdFor;
	private OfficeTO createdAtBranch;
	private OfficeTO createdForBranch;
	private Integer createdForBranchId;
	private Integer createdAtBranchId;
	private List<LabelValueBean> branchTOs;
	private EmployeeTO employeeTO;
	private Integer employeeId;
	private List<EmployeeTO> employeeTOs;
	private List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs;
	private Boolean freshAssignment;
	private String assignmentDetails;
	private String dataTransferStatus;

	private Integer createdBy;
	private String createdDate;
	private Integer updatedBy;
	private String updatedDate;
	private boolean previouslyMapped;

	private String assignmentDetailIds;
	private String orderNumbers;
	private String customerCodes;
	private String currentSelected;
	private String previousSelected;
	// private String contractIds;
	// Added By Narasimha for Pickup req#2 dev
	private String pickupLocIds;
	private String revPickupIds;
	private String assignmentGenerated;
	private String radioButtonType;
	private Boolean isValidUser;
	public int rowCount;
	private Integer rowCustomerId[] = new Integer[rowCount];
	private Integer rowCustomerBranchId[] = new Integer[rowCount];
	private Integer rowAssignmentDetailId[] = new Integer[rowCount];
	// private Integer rowContractId[] = new Integer[rowCount];
	private Integer rowRevPickupHeaderId[] = new Integer[rowCount];
	private Integer rowRevPickupDtlId[] = new Integer[rowCount];
	private String rowOrderNumber[] = new String[rowCount];
	private String rowPickupType[] = new String[rowCount];
	// Added By Narasimha for Pickup req#2 dev
	private Integer rowPickupLocationId[] = new Integer[rowCount];

	private String assignmentStatusGenerated;
	private String assignmentStatusUnused;

	private List<PickupCustomerTO> customerList;
	//Save SUCCESS/FAILURE
	private boolean isSaved = Boolean.FALSE;
	
	//Two-way write
	private PickupTwoWayWriteTO pickupTwoWayWriteTO;
	
	public String getDataTransferStatus() {
		return dataTransferStatus;
	}

	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getAssignmentHeaderId() {
		return assignmentHeaderId;
	}

	public void setAssignmentHeaderId(Integer assignmentHeaderId) {
		this.assignmentHeaderId = assignmentHeaderId;
	}

	public String getAssignmentDetails() {
		return assignmentDetails;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentDetails(String assignmentDetails) {
		this.assignmentDetails = assignmentDetails;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Integer getRunsheetTypeId() {
		return runsheetTypeId;
	}

	public void setRunsheetTypeId(Integer runsheetTypeId) {
		this.runsheetTypeId = runsheetTypeId;
	}

	public String getRunsheetStatus() {
		return runsheetStatus;
	}

	public void setRunsheetStatus(String runsheetStatus) {
		this.runsheetStatus = runsheetStatus;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedFor() {
		return createdFor;
	}

	public void setCreatedFor(String createdFor) {
		this.createdFor = createdFor;
	}

	public OfficeTO getCreatedAtBranch() {
		if (createdAtBranch == null) {
			createdAtBranch = new OfficeTO();
			createdAtBranch.setOfficeTypeTO(new OfficeTypeTO());
		}
		return createdAtBranch;
	}

	public void setCreatedAtBranch(OfficeTO createdAtBranch) {
		this.createdAtBranch = createdAtBranch;
	}

	public OfficeTO getCreatedForBranch() {
		if (createdForBranch == null) {
			createdForBranch = new OfficeTO();
			createdForBranch.setOfficeTypeTO(new OfficeTypeTO());
		}
		return createdForBranch;
	}

	public void setCreatedForBranch(OfficeTO createdForBranch) {
		this.createdForBranch = createdForBranch;
	}

	public List<LabelValueBean> getBranchTOs() {
		return branchTOs;
	}

	public void setBranchTOs(List<LabelValueBean> branchTOs) {
		this.branchTOs = branchTOs;
	}

	public EmployeeTO getEmployeeTO() {
		if (employeeTO == null)
			employeeTO = new EmployeeTO();
		return employeeTO;
	}

	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}

	public List<RunsheetAssignmentDetailTO> getRunsheetAssignmentDetailTOs() {
		return runsheetAssignmentDetailTOs;
	}

	public void setRunsheetAssignmentDetailTOs(
			List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs) {
		this.runsheetAssignmentDetailTOs = runsheetAssignmentDetailTOs;
	}

	public Boolean getFreshAssignment() {
		return freshAssignment;
	}

	public void setFreshAssignment(Boolean freshAssignment) {
		this.freshAssignment = freshAssignment;
	}

	public List<PickupAssignmentTypeTO> getPickupAssignmentTypeTOs() {
		return pickupAssignmentTypeTOs;
	}

	public void setPickupAssignmentTypeTOs(
			List<PickupAssignmentTypeTO> pickupAssignmentTypeTOs) {
		this.pickupAssignmentTypeTOs = pickupAssignmentTypeTOs;
	}

	public List<EmployeeTO> getEmployeeTOs() {
		return employeeTOs;
	}

	public void setEmployeeTOs(List<EmployeeTO> employeeTOs) {
		this.employeeTOs = employeeTOs;
	}

	public boolean isPreviouslyMapped() {
		return previouslyMapped;
	}

	public void setPreviouslyMapped(boolean previouslyMapped) {
		this.previouslyMapped = previouslyMapped;
	}

	public String getAssignmentDetailIds() {
		return assignmentDetailIds;
	}

	public void setAssignmentDetailIds(String assignmentDetailIds) {
		this.assignmentDetailIds = assignmentDetailIds;
	}

	public String getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public String getCustomerCodes() {
		return customerCodes;
	}

	public void setCustomerCodes(String customerCodes) {
		this.customerCodes = customerCodes;
	}

	public String getCurrentSelected() {
		return currentSelected;
	}

	public void setCurrentSelected(String currentSelected) {
		this.currentSelected = currentSelected;
	}

	public String getPreviousSelected() {
		return previousSelected;
	}

	public void setPreviousSelected(String previousSelected) {
		this.previousSelected = previousSelected;
	}

	/**
	 * @return the pickupLocIds
	 */
	public String getPickupLocIds() {
		return pickupLocIds;
	}

	/**
	 * @param pickupLocIds
	 *            the pickupLocIds to set
	 */
	public void setPickupLocIds(String pickupLocIds) {
		this.pickupLocIds = pickupLocIds;
	}

	public String getRevPickupIds() {
		return revPickupIds;
	}

	public void setRevPickupIds(String revPickupIds) {
		this.revPickupIds = revPickupIds;
	}

	public String getAssignmentGenerated() {
		return assignmentGenerated;
	}

	public void setAssignmentGenerated(String assignmentGenerated) {
		this.assignmentGenerated = assignmentGenerated;
	}

	@Override
	public String toString() {
		return "RunsheetAssignmentTO [assignmentHeaderId=" + assignmentHeaderId
				+ ", runsheetTypeId=" + runsheetTypeId
				+ ", pickupAssignmentTypeTOs=" + pickupAssignmentTypeTOs
				+ ", assignmentType=" + assignmentType + ", runsheetStatus="
				+ runsheetStatus + ", createdAt=" + createdAt + ", createdFor="
				+ createdFor + ", createdAtBranch=" + createdAtBranch
				+ ", createdForBranch=" + createdForBranch + ", employeeTO="
				+ employeeTO + ", employeeTOs=" + employeeTOs
				+ ", runsheetAssignmentDetailTOs="
				+ runsheetAssignmentDetailTOs + ", freshAssignment="
				+ freshAssignment + ", assignmentDetails=" + assignmentDetails
				+ ", dataTransferStatus=" + dataTransferStatus + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	/**
	 * @return the radioButton
	 */
	/**
	 * @return the radioButtonType
	 */
	public String getRadioButtonType() {
		return radioButtonType;
	}

	/**
	 * @param radioButtonType
	 *            the radioButtonType to set
	 */
	public void setRadioButtonType(String radioButtonType) {
		this.radioButtonType = radioButtonType;
	}

	/**
	 * @return the createdForBranchId
	 */
	public Integer getCreatedForBranchId() {
		return createdForBranchId;
	}

	/**
	 * @param createdForBranchId
	 *            the createdForBranchId to set
	 */
	public void setCreatedForBranchId(Integer createdForBranchId) {
		this.createdForBranchId = createdForBranchId;
	}

	/**
	 * @return the createdAtBranchId
	 */
	public Integer getCreatedAtBranchId() {
		return createdAtBranchId;
	}

	/**
	 * @param createdAtBranchId
	 *            the createdAtBranchId to set
	 */
	public void setCreatedAtBranchId(Integer createdAtBranchId) {
		this.createdAtBranchId = createdAtBranchId;
	}

	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @return the rowCustomerId
	 */
	public Integer[] getRowCustomerId() {
		return rowCustomerId;
	}

	/**
	 * @return the rowCustomerBranchId
	 */
	public Integer[] getRowCustomerBranchId() {
		return rowCustomerBranchId;
	}

	/**
	 * @return the rowAssignmentDetailId
	 */
	public Integer[] getRowAssignmentDetailId() {
		return rowAssignmentDetailId;
	}

	/**
	 * @param rowCustomerId
	 *            the rowCustomerId to set
	 */
	public void setRowCustomerId(Integer[] rowCustomerId) {
		this.rowCustomerId = rowCustomerId;
	}

	/**
	 * @param rowCustomerBranchId
	 *            the rowCustomerBranchId to set
	 */
	public void setRowCustomerBranchId(Integer[] rowCustomerBranchId) {
		this.rowCustomerBranchId = rowCustomerBranchId;
	}

	/**
	 * @param rowAssignmentDetailId
	 *            the rowAssignmentDetailId to set
	 */
	public void setRowAssignmentDetailId(Integer[] rowAssignmentDetailId) {
		this.rowAssignmentDetailId = rowAssignmentDetailId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the rowRevPickupHeaderId
	 */
	public Integer[] getRowRevPickupHeaderId() {
		return rowRevPickupHeaderId;
	}

	/**
	 * @return the rowRevPickupDtlId
	 */
	public Integer[] getRowRevPickupDtlId() {
		return rowRevPickupDtlId;
	}

	/**
	 * @param rowRevPickupHeaderId
	 *            the rowRevPickupHeaderId to set
	 */
	public void setRowRevPickupHeaderId(Integer[] rowRevPickupHeaderId) {
		this.rowRevPickupHeaderId = rowRevPickupHeaderId;
	}

	/**
	 * @param rowRevPickupDtlId
	 *            the rowRevPickupDtlId to set
	 */
	public void setRowRevPickupDtlId(Integer[] rowRevPickupDtlId) {
		this.rowRevPickupDtlId = rowRevPickupDtlId;
	}

	/**
	 * @return the rowOrderNumber
	 */
	public String[] getRowOrderNumber() {
		return rowOrderNumber;
	}

	/**
	 * @param rowOrderNumber
	 *            the rowOrderNumber to set
	 */
	public void setRowOrderNumber(String[] rowOrderNumber) {
		this.rowOrderNumber = rowOrderNumber;
	}

	/**
	 * @return the rowPickupType
	 */
	public String[] getRowPickupType() {
		return rowPickupType;
	}

	/**
	 * @param rowPickupType
	 *            the rowPickupType to set
	 */
	public void setRowPickupType(String[] rowPickupType) {
		this.rowPickupType = rowPickupType;
	}

	/**
	 * @return the assignmentStatusGenerated
	 */
	public String getAssignmentStatusGenerated() {
		return assignmentStatusGenerated;
	}

	/**
	 * @return the assignmentStatusUnused
	 */
	public String getAssignmentStatusUnused() {
		return assignmentStatusUnused;
	}

	/**
	 * @param assignmentStatusGenerated
	 *            the assignmentStatusGenerated to set
	 */
	public void setAssignmentStatusGenerated(String assignmentStatusGenerated) {
		this.assignmentStatusGenerated = assignmentStatusGenerated;
	}

	/**
	 * @param assignmentStatusUnused
	 *            the assignmentStatusUnused to set
	 */
	public void setAssignmentStatusUnused(String assignmentStatusUnused) {
		this.assignmentStatusUnused = assignmentStatusUnused;
	}

	/**
	 * @return the isValidUser
	 */
	public Boolean getIsValidUser() {
		return isValidUser;
	}

	/**
	 * @param isValidUser
	 *            the isValidUser to set
	 */
	public void setIsValidUser(Boolean isValidUser) {
		this.isValidUser = isValidUser;
	}

	/**
	 * @return the rowPickupLocationId
	 */
	public Integer[] getRowPickupLocationId() {
		return rowPickupLocationId;
	}

	/**
	 * @param rowPickupLocationId
	 *            the rowPickupLocationId to set
	 */
	public void setRowPickupLocationId(Integer[] rowPickupLocationId) {
		this.rowPickupLocationId = rowPickupLocationId;
	}

	public List<PickupCustomerTO> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<PickupCustomerTO> customerList) {
		this.customerList = customerList;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public PickupTwoWayWriteTO getPickupTwoWayWriteTO() {
		return pickupTwoWayWriteTO;
	}

	public void setPickupTwoWayWriteTO(PickupTwoWayWriteTO pickupTwoWayWriteTO) {
		this.pickupTwoWayWriteTO = pickupTwoWayWriteTO;
	}

}
