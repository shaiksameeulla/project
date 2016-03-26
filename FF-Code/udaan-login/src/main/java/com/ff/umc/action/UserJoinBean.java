package com.ff.umc.action;

import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.umc.PasswordDO;
import com.ff.domain.umc.UserDO;

public class UserJoinBean {
	
	private UserDO user;
	private EmployeeDO empDO;
	private CustomerDO cusDo;
	private PasswordDO paswd;
	private OfficeDO officeDO;

	
	public UserJoinBean(UserDO user, EmployeeDO empDO, CustomerDO cusDo, PasswordDO paswd, OfficeDO officeDO){
		this.user=user;
		this.empDO=empDO;
		this.setCusDo(cusDo);
		this.paswd=paswd;
		this.officeDO=officeDO;
	}


	/**
	 * @return the user
	 */
	public UserDO getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(UserDO user) {
		this.user = user;
	}


	/**
	 * @return the empDO
	 */
	public EmployeeDO getEmpDO() {
		return empDO;
	}


	/**
	 * @param empDO the empDO to set
	 */
	public void setEmpDO(EmployeeDO empDO) {
		this.empDO = empDO;
	}


	/**
	 * @return the cusDo
	 */
	public CustomerDO getCusDo() {
		return cusDo;
	}


	/**
	 * @param cusDo the cusDo to set
	 */
	public void setCusDo(CustomerDO cusDo) {
		this.cusDo = cusDo;
	}


	/**
	 * @return the paswd
	 */
	public PasswordDO getPaswd() {
		return paswd;
	}


	/**
	 * @param paswd the paswd to set
	 */
	public void setPaswd(PasswordDO paswd) {
		this.paswd = paswd;
	}



	/**
	 * @return the officeDO
	 */
	public OfficeDO getOfficeDO() {
		return officeDO;
	}


	/**
	 * @param officeDO the officeDO to set
	 */
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
	}

}
