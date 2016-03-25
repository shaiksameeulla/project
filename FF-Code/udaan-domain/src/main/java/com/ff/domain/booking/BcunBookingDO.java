package com.ff.domain.booking;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BcunBookingDO extends CGFactDO 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2753299598766361116L;
	private Date deliveryDate;
	private Integer bookingId;
	private String consgNumber;
	private Date bookingDate;
	private Date orderDate;
	private Integer bookingType; //Master object
	private Integer bookingOfficeId;
	private Integer customerId; //Master object
	private Integer noOfPieces;
	private Double actualWeight;
	private Double fianlWeight;
	private Double volWeight;
	private Integer consgTypeId; //Master object
	private Integer pincodeId; //Master object
	private Double price;
	private Double height;
	private Double length;
	private Double breath;
	private Double declaredValue;
	private Integer cnContentId;//Master object
	private Integer cnPaperWorkId;//Master object
	private Double trnaspmentChg;
	private String cnStatus = "A";
	private String status = "B";
	/*private ConsigneeConsignorDO consigneeId; //Fact object
	private ConsigneeConsignorDO consignorId; //Fact object
*/	private Integer destCityId; // Master object
	private Integer updatedProcess; // Master object
	private BookingPaymentDO bookingPayment; // Master object
	private Integer approvedBy;
	private String weightCapturedMode="M";
	private String otherCNContent;
	private String paperWorkRefNo;
	private String pickRunsheetNo;
	private String insurencePolicyNo;
	private Double insurenceAmt;
	private String refNo;
	private Integer pincodeDlvTimeMapId;
	private Integer insuredBy; // Master object
	private Set<BcunBookingPreferenceMappingDO> bokingPrefs;// Fact object
	private BulkBookingVendorDtlsDO bulkBookingVendorDtls; // Fact object
	private String processNumber;
	private Integer createdBy;
	private String bookingOffCode;
	private String orgCityName;
	private String shippedToCode;
		
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
		this.consgNumber = consgNumber;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getBookingType() {
		return bookingType;
	}
	public void setBookingType(Integer bookingType) {
		this.bookingType = bookingType;
	}
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public Integer getPincodeId() {
		return pincodeId;
	}
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public Double getTrnaspmentChg() {
		return trnaspmentChg;
	}
	public void setTrnaspmentChg(Double trnaspmentChg) {
		this.trnaspmentChg = trnaspmentChg;
	}
	public String getCnStatus() {
		return cnStatus;
	}
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getDestCityId() {
		return destCityId;
	}
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	public Integer getUpdatedProcess() {
		return updatedProcess;
	}
	public void setUpdatedProcess(Integer updatedProcess) {
		this.updatedProcess = updatedProcess;
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
	public String getPickRunsheetNo() {
		return pickRunsheetNo;
	}
	public void setPickRunsheetNo(String pickRunsheetNo) {
		this.pickRunsheetNo = pickRunsheetNo;
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
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public Integer getPincodeDlvTimeMapId() {
		return pincodeDlvTimeMapId;
	}
	public void setPincodeDlvTimeMapId(Integer pincodeDlvTimeMapId) {
		this.pincodeDlvTimeMapId = pincodeDlvTimeMapId;
	}
	public Integer getInsuredBy() {
		return insuredBy;
	}
	public void setInsuredBy(Integer insuredBy) {
		this.insuredBy = insuredBy;
	}
	public Set<BcunBookingPreferenceMappingDO> getBokingPrefs() {
		return bokingPrefs;
	}
	public void setBokingPrefs(Set<BcunBookingPreferenceMappingDO> bokingPrefs) {
		this.bokingPrefs = bokingPrefs;
	}
	public BulkBookingVendorDtlsDO getBulkBookingVendorDtls() {
		return bulkBookingVendorDtls;
	}
	public void setBulkBookingVendorDtls(
			BulkBookingVendorDtlsDO bulkBookingVendorDtls) {
		this.bulkBookingVendorDtls = bulkBookingVendorDtls;
	}
	public String getProcessNumber() {
		return processNumber;
	}
	public void setProcessNumber(String processNumber) {
		this.processNumber = processNumber;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getBookingOffCode() {
		return bookingOffCode;
	}
	public void setBookingOffCode(String bookingOffCode) {
		this.bookingOffCode = bookingOffCode;
	}
	public String getOrgCityName() {
		return orgCityName;
	}
	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}
	/**
	 * @return the bookingPayment
	 */
	public BookingPaymentDO getBookingPayment() {
		return bookingPayment;
	}
	/**
	 * @param bookingPayment the bookingPayment to set
	 */
	public void setBookingPayment(BookingPaymentDO bookingPayment) {
		this.bookingPayment = bookingPayment;
	}
	public String getShippedToCode() {
		return shippedToCode;
	}
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}
	
}
