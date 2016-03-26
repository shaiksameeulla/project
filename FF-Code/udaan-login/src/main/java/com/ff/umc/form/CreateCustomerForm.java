package com.ff.umc.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.umc.CustomerUserTO;

/**
 * @author nihsingh
 *
 */
public class CreateCustomerForm extends CGBaseForm {

	private static final long serialVersionUID = -2572660663319245609L;
	private String userName;

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * default constructor to set the new TO object.
	 */
	public CreateCustomerForm() {
		CustomerUserTO custuser = new CustomerUserTO();
		CustomerTO cust = new CustomerTO();
		CustomerTypeTO custType = new CustomerTypeTO();
		cust.setCustomerTypeTO(custType);
		custuser.setCustTO(cust);
		setTo(custuser);
		//setTo(new CustomerUserTO());
	}

}
