/**
 * 
 */
package com.ff.domain.stockmanagement.masters;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author cbhure
 *
 */

public class SAPItemDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263032004892721669L;
	private Long Id;
	private String itemTypeCode;
	private String itemTypeName;
	private char curStatus;
	private String itemCode;
	private String itemName;
	private char itemHasSeries;
	private String itemSeries;
	private String description;
	private String uom;
	private Integer seriesLength;
	private char isError;
	private String errorDesc;
	private String exception;
	
	
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
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
	 * @return the curStatus
	 */
	public char getCurStatus() {
		return curStatus;
	}
	/**
	 * @param curStatus the curStatus to set
	 */
	public void setCurStatus(char curStatus) {
		this.curStatus = curStatus;
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
	
	public String getItemSeries() {
		return itemSeries;
	}
	public void setItemSeries(String itemSeries) {
		this.itemSeries = itemSeries;
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
	 * @return the isError
	 */
	public char getIsError() {
		return isError;
	}
	/**
	 * @param isError the isError to set
	 */
	public void setIsError(char isError) {
		this.isError = isError;
	}
	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	/**
	 * @return the itemHasSeries
	 */
	public char getItemHasSeries() {
		return itemHasSeries;
	}
	/**
	 * @param itemHasSeries the itemHasSeries to set
	 */
	public void setItemHasSeries(char itemHasSeries) {
		this.itemHasSeries = itemHasSeries;
	}

}
