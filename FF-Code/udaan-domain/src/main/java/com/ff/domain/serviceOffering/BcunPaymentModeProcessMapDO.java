package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;

/**
 * @author hkansagr
 */

public class BcunPaymentModeProcessMapDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Integer paymentModeProcessMapId;
	
	/** FK with PaymentModeDO i.e. ff_d_payment_mode */
	private Integer paymentMode;
	
	/** FK with processDO i.e. ff_d_process */
	private Integer process; 
	
	/** The status. */
	private String status = "A";

	/**
	 * @return the paymentModeProcessMapId
	 */
	public Integer getPaymentModeProcessMapId() {
		return paymentModeProcessMapId;
	}

	/**
	 * @param paymentModeProcessMapId the paymentModeProcessMapId to set
	 */
	public void setPaymentModeProcessMapId(Integer paymentModeProcessMapId) {
		this.paymentModeProcessMapId = paymentModeProcessMapId;
	}

	/**
	 * @return the paymentMode
	 */
	public Integer getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the process
	 */
	public Integer getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(Integer process) {
		this.process = process;
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

	
}
