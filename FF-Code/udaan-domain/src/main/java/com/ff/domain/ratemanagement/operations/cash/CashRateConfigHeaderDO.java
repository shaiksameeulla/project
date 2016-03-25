package com.ff.domain.ratemanagement.operations.cash;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */

public class CashRateConfigHeaderDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The cashRateHeaderId - PK. */
	private Integer cashRateHeaderId;
	
	/** The Region Id. */
	private Integer regionId;
	
	/** The from date. */
	private Date fromDate;
	
	/** The to date. */
	private Date toDate;

	/** The header status. */
	private String headerStatus;

	private Set<CashRateConfigHeaderProductDO> cashRateProductDOs;
	
	private Set<CashRateConfigAdditionalChargesDO> cashRateConfigAdditionalChargesDOs;
	
	private Set<CashRateConfigFixedChargesDO> cashRateConfigFixedChargesDOs;
	
	private Set<CashRateConfigRTOChargesDO> cashRateConfigRtoChargesDOs;

	
	/**
	 * @return the cashRateProductDOs
	 */
	public Set<CashRateConfigHeaderProductDO> getCashRateProductDOs() {
		return cashRateProductDOs;
	}
	/**
	 * @param cashRateProductDOs the cashRateProductDOs to set
	 */
	public void setCashRateProductDOs(
			Set<CashRateConfigHeaderProductDO> cashRateProductDOs) {
		this.cashRateProductDOs = cashRateProductDOs;
	}
	/**
	 * @return the cashRateHeaderId
	 */
	public Integer getCashRateHeaderId() {
		return cashRateHeaderId;
	}
	/**
	 * @param cashRateHeaderId the cashRateHeaderId to set
	 */
	public void setCashRateHeaderId(Integer cashRateHeaderId) {
		this.cashRateHeaderId = cashRateHeaderId;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the headerStatus
	 */
	public String getHeaderStatus() {
		return headerStatus;
	}
	/**
	 * @param headerStatus the headerStatus to set
	 */
	public void setHeaderStatus(String headerStatus) {
		this.headerStatus = headerStatus;
	}
	/**
	 * @return the cashRateConfigAdditionalChargesDO
	 */
	public Set<CashRateConfigAdditionalChargesDO> getCashRateConfigAdditionalChargesDOs() {
		return cashRateConfigAdditionalChargesDOs;
	}
	/**
	 * @param cashRateConfigAdditionalChargesDO the cashRateConfigAdditionalChargesDO to set
	 */
	public void setCashRateConfigAdditionalChargesDOs(
			Set<CashRateConfigAdditionalChargesDO> cashRateConfigAdditionalChargesDOs) {
		this.cashRateConfigAdditionalChargesDOs = cashRateConfigAdditionalChargesDOs;
	}
	/**
	 * @return the cashRateConfigFixedChargesDO
	 */
	public Set<CashRateConfigFixedChargesDO> getCashRateConfigFixedChargesDOs() {
		return cashRateConfigFixedChargesDOs;
	}
	/**
	 * @param cashRateConfigFixedChargesDO the cashRateConfigFixedChargesDO to set
	 */
	public void setCashRateConfigFixedChargesDOs(
			Set<CashRateConfigFixedChargesDO> cashRateConfigFixedChargesDOs) {
		this.cashRateConfigFixedChargesDOs = cashRateConfigFixedChargesDOs;
	}
	public Set<CashRateConfigRTOChargesDO> getCashRateConfigRtoChargesDOs() {
		return cashRateConfigRtoChargesDOs;
	}
	public void setCashRateConfigRtoChargesDOs(
			Set<CashRateConfigRTOChargesDO> cashRateConfigRtoChargesDOs) {
		this.cashRateConfigRtoChargesDOs = cashRateConfigRtoChargesDOs;
	}
	
}
