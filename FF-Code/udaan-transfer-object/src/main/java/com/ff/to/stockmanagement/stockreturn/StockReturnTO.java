/**
 * 
 */
package com.ff.to.stockmanagement.stockreturn;

import java.util.List;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author mohammes
 *
 */
public class StockReturnTO extends StockHeaderTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -730870793591219627L;
		
	private Long stockReturnId;
	

	private String issuedDate;
	private Integer returningOfficeId;
	private Integer issuedOfficeId;
	private String returnDate;
	private String returnDateStr;
	private String stockIssueNumber;
	
	
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	private Integer loggedInOfficeId;
	private String loggedInOfficeName;
	
	
	
	
	
	private Long rowStockReturnItemDtlsId[]= new Long[rowCount];
	
	List<StockReturnItemDtlsTO> returnItemDetls;
	
	
	
	
	
	

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
	 * @return the loggedInOfficeName
	 */
	public String getLoggedInOfficeName() {
		return loggedInOfficeName;
	}

	/**
	 * @param loggedInOfficeName the loggedInOfficeName to set
	 */
	public void setLoggedInOfficeName(String loggedInOfficeName) {
		this.loggedInOfficeName = loggedInOfficeName;
	}

	/**
	 * @return the stockReturnId
	 */
	public Long getStockReturnId() {
		return stockReturnId;
	}

	

	/**
	 * @return the issuedDate
	 */
	public String getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return the returningOfficeId
	 */
	public Integer getReturningOfficeId() {
		return returningOfficeId;
	}

	/**
	 * @return the issuedOfficeId
	 */
	public Integer getIssuedOfficeId() {
		return issuedOfficeId;
	}

	/**
	 * @return the returnDate
	 */
	public String getReturnDate() {
		return returnDate;
	}

	/**
	 * @return the returnDateStr
	 */
	public String getReturnDateStr() {
		return returnDateStr;
	}

	/**
	 * @return the stockIssueNumber
	 */
	public String getStockIssueNumber() {
		return stockIssueNumber;
	}

	/**
	 * @return the loggedInUserId
	 */
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	/**
	 * @return the createdByUserId
	 */
	public Integer getCreatedByUserId() {
		return createdByUserId;
	}

	/**
	 * @return the updatedByUserId
	 */
	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}

	/**
	 * @return the rowStockReturnItemDtlsId
	 */
	public Long[] getRowStockReturnItemDtlsId() {
		return rowStockReturnItemDtlsId;
	}

	/**
	 * @return the returnItemDetls
	 */
	public List<StockReturnItemDtlsTO> getReturnItemDetls() {
		return returnItemDetls;
	}

	/**
	 * @param stockReturnId the stockReturnId to set
	 */
	public void setStockReturnId(Long stockReturnId) {
		this.stockReturnId = stockReturnId;
	}

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * @param returningOfficeId the returningOfficeId to set
	 */
	public void setReturningOfficeId(Integer returningOfficeId) {
		this.returningOfficeId = returningOfficeId;
	}

	/**
	 * @param issuedOfficeId the issuedOfficeId to set
	 */
	public void setIssuedOfficeId(Integer issuedOfficeId) {
		this.issuedOfficeId = issuedOfficeId;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * @param returnDateStr the returnDateStr to set
	 */
	public void setReturnDateStr(String returnDateStr) {
		this.returnDateStr = returnDateStr;
	}

	/**
	 * @param stockIssueNumber the stockIssueNumber to set
	 */
	public void setStockIssueNumber(String stockIssueNumber) {
		this.stockIssueNumber = stockIssueNumber;
	}

	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	/**
	 * @param createdByUserId the createdByUserId to set
	 */
	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	/**
	 * @param updatedByUserId the updatedByUserId to set
	 */
	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	/**
	 * @param rowStockReturnItemDtlsId the rowStockReturnItemDtlsId to set
	 */
	public void setRowStockReturnItemDtlsId(Long[] rowStockReturnItemDtlsId) {
		this.rowStockReturnItemDtlsId = rowStockReturnItemDtlsId;
	}

	/**
	 * @param returnItemDetls the returnItemDetls to set
	 */
	public void setReturnItemDetls(List<StockReturnItemDtlsTO> returnItemDetls) {
		this.returnItemDetls = returnItemDetls;
	}

	

	
	
	
	

}
