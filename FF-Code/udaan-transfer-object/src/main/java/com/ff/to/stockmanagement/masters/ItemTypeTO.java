/**
 * 
 */
package com.ff.to.stockmanagement.masters;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 *
 */
@SuppressWarnings("serial")
public class ItemTypeTO extends CGBaseTO {

	
	/**  primary key**/
	private Integer itemTypeId;
	/**  code of the item Type**/
	private String itemTypeCode;
	
	/**  Name (description)of the item Type**/
	private String itemTypeName;
	/**  whether item is having series or not**/
	private String itemHasSeries;
	/**  current status of the record**/
	private String isActive;
	/**
	 * @return the itemTypeId
	 */
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	/**
	 * @param itemTypeId the itemTypeId to set
	 */
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	/**
	 * @return the itemTypeCode
	 */
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	/**
	 * @param itemTypeCode the itemTypeCode to set
	 */
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	/**
	 * @return the itemTypeName
	 */
	public String getItemTypeName() {
		return itemTypeName;
	}
	/**
	 * @param itemTypeName the itemTypeName to set
	 */
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	/**
	 * @return the itemHasSeries
	 */
	public String getItemHasSeries() {
		return itemHasSeries;
	}
	/**
	 * @param itemHasSeries the itemHasSeries to set
	 */
	public void setItemHasSeries(String itemHasSeries) {
		this.itemHasSeries = itemHasSeries;
	}
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}
