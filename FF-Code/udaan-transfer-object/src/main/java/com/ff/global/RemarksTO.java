/**
 * 
 */
package com.ff.global;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 *
 */
public class RemarksTO extends CGBaseTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1057790637854327879L;
	/** Remark Id */
	private Integer remarkId;
	/** Remark Type */
	private String remarkType;
	/** Description */
	private String description;
	/** Record Status */
	private String status;
	/**
	 * @return the remarkId
	 */
	public Integer getRemarkId() {
		return remarkId;
	}
	/**
	 * @param remarkId the remarkId to set
	 */
	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}
	/**
	 * @return the remarkType
	 */
	public String getRemarkType() {
		return remarkType;
	}
	/**
	 * @param remarkType the remarkType to set
	 */
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
