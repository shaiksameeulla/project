package com.ff.domain.consignment;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.billing.OpsmanConsignmentBillingRateDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.tracking.ProcessDO;

public class OpsmanConsignmentDO extends CGFactDO 
{
	private static final long serialVersionUID = 7446045642062207659L;
	private Integer consgId;
	private String consgNo;
	private Integer bookingTypeConsg;
	private Date bookingDateConsg;
	private Integer orgOffId;
	private PincodeDO destPincodeDO;
	private Integer noOfPcs = 1;
	private Integer consgType;
	private Double price;
	private Integer productId; // mandatory for consignment
	private String productCode; // mandatory for rate calculation
	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;
	private ProcessDO updatedProcess;
	private Double height;
	private Double length;
	private Double breath;
	private Integer cnContentId;
	private Integer cnPaperWorkId;
	private String otherCNContent;
	private String paperWorkRefNo;
	private String refNo;
	private Integer insuredBy;
	private String insurencePolicyNo;
	private Integer customerId;
	private Integer consigneeId;
	private Integer consignorId;
	private String consgStatus = "B"; // mandatory for consignment
	private String billingStatus = "TBB"; // mandatory for consignment
	private String changedAfterBillingWtDest = "N"; // mandatory for billing
	private String changedAfterNewRateCmpnt = "N"; // mandatory for billing
	private Double declaredValue;
	private Integer operatingOffice; // mandatory for consignment
	private String lcBankName; // optional for consignment
	private Date eventDate; // mandatory for consignment
	private Double topayAmt; // optional for consignment
	private Double codAmt; // conditional mandatory in consignment
	private Double lcAmt; // conditional mandatory in consignment
	private String servicedOn; // conditional mandatory in consignment
	private String rateType; // mandatory in consignment
	private String mobileNo;
	private String remarks;
	private ReasonDO cnReturnReason;
	private String isExcessConsg = "N";
	private Double baAmt;
	private Date deliveredDate;
	private String recvNameOrCompName;
	@JsonManagedReference
	private Set<OpsmanConsignmentBillingRateDO> consgRateDtls; // rates part
	
	// NON-PERSISTENT VARIABLE
	private CGBaseException cgBaseException;

	
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
	
	public Integer getBookingTypeConsg() {
		return bookingTypeConsg;
	}
	public void setBookingTypeConsg(Integer bookingTypeConsg) {
		this.bookingTypeConsg = bookingTypeConsg;
	}
	
	public Date getBookingDateConsg() {
		return bookingDateConsg;
	}
	public void setBookingDateConsg(Date bookingDateConsg) {
		this.bookingDateConsg = bookingDateConsg;
	}
	public Integer getOrgOffId() {
		return orgOffId;
	}
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}
	public PincodeDO getDestPincodeDO() {
		return destPincodeDO;
	}
	public void setDestPincodeDO(PincodeDO destPincodeDO) {
		this.destPincodeDO = destPincodeDO;
	}
	public Integer getNoOfPcs() {
		return noOfPcs;
	}
	public void setNoOfPcs(Integer noOfPcs) {
		this.noOfPcs = noOfPcs;
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getConsigneeId() {
		return consigneeId;
	}
	public void setConsigneeId(Integer consigneeId) {
		this.consigneeId = consigneeId;
	}
	public Integer getConsignorId() {
		return consignorId;
	}
	public void setConsignorId(Integer consignorId) {
		this.consignorId = consignorId;
	}
	public String getConsgStatus() {
		return consgStatus;
	}
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
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
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public Integer getOperatingOffice() {
		return operatingOffice;
	}
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}
	public String getLcBankName() {
		return lcBankName;
	}
	public void setLcBankName(String lcBankName) {
		this.lcBankName = lcBankName;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Double getTopayAmt() {
		return topayAmt;
	}
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}
	public Double getCodAmt() {
		return codAmt;
	}
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}
	public Double getLcAmt() {
		return lcAmt;
	}
	public void setLcAmt(Double lcAmt) {
		this.lcAmt = lcAmt;
	}
	public String getServicedOn() {
		return servicedOn;
	}
	public void setServicedOn(String servicedOn) {
		this.servicedOn = servicedOn;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public ReasonDO getCnReturnReason() {
		return cnReturnReason;
	}
	public void setCnReturnReason(ReasonDO cnReturnReason) {
		this.cnReturnReason = cnReturnReason;
	}
	public String getIsExcessConsg() {
		return isExcessConsg;
	}
	public void setIsExcessConsg(String isExcessConsg) {
		this.isExcessConsg = isExcessConsg;
	}
	public Double getBaAmt() {
		return baAmt;
	}
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}
	public Set<OpsmanConsignmentBillingRateDO> getConsgRateDtls() {
		return consgRateDtls;
	}
	public void setConsgRateDtls(Set<OpsmanConsignmentBillingRateDO> consgRateDtls) {
		this.consgRateDtls = consgRateDtls;
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
	public CGBaseException getCgBaseException() {
		return cgBaseException;
	}
	public void setCgBaseException(CGBaseException cgBaseException) {
		this.cgBaseException = cgBaseException;
	}
	
}
