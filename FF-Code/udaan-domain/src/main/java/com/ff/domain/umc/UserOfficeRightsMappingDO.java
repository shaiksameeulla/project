package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.UserDO;

public class UserOfficeRightsMappingDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3325431887647893351L;
	private Integer userRightappingId;
	@JsonManagedReference private UserDO user;
	private OfficeDO office;
	private String mappedTo;
	private String status;

	public Integer getUserRightappingId() {
		return userRightappingId;
	}

	public void setUserRightappingId(Integer userRightappingId) {
		this.userRightappingId = userRightappingId;
	}

	public UserDO getUser() {
		return user;
	}

	public void setUser(UserDO user) {
		this.user = user;
	}

	public OfficeDO getOffice() {
		return office;
	}

	public void setOffice(OfficeDO office) {
		this.office = office;
	}

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
