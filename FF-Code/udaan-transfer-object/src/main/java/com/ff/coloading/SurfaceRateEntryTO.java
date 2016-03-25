package com.ff.coloading;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class SurfaceRateEntryTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vendorId;
	private String fromDate;
	private Integer toWeight;
	private Double additionalPerKg;
	private Double rate;
	
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public Integer getToWeight() {
		return toWeight;
	}
	public void setToWeight(Integer toWeight) {
		this.toWeight = toWeight;
	}
	public Double getAdditionalPerKg() {
		return additionalPerKg;
	}
	public void setAdditionalPerKg(Double additionalPerKg) {
		this.additionalPerKg = additionalPerKg;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}
