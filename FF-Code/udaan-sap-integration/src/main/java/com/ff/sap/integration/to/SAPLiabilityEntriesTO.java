/**
 * 
 */
package com.ff.sap.integration.to;

import java.util.Date;
import java.util.List;

/**
 * @author CBHURE
 *
 */
public class SAPLiabilityEntriesTO {

	private String sapStatus;
	private Integer position;
	private List productIDList;
	private String consgDelivered;
	private String statusFlag;
	private  String maxCheck;
	private Date presentDate;
	
	
	/**
	 * @return the presentDate
	 */
	public Date getPresentDate() {
		return presentDate;
	}

	/**
	 * @param presentDate the presentDate to set
	 */
	public void setPresentDate(Date presentDate) {
		this.presentDate = presentDate;
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
	
	

	public  String getMaxCheck() {
		return maxCheck;
	}

	public void setMaxCheck(String maxCheck) {
		this.maxCheck = maxCheck;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the productIDList
	 */
	public List getProductIDList() {
		return productIDList;
	}

	/**
	 * @param productIDList the productIDList to set
	 */
	public void setProductIDList(List productIDList) {
		this.productIDList = productIDList;
	}

	/**
	 * @return the consgDelivered
	 */
	public String getConsgDelivered() {
		return consgDelivered;
	}

	/**
	 * @param consgDelivered the consgDelivered to set
	 */
	public void setConsgDelivered(String consgDelivered) {
		this.consgDelivered = consgDelivered;
	}

	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	
	
}
