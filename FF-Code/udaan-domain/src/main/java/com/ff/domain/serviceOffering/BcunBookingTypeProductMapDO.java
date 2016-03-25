package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class BcunBookingTypeProductMapDO extends CGMasterDO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 3478260031269258509L;

	private Integer bookingTypeProductMapId;
	private Integer bookingTypeId;
	private Integer productId;
	private String status = "A";
	
	public Integer getBookingTypeProductMapId() {
		return bookingTypeProductMapId;
	}
	public void setBookingTypeProductMapId(Integer bookingTypeProductMapId) {
		this.bookingTypeProductMapId = bookingTypeProductMapId;
	}
	public Integer getBookingTypeId() {
		return bookingTypeId;
	}
	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}