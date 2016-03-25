package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.geography.StateDO;

/**
 * 
 */

public class AddressDO extends CGMasterDO {

	private static final long serialVersionUID = -5057997499059879504L;
	private Integer addressId;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String mobile;
	private String phone;
	private String email;
	private String status;
	private CityDO cityDO;
	private StateDO stateDO;
	private PincodeDO pincodeDO;
	private String designation;
	private String contactPerson;
	
	
	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
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
	 * @param name
	 *            the name to set
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
	 * @param address1
	 *            the address1 to set
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
	 * @param address2
	 *            the address2 to set
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
	 * @param address3
	 *            the address3 to set
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
	 * @param mobile
	 *            the mobile to set
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
	 * @param phone
	 *            the phone to set
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
	 * @param email
	 *            the email to set
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
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public CityDO getCityDO() {
		return cityDO;
	}

	public void setCityDO(CityDO cityDO) {
		this.cityDO = cityDO;
	}

	/**
	 * @return the stateDO
	 */
	public StateDO getStateDO() {
		return stateDO;
	}

	/**
	 * @param stateDO the stateDO to set
	 */
	public void setStateDO(StateDO stateDO) {
		this.stateDO = stateDO;
	}

	/**
	 * @return the pincodeDO
	 */
	public PincodeDO getPincodeDO() {
		return pincodeDO;
	}

	/**
	 * @param pincodeDO the pincodeDO to set
	 */
	public void setPincodeDO(PincodeDO pincodeDO) {
		this.pincodeDO = pincodeDO;
	}



}
