package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class ApplRightsDO extends CGFactDO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5954498420783693296L;
	private Integer rightsId;
	/*private String rightsCode;
	private String rightsName;*/
	private ApplScreenDO appscreenTypeDO;
	@JsonBackReference private UserRolesDO rolesTypeDO;
	
	private String status;
	
	public Integer getRightsId() {
		return rightsId;
	}
	public void setRightsId(Integer rightsId) {
		this.rightsId = rightsId;
	}
	/*public String getRightsCode() {
		return rightsCode;
	}
	public void setRightsCode(String rightsCode) {
		this.rightsCode = rightsCode;
	}
	public String getRightsName() {
		return rightsName;
	}
	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}*/
	
	public ApplScreenDO getAppscreenTypeDO() {
		return appscreenTypeDO;
	}
	public void setAppscreenTypeDO(ApplScreenDO appscreenTypeDO) {
		this.appscreenTypeDO = appscreenTypeDO;
	}
	public UserRolesDO getRolesTypeDO() {
		return rolesTypeDO;
	}
	public void setRolesTypeDO(UserRolesDO rolesTypeDO) {
		this.rolesTypeDO = rolesTypeDO;
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
