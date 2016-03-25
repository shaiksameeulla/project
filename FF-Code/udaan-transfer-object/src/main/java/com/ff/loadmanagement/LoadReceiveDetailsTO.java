package com.ff.loadmanagement;


/**
 * The Class LoadReceiveDetailsTO used for Receive-Local Load Grid.
 *
 * @author narmdr
 */
public class LoadReceiveDetailsTO extends LoadManagementDetailsTO implements
		Comparable<LoadReceiveDetailsTO> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 386764910547645259L;

	/** The received status. */
	private String receivedStatus;

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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LoadReceiveDetailsTO detailsTO) {
		return this.getLoadConnectedId().compareTo(detailsTO.getLoadConnectedId());
		//return this.getLoadNumber().compareTo(detailsTO.getLoadNumber());
	}
}
