package com.ff.domain.billing;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.tracking.ProcessDO;

/**
 * The Class BillingConsignmentDO.
 *
 * @author narmdr
 */
public class BillingConsignmentDO extends CGFactDO {
	
	private static final long serialVersionUID = 8205369451143324077L;
	private Integer billingConsignmentId;
	private Integer consgId;
	private String consgNo;
	private Integer originOfficeId;
	private Integer noOfPcs;
	private Double price;
	private Double actualWeight;
	private Double volWeight;
	private Double finalWeight;
	private String mobileNo;
	private String insurancePolicyNo;
	private String refNo;
	private Double height;
	private Double length;
	private Double breadth;
	private Date receivedDate;
	private Date dlvDateTime;
	private String recvNameOrCompSeal;
	private String receivedStatus;
	private String consgStatus;
	private Double declaredValue;
	private String scope;
	private Integer consignmentCopy;
	private Integer operatingLevel;
	public Integer invoiceId;
	
	private ProductDO productDO;
	private PincodeDO destPincodeDO;	
	private ConsignmentTypeDO consignmentTypeDO;
	private ProcessDO updatedProcessDO;
	private CNContentDO cnContentDO;
	private CNPaperWorksDO cnPaperWorksDO;
	private InsuredByDO insuredByDO;
	private ConsigneeConsignorDO consignor;
	private ConsigneeConsignorDO consignee;
	private BillingConsignmentSummaryDO billingConsignmentSummaryDO;
	//private BillDO billDO;
	private Integer version;
	
	private Set<BillingConsignmentRateDO> billingConsignmentRateDOs;
	
	/**
	 * @return the billingConsignmentId
	 */
	public Integer getBillingConsignmentId() {
		return billingConsignmentId;
	}
	/**
	 * @param billingConsignmentId the billingConsignmentId to set
	 */
	public void setBillingConsignmentId(Integer billingConsignmentId) {
		this.billingConsignmentId = billingConsignmentId;
	}
	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}
	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
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
	 * @return the originOfficeId
	 */
	public Integer getOriginOfficeId() {
		return originOfficeId;
	}
	/**
	 * @param originOfficeId the originOfficeId to set
	 */
	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
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
	 * @return the insurancePolicyNo
	 */
	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}
	/**
	 * @param insurancePolicyNo the insurancePolicyNo to set
	 */
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
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
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}
	/**
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Double length) {
		this.length = length;
	}
	/**
	 * @return the breadth
	 */
	public Double getBreadth() {
		return breadth;
	}
	/**
	 * @param breadth the breadth to set
	 */
	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}
	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}
	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	/**
	 * @return the dlvDateTime
	 */
	public Date getDlvDateTime() {
		return dlvDateTime;
	}
	/**
	 * @param dlvDateTime the dlvDateTime to set
	 */
	public void setDlvDateTime(Date dlvDateTime) {
		this.dlvDateTime = dlvDateTime;
	}
	/**
	 * @return the recvNameOrCompSeal
	 */
	public String getRecvNameOrCompSeal() {
		return recvNameOrCompSeal;
	}
	/**
	 * @param recvNameOrCompSeal the recvNameOrCompSeal to set
	 */
	public void setRecvNameOrCompSeal(String recvNameOrCompSeal) {
		this.recvNameOrCompSeal = recvNameOrCompSeal;
	}
	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}
	/**
	 * @param receivedStatus the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
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
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	/**
	 * @return the consignmentCopy
	 */
	public Integer getConsignmentCopy() {
		return consignmentCopy;
	}
	/**
	 * @param consignmentCopy the consignmentCopy to set
	 */
	public void setConsignmentCopy(Integer consignmentCopy) {
		this.consignmentCopy = consignmentCopy;
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
	 * @return the destPincodeDO
	 */
	public PincodeDO getDestPincodeDO() {
		return destPincodeDO;
	}
	/**
	 * @param destPincodeDO the destPincodeDO to set
	 */
	public void setDestPincodeDO(PincodeDO destPincodeDO) {
		this.destPincodeDO = destPincodeDO;
	}
	/**
	 * @return the consignmentTypeDO
	 */
	public ConsignmentTypeDO getConsignmentTypeDO() {
		return consignmentTypeDO;
	}
	/**
	 * @param consignmentTypeDO the consignmentTypeDO to set
	 */
	public void setConsignmentTypeDO(ConsignmentTypeDO consignmentTypeDO) {
		this.consignmentTypeDO = consignmentTypeDO;
	}
	/**
	 * @return the updatedProcessDO
	 */
	public ProcessDO getUpdatedProcessDO() {
		return updatedProcessDO;
	}
	/**
	 * @param updatedProcessDO the updatedProcessDO to set
	 */
	public void setUpdatedProcessDO(ProcessDO updatedProcessDO) {
		this.updatedProcessDO = updatedProcessDO;
	}
	/**
	 * @return the cnContentDO
	 */
	public CNContentDO getCnContentDO() {
		return cnContentDO;
	}
	/**
	 * @param cnContentDO the cnContentDO to set
	 */
	public void setCnContentDO(CNContentDO cnContentDO) {
		this.cnContentDO = cnContentDO;
	}
	/**
	 * @return the cnPaperWorksDO
	 */
	public CNPaperWorksDO getCnPaperWorksDO() {
		return cnPaperWorksDO;
	}
	/**
	 * @param cnPaperWorksDO the cnPaperWorksDO to set
	 */
	public void setCnPaperWorksDO(CNPaperWorksDO cnPaperWorksDO) {
		this.cnPaperWorksDO = cnPaperWorksDO;
	}
	/**
	 * @return the insuredByDO
	 */
	public InsuredByDO getInsuredByDO() {
		return insuredByDO;
	}
	/**
	 * @param insuredByDO the insuredByDO to set
	 */
	public void setInsuredByDO(InsuredByDO insuredByDO) {
		this.insuredByDO = insuredByDO;
	}
	/**
	 * @return the consignor
	 */
	public ConsigneeConsignorDO getConsignor() {
		return consignor;
	}
	/**
	 * @param consignor the consignor to set
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
	 * @param consignee the consignee to set
	 */
	public void setConsignee(ConsigneeConsignorDO consignee) {
		this.consignee = consignee;
	}
	/**
	 * @return the billingConsignmentSummaryDO
	 */
	public BillingConsignmentSummaryDO getBillingConsignmentSummaryDO() {
		return billingConsignmentSummaryDO;
	}
	/**
	 * @param billingConsignmentSummaryDO the billingConsignmentSummaryDO to set
	 */
	public void setBillingConsignmentSummaryDO(
			BillingConsignmentSummaryDO billingConsignmentSummaryDO) {
		this.billingConsignmentSummaryDO = billingConsignmentSummaryDO;
	}
	/**
	 * @return the billDO
	 *//*
	public BillDO getBillDO() {
		return billDO;
	}
	*//**
	 * @param billDO the billDO to set
	 *//*
	public void setBillDO(BillDO billDO) {
		this.billDO = billDO;
	}*/
	/**
	 * @return the operatingLevel
	 */
	public Integer getOperatingLevel() {
		return operatingLevel;
	}
	/**
	 * @param operatingLevel the operatingLevel to set
	 */
	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}
	/**
	 * @return the billingConsignmentRateDOs
	 */
	public Set<BillingConsignmentRateDO> getBillingConsignmentRateDOs() {
		return billingConsignmentRateDOs;
	}
	/**
	 * @param billingConsignmentRateDOs the billingConsignmentRateDOs to set
	 */
	public void setBillingConsignmentRateDOs(
			Set<BillingConsignmentRateDO> billingConsignmentRateDOs) {
		this.billingConsignmentRateDOs = billingConsignmentRateDOs;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
	    return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
	    this.version = version;
	}
	/**
	 * @return the invoiceId
	 */
	public Integer getInvoiceId() {
	    return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Integer invoiceId) {
	    this.invoiceId = invoiceId;
	}
	
}
