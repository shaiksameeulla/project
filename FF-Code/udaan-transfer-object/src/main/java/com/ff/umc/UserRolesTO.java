package com.ff.umc;

import org.apache.commons.lang.StringUtils;

/**
 * Author : Narasimha Rao Kattunga
 * Date : Nov - 05 - 2012
 */
import com.capgemini.lbs.framework.to.CGBaseTO;

public class UserRolesTO extends CGBaseTO implements Comparable<UserRolesTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 609185750067781040L;

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String roleType;
	private String status;
	private Integer rightId;
	private Integer userId;

	int rowCount;
	private String[] userRightNames = new String[rowCount];
	private Integer[] userRightIds = new Integer[rowCount];
	private Integer[] applScreenId = new Integer[rowCount];
	private String[] isRightAssigned = new String[rowCount];

	private String isUpdationMode = "N";

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

	public String[] getUserRightNames() {
		return userRightNames;
	}

	public void setUserRightNames(String[] userRightNames) {
		this.userRightNames = userRightNames;
	}

	public Integer[] getUserRightIds() {
		return userRightIds;
	}

	public void setUserRightIds(Integer[] userRightIds) {
		this.userRightIds = userRightIds;
	}

	public Integer[] getApplScreenId() {
		return applScreenId;
	}

	public void setApplScreenId(Integer[] applScreenId) {
		this.applScreenId = applScreenId;
	}

	public String[] getIsRightAssigned() {
		return isRightAssigned;
	}

	public void setIsRightAssigned(String[] isRightAssigned) {
		this.isRightAssigned = isRightAssigned;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIsUpdationMode() {
		return isUpdationMode;
	}

	public void setIsUpdationMode(String isUpdationMode) {
		this.isUpdationMode = isUpdationMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserRolesTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.roleName)) {
			returnVal = this.roleName.compareTo(obj1.roleName);
		}
		return returnVal;
	}

}
