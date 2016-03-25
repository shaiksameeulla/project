package com.ff.manifest;

import java.io.Serializable;

import com.ff.to.stockmanagement.StockUserTO;

public class ManifestIssueValidationTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -606670381095763927L;

	// Input
	/** The stock item number. */
	private String stockItemNumber;

	/** The issued to party type. */
	private String issuedTOPartyType;

	/** The issued to party id. */
	private Integer issuedTOPartyId;

	/** The series type. */
	private String seriesType;

	/** The region code. */
	private String regionCode;

	// Output
	/** The is issued to party. */
	private String isIssued;
	private String errorMsg;

	/** The issued to. */
	private StockUserTO issuedTO; // the party to whom the stock is actually
									// issued (program Outcome)

	/** The city code. */
	private String cityCode;

	
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the stockItemNumber
	 */
	public String getStockItemNumber() {
		return stockItemNumber;
	}

	/**
	 * @param stockItemNumber
	 *            the stockItemNumber to set
	 */
	public void setStockItemNumber(String stockItemNumber) {
		this.stockItemNumber = stockItemNumber;
	}

	/**
	 * @return the issuedTOPartyType
	 */
	public String getIssuedTOPartyType() {
		return issuedTOPartyType;
	}

	/**
	 * @param issuedTOPartyType
	 *            the issuedTOPartyType to set
	 */
	public void setIssuedTOPartyType(String issuedTOPartyType) {
		this.issuedTOPartyType = issuedTOPartyType;
	}

	/**
	 * @return the issuedTOPartyId
	 */
	public Integer getIssuedTOPartyId() {
		return issuedTOPartyId;
	}

	/**
	 * @param issuedTOPartyId
	 *            the issuedTOPartyId to set
	 */
	public void setIssuedTOPartyId(Integer issuedTOPartyId) {
		this.issuedTOPartyId = issuedTOPartyId;
	}

	/**
	 * @return the seriesType
	 */
	public String getSeriesType() {
		return seriesType;
	}

	/**
	 * @param seriesType
	 *            the seriesType to set
	 */
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	/**
	 * @return the isIssued
	 */
	public String getIsIssued() {
		return isIssued;
	}

	/**
	 * @param isIssued
	 *            the isIssued to set
	 */
	public void setIsIssued(String isIssued) {
		this.isIssued = isIssued;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the issuedTO
	 */
	public StockUserTO getIssuedTO() {
		return issuedTO;
	}

	/**
	 * @param issuedTO
	 *            the issuedTO to set
	 */
	public void setIssuedTO(StockUserTO issuedTO) {
		this.issuedTO = issuedTO;
	}

	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

}
