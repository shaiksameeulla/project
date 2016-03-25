package com.ff.domain.consignment;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.tracking.ProcessDO;

public class BcunConsignmentDO extends CGFactDO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6250820658710267087L;
	private Integer consgId;
	private String consgNo;
	private Integer orgOffId;
	private Integer destPincodeId;
	private Integer noOfPcs;
	private String processStatus;
	private Integer consgType;
	private Double price;
	private Integer productId;
	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;
	private ProcessDO updatedProcess;
	private Set<ChildConsignmentDO> childCNs;
	private Double height;
	private Double length;
	private Double breath;
	private Integer cnContentId;
	private Integer cnPaperWorkId;
	private String otherCNContent;
	private String paperWorkRefNo;
	private String insurencePolicyNo;
	private String refNo;
	private Integer insuredBy;
	private String mobileNo;
	private String consgStatus = "B";

	// For POD
	private Date receivedDateTime;
	private Date deliveredDate;
	private String recvNameOrCompName;
	private String receivedStatus;
	private Integer consignor;
	private Integer consignee;

	// For DRS Date Time
	private Date deliveryDateTime;

	private Double declaredValue;

	private Integer operatingLevel;
	private Integer operatingOffice;

	// for Billing
	private String billingStatus = "TBB";
	private String changedAfterBillingWtDest = "N";
	private String changedAfterNewRateCmpnt = "N";
	
	//private CNPricingDetailsDO consgPricingDtls;
	private Double discount;
	private Double topayAmt;
	private Double splChg;
	private Double codAmt;
	private Double lcAmount;
	private String lcBankName;
	private String servicedOn;
	private String rateType;
	private String ebPreferencesCodes;
	
	public Integer getConsgId() {
		return consgId;
	}
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	public String getConsgNo() {
		return consgNo;
	}
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	public Integer getOrgOffId() {
		return orgOffId;
	}
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}
	public Integer getDestPincodeId() {
		return destPincodeId;
	}
	public void setDestPincodeId(Integer destPincodeId) {
		this.destPincodeId = destPincodeId;
	}
	public Integer getNoOfPcs() {
		return noOfPcs;
	}
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public Integer getConsgType() {
		return consgType;
	}
	public void setConsgType(Integer consgType) {
		this.consgType = consgType;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public Double getVolWeight() {
		return volWeight;
	}
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}
	public Double getFinalWeight() {
		return finalWeight;
	}
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}
	
	/**
	 * @return the updatedProcess
	 */
	public ProcessDO getUpdatedProcess() {
		return updatedProcess;
	}
	/**
	 * @param updatedProcess the updatedProcess to set
	 */
	public void setUpdatedProcess(ProcessDO updatedProcess) {
		this.updatedProcess = updatedProcess;
	}
	public Set<ChildConsignmentDO> getChildCNs() {
		return childCNs;
	}
	public void setChildCNs(Set<ChildConsignmentDO> childCNs) {
		this.childCNs = childCNs;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getBreath() {
		return breath;
	}
	public void setBreath(Double breath) {
		this.breath = breath;
	}
	public Integer getCnContentId() {
		return cnContentId;
	}
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}
	public Integer getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	public void setCnPaperWorkId(Integer cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	public String getOtherCNContent() {
		return otherCNContent;
	}
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}
	public String getPaperWorkRefNo() {
		return paperWorkRefNo;
	}
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public Integer getInsuredBy() {
		return insuredBy;
	}
	public void setInsuredBy(Integer insuredBy) {
		this.insuredBy = insuredBy;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getConsgStatus() {
		return consgStatus;
	}
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
	public Date getReceivedDateTime() {
		return receivedDateTime;
	}
	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}
	public Date getDeliveredDate() {
		return deliveredDate;
	}
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	public String getRecvNameOrCompName() {
		return recvNameOrCompName;
	}
	public void setRecvNameOrCompName(String recvNameOrCompName) {
		this.recvNameOrCompName = recvNameOrCompName;
	}
	public String getReceivedStatus() {
		return receivedStatus;
	}
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	public Integer getConsignor() {
		return consignor;
	}
	public void setConsignor(Integer consignor) {
		this.consignor = consignor;
	}
	public Integer getConsignee() {
		return consignee;
	}
	public void setConsignee(Integer consignee) {
		this.consignee = consignee;
	}
	public Date getDeliveryDateTime() {
		return deliveryDateTime;
	}
	public void setDeliveryDateTime(Date deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public Integer getOperatingLevel() {
		return operatingLevel;
	}
	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}
	public Integer getOperatingOffice() {
		return operatingOffice;
	}
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getChangedAfterBillingWtDest() {
		return changedAfterBillingWtDest;
	}
	public void setChangedAfterBillingWtDest(String changedAfterBillingWtDest) {
		this.changedAfterBillingWtDest = changedAfterBillingWtDest;
	}
	public String getChangedAfterNewRateCmpnt() {
		return changedAfterNewRateCmpnt;
	}
	public void setChangedAfterNewRateCmpnt(String changedAfterNewRateCmpnt) {
		this.changedAfterNewRateCmpnt = changedAfterNewRateCmpnt;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	/**
	 * @return the topayAmt
	 */
	public Double getTopayAmt() {
		return topayAmt;
	}
	/**
	 * @param topayAmt the topayAmt to set
	 */
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}
	/**
	 * @return the splChg
	 */
	public Double getSplChg() {
		return splChg;
	}
	/**
	 * @param splChg the splChg to set
	 */
	public void setSplChg(Double splChg) {
		this.splChg = splChg;
	}
	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}
	/**
	 * @param codAmt the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}
	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
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
	 * @return the servicedOn
	 */
	public String getServicedOn() {
		return servicedOn;
	}
	/**
	 * @param servicedOn the servicedOn to set
	 */
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	/**
	 * @return the ebPreferencesCodes
	 */
	public String getEbPreferencesCodes() {
		return ebPreferencesCodes;
	}
	/**
	 * @param ebPreferencesCodes the ebPreferencesCodes to set
	 */
	public void setEbPreferencesCodes(String ebPreferencesCodes) {
		this.ebPreferencesCodes = ebPreferencesCodes;
	}
	
	
	
	
}
