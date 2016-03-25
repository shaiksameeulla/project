package com.ff.domain.mec.collection;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.PaymentModeDO;

/**
 * @author prmeher
 * 
 */
public class CollectionDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer collectionId;
	private String txnNo;
	private String collectionCategory;
	private Date CollectionDate;
	private CustomerDO customerDO;
	private String chqNo;
	private Date chqDate;
	private Double totalAmount;
	private String status;
	 @JsonManagedReference 
	private Set<CollectionDtlsDO> collectionDtls;
	private PaymentModeDO paymentModeDO;
	private OfficeDO collectionOfficeDO;
	private String bankName;
	private GLMasterDO bankGLDO;
	private String isReasonPartyLetter;
	private String isRecalculationReq = CommonConstants.NO;

	/**
	 * @return the isRecalculationReq
	 */
	public String getIsRecalculationReq() {
		return isRecalculationReq;
	}

	/**
	 * @param isRecalculationReq
	 *            the isRecalculationReq to set
	 */
	public void setIsRecalculationReq(String isRecalculationReq) {
		this.isRecalculationReq = isRecalculationReq;
	}

	/**
	 * @return the bankGLDO
	 */
	public GLMasterDO getBankGLDO() {
		return bankGLDO;
	}

	/**
	 * @param bankGLDO
	 *            the bankGLDO to set
	 */
	public void setBankGLDO(GLMasterDO bankGLDO) {
		this.bankGLDO = bankGLDO;
	}

	/**
	 * @return the isReasonPartyLetter
	 */
	public String getIsReasonPartyLetter() {
		return isReasonPartyLetter;
	}

	/**
	 * @param isReasonPartyLetter
	 *            the isReasonPartyLetter to set
	 */
	public void setIsReasonPartyLetter(String isReasonPartyLetter) {
		this.isReasonPartyLetter = isReasonPartyLetter;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the collectionOfficeDO
	 */
	public OfficeDO getCollectionOfficeDO() {
		return collectionOfficeDO;
	}

	/**
	 * @param collectionOfficeDO
	 *            the collectionOfficeDO to set
	 */
	public void setCollectionOfficeDO(OfficeDO collectionOfficeDO) {
		this.collectionOfficeDO = collectionOfficeDO;
	}

	/**
	 * @return the customerDO
	 */
	public CustomerDO getCustomerDO() {
		return customerDO;
	}

	/**
	 * @param customerDO
	 *            the customerDO to set
	 */
	public void setCustomerDO(CustomerDO customerDO) {
		this.customerDO = customerDO;
	}

	/**
	 * @return the collectionId
	 */
	public Integer getCollectionId() {
		return collectionId;
	}

	/**
	 * @param collectionId
	 *            the collectionId to set
	 */
	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the txnNo
	 */
	public String getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * @return the collectionCategory
	 */
	public String getCollectionCategory() {
		return collectionCategory;
	}

	/**
	 * @param collectionCategory
	 *            the collectionCategory to set
	 */
	public void setCollectionCategory(String collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	/**
	 * @return the collectionDate
	 */
	public Date getCollectionDate() {
		return CollectionDate;
	}

	/**
	 * @param collectionDate
	 *            the collectionDate to set
	 */
	public void setCollectionDate(Date collectionDate) {
		CollectionDate = collectionDate;
	}

	/**
	 * @return the collectionModeId
	 */
	/*
	 * public Integer getCollectionModeId() { return collectionModeId; }
	 *//**
	 * @param collectionModeId
	 *            the collectionModeId to set
	 */
	/*
	 * public void setCollectionModeId(Integer collectionModeId) {
	 * this.collectionModeId = collectionModeId; }
	 */
	/**
	 * @return the chqNo
	 */
	public String getChqNo() {
		return chqNo;
	}

	/**
	 * @param chqNo
	 *            the chqNo to set
	 */
	public void setChqNo(String chqNo) {
		this.chqNo = chqNo;
	}

	/**
	 * @return the chqDate
	 */
	public Date getChqDate() {
		return chqDate;
	}

	/**
	 * @param chqDate
	 *            the chqDate to set
	 */
	public void setChqDate(Date chqDate) {
		this.chqDate = chqDate;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the collectionDtls
	 */
	public Set<CollectionDtlsDO> getCollectionDtls() {
		return collectionDtls;
	}

	/**
	 * @param collectionDtls
	 *            the collectionDtls to set
	 */
	public void setCollectionDtls(Set<CollectionDtlsDO> collectionDtls) {
		this.collectionDtls = collectionDtls;
	}

	/**
	 * @return the paymentModeDO
	 */
	public PaymentModeDO getPaymentModeDO() {
		return paymentModeDO;
	}

	/**
	 * @param paymentModeDO
	 *            the paymentModeDO to set
	 */
	public void setPaymentModeDO(PaymentModeDO paymentModeDO) {
		this.paymentModeDO = paymentModeDO;
	}
}
