package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunApproverUserOfficeDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3559319983181012986L;

	private Integer approverUserOfficeId;
	private Integer user;
	private Integer office;
	private Integer city;
	private Integer regionalOffice;
	private String mappedTo;
	public Integer getApproverUserOfficeId() {
		return approverUserOfficeId;
	}
	public void setApproverUserOfficeId(Integer approverUserOfficeId) {
		this.approverUserOfficeId = approverUserOfficeId;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getOffice() {
		return office;
	}
	public void setOffice(Integer office) {
		this.office = office;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getRegionalOffice() {
		return regionalOffice;
	}
	public void setRegionalOffice(Integer regionalOffice) {
		this.regionalOffice = regionalOffice;
	}
	public String getMappedTo() {
		return mappedTo;
	}
	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}
	
}

