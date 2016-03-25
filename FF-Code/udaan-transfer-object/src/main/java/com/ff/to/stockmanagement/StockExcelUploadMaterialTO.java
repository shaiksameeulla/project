/**
 * 
 */
package com.ff.to.stockmanagement;


/**
 * @author mohammes
 *
 */
public class StockExcelUploadMaterialTO extends StockDetailTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132114917027439991L;

	private String stockReceiptOfficeCode;
	private Integer stockReceiptOfficeId;
	/**
	 * @return the stockReceiptOfficeCode
	 */
	public String getStockReceiptOfficeCode() {
		return stockReceiptOfficeCode;
	}
	/**
	 * @return the stockReceiptOfficeId
	 */
	public Integer getStockReceiptOfficeId() {
		return stockReceiptOfficeId;
	}
	/**
	 * @param stockReceiptOfficeCode the stockReceiptOfficeCode to set
	 */
	public void setStockReceiptOfficeCode(String stockReceiptOfficeCode) {
		this.stockReceiptOfficeCode = stockReceiptOfficeCode;
	}
	/**
	 * @param stockReceiptOfficeId the stockReceiptOfficeId to set
	 */
	public void setStockReceiptOfficeId(Integer stockReceiptOfficeId) {
		this.stockReceiptOfficeId = stockReceiptOfficeId;
	}

	
	

}
