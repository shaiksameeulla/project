package com.ff.umc.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.umc.PasswordTO;
import com.ff.umc.UserTO;


/**
 * @author nihsingh
 *
 */
public class LoginForm extends CGBaseForm {

/**
	 * 
	 */
	private static final long serialVersionUID = -2572660663319245609L;
private PasswordTO pwdto=null;


public PasswordTO getPwdto() {
	return pwdto;
}


public void setPwdto(PasswordTO pwdto) {
	this.pwdto = pwdto;
}


/**
 * default constructor to set the new TO object.
 */
public LoginForm() {
	
	setTo(new UserTO());
	setPwdto(new PasswordTO());
}
}
