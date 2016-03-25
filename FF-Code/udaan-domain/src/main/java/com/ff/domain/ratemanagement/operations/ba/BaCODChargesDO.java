package com.ff.domain.ratemanagement.operations.ba;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;

public class BaCODChargesDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3540551778075157291L;
	private int baCodChargesId;
	private String considerFixed;
	private CodChargeDO codCharge;
	private String considerHigherFixedOrPercent;
	private double percentileValue;
	private BaRateProductDO baRateProductDO;
	private Double fixedChargeValue;
	
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
	public CodChargeDO getCodCharge() {
		return codCharge;
	}
	public void setCodCharge(CodChargeDO codCharge) {
		this.codCharge = codCharge;
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
	public BaRateProductDO getBaRateProductDO() {
		return baRateProductDO;
	}
	public void setBaRateProductDO(BaRateProductDO baRateProductDO) {
		this.baRateProductDO = baRateProductDO;
	}
	public Double getFixedChargeValue() {
		return fixedChargeValue;
	}
	public void setFixedChargeValue(Double fixedChargeValue) {
		this.fixedChargeValue = fixedChargeValue;
	}

	
}
