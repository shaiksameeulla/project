package com.ff.pickup;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RunsheetAssignmentDetailTO extends CGBaseTO implements
		Comparable<RunsheetAssignmentDetailTO> {
	/**
	 * @author hkansagr
	 * @date Friday, November 09, 2012
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer assignmentDetailId;
	private Integer assignmentHeaderId;
	private Integer customerId;
	private String customerCode;
	private String customerName;
	private Integer reversePickupOrderDetailId;
	private String reversePickupOrderNumber;
	private String consignerName;
	private String pickupType;
	private Boolean currentlyMapped;
	private Boolean previouslyMapped;
	private Integer createdBy;
	private Date createdDate;
	private Integer updatedBy;
	private Date updatedDate;
	private String value;
	private String label;
	private String pickupBranch;
	private Integer pickupBranchId;// dummy variables
	private String pickupBranchCode;// dummy variables
	private String pickupBranchName;// dummy variables
	private String status;// dummy variables
	//private Integer pickupDeliveryContractId;
	private Integer revPickupId;

	// Added By Narasimha for Pickup req#2 dev
	private String pickupLocation;
	private Integer pickupLocationId;
	
	//Added shippedToCode
	private String shippedToCode;

	public Integer getAssignmentHeaderId() {
		return assignmentHeaderId;
	}

	public void setAssignmentHeaderId(Integer assignmentHeaderId) {
		this.assignmentHeaderId = assignmentHeaderId;
	}

	public Date getCreatedDate() {
		if (this.createdDate == null)
			this.createdDate = new Date();
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Date getUpdatedDate() {
		if (this.updatedDate == null)
			this.updatedDate = new Date();
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getAssignmentDetailId() {
		return assignmentDetailId;
	}

	public void setAssignmentDetailId(Integer assignmentDetailId) {
		this.assignmentDetailId = assignmentDetailId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Boolean getCurrentlyMapped() {
		return currentlyMapped;
	}

	public void setCurrentlyMapped(Boolean currentlyMapped) {
		this.currentlyMapped = currentlyMapped;
	}

	public Boolean getPreviouslyMapped() {
		return previouslyMapped;
	}

	public void setPreviouslyMapped(Boolean previouslyMapped) {
		this.previouslyMapped = previouslyMapped;
	}

	public RunsheetAssignmentDetailTO() {

	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public Integer getReversePickupOrderDetailId() {
		return reversePickupOrderDetailId;
	}

	public void setReversePickupOrderDetailId(Integer reversePickupOrderDetailId) {
		this.reversePickupOrderDetailId = reversePickupOrderDetailId;
	}

	public String getReversePickupOrderNumber() {
		return reversePickupOrderNumber;
	}

	public void setReversePickupOrderNumber(String reversePickupOrderNumber) {
		this.reversePickupOrderNumber = reversePickupOrderNumber;
	}

	public String getConsignerName() {
		return consignerName;
	}

	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPickupBranch() {
		return pickupBranch;
	}

	public void setPickupBranch(String pickupBranch) {
		this.pickupBranch = pickupBranch;
	}

	/*public Integer getPickupDeliveryContractId() {
		return pickupDeliveryContractId;
	}

	public void setPickupDeliveryContractId(Integer pickupDeliveryContractId) {
		this.pickupDeliveryContractId = pickupDeliveryContractId;
	}*/

	public Integer getRevPickupId() {
		return revPickupId;
	}

	public void setRevPickupId(Integer revPickupId) {
		this.revPickupId = revPickupId;
	}

	@Override
	public String toString() {
		return "RunsheetAssignmentDetailTO [assignmentDetailId="
				+ assignmentDetailId + ", assignmentHeaderId="
				+ assignmentHeaderId + ", customerId=" + customerId
				+ ", customerCode=" + customerCode + ", customerName="
				+ customerName + ", reversePickupOrderDetailId="
				+ reversePickupOrderDetailId + ", reversePickupOrderNumber="
				+ reversePickupOrderNumber + ", consignerName=" + consignerName
				+ ", pickupType=" + pickupType + ", currentlyMapped="
				+ currentlyMapped + ", previouslyMapped=" + previouslyMapped
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ "]";
	}

	@Override
	public int compareTo(RunsheetAssignmentDetailTO d) {
		int returnVal = 0;
		int compareVal = 0;
		/*if (previouslyMapped == null|| d.previouslyMapped ==null || pickupType == null || d.pickupType == null ||customerName==null || d.customerName ==null ){
			returnVal = 1;
		}else*/ if (this.previouslyMapped == d.previouslyMapped) {
			compareVal = d.pickupType.compareTo(this.pickupType);
			returnVal = compareVal;
			if (compareVal == -1) {
				returnVal = 1;//show RL customers in prior
			}else if(compareVal == 0){				
				String compStr1 = "";
				String compStr2 = "";
				if (StringUtils.isNotEmpty(this.customerName)) {
					compStr1 = this.customerName.toUpperCase();
				}
				if (StringUtils.isNotEmpty(d.customerName)) {
					compStr2 = d.customerName.toUpperCase();
				}
				returnVal = compStr1.compareTo(compStr2);
			}
		} else if (this.previouslyMapped == null
				|| (!this.previouslyMapped)) {
			returnVal = 1;
		} else {
			returnVal = -1;
		}
		return returnVal;
	}

	/**
	 * @return the pickupBranchId
	 */
	public Integer getPickupBranchId() {
		return pickupBranchId;
	}

	/**
	 * @param pickupBranchId
	 *            the pickupBranchId to set
	 */
	public void setPickupBranchId(Integer pickupBranchId) {
		this.pickupBranchId = pickupBranchId;
	}

	/**
	 * @return the pickupBranchCode
	 */
	public String getPickupBranchCode() {
		return pickupBranchCode;
	}

	/**
	 * @return the pickupBranchName
	 */
	public String getPickupBranchName() {
		return pickupBranchName;
	}

	/**
	 * @param pickupBranchCode
	 *            the pickupBranchCode to set
	 */
	public void setPickupBranchCode(String pickupBranchCode) {
		this.pickupBranchCode = pickupBranchCode;
	}

	/**
	 * @param pickupBranchName
	 *            the pickupBranchName to set
	 */
	public void setPickupBranchName(String pickupBranchName) {
		this.pickupBranchName = pickupBranchName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the pickupLocation
	 */
	public String getPickupLocation() {
		return pickupLocation;
	}

	/**
	 * @param pickupLocation
	 *            the pickupLocation to set
	 */
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	/**
	 * @return the pickupLocationId
	 */
	public Integer getPickupLocationId() {
		return pickupLocationId;
	}

	/**
	 * @param pickupLocationId
	 *            the pickupLocationId to set
	 */
	public void setPickupLocationId(Integer pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	public String getShippedToCode() {
		return shippedToCode;
	}

	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}
}
