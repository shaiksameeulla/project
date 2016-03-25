package com.ff.domain.complaints;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */
public class ServiceRequestComplaintDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	/** The service request complaint id - Primary Key */
	private Integer serviceRequestComplaintId;

	/** The complaint creation date. */
	private Date complaintCreationDate;

	/** The consignment number. */
	private String consignmentNumber;

	/** The branch. */
	private String branch;

	/** The reason. */
	private String reason;

	/** The declared value. */
	private Double declaredValue;

	/** The consigner name. */
	private String consignerName;

	/** The customer code. */
	private String customerCode;

	/** The customer address. */
	private String customerAddress;

	/** The customer phone. */
	private String customerPhone;

	/** The customer email. */
	private String customerEmail;

	/** The information given to. */
	private String informationGivenTo;

	/** The FIR Copy. */
	private String firCopy;

	/** The FIR date. */
	private Date firDate;

	/** The FOC Number. */
	private String focNumber;

	/** The customer type. */
	private String customerType;

	/** The customer type date. */
	private Date typeDate;

	/** The remark. */
	private String remark;

	/** Mapped to Service Request table. */
	private ServiceRequestDO serviceRequestDO;

	/** Mapped to Service Request Paper work table */
	private ServiceRequestPapersDO mailerPaperDO;

	// LIR Copy
	private String lirCopy;

	private Date lirDate;

	private String lirRemarks;

	// Lost letter Copy
	private String lostLetter;

	private Date lostLetterDate;

	private String lostLetterRemarks;

	// COF Received Copy
	private String cofCopy;

	private Date cofDate;

	private String cofRemarks;

	/** The critical complaints status - C-CLAIM, L-LEGAL, S-SETTELED */
	private String criticalCmpltStatus;

	/**
	 * @return the criticalCmpltStatus
	 */
	public String getCriticalCmpltStatus() {
		return criticalCmpltStatus;
	}

	/**
	 * @param criticalCmpltStatus
	 *            the criticalCmpltStatus to set
	 */
	public void setCriticalCmpltStatus(String criticalCmpltStatus) {
		this.criticalCmpltStatus = criticalCmpltStatus;
	}

	/**
	 * @return the cofDate
	 */
	public Date getCofDate() {
		return cofDate;
	}

	/**
	 * @param cofDate
	 *            the cofDate to set
	 */
	public void setCofDate(Date cofDate) {
		this.cofDate = cofDate;
	}

	/**
	 * @return the cofCopy
	 */
	public String getCofCopy() {
		return cofCopy;
	}

	/**
	 * @param cofCopy
	 *            the cofCopy to set
	 */
	public void setCofCopy(String cofCopy) {
		this.cofCopy = cofCopy;
	}

	/**
	 * @return the cofRemarks
	 */
	public String getCofRemarks() {
		return cofRemarks;
	}

	/**
	 * @param cofRemarks
	 *            the cofRemarks to set
	 */
	public void setCofRemarks(String cofRemarks) {
		this.cofRemarks = cofRemarks;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the serviceRequestComplaintId
	 */
	public Integer getServiceRequestComplaintId() {
		return serviceRequestComplaintId;
	}

	/**
	 * @return the mailerPaperDO
	 */
	public ServiceRequestPapersDO getMailerPaperDO() {
		return mailerPaperDO;
	}

	/**
	 * @param mailerPaperDO
	 *            the mailerPaperDO to set
	 */
	public void setMailerPaperDO(ServiceRequestPapersDO mailerPaperDO) {
		this.mailerPaperDO = mailerPaperDO;
	}

	/**
	 * @return the serviceRequestDO
	 */
	public ServiceRequestDO getServiceRequestDO() {
		return serviceRequestDO;
	}

	/**
	 * @param serviceRequestDO
	 *            the serviceRequestDO to set
	 */
	public void setServiceRequestDO(ServiceRequestDO serviceRequestDO) {
		this.serviceRequestDO = serviceRequestDO;
	}

	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * @param declaredValue
	 *            the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * @param serviceRequestComplaintId
	 *            the serviceRequestComplaintId to set
	 */
	public void setServiceRequestComplaintId(Integer serviceRequestComplaintId) {
		this.serviceRequestComplaintId = serviceRequestComplaintId;
	}

	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the consignerName
	 */
	public String getConsignerName() {
		return consignerName;
	}

	/**
	 * @param consignerName
	 *            the consignerName to set
	 */
	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}

	/**
	 * @param customerAddress
	 *            the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone
	 *            the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail
	 *            the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the informationGivenTo
	 */
	public String getInformationGivenTo() {
		return informationGivenTo;
	}

	/**
	 * @param informationGivenTo
	 *            the informationGivenTo to set
	 */
	public void setInformationGivenTo(String informationGivenTo) {
		this.informationGivenTo = informationGivenTo;
	}

	/**
	 * @return the firCopy
	 */
	public String getFirCopy() {
		return firCopy;
	}

	/**
	 * @param firCopy
	 *            the firCopy to set
	 */
	public void setFirCopy(String firCopy) {
		this.firCopy = firCopy;
	}

	/**
	 * @return the firDate
	 */
	public Date getFirDate() {
		return firDate;
	}

	/**
	 * @param firDate
	 *            the firDate to set
	 */
	public void setFirDate(Date firDate) {
		this.firDate = firDate;
	}

	/**
	 * @return the focNumber
	 */
	public String getFocNumber() {
		return focNumber;
	}

	/**
	 * @param focNumber
	 *            the focNumber to set
	 */
	public void setFocNumber(String focNumber) {
		this.focNumber = focNumber;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the typeDate
	 */
	public Date getTypeDate() {
		return typeDate;
	}

	/**
	 * @param typeDate
	 *            the typeDate to set
	 */
	public void setTypeDate(Date typeDate) {
		this.typeDate = typeDate;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the complaintCreationDate
	 */
	public Date getComplaintCreationDate() {
		return complaintCreationDate;
	}

	/**
	 * @param complaintCreationDate
	 *            the complaintCreationDate to set
	 */
	public void setComplaintCreationDate(Date complaintCreationDate) {
		this.complaintCreationDate = complaintCreationDate;
	}

	public String getLirCopy() {
		return lirCopy;
	}

	public void setLirCopy(String lirCopy) {
		this.lirCopy = lirCopy;
	}

	public Date getLirDate() {
		return lirDate;
	}

	public void setLirDate(Date lirDate) {
		this.lirDate = lirDate;
	}

	public String getLirRemarks() {
		return lirRemarks;
	}

	public void setLirRemarks(String lirRemarks) {
		this.lirRemarks = lirRemarks;
	}

	public String getLostLetter() {
		return lostLetter;
	}

	public void setLostLetter(String lostLetter) {
		this.lostLetter = lostLetter;
	}

	public Date getLostLetterDate() {
		return lostLetterDate;
	}

	public void setLostLetterDate(Date lostLetterDate) {
		this.lostLetterDate = lostLetterDate;
	}

	public String getLostLetterRemarks() {
		return lostLetterRemarks;
	}

	public void setLostLetterRemarks(String lostLetterRemarks) {
		this.lostLetterRemarks = lostLetterRemarks;
	}

}
