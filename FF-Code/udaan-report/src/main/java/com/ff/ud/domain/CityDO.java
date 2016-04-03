package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ff.ud.constants.OpsmanDBFConstant;


@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_city")
@Entity(name="com.ff.ud.domain.CityDO")
public class CityDO {
	@Id
	@Column(name="city_id") private String cityId;
	@Column(name="city_code") private String cityCode;
	
	@Column(name="city_name") private String cityName;
	@Transient private String regCode;
	
	@Column(name="region") private Integer regionId;
	
	
	
	
	
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	
	
}
