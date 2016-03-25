/**
 * 
 */
package com.ff.to.drs;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.geography.PincodeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.VolumetricWeightTO;

/**
 * The Class DeliveryConsignmentTO.
 *
 * @author mohammes
 */
public class DeliveryConsignmentTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2419105379854523111L;

	/** The consg id. */
	private Integer consgId;
	
	/** The consg no. */
	private String consgNo;
	
	private String consgStatus;
	
	/** The org off id. */
	private Integer orgOffId;
	
	/** The dest pincode. */
	private PincodeTO destPincode;
	
	/** The no of pcs. */
	private Integer noOfPcs;
	
	/** The process status. */
	private String processStatus;
	
	/** The consg type id. */
	private Integer consgTypeId;
	
	/** The consignment type code. */
	private String consignmentTypeCode;
	
	/** The consignment type name. */
	private String consignmentTypeName;
	
	/** The price. */
	private Double price;
	
	/** The product id. */
	private Integer productId;
	
	/** The actual weight. */
	private Double actualWeight;
	
	/** The final weight.  ie chargeable weight*/
	private Double finalWeight;
	
	/** The city id. */
	private Integer cityId;
	
	/** The city code. */
	private String cityCode;
	
	/** The city name. */
	private String cityName;
	
	/** The consignee. */
	private ConsignorConsigneeTO consigneeTO;
	
	/** The consignor. */
	private ConsignorConsigneeTO consignorTO;
	
	/** The cn paper works. */
	private CNPaperWorksTO cnPaperWorks;
	
	/** The cn contents. */
	private CNContentTO cnContents;
	
	/** The insured by to. */
	private InsuredByTO insuredByTO;
	
	/** The vol wight dtls. */
	private VolumetricWeightTO volWightDtls;
	
	/** The mobile no. */
	private String mobileNo;
	
	/** The child c ns dtls. */
	private String childCNsDtls;
	
	
	/** The vol weight. */
	private Double volWeight;
	
	/** The height. */
	private Double height;
	
	/** The length. */
	private Double length;
	
	/** The breath. */
	private Double breath;
	
	/** The other cn content. */
	private String otherCNContent;
	
	/** The paper work ref no. */
	private String paperWorkRefNo;
	
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

	/** The insurence policy no. */
	private String insurencePolicyNo;
	
	/** The ref no. */
	private String refNo;
	
	/** The parent cn number. it's only for PPX consignment number */
	private String parentCnNumber;
	
	/** The parent child cn type. P-PARENT CONSIGNMENT,C-CHILD CONSIGNMENT*/
	private String parentChildCnType;
	
	/** The attempt number. */
	private Integer attemptNumber;
	
	private Double codAmount;
	
	private Double lcAmount;
	
	private Double toPayAmount;
	
	private Double otherAmount;
	private Double baAmount;
	
	private Double additionalCharges;
	
	private String vendorCode;
	private String vendorName;
	
	private String customerName;
	private String customerCode;
	
	private String isPaymentAlreadyCaptured="N";
	
	
	
	

	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @return the orgOffId
	 */
	public Integer getOrgOffId() {
		return orgOffId;
	}

	/**
	 * @return the destPincode
	 */
	public PincodeTO getDestPincode() {
		return destPincode;
	}

	

	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @return the consgTypeId
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	/**
	 * @return the consignmentTypeCode
	 */
	public String getConsignmentTypeCode() {
		return consignmentTypeCode;
	}

	/**
	 * @return the consignmentTypeName
	 */
	public String getConsignmentTypeName() {
		return consignmentTypeName;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @return the finalWeight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	

	/**
	 * @return the cnPaperWorks
	 */
	public CNPaperWorksTO getCnPaperWorks() {
		return cnPaperWorks;
	}

	/**
	 * @return the cnContents
	 */
	public CNContentTO getCnContents() {
		return cnContents;
	}

	/**
	 * @return the insuredByTO
	 */
	public InsuredByTO getInsuredByTO() {
		return insuredByTO;
	}

	/**
	 * @return the volWightDtls
	 */
	public VolumetricWeightTO getVolWightDtls() {
		return volWightDtls;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @return the childCNsDtls
	 */
	public String getChildCNsDtls() {
		return childCNsDtls;
	}

	/**
	 * @return the volWeight
	 */
	public Double getVolWeight() {
		return volWeight;
	}

	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @return the length
	 */
	public Double getLength() {
		return length;
	}

	/**
	 * @return the baAmount
	 */
	public Double getBaAmount() {
		return baAmount;
	}

	/**
	 * @param baAmount the baAmount to set
	 */
	public void setBaAmount(Double baAmount) {
		this.baAmount = baAmount;
	}

	/**
	 * @return the breath
	 */
	public Double getBreath() {
		return breath;
	}

	/**
	 * @return the otherCNContent
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}

	/**
	 * @return the paperWorkRefNo
	 */
	public String getPaperWorkRefNo() {
		return paperWorkRefNo;
	}

	/**
	 * @return the isPaymentAlreadyCaptured
	 */
	public String getIsPaymentAlreadyCaptured() {
		return isPaymentAlreadyCaptured;
	}

	/**
	 * @param isPaymentAlreadyCaptured the isPaymentAlreadyCaptured to set
	 */
	public void setIsPaymentAlreadyCaptured(String isPaymentAlreadyCaptured) {
		this.isPaymentAlreadyCaptured = isPaymentAlreadyCaptured;
	}

	/**
	 * @return the insurencePolicyNo
	 */
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}

	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * @return the parentCnNumber
	 */
	public String getParentCnNumber() {
		return parentCnNumber;
	}

	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @param orgOffId the orgOffId to set
	 */
	public void setOrgOffId(Integer orgOffId) {
		this.orgOffId = orgOffId;
	}

	/**
	 * @param destPincode the destPincode to set
	 */
	public void setDestPincode(PincodeTO destPincode) {
		this.destPincode = destPincode;
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
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	/**
	 * @param consignmentTypeCode the consignmentTypeCode to set
	 */
	public void setConsignmentTypeCode(String consignmentTypeCode) {
		this.consignmentTypeCode = consignmentTypeCode;
	}

	/**
	 * @param consignmentTypeName the consignmentTypeName to set
	 */
	public void setConsignmentTypeName(String consignmentTypeName) {
		this.consignmentTypeName = consignmentTypeName;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @param finalWeight the finalWeight to set
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	

	/**
	 * @param cnPaperWorks the cnPaperWorks to set
	 */
	public void setCnPaperWorks(CNPaperWorksTO cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}

	/**
	 * @param cnContents the cnContents to set
	 */
	public void setCnContents(CNContentTO cnContents) {
		this.cnContents = cnContents;
	}

	/**
	 * @param insuredByTO the insuredByTO to set
	 */
	public void setInsuredByTO(InsuredByTO insuredByTO) {
		this.insuredByTO = insuredByTO;
	}

	/**
	 * @param volWightDtls the volWightDtls to set
	 */
	public void setVolWightDtls(VolumetricWeightTO volWightDtls) {
		this.volWightDtls = volWightDtls;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @param childCNsDtls the childCNsDtls to set
	 */
	public void setChildCNsDtls(String childCNsDtls) {
		this.childCNsDtls = childCNsDtls;
	}

	/**
	 * @param volWeight the volWeight to set
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Double length) {
		this.length = length;
	}

	/**
	 * @param breath the breath to set
	 */
	public void setBreath(Double breath) {
		this.breath = breath;
	}

	/**
	 * @param otherCNContent the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * @param paperWorkRefNo the paperWorkRefNo to set
	 */
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}

	/**
	 * @param insurencePolicyNo the insurencePolicyNo to set
	 */
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}

	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @param parentCnNumber the parentCnNumber to set
	 */
	public void setParentCnNumber(String parentCnNumber) {
		this.parentCnNumber = parentCnNumber;
	}

	/**
	 * @return the attemptNumber
	 */
	public Integer getAttemptNumber() {
		return attemptNumber;
	}

	/**
	 * @param attemptNumber the attemptNumber to set
	 */
	public void setAttemptNumber(Integer attemptNumber) {
		this.attemptNumber = attemptNumber;
	}

	/**
	 * @return the consigneeTO
	 */
	public ConsignorConsigneeTO getConsigneeTO() {
		return consigneeTO;
	}

	/**
	 * @return the consignorTO
	 */
	public ConsignorConsigneeTO getConsignorTO() {
		return consignorTO;
	}

	/**
	 * @param consigneeTO the consigneeTO to set
	 */
	public void setConsigneeTO(ConsignorConsigneeTO consigneeTO) {
		this.consigneeTO = consigneeTO;
	}

	/**
	 * @param consignorTO the consignorTO to set
	 */
	public void setConsignorTO(ConsignorConsigneeTO consignorTO) {
		this.consignorTO = consignorTO;
	}

	/**
	 * @return the codAmount
	 */
	public Double getCodAmount() {
		return codAmount;
	}

	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public Double getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @return the otherAmount
	 */
	public Double getOtherAmount() {
		return otherAmount;
	}

	/**
	 * @return the additionalCharges
	 */
	public Double getAdditionalCharges() {
		return additionalCharges;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @param otherAmount the otherAmount to set
	 */
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	/**
	 * @param additionalCharges the additionalCharges to set
	 */
	public void setAdditionalCharges(Double additionalCharges) {
		this.additionalCharges = additionalCharges;
	}


	/**
	 * @return the parentChildCnType
	 */
	public String getParentChildCnType() {
		return parentChildCnType;
	}

	/**
	 * @param parentChildCnType the parentChildCnType to set
	 */
	public void setParentChildCnType(String parentChildCnType) {
		this.parentChildCnType = parentChildCnType;
	}

	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	

}
