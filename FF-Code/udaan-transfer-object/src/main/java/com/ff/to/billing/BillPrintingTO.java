package com.ff.to.billing;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.geography.RegionTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

public class BillPrintingTO extends CGBaseTO {

	Integer regionTo;
	Integer productTo;
	String startDate;
	String endDate;
	String stationeryList;
	/**
	 * @return the regionTo
	 */
	public Integer getRegionTo() {
		return regionTo;
	}
	/**
	 * @param regionTo the regionTo to set
	 */
	public void setRegionTo(Integer regionTo) {
		this.regionTo = regionTo;
	}
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the stationeryList
	 */
	public String getStationeryList() {
		return stationeryList;
	}
	/**
	 * @param stationeryList the stationeryList to set
	 */
	public void setStationeryList(String stationeryList) {
		this.stationeryList = stationeryList;
	}
	/**
	 * @return the productTo
	 */
	public Integer getProductTo() {
		return productTo;
	}
	/**
	 * @param productTo the productTo to set
	 */
	public void setProductTo(Integer productTo) {
		this.productTo = productTo;
	}
	
	
}
