package com.ff.web.manifest.form;

import com.capgemini.lbs.framework.form.CGBaseForm;
import com.ff.manifest.BranchOutManifestParcelTO;

public class BranchOutManifestParcelForm extends CGBaseForm {
	
	private static final long serialVersionUID = -8250629582478090369L;
	
	public BranchOutManifestParcelForm() {
		setTo(new BranchOutManifestParcelTO());
	}
	
}
