package com.ff.umc.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.umc.RoleAssignmentTO;

public class UserRoleAssignmentForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8021935847095063440L;

	public UserRoleAssignmentForm() {
		setTo(new RoleAssignmentTO());
	}
}
