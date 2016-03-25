package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.StateTO;
import com.ff.to.rate.RateComponentTO;
/**
 * @author preegupt
 *
 */
public class RateTaxComponentTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6847091944468787689L;
	private Integer taxComponentId;
	private Double taxPercentile;
	private String activeStatus;
	private String effectiveFrom;
	private String effectiveTo;
	private RateComponentTO rateComponentId;
	private String taxGroup;
	private StateTO state;
	/**
	 * @return the taxComponentId
	 */
	public Integer getTaxComponentId() {
		return taxComponentId;
	}
	/**
	 * @param taxComponentId the taxComponentId to set
	 */
	public void setTaxComponentId(Integer taxComponentId) {
		this.taxComponentId = taxComponentId;
	}
	/**
	 * @return the taxPercentile
	 */
	public Double getTaxPercentile() {
		return taxPercentile;
	}
	/**
	 * @param taxPercentile the taxPercentile to set
	 */
	public void setTaxPercentile(Double taxPercentile) {
		this.taxPercentile = taxPercentile;
	}
	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	/**
	 * @return the effectiveFrom
	 */
	public String getEffectiveFrom() {
		return effectiveFrom;
	}
	/**
	 * @param effectiveFrom the effectiveFrom to set
	 */
	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	/**
	 * @return the effectiveTo
	 */
	public String getEffectiveTo() {
		return effectiveTo;
	}
	/**
	 * @param effectiveTo the effectiveTo to set
	 */
	public void setEffectiveTo(String effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	/**
	 * @return the rateComponentId
	 */
	public RateComponentTO getRateComponentId() {
		return rateComponentId;
	}
	/**
	 * @param rateComponentId the rateComponentId to set
	 */
	public void setRateComponentId(RateComponentTO rateComponentId) {
		this.rateComponentId = rateComponentId;
	}
	/**
	 * @return the taxGroup
	 */
	public String getTaxGroup() {
		return taxGroup;
	}
	/**
	 * @param taxGroup the taxGroup to set
	 */
	public void setTaxGroup(String taxGroup) {
		this.taxGroup = taxGroup;
	}
	/**
	 * @return the state
	 */
	public StateTO getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(StateTO state) {
		this.state = state;
	}
	
	
}
