package com.ff.to.ratemanagement.operations.rateconfiguration;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class BAMaterialRateConfigTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;
	
	/** The baMaterialRateId - Primary Key */
	private Long baMaterialRateId;
	
	/** The valid from date - string */
	private String fromDateStr;
	
	/** The valid to date - string */
	private String toDateStr;

	/** 
	 * The valid to date - date  
	 * Use to update previous tariff valid to date 
	 */
	private Date toDate;
	
	/** The loggedInOfficeId */
	private Integer loggedInOfficeId;
	
	/** The createdBy user */
	private Integer createdBy;
	
	/** The updatedBy user */
	private Integer updatedBy;
	
	/** The loggedInDate/Current Date - String */
	private String loggedInDateStr;
	
	/** The isRenew i.e. Y- if renew required, N- if renew not required */
	private String isRenew;
	
	/** The isAlreadyRenewed i.e. Y- if already renew required, N- if already renew not required */
	private String isAlreadyRenewed;
	
	/** The prevBAMtrlRateId - For Previous Contract - Primary Key */
	private Long prevBAMtrlRateId;
	
	/* ********* India **********/
	/** The serviceTax */
	private Double serviceTax;
	
	/** The eduCess */
	private Double eduCess;
	
	/** The higherEduCess */
	private Double higherEduCess;
	
	/* ********* J&K **********/
	/** The stateTax */
	private Double stateTax;
	
	/** The surchargeOnST */
	private Double surchargeOnST;
	
	/* ********* The grid details **********/
	int rowCount;
	private Integer[] itemTypeIds = new Integer[rowCount];
	private Integer[] itemIds = new Integer[rowCount];
	private String[] uoms = new String[rowCount];
	private Double[] amounts = new Double[rowCount];
	private Integer[] rowNumber = new Integer[rowCount];
	
	/** The baMtrlDtlsTOList */
	List<BAMaterialRateDetailsTO> baMtrlDtlsTOList;
	
	
	/**
	 * @return the isAlreadyRenewed
	 */
	public String getIsAlreadyRenewed() {
		return isAlreadyRenewed;
	}
	/**
	 * @param isAlreadyRenewed the isAlreadyRenewed to set
	 */
	public void setIsAlreadyRenewed(String isAlreadyRenewed) {
		this.isAlreadyRenewed = isAlreadyRenewed;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the prevBAMtrlRateId
	 */
	public Long getPrevBAMtrlRateId() {
		return prevBAMtrlRateId;
	}
	/**
	 * @param prevBAMtrlRateId the prevBAMtrlRateId to set
	 */
	public void setPrevBAMtrlRateId(Long prevBAMtrlRateId) {
		this.prevBAMtrlRateId = prevBAMtrlRateId;
	}
	/**
	 * @return the isRenew
	 */
	public String getIsRenew() {
		return isRenew;
	}
	/**
	 * @param isRenew the isRenew to set
	 */
	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	/**
	 * @return the baMaterialRateId
	 */
	public Long getBaMaterialRateId() {
		return baMaterialRateId;
	}
	/**
	 * @param baMaterialRateId the baMaterialRateId to set
	 */
	public void setBaMaterialRateId(Long baMaterialRateId) {
		this.baMaterialRateId = baMaterialRateId;
	}
	/**
	 * @return the baMtrlDtlsTOList
	 */
	public List<BAMaterialRateDetailsTO> getBaMtrlDtlsTOList() {
		return baMtrlDtlsTOList;
	}
	/**
	 * @param baMtrlDtlsTOList the baMtrlDtlsTOList to set
	 */
	public void setBaMtrlDtlsTOList(List<BAMaterialRateDetailsTO> baMtrlDtlsTOList) {
		this.baMtrlDtlsTOList = baMtrlDtlsTOList;
	}
	/**
	 * @return the loggedInDateStr
	 */
	public String getLoggedInDateStr() {
		return loggedInDateStr;
	}
	/**
	 * @param loggedInDateStr the loggedInDateStr to set
	 */
	public void setLoggedInDateStr(String loggedInDateStr) {
		this.loggedInDateStr = loggedInDateStr;
	}
	/**
	 * @return the rowNumber
	 */
	public Integer[] getRowNumber() {
		return rowNumber;
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer[] rowNumber) {
		this.rowNumber = rowNumber;
	}
	/**
	 * @return the toDateStr
	 */
	public String getToDateStr() {
		return toDateStr;
	}
	/**
	 * @param toDateStr the toDateStr to set
	 */
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}
	/**
	 * @return the loggedInOfficeId
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	/**
	 * @param loggedInOfficeId the loggedInOfficeId to set
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
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
	 * @return the itemTypeIds
	 */
	public Integer[] getItemTypeIds() {
		return itemTypeIds;
	}
	/**
	 * @param itemTypeIds the itemTypeIds to set
	 */
	public void setItemTypeIds(Integer[] itemTypeIds) {
		this.itemTypeIds = itemTypeIds;
	}
	/**
	 * @return the itemIds
	 */
	public Integer[] getItemIds() {
		return itemIds;
	}
	/**
	 * @param itemIds the itemIds to set
	 */
	public void setItemIds(Integer[] itemIds) {
		this.itemIds = itemIds;
	}
	/**
	 * @return the uoms
	 */
	public String[] getUoms() {
		return uoms;
	}
	/**
	 * @param uoms the uoms to set
	 */
	public void setUoms(String[] uoms) {
		this.uoms = uoms;
	}
	/**
	 * @return the serviceTax
	 */
	public Double getServiceTax() {
		return serviceTax;
	}
	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	/**
	 * @return the stateTax
	 */
	public Double getStateTax() {
		return stateTax;
	}
	/**
	 * @param stateTax the stateTax to set
	 */
	public void setStateTax(Double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return the surchargeOnST
	 */
	public Double getSurchargeOnST() {
		return surchargeOnST;
	}
	/**
	 * @param surchargeOnST the surchargeOnST to set
	 */
	public void setSurchargeOnST(Double surchargeOnST) {
		this.surchargeOnST = surchargeOnST;
	}
	/**
	 * @return the amounts
	 */
	public Double[] getAmounts() {
		return amounts;
	}
	/**
	 * @param amounts the amounts to set
	 */
	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}
	/**
	 * @return the fromDateStr
	 */
	public String getFromDateStr() {
		return fromDateStr;
	}
	/**
	 * @param fromDateStr the fromDateStr to set
	 */
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}
	/**
	 * @return the eduCess
	 */
	public Double getEduCess() {
		return eduCess;
	}
	/**
	 * @param eduCess the eduCess to set
	 */
	public void setEduCess(Double eduCess) {
		this.eduCess = eduCess;
	}
	/**
	 * @return the higherEduCess
	 */
	public Double getHigherEduCess() {
		return higherEduCess;
	}
	/**
	 * @param higherEduCess the higherEduCess to set
	 */
	public void setHigherEduCess(Double higherEduCess) {
		this.higherEduCess = higherEduCess;
	}

}
