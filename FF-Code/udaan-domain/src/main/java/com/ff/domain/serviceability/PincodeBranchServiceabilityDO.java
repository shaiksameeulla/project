package com.ff.domain.serviceability;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.OfficeDO;

public class PincodeBranchServiceabilityDO extends CGFactDO {
	
	private static final long serialVersionUID = -2753299598766361116L;
	private Integer pincodeBranchServiceId;
	private OfficeDO office;
	private PincodeDO pincode;
	private Integer createdBy;
	private Integer updateBy;
	private Date updateDate;
	private Date creationDate;
	private String dtToBranch;
	private String status = "A";
	
	
	
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDtToBranch() {
		return dtToBranch;
	}
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OfficeDO getOffice() {
		return office;
	}
	public void setOffice(OfficeDO office) {
		this.office = office;
	}
	public PincodeDO getPincode() {
		return pincode;
	}
	public void setPincode(PincodeDO pincode) {
		this.pincode = pincode;
	}
	public Integer getPincodeBranchServiceId() {
		return pincodeBranchServiceId;
	}
	public void setPincodeBranchServiceId(Integer pincodeBranchServiceId) {
		this.pincodeBranchServiceId = pincodeBranchServiceId;
	}
	
}
