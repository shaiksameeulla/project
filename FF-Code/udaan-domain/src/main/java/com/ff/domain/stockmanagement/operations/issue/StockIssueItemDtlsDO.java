package com.ff.domain.stockmanagement.operations.issue;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.stockmanagement.operations.StockCommonDetailsDO;

/**
 * The Class StockIssueItemDtlsDO.
 *
 * @author hkansagr
 */

public class StockIssueItemDtlsDO extends StockCommonDetailsDO{
	
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
	
	/** foreign key to parent many-to-one relationship. */
	@JsonBackReference
	private StockIssueDO stockIssueDO;

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
	 * Gets the stock issue do.
	 *
	 * @return the stockIssueDO
	 */
	public StockIssueDO getStockIssueDO() {
		return stockIssueDO;
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
	 * Sets the stock issue do.
	 *
	 * @param stockIssueDO the stockIssueDO to set
	 */
	public void setStockIssueDO(StockIssueDO stockIssueDO) {
		this.stockIssueDO = stockIssueDO;
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
