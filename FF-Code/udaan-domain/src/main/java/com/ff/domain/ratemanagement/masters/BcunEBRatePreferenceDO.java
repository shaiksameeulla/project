package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.booking.BcunBookingPreferenceDetailsDO;

public class BcunEBRatePreferenceDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2942112744954332646L;
		
	private Integer ebRatePrefId;
	private Integer ebRateConfigDO;
	private String applicability;
	private BcunBookingPreferenceDetailsDO bookingPreferenceDetailsDO;
	private Double rate;
	/**
	 * @return the ebRatePrefId
	 */
	public Integer getEbRatePrefId() {
		return ebRatePrefId;
	}
	/**
	 * @param ebRatePrefId the ebRatePrefId to set
	 */
	public void setEbRatePrefId(Integer ebRatePrefId) {
		this.ebRatePrefId = ebRatePrefId;
	}
	
	/**
	 * @return the applicability
	 */
	public String getApplicability() {
		return applicability;
	}
	/**
	 * @param applicability the applicability to set
	 */
	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}
	/**
	 * @return the bookingPreferenceDetailsDO
	 */
	public BcunBookingPreferenceDetailsDO getBookingPreferenceDetailsDO() {
		return bookingPreferenceDetailsDO;
	}
	/**
	 * @param bookingPreferenceDetailsDO the bookingPreferenceDetailsDO to set
	 */
	public void setBookingPreferenceDetailsDO(
			BcunBookingPreferenceDetailsDO bookingPreferenceDetailsDO) {
		this.bookingPreferenceDetailsDO = bookingPreferenceDetailsDO;
	}
	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	/**
	 * @return the ebRateConfigDO
	 */
	public Integer getEbRateConfigDO() {
		return ebRateConfigDO;
	}
	/**
	 * @param ebRateConfigDO the ebRateConfigDO to set
	 */
	public void setEbRateConfigDO(Integer ebRateConfigDO) {
		this.ebRateConfigDO = ebRateConfigDO;
	}

	
	

}
