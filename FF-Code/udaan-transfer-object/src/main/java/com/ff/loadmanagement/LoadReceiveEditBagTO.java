package com.ff.loadmanagement;

/**
 * The Class LoadReceiveEditBagTO.
 *
 * @author hkansagr
 */

public class LoadReceiveEditBagTO extends LoadReceiveOutstationDetailsTO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The modification date time. */
	private String modificationDateTime;
	
	/** The transport mode id. */
	private Integer transportModeId;
    
    /** The load movement id. */
    private Integer loadMovementId;    
    
	/**
	 * Gets the transport mode id.
	 *
	 * @return the transportModeId
	 */
	public Integer getTransportModeId() {
		return transportModeId;
	}
	
	/**
	 * Sets the transport mode id.
	 *
	 * @param transportModeId the transportModeId to set
	 */
	public void setTransportModeId(Integer transportModeId) {
		this.transportModeId = transportModeId;
	}
	
	/**
	 * Gets the load movement id.
	 *
	 * @return the loadMovementId
	 */
	public Integer getLoadMovementId() {
		return loadMovementId;
	}
	
	/**
	 * Sets the load movement id.
	 *
	 * @param loadMovementId the loadMovementId to set
	 */
	public void setLoadMovementId(Integer loadMovementId) {
		this.loadMovementId = loadMovementId;
	}
	
	/**
	 * Gets the modification date time.
	 *
	 * @return the modificationDateTime
	 */
	public String getModificationDateTime() {
		return modificationDateTime;
	}
	
	/**
	 * Sets the modification date time.
	 *
	 * @param modificationDateTime the modificationDateTime to set
	 */
	public void setModificationDateTime(String modificationDateTime) {
		this.modificationDateTime = modificationDateTime;
	}
    
}
