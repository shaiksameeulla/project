/**
 * 
 */
package com.ff.pickup;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author uchauhan
 *
 */
public class ReversePickupOrderBranchTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8544690265114201521L;
	
	private Integer id;
	  private String orderNo;
	  private Integer orderAssignedBranch;
	 private String branchOrderRequestStatus;
	 
	 
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param orderNo the orderNo to set
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
	 * @param orderAssignedBranch the orderAssignedBranch to set
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
	 * @param branchOrderRequestStatus the branchOrderRequestStatus to set
	 */
	public void setBranchOrderRequestStatus(String branchOrderRequestStatus) {
		this.branchOrderRequestStatus = branchOrderRequestStatus;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
