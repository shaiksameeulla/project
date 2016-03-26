package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.ThirdPartyBPLOutManifestTO;

public class ThirdPartyBPLOutManifestForm extends CGBaseForm {

	private static final long serialVersionUID = -802950474211588662L;

	public ThirdPartyBPLOutManifestForm() {
		ThirdPartyBPLOutManifestTO thirdPartyBPLOutManifestTO = new ThirdPartyBPLOutManifestTO();
		setTo(thirdPartyBPLOutManifestTO);
	}
}
