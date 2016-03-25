package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author Pravin Meher
 */

public class BcunCashRateConfigAdditionalChargesDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	
	/** The rateCashAdditionalChrgId. */
	private Integer rateCashAdditionalChrgId;
	
	/** The componentValue. */
	private Double componentValue;
	
	/** The componentCode. */
	private String componentCode;
	
	/** The cashRateHeaderProductDO. */
	private Integer cashRateHeaderDO;
	
	/** The priorityInd. i.e. Y= Priority, N= Non-Priority */
	private String priorityInd;

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

	/**
	 * @return the cashRateHeaderDO
	 */
	public Integer getCashRateHeaderDO() {
		return cashRateHeaderDO;
	}

	/**
	 * @param cashRateHeaderDO the cashRateHeaderDO to set
	 */
	public void setCashRateHeaderDO(Integer cashRateHeaderDO) {
		this.cashRateHeaderDO = cashRateHeaderDO;
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
	
	
}
