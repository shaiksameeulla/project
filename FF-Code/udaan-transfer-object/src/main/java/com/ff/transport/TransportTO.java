package com.ff.transport;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author narmdr
 *
 */
public class TransportTO extends CGBaseTO {

	private static final long serialVersionUID = 1L;

	private Integer transportId;
	private TransportModeTO transportModeTO;
	private FlightTO flightTO;
	private TrainTO trainTO;
	private VehicleTO vehicleTO;
	private Integer regionId;
	
	public Integer getTransportId() {
		return transportId;
	}
	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}
	public TransportModeTO getTransportModeTO() {
		return transportModeTO;
	}
	public void setTransportModeTO(TransportModeTO transportModeTO) {
		this.transportModeTO = transportModeTO;
	}
	public FlightTO getFlightTO() {
		return flightTO;
	}
	public void setFlightTO(FlightTO flightTO) {
		this.flightTO = flightTO;
	}
	public TrainTO getTrainTO() {
		return trainTO;
	}
	public void setTrainTO(TrainTO trainTO) {
		this.trainTO = trainTO;
	}
	public VehicleTO getVehicleTO() {
		return vehicleTO;
	}
	public void setVehicleTO(VehicleTO vehicleTO) {
		this.vehicleTO = vehicleTO;
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
	
}
