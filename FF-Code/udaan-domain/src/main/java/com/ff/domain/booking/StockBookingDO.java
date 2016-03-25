package com.ff.domain.booking;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.domain.CGBaseDO;

public class StockBookingDO extends CGBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2758498766361116L;
	private Integer bookingId;
	private String consgNumber;
	private Date bookingDate;
	
	private Integer bookingOfficeId;
	private String cnStatus ;
	private String status;
	private String isStockConsumed;
	private String consgTypeId;
	private String consgTypeCode;
	private List<String> childCnList;
	/**
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}
	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}
	/**
	 * @return the bookingOfficeId
	 */
	public Integer getBookingOfficeId() {
		return bookingOfficeId;
	}
	/**
	 * @return the cnStatus
	 */
	public String getCnStatus() {
		return cnStatus;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the isStockConsumed
	 */
	public String getIsStockConsumed() {
		return isStockConsumed;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
	}
	/**
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	/**
	 * @param bookingOfficeId the bookingOfficeId to set
	 */
	public void setBookingOfficeId(Integer bookingOfficeId) {
		this.bookingOfficeId = bookingOfficeId;
	}
	/**
	 * @param cnStatus the cnStatus to set
	 */
	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param isStockConsumed the isStockConsumed to set
	 */
	public void setIsStockConsumed(String isStockConsumed) {
		this.isStockConsumed = isStockConsumed;
	}
	/**
	 * @return the childCnList
	 */
	public List<String> getChildCnList() {
		return childCnList;
	}
	/**
	 * @return the consgTypeId
	 */
	public String getConsgTypeId() {
		return consgTypeId;
	}
	/**
	 * @return the consgTypeCode
	 */
	public String getConsgTypeCode() {
		return consgTypeCode;
	}
	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(String consgTypeId) {
		this.consgTypeId = consgTypeId;
	}
	/**
	 * @param consgTypeCode the consgTypeCode to set
	 */
	public void setConsgTypeCode(String consgTypeCode) {
		this.consgTypeCode = consgTypeCode;
	}
	/**
	 * @param childCnList the childCnList to set
	 */
	public void setChildCnList(List<String> childCnList) {
		this.childCnList = childCnList;
	}
	
	
}
