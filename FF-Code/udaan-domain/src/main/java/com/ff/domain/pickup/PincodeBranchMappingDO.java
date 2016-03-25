package com.ff.domain.pickup;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;


public class PincodeBranchMappingDO extends CGFactDO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7663385835775793965L;
	private Integer branchPincodeId;
	private Integer officeId;
	private Integer pincodeId;
	
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
