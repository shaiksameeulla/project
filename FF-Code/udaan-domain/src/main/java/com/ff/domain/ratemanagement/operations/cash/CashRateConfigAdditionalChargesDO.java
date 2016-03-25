package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author hkansagr
 */

public class CashRateConfigAdditionalChargesDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The rateCashAdditionalChrgId. */
	private Integer rateCashAdditionalChrgId;
	
	/** The componentValue. */
	private Double componentValue;
	
	/** The componentCode. */
	private String componentCode;
	
	/** The cashRateHeaderProductDO. */
	private CashRateConfigHeaderDO cashRateHeaderDO;
	
	/** The priorityInd. i.e. Y= Priority, N= Non-Priority */
	private String priorityInd;

	
	/**
	 * @return the cashRateHeaderProductDO
	 */
	public CashRateConfigHeaderDO getCashRateHeaderDO() {
		return cashRateHeaderDO;
	}
	/**
	 * @param cashRateHeaderProductDO the cashRateHeaderProductDO to set
	 */
	public void setCashRateHeaderDO(
			CashRateConfigHeaderDO cashRateHeaderDO) {
		this.cashRateHeaderDO = cashRateHeaderDO;
	}
	/**
	 * @return the rateCashAdditionalChrgId
	 */
	public Integer getRateCashAdditionalChrgId() {
		return rateCashAdditionalChrgId;
	}
	/**
	 * @param rateCashAdditionalChrgId the rateCashAdditionalChrgId to set
	 */
	public void setRateCashAdditionalChrgId(Integer rateCashAdditionalChrgId) {
		this.rateCashAdditionalChrgId = rateCashAdditionalChrgId;
	}
	/**
	 * @return the componentValue
	 */
	public Double getComponentValue() {
		return componentValue;
	}
	/**
	 * @param componentValue the componentValue to set
	 */
	public void setComponentValue(Double componentValue) {
		this.componentValue = componentValue;
	}
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
	 * @return the componentCode
	 */
	public String getComponentCode() {
		return componentCode;
	}
	/**
	 * @param componentCode the componentCode to set
	 */
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	
}
