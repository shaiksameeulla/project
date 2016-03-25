package com.ff.domain.transport;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author narmdr
 *
 */
public class TransportDO extends CGFactDO {

	private static final long serialVersionUID = 1L;

	private Integer transportId;
	private TransportModeDO transportModeDO;
	private FlightDO flightDO;
	private TrainDO trainDO;
	private VehicleDO vehicleDO;
	
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
	public void setFlightDO(FlightDO flightDO) {
		this.flightDO = flightDO;
	}
	public TrainDO getTrainDO() {
		return trainDO;
	}
	public void setTrainDO(TrainDO trainDO) {
		this.trainDO = trainDO;
	}
	public VehicleDO getVehicleDO() {
		return vehicleDO;
	}
	public void setVehicleDO(VehicleDO vehicleDO) {
		this.vehicleDO = vehicleDO;
	}	
}
