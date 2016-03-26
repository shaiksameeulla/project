package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.OutManifestParcelTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Class OutManifestParcelForm.
 */
public class OutManifestParcelForm extends CGBaseForm {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new out manifest parcel form.
	 */
	public OutManifestParcelForm() {
		OutManifestParcelTO outManifestParcelTO = new OutManifestParcelTO();
		outManifestParcelTO.setConsignmentTypeTO(new ConsignmentTypeTO());
		setTo(outManifestParcelTO);
	}
}
