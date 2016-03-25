package com.ff.to.ratemanagement.masters;


import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class RateContractSpocTO.
 * @author rmaladi
 */
public class RateContractSpocTO extends CGBaseTO {
	
	private static final long serialVersionUID = 6967186622295398004L;
	
	
	
	private Integer rateContractId;
	
	private Integer contractSpocId;
	private String complaintType;
	private String email;
	private String fax;
	private String mobile;
	private String contactNo;
	private String contactName;
	private String contactType;
	
	int rowCount;
	private String[] complaintTypeAry = new String[rowCount];
	private String[] emailAry = new String[rowCount];
	private String[] faxAry = new String[rowCount];
	private String[] mobileAry = new String[rowCount];
	private String[] contactNoAry = new String[rowCount];
	private String[] contactNameAry = new String[rowCount];
	private Integer[] contractSpocIdAry = new Integer[rowCount];
	Integer rowCountC;
	Integer rowCountF;
	
	/**
	 * @return the rateContractId
	 */
	public Integer getRateContractId() {
		return rateContractId;
	}
	/**
	 * @param rateContractId the rateContractId to set
	 */
	public void setRateContractId(Integer rateContractId) {
		this.rateContractId = rateContractId;
	}
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
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}
	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	/**
	 * @return the complaintTypeAry
	 */
	public String[] getComplaintTypeAry() {
		return complaintTypeAry;
	}
	/**
	 * @param complaintTypeAry the complaintTypeAry to set
	 */
	public void setComplaintTypeAry(String[] complaintTypeAry) {
		this.complaintTypeAry = complaintTypeAry;
	}
	/**
	 * @return the emailAry
	 */
	public String[] getEmailAry() {
		return emailAry;
	}
	/**
	 * @param emailAry the emailAry to set
	 */
	public void setEmailAry(String[] emailAry) {
		this.emailAry = emailAry;
	}
	/**
	 * @return the faxAry
	 */
	public String[] getFaxAry() {
		return faxAry;
	}
	/**
	 * @param faxAry the faxAry to set
	 */
	public void setFaxAry(String[] faxAry) {
		this.faxAry = faxAry;
	}
	/**
	 * @return the mobileAry
	 */
	public String[] getMobileAry() {
		return mobileAry;
	}
	/**
	 * @param mobileAry the mobileAry to set
	 */
	public void setMobileAry(String[] mobileAry) {
		this.mobileAry = mobileAry;
	}
	/**
	 * @return the contactNoAry
	 */
	public String[] getContactNoAry() {
		return contactNoAry;
	}
	/**
	 * @param contactNoAry the contactNoAry to set
	 */
	public void setContactNoAry(String[] contactNoAry) {
		this.contactNoAry = contactNoAry;
	}
	/**
	 * @return the contactNameAry
	 */
	public String[] getContactNameAry() {
		return contactNameAry;
	}
	/**
	 * @param contactNameAry the contactNameAry to set
	 */
	public void setContactNameAry(String[] contactNameAry) {
		this.contactNameAry = contactNameAry;
	}
	/**
	 * @return the contractSpocIdAry
	 */
	public Integer[] getContractSpocIdAry() {
		return contractSpocIdAry;
	}
	/**
	 * @param contractSpocIdAry the contractSpocIdAry to set
	 */
	public void setContractSpocIdAry(Integer[] contractSpocIdAry) {
		this.contractSpocIdAry = contractSpocIdAry;
	}
	/**
	 * @return the rowCountC
	 */
	public Integer getRowCountC() {
		return rowCountC;
	}
	/**
	 * @param rowCountC the rowCountC to set
	 */
	public void setRowCountC(Integer rowCountC) {
		this.rowCountC = rowCountC;
	}
	/**
	 * @return the rowCountF
	 */
	public Integer getRowCountF() {
		return rowCountF;
	}
	/**
	 * @param rowCountF the rowCountF to set
	 */
	public void setRowCountF(Integer rowCountF) {
		this.rowCountF = rowCountF;
	}
	

}
