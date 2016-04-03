package com.ff.ud.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;

@Entity(name="com.ff.ud.dto.OfficeDTO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_office")
public class OfficeDTO {
	
	@Id
	@Column(name="MAPPED_TO_REGION") private String regionCode;
	@Column(name="OFFICE_NAME") private String regionName;
	@Column(name="CITY_ID") private Integer cityId;
	@Column(name="OFFICE_CODE") private String officeCode;
	
	
	
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	

}
