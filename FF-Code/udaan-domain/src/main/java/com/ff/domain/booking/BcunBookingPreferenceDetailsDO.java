package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunBookingPreferenceDetailsDO extends CGMasterDO {

	private static final long serialVersionUID = -5465619466992470628L;
	private Integer preferenceId;
	private String preferenceCode;
	private String preferenceName;
	private String description;
	private String status = "A";
	
	public Integer getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(Integer preferenceId) {
		this.preferenceId = preferenceId;
	}
	public String getPreferenceCode() {
		return preferenceCode;
	}
	public void setPreferenceCode(String preferenceCode) {
		this.preferenceCode = preferenceCode;
	}
	public String getPreferenceName() {
		return preferenceName;
	}
	public void setPreferenceName(String preferenceName) {
		this.preferenceName = preferenceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
