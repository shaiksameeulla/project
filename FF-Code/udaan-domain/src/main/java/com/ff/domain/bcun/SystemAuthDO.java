package com.ff.domain.bcun;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author surajsah
 *
 */
public class SystemAuthDO extends CGMasterDO {

	private static final long serialVersionUID = -178719985174823116L;
	
	private  Integer systemId;
	private String systemCode;
	private Date authDate;
	private String authBranchCode;
	private String systemUserCode;
	private String active = "N";
	
	public Integer getSystemId() {
		return systemId;
	}
	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public Date getAuthDate() {
		return authDate;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	public String getAuthBranchCode() {
		return authBranchCode;
	}
	public void setAuthBranchCode(String authBranchCode) {
		this.authBranchCode = authBranchCode;
	}
	public String getSystemUserCode() {
		return systemUserCode;
	}
	public void setSystemUserCode(String systemUserCode) {
		this.systemUserCode = systemUserCode;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
		
}
