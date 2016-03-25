package com.ff.domain.pickup;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.organization.OfficeDO;

//
//  @ Project : FirstFlight
//  @ File Name : ReversePickupOrderDetailDO.java
//  @ Date : 10/4/2012
//  @ Author : 
//
//

public class ReversePickupOrderDetailDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 376623771209479893L;
	private Integer detailId;
	private Integer headerId;
	private String orderNumber;
//	private Integer customerNo;
	private String consignnorName;
	private String address;
	private Integer city;
	private Integer pincode;
	private String telephone;
	private String mobile;
	private Integer assignedBranch;
	private String email;
	private Integer consignmentType;
	private Integer materialVal;
	private String materialDesc;
	private String insuaranceRefNum;
	private String remarks;
	private String dataTransferStatus;
	private String orderRequestStatus="P";
	@JsonManagedReference
	private Set<ReversePickupOrderBranchMappingDO> branchesAssignedDO;
	private List<OfficeDO> lstassignmentBranchDO;
	@JsonBackReference
	private ReversePickupOrderHeaderDO pickupOrderHeader;

	/**
	 * @return the detailId
	 */
	public Integer getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId
	 *            the detailId to set
	 */
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return the headerId
	 */
	public Integer getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the customerNo
	 */
	/*public Integer getCustomerNo() {
		return customerNo;
	}

	*//**
	 * @param customerNo
	 *            the customerNo to set
	 *//*
	public void setCustomerNo(Integer customerNo) {
		this.customerNo = customerNo;
	}*/

	/**
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * @return the pincode
	 */
	public Integer getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the consignmentType
	 */
	public Integer getConsignmentType() {
		return consignmentType;
	}

	/**
	 * @return the consignnorName
	 */
	public String getConsignnorName() {
		return consignnorName;
	}

	/**
	 * @param consignnorName
	 *            the consignnorName to set
	 */
	public void setConsignnorName(String consignnorName) {
		this.consignnorName = consignnorName;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the assignedBranch
	 */
	public Integer getAssignedBranch() {
		return assignedBranch;
	}

	/**
	 * @param assignedBranch
	 *            the assignedBranch to set
	 */
	public void setAssignedBranch(Integer assignedBranch) {
		this.assignedBranch = assignedBranch;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the materialVal
	 */
	public Integer getMaterialVal() {
		return materialVal;
	}

	/**
	 * @param materialVal
	 *            the materialVal to set
	 */
	public void setMaterialVal(Integer materialVal) {
		this.materialVal = materialVal;
	}

	/**
	 * @return the orderRequestStatus
	 */
	public String getOrderRequestStatus() {
		return orderRequestStatus;
	}

	/**
	 * @param orderRequestStatus
	 *            the orderRequestStatus to set
	 */
	public void setOrderRequestStatus(String orderRequestStatus) {
		this.orderRequestStatus = orderRequestStatus;
	}

	/**
	 * @param consignmentType
	 *            the consignmentType to set
	 */
	public void setConsignmentType(Integer consignmentType) {
		this.consignmentType = consignmentType;
	}

	/**
	 * @return the materialDesc
	 */
	public String getMaterialDesc() {
		return materialDesc;
	}

	/**
	 * @param materialDesc
	 *            the materialDesc to set
	 */
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	/**
	 * @return the insuaranceRefNum
	 */
	public String getInsuaranceRefNum() {
		return insuaranceRefNum;
	}

	/**
	 * @param insuaranceRefNum
	 *            the insuaranceRefNum to set
	 */
	public void setInsuaranceRefNum(String insuaranceRefNum) {
		this.insuaranceRefNum = insuaranceRefNum;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the dataTransferStatus
	 */
	public String getDataTransferStatus() {
		return dataTransferStatus;
	}

	/**
	 * @param dataTransferStatus
	 *            the dataTransferStatus to set
	 */
	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}

	/**
	 * @return the pickupOrderHeader
	 */
	public ReversePickupOrderHeaderDO getPickupOrderHeader() {
		return pickupOrderHeader;
	}

	/**
	 * @param pickupOrderHeader
	 *            the pickupOrderHeader to set
	 */
	public void setPickupOrderHeader(
			ReversePickupOrderHeaderDO pickupOrderHeader) {
		this.pickupOrderHeader = pickupOrderHeader;
	}

	/**
	 * @return the lstassignmentBranchDO
	 */
	public List<OfficeDO> getLstassignmentBranchDO() {
		return lstassignmentBranchDO;
	}

	/**
	 * @param lstassignmentBranchDO
	 *            the lstassignmentBranchDO to set
	 */
	public void setLstassignmentBranchDO(List<OfficeDO> lstassignmentBranchDO) {
		this.lstassignmentBranchDO = lstassignmentBranchDO;
	}

	/**
	 * @return the branchesAssignedDO
	 */
	public Set<ReversePickupOrderBranchMappingDO> getBranchesAssignedDO() {
		return branchesAssignedDO;
	}

	/**
	 * @param branchesAssignedDO
	 *            the branchesAssignedDO to set
	 */
	public void setBranchesAssignedDO(
			Set<ReversePickupOrderBranchMappingDO> branchesAssignedDO) {
		this.branchesAssignedDO = branchesAssignedDO;
	}
}
