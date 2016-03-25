package com.ff.domain.releasedscripts;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ReleasedScriptsDO extends CGFactDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559319983181012986L;

	private Integer scriptId;
	private String scriptName;
	private String moduleName;
	private String scriptContent;
	private Date releaseDate;
	private String isExecuted;
	private Date executedDate;
	private String exception;
	
	//generate setter & getters
	public Integer getScriptId() {
		return scriptId;
	}
	public void setScriptId(Integer scriptId) {
		this.scriptId = scriptId;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getScriptContent() {
		return scriptContent;
	}
	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getIsExecuted() {
		return isExecuted;
	}
	public void setIsExecuted(String isExecuted) {
		this.isExecuted = isExecuted;
	}
	public Date getExecutedDate() {
		return executedDate;
	}
	public void setExecutedDate(Date executedDate) {
		this.executedDate = executedDate;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
		
}
