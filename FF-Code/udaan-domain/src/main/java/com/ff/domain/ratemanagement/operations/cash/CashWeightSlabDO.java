package com.ff.domain.ratemanagement.operations.cash;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.ratemanagement.masters.WeightSlabDO;

public class CashWeightSlabDO extends CGFactDO {
	private Integer weightSlabId;
	private Integer productMapId;
	private WeightSlabDO startWt;
	private WeightSlabDO endWt;
	private int slabOrder;
	private String additional;
	private Date fromDate;
	private Date toDate;

	public Integer getWeightSlabId() {
		return weightSlabId;
	}
	public void setWeightSlabId(Integer weightSlabId) {
		this.weightSlabId = weightSlabId;
	}
	public Integer getProductMapId() {
		return productMapId;
	}
	public void setProductMapId(Integer productMapId) {
		this.productMapId = productMapId;
	}
	public WeightSlabDO getStartWt() {
		return startWt;
	}
	public void setStartWt(WeightSlabDO startWt) {
		this.startWt = startWt;
	}
	public WeightSlabDO getEndWt() {
		return endWt;
	}
	public void setEndWt(WeightSlabDO endWt) {
		this.endWt = endWt;
	}
	public int getSlabOrder() {
		return slabOrder;
	}
	public void setSlabOrder(int slabOrder) {
		this.slabOrder = slabOrder;
	}
	public String getAdditional() {
		return additional;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
