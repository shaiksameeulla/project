package com.ff.ud.dto;

/**
 * @author Kir@N
 * This bean is used to hold the  data(form data) from deliveryRunsheetReport.jsp.
 */
public class BranchToHubFormBean {

	private String region;
	private String cityId;
	private String branchCode;
	private String dispatchDate;
	private String startDate;
	private String endDate;
	private String branchName;
	private String productSerices;
	private String customerId;
	private String bussinessName;
	private String vendorTypeId;


	
	public BranchToHubFormBean(){
		super();
	}
	
	public BranchToHubFormBean(String region, String cityId, String branchCode,
			String dispatchDate,String branchName,String productSerices,String customerId,String bussinessName,String vendorTypeId) {
		super();
		this.region = region;
		this.cityId = cityId;
		this.branchCode = branchCode;
		this.dispatchDate = dispatchDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.branchName=branchName;
		this.productSerices=productSerices;
		this.customerId=customerId;
		this.bussinessName=bussinessName;
		this.vendorTypeId=vendorTypeId;
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
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductSerices() {
		return productSerices;
	}

	public void setProductSerices(String productSerices) {
		this.productSerices = productSerices;
	}
	
	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(String vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	@Override
	public String toString() {
		return "BranchToHubFormBean [region=" + region + ", cityId=" + cityId
				+ ", branchCode=" + branchCode + ", dispatchDate="
				+ dispatchDate + ", startDate=" + startDate + ", endDate="
				+ endDate + ", branchName=" + branchName + ", productSerices="
				+ productSerices + ", customerId=" + customerId
				+ ", bussinessName=" + bussinessName + ", vendorTypeId="
				+ vendorTypeId + "]";
	}

	


	
}
