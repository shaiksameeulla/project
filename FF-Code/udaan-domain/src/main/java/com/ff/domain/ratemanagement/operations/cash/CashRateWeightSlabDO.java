package com.ff.domain.ratemanagement.operations.cash;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

/**
 * @author hkansagr
 */

public class CashRateWeightSlabDO extends CGFactDO {

	private static final long serialVersionUID = 1L;
	
	/** The weightSlabId. */
	private Integer weightSlabId;
	
	/** The productMapId. */
	private Integer productMapId;
	
	/** The startWt. */
	private WeightSlabDO startWt;
	
	/** The endWt. */
	private WeightSlabDO endWt;
	
	/** The slabOrder. */
	private Integer slabOrder;
	
	/** The additional. */
	private String additional;
	
	/** The fromDate. */
	private Date fromDate;
	
	/** The toDate. */
	private Date toDate;
	
	
	/**
	 * @return the slabOrder
	 */
	public Integer getSlabOrder() {
		return slabOrder;
	}
	/**
	 * @param slabOrder the slabOrder to set
	 */
	public void setSlabOrder(Integer slabOrder) {
		this.slabOrder = slabOrder;
	}
	/**
	 * @return the weightSlabId
	 */
	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	/**
	 * @param weightSlabId the weightSlabId to set
	 */
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
	}
	/**
	 * @return the productMapId
	 */
	public Integer getProductMapId() {
		return productMapId;
	}
	/**
	 * @param productMapId the productMapId to set
	 */
	public void setProductMapId(Integer productMapId) {
		this.productMapId = productMapId;
	}
	/**
	 * @return the startWt
	 */
	public WeightSlabDO getStartWt() {
		return startWt;
	}
	/**
	 * @param startWt the startWt to set
	 */
	public void setStartWt(WeightSlabDO startWt) {
		this.startWt = startWt;
	}
	/**
	 * @return the endWt
	 */
	public WeightSlabDO getEndWt() {
		return endWt;
	}
	/**
	 * @param endWt the endWt to set
	 */
	public void setEndWt(WeightSlabDO endWt) {
		this.endWt = endWt;
	}
	/**
	 * @return the additional
	 */
	public String getAdditional() {
		return additional;
	}
	/**
	 * @param additional the additional to set
	 */
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
