package com.ff.umc.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.umc.EmployeeUserTO;


/**
 * @author nihsingh
 *
 */
public class CreateEmployeeForm extends CGBaseForm {

	private static final long serialVersionUID = -2572660663319245609L;
	private String userName;
	private String checkbox;
	
	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * default constructor to set the new TO object.
	 */
	public CreateEmployeeForm() {
		
		setTo(new EmployeeUserTO());
	}




}
