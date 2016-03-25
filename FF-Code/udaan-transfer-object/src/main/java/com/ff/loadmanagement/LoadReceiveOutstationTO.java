package com.ff.loadmanagement;

/**
 * The Class LoadReceiveOutstationTO used for Load Receive-Outstation Header.
 * 
 * @author narmdr
 */
public class LoadReceiveOutstationTO extends LoadReceiveTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5209574799904467384L;

	/** The receive number. */
	private String receiveNumber;

	//grid
	/** The row count. */
	private int rowCount;
	
	/** The recv vendor. */
	private String[] recvVendor = new String[rowCount];
	
	/** The recv transport mode. */
	private String[] recvTransportMode = new String[rowCount];
	
	/** The recv transport number. */
	private String[] recvTransportNumber = new String[rowCount];
	
	/** The recv gate pass number. */
	private String[] recvGatePassNumber = new String[rowCount];
	
	/**
	 * Gets the receive number.
	 * 
	 * @return the receiveNumber
	 */
	public String getReceiveNumber() {
		return receiveNumber;
	}

	/**
	 * Sets the receive number.
	 * 
	 * @param receiveNumber
	 *            the receiveNumber to set
	 */
	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadReceiveTO#getRowCount()
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadReceiveTO#setRowCount(int)
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * Gets the recv vendor.
	 *
	 * @return the recv vendor
	 */
	public String[] getRecvVendor() {
		return recvVendor;
	}

	/**
	 * Sets the recv vendor.
	 *
	 * @param recvVendor the new recv vendor
	 */
	public void setRecvVendor(String[] recvVendor) {
		this.recvVendor = recvVendor;
	}

	/**
	 * Gets the recv transport mode.
	 *
	 * @return the recv transport mode
	 */
	public String[] getRecvTransportMode() {
		return recvTransportMode;
	}

	/**
	 * Sets the recv transport mode.
	 *
	 * @param recvTransportMode the new recv transport mode
	 */
	public void setRecvTransportMode(String[] recvTransportMode) {
		this.recvTransportMode = recvTransportMode;
	}

	/**
	 * Gets the recv transport number.
	 *
	 * @return the recv transport number
	 */
	public String[] getRecvTransportNumber() {
		return recvTransportNumber;
	}

	/**
	 * Sets the recv transport number.
	 *
	 * @param recvTransportNumber the new recv transport number
	 */
	public void setRecvTransportNumber(String[] recvTransportNumber) {
		this.recvTransportNumber = recvTransportNumber;
	}

	/**
	 * Gets the recv gate pass number.
	 *
	 * @return the recv gate pass number
	 */
	public String[] getRecvGatePassNumber() {
		return recvGatePassNumber;
	}

	/**
	 * Sets the recv gate pass number.
	 *
	 * @param recvGatePassNumber the new recv gate pass number
	 */
	public void setRecvGatePassNumber(String[] recvGatePassNumber) {
		this.recvGatePassNumber = recvGatePassNumber;
	}
}
