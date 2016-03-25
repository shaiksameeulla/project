package com.ff.domain.stockmanagement.operations.issue;

import com.ff.domain.stockmanagement.operations.BcunStockCommonDetailsDO;

/**
 * The Class StockIssueItemDtlsDO.
 *
 * @author hkansagr
 */

public class BcunStockIssueItemDtlsDO extends BcunStockCommonDetailsDO{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** primary key. */
	private Long stockIssueItemDtlsId;
	
	
	
	/** balanceReceiptQuantity : updated based on receipt. */
	private Integer balanceReceiptQnty;
	
	
	/** The rate per unit quantity. */
	private Double ratePerUnitQuantity;
	
	/** The total rate.  ie RatePerUnit * Stock Issue Quantity*/
	private Double itemPrice;
	

	/**
	 * Gets the stock issue item dtls id.
	 *
	 * @return the stockIssueItemDtlsId
	 */
	public Long getStockIssueItemDtlsId() {
		return stockIssueItemDtlsId;
	}

	/**
	 * Sets the stock issue item dtls id.
	 *
	 * @param stockIssueItemDtlsId the stockIssueItemDtlsId to set
	 */
	public void setStockIssueItemDtlsId(Long stockIssueItemDtlsId) {
		this.stockIssueItemDtlsId = stockIssueItemDtlsId;
	}

	/**
	 * Gets the balance receipt qnty.
	 *
	 * @return the balanceReceiptQnty
	 */
	public Integer getBalanceReceiptQnty() {
		return balanceReceiptQnty;
	}

	/**
	 * Sets the balance receipt qnty.
	 *
	 * @param balanceReceiptQnty the balanceReceiptQnty to set
	 */
	public void setBalanceReceiptQnty(Integer balanceReceiptQnty) {
		this.balanceReceiptQnty = balanceReceiptQnty;
	}

	/**
	 * @return the ratePerUnitQuantity
	 */
	public Double getRatePerUnitQuantity() {
		return ratePerUnitQuantity;
	}

	/**
	 * @return the itemPrice
	 */
	public Double getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param ratePerUnitQuantity the ratePerUnitQuantity to set
	 */
	public void setRatePerUnitQuantity(Double ratePerUnitQuantity) {
		this.ratePerUnitQuantity = ratePerUnitQuantity;
	}

	/**
	 * @param itemPrice the itemPrice to set
	 */
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	



	
	
}
