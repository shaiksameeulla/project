package com.ff.to.utilities;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.tracking.ProcessTO;

public class BookingStopDlvTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The booking id. */
	private Integer bookingId;

	/** The booking id. */
	private String bookingOffCode;

	private String originCity;

	/** The consg number. */
	private String consgNumber;

	/** The booking date. */
	private Date bookingDate;
	
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
    private CustomerTO  customerTO;
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public String getBookingOffCode() {
		return bookingOffCode;
	}
	public void setBookingOffCode(String bookingOffCode) {
		this.bookingOffCode = bookingOffCode;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getConsgNumber() {
		return consgNumber;
	}
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	public Integer getBookingCityId() {
		return bookingCityId;
	}
	public void setBookingCityId(Integer bookingCityId) {
		this.bookingCityId = bookingCityId;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	public Integer getNoOfPieces() {
		return noOfPieces;
	}
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
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
	public Double getVolWeight() {
		return volWeight;
	}
	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}
	public Integer getConsgTypeId() {
		return consgTypeId;
	}
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}
	public String getConsgTypeName() {
		return consgTypeName;
	}
	public void setConsgTypeName(String consgTypeName) {
		this.consgTypeName = consgTypeName;
	}
	public Integer getPincodeId() {
		return pincodeId;
	}
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCnStatus() {
		return cnStatus;
	}
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}
	public ConsignorConsigneeTO getConsignee() {
		return consignee;
	}
	public void setConsignee(ConsignorConsigneeTO consignee) {
		this.consignee = consignee;
	}
	public ConsignorConsigneeTO getConsignor() {
		return consignor;
	}
	public void setConsignor(ConsignorConsigneeTO consignor) {
		this.consignor = consignor;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public CNPricingDetailsTO getCnPricingDtls() {
		return cnPricingDtls;
	}
	public void setCnPricingDtls(CNPricingDetailsTO cnPricingDtls) {
		this.cnPricingDtls = cnPricingDtls;
	}
	public ConsignmentTO getConsigmentTO() {
		return consigmentTO;
	}
	public void setConsigmentTO(ConsignmentTO consigmentTO) {
		this.consigmentTO = consigmentTO;
	}
	public String getWeightCapturedMode() {
		return weightCapturedMode;
	}
	public void setWeightCapturedMode(String weightCapturedMode) {
		this.weightCapturedMode = weightCapturedMode;
	}
	public String getBookingProcess() {
		return bookingProcess;
	}
	public void setBookingProcess(String bookingProcess) {
		this.bookingProcess = bookingProcess;
	}
	public ProcessTO getProcessTO() {
		return processTO;
	}
	public void setProcessTO(ProcessTO processTO) {
		this.processTO = processTO;
	}
	public String getPickupRunsheetNo() {
		return pickupRunsheetNo;
	}
	public void setPickupRunsheetNo(String pickupRunsheetNo) {
		this.pickupRunsheetNo = pickupRunsheetNo;
	}
	public Double getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getChildCNsDtls() {
		return childCNsDtls;
	}
	public void setChildCNsDtls(String childCNsDtls) {
		this.childCNsDtls = childCNsDtls;
	}
	public String getProcessNumber() {
		return processNumber;
	}
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	public String getConsgRateDtls() {
		return consgRateDtls;
	}
	public void setConsgRateDtls(String consgRateDtls) {
		this.consgRateDtls = consgRateDtls;
	}
	public boolean isReCalcRateReq() {
		return isReCalcRateReq;
	}
	public void setReCalcRateReq(boolean isReCalcRateReq) {
		this.isReCalcRateReq = isReCalcRateReq;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getBizAssociateId() {
		return bizAssociateId;
	}
	public void setBizAssociateId(Integer bizAssociateId) {
		this.bizAssociateId = bizAssociateId;
	}
	public Integer getDlvTimeMapId() {
		return dlvTimeMapId;
	}
	public void setDlvTimeMapId(Integer dlvTimeMapId) {
		this.dlvTimeMapId = dlvTimeMapId;
	}
	public ConsignorConsigneeTO getAltConsigneeAddr() {
		return altConsigneeAddr;
	}
	public void setAltConsigneeAddr(ConsignorConsigneeTO altConsigneeAddr) {
		this.altConsigneeAddr = altConsigneeAddr;
	}
	public List<String> getFailureConsignments() {
		return failureConsignments;
	}
	public void setFailureConsignments(List<String> failureConsignments) {
		this.failureConsignments = failureConsignments;
	}
	public CustomerTO getCustomerTO() {
		return customerTO;
	}
	public void setCustomerTO(CustomerTO customerTO) {
		this.customerTO = customerTO;
	}
    
    
    
}
