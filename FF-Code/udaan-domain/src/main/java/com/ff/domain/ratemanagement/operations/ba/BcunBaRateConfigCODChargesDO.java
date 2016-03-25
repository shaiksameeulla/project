/**
 * 
 */
package com.ff.domain.ratemanagement.operations.ba;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.BcunCodChargeDO;

/**
 * @author abarudwa
 *
 */
public class BcunBaRateConfigCODChargesDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int baCodChargesId;
	private String considerFixed;
	private BcunCodChargeDO bcunCodChargeDO;
	private String considerHigherFixedOrPercent;
	private double percentileValue;
	private Double fixedChargeValue;
	private Integer baRateConfigHeaderId;
	private String priorityIndicator;
	
	private Date createdDate;
	private Date updatedDate;
	private Integer createdBy;
	private Integer updatedBy;
	
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
	public BcunCodChargeDO getBcunCodChargeDO() {
		return bcunCodChargeDO;
	}
	public void setBcunCodChargeDO(BcunCodChargeDO bcunCodChargeDO) {
		this.bcunCodChargeDO = bcunCodChargeDO;
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
	public Integer getBaRateConfigHeaderId() {
		return baRateConfigHeaderId;
	}
	public void setBaRateConfigHeaderId(Integer baRateConfigHeaderId) {
		this.baRateConfigHeaderId = baRateConfigHeaderId;
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
