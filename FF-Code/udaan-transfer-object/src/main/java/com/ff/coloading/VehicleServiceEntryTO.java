package com.ff.coloading;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class VehicleServiceEntryTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String date;
	private String vehicalNumber;
	private Integer dutyHours;
	private Integer ot;
	private Integer openingKm;
	private Integer closingKm;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getVehicalNumber() {
		return vehicalNumber;
	}
	public void setVehicalNumber(String vehicalNumber) {
		this.vehicalNumber = vehicalNumber;
	}
	public Integer getDutyHours() {
		return dutyHours;
	}
	public void setDutyHours(Integer dutyHours) {
		this.dutyHours = dutyHours;
	}
	public Integer getOt() {
		return ot;
	}
	public void setOt(Integer ot) {
		this.ot = ot;
	}
	public Integer getOpeningKm() {
		return openingKm;
	}
	public void setOpeningKm(Integer openingKm) {
		this.openingKm = openingKm;
	}
	public Integer getClosingKm() {
		return closingKm;
	}
	public void setClosingKm(Integer closingKm) {
		this.closingKm = closingKm;
	}
	
	
}
