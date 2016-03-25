/**
 * 
 */
package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.organization.OfficeDO;

/**
 * @author narmdr
 *
 */
public class VehicleDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	private Integer vehicleId;
	/*private String vehicleCode;
	private String vehicleName;
	private String vehicleDesc;*/
	private String vehicleType;
	private String regNumber;
	private OfficeDO regionalOfficeDO;	
	
	private Boolean isGpsEnabled;

	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
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
	public OfficeDO getRegionalOfficeDO() {
		return regionalOfficeDO;
	}
	public void setRegionalOfficeDO(OfficeDO regionalOfficeDO) {
		this.regionalOfficeDO = regionalOfficeDO;
	}
	public Boolean getIsGpsEnabled() {
		return isGpsEnabled;
	}
	public void setIsGpsEnabled(Boolean isGpsEnabled) {
		this.isGpsEnabled = isGpsEnabled;
	}	
	
	
}
