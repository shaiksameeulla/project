package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class CustomerVendorDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7079533275377138099L;
	private Integer custVendorId;
	private String custVendorCode;
	private String custVendorName;
	private String custVendorDesc;
	private String custVendorContactNum;
	private String status;
	public Integer getCustVendorId() {
		return custVendorId;
	}
	public void setCustVendorId(Integer custVendorId) {
		this.custVendorId = custVendorId;
	}
	public String getCustVendorCode() {
		return custVendorCode;
	}
	public void setCustVendorCode(String custVendorCode) {
		this.custVendorCode = custVendorCode;
	}
	public String getCustVendorName() {
		return custVendorName;
	}
	public void setCustVendorName(String custVendorName) {
		this.custVendorName = custVendorName;
	}
	public String getCustVendorDesc() {
		return custVendorDesc;
	}
	public void setCustVendorDesc(String custVendorDesc) {
		this.custVendorDesc = custVendorDesc;
	}
	public String getCustVendorContactNum() {
		return custVendorContactNum;
	}
	public void setCustVendorContactNum(String custVendorContactNum) {
		this.custVendorContactNum = custVendorContactNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
