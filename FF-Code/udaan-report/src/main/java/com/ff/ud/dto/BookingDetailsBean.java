package com.ff.ud.dto;

/**
 * @author Kir@N
 * This bean is used to hold the  data(form data) from bookingDeatils.jsp.
 */
public class BookingDetailsBean {
	private String region;
	private String cityId;
	private String branchCode;
	private String startDate;
	private String endDate;
	private String bussinessName;
	
	
	
	public BookingDetailsBean(){
		super();
	}
	
	public BookingDetailsBean(String region, String cityId, String branchCode,
			String startDate,String endDate,String bussinessName) {
		super();
		this.region = region;
		this.cityId = cityId;
		this.branchCode = branchCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bussinessName = bussinessName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	@Override
	public String toString() {
		return "BookingDetailsBean [region=" + region + ", cityId=" + cityId
				+ ", branchCode=" + branchCode + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", bussinessName=" + bussinessName
				+ "]";
	}

}
