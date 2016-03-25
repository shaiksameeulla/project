package com.ff.domain.geography;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunZoneDO extends CGFactDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7001810503326375528L;
	private Integer zoneId;
	private String zoneName;
	private String zoneCode;
	/**
	 * @return the zoneId
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}
	/**
	 * @param zoneCode the zoneCode to set
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	
	
}
