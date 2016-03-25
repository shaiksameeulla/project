package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.CodChargeDO;

public class CashCODChargesDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5399427179478542949L;
	private int cashCodChargesId;
	private String considerFixed;
	private CodChargeDO codCharge;
	private String considerHigherFixedOrPercent;
	private double percentileValue;
	private CashRateHeaderProductDO cashRateProductDO;
	private Double fixedChargeValue;
	public int getCashCodChargesId() {
		return cashCodChargesId;
	}
	public void setCashCodChargesId(int cashCodChargesId) {
		this.cashCodChargesId = cashCodChargesId;
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
	public CashRateHeaderProductDO getCashRateProductDO() {
		return cashRateProductDO;
	}
	public void setCashRateProductDO(CashRateHeaderProductDO cashRateProductDO) {
		this.cashRateProductDO = cashRateProductDO;
	}
	public Double getFixedChargeValue() {
		return fixedChargeValue;
	}
	public void setFixedChargeValue(Double fixedChargeValue) {
		this.fixedChargeValue = fixedChargeValue;
	}
	
	
	
}
