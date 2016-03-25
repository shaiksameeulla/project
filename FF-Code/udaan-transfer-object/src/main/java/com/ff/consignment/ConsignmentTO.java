package com.ff.consignment;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.serviceOfferring.VolumetricWeightTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;

public class ConsignmentTO extends CGBaseTO implements
		Comparable<ConsignmentTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4213569120180725446L;

	private Double volWeight;
	private Double height;
	private Double length;
	private Double breath;

	private String otherCNContent;
	private String paperWorkRefNo;
	private String insurencePolicyNo;
	private String refNo;

	private Integer consgId;
	private String consgNo;
	private Integer orgOffId;
	private PincodeTO destPincode;
	private Integer noOfPcs;
	private String processStatus;
	private Integer consgTypeId;
	private Double price;
	private Integer productId;
	private Double actualWeight;
	private Double finalWeight;
	private CityTO destCity;
	private ProcessTO updatedProcessFrom;
	private Set<ChildConsignmentTO> childTOSet;

	private ConsignmentTypeTO typeTO;

	// private String content;
	private CNPaperWorksTO cnPaperWorks;
	private CNContentTO cnContents;
	private InsuredByTO insuredByTO;
	private VolumetricWeightTO volWightDtls;
	private String mobileNo;
	private String childCNsDtls;
	// For POD
	private Date receivedDateTime;
	private Date deliveredDate;
	private String recvNameOrCompName;
	private String receivedStatus;
	private ConsignorConsigneeTO consignorTO;
	private ConsignorConsigneeTO consigneeTO;
	private Double declaredValue;

	// For DRS Date Time
	private Date deliveryDateTime;

	// For InManifest Remarks
	private String remarks;
	private String message;

	// For CN price Information START****
	private Double topayAmt;
	private Double splChg;
	private Double codAmt;
	private Double lcAmount;
	private Double baAmount;
	private Double baAmt;
	private Double discount;
	// For CN price Information END****

	// For rate Details:
	private Map<String, ConsignmentRateCalculationOutputTO> consgRateOutputTOs = null;
	private CNPricingDetailsTO consgPriceDtls;
	private String consgStatus;

	/** The is re calc rate req. */
	private boolean isReCalcRateReq = Boolean.FALSE;

	private Integer operatingLevel;
	private Integer operatingOffice;
	private OfficeTO destOffice;

	// for Billing
	private String billingStatus;
	private String changedAfterBillingWtDest;
	private String changedAfterNewRateCmpnt;
	private ProductTO productTO;
	private String bookingType;

	private Date eventDate;
	private Integer customer;
	private CustomerTO customerTO;
	private String lcBankName;

	private CityTO cityTO;
	private String BOOKING_RATE_BILLED;
	private String RTO_RATE_BILLED;

	/** Booking Date */
	private Date bookingDate;

	private Boolean isNewConsignment = Boolean.FALSE;

	/** The Rate Customer Category Code. */
	private String rateCustomerCatCode;
	/** The alternate consignee. */
	private ConsignorConsigneeTO altConsigneeAddrTO;

	/** Octroi components */
	private Double octroiAmount;
	private Integer octroiState;
	private String stopDelivery;
	private ReasonTO reasonTO;
	private Date stopDelvDate;
	private ReasonTO stopReasonTO;
	private String isBulkBookedCN;
	private Date createdDate;
	private String isExcessConsg = CommonConstants.NO;
	private String servicedOn;
	private String consgTypeCode;

	private Integer createdBy;
	private Integer updatedBy;
	private String strCnWeight;

	public String getStrCnWeight() {
		return strCnWeight;
	}

	public void setStrCnWeight(String strCnWeight) {
		this.strCnWeight = strCnWeight;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getConsgTypeCode() {
		return consgTypeCode;
	}

	public void setConsgTypeCode(String consgTypeCode) {
		this.consgTypeCode = consgTypeCode;
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
	 * @return the baAmount
	 */
	public Double getBaAmount() {
		return baAmount;
	}

	/**
	 * @param baAmount
	 *            the baAmount to set
	 */
	public void setBaAmount(Double baAmount) {
		this.baAmount = baAmount;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the isBulkBookedCN
	 */
	public String getIsBulkBookedCN() {
		return isBulkBookedCN;
	}

	/**
	 * @param isBulkBookedCN
	 *            the isBulkBookedCN to set
	 */
	public void setIsBulkBookedCN(String isBulkBookedCN) {
		this.isBulkBookedCN = isBulkBookedCN;
	}

	/**
	 * @return the octroiAmount
	 */
	public Double getOctroiAmount() {
		return octroiAmount;
	}

	/**
	 * @param octroiAmount
	 *            the octroiAmount to set
	 */
	public void setOctroiAmount(Double octroiAmount) {
		this.octroiAmount = octroiAmount;
	}

	/**
	 * @return the octroiState
	 */
	public Integer getOctroiState() {
		return octroiState;
	}

	/**
	 * @param octroiState
	 *            the octroiState to set
	 */
	public void setOctroiState(Integer octroiState) {
		this.octroiState = octroiState;
	}

	/**
	 * @return the rateCustomerCatCode
	 */
	public String getRateCustomerCatCode() {
		return rateCustomerCatCode;
	}

	/**
	 * @param rateCustomerCatCode
	 *            the rateCustomerCatCode to set
	 */
	public void setRateCustomerCatCode(String rateCustomerCatCode) {
		this.rateCustomerCatCode = rateCustomerCatCode;
	}

	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
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

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getConsgId() {
		return consgId;
	}

	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @return the topayAmt
	 */
	public Double getTopayAmt() {
		return topayAmt;
	}

	/**
	 * @return the splChg
	 */
	public Double getSplChg() {
		return splChg;
	}

	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}

	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * @param topayAmt
	 *            the topayAmt to set
	 */
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
	}

	/**
	 * @param splChg
	 *            the splChg to set
	 */
	public void setSplChg(Double splChg) {
		this.splChg = splChg;
	}

	/**
	 * @param codAmt
	 *            the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}

	/**
	 * @param lcAmount
	 *            the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
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

	public PincodeTO getDestPincode() {
		return destPincode;
	}

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

	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
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

	public Double getFinalWeight() {
		return finalWeight;
	}

	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public CityTO getDestCity() {
		return destCity;
	}

	public void setDestCity(CityTO destCity) {
		this.destCity = destCity;
	}

	public ProcessTO getUpdatedProcessFrom() {
		return updatedProcessFrom;
	}

	public void setUpdatedProcessFrom(ProcessTO updatedProcessFrom) {
		this.updatedProcessFrom = updatedProcessFrom;
	}

	public VolumetricWeightTO getVolWightDtls() {
		return volWightDtls;
	}

	public void setVolWightDtls(VolumetricWeightTO volWightDtls) {
		this.volWightDtls = volWightDtls;
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
	 * @return the receivedDateTime
	 */
	public Date getReceivedDateTime() {
		return receivedDateTime;
	}

	/**
	 * @return the deliveredDate
	 */
	public Date getDeliveredDate() {
		return deliveredDate;
	}

	/**
	 * @return the recvNameOrCompName
	 */
	public String getRecvNameOrCompName() {
		return recvNameOrCompName;
	}

	/**
	 * @return the receivedStatus
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}

	/**
	 * @return the consignorTO
	 */
	public ConsignorConsigneeTO getConsignorTO() {
		return consignorTO;
	}

	/**
	 * @return the consigneeTO
	 */
	public ConsignorConsigneeTO getConsigneeTO() {
		return consigneeTO;
	}

	/**
	 * @param volWeight
	 *            the volWeight to set
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(Double length) {
		this.length = length;
	}

	/**
	 * @param breath
	 *            the breath to set
	 */
	public void setBreath(Double breath) {
		this.breath = breath;
	}

	/**
	 * @param otherCNContent
	 *            the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}

	/**
	 * @param paperWorkRefNo
	 *            the paperWorkRefNo to set
	 */
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}

	/**
	 * @param insurencePolicyNo
	 *            the insurencePolicyNo to set
	 */
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}

	/**
	 * @param refNo
	 *            the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @param receivedDateTime
	 *            the receivedDateTime to set
	 */
	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}

	/**
	 * @param deliveredDate
	 *            the deliveredDate to set
	 */
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	/**
	 * @param recvNameOrCompName
	 *            the recvNameOrCompName to set
	 */
	public void setRecvNameOrCompName(String recvNameOrCompName) {
		this.recvNameOrCompName = recvNameOrCompName;
	}

	/**
	 * @param receivedStatus
	 *            the receivedStatus to set
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * @param consignorTO
	 *            the consignorTO to set
	 */
	public void setConsignorTO(ConsignorConsigneeTO consignorTO) {
		this.consignorTO = consignorTO;
	}

	/**
	 * @param consigneeTO
	 *            the consigneeTO to set
	 */
	public void setConsigneeTO(ConsignorConsigneeTO consigneeTO) {
		this.consigneeTO = consigneeTO;
	}

	public CNPaperWorksTO getCnPaperWorks() {
		return cnPaperWorks;
	}

	public void setCnPaperWorks(CNPaperWorksTO cnPaperWorks) {
		this.cnPaperWorks = cnPaperWorks;
	}

	public CNContentTO getCnContents() {
		return cnContents;
	}

	public void setCnContents(CNContentTO cnContents) {
		this.cnContents = cnContents;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @return the childTOSet
	 */
	public Set<ChildConsignmentTO> getChildTOSet() {
		return childTOSet;
	}

	/**
	 * @param childTOSet
	 *            the childTOSet to set
	 */
	public void setChildTOSet(Set<ChildConsignmentTO> childTOSet) {
		this.childTOSet = childTOSet;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public InsuredByTO getInsuredByTO() {
		return insuredByTO;
	}

	public void setInsuredByTO(InsuredByTO insuredByTO) {
		this.insuredByTO = insuredByTO;
	}

	/**
	 * @return the childCNsDtls
	 */
	public String getChildCNsDtls() {
		return childCNsDtls;
	}

	/**
	 * @param childCNsDtls
	 *            the childCNsDtls to set
	 */
	public void setChildCNsDtls(String childCNsDtls) {
		this.childCNsDtls = childCNsDtls;
	}

	/**
	 * @return the typeTO
	 */
	public ConsignmentTypeTO getTypeTO() {
		return typeTO;
	}

	/**
	 * @param typeTO
	 *            the typeTO to set
	 */
	public void setTypeTO(ConsignmentTypeTO typeTO) {
		this.typeTO = typeTO;
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
	 * @return the consgPriceDtls
	 */
	public CNPricingDetailsTO getConsgPriceDtls() {
		return consgPriceDtls;
	}

	/**
	 * @param consgPriceDtls
	 *            the consgPriceDtls to set
	 */
	public void setConsgPriceDtls(CNPricingDetailsTO consgPriceDtls) {
		this.consgPriceDtls = consgPriceDtls;
	}

	/**
	 * @return the isReCalcRateReq
	 */
	public boolean isReCalcRateReq() {
		return isReCalcRateReq;
	}

	/**
	 * @param isReCalcRateReq
	 *            the isReCalcRateReq to set
	 */
	public void setReCalcRateReq(boolean isReCalcRateReq) {
		this.isReCalcRateReq = isReCalcRateReq;
	}

	/**
	 * @return the operatingLevel
	 */
	public Integer getOperatingLevel() {
		return operatingLevel;
	}

	/**
	 * @param operatingLevel
	 *            the operatingLevel to set
	 */
	public void setOperatingLevel(Integer operatingLevel) {
		this.operatingLevel = operatingLevel;
	}

	/**
	 * @return the destOffice
	 */
	public OfficeTO getDestOffice() {
		return destOffice;
	}

	/**
	 * @param destOffice
	 *            the destOffice to set
	 */
	public void setDestOffice(OfficeTO destOffice) {
		this.destOffice = destOffice;
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

	public ProductTO getProductTO() {
		return productTO;
	}

	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
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

	public CityTO getCityTO() {
		return cityTO;
	}

	public void setCityTO(CityTO cityTO) {
		this.cityTO = cityTO;
	}

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

	public CustomerTO getCustomerTO() {
		return customerTO;
	}

	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
	}

	/**
	 * @return the isNewConsignment
	 */
	public Boolean getIsNewConsignment() {
		return isNewConsignment;
	}

	/**
	 * @param isNewConsignment
	 *            the isNewConsignment to set
	 */
	public void setIsNewConsignment(Boolean isNewConsignment) {
		this.isNewConsignment = isNewConsignment;
	}

	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * @param bookingType
	 *            the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * @return the consgRateOutputTOs
	 */
	public Map<String, ConsignmentRateCalculationOutputTO> getConsgRateOutputTOs() {
		return consgRateOutputTOs;
	}

	/**
	 * @param consgRateOutputTOs
	 *            the consgRateOutputTOs to set
	 */
	public void setConsgRateOutputTOs(
			Map<String, ConsignmentRateCalculationOutputTO> consgRateOutputTOs) {
		this.consgRateOutputTOs = consgRateOutputTOs;
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
	 * @return the altConsigneeAddrTO
	 */
	public ConsignorConsigneeTO getAltConsigneeAddrTO() {
		return altConsigneeAddrTO;
	}

	/**
	 * @param altConsigneeAddrTO
	 *            the altConsigneeAddrTO to set
	 */
	public void setAltConsigneeAddrTO(ConsignorConsigneeTO altConsigneeAddrTO) {
		this.altConsigneeAddrTO = altConsigneeAddrTO;
	}

	/**
	 * @return the bOOKING_RATE_BILLED
	 */
	public String getBOOKING_RATE_BILLED() {
		return BOOKING_RATE_BILLED;
	}

	/**
	 * @param bOOKING_RATE_BILLED
	 *            the bOOKING_RATE_BILLED to set
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
	 * @param rTO_RATE_BILLED
	 *            the rTO_RATE_BILLED to set
	 */
	public void setRTO_RATE_BILLED(String rTO_RATE_BILLED) {
		RTO_RATE_BILLED = rTO_RATE_BILLED;
	}

	public String getStopDelivery() {
		return stopDelivery;
	}

	public void setStopDelivery(String stopDelivery) {
		this.stopDelivery = stopDelivery;
	}

	public ReasonTO getReasonTO() {
		return reasonTO;
	}

	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
	}

	public Date getStopDelvDate() {
		return stopDelvDate;
	}

	public void setStopDelvDate(Date stopDelvDate) {
		this.stopDelvDate = stopDelvDate;
	}

	public ReasonTO getStopReasonTO() {
		return stopReasonTO;
	}

	public void setStopReasonTO(ReasonTO stopReasonTO) {
		this.stopReasonTO = stopReasonTO;
	}

	/**
	 * @return the isExcessConsg
	 */
	public String getIsExcessConsg() {
		return isExcessConsg;
	}

	/**
	 * @param isExcessConsg
	 *            the isExcessConsg to set
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
	 * @param baAmt
	 *            the baAmt to set
	 */
	public void setBaAmt(Double baAmt) {
		this.baAmt = baAmt;
	}
	/*The sequencing of embedded elements in OGM / BPL are getting changed in tracking*/
	@Override
	public int compareTo(ConsignmentTO to) {
		int returnVal = 0;
		if (StringUtils.isNotEmpty(this.consgNo)) {
			returnVal = this.consgNo.compareTo(to.consgNo);
		}
		return returnVal;
	}
}
