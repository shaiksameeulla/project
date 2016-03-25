package com.ff.serviceOfferring;

import com.capgemini.lbs.framework.to.CGBaseTO;

public class PaymentModeTO extends CGBaseTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5664530553793390083L;
	/**
	 * 
	 */

	
	private int paymentId;
	private String paymentCode;
	private String paymentType;
	private String description;
	private String status;
	
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
