package com.ff.domain.serviceOffering;

import com.capgemini.lbs.framework.domain.CGMasterDO;
import com.ff.domain.tracking.ProcessDO;

/**
 * @author hkansagr
 */

public class PaymentModeProcessMapDO extends CGMasterDO {

	private static final long serialVersionUID = 1L;

	/** primary key */
	private Integer paymentModeProcessMapId;
	
	/** FK with PaymentModeDO i.e. ff_d_payment_mode */
	private PaymentModeDO paymentModeDO;
	
	/** FK with processDO i.e. ff_d_process */
	private ProcessDO processDO; 
	
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
	 * @return the paymentModeDO
	 */
	public PaymentModeDO getPaymentModeDO() {
		return paymentModeDO;
	}
	/**
	 * @param paymentModeDO the paymentModeDO to set
	 */
	public void setPaymentModeDO(PaymentModeDO paymentModeDO) {
		this.paymentModeDO = paymentModeDO;
	}
	/**
	 * @return the processDO
	 */
	public ProcessDO getProcessDO() {
		return processDO;
	}
	/**
	 * @param processDO the processDO to set
	 */
	public void setProcessDO(ProcessDO processDO) {
		this.processDO = processDO;
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
