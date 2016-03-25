package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceTransferTO;

/**
 * @author sdalli
 *
 */
public class ServiceRequestForServiceForm extends CGBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7047176644242768470L;
	public ServiceRequestTO to;
	public ServiceRequestForServiceForm(){
		super();
		to=new ServiceRequestTO();
		to.setTransferTO(new ServiceTransferTO());
		setTo(to);
	}
}
