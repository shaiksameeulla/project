package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;

public class PickupAssignmentDetailDO extends CGFactDO{
	/**
     * 
     */
    private static final long serialVersionUID = -930991074461922503L;
	private Integer pickupAssignmentDtlId;
	private Integer pickupAssignmentHeaderId;
	private PickupAssignmentHeaderDO pickupAssignmentHeader;//need to check need of this ??
	private String pickupType;
	private CustomerDO standardCustomer;
	private ReversePickupOrderDetailDO reversePickupCustomer; 
	public Integer getPickupAssignmentDtlId() {
		return pickupAssignmentDtlId;
	}
	public void setPickupAssignmentDtlId(Integer pickupAssignmentDtlId) {
		this.pickupAssignmentDtlId = pickupAssignmentDtlId;
	}
	public PickupAssignmentHeaderDO getPickupAssignmentHeader() {
		return pickupAssignmentHeader;
	}
	public void setPickupAssignmentHeader(
			PickupAssignmentHeaderDO pickupAssignmentHeader) {
		this.pickupAssignmentHeader = pickupAssignmentHeader;
	}
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public CustomerDO getCustomer() {
		return standardCustomer;
	}
	public void setCustomer(CustomerDO customer) {
		this.standardCustomer = customer;
	}
	public ReversePickupOrderDetailDO getRevPickupCustomerNo() {
		return reversePickupCustomer;
	}
	public void setRevPickupCustomerNo(
			ReversePickupOrderDetailDO revPickupCustomerNo) {
		this.reversePickupCustomer = revPickupCustomerNo;
	}
	public Integer getPickupAssignmentHeaderId() {
	    return pickupAssignmentHeaderId;
	}
	public void setPickupAssignmentHeaderId(Integer pickupAssignmentHeaderId) {
	    this.pickupAssignmentHeaderId = pickupAssignmentHeaderId;
	}
	
}
