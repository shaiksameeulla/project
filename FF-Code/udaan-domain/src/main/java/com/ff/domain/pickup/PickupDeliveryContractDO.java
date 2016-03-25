/**
 * 
 */
package com.ff.domain.pickup;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;

/**
 * @author uchauhan
 *
 */
public class PickupDeliveryContractDO extends CGFactDO{

	private static final long serialVersionUID = 6488356119140109635L;
	
	private Integer contractId;
	private CustomerDO customer;
	private OfficeDO office;
	private String contractType;
	private String status;
	
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public CustomerDO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDO customer) {
		this.customer = customer;
	}
	public OfficeDO getOffice() {
		return office;
	}
	public void setOffice(OfficeDO office) {
		this.office = office;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
