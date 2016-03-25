/**
 * 
 */
package com.ff.report;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author shashsax
 * 
 */
public class RateRevisionAnalysisReportTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4054729598717264776L;
	String region;
	String station;
	String month1;
	String month2;
	String sector;
	String fuelPercent;
	String customer;
	String product;
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return the month1
	 */
	public String getMonth1() {
		return month1;
	}
	/**
	 * @param month1 the month1 to set
	 */
	public void setMonth1(String month1) {
		this.month1 = month1;
	}
	/**
	 * @return the month2
	 */
	public String getMonth2() {
		return month2;
	}
	/**
	 * @param month2 the month2 to set
	 */
	public void setMonth2(String month2) {
		this.month2 = month2;
	}
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * @return the fuelPercent
	 */
	public String getFuelPercent() {
		return fuelPercent;
	}
	/**
	 * @param fuelPercent the fuelPercent to set
	 */
	public void setFuelPercent(String fuelPercent) {
		this.fuelPercent = fuelPercent;
	}
	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
}
