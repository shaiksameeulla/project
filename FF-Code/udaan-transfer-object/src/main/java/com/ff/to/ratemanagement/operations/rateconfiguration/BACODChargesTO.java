/**
 * 
 */
package com.ff.to.ratemanagement.operations.rateconfiguration;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.to.ratemanagement.operations.ratequotation.CodChargeTO;

/**
 * @author abarudwa
 *
 */
public class BACODChargesTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int baCodChargesId;
	private String considerFixed;
	private CodChargeTO codChargeTO;
	private String considerHigherFixedOrPercent;
	private Double percentileValue;
	private BaRateProductTO baRateProductTO;
	private Double fixedChargeValue;
	private String priorityIndicator;
	public int getBaCodChargesId() {
		return baCodChargesId;
	}
	public void setBaCodChargesId(int baCodChargesId) {
		this.baCodChargesId = baCodChargesId;
	}
	public String getConsiderFixed() {
		return considerFixed;
	}
	public void setConsiderFixed(String considerFixed) {
		this.considerFixed = considerFixed;
	}
	public String getConsiderHigherFixedOrPercent() {
		return considerHigherFixedOrPercent;
	}
	public void setConsiderHigherFixedOrPercent(String considerHigherFixedOrPercent) {
		this.considerHigherFixedOrPercent = considerHigherFixedOrPercent;
	}
	public double getPercentileValue() {
		return percentileValue;
	}
	public void setPercentileValue(double percentileValue) {
		this.percentileValue = percentileValue;
	}
	public Double getFixedChargeValue() {
		return fixedChargeValue;
	}
	public void setFixedChargeValue(Double fixedChargeValue) {
		this.fixedChargeValue = fixedChargeValue;
	}
	public CodChargeTO getCodChargeTO() {
		return codChargeTO;
	}
	public void setCodChargeTO(CodChargeTO codChargeTO) {
		this.codChargeTO = codChargeTO;
	}
	public BaRateProductTO getBaRateProductTO() {
		return baRateProductTO;
	}
	public void setBaRateProductTO(BaRateProductTO baRateProductTO) {
		this.baRateProductTO = baRateProductTO;
	}
	public String getPriorityIndicator() {
		return priorityIndicator;
	}
	public void setPriorityIndicator(String priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}
	
	

}
