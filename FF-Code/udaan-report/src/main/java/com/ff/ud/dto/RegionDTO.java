package com.ff.ud.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;
@Entity(name="com.ff.ud.dto.RegionDTO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_region")
public class RegionDTO {
@Id
@Column(name="REGION_ID") private String regionCode;
@Column(name="REGION_NAME") private String regionName;
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

}
