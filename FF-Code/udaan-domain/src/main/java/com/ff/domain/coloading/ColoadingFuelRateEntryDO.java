package com.ff.domain.coloading;

import java.util.Date;

public class ColoadingFuelRateEntryDO extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer cityId;
	private Date effectiveFrom;
	private Date effectiveTill;
	private double petrol;
	private double diesel;
	private double cng;
	private double lpg;
	
	public ColoadingFuelRateEntryDO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveTill() {
		return effectiveTill;
	}

	public void setEffectiveTill(Date effectiveTill) {
		this.effectiveTill = effectiveTill;
	}

	public double getPetrol() {
		return this.petrol;
	}

	public void setPetrol(double petrol) {
		this.petrol = petrol;
	}

	public double getDiesel() {
		return this.diesel;
	}

	public void setDiesel(double diesel) {
		this.diesel = diesel;
	}

	public double getCng() {
		return this.cng;
	}

	public void setCng(double cng) {
		this.cng = cng;
	}

	public double getLpg() {
		return this.lpg;
	}

	public void setLpg(double lpg) {
		this.lpg = lpg;
	}

}
