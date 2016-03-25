package com.ff.domain.stockmanagement.operations.receipt;

import com.ff.domain.stockmanagement.operations.BcunStockCommonDetailsDO;

/**
 * The Class StockReceiptItemDtlsDO.
 *
 * @author hkansagr
 */

public class BcunStockReceiptItemDtlsDO extends BcunStockCommonDetailsDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key for the stock receipt item. */
	private Long stockReceiptItemDtlsId;
	
	/** foreign key to parent Many-to-one relationship in hbms. */
	
	
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

	
}
