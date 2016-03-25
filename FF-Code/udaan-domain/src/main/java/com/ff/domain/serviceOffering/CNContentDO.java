package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class CNContentDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4663558330819187483L;
	private Integer cnContentId;
	private String cnContentCode;
	private String cnContentName;
	private String cnContentDesc;
	private String status = "A";
	public Integer getCnContentId() {
		return cnContentId;
	}
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}
	public String getCnContentCode() {
		return cnContentCode;
	}
	public void setCnContentCode(String cnContentCode) {
		this.cnContentCode = cnContentCode;
	}
	public String getCnContentName() {
		return cnContentName;
	}
	public void setCnContentName(String cnContentName) {
		this.cnContentName = cnContentName;
	}
	public String getCnContentDesc() {
		return cnContentDesc;
	}
	public void setCnContentDesc(String cnContentDesc) {
		this.cnContentDesc = cnContentDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
