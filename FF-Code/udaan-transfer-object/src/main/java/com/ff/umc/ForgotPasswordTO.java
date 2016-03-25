package com.ff.umc;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author nihsingh
 *
 */
public class ForgotPasswordTO extends CGBaseTO {
	
	private Integer loginid;

	/**
	 * @desc get Loginid
	 * @return loginid
	 */
	public Integer getLoginid() {
		return loginid;
	}

	/**
	 * @desc set Loginid
	 * @param loginid
	 */
	public void setLoginid(Integer loginid) {
		this.loginid = loginid;
	}

}
