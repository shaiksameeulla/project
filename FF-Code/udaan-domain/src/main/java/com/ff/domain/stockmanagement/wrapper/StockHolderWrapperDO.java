/**
 * 
 */
package com.ff.domain.stockmanagement.wrapper;

import java.util.Date;

import com.capgemini.lbs.framework.utils.StringUtil;

/**
 * @author mohammes
 *
 */
public class StockHolderWrapperDO implements Comparable<StockHolderWrapperDO> {

	/**
	 * @param stockNumber
	 * @param stockOfficeId
	 * @param stockEmployeeId
	 * @param stockFranchiseeId
	 * @param stockCustomerId
	 * @param stockBaId
	 * @param stockDate
	 */
	public StockHolderWrapperDO(String stockNumber, Integer stockOfficeId,
			Integer stockEmployeeId, Integer stockFranchiseeId,
			Integer stockCustomerId, Integer stockBaId, Date stockDate) {
		this.stockNumber = stockNumber;
		this.stockOfficeId = stockOfficeId;
		this.stockEmployeeId = stockEmployeeId;
		this.stockFranchiseeId = stockFranchiseeId;
		this.stockCustomerId = stockCustomerId;
		this.stockBaId = stockBaId;
		this.stockDate = stockDate;
	}
	public StockHolderWrapperDO(Integer stockOfficeId,
			Integer stockEmployeeId, Integer stockFranchiseeId,
			Integer stockCustomerId, Integer stockBaId, Date stockDate) {
		this.stockOfficeId = stockOfficeId;
		this.stockEmployeeId = stockEmployeeId;
		this.stockFranchiseeId = stockFranchiseeId;
		this.stockCustomerId = stockCustomerId;
		this.stockBaId = stockBaId;
		this.stockDate = stockDate;
	}
	public StockHolderWrapperDO(Integer stockOfficeId,
			Integer stockEmployeeId,Integer stockCustomerId, Integer stockBaId, Date stockDate) {
		this.stockOfficeId = stockOfficeId;
		this.stockEmployeeId = stockEmployeeId;
		this.stockCustomerId = stockCustomerId;
		this.stockBaId = stockBaId;
		this.stockDate = stockDate;
	}
	public StockHolderWrapperDO(Integer stockOfficeId,
			Date stockDate) {
		this.stockOfficeId = stockOfficeId;
		this.stockDate = stockDate;
	}
	public StockHolderWrapperDO() {
	}

	private String stockNumber;
	private Integer stockOfficeId;
	private Integer stockEmployeeId;
	private Integer stockFranchiseeId;
	private Integer stockCustomerId;
	private Integer stockBaId;
	private Date stockDate;
	private Integer stockDtlsId;
	private String processName;
	/**
	 * @return the stockNumber
	 */
	public String getStockNumber() {
		return stockNumber;
	}
	/**
	 * @return the stockOfficeId
	 */
	public Integer getStockOfficeId() {
		return stockOfficeId;
	}
	/**
	 * @return the stockEmployeeId
	 */
	public Integer getStockEmployeeId() {
		return stockEmployeeId;
	}
	/**
	 * @return the stockFranchiseeId
	 */
	public Integer getStockFranchiseeId() {
		return stockFranchiseeId;
	}
	/**
	 * @return the stockCustomerId
	 */
	public Integer getStockCustomerId() {
		return stockCustomerId;
	}
	/**
	 * @return the stockBaId
	 */
	public Integer getStockBaId() {
		return stockBaId;
	}
	/**
	 * @return the stockDate
	 */
	public Date getStockDate() {
		return stockDate;
	}
	/**
	 * @param stockNumber the stockNumber to set
	 */
	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}
	/**
	 * @param stockOfficeId the stockOfficeId to set
	 */
	public void setStockOfficeId(Integer stockOfficeId) {
		this.stockOfficeId = stockOfficeId;
	}
	/**
	 * @param stockEmployeeId the stockEmployeeId to set
	 */
	public void setStockEmployeeId(Integer stockEmployeeId) {
		this.stockEmployeeId = stockEmployeeId;
	}
	/**
	 * @param stockFranchiseeId the stockFranchiseeId to set
	 */
	public void setStockFranchiseeId(Integer stockFranchiseeId) {
		this.stockFranchiseeId = stockFranchiseeId;
	}
	/**
	 * @param stockCustomerId the stockCustomerId to set
	 */
	public void setStockCustomerId(Integer stockCustomerId) {
		this.stockCustomerId = stockCustomerId;
	}
	/**
	 * @param stockBaId the stockBaId to set
	 */
	public void setStockBaId(Integer stockBaId) {
		this.stockBaId = stockBaId;
	}
	/**
	 * @param stockDate the stockDate to set
	 */
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	
	public int compareTo(StockHolderWrapperDO arg0) {
		int result=0;
		 if(!StringUtil.isNull(stockDate) && !StringUtil.isNull(arg0.getStockDate())) {
			result = this.stockDate.compareTo(arg0.getStockDate());
		}
		return result;
	}
	/**
	 * @return the stockDtlsId
	 */
	public Integer getStockDtlsId() {
		return stockDtlsId;
	}
	/**
	 * @param stockDtlsId the stockDtlsId to set
	 */
	public void setStockDtlsId(Integer stockDtlsId) {
		this.stockDtlsId = stockDtlsId;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
}
