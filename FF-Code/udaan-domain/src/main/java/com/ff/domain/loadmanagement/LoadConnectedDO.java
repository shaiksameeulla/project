package com.ff.domain.loadmanagement;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGFactDO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.transport.TransportModeDO;

/**
 * The Class LoadConnectedDO.
 *
 * @author narmdr
 */
public class LoadConnectedDO extends CGFactDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The load connected id. */
	private Integer loadConnectedId;
	
	/** The connect weight. */
	private Double connectWeight;
	
	/** The dispatch weight. */
	private Double dispatchWeight;
	
	/** The token number. */
	private String tokenNumber;
	
	/** The lock number. */
	private String lockNumber;
	
	/** The remarks. */
	private String remarks;
	
	/** The received status. */
	private String receivedStatus;
	
	/** The recv gatepass number. */
	private String recvGatepassNumber;
	
	/** The recv transport number. */
	private String recvTransportNumber;
	
	/** The manifest do. */
	private ManifestDO manifestDO;	
	
	/** The load movement do. */
	@JsonBackReference private LoadMovementDO loadMovementDO;
	
	/** The recv transport mode do. */
	private TransportModeDO recvTransportModeDO;
	
	/** The recv vendor name. */
	private String recvVendorName;
	
	//coloading fields
	private String coloadingDispatchedUsing = CommonConstants.ENUM_DEFAULT_NULL;//CD/AWB/RR/Z
	private String coloadingDispatchedType = CommonConstants.ENUM_DEFAULT_NULL;//CC/FF/Z
	private String coloadingRateCalculated = CommonConstants.NO;//Y/N

	/**
	 * Gets the recv vendor name.
	 *
	 * @return the recv vendor name
	 */
	public String getRecvVendorName() {
		return recvVendorName;
	}
	
	/**
	 * Sets the recv vendor name.
	 *
	 * @param recvVendorName the new recv vendor name
	 */
	public void setRecvVendorName(String recvVendorName) {
		this.recvVendorName = recvVendorName;
	}
	
	/**
	 * Gets the load connected id.
	 *
	 * @return the load connected id
	 */
	public Integer getLoadConnectedId() {
		return loadConnectedId;
	}
	
	/**
	 * Sets the load connected id.
	 *
	 * @param loadConnectedId the new load connected id
	 */
	public void setLoadConnectedId(Integer loadConnectedId) {
		this.loadConnectedId = loadConnectedId;
	}
	
	/**
	 * Gets the connect weight.
	 *
	 * @return the connect weight
	 */
	public Double getConnectWeight() {
		return connectWeight;
	}
	
	/**
	 * Sets the connect weight.
	 *
	 * @param connectWeight the new connect weight
	 */
	public void setConnectWeight(Double connectWeight) {
		this.connectWeight = connectWeight;
	}
	
	/**
	 * Gets the dispatch weight.
	 *
	 * @return the dispatch weight
	 */
	public Double getDispatchWeight() {
		return dispatchWeight;
	}
	
	/**
	 * Sets the dispatch weight.
	 *
	 * @param dispatchWeight the new dispatch weight
	 */
	public void setDispatchWeight(Double dispatchWeight) {
		this.dispatchWeight = dispatchWeight;
	}
	
	/**
	 * Gets the token number.
	 *
	 * @return the token number
	 */
	public String getTokenNumber() {
		return tokenNumber;
	}
	
	/**
	 * Sets the token number.
	 *
	 * @param tokenNumber the new token number
	 */
	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	
	/**
	 * Gets the lock number.
	 *
	 * @return the lock number
	 */
	public String getLockNumber() {
		return lockNumber;
	}
	
	/**
	 * Sets the lock number.
	 *
	 * @param lockNumber the new lock number
	 */
	public void setLockNumber(String lockNumber) {
		this.lockNumber = lockNumber;
	}
	
	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * Gets the manifest do.
	 *
	 * @return the manifest do
	 */
	public ManifestDO getManifestDO() {
		return manifestDO;
	}
	
	/**
	 * Sets the manifest do.
	 *
	 * @param manifestDO the new manifest do
	 */
	public void setManifestDO(ManifestDO manifestDO) {
		this.manifestDO = manifestDO;
	}
	
	/**
	 * Gets the load movement do.
	 *
	 * @return the load movement do
	 */
	public LoadMovementDO getLoadMovementDO() {
		return loadMovementDO;
	}
	
	/**
	 * Sets the load movement do.
	 *
	 * @param loadMovementDO the new load movement do
	 */
	public void setLoadMovementDO(LoadMovementDO loadMovementDO) {
		this.loadMovementDO = loadMovementDO;
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
	 * Gets the recv gatepass number.
	 *
	 * @return the recv gatepass number
	 */
	public String getRecvGatepassNumber() {
		return recvGatepassNumber;
	}
	
	/**
	 * Sets the recv gatepass number.
	 *
	 * @param recvGatepassNumber the new recv gatepass number
	 */
	public void setRecvGatepassNumber(String recvGatepassNumber) {
		this.recvGatepassNumber = recvGatepassNumber;
	}
	
	/**
	 * Gets the recv transport number.
	 *
	 * @return the recv transport number
	 */
	public String getRecvTransportNumber() {
		return recvTransportNumber;
	}
	
	/**
	 * Sets the recv transport number.
	 *
	 * @param recvTransportNumber the new recv transport number
	 */
	public void setRecvTransportNumber(String recvTransportNumber) {
		this.recvTransportNumber = recvTransportNumber;
	}
	
	/**
	 * Gets the recv transport mode do.
	 *
	 * @return the recv transport mode do
	 */
	public TransportModeDO getRecvTransportModeDO() {
		return recvTransportModeDO;
	}
	
	/**
	 * Sets the recv transport mode do.
	 *
	 * @param recvTransportModeDO the new recv transport mode do
	 */
	public void setRecvTransportModeDO(TransportModeDO recvTransportModeDO) {
		this.recvTransportModeDO = recvTransportModeDO;
	}

	/**
	 * @return the coloadingDispatchedUsing
	 */
	public String getColoadingDispatchedUsing() {
		return coloadingDispatchedUsing;
	}

	/**
	 * @param coloadingDispatchedUsing the coloadingDispatchedUsing to set
	 */
	public void setColoadingDispatchedUsing(String coloadingDispatchedUsing) {
		this.coloadingDispatchedUsing = coloadingDispatchedUsing;
	}

	/**
	 * @return the coloadingDispatchedType
	 */
	public String getColoadingDispatchedType() {
		return coloadingDispatchedType;
	}

	/**
	 * @param coloadingDispatchedType the coloadingDispatchedType to set
	 */
	public void setColoadingDispatchedType(String coloadingDispatchedType) {
		this.coloadingDispatchedType = coloadingDispatchedType;
	}

	/**
	 * @return the coloadingRateCalculated
	 */
	public String getColoadingRateCalculated() {
		return coloadingRateCalculated;
	}

	/**
	 * @param coloadingRateCalculated the coloadingRateCalculated to set
	 */
	public void setColoadingRateCalculated(String coloadingRateCalculated) {
		this.coloadingRateCalculated = coloadingRateCalculated;
	}
}
