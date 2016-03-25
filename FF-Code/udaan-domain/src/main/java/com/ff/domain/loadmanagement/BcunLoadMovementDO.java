package com.ff.domain.loadmanagement;

import java.util.Date;
import java.util.Set;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * The Class BcunLoadMovementDO.
 *
 * @author narmdr
 */
public class BcunLoadMovementDO extends CGFactDO {

	private static final long serialVersionUID = 4527813781732943480L;

	/** The load movement id. */
	private Integer loadMovementId;
	
	/** The movement direction. */
	private String movementDirection;
	
	/** The gate pass number. */
	private String gatePassNumber;
	
	/** The vehicle type. */
	private String vehicleType;
	
	/** The vehicle reg number. */
	private String vehicleRegNumber;
	
	/** The driver name. */
	private String driverName;
	
	/** The loading date. */
	private Date loadingDate;
	
	/** The loading time. */
	private String loadingTime;
	
	/** The route serviced transport type. */
	private String routeServicedTransportType;
	
	/** The route serviced transport number. */
	private String routeServicedTransportNumber;
	
	/** The received at time. */
	private String receivedAtTime;
	
	/** The received status. */
	private String receivedStatus;
	
	/** The receive type. */
	private String receiveType;
	
	/** The receive number. */
	private String receiveNumber;
	
	//removed for bcun perspective 
	/** The received against. */
	//private Integer receivedAgainst;

	//came from CGFactDO as originOfficeId
	//private Integer originOfficeId;
	//private OfficeDO originOfficeDO;
	
	//came from CGFactDO as destOfficeId
	//private Integer destOfficeId;
	//private OfficeDO destOfficeDO;
	
	//private VehicleDO vehicleDO;
	private Integer vehicleId;
	
	/** The trip serviced by do. */
	//private TripServicedByDO tripServicedByDO;
	private Integer tripServicedById;
	
	/** The transport mode do. */
	//private TransportModeDO transportModeDO;
	private Integer transportModeId;
	
	/** The process do. */
	//private ProcessDO processDO;
	private Integer processId;

	/** The operating office means logged in office */
	private Integer operatingOffice;
	
	/** The load connected d os. */
	//@JsonManagedReference
	private Set<BcunLoadConnectedDO> loadConnectedDOs;
	
	/**
	 * Gets the load movement id.
	 *
	 * @return the load movement id
	 */
	public Integer getLoadMovementId() {
		return loadMovementId;
	}
	
	/**
	 * Sets the load movement id.
	 *
	 * @param loadMovementId the new load movement id
	 */
	public void setLoadMovementId(Integer loadMovementId) {
		this.loadMovementId = loadMovementId;
	}
	
	/**
	 * Gets the movement direction.
	 *
	 * @return the movement direction
	 */
	public String getMovementDirection() {
		return movementDirection;
	}
	
	/**
	 * Sets the movement direction.
	 *
	 * @param movementDirection the new movement direction
	 */
	public void setMovementDirection(String movementDirection) {
		this.movementDirection = movementDirection;
	}
	
	/**
	 * Gets the gate pass number.
	 *
	 * @return the gate pass number
	 */
	public String getGatePassNumber() {
		return gatePassNumber;
	}
	
	/**
	 * Sets the gate pass number.
	 *
	 * @param gatePassNumber the new gate pass number
	 */
	public void setGatePassNumber(String gatePassNumber) {
		this.gatePassNumber = gatePassNumber;
	}
	
	/**
	 * Gets the vehicle type.
	 *
	 * @return the vehicle type
	 */
	public String getVehicleType() {
		return vehicleType;
	}
	
	/**
	 * Sets the vehicle type.
	 *
	 * @param vehicleType the new vehicle type
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	/**
	 * Gets the vehicle reg number.
	 *
	 * @return the vehicle reg number
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}
	
	/**
	 * Sets the vehicle reg number.
	 *
	 * @param vehicleRegNumber the new vehicle reg number
	 */
	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}
	
	/**
	 * Gets the driver name.
	 *
	 * @return the driver name
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * Sets the driver name.
	 *
	 * @param driverName the new driver name
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * Gets the loading date.
	 *
	 * @return the loading date
	 */
	public Date getLoadingDate() {
		return loadingDate;
	}
	
	/**
	 * Sets the loading date.
	 *
	 * @param loadingDate the new loading date
	 */
	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}
	
	/**
	 * Gets the loading time.
	 *
	 * @return the loading time
	 */
	public String getLoadingTime() {
		return loadingTime;
	}
	
	/**
	 * Sets the loading time.
	 *
	 * @param loadingTime the new loading time
	 */
	public void setLoadingTime(String loadingTime) {
		this.loadingTime = loadingTime;
	}	
	
	/**
	 * Gets the route serviced transport type.
	 *
	 * @return the route serviced transport type
	 */
	public String getRouteServicedTransportType() {
		return routeServicedTransportType;
	}
	
	/**
	 * Sets the route serviced transport type.
	 *
	 * @param routeServicedTransportType the new route serviced transport type
	 */
	public void setRouteServicedTransportType(String routeServicedTransportType) {
		this.routeServicedTransportType = routeServicedTransportType;
	}
	
	/**
	 * Gets the route serviced transport number.
	 *
	 * @return the route serviced transport number
	 */
	public String getRouteServicedTransportNumber() {
		return routeServicedTransportNumber;
	}
	
	/**
	 * Sets the route serviced transport number.
	 *
	 * @param routeServicedTransportNumber the new route serviced transport number
	 */
	public void setRouteServicedTransportNumber(String routeServicedTransportNumber) {
		this.routeServicedTransportNumber = routeServicedTransportNumber;
	}
	
	/**
	 * Gets the received at time.
	 *
	 * @return the received at time
	 */
	public String getReceivedAtTime() {
		return receivedAtTime;
	}
	
	/**
	 * Sets the received at time.
	 *
	 * @param receivedAtTime the new received at time
	 */
	public void setReceivedAtTime(String receivedAtTime) {
		this.receivedAtTime = receivedAtTime;
	}
	
	/**
	 * Gets the received status.
	 *
	 * @return the received status
	 */
	public String getReceivedStatus() {
		return receivedStatus;
	}
	
	/**
	 * Sets the received status.
	 *
	 * @param receivedStatus the new received status
	 */
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
		
	/**
	 * Gets the receive type.
	 *
	 * @return the receive type
	 */
	public String getReceiveType() {
		return receiveType;
	}
	
	/**
	 * Sets the receive type.
	 *
	 * @param receiveType the new receive type
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	
	/**
	 * Gets the receive number.
	 *
	 * @return the receive number
	 */
	public String getReceiveNumber() {
		return receiveNumber;
	}
	
	/**
	 * Sets the receive number.
	 *
	 * @param receiveNumber the new receive number
	 */
	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}

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
	 * @return the transportModeId
	 */
	public Integer getTransportModeId() {
		return transportModeId;
	}

	/**
	 * @param transportModeId the transportModeId to set
	 */
	public void setTransportModeId(Integer transportModeId) {
		this.transportModeId = transportModeId;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * @return the loadConnectedDOs
	 */
	public Set<BcunLoadConnectedDO> getLoadConnectedDOs() {
		return loadConnectedDOs;
	}

	/**
	 * @param loadConnectedDOs the loadConnectedDOs to set
	 */
	public void setLoadConnectedDOs(Set<BcunLoadConnectedDO> loadConnectedDOs) {
		this.loadConnectedDOs = loadConnectedDOs;
	}

	/**
	 * @return the operatingOffice
	 */
	public Integer getOperatingOffice() {
		return operatingOffice;
	}

	/**
	 * @param operatingOffice the operatingOffice to set
	 */
	public void setOperatingOffice(Integer operatingOffice) {
		this.operatingOffice = operatingOffice;
	}

	/**
	 * @return the tripServicedById
	 */
	public Integer getTripServicedById() {
		return tripServicedById;
	}

	/**
	 * @param tripServicedById the tripServicedById to set
	 */
	public void setTripServicedById(Integer tripServicedById) {
		this.tripServicedById = tripServicedById;
	}
	
}
