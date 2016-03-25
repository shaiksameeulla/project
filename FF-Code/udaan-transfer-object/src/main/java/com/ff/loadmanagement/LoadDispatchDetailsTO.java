package com.ff.loadmanagement;

/**
 * The Class LoadDispatchDetailsTO.
 *
 * @author narmdr
 */
public class LoadDispatchDetailsTO extends LoadManagementDetailsTO implements
		Comparable<LoadDispatchDetailsTO>  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6113606069461685603L;
	
	/** The cd weight. */
	private Double cdWeight;
	
	
	/**
	 * Gets the cd weight.
	 *
	 * @return the cd weight
	 */
	public Double getCdWeight() {
		return cdWeight;
	}

	/**
	 * Sets the cd weight.
	 *
	 * @param cdWeight the new cd weight
	 */
	public void setCdWeight(Double cdWeight) {
		this.cdWeight = cdWeight;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LoadDispatchDetailsTO detailsTO) {
		return this.getLoadConnectedId().compareTo(detailsTO.getLoadConnectedId());
	}
}
