package com.ff.loadmanagement;

import java.util.List;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * The Class LoadReceiveValidationTO.
 *
 * @author narmdr
 */
public class LoadReceiveValidationTO extends CGBaseTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2374688006871187646L;

	/** The load movement id. */
	private Integer loadMovementId; //used for receive loadMovementId
	
	/** The received against id. */
	private Integer receivedAgainstId; //used for dispatch loadMovementId
	
	/** The manifest number list. */
	private List<String> manifestNumberList; //BPL Number
	
	/** The manifest number. */
	private String manifestNumber; //used in editBag as BPL/MBPL number
	
	/** The receive number. */
	private String receiveNumber; //used in outstation as receive Number	

	/** Outputs. */
	private String errorMsg;
	
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
	 * Gets the manifest number list.
	 *
	 * @return the manifest number list
	 */
	public List<String> getManifestNumberList() {
		return manifestNumberList;
	}
	
	/**
	 * Sets the manifest number list.
	 *
	 * @param manifestNumberList the new manifest number list
	 */
	public void setManifestNumberList(List<String> manifestNumberList) {
		this.manifestNumberList = manifestNumberList;
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
}
