package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.masters.RateIndustryCategoryTO;
/**
 * @author preegupt
 *
 */
public class RateTaxComponentApplicableTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4673379363806526869L;
	
	private Integer rateTaxComponentAplicableId;
	private RateTaxComponentTO taxComponent;
	private RateIndustryCategoryTO industryType;
	/**
	 * @return the rateTaxComponentAplicableId
	 */
	public Integer getRateTaxComponentAplicableId() {
		return rateTaxComponentAplicableId;
	}
	/**
	 * @param rateTaxComponentAplicableId the rateTaxComponentAplicableId to set
	 */
	public void setRateTaxComponentAplicableId(Integer rateTaxComponentAplicableId) {
		this.rateTaxComponentAplicableId = rateTaxComponentAplicableId;
	}
	/**
	 * @return the taxComponent
	 */
	public RateTaxComponentTO getTaxComponent() {
		return taxComponent;
	}
	/**
	 * @param taxComponent the taxComponent to set
	 */
	public void setTaxComponent(RateTaxComponentTO taxComponent) {
		this.taxComponent = taxComponent;
	}
	/**
	 * @return the industryType
	 */
	public RateIndustryCategoryTO getIndustryType() {
		return industryType;
	}
	/**
	 * @param industryType the industryType to set
	 */
	public void setIndustryType(RateIndustryCategoryTO industryType) {
		this.industryType = industryType;
	}

}
