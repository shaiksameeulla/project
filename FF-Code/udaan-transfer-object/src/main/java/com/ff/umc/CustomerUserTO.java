package com.ff.umc;

import com.capgemini.lbs.framework.to.CGBaseTO;
import com.ff.business.CustomerTO;

/**
 * @author nihsingh
 *
 */
public class CustomerUserTO extends CGBaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6274349399451222785L;

	private Integer userId;
	private CustomerTO custTO;
	private UserTO userTO;
	private Integer customerUserId;

	/**
	 * @desc get UserId
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	
	
	/**
	 * @desc set UserId
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
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
	
	
	/**
	 * @desc set CustTO
	 * @param custTO
	 */
	public void setCustTO(CustomerTO custTO) {
		this.custTO = custTO;
	}
	
	
	/**
	 * @desc set UserTO
	 * @param userTO
	 */
	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}
	
	
	/**
	 * @desc get CustTO
	 * @return CustomerTO
	 */
	public CustomerTO getCustTO() {
		if (custTO == null)
			custTO = new CustomerTO();
		return custTO;
	}
	
	
	/**
	 * @desc get UserTO
	 * @return UserTO
	 */
	public UserTO getUserTO() {
		if (userTO == null)
			userTO = new UserTO();
		return userTO;
	}

}
