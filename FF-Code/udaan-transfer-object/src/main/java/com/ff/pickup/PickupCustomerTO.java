/**
 * 
 */
package com.ff.pickup;

import com.ff.business.CustomerTO;

/**
 * @author mohammes
 * 
 */
public class PickupCustomerTO extends CustomerTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7614093623517542584L;
	private Integer detailId;// reverse pickup detail id
	private Integer headerId;// reverse pickup Header id
	private String orderNumber;// reverse pickup Order
	// private Integer contractId;
	private String pickupType;// R - Reverse; S - Standard
	private Integer assignmentDetailId;// assignmentDetailsId

	private Integer pickupLocationId;
	private String pickupLocation;

	/**
	 * @return the detailId
	 */
	public Integer getDetailId() {
		return detailId;
	}

	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param detailId
	 *            the detailId to set
	 */
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the contractId
	 */
	/*
	 * public Integer getContractId() { return contractId; }
	 *//**
	 * @param contractId
	 *            the contractId to set
	 */
	/*
	 * public void setContractId(Integer contractId) { this.contractId =
	 * contractId; }
	 */

	/**
	 * @return the pickupType
	 */
	public String getPickupType() {
		return pickupType;
	}

	/**
	 * @param pickupType
	 *            the pickupType to set
	 */
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	/**
	 * @return the assignmentDetailId
	 */
	public Integer getAssignmentDetailId() {
		return assignmentDetailId;
	}

	/**
	 * @param assignmentDetailId
	 *            the assignmentDetailId to set
	 */
	public void setAssignmentDetailId(Integer assignmentDetailId) {
		this.assignmentDetailId = assignmentDetailId;
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

}
