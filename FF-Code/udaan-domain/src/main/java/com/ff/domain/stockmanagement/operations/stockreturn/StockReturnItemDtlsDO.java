/**
 * 
 */
package com.ff.domain.stockmanagement.operations.stockreturn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.stockmanagement.operations.StockCommonDetailsDO;

/**
 * The Class StockReturnItemDtlsDO.
 *
 * @author cbhure
 */
public class StockReturnItemDtlsDO extends StockCommonDetailsDO{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6732925401141191285L;
	
	/** The stock return item dtls id. */
	private Long stockReturnItemDtlsId;
		
	/** The return do. */
	@JsonBackReference
	private StockReturnDO returnDo;

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

	/**
	 * Gets the return do.
	 *
	 * @return the returnDo
	 */
	public StockReturnDO getReturnDo() {
		return returnDo;
	}

	/**
	 * Sets the return do.
	 *
	 * @param returnDo the returnDo to set
	 */
	public void setReturnDo(StockReturnDO returnDo) {
		this.returnDo = returnDo;
	}
	
}
