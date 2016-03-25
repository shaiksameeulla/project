package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunAssignApproverDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3559319983181012986L;

	private Integer assignApproverId;
	private Integer user;
	private Integer screen;
	public Integer getAssignApproverId() {
		return assignApproverId;
	}
	public void setAssignApproverId(Integer assignApproverId) {
		this.assignApproverId = assignApproverId;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getScreen() {
		return screen;
	}
	public void setScreen(Integer screen) {
		this.screen = screen;
	}
	
}

