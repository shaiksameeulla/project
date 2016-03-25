package com.ff.booking;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.tracking.ProcessTO;

/**
 * The Class BookingTO.
 * 
 * @author Narasimha Rao kattunga
 */

public abstract class BookingTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9196269017297958251L;

	/** The booking id. */
	private Integer bookingId;

	/** The booking id. */
	private String bookingOffCode;

	private String originCity;

	/** The consg number. */
	private String consgNumber;

	/** The booking date. */
	private String bookingDate;
	
	private String bookingTime;

	/** The booking office id. */
	private Integer bookingOfficeId;
	
	/** The booking office id. */
	private Integer bookingCityId;

	/** The booking type. */
	private String bookingType;

	/** The booking type id. */
	private Integer bookingTypeId;

	/** The no of pieces. */
	private Integer noOfPieces;

	/** The actual weight. */
	private Double actualWeight;

	/** The final weight. */
	private Double finalWeight;

	/** The vol weight. */
	private Double volWeight;

	/** The consg type id. */
	private Integer consgTypeId;

	/** The consg type name. */
	private String consgTypeName;

	/** The pincode id. */
	private Integer pincodeId;

	/** The pincode. */
	private String pincode;
	
	private String altPincode;

	/** The price. */
	private Double price;

	/** The cn status. */
	private String cnStatus;

	/** The consignee. */
	private ConsignorConsigneeTO consignee;

	/** The consignor. */
	private ConsignorConsigneeTO consignor;

	/** The city id. */
	private Integer cityId;

	/** The city name. */
	private String cityName;

	/** The cn pricing dtls. */
	private CNPricingDetailsTO cnPricingDtls;
	private ConsignmentTO consigmentTO;

	/** The weight captured mode. */
	private String weightCapturedMode;

	/** The booking process. */
	private String bookingProcess;

	/** The process to. */
	private ProcessTO processTO;

	/** The pickup runsheet no. */
	private String pickupRunsheetNo;

	/** The cod amount. */
	private Double codAmount;

	/** The file name. */
	private String fileName;

	/** The product id. */
	private Integer productId;

	/** The product code. */
	private String productCode;

	/** The child c ns dtls. */
	private String childCNsDtls;
	private String processNumber;

	// for rate
	private String consgRateDtls;

	/** The is re calc rate req. */
	private boolean isReCalcRateReq = Boolean.FALSE;
	private Integer customerId;
	private Integer bizAssociateId;
	private Integer dlvTimeMapId;
	/** The alternate consignee. */
	private ConsignorConsigneeTO altConsigneeAddr;

	// For Failure consignments
	private List<String> failureConsignments;
	
	private String customerCodeSingle;
	
	private String priorityServiced;

	private String loginRHOCode;
	private Double baAmt;
	

	private Integer createdBy ;
	private Integer updatedBy;
	
	
	
	
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

	/**
	 * @return the loginRHOCode
	 */
	public String getLoginRHOCode() {
		return loginRHOCode;
	}

	/**
	 * @param loginRHOCode the loginRHOCode to set
	 */
	public void setLoginRHOCode(String loginRHOCode) {
		this.loginRHOCode = loginRHOCode;
	}

	/**
	 * @return the priorityServiced
	 */
	public String getPriorityServiced() {
		return priorityServiced;
	}

	/**
	 * @param priorityServiced the priorityServiced to set
	 */
	public void setPriorityServiced(String priorityServiced) {
		this.priorityServiced = priorityServiced;
	}

	/**
	 * @return the customerCodeSingle
	 */
	public String getCustomerCodeSingle() {
		return customerCodeSingle;
	}

	/**
	 * @param customerCodeSingle the customerCodeSingle to set
	 */
	public void setCustomerCodeSingle(String customerCodeSingle) {
		this.customerCodeSingle = customerCodeSingle;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	/**
	 * Gets the booking id.
	 * 
	 * @return the booking id
	 */
	public Integer getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking id.
	 * 
	 * @param bookingId
	 *            the new booking id
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the consg number.
	 * 
	 * @return the consg number
	 */
	public String getConsgNumber() {
		return consgNumber;
	}

	/**
	 * Sets the consg number.
	 * 
	 * @param consgNumber
	 *            the new consg number
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}

	/**
	 * Gets the booking date.
	 * 
	 * @return the booking date
	 */
	public String getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 * 
	 * @param bookingDate
	 *            the new booking date
	 */
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the booking office id.
	 * 
	 * @return the booking office id
	 */
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}

	/**
	 * Sets the booking office id.
	 * 
	 * @param bookingOfficeId
	 *            the new booking office id
	 */
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}

	/**
	 * Gets the booking type.
	 * 
	 * @return the booking type
	 */
	public String getBookingType() {
		return bookingType;
	}

	/**
	 * Sets the booking type.
	 * 
	 * @param bookingType
	 *            the new booking type
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * Gets the no of pieces.
	 * 
	 * @return the no of pieces
	 */
	public Integer getNoOfPieces() {
		return noOfPieces;
	}

	/**
	 * Sets the no of pieces.
	 * 
	 * @param noOfPieces
	 *            the new no of pieces
	 */
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
	}

	/**
	 * Gets the actual weight.
	 * 
	 * @return the actual weight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * Sets the actual weight.
	 * 
	 * @param actualWeight
	 *            the new actual weight
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * Gets the final weight.
	 * 
	 * @return the final weight
	 */
	public Double getFinalWeight() {
		return finalWeight;
	}

	/**
	 * Sets the final weight.
	 * 
	 * @param finalWeight
	 *            the new final weight
	 */
	public void setFinalWeight(Double finalWeight) {
		this.finalWeight = finalWeight;
	}

	/**
	 * Gets the vol weight.
	 * 
	 * @return the vol weight
	 */
	public Double getVolWeight() {
		return volWeight;
	}

	/**
	 * Sets the vol weight.
	 * 
	 * @param volWeight
	 *            the new vol weight
	 */
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/**
	 * Gets the consg type id.
	 * 
	 * @return the consg type id
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}

	/**
	 * Sets the consg type id.
	 * 
	 * @param consgTypeId
	 *            the new consg type id
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	/**
	 * Gets the consg type name.
	 * 
	 * @return the consg type name
	 */
	public String getConsgTypeName() {
		return consgTypeName;
	}

	/**
	 * Sets the consg type name.
	 * 
	 * @param consgTypeName
	 *            the new consg type name
	 */
	public void setConsgTypeName(String consgTypeName) {
		this.consgTypeName = consgTypeName;
	}

	/**
	 * Gets the pincode id.
	 * 
	 * @return the pincode id
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}

	/**
	 * Sets the pincode id.
	 * 
	 * @param pincodeId
	 *            the new pincode id
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	/**
	 * Gets the pincode.
	 * 
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * Sets the pincode.
	 * 
	 * @param pincode
	 *            the new pincode
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * Gets the price.
	 * 
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 * 
	 * @param price
	 *            the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the cn status.
	 * 
	 * @return the cn status
	 */
	public String getCnStatus() {
		return cnStatus;
	}

	/**
	 * Sets the cn status.
	 * 
	 * @param cnStatus
	 *            the new cn status
	 */
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}

	/**
	 * Gets the consignee.
	 * 
	 * @return the consignee
	 */
	public ConsignorConsigneeTO getConsignee() {
		return consignee;
	}

	/**
	 * Sets the consignee.
	 * 
	 * @param consignee
	 *            the new consignee
	 */
	public void setConsignee(ConsignorConsigneeTO consignee) {
		this.consignee = consignee;
	}

	/**
	 * Gets the consignor.
	 * 
	 * @return the consignor
	 */
	public ConsignorConsigneeTO getConsignor() {
		return consignor;
	}

	/**
	 * Sets the consignor.
	 * 
	 * @param consignor
	 *            the new consignor
	 */
	public void setConsignor(ConsignorConsigneeTO consignor) {
		this.consignor = consignor;
	}

	/**
	 * Gets the city id.
	 * 
	 * @return the city id
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * Sets the city id.
	 * 
	 * @param cityId
	 *            the new city id
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * Gets the city name.
	 * 
	 * @return the city name
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * Sets the city name.
	 * 
	 * @param cityName
	 *            the new city name
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the consigmentTO
	 */
	public ConsignmentTO getConsigmentTO() {
		return consigmentTO;
	}

	/**
	 * @param consigmentTO
	 *            the consigmentTO to set
	 */
	public void setConsigmentTO(ConsignmentTO consigmentTO) {
		this.consigmentTO = consigmentTO;
	}

	/**
	 * Gets the weight captured mode.
	 * 
	 * @return the weight captured mode
	 */
	public String getWeightCapturedMode() {
		return weightCapturedMode;
	}

	/**
	 * Sets the weight captured mode.
	 * 
	 * @param weightCapturedMode
	 *            the new weight captured mode
	 */
	public void setWeightCapturedMode(String weightCapturedMode) {
		this.weightCapturedMode = weightCapturedMode;
	}

	/**
	 * Gets the booking process.
	 * 
	 * @return the booking process
	 */
	public String getBookingProcess() {
		return bookingProcess;
	}

	/**
	 * Sets the booking process.
	 * 
	 * @param bookingProcess
	 *            the new booking process
	 */
	public void setBookingProcess(String bookingProcess) {
		this.bookingProcess = bookingProcess;
	}

	/**
	 * Gets the booking type id.
	 * 
	 * @return the booking type id
	 */
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}

	/**
	 * Sets the booking type id.
	 * 
	 * @param bookingTypeId
	 *            the new booking type id
	 */
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	/**
	 * Gets the process to.
	 * 
	 * @return the process to
	 */
	public ProcessTO getProcessTO() {
		return processTO;
	}

	/**
	 * Sets the process to.
	 * 
	 * @param processTO
	 *            the new process to
	 */
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}

	/**
	 * Gets the pickup runsheet no.
	 * 
	 * @return the pickup runsheet no
	 */
	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}

	/**
	 * Sets the pickup runsheet no.
	 * 
	 * @param pickupRunsheetNo
	 *            the new pickup runsheet no
	 */
	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}

	/**
	 * Gets the cod amount.
	 * 
	 * @return the cod amount
	 */
	public Double getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the cod amount.
	 * 
	 * @param codAmount
	 *            the new cod amount
	 */
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the product id.
	 * 
	 * @return the product id
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 * 
	 * @param productId
	 *            the new product id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * Gets the product code.
	 * 
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 * 
	 * @param productCode
	 *            the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the child c ns dtls.
	 * 
	 * @return the child c ns dtls
	 */
	public String getChildCNsDtls() {
		return childCNsDtls;
	}

	/**
	 * Sets the child c ns dtls.
	 * 
	 * @param childCNsDtls
	 *            the new child c ns dtls
	 */
	public void setChildCNsDtls(String childCNsDtls) {
		this.childCNsDtls = childCNsDtls;
	}

	/**
	 * @return the bookingOffCode
	 */
	public String getBookingOffCode() {
		return bookingOffCode;
	}

	/**
	 * @param bookingOffCode
	 *            the bookingOffCode to set
	 */
	public void setBookingOffCode(String bookingOffCode) {
		this.bookingOffCode = bookingOffCode;
	}

	/**
	 * @return the processNumber
	 */
	public String getProcessNumber() {
		return processNumber;
	}

	/**
	 * @param processNumber
	 *            the processNumber to set
	 */
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}

	/**
	 * @return the originCity
	 */
	public String getOriginCity() {
		return originCity;
	}

	/**
	 * @param originCity
	 *            the originCity to set
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	/**
	 * @return the consgRateDtls
	 */
	public String getConsgRateDtls() {
		return consgRateDtls;
	}

	/**
	 * @param consgRateDtls
	 *            the consgRateDtls to set
	 */
	public void setConsgRateDtls(String consgRateDtls) {
		this.consgRateDtls = consgRateDtls;
	}

	/**
	 * @return the cnPricingDtls
	 */
	public CNPricingDetailsTO getCnPricingDtls() {
		return cnPricingDtls;
	}

	/**
	 * @param cnPricingDtls
	 *            the cnPricingDtls to set
	 */
	public void setCnPricingDtls(CNPricingDetailsTO cnPricingDtls) {
		this.cnPricingDtls = cnPricingDtls;
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
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the bizAssociateId
	 */
	public Integer getBizAssociateId() {
		return bizAssociateId;
	}

	/**
	 * @param bizAssociateId
	 *            the bizAssociateId to set
	 */
	public void setBizAssociateId(Integer bizAssociateId) {
		this.bizAssociateId = bizAssociateId;
	}

	/**
	 * @return the failureConsignments
	 */
	public List<String> getFailureConsignments() {
		return failureConsignments;
	}

	/**
	 * @param failureConsignments
	 *            the failureConsignments to set
	 */
	public void setFailureConsignments(List<String> failureConsignments) {
		this.failureConsignments = failureConsignments;
	}

	/**
	 * @return the dlvTimeMapId
	 */
	public Integer getDlvTimeMapId() {
		return dlvTimeMapId;
	}

	/**
	 * @param dlvTimeMapId the dlvTimeMapId to set
	 */
	public void setDlvTimeMapId(Integer dlvTimeMapId) {
		this.dlvTimeMapId = dlvTimeMapId;
	}

	/**
	 * @return the altConsigneeAddr
	 */
	public ConsignorConsigneeTO getAltConsigneeAddr() {
		return altConsigneeAddr;
	}

	/**
	 * @param altConsigneeAddr the altConsigneeAddr to set
	 */
	public void setAltConsigneeAddr(ConsignorConsigneeTO altConsigneeAddr) {
		this.altConsigneeAddr = altConsigneeAddr;
	}

	/**
	 * @return the bookingCityId
	 */
	public Integer getBookingCityId() {
		return bookingCityId;
	}

	/**
	 * @param bookingCityId the bookingCityId to set
	 */
	public void setBookingCityId(Integer bookingCityId) {
		this.bookingCityId = bookingCityId;
	}

	public String getAltPincode() {
		return altPincode;
	}

	public void setAltPincode(String altPincode) {
		this.altPincode = altPincode;
	}
}
