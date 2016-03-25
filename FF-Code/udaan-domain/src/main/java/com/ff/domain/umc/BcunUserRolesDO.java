package com.ff.domain.umc;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * Author : Narasimha Rao Kattunga
 * Date : Nov - 05 - 2012
 */

public class BcunUserRolesDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8120908622205014349L;

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String roleType;
	private String status;
	private Integer createdBy ;
	private Integer updatedBy ;
	private Date creationDate;
	private Date updateDate;
	private String dtToBranch;
	//private BcunUserDO createdBY;
	
	
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
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the dtToBranch
	 */
	public String getDtToBranch() {
		return dtToBranch;
	}
	/**
	 * @param dtToBranch the dtToBranch to set
	 */
	public void setDtToBranch(String dtToBranch) {
		this.dtToBranch = dtToBranch;
	}
	
	
	
}
