/**
 * 
 */
package com.ff.mec.collection;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

/**
 * @author prmeher
 * 
 */
public class BillCollectionDetailTO {

	private Integer srNo;
	private Integer collectionEntryId;
	private String collectionAgainst;
	private List<LabelValueBean> collectionAgainstList;
	private String billNo;
	private List<LabelValueBean> billNoList;
	private Double billAmount;
	private Double recvdAmt;
	private Double tdsAmt;
	private Double deduction;
	private Double total;
	private String Remarks;
	private Double correctedAmount;
	private Double correctedTDS;
	private String receiptNo;
	private String cnNo;
	private String collectionType;
	private Integer reasonId;
	private Double correctedDeduction;

	private Integer createdBy;
	private Integer updatedBy;

	// validation of bill and CN collection - hidden
	private Integer consgId;
	private String cnfor;
	private String cnDeliveryDt;

	
	
	/**
	 * @return the correctedDeduction
	 */
	public Double getCorrectedDeduction() {
		return correctedDeduction;
	}

	/**
	 * @param correctedDeduction
	 *            the correctedDeduction to set
	 */
	public void setCorrectedDeduction(Double correctedDeduction) {
		this.correctedDeduction = correctedDeduction;
	}

	/**
	 * @return the cnDeliveryDt
	 */
	public String getCnDeliveryDt() {
		return cnDeliveryDt;
	}

	/**
	 * @param cnDeliveryDt
	 *            the cnDeliveryDt to set
	 */
	public void setCnDeliveryDt(String cnDeliveryDt) {
		this.cnDeliveryDt = cnDeliveryDt;
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
	 * @return the collectionAgainstList
	 */
	public List<LabelValueBean> getCollectionAgainstList() {
		return collectionAgainstList;
	}

	/**
	 * @param collectionAgainstList
	 *            the collectionAgainstList to set
	 */
	public void setCollectionAgainstList(
			List<LabelValueBean> collectionAgainstList) {
		this.collectionAgainstList = collectionAgainstList;
	}

	/**
	 * @return the billNoList
	 */
	public List<LabelValueBean> getBillNoList() {
		return billNoList;
	}

	/**
	 * @param billNoList
	 *            the billNoList to set
	 */
	public void setBillNoList(List<LabelValueBean> billNoList) {
		this.billNoList = billNoList;
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
	 * @return the correctedAmount
	 */
	public Double getCorrectedAmount() {
		return correctedAmount;
	}

	/**
	 * @param correctedAmount
	 *            the correctedAmount to set
	 */
	public void setCorrectedAmount(Double correctedAmount) {
		this.correctedAmount = correctedAmount;
	}

	/**
	 * @return the correctedTDS
	 */
	public Double getCorrectedTDS() {
		return correctedTDS;
	}

	/**
	 * @param correctedTDS
	 *            the correctedTDS to set
	 */
	public void setCorrectedTDS(Double correctedTDS) {
		this.correctedTDS = correctedTDS;
	}

	/**
	 * @return the collectionEntryId
	 */
	public Integer getCollectionEntryId() {
		return collectionEntryId;
	}

	/**
	 * @param collectionEntryId
	 *            the collectionEntryId to set
	 */
	public void setCollectionEntryId(Integer collectionEntryId) {
		this.collectionEntryId = collectionEntryId;
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
	 * @return the recvdAmt
	 */
	public Double getRecvdAmt() {
		return recvdAmt;
	}

	/**
	 * @param recvdAmt
	 *            the recvdAmt to set
	 */
	public void setRecvdAmt(Double recvdAmt) {
		this.recvdAmt = recvdAmt;
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
	 * @return the remarks
	 */
	public String getRemarks() {
		return Remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

}
