package com.ff.ud.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;
@Entity(name="com.ff.ud.dto.CityDTO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_city")
public class CityDTO {
@Id
@Column(name="CITY_CODE") private String cityCode;
@Column(name="city_id")  private String cityId;
@Column(name="CITY_NAME") private String cityName;
@Column(name="REGION")  private String regCode;



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
