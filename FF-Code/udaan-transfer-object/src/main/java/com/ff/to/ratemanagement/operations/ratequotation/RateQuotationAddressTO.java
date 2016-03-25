package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.StateTO;
/**
 * @author preegupt
 *
 */
public class RateQuotationAddressTO extends CGBaseTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer addressId;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String mobile;
	private String phone;
	private String email;
	private String status;
	private CityTO city;
	private StateTO state;
	private PincodeTO pincode;
	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}
	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the city
	 */
	public CityTO getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(CityTO city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public StateTO getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(StateTO state) {
		this.state = state;
	}
	/**
	 * @return the pincode
	 */
	public PincodeTO getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(PincodeTO pincode) {
		this.pincode = pincode;
	}
	
}
