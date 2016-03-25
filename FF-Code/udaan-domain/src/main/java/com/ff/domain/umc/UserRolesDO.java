package com.ff.domain.umc;

/**
 * Author : Narasimha Rao Kattunga
 * Date : Nov - 05 - 2012
 */
import java.util.HashSet;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class UserRolesDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8120908622205014349L;

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String roleType;
	private String status;

	@JsonManagedReference
	private Set<ApplRightsDO> applRights = new HashSet<ApplRightsDO>();

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

	public Set<ApplRightsDO> getApplRights() {
		return applRights;
	}

	public void setApplRights(Set<ApplRightsDO> applRights) {
		this.applRights = applRights;
	}

}
