package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class InsuredByTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5308204288723965629L;
	private Integer insuredById;
	private String insuredByCode;
	private String insuredByDesc;
	private String policyNo;
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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Double getPercentile() {
		return percentile;
	}

	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}

}
