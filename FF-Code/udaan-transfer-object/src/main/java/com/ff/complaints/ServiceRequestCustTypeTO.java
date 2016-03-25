/**
 * 
 */
package com.ff.complaints;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author abarudwa
 *
 */
public class ServiceRequestCustTypeTO extends CGBaseTO{

private static final long serialVersionUID = 1L;
	
	private Integer serviceRequestCustomerTypeId;
	private String customerTypeCode;
	private String customerTypeName;
	private String customerTypeDescription;
	
	public Integer getServiceRequestCustomerTypeId() {
		return serviceRequestCustomerTypeId;
	}
	public void setServiceRequestCustomerTypeId(Integer serviceRequestCustomerTypeId) {
		this.serviceRequestCustomerTypeId = serviceRequestCustomerTypeId;
	}
	public String getCustomerTypeCode() {
		return customerTypeCode;
	}
	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getCustomerTypeDescription() {
		return customerTypeDescription;
	}
	public void setCustomerTypeDescription(String customerTypeDescription) {
		this.customerTypeDescription = customerTypeDescription;
	}
	
	
	
}
