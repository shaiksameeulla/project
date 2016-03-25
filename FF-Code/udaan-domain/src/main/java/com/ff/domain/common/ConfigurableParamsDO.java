package com.ff.domain.common;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class ConfigurableParamsDO extends CGMasterDO implements Serializable{
	
	private static final long serialVersionUID = -2999475269526149987L;
	private Integer configurableParamsId;
	private String paramName;
	public Integer getConfigurableParamsId() {
		return configurableParamsId;
	}
	public void setConfigurableParamsId(Integer configurableParamsId) {
		this.configurableParamsId = configurableParamsId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	private String paramValue;

}
