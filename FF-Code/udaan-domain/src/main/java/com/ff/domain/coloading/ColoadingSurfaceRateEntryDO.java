package com.ff.domain.coloading;

import java.util.Date;

public class ColoadingSurfaceRateEntryDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer vendorId;
	private Date fromDate;
	private Integer toWeight;
	private Double additionalPerKg;
	private Double rate;
	
	public ColoadingSurfaceRateEntryDO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
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
