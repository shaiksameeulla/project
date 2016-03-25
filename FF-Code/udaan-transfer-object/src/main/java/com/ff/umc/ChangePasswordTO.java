package com.ff.umc;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nihsingh
 *
 */
public class ChangePasswordTO extends CGBaseTO {

	
	private static final long serialVersionUID = 2667708935774237410L;
	
	private int loginid;
	private String username;
	private String oldpassword;
	private String newpassword;
	private String confirmpassword;
	
	/**
	 * @desc get Loginid
	 * @return loginId
	 */
	public int getLoginid() {
		return loginid;
	}
	
	/**
	 * @desc set Loginid
	 * @param loginid
	 */
	public void setLoginid(int loginid) {
		this.loginid = loginid;
	}
	
	/**
	 * @desc get Username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	
	/**
	 * @desc set Username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/**
	 * @desc get Oldpassword
	 * @return oldpassword
	 */
	public String getOldpassword() {
		return oldpassword;
	}
	
	
	/**
	 * @desc set Oldpassword
	 * @param oldpassword
	 */
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
		
	
	/**
	 * @desc get Newpassword
	 * @return newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}
	
	
	/**
	 * @desc set Newpassword
	 * @param newpassword
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	
	/**
	 * @desc get Confirmpassword
	 * @return confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}
	
	
	/**
	 * @desc set Confirmpassword
	 * @param confirmpassword
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
}
