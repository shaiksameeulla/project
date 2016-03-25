package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;

public class BaRateCalculationCODChargesDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3540551778075157291L;
	private int baCodChargesId;
	private String considerFixed;
	private CodChargeDO codCharge;
	private String considerHigherFixedOrPercent;
	private double percentileValue;
	private Integer baRateHeader;
	private Double fixedChargeValue;
	private String priorityInd;
	
	
	/**
	 * @return the priorityInd
	 */
	public String getPriorityInd() {
		return priorityInd;
	}
	/**
	 * @param priorityInd the priorityInd to set
	 */
	public void setPriorityInd(String priorityInd) {
		this.priorityInd = priorityInd;
	}
	/**
	 * @return the baCodChargesId
	 */
	public int getBaCodChargesId() {
		return baCodChargesId;
	}
	/**
	 * @param baCodChargesId the baCodChargesId to set
	 */
	public void setBaCodChargesId(int baCodChargesId) {
		this.baCodChargesId = baCodChargesId;
	}
	/**
	 * @return the considerFixed
	 */
	public String getConsiderFixed() {
		return considerFixed;
	}
	/**
	 * @param considerFixed the considerFixed to set
	 */
	public void setConsiderFixed(String considerFixed) {
		this.considerFixed = considerFixed;
	}
	/**
	 * @return the codCharge
	 */
	public CodChargeDO getCodCharge() {
		return codCharge;
	}
	/**
	 * @param codCharge the codCharge to set
	 */
	public void setCodCharge(CodChargeDO codCharge) {
		this.codCharge = codCharge;
	}
	/**
	 * @return the considerHigherFixedOrPercent
	 */
	public String getConsiderHigherFixedOrPercent() {
		return considerHigherFixedOrPercent;
	}
	/**
	 * @param considerHigherFixedOrPercent the considerHigherFixedOrPercent to set
	 */
	public void setConsiderHigherFixedOrPercent(String considerHigherFixedOrPercent) {
		this.considerHigherFixedOrPercent = considerHigherFixedOrPercent;
	}
	/**
	 * @return the percentileValue
	 */
	public double getPercentileValue() {
		return percentileValue;
	}
	/**
	 * @param percentileValue the percentileValue to set
	 */
	public void setPercentileValue(double percentileValue) {
		this.percentileValue = percentileValue;
	}
	/**
	 * @return the baRateHeader
	 */
	public Integer getBaRateHeader() {
		return baRateHeader;
	}
	/**
	 * @param baRateHeader the baRateHeader to set
	 */
	public void setBaRateHeader(Integer baRateHeader) {
		this.baRateHeader = baRateHeader;
	}
	/**
	 * @return the fixedChargeValue
	 */
	public Double getFixedChargeValue() {
		return fixedChargeValue;
	}
	/**
	 * @param fixedChargeValue the fixedChargeValue to set
	 */
	public void setFixedChargeValue(Double fixedChargeValue) {
		this.fixedChargeValue = fixedChargeValue;
	}
	
	
}
