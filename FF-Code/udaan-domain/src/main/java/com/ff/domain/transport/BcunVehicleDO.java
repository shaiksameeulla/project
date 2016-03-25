package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * The Class BcunVehicleDO.
 *
 * @author narmdr
 */
public class BcunVehicleDO extends CGMasterDO {

	private static final long serialVersionUID = -3768523395202961157L;

	private Integer vehicleId;
	/*private String vehicleCode;
	private String vehicleName;
	private String vehicleDesc;*/
	private String vehicleType;
	private String regNumber;
	private Integer regionalOfficeId;
	/**
	 * @return the vehicleId
	 */
	public Integer getVehicleId() {
		return vehicleId;
	}
	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}
	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	/**
	 * @return the regNumber
	 */
	public String getRegNumber() {
		return regNumber;
	}
	/**
	 * @param regNumber the regNumber to set
	 */
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	/**
	 * @return the regionalOfficeId
	 */
	public Integer getRegionalOfficeId() {
		return regionalOfficeId;
	}
	/**
	 * @param regionalOfficeId the regionalOfficeId to set
	 */
	public void setRegionalOfficeId(Integer regionalOfficeId) {
		this.regionalOfficeId = regionalOfficeId;
	}

}
