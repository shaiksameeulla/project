package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class InsuredByDO extends CGMasterDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3509451782907010334L;
	private Integer insuredById;
	private String insuredByCode = "NA";
	private String insuredByDesc;
	private Double percentile;

	public Integer getInsuredById() {
		return insuredById;
	}

	public void setInsuredById(Integer insuredById) {
		this.insuredById = insuredById;
	}

	public String getInsuredByCode() {
		return insuredByCode;
	}

	public void setInsuredByCode(String insuredByCode) {
		this.insuredByCode = insuredByCode;
	}

	public String getInsuredByDesc() {
		return insuredByDesc;
	}

	public void setInsuredByDesc(String insuredByDesc) {
		this.insuredByDesc = insuredByDesc;
	}

	public Double getPercentile() {
		return percentile;
	}

	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}

}
