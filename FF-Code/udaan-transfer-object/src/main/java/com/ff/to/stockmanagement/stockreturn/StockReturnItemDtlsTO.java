/**
 * 
 */
package com.ff.to.stockmanagement.stockreturn;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.stockissue.StockIssueItemDtlsTO;

/**
 * @author cbhure
 *
 */
public class StockReturnItemDtlsTO extends StockDetailTO implements Comparable<StockReturnItemDtlsTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531669289376667366L;
	
	private Long stockReturnItemDtlsId;
	
	/**
	 * @return the stockReturnItemDtlsId
	 */
	public Long getStockReturnItemDtlsId() {
		return stockReturnItemDtlsId;
	}
	/**
	 * @param stockReturnItemDtlsId the stockReturnItemDtlsId to set
	 */
	public void setStockReturnItemDtlsId(Long stockReturnItemDtlsId) {
		this.stockReturnItemDtlsId = stockReturnItemDtlsId;
	}
	public int compareTo(StockReturnItemDtlsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(stockReturnItemDtlsId) && !StringUtil.isEmptyLong(arg0.getStockReturnItemDtlsId())) {
			result = this.stockReturnItemDtlsId.compareTo(arg0.getStockReturnItemDtlsId());
		}
		return result;
	}

}
