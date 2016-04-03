package com.ff.ud.dto;

public class PinckUpDeliveryDTO {
	
	private String customer;
	private Integer officeId;
	private Integer customerId;
	private String businessName;
	private String customerCode;
	
	
	
	
	public PinckUpDeliveryDTO(){}
	 public PinckUpDeliveryDTO(String customer,Integer officeId, Integer customerId,String businessName,String customerCode) {
		 this.customer=customer;
	      this.officeId = officeId;
	      this.customerId = customerId;
	      this.businessName=businessName;
	      this.customerCode=customerCode;
	      
	      
	      
	   }
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	
	
	
	
	
	
	

}
