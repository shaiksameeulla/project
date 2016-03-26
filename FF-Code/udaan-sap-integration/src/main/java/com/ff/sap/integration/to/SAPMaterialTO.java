package com.ff.sap.integration.to;

import java.util.Date;

public class SAPMaterialTO {
	/**  primary key**/
	private Integer itemId;
	
	/**  item code**/
	private String itemCode;
	/**  name of the item code**/
	private String itemName;
	
	/**  name of the item description**/
	private String description;
	
	/**  Unit of the measure**/
	private String uom;
	
	/**  it's complete description of the the actual uom**/
	private String uomDescription;
	
	/** if the item is having series then it holds the type of the  series
	 * such as L,M,H etc
	 * **/
	private String itemSeries;
	
	
	/**  if the item is having series,then it decides length of the series
	 * and it excludes the Branch and product. 
	 * This field hold null when item is not having series
	 * **/
	private Integer seriesLength;
	/**  date of actual item purchased**/
	private Date purchaseDate;
	
	/**  whether record is active or inactive**/
	private String isActive;
	
	/**it distinguishes  the type of the item as  
	 * foreign key reference
	 * 
	 * **/
	private String itemTypeCode;
	
	private String itemTypeName;
	/**  whether item is having series or not**/
	private String itemHasSeries;
	/**  current status of the record**/
	
	private Integer itemTypeId;
	private String isDeleted;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getUomDescription() {
		return uomDescription;
	}
	public void setUomDescription(String uomDescription) {
		this.uomDescription = uomDescription;
	}
	public String getItemSeries() {
		return itemSeries;
	}
	public void setItemSeries(String itemSeries) {
		this.itemSeries = itemSeries;
	}
	public Integer getSeriesLength() {
		return seriesLength;
	}
	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public String getItemHasSeries() {
		return itemHasSeries;
	}
	public void setItemHasSeries(String itemHasSeries) {
		this.itemHasSeries = itemHasSeries;
	}
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
}
