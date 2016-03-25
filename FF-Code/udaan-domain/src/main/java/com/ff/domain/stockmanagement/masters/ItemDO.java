/**
 * 
 */
package com.ff.domain.stockmanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class ItemDO.
 *
 * @author mohammes
 */
public class ItemDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4482017963398142826L;

	
	/** primary key*. */
	private Integer itemId;
	
	/** item code*. */
	private String itemCode;
	
	/** name of the item code*. */
	private String itemName;
	
	/** name of the item description*. */
	private String description;
	
	/** Unit of the measure*. */
	private String uom;
	
	/** it's complete description of the the actual uom*. */
	private String uomDescription;
	
	/** if the item is having series then it holds the type of the  series such as L,M,H etc *. */
	private String itemSeries;
	
	
	/**  if the item is having series,then it decides length of the series
	 * and it excludes the Branch and product. 
	 * This field hold null when item is not having series
	 * **/
	private Integer seriesLength;
	
	/** date of actual item purchased*. */
	private Date purchaseDate;
	
	/** whether record is active or inactive*. */
	private String isActive;
	
	/** Non persistent property it's for generating dynamic Query purpose (ie find whether item has series then get item details) it's only useful for StockUniversalDAOImpl::getAllItemsByType *. */
	private Boolean isSeriesVerifier;
	
	/** it distinguishes  the type of the item as foreign key reference  *. */
	private ItemTypeDO itemTypeDO;

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
	 * Gets the item code.
	 *
	 * @return the item code
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Sets the item code.
	 *
	 * @param itemCode the new item code
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the uom.
	 *
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * Sets the uom.
	 *
	 * @param uom the new uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Gets the uom description.
	 *
	 * @return the uom description
	 */
	public String getUomDescription() {
		return uomDescription;
	}

	/**
	 * Sets the uom description.
	 *
	 * @param uomDescription the new uom description
	 */
	public void setUomDescription(String uomDescription) {
		this.uomDescription = uomDescription;
	}

	/**
	 * Gets the item series.
	 *
	 * @return the item series
	 */
	public String getItemSeries() {
		return itemSeries;
	}

	/**
	 * Sets the item series.
	 *
	 * @param itemSeries the new item series
	 */
	public void setItemSeries(String itemSeries) {
		this.itemSeries = itemSeries;
	}

	/**
	 * Gets the series length.
	 *
	 * @return the series length
	 */
	public Integer getSeriesLength() {
		return seriesLength;
	}

	/**
	 * Sets the series length.
	 *
	 * @param seriesLength the new series length
	 */
	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}

	/**
	 * Gets the purchase date.
	 *
	 * @return the purchase date
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * Sets the purchase date.
	 *
	 * @param purchaseDate the new purchase date
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * Gets the checks if is active.
	 *
	 * @return the checks if is active
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * Sets the checks if is active.
	 *
	 * @param isActive the new checks if is active
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * Gets the item type do.
	 *
	 * @return the item type do
	 */
	public ItemTypeDO getItemTypeDO() {
		return itemTypeDO;
	}

	/**
	 * Sets the item type do.
	 *
	 * @param itemTypeDO the new item type do
	 */
	public void setItemTypeDO(ItemTypeDO itemTypeDO) {
		this.itemTypeDO = itemTypeDO;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the checks if is series verifier.
	 *
	 * @return the isSeriesVerifier
	 */
	public Boolean getIsSeriesVerifier() {
		return isSeriesVerifier;
	}

	/**
	 * Sets the checks if is series verifier.
	 *
	 * @param isSeriesVerifier the isSeriesVerifier to set
	 */
	public void setIsSeriesVerifier(Boolean isSeriesVerifier) {
		this.isSeriesVerifier = isSeriesVerifier;
	}

	
}
