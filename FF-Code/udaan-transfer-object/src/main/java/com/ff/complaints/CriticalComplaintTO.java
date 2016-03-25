package com.ff.complaints;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author hkansagr
 */
public class CriticalComplaintTO extends CGBaseTO {

	/** The serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The service request complaint id - Primary Key */
	private Integer serviceRequestComplaintId;

	/** The complaint creation date. */
	private String complaintCreationDateStr;

	/** The complaint No. */
	private String complaintNo;

	/** The complaint Id or Service Request Id. */
	private Integer complaintId;

	/** The consignment number. */
	private String consignmentNumber;

	/** The branch. */
	private String branch;

	/** The Reason. */
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

	/** The FIR Copy - radio button */
	private String firCopy;

	/** The FIR date. */
	private String firDateStr;

	/** The FOC Number. */
	private String focNumber;

	/** The customer type - radio button */
	private String customerType;

	/** The customer type date. */
	private String typeDateStr;

	/** The remark. */
	private String remark;

	/** Common Attribute - created and updated user id. */
	private Integer createdBy;
	private Integer updatedBy;

	/** The Mailer file name. */
	private String mailerFileName;// hidden

	/** The Mailer file object. */
	private FormFile mailerFile;// file

	/** The mailer created date Str. */
	private String mailerCreatedDateStr;// hidden

	/** The mailer id. */
	private Integer mailerId;// hidden

	/** The message display to screen. */
	private String transMsg;

	/** The todays date */
	private String todaysDate;

	/** The information given to list. */
	private List<StockStandardTypeTO> infoGivenTO;

	// LIR Received Copy
	private String lirCopy;

	private String lirDateStr;

	private String lirRemarks;

	// Lost Letter Copy
	private String lostLetter;

	private String lostLetterDateStr;

	private String lostLetterRemarks;

	private String tabName;

	// COF Received Copy
	private String cofCopy;

	private String cofDateStr;

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
	 * @return the cofDateStr
	 */
	public String getCofDateStr() {
		return cofDateStr;
	}

	/**
	 * @param cofDateStr
	 *            the cofDateStr to set
	 */
	public void setCofDateStr(String cofDateStr) {
		this.cofDateStr = cofDateStr;
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
	 * @return the infoGivenTO
	 */
	public List<StockStandardTypeTO> getInfoGivenTO() {
		return infoGivenTO;
	}

	/**
	 * @param infoGivenTO
	 *            the infoGivenTO to set
	 */
	public void setInfoGivenTO(List<StockStandardTypeTO> infoGivenTO) {
		this.infoGivenTO = infoGivenTO;
	}

	/**
	 * @return the todaysDate
	 */
	public String getTodaysDate() {
		return todaysDate;
	}

	/**
	 * @param todaysDate
	 *            the todaysDate to set
	 */
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}

	/**
	 * @return the mailerId
	 */
	public Integer getMailerId() {
		return mailerId;
	}

	/**
	 * @param mailerId
	 *            the mailerId to set
	 */
	public void setMailerId(Integer mailerId) {
		this.mailerId = mailerId;
	}

	/**
	 * @return the mailerCreatedDateStr
	 */
	public String getMailerCreatedDateStr() {
		return mailerCreatedDateStr;
	}

	/**
	 * @param mailerCreatedDateStr
	 *            the mailerCreatedDateStr to set
	 */
	public void setMailerCreatedDateStr(String mailerCreatedDateStr) {
		this.mailerCreatedDateStr = mailerCreatedDateStr;
	}

	/**
	 * @return the mailerFile
	 */
	public FormFile getMailerFile() {
		return mailerFile;
	}

	/**
	 * @param mailerFile
	 *            the mailerFile to set
	 */
	public void setMailerFile(FormFile mailerFile) {
		this.mailerFile = mailerFile;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
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
	 * @return the complaintId
	 */
	public Integer getComplaintId() {
		return complaintId;
	}

	/**
	 * @param complaintId
	 *            the complaintId to set
	 */
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
	}

	/**
	 * @return the mailerFileName
	 */
	public String getMailerFileName() {
		return mailerFileName;
	}

	/**
	 * @param mailerFileName
	 *            the mailerFileName to set
	 */
	public void setMailerFileName(String mailerFileName) {
		this.mailerFileName = mailerFileName;
	}

	/**
	 * @return the serviceRequestComplaintId
	 */
	public Integer getServiceRequestComplaintId() {
		return serviceRequestComplaintId;
	}

	/**
	 * @return the complaintNo
	 */
	public String getComplaintNo() {
		return complaintNo;
	}

	/**
	 * @return the tabName
	 */
	public String getTabName() {
		return tabName;
	}

	/**
	 * @param tabName
	 *            the tabName to set
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	/**
	 * @param complaintNo
	 *            the complaintNo to set
	 */
	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
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
	 * @return the firDateStr
	 */
	public String getFirDateStr() {
		return firDateStr;
	}

	/**
	 * @param firDateStr
	 *            the firDateStr to set
	 */
	public void setFirDateStr(String firDateStr) {
		this.firDateStr = firDateStr;
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
	 * @return the typeDateStr
	 */
	public String getTypeDateStr() {
		return typeDateStr;
	}

	/**
	 * @param typeDateStr
	 *            the typeDateStr to set
	 */
	public void setTypeDateStr(String typeDateStr) {
		this.typeDateStr = typeDateStr;
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
	 * @return the complaintCreationDateStr
	 */
	public String getComplaintCreationDateStr() {
		return complaintCreationDateStr;
	}

	/**
	 * @param complaintCreationDateStr
	 *            the complaintCreationDateStr to set
	 */
	public void setComplaintCreationDateStr(String complaintCreationDateStr) {
		this.complaintCreationDateStr = complaintCreationDateStr;
	}

	public String getLirCopy() {
		return lirCopy;
	}

	public void setLirCopy(String lirCopy) {
		this.lirCopy = lirCopy;
	}

	public String getLirDateStr() {
		return lirDateStr;
	}

	public void setLirDateStr(String lirDateStr) {
		this.lirDateStr = lirDateStr;
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

	public String getLostLetterDateStr() {
		return lostLetterDateStr;
	}

	public void setLostLetterDateStr(String lostLetterDateStr) {
		this.lostLetterDateStr = lostLetterDateStr;
	}

	public String getLostLetterRemarks() {
		return lostLetterRemarks;
	}

	public void setLostLetterRemarks(String lostLetterRemarks) {
		this.lostLetterRemarks = lostLetterRemarks;
	}

}
