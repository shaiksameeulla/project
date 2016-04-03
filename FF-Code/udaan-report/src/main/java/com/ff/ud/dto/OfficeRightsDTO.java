package com.ff.ud.dto;
public class OfficeRightsDTO {
	 
	private Integer cityId;
	private String cityName;
	
	 public OfficeRightsDTO() {}
	   public OfficeRightsDTO(Integer cityId, String cityName) {
	      this.cityId = cityId;
	      this.cityName = cityName;
	      
	   }
	
	
	
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	

	
	
	
	
	
	

}
