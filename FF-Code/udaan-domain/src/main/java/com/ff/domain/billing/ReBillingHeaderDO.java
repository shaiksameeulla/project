package com.ff.domain.billing;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

public class ReBillingHeaderDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759616937964869939L;
	
	private Integer reBillingId;
	private String reBillingNumber;
	private Integer region;
	private Integer city;
	private Integer office;
	private Integer customer;
	private Date startDate;
	private Date endDate;
	private String calculated="N";
	
	public Integer getReBillingId() {
		return reBillingId;
	}
	public void setReBillingId(Integer reBillingId) {
		this.reBillingId = reBillingId;
	}
	public String getReBillingNumber() {
		return reBillingNumber;
	}
	public void setReBillingNumber(String reBillingNumber) {
		this.reBillingNumber = reBillingNumber;
	}
	public Integer getRegion() {
		return region;
	}
	public void setRegion(Integer region) {
		this.region = region;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getOffice() {
		return office;
	}
	public void setOffice(Integer office) {
		this.office = office;
	}
	public Integer getCustomer() {
		return customer;
	}
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCalculated() {
		return calculated;
	}
	public void setCalculated(String calculated) {
		this.calculated = calculated;
	}
	
	
}
