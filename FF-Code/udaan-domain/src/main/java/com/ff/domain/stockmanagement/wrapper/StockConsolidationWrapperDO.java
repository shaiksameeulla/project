package com.ff.domain.stockmanagement.wrapper;

import java.util.Date;

/**
 * @author hkansagr
 * 
 */
public class StockConsolidationWrapperDO {

	/** The transaction created date. */
	private Date txDate;

	/** The stock office. */
	private Integer officeId;

	/** The stock material id. */
	private Integer itemId;

	/** The stock quantity. */
	private Long stockQty;

	/** The transaction number. */
	private String txNo;

	/**
	 * @return the txNo
	 */
	public String getTxNo() {
		return txNo;
	}

	/**
	 * @param txNo
	 *            the txNo to set
	 */
	public void setTxNo(String txNo) {
		this.txNo = txNo;
	}

	/**
	 * @return the txDate
	 */
	public Date getTxDate() {
		return txDate;
	}

	/**
	 * @param txDate
	 *            the txDate to set
	 */
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}

	/**
	 * @param officeId
	 *            the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the stockQty
	 */
	public Long getStockQty() {
		return stockQty;
	}

	/**
	 * @param stockQty
	 *            the stockQty to set
	 */
	public void setStockQty(Long stockQty) {
		this.stockQty = stockQty;
	}

}
