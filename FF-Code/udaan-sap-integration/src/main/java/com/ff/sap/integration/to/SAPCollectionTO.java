/**
 * 
 */
package com.ff.sap.integration.to;

import java.util.Date;
import java.util.List;

/**
 * @author cbhure
 *
 */
public class SAPCollectionTO {
	
	private String sapStatus;
	private String status;
	private Date bookingDate;
	private Integer bookingOfficeId;
	private String bookingType;
	private String paymentCode;
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
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @return the bookingOfficeId
	 */
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	/**
	 * @param bookingOfficeId the bookingOfficeId to set
	 */
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	/**
	 * @return the paymentCode
	 */
	public String getPaymentCode() {
		return paymentCode;
	}
	/**
	 * @param paymentCode the paymentCode to set
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
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
