package com.ff.domain.ratemanagement.operations.cash;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class CashRateHeaderDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866240661337840981L;
	private Integer cashRateHeaderId;
	private Integer regionId;
	private Date fromDate;
	private Date toDate;
	/** The header status. */
	private String headerStatus;
	
	
	
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
	public Integer getCashRateHeaderId() {
		return cashRateHeaderId;
	}
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
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
