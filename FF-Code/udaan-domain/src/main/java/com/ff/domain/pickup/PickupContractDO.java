/**
 * 
 */
package com.ff.domain.pickup;

import java.io.Serializable;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;


/**
 * @author uchauhan
 *
 */
public class PickupContractDO  extends CGFactDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8622942914290662902L;
	private int contractId;
	private CustomerDO customer;
	private OfficeDO office;
	private String status;
	/**
	 * @return the contractId
	 */
	public int getContractId() {
		return contractId;
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
	/**
	 * @return the customer
	 */
	public CustomerDO getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(CustomerDO customer) {
		this.customer = customer;
	}
	/**
	 * @return the office
	 */
	public OfficeDO getOffice() {
		return office;
	}
	/**
	 * @param office the office to set
	 */
	public void setOffice(OfficeDO office) {
		this.office = office;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	
	

}
