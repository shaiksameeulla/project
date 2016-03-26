package com.ff.umc.form;

import com.capgemini.lbs.framework.form.CGBaseForm;

import com.ff.umc.PasswordTO;

/**
 * @author nihsingh
 *
 */
public class ForgotPasswordForm extends CGBaseForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518913466067357917L;

	/**
	 * The forgot Password form
	 */
	public  ForgotPasswordForm() {
		setTo(new PasswordTO());
	}
}
