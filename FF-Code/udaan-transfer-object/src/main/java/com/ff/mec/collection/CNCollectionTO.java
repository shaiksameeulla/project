/**
 * 
 */
package com.ff.mec.collection;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class CNCollectionTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private String cnCollectionDate;
	private Integer originOfficeId;
	private List<CNCollectionDtlsTO> cnCollectionDtls;
	private List<LabelValueBean> bankNameList;
	private List<LabelValueBean> cnForList;
	private List<LabelValueBean> collectionModeList;
	private String originOfficeCode;
	private String status;
	private String isSaved;

	private String currentDate;
	private String nextDate;

	private Date currentDt;
	private Date nextDt;

	/** For UI */
	private int rowCount;
	private String[] checkboxes = new String[rowCount];
	private String[] isChecked = new String[rowCount];// Yes or No
	private String[] txnNo = new String[rowCount];
	private String[] receiptNo = new String[rowCount];
	private String[] cnNo = new String[rowCount];
	private String[] collectionType = new String[rowCount];
	private Integer[] collectionTypeId = new Integer[rowCount];
	private String[] collectionTypeList = new String[rowCount];
	private Double[] amount = new Double[rowCount];
	private Double[] rcvdAmt = new Double[rowCount];
	private Double[] tdsAmt = new Double[rowCount];
	private Double[] totals = new Double[rowCount];
	private String[] mode = new String[rowCount];
	private String[] cnfor = new String[rowCount];
	private String[] chqNo = new String[rowCount];
	private String[] chqDate = new String[rowCount];
	private String[] bankName = new String[rowCount];
	private Integer[] bankId = new Integer[rowCount];
	private String[] trnsstatus = new String[rowCount];
	private Integer[] collectionEntryId = new Integer[rowCount];
	private Integer[] consgIds = new Integer[rowCount];
	private Integer[] collectionID = new Integer[rowCount];
	private Integer[] reasonIds = new Integer[rowCount];
	private String[] cnDeliveryDt = new String[rowCount];
	private Integer[] bankGLs = new Integer[rowCount];

	private Integer createdBy;
	private Integer updatedBy;
	private Integer bankGL;

	/**
	 * @return the isChecked
	 */
	public String[] getIsChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked
	 *            the isChecked to set
	 */
	public void setIsChecked(String[] isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the checkboxes
	 */
	public String[] getCheckboxes() {
		return checkboxes;
	}

	/**
	 * @param checkboxes
	 *            the checkboxes to set
	 */
	public void setCheckboxes(String[] checkboxes) {
		this.checkboxes = checkboxes;
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
	 * @return the bankGLs
	 */
	public Integer[] getBankGLs() {
		return bankGLs;
	}

	/**
	 * @param bankGLs
	 *            the bankGLs to set
	 */
	public void setBankGLs(Integer[] bankGLs) {
		this.bankGLs = bankGLs;
	}

	/**
	 * @return the cnDeliveryDt
	 */
	public String[] getCnDeliveryDt() {
		return cnDeliveryDt;
	}

	/**
	 * @param cnDeliveryDt
	 *            the cnDeliveryDt to set
	 */
	public void setCnDeliveryDt(String[] cnDeliveryDt) {
		this.cnDeliveryDt = cnDeliveryDt;
	}

	/**
	 * @return the totals
	 */
	public Double[] getTotals() {
		return totals;
	}

	/**
	 * @param totals
	 *            the totals to set
	 */
	public void setTotals(Double[] totals) {
		this.totals = totals;
	}

	/**
	 * @return the currentDt
	 */
	public Date getCurrentDt() {
		return currentDt;
	}

	/**
	 * @param currentDt
	 *            the currentDt to set
	 */
	public void setCurrentDt(Date currentDt) {
		this.currentDt = currentDt;
	}

	/**
	 * @return the nextDt
	 */
	public Date getNextDt() {
		return nextDt;
	}

	/**
	 * @param nextDt
	 *            the nextDt to set
	 */
	public void setNextDt(Date nextDt) {
		this.nextDt = nextDt;
	}

	/**
	 * @return the currentDate
	 */
	public String getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate
	 *            the currentDate to set
	 */
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the nextDate
	 */
	public String getNextDate() {
		return nextDate;
	}

	/**
	 * @param nextDate
	 *            the nextDate to set
	 */
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}

	/**
	 * @return the reasonIds
	 */
	public Integer[] getReasonIds() {
		return reasonIds;
	}

	/**
	 * @param reasonIds
	 *            the reasonIds to set
	 */
	public void setReasonIds(Integer[] reasonIds) {
		this.reasonIds = reasonIds;
	}

	/**
	 * @return the consgIds
	 */
	public Integer[] getConsgIds() {
		return consgIds;
	}

	/**
	 * @param consgIds
	 *            the consgIds to set
	 */
	public void setConsgIds(Integer[] consgIds) {
		this.consgIds = consgIds;
	}

	/**
	 * @return the collectionEntryId
	 */
	public Integer[] getCollectionEntryId() {
		return collectionEntryId;
	}

	/**
	 * @param collectionEntryId
	 *            the collectionEntryId to set
	 */
	public void setCollectionEntryId(Integer[] collectionEntryId) {
		this.collectionEntryId = collectionEntryId;
	}

	/**
	 * @return the isSaved
	 */
	public String getIsSaved() {
		return isSaved;
	}

	/**
	 * @param isSaved
	 *            the isSaved to set
	 */
	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}

	/**
	 * @return the originOfficeId
	 */
	public Integer getOriginOfficeId() {
		return originOfficeId;
	}

	/**
	 * @param originOfficeId
	 *            the originOfficeId to set
	 */
	public void setOriginOfficeId(Integer originOfficeId) {
		this.originOfficeId = originOfficeId;
	}

	/**
	 * @return the collectionModeList
	 */
	public List<LabelValueBean> getCollectionModeList() {
		return collectionModeList;
	}

	/**
	 * @param collectionModeList
	 *            the collectionModeList to set
	 */
	public void setCollectionModeList(List<LabelValueBean> collectionModeList) {
		this.collectionModeList = collectionModeList;
	}

	/**
	 * @return the cnForList
	 */
	public List<LabelValueBean> getCnForList() {
		return cnForList;
	}

	/**
	 * @param cnForList
	 *            the cnForList to set
	 */
	public void setCnForList(List<LabelValueBean> cnForList) {
		this.cnForList = cnForList;
	}

	/**
	 * @return the cnCollectionDate
	 */
	public String getCnCollectionDate() {
		return cnCollectionDate;
	}

	/**
	 * @param cnCollectionDate
	 *            the cnCollectionDate to set
	 */
	public void setCnCollectionDate(String cnCollectionDate) {
		this.cnCollectionDate = cnCollectionDate;
	}

	/**
	 * @return the cnCollectionDtls
	 */
	public List<CNCollectionDtlsTO> getCnCollectionDtls() {
		return cnCollectionDtls;
	}

	/**
	 * @param cnCollectionDtls
	 *            the cnCollectionDtls to List
	 */
	public void setCnCollectionDtls(List<CNCollectionDtlsTO> cnCollectionDtls) {
		this.cnCollectionDtls = cnCollectionDtls;
	}

	/**
	 * @return the bankNameList
	 */
	public List<LabelValueBean> getBankNameList() {
		return bankNameList;
	}

	/**
	 * @param bankNameList
	 *            the bankNameList to set
	 */
	public void setBankNameList(List<LabelValueBean> bankNameList) {
		this.bankNameList = bankNameList;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the txnNo
	 */
	public String[] getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(String[] txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * @return the receiptNo
	 */
	public String[] getReceiptNo() {
		return receiptNo;
	}

	/**
	 * @param receiptNo
	 *            the receiptNo to set
	 */
	public void setReceiptNo(String[] receiptNo) {
		this.receiptNo = receiptNo;
	}

	/**
	 * @return the cnNo
	 */
	public String[] getCnNo() {
		return cnNo;
	}

	/**
	 * @param cnNo
	 *            the cnNo to set
	 */
	public void setCnNo(String[] cnNo) {
		this.cnNo = cnNo;
	}

	/**
	 * @return the collectionType
	 */
	public String[] getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType
	 *            the collectionType to set
	 */
	public void setCollectionType(String[] collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the collectionTypeId
	 */
	public Integer[] getCollectionTypeId() {
		return collectionTypeId;
	}

	/**
	 * @param collectionTypeId
	 *            the collectionTypeId to set
	 */
	public void setCollectionTypeId(Integer[] collectionTypeId) {
		this.collectionTypeId = collectionTypeId;
	}

	/**
	 * @return the collectionTypeList
	 */
	public String[] getCollectionTypeList() {
		return collectionTypeList;
	}

	/**
	 * @param collectionTypeList
	 *            the collectionTypeList to set
	 */
	public void setCollectionTypeList(String[] collectionTypeList) {
		this.collectionTypeList = collectionTypeList;
	}

	/**
	 * @return the amount
	 */
	public Double[] getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double[] amount) {
		this.amount = amount;
	}

	/**
	 * @return the rcvdAmt
	 */
	public Double[] getRcvdAmt() {
		return rcvdAmt;
	}

	/**
	 * @param rcvdAmt
	 *            the rcvdAmt to set
	 */
	public void setRcvdAmt(Double[] rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	/**
	 * @return the tdsAmt
	 */
	public Double[] getTdsAmt() {
		return tdsAmt;
	}

	/**
	 * @param tdsAmt
	 *            the tdsAmt to set
	 */
	public void setTdsAmt(Double[] tdsAmt) {
		this.tdsAmt = tdsAmt;
	}

	/**
	 * @return the mode
	 */
	public String[] getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String[] mode) {
		this.mode = mode;
	}

	/**
	 * @return the cnfor
	 */
	public String[] getCnfor() {
		return cnfor;
	}

	/**
	 * @param cnfor
	 *            the cnfor to set
	 */
	public void setCnfor(String[] cnfor) {
		this.cnfor = cnfor;
	}

	/**
	 * @return the chqNo
	 */
	public String[] getChqNo() {
		return chqNo;
	}

	/**
	 * @param chqNo
	 *            the chqNo to set
	 */
	public void setChqNo(String[] chqNo) {
		this.chqNo = chqNo;
	}

	/**
	 * @return the chqDate
	 */
	public String[] getChqDate() {
		return chqDate;
	}

	/**
	 * @param chqDate
	 *            the chqDate to set
	 */
	public void setChqDate(String[] chqDate) {
		this.chqDate = chqDate;
	}

	/**
	 * @return the bankName
	 */
	public String[] getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String[] bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankId
	 */
	public Integer[] getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(Integer[] bankId) {
		this.bankId = bankId;
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
	 * @return the trnsstatus
	 */
	public String[] getTrnsstatus() {
		return trnsstatus;
	}

	/**
	 * @param trnsstatus
	 *            the trnsstatus to set
	 */
	public void setTrnsstatus(String[] trnsstatus) {
		this.trnsstatus = trnsstatus;
	}

	/**
	 * @return the originOfficeCode
	 */
	public String getOriginOfficeCode() {
		return originOfficeCode;
	}

	/**
	 * @param originOfficeCode
	 *            the originOfficeCode to set
	 */
	public void setOriginOfficeCode(String originOfficeCode) {
		this.originOfficeCode = originOfficeCode;
	}

	/**
	 * @return the collectionID
	 */
	public Integer[] getCollectionID() {
		return collectionID;
	}

	/**
	 * @param collectionID
	 *            the collectionID to set
	 */
	public void setCollectionID(Integer[] collectionID) {
		this.collectionID = collectionID;
	}

}
