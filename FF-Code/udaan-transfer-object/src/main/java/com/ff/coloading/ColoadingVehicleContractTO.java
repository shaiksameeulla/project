package com.ff.coloading;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class ColoadingVehicleContractTO extends CGBaseTO {

	private static final long serialVersionUID = -1625486300796456617L;

	private String vehicleNo;
	private Integer vendorId;
	private String vehicleType;
	private Integer capacity;
	private String effectiveFrom;
	private String rateType;
	private String dutyHours;
	private Double average;
	private Double rent;
	private Integer freeKm;
	private Integer perKmRate;
	private Integer perHourRate;
	private Integer noOfDays;
	private Integer others;
	private String fuelType;
	private Boolean gpsEnabled;
	private String renewFlag;
	private String storeStatus;
	private Boolean isRenewalAllow = true;
	
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getDutyHours() {
		return dutyHours;
	}
	public void setDutyHours(String dutyHours) {
		this.dutyHours = dutyHours;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public Double getRent() {
		return rent;
	}
	public void setRent(Double rent) {
		this.rent = rent;
	}
	public Integer getFreeKm() {
		return freeKm;
	}
	public void setFreeKm(Integer freeKm) {
		this.freeKm = freeKm;
	}
	public Integer getPerKmRate() {
		return perKmRate;
	}
	public void setPerKmRate(Integer perKmRate) {
		this.perKmRate = perKmRate;
	}
	public Integer getPerHourRate() {
		return perHourRate;
	}
	public void setPerHourRate(Integer perHourRate) {
		this.perHourRate = perHourRate;
	}
	public Integer getOthers() {
		return others;
	}
	public void setOthers(Integer others) {
		this.others = others;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public Boolean getGpsEnabled() {
		return gpsEnabled;
	}
	public void setGpsEnabled(Boolean gpsEnabled) {
		this.gpsEnabled = gpsEnabled;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getRenewFlag() {
		return renewFlag;
	}
	public void setRenewFlag(String renewFlag) {
		this.renewFlag = renewFlag;
	}
	public String getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(String effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public Boolean getIsRenewalAllow() {
		return isRenewalAllow;
	}
	public void setIsRenewalAllow(Boolean isRenewalAllow) {
		this.isRenewalAllow = isRenewalAllow;
	}
	public String getStoreStatus() {
		return storeStatus;
	}
	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}
	
	
	
}
