package com.ff.to.mec.expense;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class ValidateExpenseTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;
	
	/** The expense id. (primary key) */
	private Long expenseId;//hidden
	
	/** The From date. */
	private String fromDate;
	
	/** The To date. */
	private String toDate;
	
	/** The station Id. */
	private Integer stationId;
	
	/** The stations List. */
	private List<LabelValueBean> stationList;
	
	/** The office Id. */
	private Integer officeId;
	
	/** The offices List. */
	private List<LabelValueBean> officeList;
	
	/** The status. */
	private String status;
	
	/** The status List. */
	private List<LabelValueBean> statusList;
	
	/** The tx. No. */
	private String txNumber;
	
	/** The expenses List. */
	private List<ValidateExpenseDetailTO> validateExpDtlsTOs;
	
	/* Common hidden fields. */
	
	/** The loggedIn Office id*/
	private Integer loginOfficeId;/* hidden */
	
	/** The loggedIn Office Code*/
	private String loginOfficeCode;/* hidden */
	
	/** The loggedIn Office region id*/
	private Integer regionId;/* hidden */
	
	/** The expense created by user id*/
	private Integer createdBy;/* hidden */
	
	/** The expense updated by user id*/
	private Integer updatedBy;/* hidden */
	
	/* Row grid details */
	
	int rowCount;
	
	/** The rowExpenseId. */
	private Long rowExpenseId[] = new Long[rowCount];
	
	/** The rowTxDate. */
	private String rowTxDate[] = new String[rowCount];
	
	/** The rowTxNumber. */
	private String rowTxNumber[] = new String[rowCount];;
	
	/** The rowExpenseForId. */
	private Integer rowExpenseForId[] = new Integer[rowCount];//hidden
	
	/** The rowExpenseFor. */
	private String rowExpenseFor[] = new String[rowCount];
	
	/** The rowExpenseTypeId. */
	private Integer rowExpenseTypeId[] = new Integer[rowCount];//hidden
	
	/** The rowExpenseType. */
	private String rowExpenseType[] = new String[rowCount];
	
	/** The rowAmount. */
	private Double rowAmount[] = new Double[rowCount];
	
	/** The rowExpenseStatus. i.e S/V */
	private String rowExpenseStatus[] = new String[rowCount];//hidden 
	
	/** The rowStatus. Description: SUBMITTED/VALIDATED */
	private String rowStatus[] = new String[rowCount];//
	
	/** The rowExpenseOfficeId. */
	private Integer rowExpenseOfficeId[] = new Integer[rowCount];
	
	/** The rowExpenseRegionId. */
	private Integer rowExpenseRegionId[] = new Integer[rowCount];
	
	/** The fromDt. */
	private Date fromDt;
	
	/** The toDt. */
	private Date toDt;
	
	/** The new to Dt. i.e. new To Dt. */
	private Date newToDt;
	
		
	/**
	 * @return the newToDt
	 */
	public Date getNewToDt() {
		return newToDt;
	}
	/**
	 * @param newToDt the newToDt to set
	 */
	public void setNewToDt(Date newToDt) {
		this.newToDt = newToDt;
	}
	/**
	 * @return the rowExpenseRegionId
	 */
	public Integer[] getRowExpenseRegionId() {
		return rowExpenseRegionId;
	}
	/**
	 * @param rowExpenseRegionId the rowExpenseRegionId to set
	 */
	public void setRowExpenseRegionId(Integer[] rowExpenseRegionId) {
		this.rowExpenseRegionId = rowExpenseRegionId;
	}
	/**
	 * @return the rowExpenseOfficeId
	 */
	public Integer[] getRowExpenseOfficeId() {
		return rowExpenseOfficeId;
	}
	/**
	 * @param rowExpenseOfficeId the rowExpenseOfficeId to set
	 */
	public void setRowExpenseOfficeId(Integer[] rowExpenseOfficeId) {
		this.rowExpenseOfficeId = rowExpenseOfficeId;
	}
	/**
	 * @return the fromDt
	 */
	public Date getFromDt() {
		return fromDt;
	}
	/**
	 * @param fromDt the fromDt to set
	 */
	public void setFromDt(Date fromDt) {
		this.fromDt = fromDt;
	}
	/**
	 * @return the toDt
	 */
	public Date getToDt() {
		return toDt;
	}
	/**
	 * @param toDt the toDt to set
	 */
	public void setToDt(Date toDt) {
		this.toDt = toDt;
	}
	/**
	 * @return the validateExpDtlsTOs
	 */
	public List<ValidateExpenseDetailTO> getValidateExpDtlsTOs() {
		return validateExpDtlsTOs;
	}
	/**
	 * @param validateExpDtlsTOs the validateExpDtlsTOs to set
	 */
	public void setValidateExpDtlsTOs(
			List<ValidateExpenseDetailTO> validateExpDtlsTOs) {
		this.validateExpDtlsTOs = validateExpDtlsTOs;
	}
	/**
	 * @return the rowExpenseId
	 */
	public Long[] getRowExpenseId() {
		return rowExpenseId;
	}
	/**
	 * @param rowExpenseId the rowExpenseId to set
	 */
	public void setRowExpenseId(Long[] rowExpenseId) {
		this.rowExpenseId = rowExpenseId;
	}
	/**
	 * @return the rowTxDate
	 */
	public String[] getRowTxDate() {
		return rowTxDate;
	}
	/**
	 * @param rowTxDate the rowTxDate to set
	 */
	public void setRowTxDate(String[] rowTxDate) {
		this.rowTxDate = rowTxDate;
	}
	/**
	 * @return the rowTxNumber
	 */
	public String[] getRowTxNumber() {
		return rowTxNumber;
	}
	/**
	 * @param rowTxNumber the rowTxNumber to set
	 */
	public void setRowTxNumber(String[] rowTxNumber) {
		this.rowTxNumber = rowTxNumber;
	}
	/**
	 * @return the rowExpenseForId
	 */
	public Integer[] getRowExpenseForId() {
		return rowExpenseForId;
	}
	/**
	 * @param rowExpenseForId the rowExpenseForId to set
	 */
	public void setRowExpenseForId(Integer[] rowExpenseForId) {
		this.rowExpenseForId = rowExpenseForId;
	}
	/**
	 * @return the rowExpenseFor
	 */
	public String[] getRowExpenseFor() {
		return rowExpenseFor;
	}
	/**
	 * @param rowExpenseFor the rowExpenseFor to set
	 */
	public void setRowExpenseFor(String[] rowExpenseFor) {
		this.rowExpenseFor = rowExpenseFor;
	}
	/**
	 * @return the rowExpenseTypeId
	 */
	public Integer[] getRowExpenseTypeId() {
		return rowExpenseTypeId;
	}
	/**
	 * @param rowExpenseTypeId the rowExpenseTypeId to set
	 */
	public void setRowExpenseTypeId(Integer[] rowExpenseTypeId) {
		this.rowExpenseTypeId = rowExpenseTypeId;
	}
	/**
	 * @return the rowExpenseType
	 */
	public String[] getRowExpenseType() {
		return rowExpenseType;
	}
	/**
	 * @param rowExpenseType the rowExpenseType to set
	 */
	public void setRowExpenseType(String[] rowExpenseType) {
		this.rowExpenseType = rowExpenseType;
	}
	/**
	 * @return the rowAmount
	 */
	public Double[] getRowAmount() {
		return rowAmount;
	}
	/**
	 * @param rowAmount the rowAmount to set
	 */
	public void setRowAmount(Double[] rowAmount) {
		this.rowAmount = rowAmount;
	}
	/**
	 * @return the rowExpenseStatus
	 */
	public String[] getRowExpenseStatus() {
		return rowExpenseStatus;
	}
	/**
	 * @param rowExpenseStatus the rowExpenseStatus to set
	 */
	public void setRowExpenseStatus(String[] rowExpenseStatus) {
		this.rowExpenseStatus = rowExpenseStatus;
	}
	/**
	 * @return the rowStatus
	 */
	public String[] getRowStatus() {
		return rowStatus;
	}
	/**
	 * @param rowStatus the rowStatus to set
	 */
	public void setRowStatus(String[] rowStatus) {
		this.rowStatus = rowStatus;
	}
	/**
	 * @return the loginOfficeId
	 */
	public Integer getLoginOfficeId() {
		return loginOfficeId;
	}
	/**
	 * @param loginOfficeId the loginOfficeId to set
	 */
	public void setLoginOfficeId(Integer loginOfficeId) {
		this.loginOfficeId = loginOfficeId;
	}
	/**
	 * @return the loginOfficeCode
	 */
	public String getLoginOfficeCode() {
		return loginOfficeCode;
	}
	/**
	 * @param loginOfficeCode the loginOfficeCode to set
	 */
	public void setLoginOfficeCode(String loginOfficeCode) {
		this.loginOfficeCode = loginOfficeCode;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
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
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the stationId
	 */
	public Integer getStationId() {
		return stationId;
	}
	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	/**
	 * @return the stationList
	 */
	public List<LabelValueBean> getStationList() {
		return stationList;
	}
	/**
	 * @param stationList the stationList to set
	 */
	public void setStationList(List<LabelValueBean> stationList) {
		this.stationList = stationList;
	}
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return the officeList
	 */
	public List<LabelValueBean> getOfficeList() {
		return officeList;
	}
	/**
	 * @param officeList the officeList to set
	 */
	public void setOfficeList(List<LabelValueBean> officeList) {
		this.officeList = officeList;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the statusList
	 */
	public List<LabelValueBean> getStatusList() {
		return statusList;
	}
	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<LabelValueBean> statusList) {
		this.statusList = statusList;
	}
	/**
	 * @return the txNumber
	 */
	public String getTxNumber() {
		return txNumber;
	}
	/**
	 * @param txNumber the txNumber to set
	 */
	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}
	/**
	 * @return the expenseId
	 */
	public Long getExpenseId() {
		return expenseId;
	}
	/**
	 * @param expenseId the expenseId to set
	 */
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	
}
