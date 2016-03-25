package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunRunsheetReversePickupOrderDetailDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 670825347267415959L;
	private Integer detailId;
	private Integer headerId;
	private String orderNumber;
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
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getHeaderId() {
		return headerId;
	}
	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getConsignnorName() {
		return consignnorName;
	}
	public void setConsignnorName(String consignnorName) {
		this.consignnorName = consignnorName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAssignedBranch() {
		return assignedBranch;
	}
	public void setAssignedBranch(Integer assignedBranch) {
		this.assignedBranch = assignedBranch;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getConsignmentType() {
		return consignmentType;
	}
	public void setConsignmentType(Integer consignmentType) {
		this.consignmentType = consignmentType;
	}
	public Integer getMaterialVal() {
		return materialVal;
	}
	public void setMaterialVal(Integer materialVal) {
		this.materialVal = materialVal;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getInsuaranceRefNum() {
		return insuaranceRefNum;
	}
	public void setInsuaranceRefNum(String insuaranceRefNum) {
		this.insuaranceRefNum = insuaranceRefNum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDataTransferStatus() {
		return dataTransferStatus;
	}
	public void setDataTransferStatus(String dataTransferStatus) {
		this.dataTransferStatus = dataTransferStatus;
	}
	public String getOrderRequestStatus() {
		return orderRequestStatus;
	}
	public void setOrderRequestStatus(String orderRequestStatus) {
		this.orderRequestStatus = orderRequestStatus;
	}
}
