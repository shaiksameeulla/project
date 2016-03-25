package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class CNPaperWorksDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2411151898849003104L;
	private Integer cnPaperWorkId;
	private String cnPaperWorkCode;
	private String cnPaperWorkName;
	private String cnPaperWorkDesc;
	private String status = "A";
	public Integer getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	public void setCnPaperWorkId(Integer cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	public String getCnPaperWorkCode() {
		return cnPaperWorkCode;
	}
	public void setCnPaperWorkCode(String cnPaperWorkCode) {
		this.cnPaperWorkCode = cnPaperWorkCode;
	}
	public String getCnPaperWorkName() {
		return cnPaperWorkName;
	}
	public void setCnPaperWorkName(String cnPaperWorkName) {
		this.cnPaperWorkName = cnPaperWorkName;
	}
	public String getCnPaperWorkDesc() {
		return cnPaperWorkDesc;
	}
	public void setCnPaperWorkDesc(String cnPaperWorkDesc) {
		this.cnPaperWorkDesc = cnPaperWorkDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
