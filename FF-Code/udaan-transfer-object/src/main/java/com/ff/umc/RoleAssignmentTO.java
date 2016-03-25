package com.ff.umc;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class RoleAssignmentTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7677859071544033059L;
	private Integer roleIds;
	private Integer assignRoleIds;
	private String roleCode;
	private String roleName;
	private Integer userId;
	private String userCode;
	private String userName;
	private String roleType;
	private Integer officeIds;
	private Integer mappedOfficeIds;
	private String officeCode;
	private String officeName;
	private String assignedUserRoleIds;
	private Integer loginUserId;
	private String assignedOfficeIds;
	private String mappingTO;
	private Integer userCityId;

	public Integer getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getOfficeIds() {
		return officeIds;
	}

	public void setOfficeIds(Integer officeIds) {
		this.officeIds = officeIds;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Integer getAssignRoleIds() {
		return assignRoleIds;
	}

	public void setAssignRoleIds(Integer assignRoleIds) {
		this.assignRoleIds = assignRoleIds;
	}

	public Integer getMappedOfficeIds() {
		return mappedOfficeIds;
	}

	public void setMappedOfficeIds(Integer mappedOfficeIds) {
		this.mappedOfficeIds = mappedOfficeIds;
	}

	public String getAssignedUserRoleIds() {
		return assignedUserRoleIds;
	}

	public void setAssignedUserRoleIds(String assignedUserRoleIds) {
		this.assignedUserRoleIds = assignedUserRoleIds;
	}

	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getAssignedOfficeIds() {
		return assignedOfficeIds;
	}

	public void setAssignedOfficeIds(String assignedOfficeIds) {
		this.assignedOfficeIds = assignedOfficeIds;
	}

	public String getMappingTO() {
		return mappingTO;
	}

	public void setMappingTO(String mappingTO) {
		this.mappingTO = mappingTO;
	}

	/**
	 * @return the userCityId
	 */
	public Integer getUserCityId() {
		return userCityId;
	}

	/**
	 * @param userCityId the userCityId to set
	 */
	public void setUserCityId(Integer userCityId) {
		this.userCityId = userCityId;
	}

	

}
