package com.ff.to.ratemanagement.operations.ratequotation;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.to.rate.RateComponentTO;

public class ConsignmentRateTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer consignmentRateId;
	private Double calculatedValue;
	private RateComponentTO rateComponent;
	private ConsignmentTO consignmentNo;
	private String billed;
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
	 * @return the rateComponent
	 */
	public RateComponentTO getRateComponent() {
		return rateComponent;
	}
	/**
	 * @param rateComponent the rateComponent to set
	 */
	public void setRateComponent(RateComponentTO rateComponent) {
		this.rateComponent = rateComponent;
	}
	/**
	 * @return the consignmentNo
	 */
	public ConsignmentTO getConsignmentNo() {
		return consignmentNo;
	}
	/**
	 * @param consignmentNo the consignmentNo to set
	 */
	public void setConsignmentNo(ConsignmentTO consignmentNo) {
		this.consignmentNo = consignmentNo;
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
