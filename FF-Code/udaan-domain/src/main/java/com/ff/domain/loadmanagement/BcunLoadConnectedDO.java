package com.ff.domain.loadmanagement;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.manifest.BcunLoadManifestDO;

/**
 * The Class BcunLoadConnectedDO.
 *
 * @author narmdr
 */
public class BcunLoadConnectedDO extends CGFactDO {

	
	private static final long serialVersionUID = -3756833206910594788L;

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
	private BcunLoadManifestDO manifestDO;

	/** The load movement do. */
	/*@JsonBackReference 
	private BcunLoadMovementDO loadMovementDO;*/

	/** The load movement id. */
	private Integer loadMovementId;
	
	/** The recv transport mode id. */
	private Integer recvTransportModeId;
	
	/** The recv vendor name. */
	private String recvVendorName;
	
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
	 * @return the recvTransportModeId
	 */
	public Integer getRecvTransportModeId() {
		return recvTransportModeId;
	}

	/**
	 * @param recvTransportModeId the recvTransportModeId to set
	 */
	public void setRecvTransportModeId(Integer recvTransportModeId) {
		this.recvTransportModeId = recvTransportModeId;
	}

	/**
	 * @return the loadMovementId
	 */
	public Integer getLoadMovementId() {
		return loadMovementId;
	}

	/**
	 * @param loadMovementId the loadMovementId to set
	 */
	public void setLoadMovementId(Integer loadMovementId) {
		this.loadMovementId = loadMovementId;
	}

	/**
	 * @return the manifestDO
	 */
	public BcunLoadManifestDO getManifestDO() {
		return manifestDO;
	}

	/**
	 * @param manifestDO the manifestDO to set
	 */
	public void setManifestDO(BcunLoadManifestDO manifestDO) {
		this.manifestDO = manifestDO;
	}
}
