package com.ff.umc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class UserRightsTO extends CGBaseTO implements Comparable<UserRightsTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2060772316479366401L;
	private Integer userId;
	private String userName;
	private String userCode;
	private Integer roleId;
	private String roleCode;
	private String roleName;
	private Integer userRightsId;
	private Integer userCityId;
	private String status;
	private List<UserOfficeRightsMappingTO> userMappings = new ArrayList();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public Integer getUserRightsId() {
		return userRightsId;
	}

	public void setUserRightsId(Integer userRightsId) {
		this.userRightsId = userRightsId;
	}

	public List<UserOfficeRightsMappingTO> getUserMappings() {
		return userMappings;
	}

	public void setUserMappings(List<UserOfficeRightsMappingTO> userMappings) {
		this.userMappings = userMappings;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserRightsTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.roleCode)) {
			returnVal = this.roleCode.compareTo(obj1.roleCode);
		}
		return returnVal;
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
