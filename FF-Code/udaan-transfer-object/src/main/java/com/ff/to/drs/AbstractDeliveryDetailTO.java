/**
 * 
 */
package com.ff.to.drs;

import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class AbstractDeliveryDetailTO.
 *
 * @author mohammes
 */
public abstract class AbstractDeliveryDetailTO extends CGBaseTO  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2091616585145554226L;
	
	/** The delivery detail id. */
	public Long deliveryDetailId;
	
	public Integer consgnmentId;
	
	/** The origin city code. */
	private String originCityCode;
	
	/** The origin city id. */
	private Integer originCityId;
	
	/** The origin city name. */
	private String originCityName;
	
	/** The consignment number. */
	private String consignmentNumber;
	
	/** The undelivered reason id. */
	private Integer reasonId;
	
	/** The remarks. */
	private String remarks;
	
	/** The missed card number. */
	private String missedCardNumber;

	/**Type of delivery of the Consignment. i.e. Home delivery or Office Delivery or Non Delivery
	 **/
	private String deliveryType;
	
	/** The delivery time str. */
	//private String deliveryTimeStr;
	
	private String deliveryTimeInHHStr;
	
	private String deliveryTimeInMMStr;
	
	
	/** The relation id. */
	private Integer relationId;
	
	/** The contact number. */
	private String contactNumber;
	
	/** The receiver name. */
	private String receiverName;
	
	/** The id proof type id. */
	private Integer idProofTypeId;
	
	/** The id proof number. */
	private String idProofNumber;
	
	/** The row number. */
	public Integer rowNumber;
	
	/** The non dlv reason. */
	private Map<Integer,String> nonDlvReason;
	
	/** The attemp number.it's no of delivery attempts */
	private Integer attemptNumber;
	
	
	
	/** The relation map. */
	private Map<Integer,String> relationMap;
	
	
	/** The id proof map. */
	private Map<Integer,String> idProofMap;
	
	/** The company seal sign. */
	private String companySealSign;
	
	/** FOR PPX consignments  START. */
	
	/** The actual weight. */
	private Double actualWeight;
	
	/** The chargeable weight.  it's a final weight in the Consgment to/Do*/
	private Double chargeableWeight;
	
	/** The content id. */
	private Integer contentId;
	
	/** The content name. */
	private String contentName;
	
	/** The paper work id. */
	private Integer paperWorkId;
	
	/** The paper work name. */
	private String paperWorkName;
	
	/** The amount. */
	private Double amount;
	
	private Integer noOfPieces;
	
	private String vendorName;
	private String vendorCode;
	
	/** The consignee name. */
	private String consigneeName;
	/** FOR PPX consignments  END */
	
	private String deliveryStatus;
	
	/** The id number. */
	private String idNumber;
	
	/** The identity proof type id. */
	private Integer identityProofTypeId;
	
	/** The parent child cn type. P-PARENT CONSIGNMENT,C-CHILD CONSIGNMENT*/
	private String parentChildCnType;
	
	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	/**
	 * Gets the delivery detail id.
	 *
	 * @return the deliveryDetailId
	 */
	public Long getDeliveryDetailId() {
		return deliveryDetailId;
	}
	
	/**
	 * Gets the consignment number.
	 *
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	
	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * Gets the missed card number.
	 *
	 * @return the missedCardNumber
	 */
	public String getMissedCardNumber() {
		return missedCardNumber;
	}
	
	
	/**
	 * Gets the delivery type.
	 *
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}
	
	/**
	 * Gets the contact number.
	 *
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	
	/**
	 * Gets the receiver name.
	 *
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	
	/**
	 * Sets the delivery detail id.
	 *
	 * @param deliveryDetailId the deliveryDetailId to set
	 */
	public void setDeliveryDetailId(Long deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}
	
	
	/**
	 * Sets the consignment number.
	 *
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	
	public Integer getAttemptNumber() {
		return attemptNumber;
	}

	public void setAttemptNumber(Integer attemptNumber) {
		this.attemptNumber = attemptNumber;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * Sets the missed card number.
	 *
	 * @param missedCardNumber the missedCardNumber to set
	 */
	public void setMissedCardNumber(String missedCardNumber) {
		this.missedCardNumber = missedCardNumber;
	}
	
	/**
	 * Sets the delivery type.
	 *
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	/**
	 * Sets the contact number.
	 *
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	/**
	 * Sets the receiver name.
	 *
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * Gets the origin city code.
	 *
	 * @return the originCityCode
	 */
	public String getOriginCityCode() {
		return originCityCode;
	}

	/**
	 * Gets the origin city id.
	 *
	 * @return the originCityId
	 */
	public Integer getOriginCityId() {
		return originCityId;
	}

	/**
	 * Gets the origin city name.
	 *
	 * @return the originCityName
	 */
	public String getOriginCityName() {
		return originCityName;
	}

	/**
	 * Sets the origin city code.
	 *
	 * @param originCityCode the originCityCode to set
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}

	/**
	 * Sets the origin city id.
	 *
	 * @param originCityId the originCityId to set
	 */
	public void setOriginCityId(Integer originCityId) {
		this.originCityId = originCityId;
	}

	/**
	 * Sets the origin city name.
	 *
	 * @param originCityName the originCityName to set
	 */
	public void setOriginCityName(String originCityName) {
		this.originCityName = originCityName;
	}

	/**
	 * Gets the relation id.
	 *
	 * @return the relationId
	 */
	public Integer getRelationId() {
		return relationId;
	}

	/**
	 * Gets the id proof type id.
	 *
	 * @return the idProofTypeId
	 */
	public Integer getIdProofTypeId() {
		return idProofTypeId;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @return the identityProofTypeId
	 */
	public Integer getIdentityProofTypeId() {
		return identityProofTypeId;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @param identityProofTypeId the identityProofTypeId to set
	 */
	public void setIdentityProofTypeId(Integer identityProofTypeId) {
		this.identityProofTypeId = identityProofTypeId;
	}

	/**
	 * Gets the id proof number.
	 *
	 * @return the idProofNumber
	 */
	public String getIdProofNumber() {
		return idProofNumber;
	}

	public String getParentChildCnType() {
		return parentChildCnType;
	}

	public void setParentChildCnType(String parentChildCnType) {
		this.parentChildCnType = parentChildCnType;
	}

	/**
	 * Sets the relation id.
	 *
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	/**
	 * Sets the id proof type id.
	 *
	 * @param idProofTypeId the idProofTypeId to set
	 */
	public void setIdProofTypeId(Integer idProofTypeId) {
		this.idProofTypeId = idProofTypeId;
	}

	/**
	 * Sets the id proof number.
	 *
	 * @param idProofNumber the idProofNumber to set
	 */
	public void setIdProofNumber(String idProofNumber) {
		this.idProofNumber = idProofNumber;
	}

	/**
	 * Gets the reason id.
	 *
	 * @return the reasonId
	 */
	public  Integer getReasonId() {
		return reasonId;
	}

	/**
	 * Sets the reason id.
	 *
	 * @param reasonId the reasonId to set
	 */
	public  void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * @return the nonDlvReason
	 */
	public Map<Integer, String> getNonDlvReason() {
		return nonDlvReason;
	}

	/**
	 * @param nonDlvReason the nonDlvReason to set
	 */
	public void setNonDlvReason(Map<Integer, String> nonDlvReason) {
		this.nonDlvReason = nonDlvReason;
	}

	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	

	/**
	 * @return the companySealSign
	 */
	public String getCompanySealSign() {
		return companySealSign;
	}

	/**
	 * @param companySealSign the companySealSign to set
	 */
	public void setCompanySealSign(String companySealSign) {
		this.companySealSign = companySealSign;
	}

	

	/**
	 * @return the contentId
	 */
	public Integer getContentId() {
		return contentId;
	}

	/**
	 * @return the contentName
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * @return the paperWorkId
	 */
	public Integer getPaperWorkId() {
		return paperWorkId;
	}

	/**
	 * @return the paperWorkName
	 */
	public String getPaperWorkName() {
		return paperWorkName;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	/**
	 * @param contentName the contentName to set
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * @param paperWorkId the paperWorkId to set
	 */
	public void setPaperWorkId(Integer paperWorkId) {
		this.paperWorkId = paperWorkId;
	}

	/**
	 * @param paperWorkName the paperWorkName to set
	 */
	public void setPaperWorkName(String paperWorkName) {
		this.paperWorkName = paperWorkName;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName the consigneeName to set
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return the actualWeight
	 */
	public Double getActualWeight() {
		return actualWeight;
	}

	/**
	 * @return the chargeableWeight
	 */
	public Double getChargeableWeight() {
		return chargeableWeight;
	}

	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @param chargeableWeight the chargeableWeight to set
	 */
	public void setChargeableWeight(Double chargeableWeight) {
		this.chargeableWeight = chargeableWeight;
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
	 * @return the relationMap
	 */
	public Map<Integer, String> getRelationMap() {
		return relationMap;
	}

	/**
	 * @return the idProofMap
	 */
	public Map<Integer, String> getIdProofMap() {
		return idProofMap;
	}

	

	

	/**
	 * @param relationMap the relationMap to set
	 */
	public void setRelationMap(Map<Integer, String> relationMap) {
		this.relationMap = relationMap;
	}

	/**
	 * @param idProofMap the idProofMap to set
	 */
	public void setIdProofMap(Map<Integer, String> idProofMap) {
		this.idProofMap = idProofMap;
	}

	/**
	 * @return the consgnmentId
	 */
	public Integer getConsgnmentId() {
		return consgnmentId;
	}

	/**
	 * @param consgnmentId the consgnmentId to set
	 */
	public void setConsgnmentId(Integer consgnmentId) {
		this.consgnmentId = consgnmentId;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @return the vendorCode
	 */
	public String getVendorCode() {
		return vendorCode;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @param vendorCode the vendorCode to set
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * @return the deliveryTimeInHHStr
	 */
	public String getDeliveryTimeInHHStr() {
		return deliveryTimeInHHStr;
	}

	/**
	 * @param deliveryTimeInHHStr the deliveryTimeInHHStr to set
	 */
	public void setDeliveryTimeInHHStr(String deliveryTimeInHHStr) {
		this.deliveryTimeInHHStr = deliveryTimeInHHStr;
	}

	/**
	 * @return the deliveryTimeInMMStr
	 */
	public String getDeliveryTimeInMMStr() {
		return deliveryTimeInMMStr;
	}

	/**
	 * @param deliveryTimeInMMStr the deliveryTimeInMMStr to set
	 */
	public void setDeliveryTimeInMMStr(String deliveryTimeInMMStr) {
		this.deliveryTimeInMMStr = deliveryTimeInMMStr;
	}

	

	

}
