package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class BcunUserRightsDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4444121294767467115L;
	
	
	private Integer userRightsId;
	private Integer userId;
	private BcunUserRolesDO roleDO;
	private Integer roleId;
	private String status;
	
	public Integer getUserRightsId() {
		return userRightsId;
	}
	public void setUserRightsId(Integer userRightsId) {
		this.userRightsId = userRightsId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BcunUserRolesDO getRoleDO() {
		return roleDO;
	}
	public void setRoleDO(BcunUserRolesDO roleDO) {
		this.roleDO = roleDO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
