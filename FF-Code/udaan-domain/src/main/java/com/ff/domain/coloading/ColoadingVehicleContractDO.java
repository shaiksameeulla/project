package com.ff.domain.coloading;


import java.util.Date;

public class ColoadingVehicleContractDO  extends CGBaseColaodingDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String vehicleNo;
	private Integer vendorId;
	private String vehicleType;
	private Integer capacity;
	private Date effectiveFrom;
	private Date effectiveTill;
	private String rateType;
	private String dutyHours;
	private Double average;
	private Double rent;
	private Integer freeKm;
	private Integer perKmRate;
	private Integer perHourRate;
	private String fuelType;
	private Integer noOfDays;
	private Integer others;
	private Boolean gpsEnabled;
	

	public ColoadingVehicleContractDO() {
	}

	public String getVehicleNo() {
		return this.vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Integer getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Date getEffectiveFrom() {
		return this.effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getDutyHours() {
		return this.dutyHours;
	}

	public void setDutyHours(String dutyHours) {
		this.dutyHours = dutyHours;
	}

	public Double getAverage() {
		return this.average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getRent() {
		return this.rent;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public Integer getFreeKm() {
		return this.freeKm;
	}

	public void setFreeKm(Integer freeKm) {
		this.freeKm = freeKm;
	}

	public Integer getPerKmRate() {
		return this.perKmRate;
	}

	public void setPerKmRate(Integer perKmRate) {
		this.perKmRate = perKmRate;
	}

	public Integer getPerHourRate() {
		return this.perHourRate;
	}

	public void setPerHourRate(Integer perHourRate) {
		this.perHourRate = perHourRate;
	}

	public Integer getOthers() {
		return this.others;
	}

	public void setOthers(Integer others) {
		this.others = others;
	}

	
	public Date getEffectiveTill() {
		return effectiveTill;
	}

	public void setEffectiveTill(Date effectiveTill) {
		this.effectiveTill = effectiveTill;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	public Boolean getGpsEnabled() {
		return gpsEnabled;
	}

	public void setGpsEnabled(Boolean gpsEnabled) {
		this.gpsEnabled = gpsEnabled;
	}
	
}
