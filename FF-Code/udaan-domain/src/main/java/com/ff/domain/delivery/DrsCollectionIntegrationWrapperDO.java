package com.ff.domain.delivery;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGBaseDO;

public class DrsCollectionIntegrationWrapperDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 674013650005277810L;
	
	
	public DrsCollectionIntegrationWrapperDO(){
		
	}
	
	/**
	 * @param consgId
	 * @param consgNo
	 * @param codAmt
	 * @param lcAmount
	 * @param topayAmt
	 * @param customerId
	 * @param baAmt
	 * @param deliveryDtlsId
	 * @param drsConsgNumber
	 * @param createdOfficeCode
	 * @param createdOfficeId
	 * @param modeOfPayment
	 * @param chequeDDNumber
	 * @param chequeDDDate
	 * @param bankNameBranch
	 * @param deliveryDate
	 * @param drsCodAmount
	 * @param drsLcAmount
	 * @param drsToPayAmount
	 * @param drsOtherAmount
	 * @param drsBaAmount
	 * @param drsAdditionalCharges
	 */
	public DrsCollectionIntegrationWrapperDO(Integer consgId, String consgNo,
			Double codAmt, Double lcAmount, Double topayAmt,
			Integer customerId, Double baAmt, Long deliveryDtlsId,
			String drsConsgNumber, String createdOfficeCode,
			Integer createdOfficeId, String modeOfPayment,
			String chequeDDNumber, Date chequeDDDate, String bankNameBranch,
			Date deliveryDate, Double drsCodAmount, Double drsLcAmount,
			Double drsToPayAmount, Double drsOtherAmount, Double drsBaAmount,
			Double drsAdditionalCharges) {
		this.consgId = consgId;
		this.consgNo = consgNo;
		this.codAmt = codAmt;
		this.lcAmount = lcAmount;
		this.topayAmt = topayAmt;
		this.customerId = customerId;
		this.baAmt = baAmt;
		this.deliveryDtlsId = deliveryDtlsId;
		this.drsConsgNumber = drsConsgNumber;
		this.createdOfficeCode = createdOfficeCode;
		this.createdOfficeId = createdOfficeId;
		this.modeOfPayment = modeOfPayment;
		this.chequeDDNumber = chequeDDNumber;
		this.chequeDDDate = chequeDDDate;
		this.bankNameBranch = bankNameBranch;
		this.deliveryDate = deliveryDate;
		this.drsCodAmount = drsCodAmount;
		this.drsLcAmount = drsLcAmount;
		this.drsToPayAmount = drsToPayAmount;
		this.drsOtherAmount = drsOtherAmount;
		this.drsBaAmount = drsBaAmount;
		this.drsAdditionalCharges = drsAdditionalCharges;
	}
	public DrsCollectionIntegrationWrapperDO(Integer consgId,Long deliveryDtlsId,
			String drsConsgNumber, String createdOfficeCode,
			String modeOfPayment,
			String chequeDDNumber, Date chequeDDDate, String bankNameBranch,
			Date deliveryDate, Double drsCodAmount, Double drsLcAmount,
			Double drsToPayAmount, Double drsOtherAmount, Double drsBaAmount,
			Double drsAdditionalCharges) {
		this.consgId = consgId;
		this.deliveryDtlsId = deliveryDtlsId;
		this.drsConsgNumber = drsConsgNumber;
		this.createdOfficeCode = createdOfficeCode;
		this.modeOfPayment = modeOfPayment;
		this.chequeDDNumber = chequeDDNumber;
		this.chequeDDDate = chequeDDDate;
		this.bankNameBranch = bankNameBranch;
		this.deliveryDate = deliveryDate;
		this.drsCodAmount = drsCodAmount;
		this.drsLcAmount = drsLcAmount;
		this.drsToPayAmount = drsToPayAmount;
		this.drsOtherAmount = drsOtherAmount;
		this.drsBaAmount = drsBaAmount;
		this.drsAdditionalCharges = drsAdditionalCharges;
	}
	/**
	 *  properties from consignment table
	 * */
	private Integer consgId;
	private String consgNo;
	private Double codAmt;
	private Double lcAmount;
	private Double topayAmt;
	private Integer customerId;
	private Double baAmt;
	
	
	
	

	/**
	 *  properties from DRS table
	 * */
	
	private Long deliveryDtlsId;
	private String drsConsgNumber;
	private String createdOfficeCode;
	private Integer createdOfficeId;
	
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
	/** The cod amount. */
	private Double drsCodAmount;
	
	/** The lc amount. */
	private Double drsLcAmount;
	
	/** The to pay amount. */
	private Double drsToPayAmount;
	
	/** The other amount. */
	private Double drsOtherAmount;
	
	private Double drsBaAmount;
	
	/** The additional charges. */
	private Double drsAdditionalCharges;
	
	private String collectionStatus;
	private Integer productId;

	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}

	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}

	/**
	 * @return the codAmt
	 */
	public Double getCodAmt() {
		return codAmt;
	}

	/**
	 * @param codAmt the codAmt to set
	 */
	public void setCodAmt(Double codAmt) {
		this.codAmt = codAmt;
	}

	/**
	 * @return the lcAmount
	 */
	public Double getLcAmount() {
		return lcAmount;
	}

	/**
	 * @param lcAmount the lcAmount to set
	 */
	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	/**
	 * @return the topayAmt
	 */
	public Double getTopayAmt() {
		return topayAmt;
	}

	/**
	 * @param topayAmt the topayAmt to set
	 */
	public void setTopayAmt(Double topayAmt) {
		this.topayAmt = topayAmt;
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
	 * @return the createdOfficeCode
	 */
	public String getCreatedOfficeCode() {
		return createdOfficeCode;
	}

	/**
	 * @param createdOfficeCode the createdOfficeCode to set
	 */
	public void setCreatedOfficeCode(String createdOfficeCode) {
		this.createdOfficeCode = createdOfficeCode;
	}

	/**
	 * @return the createdOfficeId
	 */
	public Integer getCreatedOfficeId() {
		return createdOfficeId;
	}

	/**
	 * @param createdOfficeId the createdOfficeId to set
	 */
	public void setCreatedOfficeId(Integer createdOfficeId) {
		this.createdOfficeId = createdOfficeId;
	}

	/**
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @param modeOfPayment the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @return the chequeDDNumber
	 */
	public String getChequeDDNumber() {
		return chequeDDNumber;
	}

	/**
	 * @param chequeDDNumber the chequeDDNumber to set
	 */
	public void setChequeDDNumber(String chequeDDNumber) {
		this.chequeDDNumber = chequeDDNumber;
	}

	/**
	 * @return the chequeDDDate
	 */
	public Date getChequeDDDate() {
		return chequeDDDate;
	}

	/**
	 * @param chequeDDDate the chequeDDDate to set
	 */
	public void setChequeDDDate(Date chequeDDDate) {
		this.chequeDDDate = chequeDDDate;
	}

	/**
	 * @return the bankNameBranch
	 */
	public String getBankNameBranch() {
		return bankNameBranch;
	}

	/**
	 * @param bankNameBranch the bankNameBranch to set
	 */
	public void setBankNameBranch(String bankNameBranch) {
		this.bankNameBranch = bankNameBranch;
	}

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
	 * @return the drsCodAmount
	 */
	public Double getDrsCodAmount() {
		return drsCodAmount;
	}

	/**
	 * @param drsCodAmount the drsCodAmount to set
	 */
	public void setDrsCodAmount(Double drsCodAmount) {
		this.drsCodAmount = drsCodAmount;
	}

	/**
	 * @return the drsLcAmount
	 */
	public Double getDrsLcAmount() {
		return drsLcAmount;
	}

	/**
	 * @param drsLcAmount the drsLcAmount to set
	 */
	public void setDrsLcAmount(Double drsLcAmount) {
		this.drsLcAmount = drsLcAmount;
	}

	/**
	 * @return the drsToPayAmount
	 */
	public Double getDrsToPayAmount() {
		return drsToPayAmount;
	}

	/**
	 * @param drsToPayAmount the drsToPayAmount to set
	 */
	public void setDrsToPayAmount(Double drsToPayAmount) {
		this.drsToPayAmount = drsToPayAmount;
	}

	/**
	 * @return the drsOtherAmount
	 */
	public Double getDrsOtherAmount() {
		return drsOtherAmount;
	}

	/**
	 * @param drsOtherAmount the drsOtherAmount to set
	 */
	public void setDrsOtherAmount(Double drsOtherAmount) {
		this.drsOtherAmount = drsOtherAmount;
	}

	/**
	 * @return the drsBaAmount
	 */
	public Double getDrsBaAmount() {
		return drsBaAmount;
	}

	/**
	 * @param drsBaAmount the drsBaAmount to set
	 */
	public void setDrsBaAmount(Double drsBaAmount) {
		this.drsBaAmount = drsBaAmount;
	}

	/**
	 * @return the drsAdditionalCharges
	 */
	public Double getDrsAdditionalCharges() {
		return drsAdditionalCharges;
	}

	/**
	 * @param drsAdditionalCharges the drsAdditionalCharges to set
	 */
	public void setDrsAdditionalCharges(Double drsAdditionalCharges) {
		this.drsAdditionalCharges = drsAdditionalCharges;
	}

	/**
	 * @return the deliveryDtlsId
	 */
	public Long getDeliveryDtlsId() {
		return deliveryDtlsId;
	}

	/**
	 * @param deliveryDtlsId the deliveryDtlsId to set
	 */
	public void setDeliveryDtlsId(Long deliveryDtlsId) {
		this.deliveryDtlsId = deliveryDtlsId;
	}

	/**
	 * @return the drsConsgNumber
	 */
	public String getDrsConsgNumber() {
		return drsConsgNumber;
	}

	/**
	 * @param drsConsgNumber the drsConsgNumber to set
	 */
	public void setDrsConsgNumber(String drsConsgNumber) {
		this.drsConsgNumber = drsConsgNumber;
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
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	

}
