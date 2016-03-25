package com.ff.loadmanagement;

import java.util.List;

/**
 * The Class LoadReceiveTO is Base/Common Header TO for Receive.
 *
 * @author narmdr
 */
public abstract class LoadReceiveTO extends LoadManagementTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6610301660578651424L;	
	
	/** The header received status. */
	private String headerReceivedStatus;
	
	/** The received against id. */
	private Integer receivedAgainstId;
	
	/** The logged in office id. */
	private Integer loggedInOfficeId;
		
	/** The transport mode details. */
	private String transportModeDetails;	
	
	/** The logged in office. */
	private String loggedInOffice;	
	
	/** The receive date time. */
	private String receiveDateTime;	

	/** The load receive details t os. */
	private List<LoadReceiveDetailsTO> loadReceiveDetailsTOs;

	//grid
	/** The row count. */
	private int rowCount;
	
	/** The received status. */
	private String[] receivedStatus = new String[rowCount];
	
	/** Outputs. */
	private Boolean isNewReceive; 
	
	/** The is receive saved. */
	private Boolean isReceiveSaved; 
	
	/**
	 * Gets the header received status.
	 *
	 * @return the header received status
	 */
	public String getHeaderReceivedStatus() {
		return headerReceivedStatus;
	}
	
	/**
	 * Sets the header received status.
	 *
	 * @param headerReceivedStatus the new header received status
	 */
	public void setHeaderReceivedStatus(String headerReceivedStatus) {
		this.headerReceivedStatus = headerReceivedStatus;
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
	 * Gets the transport mode details.
	 *
	 * @return the transport mode details
	 */
	public String getTransportModeDetails() {
		return transportModeDetails;
	}
	
	/**
	 * Sets the transport mode details.
	 *
	 * @param transportModeDetails the new transport mode details
	 */
	public void setTransportModeDetails(String transportModeDetails) {
		this.transportModeDetails = transportModeDetails;
	}
	
	/**
	 * Gets the load receive details t os.
	 *
	 * @return the load receive details t os
	 */
	public List<LoadReceiveDetailsTO> getLoadReceiveDetailsTOs() {
		return loadReceiveDetailsTOs;
	}
	
	/**
	 * Sets the load receive details t os.
	 *
	 * @param loadReceiveDetailsTOs the new load receive details t os
	 */
	public void setLoadReceiveDetailsTOs(
			List<LoadReceiveDetailsTO> loadReceiveDetailsTOs) {
		this.loadReceiveDetailsTOs = loadReceiveDetailsTOs;
	}
	
	/**
	 * Gets the receive date time.
	 *
	 * @return the receive date time
	 */
	public String getReceiveDateTime() {
		return receiveDateTime;
	}
	
	/**
	 * Sets the receive date time.
	 *
	 * @param receiveDateTime the new receive date time
	 */
	public void setReceiveDateTime(String receiveDateTime) {
		this.receiveDateTime = receiveDateTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#getRowCount()
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#setRowCount(int)
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	/**
	 * Gets the received status.
	 *
	 * @return the received status
	 */
	public String[] getReceivedStatus() {
		return receivedStatus;
	}
	
	/**
	 * Sets the received status.
	 *
	 * @param receivedStatus the new received status
	 */
	public void setReceivedStatus(String[] receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	
	/**
	 * Gets the logged in office id.
	 *
	 * @return the logged in office id
	 */
	public Integer getLoggedInOfficeId() {
		return loggedInOfficeId;
	}
	
	/**
	 * Sets the logged in office id.
	 *
	 * @param loggedInOfficeId the new logged in office id
	 */
	public void setLoggedInOfficeId(Integer loggedInOfficeId) {
		this.loggedInOfficeId = loggedInOfficeId;
	}
	
	/**
	 * Gets the logged in office.
	 *
	 * @return the logged in office
	 */
	public String getLoggedInOffice() {
		return loggedInOffice;
	}
	
	/**
	 * Sets the logged in office.
	 *
	 * @param loggedInOffice the new logged in office
	 */
	public void setLoggedInOffice(String loggedInOffice) {
		this.loggedInOffice = loggedInOffice;
	}
	
	/**
	 * Gets the checks if is new receive.
	 *
	 * @return the checks if is new receive
	 */
	public Boolean getIsNewReceive() {
		return isNewReceive;
	}
	
	/**
	 * Sets the checks if is new receive.
	 *
	 * @param isNewReceive the new checks if is new receive
	 */
	public void setIsNewReceive(Boolean isNewReceive) {
		this.isNewReceive = isNewReceive;
	}
	
	/**
	 * Gets the checks if is receive saved.
	 *
	 * @return the checks if is receive saved
	 */
	public Boolean getIsReceiveSaved() {
		return isReceiveSaved;
	}
	
	/**
	 * Sets the checks if is receive saved.
	 *
	 * @param isReceiveSaved the new checks if is receive saved
	 */
	public void setIsReceiveSaved(Boolean isReceiveSaved) {
		this.isReceiveSaved = isReceiveSaved;
	}
		
}
