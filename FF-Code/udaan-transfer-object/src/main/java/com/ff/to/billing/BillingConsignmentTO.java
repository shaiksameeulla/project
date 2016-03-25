package com.ff.to.billing;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class BillingConsignmentTO.
 *
 * @author narmdr
 */
public class BillingConsignmentTO extends CGBaseTO {

	private static final long serialVersionUID = 384422829794231966L;
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
	
	private ProductTO productTO;
	private PincodeTO destPincodeTO;	
	private ConsignmentTypeTO consignmentTypeTO;
	private ProcessTO updatedProcessTO;
	private CNContentTO cnContentTO;
	private CNPaperWorksTO cnPaperWorksTO;
	private InsuredByTO insuredByTO;
	private ConsignorConsigneeTO consignorTO;
	private ConsignorConsigneeTO consigneeTO;
	private BillingConsignmentSummaryTO billingConsignmentSummaryTO;
	private BillTO billTO;
	private Integer version;
	
	private List<BillingConsignmentRateTO> billingConsignmentRateTOs;

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
	 * @return the productTO
	 */
	public ProductTO getProductTO() {
		return productTO;
	}

	/**
	 * @param productTO the productTO to set
	 */
	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}

	/**
	 * @return the destPincodeTO
	 */
	public PincodeTO getDestPincodeTO() {
		return destPincodeTO;
	}

	/**
	 * @param destPincodeTO the destPincodeTO to set
	 */
	public void setDestPincodeTO(PincodeTO destPincodeTO) {
		this.destPincodeTO = destPincodeTO;
	}

	/**
	 * @return the consignmentTypeTO
	 */
	public ConsignmentTypeTO getConsignmentTypeTO() {
		return consignmentTypeTO;
	}

	/**
	 * @param consignmentTypeTO the consignmentTypeTO to set
	 */
	public void setConsignmentTypeTO(ConsignmentTypeTO consignmentTypeTO) {
		this.consignmentTypeTO = consignmentTypeTO;
	}

	/**
	 * @return the updatedProcessTO
	 */
	public ProcessTO getUpdatedProcessTO() {
		return updatedProcessTO;
	}

	/**
	 * @param updatedProcessTO the updatedProcessTO to set
	 */
	public void setUpdatedProcessTO(ProcessTO updatedProcessTO) {
		this.updatedProcessTO = updatedProcessTO;
	}

	/**
	 * @return the cnContentTO
	 */
	public CNContentTO getCnContentTO() {
		return cnContentTO;
	}

	/**
	 * @param cnContentTO the cnContentTO to set
	 */
	public void setCnContentTO(CNContentTO cnContentTO) {
		this.cnContentTO = cnContentTO;
	}

	/**
	 * @return the cnPaperWorksTO
	 */
	public CNPaperWorksTO getCnPaperWorksTO() {
		return cnPaperWorksTO;
	}

	/**
	 * @param cnPaperWorksTO the cnPaperWorksTO to set
	 */
	public void setCnPaperWorksTO(CNPaperWorksTO cnPaperWorksTO) {
		this.cnPaperWorksTO = cnPaperWorksTO;
	}

	/**
	 * @return the insuredByTO
	 */
	public InsuredByTO getInsuredByTO() {
		return insuredByTO;
	}

	/**
	 * @param insuredByTO the insuredByTO to set
	 */
	public void setInsuredByTO(InsuredByTO insuredByTO) {
		this.insuredByTO = insuredByTO;
	}

	
	/**
	 * @return the consignorTO
	 */
	public ConsignorConsigneeTO getConsignorTO() {
		return consignorTO;
	}

	/**
	 * @param consignorTO the consignorTO to set
	 */
	public void setConsignorTO(ConsignorConsigneeTO consignorTO) {
		this.consignorTO = consignorTO;
	}

	/**
	 * @return the consigneeTO
	 */
	public ConsignorConsigneeTO getConsigneeTO() {
		return consigneeTO;
	}

	/**
	 * @param consigneeTO the consigneeTO to set
	 */
	public void setConsigneeTO(ConsignorConsigneeTO consigneeTO) {
		this.consigneeTO = consigneeTO;
	}

	/**
	 * @return the billingConsignmentSummaryTO
	 */
	public BillingConsignmentSummaryTO getBillingConsignmentSummaryTO() {
		return billingConsignmentSummaryTO;
	}

	/**
	 * @param billingConsignmentSummaryTO the billingConsignmentSummaryTO to set
	 */
	public void setBillingConsignmentSummaryTO(
			BillingConsignmentSummaryTO billingConsignmentSummaryTO) {
		this.billingConsignmentSummaryTO = billingConsignmentSummaryTO;
	}

	/**
	 * @return the billTO
	 */
	public BillTO getBillTO() {
		return billTO;
	}

	/**
	 * @param billTO the billTO to set
	 */
	public void setBillTO(BillTO billTO) {
		this.billTO = billTO;
	}

	/**
	 * @return the billingConsignmentRateTOs
	 */
	public List<BillingConsignmentRateTO> getBillingConsignmentRateTOs() {
		return billingConsignmentRateTOs;
	}

	/**
	 * @param billingConsignmentRateTOs the billingConsignmentRateTOs to set
	 */
	public void setBillingConsignmentRateTOs(
			List<BillingConsignmentRateTO> billingConsignmentRateTOs) {
		this.billingConsignmentRateTOs = billingConsignmentRateTOs;
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
