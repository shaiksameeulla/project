package com.ff.loadmanagement;


/**
 * The Class LoadReceiveOutstationDetailsTO used for Receive-Outstation Grid.
 *
 * @author narmdr
 */
public class LoadReceiveOutstationDetailsTO extends LoadManagementDetailsTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8099966720060299646L;
	
	/** The gate pass number. */
	private String gatePassNumber;
	
	/** The vendor. */
	private String vendor;//vendor businessName
	
	/** The transport mode. */
	private String transportMode;
    
    /** The transport number. */
    private String transportNumber;
    
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
	 * Gets the vendor.
	 *
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}
	
	/**
	 * Sets the vendor.
	 *
	 * @param vendor the new vendor
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * Gets the transport mode.
	 *
	 * @return the transport mode
	 */
	public String getTransportMode() {
		return transportMode;
	}
	
	/**
	 * Sets the transport mode.
	 *
	 * @param transportMode the new transport mode
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	
	/**
	 * Gets the transport number.
	 *
	 * @return the transport number
	 */
	public String getTransportNumber() {
		return transportNumber;
	}
	
	/**
	 * Sets the transport number.
	 *
	 * @param transportNumber the new transport number
	 */
	public void setTransportNumber(String transportNumber) {
		this.transportNumber = transportNumber;
	}    
}
