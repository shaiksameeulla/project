/**
 * 
 */
package com.ff.web.manifest.pod.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.pod.PODManifestTO;
import com.ff.organization.OfficeTO;

/**
 * @author nkattung
 *
 */
public class IncomingPODManifestForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * To set PODManifestTO to form
	 */
	public IncomingPODManifestForm(){
		OfficeTO dispachOfficeTO = new OfficeTO();
		PODManifestTO podManifestTO = new PODManifestTO();
		podManifestTO.setDispachOfficeTO(dispachOfficeTO);
		setTo(podManifestTO);
	}

}
