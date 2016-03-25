package com.ff.domain.ratemanagement.operations.cash;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 */

public class BcunCashRateConfigHeaderDO extends CGFactDO {

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

	private Set<BcunCashRateConfigHeaderProductDO> cashRateProductDOs;
	
	private Set<BcunCashRateConfigAdditionalChargesDO> cashRateConfigAdditionalChargesDOs;
	
	private Set<BcunCashRateConfigFixedChargesDO> cashRateConfigFixedChargesDOs;
	
	private Set<BcunCashRateConfigRTOChargesDO> cashRateConfigRtoChargesDOs;

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
	 * @return the cashRateProductDOs
	 */
	public Set<BcunCashRateConfigHeaderProductDO> getCashRateProductDOs() {
		return cashRateProductDOs;
	}

	/**
	 * @param cashRateProductDOs the cashRateProductDOs to set
	 */
	public void setCashRateProductDOs(
			Set<BcunCashRateConfigHeaderProductDO> cashRateProductDOs) {
		this.cashRateProductDOs = cashRateProductDOs;
	}

	/**
	 * @return the cashRateConfigAdditionalChargesDOs
	 */
	public Set<BcunCashRateConfigAdditionalChargesDO> getCashRateConfigAdditionalChargesDOs() {
		return cashRateConfigAdditionalChargesDOs;
	}

	/**
	 * @param cashRateConfigAdditionalChargesDOs the cashRateConfigAdditionalChargesDOs to set
	 */
	public void setCashRateConfigAdditionalChargesDOs(
			Set<BcunCashRateConfigAdditionalChargesDO> cashRateConfigAdditionalChargesDOs) {
		this.cashRateConfigAdditionalChargesDOs = cashRateConfigAdditionalChargesDOs;
	}

	/**
	 * @return the cashRateConfigFixedChargesDOs
	 */
	public Set<BcunCashRateConfigFixedChargesDO> getCashRateConfigFixedChargesDOs() {
		return cashRateConfigFixedChargesDOs;
	}

	/**
	 * @param cashRateConfigFixedChargesDOs the cashRateConfigFixedChargesDOs to set
	 */
	public void setCashRateConfigFixedChargesDOs(
			Set<BcunCashRateConfigFixedChargesDO> cashRateConfigFixedChargesDOs) {
		this.cashRateConfigFixedChargesDOs = cashRateConfigFixedChargesDOs;
	}

	/**
	 * @return the cashRateConfigRtoChargesDOs
	 */
	public Set<BcunCashRateConfigRTOChargesDO> getCashRateConfigRtoChargesDOs() {
		return cashRateConfigRtoChargesDOs;
	}

	/**
	 * @param cashRateConfigRtoChargesDOs the cashRateConfigRtoChargesDOs to set
	 */
	public void setCashRateConfigRtoChargesDOs(
			Set<BcunCashRateConfigRTOChargesDO> cashRateConfigRtoChargesDOs) {
		this.cashRateConfigRtoChargesDOs = cashRateConfigRtoChargesDOs;
	}
	
	
	
}
