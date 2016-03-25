/**
 * 
 */
package com.ff.to.stockmanagement.masters;

import java.util.Date;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.operations.StockRateTO;

/**
 * @author mohammes
 *
 */
@SuppressWarnings("serial")
public class ItemTO extends CGBaseTO {
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
	
	/**  Non persistent property it's for generating dynamic Query purpose (ie find whether item has series then get item details)
	 * it's only useful for StockUniversalDAOImpl::getAllItemsByType 
	 * **/
	private Boolean isSeriesVerifier=false;
	
	/**it distinguishes  the type of the item as  
	 * foreign key reference
	 * 
	 * **/
	private ItemTypeTO itemTypeTO;

	private Integer stockQuantity;//dont remove it's referring in Stock issue js
	
	/**  series type 
	 * **/
	
	private String seriesType;//dont remove it's referring in Stock issue js
	
	private StockRateTO rateTO;
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * @return the uomDescription
	 */
	public String getUomDescription() {
		return uomDescription;
	}

	/**
	 * @param uomDescription the uomDescription to set
	 */
	public void setUomDescription(String uomDescription) {
		this.uomDescription = uomDescription;
	}

	/**
	 * @return the itemSeries
	 */
	public String getItemSeries() {
		return itemSeries;
	}

	/**
	 * @param itemSeries the itemSeries to set
	 */
	public void setItemSeries(String itemSeries) {
		this.itemSeries = itemSeries;
	}

	/**
	 * @return the seriesLength
	 */
	public Integer getSeriesLength() {
		return seriesLength;
	}

	/**
	 * @param seriesLength the seriesLength to set
	 */
	public void setSeriesLength(Integer seriesLength) {
		this.seriesLength = seriesLength;
	}

	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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

	/**
	 * @return the itemTypeTO
	 */
	public ItemTypeTO getItemTypeTO() {
		return itemTypeTO;
	}

	/**
	 * @param itemTypeTO the itemTypeTO to set
	 */
	public void setItemTypeTO(ItemTypeTO itemTypeTO) {
		this.itemTypeTO = itemTypeTO;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	/**
	 * @return the isSeriesVerifier
	 */
	public Boolean getIsSeriesVerifier() {
		return isSeriesVerifier;
	}

	/**
	 * @param isSeriesVerifier the isSeriesVerifier to set
	 */
	public void setIsSeriesVerifier(Boolean isSeriesVerifier) {
		this.isSeriesVerifier = isSeriesVerifier;
	}

	/**
	 * @return the seriesType
	 */
	public String getSeriesType() {
		return seriesType;
	}

	/**
	 * @return the rateTO
	 */
	public StockRateTO getRateTO() {
		return rateTO;
	}

	/**
	 * @param rateTO the rateTO to set
	 */
	public void setRateTO(StockRateTO rateTO) {
		this.rateTO = rateTO;
	}

	/**
	 * @param seriesType the seriesType to set
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	

	

	
}
