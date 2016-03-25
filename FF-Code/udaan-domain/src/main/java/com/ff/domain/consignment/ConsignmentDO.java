package com.ff.domain.consignment;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsignmentDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1277125712275929468L;
	private Integer consgId;
	private String consgNo;
	private Integer orgOffId;
	private PincodeDO destPincodeId;
	// No of pieces are not applicable for docs booking type
	private Integer noOfPcs = 1;
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

	private Double baAmt;
	
	// For DRS Date Time
	private Date deliveryDateTime;

	// For rate compomenets
	@JsonManagedReference
	private Set<ConsignmentBillingRateDO> consgRateDtls;
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

	private Integer operatingOffice;

	// for Billing
	private String billingStatus = "TBB";
	private String changedAfterBillingWtDest = "N";
	private String changedAfterNewRateCmpnt = "N";

	private Date eventDate;
	private Integer customer;
	private ReasonDO cnReturnReason;
	private String remarks;
	private ConsigneeConsignorDO altConsigneeAddr;
	private ReasonDO stopReason;
	private String stopDelivery = "N";
	private Date stopDelvDate;
	
	private ProductDO productDO;
	
	private String misRouted = "N";
	private String isExcessConsg = CommonConstants.NO;
	
	
	
	private String codAmtStr;
	//non-persistant
	private String errorCode;
	private String lcAmtStr;
	//private int excelRowId;
	private Integer stopDeliveryReqOff;
	
	
	private boolean isPickedup = false;
	
	/**
	 * @return the isPickedup
	 */
	@JsonIgnore
	public boolean isPickedup() {
		return isPickedup;
	}

	/**
	 * @param isPickedup the isPickedup to set
	 */
	public void setPickedup(boolean isPickedup) {
		this.isPickedup = isPickedup;
	}

	/**
	 * @return the lcAmtStr
	 */
	public String getLcAmtStr() {
		return lcAmtStr;
	}

	/**
	 * @param lcAmtStr the lcAmtStr to set
	 */
	public void setLcAmtStr(String lcAmtStr) {
		this.lcAmtStr = lcAmtStr;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the codAmtStr
	 */
	public String getCodAmtStr() {
		return codAmtStr;
	}

	/**
	 * @param codAmtStr the codAmtStr to set
	 */
	public void setCodAmtStr(String codAmtStr) {
		this.codAmtStr = codAmtStr;
	}

	/**
	 * @return the misRouted
	 */
	public String getMisRouted() {
		return misRouted;
	}

	/**
	 * @param misRouted the misRouted to set
	 */
	public void setMisRouted(String misRouted) {
		this.misRouted = misRouted;
	}

	/**
	 * @return the productDO
	 */
	public ProductDO getProductDO() {
		return productDO;
	}

	/**
	 * @param productDO the productDO to set
	 */
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
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
	 * @param topayAmt
	 *            the topayAmt to set
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
	 * @param splChg
	 *            the splChg to set
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
	 * @param declaredValue
	 *            the declaredValue to set
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
	 * @param codAmt
	 *            the codAmt to set
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
	 * @param lcAmount
	 *            the lcAmount to set
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
	 * @param lcBankName
	 *            the lcBankName to set
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
	 * @param servicedOn
	 *            the servicedOn to set
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
	 * @param rateType
	 *            the rateType to set
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
	 * @param ebPreferencesCodes
	 *            the ebPreferencesCodes to set
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
	 * @return the consgRateDtls
	 */
	public Set<ConsignmentBillingRateDO> getConsgRateDtls() {
		return consgRateDtls;
	}

	/**
	 * @param consgRateDtls
	 *            the consgRateDtls to set
	 */
	public void setConsgRateDtls(Set<ConsignmentBillingRateDO> consgRateDtls) {
		this.consgRateDtls = consgRateDtls;
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
	 * @return the altConsigneeAddr
	 */
	public ConsigneeConsignorDO getAltConsigneeAddr() {
		return altConsigneeAddr;
	}

	/**
	 * @param altConsigneeAddr
	 *            the altConsigneeAddr to set
	 */
	public void setAltConsigneeAddr(ConsigneeConsignorDO altConsigneeAddr) {
		this.altConsigneeAddr = altConsigneeAddr;
	}

	public ReasonDO getStopReason() {
		return stopReason;
	}

	public void setStopReason(ReasonDO stopReason) {
		this.stopReason = stopReason;
	}

	public String getStopDelivery() {
		return stopDelivery;
	}

	public void setStopDelivery(String stopDelivery) {
		this.stopDelivery = stopDelivery;
	}

	public Date getStopDelvDate() {
		return stopDelvDate;
	}

	public void setStopDelvDate(Date stopDelvDate) {
		this.stopDelvDate = stopDelvDate;
	}

	/**
	 * @return the isExcessConsg
	 */
	public String getIsExcessConsg() {
		return isExcessConsg;
	}

	/**
	 * @param isExcessConsg the isExcessConsg to set
	 */
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}

	/**
	 * @return the baAmt
	 */
	public Double getBaAmt() {
		return baAmt;
	}

	/**
	 * @param baAmt the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}

	public Integer getStopDeliveryReqOff() {
		return stopDeliveryReqOff;
	}

	public void setStopDeliveryReqOff(Integer stopDeliveryReqOff) {
		this.stopDeliveryReqOff = stopDeliveryReqOff;
	}
	
	
	/*public int getExcelRowId() {
		return excelRowId;
	}

	public void setExcelRowId(int excelRowId) {
		this.excelRowId = excelRowId;
	}*/
}
