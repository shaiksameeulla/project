package com.ff.mec.collection;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author prmeher
 * 
 */
public class BillCollectionTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private String txnNo;
	private Integer collectionID;
	private Integer prevTxnCollectionID;
	private String collectionDate;
	private String custName;
	private List<LabelValueBean> custNameList;
	private Integer custId;
	private String custCode;
	private Integer collectionModeId;
	private List<LabelValueBean> collectionModeList;
	private String chqNo;
	private String chqDate;
	private Integer bankId;
	private List<LabelValueBean> bankNameList;
	private String amount;
	private String status;
	private List<BillCollectionDetailTO> billCollectionDetailTO;
	private String originOfficeCode;
	private String isSaved;
	private String tranStatus;
	private Integer originOfficeId;
	private Double corrAmount;
	private String collectionType;
	private String isCorrection;// "Y" or "N"
	private String isOldRecord; // "Y" or "N"
	private String bankName;
	private String isCreationScreen; // "Y" or "N"
	
	private Integer bankGL;
	private List<LabelValueBean> bankGLList;

	// for UI Specific
	private int rowCount;
	private Integer[] collectionEntryIds = new Integer[rowCount];
	private Integer[] srNos = new Integer[rowCount];
	private String[] collectionAgainsts = new String[rowCount];
	private String[] billNos = new String[rowCount];
	private Double[] billAmounts = new Double[rowCount];;
	private Double[] receivedAmounts = new Double[rowCount];
	private Double[] tdsAmounts = new Double[rowCount];
	private Double[] deductions = new Double[rowCount];
	private Double[] totals = new Double[rowCount];
	private String[] remarks = new String[rowCount];
	private Double[] correctedRecvAmount = new Double[rowCount];
	private Double[] correctedTDS = new Double[rowCount];
	private Integer[] reasonIds = new Integer[rowCount];
	private List<LabelValueBean> reasonList;

	private Integer createdBy;
	private Integer updatedBy;

	private String transMsg;

	private String[] receiptNo = new String[rowCount];
	private Integer[] consgIds = new Integer[rowCount];
	private String[] cnNo = new String[rowCount];
	private String[] collectionTypes = new String[rowCount];
	private String[] cnfor = new String[rowCount];
	private String[] cnDeliveryDt = new String[rowCount];
	private Integer[] createdBys = new Integer[rowCount];

	
	/**
	 * @return the isCreationScreen
	 */
	public String getIsCreationScreen() {
		return isCreationScreen;
	}

	/**
	 * @param isCreationScreen the isCreationScreen to set
	 */
	public void setIsCreationScreen(String isCreationScreen) {
		this.isCreationScreen = isCreationScreen;
	}

	/**
	 * @return the createdBys
	 */
	public Integer[] getCreatedBys() {
		return createdBys;
	}

	/**
	 * @param createdBys the createdBys to set
	 */
	public void setCreatedBys(Integer[] createdBys) {
		this.createdBys = createdBys;
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
	 * @return the collectionTypes
	 */
	public String[] getCollectionTypes() {
		return collectionTypes;
	}

	/**
	 * @param collectionTypes
	 *            the collectionTypes to set
	 */
	public void setCollectionTypes(String[] collectionTypes) {
		this.collectionTypes = collectionTypes;
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
	 * @return the prevTxnCollectionID
	 */
	public Integer getPrevTxnCollectionID() {
		return prevTxnCollectionID;
	}

	/**
	 * @param prevTxnCollectionID
	 *            the prevTxnCollectionID to set
	 */
	public void setPrevTxnCollectionID(Integer prevTxnCollectionID) {
		this.prevTxnCollectionID = prevTxnCollectionID;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the billCollectionDetailTO
	 */
	public List<BillCollectionDetailTO> getBillCollectionDetailTO() {
		return billCollectionDetailTO;
	}

	/**
	 * @param billCollectionDetailTO
	 *            the billCollectionDetailTO to set
	 */
	public void setBillCollectionDetailTO(
			List<BillCollectionDetailTO> billCollectionDetailTO) {
		this.billCollectionDetailTO = billCollectionDetailTO;
	}

	/**
	 * @return the transMsg
	 */
	public String getTransMsg() {
		return transMsg;
	}

	/**
	 * @param transMsg
	 *            the transMsg to set
	 */
	public void setTransMsg(String transMsg) {
		this.transMsg = transMsg;
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
	 * @return the bankGLList
	 */
	public List<LabelValueBean> getBankGLList() {
		return bankGLList;
	}

	/**
	 * @param bankGLList
	 *            the bankGLList to set
	 */
	public void setBankGLList(List<LabelValueBean> bankGLList) {
		this.bankGLList = bankGLList;
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
	 * @return the reasonList
	 */
	public List<LabelValueBean> getReasonList() {
		return reasonList;
	}

	/**
	 * @param reasonList
	 *            the reasonList to set
	 */
	public void setReasonList(List<LabelValueBean> reasonList) {
		this.reasonList = reasonList;
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
	 * @return the isOldRecord
	 */
	public String getIsOldRecord() {
		return isOldRecord;
	}

	/**
	 * @param isOldRecord
	 *            the isOldRecord to set
	 */
	public void setIsOldRecord(String isOldRecord) {
		this.isOldRecord = isOldRecord;
	}

	/**
	 * @return the isCorrection
	 */
	public String getIsCorrection() {
		return isCorrection;
	}

	/**
	 * @param isCorrection
	 *            the isCorrection to set
	 */
	public void setIsCorrection(String isCorrection) {
		this.isCorrection = isCorrection;
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
	 * @return the correctedRecvAmount
	 */
	public Double[] getCorrectedRecvAmount() {
		return correctedRecvAmount;
	}

	/**
	 * @param correctedRecvAmount
	 *            the correctedRecvAmount to set
	 */
	public void setCorrectedRecvAmount(Double[] correctedRecvAmount) {
		this.correctedRecvAmount = correctedRecvAmount;
	}

	/**
	 * @return the correctedTDS
	 */
	public Double[] getCorrectedTDS() {
		return correctedTDS;
	}

	/**
	 * @param correctedTDS
	 *            the correctedTDS to set
	 */
	public void setCorrectedTDS(Double[] correctedTDS) {
		this.correctedTDS = correctedTDS;
	}

	/**
	 * @return the corrAmount
	 */
	public Double getCorrAmount() {
		return corrAmount;
	}

	/**
	 * @param corrAmount
	 *            the corrAmount to set
	 */
	public void setCorrAmount(Double corrAmount) {
		this.corrAmount = corrAmount;
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

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}

	public String getOriginOfficeCode() {
		return originOfficeCode;
	}

	public void setOriginOfficeCode(String originOfficeCode) {
		this.originOfficeCode = originOfficeCode;
	}

	/**
	 * @return the collectionEntryIds
	 */
	public Integer[] getCollectionEntryIds() {
		return collectionEntryIds;
	}

	/**
	 * @param collectionEntryIds
	 *            the collectionEntryIds to set
	 */
	public void setCollectionEntryIds(Integer[] collectionEntryIds) {
		this.collectionEntryIds = collectionEntryIds;
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
	 * @return the collectionModeId
	 */
	public Integer getCollectionModeId() {
		return collectionModeId;
	}

	/**
	 * @param collectionModeId
	 *            the collectionModeId to set
	 */
	public void setCollectionModeId(Integer collectionModeId) {
		this.collectionModeId = collectionModeId;
	}

	/**
	 * @return the bankId
	 */
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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
	 * @return the collectionDate
	 */
	public String getCollectionDate() {
		return collectionDate;
	}

	/**
	 * @param collectionDate
	 *            the collectionDate to set
	 */
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the custNameList
	 */
	public List<LabelValueBean> getCustNameList() {
		return custNameList;
	}

	/**
	 * @param custNameList
	 *            the custNameList to set
	 */
	public void setCustNameList(List<LabelValueBean> custNameList) {
		this.custNameList = custNameList;
	}

	/**
	 * @return the custId
	 */
	public Integer getCustId() {
		return custId;
	}

	/**
	 * @param custId
	 *            the custId to set
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	/**
	 * @return the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * @param custCode
	 *            the custCode to set
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	 * @return the srNos
	 */
	public Integer[] getSrNos() {
		return srNos;
	}

	/**
	 * @param srNos
	 *            the srNos to set
	 */
	public void setSrNos(Integer[] srNos) {
		this.srNos = srNos;
	}

	/**
	 * @return the collectionAgainsts
	 */
	public String[] getCollectionAgainsts() {
		return collectionAgainsts;
	}

	/**
	 * @param collectionAgainsts
	 *            the collectionAgainsts to set
	 */
	public void setCollectionAgainsts(String[] collectionAgainsts) {
		this.collectionAgainsts = collectionAgainsts;
	}

	/**
	 * @return the billNos
	 */
	public String[] getBillNos() {
		return billNos;
	}

	/**
	 * @param billNos
	 *            the billNos to set
	 */
	public void setBillNos(String[] billNos) {
		this.billNos = billNos;
	}

	/**
	 * @return the billAmounts
	 */
	public Double[] getBillAmounts() {
		return billAmounts;
	}

	/**
	 * @param billAmounts
	 *            the billAmounts to set
	 */
	public void setBillAmounts(Double[] billAmounts) {
		this.billAmounts = billAmounts;
	}

	/**
	 * @return the receivedAmounts
	 */
	public Double[] getReceivedAmounts() {
		return receivedAmounts;
	}

	/**
	 * @param receivedAmounts
	 *            the receivedAmounts to set
	 */
	public void setReceivedAmounts(Double[] receivedAmounts) {
		this.receivedAmounts = receivedAmounts;
	}

	/**
	 * @return the tdsAmounts
	 */
	public Double[] getTdsAmounts() {
		return tdsAmounts;
	}

	/**
	 * @param tdsAmounts
	 *            the tdsAmounts to set
	 */
	public void setTdsAmounts(Double[] tdsAmounts) {
		this.tdsAmounts = tdsAmounts;
	}

	/**
	 * @return the deductions
	 */
	public Double[] getDeductions() {
		return deductions;
	}

	/**
	 * @param deductions
	 *            the deductions to set
	 */
	public void setDeductions(Double[] deductions) {
		this.deductions = deductions;
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
	 * @return the remarks
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
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

}
