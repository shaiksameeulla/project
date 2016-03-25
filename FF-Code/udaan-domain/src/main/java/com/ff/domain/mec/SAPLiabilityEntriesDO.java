package com.ff.domain.mec;

import java.util.Date;

import com.capgemini.lbs.framework.domain.CGFactDO;

/**
 * @author CBHURE
 *
 */

public class SAPLiabilityEntriesDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306898995735466508L;
	
	private Long Id;
	private Date bookingDate;
	private Date creationDate;
	private String bookingOfcRHOCode;
	private String consgNo;
	private Integer consgId;
	private Double codValue;
	private Double lcValue;
	private String destRHO;
	private String statusFlag;
	private String custNo;
	private String consgDelivered;
	private Date rtoDate;
	private Date rtoDrsDate;
	private Date consigneeDate;
	private String exception;
	private Double baAmount;
	private String isConsumed;
	
	
	/**
	 * @return the isConsumed
	 */
	public String getIsConsumed() {
		return isConsumed;
	}
	/**
	 * @param isConsumed the isConsumed to set
	 */
	public void setIsConsumed(String isConsumed) {
		this.isConsumed = isConsumed;
	}
	
	/**
	 * @return the baAmount
	 */
	public Double getBaAmount() {
		return baAmount;
	}
	/**
	 * @param baAmount the baAmount to set
	 */
	public void setBaAmount(Double baAmount) {
		this.baAmount = baAmount;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the bookingOfcRHOCode
	 */
	public String getBookingOfcRHOCode() {
		return bookingOfcRHOCode;
	}
	/**
	 * @param bookingOfcRHOCode the bookingOfcRHOCode to set
	 */
	public void setBookingOfcRHOCode(String bookingOfcRHOCode) {
		this.bookingOfcRHOCode = bookingOfcRHOCode;
	}
	/**
	 * @return the consgNo
	 */
	public String getConsgNo() {
		return consgNo;
	}
	/**
	 * @param consgNo the consgNo to set
	 */
	public void setConsgNo(String consgNo) {
		this.consgNo = consgNo;
	}
	/**
	 * @return the codValue
	 */
	public Double getCodValue() {
		return codValue;
	}
	/**
	 * @param codValue the codValue to set
	 */
	public void setCodValue(Double codValue) {
		this.codValue = codValue;
	}
	/**
	 * @return the lcValue
	 */
	public Double getLcValue() {
		return lcValue;
	}
	/**
	 * @param lcValue the lcValue to set
	 */
	public void setLcValue(Double lcValue) {
		this.lcValue = lcValue;
	}
	/**
	 * @return the destRHO
	 */
	public String getDestRHO() {
		return destRHO;
	}
	/**
	 * @param destRHO the destRHO to set
	 */
	public void setDestRHO(String destRHO) {
		this.destRHO = destRHO;
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
	/**
	 * @return the custNo
	 */
	public String getCustNo() {
		return custNo;
	}
	/**
	 * @param custNo the custNo to set
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
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
	 * @return the rtoDate
	 */
	public Date getRtoDate() {
		return rtoDate;
	}
	/**
	 * @param rtoDate the rtoDate to set
	 */
	public void setRtoDate(Date rtoDate) {
		this.rtoDate = rtoDate;
	}
	/**
	 * @return the rtoDrsDate
	 */
	public Date getRtoDrsDate() {
		return rtoDrsDate;
	}
	/**
	 * @param rtoDrsDate the rtoDrsDate to set
	 */
	public void setRtoDrsDate(Date rtoDrsDate) {
		this.rtoDrsDate = rtoDrsDate;
	}
	/**
	 * @return the consigneeDate
	 */
	public Date getConsigneeDate() {
		return consigneeDate;
	}
	/**
	 * @param consigneeDate the consigneeDate to set
	 */
	public void setConsigneeDate(Date consigneeDate) {
		this.consigneeDate = consigneeDate;
	}
	/**
	 * @return the consgId
	 */
	public Integer getConsgId() {
		return consgId;
	}
	/**
	 * @param consgId the consgId to set
	 */
	public void setConsgId(Integer consgId) {
		this.consgId = consgId;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	
}
