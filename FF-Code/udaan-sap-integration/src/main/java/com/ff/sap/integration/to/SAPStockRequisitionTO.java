/**
 * 
 */
package com.ff.sap.integration.to;


/**
 * @author cbhure
 *
 */
public class SAPStockRequisitionTO {

	
	private String sapStatus;
	private String isPrConsolidated;
	private String maxCheck;
	
	
	/**
	 * @return the sapStatus
	 */
	public String getSapStatus() {
		return sapStatus;
	}
	/**
	 * @param sapStatus the sapStatus to set
	 */
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	/**
	 * @return the isPrConsolidated
	 */
	public String getIsPrConsolidated() {
		return isPrConsolidated;
	}
	/**
	 * @param isPrConsolidated the isPrConsolidated to set
	 */
	public void setIsPrConsolidated(String isPrConsolidated) {
		this.isPrConsolidated = isPrConsolidated;
	}
	
	public String getMaxCheck() {
		return maxCheck;
	}
	
	public void setMaxCheck(String maxCheck) {
		this.maxCheck = maxCheck;
	}
	
}
