package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BcunRunsheetAssignmentDetailDO extends CGFactDO{
	/**
     * 
     */
    private static final long serialVersionUID = -930991074461922503L;
	private Integer pickupAssignmentDtlId;	
	private String pickupType;
	private String mappedStatus;
	@JsonBackReference
	private BcunRunsheetAssignmentHeaderDO pickupAssignmentHeader;
	private ReversePickupForAssignmentDetailDO reversePickupCustomer;
	private Integer pickupDlvLocation;
	
	public Integer getPickupAssignmentDtlId() {
		return pickupAssignmentDtlId;
	}
	public void setPickupAssignmentDtlId(Integer pickupAssignmentDtlId) {
		this.pickupAssignmentDtlId = pickupAssignmentDtlId;
	}
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getMappedStatus() {
		return mappedStatus;
	}
	public void setMappedStatus(String mappedStatus) {
		this.mappedStatus = mappedStatus;
	}
	public BcunRunsheetAssignmentHeaderDO getPickupAssignmentHeader() {
		return pickupAssignmentHeader;
	}
	public void setPickupAssignmentHeader(
			BcunRunsheetAssignmentHeaderDO pickupAssignmentHeader) {
		this.pickupAssignmentHeader = pickupAssignmentHeader;
	}	

	public ReversePickupForAssignmentDetailDO getReversePickupCustomer() {
		return reversePickupCustomer;
	}
	public void setReversePickupCustomer(
			ReversePickupForAssignmentDetailDO reversePickupCustomer) {
		this.reversePickupCustomer = reversePickupCustomer;
	}
	
	public Integer getPickupDlvLocation() {
		return pickupDlvLocation;
	}
	public void setPickupDlvLocation(Integer pickupDlvLocation) {
		this.pickupDlvLocation = pickupDlvLocation;
	}

}
