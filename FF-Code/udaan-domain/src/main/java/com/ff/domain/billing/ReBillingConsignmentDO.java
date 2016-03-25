package com.ff.domain.billing;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;

public class ReBillingConsignmentDO extends CGFactDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4319537216321570320L;
	private Integer reBillingConsignmentId;
	private Integer reBillId;
	private Integer consgId;
	private String consgNo;
	private Integer orgOffId;
	private PincodeDO destPincodeDO;
	private Integer noOfPcs;
	private ConsignmentTypeDO consignmentTypeDO;
	private Double price;
	private ProductDO productDO;
	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;
	private ProcessDO updatedProcessDO;
	private String mobileNo;
	private CNContentDO cnContentDO;
	private CNPaperWorksDO cnPaperWorksDO;
	private InsuredByDO insuredByDO;
	private String insurencePolicyNo;
	private String refNo;
	private Double height;
	private Double length;
	private Double breath;
	private Date receivedDateTime;
	private Date deliveredDate;
	private String recvNameOrCompName;
	private String receivedStatus;
	private ConsigneeConsignorDO consignor;
	private ConsigneeConsignorDO consignee;
	private Double declaredValue;
	private String consgStatus;
	private Integer billingConsgId;
	
	public Integer getReBillingConsignmentId() {
		return reBillingConsignmentId;
	}
	public void setReBillingConsignmentId(Integer reBillingConsignmentId) {
		this.reBillingConsignmentId = reBillingConsignmentId;
	}
	public Integer getReBillId() {
		return reBillId;
	}
	public void setReBillId(Integer reBillId) {
		this.reBillId = reBillId;
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
	public ConsignmentTypeDO getConsignmentTypeDO() {
		return consignmentTypeDO;
	}
	public void setConsignmentTypeDO(ConsignmentTypeDO consignmentTypeDO) {
		this.consignmentTypeDO = consignmentTypeDO;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
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
	public ProcessDO getUpdatedProcessDO() {
		return updatedProcessDO;
	}
	public void setUpdatedProcessDO(ProcessDO updatedProcessDO) {
		this.updatedProcessDO = updatedProcessDO;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public CNContentDO getCnContentDO() {
		return cnContentDO;
	}
	public void setCnContentDO(CNContentDO cnContentDO) {
		this.cnContentDO = cnContentDO;
	}
	public CNPaperWorksDO getCnPaperWorksDO() {
		return cnPaperWorksDO;
	}
	public void setCnPaperWorksDO(CNPaperWorksDO cnPaperWorksDO) {
		this.cnPaperWorksDO = cnPaperWorksDO;
	}
	public InsuredByDO getInsuredByDO() {
		return insuredByDO;
	}
	public void setInsuredByDO(InsuredByDO insuredByDO) {
		this.insuredByDO = insuredByDO;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
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
	public String getReceivedStatus() {
		return receivedStatus;
	}
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	public ConsigneeConsignorDO getConsignor() {
		return consignor;
	}
	public void setConsignor(ConsigneeConsignorDO consignor) {
		this.consignor = consignor;
	}
	public ConsigneeConsignorDO getConsignee() {
		return consignee;
	}
	public void setConsignee(ConsigneeConsignorDO consignee) {
		this.consignee = consignee;
	}
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getConsgStatus() {
		return consgStatus;
	}
	public void setConsgStatus(String consgStatus) {
		this.consgStatus = consgStatus;
	}
	public Integer getOrgOffId() {
		return orgOffId;
	}
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}
	public Double getBreath() {
		return breath;
	}
	public void setBreath(Double breath) {
		this.breath = breath;
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
	public Integer getBillingConsgId() {
		return billingConsgId;
	}
	public void setBillingConsgId(Integer billingConsgId) {
		this.billingConsgId = billingConsgId;
	}
	
   
}
