package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.ThirdPartyOutManifestDoxTO;

public class ThirdPartyOutManifestDoxForm extends CGBaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5938860958845043903L;
	
	public ThirdPartyOutManifestDoxForm() {
		setTo(new ThirdPartyOutManifestDoxTO());
	}
}
