package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class UserRightsDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4444121294767467115L;
	@JsonManagedReference private UserDO userDO;
	private UserRolesDO roleDO;
	private Integer userRightsId;
	private String status;

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

	public UserRolesDO getRoleDO() {
		return roleDO;
	}

	public void setRoleDO(UserRolesDO roleDO) {
		this.roleDO = roleDO;
	}

	public Integer getUserRightsId() {
		return userRightsId;
	}

	public void setUserRightsId(Integer userRightsId) {
		this.userRightsId = userRightsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
