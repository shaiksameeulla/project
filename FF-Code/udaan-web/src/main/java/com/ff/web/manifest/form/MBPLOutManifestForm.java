package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.MBPLOutManifestTO;

/**
 * @author preegupt
 *
 */
public class MBPLOutManifestForm extends CGBaseForm {

	private static final long serialVersionUID = 4369631002533618681L;

	
	public MBPLOutManifestForm() {
		setTo(new MBPLOutManifestTO());
	}

}
