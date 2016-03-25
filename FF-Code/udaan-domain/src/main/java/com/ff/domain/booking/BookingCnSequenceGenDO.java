package com.ff.domain.booking;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ProductDO;

public class BookingCnSequenceGenDO extends CGFactDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2612570055891955859L;
	
	private Integer bookingCnSequenceGenId;
	private ProductDO productId;
	private OfficeDO officeId;
	private Integer increamentValue;
	private Integer lastGeneratedSeq;
	private Integer lengthOfGeneratedSeq;
	private String reinitializeRequired;
	
	public Integer getBookingCnSequenceGenId() {
		return bookingCnSequenceGenId;
	}
	public void setBookingCnSequenceGenId(Integer bookingCnSequenceGenId) {
		this.bookingCnSequenceGenId = bookingCnSequenceGenId;
	}
	public ProductDO getProductId() {
		return productId;
	}
	public void setProductId(ProductDO productId) {
		this.productId = productId;
	}
	public OfficeDO getOfficeId() {
		return officeId;
	}
	public void setOfficeId(OfficeDO officeId) {
		this.officeId = officeId;
	}
	public Integer getIncreamentValue() {
		return increamentValue;
	}
	public void setIncreamentValue(Integer increamentValue) {
		this.increamentValue = increamentValue;
	}
	public Integer getLastGeneratedSeq() {
		return lastGeneratedSeq;
	}
	public void setLastGeneratedSeq(Integer lastGeneratedSeq) {
		this.lastGeneratedSeq = lastGeneratedSeq;
	}
	public Integer getLengthOfGeneratedSeq() {
		return lengthOfGeneratedSeq;
	}
	public void setLengthOfGeneratedSeq(Integer lengthOfGeneratedSeq) {
		this.lengthOfGeneratedSeq = lengthOfGeneratedSeq;
	}
	public String getReinitializeRequired() {
		return reinitializeRequired;
	}
	public void setReinitializeRequired(String reinitializeRequired) {
		this.reinitializeRequired = reinitializeRequired;
	}
	
}
