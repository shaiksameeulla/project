package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

public class PaymentModeDO extends CGMasterDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5191751607716513072L;

	private Integer paymentId;
	private String paymentCode;
	private String paymentType;
	private String description;
	private String status = "A";
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
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
