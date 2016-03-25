package com.ff.pickup;

import com.capgemini.lbs.framework.to.CGBaseTO;


public class ReversePickupCustomerTO extends CGBaseTO
{
 /**
	 * 
	 */
	private static final long serialVersionUID = -3662589629703452167L;
private int id;
 private String consignerName;
 private String consignerAddress;
 private int cityId;
 private int pincodeId;
 
 private String telephoneNumber;
 private String mobileNumber;
 private String emailId;
/**
 * @return the id
 */
public int getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(int id) {
	this.id = id;
}
/**
 * @return the consignerName
 */
public String getConsignerName() {
	return consignerName;
}
/**
 * @param consignerName the consignerName to set
 */
public void setConsignerName(String consignerName) {
	this.consignerName = consignerName;
}
/**
 * @return the consignerAddress
 */
public String getConsignerAddress() {
	return consignerAddress;
}
/**
 * @param consignerAddress the consignerAddress to set
 */
public void setConsignerAddress(String consignerAddress) {
	this.consignerAddress = consignerAddress;
}

/**
 * @return the telephoneNumber
 */
public String getTelephoneNumber() {
	return telephoneNumber;
}
/**
 * @param telephoneNumber the telephoneNumber to set
 */
public void setTelephoneNumber(String telephoneNumber) {
	this.telephoneNumber = telephoneNumber;
}
/**
 * @return the mobileNumber
 */
public String getMobileNumber() {
	return mobileNumber;
}
/**
 * @param mobileNumber the mobileNumber to set
 */
public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}
/**
 * @return the emailId
 */
public String getEmailId() {
	return emailId;
}
/**
 * @param emailId the emailId to set
 */
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
/**
 * @return the pincodeId
 */
public int getPincodeId() {
	return pincodeId;
}
/**
 * @param pincodeId the pincodeId to set
 */
public void setPincodeId(int pincodeId) {
	this.pincodeId = pincodeId;
}
/**
 * @return the cityId
 */
public int getCityId() {
	return cityId;
}
/**
 * @param cityId the cityId to set
 */
public void setCityId(int cityId) {
	this.cityId = cityId;
}
 
 
 
}
