/**
 * 
 */
package com.ff.sap.integration.to;

/**
 * @author cbhure
 *
 */
public class SAPExpenseTO {

	private String sapStatus;
	private String status;
	private Integer reportingRHOID;
	private String maxCheck;
 
	
	
	/**
	 * @return the maxCheck
	 */
	public String getMaxCheck() {
		return maxCheck;
	}

	/**
	 * @param maxCheck the maxCheck to set
	 */
	public void setMaxCheck(String maxCheck) {
		this.maxCheck = maxCheck;
	}

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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the reportingRHOID
	 */
	public Integer getReportingRHOID() {
		return reportingRHOID;
	}

	/**
	 * @param reportingRHOID the reportingRHOID to set
	 */
	public void setReportingRHOID(Integer reportingRHOID) {
		this.reportingRHOID = reportingRHOID;
	}
}
