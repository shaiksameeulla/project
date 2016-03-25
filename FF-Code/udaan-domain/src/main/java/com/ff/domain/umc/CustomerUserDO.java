package com.ff.domain.umc;

import com.capgemini.lbs.framework.domain.CGFactDO;
import com.ff.domain.business.CustomerDO;

/**
 * @author nihsingh
 *
 */
public class CustomerUserDO extends CGFactDO 
{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 7468565755602632752L;
	private Integer customerUserId;
	  private Integer userId;  
	  private CustomerDO custDO;
	
	 /**
	  * @desc get CustomerUserId
	 * @return customerUserId
	 */
	public Integer getCustomerUserId() {
		return customerUserId;
	}
	
	
	/**
	 * @desc set CustomerUserId
	 * @param customerUserId
	 */
	public void setCustomerUserId(Integer customerUserId) {
		this.customerUserId = customerUserId;
	}
	
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**
	 * @desc get CustDO
	 * @return CustomerDO
	 */
	public CustomerDO getCustDO() {
		return custDO;
	}
	
	
	/**
	 * @desc set CustDO
	 * @param custDO
	 */
	public void setCustDO(CustomerDO custDO) {
		this.custDO = custDO;
	}
		

	
}
