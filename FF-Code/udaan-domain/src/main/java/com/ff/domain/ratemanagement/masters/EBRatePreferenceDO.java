package com.ff.domain.ratemanagement.masters;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.booking.BookingPreferenceDetailsDO;

public class EBRatePreferenceDO extends CGMasterDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4864791322114795785L;
	
	private Integer ebRatePrefId;
	private EBRateConfigDO ebRateConfigDO;
	private String applicability;
	private BookingPreferenceDetailsDO bookingPreferenceDetailsDO;
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
	 * @return the ebRateConfigDO
	 */
	public EBRateConfigDO getEbRateConfigDO() {
		return ebRateConfigDO;
	}
	/**
	 * @param ebRateConfigDO the ebRateConfigDO to set
	 */
	public void setEbRateConfigDO(EBRateConfigDO ebRateConfigDO) {
		this.ebRateConfigDO = ebRateConfigDO;
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
	public BookingPreferenceDetailsDO getBookingPreferenceDetailsDO() {
		return bookingPreferenceDetailsDO;
	}
	/**
	 * @param bookingPreferenceDetailsDO the bookingPreferenceDetailsDO to set
	 */
	public void setBookingPreferenceDetailsDO(
			BookingPreferenceDetailsDO bookingPreferenceDetailsDO) {
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

	
	

}
