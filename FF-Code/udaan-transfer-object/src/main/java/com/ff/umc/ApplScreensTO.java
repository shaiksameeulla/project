package com.ff.umc;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ApplScreensTO extends CGBaseTO implements
		Comparable<ApplScreensTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1188016516923340426L;
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

	@Override
	public int compareTo(ApplScreensTO obj1) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(moduleName)) {
			returnVal = this.moduleName.compareTo(obj1.moduleName);
		}
		return returnVal;
	}
}
