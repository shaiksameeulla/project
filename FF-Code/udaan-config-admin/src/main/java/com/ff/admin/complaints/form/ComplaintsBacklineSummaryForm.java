package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestTO;

public class ComplaintsBacklineSummaryForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7047176644242768470L;

	public ComplaintsBacklineSummaryForm(){
		/*UserTO userTO=new UserTO();
		ComplaintsBacklineSummaryTO summaryTO=new ComplaintsBacklineSummaryTO();*/
		/*summaryTO.setUserTO(userTO);*/
		ServiceRequestTO serviceRequestTO = new ServiceRequestTO();
		setTo(serviceRequestTO);
	}
}
