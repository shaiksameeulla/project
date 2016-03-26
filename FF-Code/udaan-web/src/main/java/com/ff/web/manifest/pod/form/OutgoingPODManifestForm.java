/**
 * 
 */
package com.ff.web.manifest.pod.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.geography.CityTO;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;

/**
 * @author nkattung
 * 
 */
public class OutgoingPODManifestForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Outgoing POD Manifest Form
	 */
	public OutgoingPODManifestForm() {
		OfficeTO dispachOfficeTO = new OfficeTO();
		PODManifestTO podManifestTO = new PODManifestTO();
		podManifestTO.setDispachOfficeTO(dispachOfficeTO);
		setTo(podManifestTO);
	}

}
