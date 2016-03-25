/**
 * 
 */
package com.cg.lbs.bcun.to;

import java.util.Date;

/**
 * @author mohammes
 *
 */
public class ManualDownloadInputTO {
	
	private String officeCode;
	private Date startDate;
	private Date endDate;
	private String blobType;
	private String blobStatus;
	private Integer maxRecordsToFetch;
	
	private String startDateStr;
	private String endDateStr;
	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @return the blobType
	 */
	public String getBlobType() {
		return blobType;
	}
	/**
	 * @return the blobStatus
	 */
	public String getBlobStatus() {
		return blobStatus;
	}
	/**
	 * @return the maxRecordsToFetch
	 */
	public Integer getMaxRecordsToFetch() {
		return maxRecordsToFetch;
	}
	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @param blobType the blobType to set
	 */
	public void setBlobType(String blobType) {
		this.blobType = blobType;
	}
	/**
	 * @param blobStatus the blobStatus to set
	 */
	public void setBlobStatus(String blobStatus) {
		this.blobStatus = blobStatus;
	}
	/**
	 * @param maxRecordsToFetch the maxRecordsToFetch to set
	 */
	public void setMaxRecordsToFetch(Integer maxRecordsToFetch) {
		this.maxRecordsToFetch = maxRecordsToFetch;
	}
	/**
	 * @return the startDateStr
	 */
	public String getStartDateStr() {
		return startDateStr;
	}
	/**
	 * @return the endDateStr
	 */
	public String getEndDateStr() {
		return endDateStr;
	}
	/**
	 * @param startDateStr the startDateStr to set
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	/**
	 * @param endDateStr the endDateStr to set
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}
