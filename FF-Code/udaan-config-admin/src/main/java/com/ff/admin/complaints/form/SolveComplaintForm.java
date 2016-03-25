/**
 * 
 */
package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestTO;

/**
 * @author abarudwa
 *
 */
public class SolveComplaintForm extends CGBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SolveComplaintForm() {
		ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
		setTo(serviceRequestTO);
	}
}
