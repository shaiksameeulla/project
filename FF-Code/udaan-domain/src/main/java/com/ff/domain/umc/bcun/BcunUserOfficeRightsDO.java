/**
 * 
 */
package com.ff.domain.umc.bcun;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author mohammes
 *
 */
public class BcunUserOfficeRightsDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6938605730015685019L;
	private Integer userRightappingId;
	private Integer userId;
	private Integer officeId;
	private String mappedTo;
	private String status;
	/**
	 * @return the userRightappingId
	 */
	public Integer getUserRightappingId() {
		return userRightappingId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @return the mappedTo
	 */
	public String getMappedTo() {
		return mappedTo;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param userRightappingId the userRightappingId to set
	 */
	public void setUserRightappingId(Integer userRightappingId) {
		this.userRightappingId = userRightappingId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @param mappedTo the mappedTo to set
	 */
	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
