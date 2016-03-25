package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ApplScreenDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3559319983181012986L;

	private Integer screenId;
	private String screenCode;
	private String screenName;
	private String screenDescription;
	private String moduleName;
	private String moduleDescription;
	private String imgPath;
	private String urlName;
	private String paramName;
	private String appName;
	private String accessibleTO;
	private String scrAssign;
	/*private Integer level;
	private Integer displaySequence;
	private String parentName;*/
	private String status;

	public String getScrAssign() {
		return scrAssign;
	}

	public void setScrAssign(String scrAssign) {
		this.scrAssign = scrAssign;
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenDescription() {
		return screenDescription;
	}

	public void setScreenDescription(String screenDescription) {
		this.screenDescription = screenDescription;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccessibleTO() {
		return accessibleTO;
	}

	public void setAccessibleTO(String accessibleTO) {
		this.accessibleTO = accessibleTO;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the level
	 *//*
	public Integer getLevel() {
		return level;
	}

	*//**
	 * @param level
	 *            the level to set
	 *//*
	public void setLevel(Integer level) {
		this.level = level;
	}

	*//**
	 * @return the displaySequence
	 *//*
	public Integer getDisplaySequence() {
		return displaySequence;
	}

	*//**
	 * @param displaySequence
	 *            the displaySequence to set
	 *//*
	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	*//**
	 * @return the parentName
	 *//*
	public String getParentName() {
		return parentName;
	}

	*//**
	 * @param parentName the parentName to set
	 *//*
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	*/
}
