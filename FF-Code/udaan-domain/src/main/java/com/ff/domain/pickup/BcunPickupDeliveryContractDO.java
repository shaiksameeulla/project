/**
 * 
 */
package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;


/**
 * @author mohammes
 *
 */
public class BcunPickupDeliveryContractDO extends CGFactDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -171870989989494523L;

	private Integer officeId;
	private Integer customerId;
	
	private Integer contractId;
	private String contractType;
	private String status;
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
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
	 * @return the contractId
	 */
	public Integer getContractId() {
		return contractId;
	}
	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
