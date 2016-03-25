/**
 * 
 */
package com.ff.to.drs;

import java.util.Date;
import java.util.Map;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.to.serviceofferings.IdentityProofTypeTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.serviceofferings.RelationTO;

/**
 * @author mohammes
 *
 */
public class DeliveryDetailsTO extends CGBaseTO implements Comparable<DeliveryDetailsTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -707954406590144980L;

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
	private String recordStatus;
	
	/** The attemp number.it's no of delivery attempts */
	private Integer attemptNumber;
	
	
	/** The no of pieces. for PPX */
	private Integer noOfPieces;
	
	
	/** The relation To. */
	private RelationTO relationTO;
	
	/** The id proof To. */
	private IdentityProofTypeTO idProofTO;
	
	/** The consignment To. */
	private ConsignmentTO consignmentTO;
	
	/** The reason To. */
	private ReasonTO reasonTO;
	
	/** The origin city To. */
	private CityTO originCityTO;
	
	/** The delivery to. */
	private DeliveryTO deliveryTO;
	
	/** The non dlv reason. */
	private Map<Integer,String> nonDlvReason;
	
	
	
	
	
	/** The relation map. */
	private Map<Integer,String> relationMap;
	
	
	/** The id proof map. */
	private Map<Integer,String> idProofMap;

	
	/** The cod amount. */
	private Double codAmount;
	
	/** The lc amount. */
	private Double lcAmount;
	
	/** The to pay amount. */
	private Double toPayAmount;
	
	private Double baAmount;
	
	/** The other amount. */
	private Double otherAmount;
	
	/** The additional charges. */
	private Double additionalCharges;
	/** The parent child cn type. P-PARENT CONSIGNMENT,C-CHILD CONSIGNMENT*/
	private String parentChildCnType;
	
	private String vendorName;
	private String vendorCode;
	
	/**
	 * 
	 * @return the deliveryDetailId
	 */
	public Long getDeliveryDetailId() {
		return deliveryDetailId;
	}

	/**
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * @return the consignmentNumber
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @return the missedCardNumber
	 */
	public String getMissedCardNumber() {
		return missedCardNumber;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the companySealSign
	 */
	public String getCompanySealSign() {
		return companySealSign;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @return the chequeDDNumber
	 */
	public String getChequeDDNumber() {
		return chequeDDNumber;
	}

	/**
	 * @return the chequeDDDate
	 */
	public Date getChequeDDDate() {
		return chequeDDDate;
	}

	/**
	 * @return the bankNameBranch
	 */
	public String getBankNameBranch() {
		return bankNameBranch;
	}

	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}

	

	/**
	 * @return the noOfPieces
	 */
	public Integer getNoOfPieces() {
		return noOfPieces;
	}

	/**
	 * @return the relationTO
	 */
	public RelationTO getRelationTO() {
		return relationTO;
	}

	/**
	 * @return the idProofTO
	 */
	public IdentityProofTypeTO getIdProofTO() {
		return idProofTO;
	}

	/**
	 * @return the consignmentTO
	 */
	public ConsignmentTO getConsignmentTO() {
		return consignmentTO;
	}

	/**
	 * @return the reasonTO
	 */
	public ReasonTO getReasonTO() {
		return reasonTO;
	}

	
	/**
	 * @param deliveryDetailId the deliveryDetailId to set
	 */
	public void setDeliveryDetailId(Long deliveryDetailId) {
		this.deliveryDetailId = deliveryDetailId;
	}

	/**
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
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @param missedCardNumber the missedCardNumber to set
	 */
	public void setMissedCardNumber(String missedCardNumber) {
		this.missedCardNumber = missedCardNumber;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param companySealSign the companySealSign to set
	 */
	public void setCompanySealSign(String companySealSign) {
		this.companySealSign = companySealSign;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @param modeOfPayment the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @param chequeDDNumber the chequeDDNumber to set
	 */
	public void setChequeDDNumber(String chequeDDNumber) {
		this.chequeDDNumber = chequeDDNumber;
	}

	/**
	 * @param chequeDDDate the chequeDDDate to set
	 */
	public void setChequeDDDate(Date chequeDDDate) {
		this.chequeDDDate = chequeDDDate;
	}

	/**
	 * @param bankNameBranch the bankNameBranch to set
	 */
	public void setBankNameBranch(String bankNameBranch) {
		this.bankNameBranch = bankNameBranch;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	

	/**
	 * @param noOfPieces the noOfPieces to set
	 */
	public void setNoOfPieces(Integer noOfPieces) {
		this.noOfPieces = noOfPieces;
	}

	/**
	 * @param relationTO the relationTO to set
	 */
	public void setRelationTO(RelationTO relationTO) {
		this.relationTO = relationTO;
	}

	/**
	 * @param idProofTO the idProofTO to set
	 */
	public void setIdProofTO(IdentityProofTypeTO idProofTO) {
		this.idProofTO = idProofTO;
	}

	/**
	 * @param consignmentTO the consignmentTO to set
	 */
	public void setConsignmentTO(ConsignmentTO consignmentTO) {
		this.consignmentTO = consignmentTO;
	}

	/**
	 * @param reasonTO the reasonTO to set
	 */
	public void setReasonTO(ReasonTO reasonTO) {
		this.reasonTO = reasonTO;
	}

	/**
	 * @return the originCityTO
	 */
	public CityTO getOriginCityTO() {
		return originCityTO;
	}

	/**
	 * @param originCityTO the originCityTO to set
	 */
	public void setOriginCityTO(CityTO originCityTO) {
		this.originCityTO = originCityTO;
	}

	/**
	 * @return the deliveryTO
	 */
	public DeliveryTO getDeliveryTO() {
		return deliveryTO;
	}

	/**
	 * @param deliveryTO the deliveryTO to set
	 */
	public void setDeliveryTO(DeliveryTO deliveryTO) {
		this.deliveryTO = deliveryTO;
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

	public int compareTo(DeliveryDetailsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(deliveryDetailId) && !StringUtil.isEmptyLong(arg0.getDeliveryDetailId())) {
			result = this.deliveryDetailId.compareTo(arg0.deliveryDetailId);
		}
		return result;
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
}
