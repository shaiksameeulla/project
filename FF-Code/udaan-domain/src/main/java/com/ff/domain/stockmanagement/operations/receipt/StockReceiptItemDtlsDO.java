package com.ff.domain.stockmanagement.operations.receipt;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.stockmanagement.operations.StockCommonDetailsDO;

/**
 * The Class StockReceiptItemDtlsDO.
 *
 * @author hkansagr
 */

public class StockReceiptItemDtlsDO extends StockCommonDetailsDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key for the stock receipt item. */
	private Long stockReceiptItemDtlsId;
	
	private Integer balanceReturnQuantity; 
	
	/** foreign key to parent Many-to-one relationship in hbms. */
	@JsonBackReference
	private StockReceiptDO stockReceiptDO;
	
	
	/**
	 * Gets the stock receipt item dtls id.
	 *
	 * @return the stockReceiptItemDtlsId
	 */
	public Long getStockReceiptItemDtlsId() {
		return stockReceiptItemDtlsId;
	}
	
	/**
	 * Sets the stock receipt item dtls id.
	 *
	 * @param stockReceiptItemDtlsId the stockReceiptItemDtlsId to set
	 */
	public void setStockReceiptItemDtlsId(Long stockReceiptItemDtlsId) {
		this.stockReceiptItemDtlsId = stockReceiptItemDtlsId;
	}
	
	/**
	 * Gets the stock receipt do.
	 *
	 * @return the stockReceiptDO
	 */
	public StockReceiptDO getStockReceiptDO() {
		return stockReceiptDO;
	}
	
	/**
	 * Sets the stock receipt do.
	 *
	 * @param stockReceiptDO the stockReceiptDO to set
	 */
	public void setStockReceiptDO(StockReceiptDO stockReceiptDO) {
		this.stockReceiptDO = stockReceiptDO;
	}

	/**
	 * @return the balanceReturnQuantity
	 */
	public Integer getBalanceReturnQuantity() {
		return balanceReturnQuantity;
	}

	/**
	 * @param balanceReturnQuantity the balanceReturnQuantity to set
	 */
	public void setBalanceReturnQuantity(Integer balanceReturnQuantity) {
		this.balanceReturnQuantity = balanceReturnQuantity;
	}
	
}
