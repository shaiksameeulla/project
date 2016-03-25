/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ratecalculation;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.operations.ba.BaRateConfigHeaderDO;

/**
 * @author prmeher
 *
 */
public class BARateConfigurationFixedChargesConfigDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2564238737526847959L;
	private Integer baFixedChargesConfigId;
	/*private BaRateConfigProductHeaderDO baProductHeader;*/
	private String insuredBy;
	private String octroiBourneBy;
	private BaRateConfigHeaderDO baRateConfigHeaderDO;
	private String priorityIndicator;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;
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
	
	public BaRateConfigHeaderDO getBaRateConfigHeaderDO() {
		return baRateConfigHeaderDO;
	}
	public void setBaRateConfigHeaderDO(BaRateConfigHeaderDO baRateConfigHeaderDO) {
		this.baRateConfigHeaderDO = baRateConfigHeaderDO;
	}
	public String getPriorityIndicator() {
		return priorityIndicator;
	}
	public void setPriorityIndicator(String priorityIndicator) {
		this.priorityIndicator = priorityIndicator;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	

}
