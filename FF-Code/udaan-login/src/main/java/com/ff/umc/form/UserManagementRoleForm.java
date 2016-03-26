package com.ff.umc.form;



/**
 * Author : Narasimha Rao Kattunga
 * Date : Nov - 05 - 2012
 */

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.umc.UserRolesTO;

public class UserManagementRoleForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8372767235799922652L;

	public UserManagementRoleForm() {
		setTo(new UserRolesTO());
	}
}
