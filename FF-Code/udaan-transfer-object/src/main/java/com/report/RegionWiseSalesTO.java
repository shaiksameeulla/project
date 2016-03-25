package com.report;

import java.io.Serializable;
import java.math.BigInteger;

public class RegionWiseSalesTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String month;
	
	private BigInteger totalPickup;
	
	private Double totalWithoutTax;
	
	private Double pickupPercent;
	
	private Double revenueWithoutTax;
	
	private Double revenuePercent;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigInteger getTotalPickup() {
		return totalPickup;
	}

	public void setTotalPickup(BigInteger totalPickup) {
		this.totalPickup = totalPickup;
	}

	public Double getTotalWithoutTax() {
		return totalWithoutTax;
	}

	public void setTotalWithoutTax(Double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}

	public Double getPickupPercent() {
		return pickupPercent;
	}

	public void setPickupPercent(Double pickupPercent) {
		this.pickupPercent = pickupPercent;
	}

	public Double getRevenueWithoutTax() {
		return revenueWithoutTax;
	}

	public void setRevenueWithoutTax(Double revenueWithoutTax) {
		this.revenueWithoutTax = revenueWithoutTax;
	}

	public Double getRevenuePercent() {
		return revenuePercent;
	}

	public void setRevenuePercent(Double revenuePercent) {
		this.revenuePercent = revenuePercent;
	}
	
	
	
}
