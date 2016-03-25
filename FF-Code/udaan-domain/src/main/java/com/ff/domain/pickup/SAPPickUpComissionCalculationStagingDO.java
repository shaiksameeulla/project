package com.ff.domain.pickup;

import java.util.Date;

public class SAPPickUpComissionCalculationStagingDO {

	private int sapPickUpCommissionId;
	private String empCode;
	private String product_grup;
	private int pickupCount;
	private double netValue;
	private Date calculatedFor;
	private int createdBy;
	private String dtSapOutbound;
	private Date sapTimeStamp;
	private String exception;
	
	
	
	/**
	 * @return the sapPickUpCommissionId
	 */
	public int getSapPickUpCommissionId() {
		return sapPickUpCommissionId;
	}
	/**
	 * @param sapPickUpCommissionId the sapPickUpCommissionId to set
	 */
	public void setSapPickUpCommissionId(int sapPickUpCommissionId) {
		this.sapPickUpCommissionId = sapPickUpCommissionId;
	}
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	/**
	 * @return the product_grup
	 */
	public String getProduct_grup() {
		return product_grup;
	}
	/**
	 * @param product_grup the product_grup to set
	 */
	public void setProduct_grup(String product_grup) {
		this.product_grup = product_grup;
	}
	/**
	 * @return the pickupCount
	 */
	public int getPickupCount() {
		return pickupCount;
	}
	/**
	 * @param pickupCount the pickupCount to set
	 */
	public void setPickupCount(int pickupCount) {
		this.pickupCount = pickupCount;
	}
	/**
	 * @return the netValue
	 */
	public double getNetValue() {
		return netValue;
	}
	/**
	 * @param netValue the netValue to set
	 */
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}
	/**
	 * @return the calculatedFor
	 */
	public Date getCalculatedFor() {
		return calculatedFor;
	}
	/**
	 * @param calculatedFor the calculatedFor to set
	 */
	public void setCalculatedFor(Date calculatedFor) {
		this.calculatedFor = calculatedFor;
	}
	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the dtSapOutbound
	 */
	public String getDtSapOutbound() {
		return dtSapOutbound;
	}
	/**
	 * @param dtSapOutbound the dtSapOutbound to set
	 */
	public void setDtSapOutbound(String dtSapOutbound) {
		this.dtSapOutbound = dtSapOutbound;
	}
	/**
	 * @return the sapTimeStamp
	 */
	public Date getSapTimeStamp() {
		return sapTimeStamp;
	}
	/**
	 * @param sapTimeStamp the sapTimeStamp to set
	 */
	public void setSapTimeStamp(Date sapTimeStamp) {
		this.sapTimeStamp = sapTimeStamp;
	}
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	
	
	
	
}
