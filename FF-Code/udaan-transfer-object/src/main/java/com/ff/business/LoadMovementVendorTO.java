package com.ff.business;

import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.organization.OfficeTO;

/**
 * @author narmdr
 *
 */
public class LoadMovementVendorTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	private Integer vendorId;
	private String vendorCode;
	private String vendorType;
	private String firstname;
	private String lastName;
	private String phone;
	private String businessName;	
	private String mobile;
	private String fax;
	private String companyType;
	private String service;
	private String email;
	private String address;
	private String pincode;
	private Integer vendorTypeId;
	private OfficeTO officeTO;
	List<VendorRegionMapTO> vendorRegionMappingTO; 	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * @return the officeTO
	 */
	public OfficeTO getOfficeTO() {
		return officeTO;
	}
	/**
	 * @param officeTO the officeTO to set
	 */
	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<VendorRegionMapTO> getVendorRegionMappingTO() {
		return vendorRegionMappingTO;
	}
	public void setVendorRegionMappingTO(
			List<VendorRegionMapTO> vendorRegionMappingTO) {
		this.vendorRegionMappingTO = vendorRegionMappingTO;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Integer getVendorTypeId() {
		return vendorTypeId;
	}
	public void setVendorTypeId(Integer vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}
	
	
}
