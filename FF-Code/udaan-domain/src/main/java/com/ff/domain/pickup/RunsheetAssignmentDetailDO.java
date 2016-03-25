package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class RunsheetAssignmentDetailDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8761008740641088709L;
	private Integer assignmentDetailId;

	private RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO;
	// private RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO;
	private String pickupType;
	/* private CustomerDO standardCustomer; */
	private ReversePickupOrderDetailDO revPickupRequestDetail;
	//TODO Delete
	//private PickupDeliveryContractDO deliveryContract;
	// Added By Narasimha for Pickup req#2 dev
	private PickupDeliveryLocationDO pickupDlvLocation;
	private String mappedStatus;

	public RunsheetAssignmentDetailDO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getAssignmentDetailId() {
		return assignmentDetailId;
	}

	public void setAssignmentDetailId(Integer assignmentDetailId) {
		this.assignmentDetailId = assignmentDetailId;
	}

	public RunsheetAssignmentHeaderDO getRunsheetAssignmentHeaderDO() {
		return runsheetAssignmentHeaderDO;
	}

	public void setRunsheetAssignmentHeaderDO(
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO) {
		this.runsheetAssignmentHeaderDO = runsheetAssignmentHeaderDO;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	/*
	 * public CustomerDO getStandardCustomer() { return standardCustomer; }
	 * public void setStandardCustomer(CustomerDO standardCustomer) {
	 * this.standardCustomer = standardCustomer; }
	 */
	public ReversePickupOrderDetailDO getRevPickupRequestDetail() {
		return revPickupRequestDetail;
	}

	public void setRevPickupRequestDetail(
			ReversePickupOrderDetailDO revPickupRequestDetail) {
		this.revPickupRequestDetail = revPickupRequestDetail;
	}

	/*public PickupDeliveryContractDO getDeliveryContract() {
		return deliveryContract;
	}

	public void setDeliveryContract(PickupDeliveryContractDO deliveryContract) {
		this.deliveryContract = deliveryContract;
	}*/

	public String getMappedStatus() {
		return mappedStatus;
	}

	public void setMappedStatus(String mappedStatus) {
		this.mappedStatus = mappedStatus;
	}

	/**
	 * @return the pickupDlvLocation
	 */
	public PickupDeliveryLocationDO getPickupDlvLocation() {
		return pickupDlvLocation;
	}

	/**
	 * @param pickupDlvLocation
	 *            the pickupDlvLocation to set
	 */
	public void setPickupDlvLocation(PickupDeliveryLocationDO pickupDlvLocation) {
		this.pickupDlvLocation = pickupDlvLocation;
	}

}
