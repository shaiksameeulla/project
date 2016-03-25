package com.ff.to.stockmanagement.stockissue;

import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.to.stockmanagement.StockDetailTO;

/**
 * @author hkansagr
 * 
 */

public class StockIssueItemDtlsTO extends StockDetailTO implements Comparable<StockIssueItemDtlsTO>{

	private static final long serialVersionUID = 1L;

	/*   	Primary Key 	*/
	private Long stockIssueItemDtlsId;
	
	/** The rate per unit quantity. */
	private Double ratePerUnitQuantity;
	
	/** The total rate.  ie RatePerUnit * Stock Issue Quantity*/
	private Double itemPrice;
	
	

	
	public Long getStockIssueItemDtlsId() {
		return stockIssueItemDtlsId;
	}




	public void setStockIssueItemDtlsId(Long stockIssueItemDtlsId) {
		this.stockIssueItemDtlsId = stockIssueItemDtlsId;
	}




	/**		GETTERS AND SETTERS		*/
	
	
	

	public int compareTo(StockIssueItemDtlsTO arg0) {
		int result=0;
		if(!StringUtil.isEmptyInteger(rowNumber) && !StringUtil.isEmptyInteger(arg0.getRowNumber())) {
			result = this.rowNumber.compareTo(arg0.rowNumber);
		}
		else if(!StringUtil.isEmptyLong(stockIssueItemDtlsId) && !StringUtil.isEmptyLong(arg0.getStockIssueItemDtlsId())) {
			result = this.stockIssueItemDtlsId.compareTo(arg0.stockIssueItemDtlsId);
		}
		return result;
	}

	/**
	 * @return the ratePerUnitQuantity
	 */
	public Double getRatePerUnitQuantity() {
		return ratePerUnitQuantity;
	}

	/**
	 * @param ratePerUnitQuantity the ratePerUnitQuantity to set
	 */
	public void setRatePerUnitQuantity(Double ratePerUnitQuantity) {
		this.ratePerUnitQuantity = ratePerUnitQuantity;
	}




	/**
	 * @return the itemPrice
	 */
	public Double getItemPrice() {
		return itemPrice;
	}




	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}







}