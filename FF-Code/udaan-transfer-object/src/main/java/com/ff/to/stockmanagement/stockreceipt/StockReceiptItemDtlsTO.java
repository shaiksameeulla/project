package com.ff.to.stockmanagement.stockreceipt;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.StockDetailTO;

/**
 * @author hkansagr
 */

public class StockReceiptItemDtlsTO extends StockDetailTO implements Comparable<StockReceiptItemDtlsTO>
{
	private static final long serialVersionUID = 1L;
	
	/**		primary key for the stock receipt item details table 	*/
	private Long stockReceiptItemDtlsId;
	
	private String stockForBranchName;//For Stock Receipt at RHO, Office Code +Name will be populated
	
	
	
		
	/**
	 * @return the stockReceiptItemDtlsId
	 */
	public Long getStockReceiptItemDtlsId() {
		return stockReceiptItemDtlsId;
	}
	/**
	 * @param stockReceiptItemDtlsId the stockReceiptItemDtlsId to set
	 */
	public void setStockReceiptItemDtlsId(Long stockReceiptItemDtlsId) {
		this.stockReceiptItemDtlsId = stockReceiptItemDtlsId;
	}
	
	
	@Override
	public int compareTo(StockReceiptItemDtlsTO arg0) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(stockReceiptItemDtlsId) && !StringUtil.isEmptyLong(arg0.getStockReceiptItemDtlsId())) {
			result = this.stockReceiptItemDtlsId.compareTo(arg0.stockReceiptItemDtlsId);
		}
		return result;
	}
	/**
	 * @return the stockForBranchName
	 */
	public String getStockForBranchName() {
		return stockForBranchName;
	}
	/**
	 * @param stockForBranchName the stockForBranchName to set
	 */
	public void setStockForBranchName(String stockForBranchName) {
		this.stockForBranchName = stockForBranchName;
	}

}
