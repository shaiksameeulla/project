package com.ff.domain.consignment;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class DuplicateConsignmentDO.
 * 
 * @author narmdr
 */
public class DuplicateConsignmentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404421028866173031L;
	private Integer consignmentId;//duplicateConsgId
	private String consgNo;
	private Integer orgOffId;
	private String consgStatus = "B";
	// No of pieces are not applicable for docs booking type
	private Integer noOfPcs = 1;
	
	private Double price;

	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;

	private String otherCNContent;
	private String insurencePolicyNo;
	private String refNo;
	private String mobileNo;

	// For POD
	private Date receivedDateTime;
	private Date deliveredDate;

	private String consigneeDetails;
	private String consignorDetails;

	private Double declaredValue;
	private String lcBankName;
	private Integer operatingOffice;

	private Integer customer;

	private Integer destinationPincodeId;
	private Integer consignmentTypeId;
	private Integer cnContentsId;
	private Integer cnPaperWorksId;
	private Integer insuredById;
	
//	private PincodeDO destPincodeDO;//rename
//	private ConsignmentTypeDO consignmentTypeDO;//rename
//	private CNContentDO cnContentDO;//rename
//	private CNPaperWorksDO cnPaperWorksDO;//rename
//	private InsuredByDO insuredByDO;//rename
	
	private Set<DuplicateChildConsignmentDO> duplicateChildCNs;


	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the orgOffId
	 */
	public Integer getOrgOffId() {
		return orgOffId;
	}

	/**
	 * @param orgOffId the orgOffId to set
	 */
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}

	/**
	 * @return the consgStatus
	 */
	public String getConsgStatus() {
		return consgStatus;
	}

	/**
	 * @param consgStatus the consgStatus to set
	 */
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}

	/**
	 * @return the noOfPcs
	 */
	public Integer getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * @param noOfPcs the noOfPcs to set
	 */
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return the volWeight
	 */
	public Double getVolWeight() {
		return volWeight;
	}

	/**
	 * @param volWeight the volWeight to set
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * @return the finalWeight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}

	/**
	 * @param finalWeight the finalWeight to set
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	/**
	 * @return the otherCNContent
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}

	/**
	 * @param otherCNContent the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * @return the insurencePolicyNo
	 */
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}

	/**
	 * @param insurencePolicyNo the insurencePolicyNo to set
	 */
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}

	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the receivedDateTime
	 */
	public Date getReceivedDateTime() {
		return receivedDateTime;
	}

	/**
	 * @param receivedDateTime the receivedDateTime to set
	 */
	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}

	/**
	 * @return the deliveredDate
	 */
	public Date getDeliveredDate() {
		return deliveredDate;
	}

	/**
	 * @param deliveredDate the deliveredDate to set
	 */
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	/**
	 * @return the consigneeDetails
	 */
	public String getConsigneeDetails() {
		return consigneeDetails;
	}

	/**
	 * @param consigneeDetails the consigneeDetails to set
	 */
	public void setConsigneeDetails(String consigneeDetails) {
		this.consigneeDetails = consigneeDetails;
	}

	/**
	 * @return the consignorDetails
	 */
	public String getConsignorDetails() {
		return consignorDetails;
	}

	/**
	 * @param consignorDetails the consignorDetails to set
	 */
	public void setConsignorDetails(String consignorDetails) {
		this.consignorDetails = consignorDetails;
	}

	/**
	 * @return the declaredValue
	 */
	public Double getDeclaredValue() {
		return declaredValue;
	}

	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}

	/**
	 * @return the lcBankName
	 */
	public String getLcBankName() {
		return lcBankName;
	}

	/**
	 * @param lcBankName the lcBankName to set
	 */
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}

	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	/**
	 * @return the destinationPincodeId
	 */
	public Integer getDestinationPincodeId() {
		return destinationPincodeId;
	}

	/**
	 * @param destinationPincodeId the destinationPincodeId to set
	 */
	public void setDestinationPincodeId(Integer destinationPincodeId) {
		this.destinationPincodeId = destinationPincodeId;
	}

	/**
	 * @return the consignmentTypeId
	 */
	public Integer getConsignmentTypeId() {
		return consignmentTypeId;
	}

	/**
	 * @param consignmentTypeId the consignmentTypeId to set
	 */
	public void setConsignmentTypeId(Integer consignmentTypeId) {
		this.consignmentTypeId = consignmentTypeId;
	}

	/**
	 * @return the cnContentsId
	 */
	public Integer getCnContentsId() {
		return cnContentsId;
	}

	/**
	 * @param cnContentsId the cnContentsId to set
	 */
	public void setCnContentsId(Integer cnContentsId) {
		this.cnContentsId = cnContentsId;
	}

	/**
	 * @return the cnPaperWorksId
	 */
	public Integer getCnPaperWorksId() {
		return cnPaperWorksId;
	}

	/**
	 * @param cnPaperWorksId the cnPaperWorksId to set
	 */
	public void setCnPaperWorksId(Integer cnPaperWorksId) {
		this.cnPaperWorksId = cnPaperWorksId;
	}

	/**
	 * @return the insuredById
	 */
	public Integer getInsuredById() {
		return insuredById;
	}

	/**
	 * @param insuredById the insuredById to set
	 */
	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
	}

	/**
	 * @return the duplicateChildCNs
	 */
	public Set<DuplicateChildConsignmentDO> getDuplicateChildCNs() {
		return duplicateChildCNs;
	}

	/**
	 * @param duplicateChildCNs the duplicateChildCNs to set
	 */
	public void setDuplicateChildCNs(
			Set<DuplicateChildConsignmentDO> duplicateChildCNs) {
		this.duplicateChildCNs = duplicateChildCNs;
	}

	/**
	 * @return the consignmentId
	 */
	public Integer getConsignmentId() {
		return consignmentId;
	}

	/**
	 * @param consignmentId the consignmentId to set
	 */
	public void setConsignmentId(Integer consignmentId) {
		this.consignmentId = consignmentId;
	}
	
}
