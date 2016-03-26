package com.ff.web.jobservice.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.jobservices.JobServicesTO;
/**
 * @author mrohini
 *
 */
public class JobServiceForm extends CGBaseForm {

	private static final long serialVersionUID = 1L;

	public JobServiceForm() {
		setTo(new JobServicesTO());
	}
}
