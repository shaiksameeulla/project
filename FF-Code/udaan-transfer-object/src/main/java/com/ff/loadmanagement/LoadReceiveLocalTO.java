package com.ff.loadmanagement;

/**
 * The Class LoadReceiveLocalTO used for Receive-Local Load Header.
 *
 * @author narmdr
 */
public class LoadReceiveLocalTO extends LoadReceiveTO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2903261446728230241L;
	
	/** The actual arrival. */
	private String actualArrival;
	
	/**
	 * The Total Weight Print
	 */
	private Double totalWeightPrint;
	
	private String destCity;
	
	

	private int totalBagPrint;

	/**
	 * Gets the actual arrival.
	 *
	 * @return the actual arrival
	 */
	public String getActualArrival() {
		return actualArrival;
	}
	
	/**
	 * Sets the actual arrival.
	 *
	 * @param actualArrival the new actual arrival
	 */
	public void setActualArrival(String actualArrival) {
		this.actualArrival = actualArrival;
	}

	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#getTotalWeightPrint()
	 */
	public Double getTotalWeightPrint() {
		return totalWeightPrint;
	}

	/* (non-Javadoc)
	 * @see com.ff.loadmanagement.LoadManagementTO#setTotalWeightPrint(java.lang.Double)
	 */
	public void setTotalWeightPrint(Double totalWeightPrint) {
		this.totalWeightPrint = totalWeightPrint;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}	
	
	public int getTotalBagPrint() {
		return totalBagPrint;
	}

	public void setTotalBagPrint(int totalBagPrint) {
		this.totalBagPrint = totalBagPrint;
	}

	
}
