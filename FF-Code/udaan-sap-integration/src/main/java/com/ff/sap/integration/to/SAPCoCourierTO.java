/**
 * 
 */
package com.ff.sap.integration.to;

import java.util.Date;


/**
 * @author cbhure
 *
 */
public class SAPCoCourierTO {
	
	private String sapStatus;
	private String maxCheck;
	private Date sapTimestamp;
	
	/**
	 * @return the sapTimestamp
	 */
	public Date getSapTimestamp() {
		return sapTimestamp;
	}
	/**
	 * @param sapTimestamp the sapTimestamp to set
	 */
	public void setSapTimestamp(Date sapTimestamp) {
		this.sapTimestamp = sapTimestamp;
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
	
	
	
}
