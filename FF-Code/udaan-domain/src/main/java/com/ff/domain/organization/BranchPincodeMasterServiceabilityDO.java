package com.ff.domain.organization;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.geography.PincodeMasterDO;

public class BranchPincodeMasterServiceabilityDO extends CGFactDO {

	
	private Integer branchPincodeMasterId;
	private Integer officeId;
	private PincodeMasterDO pincodeId;
	private String status = "A";
	
	
	/**
	 * @return the branchPincodeMasterId
	 */
	public Integer getBranchPincodeMasterId() {
		return branchPincodeMasterId;
	}
	/**
	 * @param branchPincodeMasterId the branchPincodeMasterId to set
	 */
	public void setBranchPincodeMasterId(Integer branchPincodeMasterId) {
		this.branchPincodeMasterId = branchPincodeMasterId;
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
	public PincodeMasterDO getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(PincodeMasterDO pincodeId) {
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
