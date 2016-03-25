package com.capgemini.lbs.framework.menu;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author anwar
 * 
 */
public class LeftMenuNavigationItems extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String screenCode;
	private Integer screenId;
	private String screenName;
	private String toolTip;
	private String imgPath;
	private String urlName;
	private String paramName;
	private String ctbsAppType;
	private String actionPathParam;
	private String parentName;
	private String appName;
	private String isCentralizedUrl="N";

	// Image path
	/**
	 * @return the screenCode
	 */
	public String getScreenCode() {
		return screenCode;
	}

	/**
	 * @param screenCode
	 *            the screenCode to set
	 */
	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName.trim();
	}

	/**
	 * @return the toolTip
	 */
	public String getToolTip() {
		return toolTip;
	}

	/**
	 * @param toolTip
	 *            the toolTip to set
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath
	 *            the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the urlPath
	 */
	public String getUrlName() {
		return urlName;
	}

	/**
	 * @param urlPath
	 *            the urlPath to set
	 */
	public void setUrlPath(String urlName) {
		this.urlName = urlName;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName
	 *            the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the ctbsAppType
	 */
	public String getCtbsAppType() {
		return ctbsAppType;
	}

	/**
	 * @param ctbsAppType
	 *            the ctbsAppType to set
	 */
	public void setCtbsAppType(String ctbsAppType) {
		this.ctbsAppType = ctbsAppType;
	}

	/**
	 * @return
	 */
	public String getActionPathParam() {
		return actionPathParam;
	}

	/**
	 * @param actionPathParam
	 */
	public void setActionPathParam(String actionPathParam) {
		this.actionPathParam = actionPathParam;
	}

	/**
	 * @return
	 */
	public Integer getScreenId() {
		return screenId;
	}

	/**
	 * @param screenId
	 */
	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName
	 *            the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the isCentralizedUrl
	 */
	public String getIsCentralizedUrl() {
		return isCentralizedUrl;
	}

	/**
	 * @param isCentralizedUrl the isCentralizedUrl to set
	 */
	public void setIsCentralizedUrl(String isCentralizedUrl) {
		this.isCentralizedUrl = isCentralizedUrl;
	}

}
