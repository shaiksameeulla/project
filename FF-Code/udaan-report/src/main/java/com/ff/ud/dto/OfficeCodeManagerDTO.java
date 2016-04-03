package com.ff.ud.dto;


public class OfficeCodeManagerDTO {

	private Integer officeId;
	private String officeCode;
	private String officeName;
	
	
	public OfficeCodeManagerDTO() {}
	   public OfficeCodeManagerDTO(Integer officeId, String officeCode,String officeName) {
	      this.officeId = officeId;
	      this.officeCode = officeCode;
	      this.officeName=officeName;
	      
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
	
	
	
	
}
