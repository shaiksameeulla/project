/**
 * 
 */
package com.ff.domain.stockmanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class StockBaseDO.
 *
 * @author mohammes
 */
@SuppressWarnings("serial")
public abstract class StockBaseDO extends CGMasterDO {

	/** The item do. */
	private ItemDO itemDO;//since we require entire ItemDo for Stock Office Mapping 
	
	/** The stock id. */
	private Integer stockId;
	
	/** The item id. */
	private Integer itemId;//it's required for all Stock Mappings other than Stock Office Mapping 
	
	/** The quantity. */
	private Integer  quantity;
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public Integer getItemId() {
		return itemId;
	}
	
	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	/**
	 * Gets the stock id.
	 *
	 * @return the stock id
	 */
	public Integer getStockId() {
		return stockId;
	}
	
	/**
	 * Sets the stock id.
	 *
	 * @param stockId the new stock id
	 */
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	
	/**
	 * Gets the item do.
	 *
	 * @return the itemDO
	 */
	public ItemDO getItemDO() {
		return itemDO;
	}
	
	/**
	 * Sets the item do.
	 *
	 * @param itemDO the itemDO to set
	 */
	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}
	
}
