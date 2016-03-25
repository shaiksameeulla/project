package com.ff.report;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author khassan
 *
 */
public class BookingReportTO extends CGBaseTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4071478423457624800L;
	
	 String regionTo;
	 String station;
	 String branch;
	 String destRegion;
	 String destStation;
	 String fromDate;
	 String toDate;
	 String client;
	 String product;
	 
	 // Added for Load Confirmation
	 private String serviceByType;
	 private String transportMode;
	 private List<LabelValueBean> transportModeList = null;
	 
	/**
	 * @return the serviceByType
	 */
	public String getServiceByType() {
		return serviceByType;
	}
	/**
	 * @param serviceByType the serviceByType to set
	 */
	public void setServiceByType(String serviceByType) {
		this.serviceByType = serviceByType;
	}
	/**
	 * @return the transportMode
	 */
	public String getTransportMode() {
		return transportMode;
	}
	/**
	 * @param transportMode the transportMode to set
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	/**
	 * @return the transportModeList
	 */
	public List<LabelValueBean> getTransportModeList() {
		return transportModeList;
	}
	/**
	 * @param transportModeList the transportModeList to set
	 */
	public void setTransportModeList(List<LabelValueBean> transportModeList) {
		this.transportModeList = transportModeList;
	}
	public String getRegionTo() {
		return regionTo;
	}
	public void setRegionTo(String regionTo) {
		this.regionTo = regionTo;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDestRegion() {
		return destRegion;
	}
	public void setDestRegion(String destRegion) {
		this.destRegion = destRegion;
	}
	public String getDestStation() {
		return destStation;
	}
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

}
