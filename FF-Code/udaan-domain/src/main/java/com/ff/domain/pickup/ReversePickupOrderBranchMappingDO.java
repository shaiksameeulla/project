package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;

//
//  @ Project : FirstFlight
//  @ File Name : ReversePickupOrderBranchMappingDO.java
//  @ Date : 10/4/2012
//  @ Author : 
//
//

public class ReversePickupOrderBranchMappingDO extends CGFactDO {

	private static final long serialVersionUID = -4245459097506369297L;
	private Integer id;
	private String orderNo;
	private Integer orderAssignedBranch;
	private String branchOrderRequestStatus;
	@JsonBackReference
	private ReversePickupOrderDetailDO pickupOrderDetail;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the orderAssignedBranch
	 */
	public Integer getOrderAssignedBranch() {
		return orderAssignedBranch;
	}

	/**
	 * @param orderAssignedBranch
	 *            the orderAssignedBranch to set
	 */
	public void setOrderAssignedBranch(Integer orderAssignedBranch) {
		this.orderAssignedBranch = orderAssignedBranch;
	}

	/**
	 * @return the branchOrderRequestStatus
	 */
	public String getBranchOrderRequestStatus() {
		return branchOrderRequestStatus;
	}

	/**
	 * @param branchOrderRequestStatus
	 *            the branchOrderRequestStatus to set
	 */
	public void setBranchOrderRequestStatus(String branchOrderRequestStatus) {
		this.branchOrderRequestStatus = branchOrderRequestStatus;
	}

	/**
	 * @return the pickupOrderDetail
	 */
	public ReversePickupOrderDetailDO getPickupOrderDetail() {
		return pickupOrderDetail;
	}

	/**
	 * @param pickupOrderDetail
	 *            the pickupOrderDetail to set
	 */
	public void setPickupOrderDetail(
			ReversePickupOrderDetailDO pickupOrderDetail) {
		this.pickupOrderDetail = pickupOrderDetail;
	}

}
