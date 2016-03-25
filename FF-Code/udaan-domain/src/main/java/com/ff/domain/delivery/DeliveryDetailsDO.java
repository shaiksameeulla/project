/**
 * 
 */
package com.ff.domain.delivery;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.serviceOffering.IdentityProofTypeDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.domain.serviceOffering.RelationDO;

/**
 * The Class DeliveryDetailsDO.
 *
 * @author mohammes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryDetailsDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4211345185372704683L;

	/** The delivery detail id. */
	private Long deliveryDetailId;
	
	/** The row number. */
	private Integer rowNumber;
	
	/** The consignment number. */
	private  String consignmentNumber;
	
	/** The delivery type. 
	 * Type of delivery of the Consignment. i.e. Home delivery or Office Delivery or Non Delivery*/
	private String deliveryType;
	
	/** The missed card number. */
	private String missedCardNumber;
	
	/** The remarks. 
	 * User enters remarks if the Pending reason was selected as ‘others’.*/
	private String remarks;
	
	/** The company seal sign. */
	private String companySealSign;
	
	/** The receiver name. */
	private String receiverName;
	
	/** The contact number. */
	private String contactNumber;
	
	/** The id number. */
	private String idNumber;
	
	/** The mode of payment. 
	 * Mode of payment for amount to be collected i.e Cash or Cheque or DD ;Enabled for COD/To Pay/ LC consignments*/
	private String modeOfPayment;
	
	/** The cheque dd number. */
	private String chequeDDNumber;
	
	/** The cheque dd date. */
	private Date chequeDDDate;
	
	/** The bank name branch. */
	private String bankNameBranch;
	
	/** The delivery date. */
	private Date deliveryDate;
	
	/** The delivery status. 
	 * O-OUT FOR DELIVERY,D-DELIVERED,P-PENDING/NON DELIVERED
	 * */
	private String deliveryStatus;
	
	
	/** The record status.
	 * 
	 * CN number STATUS A-ACTIVE,I-INACTIVE
	 *  IF DRS IS DISCARED, THEN CN should ALSO BE DISCARDED automatically hence cn is will be inactive)
	 *  
	 * */
	private String recordStatus="A";
	
	/** The attemp number.it's no of delivery attempts */
	private Integer attemptNumber;
	
	
	/** The no of pieces. for PPX */
	private Integer noOfPieces;
	
	
	/** The delivery do. */
	 @JsonBackReference
	private DeliveryDO deliveryDO;
	
	/** The relation do. */
	private RelationDO relationDO;
	
	/** The id proof do. */
	private IdentityProofTypeDO idProofDO;
	
	/** The consignment do. */
	private ConsignmentDO consignmentDO;
	
	/** The reason do. */
	private ReasonDO reasonDO;
	
	/** The origin city do. */
	private CityDO originCityDO;

	
	/** The cod amount. */
	private Double codAmount;
	
	/** The lc amount. */
	private Double lcAmount;
	
	/** The to pay amount. */
	private Double toPayAmount;
	
	/** The other amount. */
	private Double otherAmount;
	
	private Double baAmount;
	
	

	/** The additional charges. */
	private Double additionalCharges;
	
	/** The parent child cn type. P-PARENT CONSIGNMENT,C-CHILD CONSIGNMENT*/
	private String parentChildCnType;
	
	private String vendorName;
	private String vendorCode;
	
	/** The collection status.  it's only for COD/LC/TO Pay Consg which are delivered, 
	 * and this flag holds the status of the Collection integration, if the flag is N or Null ,
	 * then it's not posted to collection otherwise information posted to Collection*/
	private String collectionStatus="N";
	
	private Date transactionCreateDate = Calendar.getInstance().getTime();
	private Date transactionModifiedDate = Calendar.getInstance().getTime();
	
	private Integer productId;
	private String officeCode;
	
	private String consignmentUpdateFlag="N";
	
	private String isDestinationMismatch="N";
	
	/**
	 * Gets the row number.
	 *
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
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
	 * Gets the delivery type.
	 *
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
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
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Gets the company seal sign.
	 *
	 * @return the companySealSign
	 */
	public String getCompanySealSign() {
		return companySealSign;
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
	 * Gets the contact number.
	 *
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * Gets the id number.
	 *
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * Gets the mode of payment.
	 *
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * Gets the cheque dd number.
	 *
	 * @return the chequeDDNumber
	 */
	public String getChequeDDNumber() {
		return chequeDDNumber;
	}

	/**
	 * Gets the cheque dd date.
	 *
	 * @return the chequeDDDate
	 */
	public Date getChequeDDDate() {
		return chequeDDDate;
	}

	/**
	 * Gets the bank name branch.
	 *
	 * @return the bankNameBranch
	 */
	public String getBankNameBranch() {
		return bankNameBranch;
	}

	/**
	 * Gets the delivery date.
	 *
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Gets the delivery status.
	 *
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	

	

	/**
	 * Gets the delivery do.
	 *
	 * @return the deliveryDO
	 */
	public DeliveryDO getDeliveryDO() {
		return deliveryDO;
	}

	/**
	 * Gets the relation do.
	 *
	 * @return the relationDO
	 */
	public RelationDO getRelationDO() {
		return relationDO;
	}

	/**
	 * Gets the id proof do.
	 *
	 * @return the idProofDO
	 */
	public IdentityProofTypeDO getIdProofDO() {
		return idProofDO;
	}

	/**
	 * Gets the consignment do.
	 *
	 * @return the consignmentDO
	 */
	public ConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}

	/**
	 * Gets the reason do.
	 *
	 * @return the reasonDO
	 */
	public ReasonDO getReasonDO() {
		return reasonDO;
	}

	/**
	 * Gets the origin city do.
	 *
	 * @return the originCityDO
	 */
	public CityDO getOriginCityDO() {
		return originCityDO;
	}

	/**
	 * Sets the row number.
	 *
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
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
	 * Sets the consignment number.
	 *
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = !StringUtil.isStringEmpty(consignmentNumber)?consignmentNumber.trim().toUpperCase():consignmentNumber;
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
	 * Sets the missed card number.
	 *
	 * @param missedCardNumber the missedCardNumber to set
	 */
	public void setMissedCardNumber(String missedCardNumber) {
		this.missedCardNumber = missedCardNumber;
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
	 * Sets the company seal sign.
	 *
	 * @param companySealSign the companySealSign to set
	 */
	public void setCompanySealSign(String companySealSign) {
		this.companySealSign = companySealSign;
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
	 * Sets the contact number.
	 *
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the transactionCreateDate
	 */
	public Date getTransactionCreateDate() {
		return transactionCreateDate;
	}

	/**
	 * @param transactionCreateDate the transactionCreateDate to set
	 */
	public void setTransactionCreateDate(Date transactionCreateDate) {
		this.transactionCreateDate = transactionCreateDate;
	}

	/**
	 * @return the transactionModifiedDate
	 */
	public Date getTransactionModifiedDate() {
		return transactionModifiedDate;
	}

	/**
	 * @param transactionModifiedDate the transactionModifiedDate to set
	 */
	public void setTransactionModifiedDate(Date transactionModifiedDate) {
		this.transactionModifiedDate = transactionModifiedDate;
	}

	/**
	 * @return the collectionStatus
	 */
	public String getCollectionStatus() {
		return collectionStatus;
	}

	/**
	 * @param collectionStatus the collectionStatus to set
	 */
	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	/**
	 * Sets the id number.
	 *
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * Sets the mode of payment.
	 *
	 * @param modeOfPayment the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * Sets the cheque dd number.
	 *
	 * @param chequeDDNumber the chequeDDNumber to set
	 */
	public void setChequeDDNumber(String chequeDDNumber) {
		this.chequeDDNumber = chequeDDNumber;
	}

	/**
	 * Sets the cheque dd date.
	 *
	 * @param chequeDDDate the chequeDDDate to set
	 */
	public void setChequeDDDate(Date chequeDDDate) {
		this.chequeDDDate = chequeDDDate;
	}

	/**
	 * Sets the bank name branch.
	 *
	 * @param bankNameBranch the bankNameBranch to set
	 */
	public void setBankNameBranch(String bankNameBranch) {
		this.bankNameBranch = bankNameBranch;
	}

	/**
	 * Sets the delivery date.
	 *
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * Sets the delivery status.
	 *
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	

	
	/**
	 * Sets the delivery do.
	 *
	 * @param deliveryDO the deliveryDO to set
	 */
	public void setDeliveryDO(DeliveryDO deliveryDO) {
		this.deliveryDO = deliveryDO;
	}

	/**
	 * Sets the relation do.
	 *
	 * @param relationDO the relationDO to set
	 */
	public void setRelationDO(RelationDO relationDO) {
		this.relationDO = relationDO;
	}

	/**
	 * Sets the id proof do.
	 *
	 * @param idProofDO the idProofDO to set
	 */
	public void setIdProofDO(IdentityProofTypeDO idProofDO) {
		this.idProofDO = idProofDO;
	}

	/**
	 * Sets the consignment do.
	 *
	 * @param consignmentDO the consignmentDO to set
	 */
	public void setConsignmentDO(ConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}

	/**
	 * Sets the reason do.
	 *
	 * @param reasonDO the reasonDO to set
	 */
	public void setReasonDO(ReasonDO reasonDO) {
		this.reasonDO = reasonDO;
	}

	/**
	 * Sets the origin city do.
	 *
	 * @param originCityDO the originCityDO to set
	 */
	public void setOriginCityDO(CityDO originCityDO) {
		this.originCityDO = originCityDO;
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
	 * Sets the delivery detail id.
	 *
	 * @param deliveryDetailId the deliveryDetailId to set
	 */
	public void setDeliveryDetailId(Long deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}

	/**
	 * Gets the no of pieces.
	 *
	 * @return the noOfPieces
	 */
	public Integer getNoOfPieces() {
		return noOfPieces;
	}

	/**
	 * Sets the no of pieces.
	 *
	 * @param noOfPieces the noOfPieces to set
	 */
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
	}

	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}

	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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
	
	public Double getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(Double codAmount) {
		this.codAmount = codAmount;
	}

	public Double getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	public Double getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(Double toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public Double getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Double getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(Double additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public String getParentChildCnType() {
		return parentChildCnType;
	}

	public void setParentChildCnType(String parentChildCnType) {
		this.parentChildCnType = parentChildCnType;
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
	 * @return the productId
	 */
	@JsonIgnore
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @return the officeCode
	 */
	@JsonIgnore
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the consignmentUpdateFlag
	 */
	public String getConsignmentUpdateFlag() {
		return consignmentUpdateFlag;
	}

	/**
	 * @param consignmentUpdateFlag the consignmentUpdateFlag to set
	 */
	public void setConsignmentUpdateFlag(String consignmentUpdateFlag) {
		this.consignmentUpdateFlag = consignmentUpdateFlag;
	}

	/**
	 * @return the isDestinationMismatch
	 */
	public String getIsDestinationMismatch() {
		return isDestinationMismatch;
	}

	/**
	 * @param isDestinationMismatch the isDestinationMismatch to set
	 */
	public void setIsDestinationMismatch(String isDestinationMismatch) {
		this.isDestinationMismatch = isDestinationMismatch;
	}

	
}
