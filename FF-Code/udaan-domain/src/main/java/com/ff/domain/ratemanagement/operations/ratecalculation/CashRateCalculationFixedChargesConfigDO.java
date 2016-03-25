/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ratecalculation;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 *
 */
public class CashRateCalculationFixedChargesConfigDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2564238737526847959L;
	private Integer cashFixedChargesConfigId;
	private Integer cashHeader;
	private String insuredBy;
	private String octroiBourneBy;
	private String priorityInd;
	/**
	 * @return the cashFixedChargesConfigId
	 */
	public Integer getCashFixedChargesConfigId() {
		return cashFixedChargesConfigId;
	}
	/**
	 * @param cashFixedChargesConfigId the cashFixedChargesConfigId to set
	 */
	public void setCashFixedChargesConfigId(Integer cashFixedChargesConfigId) {
		this.cashFixedChargesConfigId = cashFixedChargesConfigId;
	}
	/**
	 * @return the cashProductHeader
	 *//*
	public Integer getCashProductHeader() {
		return cashProductHeader;
	}
	*//**
	 * @param cashProductHeader the cashProductHeader to set
	 *//*
	public void setCashProductHeader(Integer cashProductHeader) {
		this.cashProductHeader = cashProductHeader;
	}*/
	/**
	 * @return the insuredBy
	 */
	public String getInsuredBy() {
		return insuredBy;
	}
	/**
	 * @param insuredBy the insuredBy to set
	 */
	public void setInsuredBy(String insuredBy) {
		this.insuredBy = insuredBy;
	}
	/**
	 * @return the octroiBourneBy
	 */
	public String getOctroiBourneBy() {
		return octroiBourneBy;
	}
	/**
	 * @param octroiBourneBy the octroiBourneBy to set
	 */
	public void setOctroiBourneBy(String octroiBourneBy) {
		this.octroiBourneBy = octroiBourneBy;
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
	public Integer getCashHeader() {
		return cashHeader;
	}
	public void setCashHeader(Integer cashHeader) {
		this.cashHeader = cashHeader;
	}
	
	
}
