package com.ff.loadmanagement;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class LoadReceiveManifestValidationTO.
 *
 * @author narmdr
 */
public class LoadReceiveManifestValidationTO extends CGBaseTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5571869745442524959L;
	
	/** The load movement id. */
	private Integer loadMovementId; //used for receive loadMovementId
	
	/** The received against id. */
	private Integer receivedAgainstId; //used for dispatch loadMovementId
	
	/** The manifest number. */
	private String manifestNumber; //BPL Number
	
	/** The dest office id. */
	private Integer destOfficeId;

	/** The load receive details to. */
	private LoadReceiveDetailsTO loadReceiveDetailsTO;
	
	/** The load receive outstation details to. */
	private LoadReceiveOutstationDetailsTO loadReceiveOutstationDetailsTO;
	
	/** The manifest to. */
	private ManifestTO manifestTO;

	/** Outputs. */
	private String errorMsg; 
	
	/** The is new manifest. */
	private Boolean isNewManifest;
	
	/** The is receive. */
	private Boolean isReceive;
	
	/** The is dispatch. */
	private Boolean isDispatch;
	
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
	 * Gets the received against id.
	 *
	 * @return the received against id
	 */
	public Integer getReceivedAgainstId() {
		return receivedAgainstId;
	}
	
	/**
	 * Sets the received against id.
	 *
	 * @param receivedAgainstId the new received against id
	 */
	public void setReceivedAgainstId(Integer receivedAgainstId) {
		this.receivedAgainstId = receivedAgainstId;
	}
	
	/**
	 * Gets the manifest number.
	 *
	 * @return the manifest number
	 */
	public String getManifestNumber() {
		return manifestNumber;
	}
	
	/**
	 * Sets the manifest number.
	 *
	 * @param manifestNumber the new manifest number
	 */
	public void setManifestNumber(String manifestNumber) {
		this.manifestNumber = manifestNumber;
	}
	
	/**
	 * Gets the load receive details to.
	 *
	 * @return the load receive details to
	 */
	public LoadReceiveDetailsTO getLoadReceiveDetailsTO() {
		return loadReceiveDetailsTO;
	}
	
	/**
	 * Sets the load receive details to.
	 *
	 * @param loadReceiveDetailsTO the new load receive details to
	 */
	public void setLoadReceiveDetailsTO(LoadReceiveDetailsTO loadReceiveDetailsTO) {
		this.loadReceiveDetailsTO = loadReceiveDetailsTO;
	}
	
	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * Gets the checks if is new manifest.
	 *
	 * @return the checks if is new manifest
	 */
	public Boolean getIsNewManifest() {
		return isNewManifest;
	}
	
	/**
	 * Sets the checks if is new manifest.
	 *
	 * @param isNewManifest the new checks if is new manifest
	 */
	public void setIsNewManifest(Boolean isNewManifest) {
		this.isNewManifest = isNewManifest;
	}
	
	/**
	 * Gets the checks if is receive.
	 *
	 * @return the checks if is receive
	 */
	public Boolean getIsReceive() {
		return isReceive;
	}
	
	/**
	 * Sets the checks if is receive.
	 *
	 * @param isReceive the new checks if is receive
	 */
	public void setIsReceive(Boolean isReceive) {
		this.isReceive = isReceive;
	}
	
	/**
	 * Gets the checks if is dispatch.
	 *
	 * @return the checks if is dispatch
	 */
	public Boolean getIsDispatch() {
		return isDispatch;
	}
	
	/**
	 * Sets the checks if is dispatch.
	 *
	 * @param isDispatch the new checks if is dispatch
	 */
	public void setIsDispatch(Boolean isDispatch) {
		this.isDispatch = isDispatch;
	}
	
	/**
	 * Gets the manifest to.
	 *
	 * @return the manifest to
	 */
	public ManifestTO getManifestTO() {
		return manifestTO;
	}
	
	/**
	 * Sets the manifest to.
	 *
	 * @param manifestTO the new manifest to
	 */
	public void setManifestTO(ManifestTO manifestTO) {
		this.manifestTO = manifestTO;
	}
	
	/**
	 * Gets the dest office id.
	 *
	 * @return the dest office id
	 */
	public Integer getDestOfficeId() {
		return destOfficeId;
	}
	
	/**
	 * Sets the dest office id.
	 *
	 * @param destOfficeId the new dest office id
	 */
	public void setDestOfficeId(Integer destOfficeId) {
		this.destOfficeId = destOfficeId;
	}
	
	/**
	 * Gets the load receive outstation details to.
	 *
	 * @return the load receive outstation details to
	 */
	public LoadReceiveOutstationDetailsTO getLoadReceiveOutstationDetailsTO() {
		return loadReceiveOutstationDetailsTO;
	}
	
	/**
	 * Sets the load receive outstation details to.
	 *
	 * @param loadReceiveOutstationDetailsTO the new load receive outstation details to
	 */
	public void setLoadReceiveOutstationDetailsTO(
			LoadReceiveOutstationDetailsTO loadReceiveOutstationDetailsTO) {
		this.loadReceiveOutstationDetailsTO = loadReceiveOutstationDetailsTO;
	}	
}
