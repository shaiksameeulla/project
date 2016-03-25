package com.ff.domain.organization;

import com.capgemini.lbs.framework.domain.CGFactDO;

/*
 * description: com.ff.domain.pickup.PincodeBranchMappingDO renamed as com.ff.domain.organization.BranchPincodeServiceabilityDO
 * this is meant for branch mapped pincodes.
 * so considered as organization silo related DO
 * */
public class BranchPincodeServiceabilityDO extends CGFactDO{

	private static final long serialVersionUID = -7663385835775793965L;
	
	private Integer branchPincodeId;
	private Integer officeId;
	private Integer pincodeId;
	private String status = "A";
	
	
	/**
	 * @return the branchPincodeId
	 */
	public Integer getBranchPincodeId() {
		return branchPincodeId;
	}
	/**
	 * @param branchPincodeId the branchPincodeId to set
	 */
	public void setBranchPincodeId(Integer branchPincodeId) {
		this.branchPincodeId = branchPincodeId;
	}
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
