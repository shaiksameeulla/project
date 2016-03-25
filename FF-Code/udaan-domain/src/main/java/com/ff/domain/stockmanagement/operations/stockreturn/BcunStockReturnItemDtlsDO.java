/**
 * 
 */
package com.ff.domain.stockmanagement.operations.stockreturn;

import com.ff.domain.stockmanagement.operations.BcunStockCommonDetailsDO;

/**
 * The Class StockReturnItemDtlsDO.
 *
 * @author cbhure
 */
public class BcunStockReturnItemDtlsDO extends BcunStockCommonDetailsDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6732925401141191285L;
	
	/** The stock return item dtls id. */
	private Long stockReturnItemDtlsId;
		
	/** The return do. */
	private BcunStockReturnDO returnDo;

	/**
	 * Gets the stock return item dtls id.
	 *
	 * @return the stockReturnItemDtlsId
	 */
	public Long getStockReturnItemDtlsId() {
		return stockReturnItemDtlsId;
	}

	/**
	 * Sets the stock return item dtls id.
	 *
	 * @param stockReturnItemDtlsId the stockReturnItemDtlsId to set
	 */
	public void setStockReturnItemDtlsId(Long stockReturnItemDtlsId) {
		this.stockReturnItemDtlsId = stockReturnItemDtlsId;
	}

	public BcunStockReturnDO getReturnDo() {
		return returnDo;
	}

	public void setReturnDo(BcunStockReturnDO returnDo) {
		this.returnDo = returnDo;
	}

	
	
}
