/**
 * 
 */
package com.ff.domain.stockmanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class ItemTypeDO.
 *
 * @author mohammes
 */
public class ItemTypeDO extends CGMasterDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 12323L;
	
	/** primary key*. */
	private Integer itemTypeId;
	
	/** code of the item Type*. */
	private String itemTypeCode;
	
	/** Name (description)of the item Type*. */
	private String itemTypeName;
	
	/** whether item is having series or not*. */
	private String itemHasSeries;
	
	/** current status of the record*. */
	private String isActive;
	
	/**
	 * 
	 * Gets the item type id.
	 *
	 * @return the item type id
	 */
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	
	/**
	 * Sets the item type id.
	 *
	 * @param itemTypeId the new item type id
	 */
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	
	/**
	 * Gets the item type code.
	 *
	 * @return the item type code
	 */
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	
	/**
	 * Sets the item type code.
	 *
	 * @param itemTypeCode the new item type code
	 */
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	
	/**
	 * Gets the item type name.
	 *
	 * @return the item type name
	 */
	public String getItemTypeName() {
		return itemTypeName;
	}
	
	/**
	 * Sets the item type name.
	 *
	 * @param itemTypeName the new item type name
	 */
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	
	/**
	 * Gets the item has series.
	 *
	 * @return the item has series
	 */
	public String getItemHasSeries() {
		return itemHasSeries;
	}
	
	/**
	 * Sets the item has series.
	 *
	 * @param itemHasSeries the new item has series
	 */
	public void setItemHasSeries(String itemHasSeries) {
		this.itemHasSeries = itemHasSeries;
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
	
}
