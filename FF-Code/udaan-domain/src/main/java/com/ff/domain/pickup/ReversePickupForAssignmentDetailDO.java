package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGBaseDO;

//
//  @ Project : FirstFlight
//  @ File Name : ReversePickupOrderDetailDO.java
//  @ Date : 10/4/2012
//  @ Author : 
//
//

public class ReversePickupForAssignmentDetailDO extends CGBaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4094826764157643912L;
	/**
	 * 
	 */
	private Integer detailId;
	private String orderNumber;
	
	private ReversePickupOrderHeaderDO pickupOrderHeader;
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public ReversePickupOrderHeaderDO getPickupOrderHeader() {
		return pickupOrderHeader;
	}
	public void setPickupOrderHeader(
			ReversePickupOrderHeaderDO pickupOrderHeader) {
		this.pickupOrderHeader = pickupOrderHeader;
	}
}
