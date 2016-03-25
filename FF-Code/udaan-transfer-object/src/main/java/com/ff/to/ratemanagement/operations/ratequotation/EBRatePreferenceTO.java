package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingPreferenceDetailsTO;

public class EBRatePreferenceTO extends CGBaseTO implements Comparable<EBRatePreferenceTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6647157516266641986L;
	private Integer ebRatePrefId;
	private EBRateConfigTO ebRateConfigTO;
	private String applicability;
	private BookingPreferenceDetailsTO preferenceDetailsTO;
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
	 * @return the ebRateConfigTO
	 */
	public EBRateConfigTO getEbRateConfigTO() {
		return ebRateConfigTO;
	}
	/**
	 * @param ebRateConfigTO the ebRateConfigTO to set
	 */
	public void setEbRateConfigTO(EBRateConfigTO ebRateConfigTO) {
		this.ebRateConfigTO = ebRateConfigTO;
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
	 * @return the preferenceDetailsTO
	 */
	public BookingPreferenceDetailsTO getPreferenceDetailsTO() {
		return preferenceDetailsTO;
	}
	/**
	 * @param preferenceDetailsTO the preferenceDetailsTO to set
	 */
	public void setPreferenceDetailsTO(
			BookingPreferenceDetailsTO preferenceDetailsTO) {
		this.preferenceDetailsTO = preferenceDetailsTO;
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
	@Override
	public int compareTo(EBRatePreferenceTO obj) {
		int result = 0;
		if(!StringUtil.isEmptyInteger(ebRatePrefId) 
				&& !StringUtil.isEmptyInteger(obj.getEbRatePrefId())){
			result = this.ebRatePrefId.compareTo(obj.ebRatePrefId);
		} 
		return result;
	}

	
	

}
