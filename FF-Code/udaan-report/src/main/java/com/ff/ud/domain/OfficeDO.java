package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;




@Entity(name="com.ff.ud.domain.OfficeDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_office")
public class OfficeDO {

	@Id
	@Column(name="office_id") private Integer officeId;
	@Column(name="office_code") private String officeCode;
	@Column(name="office_name") private String officeName;
	@Column(name="office_Type_id") private Integer officeType;
	@Column(name="city_id") private Integer cityId;
	
	
	
	public OfficeDO(){
		super();
	}


	public OfficeDO(Integer officeId, String officeCode, String officeName,Integer officeType,
			Integer cityId) {
		super();
		this.officeId = officeId;
		this.officeCode = officeCode;
		this.officeName = officeName;
		this.officeType=officeType;
		this.cityId = cityId;
	}


	public Integer getOfficeId() {
		return officeId;
	}


	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}


	public String getOfficeCode() {
		return officeCode;
	}


	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}


	public String getOfficeName() {
		return officeName;
	}


	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}


	public Integer getCityId() {
		return cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	public Integer getOfficeType() {
		return officeType;
	}


	public void setOfficeType(Integer officeType) {
		this.officeType = officeType;
	}
	
	
	
}
