package com.ff.coloading;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class FuelRateEntryTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer cityId;
	private String effectiveFrom;
	private Double petrol;
	private Double diesel;
	private Double cng;
	private Double lpg;
	private String renewFlag;
	private String storeStatus;
	private Boolean isRenewalAllow = true;
	
	public FuelRateEntryTO() {
	}

	
	public Boolean getIsRenewalAllow() {
		return isRenewalAllow;
	}


	public void setIsRenewalAllow(Boolean isRenewalAllow) {
		this.isRenewalAllow = isRenewalAllow;
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

	public String getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Double getPetrol() {
		return this.petrol;
	}

	public void setPetrol(Double petrol) {
		this.petrol = petrol;
	}

	public Double getDiesel() {
		return this.diesel;
	}

	public void setDiesel(Double diesel) {
		this.diesel = diesel;
	}

	public Double getCng() {
		return this.cng;
	}

	public void setCng(Double cng) {
		this.cng = cng;
	}

	public Double getLpg() {
		return this.lpg;
	}

	public void setLpg(Double lpg) {
		this.lpg = lpg;
	}

	public String getRenewFlag() {
		return renewFlag;
	}

	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	
}
