package com.ff.ud.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ff.ud.constants.OpsmanDBFConstant;
import com.ff.ud.constants.XMLConstant;



@Entity(name="com.ff.ud.domain.AllFfMastDO")
@Table(name=OpsmanDBFConstant.DATABASE_NAME_MASTER+"ff_d_office")
public class AllFfMastDO {

	@Id
	@Column(name="office_id") private Integer officeId;
	@Column(name="office_code") private String officeCode;
	@Column(name="office_name") private String officeName;
	@Column(name="mapped_to_region") private Integer mappedToRegion;
	@Column(name="OFFICE_TYPE_ID") private String officeTypeId;
	
	
	public AllFfMastDO(){
		super();
	}
	
	
	public AllFfMastDO(Integer officeId, String officeCode, String officeName,
			Integer mappedToRegion,String officeTypeId) {
		super();
		this.officeId = officeId;
		this.officeCode = officeCode;
		this.officeName = officeName;
		this.mappedToRegion = mappedToRegion;
		this.officeTypeId = officeTypeId;
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
	public Integer getMappedToRegion() {
		return mappedToRegion;
	}
	public void setMappedToRegion(Integer mappedToRegion) {
		this.mappedToRegion = mappedToRegion;
	}


	public String getOfficeTypeId() {
		return officeTypeId;
	}


	public void setOfficeTypeId(String officeTypeId) {
		this.officeTypeId = officeTypeId;
	}
		

}
