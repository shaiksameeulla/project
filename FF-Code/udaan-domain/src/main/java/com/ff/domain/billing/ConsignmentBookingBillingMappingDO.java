/**
 * 
 */
package com.ff.domain.billing;

import java.io.Serializable;
import java.util.Date;

/**
 * @author prmeher
 *
 */
public class ConsignmentBookingBillingMappingDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer bookingId;
	private String consgNumber;
	private Date bookingDate;
	private String shippedToCode;
	private Integer customerId;
	private Integer bookingType;
	private Integer bookingOfficeId;
	private Integer pincodeId;
	private Integer consgTypeId;
	private Integer productId;
	private Integer consgId;
	private Integer applicableRateContract;
	
	/**
	 * @return the applicableRateContract
	 */
	public Integer getApplicableRateContract() {
		return applicableRateContract;
	}
	/**
	 * @param applicableRateContract the applicableRateContract to set
	 */
	public void setApplicableRateContract(Integer applicableRateContract) {
		this.applicableRateContract = applicableRateContract;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the consgNumber
	 */
	public String getConsgNumber() {
		return consgNumber;
	}
	/**
	 * @param consgNumber the consgNumber to set
	 */
	public void setConsgNumber(String consgNumber) {
		this.consgNumber = consgNumber;
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
	 * @return the shippedToCode
	 */
	public String getShippedToCode() {
		return shippedToCode;
	}
	/**
	 * @param shippedToCode the shippedToCode to set
	 */
	public void setShippedToCode(String shippedToCode) {
		this.shippedToCode = shippedToCode;
	}
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the bookingType
	 */
	public Integer getBookingType() {
		return bookingType;
	}
	/**
	 * @param bookingType the bookingType to set
	 */
	public void setBookingType(Integer bookingType) {
		this.bookingType = bookingType;
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
	 * @return the pincodeId
	 */
	public Integer getPincodeId() {
		return pincodeId;
	}
	/**
	 * @param pincodeId the pincodeId to set
	 */
	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	/**
	 * @return the consgTypeId
	 */
	public Integer getConsgTypeId() {
		return consgTypeId;
	}
	/**
	 * @param consgTypeId the consgTypeId to set
	 */
	public void setConsgTypeId(Integer consgTypeId) {
		this.consgTypeId = consgTypeId;
	}
	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	
}
