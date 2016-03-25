/**
 * 
 */
package com.ff.leads;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class BranchTO extends CGBaseTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer branchId;
	
	private String branchName;

	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
