/**
 * 
 */
package com.ff.to.stockmanagement;

import org.apache.struts.upload.FormFile;


/**
 * @author mohammes
 *
 */
public class StockExcelUploadTO extends StockHeaderTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7361677121726733737L;
	
	private Integer loggedInUserId;
	private FormFile stockExcelFile;
	
	private Integer loggedInOfficeId;
	
	private String excelUploadDateStr;
	private String filePath;
	
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
	 * @return the stockExcelFile
	 */
	public FormFile getStockExcelFile() {
		return stockExcelFile;
	}
	/**
	 * @param loggedInUserId the loggedInUserId to set
	 */
	public void setLoggedInUserId(Integer loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	/**
	 * @param stockExcelFile the stockExcelFile to set
	 */
	public void setStockExcelFile(FormFile stockExcelFile) {
		this.stockExcelFile = stockExcelFile;
	}
	/**
	 * @return the excelUploadDateStr
	 */
	public String getExcelUploadDateStr() {
		return excelUploadDateStr;
	}
	/**
	 * @param excelUploadDateStr the excelUploadDateStr to set
	 */
	public void setExcelUploadDateStr(String excelUploadDateStr) {
		this.excelUploadDateStr = excelUploadDateStr;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
