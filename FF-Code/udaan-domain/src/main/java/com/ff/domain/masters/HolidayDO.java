package com.ff.domain.masters;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;


public class HolidayDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date date;
	private Integer regionId;
	private Integer stateId;
	private Integer cityId;
	private Integer branchId;
	private Integer holidayNameId;
	private String others;
	private char status;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getHolidayNameId() {
		return holidayNameId;
	}
	public void setHolidayNameId(Integer holidayNameId) {
		this.holidayNameId = holidayNameId;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
}
