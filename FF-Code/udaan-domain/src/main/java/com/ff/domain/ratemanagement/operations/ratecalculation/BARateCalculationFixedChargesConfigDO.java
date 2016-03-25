/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ratecalculation;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author prmeher
 *
 */
public class BARateCalculationFixedChargesConfigDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2564238737526847959L;
	private Integer baFixedChargesConfigId;
	private Integer baRateHeader;
	private String insuredBy;
	private String octroiBourneBy;
	private String priorityInd;
	/**
	 * @return the baFixedChargesConfigId
	 */
	public Integer getBaFixedChargesConfigId() {
		return baFixedChargesConfigId;
	}
	/**
	 * @param baFixedChargesConfigId the baFixedChargesConfigId to set
	 */
	public void setBaFixedChargesConfigId(Integer baFixedChargesConfigId) {
		this.baFixedChargesConfigId = baFixedChargesConfigId;
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

}
