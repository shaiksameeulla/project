package com.ff.admin.heldup.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.heldup.HeldUpTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.umc.UserTO;

// TODO: Auto-generated Javadoc
/**
 * The Class HeldUpForm.
 * 
 * @author narmdr
 */
public class HeldUpForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5089590839303642681L;

	/**
	 * Instantiates a new held up form.
	 */
	public HeldUpForm() {
		HeldUpTO heldUpTO = new HeldUpTO();		
		OfficeTO officeTO = new OfficeTO();
		EmployeeTO employeeTO = new EmployeeTO();
		ReasonTO reasonTO = new ReasonTO();
		UserTO userTO = new UserTO();
		
		heldUpTO.setOfficeTO(officeTO);
		heldUpTO.setReasonTO(reasonTO);
		heldUpTO.setEmployeeTO(employeeTO);
		heldUpTO.setUserTO(userTO);
		
		setTo(heldUpTO);
	}
}
