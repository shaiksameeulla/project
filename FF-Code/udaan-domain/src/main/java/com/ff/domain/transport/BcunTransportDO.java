package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class BcunTransportDO.
 *
 * @author narmdr
 */
public class BcunTransportDO extends CGFactDO {

	private static final long serialVersionUID = 6046680977138377424L;
	private Integer transportId;
	private TransportModeDO transportModeDO;
	private BcunFlightDO flightDO;
	private BcunTrainDO trainDO;
	private BcunVehicleDO vehicleDO;
	
	public Integer getTransportId() {
		return transportId;
	}
	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}
	public TransportModeDO getTransportModeDO() {
		return transportModeDO;
	}
	public void setTransportModeDO(TransportModeDO transportModeDO) {
		this.transportModeDO = transportModeDO;
	}
	public FlightDO getFlightDO() {
		return flightDO;
	}
	/**
	 * @return the trainDO
	 */
	public BcunTrainDO getTrainDO() {
		return trainDO;
	}
	/**
	 * @param trainDO the trainDO to set
	 */
	public void setTrainDO(BcunTrainDO trainDO) {
		this.trainDO = trainDO;
	}
	/**
	 * @return the vehicleDO
	 */
	public BcunVehicleDO getVehicleDO() {
		return vehicleDO;
	}
	/**
	 * @param vehicleDO the vehicleDO to set
	 */
	public void setVehicleDO(BcunVehicleDO vehicleDO) {
		this.vehicleDO = vehicleDO;
	}
	/**
	 * @param flightDO the flightDO to set
	 */
	public void setFlightDO(BcunFlightDO flightDO) {
		this.flightDO = flightDO;
	}	
}
