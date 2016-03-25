package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestFollowupTO;
import com.ff.complaints.ServiceRequestTO;

/**
 * @author prmeher
 *
 */
public class ServiceRequestFollowupForm extends CGBaseForm{

	private static final long serialVersionUID = 1L;
	
	public ServiceRequestFollowupForm() {
		ServiceRequestFollowupTO folloupTO = new ServiceRequestFollowupTO();
		folloupTO.setServiceRequestTO(new ServiceRequestTO());
		setTo(folloupTO);
	}
}

