package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The Class RateContractDO.
 *
 * @author rmaladi
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="contractSpocId")
public class RateContractSpocDO extends CGMasterDO {
	
	private static final long serialVersionUID = -4823091798225417894L;
	
	private Integer contractSpocId;
	//private Integer rateContractId;
	private String complaintType;
	private String email;
	private String fax;
	private String mobile;
	private String contactNo;
	private String contactName;
	private String contactType;
	//@JsonBackReference
	private RateContractDO rateContractDO;
	/**
	 * @return the contractSpocId
	 */
	public Integer getContractSpocId() {
		return contractSpocId;
	}
	/**
	 * @param contractSpocId the contractSpocId to set
	 */
	public void setContractSpocId(Integer contractSpocId) {
		this.contractSpocId = contractSpocId;
	}
	/**
	 * @return the rateContractId
	 *//*
	public Integer getRateContractId() {
		return rateContractId;
	}
	*//**
	 * @param rateContractId the rateContractId to set
	 *//*
	public void setRateContractId(Integer rateContractId) {
		this.rateContractId = rateContractId;
	}*/
	/**
	 * @return the complaintType
	 */
	public String getComplaintType() {
		return complaintType;
	}
	/**
	 * @param complaintType the complaintType to set
	 */
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
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
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the contactType
	 */
	public String getContactType() {
		return contactType;
	}
	/**
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	/**
	 * @return the rateContractDO
	 */
	public RateContractDO getRateContractDO() {
		return rateContractDO;
	}
	/**
	 * @param rateContractDO the rateContractDO to set
	 */
	public void setRateContractDO(RateContractDO rateContractDO) {
		this.rateContractDO = rateContractDO;
	}
		
	
}
