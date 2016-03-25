package com.ff.admin.complaints.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.complaints.ServiceRequestForConsignmentTO;
import com.ff.complaints.ServiceRequestForServiceTO;
import com.ff.umc.UserInfoTO;

/**
 * @author sdalli
 *
 */

public class ServiceRequestForConsignmentForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6288435625108795077L;

	public ServiceRequestForConsignmentForm(){
	ServiceRequestForServiceTO serviceRequestTO  = new ServiceRequestForServiceTO();
		
		UserInfoTO userInfoTO = new UserInfoTO();
		/*serviceRequestTO.setConsignmentTO(consignmentTO);
		serviceRequestTO.setConsgTypeTO(consignmentTypeTO);
		serviceRequestTO.setPaperworkTO(cnPaperWorksTO);
		serviceRequestTO.setOriginTO(officeTO1);
		serviceRequestTO.setPincodeTO(pincodeTO);
		serviceRequestTO.setProductTO(productTO);
		serviceRequestTO.setComplaintOriginOfficeTO(officeTO2);
		serviceRequestTO.setUserTO(userTO);*/
		serviceRequestTO.setUserInfoTO(userInfoTO);
		
		setTo(serviceRequestTO);
		setTo(new ServiceRequestForConsignmentTO());
	}
}
