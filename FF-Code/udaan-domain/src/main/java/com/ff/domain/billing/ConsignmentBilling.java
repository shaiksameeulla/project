package com.ff.domain.billing;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;

public class ConsignmentBilling extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1277125712275929468L;
	private Integer consgId;
	private String consgNo;
	private Integer orgOffId;
	private PincodeDO destPincodeId;
	private Integer noOfPcs;
	private String processStatus;
	private ConsignmentTypeDO consgType;
	private Double price;
	private Integer productId;
	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;
	private ProcessDO updatedProcess;
	@JsonManagedReference
	private Set<ChildConsignmentDO> childCNs;
	private Double height;
	private Double length;
	private Double breath;
	private CNContentDO cnContentId;
	private CNPaperWorksDO cnPaperWorkId;
	private String otherCNContent;
	private String paperWorkRefNo;
	private String insurencePolicyNo;
	private String refNo;
	private InsuredByDO insuredBy;
	private String mobileNo;
	private String consgStatus = "B";

	// For POD
	private Date receivedDateTime;
	private Date deliveredDate;
	private String recvNameOrCompName;
	private String receivedStatus;
	private ConsigneeConsignorDO consignor;
	private ConsigneeConsignorDO consignee;

	// For DRS Date Time
	private Date deliveryDateTime;

	// For rate compomenets
	/*@JsonManagedReference
	private Set<ConsignmentBillingRateDO> consgRateDtls;*/
	// private CNPricingDetailsDO consgPricingDtls;
	private Double discount;
	private Double topayAmt;
	private Double splChg;
	private Double declaredValue;
	private Double codAmt;
	private Double lcAmount;
	private String lcBankName;
	private String servicedOn;
	private String rateType;
	private String ebPreferencesCodes;

	/*private Integer operatingLevel;*/
	private Integer operatingOffice;

	// for Billing
	private String billingStatus = "TBB";
	private String changedAfterBillingWtDest = "N";
	private String changedAfterNewRateCmpnt = "N";

	private Date eventDate;
	private Integer customer;
	private ReasonDO cnReturnReason;
	private String remarks;
	private String BOOKING_RATE_BILLED;
	private String RTO_RATE_BILLED;
	private String SHIP_TO_CODE;
	private String CONSG_SERIES;
	private Date BOOKING_DATE;
	private Integer CONSOLIDATION_WINDOW; 
	private String BOOKING_TYPE;
	private String CONSIGNMENT_CODE; 
	private Integer OFFICE_ID;
	private String PRODUCT_CODE;
	private Integer CONSIGNMENT_RATE_ID; 
	private String CUSTOMER_TYPE_CODE; 
	private String BOOKING_TYPE_DESC; 
	private String PRODUCT_NAME;
	private String INSURED_BY_CODE; 
	//private String MODIFIED;
	
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

	/**
	 * @return the consgStatus
	 */
	public String getConsgStatus() {
		return consgStatus;
	}

	/**
	 * @param consgStatus
	 *            the consgStatus to set
	 */
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}

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

	public PincodeDO getDestPincodeId() {
		return destPincodeId;
	}

	public void setDestPincodeId(PincodeDO destPincodeId) {
		this.destPincodeId = destPincodeId;
	}

	/**
	 * @return the noOfPcs
	 */
	public Integer getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * @param noOfPcs
	 *            the noOfPcs to set
	 */
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public ConsignmentTypeDO getConsgType() {
		return consgType;
	}

	public void setConsgType(ConsignmentTypeDO consgType) {
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

	public ProcessDO getUpdatedProcess() {
		return updatedProcess;
	}

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

	public CNContentDO getCnContentId() {
		return cnContentId;
	}

	public void setCnContentId(CNContentDO cnContentId) {
		this.cnContentId = cnContentId;
	}

	public CNPaperWorksDO getCnPaperWorkId() {
		return cnPaperWorkId;
	}

	public void setCnPaperWorkId(CNPaperWorksDO cnPaperWorkId) {
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

	public InsuredByDO getInsuredBy() {
		return insuredBy;
	}

	public void setInsuredBy(InsuredByDO insuredBy) {
		this.insuredBy = insuredBy;
	}

	public String getMobileNo() {
		return mobileNo;
	}

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
	 * @param receivedDateTime
	 *            the receivedDateTime to set
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
	 * @param deliveredDate
	 *            the deliveredDate to set
	 */
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	/**
	 * @return the recvNameOrCompName
	 */
	public String getRecvNameOrCompName() {
		return recvNameOrCompName;
	}

	/**
	 * @param recvNameOrCompName
	 *            the recvNameOrCompName to set
	 */
	public void setRecvNameOrCompName(String recvNameOrCompName) {
		this.recvNameOrCompName = recvNameOrCompName;
	}

	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * @param receivedStatus
	 *            the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * @return the consignor
	 */
	public ConsigneeConsignorDO getConsignor() {
		return consignor;
	}

	/**
	 * @param consignor
	 *            the consignor to set
	 */
	public void setConsignor(ConsigneeConsignorDO consignor) {
		this.consignor = consignor;
	}

	/**
	 * @return the consignee
	 */
	public ConsigneeConsignorDO getConsignee() {
		return consignee;
	}

	/**
	 * @param consignee
	 *            the consignee to set
	 */
	public void setConsignee(ConsigneeConsignorDO consignee) {
		this.consignee = consignee;
	}

	/**
	 * @return the deliveryDateTime
	 */
	public Date getDeliveryDateTime() {
		return deliveryDateTime;
	}

	/**
	 * @param deliveryDateTime
	 *            the deliveryDateTime to set
	 */
	public void setDeliveryDateTime(Date deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}

	/**
	 * @return the declaredValue
	 */
	/*
	 * public Double getDeclaredValue() { return declaredValue; }
	 *//**
	 * @param declaredValue
	 *            the declaredValue to set
	 */
	/*
	 * public void setDeclaredValue(Double declaredValue) { this.declaredValue =
	 * declaredValue; }
	 */

	/**
	 * @return the operatingLevel
	 */
	/*public Integer getOperatingLevel() {
		return operatingLevel;
	}*/


	/**
	 * @param operatingLevel
	 *            the operatingLevel to set
	 */
	/*public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}*/

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
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @param operatingOffice
	 *            the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @return the lcBankName
	 */
	/*
	 * public String getLcBankName() { return lcBankName; }
	 *//**
	 * @param lcBankName
	 *            the lcBankName to set
	 */
	/*
	 * public void setLcBankName(String lcBankName) { this.lcBankName =
	 * lcBankName; }
	 */

	/**
	 * @return the consgPricingDtls
	 */
	/*
	 * public CNPricingDetailsDO getConsgPricingDtls() { return
	 * consgPricingDtls; }
	 *//**
	 * @param consgPricingDtls
	 *            the consgPricingDtls to set
	 */
	/*
	 * public void setConsgPricingDtls(CNPricingDetailsDO consgPricingDtls) {
	 * this.consgPricingDtls = consgPricingDtls; }
	 */

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the customer
	 */
	public Integer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public ReasonDO getCnReturnReason() {
		return cnReturnReason;
	}

	public void setCnReturnReason(ReasonDO cnReturnReason) {
		this.cnReturnReason = cnReturnReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the bOOKING_RATE_BILLED
	 */
	public String getBOOKING_RATE_BILLED() {
		return BOOKING_RATE_BILLED;
	}

	/**
	 * @param bOOKING_RATE_BILLED the bOOKING_RATE_BILLED to set
	 */
	public void setBOOKING_RATE_BILLED(String bOOKING_RATE_BILLED) {
		BOOKING_RATE_BILLED = bOOKING_RATE_BILLED;
	}

	/**
	 * @return the rTO_RATE_BILLED
	 */
	public String getRTO_RATE_BILLED() {
		return RTO_RATE_BILLED;
	}

	/**
	 * @param rTO_RATE_BILLED the rTO_RATE_BILLED to set
	 */
	public void setRTO_RATE_BILLED(String rTO_RATE_BILLED) {
		RTO_RATE_BILLED = rTO_RATE_BILLED;
	}

	/**
	 * @return the sHIP_TO_CODE
	 */
	public String getSHIP_TO_CODE() {
		return SHIP_TO_CODE;
	}

	/**
	 * @param sHIP_TO_CODE the sHIP_TO_CODE to set
	 */
	public void setSHIP_TO_CODE(String sHIP_TO_CODE) {
		SHIP_TO_CODE = sHIP_TO_CODE;
	}

	public String getCONSG_SERIES() {
		return CONSG_SERIES;
	}

	public void setCONSG_SERIES(String cONSG_SERIES) {
		CONSG_SERIES = cONSG_SERIES;
	}

	public Date getBOOKING_DATE() {
		return BOOKING_DATE;
	}

	public void setBOOKING_DATE(Date bOOKING_DATE) {
		BOOKING_DATE = bOOKING_DATE;
	}

	public Integer getCONSOLIDATION_WINDOW() {
		return CONSOLIDATION_WINDOW;
	}

	public void setCONSOLIDATION_WINDOW(Integer cONSOLIDATION_WINDOW) {
		CONSOLIDATION_WINDOW = cONSOLIDATION_WINDOW;
	}

	public String getBOOKING_TYPE() {
		return BOOKING_TYPE;
	}

	public void setBOOKING_TYPE(String bOOKING_TYPE) {
		BOOKING_TYPE = bOOKING_TYPE;
	}

	public String getCONSIGNMENT_CODE() {
		return CONSIGNMENT_CODE;
	}

	public void setCONSIGNMENT_CODE(String cONSIGNMENT_CODE) {
		CONSIGNMENT_CODE = cONSIGNMENT_CODE;
	}

	public Integer getOFFICE_ID() {
		return OFFICE_ID;
	}

	public void setOFFICE_ID(Integer oFFICE_ID) {
		OFFICE_ID = oFFICE_ID;
	}

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}

	public Integer getCONSIGNMENT_RATE_ID() {
		return CONSIGNMENT_RATE_ID;
	}

	public void setCONSIGNMENT_RATE_ID(Integer cONSIGNMENT_RATE_ID) {
		CONSIGNMENT_RATE_ID = cONSIGNMENT_RATE_ID;
	}

	public String getCUSTOMER_TYPE_CODE() {
		return CUSTOMER_TYPE_CODE;
	}

	public void setCUSTOMER_TYPE_CODE(String cUSTOMER_TYPE_CODE) {
		CUSTOMER_TYPE_CODE = cUSTOMER_TYPE_CODE;
	}

	public String getBOOKING_TYPE_DESC() {
		return BOOKING_TYPE_DESC;
	}

	public void setBOOKING_TYPE_DESC(String bOOKING_TYPE_DESC) {
		BOOKING_TYPE_DESC = bOOKING_TYPE_DESC;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getINSURED_BY_CODE() {
		return INSURED_BY_CODE;
	}

	public void setINSURED_BY_CODE(String iNSURED_BY_CODE) {
		INSURED_BY_CODE = iNSURED_BY_CODE;
	}

	/*public String getMODIFIED() {
		return MODIFIED;
	}

	public void setMODIFIED(String mODIFIED) {
		MODIFIED = mODIFIED;
	}
*/
	
	
}
