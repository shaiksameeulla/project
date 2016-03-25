/**
 * 
 */
package com.ff.domain.booking;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * BookingDuplicateDO for duplicate bookings in central server for
 * TwoWayWriteProcess
 * 
 * @author shashsax
 * 
 */
public class BookingDuplicateDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4794049770474024977L;

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
	//private Double price;
	//private Double height;
	//private Double length;
	//private Double breath;
	private Double declaredValue;
	private Integer cnContentId;//Master object
	private Integer cnPaperWorkId;//Master object
	//private Double trnaspmentChg;
	private String cnStatus = "A";
	private String status;
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
	//private Double insurenceAmt;
	private String refNo;
	private Integer pincodeDlvTimeMapId;
	private Integer insuredBy; // Master object
	private Set<BcunBookingPreferenceMappingDO> bokingPrefs;// Fact object
	private BulkBookingVendorDtlsDO bulkBookingVendorDtls; // Fact object
	//private String processNumber;
	private Integer createdBy;
	private String bookingOffCode;
	private String orgCityName;
	//private String shippedToCode;
	
	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the bookingType
	 */
	public Integer getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(Integer bookingType) {
		this.bookingType = bookingType;
	}
	/**
	 * @return the bookingOfficeId
	 */
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	/**
	 * @param bookingOfficeId the bookingOfficeId to set
	 */
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the noOfPieces
	 */
	public Integer getNoOfPieces() {
		return noOfPieces;
	}
	/**
	 * @param noOfPieces the noOfPieces to set
	 */
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
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
	 * @return the fianlWeight
	 */
	public Double getFianlWeight() {
		return fianlWeight;
	}
	/**
	 * @param fianlWeight the fianlWeight to set
	 */
	public void setFianlWeight(Double fianlWeight) {
		this.fianlWeight = fianlWeight;
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
	 * @return the consgTypeId
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}
	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}
	/**
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
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
	 * @return the cnContentId
	 */
	public Integer getCnContentId() {
		return cnContentId;
	}
	/**
	 * @param cnContentId the cnContentId to set
	 */
	public void setCnContentId(Integer cnContentId) {
		this.cnContentId = cnContentId;
	}
	/**
	 * @return the cnPaperWorkId
	 */
	public Integer getCnPaperWorkId() {
		return cnPaperWorkId;
	}
	/**
	 * @param cnPaperWorkId the cnPaperWorkId to set
	 */
	public void setCnPaperWorkId(Integer cnPaperWorkId) {
		this.cnPaperWorkId = cnPaperWorkId;
	}
	/**
	 * @return the cnStatus
	 */
	public String getCnStatus() {
		return cnStatus;
	}
	/**
	 * @param cnStatus the cnStatus to set
	 */
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the destCityId
	 */
	public Integer getDestCityId() {
		return destCityId;
	}
	/**
	 * @param destCityId the destCityId to set
	 */
	public void setDestCityId(Integer destCityId) {
		this.destCityId = destCityId;
	}
	/**
	 * @return the updatedProcess
	 */
	public Integer getUpdatedProcess() {
		return updatedProcess;
	}
	/**
	 * @param updatedProcess the updatedProcess to set
	 */
	public void setUpdatedProcess(Integer updatedProcess) {
		this.updatedProcess = updatedProcess;
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
	/**
	 * @return the approvedBy
	 */
	public Integer getApprovedBy() {
		return approvedBy;
	}
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}
	/**
	 * @return the weightCapturedMode
	 */
	public String getWeightCapturedMode() {
		return weightCapturedMode;
	}
	/**
	 * @param weightCapturedMode the weightCapturedMode to set
	 */
	public void setWeightCapturedMode(String weightCapturedMode) {
		this.weightCapturedMode = weightCapturedMode;
	}
	/**
	 * @return the otherCNContent
	 */
	public String getOtherCNContent() {
		return otherCNContent;
	}
	/**
	 * @param otherCNContent the otherCNContent to set
	 */
	public void setOtherCNContent(String otherCNContent) {
		this.otherCNContent = otherCNContent;
	}
	/**
	 * @return the paperWorkRefNo
	 */
	public String getPaperWorkRefNo() {
		return paperWorkRefNo;
	}
	/**
	 * @param paperWorkRefNo the paperWorkRefNo to set
	 */
	public void setPaperWorkRefNo(String paperWorkRefNo) {
		this.paperWorkRefNo = paperWorkRefNo;
	}
	/**
	 * @return the pickRunsheetNo
	 */
	public String getPickRunsheetNo() {
		return pickRunsheetNo;
	}
	/**
	 * @param pickRunsheetNo the pickRunsheetNo to set
	 */
	public void setPickRunsheetNo(String pickRunsheetNo) {
		this.pickRunsheetNo = pickRunsheetNo;
	}
	/**
	 * @return the insurencePolicyNo
	 */
	public String getInsurencePolicyNo() {
		return insurencePolicyNo;
	}
	/**
	 * @param insurencePolicyNo the insurencePolicyNo to set
	 */
	public void setInsurencePolicyNo(String insurencePolicyNo) {
		this.insurencePolicyNo = insurencePolicyNo;
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
	 * @return the pincodeDlvTimeMapId
	 */
	public Integer getPincodeDlvTimeMapId() {
		return pincodeDlvTimeMapId;
	}
	/**
	 * @param pincodeDlvTimeMapId the pincodeDlvTimeMapId to set
	 */
	public void setPincodeDlvTimeMapId(Integer pincodeDlvTimeMapId) {
		this.pincodeDlvTimeMapId = pincodeDlvTimeMapId;
	}
	/**
	 * @return the insuredBy
	 */
	public Integer getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(Integer insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the bokingPrefs
	 */
	public Set<BcunBookingPreferenceMappingDO> getBokingPrefs() {
		return bokingPrefs;
	}
	/**
	 * @param bokingPrefs the bokingPrefs to set
	 */
	public void setBokingPrefs(Set<BcunBookingPreferenceMappingDO> bokingPrefs) {
		this.bokingPrefs = bokingPrefs;
	}
	/**
	 * @return the bulkBookingVendorDtls
	 */
	public BulkBookingVendorDtlsDO getBulkBookingVendorDtls() {
		return bulkBookingVendorDtls;
	}
	/**
	 * @param bulkBookingVendorDtls the bulkBookingVendorDtls to set
	 */
	public void setBulkBookingVendorDtls(
			BulkBookingVendorDtlsDO bulkBookingVendorDtls) {
		this.bulkBookingVendorDtls = bulkBookingVendorDtls;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
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
	 * @param bookingOffCode the bookingOffCode to set
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
	 * @param orgCityName the orgCityName to set
	 */
	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}
}
