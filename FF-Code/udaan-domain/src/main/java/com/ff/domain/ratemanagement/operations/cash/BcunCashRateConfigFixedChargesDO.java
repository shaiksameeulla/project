package com.ff.domain.ratemanagement.operations.cash;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 */

public class BcunCashRateConfigFixedChargesDO extends CGFactDO {
	
	private static final long serialVersionUID = 1L;
	

	/** The cashFixedChargesConfigId. */
	private Integer cashFixedChargesConfigId;
	
	/** The octroiBourneBy. i.e. CO= Consignor, CE= Consignee*/
	private String octroiBourneBy;
	
	/** The insuredBy. i.e. F= FFCL, C= Customer */
	private String insuredBy;
	
	/** The productMapId. */
	//private Integer productMapId;
	/** The cashRateHeaderProductDO. */
	private Integer cashRateHeaderDO;
	
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
