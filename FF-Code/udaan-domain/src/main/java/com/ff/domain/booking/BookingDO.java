package com.ff.domain.booking;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.serviceOffering.CNContentDO;
import com.ff.domain.serviceOffering.CNPaperWorksDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.serviceOffering.InsuredByDO;
import com.ff.domain.tracking.ProcessDO;

public class BookingDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2753299598766361116L;
	private Date deliveryDate;
	private Integer bookingId;
	private String consgNumber;
	private Date bookingDate;
	private Date orderDate;
	private BookingTypeDO bookingType; // Master object
	private Integer bookingOfficeId;
	// private Integer businessAssociateId;
	// private Integer customerId;
	private CustomerDO customerId; // Master object
	private Integer noOfPieces;
	private Double actualWeight;
	private Double fianlWeight;
	private Double volWeight;
	private ConsignmentTypeDO consgTypeId; // Master object
	private PincodeDO pincodeId; // Master object
	private Double price;
	private Double height;
	private Double length;
	private Double breath;
	private Double declaredValue;
	private CNContentDO cnContentId;// Master object
	private CNPaperWorksDO cnPaperWorkId;// Master object
	private Double trnaspmentChg;
	private String cnStatus = "A";
	private String status = "B";
	private ConsigneeConsignorDO consigneeId; // Fact object
	private ConsigneeConsignorDO consignorId; // Fact object
	private CityDO destCityId; // Master object
	private ProcessDO updatedProcess; // Master object
	private BookingPaymentDO bookingPayment; // Master object
	// private CNPricingDetailsDO cnPricingDetls;
	private Integer approvedBy;
	private String weightCapturedMode = "M";
	private String otherCNContent;
	private String paperWorkRefNo;
	// @JsonManagedReference private Set<ChildConsignmentDO> childCNs;
	private String pickRunsheetNo;
	private String insurencePolicyNo;
	private Double insurenceAmt;
	private String refNo;
	private Integer pincodeDlvTimeMapId;
	private InsuredByDO insuredBy; // Master object
	@JsonManagedReference
	private Set<BookingPreferenceMappingDO> bokingPrefs;// Fact object
	private BulkBookingVendorDtlsDO bulkBookingVendorDtls; // Fact object
	private String processNumber;
	private Integer createdBy;
	private String bookingOffCode;
	private String orgCityName;
	private String shippedToCode;
	private String altPincode;
	
	//non-persistent
	private String dlvTime;
	private Integer originCityId;
	List<String> errorCodes=null;
	
	private String actWtStr;
	private String finalWtStr;
	private String volWtStr;
	private String decValStr;
	private String noOfPcsStr;
	private String consigneeAddr;
	private String altConsigneeAddr;
	private int excelRowId;
	
	private String blkBookingType;
	
	// ADDED FOR PETTY CASH
	/*private String consideredForPettyCash;*/

	
	
	
	
	
	/**
	 * @return the altConsigneeAddr
	 */
	public String getAltConsigneeAddr() {
		return altConsigneeAddr;
	}

	/**
	 * @param altConsigneeAddr the altConsigneeAddr to set
	 */
	public void setAltConsigneeAddr(String altConsigneeAddr) {
		this.altConsigneeAddr = altConsigneeAddr;
	}

	/**
	 * @return the consigneeAddr
	 */
	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	/**
	 * @param consigneeAddr the consigneeAddr to set
	 */
	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	/**
	 * @return the actWtStr
	 */
	public String getActWtStr() {
		return actWtStr;
	}

	/**
	 * @param actWtStr the actWtStr to set
	 */
	public void setActWtStr(String actWtStr) {
		this.actWtStr = actWtStr;
	}

	/**
	 * @return the volWtStr
	 */
	public String getVolWtStr() {
		return volWtStr;
	}

	/**
	 * @param volWtStr the volWtStr to set
	 */
	public void setVolWtStr(String volWtStr) {
		this.volWtStr = volWtStr;
	}

	/**
	 * @return the decValStr
	 */
	public String getDecValStr() {
		return decValStr;
	}

	/**
	 * @param decValStr the decValStr to set
	 */
	public void setDecValStr(String decValStr) {
		this.decValStr = decValStr;
	}

	

	/**
	 * @return the originCityId
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}

	/**
	 * @param originCityId the originCityId to set
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}

	/**
	 * @return the dlvTime
	 */
	public String getDlvTime() {
		return dlvTime;
	}

	/**
	 * @param dlvTime the dlvTime to set
	 */
	public void setDlvTime(String dlvTime) {
		this.dlvTime = dlvTime;
	}

	/**
	 * @return the errorCodes
	 */
	public List<String> getErrorCodes() {
		return errorCodes;
	}

	/**
	 * @param errorCodes the errorCodes to set
	 */
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	/**
	 * @return the altPincode
	 */
	public String getAltPincode() {
		return altPincode;
	}

	/**
	 * @param altPincode the altPincode to set
	 */
	public void setAltPincode(String altPincode) {
		this.altPincode = altPincode;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getConsgNumber() {
		return consgNumber;
	}

	public void setConsgNumber(String consgNumber) {
		if (consgNumber != null) {
			this.consgNumber = consgNumber.toUpperCase();
		}
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}

	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}

	public BookingTypeDO getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingTypeDO bookingType) {
		this.bookingType = bookingType;
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

	public Double getFianlWeight() {
		return fianlWeight;
	}

	public void setFianlWeight(Double fianlWeight) {
		this.fianlWeight = fianlWeight;
	}

	public ConsignmentTypeDO getConsgTypeId() {
		return consgTypeId;
	}

	public void setConsgTypeId(ConsignmentTypeDO consgTypeId) {
		this.consgTypeId = consgTypeId;
	}

	public PincodeDO getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(PincodeDO pincodeId) {
		this.pincodeId = pincodeId;
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

	public CityDO getDestCityId() {
		return destCityId;
	}

	public void setDestCityId(CityDO destCityId) {
		this.destCityId = destCityId;
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

	public Double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
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

	public Double getTrnaspmentChg() {
		return trnaspmentChg;
	}

	public void setTrnaspmentChg(Double trnaspmentChg) {
		this.trnaspmentChg = trnaspmentChg;
	}

	public BookingPaymentDO getBookingPayment() {
		return bookingPayment;
	}

	public void setBookingPayment(BookingPaymentDO bookingPayment) {
		this.bookingPayment = bookingPayment;
	}

	/*
	 * public CNPricingDetailsDO getCnPricingDetls() { return cnPricingDetls; }
	 * 
	 * public void setCnPricingDetls(CNPricingDetailsDO cnPricingDetls) {
	 * this.cnPricingDetls = cnPricingDetls; }
	 */

	public ConsigneeConsignorDO getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(ConsigneeConsignorDO consigneeId) {
		this.consigneeId = consigneeId;
	}

	public ConsigneeConsignorDO getConsignorId() {
		return consignorId;
	}

	public void setConsignorId(ConsigneeConsignorDO consignorId) {
		this.consignorId = consignorId;
	}

	public Integer getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getWeightCapturedMode() {
		return weightCapturedMode;
	}

	public void setWeightCapturedMode(String weightCapturedMode) {
		this.weightCapturedMode = weightCapturedMode;
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

	public Double getVolWeight() {
		return volWeight;
	}

	public void setVolWeight(Double volWeight) {
		this.volWeight = volWeight;
	}

	/*
	 * public Set<ChildConsignmentDO> getChildCNs() { return childCNs; }
	 * 
	 * public void setChildCNs(Set<ChildConsignmentDO> childCNs) { this.childCNs
	 * = childCNs; }
	 */

	/*
	 * public Integer getCustomerId() { return customerId; }
	 * 
	 * public void setCustomerId(Integer customerId) { this.customerId =
	 * customerId; }
	 */

	public String getPickRunsheetNo() {
		return pickRunsheetNo;
	}

	public void setPickRunsheetNo(String pickRunsheetNo) {
		this.pickRunsheetNo = pickRunsheetNo;
	}

	/*
	 * public Integer getBusinessAssociateId() { return businessAssociateId; }
	 * 
	 * public void setBusinessAssociateId(Integer businessAssociateId) {
	 * this.businessAssociateId = businessAssociateId; }
	 */

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}

	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
	}

	public Double getInsurenceAmt() {
		return insurenceAmt;
	}

	public void setInsurenceAmt(Double insurenceAmt) {
		this.insurenceAmt = insurenceAmt;
	}

	public Integer getPincodeDlvTimeMapId() {
		return pincodeDlvTimeMapId;
	}

	public void setPincodeDlvTimeMapId(Integer pincodeDlvTimeMapId) {
		this.pincodeDlvTimeMapId = pincodeDlvTimeMapId;
	}

	/**
	 * @return the customerId
	 */
	public CustomerDO getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(CustomerDO customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public InsuredByDO getInsuredBy() {
		return insuredBy;
	}

	public void setInsuredBy(InsuredByDO insuredBy) {
		this.insuredBy = insuredBy;
	}

	/**
	 * @return the bokingPrefs
	 */
	public Set<BookingPreferenceMappingDO> getBokingPrefs() {
		return bokingPrefs;
	}

	/**
	 * @param bokingPrefs
	 *            the bokingPrefs to set
	 */
	public void setBokingPrefs(Set<BookingPreferenceMappingDO> bokingPrefs) {
		this.bokingPrefs = bokingPrefs;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate
	 *            the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the bulkBookingVendorDtls
	 */
	public BulkBookingVendorDtlsDO getBulkBookingVendorDtls() {
		return bulkBookingVendorDtls;
	}

	/**
	 * @param bulkBookingVendorDtls
	 *            the bulkBookingVendorDtls to set
	 */
	public void setBulkBookingVendorDtls(
			BulkBookingVendorDtlsDO bulkBookingVendorDtls) {
		this.bulkBookingVendorDtls = bulkBookingVendorDtls;
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
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	 * @return the orgCityName
	 */
	public String getOrgCityName() {
		return orgCityName;
	}

	/**
	 * @param orgCityName
	 *            the orgCityName to set
	 */
	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}

	/**
	 * @param shippedToCode
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}

	/**
	 * @return
	 */
	public int getExcelRowId() {
		return excelRowId;
	}

	/**
	 * @param excelRowId
	 */
	public void setExcelRowId(int excelRowId) {
		this.excelRowId = excelRowId;
	}

	/**
	 * @return
	 */
	public String getFinalWtStr() {
		return finalWtStr;
	}

	/**
	 * @param finalWtStr
	 */
	public void setFinalWtStr(String finalWtStr) {
		this.finalWtStr = finalWtStr;
	}

	/**
	 * @return the blkBookingType
	 */
	public String getBlkBookingType() {
		return blkBookingType;
	}

	/**
	 * @param blkBookingType the blkBookingType to set
	 */
	public void setBlkBookingType(String blkBookingType) {
		this.blkBookingType = blkBookingType;
	}

	/**
	 * @return the noOfPcsStr
	 */
	public String getNoOfPcsStr() {
		return noOfPcsStr;
	}

	/**
	 * @param noOfPcsStr the noOfPcsStr to set
	 */
	public void setNoOfPcsStr(String noOfPcsStr) {
		this.noOfPcsStr = noOfPcsStr;
	}

	// ADDED FOR PETTY CASH
	/*public String getConsideredForPettyCash() {
		return consideredForPettyCash;
	}

	public void setConsideredForPettyCash(String consideredForPettyCash) {
		this.consideredForPettyCash = consideredForPettyCash;
	}*/
	
}
