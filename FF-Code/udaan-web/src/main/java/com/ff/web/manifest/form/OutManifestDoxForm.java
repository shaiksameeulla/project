package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.OutManifestDoxTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

/**
 * The Class OutManifestDoxForm.
 */
public class OutManifestDoxForm extends CGBaseForm {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2307111481316379233L;
	
	/**
	 * Instantiates a new out manifest dox form.
	 */
	public OutManifestDoxForm() {
		OutManifestDoxTO outManifestDoxTO=new OutManifestDoxTO();
		ConsignmentTypeTO consignmentTypeTO=new ConsignmentTypeTO();
		outManifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
		setTo(outManifestDoxTO);
	}
}
