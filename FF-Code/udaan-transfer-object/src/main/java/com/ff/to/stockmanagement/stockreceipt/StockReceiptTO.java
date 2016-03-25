package com.ff.to.stockmanagement.stockreceipt;

import java.util.List;

import com.ff.to.stockmanagement.StockHeaderTO;

/**
 * @author hkansagr
 */

public class StockReceiptTO extends StockHeaderTO{
	private static final long serialVersionUID = 1234345354346456L;
	
	private Long stockReceiptId;
	
	
	private String receiptDateStr;
	private String receiptTimeStr;
	
	private Integer issueOfficeId;
	private Integer receiptOfficeId;
	private Integer loggedInOfficeId;
	private Integer loggedInUserId;
	private Integer createdByUserId;
	private Integer updatedByUserId;
	private String issuedDate;
		
	private Long rowStockReceiptItemDtlsId [] = new Long[rowCount];
	
	private Integer rowStockForBranchId [] = new Integer[rowCount];//Only Stock Receipt at RHO it's a Office Id 
	private String status;
	private String screenType;

	List<StockReceiptItemDtlsTO> receiptItemDtls;

	


	

	
	/**
	 * @return the issuedDate
	 */
	public String getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @param issuedDate the issuedDate to set
	 */
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	
	/**
	 * @return the receiptOfficeId
	 */
	public Integer getReceiptOfficeId() {
		return receiptOfficeId;
	}

	/**
	 * @param receiptOfficeId the receiptOfficeId to set
	 */
	public void setReceiptOfficeId(Integer receiptOfficeId) {
		this.receiptOfficeId = receiptOfficeId;
	}

	/**
	 * @return the stockReceiptId
	 */
	public Long getStockReceiptId() {
		return stockReceiptId;
	}

	/**
	 * @param stockReceiptId the stockReceiptId to set
	 */
	public void setStockReceiptId(Long stockReceiptId) {
		this.stockReceiptId = stockReceiptId;
	}

	

	
	

	/**
	 * @return the receiptDateStr
	 */
	public String getReceiptDateStr() {
		return receiptDateStr;
	}

	/**
	 * @param receiptDateStr the receiptDateStr to set
	 */
	public void setReceiptDateStr(String receiptDateStr) {
		this.receiptDateStr = receiptDateStr;
	}

	/**
	 * @return the receiptTimeStr
	 */
	public String getReceiptTimeStr() {
		return receiptTimeStr;
	}

	/**
	 * @param receiptTimeStr the receiptTimeStr to set
	 */
	public void setReceiptTimeStr(String receiptTimeStr) {
		this.receiptTimeStr = receiptTimeStr;
	}

	/**
	 * @return the issueOfficeId
	 */
	public Integer getIssueOfficeId() {
		return issueOfficeId;
	}

	/**
	 * @param issueOfficeId the issueOfficeId to set
	 */
	public void setIssueOfficeId(Integer issueOfficeId) {
		this.issueOfficeId = issueOfficeId;
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
	 * @return the loggedInUserId
	 */
	public Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}

	/**
	 * @return the createdByUserId
	 */
	public Integer getCreatedByUserId() {
		return createdByUserId;
	}

	/**
	 * @param createdByUserId the createdByUserId to set
	 */
	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	/**
	 * @return the updatedByUserId
	 */
	public Integer getUpdatedByUserId() {
		return updatedByUserId;
	}

	/**
	 * @param updatedByUserId the updatedByUserId to set
	 */
	public void setUpdatedByUserId(Integer updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	/**
	 * @return the rowStockReceiptItemDtlsId
	 */
	public Long[] getRowStockReceiptItemDtlsId() {
		return rowStockReceiptItemDtlsId;
	}

	/**
	 * @param rowStockReceiptItemDtlsId the rowStockReceiptItemDtlsId to set
	 */
	public void setRowStockReceiptItemDtlsId(Long[] rowStockReceiptItemDtlsId) {
		this.rowStockReceiptItemDtlsId = rowStockReceiptItemDtlsId;
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
	 * @return the receiptItemDtls
	 */
	public List<StockReceiptItemDtlsTO> getReceiptItemDtls() {
		return receiptItemDtls;
	}

	/**
	 * @param receiptItemDtls the receiptItemDtls to set
	 */
	public void setReceiptItemDtls(List<StockReceiptItemDtlsTO> receiptItemDtls) {
		this.receiptItemDtls = receiptItemDtls;
	}


	/**
	 * @return the rowStockForBranchId
	 */
	public Integer[] getRowStockForBranchId() {
		return rowStockForBranchId;
	}

	/**
	 * @param rowStockForBranchId the rowStockForBranchId to set
	 */
	public void setRowStockForBranchId(Integer[] rowStockForBranchId) {
		this.rowStockForBranchId = rowStockForBranchId;
	}

	/**
	 * @return the screenType
	 */
	public String getScreenType() {
		return screenType;
	}

	/**
	 * @param screenType the screenType to set
	 */
	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}

}
