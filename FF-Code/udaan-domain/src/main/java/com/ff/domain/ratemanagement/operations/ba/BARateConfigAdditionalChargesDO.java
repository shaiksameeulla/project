package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class BARateConfigAdditionalChargesDO extends CGFactDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -815207970429462197L;
	private Integer additionalChrgId;
	private Double componentValue;
	private String componentCode;
	/*private BaRateConfigProductHeaderDO baRateProductDO;*/
	/*private Integer baRateConfigHeaderId;*/
	private BaRateConfigHeaderDO baRateConfigHeaderDO;
	private String priorityIndicator;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;
	
	/**
	 * @return the additionalChrgId
	 */
	public Integer getAdditionalChrgId() {
		return additionalChrgId;
	}
	/**
	 * @param additionalChrgId the additionalChrgId to set
	 */
	public void setAdditionalChrgId(Integer additionalChrgId) {
		this.additionalChrgId = additionalChrgId;
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
