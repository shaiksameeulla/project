/**
 * 
 */
package com.ff.mec.collection;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author prmeher
 * 
 */
public class CNCollectionDtlsTO extends CGBaseTO implements
		Comparable<CNCollectionDtlsTO> {

	private static final long serialVersionUID = 1L;
	private Integer entryId;
	private Integer srNo;
	private String txnNo;
	private String receiptNo;
	private String cnNo;
	private Integer consgId;
	private String collectionType;
	private String collectionAgainst;
	private Integer collectionTypeId;
	private List<LabelValueBean> collectionTypeList;
	private Double amount;
	private Double rcvdAmt;
	private Double tdsAmt;
	private Integer paymentModeId;
	private String cnfor;
	private String chqNo;
	private String chqDate;
	private String bankName;
	// private Integer bankId;
	private String status;
	private Integer collectionID;
	private Integer reasonId;
	private String consgDeliveryDate;
	private Double total;
	private Integer bankGL;

	private Integer createdBy;
	private Integer updatedBy;


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
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the bankGL
	 */
	public Integer getBankGL() {
		return bankGL;
	}

	/**
	 * @param bankGL
	 *            the bankGL to set
	 */
	public void setBankGL(Integer bankGL) {
		this.bankGL = bankGL;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the consgDeliveryDate
	 */
	public String getConsgDeliveryDate() {
		return consgDeliveryDate;
	}

	/**
	 * @param consgDeliveryDate
	 *            the consgDeliveryDate to set
	 */
	public void setConsgDeliveryDate(String consgDeliveryDate) {
		this.consgDeliveryDate = consgDeliveryDate;
	}

	/**
	 * @return the reasonId
	 */
	public Integer getReasonId() {
		return reasonId;
	}

	/**
	 * @param reasonId
	 *            the reasonId to set
	 */
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the collectionID
	 */
	public Integer getCollectionID() {
		return collectionID;
	}

	/**
	 * @param collectionID
	 *            the collectionID to set
	 */
	public void setCollectionID(Integer collectionID) {
		this.collectionID = collectionID;
	}

	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}

	/**
	 * @param consgId
	 *            the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}

	/**
	 * @return the srNo
	 */
	public Integer getSrNo() {
		return srNo;
	}

	/**
	 * @param srNo
	 *            the srNo to set
	 */
	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
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
	 * @return the cnNo
	 */
	public String getCnNo() {
		return cnNo;
	}

	/**
	 * @param cnNo
	 *            the cnNo to set
	 */
	public void setCnNo(String cnNo) {
		this.cnNo = cnNo;
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
	 * @return the collectionTypeId
	 */
	public Integer getCollectionTypeId() {
		return collectionTypeId;
	}

	/**
	 * @param collectionTypeId
	 *            the collectionTypeId to set
	 */
	public void setCollectionTypeId(Integer collectionTypeId) {
		this.collectionTypeId = collectionTypeId;
	}

	/**
	 * @return the collectionTypeList
	 */
	public List<LabelValueBean> getCollectionTypeList() {
		return collectionTypeList;
	}

	/**
	 * @param collectionTypeList
	 *            the collectionTypeList to set
	 */
	public void setCollectionTypeList(List<LabelValueBean> collectionTypeList) {
		this.collectionTypeList = collectionTypeList;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the rcvdAmt
	 */
	public Double getRcvdAmt() {
		return rcvdAmt;
	}

	/**
	 * @param rcvdAmt
	 *            the rcvdAmt to set
	 */
	public void setRcvdAmt(Double rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	/**
	 * @return the tdsAmt
	 */
	public Double getTdsAmt() {
		return tdsAmt;
	}

	/**
	 * @param tdsAmt
	 *            the tdsAmt to set
	 */
	public void setTdsAmt(Double tdsAmt) {
		this.tdsAmt = tdsAmt;
	}

	/**
	 * @return the paymentModeId
	 */
	public Integer getPaymentModeId() {
		return paymentModeId;
	}

	/**
	 * @param paymentModeId
	 *            the paymentModeId to set
	 */
	public void setPaymentModeId(Integer paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	/**
	 * @return the cnfor
	 */
	public String getCnfor() {
		return cnfor;
	}

	/**
	 * @param cnfor
	 *            the cnfor to set
	 */
	public void setCnfor(String cnfor) {
		this.cnfor = cnfor;
	}

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
	public String getChqDate() {
		return chqDate;
	}

	/**
	 * @param chqDate
	 *            the chqDate to set
	 */
	public void setChqDate(String chqDate) {
		this.chqDate = chqDate;
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

	@Override
	public int compareTo(CNCollectionDtlsTO arg0) {
		int result = 0;
		if (!StringUtil.isEmptyInteger(entryId)
				&& !StringUtil.isEmptyInteger(arg0.getEntryId())) {
			result = entryId.compareTo(arg0.entryId);
		}
		return result;
	}

}
