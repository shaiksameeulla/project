package com.ff.pickup;

import com.ff.business.CustomerTO;
import com.ff.organization.OfficeTO;

import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author hkansagr
 */

public class PickupDeliveryContractTO extends CGBaseTO{

	private static final long serialVersionUID = 1L;

	private Integer contractId;
	private CustomerTO customer;
	private OfficeTO office;
	private String contractType;
	private String status;
	
	private Integer createdBy;
	private Integer updatedBy;
	
		
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the contractId
	 */
	public Integer getContractId() {
		return contractId;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	/**
	 * @return the customer
	 */
	public CustomerTO getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerTO customer) {
		this.customer = customer;
	}
	/**
	 * @return the office
	 */
	public OfficeTO getOffice() {
		return office;
	}
	/**
	 * @param office the office to set
	 */
	public void setOffice(OfficeTO office) {
		this.office = office;
	}
	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}
	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
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
