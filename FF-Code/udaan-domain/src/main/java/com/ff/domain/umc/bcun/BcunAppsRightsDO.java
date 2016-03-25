package com.ff.domain.umc.bcun;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunAppsRightsDO extends CGFactDO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5954498420783693296L;
	private Integer rightsId;
	
	private Integer roleId;
	private Integer screenId;
	/**
	 * @return the rightsId
	 */
	public Integer getRightsId() {
		return rightsId;
	}
	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * @return the screenId
	 */
	public Integer getScreenId() {
		return screenId;
	}
	/**
	 * @param rightsId the rightsId to set
	 */
	public void setRightsId(Integer rightsId) {
		this.rightsId = rightsId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}
	
	
	
	


}
