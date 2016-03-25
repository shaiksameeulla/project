package com.ff.domain.mec.collection;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.ReasonDO;

/**
 * @author prmeher
 * 
 */
public class CollectionDtlsDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer entryId;
	@JsonBackReference
	private CollectionDO collectionDO;
	private String collectionAgainst;
	private String billNo;
	private Double billAmount;
	private Double recvAmount;
	private Double tdsAmount;
	private Double deduction;
	private Double totalBillAmount;
	private String remarks;
	private String receiptNo;
	private ConsignmentDO consgDO;
	private String collectionType;
	private String collectionFor;
	private Integer position;
	private Date consgDeliveryDate;
	private ReasonDO reasonDO;
	private Double balanceAmount;

	/**
	 * @return the reasonDO
	 */
	public ReasonDO getReasonDO() {
		return reasonDO;
	}

	/**
	 * @param reasonDO
	 *            the reasonDO to set
	 */
	public void setReasonDO(ReasonDO reasonDO) {
		this.reasonDO = reasonDO;
	}

	/**
	 * @return the consgDeliveryDate
	 */
	public Date getConsgDeliveryDate() {
		return consgDeliveryDate;
	}

	/**
	 * @param consgDeliveryDate
	 *            the consgDeliveryDate to set
	 */
	public void setConsgDeliveryDate(Date consgDeliveryDate) {
		this.consgDeliveryDate = consgDeliveryDate;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the collectionDO
	 */
	public CollectionDO getCollectionDO() {
		return collectionDO;
	}

	/**
	 * @param collectionDO
	 *            the collectionDO to set
	 */
	public void setCollectionDO(CollectionDO collectionDO) {
		this.collectionDO = collectionDO;
	}

	/**
	 * @return the consgDO
	 */
	public ConsignmentDO getConsgDO() {
		return consgDO;
	}

	/**
	 * @param consgDO
	 *            the consgDO to set
	 */
	public void setConsgDO(ConsignmentDO consgDO) {
		this.consgDO = consgDO;
	}

	/**
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
	}

	/**
	 * @param entryId
	 *            the entryId to set
	 */
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	/**
	 * @return the collectionAgainst
	 */
	public String getCollectionAgainst() {
		return collectionAgainst;
	}

	/**
	 * @param collectionAgainst
	 *            the collectionAgainst to set
	 */
	public void setCollectionAgainst(String collectionAgainst) {
		this.collectionAgainst = collectionAgainst;
	}

	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo
	 *            the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return the billAmount
	 */
	public Double getBillAmount() {
		return billAmount;
	}

	/**
	 * @param billAmount
	 *            the billAmount to set
	 */
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	/**
	 * @return the recvAmount
	 */
	public Double getRecvAmount() {
		return recvAmount;
	}

	/**
	 * @param recvAmount
	 *            the recvAmount to set
	 */
	public void setRecvAmount(Double recvAmount) {
		this.recvAmount = recvAmount;
	}

	/**
	 * @return the tdsAmount
	 */
	public Double getTdsAmount() {
		return tdsAmount;
	}

	/**
	 * @param tdsAmount
	 *            the tdsAmount to set
	 */
	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	/**
	 * @return the deduction
	 */
	public Double getDeduction() {
		return deduction;
	}

	/**
	 * @param deduction
	 *            the deduction to set
	 */
	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}

	/**
	 * @return the totalBillAmount
	 */
	public Double getTotalBillAmount() {
		return totalBillAmount;
	}

	/**
	 * @param totalBillAmount
	 *            the totalBillAmount to set
	 */
	public void setTotalBillAmount(Double totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the receiptNo
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * @param receiptNo
	 *            the receiptNo to set
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	/**
	 * @return the collectionType
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType
	 *            the collectionType to set
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the collectionFor
	 */
	public String getCollectionFor() {
		return collectionFor;
	}

	/**
	 * @param collectionFor
	 *            the collectionFor to set
	 */
	public void setCollectionFor(String collectionFor) {
		this.collectionFor = collectionFor;
	}

	/**
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

}
