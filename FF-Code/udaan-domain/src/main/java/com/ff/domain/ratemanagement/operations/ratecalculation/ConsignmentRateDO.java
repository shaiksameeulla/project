package com.ff.domain.ratemanagement.operations.ratecalculation;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;

public class ConsignmentRateDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Integer consignmentRateId;
	private Double calculatedValue;
	private Double rtoCalculatedValue;
	private RateComponentDO rateComponentDO;
	@JsonBackReference
	private ConsignmentDO consignmentDO;
	private Double actualValue;
	private String billed="N";
	
	
	
	public Double getActualValue() {
		return actualValue;
	}
	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}
	/**
	 * @return the rtoCalculatedValue
	 */
	public Double getRtoCalculatedValue() {
		return rtoCalculatedValue;
	}
	/**
	 * @param rtoCalculatedValue the rtoCalculatedValue to set
	 */
	public void setRtoCalculatedValue(Double rtoCalculatedValue) {
		this.rtoCalculatedValue = rtoCalculatedValue;
	}
	
	/**
	 * @return the consignmentRateId
	 */
	public Integer getConsignmentRateId() {
		return consignmentRateId;
	}
	/**
	 * @param consignmentRateId the consignmentRateId to set
	 */
	public void setConsignmentRateId(Integer consignmentRateId) {
		this.consignmentRateId = consignmentRateId;
	}
	/**
	 * @return the calculatedValue
	 */
	public Double getCalculatedValue() {
		return calculatedValue;
	}
	/**
	 * @param calculatedValue the calculatedValue to set
	 */
	public void setCalculatedValue(Double calculatedValue) {
		this.calculatedValue = calculatedValue;
	}
	/**
	 * @return the rateComponentDO
	 */
	public RateComponentDO getRateComponentDO() {
		return rateComponentDO;
	}
	/**
	 * @param rateComponentDO the rateComponentDO to set
	 */
	public void setRateComponentDO(RateComponentDO rateComponentDO) {
		this.rateComponentDO = rateComponentDO;
	}
	/**
	 * @return the consignmentDO
	 */
	public ConsignmentDO getConsignmentDO() {
		return consignmentDO;
	}
	/**
	 * @param consignmentDO the consignmentDO to set
	 */
	public void setConsignmentDO(ConsignmentDO consignmentDO) {
		this.consignmentDO = consignmentDO;
	}
	/**
	 * @return the billed
	 */
	public String getBilled() {
		return billed;
	}
	/**
	 * @param billed the billed to set
	 */
	public void setBilled(String billed) {
		this.billed = billed;
	}
	
	
	
}
