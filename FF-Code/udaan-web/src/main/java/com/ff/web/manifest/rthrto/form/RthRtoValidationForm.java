package com.ff.web.manifest.rthrto.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.rthrto.RthRtoValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoValidationForm.
 * 
 * @author narmdr
 */
public class RthRtoValidationForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8975217135289682074L;

	/**
	 * Instantiates a new rth rto validation form.
	 */
	public RthRtoValidationForm() {
		final RthRtoValidationTO rthRtoValidationTO = new RthRtoValidationTO();
		final OfficeTO officeTO = new OfficeTO();
		final ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		final ProcessTO processTO = new ProcessTO();
		rthRtoValidationTO.setOfficeTO(officeTO);
		rthRtoValidationTO.setProcessTO(processTO);
		rthRtoValidationTO.setConsignmentTypeTO(consignmentTypeTO);
		
		setTo(rthRtoValidationTO);
	}
}
