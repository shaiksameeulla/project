package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author narmdr
 *
 */
public class VehicleTO extends CGBaseTO {

	private static final long serialVersionUID = -2203460186580985275L;
	private Integer vehicleId;
	private String vehicleCode;
	private String vehicleName;
	private String vehicleDesc;
	private String vehicleType;
	private String regNumber;
	private Integer rhoOfcId;
	private Integer regionId;
	private boolean allVehiclesInRegion;
	
	private String[] transportNumbers;
	private boolean isAvailable;

	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getVehicleCode() {
		return vehicleCode;
	}
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleDesc() {
		return vehicleDesc;
	}
	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	/**
	 * @return the rhoOfcId
	 */
	public Integer getRhoOfcId() {
		return rhoOfcId;
	}
	/**
	 * @param rhoOfcId the rhoOfcId to set
	 */
	public void setRhoOfcId(Integer rhoOfcId) {
		this.rhoOfcId = rhoOfcId;
	}
	/**
	 * @return the regionId
	 */
	public Integer getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	

	public boolean isAllVehiclesInRegion() {
		return allVehiclesInRegion;
	}
	public void setAllVehiclesInRegion(boolean allVehiclesInRegion) {
		this.allVehiclesInRegion = allVehiclesInRegion;
	}
	
	public String[] getTransportNumbers() {
		return transportNumbers;
	}
	public void setTransportNumbers(String[] transportNumbers) {
		this.transportNumbers = transportNumbers;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	
}
