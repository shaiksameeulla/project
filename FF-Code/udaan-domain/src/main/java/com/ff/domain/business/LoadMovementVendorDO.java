package com.ff.domain.business;

import java.util.Set;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.organization.OfficeDO;

/**
 * @author narmdr
 *
 */
public class LoadMovementVendorDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;
	
	private Integer vendorId;
	private String vendorCode;
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
	private OfficeDO officeDO;
	private VendorTypeDO vendorTypeDO;
	/*private RegionDO regionDO;*/
	@JsonManagedReference Set<VendorRegionMapDO> vendorRegionMappingDO;     
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public OfficeDO getOfficeDO() {
		return officeDO;
	}
	public void setOfficeDO(OfficeDO officeDO) {
		this.officeDO = officeDO;
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}
	/**
	 * @param companyType the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the regionDO
	 *//*
	public RegionDO getRegionDO() {
		return regionDO;
	}
	*//**
	 * @param regionDO the regionDO to set
	 *//*
	public void setRegionDO(RegionDO regionDO) {
		this.regionDO = regionDO;
	}*/
	/**
	 * @return the vendorRegionMappingDO
	 */
	/**
	 * @return the vendorRegionMappingDO
	 */
	public Set<VendorRegionMapDO> getVendorRegionMappingDO() {
		return vendorRegionMappingDO;
	}
	/**
	 * @param vendorRegionMappingDO the vendorRegionMappingDO to set
	 */
	public void setVendorRegionMappingDO(
			Set<VendorRegionMapDO> vendorRegionMappingDO) {
		this.vendorRegionMappingDO = vendorRegionMappingDO;
	}
	/**
	 * @return the vendorTypeDO
	 */
	public VendorTypeDO getVendorTypeDO() {
		return vendorTypeDO;
	}
	/**
	 * @param vendorTypeDO the vendorTypeDO to set
	 */
	public void setVendorTypeDO(VendorTypeDO vendorTypeDO) {
		this.vendorTypeDO = vendorTypeDO;
	}
	
	
	
}
